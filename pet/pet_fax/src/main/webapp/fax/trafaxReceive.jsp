<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>传真接收</title>
</head>
<body>

<%
if (!"".equals(request.getAttribute("msg")) && request.getAttribute("msg")!=null)
{
%>
<div style="color:red"><%=request.getAttribute("msg") %></div><br/><br/><br/>
<%
}
%>
	
	模拟接收到传真<br/>
	
	<form action="<%=request.getContextPath() %>/TrafaxReceiveServlet?action=receive" method="post" enctype="multipart/form-data" id="uploadTiff">
	faxSendId：<input type="text" name="barCode" id="barCode"/> <br/>
	faxId(tiff文件)：<input type="file" name="tiff"/> <br/>
	  <input type="button" value="提交" onclick="uploadTiff();" />
	</form>
	<br/>
	<br/>
	
	修改传真状态<br/>
	<form action="<%=request.getContextPath() %>/TrafaxReceiveServlet?action=faxStatus" method="post" id="updateFaxStatusForm">
	  faxSendId:<input type="text" name="barcode" id="barCode2"/> <br/>
	 
	  faxStatus: <input type="radio" value="4" name="faxstatus" checked="checked"/>发送成功
				 <input type="radio" value="5" name="faxstatus"/>发失败
       <br/>
				 失败原因：
				 <select name="errorMsg">
				 	<option></option>
				 	<option value="Faxnumber format is not right">12传真号码错误</option>
				 	<option value="对方忙">29对方占线</option>
				 	<option value="对方未应答">30无人接听或是空号</option>
				 	<option value="呼叫已被远端传真机断开">31用户取消</option>
				 	<option value="通信失败:拨号超时">26长时间静音</option>
				 	<option value="通信失败:拨号后检测到拨号音">23没有拨号音</option>
				 	<option value="无传真设备应答">25没有回铃音</option>
				 	<option value="NULL">99未知错误</option>
				 </select>
       <br/>
	   <input type="button" value="提交" onclick="updateFaxStatus();"/>
	</form>
	<br/>
	<br/>
	<a href="<%=request.getContextPath() %>/fax/trafaxReceive.jsp">刷新页面</a>
	<script type="text/javascript">
	 function uploadTiff(){
		 var form = document.getElementById("uploadTiff");
		 var barCode = document.getElementById("barCode").value;
		 if(barCode==""){
			 alert("barCode为空");
		 }else{
		   form.submit();
		 }
	 }
	 
	 function updateFaxStatus(){
		 var form = document.getElementById("updateFaxStatusForm");
		 var barCode2 = document.getElementById("barCode2").value;
		 if(barCode2 == ""){
			 alert("请填写需要修改的传真的faxSendId");
		 }else{
			 form.submit();
		 }
	 }
	</script>
</body>
</html>