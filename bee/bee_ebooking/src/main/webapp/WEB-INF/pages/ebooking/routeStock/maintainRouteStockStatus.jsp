<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" media="all" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="${contextPath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.validate.js"></script>
<script type="text/javascript">
var basePath = "${basePath}";
var nowDate = "<s:date name='createTimeBegin' format='yyyy-MM-dd'/>";
</script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/maintainRouteStockStatus.js"></script>
<style type="text/css">
.baoliu {
	width: 120px;
	color: #555;
}
</style>
</head>
<body id="body_ftwh" class="ebooking_house">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home"><a href="#">首页</a></li>
    	<li><a href="#">线路库存维护</a></li>
        <li>线路库存维护</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/kucunRoute-guide.ppt" class="ppt_xz">库存维护操作PPT下载</a>
</div>
<dl class="order_nav">
	<dt>线路库存维护</dt>
    <dd class="order_nav_dd"><a href="javascript:void(0)">线路库存维护</a></dd>
    <dd><a href="${basePath}ebooking/routeStock/submitedRouteStockApply.do">已提交申请</a></dd>
</dl>
<!--以上是公用部分-->
<!--订单处理开始-->
<ul class="order_all">
   <li class="order_all_li">
   <form id="changeRouteStockStatusForm" method="post" action="${basePath}ebooking/routeStock/maintainRouteStockStatus.do">
	    <input type="hidden" value="true" name="isSearch">
	    <div class="order_list">
	    	<dl>
	        	<dt>查找线路：</dt>
	            <dd>
	            	<ul class="search_ul_t hide_js">
	            		<li class="search_ul_b_3">
	                    	<label>出发时间：<input id="Calendar71" type="text" name="ebkDayStockDetail.specDate" 
	                    	value="<s:date name="ebkDayStockDetail.specDate" format="yyyy-MM-dd" />"></label>
	                    </li>
	                    <li>
	                    	<label>供应商产品名称：<input class="width320" type="text" 
	                    	name="ebkDayStockDetail.metaProductName" value="${ebkDayStockDetail.metaProductName}"></label>
	                    </li>
	                    <li class="search_ul_b_but">
							<span onclick="$('#changeRouteStockStatusForm').submit();">搜索</span>
						</li>
	                </ul>
	            </dd>
	        </dl>
	    </div>
	    <div class="tableWrap">
		<table width="960" border="0" class="table01 has_border">
	    	<tr>
	      	  <th width="80">产品ID</th>
	   		  <th width="300">供应商产品名称</th>
	   		  <th width="250">类别</th>
	   		  <th width="90">出发时间</th>
	   		  <th width="100">日库存总数</th>
	   		  <th width="80">已售出数</th>
	   		  <th width="80">占位数</th>
	   		  <th width="100">剩余日库存数</th>
	   		  <th width="70">操作</th>
	   		</tr>
	    	<s:iterator var="list" value="ebkDayStockDetailList">
			<tr>
				<td>${metaBranchId}</td>
				<td>${metaProductName}</td>
				<td>${metaBranchName}</td>
				<td> <s:date name="specDate" format="yyyy-MM-dd"/></td>
				<td>${totalDayStock }</td>
				<td>${soldStock }</td>
				<td>${seatOccupiedQuantity }</td>
				<td>${dayStock }</td>
				<td>
					<a href="${basePath}ebooking/routeStock/searchRouteStockTimePriceTable.do
					?ebkDayStockDetail.metaBranchId=${metaBranchId}
					&ebkDayStockDetail.specDate=<s:date name='specDate' format='yyyy-MM-dd'/>" target="_blank">修改</a>
				</td>
			</tr>
			</s:iterator>
	    </table>
		</div>
	</form>
	<div class="table01Footer" style="padding-left: 0px;">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(#request.ebkDayStockDetailPage,'')"/>
   	</div>
   </li>
</ul>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>