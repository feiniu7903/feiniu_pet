<!-- 奖金账户支付 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="bounusPayDiv" class="hide">
    <p>您的奖金账户可支付余额 <span class="dfn">${moneyAccount.bonusBalanceYuan}</span> 元，用奖金账户余额付款  本次支付将使用 <span class="dfn">${bounusPayAmountYuan }</span> 元</p>
    <div class="dot_line"></div>
    <div class="form-hor form-inline form-w">
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <button class="pbtn pbtn-orange affirmBonusPay">确认支付</button>
            </div>
        </div>
    </div>
    
    <div class="form-hor form-inline form-w">
    	 <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <p><a href="#" data-dismiss="dialog">选择其他方式付款</a></p>
            </div>
        </div>
    </div>
</div>

<scrip type="text/javascript">
	$("#affirmBonusPay").click(function(){
		if(this.checked){
			pandora.dialog({
				title: "奖金支付",
				content: "是否使用奖金支付 ？",
				okClassName:"pbtn-orange",
				okValue: "确认支付",
				ok: function () {
					bonusPayBtn();
				},
				cancel: true
			});
		}
	});
	
	//奖金支付
	function bonusPayBtn(){
		var pay_url="${constant.paymentUrl}pay/bonusValidateAndPay.do${paymentParamsCashAccount}&orderId=${orderId}&userNo=${user.userId}&bonus=${bounusPayAmountFen}&jsoncallback=?";
		$.getJSON(pay_url,function(data){
			if(data.success){
					pandora.dialog({
						title: "奖金账户支付",
						content: "奖金账户余额付款成功！",
						okClassName:"pbtn-orange",
						okValue: "确认",
						ok: function () {
							payOKButton();
						},
					});
			}else{
				$.alert(data.msg);
			}
		});
	}
	
</scrip>