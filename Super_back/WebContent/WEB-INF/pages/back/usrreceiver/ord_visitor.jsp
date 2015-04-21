<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>
  
  <body>
     
             <table cellspacing="1" cellpadding="4" border="0"  bgcolor="#B8C9D6" width="100%" class="newfont04">
				 <tbody>
                  <tr bgcolor="#f4f4f4" align="center">
				    <td height="30" width="8%">类别</td>
                    <td width="8%">姓名</td>
					<td width="6%">联系电话</td>					
					<td width="9%">Email</td>
                    <td width="11%">证件类型</td>
					<td width="10%">证件号码</td>
				    <td width="15%">备用联系方式</td>
					<td width="5%">邮编</td>
                    <td width="20%">地址</td>			
					<td width="6%">操作</td>
                  </tr>
                  <s:iterator value="visitorList" status="st">
                  
                  <tr bgcolor="#ffffff" align="center">
				    <td height="25">游客</td>
                    <td>${receiverName }</td>
					<td>${mobileNumber }</td>
                    <td>${email }</td>
                    <td>${cardType }</td>
                    <td>${cardNum }</td>
				    <td>${phone }</td>
					<td>${postCode }</td>
                    <td>${address }</td>			
                    <td><a href="javascript:deleteVisitor('<s:property value="#st.index"/>');">删除</a></td>
                  </tr>
                  </s:iterator>
           </tbody></table> 
  </body>
</html>
