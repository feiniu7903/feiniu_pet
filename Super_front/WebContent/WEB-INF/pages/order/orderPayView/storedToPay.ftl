<div id="storedId" class="hide">
	<p>友情提示:1.您可以分多次使用多张储值卡进行支付&nbsp;&nbsp;2.订单取消后本次使用金额会退回储值卡 </p>
	<div class="dot_line"></div>
    <div class="hr_a"></div>
    <div class="form form-hor form-inline form-w">
        <div class="control-group">
        	<input id="orderId" name="orderId" type="hidden" value="${order.orderId}">
            <input id="stc_userNo" name="userNo" type="hidden" value="${order.userId}">
            <input id="stc_amount" name="amount" type="hidden" value="${order.oughtPay}">
            <input id="paymentParamsStc" name="paymentParams" type="hidden" value="${paymentParams}">
            
            <li><label class="control-label"><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoOld')" value="cardNoOldRadio" type="radio" checked> 储值卡卡号：</label><input name="cardNo"  id="cardNoOld" type="text"  onChange="chackOldCardNo(this.value);" class="input-text"/></li>
            <br>
			<li><label class="control-label"><input class="card_checkbox" name="cardTypeCheck" onClick="checkCardType('cardNoNew')" value="cardNoNewRadio"  type="radio"> 驴游天下卡：</label><input name="cardNo"  id="cardNoNew" type="text"  onChange="chackNewCardNo(this.value);" class="input-text" disabled=true/><label>密码：</label><input  type="password" name="cardPassword" id="cardPassword" class="input-text"  disabled=true/></li>
            
        </div>
        <div class="control-group">
            <label class="control-label">验证码：</label>
            <div class="controls">
                <input name="verifycode" type="text" class="input-text" id="verifycode" size="4"   onChange="chackVerifycode(this.value);"/>
                <img class="vmiddle" id="card_image2" src="/account/checkcode.htm" width="70" height="30"/><a href="#" onClick="refreshw('card_image2');return false;"> 看不清？换一个</a>
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
<script>

function checkCardType(id){

	if('cardNoOld'==id){
		$("#cardNoNew").val("");
		$("#cardPassword").val("");
		$("#cardNoOld").attr("disabled",false);
		$("#cardNoNew").attr("disabled",true);
		$("#cardPassword").attr("disabled",true);
	}
	
	if('cardNoNew'==id){
		$("#cardNoOld").val("");
		$("#cardNoNew").attr("disabled",false);
		$("#cardPassword").attr("disabled",false);
		$("#cardNoOld").attr("disabled",true);
	}
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
	}
	
	if('cardNoNewRadio'==checkType){
		$.post("/ajax/chackCodeNoPassword.do",{"cardNo":userCardNo,"cardPassword":userCardpassword},function(dt){
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
}
function storecardpayOKButton(){
	var address = "/pay/ticket-${order.mainProduct.productId}-${order.orderId}";
	window.location.href = address;
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
							storecardpayOKButton();
						}
					});   
				}else{
					pandora.dialog({
						title: "储值支付",
						content: "储值卡支付成功！<br/><strong><b>注:</b>您的订单还未支付完成，请使用在线支付继续支付</strong>",
						okClassName:"pbtn-orange",
						okValue: "确认",
						ok: function () {
							storecardpayOKButton();
						}
					});
				}
			}else{
				alert(data.msg);
				storecardpayOKButton();
			}
		}
	);
}
</script>
</div>
<div class="bgDiv"></div>
