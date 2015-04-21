<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户产品权限</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
}); 
function addUserProductHandler(userId,supplierId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增绑定产品",
        position: 'center',
        width: 620, 
        height: 480,
        close: function(event,ui){
        	refreshPage();
        }
	}).width(600).height(450).attr("src","to_add_admin_product.do?userId="+userId+"&supplierId="+supplierId);
}
function deleteUserMetaHandler(userId,metaBranchId,userMetaId){
	$.post("delete_user_meta_branch.do",{userId:userId,metaBranchId:metaBranchId,userMetaId:userMetaId},
			function(data){
				if("success" != data){
					alert("操作失败");
				}else{
					alert("操作成功");
					refreshPage();
				}
			}		
	);
}
function refreshPage(){
	window.location.href = "to_admin_product.do?userId="+$("#userIdHd").val() + "&supplierId="+$("#supplierIdHd").val()+"&bizType="+$("#bizTypeHd").val();
}
</script>
</head>
<body>
	<input type="hidden" id="userIdHd" value="${userId}">
	<input type="hidden" id="supplierIdHd" value="${supplierId}">
	<input type="hidden" id="bizTypeHd" value="${bizType}">
	<input type="button" value="新增绑定产品" onclick="addUserProductHandler(${userId},${supplierId})" />
	<br />
	已绑定产品：
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>采购产品ID</th>
	    <th>采购产品名称</th>
	    <th>类别</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="ebkUserMetaBranchPage.items" var="item">
			<tr>
				<td>${item.ebkMetaBranch.metaProductId }</td>
				<td>${item.ebkMetaBranch.metaProductName }</td>
				<td>${item.ebkMetaBranch.branchName }(${item.ebkMetaBranch.metaBranchId })</td>
				<td class="gl_cz" style="width: 30px;">
					<a href="javascript:void(0);" onclick="deleteUserMetaHandler(${item.userId},${item.metaBranchId},${item.userMetaId})">删除</a>
				</td>
			</tr>
	  </s:iterator>
	   <tr>
		<td>总条数：<s:property value="ebkUserMetaBranchPage.totalResultSize" /></td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkUserMetaBranchPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>