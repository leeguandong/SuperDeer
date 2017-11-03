package com.hfut.superdeer.createroutes;

import com.hfut.superdeer.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author LeeGuandong
 * 
 */
public class CreateRoutesActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_routes_menu);

		// 1.找到我们关心的控件
		Button btn_outdoor = (Button) findViewById(R.id.btn_outdoor);
		Button btn_indoor = (Button) findViewById(R.id.btn_indoor);

		// 2.给当前按钮设置点击事件
		btn_outdoor.setOnClickListener(this);
		btn_indoor.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_outdoor:
			Intent intent = new Intent();
			intent.setClass(CreateRoutesActivity.this, OutdoorActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_indoor:
			Intent intent1 = new Intent();
			intent1.setClass(CreateRoutesActivity.this, IndoorActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}

}
