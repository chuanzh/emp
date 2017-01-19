package cn.chuanz.bean;

/**
 * @Description: TODO
 * @author huoli
 * @date 2013-4-11
 */
public class MessageItem {
	
	private String title;
	
	private String Description;
	
	private String PicUrl;
	
	private String Url;
	
	private String timeoutDesc;//超时描述文字
	
	private String timeoutUrl;//超时链接
	
	public String getTimeoutUrl() {
		return timeoutUrl;
	}

	public void setTimeoutUrl(String timeoutUrl) {
		this.timeoutUrl = timeoutUrl;
	}

	public String getTimeoutDesc() {
		return timeoutDesc;
	}

	public void setTimeoutDesc(String timeoutDesc) {
		this.timeoutDesc = timeoutDesc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
	public String getMessageString(){
		String xml = "<item> <Title><![CDATA[{title}]]></Title><Description><![CDATA[{description}]]></Description><PicUrl><![CDATA[{picurl}]]></PicUrl><Url><![CDATA[{url}]]></Url></item>";
		xml = xml.replace("{title}", title==null?"":title);
		xml = xml.replace("{description}", Description==null?"":Description);
		xml = xml.replace("{picurl}", PicUrl==null?"":PicUrl);
		xml = xml.replace("{url}", Url==null?"":Url);
		return xml;
	}
}
