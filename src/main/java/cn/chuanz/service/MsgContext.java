package cn.chuanz.service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncFile;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.util.ConfigRead;
import cn.chuanz.util.Constant;
import cn.chuanz.util.anotation.KeywordQuery;

public class MsgContext {
	
	private static Logger logger = Logger.getLogger(MsgContext.class);
	private static List<Class> queryList = null;
	
	public static void main(String[] args) throws Exception {
		String xml = "<xml><ToUserName><![CDATA[gh_5b867016dad2]]></ToUserName><FromUserName><![CDATA[oxYiaw6OYPKc1kAb7688JB6bsyJQ]]></FromUserName><CreateTime>1492055322</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
		String result = MsgContext.parseMsg(new MsgRequestBean(xml));
		System.out.println(result);
	}

	public static String parseMsg(MsgRequestBean request) throws Exception {
		MsgResponseBean response = null;
		initKeywordQuery(); //初始化关键字查询类
		AbstractQuery query = null;
		for (Class c : queryList) {
			Constructor cons = c.getConstructor(MsgRequestBean.class);
			AbstractQuery q = (AbstractQuery) cons.newInstance(request);
			if (q.match()) {
				query = q;
				break;
			}
		}
		
		if (query != null) {
			Object o = query.process();
			if (o instanceof MsgResponseBean) {
				response = (MsgResponseBean) o;
				return MsgBuilder.createMsg(response);
			}
			return o.toString();
		} else {
			/* 无效查询 */
			response = new MsgResponseBean();
			response.setFromUserName(request.getToUserName());
			response.setToUserName(request.getFromUserName());
			response.setContent(MsgTemplate.INVALID);
			return MsgBuilder.createTextMsg(response);
		}
		
	}
	
	private static void initKeywordQuery() throws Exception {
		if(queryList != null) return;
		queryList = new ArrayList<Class>();
		Set<Class<?>> keywordClass = FuncFile.getClasses(ConfigRead.readValue("keyword_query_package"));
		for (Class c : keywordClass) {
			queryList.add(c);
		}
		
		List<Class> sortList = new ArrayList<Class>();
		List<Class> unSortList = new ArrayList<Class>();
		for (Class c : queryList) {
			if (c.getAnnotation(KeywordQuery.class) != null) {
				KeywordQuery kq = (KeywordQuery) c.getAnnotation(KeywordQuery.class);
				if (!kq.disable()) {
					sortList.add(c);
				}
			} else {
				unSortList.add(c);
			}
		}
		
		/* 按权重排序*/
		Class c = null;
		for (int i=0;i<sortList.size()-1;i++) {
			for (int j=0;j<sortList.size()-i-1;j++) {
				KeywordQuery kq1 = (KeywordQuery) sortList.get(j).getAnnotation(KeywordQuery.class);
				KeywordQuery kq2 = (KeywordQuery) sortList.get(j+1).getAnnotation(KeywordQuery.class);
				if (kq1.weight() > kq2.weight()) {
					c = sortList.get(j);
					sortList.set(j, sortList.get(j+1));
					sortList.set(j+1, c);
				}
			}
		}
		
		queryList.clear();
		queryList.addAll(sortList);
		queryList.addAll(unSortList);
		
	}
	
	
}
