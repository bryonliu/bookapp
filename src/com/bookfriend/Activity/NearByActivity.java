package com.bookfriend.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.bookfriend.R;
import com.bookfriend.Fragment.NearByBookFragment;
import com.bookfriend.Fragment.NearByPerson;
import com.bookfriend.Fragment.NearLocationFragment;

public class NearByActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;
	private LayoutInflater layoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near_by);
		initView();
	}
    //初始化底部菜单栏
	private void initView() {
		layoutInflater = LayoutInflater.from(this);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		fillOneTab(R.drawable.seletor_tab_location,"附近",0,NearLocationFragment.class);
		fillOneTab(R.drawable.seletor_tab_location,"附近的书",1,NearByBookFragment.class);
		fillOneTab(R.drawable.seletor_tab_location,"附近的人",2,NearByPerson.class);
	}

	private void fillOneTab(int slId ,String hint,int index,Class clazz) {
		TabSpec tabSpec = mTabHost.newTabSpec(hint).setIndicator(getTabItemView(slId, hint));
		mTabHost.addTab(tabSpec, clazz, null);
		 mTabHost.getTabWidget().getChildAt(index).setBackgroundResource(R.drawable.selector_tab_background);
	}

	private View getTabItemView(int imgId, String hint) {
		View view = layoutInflater.inflate(R.layout.item_tabs, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_image);
		imageView.setImageResource(imgId);
		TextView textView = (TextView) view.findViewById(R.id.tab_text);
		textView.setText(hint);
		return view;
	}
}
