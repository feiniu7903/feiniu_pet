<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.lvmama.clutter.service.PepsiService"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>百事优惠券-客服平台</title>
</head>
<%
	String mobile = request.getParameter("mobile");
	String serialNo = String.valueOf(System.currentTimeMillis());
	String email = request.getParameter("email");
	
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
	PepsiService pepsiService = (PepsiService)context.getBean("pepsiServiceBean");
	
	int i = pepsiService.exchangeCoupon(mobile, serialNo, email);
	if (mobile == null && email == null) {
		out.println("提示信息: ");
	} else if (i == 1) {
		out.println("提示信息: 产生成功");
	} else if (i == 0) {
		out.println("提示信息: 失败");
	}
%>
<body>
	<p>客服帮助客户生成优惠券</p>
	<form method="post">
		手机号：<input type="text" name="mobile" value=""><br/>
		邮箱：<input type="text" name="email" value=""><br/>
		<input type="submit" value="提交">
	</form>
</body>
</html>