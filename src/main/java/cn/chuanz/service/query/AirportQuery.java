package cn.chuanz.service.query;

import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.FlightUtil;

public class AirportQuery extends AbstractQuery {

	@Override
	protected MsgResponseBean query() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MsgResponseBean noFoundResponse() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean matchKeyword(String keyword) throws Exception {
		return FlightUtil.isAirport(keyword);
	}

}
