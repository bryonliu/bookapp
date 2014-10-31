package com.bookfriend.AsyncTask;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Location;

public class ShareLoctionTask extends AsyncTask<String, Integer, Integer> {

	private Fragment fragment;

	private String msgBody;

	public ShareLoctionTask(Fragment fragment) {
		this.fragment = fragment;
	}

	@Override
	protected Integer doInBackground(String... params) {
		HashMap<String, String> heads = new HashMap<String, String>();
		HashMap<String, Object> paras = new HashMap<String, Object>();
		
		heads.put("Content-Type", "application/x-www-form-urluncoded");
		long userId = getUserId();
		Location location = new Location(params[3], params[0] + "," + params[1], params[2], userId);
		paras.put("key", "3c22cbef144d135876dcd5b55b51db32");
		paras.put("tableid", "5411201de4b06104d2c09ac4");
		paras.put("data", location.toJsonString().trim());
		int code = 0;
		HttpAgent httpAgent = new HttpAgent();
		String url = "http://yuntuapi.amap.com/datamanage/data/create";
        
		 
		String result = httpAgent.request(url.toString(), heads,paras);
		try {
			JSONObject jsonResult = new JSONObject(result);
			code = jsonResult.getInt("status");
			msgBody = jsonResult.getString("info");
		} catch (JSONException e) {
			msgBody = "网络出现错误";
			Log.e("book", e.getMessage(), e);
		}
		return code;
	}

	private long getUserId() {
		SharedPreferences sp = fragment.getActivity().getSharedPreferences("cloud", 0);
		long userId = sp.getLong("userId", -1);
		return userId;
	}
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		Toast.makeText(fragment.getActivity(), msgBody, Toast.LENGTH_LONG).show();
		;
	}

}
