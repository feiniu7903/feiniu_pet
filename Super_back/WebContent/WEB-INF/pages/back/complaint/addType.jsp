<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增投诉类型</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/ui-components.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/panel-content.css"></link>
<script type="text/javascript" src="<%=basePath%>/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/complaint/complaint.js"></script>
<script type="text/javascript">
$(function(){
	checkTextLength();
	$("#typeName").keyup(function(){
		$("#typeNameCount").text(20-$("#typeName").val().length);
		$("#typeNameCount1").text(20-$("#typeName").val().length);
	});
	$("#typeDescription").keyup(function(){
		$("#typeDescriptionCount").text(100-$("#typeDescription").val().length);
		$("#typeDescriptionCount1").text(100-$("#typeDescription").val().length);
	});
});
</script>
</head>
<body>
	<div class="p_box">
		<form id="addType_form" action="" method="post">
			<table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label">*类型名称:</td>
					<td>
						<input type="text" id="typeName" name="typeName" style="width: 200px;" maxlength="20" />
						<span style="font-size: 12px;color: red;">
							<span id="typeNameCount"  style="font-size: 12px;color: red;">20</span>/20(你还可输入<span id="typeNameCount1"  style="font-size: 12px;color: red;">20</span>个字)
						</span>
					</td>
				</tr>
				<tr>
					<td class="p_label">描述:</td>
					<td>
						<textarea rows="4" maxlength="100" style="width: 300px;" id="typeDescription" name="typeDescription"></textarea>
						<span  style="font-size: 12px;color: red;">
							<span id="typeDescriptionCount" style="font-size: 12px;color: red;">100</span>/100(你还可输入<span id="typeDescriptionCount1" style="font-size: 12px;color: red;">100</span>个字)
						</span>
					</td>
				</tr>
				<tr>
					<td class="p_label">*排序:</td>
					<td>
						<input type="text" id="sort" name="sort" />
					</td>
				</tr>
			</table>
		</form>
		<p class="tc mt20">
			<button class="btn btn-small w5" type="button" onclick="addType();">提交</button>
		</p>
	</div>
</body>
</html>
