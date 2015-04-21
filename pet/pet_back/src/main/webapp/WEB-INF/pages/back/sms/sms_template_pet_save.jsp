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
<title>后台PET短信模板管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/sms_template_pet_save.js"></script>
</head>
<body>
	<form id="form" action="petSave.do" method="post">
	<input type="hidden" name="operate" value="${request.operate }">
	<input type="hidden" name="template.channelId" value="${model.channelId }">
	<input type="hidden" name="template.channelType" value="${model.channelType }">
	<input type="hidden" name="template.templateType" value="${model.templateType }">
	<ul class="gl3_top">
		<li>模板ID：
		<s:if test="%{#request.operate=='edit'}">
		${model.templateId }
		<input type="hidden" name="template.templateId" value="${model.templateId }">
     	</s:if>
     	<s:else>
		<input type="text" name="template.templateId" value="${model.templateId }">
     	</s:else>
     	</li>
		<li>模板名称：<input type="text" name="template.templateName" value="${model.templateName }"></li>
	    <li>是否立即发送：
	    	<s:if test="%{#request.operate=='edit'}">
		    	<label><input type="radio" value="true" ${model.immendiately=="true"?"checked=\"checked\"":"" } name="template.immendiately" />是</label>
		    	<label><input type="radio" value=""  ${model.immendiately=="true"?"":"checked=\"checked\"" } name="template.immendiately"/>否</label>
	     	</s:if>
	     	<s:else>
	     	<label><input type="radio" checked="checked" value="true" name="template.immendiately" />是</label>
	    	<label><input type="radio" value="" name="template.immendiately"/>否</label>
	     	</s:else>
	    </li>
	     <li>是否可用：
	     	<s:if test="%{#request.operate=='edit'}">
		    	<label><input type="radio" value="Y" ${model.valid=="Y"?"checked=\"checked\"":"" } name="template.valid" />是</label>
		    	<label><input type="radio" value="N"  ${model.valid=="Y"?"":"checked=\"checked\"" } name="template.valid"/>否</label>
	     	</s:if>
	     	<s:else>
	     	<label><input type="radio" checked="checked" value="Y" name="template.valid" />是</label>
	    	<label><input type="radio" value="N" name="template.valid"/>否</label>
	     	</s:else>
	    </li>
		<li>短信默认发送通道：
			<select name="template.channel">
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
			<select name="template.channelCMCC">
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
			<select name="template.channelCUC">
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
			<select name="template.channelCT">
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
	    	<textarea rows="8" cols="100" name="template.content">${model.content }</textarea>
	    </li>
	</ul>
	<div class="gl3_zs_b" align="center">
	    <input name="" value="保存" type="submit">
	</div>
	</form>
</body>
</html>
	