package com.hs.framework.core;

import java.util.List;

import org.apache.http.NameValuePair;

import android.os.Handler;
import android.os.Message;

import com.hs.framework.entities.BaseEntity;
import com.hs.framework.net.GetThread;
import com.hs.framework.net.PostThread;

/**
 *
 * @author wanghuan
 * @date 2014年10月16日 上午10:49:25
 * @email hunter.v.wang@gmail.com
 *
 */
public class HttpRequestEngine {
	
	private HttpRequestConfiguration configuration;
	
	private static HttpRequestEngine httpRequestEngine;
	
	private HttpRequestEngine (){}
	
	public static HttpRequestEngine getInstance(){
		if(httpRequestEngine == null){
			httpRequestEngine = new HttpRequestEngine();
		}
		return httpRequestEngine;
	}
	
	public void init(HttpRequestConfiguration httpRequestConfiguration){
		this.configuration = httpRequestConfiguration;
	}

	public static class HttpRequestConfiguration {
		
		public HttpRequestConfiguration() {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static class HttpEntity extends BaseEntity{

		private String url;
		private Object object;
		private String response;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Object getObject() {
			return object;
		}
		public void setObject(Object object) {
			this.object = object;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
	}
	
	public interface HttpCallback {

		/**
		 * HTTP_OK is a status code , means request is success.
		 */
		public static final int HTTP_OK = 200;
		
		/**
		 * HTTP_OK is a status code , means request is failure.
		 */
		public static final int HTTP_ERROR = -1;
		
		/**
		 * this function will be called when request is success.
		 * response is content . 
		 * @param response
		 */
		public void success(HttpEntity httpEntity);
		
		/**
		 * this function will be called when request is failure.
		 */
		public void failure(HttpEntity httpEntity);
	}
	
	public static void get(final HttpEntity httpEntity ,  final HttpCallback callback){
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == HttpCallback.HTTP_OK && callback != null){
					httpEntity.setResponse(msg.obj.toString());
					callback.success(httpEntity);
				}
				if(msg.what == HttpCallback.HTTP_ERROR && callback != null){
					callback.failure(httpEntity);
				}
				super.handleMessage(msg);
			}
		};
		GetThread getThread = new GetThread(httpEntity.getUrl(), handler);
		getThread.start();
	}
	
	public static void post(final HttpEntity httpEntity  , final List<NameValuePair> list ,  final HttpCallback callback){
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == HttpCallback.HTTP_OK && callback != null){
					httpEntity.setResponse(msg.obj.toString());
					callback.success(httpEntity);
				}
				if(msg.what == HttpCallback.HTTP_ERROR && callback != null){
					callback.failure(httpEntity);
				}
				super.handleMessage(msg);
			}
		};
		PostThread postThread = new PostThread(httpEntity.getUrl(), list, handler);
		postThread.start();
	}
}
