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
	<div class="tiptext tip-warning order-tip-warning">
                    <span class="tip-icon tip-icon-warning"></span>
                    待支付订单总笔数：<b>${num }</b> 笔
                    <em>待支付订单总金额：<i>${orderAmountCount }元</i></em>
                </div>
            	<div class="search-itemBox">
                	<dl class="title">
                        <dd class="cpmcBox">产品名称</dd>
                        <dd class="jeBox">金额（元）</dd>
                        <dd class="ddztBox">订单状态</dd>
                        <dd class="htztBox">合同状态</dd>
                        <dd class="czBox">操作</dd>
                        <dd class="jsztBox">结算状态</dd>
                    </dl><!--title end-->
                    <dl class="itemNrList" style="height:40px;">
                        <dd style="margin-left:20px;"><input class="checkBox" id="mergePayCheckBox" name="mergePayCheckBox" type="checkbox"  ></dd>
                        <dd class="ddhBox"><div class="ach-box btnbg"><a class="btn cbtn-orange btn-middle" onclick="mergePay()">合并付款</a></div></dd>
                    	<!-- <dd class="ddhBox"><div class="ach-box btnbg"><a class="btn cbtn-default btn-middle">合并开票</a></div></dd> -->
                    </dl>
                    <ul class="itemList">
                    <c:if test="${orderList!=null}">
	     <c:forEach var="ord" items="${orderList}">
	     	<li>
	             <dl class="itemNrList">
	                 <dd style="margin-left:20px;"><input class="checkBox" name="orderId" value="${ord.ordOrder.orderId }" type="checkbox"  ></dd>
	                 <dd class="ddhBox">订单号：${ord.ordOrder.orderId }</dd>
	                 <dd class="xdsjBox">下单时间：${ord.ordOrder.zhCreateTime }</dd>
	                 <dd class="zffsBox">支付方式：<c:if test='${ord.ordOrder.paymentTarget=="TOLVMAMA" }'>在线支付</c:if><c:if test='${ord.ordOrder.paymentTarget!="TOLVMAMA" }'>景区支付</c:if></dd>
	                 <dd class="djsBox"><em class="itemClock"></em>离最晚支付时间还剩：${ord.waitPaymenTime }</dd>
	             </dl><!--itemNrList end-->
	             <dl class="cpList">
	                 <dd class="cpmcBox">
	                 	<a href="http://www.lvmama.com/product/${ord.ordOrder.mainProduct.productId}" target="_blank" title="${ord.tntOrder.productName}">${ord.tntOrder.shorProductName}</a><br/>
	                 </dd>
	                 <dd class="jeBox">${ord.tntOrder.orderAmountYuan}</dd>
	                 <dd class="ddztBox">未支付<br/><a class="status detail-link" href="${base}/mOrder/detail.do?orderId=${ord.ordOrder.orderId}" onclick="return showDetail(this);">订单详情</a></dd>
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
                    </ul><!--itemList end-->
                </div>
	 <c:if test="${page!=null }">
			<div class="unpayClass_pags">
				${page.pagination}
			</div>
	</c:if>                    
</body>
<script type="text/javascript">
$("#mergePayCheckBox").click(function (){
	if($('#mergePayCheckBox').attr("checked")){
	    $("[name='orderId']").each(function(){
			if($(this).attr("disabled")!="disabled"){
				$(this).attr("checked",'true');	
			}
		});
	}
	else{
		$("[name='orderId']").removeAttr("checked");
	}
});

$(".unpayClass_pags").children().children().find("a").click(function(){
	if($(this).attr("href")!="#"){
		query($(this).attr("href"),".unpayOrderList");
	}
	return false;
});
function mergePay(){
	var orderIds="";
    $("input[name='orderId']:checkbox").each(function(){ 
        if($(this).attr("checked")){
        	orderIds += $(this).val()+",";
        }
    });
    if(orderIds!=""){
    	orderIds=orderIds.substring(0,orderIds.length-1);
    }
    else{
    	alert("没有可以合并支付的订单!");
    	return ;
    }
    window.location.href="${base}/order/mergePay.do?orderIds="+orderIds;
}

var cancelOrder = function(obj) {
	if(confirm("确定取消订单吗?")){
		var url=obj.href;
		$.ajax({
			url :url,
			type : "get",
			dataType : "json",
			success : function(response) {
				alert("取消订单成功");
				query("/mOrder/unpay/query.do",".unpayOrderList");
			},
			error : function() {
				alert("取消订单失败");
			}
		});
	}
};
</script>
</html>