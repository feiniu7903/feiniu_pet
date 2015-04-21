<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<link href="http://super.lvmama.com/super_back/themes/cc.css"
	rel="stylesheet" type="text/css" />
<script>
	//显示弹出层
	function showDetailDiv(divName, orderId) {
		document.getElementById(divName).style.display = "block";
		document.getElementById("bg").style.display = "block";
		//请求数据,重新载入层
		$("#" + divName).reload({"orderId":orderId});
	}
</script>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<div class="p_box">
	<table class="p_table table_center">
		<thead>
			<tr>
				<th>主站订单号</th>
				<th>分销商订单号</th>
				<th>联系人姓名</th>
				<th>联系人电话</th>
				<th>产品名称</th>
				<th>订购数量</th>
				<th>订单金额</th>
				<th>订单状态</th>
				<th>资源状态</th>
				<th>支付状态</th>
				<th>履行状态</th>
				<th>分销结算状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="order" items="${tntOrderList}">
				<tr>
					<td>${order.orderId }</td>
					<td>${order.partnerOrderId }</td>
					<td>${order.contactName }</td>
					<td>${order.contactMoblie }</td>
					<td><c:forEach items="${order.mainOrderList }" var="item">
						<p>${item.productName }</p>
						</c:forEach> <c:forEach items="${order.relativeOrderList }" var="item">
							<p>${item.productName }</p>
						</c:forEach> <c:forEach items="${order.additionalOrderList }" var="item">
							<p>${item.productName }</p>
						</c:forEach>
					</td>
					<td><c:forEach items="${order.mainOrderList }" var="item">
						<p>${item.quantity}</p>
						</c:forEach> <c:forEach items="${order.relativeOrderList }" var="item">
							<p>${item.quantity}</p>
						</c:forEach> <c:forEach items="${order.additionalOrderList }" var="item">
							<p>${item.quantity}</p>
						</c:forEach>
					</td>
					<td>${order.orderAmount/100 }</td>
					<td>${order.cnOrderStatus }</td>
					<td>${order.cnApproveStatus }</td>
					<td>${order.cnPaymentStatus }</td>
					<td>${order.cnPerformStatus }</td>
					<td>${order.cnSettleStatus }</td>
					<td><a
						href="javascript:showDetailDiv('historyDiv', '${order.orderId}')">查看详情</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="orderpop" id="historyDiv" style="display: none;"
		href="/tnt_back/order/showHistoryOrderDetail.do"></div>
	<div id="bg" class="bg" style="display: none;">
		<iframe
			style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                             0); opacity =0; border-style: none; z-index: -1">
		</iframe>
	</div>
	<c:if test="${page!=null }">
		<div class="paging order_pages">
			<p class="page_msg cc3">
				共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
			</p>
			<div class="paging">${page.pagination}</div>
		</div>
	</c:if>
</div>
<script type="text/javascript">

	$(".order_pages").children().children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			$("#page").val(getQueryStr("page",$(this).attr("href")));
			search();
		} 
		return false;
	});
	
    function getQueryStr(str,url){ 
        var rs = new RegExp("(^|)"+str+"=([^\&]*)(\&|$)","gi").exec(url),tmp;
        if(tmp!=rs){    
            return rs[2];    
        }    
        return "1";    
    }    

</script>