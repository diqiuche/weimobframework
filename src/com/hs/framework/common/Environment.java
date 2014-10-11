package com.hs.framework.common;
/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午2:57:18
 * @email hunter.v.wang@gmail.com
 *
 */
public enum Environment {
	
	DEVELOPMENT(true),
	
	PRERELEASE(true),
	
	PRODUCTION(false);
	
	/**
	 * 是否开启日志打印
	 */
	private boolean isDebug;
	
	private Environment(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

}
