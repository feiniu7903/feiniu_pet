<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="no-cache">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<title>驴妈妈供应商管理系统-关联销售产品</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
</head>
<body id="body_cpgl" class="ebooking_price">
	<s:include value="./subPage/ebkProductHead.jsp"></s:include>
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<jsp:include page="./subPage/navigation.jsp"></jsp:include>
	<!--以上是公用部分-->
	<div class="xzxx_box">
		<ul class="xzxx_box_tab">
			<li id="EBK_AUDIT_TAB" onclick="window.location.href='${basePath}ebooking/product/editEbkProductInit.do?ebkProdProductId=<s:property value="ebkProdProductId"/>&toShowEbkProduct=<s:property value="toShowEbkProduct"/>'">产品基础信息</li>
			<li id="EBK_AUDIT_TAB_TIME_PRICE"  onclick="window.location.href='${basePath}product/branch/ebkProdBranch.do?ebkProdProductId=<s:property value="ebkProdProductId"/>&toShowEbkProduct=<s:property value="toShowEbkProduct"/>'">价格/库存维护</li>
			<li class="tab_this"  id="EBK_AUDIT_TAB_RELATION">关联销售产品</li>
		</ul>
		<div class="xzxx_box_list" style="display:block;">
			<s:hidden id="ebkProdProductId" name="ebkProdProductId"></s:hidden>
			<s:hidden id="ebkProdProduct_metaName" name="metaName"/>
			<s:hidden id="toShowEbkProduct" name="toShowEbkProduct"/>
			<s:if test='"SHOW_EBK_PRODUCT"!=toShowEbkProduct'>
			<div>
				<table class="kcwh_table">
					<tr>
						<td width="80">　产品类型</td><td><s:select list="#{'VISA':'签证'}" listKey="key"	name="productType" listValue="value" cssStyle="width:80px;" value="'VISA'" theme="simple"/></td>
						<td width="80">　产品名称</td><td><input type="text" class="searchInput" autocomplete="off" id="ebkRelationProduct" productid="" productname=""/></td>
						<td width="104">　<select name="productBranchId" style="width:80px;"/></td><td width="80"><span class="fp_btn addEbkProdRelation">增加关联</span></td>
					</tr>
				</table>
			</div>
			</s:if>
			<div>
				<table class="kcwh_table" style="width:70%">
					<thead>
						<tr>
							<th width="80">产品类型</th>
							<th width="80">关联产品ID</th>
							<th>关联产品名称</th>
							<th width="80">类别</th>
							<th width="80">价格库存</th>
							<s:if test='"SHOW_EBK_PRODUCT"!=toShowEbkProduct'><th width="80">操作</th></s:if>
						</tr>
					</thead>
					<tbody>
							<s:iterator value="relations" var="relation" id="column">
								<tr>
									<td><s:property value="relateProductTypeCh"/></td>
									<td><s:property value="relateProductId"/></td>
									<td><s:property value="relateProductName"/></td>
									<td><s:property value="relateProdBranchName" /></td>
									<td prodbranchid="<s:property value="relateProdBranchId" />"><a href="javascript:void(0);" class="showRelationProductBranchTimePrice">查看</a></td>
									<s:if test='"SHOW_EBK_PRODUCT"!=toShowEbkProduct'><td><a href="javascript:void(0)" class="ebkProdRelationDelete" relationid="<s:property value="relationId"/>" relateprodbranchid="<s:property value="relateProdBranchId" />">删除</a></td></s:if>
								</tr>
							</s:iterator>
					</tbody>
					<tbody><tr><td colspan="6">
					<div  id="queryProdTimePriceStockTbody"></div>
					</td></tr></tbody>
				</table>
			</div>
		</div>
	</div>
	<!--公用底部-->
	<jsp:include page="../../common/footer.jsp"></jsp:include>

	<div id='dateAllInfoDiv'>
		<table style="width:180px;">
			<tbody>
			<tr><td>提前预订(小时)</td><td class="c_gray" columnname="aheadHour" showold="old"></td></tr>
			<tr><td>退改策略</td><td class="c_gray" columnname="cancelStrategy" showold="old"></td></tr>
			<tr><td class="jiesuanjia">结算价(￥)</td><td class="c_gray"  columnname="settlementPrice" showold="old"></td></tr>
			<tr><td class="menshijia">门市价(￥)</td><td class="c_gray" columnname="marketPrice" showold="old"></td></tr>
			<tr><td class="kucun">日库存量</td><td class="c_gray" columnname="dayStock" showold="old"></td></tr>
			<tr><td class="ziyuan">资源审核</td><td class="c_gray" columnname="resourceConfirm" showold="old"></td></tr>
			<tr><td class="caomai">超卖</td><td class="c_gray" columnname="overSale" showold="old"></td></tr>
			</tbody>
		</table>
	</div>
</body>
</html>