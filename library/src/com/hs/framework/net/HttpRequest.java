package com.hs.framework.net;

import java.util.List;

import org.apache.http.NameValuePair;
import android.os.Handler;
import android.os.Message;

/**
 *
 * @author wanghuan
 * @date 2014��10��11�� ����1:24:01
 * @email hunter.v.wang@gmail.com
 *
 */
public class HttpRequest {
	
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
