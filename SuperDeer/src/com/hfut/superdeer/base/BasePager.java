package com.hfut.superdeer.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hfut.superdeer.R;

/**
 * @author LeeGuandong 2个标签页的基类
 */
public class BasePager {
	public Activity mActivity;

	public TextView tvTitle;
	public ImageButton btnMenu;
	public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局

	public ImageButton btnPhoto;// 组图切换按钮
	public View mRootView;// 当前页面的布局对象

	public BasePager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}

	// 初始化布局
	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		// 最上部标签的title
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		// 点击可调出左边页面的
		btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
		btnPhoto = (ImageButton) view.findViewById(R.id.btn_photo);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content_menu);

		btnMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				toggle();
			}
		});

		return view;
	}

	/**
	 * 打开或者关闭侧边栏
	 */
	public void toggle() {
		
	}

	// 初始化数据
	public void initData() {

	}
}
