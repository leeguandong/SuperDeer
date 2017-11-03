package com.hfut.superdeer.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author LeeGuandong Fragment基类实现
 */
public abstract class BaseFragment extends Fragment {

	public Activity mActivity;

	// fragment创建
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 方便子类调用
		mActivity = getActivity();
	}

	// 处理fragment的布局，返回view对象
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initviews();
	}

	// 依附于activity创建完成,activity早已创建完成，此时是acticity跑完所有自身逻辑之后的。
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData();
	}

	// 每个子类不一致，搞个抽象类让不同子类实现初始化方法
	public abstract View initviews();

	// 初始化数据
	public void initData() {

	}
}
