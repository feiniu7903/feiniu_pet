<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ebk" uri="/tld/lvmama-ebk-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
</head>
<body>
<div>
	<!-- 控制登录后的第一个页面显示 -->
	<!--首页  -->
	<ebk:perm permissionId="7" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath}/ebookingindex.do";
	 	</script>
	</ebk:perm>
	<!--订单处理  -->
	<ebk:perm permissionId="13" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/ebooking/task/confirmTaskList.do";
	 	</script>
	</ebk:perm>
	<ebk:perm permissionId="14" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/ebooking/task/confirmRouteTaskList.do";
	 	</script>
	</ebk:perm>
	<ebk:perm permissionId="3" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/eplace/passOrderIndex.do";
	 	</script>
	</ebk:perm>
	<!--房价维护  -->
	<ebk:perm permissionId="9" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/ebooking/houseprice/changePriceSuggest.do";
	 	</script>
	</ebk:perm>
	<!--房态维护  -->
	<ebk:perm permissionId="15" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/ebooking/housestatus/applyHouseRoomStatus.do";
	 	</script>
	</ebk:perm>
	<ebk:perm permissionId="16" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/ebooking/routeStock/maintainRouteStockStatus.do";
	 	</script>
	</ebk:perm>
	<!--数据管理(报表)  -->
	<ebk:perm permissionId="18" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/report/product/onSaleProductList.do";
	 	</script>
	</ebk:perm>
	<ebk:perm permissionId="19" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/report/product/productSalesList.do";
	 	</script>
	</ebk:perm>
	<ebk:perm permissionId="20" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/report/product/productVisitorList.do";
	 	</script>
	</ebk:perm>
	<!--公告信息  -->
	<ebk:perm permissionId="11" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/announcement/ebkAnnouncementList.do";
	 	</script>
	</ebk:perm>
	<!--用户管理  -->
	<ebk:perm permissionId="1" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath}/ebk_user/index.do?valid=true";
	 	</script>
	</ebk:perm>
</body>
</html>