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
import com.bookfriend.model.Label;

public class LabelAdapter extends BaseAdapter{


	private Fragment activity;
	private static LayoutInflater inflater = null;
	private List<Label> labelsList;

	public LabelAdapter(Fragment fragment, List<Label> _labelList) {
		activity = fragment;
		this.labelsList = _labelList;
		inflater = (LayoutInflater) activity.getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return labelsList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_label, null);
		TextView labelItem = (TextView) vi.findViewById(R.id.item_label);
		Label label = labelsList.get(position);
		if (label.getLabelName()!=null && labelItem!=null)
			labelItem.setText(label.getLabelName());
		return vi;
	}
}
