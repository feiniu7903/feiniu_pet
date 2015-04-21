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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	</head>
	<body>
	<div class="main main02">
		<div class="row1">
			<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
				<tr>
					<td>输入编号系列(逗号隔开)：</td>
					<td><input type="checkbox" id="checkType" value="1"/>是否产品</td>
				</tr>
				<tr>
					<td colspan="3">
						<s:textarea label="pids" name="pids" id="pids" cols="80" rows="10" value=""></s:textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class="button" value="保存"  id="savePlaceIdsCmtTitle"/></td>
				</tr>
			</table>
		</div>
	</div>
	</body>
	<script type="text/javascript">
	$(function(){
		$("#savePlaceIdsCmtTitle").click(function(){
			if ($("#pids").val() == "") {
				alert("请先填写标号系列pids(逗号隔开)");
				return;
			}
			
			$.ajax({
    	 		url: "<%=basePath%>/commentManager/updatePlaceCommentTopic.do",
				type:"post",
    	 		data: {
						"pids":$("#pids").val(),
						"checkType":$("#checkType").val(),
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						alert("操作成功");
						$("#showUpdatePlaceCommentTopicDiv").dialog("close");
					} else {
						alert("数据丢失，操作失败");
					}
    	 		}
    		});	
		});
	});

	</script>
</html>


