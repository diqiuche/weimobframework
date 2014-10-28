package com.hs.framework.thirdpart.api.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.hs.framework.database.FrameworkSharePreference;
import com.hs.framework.thirdpart.api.sina.StatusesAPI;
import com.hs.framework.utils.L;
import com.hs.framework.utils.Util;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.b.c;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 *
 * @author wanghuan
 * @date 2014年10月21日 下午1:12:54
 * @email hunter.v.wang@gmail.com
 *
 */
public class QqObject{
	
	public static final String API_PAY_TOKEN = "weimob_framework_api_qq_pay_token";
	public static final String API_OPEN_ID = "weimob_framework_api_qq_open_id";
	public static final String API_EXPIRESS_IN = "weimob_framework_api_qq_expiress_in";
	public static final String API_PF = "weimob_framework_api_qq_pf";
	public static final String API_PF_KEY = "weimob_framework_api_qq_pf_key";
	public static final String API_ACCESS_YOKEN = "weimob_framework_api_qq_access_token";
	public static final String API_QQ_HEADIMAGEURL = "weimob_framework_api_qq_qqheadimgurl";
	public static final String API_NICKNAME = "weimob_framework_api_qq_nickname";
	public static final String API_CITY = "weimob_framework_api_qq_city";
	public static final String API_QZONEHEADIMGURL = "weimob_framework_api_qq_qzoneheadimgurl";
	public static final String API_PROVINCE = "weimob_framework_api_qq_province";
	public static final String API_SEX = "weimob_framework_api_qq_sex";
	
	private Context context;
	private QqCallback qqCallback;
	private String appId;
	private String appKey;
	private String scope;
	
	private String payToken;
	private String openId;
	private long expiressin;
	private String pf;
	private String pfKey;
	private String accessToken;
	
	private String qqHeadimgurl;
	private String nickName;
	private String city;
	private String qzoneHeadimgurl;
	private String province;
	private String sex;
	
	private Tencent tencent;
	
	private static QqObject object;
	
	private QqObject(Context context){
		this.context = context;
	}

	public static QqObject getInstance(Context context){
		if(object == null){
			object = new QqObject(context);
		}
		return object;
	}
	
	/**
	 * 
	 * @return
	 */
	public Tencent getTencent(){
		if(tencent == null){
			tencent = Tencent.createInstance(appId, context);
		}
//		if(!tencent.isSessionValid()){
//			L.e("tencent.isSessionValid()");
//		}
		return tencent;
	}
	
	/**
	 * 
	 * @param activity
	 * @param qqCallback
	 */
	public void login(Activity activity , QqCallback qqCallback){
		this.qqCallback = qqCallback;
		if(getTencent().isSessionValid()){
			L.e("tencent.SessionValid()");
			logout();
		}
		getTencent().login(activity, !Util.isEmpty(scope) ? scope : "get_user_info", iUiListener);
	}
	
