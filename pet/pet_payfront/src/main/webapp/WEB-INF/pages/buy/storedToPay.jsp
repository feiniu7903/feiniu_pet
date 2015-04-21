<!-- 储值卡支付 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="storedPayDiv" class="hide">
    <p>友情提示:1.您可以分多次使用多张储值卡进行支付&nbsp;&nbsp;2.订单取消后本次使用金额会退回储值卡</p>
    <div class="dot_line"></div>
    <div class="hr_a"></div>
    <div class="form form-hor form-inline form-w">
       <div class="control-group">
        	<input id="orderId" name="orderId" type="hidden" value="${orderId}">
            <input id="stc_userNo" name="userNo" type="hidden" value="${orderUserId}">
            <input id="stc_amount" name="amount" type="hidden" value="${payAmountFen}">
            <input id="paymentParamsStc" name="paymentParams" type="hidden" value="${paymentParams}">
            <input id="isBoundLipinka" name="isBoundLipinka" type="hidden" value="${isBoundLipinka}">
            <input id="boundLipinkaUsable" name="boundLipinkaUsable" type="hidden" value="${boundLipinkaUsable}">
            <input id="hadConsumedLipinka" name="hadConsumedLipinka" type="hidden" value="${hadConsumedLipinka}">
            <c:if test="${isBoundLipinka=='1' && boundLipinkaUsable!='1'}">
            	<div class="tiptext tip-info"><span class="tip-icon tip-icon-info"></span>尊敬的用户，您绑定的礼品卡无法使用（可能原因：被冻结、过期、卡内金额为0），详情请查看礼品卡明细</div>
            </c:if>
            <label class="control-label"><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoOld')" value="cardNoOldRadio" type="radio" checked> 储值卡卡号：</label><input name="cardNo"  id="cardNoOld" type="text"  onChange="chackOldCardNo(this.value);" class="input-text"/>
        </div>
        
        <c:if test="${isBoundLipinka!='1' || boundLipinkaUsable!='1' }">
	        <div class="control-group">
	   			<!-- 文本框输入提示 TODO hadConsumedLipinka -->
	   			<label class="control-label"><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoNew')" value="cardNoNewRadio"  type="radio"> 驴游天下卡：</label>
	   			<input name="cardNo"  id="cardNoNew" type="text"  onChange="chackNewCardNo(this.value);" class="input-text" disabled/>
	   			<label>密码：</label><input  type="password" name="cardPassword" id="cardPassword" class="input-text"  disabled/>
	   	    </div>
        </c:if>
        <c:if test="${isBoundLipinka=='1' && boundLipinkaUsable=='1' }">
        	<div class="control-group">
        		<input id="cardNoNew" name="cardNo" type="hidden" value="${bindCardNo}">
           		<input id="cardPassword" name="cardPassword" type="hidden" value="${bindCardPassword}">
                <label class="control-label"><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoNew')" value="cardNoNewRadio"  type="radio">驴妈妈礼品卡：</label><label>短信验证码：</label><input type="text" style="width:60px;" class="input-text i-checkcode" value="" id="cardMobileCode" name="cardMobileCode" disabled />
                <a href="javascript:;" class="ui-btn ui-btn1" id="send-verifycode-card"><i>免费获取校验码</i></a>
                <span style="display:none;padding-left:0;" id="JS_countdown_card"><span class="tips-success" style="display:inline"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo" style="display:inline; padding-left:0; border:none;">60秒内没有收到短信?&#12288;<span href="" class="ui-btn ui-disbtn" style="float:none;display:inline-block; padding-left:5px;"><i>(<span class="num-second" style=" padding-left:0;display:inline;">60</span>)秒后再次发送</i></span></span></span>
				<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span>
			</div>
        </c:if>

        <div class="control-group">
            <label class="control-label">验证码：</label>
            <div class="controls">
                <input name="verifycode" type="text" class="input-text" id="verifycode" size="4"   onChange="chackVerifycode(this.value);"/>
                <img class="vmiddle" id="card_image2" src="/payfront/account/checkcode.htm" width="70" height="30"/><a href="#" onClick="refreshw('card_image2');return false;"> 看不清？换一个</a>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button class="pbtn pbtn-orange" id="submit_pay" name="button" onclick="affirmPay();">确认支付</button>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <p><a href="#" data-dismiss="dialog">选择其他方式付款</a></p>
            </div>
        </div>
    </div>
