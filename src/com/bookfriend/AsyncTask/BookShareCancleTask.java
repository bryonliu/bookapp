package com.bookfriend.AsyncTask;

import java.util.HashMap;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bookfriend.Fragment.MybookFragment;
import com.bookfriend.http.HttpAgent;

public class BookShareCancleTask extends AsyncTask<String, Integer, Integer> {

	private MybookFragment myBookFragment;

	public BookShareCancleTask(MybookFragment myBookFragment) {
		this.myBookFragment = myBookFragment;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (result == 200) {
			Toast.makeText(myBookFragment.getActivity(), "取消成功", Toast.LENGTH_LONG).show();
			myBookFragment.showMyBookList();
		} else {
			Toast.makeText(myBookFragment.getActivity(), "取消失败", Toast.LENGTH_LONG).show();
		}

		myBookFragment.showMyBookList();
	}

	@Override
	protected Integer doInBackground(String... params) {
		int code = 500;
		HttpAgent httpAgent = new HttpAgent();
		HashMap<String, Object> paras = new HashMap<String, Object>();
		paras.put("bookId", params[0]);
		String result = httpAgent.request("api/app/cancle-share", paras, "");
		try {
			JSONObject jsonResult = new JSONObject(result);
			code = jsonResult.getInt("code");
		} catch (Exception e) {
			Log.e("book", e.getMessage(), e);
		}
		return code;
	}

}
