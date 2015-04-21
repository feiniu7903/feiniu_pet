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
 		<title>绑定产品</title>
	</head>
	<body>
		<div class="iframe-content">
			<div class="p_box">
				<form id="addProductSearchForm" action="addProduct.do" method="post">
					<input type="hidden" value="${couponId}" name="couponId" id="couponId"/>
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label" width="15%">商品名称：</td>
							<td>
								<input name="productName" id="productName" value="${productName}" class="newtext1"/>
							</td>
							<td class="p_label" width="15%">商品编号：</td>
							<td>
								<input name="proudctBizcode" id="proudctBizcode" value="${proudctBizcode}" class="newtext1"/>
							</td>
							<td class="p_label" width="15%">商品ID：</td>
							<td>
								<input name="productId" id="productId"  value="${productId}" class="newtext1"/>
							</td>	
						</tr>
						<tr>
							<td class="p_label" width="15%">商品类型：</td>
							<td colspan="5">
								门票 &nbsp;&nbsp;
								<select id="ticketSlct" name="ticketSlct">
									<option value="" <s:if test="\"\"== #request.ticketSlct"> selected="selected"</s:if>>全部</option>
									<s:iterator value="ticketCodeItemList" var="codeItem" status="c">
										<option value="${codeItem.code}" <s:if test="code== #request.ticketSlct"> selected="selected"</s:if>>${name}</option>
									</s:iterator>
								</select>
								酒店&nbsp;&nbsp;
								<select id="hotelSlct" name="hotelSlct">
									<option value="" <s:if test="\"\"== #request.hotelSlct"> selected="selected"</s:if>>全部</option>
									<s:iterator value="hotelCodeItemList" var="codeItem" status="c">
										<option value="${codeItem.code}" <s:if test="code== #request.hotelSlct"> selected="selected"</s:if>>${name}</option>
									</s:iterator>
								</select>
								<br/>
								线路&nbsp;&nbsp;
								<select id="routeSlct" name="routeSlct">
									<option value=""  <s:if test="\"\"== #request.routeSlct"> selected="selected"</s:if>>全部</option>
									<s:iterator value="routeCodeItemList" var="codeItem" status="c">
										<option value="${codeItem.code}" <s:if test="code== #request.routeSlct"> selected="selected"</s:if>>${name}</option>
									</s:iterator>
								</select>
								其他&nbsp;&nbsp;
								<select id="otherSlct" name="otherSlct">
									<option value=""  <s:if test="\"\"== #request.otherSlct"> selected="selected"</s:if>>全部</option>
									<s:iterator value="otherCodeItemList" var="codeItem" status="c">
										<option value="${codeItem.code}" <s:if test="code== #request.otherSlct"> selected="selected"</s:if>>${name}</option>
									</s:iterator>
								</select>
							</td>	
						</tr>	
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="button" onclick="addProductSubmit()">查询</button>　</p>
				</form>			
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="6%" align="center">选择</th>
						<th>商品ID</th>
						<th>商品编号</th>
						<th width="50%">商品名称</th>
						<th width="10%">商品类型</th> 
						<th width="10%">商品子类型</th>
					</tr>
					<s:iterator value="productList" var="prod" status="c">
						<tr>
						    <td><input type="checkbox" id="checkBox<s:property value="#c.index"/>"  value="${productId}"/></td>
							<td><a href="http://www.lvmama.com/product/${productId}" target="_blank"><u>${productId}</u></a></td>
							<td><s:property value="bizcode" /></td>
							<td width="15%"><s:property value="productName" /></td>
							<td><s:property value="zhProductType" /></td>
							<td><s:property value="zhSubProductType" /></tr>
						</tr>
					</s:iterator>
			  		<tr>
						<td colspan="2">总条数：<s:property value="pagination.totalResultSize" /></td>
						<td colspan="5" align="right">
							<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage,'js')"/>
						</td>
			  		</tr>
				</table>
			</div>
		</div>
		<p class="tc mt20">
			<button id="saveSelectedProductCoupon" class="btn btn-small" type="button" onClick="saveSelectedProductCoupon()">绑定选择的产品</button> 
			<button id="saveAllProductCoupon" class="btn btn-small" type="button" onClick="saveAllProductCoupon()">自动绑定查询结果的所有产品</button>
		</p>
		
		<script type="text/javascript">
			function refreshProductList(url) {
	        	$("#newMarkCouponDiv").load(url,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"绑定产品",
	        			width:950,
	        			height:700
	            	});	
	        	});				
			}
			//查询
			function addProductSubmit() {
				var arr = [];
				$("#addProductSearchForm").find("input,select").each(function(){
				    arr.push(this.name+'='+encodeURIComponent(this.value));
				});
				var data = arr.join("&");
				$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/addProduct.do?" + data,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"绑定产品",
	        			width:950,
	        			height:700
	            	});	
	        	});				
			}
			 //绑定产品记录
		    function saveProductCoupon(pidList)
		    {
				var couponId = $("#couponId").val();
				if(pidList == ""){
					alert("请选择绑定的产品");
					return;
				}
				if(confirm("是否确认操作？")){
		          	 var url = "<%=basePath%>mark/coupon/ajaxSaveProductCoupon.do";
		        	 $.ajax({
		        	 	url: url,
		        	 	data: {"pidListStr":pidList,"couponId":couponId},
		        	 	dataType:"json",
		        	 	success: function(result) {
			        		if (result.success) {
			        			alert("绑定产品成功!");
			        		} else {
			        			alert("绑定产品失败!");
			        		}
		        	 	}
		        	 });
				}
		    }
			
		    //批量操作多条记录
		    function saveSelectedProductCoupon()
		    {
		    	var pidList = "";
				for(var i = 0; i < 10; i++)
				{
					if($("#checkBox"+i) != null && $("#checkBox"+i).attr("checked")==true)
					{
						pidList += $("#checkBox"+i).val() + ",";
					}
				}
				saveProductCoupon(pidList);
		    }
		    
		    //绑定查出的所有记录
		    function saveAllProductCoupon()
		    {
		    	if(confirm("确定绑定符合条件的所有产品吗?系统自动过滤不符合条件的产品，将得不到任何提示信息.")){
			        var url = "<%=basePath%>mark/coupon/ajaxSaveAllProductCoupon.do";
			       $.ajax({
			    	    type: 'POST',
			        	url: url,
			        	data: {"couponId":$("#couponId").val(),"productName":$("#productName").val(),"proudctBizcode":$("#proudctBizcode").val(),"productId":$("#productId").val(),"ticketSlct":$("#ticketSlct").val(),"hotelSlct":$("#hotelSlct").val(),"routeSlct":$("#routeSlct").val(),"otherSlct":$("#otherSlct").val()},
			        	dataType:"json",
				        success: function(result) {
						     if (result.success) {
						        alert("绑定产品成功!");
						        $("#newMarkCouponDiv").dialog("close");	
						     } else {
						        alert("绑定产品失败!");
						    }
				       	}
			       });
		    	}
		    }
		</script>
	</body>
</html>
