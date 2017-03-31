package cn.chuanz.service.query;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;
import cn.chuanz.service.AbstractQuery;
import cn.chuanz.util.Constant;

/**
 * 推送消息成功事件
 * @author zhangchuan
 *
 */
public class PushFinishEventQuery extends AbstractQuery {
	
	public PushFinishEventQuery(MsgRequestBean request) {
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
	protected boolean matchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return Constant.EVENT.equals(request.getMsgType()) && Constant.TEMPLATESENDJOBFINISH.equals(request.getEvent());
	}

}
