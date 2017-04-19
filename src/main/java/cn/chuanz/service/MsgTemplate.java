package cn.chuanz.service;

public class MsgTemplate {

	/** 无效的输入*/
	public static String INVALID = "无效的输入";
	
	/** 根据航班号未查询到航班信息 */
	public static String NO_FIND_FLIGHT_BY_NO = "非常抱歉，系统无法查找到YYYYMMDD的XX航班信息，请你确认航班号是否正确。如果查询非当日航班，请发送日期和航班号。例如：\"TOMORROW,MU5117\"。(国际航班日期均为起飞地的当地时间)";
	
	/** 根据航线未查询到航班信息 */
	public static String NO_FIND_FLIGHT_BY_DEP_AND_ARR = "非常抱歉，没有查到该航线的数据。";
	
	public static String NO_FIND_AIRPORT = "很抱歉，系统暂时没有查到该机场的相关信息";
	
	/** 第一次订阅后返回信息 */
	public static String FIRST_SUBSCRIBE = "终于等到你，欢迎关注我的公众号，在这里会定期分享自己技术、生活的感悟。和你共同成长！<a href=\"http://mp.ihnbc.cn/emp/dynamic.e?method=detail\">点击进入</a>";

	
	public static String DATE_WILDCARD = "YYYYMMDD";
	public static String FLIGHT_WILDCARD = "XX";
	public static String TOMORROW_WILDCARD = "TOMORROW";
	

}
