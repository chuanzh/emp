package cn.chuanz.util;

import org.apache.commons.codec.digest.DigestUtils;

import cn.chuanz.bean.TemplateBean;

public abstract class MPUtil {
	
	private static MPUtil mpUtil = null; 
	
	protected MPUtil() {};
	
	public static MPUtil init () {
		if (mpUtil == null) {
			if ("qq".equals(ConfigRead.readValue("mp_flag"))) {
				mpUtil = new QQUtil();
			} else {
				mpUtil = new WeiXinUtil();
			}
			
		}
		return mpUtil;
	}
	
	public static void main(String[] args) {
		System.out.println(MPUtil.init().token());
	}
	
	
	/**
	 * 获取token
	 * @return
	 */
	public abstract String token();
	
	/**
	 * 获取JS ticket
	 * @return
	 */
	public abstract String jsapi_ticket(String token);
	
	/**
	 * 发送消息
	 * @param template
	 * @return
	 */
	public abstract boolean sendMessage(TemplateBean template);
	
	/**
	 * 获取授权地址
	 * @param redirectUri
	 * @return
	 */
	public abstract String getSnsapiBaseUrl(String redirectUri);
	
	/**
	 * 根据CODE获取openId
	 * @param code
	 * @return
	 */
	public abstract String getOpenIdByCode(String code);
	
	
	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param token
	 * @return
	 */
	public static boolean checksignautre(String signature,String timestamp,String nonce,String token){
	    String key = DigestUtils.shaHex(token + timestamp + nonce);
		if (key.equalsIgnoreCase(signature)) {
			return true;
		}
	    
	    return false;
	}
	
	

}
