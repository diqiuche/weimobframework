package com.hs.framework.receiver;

import com.hs.framework.common.NetStatus;
import com.hs.framework.utils.NetworkUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午1:37:01
 * @email hunter.v.wang@gmail.com
 *
 */
public class FrameworkReceiver extends BroadcastReceiver{
	
	final String SYSTEM_DIALOG_REASON_KEY = "reason";
	final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
	final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	
	private Context context;
	private FrameworkListener frameworkListener;
	
	public FrameworkReceiver(Context context , FrameworkListener frameworkListener) {
		this.context = context;
		this.frameworkListener = frameworkListener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		
		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
			if (reason != null) {
				if (frameworkListener != null) {
					if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
						frameworkListener.onHomeKeyPressed();
					} else if (reason
							.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
						frameworkListener.onHomeKeyLongPressed();
					}
				}
			}
		}
		if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
			NetStatus status = NetworkUtil.getNetState(this.context);
			frameworkListener.onNetworkChaged(status);
		}
	}

}
