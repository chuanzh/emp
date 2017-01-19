package cn.chuanz.service.query;

import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.Constant;
import cn.chuanz.util.anotation.KeywordQuery;

@KeywordQuery({Constant.FLIGHT_NUMBER, Constant.FLIGHT_DATE_AND_NUMBER})
public class FlightNumberQuery extends AbstractQuery {

	@Override
	protected MsgResponseBean query() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String messageBuilder() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String noFindResponse() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
