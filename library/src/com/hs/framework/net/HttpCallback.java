package com.hs.framework.net;

/**
 *
 * @author wanghuan
 * @date 2014��10��11�� ����1:25:41
 * @email hunter.v.wang@gmail.com
 *
 */
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
