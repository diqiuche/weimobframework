package com.hs.framework.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

/**
 *
 * @author wanghuan
 * @date 2014��10��11�� ����1:24:16
 * @email hunter.v.wang@gmail.com
 *
 */
public class GetThread extends Thread{
	
	private String url;
	private Handler handler;

	public GetThread(String url , Handler handler) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.handler = handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		Message message = new Message();
		message.what = HttpCallback.HTTP_ERROR;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				message.what = HttpCallback.HTTP_OK;
				message.obj = EntityUtils.toString(httpResponse.getEntity());
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
