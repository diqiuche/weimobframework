package com.hs.framework.thirdpart.api.wxapi;

import org.apache.http.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.hs.framework.core.HttpRequestEngine;
import com.hs.framework.core.HttpRequestEngine.HttpCallback;
import com.hs.framework.core.HttpRequestEngine.HttpEntity;
import com.hs.framework.utils.L;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
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
	
	private Context context;
	private String appKey;
	private String appSecret;
	private String state;
	private String code;
	private String accessToken;
	private String accessSecret;
	private String refreshToken;
	private int expiress;
	private String openid;
	
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

	public int getExpiress() {
		return expiress;
	}

	public void setExpiress(int expiress) {
		this.expiress = expiress;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
		req.scope = "snsapi_userinfo";
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
					expiress = jsonObject.getInt("expires_in");
					if(weixinCallback != null)
						weixinCallback.onSuccess();
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
	
	private void request4Userinfo(){
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.setUrl(getAccesstokenUrl());
		L.i(httpEntity.getUrl());
		HttpRequestEngine.get(httpEntity, httpCallback);
	}

}
