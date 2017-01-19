package cn.chuanz.bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MsgRequestBean {

	private String MsgId;//消息id，64位整形
	
	private String ToUserName;//QQ公众号
	private String FromUserName;//发送方帐号（一个OpenID）
	private String CreateTime;//消息创建时间（整型）
	private String MsgType;//text | image | video | voice | location | event
	
	/**text类型返回*/
	private String Content;//文本消息内容
	
	/**image类型返回*/
	private String PicUrl;//图片链接
	
	/**video,voice类型返回*/
	private String MediaId;//图片消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String ThumbMediaId;//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String Format;//语音格式，如amr等
	
	/**location类型返回*/
	private String Location_X;//地理位置维度
	private String Location_Y;//地理位置经度
	private String Scale;//地图缩放大小
	private String Label;//地理位置信息

	/**事件类型*/
	private String Event;//事件类型：subscribe(关注)、unsubscribe(取消关注)、SCAN
	private String EventKey;//事件KEY值，qrscene_为前缀，后面为二维码的参数值 | 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	private String Ticket;//二维码的ticket，可用来换取二维码图片
	
	public MsgRequestBean (String xml) {
		Document document = Jsoup.parse(xml);
		this.setMsgId(document.select("MsgId").text());
		this.setToUserName(document.select("ToUserName").text());
		this.setFromUserName(document.select("FromUserName").text());
		this.setCreateTime(document.select("CreateTime").text());
		this.setMsgType(document.select("MsgType").text());
		this.setContent(document.select("Content").text().toUpperCase());
		this.setEvent(document.select("Event").text());
		this.setEventKey(document.select("EventKey").text());
		this.setTicket(document.select("Ticket").text());
		this.setPicUrl(document.select("PicUrl").text());
		this.setLocation_X(document.select("Location_X").text());
		this.setLocation_Y(document.select("Location_Y").text());
		this.setLabel(document.select("Label").text());;
		this.setMediaId(document.select("MediaId").text());
		this.setThumbMediaId(document.select("ThumbMediaId").text());
		this.setFormat(document.select("Format").text());
	}
	
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
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}

}
