package com.hfut.superdeer.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hfut.superdeer.R;
import com.hfut.superdeer.bean.GetRoutesBean;
import com.hfut.superdeer.bean.GetRoutesBean.TraceWay;
import com.hfut.superdeer.global.GlobalConstants;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Administrator
 * 
 */
public class GetRoutes extends Activity {

	private ListView lv_get_routes;
	private GetRoutesAdapter routesAdapter;
	private Activity mActivity;
	private GetRoutesBean RoutesData;
	private ArrayList<TraceWay> Traceway;
	private Context Context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_routes_menu);
		getDataFromServer();

		lv_get_routes = (ListView) findViewById(R.id.lv_get_routes);
		
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalConstants.CATEGORY_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 请求成功
				String result = responseInfo.result;// 获取服务器返回结果
				System.out.println("服务器返回结果:" + result);

				// JsonObject, Gson
				processData(result);

				// 写缓存
				// CacheUtils.setCache(GlobalConstants.CATEGORY_URL, result,
				// mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// 请求失败
				error.printStackTrace();
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void processData(String json) {
		Gson gson = new Gson();
		RoutesData = gson.fromJson(json, GetRoutesBean.class);
		System.out.println("解析结果:" + RoutesData);
		Traceway = RoutesData.traces;
		System.out.println(Traceway);
		System.out.println(Traceway + "不为空");
		if (Traceway != null) {
			routesAdapter = new GetRoutesAdapter();
			lv_get_routes.setAdapter(routesAdapter);
		}
	}

	class GetRoutesAdapter extends BaseAdapter {

		private TextView tvTitle;
		private TextView tvstart;
		private TextView tvend;

		public int getCount() {
			return Traceway.size();
		}

		public TraceWay getItem(int position) {
			return Traceway.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view ;
			if (convertView != null) {
				view = convertView;
			} else {
				view = View.inflate(GetRoutes.this, R.layout.list_item_ways, null);
			}

			// SmartImageView info_iv_pic = (SmartImageView)
			// view.findViewById(R.id.info_iv_pic);
			tvTitle = (TextView) view.findViewById(R.id.tv_title_ways);
			tvstart = (TextView) view.findViewById(R.id.tv_start);
			tvend = (TextView) view.findViewById(R.id.tv_end);

			// 3.获取postion位置条目对应的list集合中的车位数据
			TraceWay ways = Traceway.get(position);
			// 4.将数据设置给这些子控件做显示
			tvTitle.setText(ways.name);
			tvstart.setText(ways.startposition);
			tvend.setText(ways.endposition);

			return view;

		}

//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if (convertView == null) {
//				convertView = View.inflate(Context, R.layout.list_item_ways, null);
//				holder = new ViewHolder();
//				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title_ways);
//				holder.tvstart = (TextView) convertView.findViewById(R.id.tv_start);
//				holder.tvend = (TextView) convertView.findViewById(R.id.tv_end);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			TraceWay ways = getItem(position);
//			holder.tvTitle.setText(ways.name);
//			holder.tvstart.setText(ways.startposition);
//			holder.tvend.setText(ways.endposition);
//
//			// 根据本地记录来标记已读未读
//			String readIds = PrefUtils.getString(mActivity, "read_ids", "");
//			if (readIds.contains(ways.name + "")) {
//				holder.tvTitle.setTextColor(Color.GRAY);
//			} else {
//				holder.tvTitle.setTextColor(Color.BLACK);
//			}
//
//			// mBitmapUtils.display(holder.ivIcon, news.listimage);
//
//			return convertView;
//		}
	}

	// static class ViewHolder {
	// public TextView tvstart;
	// public TextView tvTitle;
	// public TextView tvend;
	// }

}
