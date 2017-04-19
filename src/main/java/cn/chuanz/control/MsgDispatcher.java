package cn.chuanz.control;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncStatic;
import com.github.chuanzh.webframe.annotation.IjHttpServletRequest;

import cn.chuanz.bean.MsgRequestBean;
import cn.chuanz.service.MsgContext;
import cn.chuanz.util.ConfigRead;
import cn.chuanz.util.MPApi;

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
	
//	/** 初次授权使用*/
//	public String func() throws Exception {
//		String echostr = request.getParameter("echostr");
//		logger.info("echostr: "+request.getParameter("echostr")+",signature: "+request.getParameter("signature")+",timestamp: "+request.getParameter("timestamp")+",nonce: "+request.getParameter("nonce"));
//		if (MPApi.checksignautre(request.getParameter("signature"),request.getParameter("timestamp"),
//				request.getParameter("nonce"),ConfigRead.readValue("token"))) 
//			return echostr;
//		
//		return "auth fail";
//	}
	
	
}
