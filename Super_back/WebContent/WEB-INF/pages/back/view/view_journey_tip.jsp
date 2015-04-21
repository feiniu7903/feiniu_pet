<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/prod/view_journey_tips.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prod/sensitive_word.js"></script>
<div id="tipsList">
	<p>
		<font class="add_margin_left">小贴士维护</font>
	</p>
	<form class="mySensitiveForm">
	<table id="journeyTipsTable" class="newTable" width="90%" border="0"
		cellspacing="0" cellpadding="0">
		<tr class="newTableTit">
			<td>
				标题
			</td>
			<td>
				内容
			</td>
			<td>
				操作
			</td>
		</tr>
		<s:iterator value="journeyTipList">
			<tr id="${tipId}">
				<td>
					<s:property value='tipTitle' /><input type="hidden" name="tipId" value="${tipId}" />
					<input type="hidden" name="tipTitle${tipId}" value="<s:property value='tipTitle'/>" class="sensitiveVad" />
				</td>
				<td>
					<s:property value='tipContent' /><input type="hidden" name="tipContent${tipId}" value="<s:property value='tipContent'/>" class="sensitiveVad" />
				</td>
				<td>
					<a href="javascript:deleteJourneyTips(${journeyId},${tipId});">删除</a>
				</td>
			</tr>
		</s:iterator>
	</table>
	</form>
</div>
<div id="divspace" style=" height: 10px;">
	&nbsp;
</div>
<div id="addTips">
	<form action="view/saveJourneyTips.do" method="post" name="journeyTipForm" id="journeyTipForm">
		<input type="hidden" name="journeyId" value="${journeyId}"/>
		<table class="newTable" width="90%" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td style="text-align: right;">
					标题:
				</td>
				<td>
					<s:textfield cssClass="text1" id="tipTitle" name="viewJourneyTips.tipTitle" cssClass="sensitiveVad" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					内容:
				</td>
				<td><!--<s:textfield id="tipContent" name="tipContents" cssClass="text1" /> -->
					 <textarea id="tipContent" name="tipContent" cols="50" rows="5" class="sensitiveVad"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" id="saveTipButton" name="saveTipButton" value="完成" class="button01" />
				</td>
			</tr>
		</table>
	</form>
</div>

