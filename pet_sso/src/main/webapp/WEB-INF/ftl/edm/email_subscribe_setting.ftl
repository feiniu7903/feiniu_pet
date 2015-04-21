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
<script  type="text/javascript">
		var EMPTY_MSG="<font color=\"red\">*请输入邮箱,以便订阅邮件</font>";
		var INVALID_MSG="<font color=\"red\">请输入有效的邮箱地址，以便订阅邮件</font>";
		var SUBSCRIBE_MSG="<font color=\"red\">*该邮箱已订阅了邮件，请输入其它邮箱</font>";
	var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	function check_email(email){
		
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
		return true;	
	}
	function subscribe_mail(){
		var email = $(':text[name=email]').val();
		if(!(check_email(email))){
			return false;
		}
		//订阅
			var province = $("select[name='province']").val();
			var city	 = $("select[name='city']").val();
		var edmUserId = $('#edmUserId_id').val();
		//4.检查订阅类型是否已选择
		var type_array=new Array();
		$(":checkbox:checked[name='type']").each(function(){
			type_array.push($(this).val());
		});
		var oldEmail=$('#oldEmail_id').val();
		$.ajax({
				type: "POST",
				url: "/nsso/edm/updateSubscribeEmail.do",
				async: false,
				data: {id:edmUserId,email: email,oldEmail:oldEmail,province:province,city:city,type:type_array},
				dataType: "json",
				success: function(response) {
						if (response.success == false) {
								var msg=response.errorText;
								var msg=response.errorText;
								if(msg=='A'){
									alert(EMPTY_MSG);
								}else if(msg=='B'){
									alert(INVALID_MSG);
								}else if(msg=='D'){
									alert("您没有订阅邮件，请先订阅");
								}else if(msg=='E'){
									alert("您输入的邮箱已为订阅邮箱，请输入其它邮箱订阅");
								}else{
									alert(msg);
								}
								$('#error_email_id').html('为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址');
								$('#error_type_id').html('');
								return false;
						} else {
								$('#error_email_id').html('为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址');
								$('#error_type_id').html('');
								alert("修改订阅信息成功");
						}
				}
		});	
	}
</script>
<div>
	<div class="subscribe_title">
		<p>订阅设置</p>
	</div>
	<div class="subscribe_content">
		<div class="subscribe_form">
			<form method="post" id="subscribeFormId" name="subscribeForm">
				<input type="hidden" name="id" id="edmUserId_id" value="<@s.property value='subscribe.id'/>"/>
				<input type="hidden" name="oldEmail" id="oldEmail_id" value="<@s.property value='subscribe.email'/>"/>
				<input type="hidden" name="createDate" value="<@s.property value='subscribe.createDate'/>"/>
				<table>
					<tr>
						<th><b>*</b><label>我的邮箱:</label></th>
						<td>
							<p><input class="subscribe_text" style="height: 30px;" type="text" name="email" value="<@s.property value='subscribe.email'/>" <#if null!=userOldEmail>disabled=true</#if> /></p>
							<#if null==userOldEmail><p  id="error_email_id">为确保您能够正常收到邮件，请务必填写真实有效的邮箱地址</p></#if>
						</td>
					</tr>
					<#include "/WEB-INF/ftl/edm/province_city.ftl">
					<tr>
						<th><b>*</b><label>我要订阅：</label></th>
						<td>
							<p><input type="checkbox" name="type" value="MARKETING_EMAIL" <@s.iterator value="subscribe.infoList" id="stat"><@s.if test="type=='MARKETING_EMAIL'"> checked="checked" </@s.if></@s.iterator> /><span><strong>驴妈妈新品周刊</strong> —— 驴妈妈最新产品抢先推荐</span></p>
							<p><input type="checkbox" name="type" value="GROUP_PROCUREMENT_EMAIL" <@s.iterator value="subscribe.infoList" id="stat"><@s.if test="type=='GROUP_PROCUREMENT_EMAIL'"> checked="checked" </@s.if></@s.iterator>  /><span><strong>驴妈妈资讯周刊</strong> —— 海内外最新旅游资讯播报</span></p>
							<p><input type="checkbox" name="type" value="PRODUCT_EMAIL" <@s.iterator value="subscribe.infoList" id="stat"><@s.if test="type=='PRODUCT_EMAIL'"> checked="checked" </@s.if></@s.iterator>  /><span><strong>驴妈妈团购专刊</strong> —— 驴妈妈最新团购给力推荐</span></p>
							<p><input type="checkbox" name="type" value="REBATE_TICKET_EMAIL" <@s.iterator value="subscribe.infoList" id="stat"><@s.if test="type=='REBATE_TICKET_EMAIL'"> checked="checked" </@s.if></@s.iterator>  /><span><strong>驴妈妈打折门票专刊 </strong> ——驴妈妈最新优惠门票产品推荐</span></p>
							<p><input type="checkbox" name="type" value="SELF_HELP_EMAIL" <@s.iterator value="subscribe.infoList" id="stat"><@s.if test="type=='SELF_HELP_EMAIL'"> checked="checked" </@s.if></@s.iterator>  /><span><strong>驴妈妈自驾自助专刊</strong> —— 驴妈妈自助、自驾游线路推荐</span></p>
						</td>
					</tr>
					<tr>
						<th></th>
						<td class="subscribe_submit"><input class="subscribe_botton_submit subscribe_setting" type="button" name="submit" onClick="subscribe_mail()"/></td>
					</tr>
				</table>

			</form>
		</div>
		
	</div>
</div>