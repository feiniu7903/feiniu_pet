<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'selectReciever.jsp' starting page</title>
    
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
  <s:if test="usrReceivers!=null">
		<td>游客
			<%-- <select name='person.personType'>
				<option selected='selected' value='TRAVELLER'>游客</option>
				<option value='EMERGENCY_CONTACT'>紧急联系人</option>
			</select> --%>
		</td>
		<td>
			<input name="person[${i.index }].name" id="personName" size="5" 
				type="text" value="${usrReceivers.receiverName}" maxlength="40" />
		</td>
		<td>
			<input name="person[${i.index }].mobile" type="text" id="mobileNumber" 
			value="${usrReceivers.mobileNumber}" maxlength="30" size="9" />
		</td>
		<td>
			<input name="person[${i.index }].tel" type="text" size="8"
				value="${usrReceivers.phone}" maxlength="30" /></td>
		<td>
			${usrReceivers.cardType eq 'ID_CARD'?'身份证':''}
			${usrReceivers.cardType eq 'HUZHAO'?'护照':''}
			${usrReceivers.cardType eq 'OTHER'?'其他':''}
			<%-- <s:select value="cardType" name="person[%{#i.index }].certType"
					list="#{'':'请选择','ID_CARD':'身份证','HUZHAO':'护照','OTHER':'其他'}"></s:select> --%>
		</td>
		<td>
			<input name="person[${i.index }].certNo" type="text"
				size="18" value="${usrReceivers.cardNum}" maxlength="30" />
		</td>
		<td>
			<input name="person[${i.index }].brithday" type="text" size="9" 
				value="${usrReceivers.zhBrithday}" maxlength="40" class="date" />
		</td>
		<td>
			<input type="radio" name="person[${i.index }].gender" value="M" 
				<s:if test='gender=="M"'>checked</s:if> />男
			<input type="radio" name="person[${i.index }].gender" value="F"
				<s:if test='gender=="F"'>checked</s:if> />女
		</td>
		<td>
			<input name="person[${i.index }].email" type="text" size="16"
				value="${usrReceivers.email}" maxlength="50" />
		</td>
		<td>
			<input name="person[${i.index }].postcode" id="personPostCode" type="text"
				value="${usrReceivers.postCode}" size="5" maxlength="40" />
		</td>
		<td>
			<input name="person[${i.index }].address" id="personAddress"
				size="16" type="text" value="${usrReceivers.address}" />
		</td>
		<td>
				
		</td>
	</s:if>
  
  <%-- <s:if test="usrReceivers!=null">
      <table width="100%" border="0" class="contactlist" cellspacing="1" bgcolor="#b8c9d6">
            <tr bgcolor="#ffffff">
             <td width="12%">取票(联系)人：</td>
             <td width="30%">${usrReceivers.receiverName}</td>			 
             <td width="10%">Email：</td>
             <td width="48%">${usrReceivers.email}</td>			 
            </tr>
            <tr bgcolor="#ffffff">
             <td>联系电话：</td>
             <td>${usrReceivers.mobileNumber}</td>			 
             <td>座机号：</td>
             <td>${usrReceivers.phone}</td>				 			 
            </tr>
            <tr bgcolor="#ffffff">
             <td>证件类型：</td>
             <td>${usrReceivers.cardType}</td>		 
             <td>证件号码：</td>
             <td>${usrReceivers.cardNum}</td>            
			</tr>
            <tr bgcolor="#ffffff">
             <td>传真：</td>
             <td>${usrReceivers.fax}</td>			 
             <td>传真接收人：</td>
             <td>${usrReceivers.faxContactor}</td>			 
            </tr>
            <tr bgcolor="#ffffff">
             <td>邮编：</td>
             <td>${usrReceivers.postCode}</td>					 			
             <td colspan="2">&nbsp;</td> 		 
            </tr>
            <tr bgcolor="#ffffff">
             <td>地址：</td>
             <td colspan="3">${usrReceivers.address}</td>			 			 
            </tr>						
		   </table>
		   </s:if> --%>
  </body>
</html>
