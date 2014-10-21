package com.hs.framework.core;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 *
 * @author wanghuan
 * @date 2014年10月15日 下午4:11:56
 * @email hunter.v.wang@gmail.com
 *
 */
public class ImageLoaderEngine {
	
	private static ImageLoaderEngine imageLoaderEngine;
	private ImageLoader imageLoader;
	
	private ImageLoaderEngine(){}
	
	public static ImageLoaderEngine getInstance(){
		if(imageLoaderEngine == null){
			imageLoaderEngine = new ImageLoaderEngine();
			imageLoaderEngine.init();
		}
		return imageLoaderEngine;
	}
	
	/**
	 * 
	 */
	private void init(){
		imageLoader = ImageLoader.getInstance();
	}
	
	/**
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void load(String uri , ImageView imageView){
		load(uri, imageView, null, null, null);
	}
	
	/**
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 */
	public void load(String uri , ImageView imageView , DisplayImageOptions options){
		load(uri, imageView, options, null, null);
	}
	
	/**
	 * 
	 * @param uri
	 * @param imageView
	 * @param listener
	 */
	public void load(String uri , ImageView imageView , ImageLoadingListener listener){
		load(uri, imageView, null, listener, null);
	}
	
	/**
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 * @param listener
	 */
	public void load(String uri , ImageView imageView , DisplayImageOptions options , ImageLoadingListener listener){
		load(uri, imageView, options, listener, null);
	}
	
	/**
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 * @param listener
	 * @param progressListener
	 */
	public void load(String uri , ImageView imageView , DisplayImageOptions options , ImageLoadingListener listener , ImageLoadingProgressListener progressListener){
	}

}
