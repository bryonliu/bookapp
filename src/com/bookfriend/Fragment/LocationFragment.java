package com.bookfriend.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.bookfriend.R;
import com.bookfriend.Activity.NearByActivity;
import com.bookfriend.AsyncTask.MyLocationsTask;
import com.bookfriend.AsyncTask.ShareLoctionTask;

public class LocationFragment extends Fragment implements AMapLocationListener {

	private LocationManagerProxy mLocationManagerProxy;
	private TextView longitudeView;
	private TextView latitudeView;
	private TextView addressView;

	private ListView loclistView;
	private Button shareButon;

	private String geoLat = "";
	private String geoLng = "";
	private String address = "";
	private String name = "";
	private boolean locisRight = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location, container, false);
		longitudeView = (TextView) rootView.findViewById(R.id.longitude);
		latitudeView = (TextView) rootView.findViewById(R.id.latitude);
		addressView = (TextView) rootView.findViewById(R.id.address);
		loclistView = (ListView) rootView.findViewById(R.id.list_mylocation);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//showMyHistoryLoctions();
		initlocation();
		shareButon = (Button) getActivity().findViewById(R.id.button_share_mylocation);

		shareButon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (locisRight) {
					ShareLoctionTask task = new ShareLoctionTask(LocationFragment.this);
					task.execute(geoLng, geoLat, address, name);
					Intent intent = new Intent();
					intent.setClass(LocationFragment.this.getActivity(), NearByActivity.class);
				  //  intent.putExtra("initLat", Double.valueOf(geoLat));
				//	intent.putExtra("initLng", Double.valueOf(geoLng));
					startActivity(intent);
				} else {
					initlocation();
				}

			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationManagerProxy != null) {
			mLocationManagerProxy.removeUpdates(this);
			mLocationManagerProxy.destroy();
		}
		mLocationManagerProxy = null;
	}

	private void showMyHistoryLoctions() {
		MyLocationsTask task = new MyLocationsTask(LocationFragment.this);
		task.execute();
	}

	/**
	 * 初始化定位
	 */
	private void initlocation() {
		mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, this);
		mLocationManagerProxy.setGpsEnable(false);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
			shareButon.setText("分享你的位置");
			locisRight = true;
			geoLat = String.valueOf(amapLocation.getLatitude());
			geoLng = String.valueOf(amapLocation.getLongitude());
			address = amapLocation.getAddress();
			name = amapLocation.getStreet();
		} else {
			geoLat = "未知";
			geoLng = "未知";
			address = "你是来到火星了么 ?  !";
			shareButon.setText("重新定位");
		}
		latitudeView.setText(geoLat.toString());
		longitudeView.setText(geoLng.toString());
		addressView.setText(name.trim());
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public ListView getLoclistView() {
		return loclistView;
	}

	public void setLoclistView(ListView loclistView) {
		this.loclistView = loclistView;
	}

}
