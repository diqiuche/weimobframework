package com.hs.framework.thirdpart.api;
/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午1:55:16
 * @email hunter.v.wang@gmail.com
 *
 */
public interface ApiCallback {

	/**
	 * 将在API事物处理<b>成功</b>后回调
	 */
	public void onSuccess();
	
	/**
	 * 将在API事物处理<b>失败</b>后回调
	 */
	public void onFailure();
}
