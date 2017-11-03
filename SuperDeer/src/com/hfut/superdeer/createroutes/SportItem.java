package com.hfut.superdeer.createroutes;

import java.io.Serializable;
import java.util.List;

import com.baidu.mapapi.model.LatLng;

public class SportItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 此次运动的开始时间。2014-12-15 16:20
	 */
	private String start_time;
	/**
	 * 此次运动时长24:35
	 * 
	 */
	private long totalTime;
	/**
	 * 运动的总距离
	 */
	private int distance;
	/**
	 * 运动经过的坐标点，用于恢复轨迹
	 */
	private List<LatLng> points;

	public SportItem() {
		super();

	}

	public SportItem(String start_time, long totalTime, int distance,
			List<LatLng> points) {

		this.start_time = start_time;
		this.totalTime = totalTime;
		this.distance = distance;
		this.points = points;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<LatLng> getPoints() {
		return points;
	}

	public void setPoints(List<LatLng> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "SportItem [start_time=" + start_time + ", totalTime="
				+ totalTime + ", distance=" + distance + ", points=" + points
				+ "]";
	}

}
