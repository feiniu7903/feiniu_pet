<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购类别关联的销售类别</title>
</head> 
<body>
   		<table class="newTable" width="100%" border="0" cellspacing="0" cellpadding="0" id="branch_tb">
          <tr class="newTableTit">
            <td>销售产品ID</td>
            <td>销售产品名称</td>
            <td>类别名称</td>
            <td>负责人</td>
            <td>在线状态</td>
            <td>操作</td>      
          </tr>
          <s:iterator value="relativeProdProductAndBranchList">
		          <tr>
		            <td><s:property value="prodProductId"/> </td>
		            <td><s:property value="prodProductName"/></td>
		            <td><s:property value="prodBranchName"/>(<s:property value="prodBranchId"/>)</td>
		            <td><s:property value="prodManagerName"/></td>
		            <td><s:if test="prodBranchState =='true'">上线</s:if><s:else>下线</s:else></td>
		            <td><a href="#timePrice" tt="PROD_PRODUCT" class="showTimePrice" param="{'prodBranchId':<s:property value="prodBranchId"/>,'editable':true}">修改时间价格</a></td>
		          </tr>
           </s:iterator>	         
        </table>
</body>
</html>


