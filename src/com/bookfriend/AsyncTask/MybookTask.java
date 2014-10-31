package com.bookfriend.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bookfriend.Fragment.MybookFragment;
import com.bookfriend.adapter.BookAdapter;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Book;

public class MybookTask extends AsyncTask<String, Integer, Integer> {
	private HashMap<String, Object> paras = new HashMap<String, Object>();
	private MybookFragment myBookFragment;
	private List<Book> bookList;
	private String msgCode = "";
	private BookAdapter adapter;
	public static final String KEY_title = "bookName";
	public static final String KEY_authorName = "authorName";

	public MybookTask(MybookFragment mybook_fragment) {
		this.myBookFragment = mybook_fragment;
	}

	/**
	 * 执行任务后调用
	 */
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (!msgCode.equals("200")) {
			Toast.makeText(myBookFragment.getActivity(), "网络错误",
					Toast.LENGTH_LONG).show();
		}
		    myBookFragment.setBookList(bookList);
			if (bookList == null || bookList.size()==0) {
				Toast.makeText(myBookFragment.getActivity(), "你还没有分享哦!",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new BookAdapter(myBookFragment, bookList);
				myBookFragment.bookListView.setAdapter(adapter);
			}

	}

	private List<Book> parseJsonResult(String result) {
		JSONArray msgBody = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			JSONObject mess = new JSONObject(result);
			msgCode = mess.getString("code");
			msgBody = mess.getJSONArray("msg");
			for (int i = 0; msgBody != null && i < msgBody.length(); i++) {
				Book book;
				book = Book.String2Book(msgBody.getJSONObject(i).toString());
				if (book != null)
					bookList.add(book);
			}
		} catch (Exception e) {
			Log.e("book", e.getMessage(), e);
		}
		return bookList;
	}

	/**
	 * 执行任务
	 */
	@Override
	protected Integer doInBackground(String... arg0) {
		HttpAgent httpAgent = new HttpAgent();
		String result = httpAgent.request("api/app/mybook", paras, "");
		bookList = parseJsonResult(result);
		return null;
	}

}
