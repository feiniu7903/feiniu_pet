<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ebk" uri="/tld/lvmama-ebk-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" type="text/css" href="${contextPath }/css/base/jquery-ui-timepicker-addon.css"  />
<link rel="stylesheet" type="text/css" href="${contextPath }/css/base/jquery.ui.all.css" />
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
</head>
<body id="body_ddgl">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div>
	<div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
			<li>订单处理</li>
			<li>门票订单处理</li>
	    </ul>
	</div>
	<!--订单处理开始-->
	<dl class="order_nav">
		<dt>订单处理</dt>
		<ebk:perm permissionId="4" >
	    <dd><a href="${contextPath }/eplace/queryPassPort.do">未游玩订单</a></dd>
	    </ebk:perm>
	    <dd  class="order_nav_dd"><a href="${contextPath }/eplace/tongJi.do">统计订单</a></dd>
	    <ebk:perm permissionId="6" >
	    <dd><a href="${contextPath }/eplace/allPassportList.do">全部订单</a></dd>
	    </ebk:perm>
	</dl>
	
	<div class="order_all">
	  <form action="${contextPath }/eplace/tongJiQuery.do" method="post" id="tongjiForm">
	  <ul class="search_ul_t">
	      <li>
	          <label>游玩时间：</label>
	          		<input id="playTimeStart" type="text" name="playTimeStart" class="snspt_init_input" readonly="readonly" value="${playTimeStart }"> 
		          		至 <input type="text" id="playTimeEnd" name="playTimeEnd" class="snspt_init_input" value="${playTimeEnd }" readonly="readonly" >
	      </li>
	      <li>
	          <label>产品名称：</label>
		          	<s:if test="ebkProductList != null">
		          		<s:select id="productId" name="productId" list="ebkProductList" listKey="metaProductId" listValue="productName" headerKey="" headerValue="全部"/>
				  	</s:if>
				  	<s:else>
				  		<select id="productId" name="productId">
			              	<option value="">全部</option>
			            </select>
				  	</s:else>
				  	
				  	<s:if test="ebkMetaBranchList != null">
		          		<s:select id="branchId" name="branchId" list="ebkMetaBranchList" listKey="metaBranchId" listValue="branchName" headerKey="" headerValue="全部"/>
				  	</s:if>
				  	<s:else>
				  		<select id="branchId" name="branchId" disabled="disabled">
		                	<option value="">全部</option>
		              	</select>
				  	</s:else>
	      </li> 
	      <li>
	          <label>付款方式：</label>
             <s:select name="paymentTarget" list="#{'':'全部','TOLVMAMA':'在线付款','TOSUPPLIER':'景区现付'}"/>
	      </li>
	      <li><a href="javascript:submitFn()" class="snspt_Btn snspt_srBtn">查找</a></li>
	  </ul>
	</form>
	  <div class="tableWrap">
		<p class="table01Header">订单列表</p>
		<table width="960" border="0" class="table01">
		    <tr>
		      <th width="180">游玩日期</th>
		      <th width="120">订购票数</th>
		      <th width="120">已取票数</th>
		      <th width="120">待取票数</th>
		      <th width="120">预计游玩人数</th>
		      <th width="120">已游玩人数</th>
		      <th>未游玩人数</th>
		    </tr>
		    <s:if test="eplaceOrderQuantity!= null">
				<s:iterator var="value" value="eplaceOrderQuantity">
					<tr>
					  <td>${value.queryDate }</td>
					  <td>${value.sumBookTicketQuantity }</td>
					  <td>${value.sumPickupTicketQuantity }</td>
					  <td>${value.sumUnpickupTicketQuantity }</td>
					  <td>${value.sumExpectPlayQuantity }</td>
					  <td>${value.sumPlayQuantity }</td>
					  <td>${value.sumUnplayQuantity }</td>
					</tr>	
				</s:iterator>
				<tr> 
				  <td>总数</td>
				  <td>${eplaceOrderTotalQuantity.sumBookTicketQuantity }</td>
				  <td>${eplaceOrderTotalQuantity.sumPickupTicketQuantity }</td>
				  <td>${eplaceOrderTotalQuantity.sumUnpickupTicketQuantity }</td>
				  <td>${eplaceOrderTotalQuantity.sumExpectPlayQuantity }</td>
				  <td>${eplaceOrderTotalQuantity.sumPlayQuantity }</td>
				  <td>${eplaceOrderTotalQuantity.sumUnplayQuantity }</td>
				</tr>
			</s:if>
		</table>
		   
        <div class="table01Footer" style="padding-left: 0px;">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(tongjiPage,'')"/>
	    </div>
       </div><!--tableWrap--> 
	</div><!--order_all-->
</div><!--wrap-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript">
function submitFn(){
	$('#tongjiForm').submit();
}
$(document).ready(function(){
	$('#playTimeStart,#playTimeEnd').datepicker({ changeMonth:true,changeYear:true,dateFormat: "yy-mm-dd"});
	
	$('#productId').change(function(){
		var value=$('#productId').val();
		if(value!=''){
			$.ajax({
		   		url: '${contextPath }/eplace/getEbkMetaBranchByProductId.do',
		    	dataType: 'html',
		  		data: {productId:value},
		  		success: function(datas){
		  			$('#branchId').attr("disabled",false);
		  			$('#branchId').html("<option value=''>全部</option>");
		  			var arr=eval(datas);
		  			$.each(arr,function(e,v){
		  				$('#branchId').append("<option value='"+v.id+"'>"+v.text+"</option>");
		  			});
				},
				error : function(){
					alert("通关数据传输出错");
				} 
			});
		}else{
			$('#branchId').attr("disabled","disabled");
			$('#branchId').html("<option value=''>全部</option>");
		}
	});
});
</script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
<script type="text/javascript" src="${contextPath }/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${contextPath }/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${contextPath }/js/base/jquery-ui-timepicker-addon.js"></script>
</body>
</html>