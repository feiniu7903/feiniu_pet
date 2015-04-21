<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/mis.css">
		<script type="text/javascript" charset="utf-8"
			src="<%=basePath%>/kindeditor-3.5.1/kindeditor.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js">
</script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js">
</script>
		<script type="text/javascript">
var path = '<%=basePath%>';
</script>
		<script type="text/javascript">
function save() {

	var cnn = KE.util.getData('contentA');
	$("#content").val(cnn);
	var m = $('#viewContentForm').getForm( {
		prefix : ''
	});
	$.ajax({
		type : "POST",
		url : "<%=basePath%>groupadvicenote/saveGroupAdviceNote.do",
		data : m,
		success : function(data) {
			 var d = eval(data);
			if (d) {
				alert("出团通知书生成并发送成功！");
			} else {
				alert("出团通知书生成并发送失败！");
			}
		}
	});
}
</script>
		<title>修改出团通知书</title>
	</head>
	<body>
		<form id="viewContentForm" name="contentForm" method="post" action="">
			<s:hidden id="contentFirstFix" name="contentFirstFix"></s:hidden>
			<s:hidden id="contentEndFix" name="contentEndFix"></s:hidden>
			<s:hidden id="objectId" name="objectId"></s:hidden>
			<s:hidden id="objectType" name="objectType"></s:hidden>
			<s:hidden id="affixObjectId" name="affix.objectId"></s:hidden>
			<s:hidden id="affixObjectType" name="affix.objectType"></s:hidden>
			<s:hidden id="orderId" name="orderId"></s:hidden>
			
			<s:hidden id="content" name="content"></s:hidden>
			<table>
				<tr height="100">
					<td height="100">
						<textarea id="contentA" name="contentA"
							style="width: 780px; height: 400px; visibility: hidden;">
							${content}
						</textarea>
						<script type="text/javascript">
						function showKindEditor(id) {
							KE.show( {
								id : 'content' + id,
								cssPath : '/FCKEditor/skins/'
							});
						}
						</script>
						<script type="text/javascript">
												showKindEditor('A')</script>
						<input value="生成出团通知书并发送" name="btnSaveViewContent" type="button"
							onclick="save()"></input>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
