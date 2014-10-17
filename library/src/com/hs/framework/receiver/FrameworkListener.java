package com.hs.framework.receiver;

import com.hs.framework.common.NetStatus;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午1:51:04
 * @email hunter.v.wang@gmail.com
 *
 */
public interface FrameworkListener {
	
	public void onHomeKeyPressed();
	
	public void onHomeKeyLongPressed();
	
	public void onNetworkChaged(NetStatus status);

}
