package com.weimobframework.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.hs.framework.base.BaseActivity;
import com.hs.framework.thirdpart.api.qq.QqObject;
import com.hs.framework.thirdpart.api.sina.SinaObject;
import com.hs.framework.thirdpart.api.wxapi.WeixinObject;
import com.hs.framework.utils.L;
import com.hs.framework.utils.T;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 *
 * @author wanghuan
 * @date 2014年10月16日 下午4:11:54
 * @email hunter.v.wang@gmail.com
 *
 */
public class ThirdPartShareSample extends BaseActivity{
	
	private WeixinObject weixinObject;
	private QqObject qqObject;
	private SinaObject sinaObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdpart_share_layout);
		
		weixinObject = getFramework().getWeixinObject();
		qqObject = getFramework().getQqObject();
		sinaObject = getFramework().getSinaObject();
		
		findViewById(R.id.share_weixin_register).setOnClickListener(this);
		findViewById(R.id.share_weixin_oauth).setOnClickListener(this);
		findViewById(R.id.share_weixin).setOnClickListener(this);
		findViewById(R.id.share_qq_oauth).setOnClickListener(this);
		findViewById(R.id.share_qq).setOnClickListener(this);
		findViewById(R.id.share_sina_oauth).setOnClickListener(this);
		findViewById(R.id.share_sina_text).setOnClickListener(this);
		findViewById(R.id.share_sina_image_bitmap).setOnClickListener(this);
		findViewById(R.id.share_sina_image_url).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.share_weixin_register:
			
			break;
		case R.id.share_weixin_oauth:
			
			break;
		case R.id.share_weixin:
			weixinObject.share2Friends("hah");
			break;
		case R.id.share_qq_oauth:
			
			break;
		case R.id.share_qq:
			
			break;
		case R.id.share_sina_oauth:
			
			sinaObject.readSinaObject();
			Oauth2AccessToken token = sinaObject.getOauth2AccessToken();
			L.d("case R.id.share_sina_oauth:");
			L.d("getUid:" + token.getUid());
			L.d("getToken:" + token.getToken());
			L.d("getRefreshToken:" + token.getRefreshToken());
			L.d("getExpiresTime:" + token.getExpiresTime());
			L.d("currentTimeMillis:" + System.currentTimeMillis());
			break;
		case R.id.share_sina_text:
			L.d("case R.id.share_sina");
			if(sinaObject.getOauth2AccessToken().isSessionValid()){
				L.d("isSessionValid true:" + System.currentTimeMillis());
				L.d("isSessionValid true:" + sinaObject.getExpiresin());
				L.d("isSessionValid true:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}else{
				L.d("isSessionValid false:" + System.currentTimeMillis());
				L.d("isSessionValid false:" + sinaObject.getExpiresin());
				L.d("isSessionValid false:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}
			sinaObject.share("wanghuan 2014-10-28 for test :" + System.currentTimeMillis(), new RequestListener() {
				
				@Override
				public void onWeiboException(WeiboException arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onWeiboException");
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
				}
			});
			break;
		case R.id.share_sina_image_bitmap:
			L.d("case R.id.share_sina_image_bitmap");
			if(sinaObject.getOauth2AccessToken().isSessionValid()){
				L.d("isSessionValid true:" + System.currentTimeMillis());
				L.d("isSessionValid true:" + sinaObject.getExpiresin());
				L.d("isSessionValid true:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}else{
				L.d("isSessionValid false:" + System.currentTimeMillis());
				L.d("isSessionValid false:" + sinaObject.getExpiresin());
				L.d("isSessionValid false:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}
			sinaObject.share("wanghuan 2014-10-28 for test :" + System.currentTimeMillis(),
					BitmapFactory.decodeResource(getResources(), R.drawable.share_img_2)
					,new RequestListener() {
				
				@Override
				public void onWeiboException(WeiboException arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onWeiboException");
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
				}
			});
			break;
		case R.id.share_sina_image_url:
			//http://tp4.sinaimg.cn/2096494643/180/5695183546/1
			L.d("case R.id.share_sina_image_url");
			if(sinaObject.getOauth2AccessToken().isSessionValid()){
				L.d("isSessionValid true:" + System.currentTimeMillis());
				L.d("isSessionValid true:" + sinaObject.getExpiresin());
				L.d("isSessionValid true:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}else{
				L.d("isSessionValid false:" + System.currentTimeMillis());
				L.d("isSessionValid false:" + sinaObject.getExpiresin());
				L.d("isSessionValid false:" + (sinaObject.getExpiresin() - System.currentTimeMillis() ));
			}
			sinaObject.share("wanghuan 2014-10-28 for test :" + System.currentTimeMillis(),
					"http://tp4.sinaimg.cn/2096494643/180/5695183546/1" ,
					new RequestListener() {
				
				@Override
				public void onWeiboException(WeiboException arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onWeiboException");
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
				}
			});
			break;
		default:
			break;
		}
	}
	

}
