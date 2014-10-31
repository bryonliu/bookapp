package com.bookfriend.tools;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

public  class LoginUtils {

	LinkedHashMap<String, String> li = new LinkedHashMap<String, String>(){

		@Override
		protected boolean removeEldestEntry(Entry<String, String> eldest) {
			// TODO Auto-generated method stub
			return super.removeEldestEntry(eldest);
		}
		
	};
	public static long getCurrentUserId(Context context){
		SharedPreferences sp = context.getSharedPreferences("cloud", 0);
		long userId = sp.getLong("userId", -1);
		return userId;
	}
}
