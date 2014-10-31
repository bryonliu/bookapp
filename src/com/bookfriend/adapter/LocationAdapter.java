package com.bookfriend.adapter;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bookfriend.R;
import com.bookfriend.model.Location;

public class LocationAdapter extends BaseAdapter {

	List<Location> locations ;
	private static LayoutInflater inflater = null;
	
	public LocationAdapter(List<Location> locations, Fragment activity) {
		super();
		this.locations = locations;
		inflater = (LayoutInflater) activity.getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_location, null);
		TextView textView = (TextView) vi.findViewById(R.id.text_location);
		textView.setText(locations.get(position)._name);
		return vi;
	}
	@Override
	public int getCount() {
		return locations.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	

}
