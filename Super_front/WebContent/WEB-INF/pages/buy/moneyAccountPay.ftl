<div class="cardsDeposit" id="moneyAccountPayBox">
  	<h3 class="deposith3Close <@s.if test="moneyAccount.maxPayMoney>0">h3CloseB</@s.if>" data-biaoshi="cardsDeposit"><em></em>存款账户支付</h3>
	<ul class="DepositTop" <@s.if test="moneyAccount.maxPayMoney==0">style="display: none;"</@s.if><@s.else>style="display: block;"</@s.else> id="cashAccountDetail">
  		<!-- 若账户余额为0时，显示以下代码-->
  		<@s.if test='moneyAccount.valid=="Y"'>
			<!-- 账户余额支付信息-->
			<@s.if test="moneyAccount.maxPayMoney>0">
		    	<li>
		    		您的存款账户可支付余额：<b><em>&yen; ${moneyAccount.maxPayMoneyYuan}</em></b>，
		    		用存款账户余额付款
	    			<@s.if test="moneyAccount.maxPayMoney>=order.actualPayFloat">
			    		<@s.if test="moneyAccount.maxPayMoneyYuan>=order.oughtPayYuanFloat">
			    			<em>&yen;${order.oughtPayYuanFloat}</em>
			    		</@s.if>
			    		<@s.else>
			    			<em>&yen;${moneyAccount.maxPayMoneyYuan}</em>，剩余金额<em>&yen;${order.oughtPayYuanFloat - moneyAccount.maxPayMoneyYuan}</em>可选择“在线支付”付款。
			    		</@s.else>
			    	</@s.if>
		    	</li>
			</@s.if>
			<!-- /账户余额支付信息-->
			<@s.else>
				<!--提示充值-->
				<dl class="count-pay">
			  		<dt>您的账户余额为<em>&yen;0</em>，<a target="_blank" href="/myspace/account/store.do">账户充值</a></dt>
			  		<dt>若订单金额超过网上银行最大单笔支付限额时，您可以为驴妈妈存款账户充值后一同完成支付。</dt>
				</dl>
				<!--/提示充值-->
			</@s.else>
			<input id="payBaseUrl" name="payBaseUrl" value="${constant.paymentUrl}" type="hidden"/>
    	    <input id="IsParentPage" name="IsParentPage" value="Y" type="hidden"/>
    	    <input id="orderId" name="orderId" value="${order.orderId}" type="hidden"/>
    	    <input id="oughtPay" name="oughtPay" value="${order.oughtPay}" type="hidden"/>
    	    <input id="userId" name="userId" value="${order.userId}" type="hidden"/>
    	    <input id="paymentParamsCa" name="paymentParams" type="hidden" value="${paymentParamsCashAccount}">
			 <@s.if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
		 		<li id="usermessage">
                    <p><label>您的手机号：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></p>
                    <p><label></label><a href="javascript:;" class="ui-btn ui-btn1" id="send-verifycode"><i>免费获取校验码</i></a><span style="display:none" id="JS_countdown"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span href="" class="ui-btn ui-disbtn"><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?&#12288;</span></span>
					<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
                    <p><label>请输入短信校验码：</label><input type="text" style="width:60px;" class="input-text i-checkcode" value="" id="cashAccountVerifyCode"></p>
        		 	<p><label></label><a class="ui-btn ui-btn4"  id="moneyAccountPayButton" data-biaoshi="settingSuccessW" ><i>&nbsp;确认支付&nbsp;</i></a></p>
			 	 </li>
			 </@s.if>
			 <@s.elseif test='user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==true'>
		 		<li id="usermessage">
                    <h4>邮箱验证</h4>
	                <p><label>邮箱地址：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></p>
	                <p><label></label><a href="javascript:;" class="ui-btn ui-btn1" id="send-email-verifycode"><i>获取邮箱验证码</i></a>
	                <span id="JS_sendEmail" style="display:none"><span class="tips-success"><span class="tips-ico01"></span><span id="email-send-msg">已成功发送验证邮件。</span></span><span class="lh28">&#12288;&#12288;没收到邮件？<a style="cursor: pointer;" id="reSend-email-verifycode">再次发送</a></span></span>
	                </p>
					<p><label>验证码：</label><input type="text" class="input-text i-checkcode" id="emailVerifyCode" name="emailVerifycode"></p>
                    <div class="dot_line">间隔线</div>
                    <h4>订单信息验证</h4>
                    <div class="gray-info">您在驴妈妈的第一笔订单:
	                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
	                	<#list firstOrder.ordOrderItemProds as itemObj>
						    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
						</#list><br/>
	                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
                    </div>
                    <div class="xtext">&nbsp;&nbsp;<span class="tips-ico04 tips-show"></span>请输入联系人手机号码，以便验证</div>
                    <p><label>手机号码：</label><input type="text" name="firstOrderCtMobile" id="firstOrderCtMobile" class="input-text input-phone"></p>
                    <p><label></label><a class="ui-btn ui-btn4"  id="moneyAccountPayButton" data-biaoshi="settingSuccessW" ><i>&nbsp;确认支付&nbsp;</i></a></p>
        		 </li>
			 </@s.elseif>
			 <@s.elseif test='user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==false'>
		 		<li id="usermessage">
                   <h4>订单信息验证</h4>
                    <div class="gray-info">您在驴妈妈的第一笔订单:
	                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
	                	<#list firstOrder.ordOrderItemProds as itemObj>
						    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
						</#list><br/>
	                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
                    </div>
                    <div class="xtext">&nbsp;&nbsp;<span class="tips-ico04 tips-show"></span>请输入联系人手机号码，以便验证</div>
                    <p><label>手机号码：</label><input type="text" name="firstOrderCtMobile" id="firstOrderCtMobile" class="input-text input-phone"></p>
                    <p><label></label><a class="ui-btn ui-btn4"  id="moneyAccountPayButton" data-biaoshi="settingSuccessW" ><i>&nbsp;确认支付&nbsp;</i></a></p>
        		 </li>
			 </@s.elseif>
			 <@s.elseif test='null!=firstOrder'>
		 		<li id="usermessage">
                   <h4>订单信息验证</h4>
                    <div class="gray-info">您在驴妈妈的第一笔订单:
	                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
	                	<#list firstOrder.ordOrderItemProds as itemObj>
						    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
						</#list><br/>
	                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
                    </div>
                    <div class="xtext">&nbsp;&nbsp;<span class="tips-ico04 tips-show"></span>请输入联系人手机号码，以便验证</div>
                    <p><label>手机号码：</label><input type="text" name="firstOrderCtMobile" id="firstOrderCtMobile" class="input-text input-phone"></p>
                    <p><label></label><a class="ui-btn ui-btn4"  id="moneyAccountPayButton" data-biaoshi="settingSuccessW" ><i>&nbsp;确认支付&nbsp;</i></a></p>
        		 </li>
			 </@s.elseif>
			 <@s.else>
			 	亲爱的用户，为了保障账户的安全，请先绑定你的手机，再进行支付！<span  data-biaoshi="msgDiv"><a href="/myspace/userinfo/phone.do" target="_blank">立即绑定</a></span>。绑定成功后，请<a href="javascript:window.location.reload();">刷新</a>当前页面！
			 </@s.else>
			 <p id="paymentPassword_inputTip"></p>
		</@s.if>
		<@s.else> 
			<font color="#FF0000">亲爱的用户，系统认为您的帐户存在风险，为了您的账户安全，系统已将您的账户冻结，请立即致电客服10106060。</font>
		</@s.else>
	 </ul>
