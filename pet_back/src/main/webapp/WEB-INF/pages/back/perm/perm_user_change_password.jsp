<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改密码</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
	<script type="text/javascript">
		function saveHandler(){
			var msg = "";
			if($.trim($("#oldPasswordInput").val()) == ""){
				msg = msg + "请输入原密码";
			}else if($.trim($("#newPsswordInput1").val()) == ""){
				msg = msg + "请输入新密码";
			}else if($.trim($("#newPsswordInput2").val()) == ""){
				msg = msg + "请输入确认新密码";
			}else if($.trim($("#newPsswordInput1").val()) != $.trim($("#newPsswordInput2").val())){
				msg = msg + "新密码填写不一致";
			}
			if(msg.length > 0){
				alert(msg);
				return;
			}else{
				$.post("save_new_password.do",
						{
							oldPassword:$.trim($("#oldPasswordInput").val()),
							newPassword:$.trim($("#newPsswordInput1").val())
						},
						function(result){
							if(result == "1"){
								alert("原密码错误！");
								return;
							}else{
								window.location.href = window.location.href;
							}
						}
				);
			}
		}
	</script>
</head>
<body>
	<form id="pwdForm" action="save_new_password.do" method="post">
	<ul class="gl3_top" style="width: 350px">
		<li>
			原密码：<input id="oldPasswordInput" type="password" name="oldPassword">
		</li>
		<li>
			新密码：<input id="newPsswordInput1" type="password" name="newPassword1">
		</li>
		<li>
			确认新密码：<input id="newPsswordInput2" type="password" name="newPassword2">
		</li>
		<li>
			<span><input name="" value="保存" type="button" onclick="saveHandler()"></span>
		</li>
	</ul>
	</form>
</body>
</html>