/*
 * Copyright (C) 2010-2013 The WEIMOB Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hs.framework.base;

import com.hs.framework.common.NetStatus;
import com.hs.framework.core.Framework;
import com.hs.framework.receiver.FrameworkListener;
import com.hs.framework.receiver.FrameworkReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午1:32:52
 * @email hunter.v.wang@gmail.com
 *
 */
public class BaseActivity extends Activity implements FrameworkListener , OnClickListener{
	
	private FrameworkReceiver frameworkReceiver;
	private IntentFilter intentFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		frameworkReceiver = new FrameworkReceiver(this , this);
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(frameworkReceiver, intentFilter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(frameworkReceiver);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onHomeKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHomeKeyLongPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetworkChaged(NetStatus staus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	public void iFinish(){
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 
	 * @param intent
	 */
	public void iStartActivity(Intent intent){
		if(intent == null){
			return;
		}
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 
	 * @return
	 */
	public Framework getFramework(){
		return Framework.getInstance();
	}

}
