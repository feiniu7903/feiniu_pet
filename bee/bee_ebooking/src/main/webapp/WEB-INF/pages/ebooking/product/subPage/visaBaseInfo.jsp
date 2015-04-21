<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="'ABROAD_PROXY'==ebkProductViewType">
	<tr>
		<td align="right">最少成团人数：</td>
		<td><s:textfield name="ebkProdProduct.initialNum"/></td>
		<td align="right">与游客签署合同范本：</td>
		<td><s:select name="ebkProdProduct.econtractTemplate" list="#{'':'请选择','GROUP_ABROAD_ECONTRACT':'团队出境游合同','PRE_PAY_ECONTRACT':'预付款协议'}" listKey="key" listValue="value"/></td>
		<td><span tip-content="团队出境游合同用于出境跟团游产品；<br/> 预付款协议用于长线跟团游酒店等无法确认产品或马代等需先全额付款后确认资源产品；<br/>出境自由行则无需选择合同及协议。 " class="text_ts tip-icon tip-icon-info"></td>
	</tr>
	<tr>
		<td align="right"><span class="red_ff4444">*</span>区域划分：</td>
		<td><s:select list="regionNames"	maxLength="50" name="ebkProdProduct.regionName" listKey="code"	listValue="cnName" headerKey="" headerValue="请选择"	messagetitle="区域划分" /></td>
		<td align="right"><span class="red_ff4444">*</span>送签国家：</td>
		<td><input type="text" id="countryName" class="searchInput" name="ebkProdProduct.country" autocomplete="off" value="<s:property value="ebkProdProduct.country"/>"></td>
		<td></td>								
	</tr>
	<tr>
		<td align="right"><span class="red_ff4444">*</span>送签城市：</td>
		<td><s:select list="visaCitys"	maxLength="50" name="ebkProdProduct.city" listKey="code"	listValue="cnName" headerKey="" headerValue="请选择"	messagetitle="送签城市" /></td>
		<td align="right"><span class="red_ff4444">*</span>送签类型：</td>
		<td colspan="2"><s:radio name="ebkProdProduct.visaType" list="#{'GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','REGISTER_VISA':'签注'}" listKey="key" listValue="value" labelSeparator="true" cssStyle="width:20px"/></td>
	</tr>
</s:if>