package cn.chuanz.service;

public class MsgTemplate {

	/** 无效的输入*/
	public static String INVALID = "很抱歉，系统暂时只能提供航班动态、机场信息查询";
	
	/** 根据航班号未查询到航班信息 */
	public static String NO_FIND_FLIGHT_BY_NO = "非常抱歉，系统无法查找到YYYYMMDD的XX航班信息，请你确认航班号是否正确。如果查询非当日航班，请发送日期和航班号。例如：\"TOMORROW,MU5117\"。(国际航班日期均为起飞地的当地时间)";
	
	/** 根据航线未查询到航班信息 */
	public static String NO_FIND_FLIGHT_BY_DEP_AND_ARR = "非常抱歉，没有查到该航线的数据。";
	
	public static String NO_FIND_AIRPORT = "很抱歉，系统暂时没有查到该机场的相关信息";
	
	/** 第一次订阅后返回信息 */
	public static String FIRST_SUBSCRIBE = "感谢您关注查航班，我将为您提供最便捷的航班起降动态、目的地天气和机场运行情况查询服务。现在就试试吧。\n\n>>查航班 – 回复航班号（如：MU5117）\n>>查机场 – 回复机场名称（如：广州）\n>>查起降地 – 回复起降地名称（如：北京到武汉）\n\n（本数据由航班管家提供，下载http://www.huoli.com）";

	
	public static String DATE_WILDCARD = "YYYYMMDD";
	public static String FLIGHT_WILDCARD = "XX";
	public static String TOMORROW_WILDCARD = "TOMORROW";
	

}
