package com.hfut.superdeer.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hfut.superdeer.LoginActivity;
import com.hfut.superdeer.MainActivity;
import com.hfut.superdeer.R;
import com.hfut.superdeer.createroutes.CreateRoutesActivity;
import com.hfut.superdeer.deerai.DeerAiActivity;
import com.hfut.superdeer.impl.Cloudeer;
import com.hfut.superdeer.impl.GetRoutes;
import com.hfut.superdeer.impl.MyRoutes;
import com.hfut.superdeer.impl.SearchRoutes;
import com.hfut.superdeer.impl.Setting;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author LeeGuandong 侧边栏
 */
public class LeftMenuFragment extends BaseFragment {

	private RadioGroup rgGroup;
	private ImageButton ib_left_login;

	@Override
	public View initviews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group_left);

		ib_left_login = (ImageButton) view.findViewById(R.id.ib_left_login);

		ib_left_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent is = new Intent(getActivity(), LoginActivity.class);
				startActivity(is);
			}
		});

		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_supercloud:
					Intent is = new Intent(getActivity(), Cloudeer.class);
					startActivity(is);
//					toggle();
					break;

				case R.id.rb_get_route:
					Intent igr = new Intent(getActivity(), GetRoutes.class);
					startActivity(igr);
//					toggle();
					break;

				case R.id.rb_create_route:
					Intent icr = new Intent(getActivity(), CreateRoutesActivity.class);
					startActivity(icr);
//					toggle();

					break;
				case R.id.rb_route:
					Intent ir = new Intent(getActivity(), MyRoutes.class);
					startActivity(ir);
//					toggle();
					break;

				case R.id.rb_search_route:
					Intent isr = new Intent(getActivity(), SearchRoutes.class);
					startActivity(isr);
//					toggle();
					break;

				case R.id.rb_setting:
					Intent ist = new Intent(getActivity(), Setting.class);
					startActivity(ist);
//					toggle();
					break;

				case R.id.rb_deerai:
					Intent id = new Intent(getActivity(), DeerAiActivity.class);
					startActivity(id);
//					toggle();
					break;

				default:
					break;
				}
			}
		});

		return view;
	}

	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
	}

	public void initData() {

	}
}
