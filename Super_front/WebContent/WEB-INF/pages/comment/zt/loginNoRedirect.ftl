
<!--<link href="http://pic.lvmama.com/styles/zt/phb/phb_write_cmt.css?r=3332" rel="stylesheet" type="text/css" />-->

<!--判断是否登陆-->
<input type="hidden" name="userName" id="userName" value="<@s.if test="users!=null">${users.userName}</@s.if>" />

<div id="log_layer">
  			<dl class="c_Steps">
				<dt id="Steps_Title_4">用户登录</dt>
				<input type="hidden" id="targetUrl" value="../comment/writeComment/fillComment.do?placeId=${placeId}" />
				<dd id="Steps_Content_4">
					<p>您可以使用用户名/手机号/Email/会员卡登录</p>
					<ul>
						<form method="post" id="loginform" name="login_form">
							<li><label>用户名/手机号/Email/会员卡：</label></li>
							<li><input name="username"  class="inputtext"  value="" id="loginName"  type="text"></li>
							<li class="red" id="errorTipName">*请输入用户名、手机、Email、会员卡四者之一登录</li>
							<li><label>密码：</label></li>
							<li><input name="password" class="inputtext" id="password" type="password"></li>
							<li id="errorTipPwd" class="red">*请输入密码</li>
							<li><img src="http://pic.lvmama.com/img/zt/phb/c_login_submit.gif" id="loginBtn"><strong>还没注册？
							<a href="http://login.lvmama.com/nsso/register/registering.do" target="_brank" >点此注册</a></strong></li>
						</form>
				  </ul>
				</dd>
			</dl>
  			<a class="close" id="close"><img src="http://pic.lvmama.com/img/zt/phb/close.gif" height="15" width="15" /></a>
  </div>

<script type="text/javascript">
<!--弹出登陆层-->
function Login_scrollTop(){ 
			var loginTop=$(document).scrollTop()+200; 
			$("#log_layer").css({"top":loginTop}); 
			$("#log_layer").show();
			$(".close").click(function(){ 
				$("#log_layer,#c_overlay").hide(); 
			}); 
}

<!--登陆操作-->
$(function(){
	$('#loginBtn').click(function(){
		if ($('#loginName').val() == "" || $('#loginName').val() == "用户名/手机号/Email/会员卡") {
			alert('请输入用户名');
			return;
		}
		if ($('#password').val() == "") {
			alert('请输入密码');
			return;
		}
		$.getJSON("http://login.lvmama.com/nsso/ajax/login.do?mobileOrEMail=" + $('#loginName').val()  + "&password=" + $('#password').val() + "&jsoncallback=?" ,function (data){ 
			if (data.success) {
				<!--仅古镇点评专题使用-->
				alert("您已登陆成功,请重新提交.");
				document.getElementById("userName").value = $('#loginName').val();
				$("#log_layer,#c_overlay").hide(); 
			} else {
				alert("用户名密码出错，请重新登录");
			}
		}); 
	});
});
</script>

