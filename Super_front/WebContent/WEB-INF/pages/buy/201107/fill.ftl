<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>填写订单信息_<@s.property value="mainProdBranch.prodProduct.productName" />_标准订单_驴妈妈旅游网</title>
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_globalV1_0.css,/styles/new_v/header-air.css,/styles/super_v2/orderV2.css,/styles/super_v2/order-plugin.css,/styles/new_v/ui_plugin/jquery-ui-datepicker.css"/>

        <#--<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/v4/modules/pandora-dialog.js"></script>-->

		<script type="text/javascript" src="http://pic.lvmama.com/min/index.php?f=/js/jquery142.js,/js/common_new/alert.js,/js/super_v2/order_func.js,/js/super_v2/detail_func.js,/js/ui/jquery-ui-datepicker.custom.min.js"></script>
        <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/calendar.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
        <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >

        <script src="http://pic.lvmama.com/min/index.php?f=/js/ui/lvmamaUI/lvmamaUI.js"></script>
        <style type="text/css">
		</style>
		<#include "/common/coremetricsHead.ftl">
	</head>
	<body style="background:none;">
		<!--===== 头部文件区域 S ======-->
		<div style="width:980px; margin:0 auto;">
		<div style="text-align:center;">
			<@s.if test='buyInfo.channel=="HUANLEGU"'>
				<a href="http://sh.happyvalley.cn/flash/index.shtml"><img src="http://pic.lvmama.com/zt/edm/logo_huanlegu.jpg"  height="100"/></a>
			</@s.if>
			<@s.else>
				<#include "/common/orderHeaderNew.ftl">
			</@s.else>
		</div>
		<!--===== 头部文件区域 E ======-->

		<!--===== 导航条 S ======-->
		<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
		<@navigation productType="${mainProdBranch.prodProduct.productType?if_exists}" 
			subProductType="${mainProdBranch.prodProduct.subProductType?if_exists}" 
			resourceConfirm="${mainProductNeedResourceConfirm}"
			eContract = "${mainProductEContract}" 
			stepView="fillView"
			payToSupplier="${mainProdBranch.prodProduct.payToSupplier}" 
			fillTravellerInfo="${(!travellerInfoOptions.isEmpty())?string('true','false')}"/>
		<!--===== 导航条 E ======-->
		
		<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <@creditTip productName="${mainProdBranch.prodProduct.productName}" productType="${mainProdBranch.prodProduct.productType?if_exists}"/>
		<!--===== 页面信用贴士E ======-->
        
        <#-- 设置线路类与非线路类表单的提交地址 -->
        <@s.if test = '(mainProdBranch.prodProduct.productType == "ROUTE" &&  mainProdBranch.prodProduct.subProductType != "FREENESS")||mainProdBranch.prodProduct.isTrain() || !travellerInfoOptions.isEmpty()'>
        	<#assign formSubmitUrl = "/buy/fillTraveler.do"> 
        </@s.if>
        <@s.else>
        	<#assign formSubmitUrl = "/buy/update.do">
        </@s.else>
		<form id="buyUpdateForm" name="orderForm" method="post" action="${formSubmitUrl!"/buy/update.do"}">
			<@s.token></@s.token>			
			<input name="_leaveTime" type="hidden" id="_leaveTime" value="${buyInfo.leaveTime?if_exists}" />
			<input type="hidden" name="mainProductNeedResourceConfirm" value="${mainProductNeedResourceConfirm}"/>
			<input type="hidden" name="mainProductEContract" value="${mainProductEContract}"/>
			<input type="hidden" name="submitOrder" value="true"/>
			<input type="hidden" name="buyInfo.productId" value="${buyInfo.productId?if_exists}">
			<input type="hidden" name="buyInfo.prodBranchId" value="${buyInfo.prodBranchId?if_exists}">
			<input type="hidden" value="${buyInfo.visitTime?if_exists}" id="allVisitDate" name="buyInfo.visitTime"/>
			<input type="hidden" value="${mainProdBranch.productId?if_exists}" id="_mainProductId" name="product_mainProductId"/>			
			<input type="hidden" name="buyInfo.productType" value="${mainProdBranch.prodProduct.productType?if_exists}"/>
			<input type="hidden" id="_mainSubProductType" name="buyInfo.subProductType" value="${mainProdBranch.prodProduct.subProductType?if_exists}"/>
			<input type="hidden" name="buyInfo.days" value="${buyInfo.days?if_exists}"/>
			<#-- 百度团购参数 -->
			<input type="hidden" name="tn"  id="tn" value="${tn}"/>
			<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
			
            <@s.if test='mainProdBranch.prodProduct.payToLvmama=="true"'>
				<input type="hidden" name="buyInfo.paymentTarget" value="TOLVMAMA"/>
			</@s.if>
			<@s.elseif test='mainProdBranch.prodProduct.payToSupplier=="true"'>
				<input type="hidden" name="buyInfo.paymentTarget" value="TOSUPPLIER"/>
			</@s.elseif>
            <input name="buyInfo.selfPack"  type="hidden" value="${buyInfo.selfPack?if_exists}"/>
            <input name="buyInfo.adult"  type="hidden" value="${buyInfo.adult}"/>
            <input name="buyInfo.child"  type="hidden" value="${buyInfo.child}"/>
            <input name="content"  type="hidden" value="${buyInfo.content}"/>
            <input name="buyInfo.content" type="hidden" value="${buyInfo.content}"/>
            <input name="orderCouponPrice"  type="hidden" id="orderCouponPrice" value="0"/>
            <input name="buyTime" type="hidden" value="${buyInfo.visitTime?if_exists}" id="buyTime"/>
            <input name="buyInfo.seckillBranchId" type="hidden" value="${buyInfo.seckillBranchId?if_exists}"/>
            <input name="buyInfo.seckillId" type="hidden" value="${buyInfo.seckillId?if_exists}"/>
            <input name="buyInfo.waitPayment" type="hidden" value="${buyInfo.waitPayment?if_exists}"/> 
            <input name="buyInfo.seckillToken" type="hidden"/>          
			<div class="o-container">
				<!--========= 预订产品 S ========-->
				<#if mainProdBranch.prodProduct.selfPack=='true'>
					<#include "/WEB-INF/pages/buy/201107/route/fill_detail_selfpack.ftl">
			  	<#elseif  mainProdBranch.prodProduct.productType = "TICKET" || mainProdBranch.prodProduct.productType = "ROUTE" ||mainProdBranch.prodProduct.isTrain()>
			  		<#include "/WEB-INF/pages/buy/201107/ticket/fill_detail.ftl">
				<#elseif mainProdBranch.prodProduct.productType = "HOTEL">
					<#if mainProdBranch.prodProduct.subProductType = "SINGLE_ROOM" && mainProdBranch.prodProduct.isAperiodic != "true">
						<#include "/WEB-INF/pages/buy/201107/hotel/fill_detail_single.ftl">
					<#else>
						<#include "/WEB-INF/pages/buy/201107/hotel/fill_detail.ftl">
					</#if>
			  	</#if>
			    <!--========= 预订产品 E ========-->
			    
    			<!--========= 费用包含 等 S ========-->
    			<div class="detail">
    			<ul class="detail-cur">
        				<li class="cur-tab" cur="costcontain">费用说明<s></s></li>
        				<@s.if test='mainProdBranch.prodProduct.productType == "TICKET"'>
        				<li cur="">预订须知</li>
        				</@s.if>
						<li cur="refundsexplanation">退款说明<s></s></li>
        				<!--li>费用不包含</li-->
        			</ul>
        			<div class="cur-content tab-con pro-info">
        				<div id="costcontain">
        				<#if mainProdBranch.prodProduct.productType = "HOTEL">        					        					
        				<#if mainProdBranch.description?if_exists>
        				<#list mainProdBranch.description?split("\n") as line>
        				${line}<br/>
        				</#list>        					
        				</#if>
        				<#elseif mainProdBranch.prodProduct.productType=='TRAFFIC' && mainProdBranch.prodProduct.subProductType=='TRAIN'>
        					卧铺上中下铺位为随机出票，实际出票铺位以铁路局为准，驴妈妈暂收下铺位的价格。出票后根据实际票价退还差价，至实际支付给驴妈妈的银行卡中。<br/>
							请凭下单填写的身份证或护照到火车站或全国各代售点窗口取票，凭票进站，部分火车站可以刷身份证直接进站，以具体车站为准。

        				<#else>
        					<@s.property value="viewPage.contents.COSTCONTAIN.contentRn" escapeHtml="false"/>
        				</#if>
        				</div>        				
        			</div>
        			<@s.if test='mainProdBranch.prodProduct.productType == "TICKET"'>
        			<div class="tab-con pro-info">
        				<@s.if test="viewPage.contents.ORDERTOKNOWN!=null">
        					<@s.property value="viewPage.contents.ORDERTOKNOWN.contentRn" escapeHtml="false"/>
        				</@s.if>
        			</div>
        			</@s.if>
        			<div class="tab-con pro-info">
						<div id="refundsexplanation" style="">

						<#--<span id="refundContent">-->
							<#--<#if refundContent!=null>-->
							<#--${refundContent};<br/>其它：-->
							<#--</#if>-->
						<#--</span>-->
                        <@s.if test="mainProdBranch.prodProduct.isTrain()">
                            退款说明： <br/>
                            1)产品预订失败退款：如果快铁驴行预订失败，驴妈妈将在确认失败后退款至原支付渠道，由银行将所有支付款项全额退回。 <br/>
                            2)产品差价退款：如果快铁驴行订单内含有的车票的实际票价低于您所支付的票价，驴妈妈将在确认实际票价后将差额退款至原支付渠道，由银行将差额款项退回，因出票原因产生的差额或全额退款，将在2小时内返还。<br/>
                            3)产品退票退款：若客户自行将快铁驴行订单内含有的车票进行退票，驴妈妈将在确认退票后，将差额部分退回原支付渠道，由银行将差额款项退回，退回时间为20个工作日。 <br/>
                            4)用户在火车站或代售窗口改签后，请在原列车班次的前一天17:00之前，致电我司客服（10106060）进行保险改签。游客可在改签提交后的5个工作日致电保险客服热线(太平保险：95589)查询到最终投保记录。如没有及时通知，保险还是原车次生效，一旦出险，后果自行承担。<br/>
                        </@s.if>
                        <@s.else>
                            <@s.if test="viewPage.contents.REFUNDSEXPLANATION!=null">
                                <@s.property value="viewPage.contents.REFUNDSEXPLANATION.contentRn" escapeHtml="false"/>
                            </@s.if>
                            <@s.else>无</@s.else>
                        </@s.else>
        				</div>
					</div>
    			</div>
    			<!--========= 费用包含 等 E ========-->
    			<div style="clear:both;"></div>
    			<!--========= 附加产品 S ========-->
    			<@s.if test="additionalProduct.size>0">
	      			<h3><s></s>附加产品</h3><input type="hidden" name="baoxianSelect" value="0"/>
	      			<table class="orderpro-list">
		    			<thead>
					        <tr>
					        	<th class="col1">产品类型</th>
					           	<th class="col2">名称</th>
					           	<th class="col3">门市价</th>
					           	<th class="col4">驴妈妈价</th>
					           	<th class="col5">计算单位</th>
					           	<th class="col6">份数</th>
					           	<th class="col7">小计</th>
					         </tr>
		        		</thead>
		        		<tbody>
		        			<@s.iterator value="additionalProduct" var="add">
		        				<@s.iterator value="#add.value" var="product">
		        						<tr>
					            			<td class="col1 addition"><strong>${add.key}</strong></td>
					            			<td class="col2 <@s.if test='#product.branch.description!=null&&#product.branch.description.length()>0'>show-insure</@s.if><@s.else>hide-insure</@s.else>">${product.relationProduct.productName}(${product.branch.branchName?if_exists})</td>
					            			<td class="col3"><del>&yen;${product.branch.marketPriceYuan?if_exists}</del></td>
					            			<td class="col4"><strong>&yen;${product.branch.sellPriceYuan?if_exists}</strong></td>
					            			<td class="col5">${product.branch.priceUnit}</td>
					            			<td class="col6 btn-nums">
					            				<input type="hidden" ordNum='ordNum' value="0" name="buyInfo.buyNum.product_${product.branch.prodBranchId}"  id="addition${product.branch.prodBranchId}" sellName="sellName" cashRefund="" couponPrice="0"  marketPrice="${product.branch.marketPriceYuan?if_exists}" sellPrice="${product.branch.sellPriceYuan?if_exists}" />					            				
					              				<select btype="${product.branch.branchType}" pstype="${product.relationProduct.subProductType}" <#if product.relationProduct.subProductType="INSURANCE">tt="baoxianSelect"</#if> <#if product.saleNumType=="ALL">style="display:none"</#if> id="select${product.branch.prodBranchId}" name="select${product.branch.prodBranchId}" saleNumType="${product.saleNumType}" minAmt="${product.branch.minimum}" onchange="updateAddition(this,'addition${product.branch.prodBranchId}','sumAddition${product.branch.prodBranchId}');">
							                		<option value="${product.branch.minimum}">${product.branch.minimum}</option>
							              		</select>
					                		</td>
					            			<td class="col7"><strong>&yen;</strong><strong class="total" id="sumAddition${product.branch.prodBranchId}">0</strong></td>
					            		</tr>
					            		<#if product.branch.branchType == "FANGCHA">
					            			<tr class="insure-detail" id="insure_info_${product.branch.prodBranchId}">
												<td colspan="7" align="left">
													行程中的住宿一般为两个床位的标准间，出游人数（成人）为单数时，需补足额外一个床位的费用。您也可以选择接受与其他人拼房，当无法拼房时再补房差。
												</td>
											</tr>	
		        						<#elseif product.relationProduct.subProductType == "INSURANCE">
					            			<tr>
												<td colspan="7" class="insure-detail" align="left">
													<#if product.branch.description??>
													${product.branch.description?replace("\n","<br/>")}
													</#if>	
												</td>
											</tr>	
                                            <tr id="insure_info_${product.branch.prodBranchId}"><td colspan="7" class="without">旅游保险可为您的行程提供全面的风险保障。为了您自身的权益，强烈建议您购买旅游保险。</td></tr>
                                        <#elseif product.branch.description?? && product.branch.description?length gt 0>
                                        	<tr>
												<td colspan="7" class="insure-detail" align="left">
													<#if product.branch.description??>
													${product.branch.description?replace("\n","<br/>")}
													</#if>	
												</td>
											</tr>
		        						</#if>
		        				</@s.iterator>
		        			</@s.iterator>
		        		</tbody>
		        	</table>	          		   			
    			</@s.if>			
                <script>	
				  jQuery(function(){
						var Obj = $(".without");
						Obj.hover(function(){
							 $('body').unbind('mousedown');
							 }, function(){
								 $('body').bind('mousedown', function(){
								$(".without").hide();	  
							  });
						});
				  })
				</script>  
    			<!--========= 附加产品 E ========-->
    			

			    <!--使用优惠券 S-->
                <#if couponEnabled=='Y'>
			    <#--<#if hasCoupon>-->
			    
				    <input type="hidden" value="${mainProdBranch.branchName}" id="businessCouponBranchId${mainProdBranch.prodBranchId}" name="businessCouponBranchId${mainProdBranch.prodBranchId}"/>
	    			<@s.iterator value="relatedProductList" var="product">
	    			<input type="hidden" value="${product.branchName}" id="businessCouponBranchId${product.prodBranchId}" name="businessCouponBranchId${product.prodBranchId}"/>
	    			</@s.iterator>
			    
					<h3><s></s>优惠</h3>
					<h4 class="sub-title">优惠活动</h4>

                <div class="sub-content">
                    <div id="businessCouponDisplayInfo" style="display:none">
                        <p>温馨提示：若您在下单后对出游时间或出游人数进行变更，将无法享受早订早惠或多订多惠。</p>
                        <ul class="xh-active">
                            <li id="earlyCouponShow" style="display:none">
                                <div><b>早订早惠</b><span id="earlyCoupon">
						</span></div>
                            </li>
                            <li id="moreCouponShow" style="display:none">
                                <div><b>多订多惠</b><span id="moreCoupon">
						</span></div>
                            </li>
                        </ul>
                    </div>

                    <div id="divhidden" style="display:none;">
                        <input type="radio" id="couponCheckedSpec" name="couponChose" class="inp-radio"/>
                    </div>

                    <dl class="xdl">
                        <dt class="B">可享优惠：</dt>
                        <dd class="mb10">
                            <div id="couponActivity" class="selectbox selectbox-big">
                                <p class="select-info like-input">
                                    <span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>
                                    <span class="select-value">不使用任何优惠</span>
                                </p>

                                <div class="selectbox-drop">
                                    <ul class="select-results">
                                        <li data-value="">不使用任何优惠</li>
                                    <#if hasCoupon>
                                        <#if mainProdBranch.prodProduct.couponActivity=='true'||mainProdBranch.prodProduct.couponActivity==null||mainProdBranch.prodProduct.couponActivity==''>
                                            <@s.iterator value="orderCouponList" status="st" var="orderCoupon">
                                                <#if orderCoupon.couponId!=2818>
                                                    <li data-value='<@s.property value="couponId"/>' payment='<@s.property value="paymentChannel"/>'><@s.property value="couponName"/></li>
                                                </#if>
                                            </@s.iterator>
                                        </#if>
                                        <#if mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble==''>
                                            <li data-value="userCoupon">使用优惠券</li>
                                        </#if>
                                    </#if>
                                        <#if mainProdBranch.prodProduct.payToLvmama == 'true'>
                                            <li data-value="userCash">奖金全额抵用（可抵用<span id="availableCash">0</span>元）</li>
                                        </#if>
                                    </ul>

                                </div>
                            </div>
                        </dd>
                        <dd>
                            <input type="hidden" id="couponPayment"     name="buyInfo.couponList[0].paymentChannel"/>
                            <input type="hidden"  id="productId_youhui" name="buyInfo.couponList[0].couponId"/>
                            <input type="hidden" id="couponChecked"     name="buyInfo.couponList[0].checked" value="false"/>
                            <input type="hidden" id="couponCode" name="buyInfo.couponList[0].code"/>
                            <input id="cashValue" type="hidden" name="buyInfo.cashValue"/>

                            <div class="tiptext tip-default" id="myCoupon" style="display:none">
                                <#if mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble==''>
                                    <div class="check-text">
                                        <label class="radio inline">
                                            输入优惠券代码：
                                        </label>
                                        <input type="text" id="code_youhui" size="20" class="inp-txt"/>
                                        <input name="checkCodeBtn" type="button" id="checkCodeBtn" onclick="userCouponCode('code_youhui')" value="确定使用" align="absmiddle" style="padding:2px 5px;margin-left:5px;"/>
                                        &nbsp;<em class="txt">（如果您已有驴妈妈优惠券，请输入优惠券号码）</em> <br>
                                        <span class="coupon-tips" id="couponInfo_youhui"></span>

                                        <input type="hidden" id="couponCodeUseStatus" value="unuse"/>


                                    </div>
                                </#if>
                                <div class="hr_a"></div>
                            </div>





                            <div style="display: none;" id="myCash" class="tiptext tip-default">
                                <label class="radio inline">
                                    我要抵现
                                    <input type="text" class="input-text" id="cashValue_input">
                                    元<i style="color:#ccc;margin-left:10px">可输入1~<span id="availableCashShow">0</span>的整数</i>
                                </label><br/>
                                <button class="pbtn pbtn-mini pbtn-blue" id="confirmSumbitCash" onclick="userCash();return false;" style="margin-top:10px;">确定</button>
                            </div>
                        </dd>
                    </dl>


                </div>
			    <#--</#if>-->
			    </#if>
			    <!--使用优惠券 E-->


				<@s.if test='!(!travellerInfoOptions.isEmpty() || (mainProdBranch.prodProduct.productType == "ROUTE" &&  mainProdBranch.prodProduct.subProductType != "FREENESS"))'>	        

			    	<!--========= 取票人信息 S ========-->
	        		<@s.if test='mainProdBranch.prodProduct.productType=="TICKET" || mainProdBranch.prodProduct.subProductType == "FREENESS"'>
	        			<#include "/WEB-INF/pages/buy/201107/ticket/ticket_person.ftl"/>
	        		</@s.if>
	        		<@s.elseif test='mainProdBranch.prodProduct.productType=="HOTEL"'>
	        			<#include "/WEB-INF/pages/buy/201107/hotel/hotel_person.ftl"/>
	        		</@s.elseif>



	        		<!--========= 取票人信息 E ========-->
	        		<@s.if test='mainProductNeedResourceConfirm=="true"'>
					<!--========= 订单备注 S ========-->
					<#--
					<h3><s></s>订单备注</h3>
						<dl class="user-info remark2">
						<dt>订单备注：</dt><dd><textarea cols="70" rows="4" id="dingdan_xuqiu" name="buyInfo.userMemo" onfocus="if ($(this).val() == '您对订单的特殊要求') {$(this).val('');}">您对订单的特殊要求</textarea></dd>
					</dl> 
					-->	
					<!--========= 订单备注 E ========-->
			    	</@s.if>
        		    </@s.if>
	
				
			    <!--订单总价 S-->
			    <div class="order-total">
    				<span>产品金额：<em>&yen;</em><em id='heji'>0</em>  - 优惠活动：<em>&yen;</em><em id='youhuiquan'>0</em>  - 奖金抵用：<em>&yen;</em><em id='cashValueJian'>0</em> ＝ </span>应付：<em>&yen;</em><strong id='dingdanjiesujinger'>0</strong>
			    </div>
		        <!--订单总价 E-->
			    
			    <div class="order-commit order-commit-floatnone">
    				<input name="btnsubmitOrder" type="button" value="<@s.if test='mainProdBranch.prodProduct.isRoute()'>下一步</@s.if><@s.else>确认下单</@s.else>" class="btn-submit" style="width:130px" onclick="btnFormSubmit()"/>
    				
			        <!--协议 S-->
			        <@s.if test='buyInfo.channel=="HUANLEGU" || (mainProdBranch.prodProduct.productType == "ROUTE" && mainProductEContract=="true")'>
						 <div style="display:none">
		                   <input type="checkbox" name="inpAgreement" checked="checked" id="lvmamaxieyi"/>
							</div>
					</@s.if>
					<@s.else>
						 <#include "/WEB-INF/pages/buy/xieyi/xieyi.ftl"/>     
					</@s.else>
                      
                    <!--协议 E-->
			    </div>	  
			</div>
		</form>
		<!--===== 底部文件区域 S ======-->
	 
		     <@s.if test='buyInfo.channel=="HUANLEGU"'>
						
			</@s.if>
			<@s.else>
				 <#include "/common/orderFooter.ftl" />
			</@s.else>
		<!--===== 底部文件区域 E ======-->
		
		<#-- 未登录状态下需要显示快速登录层 S -->
		<@s.if test='!isLogin()'>
			<#include "/WEB-INF/pages/buy/201107/rapidLogin.ftl"/>
		</@s.if>
		<#-- 未登录状态下需要显示快速登录层 E -->
		</div>
		
	</body>
	
	<!--===== 加载JS代码 ======-->
	<script src="/js/buy/form.js" type="text/javascript"></script>
	<script src="/js/buyBase.js" type="text/javascript"></script>
	<script src="/js/fillNew.js" type="text/javascript"></script>
	<script src="/js/youhui.js" type="text/javascript"></script>
	<script src="/js/rapidLogin.js" type="text/javascript"></script>
    <script src="http://pic.lvmama.com/js/v4/order-page.js"></script>

    <#include "/WEB-INF/pages/buy/201107/subOrder.ftl" />
	<script type="text/javascript">
			jQuery(function(){ 
				var insure = $(".show-insure"), 	
					insureCon = $(".insure-detail"); 
				showThing(insure,insureCon,''); 
				updatePrice_youhui();
				$("#titAgreement").click(function(){
					if($("#lvmamaxieyi").attr("checked")==true){
						$("#lvmamaxieyi").attr("checked",false);
					}else{
						$("#lvmamaxieyi").attr("checked",true);
					}
					
				})
			}) 
	</script>
	
	
	<script language="javascript">
	$(".detail-cur li").each(function(i){
			$(this).css("cursor","pointer");
			$(this).mouseover(function(){
				$(this).addClass("cur-tab").siblings().removeClass("cur-tab01");
				$(".pro-info").eq(i).show().siblings(".pro-info").hide();				
				});
		});
