<!--bounus start-->
<div class="cardsDeposit">
  	<h3 id="h3CloseBonus" class="h3Close" ><em></em>奖金账户支付</h3>
	<ul id="bonusDetail" class="DepositTop" style="display:none">
  		<@s.if test='moneyAccount.valid=="Y"'>
		  		<@s.if test="order.bonusPaidAmount!=null&&order.bonusPaidAmount>0">
	  				<li>
		  				<p>
					  		您已经使用奖金支付了<em>&yen; ${order.bonusPaidAmount/100}</em>，同一个订单只允许使用一次奖金余额支付。
						</p>
					</li>
				</@s.if>
				<@s.else>
					<@s.if test="moneyAccount.bonusBalance==0">
		  				<li>
			  				<p>
						  		您的奖金账户余额为0，无法使用奖金抵扣
							</p>
						</li>
		  			</@s.if>
		  			<@s.elseif test="moneyAccount.bonusBalance>0&&actBonus==0">
		  				<li>
			  				<p>
						  		当前产品不支持使用奖金抵扣或您所预订的日期不能使用奖金抵扣
						  	</p>	
						  	<p>
						  		备注：有些产品因不同预订日期以及数量，会导致可以或不能使用奖金的情况发生。所以是否可使用奖金请以当前页面是否可使用为准，其它页面的提示仅作参考
							</p>
						</li>
		  			</@s.elseif>
		  			
		  			<@s.elseif test="moneyAccount.bonusBalance>0&&bonus>0">
		  				<li>
							<p>
								您的奖金账户余额为<em>&yen; ${moneyAccount.bonusBalanceYuan}</em>，当前订单最高可用奖金支付
								<em>&yen; ${useBonusYuan}</em>，实际可用<em>&yen; ${actUseBonusYuan}</em>
							</p>
							<p >
								是否使用奖金支付 
								<input type="radio" style="width:13px;height:13px;margin:0" value="Y" name="useBonusRO" id="bonusY" /> 是   
								<span style="margin-left:5px;color:#333;">
									<input type="radio" style="width:13px;height:13px;margin:0" checked="checked" value="N" name="useBonusRO" id="bonusN"/> 否
								</span>
								<span style="margin-left:35px;">
									<input type="button" id="bonusPayBtn"  value="确认支付" class="button" style="display:none;cursor:pointer" />
								</span>
							</p>
						</li>
						<script type="text/javascript">
							var status=true;
							$(document).ready(function() {
							
								$('#bonusPayOKButton').click(function(){
									var address = "/view/view.do?orderId=${order.orderId}";
									window.location.href = address;
								});
							
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
								
								//奖金支付
								$('#bonusPayBtn').click(function(){
									if(!status){
										return;
									}
									status=false;
									var pay_url="${constant.paymentUrl}pay/bonusValidateAndPay.do${paymentParamsCashAccount}&orderId=${order.orderId}&userNo=${order.userId}&bonus=${actBonus}&jsoncallback=?";
									$('#bonusPayBtn').val("正在支付...");
									$.getJSON(pay_url,function(data){
											$('#bonusPayBtn').val("确认支付");
											if(data.success){
												$('.bgDiv').show();	
												$("#bonusPaySuccessTips").show();
											}else{
												alert(data.msg);
											}
										}
									);
								});
							});
						</script>
		  			</@s.elseif>
				</@s.else>
		</@s.if>
		<@s.else>
			<font color="#FF0000">亲爱的用户，系统认为您的帐户存在风险，为了您的账户安全，系统已将您的账户冻结，请立即致电客服10106060。</font>
		</@s.else>
	 </ul>
</div>
<div class="settingSuccess settingSuccessW" id="bonusPaySuccessTips">
	<h3><s class="close" data-biaoshi="settingSuccess"></s>奖金账户支付</h3>
    <p class="textPrompt"><span><s class="icon4"></s>奖金账户余额付款成功！</span>
    <p class="ClickButton"><input type="button" id="bonusPayOKButton"  data-biaoshi="settingSuccess" value="确认" /></p>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#h3CloseBonus").click(function(){
			if($(this).hasClass('h3CloseB')){
				$(this).removeClass('h3CloseB');
				$('#bonusDetail').hide();
			}else{
				$(this).addClass('h3CloseB');
				$('#bonusDetail').show();
			}								
		});
	});
</script>