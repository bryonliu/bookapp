package com.bookfriend.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bookfriend.AsyncTask.AutoLoginTask;
import com.bookfriend.R;

public class StartActivity extends Activity {

	public ImageView image;

	public boolean isFirstIn;
	public String username;
	public String password;
	private SharedPreferences preferences;
	private String nickName;

	private static final int GO_HOME = 1;
	private static final int GO_GUIDE = 2;
	private static final String SHAREDPREFERENCES_NAME = "cloud";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		init();
	}
	private void init() {
		preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, 0);
		password = preferences.getString("password", "");
		username = preferences.getString("username", "");
		
		if(password.equals("")||username.equals("")){
			invisiableHandler.sendEmptyMessage(GO_HOME);
		}else{
			invisiableHandler.sendEmptyMessage(GO_GUIDE);
			try {
				AutoLoginTask asyTask = new AutoLoginTask(username, password,
						this);
				asyTask.execute();
			} catch (Exception e) {
				Log.e("book", e.getMessage(), e);
			}
		}

	}

	public Handler invisiableHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			if (msg.obj != null && msg.obj.equals("success")) {
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				intent.putExtra("username", getNickName());
				  intent.getAction();
				StartActivity.this.startActivity(intent);
				try {
					Thread.sleep(666);
				} catch (InterruptedException e) {
					Log.e("book", e.getMessage(), e);
				}
				StartActivity.this.finish();
			}
		}
	};

	protected void goHome() {
		Intent intent = new Intent();
		intent.setClass(StartActivity.this, LoginActivity.class);
		StartActivity.this.startActivity(intent);
		try {
			Thread.sleep(666);
		} catch (Exception e) {
			Log.e("book", e.getMessage(), e);
		}
		finish();
	}

	protected void goGuide() {
		setContentView(R.layout.activity_start);
		image = (ImageView) findViewById(R.id.guide);
		image.setImageResource(R.drawable.start);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
