<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
 		<title>绑定产品类型</title>
 		<script type="text/javascript">
		//选中某一个子类型
		function checkSubProductTypeOptions(arg, subProdTypeVal, date) {
	        $("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/checkSubProductTypeOptions.do?couponId=" + $("#couponId").val() +"&isCheckedSubProdType="+arg.checked + "&checkedSubProdTypeVal=" + subProdTypeVal + "&_=" + (new Date).getTime(),function(){
	        	$(this).dialog({
	        		modal:true,
	        		title:"绑定产品类型",
	        		width:950,
	        		height:650
	           	});	
	        });
		}
		
		//删除产品记录
		function deleteSingle(delAmount,delSubProductType,delProductType,couponProductId,date){
			if(confirm("确认做删除？")) {
		        $("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/deleteSubProductType.do?couponId=" + $("#couponId").val() + "&delAmount="+ delAmount + "&delSubProductType=" + delSubProductType + "&couponProductId=" + couponProductId+ "&_=" + (new Date).getTime(),function(){
		        	$(this).dialog({
		        		modal:true,
		        		title:"绑定产品类型",
		        		width:950,
		        		height:650
		           	});	
		        });
			}
		}
		   
		//修改优惠价格
		function updateSubTypeCouponAmount(amountMode,couponProductId,index){
			
			var updateCouponAmount = $("#couponAmount_" + index).val();
	    	if(updateCouponAmount == null || updateCouponAmount < 0){
	    		alert("输入数字,且不能为负!");
	    		return;
	    	}
	    	if(updateCouponAmount==""){
	    		updateCouponAmount=0;
	    	}
	    	if(amountMode == 'amountYuan'){
	    		 var reg = /^[0-9]*[1-9][0-9]*$/;
		    	 if(!reg.test(updateCouponAmount) || updateCouponAmount > 1000000){
		    		 alert("请输入0-1000000范围正整数金额");
					 return;
		    	 }
	    	}else{
	    		 var reg = /^(?:0|[1-9][0-9]?|100)$/; 
		    	 if(!reg.test(updateCouponAmount)){
		    		 alert("请输入0-100范围内正整数折扣，如输入78表示7.8折"); 
					 return; 
		    	 }
	    	} 
		    var url = "<%=basePath%>mark/coupon/ajaxUpdateSubTypeCouponAmount.do";
		    $.ajax({
		       url: url,
		       data: {"couponProductId":couponProductId,"${amountMode}":updateCouponAmount,"couponId":$("#couponId").val()},
		       dataType:"json",
			   success: function(result) {
					if (result.success) {
					     alert("修改成功!");
					} else {
					     alert("修改失败!");
					}
			     }
		     });
		}
		</script>	
			
	</head>
	<body>
		<s:hidden key="couponId" name="couponId" id="couponId"/>
		<div class="p_box">
			<table class="p_table table_center">
				<tr>
					<th>产品类型</th> 
					<th>产品子类型</th> 
					<th>关联优惠金额/折扣(请正确填写整数（非负值）折扣，如输入78表示7.8折)</th>
					<th>操作</th>
				</tr>
				<s:iterator value="subProductTypeEleList" var="productTypeEle" status="c">
					<tr id="tr_<s:property value="#c.index"/>">
						<td><s:property value="productType" /></td>
						<td><s:property value="subProductTypeNames" /></td>
						
						<s:if test="amountMode == 'amountYuan'">
							<td><input type="text" id="couponAmount_<s:property value="#c.index"/>"   value="${amountYuan}"/></td>
						</s:if>
						<s:else>
							<td><input type="text" id="couponAmount_<s:property value="#c.index"/>"   value="${amount}"/></td>
						</s:else>
						
						<td>
							<a permCode="1307" href="javascript:updateSubTypeCouponAmount('${amountMode}','${couponProductIds}','<s:property value="#c.index"/>');">
								<s:if test="amountMode == 'amountYuan'">
								修改优惠价格
								</s:if>
								<s:else>
								修改优惠折扣
								</s:else>
							</a>
							<a permCode="1307" href="javascript:deleteSingle('${amount}','${subProductType}','${productType}','${couponProductIds}',(new Date).getTime());">删除</a>
						</td>
					</tr>
		  		</s:iterator>
			</table>
		</div>
		<div>
		<s:if test='errorMessageFlag=="onlyBoundOrder"'>
			该优惠券只能绑定订单.
		</s:if>
		<s:if test='errorMessageFlag=="boundedSubprod"'>
			优惠已经绑定了该子类型 下的产品.
		</s:if>
		</div>
		<div class="iframe-content">
			<table class="table" width="100%">
				<tr >
					<td class="p_label" width="15%">门票类型：</td>
					<td>
						<s:iterator value="ticketCodeItemList" var="codeItem" status="c">					
							<input id="ticket_<s:property value="#c.index"/>" onClick="checkSubProductTypeOptions(this,'${codeItem.code}',(new Date).getTime());" type="checkbox" <s:if test="checked"> checked="checked" </s:if> >
							${name}&nbsp;&nbsp;
						</s:iterator>
					</td>
				</tr>
				<tr>
					<td class="p_label" width="15%">酒店类型：</td>
					<td>
						<s:iterator value="hotelCodeItemList" var="codeItem" status="c">
							<input id="hotel_<s:property value="#c.index"/>" onClick="checkSubProductTypeOptions(this,'${codeItem.code}',(new Date).getTime());" type="checkbox" <s:if test="checked"> checked="checked" </s:if> >
							${name}&nbsp;&nbsp;
					 	</s:iterator>
					 </td>			
				</tr>
				<tr>
					<td class="p_label" width="15%">线路类型：</td>
					<td>
						<s:iterator value="routeCodeItemList" var="codeItem" status="c">
							<input id="route_<s:property value="#c.index"/>" onClick="checkSubProductTypeOptions(this,'${codeItem.code}',(new Date).getTime());" type="checkbox" <s:if test="checked"> checked="checked" </s:if> >
							${name}&nbsp;&nbsp;
						</s:iterator>
					</td>
				</tr>
				<!-- <tr>
					<td class="p_label" width="15%">其他类型：</td>
					<td>
						<s:iterator value="otherCodeItemList" var="codeItem" status="c">
							<input id="other_<s:property value="#c.index"/>" onChange="checkSubProductTypeOptions(this,'${codeItem.code}');" type="checkbox" <s:if test="checked"> checked="checked" </s:if> >
							${name}&nbsp;&nbsp;
						</s:iterator>
					</td>
				</tr>
				-->
			</table>
			<p class="tc mt20"><button class="btn btn-small w5" type="button" onClick='javascript:$("#newMarkCouponDiv").dialog("close")'>关闭</button>　</p>
		</div>	
	</body>
</html>
