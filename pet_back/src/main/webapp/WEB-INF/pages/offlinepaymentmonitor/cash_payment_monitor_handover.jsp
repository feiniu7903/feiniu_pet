<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>现金交接</h1></title>
</head>
<body>
	<form method="post" action="" id="handoverForm" style="height: 320px">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>交接金额: 
					<s:if test="cashPaymentComboVO.amount!=null">
					${cashPaymentComboVO.amount/100}
					</s:if>
					<s:else>
						0
					</s:else>
				</td>
			</tr>
			<tr>
				<td>交接人: <input name="paramMap['oldReceivingPerson']" value="${cashPaymentComboVO.receivingPerson}" type="text" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>接收人:
				<input type="text" id="reReceivingPerson" name="cashPaymentComboVO.receivingPerson" class="searchInput" autocomplete="off" style="height: 22px"/>
				</td>
			</tr>
			
			<tr>
				<input type="hidden" value="${cashPaymentComboVO.paymentDetailId}" name="cashPaymentComboVO.paymentDetailId"/>
				<input type="hidden" value="${cashPaymentComboVO.paymentId}" name="cashPaymentComboVO.paymentId"/>
				<td colspan="2" align="center"">
					<input name="right_button08Submit" type="button" value="确定" class="right-button08" id="handoverSubmit"/>
					<input name="right_button08Submit" type="button" value="取消" class="right-button08" onclick="$('#cashPaymentMonitorDiv').dialog('close')" />
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
$("#handoverSubmit").click(function(){
	if($('#reReceivingPerson').val()==''){
		alert('接收人不可为空!');
		return ;
	}
	$.ajax({
			type:"POST", 
			url:'${basePath}/offlinepaymentmonitor/cash_payment_monitor!handover.do' + '?random=' + Math.random(), 
			data:$("#handoverForm").serialize(), 
			async: false, 
			success:function (result) {
				alert(eval(result));
				$('#cashPaymentMonitorDiv').dialog('close');
				$('#cashPaymentMonitorForm').submit();
			}
	});
});
$("#reReceivingPerson").jsonSuggest({
	url:"${basePath}/offlinepaymentmonitor/cash_payment_monitor!permUserAutoComplete.do"+ '?random=' + Math.random(),
	maxResults: 20,
	minCharacters:1,
	onSelect:function(item){
		$("#reReceivingPerson").val("");
		$("#reReceivingPerson").val(item.id);
	}
});
</script>
</html>
