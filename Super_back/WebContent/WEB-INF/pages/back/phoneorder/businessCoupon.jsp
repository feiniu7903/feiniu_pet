<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript">
		$(function(){	
			//展示优惠系统信息
			var productBranchId;
			var productCouponType;
			var productBranchName;
			var displayInfo;
			//var data = ${priceInfo.validateBusinessCouponInfoList};
			//alert(data);
			var validateBusinessCouponInfoList=${validateBusinessCouponInfoJSON};
			$("#earlyCouponShow").hide();
			$("#moreCouponShow").hide();
			if(validateBusinessCouponInfoList == null || validateBusinessCouponInfoList.length == 0){
				$("#businessCouponDisplayInfo").hide();
			}else{
				$("#earlyCoupon").html("");
				$("#moreCoupon").html("");
				for(var i = 0; i < validateBusinessCouponInfoList.length ; i++){
					productBranchId = validateBusinessCouponInfoList[i].productBranchId;
					productCouponType = validateBusinessCouponInfoList[i].couponType;
					displayInfo = validateBusinessCouponInfoList[i].displayInfo;
					if($("#businessCouponBranchId"+productBranchId).val() != null && $("#businessCouponBranchId"+productBranchId).val() != "" ){
						productBranchName = $("#businessCouponBranchId"+productBranchId).val();
						if(productCouponType == "EARLY"){
							$("#earlyCouponShow").show();
							$("#earlyCoupon").html($("#earlyCoupon").html()+"<p>"+productBranchName+displayInfo+"</p>");
						}else if(productCouponType == "MORE"){
							$("#moreCouponShow").show();
							$("#moreCoupon").html($("#moreCoupon").html()+"<p>"+productBranchName+displayInfo+"</p>");
						}
					}
				}
				$("#businessCouponDisplayInfo").show();
			}
			
		});
		</script>
	</head>
	<body>
		<div>
		<div class="preferential"><b>可享优惠:</b></div>
			<p>&nbsp;</p>
			<div id="businessCouponDisplayInfo" style="display:none;margin-left:10px">
				<table>
					<tr id="earlyCouponShow">
						<td>早订早惠:</td>
						<td>
							<span id="earlyCoupon"></span>
						</td>
					</tr>
					<tr id="moreCouponShow">
						<td>
							多订多惠:
						</td>
						<td>
							<span id="moreCoupon"></span>
						</td>
					</tr>
				</table>
				<p>&nbsp;</p>
				<div>
					温馨提示：若您在下单后对出游时间或出游人数进行变更，将无法享受早订早惠或多订多惠。
				</div>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
		</div>
	<script type="text/javascript">
	</script>
	</body>
</html>


