package com.bookfriend.Fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.bookfriend.R;
import com.bookfriend.AsyncTask.BookShareCancleTask;
import com.bookfriend.AsyncTask.MybookTask;
import com.bookfriend.model.Book;

public class MybookFragment extends Fragment {

	public ListView bookListView;
	private List<Book> bookList;
	private MybookTask asyTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.book_fragment, container, false);
		bookListView = (ListView) rootView.findViewById(R.id.list2);
		showMyBookList();
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bookListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Book book = bookList.get(position);
				dialog(String.valueOf(book.getId()));
				return false;
			}
		});
	}

	public void showMyBookList() {
		try {
		    asyTask = new MybookTask(this);
			asyTask.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("message", e.getMessage());
		}
	}

	protected void dialog(final String bookId) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("取消该图书的分享 ?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				BookShareCancleTask task = new BookShareCancleTask(MybookFragment.this);
				task.execute(bookId);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();

	}

	public ListView getList() {
		return bookListView;
	}

	public void setList(ListView list) {
		this.bookListView = list;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public void onPause() {
		super.onPause();
		if(asyTask!=null)asyTask.cancel(true);
		asyTask.cancel(true);
	}

}
