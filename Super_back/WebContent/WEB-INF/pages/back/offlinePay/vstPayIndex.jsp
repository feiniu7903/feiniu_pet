<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"> </script>
<script type="text/javascript">
	$(function(){
		$("input.submit").click(function(){
			$.post("/super_back/offlinePay/vstPaySubmit.do",$("#ff").serialize(),function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					alert("支付成功");
				}else{
					alert(data.msg);
				}
			});
		});
	});
</script>
</head>
<body>
<form onsubmit="return false" id="ff">
	<div>订单编号<input type="text" name="orderId"/></div>
	<div>支付金额<input type="text" name="amount"/>(分)</div>
	<input type="button" class="submit" value="提交"/>
</form>
</body>
</html>