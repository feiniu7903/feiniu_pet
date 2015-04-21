<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>保险产品列表</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>

</head>
<body>
<div><form action="${basePath}/prod/insurance/list.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			<tr>
				<td>
					产品标识:
				</td>
				<td>
					<input type="text" class="newtext1" name="productId" value="${productId}" />	
				</td>
				<td>产品名称：</td>
				<td>
					<input type="text" class="newtext1" name="productName" value="${productName}" />
				</td>
				<td>
					供应商:
				</td>
				<td>
					<s:select list="#{'':'所有','21':'平安保险','6054':'大众保险'}" name="supplierId"></s:select>
				</td>
				<td>
					<input type="submit" value="查询" class="button"/>&nbsp;&nbsp;
					<mis:checkPerm permCode="1402"></mis:checkPerm>
					<input type="button" value="新增保险产品" class="addNewInsurance button"/>
				</td>
			</tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>产品ID</th>
			<th>销售产品名称</th>
			<th>销售价格</th>
			<th>结算价格</th>
			<th>保险天数</th>
			<th>供应商</th>
			<th>承保单位产品标识</th>
			<th>操作</th>
		</tr>
		<s:iterator value="pagination.items" var="insurances">			
			<tr>
				<td><s:property value="productId"/></td>
				<td><s:property value="productName"/></td>
				<td><s:property value="sellPriceYuan"/></td>
				<td><s:property value="settlementPriceYuan"/></td>
				<td><s:property value="days"/></td>
				<td><s:property value="zhSupplierName"/></td>
				<td><s:property value="productIdSupplier"/></td>
				<td>
					<a href="javascript:void(0)" data="${productId}" class="editProdInsurance">修改</a>|
					<a href="javascript:void(0)" data="${productId}" class="copyProdInsurance">复制</a>|
					<a href="javascript:void(0)" param="{'objectId':'<s:property value="productId" />','objectType':'PROD_PRODUCT'}" class="showLogDialog">日志</a>
				</td>					
			</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="7" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
</div>
<div id="editInsurance" url="${basePath}/prod/insurance/edit.do"></div>
<script type="text/javascript">
	$(function(){
		$("input.addNewInsurance").click(function(){
			$("#editInsurance").showWindow();
		});
		
		//修改产品
		$("a.editProdInsurance").click(function(){
			var productId=$(this).attr("data");
			$("#editInsurance").showWindow({
				url:basePath+"/prod/insurance/edit.do",
				data:{"productId":productId}});
		});
		//复制产品
		$("a.copyProdInsurance").click(function(){
			var productId=$(this).attr("data");
			$("#editInsurance").showWindow({
				url:basePath+"/prod/insurance/copy.do",
				data:{"productId":productId}});
		});
		
	});
</script>
</body>
</html>