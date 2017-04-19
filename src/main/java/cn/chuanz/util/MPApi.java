package cn.chuanz.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import cn.chuanz.bean.MPSignBean;
import cn.chuanz.bean.TemplateBean;

public abstract class MPApi {
	
	private static MPApi mpApi = null; 
	
	protected MPApi() {};
	
	public static MPApi init () {
		if (mpApi == null) {
			if ("qq".equals(ConfigRead.readValue("mp_flag"))) {
				mpApi = new QQApi();
			} else {
				mpApi = new WeiXinApi();
			}
			
		}
		return mpApi;
	}
	
	public static void main(String[] args) {
		System.out.println(MPApi.init().token());
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
	 * 根据字符串做签名
	 * @param str
	 * @return
	 */
	public abstract MPSignBean signature(String... params);
	
	
	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param token
	 * @return
	 */
	public static boolean checksignautre(String signature,String timestamp,String nonce,String token){
		String[] str = { token, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
	    String key = DigestUtils.shaHex(str[0] + str[1] + str[2]);
		if (key.equalsIgnoreCase(signature)) {
			return true;
		}
	    
	    return false;
	}
	
	

}
