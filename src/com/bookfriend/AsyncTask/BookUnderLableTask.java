package com.bookfriend.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bookfriend.Fragment.BookUnderLabelFragment;
import com.bookfriend.adapter.BookAdapter;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Book;

public class BookUnderLableTask extends AsyncTask<String, Integer, List<Book>> {

	int code = 500;
	private BookUnderLabelFragment fragment;
	public BookUnderLableTask(BookUnderLabelFragment bookUnderLabelFragment) {
		fragment = bookUnderLabelFragment;
	}

	@Override
	protected List<Book> doInBackground(String... params) {
		HttpAgent httpAgent = new HttpAgent();
		HashMap<String, Object> paras = new HashMap<String, Object>();
		paras.put("labelId", params[0]);
		String result = httpAgent.request("api/app/books-under-label", paras, "");
		return parseJsonResult(result);
	}

	@Override
	protected void onPostExecute(List<Book> result) {
		super.onPostExecute(result);
		
		if(code==500){
			Toast.makeText(fragment.getActivity(), "获取书籍失败", Toast.LENGTH_LONG).show();
		}else{
			if(result==null || result.size()==0)
				Toast.makeText(fragment.getActivity(), "该标签下还没有书籍，赶快把你的标签分享出去吧", Toast.LENGTH_LONG).show();
			else{
				fragment.setBookList(result);
				BookAdapter adapter = new BookAdapter(fragment, result);
				fragment.bookListView.setAdapter(adapter);
			}
		}
	}

	private List<Book> parseJsonResult(String result) {
		List<Book> bookList = new ArrayList<Book>();
		try {
			JSONObject mess = new JSONObject(result);
			code = mess.getInt("code");
			if(code==500)return null;
			bookList = JSON.parseArray(mess.getString("msg"), Book.class);
		} catch (Exception e) {
			Log.e("book", e.getMessage(), e);
		}
		return bookList;
	}
}
