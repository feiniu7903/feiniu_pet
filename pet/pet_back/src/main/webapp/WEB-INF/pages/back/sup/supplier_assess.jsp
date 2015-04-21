<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商考核</title>
</head>
<body>
<div>
	供应商最终得分：${supplier.assessPoints}
</div>
<form action="${basePath}/sup/assess/saveAssess.do" method="post" onsubmit="return false">
<input type="hidden" name="supplierAssess.supplierId" value="${supplierId}"/>
	<table style="width:100%" class="cg_xx" cellspacing="0" cellpadding="0">
		<tr>
			<td><em>分数</em></td><td><s:textfield name="supplierAssess.assessPoints" class="required number" maxlength="3" /><input type="radio" value="add" name="type" class="required"/>加分&nbsp;<input type="radio" value="minus" name="type"/>减分</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><em>加减分说明</em></td><td>
			<s:textarea name="supplierAssess.assessMemo" class="required" rows="5" cols="80" /></td>
			<td><input type="submit" value="提交" class="assessSubmit button"/></td>
		</tr>
	</table>
</form>
<table style="width:100%" class="zhanshi_table">
	<tr>
		<th>加减说明</th><th>分数</th><th>操作人</th>
	</tr>
	<s:iterator value="supplierAssessList" id="sa">
	<tr>
		<td width="50%"><s:property value="assessMemo"/></td>
		<td><s:property value="assessPoints"/></td>
		<td><s:property value="operatorName"/>/<s:date name="#sa.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
	</s:iterator>
</table>	
</body>
</html>