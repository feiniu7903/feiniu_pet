<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>super后台-EBooking线路库存修改审核</title>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/ui-components.css" />
<link type="text/css" rel="stylesheet" href="${basePath}/themes/base/jquery.jsonSuggest.css">

<!-- js -->
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.validate.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#createTimeBegin').datepicker({dateFormat : 'yy-mm-dd'});
	$('#createTimeEnd').datepicker({dateFormat : 'yy-mm-dd'});
	
	$("#ebkHousePriceApplyForm").validate({
		rules: {    
			"ebkHousePrice.housePriceId":{
				required:false,
				number:true
			}
		}, 
		messages: {    
			"ebkHousePrice.housePriceId":{
 				number:"请输入数字 "
			}
		}, 
	});
});
	$(function() {
		//查找供应商
		$("#searchSupplierName").jsonSuggest({
			url : "${basePath}supplier/searchSupplier.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comSupplierId").val(item.id);
			}
		});
	});
	function suggestDetail(housePriceId) {
		$("#suggestDetailDiv").load("${basePath }/ebooking/routestock"+
				"/ebkRouteStockApplyDetail.do?ebkHousePrice.housePriceId="+ housePriceId,
			function() {
				var $dlg = $(this);
				$(this).dialog({
						modal : true,
						title : "库存变更申请审核",
						width : 450,
						height : 450,
						buttons : {
							"确认并提交" : function() {
								var $form = $("#ebkRouteStockApplyDetailForm");
								var auditedStatus = $("input:radio[name='auditedStatus']:checked").val();
								if (auditedStatus == null || "" == auditedStatus) {
									alert("请填写审核结果!");
									return false;
								}
								//进行审核动作,确认审核通过或审核不通过.
								$.ajax({
										url : "${basePath }/ebooking/routestock/confirmAuditStatus.do",
										data : $form.serialize(),
										type : "POST",
										dataType : "json",
										success : function(dt) {
											if (dt.success) {
												alert("修改成功!");
												$dlg.dialog("close");
												document.location.reload();
											} else {
												alert(dt.errorMessage);
											}
										}
									});
							},
							"取消" : function() {
								$(this).dialog("close");
							}
						}
					});
			});
	}
</script>
</head>
<body>
	<!--订单处理开始-->
 <ul class="gl_top">
	<form id="ebkRouteStockApplyForm" action="${basePath}/ebooking/routestock/ebkRouteStockApplyList.do" method="post"">
	<table class="newfont06" border="0" cellspacing="0"  >
		<tr height="30">
			<td>申请单号：</td>
			<td>
				 <input type="text" name="ebkHousePrice.housePriceId"  value="${ebkHousePrice.housePriceId}" maxlength="10"/>
			</td>
			<td>申请人：</td>
			<td>
				 <input type="text" name="ebkHousePrice.submitUser" value="${ebkHousePrice.submitUser}" maxlength="10"/>
			</td>
			<td>供应商名称：</td>
			<td>
				 <input type="text" id="searchSupplierName" name="supplierName"  value="${supplierName}"/> 
				<input type="hidden" name="ebkHousePrice.supplierId" id="comSupplierId" />
			</td>
		</tr>
		<tr height="30">
			<td>采购产品ID：</td>
			<td>
				 <input type="text" id="metaProductId" name="ebkHousePrice.metaProductId" value="${ebkHousePrice.metaProductId}"/>
			</td>
			<td>采购产品名称：</td>
			<td>
				 <input type="text" id="productName name" name="ebkHousePrice.productName" value="${ebkHousePrice.productName}"/>
			</td>
			<td>提交时间：</td>
			<td>
				 <input type="text" id="createTimeBegin" name="createTimeBegin" value="${createTimeBegin}"/>~
				 <input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd}"/>
			</td>
		</tr>
		<tr height="30">
			<td align="right" colspan="5">
				<input type="submit" class="u10 btn btn-small" value="查询" />
 			</td>
		</tr>
	 </table>
	 </form>
 </ul>
	<table border="0" cellspacing="0"   class="gl_table">
		<tr>
			<th width="100">申请单号</th>
			<th width="80">申请人</th>
			<th width="80">采购产品ID</th>
			<th width="80">采购产品名称</th>
			<th width="120">提交时间</th>
			<th width="180">供应商名称</th>
			<th width="80">状态</th>
			<th width="30">操作</th>
		</tr>
		<s:iterator value="ebkHousePricePage.items">
			<tr>
				<td><s:property value="housePriceId" /></td>
				<td><s:property value="submitUser" /></td>
				<td><s:property value="metaProductId" /></td>
				<td><s:property value="productName" />(<s:property value="branchName" />)</td>
				<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${supplierName}（ID：${supplierId}）</td>			
				<td><s:property value="auditStatus.cnName" /></td>
				<td><a href="javascript:suggestDetail('<s:property value="housePriceId" />');">查看</a></td>
			</tr>
		</s:iterator>
		<tr>
			<td>总条数：<s:property value="ebkHousePricePage.totalResultSize" />
			</td>
			<td colspan="8" align="right">${ebkHousePricePage.pagination }</td>
		</tr>
	</table>
	<div id="suggestDetailDiv" style="display: none">
		 
	</div>
</body>
</html>
