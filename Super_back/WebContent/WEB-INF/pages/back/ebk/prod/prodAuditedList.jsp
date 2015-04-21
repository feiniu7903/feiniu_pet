<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>EBK供应商已审核产品</title>
<jsp:include page="/WEB-INF/pages/back/ebk/prod/ebkProdRes.jsp" />
<jsp:include page="/WEB-INF/pages/back/ebk/prod/prodAuditJs.jsp" />
</head>
<body>
	<%request.setAttribute("tabName","CHECKED"); %>
	<jsp:include page="/WEB-INF/pages/back/ebk/prod/prodAuditTabs.jsp"  />
	<ul class="gl_top">
	<form id="prodApprovalAuditForm" action="${basePath}/ebooking/prod/prodAuditedList.do" method="post" >
		<input id="page" type="hidden" name="page" value="${param.page }"/>
		<table class="newfont06" border="0"  cellpadding="0"  >
				<tr height="30">
					<td width="96px">供应商产品名称：</td>
					<td>
					    <input type="text" name="ebkProdProduct.metaName" maxlength="30" value="${ebkProdProduct.metaName }"/>
					</td>
					<td width="96px">驴妈妈产品名称：</td>
					<td>
						<input type="text" name="ebkProdProduct.prodName" maxlength="30" value="${ebkProdProduct.prodName }" /> 
					</td>
					<td width="96px">销售ID：</td>
					<td>
					    <input type="text" name="ebkProdProduct.prodProductId" maxlength="20" value="${ebkProdProduct.prodProductId }"/>
					</td>
					<td width="96px">供应商名称：</td>
					<td>
						<input type="text" id="supplierIdInput" maxlength="25" name="supplierName" value="${param.supplierName }"/>
						<input type="hidden" name="ebkProdProduct.supplierId" id="comSupplierId" value="${ebkProdProduct.supplierId }"/>
					</td>
					<td></td>
				</tr>
				<tr height="30">
					<td>提交时间：</td>
					<td >
						 <input style="width: 70px;margin-right: 0px;" type="text" id="sumitDateBegin" name="sumitDateBegin" value="${param.sumitDateBegin}"/>
						 <label>~</label>
						 <input style="width: 70px" type="text" id="sumitDateEnd" name="sumitDateEnd" value="${param.sumitDateEnd}"/>
					</td>
					<td>产品经理：</td>
					<td>
						 <input type="text" id="managerIdInput" maxlength="25" name="ebkProdProduct.managerName" value="${ebkProdProduct.managerName }" maxlength="100"/>
						 <input type="hidden" name="ebkProdProduct.managerId" id="comManagerId" value="${ebkProdProduct.managerId }" />
					</td>
					<td>产品类型：</td>
					<td>
						<s:select list="subProductTypeList" name="ebkProdProduct.subProductType" listKey="code" listValue="name"></s:select>
					</td>
					<td>审核结果：</td>
					<td>
						<select name="ebkProdProduct.status" >
							<option value="">请选择</option>
							<option value="THROUGH_AUDIT">审核通过</option>
							<option value="REJECTED_AUDIT">审核不通过</option>
						</select>
					</td>
					<td>
						<input type="submit" class="button btn btn-small" value="查询"/>
	 				</td>
				</tr>
		</table>
	 </form>
	 </ul>
	 <div class="tab_top"></div>
	<table border="0"   cellpadding="0" class="gl_table">
		<tr>
			<th width="200">供应商产品名称</th>
			<th width="40">销售ID</th>
			<th width="200">驴妈妈产品名称</th>
			<th width="120">供应商名称</th>
			<th width="60">产品类型</th>
			<th width="60">申请人</th>
			<th width="60">产品经理</th>
			<th width="30">提交时间</th>
			<th width="60">审核状态</th>
			<th width="30">审核时间</th>
			<th width="40">上下线状态</th>
			<th width="30">操作</th>
		</tr>
		<s:iterator value="ebkProdProductPage.items">
			<tr>
				<td><s:property value="metaName" /></td>
				<td><s:property value="prodProductId" /></td>
				<td><s:if test="prodProductId!=null">
						<a target="_blank" href="http://www.lvmama.com/product/preview.do?id=<s:property value="prodProductId" />">
							<s:property value="prodName" />
						</a>
					</s:if>
					<s:else>
						<s:property value="prodName" />
					</s:else>
				</td>
				<td><a data="<s:property value="supplierId" />"  href="javascript:;" class="showDetail"><s:property value="supSupplierName" /></a></td>
				<td><s:property value="subProductTypeCh" /></td>
				<td><s:property value="applyAuditUserName" /></td>
				<td><s:property value="managerName" /></td>
				<td><s:date name="sumitDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:property value="statusCN" /></td>
				<td><s:date name="examineDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:property value="onLineCh" /></td>
				<td><a href="javascript:prodAuditDetail('<s:property value="ebkProdProductId" />','<s:property value="status" />');">查看</a></td>
			</tr>
		</s:iterator>
		<tr>
			<td>总条数：<s:property value="ebkProdProductPage.totalResultSize" /></td>
			<td colspan="8" align="right">${pageView}</td>
		</tr>
	</table>
</body>
</html>