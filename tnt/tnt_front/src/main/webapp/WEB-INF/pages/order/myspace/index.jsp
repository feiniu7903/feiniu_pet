<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心-驴妈妈分销平台</title>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css,/styles/v4/modules/dialog.css,/styles/v5/modules/tip.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/calendar.css" rel="stylesheet" type="text/css">
<!-- <script
	src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script> -->
</head>
<body>
	<div class="order">
        	<div class="nav">
            	<ul>
                	<li class="active">所有订单</li>
                    <li id="unpayOrderList">待支付</li>
                    <li id="unrefundOrderList">待退款</li>
                </ul>
            </div><!--nav end--> 
            <div class="content" style="display:block;">
            	<div class="formcontent">
            	<form:form id="tntOrder" name="tntOrder" modelAttribute="tntOrder" action="/mOrder/all/query.do" method="post">
                	<ul class="formList">
                    	<li>
                        	<label class="titleBox">订单编号：</label>
                            <div class="form-inline form-small">
                                <input class="textClass" id="orderId" name="orderId" type="text"  >
                            </div>
                        </li>
                        <li class="teshuLi">
                        	<label class="titleBox">下单时间：</label>
                            <div class="form-inline form-small">
                                <input class="textClass J_calendar" type="text" data-check="checkIn" autocomplete="off" readonly="readonly" name="createTimeBegin" value="">
                            </div>
                            <label class="titleBox">-</label>
                            <div class="form-inline form-small secondTextBox">
                                <input class="textClass J_calendar" type="text" data-check="checkOut" autocomplete="off" readonly="readonly" name="createTimeEnd" data-range="true" value="">
                            </div>
                        </li>
                       <!--  <li class="teshuLi">
                        	<label class="titleBox">下单时间：</label>
                            <div class="form-inline form-small">
                                <input class="textClass J_calendar" type="text" data-check="checkIn" autocomplete="off"  name="createTimeBegin" value="">
                            </div>
                            <label class="titleBox">-</label>
                            <div class="form-inline form-small secondTextBox">
                                <input class="textClass J_calendar" type="text" data-check="checkOut" autocomplete="off" name="createTimeEnd"  value="">
                            </div>
                        </li> -->
                        <li>
                        	<label class="titleBox">产品类型：</label>
                            <div class="form-inline form-small">
                                <select class="selectClass" name="productType">
                                	<option value="">全部</option>
                                </select>
                            </div>
                        </li>
                        <li>
                        	<label class="titleBox">产品名称：</label>
                            <div class="form-inline form-small">
                                <input class="textClass" id="productName" name="productName" type="text"  >
                            </div>
                        </li>
                        <li class="teshuLitwo">
                        	<label class="titleBox">取票人：</label>
                            <div class="form-inline form-small">
                                <input class="textClass qpren" id="contactName" name="contactName" type="text">
                            </div>
                        </li>
                    </ul><!--formList end--> 
                    <div class="rightBtnBox">
                    <input id="searchPage" name="page" type="hidden">
                    	<a class="btn btn-w cbtn-default order-search">搜索</a>
                    </div>
                   </form:form>
                </div><!--formcontent end--> 
                <div class="search-itemBox">
                	<jsp:include page="/WEB-INF/pages/order/myspace/list_all.jsp"></jsp:include>
                </div><!--search-itemBox end-->
            </div><!--content end--> 
            
            <div class="content unpayOrderList"></div>
            
            <div class="content unrefundOrderList"></div> 
        </div><!--order end--> 
</body>

<!--  <script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-calendar.js"></script>  -->
<!-- <script src="http://pic.lvmama.com/js/fx/b2b_fx_order.js"></script> -->
<script src="/js/b2b_fx_order.js"></script> 
<script type="text/javascript">
$('.nav li').click(function(){
	var _num = $(this).index();
	$(this).addClass('active').siblings().removeClass('active');
	$(this).parents('.order').find('.content').eq(_num).show().siblings('.content').hide();
})
$(".order-search").bind("click", function() {
	$("#searchPage").val(1);
	searchAllOrder();
});
function searchAllOrder(){
	$("#tntOrder").ajaxSubmit({
		success : function(data) {
			$(".search-itemBox").html(data);
		}
	});
}
$("#unrefundOrderList").bind("click", function() {
	query("/mOrder/unrefund/query.do",".unrefundOrderList");
});
$("#unpayOrderList").bind("click", function() {
	query("/mOrder/unpay/query.do",".unpayOrderList");
});
function showDetail(obj){
	var url=obj.href;
	query(url,".main_r");
	return false;
}

</script>
</html>