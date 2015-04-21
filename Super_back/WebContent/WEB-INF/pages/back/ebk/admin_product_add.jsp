<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/ebk/admin_index.js" ></script>
<script type="text/javascript" src="../../js/base/log.js"></script>
<script type="text/javascript">
function saveAdminProductHandler(userId,metaBranchId){
	$.post("save_admin_product.do",{userId:userId,metaBranchId:metaBranchId},
			function(data){
				if("is_binded" == data){
					alert("无法重复绑定");
				}else if("success" == data){
					alert("操作成功");
				}else{
					alert("操作失败");
				}
	});
}
</script>
</head>
<body>
	<ul class="gl_top">
		<form id="searchForm" action="to_add_admin_product.do" method="post">
			<input type="hidden" name="userId" value="${userId }">
			<input type="hidden" name="supplierId" value="${supplierId }">
			<li>采购产品ID：
				<input type="text" class="u3" name="metaProductId" value="${metaProductId }" />
			</li>
			<li>采购产品名称：
				<input type="text" class="u3" name="metaProductName" value="${metaProductName}"/>
			</li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10"> 
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>采购产品</th>
	    <th>类别</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="ebkMetaBranchPage.items" var="item">
			<tr>
				<td>${item.metaProductName }(${item.metaProductId })</td>
				<td>${item.branchName }(${item.metaBranchId })</td>
				<td class="gl_cz" style="width: 30px;">
					<a href="javascript:void(0);" onclick="saveAdminProductHandler(${userId},${item.metaBranchId})">绑定</a>
				</td>
			</tr>
	  </s:iterator>
	   <tr>
		<td>总条数：<s:property value="ebkMetaBranchPage.totalResultSize" /></td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkMetaBranchPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>