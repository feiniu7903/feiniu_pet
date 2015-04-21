<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.validate.js"></script>
<script type="text/javascript">
function submitSearchForm() {
	$("#reportForm #placeName").val($.trim($("#reportForm #placeName").val()));
	$("#reportForm #metaProductName").val($.trim($("#reportForm #metaProductName").val()));
	$("#reportForm #prodProductName").val($.trim($("#reportForm #prodProductName").val()));
	$("#reportForm #prodProductId").val($.trim($("#reportForm #prodProductId").val()));
	$('#reportForm').submit();
}
$(function(){
	$("#reportForm #subProductType").val("${subProductType}");
});
</script>
</head>
<body id="body_sjgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>数据管理</li>	
    	<li>产品表</li>
    </ul>
</div><!--以上是公用部分-->

<!--内容开始-->
<ul class="order_all">
	<li class="order_all_li">
		<div class="order_list">
		<form action="${contextPath }/report/product/onSaleProductList.do" id="reportForm" target="">
    	<dl>
        	<dt>查找产品：</dt>
            <dd>
            	<ul class="search_ul_t hide_js">
                	<li>
                    	<label>驴妈妈产品名称：<input type="text" value="${prodProductName }" name="prodProductName"></label>
                    </li>
                    <li>
                    	<label>产品类型：
                        	<select name="subProductType" id="subProductType">
                            	<option value="">全部</option>
                            	<option value="GROUP">短途跟团游</option>
                            	<option value="SELFHELP_BUS">自助巴士班</option>
                            </select>
                        </label>
                    </li>
                    <li>
                    	<label>销售ID：<input type="text" value="${prodProductId }" name="prodProductId" title="多个ID以逗号分隔"></label>
                    </li>
                    <li>
                    	<label>目的地：<input type="text" value="${placeName }" name="placeName"></label>
                    </li>
                	<li>
                    	<label>供应商产品名称：<input type="text" value="${metaProductName }" name="metaProductName"></label>
                    </li>
            		<li class="search_ul_b_3">
                    	<label>上线时间：<input id="Calendar1" readonly="readonly" type="text" value="${onlineTimeStart }" name="onlineTimeStart">
                    	~</label><input id="Calendar2" readonly="readonly" type="text" value="${onlineTimeEnd }" name="onlineTimeEnd">
                    </li>
                    <li class="search_ul_b_3">
                    	下线时间：<input id="Calendar3" type="text" value="${offlineTimeStart }" name="offlineTimeStart">
                    	~<input id="Calendar4" type="text" value="${offlineTimeEnd}" name="offlineTimeEnd">
                    </li>
                    
                    <li class="search_ul_b_but"><span onclick="submitSearchForm();">查找</span></li>
                </ul>
            </dd>
        </dl>
        <span class="zhankai"></span>
		</form>
    	</div>
    <div class="tableWrap">
	<div class="table01Header"><a href="javascript:void(0)" onclick="down()">下载产品列表</a>产品列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th width="150">供应商产品名称</th>
   		  <th width="50">销售ID</th>
   		  <th width="150">驴妈妈产品名称</th>
   		  <th width="70" title="成人价（2个月内最低）">售价</th>
   		  <th width="70" title="成人价（2个月内最低）">结算价</th>
   		  
   		  <th width="50">目的地</th>
   		  <th width="50">出发班期</th>
   		  <th width="50">行程天数</th>
   		  <th width="50">上下线时间</th>
   		  <th width="80">住宿</th>
   		  
   		  <th width="50">游玩特色</th>
   		  <th width="100">一句话推荐</th>
   		  <th width="70">标的</th>
   		</tr>
   		<s:iterator value="reportPage.items" var="task">
	    	<tr>
	      	  <td>${metaProductName }</td>
	   		  <td>${prodProductId}</td>
	   		  <td>${prodProductName }</td>
	   		  <td>${priceYuan}</td>
	   		  <td>${settlementPriceYuan}</td>
	   		  
	   		  <td>${placeName }</td>
	   		  <td>${travelTime }</td>
	   		  <td>${days }</td>
	   		  <td>${zhOnlineTime }~${zhOfflineTime }</td>
	   		  <td>${journeyHotel }</td>
	   		  
	   		  <td>${journeyFeature }</td>
	   		  <td>${journeyRecommend }</td>
	   		  <td>${productPlace }</td>
	   		</tr>
   		</s:iterator>
    </table>
	<div class="table01Footer" style="padding-left: 0px;">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(reportPage,'')"/>
    </div>
    </div>
    </li>
</ul>
<script type="text/javascript">
	function down() {
		var form = $("#reportForm");
		var action = form.attr("action");
		var target = form.attr("target");
		form.attr("action","${contextPath }/report/product/downOnSaleProductList.do");
		form.attr("target","_new");
		form.submit();
		form.attr("action",action);
		form.attr("target",target);
	} 
	$(function() {
		$("#subProductType").val("${subProductType}");
	});
</script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>