</div>

<div class="msgDiv msgDiv01 hide">
  <div class="div_block">
    <div class="msgNewBlock">
    	<em class="close"></em>
      <p class="msgTitle">储值卡支付</p>
      <ul>
      	<li class="msgText">确认支付吗？</li>
      </ul>
      <p class="button"><input type="button" onclick="storedCardPayFN()" class="newbutton newbutton01 newbutton01A" value="确认" /><input type="button" class="newbutton newbutton01 newbutton01A" value="取消" /></p>
    </div>
  </div>
</div><!--msgDiv end-->
<div class="msgDiv msgDiv02 hide" >
  <div class="div_block">
    <div class="msgNewBlock">
    	<em class="close"></em>
      <p class="msgTitle">储值卡支付</p>
      <p class="textPrompt"><span>储值卡支付成功！</span>
	  <span id="part_pay_flag_id"><br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong></span></p>
      <p class="ClickButton"><input type="button" id="storecardpayOKButton"  data-biaoshi="settingSuccess" value="确认" /></p>
    </div>
  </div>
</div><!--msgDiv end-->

<script  type="text/javascript">

	function checkCardType(id){
	
		if('cardNoOld'==id){
			$("#cardNoOld").attr("disabled",false);
			$("#cardNoNew").attr("disabled",true);
			$("#cardPassword").attr("disabled",true);
			$("#cardMobileCode").attr("disabled",true);
		}
		
		if('cardNoNew'==id){
			$("#cardNoOld").val("");
			$("#cardNoOld").attr("disabled",true);
			$("#cardNoNew").attr("disabled",false);
			$("#cardPassword").attr("disabled",false);
			$("#cardMobileCode").attr("disabled",false);
		}    
	}

	function chackOldCardNo(cardNo){
		$.post("${basePath }/ajax/chackOldCardNo.do",{"cardNo":cardNo},function(dt){
			var data=eval("("+dt+")");
			if(data.success){		
			}else{
				alert(data.msg);
			}
		});
	}
	function chackNewCardNo(cardNo){
		$.post("${basePath }/ajax/chackNewCardNo.do",{"cardNo":cardNo},function(dt){
			var data=eval("("+dt+")");
			if(data.success){	
			}else{
				alert(data.msg);
			}
		});
	}

	function chackVerifycode(verifycode){
		$.post("${basePath }/ajax/chackVerifycode.do",{"verifycode":verifycode},function(dt){
			var data=eval("("+dt+")");
			if(data.success){								
			}else{
				alert(data.msg);
				$("#verifycode").val("");
			}
		});
 	}
	
	function affirmPay(){

		var checkType = $('input[name=cardTypeCheck]:checked').val();
		var userCardNo;
		var userCardpassword;
		if('cardNoOldRadio'==checkType){
			userCardNo=$("#cardNoOld").val();
			if(userCardNo==""){
				alert("储值卡卡号不能为空，请填写！"); 	
				renturn;	
			}
		}
		if('cardNoNewRadio'==checkType){
			userCardNo = $("#cardNoNew").val();
			userCardpassword = $("#cardPassword").val();
			if(userCardNo==""||userCardpassword==""){
				alert("储值卡卡号，密码不能为空，请填写！"); 
				renturn;
			}	
		}
		var userVerifycode=$("#verifycode").val(); 
		if(userVerifycode==""){
			 alert("储值卡卡号不能为空,验证码不能为空 请填写！"); 		
			 renturn;	 
		}
		
		if('cardNoOldRadio'==checkType){
			$.post("${basePath }/ajax/chackOldCardNo.do",{"cardNo":userCardNo},function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					pandora.dialog({
						title: "储值支付",
						content: "确认支付吗？",
						okClassName:"pbtn-orange",
						okValue: "确认支付",
						ok: function () {
							storedCardPayFN();
						},
						cancel: true
					});    			
				}else{
					alert(data.msg);
					return;
				}
			}); 
		}
		
		if('cardNoNewRadio'==checkType){
			$.post("${basePath }/ajax/chackNewCardNo.do",{"cardNo":userCardNo},function(dt){
				var data=eval("("+dt+")");
				if(data.success){	
					var isBoundLipinka = $("#isBoundLipinka").val();
					var boundLipinkaUsable = $("#boundLipinkaUsable").val();
					if(isBoundLipinka!="1" || boundLipinkaUsable!="1") {
						$.post("${basePath }/ajax/chackCodeNoPassword.do",{"cardNo":userCardNo,"cardPassword":userCardpassword},function(dt){
							var data=eval("("+dt+")");
							if(data.success){	
								pandora.dialog({
									title: "储值支付",
									content: "确认支付吗？",
									okClassName:"pbtn-orange",
									okValue: "确认支付",
									ok: function () {
										storedCardPayFN();
									},
									cancel: true
								});     			
							}else{
								alert(data.msg);
								return;
							}
						});
					}else {//已绑定礼品卡
						//做手机验证码的判断
						$.post("${basePath}/ajax/mobileCodeValidate.do",{"userNo":$("#stc_userNo").val(),"mobileVerifyCode":$("#cardMobileCode").val()},function(dt){
							var data=eval("("+dt+")");
							if(data.success){	
								pandora.dialog({
									title: "储值支付",
									content: "确认支付吗？",
									okClassName:"pbtn-orange",
									okValue: "确认支付",
									ok: function () {
										storedCardPayFN();
									},
									cancel: true
								});   
							}else{
								alert(data.msg);
							}
						});
					}		
				}else{
					alert(data.msg);
				}
			});
		}
	}
	
	var status=true;
	function storedCardPayFN(){
		if(!status){
			return;
		}
		status=false;
		var checkType = $('input[name=cardTypeCheck]:checked').val();
		var storecard_pay_url;
		storecard_pay_url="${constant.paymentUrl}pay/storedCardPay.do";
		storecard_pay_url+=$("#paymentParamsStc").val();
		if('cardNoNewRadio'==checkType){
			storecard_pay_url+="&cardType=1";
			storecard_pay_url+="&cardNo="+$("#cardNoNew").val();
			storecard_pay_url+="&cardPassword="+$("#cardPassword").val();
		}else{
			storecard_pay_url+="&cardType=0";
			storecard_pay_url+="&cardNo="+$("#cardNoOld").val();
		}
		storecard_pay_url+="&verifycode="+$("#verifycode").val();
		storecard_pay_url+="&userNo="+$("#stc_userNo").val();
		storecard_pay_url+="&orderId="+$("#orderId").val();
		storecard_pay_url+="&jsoncallback=?";
	 	$.getJSON(storecard_pay_url,function(data){
				if(data.paySuccess){
					if(data.allPayState){
						pandora.dialog({
							title: "储值支付",
							content: "储值卡支付成功！",
							okClassName:"pbtn-orange",
							okValue: "确认",
							ok: function () {
								payOKButton();
							}
						});   
					}else{
						pandora.dialog({
							title: "储值支付",
							content: "储值卡支付成功！<br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong>",
							okClassName:"pbtn-orange",
							okValue: "确认",
							ok: function () {
								payOKButton();
							}
						});
					}
				}else{
					alert(data.msg);
					payOKButton();
				}
			}
		);
	}
