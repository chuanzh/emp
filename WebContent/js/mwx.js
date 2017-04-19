	$.ajax({
		url: "/emp/auth.e",
        type: "POST",
        data: {"method":"signUrl","url": window.location.href},
        async: true,
        dataType: 'json',
        success: function (data) {
        	if (data.code == "1") {
        		wx.config({
        		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        		    appId: data.appId, // 必填，公众号的唯一标识
        		    timestamp: data.timestamp, // 必填，生成签名的时间戳
        		    nonceStr: data.nonceStr, // 必填，生成签名的随机串
        		    signature: data.signature,// 必填，签名，见附录1
        		    jsApiList: ['checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems', 'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'translateVoice', 'startRecord', 'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'getNetworkType', 'openLocation', 'getLocation', 'hideOptionMenu', 'showOptionMenu', 'closeWindow', 'scanQRCode', 'chooseWXPay', 'openProductSpecificView', 'addCard', 'chooseCard', 'openCard']
        		});
        		
        		wx.ready(function(){
        		    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        			var t = $("#fenxiang_title").html();
        		    var d = $("#fenxiang_desc").html();
        		    var l = htmlDecode($("#fenxiang_link").html());
        		    var u = $("#fenxiang_img").html();
        		    
        		    wx.onMenuShareAppMessage({
        		        title: t, // 分享标题
        		        desc: d, // 分享描述
        		        link: l, // 分享链接
        		        imgUrl: u, // 分享图标
        		        type: '', // 分享类型,music、video或link，不填默认为link
        		        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        		        success: function () { 
        		            // 用户确认分享后执行的回调函数
        		        	alert("onMenuShareAppMessage success");
        		        },
        		        cancel: function () { 
        		            // 用户取消分享后执行的回调函数
        		        }
        		    });
        		    
//        		    wx.onMenuShareQQ({
//        			    title: t, // 分享标题
//        			    desc: d, // 分享描述
//        			    link: l, // 分享链接
//        			    imgUrl: u, // 分享图标
//        			    success: function () { 
//        			       // 用户确认分享后执行的回调函数
//        			    },
//        			    cancel: function () { 
//        			       // 用户取消分享后执行的回调函数
//        			    }
//        			});
//
//        		    
//        			wx.onMenuShareAppMessage({
//        			    title: t, // 分享标题
//        			    desc: d, // 分享描述
//        			    link: l, // 分享链接
//        			    imgUrl: u, // 分享图标
//        			    type: '', // 分享类型,music、video或link，不填默认为link
//        			    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
//        			    success: function () { 
//        			        // 用户确认分享后执行的回调函数
//        			    },
//        			    cancel: function () { 
//        			        // 用户取消分享后执行的回调函数
//        			    }
//        			});
//        			
//        			wx.onMenuShareTimeline({
//        			    title: t, // 分享标题
//        			    link: l, // 分享链接
//        			    imgUrl: u, // 分享图标
//        			    success: function () { 
//        			        // 用户确认分享后执行的回调函数
//        			    },
//        			    cancel: function () { 
//        			        // 用户取消分享后执行的回调函数
//        			    }
//        			});
//        			
//        			wx.onMenuShareQzone({
//        			    title: t, // 分享标题
//        			    desc: d, // 分享描述
//        			    link: l, // 分享链接
//        			    imgUrl: u, // 分享图标
//        			    success: function () { 
//        			       // 用户确认分享后执行的回调函数
//        			    },
//        			    cancel: function () { 
//        			        // 用户取消分享后执行的回调函数
//        			    }
//        			});

        			
        		});
        		
        		wx.error(function(res){
        		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        			alert(res);
        		});
        		
        	}
        }
	});
		
		
		function htmlDecode(e) {
		    return e.replace(/&#39;/g, "'").replace(/<br\s*(\/)?\s*>/g, "\n").replace(/&nbsp;/g, " ").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, '"').replace(/&amp;/g, "&")
		}