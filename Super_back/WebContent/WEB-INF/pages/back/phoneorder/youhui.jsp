<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<script type="text/javascript">
function useCoupon(obj) {
	var button = $(obj);
	var p = button.parent();
	var code = p.find("#code").val();
	var userId = $("#userId").val();
	var cid = p.find("#couponId").val();
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/choseCoupon.do",
		async : false,
		data : {
			id : cid,
			code : code,
			userId : userId
		},
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
			if (data.success) {
				doOperator("businessCoupon", "");
				doOperator("orderAmount", "");
			}
			alert(data.info);
		}
	});

}
</script>
	</head>

	<body>
		<div style="margin-left:10px">
		
			<!-- 修复bug:后台销售产品录入时，如果勾选了不能使用优惠券，则后台下单时不会出现优惠券输入框。 -->
			<s:if test="showYouHui == true && (product.couponAble =='true' || product.couponAble == null || product.couponAble == '')">
				<div id="useCode">
					<s:hidden name="productId" id="productId"></s:hidden>
					<s:hidden name="productName" id="productName"></s:hidden>
					<input name="couponCode" type="text" id="code" />
					<input type="button" class="button" value="验证优惠券"
						onclick="useCoupon(this)" />
					<span id="info" style="color: red; font-weight: bold;"></span>
				</div>
			</s:if>
			<s:else>
				<p>
				<span>此产品目前不适用优惠券</span>
				</p>
			</s:else>
			<br/>
			<strong>可参与活动:</strong>
			<s:if test="showYouHui == true && (product.couponActivity=='true' || product.couponActivity == null || product.couponActivity == '')">
				<div>
					<s:iterator value="partyCouponList">
						<div>
							<input name="choseParty" type="radio" onClick="useCoupon(this)" />
							${couponName}
							<br />
							<input id="couponId" type="hidden" value="${couponId}" />
							<input name="code" id="code" type="hidden" value="${couponCode}" />
						</div>
					</s:iterator>
				</div>
			</s:if>
			<s:else>
				<p>
				<br/>
				<span>此产品目前不适用优惠活动</span>
				</p>
			</s:else>
		</div>
	</body>
</html>

