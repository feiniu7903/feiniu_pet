<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'list_coupon.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <table width="150px;" class="datatable" style="border: none;">
  <s:if test="markCouponList.size()!=0">
    <s:iterator value="markCouponList">
    	<tr>
    	<td>
    	${couponName}
    	</td>
    	</tr>
    	 </s:iterator>
    	 </s:if><s:else>
    	 <tr>
    	<td style="color: red;">
    	没有参与任何优惠
    	</td>
    	</tr>
    	 </s:else>
    </table>
   
  </body>
</html>
