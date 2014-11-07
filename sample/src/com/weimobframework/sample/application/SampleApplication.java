package com.weimobframework.sample.application;

import android.app.Application;

import com.hs.framework.core.Framework;
import com.hs.framework.core.FrameworkConfiguration;
import com.hs.framework.core.HttpRequestEngine.HttpRequestConfiguration;
import com.hs.framework.thirdpart.api.qq.QqObject;
import com.hs.framework.thirdpart.api.sina.SinaObject;
import com.hs.framework.thirdpart.api.wxapi.WeixinObject;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.map.b.q;

/**
 *
 * @author wanghuan
 * @date 2014年10月15日 下午4:44:24
 * @email hunter.v.wang@gmail.com
 *
 */
public class SampleApplication extends Application{
	
	/** 
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
	
	private ImageLoaderConfiguration imageLoaderConfiguration;
	
	private HttpRequestConfiguration httpRequestConfiguration;
	
	private WeixinObject weixinObject;
	
	private QqObject qqObject;
	
	private SinaObject sinaObject;

	@Override
	public void onCreate() {
		super.onCreate();
		
		imageLoaderConfiguration = new ImageLoaderConfiguration
				.Builder(this)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) 
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		
		httpRequestConfiguration = new HttpRequestConfiguration();
		
		weixinObject = WeixinObject.getInstance(getApplicationContext());
		weixinObject.setAppKey("wxd9f77e179af57c49");
		weixinObject.setAppSecret("aea68de24f31b48023f758a00b372d86");
//		weixinObject.setAppKey("wx0764d2bf6e0cb8b5");
//		weixinObject.setAccessSecret("8540801d40cf97a927a59750983cd187");
		weixinObject.setScope("snsapi_userinfo");
		weixinObject.setState("weimob_weixin_login_state");
		
		qqObject = QqObject.getInstance(getApplicationContext());
		qqObject.setAppId("1101980906");
		qqObject.setAppKey("PZkLIELEnMYUVqvs");
		qqObject.setScope("all");
		
		sinaObject = SinaObject.getInstance(getApplicationContext());
		sinaObject.setAppKey("2274215283");
		sinaObject.setAppSecret("ab9b48d9914d61a82e22a58c5c0501f3");
		sinaObject.setRedirectUrl("https://api.weibo.com/oauth2/default.html");
		sinaObject.setScope(SCOPE);
		
		initFramework();
	}
	
	/**
	 * 初始化Framework
	 */
	private void initFramework() {
		FrameworkConfiguration configuration = new FrameworkConfiguration.Builder(getApplicationContext())
		.development()
		.initImageLoadConfig(imageLoaderConfiguration)
		.initHttpRequestConfig(httpRequestConfiguration)
		.initWeixinConfig(weixinObject)
		.initQqConfig(qqObject)
		.initSinaConfig(sinaObject)
		.writeDebugLogs()
		.build();
		Framework.getInstance().init(configuration);
	}
}
