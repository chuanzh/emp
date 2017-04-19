<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>   
<title>详细页面</title>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript" src="${host}/js/jquery-1.8.3.min.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
    <div>
    	<p>这是详细页面</p>
    	<span>航班号：${no}</span>
    	<span>起飞地：${dep}</span>
    	<span>降落地：${arr}</span>
	</div>
	
	<div style="display:none;">
		<div id="fenxiang_title">${fenxiang_title !}</div>
		<div id="fenxiang_img">${fenxiang_img !}</div>
		<div id="fenxiang_desc">${fenxiang_desc !}</div>
		<div id="fenxiang_link">${fenxiang_link !}</div>
	</div>
	
	<!-- QQ分享
	<script type="text/javascript" src="https://mp.gtimg.cn/open/js/openApi.js"></script>
	<script type="text/javascript" src="${host}/js/mqq.js"></script>
	 -->
	
	<!-- 微信分享 -->
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="${host}/js/mwx.js"></script>
	<!-- 微信分享 -->
	
</body>
</html>