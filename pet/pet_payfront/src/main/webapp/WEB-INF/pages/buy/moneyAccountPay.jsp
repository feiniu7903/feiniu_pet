<!-- 现金账户支付，账户绑定手机 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="moneyAccountPay" class="hide">
    <p>您的存款账户可支付余额 <span class="dfn">${moneyAccount.maxPayMoneyYuan}</span> 元，用存款账户余额付款  本次支付将使用 <span class="dfn"><s:if test="moneyAccount.maxPayMoneyYuan>oughtPayYuan">${oughtPayYuan }<input id="bonus" name="bonus" type="hidden" value="${payAmountFen }"></s:if><s:else>${moneyAccount.maxPayMoneyYuan }<input id="bonus" name="bonus" type="hidden" value="${moneyAccount.maxPayMoney }"></s:else></span> 元</p>
    <div class="dot_line"></div>
    <p>您的账户已绑定手机 <b><s:property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)"/></b>，为了您的账户安全需要进行验证。</p>
    <div class="hr_a"></div>
	<input id="paymentParamsCa" name="paymentParams" type="hidden" value="${paymentParamsCashAccount}">
    <div class="form-hor form-inline form-w">
        <div class="control-group">
            <label class="control-label">短信校验码：</label>
            <div class="controls">
                <p><input type="text" class="input-text" id="cashAccountVerifyCode"/></p>
                <p style="height:30px;">
                    <a id="send-verifycode" class="pbtn pbtn-small" onclick="sendVerifycode()">免费获取校验码</a>
                    <span id="JS_countdown" class="hide">
                        <span class="tiptext tip-success tip-line">
                            <span class="tip-icon tip-icon-success"></span>校验码已发送成功，请查看手机
                        </span>
                        <span class="tiptext tip-default tip-line">60秒内没有收到短信? <a href="javascript:;" class="pbtn pbtn-small pbtn-gray">(<span class="J_num">60</span>)秒后再次发送</a>
                        </span>
                    </span>
                </p>
                <p style="display:none" id="span_tips"><span class="tiptext tip-error tip-line"><span class="tip-icon tip-icon-error"></span>已超过每日发送上限，请于次日再试</span></p>
                <p style="display:none" id="span_tipstwo"><span class="tiptext tip-warning tip-line"><span class="tip-icon tip-icon-warning"></span>当前IP发送频率过快，请稍候重试</span></p>
                <p style="display:none" id="span_tipsthree"><span class="tiptext tip-warning tip-line"><span class="tip-icon tip-icon-warning"></span>发送频率过快，请稍后重试</span></p>
                
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <button class="pbtn pbtn-orange" id="moneyAccountPayButton" onclick="moneyAccountToPay()">确定</button>
            </div>
        </div>
    </div>
    <div class="form-hor form-inline form-w">
        <div class="control-group">
            <div class="controls">
                <p><a href="#" data-dismiss="dialog">选择其他方式付款</a></p>
            </div>
        </div>
    </div>
</div>
<div id="cashPayMsgBox" class="hide">
    <div class="control-group">
        <label class="control-label">&nbsp;</label>
        <div class="controls">
			<p>存款账户余额付款成功！</p>
		</div>
    </div>
    <s:if test="moneyAccount.maxPayMoneyYuan<oughtPayYuan">
    	 <div class="control-group">
    	    <label class="control-label">&nbsp;</label>
	        <div class="controls">
		    	<br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong></p>
			</div>
	    </div>
    </s:if>
</div>	
<script type="text/javascript">

	function sendVerifycode(){
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
	}

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
			}else {
			   _curCount--;
			   $(_cdbox).html(_curCount);
 			}
		}
	};

	function moneyAccountToPay(){
		var pay_url="${basePath }/orderPay/cashAccountValidateAndPay.do";
		pay_url+=$("#paymentParamsCa").val();
		pay_url+="&orderId=${orderId}";
		pay_url+="&userNo=${user.userNo }";
		pay_url+="&cashAccountVerifyCode="+$("#cashAccountVerifyCode").val();
		pay_url+="&bonus="+$("#bonus").val();
		pay_url+="&jsoncallback=?";
		doCashPay(pay_url);
		return false;
	}

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
		    	payOKButton();
		    }
		 });	
	}
</script>