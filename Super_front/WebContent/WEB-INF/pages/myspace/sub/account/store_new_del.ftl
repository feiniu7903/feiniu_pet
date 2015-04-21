<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>提现-驴妈妈旅游网 </title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/store.do">存款账户</a>
				&gt;
				<a class="current">提现</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
			<script src="/js/myspace/account.js" type="text/javascript"></script>
			<div class="ui-box mod-edit store_del-edit">
				<div class="ui-box-title"><h3>提现</h3></div>
				<div class="ui-box-container">
					<@s.if test='null != errorMessage'>
						<div class="hr_a"></div>
						<p class="tips-error"><span class="tips-ico02"></span><@s.property value="errorMessage" /></p>
					</@s.if>
					<@s.if test='moneyAccount.valid=="Y"&&moneyAccount.maxDrawMoney>0'>
						   <form id="forms" action="/myspace/account/submitdraw.do" method=post>
					        <input type="hidden" id="bankName" name="bankName"/>
					   		<input type="hidden" name="payAmount" value="${moneyAccount.maxDrawMoney}" />
					        <div class="edit-box clearfix store_del-edit-box">
						        <div class="set-step set-step1 clearfix">
						        	<ul class="hor">
					    	            <li class="s-step1"><span class="s-num">1</span>验证信息</li>
					    	            <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>等待审核</li>
					                </ul>
						        </div>
					        	<div class="edit-inbox" id="form-box">
					        	
					                <@s.if test='firstOrder!=null'>
					                <!--首笔订单验证-->
					               	 	<h4>订单信息验证</h4>
						                <div class="gray-info">
						                	您在驴妈妈的第一笔订单：
						                	<a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
						                	<#list firstOrder.ordOrderItemProds as itemObj>
											    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
											</#list><br/>
						                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" /> &nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
						                </div>
						                 &nbsp;&nbsp;<span class="tips-ico04 tips-show"></span> <span class="xtext">请输入联系人手机号码，以便验证</span>
					                	<p><label>手机号码：</label><input type="text" name="firstOrderCtMobile" id="firstOrderCtMobile" class="input-text input-phone"></p>
					                	<div class="dot_line">间隔线</div>
					                <!--/首笔订单验证-->
					                </@s.if>
					                
						        	<@s.if test='user.mobileNumber!=null'>
						        	<!--手机验证-->
						        		<h4>手机验证</h4>
										<p><label>您的手机号：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" />&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">该手机号码已不再使用</a></p>
						        		<p>
						        			<a href="javascript:;" class="ui-btn ui-btn1" id="send-mobile-verifycode"><i>免费获取校验码</i></a>
							        		<span style="display:none" id="JS_countdown">
							        		<span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span>
							        		<span class="tips-winfo"><span class="ui-btn ui-disbtn"><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?&#12288;</span></span>
						        		</p>
						        		<p>
						        		<label>校验码：</label><input type="text" id="mobileVerifycodeInput" name="mobileVerifycode" class="input-text i-checkcode" >
						        		</p>
						        		<div class="dot_line">间隔线</div>
						        	<!--/手机验证-->
									</@s.if>
					                
					                <@s.if test='user.mobileNumber==null&&user.email!=null'>
					                <!--邮箱验证-->
						                <h4>邮箱验证</h4>
						                <p><label>邮箱地址：</label><span class="u-info"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></span>&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">该邮箱地址已不再使用</a></p>
						                <p><a href="javascript:;" class="ui-btn ui-btn1" id="send-email-verifycode"><i>获取邮箱验证码</i></a>
						                 <span id="JS_sendEmail" style="display:none"><span class="tips-success"><span class="tips-ico01"></span><span id="email-send-msg">已成功发送验证邮件。</span></span><a id="login_email_btn" class="ui-btn ui-btn4" style="margin-left:15px;" href="#" target="_blank"><i>&nbsp;登录邮箱&nbsp;</i></a><span class="lh28">&#12288;&#12288;没收到邮件？<a style="cursor: pointer;" id="reSend-email-verifycode">再次发送</a></span></span>
						                </p>
						                <p><label>验证码：</label><input type="text" class="input-text i-checkcode" id="emailVerifyCode" name="emailVerifycode"></p>
						                <div class="dot_line">间隔线</div>
						             <!--/邮箱验证-->
					                </@s.if>
					                
					                <!--提现信息-->
						                <h4>提现信息填写</h4>
								         <p><label><span>*</span>请选择银行：</label><select id="bank" name="bank" class="lv-select select-bank">
								            <option value="">--请选择--</option>
											<option value="2">中国工商银行</option>
											<option value="3">招商银行</option>
											<option value="4">中国建设银行</option>
											<option value="5">中国农业银行</option>
											<option value="6">交通银行</option>
											<option value="7">上海浦东发展银行</option>
											<option value="8">广东发展银行</option>
											<option value="9">中国光大银行</option>
											<option value="10">兴业银行</option>
											<option value="11">深圳发展银行</option>
											<option value="12">中国民生银行</option>
							            </select>
							            <span style="color:gray">暂不支持公司卡的提现申请</span>
							            </p>
						                <p><label><span>*</span>银行卡收款户名：</label><input type="text" class="input-text input-uname" id="accountName" name="accountName" ></p>
						                <p><label><span>*</span>银行卡号：</label><input type="text" class="input-text input-bankcard" id="account" name="account" ></p>
						                <p><label><span>*</span>提现金额：</label> <span class="lv-c1 f16 B">${moneyAccount.maxDrawMoneyYuan}</span> 元</p>
						                <div class="dot_line">间隔线</div>
					                <!--/提现信息-->
					                	<p><a class="ui-btn ui-btn4" href="javascript:;" onclick="submitForms();return false;"><i>&nbsp;&nbsp;确定&nbsp;&nbsp;</i></a></p>
					            </div>
					        </div>
					        </form>
					</@s.if>
				</div>
			</div>
			<!-- <<退款提现 -->
		</div>
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script>
var phone = /^1[3|5|8][0-9]\d{8}$/;
function verifycodeInput_Check(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile=${user.mobileNumber}&authenticationCode="+$.trim($("#mobileVerifycodeInput").val()) + "&jsoncallback=?",function(json){
		if (json.success == true) {
			outOk($("#mobileVerifycodeInput"));
		}else{
			error_tip("#mobileVerifycodeInput","验证码输入错误");
		}
	});
}
function firstOrderCtMobile_Check(){
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
		   	 	outOk($("#firstOrderCtMobile"));
		   	 }else{
		   	 	error_tip('#firstOrderCtMobile',msg.msg);
		   	 }
		   }
		});
	} else {
		error_tip('#firstOrderCtMobile','请输入有效的手机号');
	}
}
function emailVerifycode_Check(){
	var code=$.trim($('#emailVerifyCode').val());
	if(code == ""){
		error_tip("#emailVerifyCode","邮箱校检码不能为空");
	}else{
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?email=${user.email}&authenticationCode=" + $.trim($("#emailVerifyCode").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				outOk($("#emailVerifyCode"));
			}else{
				error_tip("#emailVerifyCode","邮箱校检码错误");
			}
		});
	}
}

