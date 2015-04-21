<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>券码信息管理</title>
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
		$("input[name='bookTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='bookTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='checkinStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='checkinEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#CouponCodeForm [name='performStatus']").val("${performStatus}");
		$("#CouponCodeForm [name='orderStatus']").val("${orderStatus}");
		$("#CouponCodeForm [name='CouponCodeStatus']").val("${CouponCodeStatus}");
		$("#CouponCodeForm [name='distributorInfoId']").val("${distributorInfoId}");
		 $("#chk_all").click(function() {
            $('input[name="chk_list"]').attr("checked",this.checked);
          });
        var a_chk_list = $("input[name='chk_list']");
         a_chk_list.click(function(){
             $("#chk_all").attr("checked",a_chk_list.length == $("input[name='chk_list']:checked").length ? true : false);
         });
	});
	
	function checkForm(){
		var branchId = $("#CouponCodeForm #branchId").val();
		var batchId = $("#CouponCodeForm #batchId").val();
		var productId = $("#CouponCodeForm #productId").val();
		var orderId=$("#CouponCodeForm #orderId").val();
		if(branchId){
			if(!(/^[0-9]+$/.test($.trim(branchId)))){
				$("#branchId").focus();
				alert("类别ID必须为数字");
				return false;
			}else{
				$("#CouponCodeForm #branchId").attr("value",branchId);
			}
		}
		
		if(batchId){
			if(!(/^[0-9]+$/.test($.trim(batchId)))){
				alert("批次号必须为数字");
				return false;
			}else{
				$("#CouponCodeForm #batchId").attr("value",parseInt(batchId));
			}
		}
		
		if(productId){
			if(!(/^[0-9]+$/.test($.trim(productId)))){
				$("#productId").focus();
				alert("产品ID必须为数字");
				return false;
			}else{
				$("#CouponCodeForm #productId").attr("value",productId);
			}
		}
		
		if(orderId){
			if(!(/^[0-9]+$/.test($.trim(orderId)))){
				$("#orderId").focus();
				alert("订单号必须为数字");
				return false;
			}else{
				$("#CouponCodeForm #orderId").attr("value",orderId);
			}
		}
		return true;
	}
	

	
	function checkAndSubmitConponCodeCondition(){
		if(!checkForm()){
			return false;
		}
		var url = '${basePath}/distribution/tuangouorder/CouponCodeList.do';
		$("#CouponCodeForm").attr("action",url);
		document.CouponCodeForm.submit();
	}
	
	function doCancelOneOrder(couponCodeId){
		if(!confirm('是否确认废除该券码')){
			return;
		}
		var url = "${basePath}/distribution/tuangouorder/CancelOneCouponCode.do";
		$.get(url, 
				{"couponCodeId":couponCodeId},
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					if (t.result) {
						alert("废券码成功!");
						location.reload();
						
					} else {
						alert("废券码失败!");
					}
				});
	}
	
	
	function doActivateCouponCode(couponCodeId){
		if(!confirm('是否确认激活该券码')){
			return;
		}
		var url = "${basePath}/distribution/tuangouorder/activateCouponCode.do";
		$.get(url, 
				{"couponCodeId":couponCodeId},
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					if (t.result) {
						alert("激活成功!");
						location.reload();
					} else {
						alert("激活失败!");
					}
				});
	}
	
	function checkBatchCouponCode(){
		if($("input[name='chk_list']:checked").length == 0){
			alert("请选择要废除的券码！");
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
		var url = "${basePath}/distribution/tuangouorder/batchCancelCouponCode.do";
		$.get(url, 
				{"couponCodeIds":orderList},
				function(data, textStatus){
					var t = eval("(" + data + ")"); 
					alert(t.msg);
					location.reload();
					$('input[name="chk_list"]').attr("checked",false);
				});
		
	}
	
	function doExportExcel(){
		if(!checkForm()){
			return false;
		}
		var url = "${basePath}/distribution/tuangouorder/exportExcelCouponCode.do";
		$("#CouponCodeForm").attr("action",url);
		$("#CouponCodeForm").submit();		
	}
	
</script>
</head>
<body>
<form name='CouponCodeForm' id="CouponCodeForm" method='post' action='${basePath}/distribution/tuangouorder/CouponCodeList.do'>
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
			
		</tr>
		<tr>
			<td class="p_label">批次号：</td>
			<td ><input type="text" id="batchId" name="batchId"  value="${batchId}" class="newtext1" maxlength="10"/>
			</td>
			<td class="p_label">券码：</td>
			<td >
				<input type="text" id="contactMan" name="distributionCouponCode"  value="${distributionCouponCode}"/>
			</td>
			<td class="p_label">券码状态：</td>
			<td width="12%">
				<select id="CouponCodeStatus" name="CouponCodeStatus">
					<option value="">全部</option>
					<option value="NORMAL">未预约</option>
					<option value="USED">已预约</option>
					<option value="DESTROYED">已作废</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="p_label">订单号</td>
			<td><input type="text" id="orderId" name="orderId"  value="${orderId}"/></td>
			<td class="p_label">履行状态：</td>
			<td width="12%">
				<select id="performStatus" name="performStatus">
					<option value="">全部</option>
					<option value="UNPERFORMED">未履行</option>
					<option value="PERFORMED">已履行</option>
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
		</tr>
		<tr>
			<td class="p_label">下单时间</td>
			<td colspan="1">
			<input readonly="readonly" name="bookTimeStart" value="${bookTimeStart }" style="width:75px"/>~
			<input readonly="readonly" name="bookTimeEnd" value="${bookTimeEnd }" style="width:75px"/>
			</td>
			<td class="p_label">游玩时间</td>
			<td colspan="2">
			<input readonly="readonly" name="checkinStart" value="${checkinStart }" />~
			<input readonly="readonly" name="checkinEnd" value="${checkinEnd }" />
			</td>
			<td></td>
		</tr>
		<tr>
			<td class="p_label">创建人</td>
			<td><input type="text" id="creator" name="creator"  value="${creator}"/></td>
			<td class="p_label">创建时间</td>
			<td colspan="2">
			<input readonly="readonly" name="createTimeStart" value="${createTimeStart }" />~
			<input readonly="readonly" name="createTimeEnd" value="${createTimeEnd }" />
			</td>
			<td>
			</td>
		</tr>
		<tr>
		<td class="p_label">分销商：</td>
			<td colspan="4">
				<select name="distributorInfoId">
	             <option value=""></option>
	             <s:iterator value="distributorList" var="dist">
		             <option value="${dist.distributorTuanInfoId}">${dist.distributorName }</option>
	             </s:iterator>
	            </select>
			</td>				
			<td ><input type="button" value="查 询" onclick="checkAndSubmitConponCodeCondition()"
				class="right-button08 btn btn-small" id="btnQuery" />
			</td>
		</tr>
	</table>
</form>
<table>
	<tbody>
		<tr>
			<td colspan="2" height="40px"><input type="button" value="批量废券码" onclick="checkBatchCouponCode()"
				class="right-button08 btn btn-small" id="batchCouponCode" /></td>
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
			<th>
			<input type="checkbox" id="chk_all" name="chk_all"/>
			</th>
			<th>批次号</th>
			<th>订单号</th>
			<th width="15%">产品名称</th>
			<th width="10%">类别名称</th>
			<th>分销商</th>
			<th>订单状态</th>
			<th>履行状态</th>
			<th>券码</th>	
			<th>券码状态</th>
			<th>券码有效期</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th width="10%">操作</th>			
		</tr>
		<s:iterator id="couponCode" var="couponCode" value="couponCodeList.items">
			<tr>
				<td>
				<s:if test="#couponCode.status=='NORMAL'">
				<input type="checkbox" name="chk_list" id="_${couponCode.distributionCouponCode}" value="${couponCode.distributionCouponCode}"/>
				</s:if>
				</td>
				<td>${couponCode.batchId }</td>
				<td>${couponCode.orderId }</td>
				<td>${couponCode.productName}</td>
				<td>${couponCode.productBranchName}</td>
				<td>${couponCode.distributorTuanInfo.distributorName}</td>
				<td>${couponCode.orderStatus}</td>
				<td>${couponCode.performStatus}</td>
				<td>${couponCode.distributionCouponCode}</td>
				<td>${couponCode.statusName}</td>
				<td><s:date format="yyyy-MM-dd" name="#couponCode.couponCodeValidTime" /></td>
				<td>${couponCode.operatorName}</td>
				<td><s:date name="#couponCode.orderBatchCreatetime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
				<s:if test="#couponCode.status=='USED'">
					<a href="javascript:showDetailDiv('historyDiv', '${couponCode.orderId}');">查看</a>
					<s:if test="#couponCode.orderStatus=='取消'">
					<a href="javascript:doActivateCouponCode('${couponCode.distributionCouponCode}');">重新激活券码</a>
					</s:if>
				</s:if>
				<s:elseif test="#couponCode.status=='NORMAL'">
					<a href="javascript:doCancelOneOrder('${couponCode.distributionCouponCode}')">废券码</a>
				</s:elseif>
					<a href="#log" class="showLogDialog" param="{'objectType':'COUPON_CODE','objectId':'${couponCode.distributionCouponCode}'}">操作日志</a>
				</td>
			</tr>
		</s:iterator> 
		<tr>
			<td colspan="2" align="left">共有<s:property
					value="couponCodeList.totalResultSize" />个
			</td>
			<td colspan="12" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(couponCodeList)"/>
			</td>
		</tr>
	</tbody>
</table>
<div class="orderpop" id="historyDiv" style="display: none;" href="${basePath}/ord/showHistoryOrderDetail.do"></div>
<div id="bg" class="bg" style="display: none;">
<iframe style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
</iframe>
</div> 
</body>
</html>