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
<title>编辑专题</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<script type="text/javascript" src="<%=basePath %>js/base/jquery.jsp"></script>
<script type="text/javascript" src="<%=basePath %>js/base/ajaxupload.js"></script>
 </head>
    
<script type="text/javascript">
        function checkValue() {
        	if($("input[name='cmtSpecialSubject.title']").val()==""){
			    alert("标题不能为空");
			    return false;
        	}else if($("input[name='cmtSpecialSubject.href']").val()==""){
				alert("链接不能为空");
				return false;
        	 }else if($("input[name='cmtSpecialSubject.furl']").val()==""){
				alert("图片不能为空");
				return false;
        	 }else if($("input[name='cmtSpecialSubject.picUrl']").val()==""){
				alert("图片链接不能为空");
				return false;
			 }else if($("input[name='cmtSpecialSubject.summary']").val()==""){
				alert("简介不能为空");
				return false;
			}else if($("input[name='cmtSpecialSubject.versionNum']").val()==""){
				alert("第几期不能为空");
				return false;
			} 
        	//saveSpecialSubject.submit();
        	$("#saveSpecialSubjectForm").submit();
        }
    </script>
</head>
<body>
 	<div>
 	<form id="saveSpecialSubjectForm" name="saveSpecialSubjectForm" action="<%=basePath%>/commentManager/saveSpecialSubject.do" method ="post" enctype="multipart/form-data">
 		<table >
 			<tr>
 				<td>标题:</td>
 				<td>
 				<input name="cmtSpecialSubject.title" value="${cmtSpecialSubject.title}"  maxlength="50" width="1300px"/>
 				</td>
 			</tr>
 			<tr>
 				<td>第几期:</td>
 				<td>
 				<input name="cmtSpecialSubject.versionNum" value="${cmtSpecialSubject.versionNum}"  maxlength="4" width="1300px"/>
 				</td>
 			</tr>
 			<tr>
 				<td>链接:</td>
 				<td>
 				<input name="cmtSpecialSubject.url" value="${cmtSpecialSubject.url}"  maxlength="100" width="1300px"/>
 				</td>
 			</tr>
 			 <tr>
 				<td>图片上传:</td>
 				<td>
 					<s:file id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/>
 				</td>
 			</tr>
 			 <tr>
 				<td>图片链接:</td>
 				<td>
 				<input name="cmtSpecialSubject.picUrl" value="${cmtSpecialSubject.picUrl}" maxlength="100"  width="1300px"/>
 				</td>
 			</tr>
 			<tr>
 				<td>简介:</td>
 				<td>
 					<input name="cmtSpecialSubject.summary" value="${cmtSpecialSubject.summary}"  maxlength="400" width="1300px"/>
 				</td>
 			</tr>
 			<tr>
 				<td><input class="button" type="button" value="提交" onClick="return checkValue();"/></td>
 				<td></td>
 			</tr>
 		</table>
 		</form>
 	</div>
</body>
</html>


