package cn.chuanz.service;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.ConfigRead;
import com.github.chuanzh.util.FuncFile;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.util.Constant;

public class MsgContext {
	
	private static Logger logger = Logger.getLogger(MsgContext.class);
	private static Set<Class> querySet = null;

	public static String parseMsg(MsgRequestBean request) throws Exception {
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
			initKeywordQuery(); //初始化关键字查询类
			AbstractQuery query = null;
			for (Class c : querySet) {
				Constructor cons = c.getConstructor(MsgRequestBean.class);
				AbstractQuery q = (AbstractQuery) cons.newInstance(request);
				if (q.matchKeyword(request.getContent())) {
					query = q;
					break;
				}
			}
			
			if (query != null) {
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
	
	
	private static void initKeywordQuery() throws Exception {
		if(querySet != null) return;
		querySet = new HashSet<Class>();
		Set<Class<?>> keywordClass = FuncFile.getClasses(ConfigRead.readValue("keyword_query_package"));
		for (Class c : keywordClass) {
			querySet.add(c);
		}
	}
	
	
}
