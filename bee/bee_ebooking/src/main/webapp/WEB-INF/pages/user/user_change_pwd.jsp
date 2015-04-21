<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<style type="text/css">
label.error {
  color: Red;
  font-size: 13px;
  margin-left: 0;
  padding-left: 0;
}
</style>
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script type="text/javascript" src="../js/base/jquery.validate.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#pwdForm").validate({
		rules: {    
			"oldPwd":{
				required:true                      
			},
			"newPwd1":{                         
				required: true                 
			},
			"newPwd2":{                         
				required: true                 
			}
		},
		messages: {    
			"oldPwd":{
				required:"请输入原密码"
			},
			"newPwd1":{                         
				required: "请输入新密码"                 
			},
			"newPwd2":{                         
				required: "请输入确认密码"                
			}
		}, 
		errorPlacement: function (error, element) { 
			$(".input_error").empty();
	        error.appendTo(element.parent());                 
	    } 
	});
});
$(function(){
	if('${password_short}'=='true'){
		$("#shortPasswordNotify").html("您的密码过于简单，请修改。");
	}
});
function saveHandler(){
	var oldPwdInput = $("#oldPwdInput").val();
	var newPwdInput1 = $("#newPwdInput1").val();
	var newPwdInput2 = $("#newPwdInput2").val();
	if(oldPwdInput==null||$.trim(oldPwdInput)==""){
		alert("请输入原密码，原密码不可为空");
		return false;
	}
	if(newPwdInput1==null||$.trim(newPwdInput1)==""){
		alert("请输入新密码，新密码不可为空");
		return false;

	}
	if(newPwdInput2==null||$.trim(newPwdInput2)==""){
		alert("请输入确认密码，确认密码不可为空");
		return false;
	}
	$.ajax({
		type:'POST',
		url:'${contextPath}/ebk_user/change_pwd.do',
		data:$("#pwdForm").serialize(),
		success:function(data){
			if(data.error){
				alert(data.errorMessage);
			}else if(data.success){
				window.location.href='${contextPath}/ebk_user/change_pwd_success.do';
			}
		}
	});
}
function cancelHandler(){
	$("#oldPwdInput").val("");
	$("#newPwdInput1").val("");
	$("#newPwdInput2").val("");
}
</script>
</head>
<body id="body_yhgl">
	<jsp:include page="../common/head.jsp"></jsp:include>
    <div class="snSpt_mainBox">
         <h4 class="snSpt_mainTit">修改密码</h4>
         <input id="password_short" value="${password_short}" type="hidden"/>
         <form id="pwdForm" action="change_pwd.do" method="post">
         <ul class="snspt_pwdlist">
            <li style="width:600px;">
            	<label>原密码</label>
            	<span><input id="oldPwdInput" name="oldPwd" type="password" value=""></span>
            	<span id="shortPasswordNotify" style="color:red;"></span>
            	<i class="input_error f_red snSpt_err">${oldPwdError }</i>
            </li>
            <li>
            	<label>新密码</label>
            	<span><input id="newPwdInput1" name="newPwd1" type="password" value=""></span>
            	<i class="f_red snSpt_err"></i>
            </li>
            <li>
            	<label>确认新密码</label>
            	<span><input id="newPwdInput2" name="newPwd2" type="password" value=""></span>
            	<i class="input_error f_red snSpt_err">${newPwdError }</i>
            </li>
            <li class="snspt_pwd_btnbox" style="margin-left:110px;">
	            <a class="snspt_Btn snspt_srBtn psw_sure" href="javascript:void(0)" style="margin-left: auto;"
	            	onclick="saveHandler();">确认</a> 
	            <a class="snspt_Btn snspt_srBtn psw_sure" href="javascript:void(0)" style="margin-left: auto;"
	            	onclick="cancelHandler()">清空</a>
            </li>
         </ul>
         </form>
    </div> 
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>