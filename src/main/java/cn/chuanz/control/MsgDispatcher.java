package cn.chuanz.control;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncStatic;
import com.github.chuanzh.webframe.annotation.IjHttpServletRequest;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.service.MsgContext;

/**
 * 消息调度器
 * 接收qq,微信输入的关键字
 * @author zhangchuan
 *
 */
public class MsgDispatcher {

	@IjHttpServletRequest
	private HttpServletRequest request;
	
	private static Logger logger = Logger.getLogger(MsgDispatcher.class);
	
	public String func() throws Exception {
		try {
			ServletInputStream servletInputStream = request.getInputStream();
			StringBuilder contentBuilder = new StringBuilder();
			byte[] buffer = new byte[8192];
			while (servletInputStream.read(buffer) != -1) {
				contentBuilder.append(new String(buffer, "utf-8"));
			}
			String content = contentBuilder.toString().trim();
			logger.info("输入的内容是："+content);
			MsgRequestBean msgRequest = new MsgRequestBean(content);
			String response = MsgContext.parseMsg(msgRequest);
			logger.info("返回的内容是："+response);
			return response;
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			throw e;
		}
	}
	
	/** 初次授权使用*/
//	public String func() throws Exception {
//		String echostr = request.getParameter("echostr");
//		if (MPUtil.checksignautre(request.getParameter("signature"),request.getParameter("timestamp"),
//				request.getParameter("nonce"),request.getParameter("token"))) 
//			return echostr;
//		
//		return "auth fail";
//	}
	
	
}
