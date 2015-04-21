<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>驴妈妈兴旅团队收入成本表</title>
<link rel="stylesheet" href="${basePath}/style/group_cost.css">
<base target="_blank">
</head>
<body>
<div class="yusuan_box">
  <h2>驴妈妈兴旅团队收入成本表</h2>
	<table class="yusuan_tab">
    	<tbody>
            <tr>
                <td class="text_bold">产品名称</td>
                <td colspan="3">${productName}</td>
                <td class="text_bold">出发日期</td>
                <td><s:date name="#request.group.visitTime" format="yyyy年MM月dd日"/></td>
                <td class="text_bold" width="10%">领队/导游</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="text_bold" width="10%">团号</td>
                <td width="25%">${travelGroupCode}</td>
                <td class="text_bold" width="5%">人数</td>
                <td width="5%"><s:property value="#request.finalBudget.actPersons"/></td>
                <td class="text_bold" width="10%">回团日期</td>
                <td width="15%">&nbsp;</td>
                <td class="text_bold" width="5%">备注</td>
                <td width="25%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text_bold">结算公司</td>
                <td><label><input type="checkbox" <s:if test="null==#request.filialeName">checked disabled</s:if>/>　驴妈妈兴旅</label></td>
                <td colspan="4"><label><input type="checkbox" <s:if test="null!=#request.filialeName">checked disabled</s:if>/>驴妈妈兴旅</label><input class="text_d" type="text" value="<s:property value="#request.filialeName"/>">分公司</td>
                <td colspan="2"></td>
            </tr>
        </tbody>
    </table>
    <table class="yusuan_tab">
        <tbody>
        <tr>
		  <th rowspan="<s:property value="#request.itemRows"/>" class="l_title" style="width:20px;">应付团款</th>
		  <th width="25%">供应商</th>
		  <th width="10%">供应商ID</th>
		  <th width="15%">付款明细</th>
		  <%-- <th width="10%">单价</th>
		  <th width="10%">小计</th> --%>
		  <th width="20%">金额</th>
		  <th rowspan="<s:property value="#request.itemRows"/>" class="l_title" style="width:20px;">应收团款	</th>
		  <th width="10%">订单号</th>
		  <th width="20%">收款明细/金额</th>
		 </tr>
	<s:iterator value="#request.fixedList" var="fixed" status="st">
		<tr>
		   <td>${fixed.supplierName }</td>
		   <td>${fixed.supplierId }</td>
		   <td>${fixed.costsItemName}</td>
		   <%-- <td><s:if test="null!=#fixed.supplierId"><fmt:formatNumber pattern="#,##0.00" value="${fixed.bgCosts}" /></s:if></td> --%>
		   <td><s:if test="null!=#fixed.supplierId"><fmt:formatNumber pattern="#,##0.00" value="${fixed.subtotalCosts}" /></s:if></td>
		<s:iterator value="#request.productOrders" var="order" status="ot">
			<s:if test="#st.index==#ot.index">
		   <td>${order.orderId }</td>
		   <td><s:if test="null!=#order.orderId"><fmt:formatNumber pattern="#,##0.00" value="${order.actualPay/100}" /></s:if></td>
		   </s:if>
		</s:iterator>
		 </tr>
	</s:iterator>
		<tr>
            	<td class="text_bold">合计：人民币</td>
                <td colspan="2"><s:if test="#request.subtotalCosts!=0"><s:property value="#request.subtotalCostsZH"/></s:if></td>
                <td><s:if test="#request.subtotalCosts!=0">￥<fmt:formatNumber pattern="#,##0.00" value="${subtotalCosts}"/></s:if></td>
                <td class="text_bold">合计：</td>
                <td><s:if test="#request.payedAmount!=0">￥<fmt:formatNumber pattern="#,##0.00" value="${payedAmount}"/></s:if></td>
            </tr>
            <tr>
            	<td class="text_bold">利润：</td>
                <td colspan="3">￥<fmt:formatNumber pattern="#,##0.00" value="${profit}"/></td>
                <td colspan="2"></td>
            </tr>
        </tbody>
    </table>
    <table class="jiesuan_tab2">
    	<tbody><tr>
        	<td class="text_c">填表人：</td>
            <td><input type="text" class="text_d"></td>
            <td class="text_c">填表日期：</td>
            <td><input type="text" class="text_d"></td>
        </tr>
    </tbody></table>
    <table class="noprint" style="align:left;width:100%;"><tbody><tr><td align="right"><input type="button" value="打　印" id="#printGroupBtn" onclick="window.print(); "></td></tr></tbody></table>
</div>
</body>
</html>
