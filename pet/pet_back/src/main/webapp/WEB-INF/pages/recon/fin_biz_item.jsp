<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>

<link rel="stylesheet" type="text/css" href="/pet_back/css/base/jquery.jsonSuggest.css"/>

<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<link href="${basePath}/css/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${basePath}/themes/cc.css" />
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>

<link rel="stylesheet" href="${basePath}/js/recon/My97DatePicker/skin/default/datepicker.css"/>
<link rel="stylesheet" href="${basePath}/js/recon/My97DatePicker/skin/WdatePicker.css"/>
<script src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script src="${basePath}/js/recon/My97DatePicker/lang/en.js"></script>

<style type="text/css">
.ui-dialog .ui-dialog-content { position: relative; border: 0; padding: .5em 1em; background: none; overflow: visible; zoom: 1; }
</style>
</head>
<body>
<form method="post" action="${basePath}/recon/fin_biz_item!query.do" id="finBizItemForm">
<div>
<div class="main2">
<div class="table_box" id=tags_content_1>
<div class="mrtit3">
<table border="0" cellspacing="0" cellpadding="0" class="search_table">
	<tr>
		<td>&#12288;流水号: <input type="text" name="paramMap['bizItemId']" value="${paramMap['bizItemId']}" style="height: 22px;width: 185px;" /></td>
		<td>网关: 
		<select name="paramMap['gateway']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="reconGateway" value="reconGateways">
				<option value="${reconGateway.code}" <s:if test="paramMap['gateway']==#reconGateway.code">selected="selected"</s:if>>${reconGateway.cnName}</option>
			</s:iterator>
		</select>
		</td>
		
		<td>对账日期: 
		<input name="paramMap['bankReconTimeStart']" type="text" value="${paramMap['bankReconTimeStart']}" id="bankReconTimeStart" style="height: 22px;width: 85px;"/>
		~
		<input name="paramMap['bankReconTimeEnd']" type="text" value="${paramMap['bankReconTimeEnd']}" id="bankReconTimeEnd" style="height: 22px;width: 85px;"/>
		</td>
	</tr>
	<tr>
		<td>&#12288;订单号: <input type="text" name="paramMap['orderId']" value="${paramMap['orderId']}" style="height: 22px;width: 185px;" /></td>
		
		<td>流水状态: 
		<select name="paramMap['bizStatus']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="bizStatus" value="bizStatuss">
				<option value="${bizStatus.code}" <s:if test="paramMap['bizStatus']==#bizStatus.code">selected="selected"</s:if>>${bizStatus.cnName}</option>
			</s:iterator>
		</select>
		</td>
		
		<td>交易类型: 
		<select name="paramMap['transactionType']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="transactionType" value="transactionTypes">
				<option value="${transactionType.code}" <s:if test="paramMap['transactionType']==#transactionType.code">selected="selected"</s:if>>${transactionType.cnName}</option>
			</s:iterator>
		</select>
		</td>
		
	</tr>
	<!-- 
	<tr>
		<td>我方对账流水号: <input type="text" name="paramMap['paymentTradeNo']" value="${paramMap['paymentTradeNo']}" style="height: 22px;width: 150px;" /></td>
		<td>银行对账流水号: <input type="text" name="paramMap['bankPaymentTradeNo']" value="${paramMap['bankPaymentTradeNo']}" style="height: 22px;width: 150px;" /></td>
	</tr>
	<tr>
		<td>我方网关交易号: <input type="text" name="paramMap['gatewayTradeNo']" value="${paramMap['gatewayTradeNo']}" style="height: 22px;width: 150px;" /></td>
		<td>银行网关交易号: <input type="text" name="paramMap['bankGatewayTradeNo']" value="${paramMap['bankGatewayTradeNo']}" style="height: 22px;width: 150px;" /></td>
	</tr>
	 -->
	<tr>
		<td>我方交易金额(元): 
		<input type="text" name="paramMap['amountStart']" value="${paramMap['amountStart']}" style="height: 22px;width: 85px;" />
		~
		<input type="text" name="paramMap['amountEnd']" value="${paramMap['amountEnd']}" style="height: 22px;width: 85px;" />
		</td>
		<td>银行交易金额(元): 
		<input type="text" name="paramMap['bankAmountStart']" value="${paramMap['bankAmountStart']}" style="height: 22px;width: 85px;" />
		~
		<input type="text" name="paramMap['bankAmountEnd']" value="${paramMap['bankAmountEnd']}" style="height: 22px;width: 85px;" />
		</td>
		<td>记账状态: 
		<select name="paramMap['glStatus']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="glStatus" value="glStatuss">
				<option value="${glStatus.code}" <s:if test="paramMap['glStatus']==#glStatus.code">selected="selected"</s:if>>${glStatus.cnName}</option>
			</s:iterator>
		</select>
		</td>
		<input type="hidden" name="paramMap['orderby']" value="TRANSACTION_TIME"/>
		<input type="hidden" name="paramMap['order']" value="DESC"/>
	</tr>
	<tr>
		<td></td><td></td>
		<td>
			<input name="right_button08Submit" type="submit" value="查询" class="right-button08" style="margin-left: 20px"/>
			<input id="addFinBizItemBtn" name="right_button08Submit" type="button" value="添加流水" class="right-button08" style="margin-left: 20px"/>
		</td>
	</tr>
