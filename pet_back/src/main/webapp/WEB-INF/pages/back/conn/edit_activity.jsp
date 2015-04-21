<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${basePath}/js/base/date.js"></script>
    <title>编辑推广活动</title>
    <script type="text/javascript">
        $(document).ready(function () {
        	$(".submitBtn").click(function() {
				var $form = $(this).parents("form");        		
				var name = $form.find("input[name=activity.name]").val();
				if($.trim(name) == "") {
					alert("推广名称不能为空！");
					return false;
				}
				var beginDate = $form.find("input[name=activity.beginDate]").val();
				if(beginDate == "") {
					alert("开始日期不能为空！");
					return false;
				}
				var endDate = $form.find("input[name=activity.endDate]").val();
				if(endDate == "") {
					alert("结束日期不能为空！");
					return false;
				}
				var channelId = $form.find("select[name=channelId] option:selected"); 
				if(channelId.length < 1) {
					alert("渠道不能为空！");
					return false;
				}
				$.post("/pet_back/lvcc/editActivity.do", $form.serialize(), function(data) {
					var dt = eval("(" + data + ")");
					if (dt.success) {
						alert("操作成功");
						window.location.reload();
					} else {
						alert(dt.msg);
					}
				});
        	});
        });
    </script>
</head>

<body>
<form method="post">
	<s:hidden name="activity.activityId" />
    <table border="0" cellspacing="0" cellpadding="0" class="p_table form-inline">
		<tr>
			<td><em>推广名称：</em></td>
			<td><s:textfield cssClass="newtext1" name="activity.name" maxLength="50" /></td>
		</tr>
		<tr>
			<td><em>开始日期：</em></td>
			<td><s:textfield cssClass="newtext1 date" readonly="true"  name="activity.beginDate" >
				<s:param name="value" ><s:date name="activity.beginDate" format="yyyy-MM-dd" /></s:param>
			</s:textfield></td>
		</tr>
		<tr>
			<td><em>结束日期：</em></td>
			<td><s:textfield cssClass="newtext1 date" readonly="true"  name="activity.endDate">
				<s:param name="value" ><s:date name="activity.endDate" format="yyyy-MM-dd" /></s:param>
			</s:textfield></td>
		</tr>
		<tr>
			<td><em>推广渠道：</em></td>
			<td>
				<s:select name="channelId" list="lvccChannelList" listKey="channelId" listValue="name" multiple="true"  size="15" /><font color="red">*可多选</font>
			</td>
		</tr>
        <tr>
            <td colspan="2">
                <input type="button" value=" 提 交 " class="button submitBtn" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>


