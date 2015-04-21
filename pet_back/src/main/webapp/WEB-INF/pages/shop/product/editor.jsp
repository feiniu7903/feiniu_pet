<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js"></script>
		<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/kindeditor.js"></script>
		<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/lang/zh_CN.js"></script>	
  </head>
  <body style="padding: 0 0 0 0; margin:0 0 0 0; font-size: 12px">
	<form id="viewContentForm" action="">
		<table width="100%" class="datatable">
			<tr>
				<td align="center">
					<s:hidden id="fautureContent" name="fautureContent"></s:hidden>
					<textarea id="contentA" name="contentA" style="width: 780px; height: 450px; visibility: hidden;">${content}</textarea>
					<!--input value="暂存详细信息" name="btnSaveViewContent" type="button" onclick="save()"></input-->
				</td>
			</tr>
		</table>
	</form>
  </body>
  <script type="text/javascript">
    var editor;
	
    var save = function(){
		var cnn = editor.html();
		window.parent.document.getElementById("tmp_content").innerHTML = cnn;
	}
 
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="contentA"]', {
			allowFileManager : true,
			afterBlur : save,
			uploadJson:'/pet_back/upload/uploadImg.do'
		});
	});
  </script>
</html>
