package cn.chuanz.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.chuanzh.orm.DbBasicService;
import com.github.chuanzh.orm.DbFactory;
import com.github.chuanzh.util.FuncStatic;

import freemarker.template.utility.DateUtil;

public class FlightUtil {
	
	private static Logger logger = Logger.getLogger(FlightUtil.class);
	
	public static void main(String[] args) {
		System.out.println(FlightUtil.isFlightDateAndNumber("2017010,CA1234"));
	}
	
	public static boolean isFlightNumber(String content) {
		if(content.matches("^(([0-9][A-Z]|[A-Z][0-9]|[A-Z]{2})\\d{1,4}[A-Z]?)$"))
			return true;
		
		return false;
	}
	
	public static boolean isFlightDateAndNumber(String content) {
		if (content.indexOf(",") != -1) {
			String[] arr = content.split(",");
			if(arr[0].matches("\\d{8}") && arr[1].matches("^(([0-9][A-Z]|[A-Z][0-9]|[A-Z]{2})\\d{1,4}[A-Z]?)$"))
				return true;
		}
		
		return false;
	}
	
	public static boolean isDepAndArr(String content) {
		if (content.indexOf("到") != -1 && content.split("到").length == 2) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isAirport(String content) {
		return false;
	}
	
	/**
	 * 航班号获取航空公司名称
	 * 
	 * @param flyno
	 * @return
	 * @date 2013-5-21
	 */
	public static String getCompanyName(String flyno) {
		if (flyno.indexOf("CZ") != -1) {
			return "南航";
		} else if (flyno.indexOf("CA") != -1) {
			return "国航";
		} else if (flyno.indexOf("MU") != -1) {
			return "东航";
		} else if (flyno.indexOf("ZH") != -1) {
			return "深航";
		} else if (flyno.indexOf("3U") != -1) {
			return "川航";
		} else if (flyno.indexOf("SK") != -1) {
			return "北欧";
		} else if (flyno.indexOf("HU") != -1) {
			return "海航";
		} else if (flyno.indexOf("SC") != -1) {
			return "山航";
		} else if (flyno.indexOf("OS") != -1) {
			return "奥地";
		} else if (flyno.indexOf("HO") != -1) {
			return "吉祥";
		} else if (flyno.indexOf("FM") != -1) {
			return "上航";
		} else if (flyno.indexOf("8L") != -1) {
			return "祥鹏";
		} else if (flyno.indexOf("0Q") != -1) {
			return "重庆";
		} else if (flyno.indexOf("PN") != -1) {
			return "西部";
		} else if (flyno.indexOf("QF") != -1) {
			return "澳洲";
		} else if (flyno.indexOf("MH") != -1) {
			return "马来西亚";
		} else if (flyno.indexOf("MF") != -1) {
			return "厦航";
		} else if (flyno.indexOf("EU") != -1) {
			return "成都航空";
		} else if (flyno.indexOf("TV") != -1) {
			return "西藏航空";
		} else if (flyno.indexOf("KA") != -1) {
			return "香港港龙";
		} else if (flyno.indexOf("NX") != -1) {
			return "澳门航空";
		} else if (flyno.indexOf("CX") != -1) {
			return "国泰航空";
		} else if (flyno.indexOf("HX") != -1) {
			return "香港航空";
		} else if (flyno.indexOf("UO") != -1) {
			return "香港快运";
		} else if (flyno.indexOf("KY") != -1) {
			return "昆明航空";
		}
		return null;
	}
	
	/**
	 * 获取状态 图片url路径
	 * 
	 * @param status
	 * @return
	 * @date 2013-4-22
	 */
	public static String getStatusUrl(String status) {
		String url = Constant.getImgUrl();
		if ("计划".equals(status)) {
			return url + "newjh.png";
		} else if ("起飞".equals(status)) {
			return url + "qf.png";
		} else if ("延误".equals(status)) {
			return url + "tw.png";
		} else if ("到达".equals(status)) {
			return url + "newdd.png";
		} else if ("取消".equals(status)) {
			return url + "qx.png";
		} else if ("备降".equals(status)) {
			return url + "bj.png";
		} else if ("返航".equals(status)) {
			return url + "fh.png";
		}

		return "";
	}
	
	/**
	 * 状态 css
	 * 
	 * @param status
	 * @return
	 * @date 2013-4-22
	 */
	public static String getStautsStyle(String status) {
		if ("延误".equals(status)) {
			return "yw";
		} else if ("起飞".equals(status) || "到达".equals(status)) {
			return "dd";
		} else if ("计划".equals(status)) {
			return "dd";
		}
		return "yw";
	}
	

	/**
	 * 获取状态 图片url路径
	 * 
	 * @param status
	 * @return
	 * @date 2013-4-22
	 */
	public static String getShareImaUrl(String status) {
		String url = Constant.getImgUrl();
		if ("计划".equals(status)) {
			return url + "newfenxiang_jh.png";
		} else if ("起飞".equals(status)) {
			return url + "fenxiang_qf.png";
		} else if ("延误".equals(status)) {
			return url + "fenxiang_tw.png";
		} else if ("到达".equals(status)) {
			return url + "newfenxiang_dd.png";
		} else if ("取消".equals(status)) {
			return url + "fenxiang_qx.png";
		} else if ("备降".equals(status)) {
			return url + "fenxiang_bj.png";
		} else if ("返航".equals(status)) {
			return url + "fenxiang_fh.png";
		}

		return "";
	}
	
	
	/**
	 * 加密校验码
	 * 
	 * @param str
	 * @return
	 */
	public static String softDog(String str) {
		String key = "&*^%@%$^#&";
		String md5 = MD5Util.encode(str + key);
		// 5,2,17,10,20,31,29,23 取值位数
		return md5.charAt(4) + "" + md5.charAt(1) + "" + md5.charAt(16) + "" + md5.charAt(9) + "" + md5.charAt(19) + "" + md5.charAt(30) + "" + md5.charAt(28) + "" + md5.charAt(22);
	}
	
	
	/*
	 * 根据飞友提供的中文天气字符匹配天气代码 0晴 1多云 2阴 3雾 4小雨 5中雨 6大雨 7雷阵雨 8雨夹雪 9小雪 10中雪 11大雪
	 * 
	 */
	public static String getWeatherClientByZh(String wea) {
		String res = "1";
		int flag = 0;
		if (wea == null) {
			return "";
		}
		if (wea.indexOf("转") != -1) {
			String[] str = wea.split("转");
			if (Pattern.matches("[^x00-xff]*小雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*小雨[^x00-xff]*", str[1])) {
				return "4";
			} else if (Pattern.matches("[^x00-xff]*中雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*中雨[^x00-xff]*", str[1])
					|| Pattern.matches("[^x00-xff]*大雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*大雨[^x00-xff]*", str[1]))

			{
				return "5";
			}
		} else if (wea.indexOf("到") != -1) {
			String[] str = wea.split("到");
			if (Pattern.matches("[^x00-xff]*小雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*小雨[^x00-xff]*", str[1])) {
				return "4";
			} else if (Pattern.matches("[^x00-xff]*中雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*中雨[^x00-xff]*", str[1])
					|| Pattern.matches("[^x00-xff]*大雨[^x00-xff]*", str[0])
					|| Pattern.matches("[^x00-xff]*大雨[^x00-xff]*", str[1]))

			{
				return "5";
			}
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("[^x00-xff]*云[^x00-xff]*", "1");
		map.put("[^x00-xff]*小雨[^x00-xff]*", "4");
		map.put("[^x00-xff]*中雨[^x00-xff]*", "5");
		map.put("[^x00-xff]*大雨[^x00-xff]*", "6");
		map.put("[^x00-xff]*暴雨[^x00-xff]*", "6");
		map.put("[^x00-xff]*暴雪[^x00-xff]*", "11");
		map.put("[^x00-xff]*雷阵雨[^x00-xff]*", "7");
		map.put("[^x00-xff]*雷雨[^x00-xff]*", "7");
		map.put("[^x00-xff]*雨夹雪[^x00-xff]*", "8");
		map.put("[^x00-xff]*小雪[^x00-xff]*", "9");
		map.put("[^x00-xff]*中雪[^x00-xff]*", "10");
		map.put("[^x00-xff]*大雪[^x00-xff]*", "11");
		map.put("[^x00-xff]*阴[^x00-xff]*", "2");
		map.put("[^x00-xff]*雾[^x00-xff]*", "3");
		map.put("[^x00-xff]*霾[^x00-xff]*", "3");
		map.put("[^x00-xff]*冰雹[^x00-xff]*", "8");
		map.put("[^x00-xff]*冻雨[^x00-xff]*", "8");
		map.put("[^x00-xff]*风[^x00-xff]*", "1");

		for (Map.Entry<String, String> m : map.entrySet()) {

			boolean b = Pattern.matches(m.getKey(), wea);
			if (b) {
				res = map.get(m.getKey());
				flag = 1;
				break;
			}
		}
		if (flag == 0) {

			HashMap<String, String> mapkey = new HashMap<String, String>();
			mapkey.put("[^x00-xff]*晴[^x00-xff]*", "0");
			mapkey.put("[^x00-xff]*雷[^x00-xff]*", "7");
			mapkey.put("[^x00-xff]*雨[^x00-xff]*", "5");
			mapkey.put("[^x00-xff]*雪[^x00-xff]*", "10");
			mapkey.put("[^x00-xff]*沙[^x00-xff]*", "2");
			mapkey.put("[^x00-xff]*尘[^x00-xff]*", "2");
			for (Map.Entry<String, String> n : mapkey.entrySet()) {
				boolean a = Pattern.matches(n.getKey(), wea);
				if (a) {
					res = mapkey.get(n.getKey());
					break;
				}
			}
			return res;
		}

		return res;
	}
	
	
}
