<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心-驴妈妈分销平台</title>
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/calendar.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="order">
			<div class="ui-box-title">
            	<h3>订单详情</h3>
            </div>
			
            <div class="xq-fx-content">
            	<h4>游客信息</h4>
                <div class="nrBox"><span>联系人姓名：<b>${order.ordOrder.contact.name }</b></span><span>联系人手机：<b>${order.contactHiddenMobile }</b></span></div>
                <dl class="xq-fx-content-title">
                    <dd class="nameBox">姓名</dd>
                    <dd class="telBox">手机</dd>
                    <dd class="zjBox">证件类型</dd>
                    <dd class="zjhBox">证件号码</dd>
                    <dd class="emailBox">电子邮箱</dd>
                    <dd class="birthBox">出生年月</dd>
                </dl>
                <c:forEach var="per" items="${order.travellerList}">
                	<dl class="xq-fx-content-title">
	                    <dd class="nameBox">${per.fullName }</dd>
	                    <dd class="telBox">${per.hiddenMobile }</dd>
	                    <dd class="zjBox">${per.cnCertType }</dd>
	                    <dd class="zjhBox">${per.hiddenIDCard }</dd>
	                    <dd class="emailBox">${per.hiddenEmail }</dd>
	                    <dd class="birthBox">${per.zhBrithday }</dd>
                	</dl>
                </c:forEach>
                
                <br/>
            	<h4>配送信息</h4>
                <p>
                <c:forEach var="per" items="${order.personList}">
                <c:if test='${per.personType=="ADDRESS" }'>
                <p>${per.fullName} ， ${per.hiddenMobile} ， ${per.address} ， ${per.postCode} </p>
                </c:if>
                </c:forEach>
                </p>
                <br/>
            	<h4>订单备注</h4>
                <p>${order.ordOrder.userMemo}</p>
                <br/>
            	<h4>订单信息</h4>
                <div class="nrBox">
                	<span>订单号：${order.ordOrder.orderId }</span><span>订单状态：${order.ordOrder.zhOrderStatus }</span><br/>
                	<span>下单时间：${order.zhCreateTime}</span><span>支付方式:<c:if test="${order.ordOrder.paymentTarget=='TOLVMAMA' }">在线支付</c:if><c:if test="${order.ordOrder.paymentTarget=='TOSUPPLIER' }">景区支付</c:if></span>
                </div>
                <div class="tablebox">
                    <dl class="xq-fx-content-title">
                        <dd class="cpmcBox">产品名称</dd>
                        <dd class="ywsjBox">有效期</dd>
                        <dd class="scjBox">市场价</dd>
                        <dd class="lmmjBox">驴妈妈价</dd>
                        <dd class="fxjBox">分销价</dd>
                        <dd class="xjBox">小计</dd>
                        <dd class="qtBox">其他</dd>
                    </dl>
                    
                    <c:forEach items="${order.tntOrder.mainOrderList }" var="item">
                    	<dl class="xq-fx-content-title tshuDl">
                        <dd class="cpmcBox"><a href="http://www.lvmama.com/product/${item.productId}" title="${item.productName }">${item.shorProductName }</a><span>X${item.quantity}</span></dd>
                        <dd class="ywsjBox">
	                        <c:if test="${order.tntOrder.visitTime==null }">
	                        ${order.tntOrder.visitTimeBegin }~${order.tntOrder.visitTimeEnd }
	                        </c:if>
	                        <c:if test="${order.tntOrder.visitTime!=null }">
	                        ${item.cnVisitTime }
	                        </c:if>
                        </dd>
                        <dd class="scjBox">${item.marketPriceYuan}</dd>
                        <dd class="lmmjBox">${item.priceYuan}</dd>
                        <dd class="fxjBox">${item.distPriceYuan}</dd>
                        <dd class="xjBox">${item.distPriceYuan*item.quantity}</dd>
                        <dd class="qtBox"></dd>
                    	</dl>
					</c:forEach> 
					<c:forEach items="${order.tntOrder.relativeOrderList }" var="item">
						<dl class="xq-fx-content-title tshuDl">
                        <dd class="cpmcBox"><a href="/product/${item.productId}">${item.shorProductName }</a><span>X${item.quantity}</span></dd>
                        <dd class="ywsjBox">
	                        <c:if test="${order.tntOrder.visitTime==null }">
	                        ${order.tntOrder.visitTimeBegin }~${order.tntOrder.visitTimeEnd }
	                        </c:if>
	                        <c:if test="${order.tntOrder.visitTime!=null }">
	                        ${item.cnVisitTime }
	                        </c:if>
                        </dd>
                        <dd class="scjBox">${item.marketPriceYuan}</dd>
                        <dd class="lmmjBox">${item.priceYuan}</dd>
                        <dd class="fxjBox">${item.distPriceYuan}</dd>
                        <dd class="xjBox">${item.distPriceYuan*item.quantity}</dd>
                        <dd class="qtBox"></dd>
                    	</dl>
					</c:forEach> 
					<c:forEach items="${order.tntOrder.additionalOrderList }" var="item">
						<dl class="xq-fx-content-title tshuDl">
                        <dd class="cpmcBox"><a href="/product/${item.productId}">${item.shorProductName }</a><span>X${item.quantity}</span></dd>
                        <dd class="ywsjBox">
	                        <c:if test="${order.tntOrder.visitTime==null }">
	                        ${order.tntOrder.visitTimeBegin }~${order.tntOrder.visitTimeEnd }
	                        </c:if>
	                        <c:if test="${order.tntOrder.visitTime!=null }">
	                        ${item.cnVisitTime }
	                        </c:if>
                        </dd>
                        <dd class="scjBox">${item.marketPriceYuan}</dd>
                        <dd class="lmmjBox">${item.priceYuan}</dd>
                        <dd class="fxjBox">${item.priceYuan}</dd>
                        <dd class="xjBox">${item.priceYuan*item.quantity}</dd>
                        <dd class="qtBox"></dd>
                    	</dl>
					</c:forEach>
            	</div>
                <div class="endjsBox">门市价：<span>&yen;${order.ordOrder.marketAmountYuan}</span>|共节省：<em>&yen;${order.saveAmountYuan}</em>|<b>订单结算总额：<i>&yen;${order.tntOrder.orderAmountYuan}</i></b></div>
            </div>
            
        </div>
</body>
</html>