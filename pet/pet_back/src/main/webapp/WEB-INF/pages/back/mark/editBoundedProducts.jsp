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
	 	<title>修改绑定产品</title>
	 	
		<script type="text/javascript">
		
			function editBoundedProductSearch() {
				var arr = [];
				$("#editBoundedProductsearchForm").find("input,select").each(function(){
				    arr.push(this.name+'='+encodeURIComponent(this.value));
				});
				var data = arr.join("&");
				$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/editBindingProduct.do?"+data,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改绑定产品",
	        			width:950,
	        			height:650
	            	});	
	        	});				
			}
		
			//删除产品记录
		    function deleteSingle(couponProductId, selectIndex)
		    {
				if(confirm("确认做删除？")){
		          	 var url = "<%=basePath%>mark/coupon/ajaxDeleteByCouponProductId.do";
		        	 $.ajax({
		        	 	url: url,
		        	 	data: {"couponProductId":couponProductId},
		        	 	dataType:"json",
		        	 	success: function(result) {
			        		if (result.success) {
			        			alert("删除产品成功!");
			        			
			        			//删除选中的行
			        			var tr = document.getElementById('tr_'+selectIndex);
			        			tr.parentNode.removeChild(tr);
			        		} else {
			        			alert("删除产品失败!");
			        		}
		        	 	}
		        	 });
				}
		    }
			
		    //批量操作多条记录
		    function batchDelete()
		    {
		    	var couponProductIds = "";
		    	var selectIndexList = new Array();
		    	
				for(var i = 0; i < 10; i++)
				{
					if($("#checkBox"+i) != null && $("#checkBox"+i).attr("checked")==true)
					{
						couponProductIds += $("#checkBox"+i).val() + ",";
						selectIndexList.push(i);
					}
				}
				if(confirm("确认删除选中的产品么？")){
		          	 var url = "<%=basePath%>mark/coupon/ajaxBatchDelete.do";
		        	 $.ajax({
		        	 	url: url,
		        	 	data: {"couponProductIds":couponProductIds},
		        	 	dataType:"json",
		        	 	success: function(result) {
			        		if (result.success) {
			        			alert("删除选中产品成功!");
			        			
			        			//删除选中的行
			        			for(var i = 0 ;i < selectIndexList.length; i++){
			        				var tr = document.getElementById('tr_'+selectIndexList[i]);
			        				tr.parentNode.removeChild(tr);
			        			}
			        		} else {
			        			alert("删除选中产品失败!");
			        		}
		        	 	}
		        	 });
				}
		    }
		    
		    //操作所有记录
		    function batchAllDelete()
		    {
		    	if(confirm("确定删除符合条件的所有产品吗?")){
			        var url = "<%=basePath%>mark/coupon/ajaxDeleteAllProds.do";
			       $.ajax({
			        	url: url,
			        	data: {"couponId":$("#couponId").val(),"productName":$("#productName").val(),"productId":$("#productId").val()},
			        	dataType:"json",
				        success: function(result) {
						     if (result.success) {
						         alert("删除产品成功!");
						         $("#newMarkCouponDiv").dialog("close");
						     } else {
						        alert("删除产品失败!");
						    }
				       	}
			       });
		    	}
		    }
		    
		    //修改优惠价格
		    function updateCouponAmount(amountMode, couponProductId, selectIndex)
		    { 
		    	var updateCouponAmount = $("#couponAmount_" + selectIndex).val();
		    	if(updateCouponAmount == null || updateCouponAmount < 0){
		    		alert("输入数字,且不能为负!");
		    		return;
		    	}
		    	if(updateCouponAmount==""){
		    		updateCouponAmount = 0;
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
		    	
			    var url = "<%=basePath%>mark/coupon/ajaxUpdateCouponAmount.do";
			    $.ajax({
			       url: url,
			       data: {"couponProductId":couponProductId,"${amountMode}":updateCouponAmount},
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
		    
		    function refreshBoundedProductList(url) {
				$("#newMarkCouponDiv").load(url,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改绑定产品",
	        			width:950,
	        			height:650
	            	});	
	        	});		    	
		    }
		    
		</script>
	</head>
	<body>
		<div class="iframe-content">
			<div class="p_box">
				<form id="editBoundedProductsearchForm" action="editBindingProduct.do" method="post">
					<input type="hidden" value="${couponId }" name="couponId" id="couponId"/>
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label" width="15%">商品名称：</td>
							<td>
								<input name="productName" id="productName"  value="${productName}" class="newtext1"/>
							</td>
							<td class="p_label" width="15%">商品ID：</td>
							<td>
								<input name="productId" id="productId" value="${productId}" cssClass="newtext1"/>
							</td>
						</tr>
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="button" onClick="editBoundedProductSearch()">查询</button>　</p>
				</form>
			</div>
		

			<div class="p_box">
				<table class="p_table table_center">
						<tr>
							<th width="10%"></th>
							<th>商品ID</th>
							<th width="30%">商品名称</th>
							<th>关联优惠金额/折扣(请正确填写整数（非负值）折扣，如输入78表示7.8折)</th>
							<th>操作</th>
						</tr>
						<s:iterator value="markCouponProdList" var="prod" status="c">
							<tr id="tr_<s:property value="#c.index"/>">
							    <td><input type="checkbox" id="checkBox<s:property value="#c.index"/>"  value="${couponProductId}"/></td>
								<td><a href="http://www.lvmama.com/product/${productId}" target="_blank"><u>${productId}</u></a></td>
								<td><s:property value="productName" /></td>
								
						<s:if test="amountMode == 'amountYuan'">
						<td><input type="text" id="couponAmount_<s:property value="#c.index"/>" name="couponAmount"  value="${amountYuan}"/></td>
						</s:if>
						<s:else>
						<td><input type="text" id="couponAmount_<s:property value="#c.index"/>" name="couponAmount"  value="${amount}"/></td>
						</s:else>
								<td>
									<a permCode="1307" href="javascript:updateCouponAmount('${amountMode}', '${couponProductId}', '<s:property value="#c.index"/>');">				
									<s:if test="amountMode == 'amountYuan'">
									修改优惠价格
									</s:if>
									<s:else>
									修改优惠折扣
									</s:else>
									</a>
									<a permCode="1307" href="javascript:deleteSingle(${couponProductId},<s:property value="#c.index"/>);">删除</a>
								</td>
							</tr>
						  </s:iterator>
						  <tr>
							<td colspan="2">总条数：<s:property value="pagination.totalResultSize" />
							</td>
							<td colspan="6" align="right">
								<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage,'js')"/>
							</td>
						  </tr>
				</table>
				<p class="tc mt20">
					<input type="button"  class="btn btn-small w5"  id="saveSelectedProductCoupon" onClick="batchDelete()" value="删除选中的产品">
					<input type="button"  class="btn btn-small w5"  id="saveAllProductCoupon" onClick="batchAllDelete()"  value="自动删除查询结果的所有产品">
				</p>
			</div>
		</div>
	</body>
</html>
