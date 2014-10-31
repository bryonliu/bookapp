package com.bookfriend.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bookfriend.R;

public class ShowMapActivity extends Activity {

	private MapView mapView;
	private AMap aMap;
    private Button btnLocation ;
    private Button btnNearByBook;
    private Button btnNearByPerson;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 必须要写
		btnLocation = (Button) findViewById(R.id.btn_location);
		btnNearByBook=(Button) findViewById(R.id.btn_nearbyBook);
		btnNearByPerson=(Button) findViewById(R.id.btn_nearbyPerson);
		
		init();
		
	}
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.setMyLocationEnabled(true);
		}
		double lat = getIntent().getDoubleExtra("initLat", 0);
		double lng = getIntent().getDoubleExtra("initLng", 0);
		//初始化地图的中心和缩放级别3-20
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),15));
		CircleOptions circle = new CircleOptions();
		circle = circle.center(new LatLng(lat, lng));
		MarkerOptions marker = new MarkerOptions();
		aMap.addCircle(circle.radius(1500));
		aMap.addMarker(marker.position(new LatLng(lat, lng)));
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
