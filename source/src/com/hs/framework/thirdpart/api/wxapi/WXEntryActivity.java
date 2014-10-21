package com.hs.framework.thirdpart.api.wxapi;

import android.os.Bundle;

import com.hs.framework.base.BaseActivity;

/**
 *
 * @author wanghuan
 * @date 2014年10月20日 下午4:31:28
 * @email hunter.v.wang@gmail.com
 *
 */
public class WXEntryActivity extends BaseActivity{
	
	private WeixinObject weixinObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		weixinObject = getFramework().getWeixinObject();
		weixinObject.getAPI().handleIntent(getIntent(), weixinObject.getIWXAPIEventHandler());
		iFinish();
	}

}
