package cn.chuanz.service.query;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.FlightUtil;

public class FlightDepAndArrQuery extends AbstractQuery {
	
	public FlightDepAndArrQuery (MsgRequestBean request) {
		super(request);
	}

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
	protected boolean match() throws Exception {
		// TODO Auto-generated method stub
		return FlightUtil.isDepAndArr(request.getContent());
	}

}
