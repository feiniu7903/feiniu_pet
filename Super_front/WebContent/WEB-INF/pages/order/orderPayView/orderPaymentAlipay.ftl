<div class="onlinepay-detail">
      <ul class="tag-menus">
          <li class="currentli"><a>在线支付</a></li>
          <li><a>电话支付</a></li>
          <li><a>线下支付</a></li>
      </ul>
      <div id="tagcontent">
          <!--========= 在线支付 S ===========-->
          <div class="pay_tagcontent1" style="display: block;">
              <ul class="user-add-pic">
                  <li>
                      <p class="tit-payway tit-payway2">网上银行支付</p>
                      <div style="display:block;" class="ico-payway">
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'ICBCB2C')"><img alt="中国工商银行" src="http://pic.lvmama.com/img/bank/gongh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CMB')"><img alt="中国招商银行" src="http://pic.lvmama.com/img/bank/zhaoh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CCB')"><img alt="中国建设银行" src="http://pic.lvmama.com/img/bank/jianh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'ABC')"><img alt="中国农业银行" src="http://pic.lvmama.com/img/bank/nongh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'COMM')"><img alt="中国交通银行" src="http://pic.lvmama.com/img/bank/jiaoh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CEBBANK')"><img alt="中国光大银行" src="http://pic.lvmama.com/img/bank/guangd.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'SPDB')"><img alt="浦发银行" src="http://pic.lvmama.com/img/bank/puf.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'GDB')"><img alt="广发银行" src="http://pic.lvmama.com/img/bank/guangf.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CITIC')"><img alt="中信银行" src="http://pic.lvmama.com/img/bank/zhongx.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'CIB')"><img alt="兴业银行" src="http://pic.lvmama.com/img/bank/xingy.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'HZCBB2C')"><img alt="杭州银行" src="http://pic.lvmama.com/img/bank/hangz.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'BJRCB')"><img alt="北京农村商业银行" src="http://pic.lvmama.com/img/bank/bein.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'SPABANK')"><img alt="平安银行" src="http://pic.lvmama.com/img/bank/pinga.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'FDB')"><img alt="富滇银行" src="http://pic.lvmama.com/img/bank/fud.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'alipay', 'NBBANK')"><img alt="宁波银行" src="http://pic.lvmama.com/img/bank/ningb.gif"></label>                          
                          
                      </div>
                  </li>                  
                  <li>
                      <p class="tit-payway tit-payway2">支付平台支付</p>
                      <div class="ico-payway">
                      	<p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipay')"><i class="bank alipay" title="支付宝"></i></label><span class="bankExplain">支付宝：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'alipayScannerCode')"><i class="bank alipayCode" title="支付宝扫码"></i></label><span class="bankExplain">支付宝扫码支付：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
												</p>                       
                      </div>
                  </li>                 
              </ul>              
              <div class="func-btn">
              <input type="hidden" id="oughtPayYuan" value="${order.oughtPayYuan}"/>
               <input type="hidden" id="isOpenPartPay" value="<@s.property value="order.isSubpenPay()"/>"/>
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
              <p><input type="submit" value="下一步" class="next" onClick="selectBanks(this)"/></p></div>
              </div>
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
						<i class="bank ceb" title="中国光大银行"></i>   
						<i class="bank shbank" title="上海银行"></i>
						<i class="bank pingan" title="平安银行"></i>
						<i class="bank ecitic" title="中信银行"></i>
						<i class="bank spdb" title="浦发银行"></i>
						<i class="bank cib" title="兴业银行"></i>
						<i class="bank cgb" title="广发银行"></i>
						<i class="bank comm" title="中国交通银行"></i>
						<i class="bank psbc" title="中国邮政储蓄银行"></i>
						<i class="bank cmbc" title="中国民生银行"></i>
						<i class="bank bjbank" title="北京银行"></i>
	                  </div>
	                  
	                  <p class="add_order1" style="overflow:hidden; clear:both;">以下银行的<em>信用卡</em>，支持电话支付，无需开通网银也能支付：<em>（单笔及日累计额度均为4万）</em></p>
	                  <div style=" overflow:hidden; clear:both; zoom:1;">
	                  	<i class="bank ccb" title="中国建设银行"></i>
	                  	<i class="bank abc" title="中国农业银行"></i>
	                  	<i class="bank ceb" title="中国光大银行"></i>
	                  	<i class="bank shbank" title="上海银行"></i>
	                  	<i class="bank pingan" title="平安银行"></i>
	                  	<i class="bank ecitic" title="中信银行"></i>
	                  	<i class="bank spdb" title="浦发银行"></i>
	                  	<i class="bank cib" title="兴业银行"></i>
	                  	<i class="bank cgb" title="广发银行"></i>
	                  	<i class="bank comm" title="中国交通银行"></i>
	                  	<i class="bank psbc" title="中国邮政储蓄银行"></i>
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
          });
</script>
