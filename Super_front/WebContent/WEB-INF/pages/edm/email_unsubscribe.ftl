<!doctype>
<html>
<head>
<meta charset="utf-8" />
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
<link href="http://pic.lvmama.com/styles/new_v/header-air.css"
	type="text/css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_main/main.css"
	type="text/css" rel="stylesheet" />
<link
	href="http://pic.lvmama.com/styles/new_v/ui_plugin/lmmcomplete.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/new_v/ob_main/search_compl.css"
	type="text/css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_yjdy/yjdy.css"
	rel="stylesheet" type="text/css">
<title>驴妈妈订阅邮件退订页面</title>
<script type="text/javascript">
	var EMPTY_MSG = "您的邮箱没有订阅邮件";
	var INVALID_MSG = "您的邮箱没有订阅邮件";
	function un_subscribe() {
		var cancelRemark = new Array();
		$(':checkbox[name=cancelRemark]:checked').each(function() {
			cancelRemark.push($(this).val());
		});

		cancelRemark.sort();
		var otherCancelRemark = $('[name=otherCancelRemark]').val();
		if (otherCancelRemark == '请填写原因') {
			otherCancelRemark = '';
		}
		if (otherCancelRemark.length > 100) {
			alert("其它原因输入值过大");
			return;
		}
		var email = $(':hidden[name=email]').val();
		var regEdmType = $(':hidden[name=regEdmType]').val();
		var type_array = new Array();
		$(":hidden[name=type]").each(function() {
			type_array.push($(this).val());
		});
		
		var subscribe ="({";
	 		subscribe+='\"email\":\"'+ email+'\",';
	 		subscribe+='\"regEdmType\":\"'+ regEdmType+'\",';
	 		subscribe+='\"type\":\"'+ regEdmType+'\",';
	 		subscribe+='\"cancelRemark\":\"'+  cancelRemark+'\",';
	 		subscribe+='\"otherCancelRemark\":\"'+  otherCancelRemark+'\"';
	 		subscribe +="})";
	 		subscribe = eval(subscribe);
		$.ajax({
			type : "POST",
			url : "/edm/unSubscribeEmail.do",
			async : false,
			data : subscribe,
			dataType : "json",
			success : function(response) {
				if (response.success == false) {
					var msg = response.errorText;
					if (msg == 'A') {
						alert(EMPTY_MSG);
					} else if (msg == 'B') {
						alert(INVALID_MSG);
					} else if (msg == 'D') {
						alert(EMPTY_MSG);
					} else {
						alert(msg);
					}
					return false;
				} else {
					alert("退订成功");
				}
			}
		});
	}
</script>
</head>

<body>
	<#include "/common/header.ftl"/>
	<div  class="subscribe_content" >
	<table width="960" style="font-family: '宋体';margin: 0 auto;" border="0"
		cellspacing="0" cellpadding="0">
		<tr>
			<td style="border-bottom: #ff4499 solid 2px; padding: 10px 0; font-weight: bold; font-size: 16px; line-height: 20px;">
				<#if null==email> 请提供退订邮箱 <#elseif null==regEdmType> 您的邮箱
				<@s.property value='email'/> 没有订阅邮件 <#else> 您将要退订的邮件为： 
				<#if regEdmType=='PRODUCT_EMAIL'>当季热卖 </#if> 
				<#if regEdmType=='MARKETING_EMAIL'>旅游资讯 </#if> 
				<#if regEdmType=='GROUP_PROCUREMENT_EMAIL'>超值团购 </#if> 
				<#if regEdmType=='REBATE_TICKET_EMAIL'>驴妈妈打折门票专刊</#if> 
				<#if regEdmType=='SELF_HELP_EMAIL'>促销活动</#if> </#if></td>
		</tr>
		<tr>
			<td><img src="http://pic.lvmama.com/opi/banner.gif" width="580" height="144" alt="" /></td>
		</tr>
		<#if null!=email && null!=regEdmType>
		<tr>
			<td>
				<div class="subscribe_form">
					<form method="post" id="subscribeFormId" name="subscribeForm">
						<input type="hidden" name="email"
							value="<@s.property value='email'/>" /> <input type="hidden"
							name="subscribe.email" value="<@s.property value='email'/>" /> 
							<input type="hidden" name="regEdmType" value="${regEdmType}" />
							<input type="hidden" name="type" value="${regEdmType}" />
						<table>
							<tr>
								<td>
									<table width="500" style="font-size: 12px;" align="center"
										border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th style="padding: 10px 0 5px; font-size: 14px;"
												align="left">请选择您退订的原因：</th>
										</tr>
										<@s.iterator value="cancelMap" status="cancel">
										<tr>
											<td style="padding: 2px 0;"><label><input
													type="checkbox" name="cancelRemark"
													value="<@s.property value='key'/>" /><@s.property
													value='value'/></label></td>
										</tr>
										</@s.iterator>
									</table>

								</td>
							</tr>
							<tr>
								<td>
									<table width="500" style="font-size: 12px;" align="center"
										border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th style="padding: 15px 0 8px; font-size: 14px;"
												align="left">其他原因：</th>
										</tr>
										<tr>
											<td><textarea
													style="width: 480px; font-family: '宋体'; height: 82px; padding: 3px; margin: 0; font-size: 12px;"
													name="otherCancelRemark" cols="" rows=""></textarea></td>
										</tr>
										<tr>
											<td style="padding-top: 15px;">
											<a  href="javascript:un_subscribe();"><img
													style="vertical-align: middle; border: none;"
													src="http://pic.lvmama.com/opi/tuiding.gif" width="90" height="32" alt="" /></a><span
												style="margin: 0 10px; color: #888;">或</span><a
												target="_blank"
												style="color: #0077cc; text-decoration: none;"
												href="http://www.lvmama.com/edm/showUpdateSubscribeEmail.do?email=<@s.property value=" email" />">修改订阅的邮件类型&gt;&gt;</a></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
		</#if>
	</table>
	</div>
	<#include "/common/footer.ftl"/>
</body>
</html>
