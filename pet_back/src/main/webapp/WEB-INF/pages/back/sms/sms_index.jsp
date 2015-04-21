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
<title>短信明细查询</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/sms_index.js"></script>
</head>
<body style="padding:10px;">
	<ul class="gl_top">
		<form action="index.do" method="post">
			<li>输入手机号码：<input type="text" class="u3" name="mobile" value="${param.mobile }"></li>
			<li>发送状态：
				<select name="reportStatus">
					<option value="">请选择</option>
					<s:iterator value="smsStatusMap.keySet()" var="status">
					<option value='<s:property value="status"/>' <s:if test="%{#status==#parameters.reportStatus[0]}">
					selected="selected"
					</s:if>><s:property value="smsStatusMap[#status]"/>
					</option>
					</s:iterator>
				</select>
			</li>
			<li>起始日期：<input type="text" class="u3" id="startDate" name="startDate" value="${param.startDate }"></li>
			<li>结束日期：<input type="text" class="u3" id="endDate" name="endDate" value="${param.endDate }"></li>
			<li>内容关键字：<input type="text" class="u3" name="likeContent" value="${param.likeContent }"></li>
			<li>查询所有：<input style="vertical-align:middle;" type="checkbox" ${param.queryAll=="1"?"checked='checked'":"" } class="u3" name="queryAll" value="1"></li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10">
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th width="100px">序号</th>
	    <th width="100px">短信唯一标识(msgId)</th>
	    <th width="100px">手机号码</th>
	    <th width="500px">发送内容</th>
	    <th width="130px">创建时间</th>
	    <th width="130px">实际发送时间</th>
	    <th width="150px">发送状态</th>
	    <th width="100px">操作账号</th>
	    <th width="100px">发送通道</th>
	    <th width="100px">操作</th>
	  </tr>
	  <s:iterator value="pageModel.items" var="item">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="serialId" /></td>
				<td><s:property value="mobile" /></td>
				<td><s:property value="contentRlppa" /></td>
				<td><s:date name="sendDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="actualSendDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:property value="statusGB" /></td>
				<td><s:property value="userId=='System'?'System':opeUserName" /></td>
				<td><s:property value="channelMap[#item.channel]" /></td>
				<td>
				<s:if test='#item.tableName!="SMS_CONTENT"'>
				<a href="javascript:void(0);" onclick="resend('${item.id}','${item.channel }','${item.tableName }')">重发短信</a>
				</s:if>
				<s:else>
				&nbsp;
				</s:else>
				</td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="pageModel.totalResultSize" />
		</td>
		<td colspan="9" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageModel)"/>
		</td>
	  </tr>
	</table>
	<div style="display:none" id="resendDialog">
		<ul class="gl_top">
		<form id="resendForm" name="resendForm" action="resend.do" method="post">
			<input type="hidden" name="id" id="id">
			<input type="hidden" name="tableName" id="tableName">
			<li>短信默认发送通道：
				<select name="channel" id="channel">
					<option value="">请选择</option>
					<s:iterator value="channelMap.keySet()" var="channel">
					<option value='<s:property value="channel"/>'>
					<s:property value="channelMap[#channel]"/>
					</option>
					</s:iterator>
				</select>
			</li> 
			<li>
				<input type="submit" value="发送">
			</li> 
		</form>
		</ul>
	</div>
</body>
</html>