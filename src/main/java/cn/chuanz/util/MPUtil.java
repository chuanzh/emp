package cn.chuanz.util;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class MPUtil {
	
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
