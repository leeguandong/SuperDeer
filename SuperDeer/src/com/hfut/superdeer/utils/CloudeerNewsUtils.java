package com.hfut.superdeer.utils;

import java.util.ArrayList;

import android.content.Context;

import com.hfut.superdeer.R;
import com.hfut.superdeer.bean.CloudeerNewsBean;

/**
 * @author LeeGuandong
 * 
 */
public class CloudeerNewsUtils {

	// 封装新闻的假数据到list中返回
	public static ArrayList<CloudeerNewsBean> getAllNews(Context context) {

		ArrayList<CloudeerNewsBean> arrayList = new ArrayList<CloudeerNewsBean>();

		for (int i = 0; i < 100; i++) {
			CloudeerNewsBean newsBean = new CloudeerNewsBean();
			newsBean.title = "大蜀山旅游线路，合肥最高峰，周末放松之首选";
			newsBean.news_url = "http://www.mafengwo.cn/poi/6631151.html";
			newsBean.icon = context.getResources().getDrawable(R.drawable.hefeidashushan);// 通过context对象将一个资源id转换成一个Drawable对象。
			newsBean.date="2016-10-12  16:40";
			arrayList.add(newsBean);

			CloudeerNewsBean newsBean1 = new CloudeerNewsBean();
			newsBean1.title = "宁国路龙虾一条街，让你吃到嗨";
			newsBean1.news_url = "http://www.mafengwo.cn/poi/6775839.html";
			newsBean1.icon = context.getResources().getDrawable(R.drawable.ninguolu);// 通过context对象将一个资源id转换成一个Drawable对象。
			newsBean1.date="2017-6-12 9:35";
			arrayList.add(newsBean1);

			CloudeerNewsBean newsBean2 = new CloudeerNewsBean();
			newsBean2.title = "回味经典，展望未来，怀恋先辈光荣革命的路线之首选--渡江战役及连贯";
			newsBean2.news_url = "http://www.dianping.com/shop/8007737";
			newsBean2.icon = context.getResources().getDrawable(R.drawable.dujiangjiliaanguan);// 通过context对象将一个资源id转换成一个Drawable对象。
			newsBean2.date="2017-6-6 7:49";
			arrayList.add(newsBean2);
		}
		return arrayList;
	}

}
