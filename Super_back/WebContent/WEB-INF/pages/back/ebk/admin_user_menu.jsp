<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户菜单权限</title>
<link rel="stylesheet" type="text/css" href="${basePath}themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath}themes/base/ztree/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${basePath}themes/ebk/admin.css" >
<script type="text/javascript" src="${basePath}js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var setting = {check : {enable : true},
				data : {
					simpleData : {
						enable : true
					}
				},
				
			};
		$.fn.zTree.init($("#menuTree"), setting, eval('${treeData}'));
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
		var nodes = treeObj.getNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			treeObj.setChkDisabled(nodes[i], true);
		}
	});
</script>
</head>
<body>
	<div>
		<ul id="menuTree" class="ztree"></ul>
	</div>
</body>
</html>