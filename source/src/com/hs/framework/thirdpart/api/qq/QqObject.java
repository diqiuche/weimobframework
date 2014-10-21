package com.hs.framework.thirdpart.api.qq;

import com.hs.framework.utils.L;
import com.hs.framework.utils.Util;
import com.tencent.mm.sdk.b.c;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;

/**
 *
 * @author wanghuan
 * @date 2014年10月21日 下午1:12:54
 * @email hunter.v.wang@gmail.com
 *
 */
public class QqObject{
	
	private Context context;
	private QqCallback qqCallback;
	private String appId;
	private String appKey;
	private String scope;
	
	private static QqObject object;
	
	private QqObject(Context context){
		this.context = context;
	}

	public static QqObject getInstance(Context context){
		if(object == null){
			object = new QqObject(context);
		}
		return object;
	}
	
	public void login(Activity activity , QqCallback qqCallback){
		this.qqCallback = qqCallback;
		Tencent tencent = Tencent.createInstance(appId, activity.getApplicationContext());
		if(tencent == null) L.e("tencent -> null");
		if(tencent == null) L.e("appId -> appId " + appId);
		if(scope == null) L.e("scope -> null");
		if(iUiListener == null) L.e("iUiListener -> null");
		tencent.login(activity, !Util.isEmpty(scope) ? scope : "get_user_info", iUiListener);
	}
	
	private IUiListener iUiListener = new IUiListener() {
		
		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> onError : " + arg0.errorMessage);
		}
		
		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> onComplete : ");
		}
		
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			L.d("QQ Object login -> onCancel : ");
		}
	};

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
