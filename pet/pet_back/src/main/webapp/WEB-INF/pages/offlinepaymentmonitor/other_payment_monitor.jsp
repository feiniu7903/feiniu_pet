<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<link href="${basePath}/css/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${basePath}/themes/cc.css" />
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
</head>
<body>
<form method="post" action="${basePath}/offlinepaymentmonitor/other_payment_monitor!query.do" id="otherPaymentMonitorForm">
<div>
<div class="main2">
<div class="table_box" id=tags_content_1>
<div class="mrtit3">
<table border="0" cellspacing="0" cellpadding="0" class="search_table">
	<tr>
		<td>订单号: <input type="text" name="paramMap['objectId']" value="${paramMap['objectId']}" style="height: 22px;width: 185px;" /></td>
		<td>支付网关: 
		<select name="paramMap['paymentGateway']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="payPaymentGateway" value="payPaymentGatewayList">
				<s:if test="'CASH'!=#payPaymentGateway.gateway && 'COMM_POS_CASH'!=#payPaymentGateway.gateway && 'SAND_POS_CASH'!=#payPaymentGateway.gateway">
				<option value="${payPaymentGateway.gateway}" <s:if test="paramMap['paymentGateway']==#payPaymentGateway.gateway">selected="selected"</s:if>>${payPaymentGateway.gatewayName}</option>
				</s:if>
			</s:iterator>
		</select>
		</td>
		<td>提交人: <input type="text" id="operator" name="paramMap['operator']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['operator']}"/></td>
	</tr>
	
	<tr>
		<td>支付时间: 
		<input name="paramMap['callbackTimeStart']" type="text" value="${paramMap['callbackTimeStart']}" id="callbackTimeStart" style="height: 22px;width: 85px;"/>
		~
		<input name="paramMap['callbackTimeEnd']" type="text" value="${paramMap['callbackTimeEnd']}" id="callbackTimeEnd" style="height: 22px;width: 85px;"/>
		</td>
			
		<td>状态: 
		<select name="paramMap['otherAuditStatus']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="otherAuditStatus" value="otherAuditStatusList">
				<option value="${otherAuditStatus.code}" <s:if test="paramMap['otherAuditStatus']==#otherAuditStatus.code">selected="selected"</s:if>>${otherAuditStatus.cnName}</option>
			</s:iterator>
		</select>
		</td>
		
		<td>审核人: <input type="text" id="auditPerson" name="paramMap['auditPerson']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['auditPerson']}"/></td>
	</tr>
	
	<tr>
		<td>网关交易号: <input type="text" name="paramMap['gatewayTradeNo']" value="${paramMap['gatewayTradeNo']}" style="height: 22px;width: 185px"/></td>
		<td></td>
		<input type="hidden" name="paramMap['gatewayType']" value="OTHER"/>
		<input type="hidden" name="paramMap['gatewayStatus']" value="ENABLE"/>
		<input type="hidden" name="paramMap['orderby']" value="PP.CREATE_TIME"/>
		<input type="hidden" name="paramMap['order']" value="DESC"/>
		
		<td><input name="right_button08Submit" type="submit" value="查询" class="right-button08" style="margin-left: 20px"/></td>
	</tr>
	
</table>
</form>
</div>
<br />
<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
	
		<tr>
			<th>订单号</th>
			<th>支付时间</th>
			<th>支付网关</th>
			<th>支付金额(元)</th>
			<th>所属中心</th>
			<th>所属部门</th>
			<th>对方户名</th>
			<th>对方银行</th>
			<th>对方账号</th>
			<th>我方户名</th>
			<th>我方银行</th>
			<th>我方账号</th>
			<th>网关交易号</th>
			<th>名称</th>
			<th>摘要</th>
			<th>备注</th>
			<th>提交人</th>
			<th>确认人</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<s:iterator value="cashPaymentComboVOList" var="cashPaymentComboVO">
			<tr bgcolor="#ffffff">
				<td>${cashPaymentComboVO.objectId}</td>
				<td><s:date name="#cashPaymentComboVO.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${cashPaymentComboVO.paymentGatewayZH}</td>
				<td>
				<s:if test="#cashPaymentComboVO.amount!=null">
					${cashPaymentComboVO.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>${cashPaymentComboVO.belongCenter}</td>
				<td>${cashPaymentComboVO.belongDepartment}</td>
				<td>${cashPaymentComboVO.paymentAccount}</td>
				<td>${cashPaymentComboVO.paymentBankName}</td>
				<td>${cashPaymentComboVO.paymentBankCardNo}</td>
				<td>${cashPaymentComboVO.receivingAccount}</td>
				<td>${cashPaymentComboVO.bankName}</td>
				<td>${cashPaymentComboVO.bankCardNo}</td>
				<td>${cashPaymentComboVO.gatewayTradeNo}</td>
				<td>${cashPaymentComboVO.detailName}</td>
				<td>${cashPaymentComboVO.summary}</td>
				<td>${cashPaymentComboVO.callbackInfo}</td>
				<td>${cashPaymentComboVO.operator}</td>
				<td>${cashPaymentComboVO.auditPerson}</td>
				<td>${cashPaymentComboVO.otherAuditStatusZH}</td>
				<td>
					<s:if test="#cashPaymentComboVO.otherAuditStatus=='UNCONFIRMED'">
						<a href="#" onclick="javascript:otherAuditConfirm('${cashPaymentComboVO.paymentDetailId}');">确认</a>
					</s:if>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2"  align="left">
			<input type="button" value="导出Excel" id="exportExcel"/>
			</td>
			
			<td colspan="2">
			总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="15">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
			</td>
		</tr>
</table>
</div>
</div>
</div>
</body>
<div id="cashPaymentMonitorDiv"/>
<script type="text/javascript">
$(function() {
	$("input[id='callbackTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
	$("input[id='callbackTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
});
function otherAuditConfirm(paymentDetailId){
	$.ajax( {
		type : "POST",
		url : "${basePath}/offlinepaymentmonitor/other_payment_monitor!otherAuditConfirm.do",
		data : {"paramMap['paymentDetailId']":paymentDetailId},
		success : function(result) {
			alert(eval(result));
			$('#otherPaymentMonitorForm').submit();
		}
	});
}
$("#operator").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/other_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#operator").val(item.id);	
	}
});
$("#auditPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/other_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#auditPerson").val(item.id);	
	}
});
$("#exportExcel").click(function(){
	var url="${basePath}/offlinepaymentmonitor/other_payment_monitor!exportExcel.do?"+$("#otherPaymentMonitorForm").serialize();
	window.open(url,"exportExcel");
});
</script>
</html>
