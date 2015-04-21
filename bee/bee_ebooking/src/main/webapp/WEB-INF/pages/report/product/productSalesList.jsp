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
	$("#reportForm #manager").val($.trim($("#reportForm #manager").val()));
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
    	<li>收客表</li>
    </ul>
</div><!--以上是公用部分-->

<!--内容开始-->
<ul class="order_all">
	<li class="order_all_li">
		<div class="order_list">
		<form action="${contextPath }/report/product/productSalesList.do" id="reportForm" target="">
    	<dl>
        	<dt>查找收客：</dt>
            <dd>
            	<ul class="search_ul_t hide_js">
                	<li>
                    	<label>驴妈妈产品名称：<input type="text" value="${prodProductName }" id="prodProductName" name="prodProductName"></label>
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
                    	<label>销售ID：<input type="text" value="${prodProductId }" id="prodProductId" name="prodProductId"></label>
                    </li>
                    <li>
                    	<label>驴妈妈产品经理：<input type="text" value="${manager }" id="manager" name="manager"></label>
                    </li>
                	<li>
                    	<label>供应商产品名称：<input type="text" value="${metaProductName }" id="metaProductName" name="metaProductName"></label>
                    </li>
                    <li>
                    	<label>目的地：<input type="text" value="${placeName }" id="placeName" name="placeName"></label>
                    </li>
            		<li class="search_ul_b_3">
                    	<label>出发日期：<input id="Calendar1" readonly="readonly" type="text" value="${visitTimeStart }" name="visitTimeStart">
                    	~</label><input id="Calendar2" readonly="readonly" type="text" value="${visitTimeEnd }" name="visitTimeEnd">
                    </li>
                    
                    <li class="search_ul_b_but"><span onclick="submitSearchForm();">查找</span></li>
                </ul>
            </dd>
        </dl>
        <span class="zhankai"></span>
		</form>
    	</div>
    <div class="tableWrap">
	<div class="table01Header"><a href="javascript:void(0)" onclick="down()">下载收客列表</a>收客列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th width="200">供应商产品名称</th>
   		  <th width="50">销售ID</th>
   		  <th width="200">驴妈妈产品名称</th>
   		  <th width="50">驴妈妈产品经理</th>
   		  
   		  <th width="80">目的地</th>
   		  <th width="50">出发日期</th>
   		  <th width="50">支付人数</th>
   		  <th width="50">未支付人数</th>
   		</tr>
   		<s:iterator value="reportPage.items" var="task">
	    	<tr>
	      	  <td>${metaProductName }</td>
	      	  <td>${prodProductId }</td>
	      	  <td>${prodProductName }</td>
	      	  <td>${manager }</td>
	      	  
	      	  <td>${placeName }</td>
	      	  <td>${zhVisitTime }</td>
	      	  <td>成人：${payedAdultQuantity }<br/>儿童：${payedChildQuantity }</td>
	      	  <td>成人：${unpayAdultQuantity }<br/>儿童：${unpayChildQuantity }</td>
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
		form.attr("action","${contextPath }/report/product/downProductSalesList.do");
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