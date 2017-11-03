package com.hfut.superdeer.bean;

import java.util.ArrayList;

/**
 * @author LeeGuandong
 * 
 */
public class GetRoutesBean {

	public String dateTime;
	public String epochTime;
	public String totalNumberOfEvents;
	public String totalNumberOfSamples;
	public String traceId;
	public String userId;
	public String userName;
	public String version;

	public ArrayList<TraceWay> traces;

	public class TraceWay {
		public String address;
		public String city;
		public String coordinates;
		public String countryCode;
		public String endposition;
		public String floor;
		public String name;
		public String notes;
		public String startposition;
	}
}
