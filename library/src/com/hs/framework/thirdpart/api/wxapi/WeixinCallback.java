package com.hs.framework.thirdpart.api.wxapi;
/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午5:22:07
 * @email hunter.v.wang@gmail.com
 *
 */
public interface WeixinCallback {

	public void onSuccess();
	
	public void onUserCancel();
	
	public void onRefuse();
	
	public void onFailure();
}
