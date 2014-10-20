package com.weimobframework.sample;

import com.weimobframework.sample.R;
import com.hs.framework.base.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 *
 * @author wanghuan
 * @date 2014年10月16日 上午10:51:33
 * @email hunter.v.wang@gmail.com
 *
 */
public class HttpRequestSample extends BaseActivity{
	
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httprequest_layout);
		textView = (TextView)findViewById(R.id.http_info);
		findViewById(R.id.http_get).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