</div><!--cardsDeposit end-->
<div class="settingSuccess settingSuccessW" style="margin-left:0px" id="cashPayMsgBox" >
	<h3><s class="close" data-biaoshi="settingSuccess"></s>存款账户支付</h3>
    <p class="textPrompt"><span><s class="icon4"></s>存款账户余额付款成功！</span>
    <@s.if test="moneyAccount.maxPayMoney < order.actualPayFloat">
    <br /><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong></p>
    </@s.if>
    <p class="ClickButton"><input type="button" id="payOKButton"  data-biaoshi="settingSuccess" value="确认" /></p>
</div><!--settingSuccess end-->
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css" rel="stylesheet" />
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script src="/js/myspace/account.js" type="text/javascript"></script>
<script>
var phone = /^1[3|5|8][0-9]\d{8}$/;
function verifycodeInput_Check(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile=${user.mobileNumber}&authenticationCode=" + $.trim($("#cashAccountVerifyCode").val()) + "&jsoncallback=?",function(json){
		if (json.success == true) {
			outOk($("#cashAccountVerifyCode"));
		}else{
			error_tip("#cashAccountVerifyCode","验证码输入错误");
		}
	});
}
function firstOrderCtMobile_Check(){
	var ctMb=$.trim($('#firstOrderCtMobile').val()).replace(/\s+/g,'');
	if(ctMb == ""){
		error_tip("#firstOrderCtMobile","手机号码不能为空");
	}else if(phone.test(ctMb)){
		$.getJSON("/myspace/userinfo/validateFirstOrder.do?firstOrderCtMobile="+ctMb, function(json){
		  	  if(json.result==true){
		   	 	outOk($("#firstOrderCtMobile"));
		   	 }else{
		   	 	error_tip('#firstOrderCtMobile',json.msg);
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

	$("#cashAccountVerifyCode").ui("validate",{
        active : "请输入短信校验码",
        empty : "校验码不能为空"
    });
    
    $("#cashAccountVerifyCode").bind('change', function() {
     	var code=$.trim($("#cashAccountVerifyCode").val());
     	if(code==""){
     		error_tip("#cashAccountVerifyCode","校验码不能为空");
     	}
     	if(code!=""){
     		verifycodeInput_Check();
     	}
    });
    
    $("#emailVerifyCode").ui("validate",{
        active : "请输入邮箱校验码",
        empty : "邮箱校验码不能为空"
    });
    
    $('#emailVerifyCode').bind('change', function() {
    	emailVerifycode_Check();
	});
    
    $("#firstOrderCtMobile").ui("validate",{
        active : "请输入联系人手机号码",
        empty : "联系人手机号码不为空",
        phone : "手机号码格式不正确"
    });
    
    $("#firstOrderCtMobile").bind('change', function() {
    	 firstOrderCtMobile_Check();
    });
    
});

<@s.if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
	$('#send-verifycode').click(function(){
		$.getJSON("http://login.lvmama.com/nsso/ajax/cashAccountPayAuthenticationCode.do?mobileOrEMail=${user.mobileNumber}&jsoncallback=?",function(json){
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
	   $("#send-verifycode"+expr).html("<i>重新发送验证码</i>").show();
	  }
	  else {
	   _curCount--;
	   $(_cdbox).html(_curCount);
	  }
	 }
	};
</@s.if>
<@s.elseif test='user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==true'>
$('#send-email-verifycode').click(function(){
	var _this=$(this);
	$.getJSON("http://login.lvmama.com/nsso/ajax/cashAccountPayAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			 $(_this).hide();
			 $("#JS_sendEmail").show();
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });
 $('#reSend-email-verifycode').click(function(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/cashAccountPayAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			$("#email-send-msg").html("成功重新发送验证到邮件");
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });
</@s.elseif>

function doCashPay(pay_url){
	var input = $("#moneyAccountPayBox").find("input");
    input.each(function(){
        $(this).trigger("change");
        if($(this).hasClass("input_border_red")){
            return false;
        }
    });
    var error_input = input.filter(".input_border_red");
    if(error_input.size()>0){
      return;
    }
    
    var int=setInterval(function(){
  		input.each(function(){
  			$(this).attr("disabled",true);
			$(this).nextAll("span").remove();
			outOk($(this));
		});
		$('#paymentPassword_inputTip').nextAll("span").remove();
	},100);
    
    $.ajax({
	  url: pay_url,
	  cache: false,
	  dataType:"json",
	  success: function(data){
	    if(data.success){
			showMsgBox();
		}else{
			clearInterval(int)
			input.attr("disabled",false);
			$('#paymentPassword_inputTip').html("<s class='icon3'></s>"+data.msg);
			$('#paymentPassword_inputTip').show();
		}
	  }
	});
    
}

function showMsgBox(){
	boxToCenter($('#cashPayMsgBox'));
	var pt=windowsSize();
	$('.bgDiv').width(pt.w+"px").height(pt.h+"px");
	$('#cashPayMsgBox').fadeIn("normal");
	$('.bgDiv').show();
}

function windowsSize(){
    var pt = {w:0,h:0}; 
    if (window.innerHeight && window.scrollMaxY){ 
      pt.w = document.body.scrollWidth;
      pt.h = window.innerHeight + window.scrollMaxY;
    }
    else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
      pt.w = document.body.scrollWidth;
      pt.h = document.body.scrollHeight;
    }
    else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
      pt.w = document.body.offsetWidth;
      pt.h = document.body.offsetHeight;
    }
    return pt;
}

function boxToCenter(obj){
    var windowWidth = document.documentElement.clientWidth;  
    var windowHeight = document.documentElement.clientHeight;  
    var popupHeight =$(obj).height();  
    var popupWidth =$(obj).width();   
   $(obj).css({
    "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),  
    "left": (windowWidth-popupWidth)/2  
   }); 
}

$(document).ready(function() {
			
		$(window).scroll(function(){
  			boxToCenter($('#cashPayMsgBox'));
 		});
 		$(window).resize(function(){
  			boxToCenter($('#cashPayMsgBox'));
 		}); 

		<@s.if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
			$("#moneyAccountPayButton").click(function(){
				var pay_url="/orderPay/cashAccountValidateAndPay.do";
				pay_url+=$("#paymentParamsCa").val();
				pay_url+="&orderId="+$('#orderId').val();
				pay_url+="&userNo="+$("#userId").val();
				pay_url+="&cashAccountVerifyCode="+$("#cashAccountVerifyCode").val();
				pay_url+="&jsoncallback=?";
				doCashPay(pay_url);
				return false;
			});
		</@s.if>
		<@s.elseif test='user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==true'>
			$("#moneyAccountPayButton").click(function(){
				var pay_url="/orderPay/cashAccountValidateAndPay.do";
				pay_url+=$("#paymentParamsCa").val();
				pay_url+="&orderId="+$('#orderId').val();
				pay_url+="&userNo="+$("#userId").val();
				pay_url+="&emailVerfityCode="+$("#emailVerifyCode").val();
				pay_url+="&firstOrderCtMobile="+$("#firstOrderCtMobile").val();
				pay_url+="&jsoncallback=?";
				doCashPay(pay_url);
				return false;
			});
		</@s.elseif>
		<@s.elseif test='user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==false'>
			$("#moneyAccountPayButton").click(function(){
					var pay_url="/orderPay/cashAccountValidateAndPay.do";
					pay_url+=$("#paymentParamsCa").val();
					pay_url+="&orderId="+$('#orderId').val();
					pay_url+="&userNo="+$("#userId").val();
					pay_url+="&firstOrderCtMobile="+$("#firstOrderCtMobile").val();
					pay_url+="&jsoncallback=?";
					doCashPay(pay_url);
					return false;
				});
		</@s.elseif>
		<@s.elseif test='null!=firstOrder'>
			$("#moneyAccountPayButton").click(function(){
					var pay_url="/orderPay/cashAccountValidateAndPay.do";
					pay_url+=$("#paymentParamsCa").val();
					pay_url+="&orderId="+$('#orderId').val();
					pay_url+="&userNo="+$("#userId").val();
					pay_url+="&firstOrderCtMobile="+$("#firstOrderCtMobile").val();
					pay_url+="&jsoncallback=?";
					doCashPay(pay_url);
					return false;
				});
		</@s.elseif>
});
 </script>
