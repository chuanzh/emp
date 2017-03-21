package cn.chuanz.bean;

import java.util.HashMap;

/**
 * @Description: 发送消息对象
 * @author chuan.zhang
 * @date 2013-7-9
 */
public class TemplateBean {

	/**根据OpenID发送消息*/
	private String tousername;

	/** 指定消息模板的id（模板通过审核之后可获得）*/
	private String templateid;

	/** 指定传入消息模板的模板参数内容，每个模板参数包含两部分内容，变量名:{ 变量值 } */
	private HashMap<String, HashMap<String,String>> data;
	
	/** 点击事件上报的关键字或跳转的url*/
	private String url;

	public String getTousername() {
		return tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public HashMap<String, HashMap<String, String>> getData() {
		return data;
	}

	public void setData(HashMap<String, HashMap<String, String>> data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
