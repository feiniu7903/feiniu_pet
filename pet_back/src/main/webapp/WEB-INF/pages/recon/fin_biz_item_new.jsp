<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>手动添加流水</h1></title>
</head>
<body>
	<form method="post" action="${basePath}/recon/fin_biz_item!addFinBizItem.do" id="saveFinBizItemForm">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><span class="required">*</span>我方交易金额(元)</td>
				<td><s:textfield name="finBizItem.amountBig" cssClass="required maxlength40" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;" maxlength="50" /></td>
				<td><span class="required">*</span>银行交易金额(元)</td>
				<td><s:textfield name="finBizItem.bankAmountBig" cssClass="required maxlength40" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;" maxlength="50" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>我方交易时间</td>
				<td><s:textfield name="finBizItem.callbackTime" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" cssClass="required Wdate" maxlength="50" /></td>
				<td><span class="required">*</span>银行交易时间</td>
				<td><s:textfield name="finBizItem.transactionTime" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" cssClass="required Wdate" maxlength="50" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>交易类型</td>
				<td>
					<s:select list="transactionTypes" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="finBizItem.transactionType" />
					
				</td>
				<td><span class="required">*</span>对账网关</td>
				<td>
					<s:select list="reconGateways" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="finBizItem.gateway" />
				</td>
			</tr>
			<tr>
				<td><span class="required">*</span>银行对账日期</td>
				<td><s:textfield name="finBizItem.bankReconTime" cssClass="required maxlength40" maxlength="50" /></td>
				<td><span class="required">*</span>创建时间</td>
				<td><s:textfield name="finBizItem.createTime" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" cssClass="required Wdate" maxlength="50" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>备注</td>
				<td><s:textfield name="finBizItem.memo" cssClass="required maxlength40" maxlength="50" /></td>
				<td><span class="required">*</span>订单号</td>
				<td><s:textfield name="finBizItem.orderId" cssClass="required maxlength40" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="50" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>记账状态</td>
				<td>
					<s:select list="glStatuss" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="finBizItem.glStatus" />
				</td>
				<td><span class="required">*</span>记账时间</td>
				<td><s:textfield name="finBizItem.glTime" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" cssClass="required Wdate" maxlength="50" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>费用类型</td>
				<td><s:select list="transactionTypes" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="finBizItem.feeType" />
				</td>
				<td><span class="required">*</span>状态</td>
				<td>
					<s:select list="bizStatuss" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="finBizItem.bizStatus" />
				</td>
			</tr>
			<tr>
				<td><span class="required">*</span>是否取消</td>
				<td><s:radio name="finBizItem.cancelStatus" list="#{'Y':'是','N':'否'}" value="'N'" theme="simple"></s:radio></td>
				<td>取消人</td>
				<td><s:textfield name="finBizItem.cancelUser" cssClass="maxlength40" maxlength="50" /></td>
			</tr>
			<tr>
				<td>取消日期</td>
				<td><s:textfield name="finBizItem.cancelTime" cssClass="maxlength40" maxlength="50" /></td>
				<td>创建人</td>
				<td><s:textfield name="finBizItem.createUser" cssClass="maxlength40" maxlength="50" /></td>
			</tr>
			<tr>
				<td>关联红字流水</td>
				<td><s:textfield name="finBizItem.bizNo" cssClass="maxlength40" maxlength="50" /></td>
				<td>关联对账结果id</td>
				<td><s:textfield name="finBizItem.reconResultId" cssClass="maxlength40" maxlength="50" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input id="saveFinBizItemBtn" name="right_button08Submit" type="button" value="保存" class="right-button08" style="margin-left: 50px"/></td>
			</tr>
		</table>
	</form>
</body>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript">

$(function() {
	$("input[id='finBizItem_bankReconTime']" ).datepicker({dateFormat:'yy-mm-dd'});
	$("input[id='finBizItem_cancelTime']" ).datepicker({dateFormat:'yy-mm-dd'});
	
	$("#saveFinBizItemForm").validateAndSubmit(
			function($form,dt){
				var data=eval("("+dt+")");//alert(data.msg);
				if(data.success){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
		});	

});


$("#saveFinBizItemBtn").click(function(){
	$('#saveFinBizItemForm').submit();
	return;
});

</script>
</html>
