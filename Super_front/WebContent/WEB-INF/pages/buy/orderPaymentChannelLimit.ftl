<div class="onlinepay-detail">
    	<ul class="tag-menus">
        	<li class="currentli"><a>在线支付</a></li>
        </ul>
        <div id="tagcontent">
        	<!--========= 在线支付 S ===========-->
        	<@s.if test='order.paymentChannel=="BOC_INSTALMENT"'>
        	   <#include "/WEB-INF/pages/buy/orderPaymentBocInstalment.ftl"/> 
        	</@s.if>
        	<@s.else>
      		<div class="pay_tagcontent1">
                 <ul class="user-add-pic">
					<@s.if test='order.paymentChannel=="ALIPAY"'>
					 <li>
                      <p class="tit-payway tit-payway2">支付平台支付</p>
                      <div class="ico-payway">                      	
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipay')"><img alt="支付宝" src="http://pic.lvmama.com/img/bank/alipay.gif"></label>
                          <span class="bankExplain">支付宝：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                      </div>
                    </li>
					</@s.if>
					
					<@s.if test='order.paymentChannel=="CMB"'>
                	<li>
                		<p class="tit-payway tit-payway2">银行卡支付：</p>									
						<div class="ico-payway">
                        	<label><input type="radio" name="banks" flag="beforeSubmitGateway"  clickEvent="beforeSubmitGateway(this,'cmb')" value="cib"><img alt="中国招商银行" src="http://pic.lvmama.com/img/bank/zhaoh.gif"></label>
                        </div>	
                     </li>				
					</@s.if>
					<@s.if test='order.paymentChannel=="SPDB"'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'spdb')"><img alt="上海浦东发展银行" src="http://pic.lvmama.com/img/bank/spdb.gif"></label> 
 							</div> 
						</li>
					</@s.if>
					<@s.if test='order.paymentChannel=="ICBC_INSTALMENT"'>
						<@s.if test='order.isCanICBCInstalment()'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" class="radio_style_add instalment-banks" name="instalmentBanks" value="icbcInstalment" checked="checked">
								<i class="bank icbc" title="工商银行分期"></i></label>

								<ul class="instalment_bankinfo_icbc" style="display:block">
					      		<li>
									<label class="label_name">中国工商银行分期支付</label>
									<span class="add_info_list"></span>
						      	</li>
								<li>
									<label class="label_name">期&nbsp;&nbsp;&nbsp;&nbsp;数：</label>
									<label class="lable_input_add"><input type="radio" value="6" class="radio_style instalment-plans-cmbICBC" id="instalmentPlansICBC" name="instalmentPlansICBC" checked="checked">6期</label>
						      	</li>
						      	<li>
									<label class="label_name">还款规则：</label>
									<div class="list_text"><span class="span_mt" style="margin-top: 5px;color:red">0利息、0利率、0手续费</span></div>
						      	</li>
								</ul>
 							</div>
						</li>
						</@s.if>
						<@s.else>
							<li> <p class="tit-payway tit-payway2" style="color:red">该订单支付金额小于中国工商银行分期最低支付限制,无法支付.(中国工商银行分期要求支付金额大于等于600元)</p> </li>
						</@s.else>
					</@s.if>
					<@s.if test='order.paymentChannel=="UNIONPAY"'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'unionpay')"><img alt="中国银联" src="http://pic.lvmama.com/img/bank/unionpay.gif"></label> 
 							</div> 
						</li>
					</@s.if>
					<@s.if test='order.paymentChannel=="COMM"'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'comm')"><img alt="中国交通银行" src="http://pic.lvmama.com/img/bank/jiaoh.gif"></label> 
 							</div> 
						</li>
					</@s.if>
					<@s.if test='order.paymentChannel=="BOC"'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'boc')"><img alt="中国银行" src="http://pic.lvmama.com/img/bank/zhongh.gif"></label>
 							</div> 
						</li>
					</@s.if>
					<@s.if test='order.paymentChannel=="ALIPAY_CEBBANK"'>
                		<li> 
 							<p class="tit-payway tit-payway2">银行卡支付：</p> 
 							<div class="ico-payway"> 
 								<label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CEBBANK')"><img alt="中国光大银行" src="http://pic.lvmama.com/img/bank/guangd.gif"></label>
 							</div> 
						</li>
					</@s.if>
					<@s.elseif test='order.paymentChannel=="ONE_CITY_ONE_CARD"'>
 					<li>
                      <p class="tit-payway tit-payway2">便利卡/积分卡支付</p>
                      <div class="ico-payway">
                          <label><input type="radio" name="banks"  flag="beforeSubmitGateway" clickEvent="beforeSubmitGateway(this,'oneCityOneCard')" value="oneCityOneCard"><img alt="新华一城卡" src="http://pic.lvmama.com/img/bank/xinh.gif"></label>
                      </div>
                    </li>
					</@s.elseif>
					<@s.if test='order.paymentChannel=="ALIPAY_APP"||order.paymentChannel=="ALIPAY_WAP"'>
                		<li> 
 							您只能通过手机支付宝支付
						</li>
					</@s.if>
                    <li style="border:1px solid #FFDFA6;background-color:#FFF8D8;padding:10px;">如何进行大额支付：<br />请将款项先充值到支付宝，用支付宝的余额支付。</li>
                </ul>
                <div class="func-btn">
                <input type="hidden" id="isOpenPartPay" value="<@s.property value="order.isSubpenPay()"/>"/>
		       <input type="hidden" id="oughtPayYuan" value="${order.oughtPayYuan}"/>
		        <input type="hidden" id="maxPayMoneyYuan" value="${moneyAccount.maxPayMoneyYuan}"/>
		       <input type="hidden" id="payDepositYuan" value="${order.payDepositYuan}"/>
		       <input type="hidden" id="paymentStatus" value="${order.paymentStatus}"/>
		       <input type="hidden" id="actualPayYuan" value="${order.actualPayFloat}"/>
                <@s.if test="order.isSubpenPay()">
		       <p style="text-align:left;"><span>本次支付金额：</span>
		      
		       <input type="text" id="payYuan" value="${order.oughtPayYuanFloat}" style="text-indent:0;line-height:16px;background:#fff !important;border:1px solid #000000;color:#ff5500;background:none;height:16px;padding:3px;float:left;margin-right:10px;width:100px;" />
		       <span>元</span>
		       <span id="PAY" style="display:block"><strong style="font-weight:100;color:#f00;margin-left:10px;;font-weight:bold">金额较大时,你可以修改金额并分多次支付.</strong></span>
		       <span style="font-weight:100;color:#f00;margin-left:10px;font-weight:bold" id="PAY1"></span>
		       </p>
              </@s.if>
              <@s.else>
               	<input type="hidden" id="payYuan" value="${order.oughtPayYuanFloat}"/>
			  </@s.else>
			  
			  <p style="clear: both; margin-bottom: 5px;"></p>
              <p>

				<@s.if test='order.paymentChannel=="ICBC_INSTALMENT"'>
					<li><input type="submit" class="next instalment-next" value="下一步"></li>
				</@s.if>
				<@s.else>
					<@s.if test='order.paymentChannel!="ALIPAY_APP"&&order.paymentChannel!="ALIPAY_WAP"'>
						<input type="submit" value="下一步" class="next" onClick="selectBanks(this)"/>
					</@s.if>
				</@s.else>
             </p></div>
            </div>
            </@s.else>
            <!--========= 在线支付 E ===========-->        	 
         </div>
  </div>