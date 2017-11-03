package com.hfut.superdeer.sub;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.hfut.superdeer.MainActivity;
import com.hfut.superdeer.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author LeeDuandong 首页，第一个进入页面，仙鹿云平台，仙鹿云平台的内容包括，城市快捷路线和旅游路线两类，里面还有更深层的内容
 * 
 */
public class CityFastRoutes extends BasePager {

	/**
	 * @param activity
	 */
	public CityFastRoutes(Activity activity) {
		super(activity);
	}

	@Override
	public void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
	}

	@Override
	public void initData() {
		// 要把帧布局填充给布局对象
		TextView view = new TextView(mActivity);
		view.setText("城市快捷推荐线路");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);

		flContent.addView(view);

		// 修改页面标题
		tvTitle.setText("城市快捷推荐线路");

	}
}
