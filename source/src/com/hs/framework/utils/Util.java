package com.hs.framework.utils;
/**
 *
 * @author wanghuan
 * @date 2014年10月21日 上午10:39:15
 * @email hunter.v.wang@gmail.com
 *
 */
public class Util {
	
	/**
	 * 判断字符串是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text){
		if(text == null || text.equals(""))
			return true;
		return false;
	}

}
