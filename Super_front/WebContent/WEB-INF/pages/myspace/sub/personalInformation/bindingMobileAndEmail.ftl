<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>绑定手机-驴妈妈旅游网</title>
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
				<a class="current">绑定手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>绑定手机</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>绑定手机后，可以用手机号码来登录。<@s.if test='!"F".equals(user.isMobileChecked)'>绑定手机并验证成功可获得<span class="lv-c1">300</span>积分。</@s.if></p>
							
							<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证邮箱，绑定手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机绑定成功</li>
							    </ul>
							</div>
							
							<div class="edit-inbox">
								<form id="myForm" action="/myspace/userinfo/phone_send.do" method="post">
									<p><label>邮箱地址：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></span>
									&nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">邮箱地址已不再使用？</a></p>
									<input type="hidden" id="nowemail" name="nowemail" value="${user.email}"/>
									<p><label></label><a class="ui-btn ui-btn1 btn-disabled" id="send-verifycode" onclick="sendVerifycode()" style="display: inline-block;"><i>发送验证邮件</i></a>
						                        <span id="JS_sendEmail" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>已成功发送验证邮件。<!--a href="" target="_blank">登录邮箱</a--></span><span class="lh28">&#12288;&#12288;没收到邮件？<a style="cursor: pointer;" onclick="sendVerifycode()">再次发送</a></span></span>
						                        </p>
									<p><label>请输入验证码：</label><input type="text" length="6" id="sso_verifycode1" name="authenticationCode" value="" class="input-text i-checkcode" />
									<p><label>您的手机号：</label><input type="text"  id="sso_mobile" name="mobile" maxlength="13" value="<@s.property value="user.mobileNumber"/>" class="input-text input-phone" /></p>
									<p><label></label><a id="send-verifycode-old" class="ui-btn ui-btn2" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown-old" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
									<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
									<p><label>请输入短信校验码：</label><input type="text" length="6" id="sso_verifycode4" name="mobileAuthenticationCode" value="" class="input-text i-checkcode" /></p>
									<p><a id="submitBtnNew" class="ui-btn ui-button" href="javascript:dosubmit();"><i>&nbsp;下一步&nbsp;</i></a></p>
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
<script src='/js/myspace/mobileUtil.js' type='text/javascript'></script>
<script type="text/javascript">
 var phone = /^1[3|5|8][0-9]\d{8}$/;
    function sendVerifycode(){
    	$("#sso_email").change();
        if($("#sso_email").hasClass("input_border_red")){
            return;
        }
        $.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId=${user.id}&mobileOrEMail=<@s.property value="user.email" />&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",
	          function(json){
	              if (json.success) {
	                  $("#send-verifycode").hide();
	                  $("#JS_sendEmail").show();
	
	              } else {
	                  alert('验证码发送失败，请重新尝试');
	              }
	          }); 
    }

	 $('#send-verifycode-old').click(function(){
	 	if($(this).hasClass("ui-btn2")){
	 		return;
	 	}
	    $("#sso_mobile").change();
		if ($("#sso_mobile").hasClass("input_border_red")) {
			return;
		}
		var elt = this;
		$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $("#sso_mobile").val().replace(/\s+/g,'') +"&jsoncallback=?",function(json){
			if (json.success) {
				$("#span_tips").hide();
				$(elt).hide();
				$('#reSendAuthenticationCodeDiv').show();
				$('#sendAuthenticationCodeDiv').hide();	
				$("#JS_countdown-old").show();
				JS_countdown("#JS_countdown-old span.num-second");
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
	 
	 function sso_verifycode1_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?email="+$.trim($("#nowemail").val())+"&authenticationCode=" + $.trim($("#sso_verifycode1").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#send-verifycode-old").addClass("ui-btn1").removeClass("ui-btn2 btn-disabled");
				$("#submitBtn").addClass("ui-btn4").removeClass("ui-btn2 btn-disabled");
				$("input[disabled]").removeAttr("disabled").removeClass("input-disabled");
			}else{
				error_tip("#sso_verifycode1","邮箱校检码错误");
			}
		});
	}
	function sso_verifycode4_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$("#sso_mobile").val().replace(/\s/g,"")+"&authenticationCode=" + $.trim($("#sso_verifycode4").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
			}else{
				error_tip("#sso_verifycode4","绑定手机校检码错误");
			}
		});
	}
	
	function sso_mobile_callback(call){
		if($.trim($("#sso_mobile").val()) == ""){
			error_tip("#sso_mobile","手机号码不能为空");
		}else if(phone.test($('#sso_mobile').val().replace(/\s+/g,''))){
			if ($('#sso_mobile').val().replace(/\s+/g,'') != "<@s.property value="user.mobileNumber" />") {
				$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?mobile=" + $('#sso_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
						if (json.success == true) {
							call();
						} else {
							error_tip('#sso_mobile','该手机号已被注册，请更换其它手机号，或用此手机号登录');
						}	
				});	
			} else {
				call();
			}
		} else {
			error_tip('#sso_mobile','请输入有效的手机号');
		}
    }	
	
	$(function(){
     	$("#sso_verifycode1").ui("validate",{
            active : "请输入短信校验码",
            empty : "校验码不能为空"
        });
        
        $("#sso_verifycode1").blur( function () {
         	var code=$.trim($("#sso_verifycode1").val());
         	if(code==""){
         		error_tip("#sso_verifycode1","校验码不能为空");
         	}
         	if(code!=""){
         		sso_verifycode1_callback(function(){});
         	}
         });
         
         $('#sso_verifycode1').bind('change', function() {
			  $(this).trigger("blur");
		  });
	});
	
	
	function dosubmit(){
		var input = $("#myForm").find("input");
        input.each(function(){
            $(this).change();
            if($(this).hasClass("input_border_red")){
                return false;
            }
        });
        var error_input = input.filter(".input_border_red");
        if(error_input.size()>0){
          return;
        }
       
       $('#myForm').submit();	
	}
	

</script>
	<script>
		cmCreatePageviewTag("绑定手机和邮箱", "D1003", null, null);
	</script>
</body>
</html>