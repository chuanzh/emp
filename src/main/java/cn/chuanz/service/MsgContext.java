package cn.chuanz.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.ConfigRead;
import com.github.chuanzh.util.FuncFile;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.util.Constant;
import cn.chuanz.util.FlightUtil;
import cn.chuanz.util.anotation.KeywordQuery;

public class MsgContext {
	
	private static Logger logger = Logger.getLogger(MsgContext.class);
	private static HashMap<Integer, Class> keywordMap = null;
	
	
	private static void initKeywordQuery() throws Exception {
		if(keywordMap != null) return;
		keywordMap = new HashMap<Integer, Class>();
		Set<Class<?>> keywordClass = FuncFile.getClasses(ConfigRead.readValue("keyword_query_package"));
		for (Class c : keywordClass) {
			KeywordQuery kwQ = (KeywordQuery) c.getAnnotation(KeywordQuery.class);
			if (kwQ != null) {
				for (int type : kwQ.value()) {
					keywordMap.put(type, c);
				}
			}
		}
	}

	public static String parseMsg(MsgRequestBean request) throws Exception {
		initKeywordQuery(); //初始化关键字查询类
		MsgResponseBean response = null;
		if (Constant.EVENT.equals(request.getMsgType())) {
			if (Constant.SUBSCRIBE.equals(request.getEvent())) {
				//订阅
				response = new MsgResponseBean();
				response.setFromUserName(request.getToUserName());
				response.setToUserName(request.getFromUserName());
				response.setContent(MsgTemplate.FIRST_SUBSCRIBE);
				return MsgBuilder.createTextMsg(response);
			} else if (Constant.UNSUBSCRIBE.equals(request.getEvent())) {
				//取消订阅
			}
		}else if (Constant.TEXT.equals(request.getMsgType())) {
			int inputType = checkKeyword(request.getContent());
			if (keywordMap.containsKey(inputType)) {
				Constructor cons = keywordMap.get(inputType).getConstructor(MsgRequestBean.class);
				AbstractQuery query = (AbstractQuery) cons.newInstance(request);
				return query.process();
			} else {
				/* 无效查询 */
				response = new MsgResponseBean();
				response.setFromUserName(request.getToUserName());
				response.setToUserName(request.getFromUserName());
				response.setContent(MsgTemplate.INVALID);
				return MsgBuilder.createTextMsg(response);
			}
			
		}
		
		return null;
	}
	
	private static int checkKeyword(String keyword) {
		if (FlightUtil.isFlightNumber(keyword)) {
			return Constant.FLIGHT_NUMBER;
		} else if (FlightUtil.isFlightDateAndNumber(keyword)) {
			return Constant.FLIGHT_DATE_AND_NUMBER;
		} else if (FlightUtil.isDepAndArr(keyword)) {
			return Constant.FLIGHT_DEP_AND_ARR;
		} else if (FlightUtil.isAirport(keyword)) {
			return Constant.AIRPORT;
		}
		
		return Constant.INVALID;
	}
	
	
	
	
}
