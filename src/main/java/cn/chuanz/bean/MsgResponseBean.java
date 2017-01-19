package cn.chuanz.bean;

import java.util.List;

public class MsgResponseBean {

	private String MsgId;//消息id，64位整形
	private String ToUserName;//QQ公众号
	private String FromUserName;//发送方帐号（一个OpenID）
	private String CreateTime;//消息创建时间（整型）
	private String MsgType;//text | news 
	
	/**text类型返回*/
	private String Content;//文本消息内容
	
	/**news类型返回*/
	private List<MessageItem> articles; //图文消息内容
	
	private boolean findFlag = false; //是否找到结果

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public List<MessageItem> getArticles() {
		return articles;
	}

	public void setArticles(List<MessageItem> articles) {
		this.articles = articles;
	}

	public boolean isFind() {
		return findFlag;
	}

	public void setFindFlag(boolean findFlag) {
		this.findFlag = findFlag;
	}
	
	
}
