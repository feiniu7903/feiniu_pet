<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SUPER短信模板管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/sms_template_super_index.js"></script>
</head>
<body style="padding:10px;">
	<ul class="gl_top">
		<form action="${pageContext.request.contextPath }/smsTemplate/superIndex.do" method="post">
			<li>模板ID：<input type="text" class="u3" name="templateId" value="${param.templateId }"></li>
			<li>模板名称：<input type="text" class="u3" name="templateName" value="${param.templateName }"></li>
			<li>模板内容：<input type="text" class="u3" name="content" value="${param.content }"></li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10">
				<input type="button" value="新增" class="u10" id="btnAdd" onclick="newHandler()">
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th width="50px">模板ID</th>
	    <th width="100px">模板名称</th>
	    <th width="500px">模板内容</th>
	    <th width="100px">是否立即发送</th>
	    <th width="100px">操作</th>
	  </tr>
	  <s:iterator value="superTemplatePage.items" var="item">
			<tr>
				<td><s:property value="templateId" /></td>
				<td><s:property value="templateName" /></td>
				<td><s:property value="content" /></td>
				<td><s:label value='%{immendiately=="true"?"是":"否"}'/></td>
				<td><a href="javascript:void(0);" onclick="editHandler('${item.templateId}')">修改</a>
				<a href="javascript:void(0);" onclick="deleteHandler('${item.templateId}')">删除</a></td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="superTemplatePage.totalResultSize" />
		</td>
		<td colspan="9" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(superTemplatePage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>