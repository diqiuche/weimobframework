package com.hs.framework.thirdpart.api.sina;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.hs.framework.database.FrameworkSharePreference;
import com.hs.framework.utils.L;
import com.hs.framework.utils.Util;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 *
 * @author wanghuan
 * @date 2014年10月21日 下午1:12:25
 * @email hunter.v.wang@gmail.com
 *
 */
public class SinaObject {
	
	public static final String API_UID = "weimob_weixin_api_id";
	public static final String API_TOKEN = "weimob_weixin_api_token";
	public static final String API_REFRESH_TOKEN = "weimob_weixin_api_refresh_token";
	public static final String API_EXPIRESIN = "weimob_weixin_api_expiress_in";
	public static final String API_IDSTR = "weimob_weixin_api_idstr";
	public static final String API_NAME = "weimob_weixin_api_name";
	public static final String API_LOCATION = "weimob_weixin_api_location";
	public static final String API_HEADIMGURL = "weimob_weixin_api_headimgurl";
	public static final String API_GENDER = "weimob_weixin_api_gender";
	
	public static final String DEFAULT_LAT = "0.0";
	public static final String DEFAULT_LON = "0.0";

	private Context context;
	
	private String appKey;
	private String appSecret;
	private String redirectUrl;
	private String scope;
	
	private String uid;
	private String token;
	private String refreshToken;
	private long expiresin;
	private long expirestime;
	private String idstr;
	private String name;
	private String location;
	private String headimgurl;
	private String gender;
	
	
	/** 微博 Web 授权类，提供登陆等功能  */
    private WeiboAuth mWeiboAuth;
    
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    
    private Oauth2AccessToken oauth2AccessToken;
    
    private SinaCallback sinaCallback;
	
	private static SinaObject object;
	
	private SinaObject(Context context){
		this.context = context;
	}

	public static SinaObject getInstance(Context context){
		if(object == null){
			object = new SinaObject(context);
		}
		return object;
	}
	
	/**
	 * 
	 * @param sinaCallback
	 */
	public void login(Activity activity , SinaCallback sinaCallback){
		this.sinaCallback = sinaCallback;
		mWeiboAuth = new WeiboAuth(activity, 
        		appKey, 
        		redirectUrl,
        		scope);
		mSsoHandler = new SsoHandler(activity, mWeiboAuth);
        mSsoHandler.authorize(authListener);
	}
	
	public void authorizeCallBack(int requestCode, int resultCode, Intent data){
		 // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
	}
	
	public WeiboAuthListener getAuthListener(){
		return authListener;
	}
	
	/**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
	public WeiboAuthListener authListener = new WeiboAuthListener(){

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			L.d("SinaObject -> onCancel()");
			if(sinaCallback != null)sinaCallback.onCancel();
		}

		@Override
		public void onComplete(Bundle arg0) {
			// TODO Auto-generated method stub
			L.d("SinaObject -> onComplete()");
			oauth2AccessToken = Oauth2AccessToken.parseAccessToken(arg0);
			UsersAPI usersAPI = new UsersAPI(oauth2AccessToken);
			long uid = Long.parseLong(oauth2AccessToken.getUid());
			usersAPI.show(uid, requestListener4userinfo);
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			L.d("SinaObject -> onWeiboException()");
			if(sinaCallback != null)sinaCallback.onWeiboException();
		}
		
	};
	
	private RequestListener requestListener4userinfo = new RequestListener() {
		
		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			L.d("requestListener4userinfo -> onWeiboException()");
			if(sinaCallback != null)sinaCallback.onWeiboException();
		}
		
		@Override
		public void onComplete(String arg0) {
			// TODO Auto-generated method stub
			L.d("requestListener4userinfo -> onComplete()");
			if(!Util.isEmpty(arg0)){
				User user = new User();
				user = user.parse(arg0);
				L.d("requestListener4userinfo -> onComplete()" + user.toString());
				setUid(oauth2AccessToken.getUid());
				setExpiresin(oauth2AccessToken.getExpiresTime());
				setToken(oauth2AccessToken.getToken());
				setRefreshToken(oauth2AccessToken.getRefreshToken());;
				setIdstr(user.idstr);
				setName(user.name);
				setLocation(user.location);
				setHeadimgurl(user.avatar_hd);
				setGender(user.gender);
				if(sinaCallback != null)sinaCallback.onSuccess();
			}else{
				if(sinaCallback != null)sinaCallback.onWeiboException();
			}
		}
	};
	
	/**
	 * 保存新浪微博授权信息
	 * @return
	 */
	public boolean saveSinaObject(){
		return FrameworkSharePreference.saveSinaObject(context, this);
	}
	
