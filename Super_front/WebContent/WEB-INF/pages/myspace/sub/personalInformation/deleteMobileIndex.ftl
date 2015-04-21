<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>解绑手机-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">解绑手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>解绑手机</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>解绑手机后，手机号将不能用来登录。</p>

							<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机解绑成功</li>
							    </ul>
							</div>

							<div class="edit-inbox">
  									<form id="myForm" action="/myspace/userinfo/phone_delete.do" method="post">
							                <p><label>您的手机号：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>
							                &nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do">手机号已丢失或停用？</a></p>
							                <input id="old_mobile" name="old_mobile" value="${user.mobileNumber}" type="hidden" />
						                        <p><label></label><a id="send-verifycode" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取验证码</i></a>
						                        <span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
						                        <span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
						                        <p><label>请输入短信校验码：</label><input id="sso_verifycode3" name="authenticationCode" value="" class="input-text i-checkcode" type="text"></p>
                                                                        <p><a class="ui-btn ui-button" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
						   			</form>	
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript">

	function sso_verifycode3_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$.trim($('#old_mobile').val())+"&authenticationCode=" + $.trim($("#sso_verifycode3").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
			}else{
				error_tip("#sso_verifycode3","手机校检码错误");
			}
		});
	}

	 $('#send-verifycode').click(function(){
	 	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#old_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
			if (json.success) {
				$("#span_tips").hide();
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

	function validate_pass(){
		$("#myForm").submit();
	}
</script>
	<script>
		cmCreatePageviewTag("解绑手机", "D1003", null, null);
	</script>
</body>
</html>