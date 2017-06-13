package cn.chuanz.service.query;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.Constant;

/**
 * 取消订阅事件
 * @author zhangchuan
 *
 */
public class UnSubscribeEventQuery extends AbstractQuery {
	
	public UnSubscribeEventQuery(MsgRequestBean request) {
		super(request);
	}

	@Override
	protected MsgResponseBean query() throws Exception {
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	protected Object noFoundResponse() throws Exception {
		// TODO Auto-generated method stub
		return Constant.SUCCESS;
	}

	@Override
	protected boolean match() throws Exception {
		// TODO Auto-generated method stub
		return Constant.EVENT.equals(request.getMsgType()) && Constant.UNSUBSCRIBE.equals(request.getEvent());
	}

}
