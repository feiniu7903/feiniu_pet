<%@page import="com.lvmama.comm.vo.Constant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商列表</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>

<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_settlement.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_bcertificate.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_perform.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_contact.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_assess.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_aptitude.js"></script>
<script type="text/javascript" src="${basePath}/js/sup/supplier_list.js"></script>
</head>
<body>
<div><form action="${basePath}/sup/supplierList.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			<tr>
				<td>供应商名称：</td>
				<td>
					<input type="text" class="newtext1" name="supplierName"
						value="<s:property value="supplierName"/>" />
				</td>
				<td>
					供应商地区:
				</td>
				<td>
					<s:select list="provinceList" name="provinceId" listKey="provinceId" listValue="provinceName" cssClass="provinceLoad" cityId="searchCitySelect"/>
					<s:select name="cityId" list="cityList" id="searchCitySelect" listKey="cityId" listValue="cityName"/>
				</td>
				
				<td>供应商ID：</td>
				<td>
					<input type="text" class="newtext1" name="supplierId"
						value="<s:property value="supplierId"/>" />
				</td>
				<td><input type="submit" value="查询" class="button"/>&nbsp;&nbsp;
				<mis:checkPerm permCode="1402">
					<input type="button" value="新增供应商" class="addNewSupplier button"/>
				</mis:checkPerm>
				</td>
			</tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>编号	</th>
			<th>供应商名称</th>
			<th>上级供应商</th>
			<th>供应商地区</th>
			<th>得分</th>
			<th>操作</th>
		</tr>
		<s:iterator value="pagination.items" var="supplier">			
		<tr>
			<td><s:property value="supplierId"/></td>
			<td><a href="javascript:void(0)" data="${supplier.supplierId}" class="showDetail"><s:property value="supplierName"/></a></td>
			<td><s:property value="parentSupplier.supplierName"/></td>
			<td><s:property value="comCity.cityName"/></td>
			<td><s:property value="assessPoints"/></td>
			<td>
				<mis:checkPerm permCode="1403">
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editSupplier">修改</a>|
				</mis:checkPerm>
				<mis:checkPerm permCode="1404">
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editContact">管理联系人</a>|
				</mis:checkPerm>
				<mis:checkPerm permCode="1405">
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editPerform">履行对象</a>|
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editSettlement">结算对象</a>|
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editBCertificate">凭证对象</a>|
				</mis:checkPerm>
				<mis:checkPerm permCode="3197">
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editAptitude">资质审核</a>|
				</mis:checkPerm>
				<mis:checkPerm permCode="3198">
					<a href="javascript:void(0)" data="${supplier.supplierId}" class="editAssess">考核</a>|
				</mis:checkPerm>
				<a href="javascript:void(0)" class="showLogDialog" param="{'parentId':${supplier.supplierId},'parentType':'SUP_SUPPLIER'}">日志</a>
			</td>					
		</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
</div>
<div id="editSettlement" url="${basePath}/sup/target/settlement.do"></div>
<div id="editBCertificate" url="${basePath}/sup/target/bCertificate.do"></div>
<div id="editPerform" url="${basePath}/sup/target/perform.do"></div>
<div id="editContact" url="${basePath}/sup/contact/contactList.do"></div>
<div id="editAssess" url="${basePath}/sup/assess/assessList.do"></div>
<div id="editAptitude" url="${basePath}/sup/aptitude/aptitudeList.do"></div>
<div id="supplierDetail" url="${basePath}/sup/detail.do"></div>
<div id="bindContactDiv" url="${basePath}/sup/contact/selectContact.do"></div>
<div id="editSupplier" url="${basePath}/sup/toAddSupplier.do"></div>
</body>
</html>