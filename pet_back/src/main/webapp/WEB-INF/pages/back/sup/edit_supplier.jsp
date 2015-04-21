<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>供应商基本信息</title>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_form.js"></script>
</head>
<body>
	<form id="supplierForm" method="post"
		action="${basePath}/sup/saveSupplier.do">
		<s:hidden name="supplier.supplierId" />
		<table class="cg_xx" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td><span class="required">*</span>供应商名称</td>
				<td><s:if test="supplier==null||supplier.supplierId==null">
						<s:textfield name="supplier.supplierName" cssClass="required maxlength40" maxlength="50" />
					</s:if> <s:else>
						<s:property value="supplier.supplierName" />
					</s:else></td>
				<td><span class="required">*</span>供应商类型</td>
				<td><s:select list="supplierTypeList" cssClass="required"
						listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
						name="supplier.supplierType" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>所在省市</td>
				<td><s:select list="provinceList" name="province"
						id="proviceSelect" listKey="provinceId" listValue="provinceName" />
					<s:select name="supplier.cityId" list="cityList" id="citySelect"
						listKey="cityId" listValue="cityName" cssClass="required" /></td>
				<td><span class="required">*</span>地址</td>
				<td><s:textfield name="supplier.address" cssClass="required maxlength80" maxlength="100" /></td>
			</tr>
			<tr>
				<td><span class="required">*</span>供应商电话</td>
				<td><s:textfield name="supplier.telephone" cssClass="required" /></td>
				<td><span class="required">*</span>传真</td>
				<td><s:textfield name="supplier.fax" cssClass="required" /></td>
			</tr>
			<tr>
				<td>网址</td>
				<td><s:textfield name="supplier.webSite" /></td>
				<td><span class="required">*</span>邮编</td>
				<td><s:textfield name="supplier.postcode" maxlength="6"  cssClass="required" /></td>
			</tr>
			<tr>
				<td>法定代表人</td>
				<td><s:textfield name="supplier.legalPerson" maxlength="10" /></td>
				<td><span class="required">*</span>我方负责人</td>
				<td><s:textfield name="supplier.bosshead" cssClass="required"
						maxlength="10" /></td>
			</tr>
			<tr>
				<td>父供应商</td>
				<td><s:hidden name="supplier.parentId" /><input type="text"
					name="supplier_suggest_id" id="supplier_suggest_id"
					value="${parentSupplierName}" /></td>
				<td>旅行社许可证号</td>
				<td><s:textfield name="supplier.travelLicense" maxlength="100" /></td>
			</tr>
			<tr>
				<td>预存款预警金额</td>
				<td><s:textfield name="supplier.advancedpositsAlertYuan"
						cssClass="number" maxLength="7" /></td>
				<td>押金回收时间</td>
				<td><input type="text" name="supplier.foregiftsAlert"
					value="<s:date name="supplier.foregiftsAlert" format="yyyy-MM-dd"/>"
					class="dateISO" /></td>
			</tr>
			<tr>
				<td>我方结算主体</td>
				<td><s:select list="settlementCompanyList"
						name="supplier.companyId" listKey="code" listValue="cnName" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<center>
			<input type="submit" value="  保存    " class="button" />
		</center>
	</form>
</body>
<div id="fff"></div>
</html>