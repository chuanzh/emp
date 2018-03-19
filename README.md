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
1）mpqq_token用来配置token保存在缓存中的key,公众号推荐将从他们服务器上获取的token保存到自己的服务器上，这样不会因为频繁访问而遭到限制，也大大提供了访问速度，公众号的token使用有效期为7200s，mpqq中配置的是6000s，需要先配置Redis缓存服务。  
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
## 关键字查询类使用

  + 方法实现  
   emp框架已经帮你实现了基本的业务分发逻辑，所以在增加一个事件处理类的时候，你只需要在keyword_query_package添加一个类即可，
   比如：需要增加一个航班号查询的类，FlightNumberQuery,只需要继承AbstractQuery类，实现下面3个方法。    
    + match()：用来匹配你的查询规则  
    + query()：处理你的业务逻辑，比如查询数据等，若查询到结果将FindFlag设置为true    
    + noFoundResponse()：若没有查询到符合要求的，返回的结果    
  + 注解使用  
    可以使用@KeywordQuery来配置查询类的权重及value值，权重weight越小，就越会靠前去匹配用户输入的关键字，
    比如：weight=0表示用户输入关键字后，首先会使用FlightNumberQuery类matchKeyword来匹配关键字，这里开发者可以根据自己业务的需求自行配置权重
    value为附加参数，选填参数，disable参数，默认为false,设置为true表示禁用此查询类。当然也可以不使用@KeywordQuery注解，默认是排到末尾.  
  + 请求对象  
    公众号请求格式分很多种，如：text类型，image类型，video,voice类型，location类型，Event事件类型  
    我这里统一封装到对象MsgRequestBean对象里面了，你可以查看MsgRequestBean，获取不同类型的参数。  
  + 响应对象  
    公众号的返回格式一般为存文本格式，或者图文格式（单图文或多图文）  
    返回的数据则需要封装到MsgResponseBean对象里面，若为存文本，只需要设置content内容即可，若为图文，则需要设置articles参数。
    articles为List<MessageItem>集合，对于图文可以设置picUrl(图片地址)，url(链接调整地址)，title(标题)，description(描述)
```Java
@KeywordQuery(weight=0,value={Constant.FLIGHT_NUMBER,Constant.FLIGHT_DATE_AND_NUMBER})
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
	protected boolean match() throws Exception {
		// TODO Auto-generated method stub
		if( FlightUtil.isFlightNumber(request.getContent()) || FlightUtil.isFlightDateAndNumber(request.getContent()))
			return true;
		
		return false;
	}


}
```  
  + 事件处理
    事件处理也相当于查询类，所以也需要继承AbstractQuery，以订阅事件为例，在noFoundResponse设置首次返回的信息。
    这里query()里面没有具体逻辑，但是有时候业务需要，在用户取消关注后再次关注，或者通过二维码扫描关注，需要做些特殊的返回，可以写在这个里面。  
    另外有些事件，如推送消息后，QQ公众号也会发送一条消息过来，此时如果不需要处理，可以直接返回“success”，可参考PushFinishEventQuery类
```Java
public class SubscribeEventQuery extends AbstractQuery {
	
	public SubscribeEventQuery(MsgRequestBean request) {
		super(request);
	}

	@Override
	protected MsgResponseBean query() throws Exception {
		return response;
	}

	@Override
	protected Object noFoundResponse() throws Exception {
		response = new MsgResponseBean();
		response.setFromUserName(request.getToUserName());
		response.setToUserName(request.getFromUserName());
		response.setContent(MsgTemplate.FIRST_SUBSCRIBE);
		return response;
	}

	@Override
	protected boolean match() throws Exception {
		// TODO Auto-generated method stub
		return Constant.EVENT.equals(request.getMsgType()) && Constant.SUBSCRIBE.equals(request.getEvent());
	}

}
```
    
