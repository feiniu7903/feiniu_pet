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
<form method="post" query_action="${basePath}/recon/fin_recon_result!query.do" 
	excel_action="${basePath}/recon/exportDataReconGLData.do" 
	id="finReconResultForm">
<div>
<div class="main2">
<div class="table_box" id=tags_content_1>
<div class="mrtit3">
<table border="0" cellspacing="0" cellpadding="0" class="search_table">
	<tr>
		<td>&#12288;订单号: <input type="text" name="paramMap['orderId']" value="${paramMap['orderId']}" style="height: 22px;width: 185px;" /></td>
		<td>对账网关: 
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
		<td>对账状态: 
		<select name="paramMap['reconStatus']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="reconStatus" value="reconStatuss">
				<option value="${reconStatus.code}" <s:if test="paramMap['reconStatus']==#reconStatus.code">selected="selected"</s:if>>${reconStatus.cnName}</option>
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
		
		<td>交易来源: 
		<select name="paramMap['transactionSource']" style="height: 22px;width: 185px;">
			<option value="">全部</option>
			<s:iterator var="transactionSource" value="transactionSources">
				<option value="${transactionSource.code}" <s:if test="paramMap['transactionSource']==#transactionSource.code">selected="selected"</s:if>>${transactionSource.cnName}</option>
			</s:iterator>
		</select>
		</td>
	</tr>

	<tr>
		<td>我方对账流水号: <input type="text" name="paramMap['paymentTradeNo']" value="${paramMap['paymentTradeNo']}" style="height: 22px;width: 150px;" /></td>
		<td>银行对账流水号: <input type="text" name="paramMap['bankPaymentTradeNo']" value="${paramMap['bankPaymentTradeNo']}" style="height: 22px;width: 150px;" /></td>
	</tr>
	<tr>
		<td>我方网关交易号: <input type="text" name="paramMap['gatewayTradeNo']" value="${paramMap['gatewayTradeNo']}" style="height: 22px;width: 150px;" /></td>
		<td>银行网关交易号: <input type="text" name="paramMap['bankGatewayTradeNo']" value="${paramMap['bankGatewayTradeNo']}" style="height: 22px;width: 150px;" /></td>
	</tr>
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
		<td></td>
		<td colspan="2">
			<input id="queryDataReconGLDataBtn" name="right_button08Submit" type="button" value="查询" class="right-button08" style="margin-left: 20px"/>
			<input id="openReRecon" name="right_button08Submit" type="button" value="重新对账" class="right-button08" style="margin-left: 20px"/>
			<input id="reGenerateGLDataBtn" name="right_button08Submit" type="button" value="重新入账" class="right-button08" style="margin-left: 20px"/>
			<input id="deleteOldDataReGenerateGLDataBtn" name="right_button08Submit" type="button" value="删除老数据入账" class="right-button08" style="margin-left: 20px"/>
			<input id="exportDataReconGLDataBtn" name="right_button08Submit" type="button" value="导出勾兑数据" class="right-button08" style="margin-left: 20px"/>
			<input id="importCsvDataBtn" name="right_button08Submit" type="button" value="导入CSV文件" class="right-button08" style="margin-left: 20px"/>
		</td>
	</tr>
</table>
</form>
<%-- <form method="post" id="excelForm" enctype="multipart/form-data">
	<h3>导入交行POS机和杉德POS机数据</h3>
	<div>
		选择文件:
		<s:file id="upload_file" name="file" label="文件" onchange="javascript:getFileName(this);" />
		<input type=button class="right-button08" value="导入" id="submitButton" 
		onclick="javascript:return checkForm();" />
	</div>
