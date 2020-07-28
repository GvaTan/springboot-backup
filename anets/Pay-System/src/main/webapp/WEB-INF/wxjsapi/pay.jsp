<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<script src='${pageContext.request.contextPath}/js/jquery.min.js'></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<title>订单信息</title>
<style>
.all {
	position: fixed;
	width: 100%;
	height: 100%;
	padding: 10% 4%;
	font-size: 60px;
	color: rgba(0, 0, 0, 0.9);
}

.forms {
	padding: 2px 5px;
	position: absolute;
	top: 50%;
	left: 50%;
	width: 100%;
	transform: translate(-50%, -50%);
	padding: 0px 2%;
}

#form1 {
	height: 750px;
}

input {
	outline: none;
	border: 1px solid #777;
	border-radius: 5px;
	float: right;
	padding: 2px 5px;
}

.group {
	margin-top: 20px;
	float: left;
}

.group * {
	float: left
}

input {
	outline: none;
	border: 1px solid #777;
	border-radius: 5px;
	float: right;
	padding: 2px 5px;
}

.group label {
	display: inline-block;
	width: 230px;
	height: 90px;
	line-height: 90px;
	font-size: 45px;
}

.group input {
	width: 70%
}

input:focus {
	border: 1px solid blue;
}

#submits {
	border: 0px;
	background-color: rgba(244, 90, 141, 1);
	color: white;
	padding: 3px 15px;
}

h1 {
	font-size: 65px;
	padding-left: 15px;
}

.car ul {
	margin-left: 50px;
	font-size: 45px;
}
</style>
</head>

<body>
	<div class="all">
		
	</div>
</body>
<script type="text/javascript">
  $(function(){
	  if (typeof WeixinJSBridge == "undefined"){
   	   if( document.addEventListener ){
   	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   	   }else if (document.attachEvent){
   	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
   	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   	   }
   	}else{
   	   onBridgeReady();
     }
  });
  
  
  function onBridgeReady(){
	  var appidz="${payInfo.appId}";
	  var timestampz="${payInfo.timeStamp}";
      var nonceStrz="${payInfo.nonceStr}";
      var packagez="${payInfo.packages}";
      var signTypez="${payInfo.signType}";
      var paySignz="${payInfo.paySign}";
      //alert(timestampz+"|"+nonceStrz+"|"+packagez+"|"+signTypez+"|"+paySignz);
	   WeixinJSBridge.invoke(
	      'getBrandWCPayRequest', {
	         "appId":appidz,     //公众号名称，由商户传入     
	         "timeStamp":timestampz,         //时间戳，自1970年以来的秒数     
	         "nonceStr":nonceStrz, //随机串     
	         "package":packagez,     
	         "signType":signTypez,         //微信签名方式：     
	         "paySign":paySignz //微信签名 
	      },
	      function(res){
	      if(res.err_msg == "get_brand_wcpay_request:ok" ) {
              console.log('支付成功');
              $("#submits").attr('value',"支付成功");
              window.location.href="success.html";
              //支付成功后跳转的页面
          }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
       	   console.log('支付取消');
       	 alert("用户取消，支付失败");
       	   
          }else if(res.err_msg == "get_brand_wcpay_request:fail"){
       	   console.log('支付失败');
       	 alert("支付失败");
              WeixinJSBridge.call('closeWindow');
          }else{
        	  alert("未知");
          }
	   }); 
	}
</script>
</html>