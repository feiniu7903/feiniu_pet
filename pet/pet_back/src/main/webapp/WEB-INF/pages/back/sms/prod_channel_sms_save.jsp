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
<title>产品相关短信模板管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/prod_channel_sms_save.js"></script>
</head>
<body>
	<form id="form" action="save.do" method="post">
	<input type="hidden" name="operate" value="${request.operate }">
	<input type="hidden" name="model.channelSmsId" value="${model.channelSmsId }">
	<ul class="gl3_top">
		<li style="width:100%; text-align: center; color:#FF0000">相关模板和内容必须填写一个,内容为空的时候以模板内容为主!</li>
		<li style="float:both; width: 80%">相关模板：
			<select name="model.templateId" id="templateId">
			<option value="">请选择</option>
			<s:iterator value="smsTemplates" var="item">
			<option value='<s:property value="#item.templateId"/>' <s:if test="%{#item.templateId==#request.model.templateId}">
				selected="selected"
				</s:if>><s:property value="#item.templateName" /></option>
			</s:iterator>
			</select>
     	</li>
     	
     	<li>产品销售渠道：
			<select name="model.channelCode" id="channelCodeSel">
			<option value="">请选择</option>
			<s:iterator value="prodChannels" var="item">
			<option value='<s:property value="#item.channelId+'_'+#item.channelCode"/>' <s:if test="%{#item.channelCode==#request.model.channelCode}">
				selected="selected"
				</s:if>><s:property value="#item.channelName" /></option>
			</s:iterator>
			</select>
     	</li>
     	
	    <li>短信默认发送通道：
			<select name="model.channel">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==#request.model.channel}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select>
		</li> 
		<li>移动短信发送通道：
			<select name="model.channelCMCC">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==#request.model.channelCMCC}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select>
		</li> 
		<li>联通短信发送通道：
			<select name="model.channelCUC">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==#request.model.channelCUC}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select>
		</li> 
		<li>电信短信发送通道：
			<select name="model.channelCT">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option value='<s:property value="channel"/>' <s:if test="%{#channel==#request.model.channelCT}">
				selected="selected"
				</s:if>><s:property value="channelMap[#channel]"/>
				</option>
				</s:iterator>
			</select>
		</li>
		<li style="clear: both;">模板内容：<br/><br/>
	    	<textarea rows="8" cols="100" name="model.content" id="content">${model.content }</textarea>
	    </li>
	</ul>
	<div class="gl3_zs_b" align="center">
	    <input name="" value="保存" type="submit">
	</div>
	</form>
</body>
</html>
	