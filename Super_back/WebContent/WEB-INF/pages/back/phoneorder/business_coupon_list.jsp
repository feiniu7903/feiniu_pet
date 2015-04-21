<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>
	<body>
		<s:if test="businessCouponList != null && !businessCouponList.isEmpty()">
			<s:iterator value="businessCouponList">
				在
				<s:if test="beginTime!=null">
          				<s:date name="beginTime" format="yyyy-MM-dd"/> ~<s:date name="endTime" format="yyyy-MM-dd"/>
          		</s:if>
          		期间下单: ${favorTypeDescription}<br />
			</s:iterator>
		</s:if>
	</body>
</html>
