<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	<SCRIPT type="text/javascript">
 	function removeCoupon(id,productId,couponProductId){
 		$.ajax( {
		type : "POST",
		dataType : "json",
		url : "<%=basePath%>/shoppingCard/removeCoupon.do",
		async : false,
		data : {couponId:id,productId:productId,couponProductId:couponProductId},
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
			if (data.jsonMsg=="ok"){
				$("#choseCoupon").reload();
				$("#price1").loadUrlHtml();
				$("#price2").loadUrlHtml();
			}
		}
	});
 	}
 	</SCRIPT>
  </head>
  
  <body>
  <div class="ordersuml">
   <em>已选择的优惠活动：</em>
  <s:iterator value="listCouponProductModel">
  
  <div><font style="font-weight: bold;"><s:property value="coupon.couponName"/></font> 
  优惠金额:<font color="red" style="font-weight: bold;">
  ￥<s:property value="-coupon.amount/100"/></font>X
  <font color="red" style="font-weight: bold;"><s:property value="productCodeUseQuantity"/> </font>
  <input type="button" value="取消" onclick="removeCoupon('${coupon.couponId}','${product.productId}','${markCouponProduct.couponProductId}')"/> <s:property value="productName"/></div>
  </s:iterator>
  </div>
  </body>
</html>
