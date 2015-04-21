<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改管理员</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/ui-components.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#adminForm').ajaxForm({
		type: 'post',
		url:  'save_admin.do',
		success: function(data) {
			if(data=="success"){
				parent.window.closePopupWin('editWin');
			}else{
				alert("修改失败");
			}
		}
	});
	$('#canPrintShow').click(function(){
		if($(this).attr('checked')==false){
			$('#canPrint').val('false');
		}else{
			$('#canPrint').val('true');			
		}
	});
}); 
</script>
</head>
<body>
	<form id="adminForm" action="save_admin.do" method="post">
	<input type="hidden" name="admin.userId" value="${admin.userId}">
	<ul class="gl3_top" style="width: 400px;">
		<li>
			　　　供应商：${admin.supplierName }
		</li>
		<li>　　　用户名：${admin.userName }	</li>
		<li>打印通关票据：<input id="canPrintShow" type="checkbox" name="canPrintShow" ${admin.canPrint eq 'true'? 'checked="checked"':'' }>
			<input type="hidden" id="canPrint" name="admin.canPrint" value="${admin.canPrint eq 'true' }">
		</li>
	    <li>驴妈妈联系人：<input type="text" name="admin.lvmamaContactName" value="${admin.lvmamaContactName }"></li>
	    <li>　　联系电话：<input type="text" name="admin.lvmamaContactPhone" value="${admin.lvmamaContactPhone }"></li>
		<li>　　账号备注：<input type="text" name="admin.description" value="${admin.description }"></li> 
		<li>　　　　状态：
			<label>
			<input type="radio" value="true" name="admin.valid" 
				<s:if test="#request.admin.valid == \"true\"">checked="checked" </s:if>
			/>正常
			</label>　
			<label>
			<input type="radio" value="false" name="admin.valid"
				<s:if test="#request.admin.valid == \"false\"">checked="checked" </s:if>
			/>锁定
			</label>
		</li> 
	</ul>
	<div class="gl3_zs_b" align="center">
	    <input class="btn btn-small" name="" value="保存" type="submit">
	</div>
	</form>
</body>
</html>