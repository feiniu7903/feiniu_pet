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
  <table height="100%"  width="100%" style="margin: 0 0 0 0;padding: 0 0 0 0;" border="0">
  <s:if test="productList.size()!=0">
  <s:iterator value="productList">
     <tr>
     <td align="left" width="5%"> <s:property value="productId"/></td>
				    <td align="left" height="20"  width="25%"><s:property value="productName"/></td>
					<td align="left" width="8%">${zhProductType}</td>
                    <td align="left" width="6%">无</td>
                   <td align="left" width="10%"><s:date format="yyyy-MM-dd" name="nearDate"/></td>
					<td align="left" width="7%">${minimum}/<s:if test="stock==-1">不限量</s:if><s:else>${stock}</s:else></td>						
                    <td align="left" width="8%"><a href="javascript:openProductDetailDialog('${productId}');">查看</a></td>
				  </tr>
				  </s:iterator>
				  </s:if>
				  <s:else>
				   <tr>
				  <td colspan="7"><font color=red>   无附加产品</font></td>
				 	</tr>
				  </s:else>
				  </table>
  </body>
</html>
