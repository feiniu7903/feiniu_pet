<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>批量权码管理</title>
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
		$("input[name='startTime']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='endTime']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
	
	
	
	function checkAndSubmitDistributorCondition(){
		var branchId = $("#branchId").val();
		var contactPhone = $("#contactPhone").val();
		var productId = $("#productId").val();
		if(branchId){
			if(!(/^[0-9]+$/.test($.trim(branchId)))){
				$("#branchId").focus();
				alert("类别ID必须为数字");
				return false;
			}
		}
		if(productId){
			if(!(/^[0-9]+$/.test($.trim(productId)))){
				$("#productId").focus();
				alert("产品ID必须为数字");
				return false;
			}
		}
		if(contactPhone){
			if(!(/^[0-9]+$/.test($.trim(contactPhone)))){
				alert("联系人电话必须为数字");
				return false;
			}
		}
		$("#branchId").val($.trim(branchId));
		$("#productId").val($.trim(productId));
		$("#ordOrderBatch").submit();
	}
	
	function go_creatBatchOrder(){
		window.location = "/super_back/distribution/create.do";
	}
	
	function downloadCodes(batchId){
		$('#distributionBatchId').val(batchId);
		$('#downloadCodes').submit();
	}
	
	function showEditDiv(id) {
		var url = "${basePath}/distribution/edit.do?distributionBatchId=" + id;
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "修改最晚可预约时间",
	        position: 'top',
	        width: 620
		}).width(676).height(376).attr("src",url);
	}
	
	function creatBatchOrder(id) {
		var url = "${basePath}/distribution/tuanBatchCreate.do";
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "批量创建券码",
	        position: 'top',
	        width: 620
		}).width(676).height(315).attr("src",url);
	}
	
</script>
</head>
<body>

<form id="downloadCodes" action="/super_back/distribution/downloadTuanCode.do" method="post">
	<input name="distributionBatchId" type="hidden" id="distributionBatchId"/>
</form>
<form name='ordOrderBatch' id="ordOrderBatch" method='post' action='${basePath}/distribution/distributionTuanBatchList.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label"  width="60px">产品ID：</td>
			<td>
				<input type="text" id="productId" name="productId"  value="${productId}" class="newtext1"/>
			</td>
			<td class="p_label" width="60px">类别ID：</td>
			<td ><input type="text" id="branchId" name="branchId"  value="${branchId}"/></td>
			<td class="p_label">创建时间</td>
			<td colspan="2">
				<input readonly="readonly" class="newtext1" type="text" name="startTime" id="startTime" class="date" value="${startTime}"/> ~ 
				<input readonly="readonly" class="newtext1" type="text" name="endTime" id="endTime"  class="date" value="${endTime}" />
			</td>
		</tr>
		<tr>
			<td class="p_label" >产品名称：</td>
			<td>
				<input type="text" id="productName" name="productName"  value="${productName}" class="newtext1"/>
			</td>
			<td class="p_label" >分销商：</td>
			<td >
			
				<select name="distributionId">
				<option value=0>请选择</option>
					<s:iterator var="dist" value="lists">
						<option value="${dist.distributorTuanInfoId}" <s:if test="distributionId == #dist.distributorTuanInfoId">selected="selected"</s:if> >${dist.distributorName}</option>
					</s:iterator>
				</select>
			</td>
			<td class="p_label">创建人：</td>
			<td >
				<input type="text" id="operatorName" name="operatorName"  value="${operatorName}"/>
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
			<td colspan="2"  height="40px"><input type="button" value="创建新批次券码" onclick="javascript:creatBatchOrder()"
				class="right-button08 btn btn-small" id="btnDistributorQuery" />
			</td>
		</tr>
	</tbody>
</table>
<table class="p_table table_center Padding5">
	<tbody>
		<tr >
			<th width="40px">批次号</th>
			<th width="40px">产品ID</th>
			<th width="50px">产品名称</th>
			<th width="50px">类别ID</th>
			<th width="50px">类别名称</th>
			<th width="40px">分销商</th>
			<th width="50px">券码数量</th>
			<th width="50px">已使用数</th>
			<th width="50px">已作废数</th>
			<th width="50px">有效时间</th>
			<th width="40px">创建人</th>
			<th width="50px">创建时间</th>
			<th >链接地址</th>
			<th width="50px">操作</th>
		</tr>
		<s:iterator id="batch" var="batch" value="distPage.items">
			<tr>
				<td >${batch.distributionBatchId}</td>
				<td>${batch.productId}</td>
				<td>${batch.productName}</td>
				<td>${batch.branchId}</td>
				<td>${batch.branchName}</td>
				<td>${batch.distributorTuanInfoName}</td>
				<td>${batch.ordBatchCount}</td>
				<td>${batch.usedCount}</td>
				<td>${batch.canceledCount}</td>
				<td><s:date format="yyyy-MM-dd" name="#batch.validEndTime" /></td>
				<td>${batch.operatorName}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#batch.orderBatchCreatetime" /></td>
				<td>http://www.lvmama.com/product/distribution-${batch.branchId}-${batch.distributorTuanInfoId}-${batch.distributionBatchId}</td>
				<td>
					<a href="javascript:downloadCodes('${batch.distributionBatchId}');">券码文件下载</a>
					<a href="javascript:showEditDiv('${batch.distributionBatchId}');">修改最晚预约时间</a>
					<a href="#log" class="showLogDialog" param="{'parentType':'ORDER_DISTRIBUTION_TUAN_COUPON','parentId':'${batch.distributionBatchId}'}">操作日志</a>
				</td>
			</tr>
		</s:iterator> 
		<tr>
			<td colspan="5" align="left">共有<s:property value="distPage.totalResultSize" />个
			</td>
			<td colspan="9" align="right" style="text-align:right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(distPage)"/>
			</td>
		</tr>
	</tbody>
</table>

</body>
</html>