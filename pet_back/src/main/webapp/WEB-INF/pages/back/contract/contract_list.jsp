<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同列表</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/contract/contract.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<script type="text/javascript" src="${basePath}/js/base/form.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
</head>
<body>
	<div>
		<form action="${basePath}/contract/contractList.do" method="post">
			<table border="0" cellspacing="0" cellpadding="0"
				class="search_table">
				<tr>
					<td>合同编号：</td>
					<td><input type="text" name="contractNo"
						value="<s:property value="contractNo"/>" /></td>
					<td>经办人：</td>
					<td><input type="text" name="arranger"
						value="<s:property value="arranger"/>" /></td>
					<td>甲方：</td>
					<td><s:select list="finAccountingEntityList"
							data-options="required:true" listKey="accountingEntityId"
							headerValue="请选择" headerKey="" listValue="name" name="partyA"
							cssClass="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>供应商名称：</td>
					<td><s:hidden name="supplierId" id="search_supplierId" /><input
						type="text" name="supplierName" id="search_supplierName"
						value="<s:property value="supplierName"/>" /></td>
					<td>距离合同到期：</td>
					<td><select name="orderby">
						<option value="">请选择</option>
						<option value="asc" <s:if test="orderby=='asc'">selected</s:if>>从小到大</option>
						<option value="desc" <s:if test="orderby=='desc'">selected</s:if>>从大到小</option>
					</select></td>
					<td>审核状态：</td>
					<td><s:select list="contractAuditList"
							data-options="required:true" listKey="code" headerValue="请选择"
							headerKey="" listValue="cnName" name="contractAudit"
							cssClass="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td colspan="2"><input type="submit" class="button" value="查询" />&nbsp;<input
						type="button" class="button" value="新增合同" id="add_contract_btn" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
			<tr>
				<th>序号</th>
				<th>合同名称</th>
				<th>合同编号</th>
				<th>审核状态</th>
				<th>对应供应商</th>
				<th>合同有效期</th>
				<th>距离合同到期</th>
				<th>经办人</th>
				<th>甲方</th>
				<th>操作</th>
			</tr>
			<s:iterator value="pagination.items" var="contract">
				<tr>
					<td><s:property value="contractId" /></td>
					<td><s:property value="contractName" /></td>
					<td><a href="javascript:void(0);" class="contract_detail_show"
						contractId="<s:property value="contractId" />"><s:property
								value="contractNo" /></a></td>
					<td><s:property value="zhContractAudit" /></td>
					<td><s:property value="supplierName" /></td>
					<td><s:date name="beginDate" format="yyyy-MM-dd"/>至<s:date name="endDate" format="yyyy-MM-dd"/></td>
					<td><s:property value="zhDateDiff" /></td>
					<td><s:property value="arranger" /></td>
					<td><s:property value="finAccountingEntityName" /></td>
					<td>
						<a
						onclick="$('#contract_change_div').showWindow({title:'变更及补充单:',data:{contractId:'<s:property value="contractId" />'}})"
						href="javascript:void(0)">合同变更</a>|
						<a href="javascript:void(0)" class="showLogDialog"
						param="{'objectId':'<s:property value="contractId" />','objectType':'SUP_CONTRACT'}">日志</a>
						<s:if test='contractAudit == "REJECTED"'>
							|<a href="javascript:void(0)" class="resubmitVerify" data="${contractId }">再次提交审核</a>
						</s:if>
						</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="2">总条数：<s:property
						value="pagination.totalResultSize" />
				</td>
				<td colspan="8" align="right"><s:property escape="false"
						value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" /></td>
			</tr>
		</table>
	</div>
	<div id="add_contract_div" url="${basePath}/contract/edit_index.do"></div>
	<div id="contract_change_div" url="${basePath}/contract/change.do"></div>
	<div id="contract_detail_div"
		url="${basePath}/contract/contractDetail.do"></div>
</body>
</html>