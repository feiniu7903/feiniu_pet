<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单快速查询</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css?r=4845" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/jquery.suggest.css?r=2916" type="text/css" rel="stylesheet"  />

<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js?r=2913"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/s2_common2.js?r=2913"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js?r=2913"></script>
<#include "/common/commonJsIncluedTopNew.ftl">
<script>
function check(){
	var mobileNumber = $("#mobileNumber").val();
	var orderId = $("#orderId").val();
	if(mobileNumber==null || mobileNumber==""){
		alert("请输入手机号");
		return false;
	}else if(orderId==null || orderId==""){
		alert("请输入订单号");
		return false;
	}else if(isNaN(orderId)){ 
		alert("订单号必须是数字"); 
		return false; 
	}
	return true;
}
</script>
<style>
#container {width:980px;margin:150px auto 200px;text-align:center;}
#container label {margin:0 15px;}
#container .txt-inp {border:1px solid #A5ACB2;background-color:#fff;padding:2px 3px;}
</style>
</head>

<body>
<#include "/common/setKeywor.ftl">
<center>
<#include "/WEB-INF/pages/msg.ftl">
</center>
<!--================ 头部 E =================-->
<form action="/view/queryOrder.do" method="post">
	<div id="container">
		<label>取票人手机号：<input id="mobileNumber" type="text" name="mobileNumber" value="<@s.property value="mobileNumber"/>" class="txt-inp" /></label>
		<label>订单号：<input name="orderId" id="orderId" type="text" value="<@s.property value="orderId"/>" name="orderId" class="txt-inp" /></label>
	    <input name="showOrderSubmit" type="submit" onclick="return check();" value=" 查看订单 " />
	</div>
</form>

<script src="http://pic.lvmama.com/js/common/copyright.js"></script>
</body>
</html>

