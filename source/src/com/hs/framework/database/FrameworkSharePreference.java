package com.hs.framework.database;

import com.hs.framework.thirdpart.api.qq.QqObject;
import com.hs.framework.thirdpart.api.sina.SinaObject;
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
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(WEIMOB_FRAMEWORK_SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences.Editor getEditor(Context context){
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
	
	/**
	 * 
	 * @param context
	 * @param qqObject
	 * @return
	 */
	public static boolean saveQqObject(Context context , QqObject qqObject){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(QqObject.API_PAY_TOKEN , qqObject.getPayToken());
		editor.putString(QqObject.API_OPEN_ID , qqObject.getOpenId());
		editor.putLong(QqObject.API_EXPIRESS_IN , qqObject.getExpiressin());
		editor.putString(QqObject.API_PF , qqObject.getPf());
		editor.putString(QqObject.API_PF_KEY, qqObject.getPfKey());
		editor.putString(QqObject.API_ACCESS_YOKEN, qqObject.getAccessToken());
		editor.putString(QqObject.API_QQ_HEADIMAGEURL, qqObject.getQqHeadimgurl());
		editor.putString(QqObject.API_NICKNAME, qqObject.getNickName());
		editor.putString(QqObject.API_CITY, qqObject.getCity());
		editor.putString(QqObject.API_QZONEHEADIMGURL, qqObject.getQzoneHeadimgurl());
		editor.putString(QqObject.API_PROVINCE, qqObject.getProvince());
		editor.putString(QqObject.API_SEX, qqObject.getSex());
		editor.commit();
		return true;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static QqObject readQqObject(Context context){
		SharedPreferences reader = getSharedPreferences(context);
		QqObject qqObject = QqObject.getInstance(context);
		qqObject.setPayToken(reader.getString(QqObject.API_PAY_TOKEN, null));
		qqObject.setOpenId(reader.getString(QqObject.API_OPEN_ID, null));
		qqObject.setExpiressin(reader.getLong(QqObject.API_EXPIRESS_IN, -1));
		qqObject.setPf(reader.getString(QqObject.API_PF, null));
		qqObject.setPfKey(reader.getString(QqObject.API_PF_KEY, null));
		qqObject.setAccessToken(reader.getString(QqObject.API_ACCESS_YOKEN, null));
		qqObject.setQqHeadimgurl(reader.getString(QqObject.API_QQ_HEADIMAGEURL, null));
		qqObject.setNickName(reader.getString(QqObject.API_NICKNAME, null));
		qqObject.setCity(reader.getString(QqObject.API_CITY, null));
		qqObject.setQzoneHeadimgurl(reader.getString(QqObject.API_QZONEHEADIMGURL, null));
		qqObject.setProvince(reader.getString(QqObject.API_PROVINCE, null));
		qqObject.setSex(reader.getString(QqObject.API_SEX, null));
		return qqObject;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean cleanQqObject(Context context){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(QqObject.API_PAY_TOKEN , null);
		editor.putString(QqObject.API_OPEN_ID , null);
		editor.putLong(QqObject.API_EXPIRESS_IN , -1);
		editor.putString(QqObject.API_PF , null);
		editor.putString(QqObject.API_PF_KEY, null);
		editor.putString(QqObject.API_ACCESS_YOKEN, null);
		editor.putString(QqObject.API_QQ_HEADIMAGEURL, null);
		editor.putString(QqObject.API_NICKNAME, null);
		editor.putString(QqObject.API_CITY, null);
		editor.putString(QqObject.API_QZONEHEADIMGURL, null);
		editor.putString(QqObject.API_PROVINCE, null);
		editor.putString(QqObject.API_SEX, null);
		editor.commit();
		return true;
	}
	
	/**
	 * 
	 * @param context
	 * @param sinaObject
	 * @return
	 */
	public static boolean saveSinaObject(Context context , SinaObject sinaObject){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(SinaObject.API_UID, sinaObject.getUid());
		editor.putString(SinaObject.API_TOKEN, sinaObject.getToken());
		editor.putString(SinaObject.API_REFRESH_TOKEN, sinaObject.getRefreshToken());
		editor.putLong(SinaObject.API_EXPIRESIN, sinaObject.getExpiresin());
		editor.putString(SinaObject.API_IDSTR, sinaObject.getIdstr());
		editor.putString(SinaObject.API_NAME, sinaObject.getName());
		editor.putString(SinaObject.API_LOCATION, sinaObject.getLocation());
		editor.putString(SinaObject.API_HEADIMGURL, sinaObject.getHeadimgurl());
		editor.putString(SinaObject.API_GENDER, sinaObject.getGender());
		editor.commit();
		return true;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static SinaObject readSinaObject(Context context){
		SharedPreferences reader = getSharedPreferences(context);
		SinaObject sinaObject = SinaObject.getInstance(context);
		sinaObject.setUid(reader.getString(SinaObject.API_UID, null));
		sinaObject.setToken(reader.getString(SinaObject.API_TOKEN, null));
		sinaObject.setRefreshToken(reader.getString(SinaObject.API_REFRESH_TOKEN, null));
		sinaObject.setExpiresin(reader.getLong(SinaObject.API_EXPIRESIN, -1));
		sinaObject.setIdstr(reader.getString(SinaObject.API_IDSTR, null));
		sinaObject.setName(reader.getString(SinaObject.API_NAME, null));
		sinaObject.setLocation(reader.getString(SinaObject.API_LOCATION, null));
		sinaObject.setHeadimgurl(reader.getString(SinaObject.API_HEADIMGURL, null));
		sinaObject.setGender(reader.getString(SinaObject.API_GENDER, null));
		return sinaObject;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean cleanSinaObject(Context context){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(SinaObject.API_UID, null);
		editor.putString(SinaObject.API_TOKEN, null);
		editor.putString(SinaObject.API_REFRESH_TOKEN, null);
		editor.putLong(SinaObject.API_EXPIRESIN, -1);
		editor.putString(SinaObject.API_IDSTR, null);
		editor.putString(SinaObject.API_NAME, null);
		editor.putString(SinaObject.API_LOCATION, null);
		editor.putString(SinaObject.API_HEADIMGURL, null);
		editor.putString(SinaObject.API_GENDER, null);
		editor.commit();
		return true;
	}
}