	/**
	 * 读取已经保存的新浪微博授权信息
	 * @return
	 */
	public SinaObject readSinaObject(){
		FrameworkSharePreference.readSinaObject(context);
		Oauth2AccessToken oauth2AccessToken = new Oauth2AccessToken();
		oauth2AccessToken.setUid(uid);
		oauth2AccessToken.setToken(token);
		oauth2AccessToken.setRefreshToken(refreshToken);
		oauth2AccessToken.setExpiresTime(expiresin);
		setOauth2AccessToken(oauth2AccessToken);
		return this;
	}
	
	/**
	 * 清空已经保存的新浪微博授权信息
	 * @return
	 */
	public boolean cleanSinaObject(){
		return FrameworkSharePreference.cleanSinaObject(context);
	}
	
	/**
	 * 分享文本
	 * @param content 分享的文本内容
	 * @param requestListener 分享的回调
	 */
	public void share(String content , RequestListener requestListener){
		StatusesAPI statusesAPI = new StatusesAPI(oauth2AccessToken);
		statusesAPI.update(content, DEFAULT_LAT, DEFAULT_LON, requestListener);
	}
	
	/**
	 * 分享文本及图片（通过图片 bitmap 方式）
	 * @param content 分享的文本内容
	 * @param bitmap 分享的图片 bitmap 
	 * @param requestListener 分享的回调
	 */
	public void share(String content , Bitmap bitmap , RequestListener requestListener){
		StatusesAPI statusesAPI = new StatusesAPI(oauth2AccessToken);
		statusesAPI.upload(content, bitmap, DEFAULT_LAT, DEFAULT_LON, requestListener);
	}
	
