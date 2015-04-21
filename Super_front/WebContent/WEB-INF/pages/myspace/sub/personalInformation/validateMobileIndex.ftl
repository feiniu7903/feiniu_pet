<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>验证手机-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
	<div id="wrap" class="ui-container lvmama-bg">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">验证手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>验证手机</h3></div>
					<div class="ui-box-container">
    						<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>
							验证手机后，可以及时收到驴妈妈的优惠产品信息。<@s.if test='!"F".equals(user.isMobileChecked)'>验证手机成功可获得<span class="lv-c1">300</span>积分。</@s.if>
							</p>
							<form id="myForm" action="/myspace/userinfo/phone_send.do" method="post">
								<div class="edit-inbox">
						            <p><label>您验证的手机号码：</label>
						            	<span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>
								<input type="hidden" name="mobile" value="<@s.property value="user.mobileNumber"/>" >
						            </p>
						            <p><label></label><a id="send-verifycode" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取校验码</i></a>
						            <span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
						            <span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
						            <p><label>请输入短信校验码：</label>
						            	<input id="sso_verifycode2" name="authenticationCode" type="text" class="input-text i-checkcode" value=""></p>
						            <p><a id="submitBtnValid" class="ui-btn ui-button"><i>&nbsp;确 定&nbsp;</i></a>&#12288;&#12288;&#12288;
						            	<a class="edit-cancel" href="">取消</a></p>
					            </div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
</body>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript">
	 var mobile="${user.mobileNumber}";
	 $('#send-verifycode').click(function(){
		$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + mobile + "&jsoncallback=?",
			function(json){
				if (json.success) {
					$("#span_tips").hide();
					$('#reSendAuthenticationCodeDiv').show();
					$('#sendAuthenticationCodeDiv').hide();	
					$('#send-verifycode').hide();
					$("#JS_countdown").show();
					JS_countdown("#JS_countdown span.num-second");
				} else {
					if(json.errorText == 'phoneWarning'){
						$("#span_tips").html("已超过每日发送上限，请于次日再试");
						$("#span_tips").show();
						$("#send-verifycode").unbind();  
					}else if(json.errorText == 'ipLimit'){
						$("#span_tips").html("当前IP发送频率过快，请稍后重试");
						$("#span_tips").show();
					}else if(json.errorText == 'waiting'){
						$("#span_tips").html("发送频率过快，请稍后重试");
						$("#span_tips").show();
					}else{
						$("#span_tips").html(json.errorText);
						$("#span_tips").show();
					}
				}
			});	
	 });
	$('#submitBtnValid').click(function(){
		$('#mobileId').val(mobile);
		$('#myForm').submit();	
	});
	
</script>
	<script>
		cmCreatePageviewTag("绑定手机-发送验证短信", "D1003", null, null);
	</script>
</html>