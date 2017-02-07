package cn.chuanz.service.query;

import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.FlightUtil;

public class FlightDepAndArrQuery extends AbstractQuery {

	@Override
	protected MsgResponseBean query() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String builderMessage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String noFoundResponse() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean matchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return FlightUtil.isDepAndArr(keyword);
	}

}
