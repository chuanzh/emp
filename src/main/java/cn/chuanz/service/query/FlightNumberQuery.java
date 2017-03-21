package cn.chuanz.service.query;

import java.util.ArrayList;
import java.util.List;

import com.github.chuanzh.util.FuncDate;

import cn.chuanz.bean.MessageItem;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.service.MsgBuilder;
import cn.chuanz.service.MsgTemplate;
import cn.chuanz.util.FlightUtil;

public class FlightNumberQuery extends AbstractQuery {
	
	private String flightNo;
	private String flightDate;

	@Override
	protected MsgResponseBean query() throws Exception {
		flightNo = request.getContent();
		flightDate = "2017-01-01";
		List<MessageItem> articles = new ArrayList<MessageItem>();
		/**
		 * 业务处理
		 */
		
		if (articles.size() > 0) {//查询到结果，设置findFlag=true
			this.response.setFindFlag(true);
		} 
		this.response.setArticles(articles);
		return response;
	}

	@Override
	protected String builderMessage() throws Exception {
		return MsgBuilder.createTextPicMsg(response);
	}

	@Override
	protected String noFoundResponse() throws Exception {
		String content = MsgTemplate.NO_FIND_FLIGHT_BY_NO;
		content = content.replace(MsgTemplate.DATE_WILDCARD, FuncDate.getNowDate())
				.replace(MsgTemplate.FLIGHT_WILDCARD, flightNo)
				.replace(MsgTemplate.TOMORROW_WILDCARD, FuncDate.AddDay(FuncDate.getNowDate(), 1));
		response.setContent(content);
		return MsgBuilder.createTextMsg(response);
	}

	@Override
	protected boolean matchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		if( FlightUtil.isFlightNumber(keyword) || FlightUtil.isFlightDateAndNumber(keyword))
			return true;
		
		return false;
	}


}