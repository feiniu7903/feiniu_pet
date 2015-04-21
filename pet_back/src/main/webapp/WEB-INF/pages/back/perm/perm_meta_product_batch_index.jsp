<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript"
	src="<%=basePath%>/js/perm/perm_meta_product_batch_index.js"></script>
</head>
<body>
	<form id="searchForm" action="search.do" method="post">
	<input type="hidden" name="allData" value="${allData }" />
	<ul class="gl_top">
		<li>采购经理：
			<input id="metaManagerInput" type="text"   name="metaManagerName" 
				value="${metaManagerName}" class="input_b">
			<input id="metaManagerIdHd" type="hidden"   name="metaManagerId" 
				value="${metaManagerId}" class="input_b" label="${metaManagerName}">
		</li>
		<li>供应商名称：
			<input id="supplierInput" type="text"   name="supplierName" 
				value="${supplierName }" class="input_b">
			<input id="supplierHd" type="hidden"   name="supplierId" 
				value="${supplierId }" class="input_b" label="${supplierName }">
		</li>
		<li>产品名称：<input id="proudctNameInput"   name="productName" 
			value="${productName }" class="input_b"></li>
		<li>产品ID：<input id="productIdInput" name="productId"
			value='<s:property value="productId"/>' class="input_b"></li>
		<li>状态：
			<select id="statusSlct" name="validY">
				<option value="">全部</option>
				<option value="true" 
					<s:if test="#request.validY == \"true\""> selected="selected"</s:if>>
					开启</option>
				<option value="false"
					<s:if test="#request.validY == \"false\""> selected="selected"</s:if>>
					关闭</option>
			</select>
		</li>
		<li>所属部门：${orgId }
			<select id="orgSlct" name="departmentId">
				<option value="" >请选择</option>
				<s:iterator value="#request.departmentsList" var="item">
					<option value="${item.orgId }" 
						<s:if test="#item.orgId == #request.departmentId"> selected="selected"</s:if>>
						${item.departmentName }
					</option>
				</s:iterator>
			</select>
		</li>
		<li>产品类型：
			门票&nbsp;&nbsp;
			<select id="ticketSlct" name="ticketType">
				<option value="none" <s:if test="\"none\"== #request.ticketType"> selected="selected"</s:if>>请选择</option>
				<option value="" <s:if test="\"\"== #request.ticketType"> selected="selected"</s:if>>
					全部</option>
				<option value="SINGLE" <s:if test="\"SINGLE\"== #request.ticketType"> selected="selected"</s:if>>单门票</option>
				<option value="WHOLE" <s:if test="\"WHOLE\"== #request.ticketType"> selected="selected"</s:if>>通票</option>
				<option value="UNION" <s:if test="\"UNION\"== #request.ticketType"> selected="selected"</s:if>>联票</option>
				<option value="SUIT" <s:if test="\"SUIT\"== #request.ticketType"> selected="selected"</s:if>>套票</option>
			</select>
		</li>
		<li>
			酒店&nbsp;&nbsp;
			<select id="hotelSlct" name="hotelType">
				<option value="none" <s:if test="\"none\"== #request.hotelType"> selected="selected"</s:if>>请选择</option>
				<option value="" <s:if test="\"\"== #request.hotelType"> selected="selected"</s:if>>全部</option>
				<option value="HOTEL" <s:if test="\"HOTEL\"== #request.hotelType"> selected="selected"</s:if>>酒店</option>
			</select>
		</li>
		<li>
			线路&nbsp;&nbsp;
			<select id="routeSlct" name="routeType">
				<option value="none" <s:if test="\"none\"== #request.routeType"> selected="selected"</s:if>>请选择</option>
				<option value=""  <s:if test="\"\"== #request.routeType"> selected="selected"</s:if>>全部</option>
				<option value="FREENESS" <s:if test="\"FREENESS\"== #request.routeType"> selected="selected"</s:if>>自由行</option>
				<option value="GROUP" <s:if test="\"GROUP\"== #request.routeType"> selected="selected"</s:if>>境内跟团游</option>
				<option value="GROUP_FOREIGN" <s:if test="\"GROUP_FOREIGN\"== #request.routeType"> selected="selected"</s:if>>境外跟团游</option>
				<option value="FREENESS_FOREIGN" <s:if test="\"FREENESS_FOREIGN\"== #request.routeType"> selected="selected"</s:if>>境外自由行</option>
			</select>
		</li>
		<li>
			其他&nbsp;&nbsp;
			<select id="otherSlct" name="otherType">
				<option value="none" <s:if test="\"FREENESS_FOREIGN\"== #request.otherType"> selected="selected"</s:if>>请选择</option>
				<option value=""  <s:if test="\"\"== #request.otherType"> selected="selected"</s:if>>全部</option>
				<option value="INSURANCE" <s:if test="\"INSURANCE\"== #request.otherType"> selected="selected"</s:if>>保险</option>
				<option value="FANGCHA" <s:if test="\"FANGCHA\"== #request.otherType"> selected="selected"</s:if>>房差</option>
				<option value="OTHER" <s:if test="\"OTHER\"== #request.otherType"> selected="selected"</s:if>>其它</option>
			</select>
		</li>
		<li>
			<input type="submit" value="查询"> 
			<input type="button" value="批量修改采购产品经理" onclick="batchEditHandler()">
		</li>
	</ul>
	</form>
	<div class="tab_top"></div>
	<table cellspacing="0" cellpadding="0" class="gl_table">
		<tbody>
			<tr>
				<th></th>
				<th>采购产品ID</th>
				<th>采购产品名称</th>
				<th>部门编号</th>
				<th>所属部门</th>
				<th>原采购经理名</th>
				<th>供应商名称</th>
				<th>产品类型</th>
			</tr>
			<s:iterator value="metaProductPage.items" var="item">
					<tr>
						<td><input type="checkbox" value="${item.metaProductId}" class="ckbClass"></td>
						<td><s:property value="metaProductId" /></td>
						<td><s:property value="productName" /></td>
						<td><s:property value="departmentId" /></td>
						<td><s:property value="departmentName" /></td>
						<td><s:property value="managerName" /></td>
						<td><s:property value="supplierName" /></td>
						<td>
							<s:if test="#item.productType == \"HOTEL\"">
								酒店
							</s:if>
							<s:if test="#item.productType == \"ROUTE\"">
								路线
							</s:if>
							<s:if test="#item.productType == \"TICKET\"">
								门票
							</s:if>
							<s:if test="#item.productType == \"OTHER\"">
								其它
							</s:if>
					</tr>
			  </s:iterator>
			  <tr>
				<td colspan="1">总条数：<s:property value="metaProductPage.totalResultSize" />
				</td>
				<td colspan="7" align="right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(metaProductPage)"/>
				</td>
			  </tr>
		</tbody>
	</table>
	<div id="popWin" style="display: none;">
		<ul class="gl_top">
			<li>采购经理：
				<input id="newMetaManagerInput" type="text" class="input_b">
				<input type="button" onclick="saveManagerHandler()" value="保存">
			</li>
		</ul>
	</div>
</body>
</html>