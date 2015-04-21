<div id="moneyAccountPayBox" class="hide">
	<#if moneyAccount.valid=="Y" >
		<#if moneyAccount.maxPayMoney gt 0 >
				<p>
					您的驴妈妈存款账户可用余额 
					<b>${moneyAccount.maxPayMoneyYuan}</b> 元，本次支付将使用 
					<b class="dfn">
						<#if moneyAccount.maxPayMoney gte order.actualPayFloat>
							<#if moneyAccount.maxPayMoneyYuan gte order.oughtPayYuanFloat>
								${order.oughtPayYuanFloat}
							<#else>
								${moneyAccount.maxPayMoneyYuan}
							</#if>
						</#if>
					</b> 元。
				</p>
				
			    <div class="dot_line"></div>
			    <input id="payBaseUrl" name="payBaseUrl" value="${constant.paymentUrl}" type="hidden"/>
			    <input id="IsParentPage" name="IsParentPage" value="Y" type="hidden"/>
			    <input id="orderId" name="orderId" value="${order.orderId}" type="hidden"/>
			    <input id="oughtPay" name="oughtPay" value="${order.oughtPay}" type="hidden"/>
			    <input id="userId" name="userId" value="${order.userId}" type="hidden"/>
			    <input id="bonus" name="bonus" value="${actBonus}" type="hidden"/>
			    <input id="paymentParamsCa" name="paymentParams" type="hidden" value="${paymentParamsCashAccount}">
				    <#if user.mobileNumber!=null&&user.isMobileChecked=="Y" >
					    <p>您的账户已绑定手机 <b><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></b>，为了您的账户安全需要进行验证。</p>
					    <div class="hr_a"></div>
					    <div class="form-hor form-inline form-w">
					        <div class="control-group">
					            <label class="control-label">短信校验码：</label>
					            <div class="controls">
					            	<p><input type="text" class="input-text i-checkcode" value="" id="cashAccountVerifyCode"/></p>
					                <p style="height:30px;">
					                	<a href="#" class="pbtn pbtn-small" id="send-verifycode">免费获取校验码</a>
					                	<span id="JS_countdown" class="hide">
						                	<span class="tiptext tip-success tip-line">
	                       	 					<span class="tip-icon tip-icon-success"></span>校验码已发送成功，请查看手机
	                       	 				</span>
						                	<span class="tiptext tip-default tip-line">60秒内没有收到短信?
												<a href="#;" class="pbtn pbtn-small pbtn-gray">
												(<span class="J_num">60</span>)秒后再次发送</a>
	                   					 	</span>
                   					 	</span>
						            </p>
						            <p style="display:none" id="span_tips">
					                	<span class="tiptext tip-error tip-line">
					                		<span class="tip-icon tip-icon-error"></span>已超过每日发送上限，请于次日再试
					                	</span>
					                </p>
					                <p style="display:none" id="span_tipstwo">
					                	<span class="tiptext tip-warning tip-line">
					                	<span class="tip-icon tip-icon-warning"></span>当前IP发送频率过快，请稍候重试</span>
					                </p>
					                <p style="display:none" id="span_tipsthree">
					                	<span class="tiptext tip-warning tip-line">
					                	<span class="tip-icon tip-icon-warning"></span>发送频率过快，请稍后重试</span>
					                </p>
					            </div>
					        </div>
					        <div class="control-group">
					            <label class="control-label">&nbsp;</label>
					            <div class="controls">
					                <button class="pbtn pbtn-orange" id="moneyAccountPayButton" >确定</button>
					            </div>
					        </div>
					    </div>
					<#elseif user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==true>
					    <div class="hr_a"></div>
					    <div class="form-hor form-inline form-w">
					        <div class="control-group">
					            <label class="control-label">邮箱地址：</label>
					            <div class="controls">
					            	<p><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /></p>
					                <p>
										<a href="#" id="send-email-verifycode" class="pbtn pbtn-mini pbtn-white">获取邮箱验证码</a>
										<span id="JS_sendEmail" style="display:none">
											<span class="tip-icon tip-icon-success"></span><span id="email-send-msg">已成功发送验证邮件</span>
											&nbsp;&nbsp; 没收到邮件?&nbsp;&nbsp;
		                    				<a href="#" id="reSend-email-verifycode">再次发送</a>
	                    				</span>
						            </p>
					            </div>
					        </div>
					        <div>
					        	<label class="control-label">验证码：</label>
					        	<div class="controls">
					                <p><input type="text" class="input-text i-checkcode" value="" id="emailVerifyCode" name="emailVerifycode" /></p>
					            </div>
					        </div>
					        <div class="dot_line"></div>
					        <h4>订单信息验证</h4>
				        	<p>您在驴妈妈的第一笔订单:
			                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
			                	<#list firstOrder.ordOrderItemProds as itemObj>
								    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
								</#list><br/>
			                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;
			                	联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
		                    </p>
					        <div class="control-group">
					            <label class="control-label">第一笔订单联系人手机号：</label>
					            <div class="controls">
					                <input type="text" class="input-text" name="firstOrderCtMobile" id="firstOrderCtMobile"/>
					            </div>
					        </div>
					        <div class="control-group">
					            <label class="control-label">&nbsp;</label>
					            <div class="controls">
					                <button class="pbtn pbtn-orange" id="moneyAccountPayButton">确定</button>
					            </div>
					        </div>
					    </div>
					 <#elseif user.mobileNumber==null&&user.isMobileChecked!="Y"&&user.email!=null&&user.isEmailChecked=="Y"&&null!=firstOrder&&needsEmailCheck==false>
				 		<h4>订单信息验证</h4>
				 		<div class="form-hor form-inline form-w">	
			                <p>您在驴妈妈的第一笔订单:
				                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
				                	<#list firstOrder.ordOrderItemProds as itemObj>
									    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
									</#list><br/>
				                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;
				                	联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
			                    <div class="control-group">
			                    	<label class="control-label">第一笔订单联系人手机号:</label>
			                    	<div class="controls">
						                <input type="text" class="input-text" name="firstOrderCtMobile" id="firstOrderCtMobile"/>
						            </div>
			                    </div>
			                    <div class="control-group">
			                        <label class="control-label">&nbsp;</label>
						            <div class="controls">
						                <button class="pbtn pbtn-orange" id="moneyAccountPayButton">确定</button>
						            </div>
						        </div>
			        		</p>
		        		</div>
					<#elseif null!=firstOrder>
				 		<h4>订单信息验证</h4>	
				 		<div class="form-hor form-inline form-w">
		                <p>您在驴妈妈的第一笔订单:
			                    <a target="_blank" href="/myspace/order_detail.do?orderId=${firstOrder.orderId}">${firstOrder.orderId}</a>，
			                	<#list firstOrder.ordOrderItemProds as itemObj>
								    ${itemObj.productName?if_exists}<em>×${itemObj.quantity?if_exists}</em>
								</#list><br/>
			                	联系人为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hidenUserName(firstOrder.contact.name)" />&nbsp;&nbsp;联系人手机号码为：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(firstOrder.contact.mobile)" />
		                    <div class="control-group">
		                    	<label class="control-label">第一笔订单联系人手机号:</label>
		                    	<div class="controls">
					                <input type="text" class="input-text" name="firstOrderCtMobile" id="firstOrderCtMobile"/>
					            </div>
		                    </div>
		                    <div class="control-group">
		                        <label class="control-label">&nbsp;</label>
					            <div class="controls">
					                <button class="pbtn pbtn-orange" id="moneyAccountPayButton">确定</button>
					            </div>
					        </div>
		        		</p>
		        		</div>
					<#else>
						<div class="control-group">
						    <label class="control-label">&nbsp;</label>
				            <div class="controls">
								<p>
									亲爱的用户，为了保障账户的安全，请先绑定你的手机，再进行支付！
									<span  data-biaoshi="msgDiv"><a href="/myspace/userinfo/phone.do" target="_blank">立即绑定</a></span>。
									绑定成功后，请<a href="javascript:window.location.reload();">刷新</a>当前页面！
								</p>
							</div>
				        </div>
					</#if>
					<div class="control-group">
					    <label class="control-label">&nbsp;</label>
			            <div class="controls">
			                <p><a href="#" data-dismiss="dialog">选择其他方式付款</a></p>
			            </div>
			        </div>
		<#else>
			您的账户余额&yen;0，<a target="_blank" href="/myspace/account/store.do">账户充值</a><br/>
		  	若订单金额超过网上银行最大单笔支付限额时，您可以为驴妈妈存款账户充值后一同完成支付。
		</#if>
	<#else>
		<div class="control-group">
		    <label class="control-label">&nbsp;</label>
	        <div class="controls">
	            	<p><font color="#FF0000">亲爱的用户，系统认为您的帐户存在风险，为了您的账户安全，系统已将您的账户冻结，请立即致电客服10106060。</font>
	            </p>
	        </div>
		</div>
	</#if>
