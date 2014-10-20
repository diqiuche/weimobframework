package com.weimobframework.sample;

import android.os.Bundle;
import android.widget.TextView;

import com.weimobframework.sample.R;
import com.hs.framework.base.BaseActivity;
import com.hs.framework.common.NetStatus;

/**
 *
 * @author wanghuan
 * @date 2014年10月13日 上午11:28:31
 * @email hunter.v.wang@gmail.com
 *
 */
public class NetWorkSampleActivity extends BaseActivity{
	
	private TextView info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_sample_layout);
		info = (TextView)findViewById(R.id.network_sample_info);
	}
	
	@Override
	public void onNetworkChaged(NetStatus staus) {
		super.onNetworkChaged(staus);
		info.setText(staus.toString());
	}

}
