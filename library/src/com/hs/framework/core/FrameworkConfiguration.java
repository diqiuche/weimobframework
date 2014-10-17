package com.hs.framework.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.hs.framework.core.HttpRequestEngine.HttpRequestConfiguration;
import com.hs.framework.utils.L;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午2:57:18
 * @email hunter.v.wang@gmail.com
 *
 */
public final class FrameworkConfiguration {
	
	/**
	 * 定义程序的运行环境
	 * 默认是开发环境
	 * @author wanghuan
	 */
	public static enum Environment{
		
		/**
		 * 开发环境
		 */
		DEVELOPMENT,
		
		/**
		 * 预发布环境
		 */
		PRE_RELEASE,
		
		/**
		 * 线上环境
		 */
		PRODUCTION
	};
	
	private Environment environment;
	final Context context;
	final Resources resources;
	final ImageLoaderConfiguration imageLoaderConfiguration;
	final HttpRequestConfiguration httpRequestConfiguration;
	
	private FrameworkConfiguration(final Builder builder) {
		context = builder.context;
		resources = context.getResources();
		this.environment = builder.environment;
		this.imageLoaderConfiguration = builder.imageLoaderConfiguration;
		this.httpRequestConfiguration = builder.httpRequestConfiguration;
		L.writeDebugLogs(builder.writeLogs);
	}
	
	/**
	 * 框架配置对象的构建类 {@link FrameworkConfiguration}
	 * @author wanghuan
	 *
	 */
	public static class Builder {
		
		private Context context;
		private Environment environment;
		private ImageLoaderConfiguration imageLoaderConfiguration;
		private HttpRequestConfiguration httpRequestConfiguration;
		private boolean writeLogs = false;
		
		public Builder(Context context) {
			this.context = context.getApplicationContext();
		}
		
		/**
		 * 设置程序的运行环境为开发环境 {@link Environment}
		 * @return
		 */
		public Builder development(){
			this.environment = Environment.DEVELOPMENT;
			return this;
		}
		
		/**
		 * 设置程序的运行环境为预发布环境 {@link Environment}
		 * @return
		 */
		public Builder prerelease(){
			this.environment = Environment.PRE_RELEASE;
			return this;
		}
		
		/**
		 * 设置程序的运行环境为线上环境 {@link Environment}
		 * @return
		 */
		public Builder production(){
			this.environment = Environment.PRODUCTION;
			return this;
		}
		
		/**
		 * Universal Image Loader 初始化配置
		 * Version: {@code universal-image-loader-1.9.3.jar}
		 * @link https://github.com/nostra13/Android-Universal-Image-Loader
		 * @param imageLoaderConfiguration
		 * @return
		 */
		public Builder initImageLoadConfig(ImageLoaderConfiguration imageLoaderConfiguration){
			this.imageLoaderConfiguration = imageLoaderConfiguration;
			if(this.imageLoaderConfiguration != null){
				ImageLoader.getInstance().init(imageLoaderConfiguration);
			}
			return this;
		}
		
		public Builder initHttpRequestConfig(HttpRequestConfiguration httpRequestConfiguration){
			this.httpRequestConfiguration = httpRequestConfiguration;
			if(this.httpRequestConfiguration != null){
				HttpRequestEngine.getInstance().init(httpRequestConfiguration);
			}
			return this;
		}
		
		/**
		 * 打开日志输出
		 * @see L
		 */
		public Builder writeDebugLogs() {
			this.writeLogs = true;
			return this;
		}

		/**
		 * 创建框架配置对象
		 * @see FrameworkConfiguration
		 * @return 框架配置对象
		 */
		public FrameworkConfiguration build() {
			initEmptyFieldsWithDefaultValues();
			return new FrameworkConfiguration(this);
		}

		/**
		 * 配置默认设置
		 */
		private void initEmptyFieldsWithDefaultValues() {
			if(environment == null){
				development();
			}
			if(imageLoaderConfiguration == null){
				ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
						.Builder(context)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.denyCacheImageMultipleSizesInMemory()
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.diskCacheSize(50 * 1024 * 1024)
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.build();
				ImageLoader.getInstance().init(imageLoaderConfiguration);
			}
			if(httpRequestConfiguration == null){
				HttpRequestConfiguration httpRequestConfiguration = new HttpRequestConfiguration();
				// make default ..
				HttpRequestEngine.getInstance().init(httpRequestConfiguration);
			}
		}
	}

}
