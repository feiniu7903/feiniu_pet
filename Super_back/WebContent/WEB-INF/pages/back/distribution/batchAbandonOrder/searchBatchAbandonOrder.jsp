<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>批量废单管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }themes/cc.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath}js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord_div.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		$("input[name='createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='usedTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='usedTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#abandonForm [name='performStatus']").val("${performStatus}");
		$("#abandonForm [name='orderStatus']").val("${orderStatus}");
		
		 $("#chk_all").click(function() {
            $('input[name="chk_list"]').attr("checked",this.checked);
          });
        var a_chk_list = $("input[name='chk_list']");
         a_chk_list.click(function(){
             $("#chk_all").attr("checked",a_chk_list.length == $("input[name='chk_list']:checked").length ? true : false);
         });
	});
	
	function checkForm(){
		var branchId = $("#abandonForm #branchId").val(); 
		var contactPhone = $("#abandonForm #contactPhone").val(); 
		var batchId = $("#abandonForm #batchId").val();
		var productId = $("#abandonForm #productId").val();
		
		if(branchId){
			if(!(/^[0-9]+$/.test($.trim(branchId)))){
				$("#branchId").focus();
				alert("类别ID必须为数字");
				return false;
			}else{
				$("#abandonForm #branchId").attr("value",branchId);
			}
		}
		
		if(contactPhone){
			if(!(/^[0-9]+$/.test($.trim(contactPhone)))){
				alert("联系人电话必须为数字");
				return false;
			}else{
				$("#abandonForm #contactPhone").attr("value",contactPhone);
			}
		}
		
		if(batchId){
			if(!(/^[0-9]+$/.test($.trim(batchId)))){
				alert("批次号必须为数字");
				return false;
			}else{
				$("#abandonForm #batchId").attr("value",parseInt(batchId));
			}
		}
		
		if(productId){
			if(!(/^[0-9]+$/.test($.trim(productId)))){
				$("#productId").focus();
				alert("产品ID必须为数字");
				return false;
			}else{
				$("#abandonForm #productId").attr("value",productId);
			}
		}
		return true;
	}
	
	
	function checkAndSubmitAbandonOrderCondition(){
		if(!checkForm()){
			return false;
		}
		var url = '${basePath}/distribution/getAbandonOrderSearchPage.do';
		$("#abandonForm").attr("action",url);
		document.abandonForm.submit();
	}
	
	function doCancelOneOrder(orderId){
		if(!confirm('是否确认废除该订单')){
			return;
		}
		var url = "${basePath}/distribution/batch/batchCancelOneOrder.do";
		$.get(url, 
				{"orderId":orderId,
			     "cancelReason":encodeURI('批次单条订单废除操作')}, 
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					if (t.result) {
						alert("废单成功!");
						location.reload();
					} else {
						alert("废单失败!");
					}
				});
	}
	
	function checkBatchAbandonOrder(){
		if($("input[name='chk_list']:checked").length == 0){
			alert("请选择要废除的订单！");
			return;
		}
		var a_chk_list = $("input[name='chk_list']");
		var orderList = '';
		var count = 0;
		for (var i = 0; i < a_chk_list.length; i++) {
			var chk = a_chk_list[i];
			if($("#"+chk.id).attr("checked")){
				orderList += $("#"+chk.id).attr("value")+",";
				count=count+1;
			}
		}
		var url = "${basePath}/distribution/batch/batchCancelOrderPage.do?orderIds=" + orderList+"&cancelReason='cancelBatchAbandonOneOrder'";
		
		$("<iframe frameborder='0' id='editDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "批量废单",
	        position: 'top',
	        width: 370
		}).width(350).height(250).attr("src",url);
		
	}
	
	function doExportExcel(){
		if(!checkForm()){
			return false;
		}
		var url = "${basePath}/distribution/batch/batchOrderResult.do";
		$("#abandonForm").attr("action",url);
		$("#abandonForm").submit();		
	}
	
	function doAbandonAllOrder(){
		if(!checkForm()){
			return false;
		}
		var url = "${basePath}/distribution/batch/batchCancelAllOrder.do";
		$.post(url,
				$("#abandonForm").serialize(),
				function(dt){
					var data=eval("(" + dt + ")");
					if(data.result){
						alert(data.msg);
					}else{
						alert(data.msg);
					}
				}
		);
	}
	function doImportExcel(){
		var url = "${basePath}/distribution/batch/upload.do";
		$("<iframe frameborder='0' id='upload'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "导入废单excel",
	        position: 'top',
	        width: 370
		}).width(350).height(250).attr("src",url);
	}
