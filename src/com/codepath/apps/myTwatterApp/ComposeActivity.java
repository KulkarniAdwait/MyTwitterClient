package com.codepath.apps.myTwatterApp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	EditText etComposeText;
	TextView tvUserName;
	TextView tvScreenName;
	TextView tvRemainingChars;
	ImageView ivProfileImage;
	private TwitterClient client;
	final int TWEET_SIZE = 140;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		etComposeText = (EditText) findViewById(R.id.etComposeText);
		
		tvUserName = (TextView) findViewById(R.id.tvUserName_1);
		tvUserName.setText(getIntent().getStringExtra("userName"));
		
		tvScreenName = (TextView) findViewById(R.id.tvScreenName_1);
		tvScreenName.setText("@" + getIntent().getStringExtra("screenName"));
		
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage_1);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(getIntent().getStringExtra("imgUrl"), ivProfileImage);
		
		tvRemainingChars = (TextView) findViewById(R.id.tvRemainingChars);
		
		client = MyTwatterApp.getRestClient();
		
		etComposeText.addTextChangedListener(new TextWatcher() {
			int cSize = 0;
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				cSize = etComposeText.getText().toString().length();
				tvRemainingChars.setText(String.valueOf(TWEET_SIZE - cSize));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void onCompose(View v) {
		if(etComposeText.getText().toString() != "") {
			client.postUpdate(new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String arg0) {
					setResult(RESULT_OK);
					finish();
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			}, etComposeText.getText().toString());
		}
	}
}
