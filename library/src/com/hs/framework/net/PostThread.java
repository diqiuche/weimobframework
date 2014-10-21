package com.hs.framework.net;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hs.framework.core.HttpRequestEngine.HttpCallback;

import android.os.Handler;
import android.os.Message;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午 1:24:25
 * @email hunter.v.wang@gmail.com
 *
 */
public class PostThread extends Thread{

	private String url;
	private List<NameValuePair> list;
	private Handler handler;

	public PostThread(String url ,List<NameValuePair> list , Handler handler) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.list = list;
		this.handler = handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.what = HttpCallback.HTTP_ERROR;
		try {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		if(list != null){
			httpPost.setEntity(new UrlEncodedFormEntity(list , "utf-8"));
		}
		HttpResponse httpResponse = httpClient.execute(httpPost);
		if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			message.what = HttpCallback.HTTP_OK;
			message.obj = EntityUtils.toString(httpResponse.getEntity() , "utf-8");
		}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.handler != null)handler.sendMessage(message);
		super.run();
	}
}


