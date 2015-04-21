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
<title>驴妈妈供应商管理系统</title>
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
			<li class="tab_this"  id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
		</ul>
		<div class="xzxx_box_list" style="display:block;">
			<s:hidden id="ebkProdProductId" name="ebkProdProductId"></s:hidden>
			<s:hidden id="ebkProdProduct_metaName" name="metaName"/>
			<s:hidden id="toShowEbkProduct" name="toShowEbkProduct"/>
			<s:if test='"SHOW_EBK_PRODUCT"!=toShowEbkProduct'>
			<span class="fp_btn ebkprodbranchnew">新增产品类别</span>　
				<s:if test="ebkProdBranchList.size()==0">
				<span class="fp_btn js_xzkc ebkProdBranchListNotEmpty" style="display:none;">新增价格和库存</span>　
				<%--<span class="fp_btn js_xgjgkc ebkProdBranchListNotEmpty" style="display:none;">修改已通过价格和库存</span>　 --%>
				<span class="fp_btn js_xgjg ebkProdBranchListNotEmpty" style="display:none;">只修改已通过价格</span>　
				<span class="fp_btn js_xgkc ebkProdBranchListNotEmpty" style="display:none;">只修改已通过库存</span>
				</s:if>
				<s:else>
				<span class="fp_btn js_xzkc ebkProdBranchListNotEmpty" >新增价格和库存</span>　
				<%--<span class="fp_btn js_xgjgkc ebkProdBranchListNotEmpty">修改已通过价格和库存</span>　--%>
				<span class="fp_btn js_xgjg ebkProdBranchListNotEmpty" >只修改已通过价格</span>　
				<span class="fp_btn js_xgkc ebkProdBranchListNotEmpty"  >只修改已通过库存</span>
				</s:else>
			</s:if>
			<div id="queryebkprodbranchdiv">
				<s:include value="./subPage/queryGroupEbkProdBranch.jsp"></s:include>
			</div>
			<div class="tishi_text"> 
			<p>审核状态：<span class="orange2">未提交</span><span class="green">通过</span><span class="chengse">待审核</span><span class="zise">拒绝</span></p> 
			每日日历框左侧显示已通过的数值，未提交、待审核、被拒绝的数值在日历框右侧显示 
			</div>
		</div>
	</div>
	<!--公用底部-->
	<jsp:include page="../../common/footer.jsp"></jsp:include>
	
	<div id='dateAllInfoDiv'>
		<table id="dataAllInfoTable" style="width:240px;" >
			<thead><tr class="eject_rz_table_tr"><td width="80" show_date="true"><b></b></td><td align="right" width="80">已通过/已修改</td></tr></thead>
			<tbody>
			<tr><td>是否关班：</td><td class="c_gray" columnname="forbiddenSell" showold="old"></td><td columnname="forbiddenSell" shownew="new"></td></tr>
			<tr><td>提前预订(小时)：</td><td class="c_gray"><span id="aheadHourOld" columnname="aheadHour" showold="old"></span><span columnname="aheadHour" shownew="new"></span></td><td></td></tr>
			<tr><td>退改策略：</td><td class="c_gray" columnname="cancelStrategy" showold="old"></td><td columnname="cancelStrategy" shownew="new"></td></tr>
			<tr><td>门市价(￥)：</td><td class="c_gray" columnname="marketPrice" showold="old"></td><td class="menshijia" columnname="marketPrice" shownew="new"></td></tr>
			<tr><td>销售价(￥)：</td><td class="c_gray" columnname="price" showold="old"></td><td class="shoujia" columnname="price" shownew="new"></td></tr>
			<tr><td>结算价(￥)：</td><td class="c_gray"  columnname="settlementPrice" showold="old"></td><td class="jiesuanjia" columnname="settlementPrice" shownew="new"></td></tr>
			<tr><td class="kucun">日库存量：</td><td class="c_gray" columnname="dayStock" showold="old"></td><td class="kucun" columnname="dayStock" shownew="new"></td></tr>
			<tr><td class="ziyuan">资源审核：</td><td class="c_gray" columnname="resourceConfirm" showold="old"></td><td class="ziyuan" columnname="resourceConfirm" shownew="new"></td></tr>
			<tr><td class="caomai">超卖：</td><td class="c_gray" columnname="overSale" showold="old"></td><td class="caomai" columnname="overSale" shownew="new"></td></tr>
			</tbody>
		</table>
	</div>
</body>
</html>