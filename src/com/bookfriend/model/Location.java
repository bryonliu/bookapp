package com.bookfriend.model;

import com.alibaba.fastjson.JSONObject;

public class Location {

	public String _id;
	public  String _name;
	public String _location;
	public String _address;
	public long user_id;
	
	public Location(String _name, String _location, String _address, long user_id) {
		this._name = _name;
		this._location = _location;
		this._address = _address;
		this.user_id = user_id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String geLocation() {
		return _location;
	}
	public void setLocation(String _location) {
		this._location = _location;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String toJsonString(){
		return JSONObject.toJSONString(this);
	}
	
}
