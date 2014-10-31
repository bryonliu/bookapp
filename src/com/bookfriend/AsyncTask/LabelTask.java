package com.bookfriend.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bookfriend.Fragment.LabelFragment;
import com.bookfriend.adapter.LabelAdapter;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.AbstractObject;
import com.bookfriend.model.Label;

public class LabelTask extends AsyncTask<String, Integer, Integer> {

	public HttpAgent httpAgent = new HttpAgent();
	public HashMap<String, Object> paras = new HashMap<String, Object>();
	private String msgCode = "";
	private LabelFragment labelFragment;
	private LabelAdapter adapter;
	private List<Label> labelList = null;
	public LabelTask(LabelFragment _labelFragment) {
		labelFragment = _labelFragment;
	}

	/**
	 * 执行任务后调用
	 */
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (!msgCode.equals("200")) {
			Toast.makeText(labelFragment.getActivity(), "网络错误",
					Toast.LENGTH_LONG).show();
			return;
		}
		labelFragment.setLabelList(labelList);
		if (labelList ==null  ||labelList.size()==0) {
			Toast.makeText(labelFragment.getActivity(), "分享，让知识不在孤单！",
					Toast.LENGTH_LONG).show();
		} 
			adapter = new LabelAdapter(labelFragment, labelList);
			labelFragment.getGridView().setAdapter(adapter);

	}


	@Override
	protected Integer doInBackground(String... arg0) {
		String result = httpAgent.request("api/app/labels", paras, "");
		labelList = parseLableList(result);
		return null;
	}
	private List<Label> parseLableList(String result) {
		List<Label> labels = new ArrayList<Label>();
		JSONArray jsonResult = null;
		try {
			JSONObject mess = new JSONObject(result);
			msgCode = mess.getString("code");
			jsonResult = mess.getJSONArray("msg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; jsonResult !=null && i < jsonResult.length(); i++) {
			try {
				Label label = AbstractObject.String2Object(jsonResult
						.getJSONObject(i).toString(), Label.class);
				labels.add(label);
			} catch (JSONException e) {
				Log.e("book", e.getMessage(), e);
			}
		}
		return labels;
	}
}
