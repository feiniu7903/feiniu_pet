<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
  <div id="addReceiverDialg">
   <s:form theme="simple" id="ReceiverForm">
      <table width="100%" border="0" class="contactlist" cellspacing="1" bgcolor="#b8c9d6">
            <tr bgcolor="#ffffff">
             <td width="12%">取票(联系)人：</td>
             <td width="30%">
				<input name="usrReceivers.receiverName" id="receiverName" type="text" value="${usrReceivers.receiverName}" maxlength="50"/>
			</td>	
             <td>联系电话：</td>
             <td>
				<input name="usrReceivers.mobileNumber" id="mobileNumber" type="text" value="${usrReceivers.mobileNumber}" maxlength="20"/>
			</td>				 
            </tr>
            <tr bgcolor="#ffffff">
            <td>证件类型：</td>
             <td>
             <s:select value="usrReceivers.cardType" name="usrReceivers.cardType" id="cardType" list="#{'':'请选择','ID_CARD':'身份证','HUZHAO':'护照','OTHER':'其他'}"></s:select>
             </td>		 
             <td>证件号码：</td>
             <td><input name="usrReceivers.cardNum" id="cardNum" type="text"  value="${usrReceivers.cardNum}" maxlength="30"/></td>  
			</tr>
		   </table>
	</s:form>
	</div>
  </body>
</html>
