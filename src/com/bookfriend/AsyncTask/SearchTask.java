package com.bookfriend.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bookfriend.Fragment.SearchFragment;
import com.bookfriend.adapter.BookAdapter;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Book;

public class SearchTask extends AsyncTask<String, Integer, Integer> {

    private BookAdapter adapter;
    public HttpAgent httpAgent = new HttpAgent();
    private String title;
    private List<Book> bookList;
    public HashMap<String, Object> paras = new HashMap<String, Object>();
    private String msgCode = "";
    private SearchFragment search;

    public SearchTask(String _title, SearchFragment _serchFrament) {
        title = _title;
        search = _serchFrament;
    }

    /**
     * 执行任务后调用
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (!msgCode.equals("200")) {
            Toast.makeText(search.getActivity(), "网络错误", Toast.LENGTH_LONG).show();
        } else {
            search.setBookList(bookList);
            if (bookList == null || bookList.size() ==0) {
			    Toast.makeText(search.getActivity(), "找不到", Toast.LENGTH_LONG).show();
			} else {
			    search.linearLayout1.setVisibility(View.VISIBLE);
			    search.linearLayout2.setVisibility(View.INVISIBLE);
			    adapter = new BookAdapter(search, bookList);
			    search.listView.setAdapter(adapter);
			}
        }

    }

	private List<Book> parseResult(String result)  {
		JSONArray msgBody = null;
		 List<Book> infos = null;
		try {
            JSONObject JsonResult = new JSONObject(result);
            msgCode = JsonResult.getString("code");
            msgBody = JsonResult.getJSONArray("msg");
           infos = new ArrayList<Book>();
    		for (int i = 0;  msgBody!=null &&i < msgBody.length(); i++) {
    		    Book info = Book.String2Book(msgBody.getJSONObject(i).toString());
    		    if(info!=null)
    		    infos.add(info);
    		}
        } catch (Exception e) {
          Log.e("book", e.getMessage(),e);
        }
		
		return infos;
	}

    /**
     * 执行任务之前调用
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        paras.put("title", title);
    }
    /**
     * 执行任务
     */
    @Override
    protected Integer doInBackground(String... arg0) {
        String result = httpAgent.request("api/app/books-for-query", paras, "");
        bookList = parseResult(result);
        return null;
    }

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

}
