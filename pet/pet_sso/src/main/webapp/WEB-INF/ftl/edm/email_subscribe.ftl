<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>邮件订阅</title>
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/global.css">
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/layout.css">
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/subscribe.css">

	<link rel="shortcut icon" href="/img/favicon.ico" />
	<script src="http://pic.lvmama.com/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/top/topMsg_comment.js"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js"></script>

</head>
<script type="text/javascript">
$(function(){
	$(":checkbox[name='type']").focus(function(){
		$('#error_type_id').html('');
	});
});
	var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	function check_email(email){
		var EMPTY_MSG="<font color=\"red\">*请输入邮箱,以便订阅邮件</font>";
		var INVALID_MSG="<font color=\"red\">请输入有效的邮箱地址，以便订阅邮件</font>";
		var SUBSCRIBE_MSG="<font color=\"red\">*该邮箱已订阅了邮件，请进入已收邮件底部链接修改邮件<br/>如您已用该邮箱注册会员请<a href='http://login.lvmama.com/nsso/login?service=http%3A%2F%2Fwww.lvmama.com%2FmySpace.htm' target='_top'>[登录]</a>后台修改</font>";
		//1.检查邮箱地址是否已填写
		if(email==''||email==null){
			$('#error_email_id').html(EMPTY_MSG);
			return false;
		}
		//2.检查邮箱地址是否合法
		if(!(EMAIL_REGX.test(email))){
			$('#error_email_id').html(INVALID_MSG);
			return false;
		}
		//3.检查是否已订阅
		$.ajax({
				type: "POST",
				url: "/nsso/edm/checkEmailIsSubscribe.do",
				async: false,
				data: {email: email},
				dataType: "json",
				success: function(response) {
						if (response.success == false) {
								var msg=response.errorText;
								if(msg=='A'){
									$('#error_email_id').html(EMPTY_MSG);
								}else if(msg=='B'){
									$('#error_email_id').html(INVALID_MSG);
								}else if(msg=='C'){
									$('#error_email_id').html(SUBSCRIBE_MSG);
								}
								return false;
						} else {
								$('#error_email_id').html('为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址');
						}
				}
		});	
		return true;	
	}
	function subscribe_mail(){
		var email = $(":text[name='email']").val();
		var is_submit=true;
		if(!(check_email(email))){
			is_submit=false;
			return ;
		}
		var TYPE_MSG="<font color=\"red\">*请选择邮件类型，以便订阅邮件</font>";
		//4.检查订阅类型是否已选择
		var type_array=new Array();
		$(":checkbox:checked[name='type']").each(function(){
			type_array.push($(this).val());
		});
		if(type_array.length==0){
			$('#error_type_id').html(TYPE_MSG);
			is_submit=false;
			return ;
		}
		if(is_submit){
			//订阅
			var province = $("select[name='province']").val();
			var city	 = $("select[name='city']").val();
			$.ajax({
					type: "POST",
					url: "/nsso/edm/subscribeEmail.do",
					async: false,
					data: {email: email,province:province,city:city,type:type_array},
					dataType: "json",
					success: function(response) {
							if (response.success == false) {
									var msg=response.errorText;
									return ;
							} else {
								alert("订阅邮件成功");
									$('#error_email_id').html('为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址');
									$('#error_type_id').html('');
							}
					}
			});	
		}
	}
</script>
<body style="overflow: hidden">
<div id="content" style="align:center;">
	<div class="subscribe_title">
		<p>邮件订阅</p>
	</div>
	<div class="subscribe_content">
		<div class="subscribe_form">
			<form method="post" id="subscribeFormId" name="subscribeForm">
				<table>
					<tr>
						<th><b>*</b><label>我的邮箱:</label></th>
						<td>
							<p><input class="subscribe_text" style="height:30px;" type="text" name="email"  value="<@s.property value='email'/>"  <#if null!=userOldEmail>disabled=true</#if> /></p>
							<p id="error_email_id">为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址</p>
						</td>
					</tr>
					<#include "/WEB-INF/ftl/edm/province_city.ftl">
					<tr>
						<th><b>*</b><label>我要订阅：</label></th>
						<td>
							<p><input type="checkbox" name="type" value="MARKETING_EMAIL"  checked="checked"  /><span><strong>驴妈妈新品周刊</strong> —— 驴妈妈最新产品抢先推荐</span></p>
							<p><input type="checkbox" name="type" value="GROUP_PROCUREMENT_EMAIL"  checked="checked"   /><span><strong>驴妈妈资讯周刊</strong> —— 海内外最新旅游资讯播报</span></p>
							<p><input type="checkbox" name="type" value="PRODUCT_EMAIL"  /><span><strong>驴妈妈团购专刊</strong> —— 驴妈妈最新团购给力推荐</span></p>
							<p><input type="checkbox" name="type" value="REBATE_TICKET_EMAIL"  /><span><strong>驴妈妈打折门票专刊 </strong> ——驴妈妈最新优惠门票产品推荐</span></p>
							<p><input type="checkbox" name="type" value="SELF_HELP_EMAIL"  /><span><strong>驴妈妈自驾自助专刊</strong> —— 驴妈妈自助、自驾游线路推荐</span></p>
							<span id="error_type_id"></span>
						</td>
					</tr>
					<tr>
						<th></th>
						<td class="subscribe_submit"><input class="subscribe_botton_submit subscribe_email" type="button" name="submit" onClick="subscribe_mail()"/></td>
					</tr>
				</table>
			</form>
		</div>
		<#if null==userOldEmail>
		<div class="subscribe_note">
			<ul>
				<li>此服务可以随时取消</li>
				<li>请放心，我们和您一样讨厌垃圾邮件</li>
				<li>邮件订阅内容包括：实时可信的景点打折门票信息、精品团购等</li>
			</ul>
		</div>
		</#if>
	</div>
</div>

</body>
</html>