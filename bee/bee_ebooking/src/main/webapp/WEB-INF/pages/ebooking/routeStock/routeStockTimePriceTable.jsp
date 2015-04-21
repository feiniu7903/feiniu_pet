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
    	<li><a href="#">库存维护</a></li>
        <li><a href="#">线路库存维护</a></li>
        <li>修改</li>
    </ul>
</div><!--以上是公用部分-->
<!--价格表-->
<div class="xl_xg">
	<div class="pricelist">
	<p class="explain">
		<b>图标说明：</b><span class="pink_bg"></span>无库存；<span class="green_bg"></span>开放状态（<span
				class="orange">当天剩余库存数</span>/<span class="green">库存总数</span>）；<span
				class="chaomai">超卖</span>库存售完后自动变为需库存确认。
	</p>
	<s:iterator value="branchCalendarModel" var="branch">
	<s:iterator value="branch" var="b">
	<div class="pricelist">
		<h4>
			<b><s:property value="key.branchName" />(<s:property value="key.metaBranchId" />)</b>
		</h4>
		<div class="plug_calendar_box">
			<s:iterator value="value" var="calendarModel">
			<div class="plug_calendar_main">
				<h2 class="plug_calendar_tit">${calendarModel.year}年${calendarModel.month + 1}月</h2>
				<div class="plug_calendar_table">
					<ul class="plug_calendar_t">
						<li>日</li>
						<li>一</li>
						<li>二</li>
						<li>三</li>
						<li>四</li>
						<li>五</li>
						<li>六</li>
					</ul>
					<s:iterator value="calendar" var="cal" status="status">
						<ul class="plug_calendar_d">
							<s:iterator value="#cal" var="timePrice">
								<!-- 非满房 -->
								<s:if test="#timePrice.metaBranchId != 0">
									<li 
									<s:if test="#timePrice.overSale != 'true' && #timePrice.dayStock ==0">
										class="plug_calendar_full" 
									</s:if>
									<s:else>
										class="plug_calendar_well" 
									</s:else>
									branchName="${key.branchName }" metaBranchId="${key.metaBranchId }" date="<s:date name='#timePrice.specDate' format='yyyy-MM-dd'/>"> 
										<div class="plug_calendar_d_box month_1" data-sellbale="true">
											<span class="plug_calendar_day"><s:property value="#timePrice.dateStr" /></span>
											<span class="plug_calendar_amount">
											<!-- 库存为-1时，仅显示无限,否则显示当日剩余库存量/总库存量. -->
											<s:if test="#timePrice.dayStock == -1">
												<b><s:property value="#timePrice.dayStockStr" /></b>
											</s:if>
											<s:else> 
												<b><s:property value="#timePrice.dayStockStr" /></b>/<i><s:property value="#timePrice.totalDayStock" /></i>
											</s:else>
											</span>
											<s:if test="#timePrice.overSale == 'true'">
											<span class="plug_calendar_chaomai">超卖</span>
											</s:if>
										</div>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</s:iterator>
				</div>
			</div>
			</s:iterator>
		</div>
	</div>
	<form id="changeMulDayRouteDayStockForm" method="get">
	<input type="hidden" name="ebkDayStockDetail.metaBranchId" 
	value='<s:property value="key.metaBranchId" />' id="metaProductBranchId"/>
	<ul class="roomStatus_list xl_xg_list">
	    	<li class="search_ul_b_3">
	            <strong>选择日期：</strong>
	                 <p><input id="Calendar1" type="text"  name="beginDate">
	                                             至 <input id="Calendar2" type="text" name="endDate"></p>
	            <span>&nbsp;&nbsp;查询时间区间不超过2个月</span>
	        </li>
			<li><strong>增减库存：</strong>
				<p>
					<label style="margin-right: 17px;"><input name="ebkDayStockDetail.isAddDayStock" id="addDayStockFirst" value="true"  type="radio" checked="checked">增加</label> 
					<label style="margin-left: 20px;"><input name="ebkDayStockDetail.isAddDayStock" id="minusDayStockFirst" value="false" type="radio">减少</label> 
					<input id="routeStockAddOrMinusInputFirst" name="ebkDayStockDetail.addOrMinusStock" value="0" type="hidden">
					<input name="routeStockAddOrMinus" id="routeStockAddOrMinusFirst" value="输入增加的数量" class="baoliu" 
					onfocus="if (value =='输入增加的数量' || value =='输入减少的数量'){value =''}"
					onblur="if (value ==''){value='输入增加的数量' || value =='输入减少的数量'}" 
					type="text"> (为空或非数字不做修改)
					<font color="red">（减少库存需要驴妈妈业务人员审核后才能生效）</font>
				</p>
			</li>
			<li><strong>是否超卖：</strong>
				<p>
					<label style="margin-right: 49px;"><input name="ebkDayStockDetail.isOverSale" id="overSaleFalseFirst" value="true"  type="radio" checked="checked">是</label> 
					<label><input name="ebkDayStockDetail.isOverSale" id="overSaleTrueFirst" value="false" type="radio" >否</label>
					<font color="red">（"是"代表产品售完后订单自动变为需确认；"否"代表产品售完后不可卖）</font>
				</p></li>
			<li>
			<s:if test="supBCertificateTarget.supplierForbidSaleFalg=='true'">
				<li>
				<strong>关班：</strong>
				<p>
					<label><input name="ebkDayStockDetail.isStockZero" id="stockZeroFirst" value="true" type="checkbox"/>是</label>
				</p>
				</li>
			</s:if>
			<li id="addStockLiId">
				<p>
					<label><input class="roomStatus_cx" value="保存修改" id="saveModify"
					type="button"></label>
				</p>
			</li>
		</ul>
	</form>
	</s:iterator>
	</s:iterator>
	</div>
