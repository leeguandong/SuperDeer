package com.hfut.superdeer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.hfut.superdeer.fragment.ContentFragment;
import com.hfut.superdeer.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/*
 * @author LeeGaundong
 */

public class MainActivity extends SlidingFragmentActivity {

	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT_MENU = "fragment_content_menu";
	public static MainActivity instance = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		instance = this;

		// 设置侧边栏对象
		setBehindContentView(R.layout.left_menu);
		// 获取侧边栏对象
		SlidingMenu slidingMenu = getSlidingMenu();
		// 设置全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// slidingMenu.setBehindOffset(200); 屏幕预留200像素宽度
		// 200/320 * 屏幕宽度
		WindowManager wm = getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(width * 200 / 320);

		initFragment();
	}

	/**
	 * 初始化Fragment，将Fragment数据填充给布局文件
	 */
	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction(); // 开启事务

		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT_MENU);

		transaction.commit();// 提交事务
	}

	// 获取侧边栏fragment对象
	public LeftMenuFragment getLeftMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);// 根据标记找到对应的fragment
		return fragment;
	}

	// 获取主页fragment对象
	public ContentFragment getContentFragment() {
		FragmentManager fm = getSupportFragmentManager();
		ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT_MENU);// 根据标记找到对应的fragment
		return fragment;
	}

}
