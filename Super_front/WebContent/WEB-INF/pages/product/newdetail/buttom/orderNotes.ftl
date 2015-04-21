            <h3 class="h3_tit"><span>订购流程</span></h3>
            <div class="row how_order">
                <#if !prodCProduct.prodProduct.isTicket()>
                <p class="booking_process">网上/电话预订<span></span>资源确认<span></span>签约/付款<span></span>收到短信凭证<span></span>开心出游<span></span>归来回访<span></span>获得点评奖金</p>
                <dl>
                    <dt>签约方式</dt>
                    <dd>在线签约：在您确认合同范本以后，我们将盖章的电子档合同发送到您邮箱。</dd>
                    <dd>传真签约：双方在合同上签字盖章后，通过传真进行签约。如涉及签证材料需要快递，请在快件中注明订单号。</dd>                   
                    <dd>门市签约：上海圣诺亚门市（总部）地址：金沙江路1759号圣诺亚大厦B座7F 电话：021-62628162、62628200

						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						人民广场旗舰店   地址：上海黄浦区西藏中路94号  电话：021-53086863 53086859 53086857  营业时间：9：00-21：00（全年无休） 
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						浦东南泉北路店 地址：上海浦东新区南泉北路528号北楼一层N1-08，N1-09   电话：021-50623220 50623221 营业时间：9：00-20：00（全年无休）
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						北京分社门市  地址：北京市朝阳区左家庄曙光西里甲6号院时间国际大厦1号楼907-08室 电话：010-59762904转801（18：00之后请拨打：1010 6060 ）
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						成都分社门市 地址：成都市一环路西一段2号高升大厦506室   电话：1010 6060。
						 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						广州分社门市 地址：广州市越秀区小北路185-189号广州鹏源发展大厦1804/1805室 电话：1010 6060。
					</dd>
                </dl>
                </#if> 
                <dl class="bank_img">
                    <dt>在线支付</dt>
                    <dd class="bank-total bank-b"></dd>
                </dl>
                <dl class="zhifubao"> 
                <dt>支付平台支付</dt> 
                <dd class="bank-zhifu"></dd> 
                </dl> 
                <dl class="bank bankinfo"> 
                <dt>信用卡电话支付</dt> 
                <dd>请致电 <@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else>，告知客服您的订单号，由客服人员帮您转入语音系统进行电话支付。一个电话就可以完成支付，安全、快捷。</dd> 
                <dd>以下银行的借记卡，无需开通网银也能支付（建行单笔及日累计均为额度3500元，其他银行单笔额度为1万，日累计额度为5万）： <br /> 
                招商银行、中国建设银行、中国银行、中国农业银行、中国邮政储蓄银行、交通银行、中国光大银行、浦发银行、中信银行、华夏银行</dd> 
                <dd>以下银行的信用卡，无需开通网银也能支付（可支付额度根据银行不同而有所差异）：<br /> 
                中国建设银行、中国工商银行、浦发银行、中国民生银行、上海银行、平安银行、中国银行、中国农业银行、广发银行</dd> 
                </dl>
                <dl class="line_payment">
                    <dt>线下支付</dt>
                    <dd class="bank-total bank-l"><span>拉卡拉：不用网银也能支付</span></dd>
                </dl>
            </div><!--how_order end-->
