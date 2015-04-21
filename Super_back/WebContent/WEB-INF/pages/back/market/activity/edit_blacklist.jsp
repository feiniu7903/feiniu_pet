<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>编辑黑名单</title>
    <script type="text/javascript">
    $(document).ready(function () {
        $("input.submitBtn").click(function() {
        	var $form = $("#blacklist_form");
        	var mobileNumber = $.trim($form.find("input[name=markActivityBlacklist.mobileNumber]").val());
        	var email = $.trim($form.find("input[name=markActivityBlacklist.email]").val());
        	if(mobileNumber == "" && email == "") {
        		alert("至少一个不为空！");
        		return false;
        	}
        	$.post(
                    "/super_back/mark_activity/editMarkActivityBlacklist.do",
                    $form.serialize(),
                    function (data) {
                    	var dt = eval("(" + data +")");
                        if (dt.success) {
                            alert("操作成功");
                            location.reload(window.location.href);
                        } else {
                            alert(dt.msg);
                        }
                    }
            );
        });
    });
    </script>
</head>
<body>
<form id="blacklist_form">
	<s:hidden name="markActivityBlacklist.blackId" />
    <table border="0" cellspacing="0" cellpadding="0" class="newTable">
		<tr><th colspan="2"></th></tr>
		<tr>
			<td>手机号码：</td>
			<td><s:textfield name="markActivityBlacklist.mobileNumber" />请输入11位手机号！</td>
		</tr>
		<tr>
			<td>邮箱地址：</td>
			<td><s:textfield name="markActivityBlacklist.email" /></td>
		</tr>
        <tr>
            <td colspan="2">
                <input type="button" value=" 提 交 " class="submitBtn" />
            </td>
        </tr>
        </tfoot>
    </table>
</form>
</body>
</html>