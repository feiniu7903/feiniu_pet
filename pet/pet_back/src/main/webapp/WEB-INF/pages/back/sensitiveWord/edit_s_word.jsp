<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改敏感词</title>
    <script type="text/javascript">
    $(document).ready(function () {
        $("input.submitBtn").click(function() {
        	var $form = $("#sensitiveWord_form");
        	var content = $.trim($form.find("textarea[name=sensitiveWord.content]").val());
        	if(content == "") {
        		alert("敏感词不能为空！");
        		return false;
        	}
        	if(content.length > 250) {
        		alert("敏感词内容不能超过250个字!");
        		return false;
        	}
        	var sensitiveId = $form.find("input[name=sensitiveWord.sensitiveId]").val();
        	if($.trim(sensitiveId) != '') {
        		if(content.indexOf(",") != -1) {
        			alert("修改敏感词内容不能包含','!");
        			return false;
        		}
        	}
        	$.post(
                    "/pet_back/sensitiveWord/editSensitiveWord.do",
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
<form id="sensitiveWord_form">
	<s:hidden name="sensitiveWord.sensitiveId" />
    <table border="0" cellspacing="0" cellpadding="0" class="p_table form-inline">
		<tr>
			<td>敏感词(<font color="red">如需添加批量关键词，以","间隔</font>)：</td>
			<td width="70%">
			<s:textarea name="sensitiveWord.content"></s:textarea></td>
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