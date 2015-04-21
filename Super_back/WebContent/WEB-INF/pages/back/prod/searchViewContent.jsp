<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>ShHoliday viewContent</title>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		$("input[name='searchTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='searchTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
	

	function checkAndSubmitCondition(){
		//var value = $.trim($("#form1 #productId").val());
		//$("#form1 #productId").val(value);
		document.form1.submit();
	}
	
</script>
</head>
<body style="height: auto;">
<form id="form1" name='form1' method='post' action='/super_back/prod/tmpUpdateViewContentAction.do'>
	<table>
		<tr>
			<td >产品ID：</td>
			<td><input type="text" id="productId" name="productId"/></td>
			<td><input type="text" id="logId" name="logId"/></td>
			<td ><input type="submit" value="操 作" id="search" /></td>
		</tr>
		
	</table>
</form>
</body>
</html>