</script>
</head>
<body>
<form name='abandonForm' id="abandonForm" method='post' action='${basePath}/distribution/getAbandonOrderSearchPage.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">产品名称：</td>
			<td ><input type="text" id="productName" name="productName"  value="${productName}" class="newtext1"/>
			</td>
			<td class="p_label">产品ID：</td>
			<td >
				<input type="text" id="productId" name="productId"  value="${productId}"/>
			</td>
			<td class="p_label">类别ID：</td>
			<td><input type="text" id="branchId" name="branchId"  value="${branchId}"/></td>			
			<td colspan="2" />
		</tr>
		<tr>
			<td class="p_label">批次号：</td>
			<td ><input type="text" id="batchId" name="batchId"  value="${batchId}" class="newtext1"/>
			</td>
			<td class="p_label">联系人：</td>
			<td >
				<input type="text" id="contactMan" name="contactMan"  value="${contactMan}"/>
			</td>
			<td class="p_label">联系人手机：</td>
			<td><input type="text" id="contactPhone" name="contactPhone"  value="${contactPhone}"/></td>			
			<td colspan="2"/>
		</tr>
		<tr>
			<td class="p_label">辅助码：</td>
			<td ><input type="text" id="addCode" name="addCode"  value="${addCode}" class="newtext1"/>
			</td>
			<td class="p_label">履行状态：</td>
			<td width="12%">
				<select id="performStatus" name="performStatus">
					<option value="">全部</option>
					<option value="USED">已履行</option>
					<option value="UNUSED">未履行</option>
					<option value="DESTROYED">已废除</option>
				</select>
			</td>
			<td class="p_label">订单状态：</td>
			<td width="12%">
				<select id="orderStatus" name="orderStatus">
					<option value="">全部</option>
					<option value="NORMAL">正常</option>
					<option value="CANCEL">取消</option>
					<option value="FINISHED">完成</option>
				</select>
			</td>
			<td colspan="2"/>
		</tr>
		<tr>
			<td class="p_label">履行时间：</td>
			<td>
			<input readonly="readonly" name="usedTimeStart" value="${usedTimeStart }" />~
			<input readonly="readonly" name="usedTimeEnd" value="${usedTimeEnd }" />
			</td>
			<td class="p_label">创建时间：</td>
			<td colspan="2">
			<input readonly="readonly" name="createTimeStart" value="${createTimeStart }" />~
			<input readonly="readonly" name="createTimeEnd" value="${createTimeEnd }" />
			</td>
			<td colspan="2"><input type="button" value="查 询" onclick="checkAndSubmitAbandonOrderCondition()"
				class="right-button08 btn btn-small" id="btnAbandonOrderQuery" />
			</td>
		</tr>
	</table>
</form>
<table>
	<tbody>
		<tr>
			<td colspan="2" height="40px"><input type="button" value="批量废单" onclick="checkBatchAbandonOrder()"
				class="right-button08 btn btn-small" id="batchAbandonOrder" /></td>
			<td>
			<td>&nbsp;</td>
			<td colspan="2"><input type="button" value="废除所有订单" onclick="doAbandonAllOrder()"
				class="right-button08 btn btn-small" id="abandonAllOrder" /></td>
			<td>
			<td>&nbsp;</td>
			<td colspan="2"><input type="button" value="导出excel" onclick="doExportExcel()"
				class="right-button08 btn btn-small" id="exportExcel" /></td>
			<td>
		</tr>
	</tbody>
</table>
<table class="p_table table_center Padding5">
	<tbody>
		<tr>
			<th><input type="checkbox" id="chk_all" name="chk_all"/></th>
			<th>批次号</th>
			<th>订单号</th>
			<th width="15%">产品名称</th>
			<th width="10%">类别名称</th>
			<th>订单状态</th>
			<th>履行状态</th>	
			<th>联系人</th>
			<th>联系人手机</th>
			<th>辅助码</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th width="10%">操作</th>			
		</tr>
		<s:iterator id="abandonOrder" var="abandonOrder" value="batchList.items">
			<tr>
				<td><input type="checkbox" name="chk_list" id="_${abandonOrder.orderId}" value="${abandonOrder.orderId}"/></td>
				<td >${abandonOrder.batchId}</td>
				<td>${abandonOrder.orderId}</td>
				<td>${abandonOrder.productName}</td>
				<td>${abandonOrder.productBranchName}</td>
				<td>${abandonOrder.orderStatus}</td>
				<td>${abandonOrder.performStatusCnName}</td>
				<td>${abandonOrder.contacts}</td>
				<td>${abandonOrder.contactsPhone }</td>
				<td>${abandonOrder.addCode}</td>
				<td>${abandonOrder.creatorName }</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#abandonOrder.createtime" /></td>
				<td>
					<a href="javascript:showDetailDiv('historyDiv', '${abandonOrder.orderId}');">查看</a>&nbsp;&nbsp;
					<s:if test="#abandonOrder.orderStatus == '正常' && #abandonOrder.performStatus != 'USED'">
					<a href="javascript:doCancelOneOrder('${abandonOrder.orderId}');">废单</a>
					</s:if>					
					 &nbsp;&nbsp;
				</td>
			</tr>
		</s:iterator> 
		<tr>
			<td colspan="6" align="left">共有<s:property
					value="batchList.totalResultSize" />个
			</td>
			<td colspan="7" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(batchList)"/>
			</td>
		</tr>
	</tbody>
</table>
<div class="orderpop" id="historyDiv" style="display: none;"
href="${basePath}/ord/showHistoryOrderDetail.do">
</div>
<div id="bg" class="bg" style="display: none;">
<iframe
style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
</iframe>
</div> 
</body>
</html>