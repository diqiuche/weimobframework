package com.hs.framework.base.activity;

import com.hs.framework.common.NetStatus;
import com.hs.framework.receiver.FrameworkListener;
import com.hs.framework.receiver.FrameworkReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

/**
 *
 * @author wanghuan
 * @date 2014��10��11�� ����1:32:52
 * @email hunter.v.wang@gmail.com
 *
 */
public class BaseActivity extends Activity implements FrameworkListener{
	
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

}
