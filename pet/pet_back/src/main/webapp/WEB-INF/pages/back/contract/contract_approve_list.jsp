<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同列表</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
</head>
<body>
<form action="${basePath}/contract/approve/list.do" method="post">
	 <table border="0" cellspacing="0" cellpadding="0" class="search_table">
				<tr>
					<td>录入人：</td>
					<td><input type="text" name="contract.operateName" value="<s:property value="contract.operateName"/>" /></td>
					<td>供应商名称：</td>
					<td><s:hidden name="contract.supplierId" id="search_supplierId" /><input
						type="text" name="supplierName" id="search_supplierName"
						value="<s:property value="supplierName"/>" /></td>
					<td>合同编号：</td>
					<td><input type="text" name="contract.contractNo" value="<s:property value="contract.contractNo"/>" /></td>
					<td><input type="submit" class="button" value="搜索" /></td>
				</tr>
			</table>
</form>
	 <table border="0" cellspacing="0" cellpadding="0" class="gl_table">
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>供应商名称</th>
					<th>录入人</th>
					<th>录入时间</th>
					<th>是否上传扫描件</th>
					<th>审核</th>
				</tr>
				<s:iterator value="pagination.items">
					<tr>
						<td><s:property value="contractId"/> </td>
						<td><s:property value="contractNo"/> </td>
						<td><s:property value="supplierName"/></td>
						<td><s:property value="operateName"/></td>
						<td><s:date name="createTime" format="yyyy-MM-dd" /> </td>
						<td><s:if test="fsCId != null">
								已上传
							</s:if>
							<s:else>
								未上传
							</s:else>
						</td>
						<td>
						<mis:checkPerm permCode="3199" permParentCode="${permId}">
									<a href="javascript:void(0)" onclick="$('#upload_file').showWindow({width:300,data:{'contract.contractId':<s:property value="contractId" />}})">上传合同</a>
						</mis:checkPerm>
						<mis:checkPerm permCode="3231" permParentCode="${permId}">
							<a href="javascript:void(0)" onclick="$('#contract_approve').showWindow({width:1000,title:'审核:',data:{'contract.contractId':'<s:property value="contractId" />'}})">审核</a>
						</mis:checkPerm></td>
					</tr>
				</s:iterator>
	</table>
	<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
	<div id="contract_approve" url="${basePath}/contract/approve.do"></div>
	<div id="upload_file" url="${basePath}/contract/uploadContract.do"></div>
	<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			$("#search_supplierName").jsonSuggest({
				url : basePath + "/sup/searchSupplierJSON.do",
				maxResults : 10,
				width : 300,
				emptyKeyup : false,
				minCharacters : 1,
				onSelect : function(item) {
					$("#search_supplierId").val(item.id);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#search_supplierId").val("");
				}
			});
		});
	})
	</script>
</body>
</html>