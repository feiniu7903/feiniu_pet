<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.lvmama.clutter.service.PepsiService"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>这个页面模拟百事调用WebService</title>
</head>
<%
	String mobile = request.getParameter("mobile");
	String serialNo = request.getParameter("serialNo");
	String email = request.getParameter("email");
	
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
	PepsiService pepsiService = (PepsiService)context.getBean("pepsiServiceBean");
	
	int i = -1;
	i = pepsiService.exchangeCoupon(mobile, serialNo, email);
	if (i == -1) {
		out.println("Message: Get started");
	} else if (i == 1) {
		out.println("Message: WebService Success");
	} else if (i == 0) {
		out.println("Message: WebService Fail");
	}
%>
<body>
	<p>这个页面模拟百事调用webservice。提交后会产生一个userId，并插入【mark_coupon_relate_user】表的【user_id】字段，
	该userId需要和【我的优惠券】登录的userId相同，才能在【我的优惠券】列表中查到记录。
	</p>
	<form method="post">
		手机号：<input type="text" name="mobile" value=""><br/>
		百事流水号：<input type="text" name="serialNo" value=""><br/>
		邮箱：<input type="text" name="email" value=""><br/>
		<input type="submit" value="提交">
	</form>
</body>
</html>
