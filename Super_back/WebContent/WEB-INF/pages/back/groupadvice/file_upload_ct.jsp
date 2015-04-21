<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>文件列表</title>
		<style type="text/css">
		form div{margin: 5px}
		</style>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/groupadvice_upload.js"></script>
		<script type="text/javascript">
 			function getFileName(obj){
				var filePath = obj.value;
				if(filePath.lastIndexOf(".")==-1){
					alert("文件类型错误");
					return;
				}else{
					var point=filePath.lastIndexOf(".");
					   if(filePath.substr(point)!=".pdf" && filePath.substr(point)!=".doc" && filePath.substr(point)!=".docx"){  //直接上传pdf文件.
					      alert("请选择后缀名为“.pdf“,“.doc”或”.docx“的文件");
					      return;
					   }
				}
				if(filePath.lastIndexOf("/")!=-1){
					filePath=filePath.substring(filePath.lastIndexOf("/")+1);
				}else if(filePath.lastIndexOf("\\")!=-1){
					filePath=filePath.substring(filePath.lastIndexOf("\\")+1);
				}
				if($.trim(filePath)==''){
					filePath=obj.value;
				}			 
				var lastNum = filePath.lastIndexOf(".");	 
				var name = filePath.substring(0,lastNum);	
				if(name.length>=50){
					var byteLen=0;
					var pos=0;
					var len=name.length;
					for(var i=0; i<len; i++){
					　　	if(name.charCodeAt(i)>255){
					　　　byteLen += 2;
					　　}else{
					　　　byteLen++;
					　　}
					　　
					　　	if(byteLen>=100){
					　　		pos=i;
					　　		break;
						}
					}
					name=name.substr(0,pos-1);
				}
			　　
				$("#affixNameId").val(name);
			}
		</script>
	</head>
	<body>
		<s:form method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
		<input type="hidden" name="affix.objectId" value="<s:property value="objectId"/>"/>
		<input type="hidden" name="affix.objectType" value="<s:property value="objectType"/>"/>
			<h3>上传文件</h3>
			<div>&nbsp;&nbsp;文&nbsp;&nbsp;件&nbsp;&nbsp;&nbsp;&nbsp;
			    <s:file id="upload_file" name="file" label="文件" cssStyle="width:300px;" onchange="getFileName(this)"/></div>
			<div>文件名称<s:textfield name="affix.name" id="affixNameId" label="文件名称" cssStyle="width:300px;"/></div>
			<div style="vertical-align: top;">文件描述<s:textarea id="upload_affix_memo" name="affix.memo" label="文件描述" cols="35" rows="1"/></div>
				<s:submit value="  上传并发送    "/>
		</s:form>
		
		 
	</body>
</html>