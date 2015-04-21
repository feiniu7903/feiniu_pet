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
	    <div>
		 <s:if test="pagination.records!=null">
		 <table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
								    <td height="35" width="9%" align="center"> 序号 </td>
									<td height="35" width="5%" align="center"> 文件名 </td>
									<td height="35" width="5%" align="center">上传时间 </td>
									<td width="10%" align="center"> 下载</td>
									 
								</tr>
								
								<s:iterator value="pagination.records" var="a" status="st">
									<tr bgcolor="#ffffff" id="tr_<s:property value="#a.affixId"/>">
										<td><span title="${a.affixId}"><s:property value='#a.affixId'/></span></td>
										<td><span title="${a.memo}"><s:property value="#a.name"/></span></td>
										<td><span title="${a.createTime}"><s:date name="#a.createTime" format="yyyy-MM-dd HH:mm:ss"/></span></td>
										<td style="width:100px;"><a href="/super_back/groupadvice/download.do?fileId=<s:property value="#a.fileId"/>&fileName=<s:property value="#a.name"/>">下载</a></td>
									</tr>
								</s:iterator>
							 <tr><td colspan="17"  bgcolor="#eeeeee"> </td></tr>
							</tbody>
						</table>
				 <table width="98%" border="0" align="center">
				   <s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			     </table>		 
				 </s:if>
				<s:else>
				        <h3 style="margin: 0px;">不存在的文件列表</h3>
				 </s:else>
				  
			 </div>	
		 </body>
</html>