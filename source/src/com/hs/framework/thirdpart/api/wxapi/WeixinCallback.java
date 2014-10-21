package com.hs.framework.thirdpart.api.wxapi;

import com.hs.framework.thirdpart.api.ApiCallback;

/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午5:22:07
 * @email hunter.v.wang@gmail.com
 *
 */
public interface WeixinCallback extends ApiCallback{
	
	public void onUserCancel();
	
	public void onRefuse();
	
}
