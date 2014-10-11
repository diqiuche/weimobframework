package com.hs.framework.net;

import com.hs.framework.entities.BaseEntity;

/**
 *
 * @author wanghuan
 * @date 2014��10��11�� ����1:26:08
 * @email hunter.v.wang@gmail.com
 *
 */
public class HttpEntity extends BaseEntity{

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
