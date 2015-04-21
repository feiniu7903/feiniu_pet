<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增模板</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<link rel="stylesheet" type="text/css" href="${basePath}themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath}themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}themes/mis.css">
<link rel="stylesheet" type="text/css" href="${basePath}css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>
<body>
	<form id="templateForm" action="" method="post">
		<table class="gl_table">
			<tr><td>标题<font color="red">[*]</font>：</td><td><input type="text" value="" name="template.title" style="width: 600px;"/></td></tr>
			<tr><td>内容<font color="red">[*]</font>：</td><td><textarea name="template.content"  style="width: 600px; height: 400px;"></textarea></td></tr>
			<tr><td>内容预览：</td><td style="border: 1px solid #67A1E2;"><div id="showContentId"></div></td></tr>
			<tr><td>有效时间<font color="red">[*]</font>：</td><td><input id="startDate" type="text" class="newtext1"
							name="template.beginTime" /> ~<input id="endDate" type="text"
							class="newtext1" name="template.endTime" /></tr>
			<tr><td colspan="2"><input class="button" type="button" name="save" value="新增"/></td></tr>
		</table>
	</form>
</body>
<script type="text/javascript" charset="utf-8">
$(function(){
	$('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
    $("textarea").blur(function(){
    	$('#showContentId').html($(this).val());
    });
    $(":button[name=save]").click(function(){
    	var title = $.trim($(":text[name='template.title']").val());
    	if(title.length == 0){alert("标题不能为空");return false;}
    	if(title.length >200){alert("标题不能大于200个字符");return false;}
    	var vcontent = $.trim($("textarea[name='template.content']").val());
    	if(vcontent.length == 0){alert("内容不能为空");return false;}
    	if(vcontent.length >2000){alert("内容不能大于2000个字符");return false;}
    	var beginTime = $.trim($('#startDate').val());
    	var endTime = $.trim($('#endDate').val());
    	if(beginTime.length == 0){alert("有效期开始时间不能为空");return false;}
    	if(endTime.length == 0){alert("有效期结束 时间不能为空");return false;}
    	$.ajax( {
			url : "${basePath}/onlineLetter/template/save.do",
			data:{"template.title":title,"template.content":vcontent,"template.beginTime":beginTime,"template.endTime":endTime},
			type: "POST",
			dataType:"json",
			success : function(data) {
					if(data.success){
						alert("新增成功");
					}else{
						alert(data.errorText);
					}
				}
			});
    });
});
</script>
</html>