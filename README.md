# emp
公众号框架，包括微信，QQ公众号
# 项目架构
![image](https://raw.githubusercontent.com/chuanzh/emp/master/doc/images/emp.png) 

# 配置说明
引用web开发包，emp项目需要引入web相关开发包，这里使用我原来的eweb框架，使用起来很方便，通过maven简单配置即可
```xml
    <dependency>
        <groupId>com.github.chuanzh</groupId>
        <artifactId>eweb</artifactId>
        <version>1.0.1</version>
    </dependency>
```
公众号配置（cfg.properties）  
1）使用缓存了从公众号获取的token，每次从公众号服务器获取token后，使用有效期为7200秒，所以你不必要每次使用都去调用公众号的接口（他们也推荐这样做），
所以通过保存到缓存中后，下次就可以使用“MP_TOKEN”来获取token了，需要先配置Redis缓存服务。  
2）mp_flag参数用来配置是qq公众号还是微信公众号  
3）keyword_query_package：你的关键字查询类的目录  
```xml
#------------公账号账号配置-----------------#
appid=你的appid
appsecret=你的appsecret
#------------公账号账号配置-----------------#
#用于存放在缓存中的token值
mpqq_token=MP_TOKEN
#公账号标识，qq或weixin
mp_flag=qq
#关键字查询类包路径
keyword_query_package=com.chuanz.service.query
```

# 使用说明
emp框架已经帮你实现了基本的业务分发逻辑，所以在增加一个事件处理类的时候，你只需要在keyword_query_package添加一个类即可，
比如：需要增加一个航班号查询的类，FlightNumberQuery,只需要继承AbstractQuery类，实现下面3个方法。
matchKeyword()：用来匹配你的关键字
query()：处理你的业务逻辑，比如查询数据等，若查询到结果将FindFlag设置为true
noFoundResponse()：若没有查询到符合要求的，返回的结果
```Java
public class FlightNumberQuery extends AbstractQuery {
	
	private String flightNo;
	private String flightDate;

	@Override
	protected MsgResponseBean query() throws Exception {
		flightNo = request.getContent();
		flightDate = "2017-01-01";
		/**
		 * 业务处理
		 */
		
		//查询到结果，设置findFlag=true
		this.response.setFindFlag(true);
		return response;
	}

	@Override
	protected MsgResponseBean noFoundResponse() throws Exception {
		String content = MsgTemplate.NO_FIND_FLIGHT_BY_NO;
		content = content.replace(MsgTemplate.DATE_WILDCARD, FuncDate.getNowDate())
				.replace(MsgTemplate.FLIGHT_WILDCARD, flightNo)
				.replace(MsgTemplate.TOMORROW_WILDCARD, FuncDate.AddDay(FuncDate.getNowDate(), 1));
		response.setContent(content);
		return response;
	}

	@Override
	protected boolean matchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		if( FlightUtil.isFlightNumber(keyword) || FlightUtil.isFlightDateAndNumber(keyword))
			return true;
		
		return false;
	}


}
```
公众号API调用，可使用MPApi.init().xxx()来获取相应api数据，下面列举了所有的方法及用途
```Java
MPApi.init().token() 获取公众号token
MPApi.init().jsapi_ticket() 根据token获取访问的ticket
MPApi.init().getOpenIdByCode() 根据微信返回的coke获取用户的openId
sendMessage() 根据模板像用户推送消息
```


