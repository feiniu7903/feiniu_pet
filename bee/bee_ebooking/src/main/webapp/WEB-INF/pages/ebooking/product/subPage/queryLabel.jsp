<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<dl class="order_nav order_nav_js">
	<dt><s:property value="EbkProductViewTypeCh"/>产品</dt>
    <dd <s:if test="null==auditStatus"> class="order_nav_dd" </s:if>><a href="${basePath}product/queryProduct.do?ebkProductViewType=<s:property value="ebkProductViewType"/>">所有产品（<font color="red" id="ALLproductcountid">0</font>）</a></dd>
    <dd <s:if test="'UNCOMMIT_AUDIT'==auditStatus"> class="order_nav_dd" </s:if>><a href="${basePath}product/query/queryUnCommit.do?ebkProductViewType=<s:property value="ebkProductViewType"/>&auditStatus=UNCOMMIT_AUDIT">未提交审核（<font color="red" id="UNCOMMIT_AUDITproductcountid">0</font>）</a></dd>
    <dd <s:if test="'PENDING_AUDIT'==auditStatus"> class="order_nav_dd" </s:if>><a href="${basePath}product/query/queryPending.do?ebkProductViewType=<s:property value="ebkProductViewType"/>&auditStatus=PENDING_AUDIT">待审核（<font color="red" id="PENDING_AUDITproductcountid">0</font>）</a></dd>
    <dd <s:if test="'REJECTED_AUDIT'==auditStatus"> class="order_nav_dd" </s:if>><a href="${basePath}product/query/queryRejected.do?ebkProductViewType=<s:property value="ebkProductViewType"/>&auditStatus=REJECTED_AUDIT">审核不通过（<font color="red" id="REJECTED_AUDITproductcountid">0</font>）</a></dd>
    <dd <s:if test="'THROUGH_AUDIT'==auditStatus"> class="order_nav_dd" </s:if>><a href="${basePath}product/query/queryThrough.do?ebkProductViewType=<s:property value="ebkProductViewType"/>&auditStatus=THROUGH_AUDIT">审核通过（<font color="red" id="THROUGH_AUDITproductcountid">0</font>）</a></dd>
	<s:iterator value="parameters" id="column">
		<input type="hidden" id="<s:property value="key"/>_parameterhidden" value="<s:property value="value"/>"/>
	</s:iterator>
</dl>