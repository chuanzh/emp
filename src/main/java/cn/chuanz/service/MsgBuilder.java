package cn.chuanz.service;

import java.util.List;

import cn.chuanz.bean.MessageItem;
import cn.chuanz.bean.MsgResponseBean;

public class MsgBuilder {

	/**
	 * 创建文本回复信息
	 * @param response
	 * @return
	 */
	public static String createTextMsg(MsgResponseBean response) {
		String xml = "<xml><ToUserName><![CDATA["+response.getToUserName()+"]]>"
				+ "</ToUserName><FromUserName><![CDATA["+response.getFromUserName()+"]]></FromUserName>"
						+ "<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime><MsgType><![CDATA[text]]></MsgType>"
								+ "<Content><![CDATA["+response.getContent()+"]]></Content></xml>";
		return xml;
	}
	
	/**
	 * 创建图文回复信息
	 * @param response
	 * @return
	 */
	public static String createTextPicMsg(MsgResponseBean response) {
		List<MessageItem> articles = response.getArticles();
		StringBuilder xml = new StringBuilder();
		xml.append("<xml><ToUserName><![CDATA["+response.getToUserName()+"]]></ToUserName><FromUserName><![CDATA["+response.getFromUserName()+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>"+articles.size()+"</ArticleCount><Articles>");
		for (MessageItem item : articles) {
			xml.append("<item>");
			xml.append("<Title><![CDATA[").append(item.getTitle()).append("]]></Title>");
			xml.append("<Description><![CDATA[").append(item.getDescription()).append("]]></Description>");
			xml.append("<PicUrl><![CDATA[").append(item.getPicUrl()).append("]]></PicUrl>");
			xml.append("<Url><![CDATA[").append(item.getUrl()).append("]]></Url>");
			xml.append("</item>");
		}
		xml.append("</Articles></xml>");
		return xml.toString();
	}
	
}
