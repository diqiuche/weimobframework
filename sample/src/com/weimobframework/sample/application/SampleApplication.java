package com.weimobframework.sample.application;

import android.app.Application;

import com.hs.framework.core.Framework;
import com.hs.framework.core.FrameworkConfiguration;
import com.hs.framework.core.HttpRequestEngine.HttpRequestConfiguration;
import com.hs.framework.thirdpart.api.qq.QqObject;
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
	
	private ImageLoaderConfiguration imageLoaderConfiguration;
	
	private HttpRequestConfiguration httpRequestConfiguration;
	
	private WeixinObject weixinObject;
	
	private QqObject qqObject;

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
		weixinObject.setAppKey("wx745063ab298f8230");
		weixinObject.setAppSecret("414509e07d3e4c2030d6500276c54b43");
		weixinObject.setScope("snsapi_userinfo");
		weixinObject.setState("weimob_weixin_login_state");
		
		qqObject = QqObject.getInstance(getApplicationContext());
		qqObject.setAppId("1101980906");
		qqObject.setAppKey("PZkLIELEnMYUVqvs");
		qqObject.setScope("all");
		
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
		.writeDebugLogs()
		.build();
		Framework.getInstance().init(configuration);
	}
}
