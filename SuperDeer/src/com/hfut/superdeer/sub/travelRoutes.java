package com.hfut.superdeer.sub;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.hfut.superdeer.MainActivity;
import com.hfut.superdeer.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author Administrator
 *
 */
public class travelRoutes extends BasePager {

	/**
	 * @param activity
	 */
	public travelRoutes(Activity activity) {
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
		view.setText("旅游路线");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		
		flContent.addView(view);
		
		tvTitle.setText("旅游路线");
	}

}
