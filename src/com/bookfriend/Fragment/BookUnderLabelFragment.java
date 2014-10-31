package com.bookfriend.Fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bookfriend.R;
import com.bookfriend.Activity.BookDetailActivity;
import com.bookfriend.AsyncTask.BookUnderLableTask;
import com.bookfriend.model.Book;

public class BookUnderLabelFragment extends Fragment {

	public ListView bookListView;
	private List<Book> bookList;
	private Long lableId;
	private BookUnderLableTask asyTask;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.book_fragment, container, false);
		bookListView = (ListView) rootView.findViewById(R.id.list2);
		showBookUnderLable();
		return rootView;
	}


	@Override
	public void onPause() {
		super.onPause();
		if(asyTask!=null)
		asyTask.cancel(true);
	}


	private void showBookUnderLable() {
	    asyTask = new BookUnderLableTask(BookUnderLabelFragment.this);
		asyTask.execute(lableId.toString());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bookListView.setOnItemClickListener(new OnItemClickListener() {

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

	public Long getLableId() {
		return lableId;
	}

	public void setLableId(Long lableId) {
		this.lableId = lableId;
	}


	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
}
