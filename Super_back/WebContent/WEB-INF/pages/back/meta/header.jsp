<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table border="0" cellSpacing="0" cellPadding="0" class="newInput newInputY">
	<tr>
		<td><em>供应商名称：<span class="require">[*]</span></em></td>
		<td><s:hidden name="metaProduct.supplierId"/><s:if test="metaProduct.metaProductId==null"><input type="text" value="" id="supplierText"/></s:if><s:else><span>${metaProduct.supplierName}</span></s:else></td>
		<td><em>合同：</em></td>
		<td>
		<s:if test="metaProduct.contractId==null"><s:select list="supContractList" listKey="contractId" listValue="contractNo" name="metaProduct.contractId"/></s:if><s:else><span>${metaProduct.contractNo}</span></s:else>
		</td>
	</tr>
	<tr>
		<td><em>采购经理：<span class="require">[*]</span></em></td>
		<td><s:hidden name="metaProduct.managerId"/><input type="text" value="${metaProduct.managerName}" name="permUser" id="inputUserId"></td>

		<s:if test="metaProduct.productType=='HOTEL' || metaProduct.productType=='ROUTE' || metaProduct.productType=='TICKET'">
			<td><em>所在组织：</em></td>
			<td>
				<s:select id="groupName" name="metaProduct.workGroupId" list="groupNameList" listKey="workGroupId" listValue="groupName" headerKey="" headerValue="请选择" />
			</td>
		</s:if>
		<s:else>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</s:else>
	</tr>
	<tr>
		<td><em>产品名称：<span class="require">[*]</span></em></td>
		<td><s:textfield cssClass="text1" name="metaProduct.productName" maxLength="100"/></td>
		<td><em>产品编号：<span class="require">[*]</span></em></td>
		<td><s:textfield cssClass="text1" name="metaProduct.bizCode" maxLength="50"/></td>
	</tr>
	<tr>
		<td><em>预控级别：</em></td>
		<td><s:select name="metaProduct.controlType"
			 value="metaProduct.controlType"
			 list="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_TYPE@values()"
			 listKey="code" listValue="cnName" headerKey="" headerValue="无预控" /></td>
		
		<s:if test="metaProduct.productType!='TRAFFIC'">
			<td><em>采购主体：<span class="require">[*]</span></em></td>
			<td><s:select name="metaProduct.filialeName"
				 value="metaProduct.filialeName"
				 list="@com.lvmama.comm.vo.Constant$FILIALE_NAME@values()"
				 listKey="code" listValue="cnName" headerKey="" headerValue="请选择" /></td>
		</s:if>
			 
	</tr>
	<s:if test="currencyList.size != null && currencyList.size != 0">
		<tr>
			<td><em>币种：<span class="require">[*]</span></em></td>
			<td>
				<s:select id="currencyType" list="currencyList" name="metaProduct.currencyType" listKey="code" listValue="name" headerKey="" headerValue="请选择" />
			</td>
		</tr>
	</s:if>
</table>