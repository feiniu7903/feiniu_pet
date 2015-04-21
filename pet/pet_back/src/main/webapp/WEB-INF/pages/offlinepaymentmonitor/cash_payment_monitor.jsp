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
<style type="text/css">
.ui-dialog .ui-dialog-content { position: relative; border: 0; padding: .5em 1em; background: none; overflow: visible; zoom: 1; }
</style>
</head>
<body>
<form method="post" action="${basePath}/offlinepaymentmonitor/cash_payment_monitor!query.do" id="cashPaymentMonitorForm">
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
			<option value="CASH" <s:if test="paramMap['paymentGateway']=='CASH'">selected="selected"</s:if>>现金</option>
			<option value="COMM_POS_CASH" <s:if test="paramMap['paymentGateway']=='COMM_POS_CASH'">selected="selected"</s:if>>交通银行POS机现金支付</option>
			<option value="SAND_POS_CASH" <s:if test="paramMap['paymentGateway']=='SAND_POS_CASH'">selected="selected"</s:if>>杉德POS机现金支付</option>
		</select>
		</td>
		<td>银行交易流水号: <input type="text" name="paramMap['gatewayTradeNo']" value="${paramMap['gatewayTradeNo']}" style="height: 22px;width: 185px"/></td>
	</tr>
	
	<tr>
		
	
		<td>收款人: <input type="text" id="receivingPerson" name="paramMap['receivingPerson']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['receivingPerson']}"/></td>
		<td>审核状态: 
		<select name="paramMap['cashAuditStatus']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="cashAuditStatus" value="cashAuditStatusList">
				<option value="${cashAuditStatus.code}" <s:if test="paramMap['cashAuditStatus']==#cashAuditStatus.code">selected="selected"</s:if>>${cashAuditStatus.cnName}</option>
			</s:iterator>
		</select>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;解款人: <input type="text" id="cashLiberateMoneyPerson" name="paramMap['cashLiberateMoneyPerson']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['cashLiberateMoneyPerson']}"/></td>
		<td>支付时间: 
		<input name="paramMap['callbackTimeStart']" type="text" value="${paramMap['callbackTimeStart']}" id="callbackTimeStart" style="height: 22px;width: 85px;"/>
		~
		<input name="paramMap['callbackTimeEnd']" type="text" value="${paramMap['callbackTimeEnd']}" id="callbackTimeEnd" style="height: 22px;width: 85px;"/>
		</td>

	</tr>
	
	<tr>
		<td>审核人: <input type="text" id="auditPerson" name="paramMap['auditPerson']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['auditPerson']}"/></td>
		<td>收款公司: 
		<select name="paramMap['receivingCompanyId']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="payReceivingCompany" value="payReceivingCompanyList">
				<option value="${payReceivingCompany.receivingCompanyId}" <s:if test="paramMap['receivingCompanyId']==#payReceivingCompany.receivingCompanyId">selected="selected"</s:if>>${payReceivingCompany.receivingCompanyName}</option>
			</s:iterator>
		</select>
		</td>
		<td>&nbsp;&nbsp;&nbsp;POS机终端号: <input type="text" id="posTerminalNo" name="paramMap['posTerminalNo']" class="searchInput" autocomplete="off" style="height: 22px" value="${paramMap['posTerminalNo']}"/></td>
		
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
			<td width="1%"><input type="checkbox" id="selectAll"/></td>
			<th width="7%">订单号</th>
			<th width="12%">支付时间</th>
			<th width="12%">支付网关</th>
			<th width="7%">POS机终端号</th>
			<th width="10%">支付金额(元)</th>
			<th width="5%">收款人</th>
			<th width="5%">解款人</th>
			<th width="5%">审核状态</th>
			<th width="5%">审核人</th>
			<th width="10%">银行交易流水号</th>
			<th width="15%">操作</th>
		</tr>
		<s:iterator value="cashPaymentComboVOList" var="cashPaymentComboVO">
			<tr bgcolor="#ffffff">
				<td><input type="checkbox" name="checkboxObjectIds" value="${cashPaymentComboVO.paymentId}" title="${cashPaymentComboVO.cashAuditStatus}"/></td>
				<td>${cashPaymentComboVO.objectId}</td>
				<td><s:date name="#cashPaymentComboVO.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${cashPaymentComboVO.paymentGatewayZH}</td>
				<td>${cashPaymentComboVO.posTerminalNo}</td>
				<td>
				<s:if test="#cashPaymentComboVO.amount!=null">
					${cashPaymentComboVO.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>${cashPaymentComboVO.receivingPerson}</td>
				<td>${cashPaymentComboVO.cashLiberateMoneyPerson}</td>
				<td>${cashPaymentComboVO.cashAuditStatusZH}</td>
				<td>${cashPaymentComboVO.auditPerson}</td>
				<td>${cashPaymentComboVO.gatewayTradeNo}</td>
				
				<td>
					<s:if test="#cashPaymentComboVO.cashAuditStatus=='UNLIBERATED'">
					<mis:checkPerm permCode="3225">
						<a href="#" onclick="javascript:liberatePay('${cashPaymentComboVO.paymentId}');">解款</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="3226">
							<a href="#" onclick="javascript:cashHandover('${cashPaymentComboVO.paymentId}');">现金交接</a>
					</mis:checkPerm>
					</s:if>
					<s:if test="#cashPaymentComboVO.cashAuditStatus=='LIBERATE'">
					<mis:checkPerm permCode="3227">
						<a href="#" onclick="javascript:liberatePay('${cashPaymentComboVO.paymentId}');">查看解款单</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="3228">
						<a href="#" onclick="javascript:openAudit('${cashPaymentComboVO.paymentId}');">审核</a>
					</mis:checkPerm>
					</s:if>
					<s:if test="#cashPaymentComboVO.cashAuditStatus=='VERIFIED'">
					<mis:checkPerm permCode="3229">
						<a href="#" onclick="javascript:view('${cashPaymentComboVO.paymentId}');">查看</a>
					</mis:checkPerm>
					</s:if>
					<mis:checkPerm permCode="3230">
						<a href="javascript:void(0)" class="showLogDialog .ui-dialog .ui-dialog-content" param="{'objectId':${cashPaymentComboVO.paymentId},'objectType':'CASH_PAYMENT_MONITOR'}">操作日志</a>
					</mis:checkPerm>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td></td>
			<td colspan="2"  align="left">
			<mis:checkPerm permCode="3225">
				<input type="button" value="批量解款" id="batchLiberatePay" />
			</mis:checkPerm>
			<input type="button" value="导出Excel" id="exportExcel"/>
			</td>
			
			<td colspan="1">
			总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="4">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
			</td>
			
			<td colspan="4" align="left"">
				收款金额：<b style="color: red;">${paymentAmountSum/100}元</b>
				解款金额：<b style="color: red;">${liberateAmountSum/100}元</b>
				已审核：<b style="color: red;">${auditPassAmountSum/100}元</b>
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
$("#receivingPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#receivingPerson").val(item.id);	
	}
});
$("#cashLiberateMoneyPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#cashLiberateMoneyPerson").val(item.id);	
	}
});
$("#auditPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#auditPerson").val(item.id);	
	}
});
$("#posTerminalNo").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!payPosAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#posTerminalNo").val(item.id);	
	}
});
function liberatePay(paymentId){
	$("#cashPaymentMonitorDiv").showWindow({
		url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!openLiberatePay.do",
		data:{"paramMap['paymentId']":paymentId}
	});
}
function view(paymentId){
	$("#cashPaymentMonitorDiv").showWindow({
		url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!view.do",
		data:{"paramMap['paymentId']":paymentId}
	});
}
function cashHandover(paymentId){
	$("#cashPaymentMonitorDiv").showWindow({
		url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!openCashHandover.do",
		data:{"paramMap['paymentId']":paymentId}
	});
}
function openAudit(paymentId){
	$("#cashPaymentMonitorDiv").showWindow({
		url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!openAudit.do",
		data:{"paramMap['paymentId']":paymentId}
	});
}
$("#exportExcel").click(function(){
	var url="${basePath}/offlinepaymentmonitor/cash_payment_monitor!exportExcel.do?"+$("#cashPaymentMonitorForm").serialize();
	window.open(url,"exportExcel");
});
$("#batchLiberatePay").click(function (){
	var paymentIds="";
    $("input[name='checkboxObjectIds']:checkbox").each(function(){ 
        if($(this).attr("checked") && $(this).attr("title")=='UNLIBERATED'){
        	paymentIds += "'"+$(this).val()+"',"
        }
    })
    if(paymentIds!=""){
    	paymentIds=paymentIds.substring(0,paymentIds.length-1);
    }
    else{
    	alert("没有需要解款的订单!");
    	return false;
    }
	$("#cashPaymentMonitorDiv").showWindow({
		url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!openLiberatePayBatch.do",
		data:{"paramMap['paymentIds']":paymentIds,"paramMap['cashAuditStatus']":"UNLIBERATED"}
	});
});
$("#selectAll").click(function (){
	if($('#selectAll').attr("checked")){
		$("[name='checkboxObjectIds']").attr("checked",'true');	
	}
	else{
		$("[name='checkboxObjectIds']").removeAttr("checked");
	}
});

$("#reReceivingPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!permUserAutoComplete.do",
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#reReceivingPerson").val(item.id);
	}
});
</script>
</html>
