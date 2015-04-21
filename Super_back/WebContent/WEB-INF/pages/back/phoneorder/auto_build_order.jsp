<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }themes/cc.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath}js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord_div.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
	</head>
	<body>
		<h3>自动生成订单数据</h3>
		<form action="<%=basePath%>phoneOrder/saveAutoBuilder.do" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="p_table form-inline">
			<tr>
				<td>类别ID</td><td><input type="text" name="prodBranchId"/></td>
			</tr>
			<tr>
				<td>联系人</td><td><input type="text" name="contactName"/></td>
			</tr>
			<tr>
				<td>联系人手机</td><td><input type="text" name="contactMobile" value="13800138000"/></td>
			</tr>
			<tr>
				<td>生成订单数</td><td><input type="text" name="count"/></td>
			</tr>
		</table>
		<input type="submit" value="生成"/>
		</form>
		<table>
			<tr>
			<td colspan="2"><input type="button" value="导入excel并废单" onclick="doImportExcel()"
				class="right-button08 btn btn-small" /></td>
			<td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	function doImportExcel(){
		var url = "${basePath}/distribution/batch/upload.do";
		$("<iframe frameborder='0' id='upload'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "导入废单excel",
	        position: 'top',
	        width: 370
		}).width(350).height(250).attr("src",url);
	}
	</script>
</html>
