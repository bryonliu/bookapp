package com.bookfriend.AsyncTask;

import java.net.URL;

import com.bookfriend.tool.bitmap.ImageFileCache;
import com.bookfriend.tool.bitmap.ImageMemoryCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapTask extends AsyncTask<String, Integer, Bitmap> {

	private ImageMemoryCache memCache;
	private ImageFileCache fileCache;
	private ImageView imageView;
	private String urlStr;

	public BitmapTask(ImageView _imageView, String _url, Context context) {
		imageView = _imageView;
		urlStr = _url;
		memCache = ImageMemoryCache.getMemCache(context);
		fileCache = new ImageFileCache();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		return getBitmap();
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		try {
			if (result == null | imageView == null)
				return;
			imageView.setImageBitmap(result);
			return;
		} catch (Exception e) {
			Log.i("book", e.getMessage(), e);
		}
	}
	private Bitmap getBitmap() {
		Bitmap result = memCache.getBitmapFromCache(urlStr);
		if (result == null) {
			result = fileCache.getImage(urlStr);
			if (result == null) {
				try {
					URL url = new URL(urlStr);
					result = BitmapFactory.decodeStream(url.openStream());
				} catch (Exception e) {
					result = null;
					Log.d("book", e.getMessage(), e);
				}
				if (result != null) {
					fileCache.saveBitmap(result, urlStr);
					memCache.addBitmapToCache(urlStr, result);
				}
			} else {
				memCache.addBitmapToCache(urlStr, result);
			}
		}
		return result;
	}
}