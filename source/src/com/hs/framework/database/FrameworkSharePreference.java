package com.hs.framework.database;

import com.hs.framework.thirdpart.api.wxapi.WeixinObject;
import com.hs.framework.utils.L;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午2:25:50
 * @email hunter.v.wang@gmail.com
 *
 */
public class FrameworkSharePreference {
	
	private final static String WEIMOB_FRAMEWORK_SHAREPREFERENCE_NAME = "weimob_framework_sharepreference";
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(WEIMOB_FRAMEWORK_SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	private static SharedPreferences.Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}
	
	/**
	 * 
	 * @param context
	 * @param weixinObject
	 * @return
	 */
	public static boolean saveWeixinObject(Context context , WeixinObject weixinObject){
		L.e("BEFORE:"+weixinObject.toString());
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(WeixinObject.API_ACCESS_TOKEN , weixinObject.getAccessToken());
		editor.putString(WeixinObject.API_REFRESH_TOKEN , weixinObject.getRefreshToken());
		editor.putString(WeixinObject.API_OPEN_ID , weixinObject.getOpenid());
		editor.putLong(WeixinObject.API_EXPIRESSIN , weixinObject.getExpiress());
		editor.putString(WeixinObject.API_NICKNAME, weixinObject.getNickname());
		editor.putInt(WeixinObject.API_SEX, weixinObject.getSex());
		editor.putString(WeixinObject.API_LANGUAGE, weixinObject.getLanguage());
		editor.putString(WeixinObject.API_CITY, weixinObject.getCity());
		editor.putString(WeixinObject.API_PROVINCE, weixinObject.getProvince());
		editor.putString(WeixinObject.API_COUNTRY, weixinObject.getCountry());
		editor.putString(WeixinObject.API_HEADIMGURL, weixinObject.getHeadimgurl());
		editor.putString(WeixinObject.API_PRIVILEGE, weixinObject.getPrivillege());
		editor.putString(WeixinObject.API_UNIONID, weixinObject.getUnionid());
		editor.commit();
		L.e("AFTER:"+readWexinObject(context).toString());
		return true;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static WeixinObject readWexinObject(Context context){
		SharedPreferences reader = getSharedPreferences(context);
		WeixinObject weixinObject = WeixinObject.getInstance(context);
		weixinObject.setAccessToken(reader.getString(WeixinObject.API_ACCESS_TOKEN, null));
		weixinObject.setRefreshToken(reader.getString(WeixinObject.API_REFRESH_TOKEN, null));
		weixinObject.setOpenid(reader.getString(WeixinObject.API_OPEN_ID, null));
		weixinObject.setExpiress(reader.getLong(WeixinObject.API_EXPIRESSIN, -1));
		weixinObject.setNickname(reader.getString(WeixinObject.API_NICKNAME, null));
		weixinObject.setSex(reader.getInt(WeixinObject.API_SEX, -1));
		weixinObject.setLanguage(reader.getString(WeixinObject.API_LANGUAGE, null));
		weixinObject.setCity(reader.getString(WeixinObject.API_CITY, null));
		weixinObject.setProvince(reader.getString(WeixinObject.API_PROVINCE, null));
		weixinObject.setCountry(reader.getString(WeixinObject.API_COUNTRY, null));
		weixinObject.setHeadimgurl(reader.getString(WeixinObject.API_HEADIMGURL, null));
		weixinObject.setPrivillege(reader.getString(WeixinObject.API_PRIVILEGE, null));
		weixinObject.setUnionid(reader.getString(WeixinObject.API_UNIONID, null));
		return weixinObject;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean cleanWexinObject(Context context){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(WeixinObject.API_ACCESS_TOKEN , null);
		editor.putString(WeixinObject.API_REFRESH_TOKEN , null);
		editor.putString(WeixinObject.API_OPEN_ID , null);
		editor.putLong(WeixinObject.API_EXPIRESSIN , -1);
		editor.putString(WeixinObject.API_NICKNAME , null);
		editor.putInt(WeixinObject.API_SEX , -1);
		editor.putString(WeixinObject.API_LANGUAGE , null);
		editor.putString(WeixinObject.API_CITY , null);
		editor.putString(WeixinObject.API_PROVINCE , null);
		editor.putString(WeixinObject.API_COUNTRY , null);
		editor.putString(WeixinObject.API_HEADIMGURL , null);
		editor.putString(WeixinObject.API_PRIVILEGE , null);
		editor.putString(WeixinObject.API_UNIONID , null);
		editor.commit();
		return true;
	}
	
	

}