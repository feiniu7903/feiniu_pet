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
				<div class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>绑定手机</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>绑定手机后，可以用手机号码来登录。<@s.if test='!"F".equals(user.isMobileChecked)'>绑定手机并验证成功可获得<span class="lv-c1">300</span>积分。</@s.if></p>
							
							<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>绑定手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机绑定成功</li>
							    </ul>
							</div>
							
							<form id="myForm" action="/myspace/userinfo/phone_send.do" method="post">
								<div class="edit-inbox">
									<@s.if test='orderValidateMust==true&&firstOrder!=null'>
									<!--首笔订单验证-->
										<h4>订单信息验证</h4>
		                                <div class="gray-info">
	                              		  您在驴妈妈的第一笔订单：
					                	<a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
					                	<#list firstOrder.ordOrderItemProds as itemObj>
										    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
										</#list><br/>
					                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
		                                </div>
		                                &nbsp;&nbsp;<span class="tips-ico04 tips-show"></span><span class="xtext">请输入联系人手机号码，以便验证</span>
		                                <p><label>手机号码：</label><input type="text"  name="firstOrderCtMobile" id="firstOrderCtMobile" class="input-text input-phone"/></p>
		                                <div class="dot_line">间隔线</div>
		                            <!--/首笔订单验证-->
	                                </@s.if>
								
									<h4>手机验证</h4>
									<p><label>请输入您的手机号：</label><input type="text" id="sso_mobile" name="mobile" maxlength="13" value="<@s.property value="user.mobileNumber"/>" class="input-text input-phone" /></p>
									<p><label></label><a id="send-verifycode" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
									<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
									<p><label>请输入短信校验码：</label><input type="text" id="verifycodeInput" name="authenticationCode" value="" class="input-text i-checkcode" /></p>
									<p><a class="ui-btn ui-button" href="javascript:dosubmit();" id="submitBtnNew"><i>&nbsp;确 定&nbsp;</i></a></p>
									<div class="dot_line">间隔线</div>
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
    var phone = /^1[3|5|8][0-9]\d{8}$/;
    function verifycodeInput_Check(){
   		var mobileNumber=$.trim($("#sso_mobile").val().replace(/\s+/g,''));
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+mobileNumber+"&authenticationCode=" + $.trim($("#verifycodeInput").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				outOk($("#verifycodeInput"));
			}else{
				error_tip("#verifycodeInput","验证码输入错误");
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

	 $('#send-verifycode').click(function(){
	 	if(phone.test($('#sso_mobile').val().replace(/\s+/g,''))){
			if ($('#sso_mobile').val().replace(/\s+/g,'') == "<@s.property value="user.mobileNumber" />") {
				sendVerifyCode();
			} else {
				$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?mobile=" + $('#sso_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
					if (json.success == true) {
						sendVerifyCode();	
					} else {
						error_tip('#sso_mobile','该手机号已被注册，请更换其它手机号，或用此手机号登录');
					}			
				}); 
			}
		} else {
			error_tip('#sso_mobile','请输入有效的手机号');
		}
	 });


	function sendVerifyCode() {
		$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#sso_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
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
	}
	
	 function firstOrderCtMobile_callback(call){
    	var ctMb=$.trim($('#firstOrderCtMobile').val()).replace(/\s+/g,'');
   		if(ctMb == ""){
			error_tip("#firstOrderCtMobile","手机号码不能为空");
		}else if(phone.test(ctMb)){
			$.ajax({
			   type: "POST",
			   url: "/myspace/userinfo/validateFirstOrder.do",
			   data: "firstOrderCtMobile="+ctMb,
			   success: function(msg){
		   		if(msg.result==true){
		   			call();
			   	 }else{
			   	 	error_tip('#firstOrderCtMobile',msg.msg);
			   	 }
			   }
			});
		} else {
			error_tip('#firstOrderCtMobile','请输入有效的手机号');
		}
    }
    
	$(function(){
		$("#firstOrderCtMobile").ui("validate",{
            active : "请输入联系人手机号码",
            empty : "联系人手机号码不允许为空",
            express : [{
                text : "手机号码格式有误",
                func : function(){
                    return /^1[3|5|8][0-9]\d{8}$/.test(this.value.replace(/\s+/g,''));
                }
            }]
     	});
     	
     	$("#verifycodeInput").ui("validate",{
            active : "请输入短信校验码",
            empty : "校验码不能为空"
        });
     	
     	
     	 $("#verifycodeInput").blur( function () {
         	var mobileNumber=$.trim($("#sso_mobile").val().replace(/\s+/g,''));
         	var code=$.trim($("#verifycodeInput").val());
         	if(mobileNumber!=""&&code==""){
         		error_tip("#verifycodeInput","校验码不能为空");
         	}
         	if(mobileNumber!=""&&code!=""){
         		verifycodeInput_Check();
         	}
         });
         
         $('#verifycodeInput').bind('change', function() {
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
		cmCreatePageviewTag("绑定手机", "D1003", null, null);
	</script>
</html>