</script>

<c:if test="${user.mobileNumber!=null && user.isMobileChecked=='Y'}">
	<script  type="text/javascript">
	$(function(){
	
		$('#send-verifycode-card').click(function(){
			$.getJSON("http://login.lvmama.com/nsso/ajax/cashAccountPayAuthenticationCode.do?mobileOrEMail=${user.mobileNumber}&jsoncallback=?",function(json){
				if (json.success) {
					$("#span_tips").hide();
					$('#send-verifycode-card').hide();
					$("#JS_countdown_card").show();
					JS_countdown_card("#JS_countdown_card span.num-second");
				} else {
					if(json.errorText == 'phoneWarning'){
						$("#span_tips").html("已超过每日发送上限，请于次日再试");
						$("#span_tips").show();
						$("#send-verifycode-card").unbind();  
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
		
	 });
		
		
		 function JS_countdown_card(_cdbox){
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
		   $("#JS_countdown_card"+expr).find(".tips-success").html("<span class=\"tips-ico01\"></span>校验码已发送成功，以最近发送的校验码为准").end().hide();
		   $("#send-verifycode-card"+expr).html("<i>重新发送验证码</i>").show();
		  }
		  else {
		   _curCount--;
		   $(_cdbox).html(_curCount);
		  }
		 }
		};
	</script>
</c:if>
<div class="bgDiv"></div>