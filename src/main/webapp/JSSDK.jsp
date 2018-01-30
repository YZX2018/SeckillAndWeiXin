<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>微信jsSdk</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<body>
慕课
<button onclick="show()">相机</button>
<button onclick="scan()">扫一扫</button>
	<script type="text/javascript">
	var appId = '${appId}';
	var timestamp = '${timestamp}';
	var nonceStr = '${nonceStr}';
	var signature = '${signature}';
	
	wx.config({
	    /* debug: true, */ // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: appId, // 必填，公众号的唯一标识
	    timestamp: timestamp, // 必填，生成签名的时间戳
	    nonceStr: nonceStr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline','chooseImage','scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	

	
	wx.ready(function(){
		//朋友圈
		wx.onMenuShareTimeline({
		    title: '朋友圈', // 分享标题
		    link: 'http://sunweixin.free.ngrok.cc/Seckill', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: 'https://www.baidu.com/img/bd_logo1.png', // 分享图标
		    success: function () {
		    alert("朋友圈分享成功");
		},
		cancel: function () {
		    // 用户取消分享后执行的回调函数
			alert("请重新分享朋友圈");
		    }
		});
		
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	
		wx.onMenuShareAppMessage({
			title: '分享标题', // 分享标题
			desc: '分享描述', // 分享描述
			link: 'http://sunweixin.free.ngrok.cc/Seckill', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			imgUrl: 'https://www.baidu.com/img/bd_logo1.png', // 分享图标
			type: 'link', // 分享类型,music、video或link，不填默认为link
			dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			success: function () {
			// 用户确认分享后执行的回调函数
			alert("分享成功!");
			},
			cancel: function () {
			// 用户取消分享后执行的回调函数
			alert("请重新分享!");
			}
			});
	    
	    
		
	});
	//相片
	function show(){
		wx.chooseImage({
			count: 0, // 默认9
			sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			success: function (res) {
			var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			}
			});
	}
	//扫一扫
	function scan(){
		wx.scanQRCode({
			needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
			var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			alert("扫描成功");
			}
			});
	}
	
	//********************
	//支付
	function pay(){
		wx.chooseWXPay({
			timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			nonceStr: '', // 支付签名随机串，不长于 32 位
			package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
			signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			paySign: '', // 支付签名
			success: function (res) {
			// 支付成功后的回调函数
			}
			});
	}
	
	
	
	
	
	
	wx.error(function(res){
		alert("");
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});
	</script>
</body>
</html>