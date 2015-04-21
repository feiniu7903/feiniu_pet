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
                    待退款订单总笔数：<b>${num }</b> 笔
                    <em>待退款订单总金额：<i>${orderRefundCount }元</i></em>
                </div>
            	<div class="search-itemBox">
                	<dl class="title">
                        <dd class="cpmcBox">产品名称</dd>
                        <dd class="jeBox">金额（元）</dd>
                        <dd class="ddztBox">订单状态</dd>
                        <dd class="htztBox">扣损费</dd>
                        <dd class="jsztBox">退款金额</dd>
                        <dd class="czBox">操作</dd>
                    </dl><!--title end-->
                    <ul class="itemList">
                    <c:if test="${orderList!=null}">
	     <c:forEach var="ord" items="${orderList}">
	     	<li>
	             <dl class="itemNrList">
	                 <dd class="ddhBox">订单号：${ord.ordOrder.orderId }</dd>
	                 <dd class="xdsjBox">下单时间：${ord.ordOrder.zhCreateTime }</dd>
	                 <dd class="zffsBox">支付方式：<c:if test='${ord.ordOrder.paymentTarget=="TOLVMAMA" }'>在线支付</c:if><c:if test='${ord.ordOrder.paymentTarget!="TOLVMAMA" }'>景区支付</c:if></dd>
	             </dl><!--itemNrList end-->
	             <dl class="cpList">
	                 <dd class="cpmcBox">
	                 	<a href="http://www.lvmama.com/product/${ord.ordOrder.mainProduct.productId}" target="_blank" title="${ord.tntOrder.productName}">${ord.tntOrder.shorProductName}</a><br/>
	                 </dd>
	                 <dd class="jeBox">${ord.tntOrder.orderAmountYuan}</dd>
	                 <dd class="ddztBox">${ord.ordOrder.zhOrderStatus }<br/><a class="status detail-link" href="${base}/mOrder/detail.do?orderId=${ord.ordOrder.orderId}" onclick="return showDetail(this);">订单详情</a></dd>
	                 <dd class="htztBox">
	                 	${ord.tntOrder.lossAmount }
	                 </dd>
	                 <dd class="jsztBox">${ord.tntOrder.refundAmountYuan}</dd>
	                 <dd class="czBox">
	                 <a href="${base}/mOrder/refund.do?orderId=${ord.ordOrder.orderId}"  class="ui-btn ui-btn4" onclick="return refund(this);"><i>&nbsp;立即退款&nbsp;</i></a>
	                 </dd>
	             </dl><!--cpList end-->
	         </li>
	     </c:forEach>
     </c:if>
                    </ul><!--itemList end-->
                </div>
      <c:if test="${page!=null }">
			<div class="unrefundmentClass_pags">
				${page.pagination}
			</div>
	</c:if>  
</body>
<script type="text/javascript">
function refund(obj){
	if(confirm("确定退款吗？")){
		var url=obj.href;
		$.ajax({
			type : "get",
			url : url,
			success : function(response) {
				alert(response.errorText);
				if(response.success){
					query("/mOrder/unrefund/query.do",".unrefundOrderList");
				}
			},
			error:function(er){
				alert("退款失败！请稍后重试");
			}
		});
	}
	return false;
}
$(".unpayClass_pags").children().children().find("a").click(function(){
	if($(this).attr("href")!="#"){
		query($(this).attr("href"),".unrefundOrderList");
	}
	return false;
});
</script>
</html>