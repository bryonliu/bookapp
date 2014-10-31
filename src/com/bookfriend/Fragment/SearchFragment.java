package com.bookfriend.Fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bookfriend.R;
import com.bookfriend.Activity.BookDetailActivity;
import com.bookfriend.AsyncTask.SearchTask;
import com.bookfriend.model.Book;
import com.bookfriend.tools.CleanableEditText;

public class SearchFragment extends Fragment {

	public static final String KEY_title = "bookName";
	public static final String KEY_authorName = "authorName";
	public static final String KEY_IMAGEURL = "imageUrl";
	private CleanableEditText cleanableEditText;
	private List<Book> bookList;
	public LinearLayout linearLayout1;
	public LinearLayout linearLayout2;
	public ListView listView;
	private SearchTask asyTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		cleanableEditText = (CleanableEditText) rootView
				.findViewById(R.id.search_edit);
		linearLayout1 = (LinearLayout) rootView
				.findViewById(R.id.linearLayout1);
		linearLayout2 = (LinearLayout) rootView
				.findViewById(R.id.linearLayout2);
		linearLayout1.setVisibility(View.INVISIBLE);
		listView = (ListView) rootView.findViewById(R.id.list);
		return rootView;

	}

	@Override
	public void onPause() {
		super.onPause();
		if(asyTask!=null)
		asyTask.cancel(true);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Button button = (Button) getActivity().findViewById(R.id.search_but);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
						search();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Book book = bookList.get(position);
				Intent intent = new Intent();
				intent.setClass(getActivity(), BookDetailActivity.class);
				intent.putExtra("currentBook", book);
				getActivity().startActivity(intent);

			}
		});
	}

	private void search() {
		try {
		    asyTask = new SearchTask(cleanableEditText.getText().toString(), this);
			asyTask.execute();
		} catch (Exception e) {
			Log.e("book", e.getMessage(), e);
		}
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
}