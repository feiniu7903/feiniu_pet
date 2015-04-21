<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信发送通道全局配置</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/sms_config.js"></script>
</head>
<body>
	<div style="padding-left:1%; font-size:20px; font-weight:bold; line-height:40px;">短信全局配置</div>
	<form id="form" action="save.do" method="post">
	<center>
	<table style=" text-align:center; line-height:30px; width:98%;" cellpadding="5px" border="1" bgcolor="#EEEEEE" bordercolor="#DDDDDD" cellspacing="0">
		<tr>
			<td>全局默认通道：
			<select name="model.defaultChannel">
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==model.defaultChannel}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td>重发首选通道：
			<select name="model.resendFirstChannel">
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==model.resendFirstChannel}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td>重发次选通道：
			<select name="model.resendSecondaryChannel">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==model.resendSecondaryChannel}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td>
				<input style="line-height:25px; height:25px; width:50px;margin:5px; " type="submit" value="保存">
			</td>
		</tr>
	</table>
	</center>
	</form>
</body>
</html>
	