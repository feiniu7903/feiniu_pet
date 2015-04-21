<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>推广渠道管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		$(".submitBtn").click(function() {
			var $form = $(this).parents("form");
			if ($.trim($form.find("input[name=lvccChannel.name]").val()) == "") {
				alert("渠道名称不能为空！");
				return false;
			}
			$.post("/pet_back/lvcc/addChannel.do", $form.serialize(), function(data) {
				var dt = eval("(" + data + ")");
				if (dt.success) {
					alert("操作成功");
					$form.find("input[name=lvccChannel.name]").val('');
					var channelId = dt.channelId;
					var name = dt.name;
					var $tr = $("<tr id='"+channelId+"'><td>"+channelId+"</td><td>"+name+"</td></tr>");
					$("#resultTr").after($tr);
				} else {
					alert(dt.msg);
				}
			});
		});
	});
</script>
</head>

<body>
	<div class="iframe-content">
		<div class="p_box">
			<form method="post">
				<table border="0" cellspacing="0" cellpadding="0"
					class="p_table form-inline">
					<tr>
						<td><em>渠道名称：</em></td>
						<td><s:textfield cssClass="newtext1" name="lvccChannel.name"  maxLength="50" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value=" 新 增 "
							class="button submitBtn" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="p_box">
			<table class="p_table table_center">
				<tr id="resultTr">
					<th>ID</th>
					<th>渠道名称</th>
				</tr>
				<s:iterator value="lvccChannelList">
					<tr id="${channelId}">
						<td>${channelId }</td>
						<td>${name }</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
</body>
</html>


