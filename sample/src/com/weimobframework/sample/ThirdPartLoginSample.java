package com.weimobframework.sample;

import com.weimobframework.sample.R;
import com.hs.framework.base.BaseActivity;
import com.hs.framework.thirdpart.api.wxapi.WeixinCallback;
import com.hs.framework.thirdpart.api.wxapi.WeixinObject;
import com.hs.framework.utils.L;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 *
 * @author wanghuan
 * @date 2014年10月16日 下午4:11:31
 * @email hunter.v.wang@gmail.com
 *
 */
public class ThirdPartLoginSample extends BaseActivity {
	
	private TextView weixin_register_result;
	
	private WeixinObject weixinObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdpart_login_layout);
		
		weixinObject = getFramework().getWeixinObject();
		
		weixin_register_result = (TextView)findViewById(R.id.login_weixin_register_result);
		
		findViewById(R.id.login_weixin_register).setOnClickListener(this);
		findViewById(R.id.login_weixin).setOnClickListener(this);
		findViewById(R.id.login_weixin_info_save).setOnClickListener(this);
		findViewById(R.id.login_weixin_info_clean).setOnClickListener(this);
		findViewById(R.id.login_weixin_info_read).setOnClickListener(this);
		findViewById(R.id.login_weixin_info).setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.login_weixin_register:
			weixin_register_result.setVisibility(View.VISIBLE);
			break;
		case R.id.login_weixin:
			weixinObject.login(weixinCallback);
			L.d("[ start login weixin]");
			break;
		case R.id.login_weixin_info:
			L.d("[ weixin info ]" + weixinObject.toString());
			break;
		case R.id.login_weixin_info_read:
			WeixinObject w = weixinObject.readObject();
			L.d("[ weixin info  read]" + w.toString());
			break;
		case R.id.login_weixin_info_save:
			L.d("[ weixin info save ]" + weixinObject.saveObject());
			break;
		case R.id.login_weixin_info_clean:
			L.d("[ weixin info clean ]" + weixinObject.cleanObject());
			break;
		case R.id.login_qq:
			
			break;
		case R.id.login_sina:
			
			break;

		default:
			break;
		}
	}
	
	private WeixinCallback weixinCallback = new WeixinCallback() {
		
		@Override
		public void onUserCancel() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			L.e("-- success --");
//			L.e("-- access token --" + weixinObject.getAccessToken());
//			L.e("-- openid --" + weixinObject.getOpenid());
//			L.e("-- refresh token --" + weixinObject.getRefreshToken());
//			L.e("-- express in --" + weixinObject.getExpiress());
			
			L.e("-- Object --" + weixinObject.toString());
			L.e("-- success --");
		}
		
		@Override
		public void onRefuse() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
			
		}
	};

}
