package com.hs.framework.thirdpart.api.wxapi;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.hs.framework.core.HttpRequestEngine;
import com.hs.framework.core.HttpRequestEngine.HttpCallback;
import com.hs.framework.core.HttpRequestEngine.HttpEntity;
import com.hs.framework.database.FrameworkSharePreference;
import com.hs.framework.utils.L;
import com.hs.framework.utils.Util;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午1:55:50
 * @email hunter.v.wang@gmail.com
 *
 */
public class WeixinObject {
	
	public static final String API_ACCESS_TOKEN = "weimob_weixin_api_access_token";
	public static final String API_REFRESH_TOKEN = "weimob_weixin_api_refresh_token";
	public static final String API_OPEN_ID = "weimob_weixin_api_open_id";
	public static final String API_EXPIRESSIN = "weimob_weixin_api_expiressin";
	public static final String API_NICKNAME = "weimob_weixin_api_nickname";
	public static final String API_SEX = "weimob_weixin_api_sex";
	public static final String API_LANGUAGE = "weimob_weixin_api_language";
	public static final String API_CITY = "weimob_weixin_api_city";
	public static final String API_PROVINCE = "weimob_weixin_api_province";
	public static final String API_COUNTRY = "weimob_weixin_api_country";
	public static final String API_HEADIMGURL = "weimob_weixin_api_headimgurl";
	public static final String API_PRIVILEGE = "weimob_weixin_api_privilege";
	public static final String API_UNIONID = "weimob_weixin_api_unionid";
	
	public static final int SEX_MALE = 1;
	
	public static final int SEX_FEMALE = 2;
	
	private static final int THUMB_SIZE = 150;
	
	
	private Context context;
	private String appKey;
	private String appSecret;
	private String scope;
	private String state;
	private String code;
	private String accessToken;
	private String accessSecret;
	private String refreshToken;
	private long expiress;
	private String openid;
	
	private String nickname;
	private int sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private String privillege;
	private String unionid;
	
	private static WeixinObject object;
	private IWXAPI iwxapi;
	
	private WeixinCallback weixinCallback;
	
	private WeixinObject(Context context){
		this.context = context;
	}
	
	public static WeixinObject getInstance(Context context){
		if(object == null){
			object = new WeixinObject(context);
		}
		return object;
	}

	public void setIWXAPIEventHandler(IWXAPIEventHandler iwxapiEventHandler){
		this.iwxapiEventHandler = iwxapiEventHandler;
	}
	
	public IWXAPIEventHandler getIWXAPIEventHandler(){
		return iwxapiEventHandler;
	}
	
	public IWXAPI getAPI(){
		if(iwxapi == null){
			iwxapi = WXAPIFactory.createWXAPI(context, appKey, true);
			iwxapi.registerApp(appKey);
		}
		return iwxapi;
	}
	
	/**
	 * action login
	 * @param weixinCallback
	 */
	public void login(WeixinCallback weixinCallback){
		this.weixinCallback = weixinCallback;
		SendAuth.Req req = new SendAuth.Req();
		req.scope = !Util.isEmpty(scope) ? scope : "snsapi_userinfo";
		req.state = state;
		getAPI().sendReq(req);
	}
	
	/**
	 * Handler for handle user's action
	 */
	private IWXAPIEventHandler iwxapiEventHandler = new IWXAPIEventHandler() {
		
		@Override
		public void onResp(BaseResp resp) {
			// TODO Auto-generated method stub
			L.d("[ weixinObject -> resp() ]");
			if(resp instanceof SendAuth.Resp){
				SendAuth.Resp auth_resp = (SendAuth.Resp)resp;
				switch (auth_resp.errCode) {
				case BaseResp.ErrCode.ERR_OK:
					L.d("[ code] " + auth_resp.code);
					code = auth_resp.code;
					request4Accesstoken();
					break;
				case BaseResp.ErrCode.ERR_AUTH_DENIED:
					if(weixinCallback != null)
						weixinCallback.onRefuse();
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					if(weixinCallback != null)
						weixinCallback.onUserCancel();
					break;
				default:
					if(weixinCallback != null)
						weixinCallback.onFailure();
					break;
				}
			}
		}
		
		@Override
		public void onReq(BaseReq req) {
			// TODO Auto-generated method stub
			L.d("[ weixinObject -> req() ]");
		}
	};
	
	/**
	 * action request for access token
	 */
	private void request4Accesstoken(){
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.setUrl(getAccesstokenUrl());
		L.i(httpEntity.getUrl());
		HttpRequestEngine.get(httpEntity, httpCallback);
	}
	
	/**
	 * 
	 */
	private void request4Userinfo(){
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.setUrl(getUserInfoUrl());
		L.i(httpEntity.getUrl());
		HttpRequestEngine.get(httpEntity, request4UserinfoCallback);
	}
	
