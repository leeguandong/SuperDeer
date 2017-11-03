package com.hfut.superdeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * @author LeeGaundong
 * 
 *         闪屏页面
 */
public class SplashActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SpeechUtility.createUtility(SplashActivity.this,SpeechConstant.APPID +" 59545687");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

		// 旋转动画
		RotateAnimation animRotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animRotate.setDuration(1000);// 动画时间
		animRotate.setFillAfter(true);// 保持动画结束状态

		// 缩放动画
		ScaleAnimation animScale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animScale.setDuration(1000);
		animScale.setFillAfter(true);// 保持动画结束状态

		// 渐变动画
		AlphaAnimation animAlpha = new AlphaAnimation(0, 1);
		animAlpha.setDuration(2000);// 动画时间
		animAlpha.setFillAfter(true);// 保持动画结束状态

		// 动画集合
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(animRotate);
		set.addAnimation(animScale);
		set.addAnimation(animAlpha);

		// 启动动画
		rlRoot.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// 动画结束,跳转页面
				// 如果是第一次进入, 跳新手引导
				// 否则跳主页面
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);

				finish();// 结束当前页面
			}
		});
	}
}
