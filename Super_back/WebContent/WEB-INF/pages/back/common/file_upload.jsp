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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/affix_upload.js"></script>
		<script type="text/javascript">
			var delete_op_url="<%=request.getContextPath()%>/common/file_del.do";
			function getFileName(obj){
				var filePath = obj.value;
				
				if(filePath.lastIndexOf(".")==-1){
					alert("文件类型错误");
					return;
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
			<div>&nbsp;&nbsp;文&nbsp;&nbsp;件&nbsp;&nbsp;&nbsp;&nbsp;<s:file name="file" label="文件" cssStyle="width:300px;" onchange="getFileName(this)"/></div>
			<div>文件名称<s:textfield name="affix.name" id="affixNameId" label="文件名称" cssStyle="width:300px;"/></div>
			<div style="vertical-align: top;">文件描述<s:textarea name="affix.memo" label="文件描述" cols="35" rows="1"/></div>
				<s:submit value="  确定    "/>
		</s:form>
		
		<div>
		<s:if test="pagination.records!=null">
		<h3 style="margin: 0px;">已经存在的文件列表</h3>
		<table style="width:400px;">
		<s:iterator value="pagination.records" var="a">
			<tr id="tr_<s:property value="#a.affixId"/>">
				<td><span title="${a.memo}"><s:property value="#a.name"/></span></td><td style="width:100px;"><a href="#delete" class="delete_affix" result="<s:property value="#a.affixId"/>">删除</a>&nbsp;
				     <s:if test="#a.fileType=='GROUP_ADVICE_NOTE'">
				        <a target="_blank" href="/super_back/groupadvice/download.do?fileId=<s:property value="#a.fileId"/>&fileName=<s:property value="#a.name"/>">下载</a>
				     </s:if>
				     <s:else>
				       <a href="http://pic.lvmama.com/pics/<s:property value="#a.path"/>" target="_blank">下载</a>  
				     </s:else>
				</td>
			</tr>
		</s:iterator>
		</table>
		</s:if>		
		</div>
	</body>
</html>