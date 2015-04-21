<script>
jQuery(function($){
	function layer_xy(obj){
		var windows = $(window);
		 var ctop = (windows.height() - obj.height())/2-90;
		 var cleft = (windows.width() - obj.width())/2;
		 if(ctop<=0){ctop = 0 + windows.scrollTop()}else{ctop=parseInt(ctop + windows.scrollTop())};
		 if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
		 obj.css({"top":ctop +50 + "px","left":cleft + "px"});
		}
	var sent_btn = $(document).find("input[name='useAccount']");
	var float_layer = $("#float_layer");
	var float_bg = $("#float_bg");
	var close_btn = $("#close_btn");
	sent_btn.click(function(){
							if (document.getElementById('useAccount').checked){
							    <@s.if test="moneyAccount.maxPayMoneyYuan >= order.oughtPayYuanFloat">
								layer_xy(float_layer);
								float_bg.css({"display":"block"});
								float_layer.fadeIn("fast");		
								</@s.if>								
							}
							})
	close_btn.click(function(){
							float_bg.css({"display":""});
							float_layer.fadeOut("fast");
							$("#useAccount").attr("checked","");
							 })
	})
</script>

<div id="float_layer" class="fl-layer">
	<span><img src="http://pic.lvmama.com/img/myspace/close_dot.gif" alt="关闭" id="close_btn" class="close-btn" /></span>
    <div>
    <h3>本笔订单共需支付&yen;<s>${order.oughtPayYuanFloat}</s></h3>
           用存款账户支付&yen;${order.oughtPayYuanFloat}，您的账户余额为 &yen;<strong>${moneyAccount.maxPayMoneyYuan}</strong> 
    <br />
    <p style="text-align:center;margin-top:15px;">
    	<form id="accountpayform" method="post" action="/view/accountpay.do?orderId=${order.orderId}">
		<@s.token></@s.token>
    		<img src="http://pic.lvmama.com/img/myspace/prepopbtn.gif" style="cursor:hand;" 
    			 onClick="document.getElementById('accountpayform').submit();"/>
    	</form>
    </p>
    </div>
</div>
<div id="float_bg" class="fl-bg"></div>

<div class="pay_tagcontent1" style="display: block;">
<!-- 若账户余额为0时，显示以下代码-->
<@s.if test="moneyAccount.maxPayMoney==0">
		<dl class="count-pay">
	  		<dt>您的账户余额为<em>&yen;0</em>，<a target="_blank" href="/usr/money/viewcharge.do">账户充值</a></dt>
	  		<dt>若订单金额超过网上银行最大单笔支付限额时，您可以为驴妈妈存款账户充值后一同完成支付。</dt>
		</dl>	
</@s.if>
<!-- 若有账户余额时，显示以下代码-->
<@s.else>            
			<dl class="count-pay">
	  			<dt>您的存款账户可支付余额：<em>&yen; ${moneyAccount.maxPayMoneyYuan} </em></dt><br>
	  			<#if moneyAccount.mobileNumber?if_exists>
	  				<@s.if test="moneyAccount.maxPayMoney>=order.actualPayFloat">
			  			<dt>
			  				<input id="useAccount" name="useAccount" type="checkbox" value="A" onClick="changeMoney()">
			  				<label for=useAccount> 用存款账户余额付款
			  				   <@s.if test="moneyAccount.maxPayMoneyYuan>=order.oughtPayYuanFloat">
			  						&yen; <em>${order.oughtPayYuanFloat}</em>
			  					</@s.if>	
			  					<@s.else>	  			
			  						&yen;<em> ${moneyAccount.maxPayMoneyYuan}</em>，剩余金额&yen;${order.oughtPayYuanFloat - moneyAccount.maxPayMoneyYuan}可选择“在线支付”付款。
			  					</@s.else>
			  				</label>
			  			</dt>		  		
				  	</@s.if>
				  	<@s.else>
				  		<input type="checkbox" id="useAccount" name="useAccount" value="A"/> 用账户支付&yen;${moneyAccount.maxPayMoney}，剩余&yen;${order.oughtPayYuanFloat - moneyAccount.maxPayMoney}用其它支付方式付款。
				  	</@s.else>			
				 <#else>
				 	 您的账户没有绑定手机号，请<a href="/usr/money/recharge1.do" target="_blank">绑定手机号</a>再使用现金账户支付。<br />
				 </#if>
			</dl>
</@s.else>
</div>