	/**
	 * 分享文本及图片（通过图片 URL 方式）
	 * @param content 分享的文本内容
	 * @param imageUrl 分享的图片 URL 必须以HTTP开头
	 * @param requestListener 分享的回调
	 */
	public void share(String content , String imageUrl , RequestListener requestListener){
		StatusesAPI statusesAPI = new StatusesAPI(oauth2AccessToken);
		statusesAPI.uploadUrlText(content, imageUrl, "", DEFAULT_LAT, DEFAULT_LON, requestListener);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isOAuthed(){
		return readSinaObject().getOauth2AccessToken().isSessionValid();
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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public SinaCallback getSinaCallback() {
		return sinaCallback;
	}

	public void setSinaCallback(SinaCallback sinaCallback) {
		this.sinaCallback = sinaCallback;
	}

	public Oauth2AccessToken getOauth2AccessToken() {
		return oauth2AccessToken;
	}

	public void setOauth2AccessToken(Oauth2AccessToken oauth2AccessToken) {
		this.oauth2AccessToken = oauth2AccessToken;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpiresin() {
		return expiresin;
	}

	public void setExpiresin(long expiresin) {
		this.expiresin = expiresin;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


@Override
	public String toString() {
		return "SinaObject [appKey=" + appKey + ", appSecret=" + appSecret
				+ ", redirectUrl=" + redirectUrl + ", scope=" + scope
				+ ", uid=" + uid + ", token=" + token + ", refreshToken="
				+ refreshToken + ", expiresin=" + expiresin + ", idstr="
				+ idstr + ", name=" + name + ", location=" + location
				+ ", headimgurl=" + headimgurl + ", gender=" + gender + "]";
	}


/**
 * 用户信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
class User {

    /** 用户UID（int64） */
    public String id;
    /** 字符串型的用户 UID */
    public String idstr;
    /** 用户昵称 */
    public String screen_name;
    /** 友好显示名称 */
    public String name;
    /** 用户所在省级ID */
    public int province;
    /** 用户所在城市ID */
    public int city;
    /** 用户所在地 */
    public String location;
    /** 用户个人描述 */
    public String description;
    /** 用户博客地址 */
    public String url;
    /** 用户头像地址，50×50像素 */
    public String profile_image_url;
    /** 用户的微博统一URL地址 */
    public String profile_url;
    /** 用户的个性化域名 */
    public String domain;
    /** 用户的微号 */
    public String weihao;
    /** 性别，m：男、f：女、n：未知 */
    public String gender;
    /** 粉丝数 */
    public int followers_count;
    /** 关注数 */
    public int friends_count;
    /** 微博数 */
    public int statuses_count;
    /** 收藏数 */
    public int favourites_count;
    /** 用户创建（注册）时间 */
    public String created_at;
    /** 暂未支持 */
    public boolean following;
    /** 是否允许所有人给我发私信，true：是，false：否 */
    public boolean allow_all_act_msg;
    /** 是否允许标识用户的地理位置，true：是，false：否 */
    public boolean geo_enabled;
    /** 是否是微博认证用户，即加V用户，true：是，false：否 */
    public boolean verified;
    /** 暂未支持 */
    public int verified_type;
    /** 用户备注信息，只有在查询用户关系时才返回此字段 */
    public String remark;
//    /** 用户的最近一条微博信息字段 */
//    public Status status;
    /** 是否允许所有人对我的微博进行评论，true：是，false：否 */
    public boolean allow_all_comment;
    /** 用户大头像地址 */
    public String avatar_large;
    /** 用户高清大头像地址 */
    public String avatar_hd;
    /** 认证原因 */
    public String verified_reason;
    /** 该用户是否关注当前登录用户，true：是，false：否 */
    public boolean follow_me;
    /** 用户的在线状态，0：不在线、1：在线 */
    public int online_status;
    /** 用户的互粉数 */
    public int bi_followers_count;
    /** 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语 */
    public String lang;
    
    /** 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段 */
    public String star;
    public String mbtype;
    public String mbrank;
    public String block_word;
    
    public  User parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public User parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        
        User user = new User();
        user.id                 = jsonObject.optString("id", "");
        user.idstr              = jsonObject.optString("idstr", "");
        user.screen_name        = jsonObject.optString("screen_name", "");
        user.name               = jsonObject.optString("name", "");
        user.province           = jsonObject.optInt("province", -1);
        user.city               = jsonObject.optInt("city", -1);
        user.location           = jsonObject.optString("location", "");
        user.description        = jsonObject.optString("description", "");
        user.url                = jsonObject.optString("url", "");
        user.profile_image_url  = jsonObject.optString("profile_image_url", "");
        user.profile_url        = jsonObject.optString("profile_url", "");
        user.domain             = jsonObject.optString("domain", "");
        user.weihao             = jsonObject.optString("weihao", "");
        user.gender             = jsonObject.optString("gender", "");
        user.followers_count    = jsonObject.optInt("followers_count", 0);
        user.friends_count      = jsonObject.optInt("friends_count", 0);
        user.statuses_count     = jsonObject.optInt("statuses_count", 0);
        user.favourites_count   = jsonObject.optInt("favourites_count", 0);
        user.created_at         = jsonObject.optString("created_at", "");
        user.following          = jsonObject.optBoolean("following", false);
        user.allow_all_act_msg  = jsonObject.optBoolean("allow_all_act_msg", false);
        user.geo_enabled        = jsonObject.optBoolean("geo_enabled", false);
        user.verified           = jsonObject.optBoolean("verified", false);
        user.verified_type      = jsonObject.optInt("verified_type", -1);
        user.remark             = jsonObject.optString("remark", "");
        //user.status             = jsonObject.optString("status", ""); // XXX: NO Need ?
        user.allow_all_comment  = jsonObject.optBoolean("allow_all_comment", true);
        user.avatar_large       = jsonObject.optString("avatar_large", "");
        user.avatar_hd          = jsonObject.optString("avatar_hd", "");
        user.verified_reason    = jsonObject.optString("verified_reason", "");
        user.follow_me          = jsonObject.optBoolean("follow_me", false);
        user.online_status      = jsonObject.optInt("online_status", 0);
        user.bi_followers_count = jsonObject.optInt("bi_followers_count", 0);
        user.lang               = jsonObject.optString("lang", "");
        
        // 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段含义
        user.star               = jsonObject.optString("star", "");
        user.mbtype             = jsonObject.optString("mbtype", "");
        user.mbrank             = jsonObject.optString("mbrank", "");
        user.block_word         = jsonObject.optString("block_word", "");
        
        return user;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", idstr=" + idstr + ", screen_name="
				+ screen_name + ", name=" + name + ", province=" + province
				+ ", city=" + city + ", location=" + location
				+ ", description=" + description + ", url=" + url
				+ ", profile_image_url=" + profile_image_url + ", profile_url="
				+ profile_url + ", domain=" + domain + ", weihao=" + weihao
				+ ", gender=" + gender + ", followers_count=" + followers_count
				+ ", friends_count=" + friends_count + ", statuses_count="
				+ statuses_count + ", favourites_count=" + favourites_count
				+ ", created_at=" + created_at + ", following=" + following
				+ ", allow_all_act_msg=" + allow_all_act_msg + ", geo_enabled="
				+ geo_enabled + ", verified=" + verified + ", verified_type="
				+ verified_type + ", remark=" + remark + ", allow_all_comment="
				+ allow_all_comment + ", avatar_large=" + avatar_large
				+ ", avatar_hd=" + avatar_hd + ", verified_reason="
				+ verified_reason + ", follow_me=" + follow_me
				+ ", online_status=" + online_status + ", bi_followers_count="
				+ bi_followers_count + ", lang=" + lang + ", star=" + star
				+ ", mbtype=" + mbtype + ", mbrank=" + mbrank + ", block_word="
				+ block_word + "]";
	}
    
    
}

}
