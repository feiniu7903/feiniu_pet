<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<dl class="title">
                   <dd class="cpmcBox">产品名称</dd>
                   <dd class="jeBox">金额（元）</dd>
                   <dd class="ddztBox">订单状态</dd>
                   <dd class="htztBox">合同状态</dd>
                   <dd class="czBox">操作</dd>
                   <dd class="jsztBox">结算状态</dd>
               </dl><!--title end-->
     <ul class="itemList">
     <c:if test="${orderList!=null}">
	     <c:forEach var="ord" items="${orderList}">
	     	<li>
	             <dl class="itemNrList">
	                 <dd class="ddhBox">订单号：${ord.ordOrder.orderId }</dd>
	                 <dd class="xdsjBox">下单时间：${ord.ordOrder.zhCreateTime }</dd>
	                 <dd class="zffsBox">支付方式：<c:if test='${ord.ordOrder.paymentTarget=="TOLVMAMA" }'>在线支付</c:if><c:if test='${ord.ordOrder.paymentTarget!="TOLVMAMA" }'>景区支付</c:if></dd>
	                 <dd class="djsBox">
	                 	<c:if test="${ord.ordOrder.canToPay || ord.ordOrder.canToPrePay}">
				       		<em class="itemClock"></em>
				       		离最晚支付时间还剩：${ord.waitPaymenTime }
				    	</c:if>
	                 </dd>
	             </dl><!--itemNrList end-->
	             <dl class="cpList">
	                 <dd class="cpmcBox">
	                 	<a href="http://www.lvmama.com/product/${ord.ordOrder.mainProduct.productId}" target="_blank" title="${ord.tntOrder.productName}">${ord.tntOrder.shorProductName}</a><br/>
	                 </dd>
	                 <dd class="jeBox">${ord.tntOrder.orderAmountYuan}</dd>
	                 <dd class="ddztBox">${ord.ordOrder.zhOrderStatus }<br/><a class="status detail-link" href="${base}/mOrder/detail.do?orderId=${ord.ordOrder.orderId}" onclick="return showDetail(this);">订单详情</a></dd>
	                 <dd class="htztBox">
	                 	<c:if test="${ord.ordOrder.needEContract }">
					    	<c:if test="${ord.ordOrder.payToLvmama && !ord.ordOrder.paymentSucc && ord.ordOrder.approvePass && ord.ordOrder.expireToPay}">已作废</c:if>
					    	<c:if test="${ord.ordOrder.canceled}">已作废</c:if>
					    	<c:if test="${!ord.ordOrder.eContractConfirmed}">未签约</c:if>
							<c:if test="${ord.ordOrder.eContractConfirmed && (!ord.ordOrder.paymentSucc || (ord.ordOrder.needResourceConfirm && !ord.ordOrder.approvePass))}">已签约未生效</c:if>
							<c:if test="${ord.ordOrder.paymentSucc}">已生效</c:if>
						</c:if>
						&nbsp;
	                 </dd>
	                 <dd class="czBox">
	                 <c:if test="${ord.ordOrder.canToPay || ord.ordOrder.canToPrePay}">
				       <p><a href="${base}/order/mergePay.do?orderIds=${ord.ordOrder.orderId}"  class="ui-btn ui-btn4"><i>&nbsp;立即支付&nbsp;</i></a></p>
				    </c:if> 
				    <c:if test="${ord.ordOrder.orderStatus=='NORMAL' and ord.ordOrder.paymentStatus=='UNPAY'}"> 
				       <p><a href="${base}/mOrder/cancelOrder.do?orderId=${ord.ordOrder.orderId}&tntOrderId=${ord.tntOrder.tntOrderId}" onclick="cancelOrder(this); return false;" class="ui-btn ui-btn4"><i>&nbsp;取消订单&nbsp;</i></a></p>
				    </c:if>
				    &nbsp;
	                 </dd>
	                 <dd class="jsztBox">${ord.tntOrder.cnSettleStatus}</dd>
	             </dl><!--cpList end-->
	         </li>
	     </c:forEach>
     </c:if>
     </ul>
     <c:if test="${page!=null }">
			<div class="allClass_pags">
				${page.pagination}
			</div>
	</c:if>
</body>
<script type="text/javascript">
function getQueryStr(str,url){ 
    var rs = new RegExp("(^|)"+str+"=([^\&]*)(\&|$)","gi").exec(url),tmp;
    if(tmp!=rs){    
        return rs[2];    
    }    
    return "1";    
}  

$(".allClass_pags").children().children().find("a").click(function(){
	if($(this).attr("href")!="#"){
		$("#searchPage").val(getQueryStr("page",$(this).attr("href")));
		searchAllOrder();
	}
	return false;
});
var cancelOrder = function(obj) {
	if(confirm("确定取消订单吗?")){
		var url=obj.href;
		$.ajax({
			url :url,
			type : "get",
			dataType : "json",
			success : function(response) {
				alert("取消订单成功");
				searchAllOrder();
			},
			error : function() {
				alert("取消订单失败");
			}
		});
	}
};
</script>
</html>