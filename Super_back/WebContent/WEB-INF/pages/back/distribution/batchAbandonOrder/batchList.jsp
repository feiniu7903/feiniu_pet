<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>批量订单管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		$("input[name='createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
	
	
	
	function checkAndSubmitDistributorCondition(){
		var branchId = $("#branchId").val();
		var contactPhone = $("#contactPhone").val();
		
		if(branchId){
			if(!(/^[0-9]+$/.test($.trim(branchId)))){
				$("#branchId").focus();
				alert("类别ID必须为数字");
				return false;
			}
		}
		
		if(contactPhone){
			if(!(/^[0-9]+$/.test($.trim(contactPhone)))){
				alert("联系人电话必须为数字");
				return false;
			}
		}
		
		$("#ordOrderBatch").submit();
	}
	
	function go_creatBatchOrder(){
		window.location = "/super_back/distribution/create.do";
	}
	function searchResult(batchCount,batchId){
		$('#result').showWindow({width:400,data:{"batchId":batchId}});
	}
	function downloadCodes(batchId){
		$('#downloadByBatchId').val(batchId);
		$('#downloadCodes').submit();//.showWindow({width:400,data:{"batchId":batchId}});
	}
	
	function showBatchCancelDiv(id) {
		var url = "${basePath}/distribution/batch/batchCancelIndex.do?batchId=" + id;
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "批量废单",
	        position: 'top',
	        width: 620
		}).width(676).height(376).attr("src",url);
	}
	
	function creatBatchOrder(id) {
		var url = "${basePath}/distribution/create.do";
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "批量生成订单",
	        position: 'top',
	        width: 620
		}).width(676).height(476).attr("src",url);
	}
	function updateBatch(id,valid){
		var con = "确定关闭此批次任务吗";
		if("true" == valid){
			con = "确定开启此批次任务吗";
		}
		if(confirm(con)){
			$.ajax({
				url:"/super_back/distribution/batch/updateBatchValid.do",
				type:"POST",
				data:{"batchId":id,"cancelReason":valid},
				success:function(dt){
					alert(dt);
					window.location = "/super_back/distribution/batch/batchList.do";
				}
			});
		}
	}
</script>
</head>
<body>
<div id="result" url="/super_back/distribution/batch/getBatchResult.do" title="批量生成订单结果">
</div>
<form id="downloadCodes" action="/super_back/distribution/batch/downloadPasscode.do" method="post">
	<input name="batchId" type="hidden" id="downloadByBatchId"/>
</form>
<form name='ordOrderBatch' id="ordOrderBatch" method='post' action='${basePath}/distribution/batch/batchList.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">类别ID：</td>
			<td>
				<input type="text" id="branchId" name="branchId"  value="${branchId}" class="newtext1"/>
			</td>
			<td class="p_label">联系人手机：</td>
			<td colspan="2"><input type="text" id="contactPhone" name="contactPhone"  value="${contactPhone}"/></td>	
		</tr>
		<tr>
			<td class="p_label">联系人：</td>
			<td >
				<input type="text" id="contactMan" name="contactMan"  value="${contactMan}"/>
			</td>
			<td class="p_label">创建时间</td>
			<td>
				<input readonly="readonly" class="newtext1" type="text" name="createTimeStart" id="createTimeStart" class="date" value="${createTimeStart}"/> ~ 
				<input readonly="readonly" class="newtext1" type="text" name="createTimeEnd" id="createTimeEnd"  class="date" value="${createTimeEnd}" />
			</td>
			<td>
				<input type="button" value="查 询" onclick="checkAndSubmitDistributorCondition()" class="right-button08 btn btn-small" id="btnDistributorQuery" />
			</td>	
		</tr>
		
	</table>
</form>
<table>
	<tbody>
		<tr>
			<td colspan="2"  height="40px"><input type="button" value="创建新批次订单" onclick="javascript:creatBatchOrder()"
				class="right-button08 btn btn-small" id="btnDistributorQuery" /></td>
			<td>
		</tr>
	</tbody>
</table>
<table class="p_table table_center Padding5">
	<tbody>
		<tr >
			<th>批次号</th>
			<th>产品ID</th>
			<th width="15%">产品名称</th>
			<th>类别ID</th>
			<th width="10%">类别名称</th>
			<th>联系人</th>
			<th>联系人手机</th>
			<th>订单数量</th>
			<th>分销商</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th width="10%">任务原因</th>
			<th>操作</th>
		</tr>
		<s:iterator id="batch" var="batch" value="batchList.items">
			<tr>
				<td >${batch.batchId}</td>
				<td>${batch.productId}</td>
				<td>${batch.productName}</td>
				<td>${batch.productBranchId}</td>
				<td>${batch.productBranchName}</td>
				<td>${batch.contacts}</td>
				<td>${batch.contactsPhone}</td>
				<td>${batch.batchCount}</td>
				<td>${batch.distributorTuanInfo.distributorName}</td>
				<td>${batch.creatorName}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#batch.createtime" /></td>
				<td>${batch.reson}</td>
				<td>
					<a href="javascript:searchResult('${batch.batchCount}','${batch.batchId}');">批量结果</a>
					<a href="javascript:downloadCodes('${batch.batchId}');">辅助码文件下载</a>
					<a href="javascript:showBatchCancelDiv('${batch.batchId}');">批量废单</a>
					<s:if test="#batch.status=='BATCHWAITTING'">
						<s:if test="#batch.isValid=='true'"><a href="javascript:updateBatch('${batch.batchId}','false');">关闭批次任务</a></s:if>
						<s:else><a href="javascript:updateBatch('${batch.batchId}','true');">开启批次任务</a></s:else>
					</s:if>
					<a href="#log" class="showLogDialog"
									param="{'parentType':'ORDER_BATCH','parentId':'${batch.batchId}'}">操作日志</a>
				</td>
			</tr>
		</s:iterator> 
		<tr>
			<td colspan="5" align="left">共有<s:property value="batchList.totalResultSize" />个
			</td>
			<td colspan="8" align="right" style="text-align:right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(batchList)"/>
			</td>
		</tr>
	</tbody>
</table>

</body>
</html>