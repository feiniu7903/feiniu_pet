<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><h1>修改对账结果</h1></title>
</head>
<body>
	<form method="post" action="" id="modifyForm">
		<table class="cg_xx" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>对账状态: 
				<select name="finReconResult.reconStatus" style="height: 22px;width: 185px;">
					<option value="SUCCESS" <s:if test="'SUCCESS'==finReconResult.reconStatus">selected="selected"</s:if>>对账成功</option>
					<option value="FAIL" <s:if test="'FAIL'==finReconResult.reconStatus">selected="selected"</s:if>>对账失败</option>
					<!-- 
					<option value="">全部</option>
					<s:iterator var="reconStatus" value="reconStatuss">
						<option value="${reconStatus.code}" <s:if test="finReconResult.reconStatus==#reconStatus.code">selected="selected"</s:if>>${reconStatus.cnName}</option>
					</s:iterator>
					 -->
				</select>
				</td>
			</tr>
			<tr>	
				<td>交易来源: 
				<select name="finReconResult.transactionSource" style="height: 22px;width: 185px;">
					<s:iterator var="transactionSource" value="transactionSources">
						<option value="${transactionSource.code}" <s:if test="finReconResult.transactionSource==#transactionSource.code">selected="selected"</s:if>>${transactionSource.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>	
				<td>记账状态: 
				<select name="finReconResult.glStatus" style="height: 22px;width: 185px;">
					<s:iterator var="glStatus" value="glStatuss">
						<option value="${glStatus.code}" <s:if test="finReconResult.glStatus==#glStatus.code">selected="selected"</s:if>>${glStatus.cnName}</option>
					</s:iterator>
				</select>
				</td>
			</tr>
			<tr>	
				<td>备注: <textarea rows="4" cols="30" name="finReconResult.memo" value="${finReconResult.memo}" /></td>
			</tr>
			<tr>
				<input type="hidden" name="finReconResult.reconResultId" id="finReconResult.reconResultId" value="${finReconResult.reconResultId}"/>
				<td><input id="modifySumit" name="right_button08Submit" type="button" value="修改" class="right-button08" style="margin-left: 50px"/></td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
$("#modifySumit").click(function(){
	if(confirm('您确定要修改?')){
		$('#modifySumit').attr("disabled",true);
		$.ajax({
			type:"POST", 
			url:'${basePath}/recon/fin_recon_result!modify.do' + '?random=' + Math.random(), 
			data:$("#modifyForm").serialize(), 
			async: false, 
			success:function (result) {
				alert(eval(result));
				$('#finReconResultDiv').dialog('close');
				$('#finReconResultForm').submit();
			}
		});
		
	}
});
</script>
</html>
