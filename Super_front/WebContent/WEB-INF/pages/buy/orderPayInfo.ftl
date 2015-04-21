<div class="onlinepay-detail">
      <ul class="tag-menus">
          <li class="currentli"><a>在线支付</a></li>
          <@s.if test='order.isCanInstalment()'>
          <li class="xh-bz" id="instalmentPayment" style="display:none"><a>分期支付</a><b class="ico-price_pre"></b></li>
          </@s.if>
          <!--
          -->
          <li id="telPayment" style="display:none"><a>电话支付</a></li>
          <li id="offlinePayment" style="display:none"><a>线下支付</a></li>
      </ul>
      <div id="tagcontent">
          <!--========= 在线支付 S ===========-->
          <div class="pay_tagcontent1" style="display: block;">
              <ul class="user-add-pic">
                  <li>
                      <p class="tit-payway tit-payway2">网上银行支付</p>
                      <div style="display:block;" class="ico-payway">
                      	  <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'icbc')"><img alt="中国工商银行" src="http://pic.lvmama.com/img/bank/gongh.gif"/></label>
                          <!--<label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'ICBCB2C')"><i class="bank icbc" title="中国工商银行"></i></label>-->
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CMB')"><i class="bank cmb" title="中国招商银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CCB')"><i class="bank ccb" title="中国建设银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'BOCB2C')"><i class="bank boc" title="中国银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'ABC')"><i class="bank abc" title="中国农业银行"></i></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank','COMM')"><i class="bank comm" title="中国交通银行"></i></label>
					 	  <!-- <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'comm')"><img src="http://s1.lvjs.com.cn/img/bank/jiaoh.gif" alt="中国交通银行" /></label>-->
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CEBBANK')"><i class="bank cebbank" title="中国光大银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'SPDB')"><i class="bank spdb" title="浦发银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'GDB')"><i class="bank gdb" title="广发银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CITIC')"><i class="bank citic" title="中信银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CIB')"><i class="bank cib" title="兴业银行"></i></label>
                          <!--<label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'SDB')"><i class="bank sdb" title="深圳发展银行"></i></label>-->
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'CMBC')"><i class="bank cmbc" title="中国民生银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'BJBANK')"><i class="bank bjbank" title="北京银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'HZCBB2C')"><i class="bank hzcbb2c" title="杭州银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'SHBANK')"><i class="bank shbank" title="上海银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'BJRCB')"><i class="bank bjrcb" title="北京农村商业银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'SPABANK')"><i class="bank spabank" title="平安银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'FDB')"><i class="bank fdb" title="富滇银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'NBBANK')"><i class="bank nbbank" title="宁波银行"></i></label>                          
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'POSTGC')"><i class="bank postgc" title="中国邮政储蓄银行"></i></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'ningboBank', 'WZCBB2C-DEBIT')"><i class="bank wzcbb2c-debit" title="温州银行"></i></label>
						  <!--label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'HXB')"><img src="http://pic.lvmama.com/img/bank/huax.gif" alt="华夏银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'HKBEA')"><img src="http://pic.lvmama.com/img/bank/dongy.gif" alt="东亚银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'CBHB')"><img src="http://pic.lvmama.com/img/bank/boh.gif" alt="渤海银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'GZCB')"><img src="http://pic.lvmama.com/img/bank/guangz.gif" alt="广州银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'SHRCB')"><img src="http://pic.lvmama.com/img/bank/shangn.gif" alt="上海农村商业银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'NJCB')"><img src="http://pic.lvmama.com/img/bank/nanj.gif" alt="南京银行" /></label>-->
                      </div>
                  </li>
                  
				  <!--
                  <li>
                      <p class="tit-payway tit-payway2">合作银行专区</p>
                      <div class="ico-payway">
                         <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'cmb')"><img alt="中国招商银行" src="http://pic.lvmama.com/img/bank/zhaoh.gif"></label>
                         <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'boc')"><img alt="中国银行" src="http://pic.lvmama.com/img/bank/zhongh.gif"></label>
                         <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'comm')"><img alt="中国交通银行" src="http://pic.lvmama.com/img/bank/jiaoh.gif"/></label>
                         <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'icbc')"><img alt="中国工商银行" src="http://pic.lvmama.com/img/bank/gongh.gif"/></label>
                      </div>
                  </li>
                  -->
                  <li>
                      <p class="tit-payway tit-payway2">支付平台支付</p>
                      <div class="ico-payway">
					  	<p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'tenpay')"><i class="bank tenpay" title="财付通"></i></label><span class="bankExplain">腾讯旗下专业的在线支付平台。财付通，会支付，会生活！</span>
                        </p>
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'tenpayDirectpay')"><i class="bank tenpay2" title="财付通快捷"></i></label><span class="bankExplain">财付通快捷</span>
                        </p>
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'weixinWeb')"><i class="bank weixinpay" title="微信扫码"></i></label><span class="bankExplain">微信扫码支付</span>
                        </p>
                      	<p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipay')"><i class="bank alipay" title="支付宝"></i></label><span class="bankExplain">支付宝：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipayScannerCode')"><i class="bank alipayScannerCode" title="支付宝扫码"></i></label><span class="bankExplain">支付宝扫码支付：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                      	<p id="alipayDirectpayIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipayDirectpay')"><i class="bank alipayDirectpay" title="支付宝快捷"></i></label><span class="bankExplain">支付宝快捷支付——安全 方便 快捷！</span>
                        </p>
                      	<p id="unionpayIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'unionpay')"><i class="bank unionpay" title="银联"></i></label><span class="bankExplain">银联 </span>
                        </p>
                      	<p id="unionpayDirectIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'unionpayDirect')"><i class="bank unionpay" title="银联快捷"></i></label><span class="bankExplain">银联快捷 </span>
                        </p>
						<p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'chinaMobile')"><i class="bank chinaMobile" title="中国移动手机支付"></i></label><span class="bankExplain">中国移动手机支付 </span>
                        </p>
                        
                      </div>
                      
                  </li>
                 
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
				<input type="submit" value="下一步" class="next" onClick="selectBanks(this)"/>
				&nbsp;&nbsp;&nbsp;<a class="status detail-link" href="javascript:void(0);" id="showMoreGatewayLink">显示更多银行</a>
			  </p>
			  </div>
              </div>
          <!--========= 在线支付 E ===========-->
           
          <!--========= 分期支付 S ===========-->
          
          <@s.if test='order.isCanInstalment()'>
           <div class="pay_tagcontent1" style="display: none;">
		      <div class="pay_tagcontent2 display-none pay_tagcontent3" style="display: block;"> 
		      <img src="http://pic.lvmama.com/img/order/fenqi.jpg" width="872" height="124"> <br class="clear"/>
		        <div class="fenqi_contant">
		          <h3 class="font_style">本产品支持以下银行信用卡分期付款：</h3>
		          <div class="fenqi_info">
		            <ul class="fenqi_list">
					<li>
						<label class="label_name other" style="margin-top: 6px">银&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行：</label>
						<@s.if test='order.isCanCMBInstalment()'>
						<label><input type="radio" class="radio_style_add instalment-banks" name="instalmentBanks" value="cmbInstalment" checked="checked">
						<i class="bank cmb" title="招商银行分期"></i></label>
						</@s.if>
						<#--
						<@s.if test='order.isCanICBCInstalment()'>
						<label><input type="radio" class="radio_style_add instalment-banks" name="instalmentBanks" value="icbcInstalment" checked="checked">
						<i class="bank icbc" title="工商银行分期"></i></label>
						</@s.if>
						-->
					</li>
			      	<li>
			      		<ul class="instalment_bankinfo" style="display:block">
			      		<li>
							<label class="label_name">分期方式：</label>
							<span class="add_info_list">在线分期(立即得知审核结果)</span>
				      	</li>
						<li>
							<label class="label_name">期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</label>
							<label class="lable_input_add"><input type="radio" class="radio_style instalment-plans-cmb" value="3" id="instalmentPlans" name="instalmentPlans" data-arrayPay="3,0.027,2.7%"  checked="checked">3期(手续费2.7%)</label>
							<label class="lable_input_add"><input type="radio" class="radio_style instalment-plans-cmb" value="6" id="instalmentPlans" name="instalmentPlans" data-arrayPay="6,0.045,4.5%">6期(手续费4.5%)</label>
							<label class="lable_input_add"><input type="radio" class="radio_style instalment-plans-cmb" value="12" id="instalmentPlans" name="instalmentPlans" data-arrayPay="12,0.066,6.6%">12期(手续费6.6%)</label>
				      	</li>
						<li>
							<label class="label_name">还款规则：</label>
							<div class="list_text"><span>第一期<em id="downPayment">&yen;${instalmentInfoMap.downPayment}</em>，以后每期<em id="averagePayment">&yen;${instalmentInfoMap.averagePayment}</em>，总计<em id="totalPayment">&yen;${instalmentInfoMap.totalPayment}</em></span> <span class="span_mt">注：当总金额不能被分期的期数整除时，具体每期还款金额以银行计算为准</span></div>
				      	</li>
			         	</ul>
			         	
			         	<#--
			         	<ul class="instalment_bankinfo_icbc" style="display:block">
			      		<li>
							<label class="label_name">分期方式：</label>
							<span class="add_info_list">在线分期(立即得知审核结果)</span>
				      	</li>
						<li>
							<label class="label_name">期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</label>
							<label class="lable_input_add"><input type="radio" value="6" class="radio_style instalment-plans-cmbICBC" id="instalmentPlansICBC" name="instalmentPlansICBC" checked="checked">6期</label>
				      	</li>
				      	<li>
							<label class="label_name">还款规则：</label>
							<div class="list_text"><span class="span_mt" style="margin-top: 0px;color:red">0利息、0利率、0手续费</span></div>
				      	</li>
			         	</ul>
			         	-->
					</li>
			      	<li><input type="submit" class="next instalment-next" value="下一步"></li>
		            </ul>
		          </div>
		        </div>
		        <div class="fenqi_bottom_text">
		                <h3 class="font_style">银行信用卡分期付款常见问题：</h3>
		                <dl>
		                	<dd class="fenqi_text_titile">1.什么是银行信用卡在线分期？</dd>
		                    <dt class="fenqi_text">答：在线分期支付是部分银行提供的信用卡持卡人网上分期付款结算的平台，通过这个平台，银行信用卡持卡人可以在驴妈妈进行购物时，可以选择将购买商品的总价平均分成
		  3期、6期、9期、12期进行支付，持卡人再根据信用卡账单按时偿还每期（月）款项。</dt>
		                  	<dd class="fenqi_text_titile">2.如何开通银行信用卡在线分期支付功能？</dd>
		                    <dt class="fenqi_text">  答：银行信用卡在线分期支付功能需要您提前开通信用卡网上支付功能，并调整您的网上支付限额，请确保您所购产品金额不超过网上支付限额，分期支付才能成功。</dt>
		                                  	<dd class="fenqi_text_titile">3.信用卡分期付款购物需要满足什么条件吗？</dd>
		                    <dt class="fenqi_text">    答：信用卡分期需满足订单金额在500元以上（含500元）方可申请分期付款。</dt>
		                </dl>
		        </div>
		        </div>
		      </div>
      </@s.if>	  
      	 <!-- --> 
      <!-- ========= 分期支付E ===========-->
      
     <!--========= 信用卡电话支付 S ===========-->
          <div class="pay_tagcontent2 display-none pay_tagcontent3" style="display: none;">
              <img src="http://pic.lvmama.com/img/super_v2/pay_kefu.gif">
              <p class="add_order">
                  请致电&nbsp;<em><@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></em>，告知客服您的订单号，由客服人员帮您转入语音系统进行电话支付。一个电话就可以完成支付，安全、快捷。<br>
              </p>
			<!--20120612增加支付方式 -->
              <div class="add_order_list" style="border-top:1px solid #D50976;padding:0;margin-left:95px;">
                  <h3>电话支付<span style="font-size:12px;font-weight:500;">(可通过手机按键方式输入银行卡信息进行交易，方便在产品资源紧张时有效锁定资源，顺利完成支付)：</span></h3>
			      <p class="add_order1">以下银行的<em>借记卡</em>，支持电话支付，无需开通网银也能支付：<em>（单笔及日累计额度均为4万）</em></p>
                  <div style=" overflow:hidden; clear:both; zoom:1;">
					<i class="bank ccb" title="中国建设银行"></i>
					<i class="bank abc" title="中国农业银行"></i>
					<i class="bank cebbank" title="中国光大银行"></i>
					<i class="bank shbank" title="上海银行"></i>
					<i class="bank spabank" title="平安银行"></i>
					<i class="bank citic" title="中信银行"></i>
					<i class="bank spdb" title="浦发银行"></i>
					<i class="bank cib" title="兴业银行"></i>
					<i class="bank gdb" title="广发银行"></i>
					<i class="bank comm" title="中国交通银行"></i>
					<i class="bank postgc" title="中国邮政储蓄银行"></i>
					<i class="bank cmbc" title="中国民生银行"></i>
					<i class="bank bjbank" title="北京银行"></i>
                  </div>
                  
                  <p class="add_order1" style="overflow:hidden; clear:both;">以下银行的<em>信用卡</em>，支持电话支付，无需开通网银也能支付：<em>（单笔及日累计额度均为4万）</em></p>
                  <div style=" overflow:hidden; clear:both; zoom:1;">
                  	<i class="bank ccb" title="中国建设银行"></i>
                  	<i class="bank abc" title="中国农业银行"></i>
                  	<i class="bank cebbank" title="中国光大银行"></i>
                  	<i class="bank shbank" title="上海银行"></i>
                  	<i class="bank spabank" title="平安银行"></i>
                  	<i class="bank citic" title="中信银行"></i>
                  	<i class="bank spdb" title="浦发银行"></i>
                  	<i class="bank cib" title="兴业银行"></i>
                  	<i class="bank gdb" title="广发银行"></i>
                  	<i class="bank comm" title="中国交通银行"></i>
                  	<i class="bank postgc" title="中国邮政储蓄银行"></i>
                  	<i class="bank cmbc" title="中国民生银行"></i>
                  	<i class="bank boc" title="中国银行"></i>
					<i class="bank cmb" title="招商银行"></i>
					<i class="bank icbc" title="工商银行"></i>
					<img src="http://pic.lvmama.com/img/bank/huax.gif" alt="华夏银行" />
                  </div>
              </div>
	       <!--20120612增加支付方式 end-->
			  
              <div class="add_order_list">
                  <h3>如何进行电话支付：</h3>
                  <img src="http://pic.lvmama.com/img/super_v2/order_add_dem.jpg">
              </div>
          </div>
          <!--========= 信用卡电话支付 E ===========-->

          <!--========= 线下支付 S ===========-->
          <div class="pay_tagcontent4 display-none" style="display: none;">
          <img alt="拉卡拉-便利支付-便利生活" src="http://pic.lvmama.com/img/super_v2/lakala_logo.gif">
          <div class="lkl-dec">拉卡拉：不用网银也能支付！支持所有银行卡刷卡支付！</div>
          <span class="lkl-btn"><a target="lvmamaPay" href="${constant.paymentUrl}pay/lakala.do?objectId=${order.orderId}&amount=${order.oughtPayFenLong}&objectType=${objectType}&paymentType=${paymentType}&bizType=${bizType}&signature=${signature}">获得拉卡拉账单号</a></span>
          <p><span>温馨提示：由于订单有效期为${order.waitPayment?if_exists}分钟，请谨慎选择线下拉卡拉刷卡机支付。</span><a target="_blank" href="http://www.lakala.com/bzzx_cjwt.html">如何使用拉卡拉？</a><a target="_blank" href="http://map.lakala.com/">哪里有拉卡拉？</a></p>
          </div>
          <!--========= 线下支付 E ===========-->
       </div>
</div>

<script type="text/javascript"> 
       $(function(){
              $(".tit-payway").click(function(){
                  $(this).siblings(".ico-payway").slideDown(400,function(){
                      $(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-down.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
                  })
                  .end().parent().siblings().children(".ico-payway").slideUp(400,function(){
                      $(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-right.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
       
                  });
              })
			  $("#showMoreGatewayLink").click(function (){
			  	$("#instalmentPayment").show();
				$("#telPayment").show();
				$("#offlinePayment").show();				
				$("#alipayDirectpayIcon").show();
				$("#unionpayIcon").show();
				$("#unionpayDirectIcon").show();
				$("#showMoreGatewayLink").hide();
			  })
      });
</script>
