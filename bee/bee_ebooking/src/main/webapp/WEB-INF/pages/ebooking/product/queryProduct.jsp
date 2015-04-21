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
<body id="body_cpgl">
	<s:include value="./subPage/ebkProductHead.jsp"></s:include>
	<s:include value="../../common/head.jsp"></s:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->
	<jsp:include page="./subPage/queryLabel.jsp"></jsp:include>
	<ul class="order_all order_all_list">
		<s:if test="'UNCOMMIT_AUDIT'==auditStatus"><jsp:include page="./subPage/unCommitProduct.jsp"></jsp:include></s:if>
		<s:elseif test="'PENDING_AUDIT'==auditStatus"><jsp:include page="./subPage/queryPending.jsp"></jsp:include></s:elseif>
		<s:elseif test="'REJECTED_AUDIT'==auditStatus"><jsp:include page="./subPage/queryRejected.jsp"></jsp:include></s:elseif>
		<s:elseif test="'THROUGH_AUDIT'==auditStatus"><jsp:include page="./subPage/queryThrough.jsp"></jsp:include></s:elseif>
		<s:else><jsp:include page="./subPage/allProduct.jsp"></jsp:include></s:else>
	</ul>
	<!--公用底部-->
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