</script>
<script language="javascript">

//popup
var s,
	tipsA = $(".o-tips").find("a"),
	tipsC = $(".o-tips").find(".btn-content");

function showThing(obj,chidCon,no){
	obj.each(function(i){
		$(this).click(function(){
			var t = $(this).offset().top + $(this).height(),
				l = $(this).offset().left - no;
				
			if(chidCon.eq(i).is(":hidden")){
				chidCon.hide();	
				chidCon.eq(i).show();
			}else{
				chidCon.hide();	
			}	
			chidCon.eq(i).css({"top":t,"left":l});
		});
	});
}
function hideThing(obj){
	if(obj.eq(0).is(":hidden")){
		clearTimeout(s)
	}else{
		obj.hide();
	}
}
showThing(tipsA,tipsC,152);	

</script>
<!-- Google Code for &#27969;&#31243;-&#22635;&#20889;&#35746;&#21333; 540&#22825; Remarketing List -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 962608731;
var google_conversion_language = "en";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "WFDDCN3MxAMQ2_yAywM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/962608731/?label=WFDDCN3MxAMQ2_yAywM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

	<script type="text/javascript">
	<!-- 
	var bd_cpro_rtid="nWTYn0";
	//-->
	</script>
	<script type="text/javascript" src="http://cpro.baidu.com/cpro/ui/rt.js"></script>
	<noscript>
	<div style="display:none;">
	<img height="0" width="0" style="border-style:none;" src="http://eclick.baidu.com/rt.jpg?t=noscript&rtid=nWTYn0" />
	</div>
	</noscript>
<#include "/common/ga.ftl"/>	
<script>
		<#if mainProdBranch.prodProduct.productType == "HOTEL">
        	cmCreatePageviewTag("填写订单-"+"<@s.property value="mainProdBranch.prodProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="mainProdBranch.prodProduct.subProductType"  escape="false"/>", "Q0001", null, null);
			cmCreateConversionEventTag("填写订单-${mainProdBranch.prodProduct.productType?if_exists}-DJJD-${mainProdBranch.prodProduct.subProductType?if_exists}","1","订单流程");
		<#else>
			cmCreatePageviewTag("填写订单-"+"<@s.property value="mainProdBranch.prodProduct.productType"  escape="false"/>-"+"<@s.property value="mainProdBranch.prodProduct.subProductType"  escape="false"/>", "Q0001", null, null);
			cmCreateConversionEventTag("填写订单-${mainProdBranch.prodProduct.productType?if_exists}-${mainProdBranch.prodProduct.subProductType?if_exists}","1","订单流程");
		</#if>
    </script>
<html>
