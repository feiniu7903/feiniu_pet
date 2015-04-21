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
<title>PET短信模板管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/sms/sms_template_pet_index.js"></script>
</head>
<body style="padding:10px;">
	<ul class="gl_top">
		<form id="searchForm" action="${pageContext.request.contextPath }/smsTemplate/petIndex.do" method="post">
			<li>模板ID：<input type="text" class="u3" name="templateId" value="${param.templateId }"></li>
			<li>模板名称：<input type="text" class="u3" name="templateName" value="${param.templateName }"></li>
			<li>模板内容：<input type="text" class="u3" name="content" value="${param.content }"></li>
			<li>默认通道：
				<select name="channelWhere">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option <s:property value='%{#parameters.channelWhere[0]==#channel?"selected=\'selected\'":""}'/> value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
				</s:iterator>
				</select>
	     	</li>
	     	<li>移动通道：
				<select name="channelCMCCWhere">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option <s:property value='%{#parameters.channelCMCCWhere[0]==#channel?"selected=\'selected\'":""}'/> value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
				</s:iterator>
				</select>
	     	</li>
	     	<li>联通通道：
				<select name="channelCUCWhere">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option <s:property value='%{#parameters.channelCUCWhere[0]==#channel?"selected=\'selected\'":""}'/> value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
				</s:iterator>
				</select>
	     	</li>
	     	<li>电信通道：
				<select name="channelCTWhere">
				<option value="">请选择</option>
				<s:iterator value="channelMap.keySet()" var="channel">
				<option <s:property value='%{#parameters.channelCTWhere[0]==#channel?"selected=\'selected\'":""}'/> value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
				</s:iterator>
				</select>
	     	</li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10">
				<input type="button" value="新增" class="u10" id="btnAdd" onclick="newHandler()">
				<input type="button" value="批量修改通道" class="u10" id="btnEditBatchChannel" onclick="editBatchChannelFun()">
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
	    <th width="100px">是否可用</th>
	    <th width="100px">短信默认发送通道</th>
	    <th width="100px">移动短信发送通道</th>
	    <th width="100px">联通短信发送通道</th>
	    <th width="100px">电信短信发送通道</th>
	    <th width="100px">操作</th>
	  </tr>
	  <s:iterator value="petTemplatePage.items" var="item">
			<tr>
				<td><s:property value="templateId" /></td>
				<td><s:property value="templateName" /></td>
				<td><s:property value="content" /></td>
				<td>${item.immendiately=="true"?"是":"否" }</td>
				<td>${item.valid=="Y"?"是":"否" }</td>
				<td><s:property value="channelMap[channel]" /></td>
				<td><s:property value="channelMap[channelCMCC]" /></td>
				<td><s:property value="channelMap[channelCUC]" /></td>
				<td><s:property value="channelMap[channelCT]" /></td>
				<td><a href="javascript:void(0);" onclick="editHandler('${item.templateId}')">修改</a>
				<a href="javascript:void(0);" onclick="deleteHandler('${item.templateId}')">删除</a></td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="petTemplatePage.totalResultSize" />
		</td>
		<td colspan="9" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(petTemplatePage)"/>
		</td>
	  </tr>
	</table>
	
	<!-- 批量修改短信发送渠道 -->
	<div id="editBatchChannelDialog" style="display:none;">
		<form id="editBatchChannelForm" action="${pageContext.request.contextPath }/smsTemplate/petEditBatchChannel.do" name="editBatchChannelForm" method="post">
		<input type="hidden" name="templateId" value="${param.templateId }">
		<input type="hidden" name="templateName" value="${param.templateName }">
		<input type="hidden" name="content" value="${param.content }">
		<input type="hidden" name="channelWhere" value="${param.channelWhere }">
		<input type="hidden" name="channelCMCCWhere" value="${param.channelCMCCWhere }">
		<input type="hidden" name="channelCUCWhere" value="${param.channelCUCWhere }">
		<input type="hidden" name="channelCTWhere" value="${param.channelCTWhere }">
		<div style="line-height:24px; color:#FF0000; text-align:center;">
		说明:此操作将对查询的所有列表数据起效
		</div>
		<ul class="gl3_top">
			<li>短信默认发送通道：
				<select name="channel">
					<option value="THESAME">不修改</option>
					<option value="">空</option>
					<s:iterator value="channelMap.keySet()" var="channel">
					<option value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
					</s:iterator>
				</select>
			</li> 
			<li>移动短信发送通道：
				<select name="channelCMCC">
					<option value="THESAME">不修改</option>
					<option value="">空</option>
					<s:iterator value="channelMap.keySet()" var="channel">
					<option value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
					</s:iterator>
				</select>
			</li> 
			<li>联通短信发送通道：
				<select name="channelCUC">
					<option value="THESAME">不修改</option>
					<option value="">空</option>
					<s:iterator value="channelMap.keySet()" var="channel">
					<option value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
					</s:iterator>
				</select>
			</li> 
			<li>电信短信发送通道：
				<select name="channelCT">
					<option value="THESAME">不修改</option>
					<option value="">空</option>
					<s:iterator value="channelMap.keySet()" var="channel">
					<option value='<s:property value="channel"/>'><s:property value="channelMap[#channel]"/></option>
					</s:iterator>
				</select>
			</li>
		</ul>
		<div class="gl3_zs_b" align="center">
		    <input onclick="editBatchChannelFormSubmit()" value="保存" type="button">
		</div>
		</form>
	</div>
</body>
</html>