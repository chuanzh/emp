package cn.chuanz.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncStatic;
import com.github.chuanzh.webframe.annotation.IjHttpServletRequest;

import cn.chuanz.bean.MPSignBean;
import cn.chuanz.util.MPApi;
import net.sf.json.JSONObject;

public class Auth {
	
	@IjHttpServletRequest
	private HttpServletRequest request;
	private static Logger logger = Logger.getLogger(Auth.class);

	public String token() {
		return MPApi.init().token();
	}
	
	public String signUrl() {
		JSONObject jo = new JSONObject();
		jo.put("code", "1");
		try {
			String url = request.getParameter("url");
			MPSignBean signBean = MPApi.init().signature("url",url.split("#")[0]);
			
			jo.put("appId", signBean.getAppId());
			jo.put("timestamp", signBean.getTimestamp());
			jo.put("nonceStr", signBean.getNonceStr());
			jo.put("signature", signBean.getSignature());
			logger.info("signUrl: "+url+"/"+jo.toString());
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			jo.put("code", "0");
		}
		
		return jo.toString();
	}
	
}
