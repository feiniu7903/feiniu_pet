<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>EBK供应商产品审核-提交审核结果</title>
<script type="text/javascript">
	$(function(){
		$('#onlineDateBegin').datepicker({dateFormat : 'yy-mm-dd'});
		$('#onlineDateEnd').datepicker({dateFormat : 'yy-mm-dd'});
		
		$('input:radio[name="auditRadio"]').click(function (){
			var val=$('input:radio[name="auditRadio"]:checked').val();
			if(val=='pass'){
				$("#nopass").hide().nextAll().hide();
				$("#onlineTime").show();
			}
			else{
				$("#nopass").show();
				$("#pass").nextAll().show();
				$("#onlineTime").hide();
			}
		});
		$("#nopass").hide().nextAll().hide();
	});
</script>
</head>
<body>
	<form id="prodApprovalAuditSubmitForm" action="${basePath}/ebooking/prod/prodAuditedList.do" method="post">
	<input type="hidden" name="ebkProdProductId" value="${ebkProdProductId}"/>
	<div class="gl_top" style="width: 400px;height: 410px;padding: 10px">
	<table class="auditSubmitTableStyle">
		<tr id="pass">
			<td><input type="radio" id="auditRadioPass" name="auditRadio" checked="checked" value="pass"/>通过</td>
			<td></td>
		</tr>
		<tr id="onlineTime">
			<td>上下线时间：</td>
			<td>
				<input style="width: 120px;margin-right: 0px;" type="text" id="onlineDateBegin" name="onlineDateBegin" value="${param.onlineDateBegin}"/>
				<label>~</label>
				<input style="width: 120px" type="text" id="onlineDateEnd" name="onlineDateEnd" value="${param.onlineDateEnd}"/>
			</td>
		</tr>
		
		<tr>
			<td><input type="radio" name="auditRadio" value="nopass"/>不通过</td>
			<td></td>
		</tr>
		<tr id="nopass">
			<td><input type="checkbox" id="EBK_AUDIT_TAB_BASE_CHECKBOX" name="ebkProdRejectInfoList[0].type" value="EBK_AUDIT_TAB_BASE"/>基本信息</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_BASE" name="ebkProdRejectInfoList[0].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_RECOMMEND_CHECKBOX"  name="ebkProdRejectInfoList[1].type" value="EBK_AUDIT_TAB_RECOMMEND"/>产品推荐及特色</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_RECOMMEND" name="ebkProdRejectInfoList[1].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_TRIP_CHECKBOX"  name="ebkProdRejectInfoList[2].type" value="EBK_AUDIT_TAB_TRIP"/>行程描述</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_TRIP" name="ebkProdRejectInfoList[2].message" maxlength="200"/></td>
		</tr>
		
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_COST_CHECKBOX"  name="ebkProdRejectInfoList[3].type" value="EBK_AUDIT_TAB_COST"/>费用说明</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_COST" name="ebkProdRejectInfoList[3].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_PICTURE_CHECKBOX"  name="ebkProdRejectInfoList[4].type" value="EBK_AUDIT_TAB_PICTURE"/>产品图片</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_PICTURE" name="ebkProdRejectInfoList[4].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_TRAFFIC_CHECKBOX"  name="ebkProdRejectInfoList[5].type" value="EBK_AUDIT_TAB_TRAFFIC"/>发车信息</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_TRAFFIC" name="ebkProdRejectInfoList[5].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_OTHER_CHECKBOX"  name="ebkProdRejectInfoList[6].type" value="EBK_AUDIT_TAB_OTHER"/>其他条款</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_OTHER" name="ebkProdRejectInfoList[6].message" maxlength="200"/></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="EBK_AUDIT_TAB_TIME_PRICE_CHECKBOX"  name="ebkProdRejectInfoList[7].type" value="EBK_AUDIT_TAB_TIME_PRICE"/>价格/库存</td>
			<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_TIME_PRICE" name="ebkProdRejectInfoList[7].message" maxlength="200"/></td>
		</tr>
		<s:if test="'ABROAD_PROXY'==ebkProdProduct.productType">
			<tr>
				<td><input type="checkbox" id="EBK_AUDIT_TAB_RELATION_CHECKBOX"  name="ebkProdRejectInfoList[8].type" value="EBK_AUDIT_TAB_RELATION"/>关联销售产品</td>
				<td><textarea rows="1" style="width: 250px;height:20px;" id="EBK_AUDIT_TAB_RELATION" name="ebkProdRejectInfoList[8].message" maxlength="200"/></td>
			</tr>
		</s:if>
	</table>
	<br/>
	<br/>
	<span style="color:red;" ><b>修改后的产品信息，审核通过后，需通知商务审核人员（大区经理）进行再次审核上线。</b></span>
	</div>
	</form>
</body>
</html>