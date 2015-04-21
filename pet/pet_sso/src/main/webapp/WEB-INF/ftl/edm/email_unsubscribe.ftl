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
	var EMPTY_MSG="您的邮箱没有订阅邮件";
		var INVALID_MSG="您的邮箱没有订阅邮件";
function un_subscribe(){
	var cancelRemark= new Array();
	$(':checkbox[name=cancelRemark]:checked').each(function(){
		cancelRemark.push($(this).val());
	});
	
	cancelRemark.sort();
	var otherCancelRemark =$(':text[name=otherCancelRemark]').val();
	if(otherCancelRemark=='请填写原因'){
		otherCancelRemark ='';
	}
	if(otherCancelRemark.length>100){
		alert("其它原因输入值过大");
		return;
	}
	var email = $(':hidden[name=email]').val();
	var type_array=new Array();
		$(":hidden[name=type]").each(function(){
			type_array.push($(this).val());
	});
	var subscribeId=$(':hidden[name=id]').val();
	$.ajax({
		type: "POST",
				url: "/nsso/edm/unSubscribeEmail.do",
				async: false,
				data: {id:subscribeId,email: email,type:type_array,cancelRemark:cancelRemark,otherCancelRemark:otherCancelRemark},
				dataType: "json",
				success: function(response) {
						if (response.success == false) {
								var msg=response.errorText;
								if(msg=='A'){
									alert(EMPTY_MSG);
								}else if(msg=='B'){
									alert(INVALID_MSG);
								}else if(msg=='D'){
									alert(EMPTY_MSG);
								}else{
									alert(msg);
								}
								return false;
						}else{
							alert("退订成功");
						}
				}
	});
}
</script>
<div>
<#if null==subscribe>
  <div class="subscribe_title">
		<p>邮件退订<span>
		您的邮箱  <@s.property value='email'/> 没有订阅邮件
		</span></p>
	</div>
<#else>
	<div class="subscribe_title">
		<p>邮件退订<span>您将退订
		<#list typeList as list1>
	     <#if list1=='MARKETING_EMAIL'> 驴妈妈资讯专刊 </#if>
		 <#if list1=='GROUP_PROCUREMENT_EMAIL'> 驴妈妈团购专刊 </#if>
		 <#if list1=='PRODUCT_EMAIL'> 驴妈妈产品周刊 </#if>
		 <#if list1=='REBATE_TICKET_EMAIL'> 驴妈妈打折门票专刊</#if>
		 <#if list1=='SELF_HELP_EMAIL'> 驴妈妈自驾自助专刊 </#if>
		</#list>
		</span></p>
	</div>
	<div class="subscribe_content">
		<div class="subscribe_form">
			<form method="post"  id="subscribeFormId" name="subscribeForm">
				<input type="hidden" name="id" id="subscribe_id" value="<@s.property value='subscribe.id'/>"/>
				<input type="hidden" name="email" value="<@s.property value='subscribe.email'/>"/>
				<#list type as list1>
			    	<input type="hidden" name="type" value="${list1}"/>
				</#list>
				<table class="unsubscribe_table">
					<tr><td>很抱歉打扰您了，能否告诉我们您退订的原因：</td></tr>
					<@s.iterator value="cancelMap" status="cancel">
						<tr><td><input type="checkbox" name="cancelRemark" value="<@s.property value='key'/>" /><span><@s.property value='value'/></span></td></tr>
					</@s.iterator>
					<tr><td><span>其他</span><input class="subscribe_text" style="height:30px;" type="text" name="otherCancelRemark" value="请填写原因" /></td></tr>
					<tr>
						<td class="subscribe_submit"><input class="subscribe_botton_submit unsubscribe_email" type="button" name="submit" onClick="un_subscribe()"/></td>
					</tr>
				</table>

			</form>
		</div>
	</div>
</div>
</#if>