<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户产品权限</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
</head>
<body>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>采购产品</th>
	    <th>类别</th>
	  </tr>
	  <s:iterator value="#request.branchs" var="item">
			<tr>
				<td>${item.metaProductName }(${item.metaProductId })</td>
				<td>${item.branchName }(${item.metaBranchId })</td>
			</tr>
	  </s:iterator>
	</table>
</body>
</html>