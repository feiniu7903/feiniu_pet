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
<title>驴妈妈供应商管理系统 - 打印订单</title>
<script type="text/javascript">
	function printFax(printpage) {
		if (window.print) { 
			var headstr = "<html><head><title></title></head><body>";
			var footstr = "</body>";
			var newstr = document.getElementById(printpage).innerHTML;
			var oldstr = document.body.innerHTML;
			document.body.innerHTML = headstr+newstr+footstr;
			window.print(); 
			document.body.innerHTML = oldstr;
		} else {
			alert("您的浏览器不支持自动打印，请使用浏览器的打印功能！")
		}
		return false;
	}
	function downPDF(){
		 window.open("${contextPath }/ebooking/task/downloadPdf.do?certificateId=${ebkCertificate.ebkCertificateId}");
	}
</script>
</head>
<body>
<div style="margin:0 auto;width:45%;height:100%;">
 <table  style="margin:0 auto;width:100%;height:100%;">
   <tr style="height:60px">
    <td>
<input type="button" id="downLoadPdf" value="下载PDF" class="btn btn-small w5" onclick="downPDF();"/>
    </td>
    <td>
<input type="button" id="printFax" value="打印" class="btn btn-small w5" onclick="printFax('faxContentDiv');"/>
    </td>
   </tr>
   <tr>
    <td colspan="2" style="border: 1px solid grey; padding: 20px;">
    <div style="margin:0 auto;width:100%;height:100%;" id="faxContentDiv">
    <s:property value="certContent" escape="false"/>
    </div>
    </td>
   </tr>
 </table>
</div>
</body>

</html>
