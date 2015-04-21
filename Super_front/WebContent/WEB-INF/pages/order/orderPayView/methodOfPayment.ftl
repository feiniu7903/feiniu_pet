 <!-- 选择支付方式 -->
            <div class="payment form-inline">
                <div class="pay-title ui-tab-trigger">
                    <h4 class="pay-price">您还需继续付款 <dfn><i>4100</i></dfn> 元</h4>
                    <ul class="tabnav order-tabnav clearfix">
                    	<li class="selected"><a href="javascript:;">支付平台/银行卡</a></li>
                    	<li class="paylink"><a href=".../otherPayment.ftl">其他方式<i class="ui-arrow-right blue-ui-arrow-right"></i></a></li>
                    </ul>
                </div>
                <div class="tab-switch payment-list">
                    <div class="tabcon">
                        <ul class="bank-list clearfix">
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'alipay')"/><i class="bank alipay" title="支付宝"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'tenpay')"/><i class="bank tenpay" title="财付通"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'chinaMobile')"/><i class="bank unionpay2" title="银联支付"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'unionpayDirect')"/><i class="bank unionpay-quick" title="银联快捷支付"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'chinaMobile')"/><i class="bank cmpay" title="中国移动手机支付"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'ningboBankExpressGatewayCredit')"/><i class="bank alipay-quick" title="支付宝快捷支付"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'tenpayDirectpay')"/><i class="bank tenpay-quick" title="财付通快捷支付"></i></label></li>
                        </ul>
                        <div class="dot_line"></div>
                        <ul class="bank-list clearfix">
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'ABC')"/><i class="bank abc" title="中国农业银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BOCB2C')"/><i class="bank boc" title="中国银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CCB')"/> <i class="bank ccb" title="中国建设银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="beforeSubmitGateway(this,'icbc')"/> <i class="bank icbc" title="中国工商银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'POSTGC')"/><i class="bank psbc" title="中国邮政储蓄银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CEBBANK')"/><i class="bank ceb" title="中国光大银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CMBC')"/><i class="bank cmbc" title="中国民生银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CMB')"/> <i class="bank cmb" title="招商银行"></i></label><span class="poptip-mini poptip-mini-warning"><div class="tip-sharp tip-sharp-bottom"></div>立减10元</span></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank','COMM')"/><i class="bank comm" title="交通银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BJBANK')"/><i class="bank bjbank" title="北京银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SHBANK')"/><i class="bank shbank" title="上海银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'HZCBB2C')"/><i class="bank hzbank" title="杭州银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'NBBANK')"/><i class="bank nbcb" title="宁波银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'WZCBB2C-DEBIT')"/><i class="bank wzcb  " title="温州银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BJRCB')"/><i class="bank bjrcb" title="北京农村商业银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SPDB')"/><i class="bank spdb" title="浦发银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CIB')"/><i class="bank cib" title="兴业银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'GDB')"/><i class="bank cgb" title="广发银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CITIC')"/><i class="bank ecitic" title="中信银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SPABANK')"/><i class="bank pingan" title="平安银行"></i></label></li>
                            <li><label class="radio inline"><input class="input-radio" name="banks" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'FDB')"/><i class="bank fudian" title="富滇银行"></i></label></li>
                        </ul>
                    </div>
                    <div class="tabcon">
                        
                    </div>
                </div>
                <div class="order-btn"><input class="pbtn pbtn-big pbtn-orange" onclick="selectBanks(this)" type="submit" value="下一步"/> </div>
                
                <div class="order-btn "><button class="pbtn pbtn-big pbtn-orange tanchaung">&nbsp;&nbsp;显示弹窗&nbsp;&nbsp;</button></div>
</div> <!-- //选择支付方式 -->
            
