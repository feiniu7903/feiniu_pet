<!--bounus start-->
<div class="check-text">
	<label class="checkbox inline">
  		<@s.if test='moneyAccount.valid=="Y"'>
		  		<@s.if test="order.bonusPaidAmount!=null&&order.bonusPaidAmount>0">
		  				<p>
					  		您已经使用奖金支付了<em>&yen; ${order.bonusPaidAmount/100}</em>，同一个订单只允许使用一次奖金余额支付。
						</p>
				</@s.if>
				<@s.else>
					<@s.if test="moneyAccount.bonusBalance==0">
			  				<p>
						  		您的奖金账户余额为0，无法使用奖金抵扣
							</p>
		  			</@s.if>
		  			<@s.elseif test="moneyAccount.bonusBalance>0&&actBonus==0">
			  				<p>
						  		当前产品不支持使用奖金抵扣或您所预订的日期不能使用奖金抵扣
						  	</p>	
						  	<p>
						  		备注：有些产品因不同预订日期以及数量，会导致可以或不能使用奖金的情况发生。所以是否可使用奖金请以当前页面是否可使用为准，其它页面的提示仅作参考
							</p>
		  			</@s.elseif>
		  			<@s.elseif test="moneyAccount.bonusBalance>0&&bonus>0">
							<p><input class="input-checkbox" type="checkbox" name="ownpro" id="affirmBonusPay">您的奖金账户余额为<em>&yen; ${moneyAccount.bonusBalanceYuan}</em>，当前订单最高可用奖金支付
								<em>&yen; ${useBonusYuan}</em>，实际可用<em>&yen; ${actUseBonusYuan}</em>
							</p>
						<script type="text/javascript">
							var status=true;
							$(document).ready(function() {
								$("#bonusY").click(function(){
									if($(this).attr("checked")==true){
										$("#bonusPayBtn").show();
									}
								});
								
								$("#bonusN").click(function(){
									if($(this).attr("checked")==true){
										$("#bonusPayBtn").hide();
									}
								});
								
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
							});
							//奖金支付
							function bonusPayBtn(){
								if(!status){
									return;
								}
								status=false;
								var pay_url="${constant.paymentUrl}pay/bonusValidateAndPay.do${paymentParamsCashAccount}&orderId=${order.orderId}&userNo=${order.userId}&bonus=${actBonus}&jsoncallback=?";
								$.getJSON(pay_url,function(data){
									if(data.success){
											pandora.dialog({
												title: "奖金账户支付",
												content: "奖金账户余额付款成功！",
												okClassName:"pbtn-orange",
												okValue: "确认",
												ok: function () {
													var address = "/pay/ticket-${order.mainProduct.productId}-${order.orderId}";
													window.location.href = address;
												},
											});
									}else{
										$.alert(data.msg);
									}
								});
							}
						</script>
		  			</@s.elseif>
				</@s.else>
		</@s.if>
		<@s.else>
			<font color="#FF0000">亲爱的用户，系统认为您的帐户存在风险，为了您的账户安全，系统已将您的奖金账户冻结，请立即致电客服10106060。</font>
		</@s.else>
	 </ul>
	</label>
</div>