<div class="cardsPayment" >
	<h3 class="h3Close" id="cardsH3CloseBtn"><em></em>礼品卡\储值卡支付</h3>
   <div class="cardsPalmentText" id="cardsPalmentTextDiv">
        <ul>
            <input id="orderId" name="orderId" type="hidden" value="${order.orderId}">
            <input id="stc_userNo" name="userNo" type="hidden" value="${order.userId}">
            <input id="stc_amount" name="amount" type="hidden" value="${order.oughtPay}">
            <input id="paymentParamsStc" name="paymentParams" type="hidden" value="${paymentParams}">
            <input id="isBoundLipinka" name="isBoundLipinka" type="hidden" value="${isBoundLipinka}">
            <input id="boundLipinkaUsable" name="boundLipinkaUsable" type="hidden" value="${boundLipinkaUsable}">
            <input id="hadConsumedLipinka" name="hadConsumedLipinka" type="hidden" value="${hadConsumedLipinka}">
            <@s.if test='isBoundLipinka=="1" && boundLipinkaUsable!="1"'>
				<div class="tiptext tip-info"><span class="tip-icon tip-icon-info"></span>尊敬的用户，您绑定的礼品卡无法使用（可能原因：被冻结、过期、卡内金额为0），详情请查看礼品卡明细</div>
			</@s.if>
            <li><label><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoOld')" value="cardNoOldRadio" type="radio" checked>储值卡卡号：</label><input name="cardNo"  id="cardNoOld" type="text"  onChange="chackOldCardNo(this.value);"/></li>
			<@s.if test='isBoundLipinka!="1" || boundLipinkaUsable!="1"'>
				<li><label><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoNew')" value="cardNoNewRadio"  type="radio">驴妈妈礼品卡：</label><input name="cardNo"  id="cardNoNew" type="text" disabled onChange="chackNewCardNo(this.value);" />
					<label>密码：</label><input class="w-170" type="password" name="cardPassword" id="cardPassword" disabled />
				</li>
			</@s.if>
			<@s.if test='isBoundLipinka=="1" && boundLipinkaUsable=="1"'>
			    <input id="cardNoNew" name="cardNo" type="hidden" value="${bindCardNo}">
            	<input id="cardPassword" name="cardPassword" type="hidden" value="${bindCardPassword}">
				<li id="usermessage">
                    <label><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoNew')" value="cardNoNewRadio"  type="radio">驴妈妈礼品卡：</label><label>短信验证码：</label><input type="text" style="width:60px;" class="input-text i-checkcode" value="" id="cardMobileCode" name="cardMobileCode" disabled />
                    <a href="javascript:;" class="ui-btn ui-btn1" id="send-verifycode-card"><i>免费获取校验码</i></a>
                    <span style="display:none;padding-left:0;" id="JS_countdown_card"><span class="tips-success" style="display:inline"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo" style="display:inline; padding-left:0; border:none;">60秒内没有收到短信?&#12288;<span href="" class="ui-btn ui-disbtn" style="float:none;display:inline-block; padding-left:5px;"><i>(<span class="num-second" style=" padding-left:0;display:inline;">60</span>)秒后再次发送</i></span></span></span>
					<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span>
			 	 </li>
			</@s.if>
            <li>
            <label>验证码：</label>
              <input name="verifycode" type="text" style="width:88px;" id="verifycode" size="4"   onChange="chackVerifycode(this.value);"/>
               <a href="#" onClick="refreshw('card_image2');return false;"><img style="vertical-align:middle;" id="card_image2" src="/account/checkcode.htm" /></a>
            </li>
            <li>
              <input id="submit_pay" name="button" type="button" value="确认支付" class="button button01"/>
            </li>
        </ul>
                      
         <@s.if test='usageList.size()>0'>
         <table width="70%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tableTit">
            <td>储值卡卡号</td>
            <td>面值</td>
            <td>本次支付</td>
            <td>剩余</td>
            <td>有效期至</td>
          </tr>
          <#list usageList as st>
          <tr>
            <td>${st.cardNo}</td>
            <td>${st.amountFloat}</td>
            <td>${st.payAmount}</td>
            <td>${st.balanceAmount}</td>
            <td>
             ${st.overTime}</td>
          </tr>
          </#list>
        
        </table> 
       </@s.if>
       <p><strong>注:</strong><span>1.您可以分多次使用多张储值卡进行支付<br />2.订单取消后本次使用金额会退回储值卡 </span></p>
   </div><!--cardsPalmentText end -->
</div><!--cardsPayment end-->

<div class="msgDiv msgDiv01">
  <div class="div_block">
    <div class="msgNewBlock">
    	<em class="close"></em>
      <p class="msgTitle">储值卡支付</p>
      <ul>
      	<li class="msgText"><s></s>确认支付吗？</li>
      </ul>
      <p class="button"><input type="button" onclick="storedCardPayFN()" class="newbutton newbutton01 newbutton01A" value="确认" /><input type="button" class="newbutton newbutton01 newbutton01A" value="取消" /></p>
    </div>
  </div>
</div><!--msgDiv end-->
<div class="msgDiv msgDiv02">
  <div class="div_block">
    <div class="msgNewBlock">
    	<em class="close"></em>
      <p class="msgTitle">储值卡支付</p>
      <p class="textPrompt"><span><s class="icon4"></s>储值卡支付成功！</span>
	  <span id="part_pay_flag_id"><br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong></span></p>
      <p class="ClickButton"><input type="button" id="storecardpayOKButton"  data-biaoshi="settingSuccess" value="确认" /></p>
    </div>
  </div>
</div><!--msgDiv end-->
<script>
$('#storecardpayOKButton').click(function(){
		var address = "/view/view.do?orderId="+$('#orderId').val();
		window.location.href = address;
	});
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
					$("#part_pay_flag_id").hide();
					$('.msgDiv01').hide();
					$('.msgDiv02').show();
					$('.bgDiv').show();   
				}else{
					$('.msgDiv01').hide();
					$('.bgDiv').show();	 
					$('.msgDiv02').show();
					$("#part_pay_flag_id").show();
				}
				
			}else{
				alert(data.msg);
				return;
			}
		}
	);
}

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


<@s.if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
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
</@s.if>
</script>

<div class="bgDiv"></div>
