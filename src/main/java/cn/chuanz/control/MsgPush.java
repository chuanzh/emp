package cn.chuanz.control;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncHttp;
import com.github.chuanzh.util.FuncStatic;
import com.github.chuanzh.webframe.annotation.IjHttpServletRequest;

import cn.chuanz.bean.TemplateBean;
import cn.chuanz.util.MPApi;
import net.sf.json.JSONObject;

public class MsgPush {
	@IjHttpServletRequest
	private HttpServletRequest request;
	
	private static Logger logger = Logger.getLogger(MsgPush.class);

	public String dynamic() {
		String resp = "fail";
		try {
			String title = request.getParameter("title");
			String templateid = request.getParameter("templateId");
			String content = request.getParameter("content");
			String user = request.getParameter("openId");
			String info = request.getParameter("info");
			logger.info("消息推送开始-user: " + user + "&title: " + title + "&content: " + content+"&info: "+info+"&templateid: "+templateid);
	
			
			TemplateBean template = createSendMsgTemplate(JSONObject.fromObject(info), title, content,user);
			boolean sendFlag = MPApi.init().sendMessage(template);
			if (sendFlag) {
				resp = "success";
			}
	
			logger.info("消息推送结束-user: "+user+", content: "+content+", status: "+resp);
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
		return resp;
	}
	
	
	private TemplateBean createSendMsgTemplate(JSONObject info, String templateId, String content, String user) {
		TemplateBean template = new TemplateBean();
		template.setTousername(user);
		template.setTemplateid(templateId);
		
		LinkedHashMap<String, HashMap<String,String>> datas = new LinkedHashMap<String, HashMap<String,String>>();
		HashMap<String,String> keynote = null;
		
		keynote = new HashMap<String,String>();
		keynote.put("value", content);
		datas.put("first", keynote);
		
		keynote = new HashMap<String,String>();
		keynote.put("value", info.getString("no"));
		datas.put("keynote1", keynote);
		
		keynote = new HashMap<String,String>();
		keynote.put("value", info.getString("dep"));
		datas.put("keynote2", keynote);
		
		keynote = new HashMap<String,String>();
		keynote.put("value", info.getString("arr"));
		datas.put("keynote3", keynote);
		
		keynote = new HashMap<String,String>();
		keynote.put("type", "view");
		keynote.put("url", "http://127.0.0.1:8080/emp/dynamic.e?method=detail&no=ca1234");
		datas.put("button", keynote);
		
		template.setUrl("http://127.0.0.1:8080/emp/dynamic.e?method=detail&no=ca1234");
		template.setData(datas);
		
		return template;
	}
	
	public static void main(String[] args) throws Exception {
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("title", "模板标题");
		params.put("content", "发送一条推送消息");
		params.put("openId", "27AXXXXXXXXXXXXXXXXXXX735");
		JSONObject jo = new JSONObject();
		jo.put("no", "CA1234");
		jo.put("date", "2017-01-01");
		jo.put("dep", "上海");
		jo.put("arr", "北京");
		params.put("info", jo.toString());
		
		String url = "http://127.0.0.1:8080/emp/msgPush.e?method=dynamic";
		String result = FuncHttp.httpPost(url, params, null);
		System.out.println(result);
		
		
	}
	
	
	
}
