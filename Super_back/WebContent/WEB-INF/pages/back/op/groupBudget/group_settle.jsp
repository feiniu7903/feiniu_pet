<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>结算付款单</title>
<link rel="stylesheet" href="${basePath}/style/group_cost.css">
<base target="_blank">
<script type="text/javascript"	src="${basePath}js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#supplierSelect").change(function(){
		window.location.href="${basePath}/op/printGroupSettle.do?travelGroupCode=<s:property value="travelGroupCode"/>&supplierId="+$(this).val();
	});
});
</script>
</head>

<body>
<div class="yusuan_box"> 
<table class="noprint"><tbody>
<tr><td>供应商</td>
<td><s:select list="#request.supplierMap"  label="供应商" listKey="key" listValue="value" value="#request.supplierId" id="supplierSelect"></s:select></td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="打　印" id="#printGroupBtn" onclick="window.print(); "></td></tr>
</tbody><tfoot><tr><td>　</td></tr></tfoot></table>
<hr color="blur" class="noprint"/>
<h2>结算付款单</h2>
  <table class="jiesuan_tab">
    	<tbody>
        	<tr>
            	<td class="text_bold" colspan="2" width="12%">付款性质：</td>
                <td width="56%" colspan="3">
                	<label><input type="checkbox">押金</label>
                	<label><input type="checkbox">预存款</label>
                    <label><input type="checkbox">订金</label>
                    <label><input type="checkbox">预付款</label>
                </td>
                <td class="text_bold" width="12%">付款方式：</td>
                <td width="20%">
                	<label><input type="checkbox">转账</label>
                	<label><input type="checkbox">支票</label>
                    <label><input type="checkbox">现金</label>
                </td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">结算方式：</td>
                <td colspan="3">
                	<label><input type="checkbox" <s:if test="#request.target.settlementPeriod=='PERMONTH'">checked="checked"</s:if>>月结</label>
                	<label><input type="checkbox" <s:if test="#request.target.settlementPeriod=='PERORDER'">checked="checked"</s:if>>每单结算</label>
                    <label><input type="checkbox" <s:if test="#request.target.settlementPeriod!='PERORDER' && #request.target.settlementPeriod!='PERMONTH'">checked="checked"</s:if>>不定期结算</label>
                </td>
                <td class="text_bold">订金：</td>
                <td>
                	<label><input type="checkbox">有</label>
                	<label><input type="checkbox">无</label>
                </td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">Application：<br>申请人：</td>
                <td width="20%"></td>
                <td class="text_bold" width="15%">Department：<br>部门：</td>
                <td width="20%"></td>
                <td class="text_bold">Appl. Date：<br>申请日期：</td>
                <td><s:date name="#request.applyDate" format="yyyy年MM月dd日"/></td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">Payer：<br>出款公司：</td>
                <td colspan="5">驴妈妈兴旅(<s:property value="#request.filialeName"/>)</td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">结算对象Supplier：</td>
                <td colspan="3"><s:property value="#request.target.name"/></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">Vendor：<br>供应商：</td>
                <td><s:property value="#request.supplier.supplierName"/></td>
                <td class="text_bold">Vendor ID：<br>供应商ID：</td>
                <td>${supplierId}</td>
                <td class="text_bold">发票：</td>
                <td>
                	<label><input type="checkbox">有</label>
                	<label><input type="checkbox">无</label><br>
                    　其他：<input class="text_d" type="text">
                </td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">收款人Payee：</td>
                <td colspan="3"><s:property value="#request.target.name"/></td>
                <td class="text_bold">GP%:毛利率：</td>
                <td><s:if test="#request.finalBudget.actProfitRate!=null"><fmt:formatNumber pattern="#,##0.00" value="${finalBudget.actProfitRate*100}" />%</s:if></td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">开户行全称<br>Bank Full Name:</td>
                <td colspan="3"><s:property value="#request.target.bankName"/></td>
                <td class="text_bold">Bank A/C：<br>银行账号：</td>
                <td><s:property value="#request.target.bankAccount"/></td>
            </tr>
            <tr>
            	<td class="text_bold" colspan="2">订单号：<br>Order No：</td>
                <td colspan="3"></td>
                <td class="text_bold">团号：</td>
                <td>${travelGroupCode}</td>
            </tr>
            <tr>
            	<td class="text_c" width="58%" colspan="5">付款用途  Description </td>
                <td class="text_c">币种 Currency</td>
                <td class="text_c">金额  Amount</td>
            </tr>
				<s:iterator value="#request.fixedList" var="fixed" status="st">
					<tr>
						<td><s:property value="#st.index+1"/></td>
						<td colspan="4">${fixed.costsItemName }</td>
						<td>${fixed.currencyName }</td>
						<td><fmt:formatNumber pattern="#,##0.00" value="${fixed.subtotalCosts}" /></td>
					</tr>
				</s:iterator>
				<tr>
            	<td class="text_c" colspan="2">金额（大写）</td>
                <td colspan="3"><s:property value="#request.totalSettleZH"/></td>
                <td class="text_c">合计（小写）</td>
                <td>￥<fmt:formatNumber pattern="#,##0.00" value="${totalSettle}" /></td>
            </tr>
        </tbody>
    </table>
    <table class="jiesuan_tab2">
    	<tr>
        	<td class="text_c" width="12%">部门审批人：</td>
            <td><input class="text_d" type="text"></td>
            <td class="text_c" width="120">总经理/CEO：</td>
            <td><input class="text_d" type="text"></td>
        </tr>
        <tr>
        	<td class="text_c">财务核算人：</td>
            <td><input class="text_d" type="text"></td>
            <td class="text_c">财务总监/CFO：</td>
            <td><input class="text_d" type="text"></td>
        </tr>
        <tr>
        	<td></td>
            <td></td>
            <td class="text_c">董事长：</td>
            <td><input class="text_d" type="text"></td>
        </tr>
    </table>
</div>
</body>
</html>
