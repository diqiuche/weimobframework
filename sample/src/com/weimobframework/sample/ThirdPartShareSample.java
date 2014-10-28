package com.weimobframework.sample;

import android.app.Dialog;
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
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

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
	
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdpart_share_layout);
		
		dialog = new Dialog(ThirdPartShareSample.this);
		dialog.setTitle("...");
		
		weixinObject = getFramework().getWeixinObject();
		qqObject = getFramework().getQqObject();
		sinaObject = getFramework().getSinaObject();
		
		findViewById(R.id.share_weixin_register).setOnClickListener(this);
		findViewById(R.id.share_weixin_oauth).setOnClickListener(this);
		findViewById(R.id.share_weixin_text).setOnClickListener(this);
		findViewById(R.id.share_weixin_pic).setOnClickListener(this);
		findViewById(R.id.share_weixin_pyq_text).setOnClickListener(this);
		findViewById(R.id.share_weixin_pyq_pic).setOnClickListener(this);
		
		findViewById(R.id.share_qzone).setOnClickListener(this);
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
		case R.id.share_weixin_text:
			weixinObject.share2Friends("message");
			break;
		case R.id.share_weixin_pic:
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.share_img_1);
			weixinObject.share2Friends("message", bitmap);
			break;
		case R.id.share_weixin_pyq_text:
			weixinObject.share2FriendsLine("");
			break;
		case R.id.share_weixin_pyq_pic:
			Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.share_img_1);
			weixinObject.share2FriendsLine("message", bitmap2);
			break;
		case R.id.share_qzone:
			final Bundle paramsZone = new Bundle();
			paramsZone.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
			paramsZone.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
			paramsZone.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
			paramsZone.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com");//必填
			qqObject.shareToQZone(ThirdPartShareSample.this, paramsZone, new IUiListener() {
				
				@Override
				public void onError(UiError arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onError");
					dialog.dismiss();
				}
				
				@Override
				public void onComplete(Object arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
					dialog.dismiss();
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onCancel");
					dialog.dismiss();
				}
			});
			break;
		case R.id.share_qq:
			   	final Bundle params = new Bundle();
			    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
			    params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
			    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
			    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
			    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
			    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
			    qqObject.shareToQQ(ThirdPartShareSample.this, params, new IUiListener() {
					
					@Override
					public void onError(UiError arg0) {
						// TODO Auto-generated method stub
						T.show(ThirdPartShareSample.this, "onError");
						dialog.dismiss();
					}
					
					@Override
					public void onComplete(Object arg0) {
						// TODO Auto-generated method stub
						T.show(ThirdPartShareSample.this, "onComplete");
						dialog.dismiss();
					}
					
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						T.show(ThirdPartShareSample.this, "onCancel");
						dialog.dismiss();
					}
				});
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
			dialog.show();
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
					dialog.dismiss();
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
					dialog.dismiss();
				}
			});
			break;
		case R.id.share_sina_image_bitmap:
			dialog.show();
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
					dialog.dismiss();
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
					dialog.dismiss();
				}
			});
			break;
		case R.id.share_sina_image_url:
			dialog.show();
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
					dialog.dismiss();
				}
				
				@Override
				public void onComplete(String arg0) {
					// TODO Auto-generated method stub
					T.show(ThirdPartShareSample.this, "onComplete");
					dialog.dismiss();
				}
			});
			break;
		default:
			break;
		}
	}
	

}