$(function(){
	
	$("#firstOrderCtMobile").ui("validate",{
        active : "请输入联系人手机号码",
        empty : "联系人手机号码不为空",
        phone : "手机号码格式不正确"
    });
    
	$("#mobileVerifycodeInput").ui("validate",{
        active : "请输入短信校验码",
        empty : "校验码不能为空"
    });
				        
     $("#mobileVerifycodeInput").blur( function () {
     	var code=$.trim($("#mobileVerifycodeInput").val());
     	if(code!=""){
     		verifycodeInput_Check();
     	}
     });
     
     $("#firstOrderCtMobile").blur( function () {
     	 firstOrderCtMobile_Check();
     });
     
	$("#emailVerifyCode").ui("validate",{
        active : "请输入邮箱校验码",
        empty : "邮箱校验码不能为空"
    });
    
     $("#emailVerifyCode").blur( function () {
     	 emailVerifycode_Check();
     });
    
    $("#bank").ui("validate",{
        active : "请选择银行",
        empty : "请选择银行"
    });
	
	$("#accountName").ui("validate",{
        active : "请输入收款户名",
        empty : "收款户名不能为空",
        express : [{
            text : "请输入正确的收款户名",
            func : function(){
            	if(/^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,}$/.test(this.value)){
            		return true;
            	}else if(/^([a-zA-Z])*$/.test(this.value)){
            		return true;
            	}
                return false;
            }
       }]
    });
    
    
    $("#account").ui("validate",{
        active : "请输入银行卡号",
        empty : "银行卡号不能为空"
    });
		
});

$('#send-mobile-verifycode').click(function(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=${user.mobileNumber}&jsoncallback=?",function(json){
		if (json.success) {
			$('#send-mobile-verifycode').hide();
			$("#JS_countdown").show();
			JS_countdown("#JS_countdown span.num-second");
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });
function JS_countdown(_cdbox){
	 var _InterValObj; //timer变量，控制时间
	 var _count = 60; //间隔函数，1秒执行
	 var _curCount;//当前剩余秒数
	 sendMessage(_count);
	 function sendMessage(_count){
	  _curCount = _count;
	   $(_cdbox).html(_curCount);
	   _InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	  }
	 //timer处理函数
	 function SetRemainTime() {
	  if (_curCount == 0) {                
	   window.clearInterval(_InterValObj);//停止计时器
	   var expr = _cdbox.indexOf("-old")>0?"-old":"";
	   $("#JS_countdown"+expr).children(".tips-success").html("<span class=\"tips-ico01\"></span>校验码已发送成功，以最近发送的校验码为准").end().hide();
	   $("#send-mobile-verifycode"+expr).html("<i>重新发送验证码</i>").show();
	  }
	  else {
	   _curCount--;
	   $(_cdbox).html(_curCount);
	  }
	 }
	}

$('#send-email-verifycode').click(function(){
	var _this=$(this);
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			 $(_this).hide();
			 $("#login_email_btn").attr("href",json.result)
			 $("#JS_sendEmail").show();
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });
 $('#reSend-email-verifycode').click(function(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			$("#email-send-msg").html("成功重新发送验证到邮件");
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });


function submitForms(){

	var input = $("#form-box").find("input,select");
	
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

	var firstOrderCtMobile=$.trim($("#firstOrderCtMobile").val()).replace(/\s/g,"");
	$("#firstOrderCtMobile").val(firstOrderCtMobile);
	
	$("#bankName").val($("#bank").find("option:selected").text());

	$('#forms').submit();	
}
</script>
	<script>
		cmCreatePageviewTag("提现", "D1002", null, null);
	</script>
</body>
</html>