</table>
</form>
</div>
<br />

<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
	
		<tr>
			<th>流水号</th>
			<th>我方交易金额(元)</th>
			<th>银行交易金额(元)</th>
			<th>我方交易时间</th>
			<th>银行交易时间</th>
			<th>交易类型</th>
			<th>网关CODE</th>
			<th>银行对账日期</th>
			<th>创建时间</th>
			<th>备注</th>
			<th>订单号</th>
			<th>记账状态</th>
			<th>记账时间</th>
			<th>费用类型</th>
			<th>状态</th>
			<th>是否取消</th>
			<th>取消人</th>
			<th>取消日期</th>
			<th>创建人</th>
			<th>关联红字流水</th>
			<th>关联对账结果id</th>
		</tr>
		<s:iterator value="finBizItemList" var="finBizItem">
			<tr bgcolor="#ffffff">
				<td>${finBizItem.bizItemId}</td>
				<td>
				<s:if test="#finBizItem.amount!=null">
					${finBizItem.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>
				<s:if test="#finBizItem.bankAmount!=null">
					${finBizItem.bankAmount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td><s:date name="#finBizItem.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#finBizItem.transactionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.transactionTypeZH}</td>
				<td>${finBizItem.gatewayZH}</td>
				<td><s:date name="#finBizItem.bankReconTime" format="yyyy-MM-dd"/></td>
				<td><s:date name="#finBizItem.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.memo}</td>
				<td>${finBizItem.orderId}</td>
				<td>${finBizItem.glStatusZH}</td>
				<td><s:date name="#finBizItem.glTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.transactionTypeZH}</td>
				<td>${finBizItem.bizStatusZH}</td>
				<td>
					<s:if test='#finBizItem.cancelStatus=="Y"'>是</s:if>
					<s:elseif test='#finBizItem.cancelStatus=="N"'>否</s:elseif>
				</td>
				<td>${finBizItem.cancelUser}</td>
				<td><s:date name="#finBizItem.cancelTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.createUser}</td>
				<td>${finBizItem.bizNo}</td>
				<td>${finBizItem.reconResultId}</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2">
			总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="6">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
			</td>
			
			<td colspan="8" align="left"">
				我方交易总金额：<b style="color: red;">${transactionAmountSum}元</b>
				银行交易总金额：<b style="color: red;">${transactionBankAmountSum}元</b>
			</td>
		</tr>
</table>
</div>
</div>
</div>
</body>
<div id="finBizItemDiv" />
<script type="text/javascript">
$(function() {
	$("input[id='bankReconTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
	$("input[id='bankReconTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
	
	//添加流水
	$("#addFinBizItemBtn").click(function (){
		$("#finBizItemDiv").showWindow({
			url:"${basePath}/recon/fin_biz_item!load.do",
			data:{"target":'new'},
			width:'680px'
		});	
	});
	
	$("#deleteOldDataReGenerateGLDataBtn").click(function(){
		$("#deleteOldDataReGenerateGLDataBtn").val("请稍后").css("disabled",true);
		$.ajax({
		   type: "POST",
		   url: "${basePath}/recon/fin_recon_result!deleteOldDataReGenerateGLData.do",
		   success: function(msg){
			 $("#deleteOldDataReGenerateGLDataBtn").val("删除老数据入账").css("disabled",false);
		     alert(msg);
		   }
		});
	});
	//导出勾兑数据
	$("#exportDataReconGLDataBtn").click(function(){
		var begin_date = $("#bankReconTimeStart").val();
		var end_date = $("#bankReconTimeEnd").val();
		
		if($.trim(begin_date)=="" || $.trim(end_date)==""){
			alert("请填写对账日期，区间不要超过一个月");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)=="") || ($.trim(begin_date)=="" && $.trim(end_date)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		var _form = $("#finReconResultForm");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
});

function processRecon(bankPaymentTradeNo,bankGatewayTradeNo,transactionType,gateway){
	$.ajax({
		type:"POST", 
		url:'http://super.lvmama.com/payment/pay/recon/accountReconSingle.do' + '?random=' + Math.random(), 
		data:{"bankPaymentTradeNo":bankPaymentTradeNo,"bankGatewayTradeNo":bankGatewayTradeNo,"transactionType":transactionType,"gateway":gateway},
		async: false
	});
	alert('重新对账完毕!');
}
function openModify(reconResultId){
	$("#finReconResultDiv").showWindow({
		url:"${basePath}/recon/fin_recon_result!load.do",
		data:{"target":'modify',"reconResultId":reconResultId},
		width:'300px'
	});	
}
</script>
</html>