公众号API调用，可使用MPApi.init().xxx()来获取相应api数据，下面列举了所有的方法及用途
```Java
MPApi.init().token() 获取公众号token
MPApi.init().jsapi_ticket() 根据token获取访问的ticket
MPApi.init().getOpenIdByCode(String openId) 根据微信返回的coke获取用户的openId
MPApi.init().sendMessage(TemplateBean template) 根据模板像用户推送消息
```


## 页面数据分享
用户在打开公众号中的链接网页后，可以分享给其他人，如果不配置，公众号默认是使用网页中的第一行文字作为分享，但是我们也可以自定义分享，如：分享的标题，分享的图片，分享的描述，引入对应的分享JS代码，以QQ为例，需要引入：
```Javascript  
	<!-- QQ分享 -->
	<script type="text/javascript" src="https://mp.gtimg.cn/open/js/openApi.js"></script>
	<script type="text/javascript" src="${host}/js/mqq.js"></script>
	<!-- QQ分享 -->
	<!-- 微信分享 -->
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
        <script type="text/javascript" src="${host}/js/mwx.js"></script>
	<!-- 微信分享 -->
```  

mqq.js会调用auth类signUrl方法，获取签名的参数，返回appId，timestamp,nonceStr,signature。jsApiList表示分享的权限，比如qq群，qq用户，qq空间，微信群，微信用户等。  
注意：fenxiang_title，fenxiang_desc，fenxiang_link，fenxiang_img是后端传过来的，可以参考dynamic类里面的逻辑。
```Javascript  
	$.ajax({
        url: "/emp/auth.e",
	type: "POST",
	data: {"method":"signUrl","url": window.location.href},
	async: true,
	dataType: 'json',
        success: function (data) {
        	if (data.code == "1") {
        		mqq.config({
        		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        		    appId: data.appId, // 必填，公众号的唯一标识
        		    timestamp: data.timestamp, // 必填，生成签名的时间戳
        		    nonceStr: data.nonceStr, // 必填，生成签名的随机串
        		    signature: data.signature,// 必填，签名，见附录1
        		    jsApiList: ['onMenuShareQQ','onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQzone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        		});
        	}
        }
	});
		
		
		mqq.ready(function(){
		    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			var t = $("#fenxiang_title").html();
		    var d = $("#fenxiang_desc").html();
		    var l = htmlDecode($("#fenxiang_link").html());
		    var u = $("#fenxiang_img").html();
		    
		    mqq.onMenuShareQQ({
			    title: t, // 分享标题
			    desc: d, // 分享描述
			    link: l, // 分享链接
			    imgUrl: u, // 分享图标
			    success: function () { 
			       // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			       // 用户取消分享后执行的回调函数
			    }
			});
		}
```

## 消息推送  
有时我们需要主动将消息推送给用户，推送消息调用MPApi.init().sendMessage(template)方法  
  + TemplateBean类说明  
     + tousername: 推送用户openId  
     + templateid: 模板ID，这需要在公众后台申请，申请通过后会展示出来  
     + data: HashMap<String,String> 推送的参数，根据模板中的配置一一对应  
     + url: 推送消息的跳转地址  
 发送消息可参考MsgPush类的dynamic方法
 
 ## 第三方授权  
 公众号可以授权第三方页面获取用户信息   
  + 获取信息步骤：  
     + 第一步：根据微信授权地址获取code  
     + 第二步：根据code获取用户信息，生成访问key  
     + 第三步：根据key获取用户信息  
  + 授权方式  
     + 静默授权：以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）  
     + 手动授权：以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。  
     + 关注公众号后都是静默授权  
  + 访问安全  
     + 通过第二步将用户信息以 key=>userinfo保存在redis中  
     + 第三步通过IP限制，防止恶意IP访问  
  获取用户信息时序图  
   ![image](https://raw.githubusercontent.com/chuanzh/emp/master/doc/images/公众号授权第三方获取用户信息.png)   
 
  ## WEB页面反抓取
  + IP限制
  + 输入验证码：当达到一定频率，弹出验证码，正确才能继续  
  + 只允许在公众号客户端中才能访问，必须通过授权跳转，如下，在需要访问的地址前加上授权地址  
     https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect

 

