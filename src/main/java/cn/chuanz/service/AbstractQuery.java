package cn.chuanz.service;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.bean.MsgResponseBean;

public abstract class AbstractQuery {
	
	protected MsgRequestBean request;
	protected MsgResponseBean response;
	
	public AbstractQuery() {}
	
	public AbstractQuery (MsgRequestBean request) {
		this.request = request;
		if (request != null) {
			response = new MsgResponseBean();
			response.setFromUserName(request.getToUserName());
			response.setToUserName(request.getFromUserName());
		}
	}
	
	protected Object process() throws Exception {
		this.response = this.query();
		if (this.response.isFind()) {
			return this.response;
		}
		return this.noFoundResponse();
	}

	/** 当查询到结果，需要将findFlag设置为true*/
	protected abstract MsgResponseBean query() throws Exception;
		
	/** 配置未查询到数据的返回结果*/
	protected abstract Object noFoundResponse() throws Exception;
	
	/** 关键字匹配，若符合则处理*/
	protected abstract boolean matchKeyword(String keyword) throws Exception;
	
}
