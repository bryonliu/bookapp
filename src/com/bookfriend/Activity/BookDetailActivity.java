package com.bookfriend.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookfriend.R;
import com.bookfriend.AsyncTask.BitmapTask;
import com.bookfriend.model.Book;

public class BookDetailActivity extends Activity{
   

   @SuppressLint("CutPasteId") 
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.owner_book);
        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
		           BookDetailActivity.this.finish();
			}
		});
        Intent intent = getIntent();
        final Book currentBook = (Book) intent.getSerializableExtra("currentBook");
        TextView title = (TextView) findViewById(R.id.bookview_title1);
        TextView author = (TextView) findViewById(R.id.bookview_author1);
        TextView publisher = (TextView) findViewById(R.id.bookview_publisher1);
        TextView publisherdate = (TextView) findViewById(R.id.bookview_publisherdate1);
        TextView isbn = (TextView) findViewById(R.id.bookview_isbn1);
        TextView summary = (TextView) findViewById(R.id.bookview_summary1);
        TextView wechatNum = (TextView) findViewById(R.id.bookview_wechatNum1);
        TextView userEmail = (TextView) findViewById(R.id.bookview_userEmail1);
        TextView userName = (TextView) findViewById(R.id.bookview_userName1);
        ImageView imageView = (ImageView) findViewById(R.id.bookview_cover);
        
        if(currentBook.getImageUrl() !=null){
        	BitmapTask task = new BitmapTask(imageView, currentBook.getImageUrl(),this);
        	task.execute();
        }
        title.setText(currentBook.getBookName());
        fillData(author,"作者:    ",currentBook.getAuthorName());
        fillData(publisher,"出版社:  ",currentBook.getBookPress());
        fillData(publisherdate,"出版日期:",currentBook.getPublishDate());
        fillData(isbn,"ISBN: ",currentBook.getIsbn());
        fillData(summary,"",currentBook.getSummary());
        fillData(userName,"拥有者 :  ",currentBook.getOwner().getUserName());
        fillData(userEmail,"邮箱:    ",currentBook.getOwner().getUserEmail());
        fillData(wechatNum,"微信:    ",currentBook.getOwner().getWechatNum());
        
        
        userEmail.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",currentBook.getOwner().getUserEmail() , null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "借书："+currentBook.getBookName());
				startActivity(Intent.createChooser(emailIntent, "Send Email"));
				return false;
			}
		});
        
        wechatNum.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				/**
				 * 分享信息到朋友圈
				 * 
				 */
					Intent intent = new Intent();
					ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
					intent.setComponent(componentName);
					intent.setAction(Intent.ACTION_SEND);
					intent.putExtra(Intent.EXTRA_TEXT, "测试:"+currentBook.getBookName());
					startActivity(intent);
				return false;
			}
		});

    }
   private void fillData(TextView view,String prex,String text){
	   if(text==null || text.length()==0)
		   view.setVisibility(TextView.GONE);
	   else view.setText(prex+text);
   }
}