	/**
	 * HttpCallback for request access token
	 */
	private HttpCallback httpCallback = new HttpCallback(){

		@Override
		public void success(HttpEntity httpEntity) {
			// TODO Auto-generated method stub
			L.d("get access token success : " + httpEntity.getResponse());
			String response = httpEntity.getResponse();
			if(response != null && response.contains("openid")){
				try {
					JSONObject jsonObject = new JSONObject(response);
					accessToken = jsonObject.getString("access_token");
					refreshToken = jsonObject.getString("refresh_token");
					openid = jsonObject.getString("openid");
					expiress = System.currentTimeMillis() + jsonObject.getInt("expires_in");
					request4Userinfo();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					if(weixinCallback != null)
						weixinCallback.onFailure();
					e.printStackTrace();
				}
			}else{
				if(weixinCallback != null)
					weixinCallback.onFailure();
			}
		}

		@Override
		public void failure(HttpEntity httpEntity) {
			// TODO Auto-generated method stub
			L.d("get access token error");
			if(weixinCallback != null){
				weixinCallback.onFailure();
			}
		}
		
	};
	
	/**
	 * 
	 */
	private HttpCallback request4UserinfoCallback = new HttpCallback() {
		
		@Override
		public void success(HttpEntity httpEntity) {
			// TODO Auto-generated method stub
			L.d("userinfo->" + httpEntity.getResponse());
			String response = httpEntity.getResponse();
			if(!Util.isEmpty(response)){
				try {
					JSONObject jsonObject = new JSONObject(response);
					setNickname(jsonObject.getString("nickname"));
					setSex(jsonObject.getInt("sex"));
					setLanguage(jsonObject.getString("language"));
					setCity(jsonObject.getString("city"));
					setProvince(jsonObject.getString("province"));
					setCountry(jsonObject.getString("country"));
					setHeadimgurl(jsonObject.getString("headimgurl"));
					setPrivillege(jsonObject.getString("privilege"));
					setUnionid(jsonObject.getString("unionid"));
					if(weixinCallback != null){
						weixinCallback.onSuccess();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					if(weixinCallback != null){
						weixinCallback.onFailure();
					}
					e.printStackTrace();
				}
			}else{
				if(weixinCallback != null){
					weixinCallback.onFailure();
				}
			}
			
		}
		
		@Override
		public void failure(HttpEntity httpEntity) {
			// TODO Auto-generated method stub
			L.d("userinfo->" + httpEntity.getResponse());
			if(weixinCallback != null){
				weixinCallback.onFailure();
			}
		}
	};
	
	/**
	 * generate https url for request access token
	 * @return
	 */
	private String getAccesstokenUrl(){
		return "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=" + appKey
				+ "&secret=" + appSecret
				+ "&code=" + code
				+ "&grant_type=authorization_code";
	}
	
	/**
	 * generate https url for request user info
	 * @return
	 */
	private String getUserInfoUrl(){
		return "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token=" + accessToken
				+ "&openid=" + openid;
	}
	
	public void share2Friends(String message){
		WXTextObject textObj = new WXTextObject();
		textObj.text = message;
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = message;
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		getAPI().sendReq(req);
	}
	
	public void share2Friends(String message , Bitmap bitmap){
		if(bitmap == null){
			share2Friends(message);
			return;
		}
		WXImageObject imgObj = new WXImageObject(bitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
		bitmap.recycle();
		msg.thumbData = bmpToByteArray(thumbBmp, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		getAPI().sendReq(req);
	}
	
	public void share2FriendsLine(String message){
		WXTextObject textObj = new WXTextObject();
		textObj.text = message;
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = message;
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		getAPI().sendReq(req);
	}
	
	public void share2FriendsLine(String message , Bitmap bitmap){
		if(bitmap == null){
			share2Friends(message);
			return;
		}
		WXImageObject imgObj = new WXImageObject(bitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
		bitmap.recycle();
		msg.thumbData = bmpToByteArray(thumbBmp, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		getAPI().sendReq(req);
	}
	
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean saveObject(){
		return FrameworkSharePreference.saveWeixinObject(context, object);
	}
	
	/**
	 * 
	 */
	public WeixinObject readObject(){
		return FrameworkSharePreference.readWexinObject(context);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean cleanObject(){
		return FrameworkSharePreference.cleanWexinObject(context);
	}
	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpiress() {
		return expiress;
	}

	public void setExpiress(long expiress) {
		this.expiress = expiress;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivillege() {
		return privillege;
	}

	public void setPrivillege(String privillege) {
		this.privillege = privillege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "WeixinObject [appKey=" + appKey + ", appSecret=" + appSecret
				+ ", state=" + state + ", code=" + code + ", accessToken="
				+ accessToken + ", accessSecret=" + accessSecret
				+ ", refreshToken=" + refreshToken + ", expiress=" + expiress
				+ ", openid=" + openid + ", nickname=" + nickname + ", sex="
				+ sex + ", language=" + language + ", city=" + city
				+ ", province=" + province + ", country=" + country
				+ ", headimgurl=" + headimgurl + ", privillege=" + privillege
				+ ", unionid=" + unionid + "]";
	}
	
}
