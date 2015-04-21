<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关联已有供应商</title>
</head>
<body>
	<div>
		<form action="${basePath }/sup/relateSupplierSearch.do" method="post">
			<table class="search_table" cellspacing="0" cellpadding="0">
				<tr>
					<td>供应商名称：</td>
					<td><input size="10" type="text" name="supplierName"
						value="<s:property value="supplierName"/>" /></td>
					<td>供应商编号：</td>
					<td><input size="10" type="text" name="supplierId"
						value="<s:property value="supplierId"/>" /></td>
					<td>供应商地区：</td>
					<td><s:select onchange="changeCity(this,'relateCitySelect')" list="provinceList" name="provinceId"
							listKey="provinceId" listValue="provinceName" />
						<s:select name="cityId" list="cityList" id="relateCitySelect"
							listKey="cityId" listValue="cityName" /></td>
					<td><input type="button" class="button" id="relate_supplier_btn" value="查询" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<table style="width:100%" class="zhanshi_table" cellspacing="0" cellpadding="0" id="relate_show_table">
			<tr>
				<th>&nbsp;</th>
				<th>编号</th>
				<th>供应商名称</th>
				<th>录入时间</th>
				<th>供应商地区</th>
			</tr>
		</table>
		<input type="button" class="button" value="确定" id="relate_supplier_confirm" />
	</div>
</body>
</html>