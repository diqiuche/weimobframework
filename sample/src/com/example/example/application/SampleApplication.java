package com.example.example.application;

import org.apache.http.HttpClientConnection;

import com.hs.framework.core.Framework;
import com.hs.framework.core.FrameworkConfiguration;
import com.hs.framework.core.HttpRequestEngine.HttpRequestConfiguration;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;

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
		.writeDebugLogs()
		.build();
		Framework.getInstance().init(configuration);
	}
}