</div>
<!--时间价格表弹出层-->
<div class="bg_opacity1 show_hide"></div>
<iframe class="bg_opacity2 t_width_512 show_hide"></iframe>
<form id="changeRouteDayStockForm" method="post">
<input type="hidden" name="ebkDayStockDetail.metaBranchId" id="metaBranchId"/>
<input type="hidden" id="priceStockVerifyFalg" 
value='<s:property value="supBCertificateTarget.priceStockVerifyFalg"/>'/>
<input id="beginDate" type="hidden" name="beginDate">
<input id="endDate" type="hidden" name="endDate">
<div class="eject_rz t_width_500 show_hide" style="text-align:left">
	<h4>修改库存</h4>
	<div class="t_xiugai_box">
		<ul class="roomStatus_list">
			<li><strong>当前产品：</strong>
				<p class="width_360">
					<b id="branchNameShow"></b>
				</p></li>
			<li><strong>当前选择时间:</strong>
				<p id="beginDateShow">2012年2月27日</p></li>
			<li><strong>增减库存：</strong>
				<p>
					<label style="margin-right: 17px;"><input name="ebkDayStockDetail.isAddDayStock" id="addDayStockSecond" value="true" checked="checked" type="radio">增加</label> 
					<label style="margin-left: 20px;"><input name="ebkDayStockDetail.isAddDayStock" id="minusDayStockSecond" value="false" type="radio">减少</label> 
					<input id="routeStockAddOrMinusInputSecond" name="ebkDayStockDetail.addOrMinusStock" value="0" type="hidden">
					<input name="routeStockAddOrMinus" id="routeStockAddOrMinusSecond" value="输入增加的数量" class="baoliu" 
					onfocus="if (value =='输入增加的数量' || value =='输入减少的数量'){value =''}"
					onblur="if (value ==''){value='输入增加的数量' || value =='输入减少的数量'}" 
					type="text"> (为空或非数字不做修改)
				</p>
			</li>
			<li><strong>是否超卖：</strong>
				<p>
					<label style="margin-right: 49px;"><input name="ebkDayStockDetail.isOverSale" id="overSaleFalseSecond" value="true" checked="checked" type="radio">是</label> 
					<label><input name="ebkDayStockDetail.isOverSale" id="overSaleTrueSecond" value="false" type="radio">否</label>
					<span style="color:red;">（"是"代表产品售完后订单自动变为需确认；"否"代表产品售完后不可卖）</span>
				</p>
				</li>
			<s:if test="supBCertificateTarget.supplierForbidSaleFalg=='true'">
				<li><strong>关班：</strong>
					<p>
						<label><input name="ebkDayStockDetail.isStockZero" id="stockZeroSecond" value="true" type="checkbox"/>是</label>
					</p>
				</li>
			</s:if>
			<li><input class="roomStatus_cx" value="保存修改" id="saveModifyDay" type="button"></li>
		</ul>
	</div>
	<span class="close"></span>
