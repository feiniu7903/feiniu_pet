<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的订单-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<script type="text/javascript">
	$(function(){
		$("span.selfPack").hover(function(){
			$("#relat_product_list").show();
		},
		function(){
			$("#relat_product_list").hide();
		}
		);
	});
	</script>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-order">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
		<p>
			<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
			&gt;
			<a href="http://www.lvmama.com/myspace/order.do">我的订单</a>
			&gt;
			<a class="current">订单详情</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
				<!-- 订单详情>> -->
				<div class="ui-box mod-edit order_detail-edit pd10">
					<div class="ui-box-title"><h3>订单详情</h3></div>
				    	<!-- 订单信息>> -->
				    	<div class="view-box clearfix">
				        	<h4>游客信息</h4>
				            <p class="p-info first">联系人姓名：<strong>${order.contact.name?if_exists}</strong>　　　联系人手机：<strong><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(order.contact.mobile)" /></strong></p>
				            <table class="lv-table order_detail-table">
				            	<tbody>
				            		<tr><th>姓名</th><th>手机</th><th>证件类型</th><th>证件号码</th><th>电子邮箱</th><th>出生年月</th></tr>
						        	 <@s.iterator id="obj" value="order.travellerList" status="index">
				            			<tr><td>${obj.name?if_exists}</td><td><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobile)" /></td><td>${obj.zhCertType?if_exists}</td>
				            			<td><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenIDCard(certNo)" /></td>
				            			<td><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /></td>
				            			<td><#if obj.brithday?exists>${obj.brithday?string("yyyy-MM-dd")}</#if></td></tr>
									 </@s.iterator>
				            	</tbody>
				            </table>
				            <@s.if test="order.orderType=='TRAIN'">
					<strong>火车票出票旅客信息</strong>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
						<tbody>
							<tr bgcolor="#f4f4f4" align="center">
								<td width="5%">姓名</td>
								<td width="8%">证件类型</td>
								<td width="10%">日期</td>
								<td width="5%">出发地</td>
								<td width="5%">目的地</td>
								<td width="8%">票种</td>
								<td width="8%">车次</td>
								<td width="15%">坐席</td>
								<td width="8%">价格</td>
							</tr>
							<@s.iterator value="order.orderTrafficList" var="traffic">
								<@s.iterator value="#traffic.orderTrafficTicketInfoList" var="ticket">
									<tr bgcolor="#ffffff" align="center">
										<td>${ticket.person.name}</td>
										<td>${ticket.person.zhCertType }</td>
										<td><@s.date name="order.visitTime" format="yyyy-MM-dd"/></td>
										<td>${traffic.departureStationName}</td>
										<td>${traffic.arrivalStationName}</td>
										<td>${ticket.zhTicketCategory}</td>
										<td>${traffic.trainName}</td>
										<td>${ticket.seatNo}</td>
										<td>${ticket.priceYuan}</td>
									</tr>
								</@s.iterator>
							</@s.iterator>
						</tbody>
					</table>
				</@s.if> 
				            <h4>配送信息</h4>
				            <#list order.personList as obj>
				            	<#if obj.personType=='ADDRESS'>
				            		<p>${obj.name?if_exists} ， ${obj.mobile?if_exists} ， ${obj.address?if_exists} ， ${obj.postcode?if_exists} </p>
				            	</#if>
				            </#list>
				            <h4>订单备注</h4>
				            <p>${order.userMemo?if_exists}</p>
				            <h4>订单信息</h4>
				            <ul class="order-info clearfix">
				            <#assign statusNameStr=order.zhOrderViewStatus/>
				        	<#if order.isPayToLvmama() && !order.isPaymentSucc() && order.isApprovePass() && order.isExpireToPay()>
								<#assign statusNameStr="取消"/>
						    </#if>
				            <li>订单号：${order.orderId?if_exists}</li><li>订单状态：${statusNameStr?if_exists}</li>
				            <li>下单时间：<#if order.createTime?exists>${order.createTime?string("yyyy-MM-dd HH:mm")}</#if></li>
				            <li>支付方式：<#if order.paymentTarget?default("")=="TOLVMAMA">在线支付</#if><#if order.paymentTarget?default("")=="TOSUPPLIER">景区支付</#if></li>
				            </ul>
				           	<!-- 订单列表>> -->
				            	<div class="order_list-box">
				        <table class="lv-table order_list-table">
					        <colgroup>
					        <col class="lvcol-1">
					        <col class="lvcol-2">
					        <col class="lvcol-3">
					        <col class="lvcol-4">
					        <col class="lvcol-5">
					        <col class="lvcol-6">
					        </colgroup>
							  <thead><tr class="thead">
				                    <th class="product-name">产品名称</th>
				                    <@s.if test="order.IsAperiodic()">
				                    	<th class="play-date">产品有效期</th>
				                    </@s.if>
				                    <th class="play-date">游玩日期</th>
					                <th class="price">市场价</th>
				                    <th class="price">现售价</th>
				                    <th class="price">小计</th>
					                <th class="other">其它</th>
							    </tr>
				              </thead>
				              <tbody>
				                <@s.if test="order.hasSelfPack()">
				                	<tr>
									    <td>
									    	<span class="selfPack">
									    		${order.mainProduct.productName?if_exists}
									    	</span>
									    	<div id="relat_product_list" style="display:none;position:absolute;background-color:#fff;border:1px solid #ccc;width:359px;padding:10px;text-align:left">
											<span style="font-weight:bold">包含产品:</span><br/>
											<#list order.ordOrderItemProds as itemObj>
											    ${itemObj.productName?if_exists}<em>&nbsp;&nbsp;&nbsp;&nbsp;×${itemObj.quantity?if_exists}</em><br/>   
											</#list>     	
											</div>
									    </td>
								        <td>
								        	<#if order.visitTime?exists>${order.visitTime?string("yyyy-MM-dd")}</#if>
								        </td>
					                    <td class="price"></td>
					                    <td class="price"></td>
					                    <td class="price">${order.oughtPayYuan?default(0)}</td>
									    <td class="other"></td>
								    </tr>
				                </@s.if>
								<@s.else>
					              	<#list order.ordOrderItemProds as itemObj>
									    <tr>
										    <td>
										    	<#if itemObj.wrapPage?default("false")=="true">
											    	<a href="/product/${itemObj.productId?if_exists}" target="_parent">
											    </#if>
											    ${itemObj.productName?if_exists}
											    <#if itemObj.wrapPage?default("false")=="true"></a></#if>
											    <span class="lv-c1">×${itemObj.quantity?if_exists}</span>
										    </td>
										    <#if order.isAperiodic == "true">
										    	<td>
										    		<#if itemObj.validBeginTime?exists && itemObj.validEndTime?exists>
										    			${itemObj.validBeginTime?string("yyyy-MM-dd")}至${itemObj.validEndTime?string("yyyy-MM-dd")}
										    			<#if itemObj.invalidDateMemo?? >
										                	(${itemObj.invalidDateMemo})
										                </#if>
										    		</#if>
										    	</td>
										    </#if>
									        <td><#if itemObj.visitTime?exists>${itemObj.visitTime?string("yyyy-MM-dd")}<#else>未确定</#if></td>
						                    <td><del>${itemObj.marketPriceYuan?if_exists}</del></td>
						                    <td class="price">${itemObj.priceYuan?default(0)}</td>
						                    <td class="price">${itemObj.priceYuan?default(0) * itemObj.quantity?default(0)}</td>
										    <td class="other"></td>
									    </tr>
								    </#list>
								</@s.else>
							</tbody>
				            </table>
				            <p class="price-sum">
				            	<@s.if test="!order.hasSelfPack()">
				            		门市价：<del>&yen;<i>${order.marketAmountYuan?default(0)}</i></del>  |  共节省：<dfn>&yen;<i>${order.saveAmountYuan?default(0)}</i></dfn> | 
				            	</@s.if>
				            	<b>订单结算总额：</b><dfn>&yen;<i class="f24">${order.oughtPayYuan?default(0)}</i></dfn>
				            </p>
				        </div>
				        	<!-- <<订单列表 --> 
				        </div>
				       <#if order.invoiceList!=null>
				       	<a name="invoiceMaodian"></a>
				        <h4 class="order_small_title">发票信息</h4>
				        <table class="lv-table visitor-table">
				            <thead>
				            <tr class="thead">
				                <th style="width: 40px;">序号</th>
				                <th style="width: 135px;">订单号</th>
				                <th style="width: 80px;">发票金额</th>
				                <th style="width: 90px;">发票项目</th>
				                <th style="width: 210px;">发票抬头</th>
				                <th style="width: 195px;">配送地址</th>
				                <th style="width: 75px;">状态</th>
				                <th style="width: 135px;">快递单号</th>
				            </tr>
				            </thead>
				            <tbody>
				            <#list order.invoiceList as invoice>
				            	<tr>
					                <td>${invoice_index+1}</td>
					                <td>${invoice.orderids}</td>
					                <td>${invoice.amountYuan}</td>
					                <td>${invoice.zhDetail}</td>
					                <td>${invoice.title}</td>
					                <#if invoice.deliveryAddress!=null>
					                	<td>${invoice.deliveryAddress.address}</td>
					                <#else>
					                	<td></td>
					                </#if>
					                <#if invoice.status=="CANCEL">
					                	<td>${invoice.zhStatus}</td>
					                <#else>
					                	<td>${invoice.zhLogisticsStatus}</td>
					                </#if>
					                <td>${invoice.expressNo}</td>
				            	</tr>
				            </#list>
				            </tbody>
				        </table>
				    	</#if>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
      cmCreatePageviewTag("订单详情", "D0001", null, null);
 	</script>
</body>
