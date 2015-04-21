<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
	});
</script>
<table class="newfont06" border="0"  cellpadding="0">
	<tr><td></td><td></td></tr>
	<tr>
		<td>审核状态：</td>
		<td><lable style="color: red;">${ebkProdProduct.statusCN}</lable></td>
	</tr>
	<tr><td></td><td></td></tr>
	<s:if test="ebkProdProduct.status=='REJECTED_AUDIT'">
		<tr>
			<td>不通过原因：</td>
			<td></td>
		</tr>
		<tr><td><hr/></td><td><hr/></td></tr>
		<s:iterator value="ebkProdRejectInfoList" var="ebkProdRejectInfo">
			<tr>
				<td><b>${ebkProdRejectInfo.typeCh}：</b></td>
				<td>${ebkProdRejectInfo.message}</td>
			</tr>
			<tr><td></td><td></td></tr>
			<tr><td></td><td></td></tr>
			<tr><td><hr style="color: white;"/></td><td><hr style="color: white;"/></td></tr>
		</s:iterator>
	</s:if>
</table>