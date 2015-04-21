<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/ebk/ui-components.css" >
<link rel="stylesheet" type="text/css" href="<%=basePath %>style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath %>style/panel-content.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/base/jquery.jsonSuggest.css"></link>
<script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="<%=basePath %>js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="<%=basePath %>js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="<%=basePath %>js/base/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>js/base/jquery.jsonSuggest-2.min.js"></script>
<title>查看传真</title>
</head>
<body>
<div style="margin:0 auto;width:45%;height:100%;">
 <table class="mt20">
   <tr style="height:60px">
    <td><input type="button" id="downLoadPdf" value="下载PDF" class="btn btn-small w5"/> </td>
    <td>&nbsp;</td>
    <s:if test="ebkFaxTask.disableSend=='false'">
    <td align="right">实际发送号码：<input type="text" id="toFax" value="<s:property value="ebkCertificate.toFax"/>"/> <input type="button" id="sendFaxNow" value="立即发送" class="btn btn-small w5"/> <input type="hidden" id="updatebtn" value="修改" class="btn btn-small w5"/></td>
   	</s:if>
   </tr>
   <tr>
    <td colspan="3" style="border: 1px solid grey; padding: 20px;"><s:property value="faxCertContent" escape="false"/></td>
   </tr>
 </table>
 </div>

 <script type="text/javascript">
 var ebkFaxTaskId=<s:property value="ebkFaxTask.ebkFaxTaskId"/>;
 var ebkCertificateId=<s:property value="ebkFaxTask.ebkCertificateId"/>;
 $(function(){
	 $("#sendFaxNow").click(function(){
		 var toFax = $("#toFax").val();
		 if(toFax==""){
			 alert("发送传真的号码为空！请输入传真号码");
			 return;
		 }
		 
		 if(window.confirm("确定要执行发送操作？")){
			var param = {'faxTaskIds':ebkFaxTaskId,'fax.toFax':toFax};
	 		$.ajax({type:"POST", url:"${basePath}fax/sendFax.do", data:param, dataType:"json", success:function (obj) {
	 			      if(true==obj.success){
		    			 alert("发送完成");
	 			      }else{
			    			 alert(obj.msg);
	 			      }
					},error: function(){
					    alert("发送失败");
					}
	 		});
			}
	 });
	 
	 $("#downLoadPdf").click(function(){
		 window.open("${basePath}fax/downloadPdf.do?faxTaskId="+ebkFaxTaskId);
	 });
 });
 </script>
</body>
</html>