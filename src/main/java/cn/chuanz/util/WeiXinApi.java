package cn.chuanz.util;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.github.chuanzh.util.FuncHttp;
import com.github.chuanzh.util.FuncStatic;

import cn.chuanz.bean.MPSignBean;
import cn.chuanz.bean.TemplateBean;
import net.sf.json.JSONObject;

public class WeiXinApi extends MPApi {
	
	private static Logger logger = Logger.getLogger(WeiXinApi.class);

	private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	/**
	 * 获取token
	 */
	@Override
	public String token() {
		try {
			String tokenKey = ConfigRead.readValue("mpqq_token");
			//String token = null;
			String token = RedisClient.get(tokenKey);
			if (token != null) {
				return token;
			}
			String url = String.format(TOKEN_URL,ConfigRead.readValue("appid"),ConfigRead.readValue("appsecret"));
			String result =  FuncHttp.httpsGet(url);
			JSONObject jo = JSONObject.fromObject(result);
			token = jo.getString("access_token");
			logger.info("token: "+token);
			RedisClient.set(tokenKey, token);
			RedisClient.expire(tokenKey, 7000);
			return token;
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			return null;
		}
	}
	
	
	private static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	/**
	 * 获取token
	 * @return
	 */
	@Override
	public String jsapi_ticket(String token) {
		try {
			String url = String.format(TICKET_URL,token);
			String result =  FuncHttp.httpGet(url);
			JSONObject jo = JSONObject.fromObject(result);
			String ticket = jo.getString("ticket");
			return ticket;
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			return null;
		}
	}

	@Override
	public boolean sendMessage(TemplateBean template) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSnsapiBaseUrl(String redirectUri) {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConfigRead.readValue("appid")+"&redirect_uri="+redirectUri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	}

	private static String GET_OPEN_ID_BY_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	/**
	 * 根据CODE获取openId
	 * @param code
	 * @return
	 */
	@Override
	public String getOpenIdByCode(String code) {
		// TODO Auto-generated method stub
		try {
			String result = FuncHttp.httpGet(String.format(GET_OPEN_ID_BY_CODE_URL, ConfigRead.readValue("appid"),ConfigRead.readValue("appsecret"),code));
			if (result != null) {
				return JSONObject.fromObject(result).getString("openid");
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
		return null;
	}

	@Override
	public MPSignBean signature(String... params) {
		long timestamp = System.currentTimeMillis()/1000;
		String token = token();
		String nonceStr = genRandomStr();
		String paramStr = "jsapi_ticket="+jsapi_ticket(token)+"&noncestr="+nonceStr+"&timestamp="+timestamp;
		for (int index=0;index<params.length;index+=2) {
			paramStr += "&"+params[index]+"="+params[index+1];
		}
		String signature = DigestUtils.shaHex(paramStr);
		MPSignBean signBean = new MPSignBean();
		signBean.setAppId(ConfigRead.readValue("appid"));
		signBean.setNonceStr(nonceStr);
		signBean.setTimestamp(timestamp);
		signBean.setSignature(signature);
		return signBean;
	}

	  /**
	   * 随机生成16位字符串
	   *
	   * @return
	   */
	  private static String genRandomStr() {
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < 16; i++) {
	      int number = random.nextInt(base.length());
	      sb.append(base.charAt(number));
	    }
	    return sb.toString();
	  }
	  
	  public static void main(String[] args) {
		try {
			System.out.println(MPApi.init().token());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