	/**
	 * TODO
	 */
	private IUiListener iUiListener = new IUiListener() {
		
		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> errorCode : " + arg0.errorCode);
			L.d("QQ Object login -> errorDetail : " + arg0.errorDetail);
			L.d("QQ Object login -> errorMessage : " + arg0.errorMessage);
			if(qqCallback != null) qqCallback.onFailure();
		}
		
		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> onComplete : " + arg0.toString());
			try {
				JSONObject jsonObject = new JSONObject(arg0.toString());
				setPayToken(jsonObject.getString("pay_token"));
				setPf(jsonObject.getString("pf"));
				setOpenId(jsonObject.getString("openid"));
				setPfKey(jsonObject.getString("pfkey"));
				setAccessToken(jsonObject.getString("access_token"));
				setExpiressin(jsonObject.getLong("expires_in")+System.currentTimeMillis());
//				if(qqCallback != null) qqCallback.onSuccess();
				request4Info();
				L.d("QQ Object json -> onComplete ");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				L.d("QQ Object json ->  JSONException" + e.getMessage());
				if(qqCallback != null) qqCallback.onFailure();
				e.printStackTrace();
			}
		}
		
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> onCancel : ");
			if(qqCallback != null) qqCallback.onCancel();
		}
	};
	
	private void request4Info(){
		UserInfo info = new UserInfo(context, getTencent().getQQToken());
		info.getUserInfo(requestInfoListener);
	}
	
	private IUiListener requestInfoListener = new IUiListener() {
		
		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object request4Info -> onError : " + arg0.toString());
			if(qqCallback != null) qqCallback.onFailure();
		}
		
		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object request4Info -> onComplete : " + arg0.toString());
			try {
				JSONObject jsonObject = new JSONObject(arg0.toString());
				setQqHeadimgurl(jsonObject.getString("figureurl_qq_2"));
				setQzoneHeadimgurl(jsonObject.getString("figureurl_2"));
				setNickName(jsonObject.getString("nickname"));
				setCity(jsonObject.getString("city"));
				setProvince(jsonObject.getString("province"));
				setSex(jsonObject.getString("gender"));
				if(qqCallback != null) qqCallback.onSuccess();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if(qqCallback != null) qqCallback.onFailure();
				e.printStackTrace();
			}
		}
		
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			L.d("QQ Object request4Info -> onCancel : ");
			if(qqCallback != null) qqCallback.onCancel();
		}
	};
	
	/**
	 * 登出
	 */
	public void logout(){
		getTencent().logout(context);
	}
	
	/**
	 * 保存 QqObject 信息
	 * @return
	 */
	public boolean saveQqObject(){
		return FrameworkSharePreference.saveQqObject(context, object);
	}
	
	/**
	 * 读取已经保存的 QqObject 信息
	 * @return
	 */
	public QqObject readQqObject(){
		return FrameworkSharePreference.readQqObject(context);
	}
	
	/**
	 * 清空已经保存的 QqObject 信息
	 * @return
	 */
	public boolean cleanQqObject(){
		return FrameworkSharePreference.cleanQqObject(context);
	}
	
	/**
	 * 	示例代码如下：
	 *  private void onClickShare() { 
	 *	    final Bundle params = new Bundle();
	 *	    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
	 *	    params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
	 *	    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
	 *	    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
		    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
		    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
		    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");		
		    mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
		}
	
		调用分享接口的params参数说明如下：
		参数	是否必传	类型	参数说明
		QQShare.SHARE_TO_QQ_KEY_TYPE	必填	Int	分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
		QQShare.PARAM_TARGET_URL	必填	String	这条分享消息被好友点击后的跳转URL。
		QQShare.PARAM_TITLE	必填	String	分享的标题, 最长30个字符。
		QQShare.PARAM_SUMMARY	可选	String	分享的消息摘要，最长40个字。
		QQShare.SHARE_TO_QQ_IMAGE_URL	可选	String	分享图片的URL或者本地路径 
		QQShare.SHARE_TO_QQ_APP_NAME	可选	String	手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
		QQShare.SHARE_TO_QQ_EXT_INT	可选	Int	分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
		QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
		QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
	 */
	public void shareToQQ(Activity activity , Bundle bundle , IUiListener iUiListener){
		getTencent().shareToQQ(activity, bundle, iUiListener);
	}
	
	/**
	 * 示例代码如下：
		private void shareToQzone () {
		　　//分享类型
		　　params.putString(QzoneShare.SHARE_TO_QQ_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
		    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
		    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
		    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
		    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
		    mTencent.shareToQzone(activity, params, new BaseUiListener());
		}
		params参数说明如下：
		参数	是否必传	类型	参数说明
		QzoneShare.SHARE_TO_QQ_KEY_TYPE	选填	Int	SHARE_TO_QZONE_TYPE_IMAGE_TEXT（图文）
		QzoneShare.SHARE_TO_QQ_TITLE	必填	Int	分享的标题，最多200个字符。
		QzoneShare.SHARE_TO_QQ_SUMMARY	选填	String	分享的摘要，最多600字符。
		QzoneShare.SHARE_TO_QQ_TARGET_URL	必填	String	需要跳转的链接，URL字符串。
		QzoneShare.SHARE_TO_QQ_IMAGE_URL	选填	String	分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
		注意:QZone接口暂不支持发送多张图片的能力，若传入多张图片，则会自动选入第一张图片作为预览图。多图的能力将会在以后支持。：
	 */
	public void shareToQZone(Activity activity , Bundle bundle , IUiListener iUiListener){
		getTencent().shareToQzone(activity, bundle, iUiListener);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getPayToken() {
		return payToken;
	}

	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public long getExpiressin() {
		return expiressin;
	}

	public void setExpiressin(long expiressin) {
		this.expiressin = expiressin;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getPfKey() {
		return pfKey;
	}

	public void setPfKey(String pfKey) {
		this.pfKey = pfKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getQqHeadimgurl() {
		return qqHeadimgurl;
	}

	public void setQqHeadimgurl(String qqHeadimgurl) {
		this.qqHeadimgurl = qqHeadimgurl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getQzoneHeadimgurl() {
		return qzoneHeadimgurl;
	}

	public void setQzoneHeadimgurl(String qzoneHeadimgurl) {
		this.qzoneHeadimgurl = qzoneHeadimgurl;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "QqObject [appId=" + appId + ", appKey=" + appKey + ", scope="
				+ scope + ", payToken=" + payToken + ", openId=" + openId
				+ ", expiressin=" + expiressin + ", pf=" + pf + ", pfKey="
				+ pfKey + ", accessToken=" + accessToken + ", qqHeadimgurl="
				+ qqHeadimgurl + ", nickName=" + nickName + ", city=" + city
				+ ", qzoneHeadimgurl=" + qzoneHeadimgurl + ", province="
				+ province + ", sex=" + sex + "]";
	}
	
}
