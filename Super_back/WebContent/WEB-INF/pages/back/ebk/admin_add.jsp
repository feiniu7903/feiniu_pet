<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增管理员</title>
<link rel="stylesheet" type="text/css" href="../../themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="../../themes/ebk/ui-components.css" >
<script type="text/javascript" src="../../js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="../../js/base/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#addAdminForm").validate({
			rules: { 
				"admin.userName":{                         
					required: true,
					remote:{
				               type:"POST",
				               url:"is_exist_username.do",             
				               data:{
				                  userName:function(){return $("#userNameInput").val();}
				               } 
			              }                  
				}
			}, 
			messages: { 
				"admin.userName": {                         
					required: "请输入用户名",
					remote:"用户名已存在"
				}
				
			}, 
			errorPlacement: function (error, element) { 
		        error.appendTo(element.parent());                 
		    } 
		});
		
		$('#addAdminForm').ajaxForm({
			type: 'post',
			url:  'save_admin.do',
			beforeSubmit:function(){
				if($('#addAdminForm').valid()){
					$('#saveBtn').attr('disabled','disabled');
				}
				return true;
			},
			success: function(data) {
				if(data=="success"){
					alert("操作成功");
					parent.window.closePopWin("addAdminWin");
				}else{
					alert("操作失败");
					$('#saveBtn').removeAttr('disabled');
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
	<form id="addAdminForm" action="save_admin.do" method="post">
	<ul class="gl3_top" style="width: 400px;">
		<li>
			　　　供应商：${supName }
				<input id="supHd" type="hidden" name="admin.supplierId" value="${supId }">
		</li>
		<li>　　　用户名：<input id="userNameInput" type="text" name="admin.userName" ></li>
		<li>打印通关票据：<input id="canPrintShow" type="checkbox" name="canPrintShow">
			<input type="hidden" id="canPrint" name="admin.canPrint" value="false">
		</li>
	    <li>驴妈妈联系人：<input type="text" name="admin.lvmamaContactName"></li>
	    <li>　　联系电话：<input type="text" name="admin.lvmamaContactPhone"></li>
		<li>　　账号备注：<input type="text" name="admin.description"><span style="font-size: 12px;color: gray;">填写景区或者酒店名称</span></li> 
		<li>　　　　状态：
			<label><input type="radio" value="true" name="admin.valid" checked="checked"/>正常</label>
			<label><input type="radio" value="false" name="admin.valid"/>锁定</label>
		</li> 
	</ul>
	<div class="gl3_zs_b" align="center">
	    <input id="saveBtn" name="" value="保存" type="submit" >
	</div>
	</form>
</body>
</html>