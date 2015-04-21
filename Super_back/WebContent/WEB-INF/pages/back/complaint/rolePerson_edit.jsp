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
<title>修改人员</title>
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
	function editRole() {
		var persons = $("#persons").val();
		//验证人员输入的格式
		if (persons == "") {
			alert("请填写人员姓名");
			$("input[name='persons']").focus();
			return;
		}
		//判断输入的文本内容中是否存在中文输入法的逗号
		if(/，/.test(persons)){
		    alert("请使用英文逗号分隔");
		    return false;
		}
		if(!checkContent(persons)){
			return false;
		}
		var orgId=$("#orgId").val();

        $.post("update_user.do",
                {
                    orgId: orgId,
                    persons: persons
                },
                function (data) {
                    if ("SUCCESS" == data) {
                        alert("操作成功");
                        parent.location.reload(parent.location.href);
                    } else if ("FAILED" == data) {
                        alert("操作失败");
                    } else {
                        alert(data);
                    }
                });
	}
</script>
</head>
<body>
	<div class="p_box">
		<form action="" method="post">
			<table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label">*角色部门:</td>
					<td>
						<input type="text" id="departmentName" name="departmentName" value="${role.departmentName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="p_label">*人员:</td>
					<td>
						<input type="text" id="persons" name="persons" value="${role.persons }" style="width: 300px;" />
						&nbsp;&nbsp;多个用逗号分隔
					</td>
				</tr>
			</table>
			<input type="hidden" name="orgId" value="${orgId}" id="orgId"/>
		</form>
		<p class="tc mt20">
			<button class="btn btn-small w5" type="button" onclick="editRole();">提交</button>
		</p>
	</div>
</body>
</html>
