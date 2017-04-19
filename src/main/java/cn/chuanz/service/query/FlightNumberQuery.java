package cn.chuanz.service.query;

import com.github.chuanzh.util.FuncDate;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.service.MsgTemplate;
import cn.chuanz.util.Constant;
import cn.chuanz.util.FlightUtil;
import cn.chuanz.util.anotation.KeywordQuery;
@KeywordQuery(weight=0,value={Constant.FLIGHT_NUMBER,Constant.FLIGHT_DATE_AND_NUMBER})
public class FlightNumberQuery extends AbstractQuery {
	
	private String flightNo;
	private String flightDate;
	
	public FlightNumberQuery() {}
	public FlightNumberQuery(MsgRequestBean request) {
		super(request);
	}

	@Override
	protected MsgResponseBean query() throws Exception {
		flightNo = request.getContent();
		flightDate = "2017-01-01";
		/**
		 * 业务处理
		 */
		
		//查询到结果，设置findFlag=true
		this.response.setFindFlag(true);
		return response;
	}

	@Override
	protected MsgResponseBean noFoundResponse() throws Exception {
		String content = MsgTemplate.NO_FIND_FLIGHT_BY_NO;
		content = content.replace(MsgTemplate.DATE_WILDCARD, FuncDate.getNowDate())
				.replace(MsgTemplate.FLIGHT_WILDCARD, flightNo)
				.replace(MsgTemplate.TOMORROW_WILDCARD, FuncDate.AddDay(FuncDate.getNowDate(), 1));
		response.setContent(content);
		return response;
	}

	@Override
	protected boolean matchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		if( FlightUtil.isFlightNumber(keyword) || FlightUtil.isFlightDateAndNumber(keyword))
			return true;
		
		return false;
	}


}
