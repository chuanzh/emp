package cn.chuanz.service.query;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.service.MsgTemplate;
import cn.chuanz.util.Constant;

/**
 * 订阅航班事件
 * @author zhangchuan
 *
 */
public class SubscribeEventQuery extends AbstractQuery {
	
	public SubscribeEventQuery(MsgRequestBean request) {
		super(request);
	}

	@Override
	protected MsgResponseBean query() throws Exception {
		return response;
	}

	@Override
	protected Object noFoundResponse() throws Exception {
		response = new MsgResponseBean();
		response.setFromUserName(request.getToUserName());
		response.setToUserName(request.getFromUserName());
		response.setContent(MsgTemplate.FIRST_SUBSCRIBE);
		return response;
	}

	@Override
	protected boolean match() throws Exception {
		// TODO Auto-generated method stub
		return Constant.EVENT.equals(request.getMsgType()) && Constant.SUBSCRIBE.equals(request.getEvent());
	}

}
