package cn.chuanz.util;

import com.github.chuanzh.util.ConfigRead;

public class Constant {

	public static String TEXT = "text";
	public static String EVENT = "event";
	public static String SUBSCRIBE = "subscribe";
	public static String UNSUBSCRIBE = "unsubscribe";
	public static String TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
	public static String TEMPLATEFANMSGREAD = "TEMPLATEFANMSGREAD";
	public static String SUCCESS = "success";
	
	/** 航班号*/
	public static final int FLIGHT_NUMBER = 1;
	
	/** 航班日期+航班号*/
	public static final int FLIGHT_DATE_AND_NUMBER = 2;
	
	/** 起降地*/
	public static final int FLIGHT_DEP_AND_ARR = 3;
	
	/** 机场*/
	public static final int AIRPORT = 4;
	
	/** 无效*/
	public static final int INVALID = 0;
	
	
	/** 静态图片路径 */
	public static String getImgUrl() {
		return ConfigRead.readValue("resourceHost") + "/images/";
	}
	
}
