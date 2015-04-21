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
	<!--未游玩订单  -->
	<ebk:perm permissionId="4" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/eplace/queryPassPort.do";
	 	</script>
	</ebk:perm>
	<!--统计订单  -->
	<ebk:perm permissionId="5" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/eplace/tongJi.do";
	 	</script>
	</ebk:perm>
	<!--全部订单  -->
	<ebk:perm permissionId="6" >
	 	<script type="text/javascript">
	 		window.location.href = "${contextPath }/eplace/allPassportList.do";
	 	</script>
	</ebk:perm>
</body>
</html>