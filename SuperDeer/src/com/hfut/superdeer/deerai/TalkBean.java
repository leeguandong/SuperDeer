package com.hfut.superdeer.deerai;

/**
 * 提问和回答的对象封装
 * 
 */
public class TalkBean {

	public TalkBean(String content, boolean isAsk, int imageId) {
		super();
		this.content = content;
		this.isAsk = isAsk;
		this.imageId = imageId;
	}

	public String content;

	public boolean isAsk;// 标记是否是提问

	public int imageId;// 回答图片的id
}
