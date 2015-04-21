<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改手机-驴妈妈旅游网</title>
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
				<a class="current">修改手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>修改手机</h3></div>
					<div class="ui-box-container">
    						<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>修改手机后，可以用新手机号码登录。原来的手机号将不能用来登录。</p>
							
						    <div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证原手机，输入新手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机修改成功</li>
							    </ul>
							</div>
							
							<form id="myForm" action="/myspace/userinfo/phone_send.do" method="post">
								<div class="edit-inbox">
			                        <p><label>您的手机号：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>
			                        &nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">原手机号已丢失或停用？</a></p>
			                        <input type="hidden" id="old_mobile" name="old_mobile" value="${user.mobileNumber}" />
			                        <p><label></label><a id="send-verifycode-old" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取验证码</i></a>
			                        <span id="JS_countdown-old" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
			                        <span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
			                        <p><label>短信校验码：</label><input id="sso_verifycode3" name="authOldMobileCode" class="input-text i-checkcode" type="text"></p>
									<p><label>新的手机号：</label><input type="text" id="sso_mobile" name="mobile" maxlength="13" value="<@s.property value="user.mobileNumber"/>" class="input-text input-phone" /></p>
									<p><label>重复输入新手机号码：</label><input type="text"  id="sso_mobile_new" name="repeatmobile" value="" class="input-text input-phone" /></p>
									<p><a class="ui-btn ui-btn4" href="javascript:dosubmit();" id="submitBtnNew"><i>&nbsp;确 定&nbsp;</i></a></p>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
	<script>
		cmCreatePageviewTag("绑定手机", "D1003", null, null);
	</script>
</body>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript">
	var phone = /^1[3|5|8][0-9]\d{8}$/;
	function sso_verifycode3_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$.trim($('#old_mobile').val())+"&authenticationCode=" + $.trim($("#sso_verifycode3").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#send-verifycode").addClass("ui-btn1").removeClass("ui-btn2 btn-disabled");
				$("#submitBtnNew").addClass("ui-button").removeClass("ui-btn2 btn-disabled");
				$("input[disabled]").removeAttr("disabled").removeClass("input-disabled");;
			}else{
				error_tip("#sso_verifycode3","验证码输入错误");
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
			}else{
				error_tip('#sso_mobile','该手机号已被注册，请更换其它手机号，或用此手机号登录');
			}
		}else{
			error_tip('#sso_mobile','请输入有效的手机号码');
		}
	}

	 $('#send-verifycode-old').click(function(){
	 	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#old_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
			if (json.success) {
				$("#span_tips").hide();
				$('#send-verifycode-old').hide();
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
	
	$(function(){
		$("#sso_mobile_new").val("");
		$("#sso_mobile_new").ui("validate",{
            active : "请重复输入新手机号码",
            empty : "新手机号码不允许为空",
            express : [{
                text : "两次输入的新手机号码不一致",
                func : function(){
                	var oldvalue=$('#sso_mobile').val().replace(/\s+/g,'');
                	var value=this.value.replace(/\s+/g,'');
                    return oldvalue==value;
                }
            },{
                text : "手机号码格式有误",
                func : function(){
                    return /^1[3|5|8][0-9]\d{8}$/.test(this.value.replace(/\s+/g,''));
                }
            }]
     	});
	});
</script>
</html>