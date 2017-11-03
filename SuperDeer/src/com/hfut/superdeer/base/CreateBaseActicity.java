package com.hfut.superdeer.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hfut.superdeer.R;

/**
 * @author LeeGuandong
 *
 */
public class CreateBaseActicity extends Activity {
	
	protected TextView tvTitle;
	private ImageButton btnMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_base_pager);
		
		tvTitle=(TextView)findViewById(R.id.tv_title);
		btnMenu=(ImageButton) findViewById(R.id.btn_menu);
		
		btnMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				toggle();
			}
		});
				
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
