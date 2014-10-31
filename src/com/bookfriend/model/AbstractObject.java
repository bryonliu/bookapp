package com.bookfriend.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;


public class AbstractObject {

	public static <T> T String2Object(String jsonString,Class<T> clazz){
		
		T info = null;
    	try{
    		info = JSON.parseObject(jsonString, clazz);
    	}catch(Exception e){
    		Log.e("book", e.getMessage(), e);
    		return null;
    	}
    	return info;
	}
}
