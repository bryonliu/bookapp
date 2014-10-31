package com.bookfriend.AsyncTask;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bookfriend.Fragment.LocationFragment;
import com.bookfriend.adapter.LocationAdapter;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Location;
import com.bookfriend.tools.LoginUtils;

public class MyLocationsTask extends AsyncTask<String, Integer, List<Location>> {

	private LocationFragment fragment;
	private Integer code = 0;
	public MyLocationsTask(LocationFragment fragment) {
		super();
		this.fragment = fragment;
	}
	@Override
	protected void onPostExecute(List<Location> result) {
		super.onPostExecute(result);
		if(code ==0){
			Toast.makeText( fragment.getActivity(),"就这网络，也是醉了！", Toast.LENGTH_SHORT).show();
		}else{
			LocationAdapter adapter = new LocationAdapter(result, fragment);
			fragment.getLoclistView().setAdapter(adapter);
		}
	}
	
	@Override
	protected List<Location> doInBackground(String... params) {
		HashMap<String, String> heads = new HashMap<String, String>();
		HashMap<String, Object> paras = new HashMap<String, Object>();
		
		heads.put("Content-Type", "application/x-www-form-urluncoded");
		paras.put("key", "3c22cbef144d135876dcd5b55b51db32");
		paras.put("tableid", "5411201de4b06104d2c09ac4");
		
		HttpAgent httpAgent = new HttpAgent();
		StringBuilder url = new StringBuilder("http://yuntuapi.amap.com/datamanage/data/list?");
        url.append("key=3c22cbef144d135876dcd5b55b51db32");
        url.append("&tableid=5411201de4b06104d2c09ac4");
        url.append("&tableid=5411201de4b06104d2c09ac4");
        url.append("&filter=user_id:"+LoginUtils.getCurrentUserId(fragment.getActivity()));
        
		String result = httpAgent.requestForGet(URLEncoder.encode(url.toString()), heads);
		List<Location> list = parseReustl(result);
		return list;
	}
	private List<Location> parseReustl(String result) {
		List<Location> list = null;
		try {
			JSONObject jsonResult = new JSONObject(result);
			code = jsonResult.getInt("status");
			if(code==0)return null;
			list = JSON.parseArray(jsonResult.getString("data"), Location.class);
		} catch (JSONException e) {
			Log.e("book", e.getMessage(), e);
		}
		return list;
	}



	

}
