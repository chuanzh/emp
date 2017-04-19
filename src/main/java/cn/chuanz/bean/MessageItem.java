package cn.chuanz.bean;

/**
 * @Description: TODO
 * @author zhangchuan
 */
public class MessageItem {
	
	private String title;
	
	private String description;
	
	private String picUrl;
	
	private String url;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMessageString(){
		String xml = "<item> <Title><![CDATA[{title}]]></Title><Description><![CDATA[{description}]]></Description><PicUrl><![CDATA[{picurl}]]></PicUrl><Url><![CDATA[{url}]]></Url></item>";
		xml = xml.replace("{title}", title==null?"":title);
		xml = xml.replace("{description}", description==null?"":description);
		xml = xml.replace("{picurl}", picUrl==null?"":picUrl);
		xml = xml.replace("{url}", url==null?"":url);
		return xml;
	}
}
