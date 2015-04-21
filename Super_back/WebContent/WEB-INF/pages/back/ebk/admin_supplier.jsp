<%@page import="com.lvmama.comm.pet.po.sup.SupSupplier"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EBOOKING账号管理</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/ui-components.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/ebk/admin_supplier.js" ></script>
<script type="text/javascript" src="../../js/base/log.js"></script>
</head>
<body>
	<ul class="gl_top">
		<form action="index.do" method="post">
			<li>供应商名称：
				<input id="supNameInput" type="text" class="u3"
					value="${supName}" name="supName">
			</li>
			<li>驴妈妈联系人：
				<input id="lvmamaContactNameInput" type="text" class="u3"
					value="${lvmamaContactName}" name="lvmamaContactName">
			</li>
			<li>
				<input type="submit" value="查询" class="u10 btn btn-small" id="u10">
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th width="100">供应商编号</th>
	    <th>供应商名称</th>
	    <th width="150">操作</th>
	  </tr>
	  <s:iterator value="supPage.items" var="item">
			<tr>
				<td><s:property value="supplierId" /></td>
				<td><s:property value="supplierName" /></td>
				<td class="gl_cz">
					<a href="javascript:void(0);" 
						onclick="addAdminHandler(${item.supplierId},'${item.supplierName }')">新增账号</a>
					<% 
						String supName = "";
						SupSupplier sup = (SupSupplier)request.getAttribute("item");
						if(sup != null){
							supName = sup.getSupplierName() == null?"":sup.getSupplierName();
						}
					%>
					<a href="admin_search.do?supId=${item.supplierId}&supName=
						<%=URLEncoder.encode(URLEncoder.encode((supName)))%>
					">账号管理</a>
				</td>
			</tr>
	  </s:iterator>
	   <tr>
		<td>总条数：<s:property value="supPage.totalResultSize" /></td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(supPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>