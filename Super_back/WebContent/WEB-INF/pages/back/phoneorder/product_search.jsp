<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<s:include value="/WEB-INF/pages/back/base/jsonSuggest.jsp" />
		<script type="text/javascript"
			src="${basePath}js/base/jquery.dateFormat-1.0.js">
</script>
		<script type="text/javascript"
			src="${basePath}js/base/jquery.editable-select.js">
</script>
		<link href="${basePath}style/jquery.editable-select.css"
			type="text/css" rel="stylesheet" />
		<link href="${basePath}style/phoneorder/superCss.css" type="text/css"
			rel="stylesheet" />
		<style type="text/css">
.datatable {
	margin-top: 10px;
	margin-left: 5px;
	margin-bottom: 10px;
	background: #fff;
}

.datatable th {
	background: #BAD9FA;
	line-height: 27px;
	font-size: 12px;
}

.main {
	background: #fff;
}

input {
	border: 1px solid #A1C5E6;
	height: 14px;
	padding: 3px 0;
	vertical-align: middle;
}
.qipiaoValidTime {
	display: none;
}
</style>
	</head>
	<body>
		<s:if test="channelList != null && channelList.size > 0">
				来源渠道：<s:radio name="orderChannel" list="channelList" listKey="code"
				listValue="name"></s:radio>
		</s:if>
		<div class="mainTop" style="font-size: 11px;">
			<div id="productSearchTab">
				<ul>
					<li>
						<a href="#tabs_1">门票</a>
					</li>
					<li>
						<a href="#tabs_2">酒店</a>
					</li>
					<li>
						<a href="#tabs_3">线路</a>
					</li>
					<li>
						<a href="#tabs_5">火车票</a>
					</li>
					<li>
						<a href="#tabs_4">其他</a>
					</li>
				</ul>
				<div id="tabs_1">
					<form id="ticketSearchForm" action="/phoneOrder/doSearchList.do"
						method="post">
						<input type="hidden" name="productType" value="TICKET" />
						<s:hidden name="testOrder" />
						<table width="100%">
							<tr>
								<td width="6%" height="35">
									目的地景区：
								</td>
								<td>
									<input name="placeName" type="text" id="ticketPlaceName" />
								</td>
								<td width="5%">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text"
										value="<s:if test='productType != null && productType == "TICKET"'>${productName}</s:if>" />
								</td>
								<td width="8%">
									产品ID：
								</td>
								<td>
									<input name="productId" type="text" class="justNumber"
										value="<s:if test='productType != null && productType == "TICKET"'>${productId}</s:if>" />
								</td>
							</tr>
							<tr>
								<td height="35">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin" class="editable-select justNumber"
										id="ticketSellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax" class="editable-select justNumber"
										id="ticketSellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										id="ticketFilialeName" listKey="code" listValue="name" />
								</td>
								<td>
									是否为期票：
								</td>
								<td>
									<input type="radio" name="isAperiodic" value="true" />是
									<input type="radio" name="isAperiodic" value="false" checked />否
								</td>
							</tr>
							<tr class="qipiaoValidTime">
								<td>有效期范围：</td>
								<td colspan="2">
									<input name="validBeginTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validBeginTime}</s:if>" />~
									<input name="validEndTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validEndTime}</s:if>" />
								</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('ticketSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_2">
					<form id="hotelSearchForm" action="/phoneOrder/doSearchList.do"
						method="post">
						<input type="hidden" name="productType" value="HOTEL" />
						<s:hidden name="testOrder" />
						<table width="100%">
							<tr>
								<td width="6%" height="35">
									目的地/城市/酒店名称：
								</td>
								<td>
									<input name="placeName" type="text" id="hotelPlaceName" />
								</td>
								<td height="35">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text"
										value="<s:if test='productType != null && productType == "HOTEL"'>${productName}</s:if>" />
								</td>
								<td>
									产品ID:
								</td>
								<td>
									<input name="productId" type="text" class="justNumber"
										value="<s:if test='productType != null && productType == "HOTEL"'>${productId}</s:if>" />
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										id="hotelFilialeName" listKey="code" listValue="name" />
								</td>
							</tr>
							<tr>
								<td width="5%">
									<font style="color: red;">*</font>入住时间：
								</td>
								<td>
									<input name="beginVisitDate" type="text" class="date"
										value="<s:if test='productType != null && productType == "HOTEL" && beginVisitDate != null'>${beginVisitDate}</s:if>"
										id="hotelBeginVisitDate" readonly="readonly" />
								</td>
								<td width="8%">
									<font style="color: red;">*</font>离店时间：
								</td>
								<td>
									<input name="endVisitDate" type="text" class="date"
										value="<s:if test='productType != null && productType == "HOTEL" && endVisitDate != null'>${endVisitDate}</s:if>"
										id="hotelEndVisitDate" readonly="readonly" />
								</td>
								<td width="8%">
									<font style="color: red;">*</font>入住天数：
								</td>
								<td>
									<input name="visitDay" type="text" id="hotelVisitDay"
										value="<s:if test='productType != null && productType == "HOTEL" && hotelVisitDay != null'>${hotelVisitDay}</s:if>" />
								</td>
								<td width="5%">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin" class="editable-select justNumber"
										id="hotelSellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax" class="editable-select justNumber"
										id="hotelSellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
								<td>
									仅显示可售：
								</td>
								<td>
									<s:checkbox name="justCanSale" id="justCanSale" />
								</td>
								<td>
									是否为期票：
								</td>
								<td>
									<input type="radio" name="isAperiodic" value="true" />是
									<input type="radio" name="isAperiodic" value="false" checked />否
								</td>
							</tr>
							<tr class="qipiaoValidTime">
								<td>有效期范围：</td>
								<td colspan="2">
									<input name="validBeginTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validBeginTime}</s:if>" />~
									<input name="validEndTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validEndTime}</s:if>" />
								</td>
								<td colspan="5">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="6">&nbsp;</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('hotelSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_3">
					<form id="routeSearchForm" action="/phoneOrder/doSearchList.do"
						method="post">
						<input type="hidden" name="productType" value="ROUTE" />
						<s:hidden name="testOrder" />
						<table width="100%">
							<tr>
								<td width="5%" height="35">
									出发地：
								</td>
								<td>
									<input name="fromPlaceName" type="text" id="routeFromPlaceName" />
								</td>
								<td width="5%">
									目的地:
								</td>
								<td>
									<input name="placeName" type="text" id="routePlaceName" />
								</td>
								<td height="35">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text"
										value="<s:if test='productType != null && productType == "ROUTE"'>${productName}</s:if>" />
								</td>
								<td>
									产品ID：
								</td>
								<td>
									<input name="productId" type="text" class="justNumber"
										value="<s:if test='productType != null && productType == "ROUTE"'>${productId}</s:if>" />
								</td>
							</tr>
							<tr>
								<td width="5%">
									<font style="color: red;">*</font>出发时间：
								</td>
								<td>
									<input name="beginVisitDate" type="text" class="date"
										readonly="readonly" id="routeBeginVisitDate"
										value="<s:if test='productType != null && productType == "ROUTE" && beginVisitDate != null'>${beginVisitDate}</s:if>" />
									到
									<input name="endVisitDate" type="text" class="date"
										readonly="readonly" id="routeEndVisitDate"
										value="<s:if test='productType != null && productType == "ROUTE" && endVisitDate != null'>${endVisitDate}</s:if>" />
								</td>
								<td>
									游玩天数：
								</td>
								<td>
									<input name="visitDay" type="text" id="routeVisitDay"
										value="<s:if test='productType != null && productType == "ROUTE" && visitDay != null'>${visitDay}</s:if>" />
								</td>
								<td width="5%">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin" class="editable-select justNumber"
										id="routeSellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax" class="editable-select justNumber"
										id="routeSellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										id="routeFilialeName" listKey="code" listValue="name" />
								</td>
							</tr>
							<tr>
								<td colspan="4" height="35">
									<s:checkboxlist list="subProductTypeList" name="subProdTypes"
										id="routeSubProdTypes" listKey="code" listValue="name" />
								</td>
								<td>是否为期票：</td>
							<td><input type="radio" name="isAperiodic" value="true" />是
								<input type="radio" name="isAperiodic" value="false" checked />否
							</td>
								<td>&nbsp;</td><td>&nbsp;</td>
							</tr>
							<tr class="qipiaoValidTime">
								<td>有效期范围：</td>
								<td colspan="2">
									<input name="validBeginTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validBeginTime}</s:if>" />~
									<input name="validEndTime" type="text" class="date" readonly="readonly"
									value="<s:if test='productType != null && productType == "TICKET"'>${validEndTime}</s:if>" />
								</td>
								<td colspan="5">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="6">&nbsp;</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('routeSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_5">
					<form id="trainSeachForm" action="/phoneOrder/doSearchList.do">
						<input type="hidden" name="productType" value="TRAFFIC"/>
						<input type="hidden" name="departureStationPinyin" value="${departureStationPinyin}"/>
						<input type="hidden" name="arrivalStationPinyin" value="${arrivalStationPinyin}"/>
						<table width="100%">
							<tr>
								<td>出发地：</td>
								<td><input type="text" name="departureStation" class="station" result="departureStationPinyin" value="<s:property value="#parameters.departureStation"/>"/></td>
								<td>目的地：</td>
								<td><input type="text" name="arrivalStation" class="station" result="arrivalStationPinyin" value="<s:property value="#parameters.arrivalStation"/>"/></td>
								<td>出发日期：</td>
								<td><input type="text" name="trainVisitTime" id="trainVisitTime"  class="date"
										readonly="readonly" value="<s:property value="#parameters.trainVisitTime"/>"/></td>
							</tr>
							<tr>
							    <td>车次</td>
								<td><input type="text" name="lineName" value="<s:property value="#parameters.lineName"/>"/></td>
								
								<td colspan="4" align="right"><a href="javascript:void(0)" class="button"
										onclick="refreshList('trainSeachForm');">查&nbsp;询</a></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_4">
					<form id="otherSearchForm" action="/phoneOrder/doSearchList.do"
						method="post">
						<input type="hidden" name="productType" value="OTHER" />
						<s:hidden name="testOrder" />
						<table width="100%">
							<tr>
								<td width="5%">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text"
										value="<s:if test='productType != null && productType == "OTHER"'>${productName}</s:if>" />
								</td>
								<td width="5%">
									产品ID：
								</td>
								<td>
									<input name="productId" type="text" class="justNumber"
										value="<s:if test='productType != null && productType == "OTHER"'>${productId}</s:if>" />
								</td>
							</tr>
							<tr>
								<td height="35">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin" class="editable-select justNumber"
										id="otherSellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax" class="editable-select justNumber"
										id="otherSellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
								<td>
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('otherSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="productList" href="${basePath}/phoneOrder/doSearchList.do"
			style="font-size: 12px;">
		</div>
		<!--main end-->
		<script type="text/javascript">
$.fn.refreshDiv = function(json) {
	var $this = $(this);
	var url = $this.attr("href");
	var param = $this.attr("param");
	var jsonObj = null;
	if (param != null && "" != param) {
		jsonObj = eval('(' + param + ')')
	}

	if (url != null || url == "") {
		$
				.ajax( {
					type : "POST",
					dataType : "html",
					url : url,
					async : true,
					data : json,
					beforeSend : function() {
						$this
								.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
					},
					error : function(a, b, c) {
						if (b == "timeout") {
							alert("地址" + url + "请求超时");
						} else if (b == "error") {
							alert("无法请求href的地址");
						}
						$this.html("");
					},
					success : function(data) {
						$this.html(data);
					}
				});
	} else {
		alert("请指定href属性");
	}
}

//当从下单页面后退时重新加载搜索条件及结果
function doInitConditions(type) {
	var formName = type + 'SearchForm';
	var $form = $("#" + formName);
	var isAperiodic = '${isAperiodic}';
	$form.find("input[name=fromPlaceName]").val('${fromPlaceName}');
	$form.find("input[name=placeName]").val('${placeName}');
	$form.find("input[type=radio][name=isAperiodic][value="+isAperiodic+"]").attr("checked", true);
	var sellPriceMin = '${sellPriceMin}', val1, val2;
	if (sellPriceMin != '') {
		val1 = parseInt(sellPriceMin) / 100;
	} else {
		val1 = '';
	}
	var sellPriceMax = '${sellPriceMax}';
	if (sellPriceMax != '') {
		val2 = parseInt(sellPriceMax) / 100;
	} else {
		val2 = '';
	}
	$form.find("input[name=sellPriceMin]").val(val1);
	$form.find("input[name=sellPriceMax]").val(val2);
	if(isAperiodic == "true") {
		$form.find(".qipiaoValidTime").show();
		$form.find("input[name=beginVisitDate]").attr("disabled", true);
		$form.find("input[name=endVisitDate]").attr("disabled", true);
		$form.find("input[name=visitDay]").attr("disabled", true);
	} else {
		if (type == 'hotel') {
			autoFillData('hotelBeginVisitDate');
		}
	}
	refreshList(type + 'SearchForm');
}

//加载门票、酒店、线路tabs
$(function() {
	var productType = '${productType}', index = 0;
	if (productType != '') {
		if (productType == 'HOTEL') {
			index = 1;
		} else if (productType == 'ROUTE') {
			index = 2;
		} else if (productType == 'OTHER') {
			index = 4;
		} else if(productType == 'TRAFFIC'){
			index = 3;
		}
	}
	$('#productSearchTab').tabs( {
		selected : index
	});
	//目的地景区内容自动提示返回操作函数
	//obj为选择的结果对象
	//输入目的地景区自动提示,发起请求
	$('input[name="placeName"],input[name="fromPlaceName"]').jsonSuggest( {
		url : '/super_back/prod/searchPlace.do',
		maxResults : 10,
		minCharacters : 1,
		useGlobalClear : true
	});
	function selectStation(item,target){
		$("input[name="+target+"]").val(item.id);
	}
	$("input.station[result=departureStationPinyin]").jsonSuggest({
		url : '/super_back/prod/searchStationJSON.do',
		maxResults : 10,
		minCharacters : 1,
		//useGlobalClear : true,
		onSelect:function(item){
			selectStation(item,"departureStationPinyin");
		}
	});
	$("input.station[result=arrivalStationPinyin]").jsonSuggest({
		url : '/super_back/prod/searchStationJSON.do',
		maxResults : 10,
		minCharacters : 1,
		//useGlobalClear : true,
		onSelect:function(item){
			selectStation(item,"arrivalStationPinyin");
		}
	});
	
	$("input[id$='BeginVisitDate']").datepicker( {
		minDate : 0,
		maxDate : "+6M"
	});
	$("#trainVisitTime").datepicker( {
		minDate : 0,
		maxDate : "+2M"
	});
	
	$("input[id$='EndVisitDate']").datepicker( {
		minDate : +1,
		maxDate : "+6M"
	});
	$("input[name='validBeginTime']").datepicker( {
		minDate : +1,
		maxDate : "+6M"
	});
	$("input[name='validEndTime']").datepicker( {
		minDate : +1,
		maxDate : "+6M"
	});
	$('#hotelBeginVisitDate,#hotelEndVisitDate').bind('change', function() {
		autoFillData($(this).attr('id'));
	});
	$('#routeBeginVisitDate,#routeEndVisitDate').bind('change', function() {
		var beginDate = $('#routeBeginVisitDate').val();
		var endDate = $('#routeEndVisitDate').val();
		var date1 = getNewDate(beginDate);
		var date2 = getNewDate(endDate);
		var d = (date2 - date1) / 24 / 60 / 60 / 1000;
		if (d < 0) {
			alert("开始日期不能大于结束日期！");
			$(this).val('');
			return;
		}
	});
	$('#hotelVisitDay,#routeVisitDay').bind('keyup', function() {
		var v = $.trim($(this).val());
		if (v.length > 0) {
			if (isNaN(v)) {
				alert('只能输入数字');
				$(this).val("");
				return;
			} else if (v < 1) {
				alert('必须大于等于1天！');
				$(this).val("");
				return;
			}
			autoFillData($(this).attr('id'));
		}
	});
	$('.justNumber').live('keyup', function() {
		var v = $.trim($(this).val());
		if (v.length > 0) {
			if (isNaN(v)) {
				alert("输入有误！");
				$(this).val("");
				return;
			}
			if (v.indexOf(".") > 0) {
				alert("输入有误！");
				$(this).val("");
				return;
			}
		}
		$(this).val(v);
	});
	$('a[href^="#tab"]').click(function() {
		$('#productList').html("");
	});

	//可输入下拉框
	$('.editable-select').editableSelect( {
		bg_iframe : true
	/*,
	        onSelect: function(list_item) {
	        }*/
	});

	$(document).ready(function() {
		if (productType != '') {
			doInitConditions(productType.toLowerCase());
		}
		
		$("input[type=radio][name=isAperiodic]").click(function() {
			var $form = $(this).parents("form");
			if($(this).val() == "true") {
				$form.find(".qipiaoValidTime").show();
				$form.find("input[name=beginVisitDate]").attr("disabled", true);
				$form.find("input[name=endVisitDate]").attr("disabled", true);
				$form.find("input[name=visitDay]").attr("disabled", true);
			} else {
				$form.find(".qipiaoValidTime").hide();
				$form.find("input[name=beginVisitDate]").attr("disabled", false);
				$form.find("input[name=endVisitDate]").attr("disabled", false);
				$form.find("input[name=visitDay]").attr("disabled", false);
			}
		});
	});
});

function refreshList(formName) {
	$('.jsonSuggest').hide();
	var myForm = $('#' + formName);
	var m = myForm.getForm();
	var ser = myForm.serialize();
	if (m.sellPriceMin != "" && m.sellPriceMax != "") {
		if (parseFloat(m.sellPriceMax) < parseFloat(m.sellPriceMin)) {
			alert("请选择正确的价格范围！");
			return;
		}
	}
	var isAperiodic = myForm.find("input[type=radio][name=isAperiodic]:checked");
	if(isAperiodic != undefined) {
		m["isAperiodic"] = isAperiodic.val();
		if(isAperiodic.val() != "true") {
			if (formName == 'hotelSearchForm') {
				if ($('#hotelBeginVisitDate').val() == '') {
					alert('必须选择入住时间！');
					return;
				}
				if ($('#hotelEndVisitDate').val() == '') {
					alert('必须选择离店时间！');
					return;
				}
				if ($('#hotelVisitDay').val() == '') {
					alert('必须输入入住天数！');
					return;
				}
			} else if (formName == 'routeSearchForm') {
				if ($('#routeBeginVisitDate').val() == ''
						|| $('#routeEndVisitDate').val() == '') {
					alert('必须选择出发时间！');
					return;
				}
			}
		}
	}
	if(m.validBeginTime != null && m.validEndTime != null) {
		var date1 = getNewDate(m.validBeginTime);
		var date2 = getNewDate(m.validEndTime);
		var d = (date2 - date1) / 24 / 60 / 60 / 1000;
		if (d < 0) {
			alert("有效期开始日期不能大于结束日期！");
			return;
		}
	} else if (formName == 'trainSearchForm'){
		if($.trim($("input[name='departureStationPinyin']").val())==''){
			alert("出发城市不可以为空");
			return;
		}
		
		if($.trim($("input[name='arrivalStationPinyin']").val())==''){
			alert("到达城市不可以为空");
			return;
		}
		if($.trim($("input[name='trainVisitTime']").val())==''){
			alert("出发日期不可为空");
			return;
		}
	}
	$('#productList').refreshDiv(ser);
}

/*
 一:改变入住日期
 1:离店日期变成后一天、入住天数变成1
 二:改变离店日期
 1:如果入住日期为空，不做处理
 2:如果入住日期不为空，算出入住天数
 三：改变入住天数
 1:如果入住日期为空，不做处理
 2:如果入住日期不为空，算出离店日期
 */
function autoFillData(name) {
	var beginVisitDate = $('#hotelBeginVisitDate').val();
	var endVisitDate = $('#hotelEndVisitDate').val();
	var visitDay = $('#hotelVisitDay').val();
	var date = getNewDate(beginVisitDate);
	if (name == 'hotelBeginVisitDate') {
		date.setDate(date.getDate() + 1);
		$('#hotelEndVisitDate').val($.format.date(date, "yyyy-MM-dd"));
		$('#hotelVisitDay').val('1');
	} else if (name == 'hotelEndVisitDate') {
		if (beginVisitDate != '') {
			var date2 = getNewDate(endVisitDate);
			var d = (date2 - date) / 24 / 60 / 60 / 1000;
			if (d < 1) {
				alert("离店时间不能小于入住时间！");
				date.setDate(date.getDate() + 1);
				$('#hotelEndVisitDate').val($.format.date(date, "yyyy-MM-dd"));
				return;
			}
			$('#hotelVisitDay').val(d);
		}
	} else {
		if (beginVisitDate != '') {
			date.setDate(date.getDate() + parseInt(visitDay));
			$('#hotelEndVisitDate').val($.format.date(date, "yyyy-MM-dd"));
		}
	}
}

//兼容ie和firefox返回日期
function getNewDate(d) {
	var strs = d.split('-');
	var date = new Date();
	date.setUTCFullYear(strs[0], strs[1] - 1, strs[2]);
	date.setUTCHours(0, 0, 0, 0);
	return date;
}

var $time_price_dlg = null;
function loadLog(param) {
	if ($('input[name="orderChannel"]').size() != 0) {
		var orderChannel = $('input[name="orderChannel"]:checked').val();
		if (orderChannel != undefined) {
			param.orderChannel = orderChannel;
		} else {
			alert("请选择来源渠道！");
			return;
		}
	}
	param.paramsStr = param.paramsStr + "&orderId=" + ${orderId};
	param.orderId = ${orderId};
	if ($time_price_dlg == null) {
		$time_price_dlg = $("<div style='display:none' class='time_price_dlg_div'></div>");
		$time_price_dlg.appendTo($("body"));
	}

	$time_price_dlg.load('/super_back/common/timePrice.do', param, function() {
		$time_price_dlg.dialog( {
			title : "时间价格表",
			width : 750,
			modal : true
		});
	});
}
function quickBooker(settings){
	var $form=$("#myForm");
	var obj=$.extend({},settings,{"quantity":1,"visitTime":$form.find("input[name=visitDate]").val()});
	$.post("/super_back/phoneOrder/orderCheckStock.do",obj,function(dt){
		var data=eval("("+dt+")");
		if(data.success){			
			$form.find("input[name=productId]").val(obj.id);
			$form.find("input[name=branch]").attr("name","prodBranchItemMap.branch_"+obj.mainProdBranchId).val(1);
			$form.find("input[name=paramsStr]").val(obj.paramsStr);
			$form.submit();
		}else{
			alert(data.msg);
		}
	});
}
</script>
	</body>
</html>