</div>
</form>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
<script type="text/javascript">
	$("#stockZeroFirst").click(function() {
		 var check=$(this).attr("checked");
		 if(check=='checked'){
				stockZeroCheck("#changeMulDayRouteDayStockForm",true);
		}else{
			stockZeroCheck("#changeMulDayRouteDayStockForm",false);
		}
	});
	$("#stockZeroSecond").click(function() {
		 var check=$(this).attr("checked");
		 if(check=='checked'){
			stockZeroCheck("#changeRouteDayStockForm",true);
		}else{
			stockZeroCheck("#changeRouteDayStockForm",false);
		}
	});
	function stockZeroCheck(form,check){
		$(form).find("input[name='ebkDayStockDetail.isAddDayStock']").attr("disabled",check);
		$(form).find("input[name='ebkDayStockDetail.isOverSale']").attr("disabled",check);
		$(form).find("input[name='routeStockAddOrMinus']").attr("disabled",check);
	}
	
	$("#addDayStockFirst").click(function() {
		$("#routeStockAddOrMinusFirst").val("输入增加的数量");
	});	
	$("#minusDayStockFirst").click(function() {
		$("#routeStockAddOrMinusFirst").val("输入减少的数量");
	});
	$("#addDayStockSecond").click(function() {
		$("#routeStockAddOrMinusSecond").val("输入增加的数量");
	});
	$("#minusDayStockSecond").click(function() {
		$("#routeStockAddOrMinusSecond").val("输入减少的数量");
	});
	$("#routeStockAddOrMinusFirst").change(function() {
		var v = $(this).val();
		var result = 0;
		if (!(v == '输入增加的数量' || v == '输入减少的数量')) {
			var val = parseInt(v);
			if (!isNaN(val)) {
				result = val;
			}
		}
		$("#routeStockAddOrMinusInputFirst").val(result);
	});
	$("#routeStockAddOrMinusSecond").change(function() {
		var v = $(this).val();
		var result = 0;
		if (!(v == '输入增加的数量' || v == '输入减少的数量')) {
			var val = parseInt(v);
			if (!isNaN(val)) {
				result = val;
			}
		}
		$("#routeStockAddOrMinusInputSecond").val(result);
	});
	
	function tan_show() {
		var index = $('.rizhi_show').index(this);
		var _hight_w = $(window).height();
		var _hight_t = $('.eject_rz').eq(index).height();
		var _hight = _hight_w - _hight_t;
		var _top = $(window).scrollTop() + _hight / 2;
		var height_w = $(document).height();
		$('.eject_rz').eq(index).css({
			'top' : _top
		}).show();
		$('.bg_opacity2').eq(index).css({
			'height' : _hight_t + 31,
			'top' : _top - 5
		}).show();
		$('.bg_opacity1').eq(index).css({
			'height' : height_w,
			'width' : $(document.body).width()
		}).show();
	}
	
	$('.close').click(function() {
		$('.show_hide').hide();
	});
	$(".plug_calendar_d li").click(function() {
		if(!$(this).attr("metabranchid") || $(this).attr("metabranchid") < 0) {
			return false;
		}
		$("#changeRouteDayStockForm #branchNameShow").html($(this).attr("branchName"));
		$("#changeRouteDayStockForm #beginDateShow").html($(this).attr("date"));
		$("#changeRouteDayStockForm #metaBranchId").val($(this).attr("metaBranchId"));
		$("#changeRouteDayStockForm #beginDate").val($(this).attr("date"));
		$("#changeRouteDayStockForm #endDate").val($(this).attr("date"));
		tan_show();
	});
	
	$(".plug_calendar_d li").each(function() {
		if($(this).find(".plug_calendar_amount").size() > 0) {
			$(this).hover(function(){
				$(this).addClass('hover_bg'); 
				},function(){ 
				$(this).removeClass('hover_bg'); 
			});
			$(this).css({'cursor':'pointer'});
		}
	});
	$(".search_ul_b_3").ui("calendar", {
		input : "#Calendar1",
		parm : {
			dateFmt : "yyyy-MM-dd",
			maxDate : "#F{$dp.$D('Calendar2')}",
			minDate : "#F{$dp.$D('Calendar2',{d:-60})||'"+nowDate+"'}"
		}
	});
	$(".search_ul_b_3").ui("calendar", {
		input : "#Calendar2",
		parm : {
			dateFmt : 'yyyy-MM-dd',
			maxDate : "#F{$dp.$D('Calendar1',{d:60})}",
			minDate : "#F{$dp.$D('Calendar1')}"
		}
	});
	function reduceRouteStockApprove(formId) {
		var basePath="ebooking";
		var url=basePath+"ebooking/routeStock/reduceRouteStockApprove.do";
		var data=$(formId).serialize();
		$.ajax({type:"POST", url:url,data:data, success:function (result) {
			if (result=="SUCCESS") {
				alert("减少库存审核提交成功,请等待后台审核。");
				window.location.href=basePath+"/ebooking/routeStock/submitedRouteStockApply.do"
			} else {
				alert(result);
			}
		}});
	}
