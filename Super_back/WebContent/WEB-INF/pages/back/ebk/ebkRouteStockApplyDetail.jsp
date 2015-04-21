<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" ></link>
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" ></link>
<link rel="stylesheet" type="text/css" href="/super_back/style/houtai.css" />
<style type="text/css">
	.calculateHours {
		width: 20px;
	}
	.hourSpan {
		display: none;
	}
</style>
<script type="text/javascript">
$(function() {
	var $time_price_dlg = null;
	var current_time_price_param = null;
	$(".showTimePrice").click(function() {
		var param = $(this).attr("param");
		current_time_price_param = eval("(" + param + ")");

		if ($time_price_dlg == null) {
			$time_price_dlg = $("<div style='display:none' class='time_price_dlg_div'>");
			$time_price_dlg.appendTo($("body"));
		}

		$time_price_dlg.load("/super_back/meta/toMetaTimePrice.do",
				current_time_price_param, function() {
					$time_price_dlg.dialog( {
						title : "时间价格表",
						width : 1000,
						modal : true
					});
				});
	});
	
	$("input:radio[name='auditedStatus']").change(function() {
		if($(this).val() == "PASSED_AUDIT") {
			$("span.hourSpan").show();
		} else {
			$("span.hourSpan").hide();
		}
	});
})
</script>
</head>
	<body>	 
         <div style='height:20px;font-size:18px; '>
         <strong>申请单号:</strong>
         <span >${ebkHousePrice.housePriceId }</span>&nbsp;&nbsp;&nbsp;
         <strong>审核状态:</strong>
         <span style="color:red;">${ebkHousePrice.auditStatus.cnName}</span></div>
         <a param="{'metaBranchId':${ebkHousePrice.metaBranchId },'editable':false}" class="showTimePrice" tt="META_PRODUCT" href="#timePrice">查看时间价格</a>
         <form id="ebkRouteStockApplyDetailForm">
     		<table class="gl_table" >
		 		<input type="hidden" name="housePriceId" id="housePriceId" value="${ebkHousePrice.housePriceId }"/>
			 	<tr>
			 		<td>供应商名称:</td><td><s:property value="ebkHousePrice.supSupplier.supplierName" />（ID：<s:property value="ebkHousePrice.supSupplier.supplierId" />）</td>
			 	</tr>
			 	<tr>
			 		<td>供应商电话:</td><td><s:property value="ebkHousePrice.supSupplier.mobile" /></td>
			 	</tr>
			 	<tr>
			 		<td>采购产品:</td><td><s:property value="ebkHousePrice.productName" />(<s:property value="ebkHousePrice.metaProductBranchName" />)</td>
			 	</tr>
			 	<tr>
			 		<td>日期范围:</td><td><s:date name="ebkHousePrice.startDate" format="yyyy-MM-dd"/> 至 <s:date name="ebkHousePrice.endDate" format="yyyy-MM-dd"/></td>
			 	</tr>
			 	<tr>
			 		<td>增减库存数:</td>
			 		<td>
			 			<s:if test="ebkHousePrice.stockAddOrMinus!=0"><s:if test="ebkHousePrice.isAddDayStock=='false'">-</s:if></s:if>	
			 			<s:property value="ebkHousePrice.stockAddOrMinus" />
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>是否超卖:</td>
			 		<td>
				 		<s:if test="ebkHousePrice.isOverSale=='true'">是</s:if>
				 		<s:elseif test="ebkHousePrice.isOverSale=='false'">否</s:elseif>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>是否关班:</td>
			 		<td>
				 		<s:if test="ebkHousePrice.isStockZero=='true'"><font color="red">是</font></s:if>
   		   				<s:else>否</s:else>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>提交人:</td><td><s:property value="ebkHousePrice.submitUser" /></td>
			 	</tr>
			 	<tr>
			 		<td>提交日期:</td><td><s:date name="ebkHousePrice.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 	</tr>
			 </table>
			 <table class="gl_table">
			 	<s:if test="ebkHousePrice.auditStatus.cnName=='待审核'">
			 	<tr>
			 		<td>审核结果:<span class="require">[*]</span></td>
			 		<td>
			 			<s:iterator value="suggestAuditStatusList" id="cl">
	 						<input type="radio" name="auditedStatus" value="<s:property value="code"/>" cssClass="radio" <s:if test='#cl.isChecked()'>checked</s:if>/><s:property value="name"/>
						</s:iterator>
			 		</td>
			 	</tr>
			 	</s:if>
			 	<s:else>
			 	<tr>
			 		<td>审核结果:</td><td><s:property value="ebkHousePrice.auditStatus.cnName"/></td>
			 	</tr>
			 	 <tr>
			 		<td>审核人:</td><td><s:property value="ebkHousePrice.confirmUser" /></td>
			 	</tr>
			 	<tr>
			 		<td>审核时间:</td><td><s:date name="ebkHousePrice.confirmTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 	</tr>
			 	</s:else>
			 </table>
		 </form>
	</body>
</html>
