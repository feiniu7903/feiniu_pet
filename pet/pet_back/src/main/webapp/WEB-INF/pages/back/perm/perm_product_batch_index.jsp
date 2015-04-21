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
	src="<%=basePath%>/js/perm/perm_product_batch_index.js"></script>
</head>
<body>
	<form id="searchForm" action="search_product.do" method="post">
	<ul class="gl_top">
		<li>产品经理：
			<input id="managerInput" type="text"   name="managerName" 
				value="${managerName}" class="input_b">
			<input id="managerIdHd" type="hidden"   name="managerId" 
				value="${managerId}" class="input_b" label="${managerName}">
		</li>	
		
		<li>产品ID：<input id="productIdInput" name="productId"
			value='<s:property value="productId"/>' class="input_b"></li>
		<li>产品名称：<input id="proudctNameInput"   name="productName" 
			value="${productName }" class="input_b"></li>
		<li>目的地：
			<input id="placeInput" type="text"   name="placeName" 
				value="${placeName}" class="input_b">
			<input id="placeIdHd" type="hidden"   name="placeId" 
				value="${placeId}" class="input_b" label="${placeName}">
		</li>
		<li>商品类型：
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
		<li>上线状态：
			<input id="valid1" type="radio" style="margin-right: 1px;" name="onlineStatus" <s:if test="#request.onlineStatus">  checked="checked" </s:if> value="true"/><label for="valid1" style="margin-right: 5px;">在线  </label>
			<input id="valid2"  type="radio" style="margin-right: 1px;" name="onlineStatus" value="false" <s:if test="#request.onlineStatus==false">  checked="checked" </s:if> /><label for="valid2" style="margin-right: 20px;">已下线</label>
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
		<li>
			<input type="submit" value="查询"> 
			<input type="button" value="批量修改产品经理" onclick="batchEditHandler()">
		</li>
	</ul>
	</form>
	<div class="tab_top"></div>
	<table cellspacing="0" cellpadding="0" class="gl_table">
		<tbody>
			<tr>
				<th></th>
				<th>产品ID</th>
				<th>产品名称</th>
				<th>部门编号</th>
				<th>所属部门</th>
				<th>原产品经理</th>
				<th>目的地</th>
				<th>产品类型</th>
			</tr>
			<s:iterator value="prodProductPage.items" var="item">
					<tr>
						<td><input type="checkbox" value="${item.productId}" class="ckbClass"></td>
						<td><s:property value="productId" /></td>
						<td><s:property value="productName" /></td>
						<td><s:property value="departmentId" /></td>
						<td><s:property value="departmentName" /></td>
						<td><s:property value="realName" /></td>
						<td><s:property value="placeName" /></td>
						<td><s:property value="zhProductType" /></tr>
			  </s:iterator>
			  <tr>
				<td colspan="1">总条数：<s:property value="prodProductPage.totalResultSize" />
				</td>
				<td colspan="7" align="right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(prodProductPage)"/>
				</td>
			  </tr>
		</tbody>
	</table>
	<div id="popWin" style="display: none;">
		<ul class="gl_top">
			<li>产品经理：
				<input id="newManagerInput" type="text" class="input_b">
				<input type="button" onclick="saveManagerHandler()" value="保存">
			</li>
		</ul>
	</div>
</body>
</html>