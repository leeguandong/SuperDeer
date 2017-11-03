package com.hfut.superdeer.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hfut.superdeer.MainActivity;
import com.hfut.superdeer.R;
import com.hfut.superdeer.base.BasePager;
import com.hfut.superdeer.sub.CityFastRoutes;
import com.hfut.superdeer.sub.travelRoutes;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author LeeGuandong 
 * 
 */
public class ContentFragment extends BaseFragment {

	private ArrayList<BasePager> mPagers;
	private ViewPager mViewPager;
	private RadioGroup rgGroup;

	public View initviews() {
		View view = View.inflate(mActivity, R.layout.fragment_content_menu, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();

		// 添加五个标签页
		mPagers.add(new CityFastRoutes(mActivity));
		mPagers.add(new travelRoutes(mActivity));

		// viewpager的适配器，在rggroup里面进行调用
		mViewPager.setAdapter(new ContentAdapter());

		// 侧边栏标签切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_cfr:
					// mViewPager.setCurrentItem(0);
					mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画

					break;
				case R.id.rb_tr:
					mViewPager.setCurrentItem(1, false);
					break;

				default:
					break;
				}
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int position) {
				BasePager pager = mPagers.get(position);
				pager.initData();

				setSlidingMenuEnable(true);

			}

			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			public void onPageScrollStateChanged(int state) {

			}
		});

		// 手动加载第一页数据，在第一个小鹿大数据上是初始化了数据的
		mPagers.get(0).initData();
	}

	/**
	 * 开启或禁用侧边栏
	 * 
	 * @param enable
	 */
	protected void setSlidingMenuEnable(boolean enable) {
		// 获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;// 获取当前页面对象的布局

			// pager.initData();// 初始化数据, viewpager会默认加载下一个页面,
			// 为了节省流量和性能,不要在此处调用初始化数据的方法

			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	public CityFastRoutes getCloudeerPage() {
		CityFastRoutes pager = (CityFastRoutes) mPagers.get(1);
		return pager;
	}
}
