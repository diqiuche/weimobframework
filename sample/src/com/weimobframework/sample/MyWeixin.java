package com.weimobframework.sample;

import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午4:11:34
 * @email hunter.v.wang@gmail.com
 *
 */
public class MyWeixin {
	
	private static MyWeixin m;
	
	private MyWeixin(){}
	
	public static MyWeixin getInstance(){
		if(m == null){
			m = new MyWeixin();
		}
		return m;
	}
	
	IWXAPIEventHandler handler;
	
	public void setHandler(IWXAPIEventHandler handler){
		this.handler = handler;
	}
	
	public IWXAPIEventHandler getHandler(){
		return handler;
	}

}
