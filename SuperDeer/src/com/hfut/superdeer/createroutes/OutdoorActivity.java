package com.hfut.superdeer.createroutes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.hfut.superdeer.R;
import com.hfut.superdeer.createroutes.MyOrientationListener.OnOrientationListener;

public class OutdoorActivity extends Activity {

	// 地图相关
	MapView mMapView;
	BaiduMap mBaiduMap;

	// UI相关
	Button but_start;
	Button but_stop;
	Chronometer chronometer;

	// 定位相关
	private LocationClient mLocationClient;

	BitmapDescriptor mCurrentMarker;
	boolean isFirstLoc = true;// 是否首次定位
	List<LatLng> points = new ArrayList<LatLng>();
	List<LatLng> points_tem = new ArrayList<LatLng>();
	OverlayOptions options;

	long toatlTime = 0;
	private SportItem sportItem;
	private List<SportItem> sportItems = new ArrayList<SportItem>();

	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	private Context context;
	private float mCurrentX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.create_routes_menut_outdoor);
		OutdoorActivity.this.context = this;

		// 初始化地图
		mMapView = (MapView) findViewById(R.id.bmapView);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapStatus(msu);

		// UI初始化
		but_start = (Button) findViewById(R.id.but_trace_start);
		but_stop = (Button) findViewById(R.id.but_trace_stop);

		chronometer = (Chronometer) findViewById(R.id.trace_timer);
		// 初始化计时器
		initChronometer();

		// 初始化定位信息
		initLocation();

		// 开始按钮监听
		but_start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (!mLocationClient.isStarted()) {
					mLocationClient.start();
				}
				chronometer.start();
				but_start.setVisibility(View.GONE);
				but_stop.setVisibility(View.VISIBLE);
			}
		});
		// 结束此次运动，绘制终点
		but_stop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				chronometer.stop();
				if (mLocationClient.isStarted()) {

					for (LatLng ll : points) {
						Log.e("TAG", ll.latitude + "," + ll.longitude);
					}

					// 绘制终点
					LatLng ll_end = points.get(points.size() - 1);
					options = new DotOptions().center(ll_end).color(0xAAff00ff).radius(15);
					mBaiduMap.addOverlay(options);
					mLocationClient.stop();

					// 保存此次运动数据
					sportItem = new SportItem("2014-12-15 07:56", 120, 100, points);
					sportItems.add(sportItem);
					// Intent intent = new
					// Intent(getBaseContext(),MyRoute_show.class);
					// intent.put
					// intent.putExtra("sportItem", sportItem);
					// startActivity(intent);
				}
			}
		});
	}

	/**
	 * 初始化计时器
	 */
	private void initChronometer() {
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {

			public void onChronometerTick(Chronometer cArg) {
				toatlTime++;

				long time = SystemClock.elapsedRealtime() - cArg.getBase();
				int h = (int) (time / 3600000);
				int m = (int) (time - h * 3600000) / 60000;
				int s = (int) (time - h * 3600000 - m * 60000) / 1000;
				String hh = h < 10 ? "0" + h : h + "";
				String mm = m < 10 ? "0" + m : m + "";
				String ss = s < 10 ? "0" + s : s + "";
				cArg.setText(hh + ":" + mm + ":" + ss);
			}
		});
		chronometer.setBase(SystemClock.elapsedRealtime());
	}

	// 初始化定位
	public void initLocation() {
		// 开启定位图层
		// mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(new MyLocationListenner());

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息,(注意如果想获取关于地址的信息，这里必须进行设置)
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		// 设置定位方式的优先级。
		// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
		// option.setPriority(LocationClientOption.GpsFirst);
		mLocationClient.setLocOption(option);

		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		
		myOrientationListener = new MyOrientationListener(context);
		myOrientationListener.setOnOrientationListener(new OnOrientationListener() {
			public void onOrientationChanged(float x) {
				mCurrentX = x;
			}
		});
	}

	/**
	 * 定位SDK监听函数
	 */
	class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {

			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			MyLocationData locData = new MyLocationData.Builder().accuracy(0).direction(mCurrentX)
					.latitude(location.getLatitude()).longitude(location.getLongitude()).build();

			mBaiduMap.setMyLocationData(locData);
			LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
			points.add(point);

			if (isFirstLoc) {
				points.add(point);
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				isFirstLoc = false;
			}

			// 设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true,
					mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			if (points.size() == 2) {
				// 这里绘制起点
				LatLng ll_start = new LatLng(points.get(0).latitude, points.get(0).longitude);
				options = new DotOptions().center(ll_start).color(0xAA00ff00).radius(15);
				mBaiduMap.addOverlay(options);
			}

			if (points.size() > 2 && points.size() <= 10) {
				options = new PolylineOptions().color(0xAAFF0000).width(6).points(points);
				mBaiduMap.addOverlay(options);
			}

			if (points.size() > 10) {
				// 每次绘制10个点，这样应该不会出现明显的折线吧
				points_tem = points.subList(points.size() - 10, points.size());
				// 绘图
				options = new PolylineOptions().color(0xAAFF0000).width(6).points(points_tem);
				mBaiduMap.addOverlay(options);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}

	}

	@Override
	protected void onStop() {
		mLocationClient.stop();
		super.onStop();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mBaiduMap.setMyLocationEnabled(true);// 默认第一次开启的权限
		if (!mLocationClient.isStarted())
			mLocationClient.start();// 是否开启
		// 开启方向传感器
		myOrientationListener.start();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocationClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
		// 停止方向传感器
		myOrientationListener.stop();
	}

}
