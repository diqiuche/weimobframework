package com.hs.framework.thirdpart.api.sina;

import android.content.Context;

import com.hs.framework.thirdpart.api.qq.QqObject;

/**
 *
 * @author wanghuan
 * @date 2014年10月21日 下午1:12:25
 * @email hunter.v.wang@gmail.com
 *
 */
public class SinaObject {

	private Context context;
	
	private static SinaObject object;
	
	private SinaObject(Context context){
		this.context = context;
	}

	public static SinaObject getInstance(Context context){
		if(object == null){
			object = new SinaObject(context);
		}
		return object;
	}
}
