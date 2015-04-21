<div class="onlinepay-detail">
      <ul class="tag-menus">
          <li class="currentli"><a>在线支付</a></li>
      </ul>
      <div id="tagcontent">
          <!--========= 在线支付 S ===========-->
          <div class="pay_tagcontent1" style="display: block;">
              <ul class="user-add-pic">
                  <li>
                      <p class="tit-payway tit-payway2">网上银行支付</p>
                      <div style="display:block;" class="ico-payway">
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'ICBCB2C')"><i class="bank icbc" title="中国工商银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CMB')"><i class="bank cmb" title="中国招商银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CCB')"><i class="bank ccb" title="中国建设银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'BOCB2C')"><i class="bank boc" title="中国银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'ABC')"><i class="bank abc" title="中国农业银行"></i></label>
			              <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank','COMM')"><i class="bank comm" title="中国交通银行"></i></label>
			              <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CEBBANK')"><i class="bank cebbank" title="中国光大银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'SPDB')"><i class="bank spdb" title="浦发银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'GDB')"><i class="bank gdb" title="广发银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CITIC')"><i class="bank citic" title="中信银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CIB')"><i class="bank cib" title="兴业银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'CMBC')"><i class="bank cmbc" title="中国民生银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'BJBANK')"><i class="bank bjbank" title="北京银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'HZCBB2C')"><i class="bank hzcbb2c" title="杭州银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'SHBANK')"><i class="bank shbank" title="上海银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'BJRCB')"><i class="bank bjrcb" title="北京农村商业银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'SPABANK')"><i class="bank spabank" title="平安银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'FDB')"><i class="bank fdb" title="富滇银行"></i></label>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'NBBANK')"><i class="bank nbbank" title="宁波银行"></i></label>                          
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'POSTGC')"><i class="bank postgc" title="中国邮政储蓄银行"></i></label>
						  <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'ningboBank', 'WZCBB2C-DEBIT')"><i class="bank wzcbb2c-debit" title="温州银行"></i></label>

                      </div>
                  </li>
				  <!--
                  <li>
                      <p class="tit-payway tit-payway2">合作银行专区</p>
                      <div class="ico-payway">
                         
                         <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'cmb')"><img alt="中国招商银行" src="http://pic.lvmama.com/img/bank/zhaoh.gif"></label>
                        
                         <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'boc')"><img alt="中国银行" src="http://pic.lvmama.com/img/bank/zhongh.gif"></label>
                         <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'comm')"><img src="http://pic.lvmama.com/img/bank/jiaoh.gif" alt="中国交通银行" /></label>
                        
                      </div>
                  </li>
				   -->
                  <li>
                      <p class="tit-payway tit-payway2">支付平台支付</p>
                      <div class="ico-payway">
                      	<p>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'alipay','')"><i class="bank alipay" title="支付宝"></i></label><span class="bankExplain">支付宝：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                        <p>
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'alipayScannerCode','')"><i class="bank alipayScannerCode" title="支付宝扫码"></i></label><span class="bankExplain">支付宝扫码支付：中国最大的第三方电子支付平台。网上支付 安全快速！</span>
                        </p>
                      	<p id="alipayDirectpayIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'alipayDirectpay','')"><i class="bank alipayDirectpay" title="支付宝快捷"></i></label><span class="bankExplain">支付宝快捷支付——安全 方便 快捷！</span>
                        </p>
                      	<p id="unionpayIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'unionpay','')"><i class="bank unionpay" title="银联"></i></label><span class="bankExplain">银联 </span>
                        </p>
                      	<p id="unionpayDirectIcon" style="display:none">
                          <label><input type="radio" name="banks" clickEvent="gotoMergePay(this,'unionpayDirect','')"><i class="bank unionpay" title="银联快捷"></i></label><span class="bankExplain">银联快捷 </span>
                        </p>
                      </div>
                  </li>
              </ul>           
              <div class="func-btn">
				<p style="text-align:left;"><span style="font-weight:100;color:#f00;margin-left:10px;font-weight:bold" id="PAY1"></span></p>
				<p style="clear: both; margin-bottom: 5px;"></p>
				<p>
					<input type="submit" value="下一步" class="next" onClick="selectBanks(this)"/>
					&nbsp;&nbsp;&nbsp;<a class="status detail-link" href="javascript:void(0);" id="showMoreGatewayLink">显示更多银行</a>
				</p>
				</div>
              </div>
       </div>
</div>
<script type="text/javascript"> 
       $(function(){
			  $("#showMoreGatewayLink").click(function (){
				$("#alipayDirectpayIcon").show();
				$("#unionpayIcon").show();
				$("#unionpayDirectIcon").show();
				$("#showMoreGatewayLink").hide();
			  })
      });
</script>
