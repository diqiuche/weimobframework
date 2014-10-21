package com.hs.framework.core;

import com.hs.framework.thirdpart.api.qq.QqObject;
import com.hs.framework.thirdpart.api.wxapi.WeixinObject;
import com.hs.framework.utils.L;

import android.content.Context;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午2:57:18
 * @email hunter.v.wang@gmail.com
 *
 */
public class Framework {
	
	private static final String LOG_INIT_CONFIG = "Initialize Framework with configuration";
	private static final String LOG_DESTROY = "Destroy Framework";
	private static final String WARNING_RE_INIT_CONFIG = "Try to initialize Framework which had already been initialized before. " +
															"To re-init Framework with new configuration call Framework.destroy() at first.";
	private static final String ERROR_NOT_INIT = "Framework must be init with configuration before using";
	private static final String ERROR_INIT_CONFIG_WITH_NULL = "Framework configuration can not be initialized with null";
	
	private static final String ERROR_FRAMEWORK_NULL = "Framework is null";
	
	private Context context;
	
	private FrameworkConfiguration configuration;
	
	private Framework(){}
	
	private static Framework framework;
	
	public static Framework getInstance(){
		if(framework == null){
			framework = new Framework();
		}
		return framework;
	}
	
	/**
	 * 初始化框架配置
	 * @param configuration
	 */
	public void init(FrameworkConfiguration configuration){
		if(configuration == null){
			throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
		}
		if (this.configuration == null) {
			L.d(LOG_INIT_CONFIG);
			this.configuration = configuration;
		} else {
			L.w(WARNING_RE_INIT_CONFIG);
		}
	}
	
	/**
	 * 返回 <b>true</b> - 如果已经初始化 {@linkplain #init(ImageLoaderConfiguration) }; <b>false</b> - 否则
	 */
	public boolean isInited() {
		return configuration != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Context getContext(){
		if (configuration == null){
			L.d(ERROR_FRAMEWORK_NULL);
			throw new RuntimeException(ERROR_FRAMEWORK_NULL);
		}
		return configuration.context;
	}
	
	/**
	 * 
	 * @return
	 */
	public WeixinObject getWeixinObject(){
		WeixinObject weixinObject = WeixinObject.getInstance(context);
		return weixinObject;
	}
	
	/**
	 * 
	 * @return
	 */
	public QqObject getQqObject(){
		QqObject qqObject = QqObject.getInstance(context);
		return qqObject;
	}
	
	/**
	 * {@link Framework} 将会被销毁 框架配置对象将被清空 <br/>
	 * 你可以用 {@linkplain #init(ImageLoaderConfiguration) init} 重新初始化
	 */
	public void destroy() {
		if (configuration != null)
			L.d(LOG_DESTROY);
		configuration = null;
	}
	
	/**
	 * Next are functions
	 */
	
//	public HttpRequestEngine getHttpRequestEngine(){
//		
//	}

	
}
