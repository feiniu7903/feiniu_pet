<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>常见问题</title>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
</head>
<body>
	<s:form action="doAsk" namespace="/">
		<s:if test="ask.placeQaId!=null">
			<s:hidden name="ask.placeQaId"></s:hidden>
		</s:if>
		<s:hidden name="ask.placeId"></s:hidden>
		<table class="p_table">
			<tr>
				<td>问：</td>
			</tr>
			<tr>
				<td><s:textarea name="ask.question" id="ask_question" maxLength="500" rows="2" cssStyle="width:300px"/></td>
			</tr>
			<tr>
				<td>答：</td>
			</tr>
			<tr>
				<td><s:textarea name="ask.answer" id="ask_answer" maxLength="500" rows="2" cssStyle="width:300px"/></td>
			</tr>
			<tr>
				<td>排序值：</td>
			</tr>
			<tr>
				<td><s:textarea name="ask.seq" id="ask_seq" onblur="checkSubmit()"/></td>
			</tr>
			<tr>
				<td align="right"><input class="btn btn-small w5" type="submit"  value="提交" /></td>
			</tr>
		</table>
	</s:form>

</body>
<script type="text/javascript">
function checkSubmit(){	
	if ($("#ask_seq").val() == "" || isNaN($("#ask_seq").val())) {
		alert("请输入合法的排序");
		$("#ask_seq").focus();
	}
}




</script>
</html>