package com.hfut.superdeer.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.hfut.superdeer.R;
import com.hfut.superdeer.bean.CloudeerNewsBean;
import com.hfut.superdeer.utils.CloudeerNewsUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author LeeGuandong
 * 
 */
public class Cloudeer extends Activity implements OnPageChangeListener, OnItemClickListener {

	private ViewPager viewPager;
	private ImageView imageView;
	private ArrayList<ImageView> imageViewList;
	private View pointView;
	private LinearLayout ll_point_container;
	private String[] contentDescs;
	private TextView tv_desc;
	private int lastEnablePoint;
	boolean isRunning = false;

	private ListView lv_coudeer;

	private Context Context;
	private ArrayList<CloudeerNewsBean> newsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloudeer);
		Context = this;

		lv_coudeer = (ListView) findViewById(R.id.lv_cloudeer);

		View mHeaderView = View.inflate(this, R.layout.activity_cloudeer_header, null);
		ViewUtils.inject(this, mHeaderView);// 此处必须将头布局也注入
		lv_coudeer.addHeaderView(mHeaderView);

		newsList = CloudeerNewsUtils.getAllNews(Context);
		NewsAdapter newsAdapter = new NewsAdapter();
		lv_coudeer.setAdapter(newsAdapter);
		lv_coudeer.setOnItemClickListener(this);

		// 初始化布局View布局
		initView();

		// Model 数据
		initData();

		// Controller 控制器
		initAdapter();

		// 开启一个轮询
		new Thread() {
			public void run() {
				isRunning = true;
				while (isRunning) {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}

					runOnUiThread(new Runnable() {
						public void run() {
							// 往下跳一位，但不可在子线程更新ui
							viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
						}
					});
				}
			};
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setOnPageChangeListener(this);
		// viewPager.setOffscreenPageLimit(1); 左右各缓存几个
		ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);

		tv_desc = (TextView) findViewById(R.id.tv_desc);
	}

	private void initData() {
		// 图片资源id数组
		int[] imageResIds = new int[] { R.drawable.xianludashuju, R.drawable.xianlukuaipao,
				R.drawable.xianlunideshenghuo, R.drawable.zuihaowan, R.drawable.tianyahaijiao, };

		// 文本描述
		contentDescs = new String[] { "仙鹿大数据", "仙鹿快跑", "仙鹿你的生活", "最好玩的路线", "一起走，去天涯海角" };

		imageViewList = new ArrayList<ImageView>();

		// 初始化要展示的5个ImageView
		for (int i = 0; i < imageResIds.length; i++) {
			imageView = new ImageView(this);
			// setBackgroundResource是安卓的图片资源库，栈
			imageView.setBackgroundResource(imageResIds[i]);
			imageViewList.add(imageView);

			// 加小白点，指示器
			pointView = new View(this);
			pointView.setBackgroundResource(R.drawable.selector_bg_point);

			LayoutParams params = new LinearLayout.LayoutParams(20, 20);
			if (i != 0)
				params.leftMargin = 10;

			ll_point_container.addView(pointView, params);
		}
	}

	private void initAdapter() {
		ll_point_container.getChildAt(0).setEnabled(true);
		tv_desc.setText(contentDescs[0]);
		lastEnablePoint = 0;

		viewPager.setAdapter(new MyAdapter());

		// 默认设置到中间某个位置
		int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
		// 2145483647/2=1073741823-(1073741823%5)肯定等于0
		viewPager.setCurrentItem(pos);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;// 假的无限循环
		}

		// 指定复用的判断逻辑，固定写法
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 党话到新的条目式，又返回了，view是否被复用，返回判断规则
			return view == object;
		}

		// 1.返回要显示的条目内容，创建条目 container 容器：Viewpager position 当前要显示的条目位置
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			// 除4取余就可以不断循环
			int newPosition = position % imageViewList.size();

			ImageView imageView = imageViewList.get(newPosition);
			// a.把View对象添加到container中
			container.addView(imageView);
			// b.把view对象返回给框架，适配器，因为他要缓存

			return imageView; // 不许重写，否则报异常
		}

		// 2.销毁条目
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// object要销毁的对象
			container.removeView((View) object);
		}
	}

	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// 滚动时调用
	}

	public void onPageSelected(int position) {
		// 新的条目被选中调用

		int newposition = position % imageViewList.size();
		tv_desc.setText(contentDescs[newposition]);

		// 把之前的禁用，把最新的启用，更新指示器
		ll_point_container.getChildAt(lastEnablePoint).setEnabled(false);
		ll_point_container.getChildAt(newposition).setEnabled(true);

		// 记录之前的位置
		lastEnablePoint = newposition;
	}

	public void onPageScrollStateChanged(int state) {
		// 滚动状态发生变化时调用
	}

	class NewsAdapter extends BaseAdapter {

		private BitmapUtils mBitmapUtils;

		public NewsAdapter() {
			mBitmapUtils = new BitmapUtils(Context);
			mBitmapUtils.configDefaultLoadingImage(R.drawable.deer);
		}

		public int getCount() {
			return newsList.size();
		}

		public Object getItem(int position) {
			return newsList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(Context, R.layout.list_item_ways, null);
				holder = new ViewHolder();
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 3.获取postion位置条目对应的list集合中的新闻数据，Bean对象
			CloudeerNewsBean newsBean = newsList.get(position);
			// 4.将数据设置给这些子控件做显示
			holder.ivIcon.setImageDrawable(newsBean.icon);// 设置imageView的图片
			holder.tvTitle.setText(newsBean.title);
			holder.tvDate.setText(newsBean.date);

			// NewsData news = getItem(position);
			// holder.tvTitle.setText(news.title);
			// holder.tvDate.setText(news.pubdate);

			// mBitmapUtils.display(holder.ivIcon, news.listimage);

			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView ivIcon;
		public TextView tvTitle;
		public TextView tvDate;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 需要获取条目上bean对象中url做跳转
		CloudeerNewsBean bean = (CloudeerNewsBean) parent.getItemAtPosition(position);

		String url = bean.news_url;

		// 跳转浏览器
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
}
