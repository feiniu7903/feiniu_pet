<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$("#ebkProdRelationOldAuditSign").live('mouseover',function(){
			$("#showEbkProdRelationOldData").show();
		}).live('mouseout',function(){
			$("#showEbkProdRelationOldData").hide();
		});
	});
</script>
<table class="newfont06 ebk_tab_small_table" cellpadding="0" border="0" style="width: 520px" id="ebkProdRelationOldAuditTable">
	<thead>
		<tr>
			<td widtd="100">产品类型<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId" /></td>
			<td widtd="100">关联产品ID</td>
			<td widtd="200">关联产品名称</td>
			<td widtd="100">类别
			<s:if test="ebkProdRelationListMapOld.containsKey('ebkProdRelationListOld')">
				<span class="tip_text" id="ebkProdRelationOldAuditSign">审
			</s:if></td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="ebkProdRelationList" var="relation">
			<tr>
				<td><s:property value="relateProductTypeCh" /></td>
				<td><s:property value="relateProductId" /></td>
				<td><s:property value="relateProductName" /></td>
				<td><s:property value="relateProdBranchName" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

<div id="showEbkProdRelationOldData" style="border:1px solid #cccccc; background:#FFFFEE;padding:5px; display:none; position:absolute; z-index:999;left: 500px; top: 20px;">
	<h2>老数据</h2>
	<s:if test="ebkProdRelationListMapOld.containsKey('ebkProdRelationListOld')">
	<table class="newfont06 ebk_tab_small_table" cellpadding="0" border="1" style="width: 500px">
	<thead>
		<tr>
			<td widtd="100">产品类型</td>
			<td widtd="100">关联产品ID</td>
			<td widtd="200">关联产品名称</td>
			<td widtd="100">类别</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="ebkProdRelationListMapOld['ebkProdRelationListOld']" var="relation">
			<tr>
				<td><s:property value="relateProductTypeCh" /></td>
				<td><s:property value="relateProductId" /></td>
				<td><s:property value="relateProductName" /></td>
				<td><s:property value="relateProdBranchName" /></td>
			</tr>
		</s:iterator>
	</tbody>
	</table>
	</s:if>
	<s:else><h2>无</h2></s:else>
</div>