</div>
<div id="cashPayMsgBox" class="hide">
    <div class="control-group">
        <label class="control-label">&nbsp;</label>
        <div class="controls">
			<p>存款账户余额付款成功！</p>
		</div>
    </div>
    <@s.if test="moneyAccount.maxPayMoney < order.actualPayFloat">
    	 <div class="control-group">
    	    <label class="control-label">&nbsp;</label>
	        <div class="controls">
		    	<br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong></p>
			</div>
	    </div>
    </@s.if>
</div>	
<script>
var phone = /^1[3|5|8][0-9]\d{8}$/;
function verifycodeInput_Check(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile=${user.mobileNumber}&authenticationCode=" + $.trim($("#cashAccountVerifyCode").val()) + "&jsoncallback=?",function(json){
		if (json.success == true) {
			outOk($("#cashAccountVerifyCode"));
		}else{
			error_tip("#cashAccountVerifyCode","验证码输入错误");
			return false;
		}
	});
}
function firstOrderCtMobile_Check(){
	var ctMb=$.trim($('#firstOrderCtMobile').val()).replace(/\s+/g,'');
	if(ctMb == ""){
		error_tip("#firstOrderCtMobile","手机号码不能为空");
		return false;
	}else if(phone.test(ctMb)){
		$.getJSON("/myspace/userinfo/validateFirstOrder.do?firstOrderCtMobile="+ctMb, function(json){
		  	  if(json.result==true){
		   	 	outOk($("#firstOrderCtMobile"));
		   	 }else{
		   	 	error_tip('#firstOrderCtMobile',json.msg);
		   	 	return false;
		   	 }
		});
	} else {
		error_tip('#firstOrderCtMobile','请输入有效的手机号');
		return false;
	}
}
function emailVerifycode_Check(){
	var code=$.trim($('#emailVerifyCode').val());
	if(code == ""){
		error_tip("#emailVerifyCode","邮箱校检码不能为空");
		return false;
	}else{
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?email=${user.email}&authenticationCode=" + $.trim($("#emailVerifyCode").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				outOk($("#emailVerifyCode"));
			}else{
				error_tip("#emailVerifyCode","邮箱校检码错误");
				return false;
			}
		});
	}
}
<@s.if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
	$('#send-verifycode').click(function(){
		$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=${user.mobileNumber}&jsoncallback=?",function(json){
			if (json.success) {
				$('#send-verifycode').hide();
				$("#span_tips").hide();
				$("#span_tipstwo").hide();
				$("#span_tipsthree").hide();
				$("#JS_countdown").show();
				JS_countdown("#JS_countdown span.J_num");
			} else {
				if(json.errorText == 'phoneWarning'){
					$("#span_tips").show();
				    $("#span_tipstwo").hide();
				    $("#span_tipsthree").hide();
				}else if(json.errorText == 'ipLimit'){
					$("#span_tips").hide();
					$("#span_tipstwo").show();
					$("#span_tipsthree").hide();
				}else if(json.errorText == 'waiting'){
					$("#span_tips").hide();
					$("#span_tipstwo").hide();
					$("#span_tipsthree").show();
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
	   $("#JS_countdown"+expr).children(".tip-success").html("<span class=\"tip-icon tip-icon-success\"></span>校验码已发送成功，请查看手机").end().hide();
	   $("#send-verifycode"+expr).html("重新发送验证码").show();
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
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			 $(_this).hide();
			 $("#JS_sendEmail").show();
		} else {
			error_tip('验证码发送失败，请重新尝试');
			return false;
		}
	});	
});
$('#reSend-email-verifycode').click(function(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId=${user.id}&mobileOrEMail=${user.email}&validateType=MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",function(json){
		if (json.success) {
			$("#email-send-msg").html("成功重新发送验证到邮件");
		} else {
			error_tip('验证码发送失败，请重新尝试');
			return false;
		}
	});	
});
</@s.elseif>

function doCashPay(pay_url){
    $("#moneyAccountPayButton").attr("disabled",true);
    $.ajax({ 
	  url: pay_url,
	  cache: false,
	  dataType:"json",
	  success: function(data){
	    if(data.success){
			showMsgBox();
		}else{
			$("#moneyAccountPayButton").attr("disabled",false);
			$.alert(data.msg);
			return false;
		}
	  }
	});
}

function showMsgBox(){
	pandora.dialog({						
	    title: "存款账户支付",						
	    content:$("#cashPayMsgBox"),
	    okValue: "确认",
	    ok: function(){
	    	var address = "/pay/ticket-${order.mainProduct.productId}-${order.orderId}";
			window.location.href = address;
	    }
	 })	
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
				pay_url+="&bonus="+$("#bonus").val();
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
				pay_url+="&bonus="+$("#bonus").val();
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
					pay_url+="&bonus="+$("#bonus").val();
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
					pay_url+="&bonus="+$("#bonus").val();
					pay_url+="&jsoncallback=?";
					doCashPay(pay_url);
					return false;
				});
		</@s.elseif>
});
 </script>