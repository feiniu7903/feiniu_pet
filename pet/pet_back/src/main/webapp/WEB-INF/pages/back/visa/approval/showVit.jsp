<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看审核材料</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>		
	</head>
	<body>
	<div class="wrap">
		<div id="popDiv2" style="display: none"></div>
		<p class="p_title">
			<span class="req">${visaApproval.name }</span> 的材料明细：(${visaApproval.cnOccupation})
			&nbsp;&nbsp;&nbsp;&nbsp;
			出签状态：${visaApproval.cnVisaStatus}
		</p>
		<table class="p_table table_info">
			<tbody>
			<s:iterator value="visaApprovalDetailsList" var="details">
				<tr>
					<td class="p_label">${title}</td>
					<td class="tl">${content}</td>
					<s:if test='approvalStatus == "Y"'>
						<td class="status-yes" id="status_${detailsId}" width="5%">(√)</td>
					</s:if>
					<s:if test='approvalStatus == "N"'>
						<td class="status-no" id="status_${detailsId}" width="5%">(×)</td>
					</s:if>					
					<td class="oper">
						${memo}
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<table class="p_table mt20">
			<thead>
				<tr>
					<th colspan="4">保证金</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="p_label w5">保证金形式</td>
					<td><s:select list="#{'':'--请选择--','NONE':'无需保证金','THIRD':'第三方保管','CASH':'押金入账'}" disabled="true" name="visaApproval.depositType"/></td>
					<td class="p_label w5">开户银行</td>
					<td><s:select list="#{'':'--请选择--','BOC':'中国银行','BEA':'东亚银行','SCB':'渣打银行','SPDB':'浦发银行黄浦营业部'}" disabled="true" name="visaApproval.bank"/></td>
				</tr>
				<tr>
					<td class="p_label w5">保证金金额</td>
					<td>${visaApproval.amount}(单位:元)</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td class="p_label w5">是否可退还材料</td>
					<td><s:if test='visaApproval.returnMaterial == "Y"'>可以</s:if></td>
					<td class="p_label w5">是否可退还保证金</td>
					<td><s:if test='visaApproval.returnBail == "Y"'>可以</s:if></td>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<p class="p_title">快递日志</p>
		<table class="p_table J_additem_box">
			<tbody>
				<s:iterator value="logs" id="log">
					<tr>
						<td width="90">${log.operatorName }添加了快递信息：${log.content }</td>
					</tr>		                   
				</s:iterator>
			</tbody>
		</table>
		<br/>
		<p class="p_title">面签/面销通知书日志</p>
		<table class="p_table J_additem_box">
			<tbody>
				<s:iterator value="facelogs" id="log">
					<tr>
						<td width="90">${log.operatorName }添加了通知书信息：${log.content }</td>
					</tr>		                   
				</s:iterator>
			</tbody>
		</table>
	</div>
	</body>
</html>