$(function(){
	$("#saveModify").click(function(){
		var isAddDayStock = $("#changeMulDayRouteDayStockForm").
							find("input:radio[name='ebkDayStockDetail.isAddDayStock']:checked").val();//增加还是减少库存
		var isStockZero = $("#changeMulDayRouteDayStockForm").
							find("input[name='ebkDayStockDetail.isStockZero']:checked").val();//关班
		var addOrMinusStock = $("#changeMulDayRouteDayStockForm").
							find("input[name='ebkDayStockDetail.addOrMinusStock']").val();//增减库存数
		var priceStockVerifyFalg = $("#priceStockVerifyFalg").val();
		if('false'==priceStockVerifyFalg){
			checkAndSubmit($('#changeMulDayRouteDayStockForm'));
		}else{
			if(isAddDayStock=='true'){
				checkAndSubmit($('#changeMulDayRouteDayStockForm'));
			}else if(isAddDayStock=='false'){
				if(addOrMinusStock==0){
					alert("请输入减少的库存数");
				}else if(confirm("减少库存需要驴妈妈业务人员审核后才能生效，您是否确认提交？")){
					reduceRouteStockApprove("#changeMulDayRouteDayStockForm");
				}else{
			        alert("您已经取消提交。");
				}
			}
		}
	});
	$("#saveModifyDay").click(function(){
		var isAddDayStock =$("#changeRouteDayStockForm").
							find("input:radio[name='ebkDayStockDetail.isAddDayStock']:checked").val();
		var isStockZero = $("#changeRouteDayStockForm").
							find("input[name='ebkDayStockDetail.isStockZero']:checked").val();
		var addOrMinusStock = $("#changeRouteDayStockForm").
							find("input[name='ebkDayStockDetail.addOrMinusStock']").val();
		var priceStockVerifyFalg = $("#priceStockVerifyFalg").val();
		if('false'==priceStockVerifyFalg){
			checkAndSubmit($('#changeRouteDayStockForm'));
		}else{
			if(isAddDayStock=='true'){
				checkAndSubmit($('#changeRouteDayStockForm'));
			}else if(isAddDayStock=='false'){
				if(addOrMinusStock==0){
					alert("请输入减少的库存数");
				}else if(confirm("减少库存需要驴妈妈业务人员审核后才能生效，您是否确认提交？")){
					reduceRouteStockApprove("#changeRouteDayStockForm");
				}else{
			        alert("您已经取消提交。");
				}
			}
		}
	});
});
</script>