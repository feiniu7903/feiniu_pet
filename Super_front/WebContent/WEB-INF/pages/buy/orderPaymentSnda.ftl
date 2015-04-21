
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
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'ICBC')"><img alt="中国工商银行" src="http://pic.lvmama.com/img/bank/gongh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'CCB')"><img alt="中国建设银行" src="http://pic.lvmama.com/img/bank/jianh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'BOC')"><img alt="中国银行" src="http://pic.lvmama.com/img/bank/zhongh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'SDB')"><img alt="深圳发展银行" src="http://pic.lvmama.com/img/bank/shenz.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'CMBC')"><img alt="中国民生银行" src="http://pic.lvmama.com/img/bank/mings.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'BCCB')"><img alt="北京银行" src="http://pic.lvmama.com/img/bank/beij.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'BOS')"><img alt="上海银行" src="http://pic.lvmama.com/img/bank/shangh.gif"></label>
                          <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'PSBC')"><img src="http://pic.lvmama.com/img/bank/youz.gif" alt="中国邮政储蓄银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'HXB')"><img src="http://pic.lvmama.com/img/bank/huax.gif" alt="华夏银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'HKBEA')"><img src="http://pic.lvmama.com/img/bank/dongy.gif" alt="东亚银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'CBHB')"><img src="http://pic.lvmama.com/img/bank/boh.gif" alt="渤海银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'WZCB')"><img src="http://pic.lvmama.com/img/bank/wenz.gif" alt="温州银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'GZCB')"><img src="http://pic.lvmama.com/img/bank/guangz.gif" alt="广州银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'SHRCB')"><img src="http://pic.lvmama.com/img/bank/shangn.gif" alt="上海农村商业银行" /></label>
						  <label><input type="radio" name="banks" clickEvent="alipayWithBank(this,'shengDirectPay', 'NJCB')"><img src="http://pic.lvmama.com/img/bank/nanj.gif" alt="南京银行" /></label>
                      </div>
                  </li>                 
                  <li>
                      <p class="tit-payway tit-payway2">支付平台支付</p>
                      <div class="ico-payway">                      	
                        <p>
                          <label><input type="radio" name="banks" clickEvent="beforeSubmitGateway(this,'shengPay')"><img alt="盛付通" src="http://pic.lvmama.com/img/bank/shengft.gif"></label><span class="bankExplain">盛付通：盛大网络旗下独立的第三方电子支付平台。银行覆盖全面，支付！<br /><em><font color="red">5月23日-6月30日，用盛付通支付，即获5元现金，限量300名，先到先得，更有机会参与500元现金大奖抽取</font></em></span>
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
              <p><input type="submit" value="下一步" class="next" onClick="selectBanks(this)"/></p>
                 </div>
              </div>
          <!--========= 在线支付 E ===========-->
     <!--========= 信用卡电话支付 S ===========-->
          <div class="pay_tagcontent2 display-none pay_tagcontent3" style="display: none;">
              <img src="http://pic.lvmama.com/img/super_v2/pay_kefu.gif">
              <p class="add_order">
                  请致电&nbsp;<em><@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></em>，告知客服您的订单号，由客服人员帮您转入语音系统进行电话支付。一个电话就可以完成支付，安全、快捷。<br>
                  
              </p>
			 <p class="add_order1">
                  以下银行的<em>借记卡</em>，无需开通网银也能支付<em>（单笔额度为1万，日累计额度为1万）</em>：

                  
              </p>
			<div class="add_order_list">
					<img alt="中国工商银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/gongh.gif" />
					<img alt="中国建设银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_j.jpg" />
					<img alt="中国农业银行" src="http://pic.lvmama.com/img/bank/nongh.gif" />
					<img alt="中国光大银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_g.jpg" />   
					<img alt="上海银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/shangh.gif" />
				    <img alt="平安银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/pinga.gif">
				    <img alt="深圳发展银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/shenz.gif">
				    <img alt="中信银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/zhongx.gif">
				    <img alt="浦发银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/puf.gif" />           
					<img alt="兴业银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/xingy.gif" />
				    <img alt="广发银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_gf.jpg" />
				    <img alt="中国交通银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/jiaoh.gif" />
				    <img alt="中国邮政储蓄" width="125" height="32" src="http://s1.lvjs.com.cn/img/super_v2/order_add_y.jpg" />


			  
			  
			  </div>
			  
			   <p class="add_order_list">
                 以下银行的<em>信用卡</em>，无需开通网银也能支付<em>（单笔额度为1万，日累计额度为1万，可支付额度根据银行不同而有所差异）</em>：

                  
              </p>
			  

			  
			  <div class="add_order_list">

			    <img alt="招商银行" width="125" height="32" src="http://s3.lvjs.com.cn/img/super_v2/order_add_z.jpg">
				<img alt="中国建设银行" width="125" height="32" src="http://pic.lvmama.com/img/super_v2/order_add_j.jpg">
				<img alt="中国银行" width="125" height="32" src="http://s1.lvjs.com.cn/img/bank/zhongh.gif">
				<img src="http://pic.lvmama.com/img/bank/gongh.gif" alt="中国工商银行">
				<img src="http://pic.lvmama.com/img/bank/nongh.gif" alt="中国农业银行">
				<img alt="中国交通银行" src="http://pic.lvmama.com/img/bank/jiaoh.gif">
				<img src="http://pic.lvmama.com/img/super_v2/mings.gif" width="125" height="32">
				<img alt="中国光大银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_g.jpg" />
				<img src="http://pic.lvmama.com/img/bank/pinga.gif" alt="平安银行">
				<img src="http://pic.lvmama.com/img/super_v2/order_add_gf.jpg" width="125" height="32">
				<img src="http://pic.lvmama.com/img/super_v2/shangh.gif" width="125" height="32">
				<img src="http://pic.lvmama.com/img/super_v2/order_add_p.jpg" width="125" height="32">
				<img alt="兴业银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/xingy.gif" />
				<img alt="深圳发展银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/shenz.gif">	

			  
			  
			  </div>
			  <!--20120612增加支付方式 -->
              <div class="add_order_list" style="border-top:1px solid #D50976;padding:0;margin-left:95px;">
                  <h3>预授权电话支付<span style="font-size:12px;font-weight:500;">(可通过手机按键方式输入银行卡信息进行预授权交易，方便在产品资源紧张时有效锁定资源，顺利完成支付)：</span></h3>
			      <p class="add_order1">以下银行的<em>借记卡</em>，支持预授权电话支付，无需开通网银也能支付：<em>（单笔及日累计额度均为1万）</em></p>
					<img alt="中国工商银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/gongh.gif" />
					<img alt="中国建设银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_j.jpg" />
					<img alt="中国农业银行" src="http://pic.lvmama.com/img/bank/nongh.gif" />
					<img alt="中国光大银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_g.jpg" />   
					<img alt="上海银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/shangh.gif" />
				    <img alt="平安银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/pinga.gif">
				    <img alt="深圳发展银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/shenz.gif">
				    <img alt="中信银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/zhongx.gif">
				    <img alt="浦发银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/puf.gif" />           
					<img alt="兴业银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/xingy.gif" />
				    <img alt="广发银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_gf.jpg" />
				    <img alt="中国交通银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/jiaoh.gif" />
				    <img alt="中国邮政储蓄" width="125" height="32" src="http://s1.lvjs.com.cn/img/super_v2/order_add_y.jpg" />
                  </div>
                  
                  <p class="add_order1" style="overflow:hidden; clear:both;">以下银行的<em>信用卡</em>，支持预授权电话支付，无需开通网银也能支付：<em>（单笔及日累计额度均为1万）</em></p>
                  <div style=" overflow:hidden; clear:both; zoom:1;">
			    <img alt="招商银行" width="125" height="32" src="http://s3.lvjs.com.cn/img/super_v2/order_add_z.jpg">
				<img alt="中国建设银行" width="125" height="32" src="http://pic.lvmama.com/img/super_v2/order_add_j.jpg">
				<img alt="中国银行" width="125" height="32" src="http://s1.lvjs.com.cn/img/bank/zhongh.gif">
				<img src="http://pic.lvmama.com/img/bank/gongh.gif" alt="中国工商银行">
				<img src="http://pic.lvmama.com/img/bank/nongh.gif" alt="中国农业银行">
				<img alt="中国交通银行" src="http://pic.lvmama.com/img/bank/jiaoh.gif">
				<img src="http://pic.lvmama.com/img/super_v2/mings.gif" width="125" height="32">
				<img alt="中国光大银行" height="32" width="125" src="http://pic.lvmama.com/img/super_v2/order_add_g.jpg" />
				<img src="http://pic.lvmama.com/img/bank/pinga.gif" alt="平安银行">
				<img src="http://pic.lvmama.com/img/super_v2/order_add_gf.jpg" width="125" height="32">
				<img src="http://pic.lvmama.com/img/super_v2/shangh.gif" width="125" height="32">
				<img src="http://pic.lvmama.com/img/super_v2/order_add_p.jpg" width="125" height="32">
				<img alt="兴业银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/xingy.gif" />
				<img alt="深圳发展银行" height="32" width="125" src="http://pic.lvmama.com/img/bank/shenz.gif">	
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
          <span class="lkl-btn"><a target="lvmamaPay" href="${lakalaURL}">获得拉卡拉账单号</a></span>
          <p><span>温馨提示：由于订单有效期为${order.waitPayment?if_exists}分钟，请谨慎选择线下拉卡拉刷卡机支付。</span><a target="_blank" href="http://b.lakala.com/images/PayFlash.swf">如何使用拉卡拉？</a><a target="_blank" href="http://map.lakala.com/">哪里有拉卡拉？</a></p>
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
          });
</script>