</form> --%>
</div>
<br />
<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
	
		<tr>
			<th>订单号</th>
			<th>我方对账流水号</th>
			<th>银行对账流水号</th>
			<th>我方网关交易号</th>
			<th>银行网关交易号</th>
			<th>我方交易金额(元)</th>
			<th>银行交易金额(元)</th>
			<th>我方交易时间</th>
			<th>银行交易时间</th>
			<th>交易类型</th>
			<th>网关名称</th>
			<th>对账状态</th>
			<th>对账结果</th>
			<th>对账日期</th>
			<th>备注</th>
			<th>记账状态</th>
			<th>操作</th>
		</tr>
		<s:iterator value="finReconResultList" var="finReconResult">
			<tr bgcolor="#ffffff">
				<td>${finReconResult.orderId}</td>
				<td>${finReconResult.paymentTradeNo}</td>
				
				<td>${finReconResult.bankPaymentTradeNo}</td>
				<td>${finReconResult.gatewayTradeNo}</td>
				<td>${finReconResult.bankGatewayTradeNo}</td>
				<td>
				<s:if test="#finReconResult.amount!=null">
					${finReconResult.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>
				<s:if test="#finReconResult.bankAmount!=null">
					${finReconResult.bankAmount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td><s:date name="#finReconResult.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#finReconResult.transactionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				
				
				<td>${finReconResult.transactionTypeZH}</td>
				<td>${finReconResult.gatewayZH}</td>
				
				<td>${finReconResult.reconStatusZH}</td>
				<td>${finReconResult.reconResult}</td>
				<td><s:date name="#finReconResult.bankReconTime" format="yyyy-MM-dd"/></td>
				<td>${finReconResult.memo}</td>
				<td>${finReconResult.glStatusZH}</td>
				<td>
					<a href="#" onclick="javascript:openModify('${finReconResult.reconResultId}');">修改</a></br>
					<s:if test="#finReconResult.reconStatus=='FAIL'">
						<a href="#" onclick="javascript:processRecon('${finReconResult.bankPaymentTradeNo}','${finReconResult.bankGatewayTradeNo}','${finReconResult.transactionType}','${finReconResult.gateway}');">重新对账</a></br>
					</s:if>
					<a href="javascript:void(0)" class="showLogDialog .ui-dialog .ui-dialog-content" param="{'objectId':${finReconResult.reconResultId},'objectType':'FIN_RECON_RESUTL'}">操作日志</a>
				</td>
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
<div id="finReconResultDiv"/>
<div id="importCsvDiv"></div>
<script type="text/javascript">
$(function() {
	$("input[id='bankReconTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
	$("input[id='bankReconTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
	
	//查询勾兑结果
	$("#queryDataReconGLDataBtn").click(function(){
		var _form = $("#finReconResultForm");
		_form.attr("action",_form.attr("query_action"));
		_form.submit();
	});
	
	//重新入账
	$("#reGenerateGLDataBtn").click(function(){
		$("#reGenerateGLDataBtn").val("请稍后").css("disabled",true);
		$.ajax({
		   type: "POST",
		   url: "${basePath}/recon/fin_recon_result!reGenerateGLData.do",
		   success: function(msg){
			 $("#reGenerateGLDataBtn").val("重新入账").css("disabled",false);
		     alert(msg);
		   }
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
			alert("请填写对账日期");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)=="") || ($.trim(begin_date)=="" && $.trim(end_date)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		if(daysBetween(begin_date,end_date)>31){
			alert("对账日期区间不要超过一个月");
			return false;
		}
		
		var _form = $("#finReconResultForm");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
});

$("#openReRecon").click(function (){
	$("#finReconResultDiv").showWindow({
		url:"${basePath}/recon/fin_recon_result!load.do",
		data:{"target":'re_recon'},
		width:'380px'
	});	
});

//导入csv文件
$("#importCsvDataBtn").click(function() {
	var $div = $('#importCsvDiv');
	$div.load('${basePath}/recon/fin_recon_result!load.do', {"target":'import_csv'}, function() {
		$div.dialog( {
			title : "导入csv",
			width : 1000,
			modal : true
		});
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

//+---------------------------------------------------
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd
//+---------------------------------------------------
function daysBetween(DateOne,DateTwo){
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));
	
	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));
	
	var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
	return Math.abs(cha);
}
</script>
</html>
