package com.example.example;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.thirdpart_login).setOnClickListener(this);
		findViewById(R.id.thirdpart_share).setOnClickListener(this);
		findViewById(R.id.imageloader).setOnClickListener(this);
		findViewById(R.id.httprequest).setOnClickListener(this);
		findViewById(R.id.common_function).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.thirdpart_login:
			startActivity(new Intent(MainActivity.this , ThirdPartLoginSample.class));
			break;
		case R.id.thirdpart_share:
					
			break;
		case R.id.httprequest:
			
			break;
		case R.id.imageloader:
			
			break;
		case R.id.common_function:
			
			break;

		default:
			break;
		}
	}
	
	

}
