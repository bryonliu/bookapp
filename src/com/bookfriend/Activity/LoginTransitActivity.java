package com.bookfriend.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bookfriend.R;
import com.bookfriend.AsyncTask.LoginTask;
import com.bookfriend.R.id;
import com.bookfriend.R.layout;
import com.bookfriend.R.menu;

public class LoginTransitActivity extends Activity {

	private String nickName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_transit);
		
		String userName = getIntent().getStringExtra("userName");
		String password = getIntent().getStringExtra("password");
		LoginTask aysTask = new LoginTask(userName,password,this);
         try{
             aysTask.execute();
         }catch(Exception e){
             Toast.makeText(LoginTransitActivity.this,"嗯，你猜得没错，崩了。。", Toast.LENGTH_LONG).show();
             Log.d("message",e.getMessage());
         }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_transit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login_transit, container, false);
			return rootView;
		}
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	 public Handler handler=	new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            if(msg.obj.equals("success")){
	                Intent intent = new Intent();
	                intent.setClass(LoginTransitActivity.this, MainActivity.class);
	                intent.putExtra("username",nickName);
	                LoginTransitActivity.this.startActivity(intent);
	                LoginTransitActivity.this.finish();
	            }
	        }
	    };

}
