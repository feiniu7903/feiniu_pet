<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--价格表-->
<div class="housePrices_cx">
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
					<li>日</li>C
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
								<s:if test="#timePrice.overSale != 'true' && #timePrice.dayStock == 0">
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
							<!-- 满房 -->
							<s:else>
								<li class="plug_calendar_full">
									<div class="plug_calendar_d_box month_1" data-sellbale="true">
										<span class="plug_calendar_day"><s:property value="#timePrice.dateStr" /></span>
									</div>
								</li>
							</s:else>
						</s:iterator>
					</ul>
				</s:iterator>
			</div>
		</div>
		</s:iterator>
	</div>
</div>
</s:iterator>
</s:iterator>
</div>
<!--时间价格表弹出层-->
<div class="bg_opacity1 show_hide"></div>
<iframe class="bg_opacity2 t_width_512 show_hide"></iframe>
<form id="changeSingleForm" method="post">
<input type="hidden" name="metaProductBranchId" id="metaProductBranchIdDiv"/>
<input id="beginDateDiv" type="hidden" name="ebkHouseStatus.beginDate" value="">
<input id="endDateDiv" type="hidden" name="ebkHouseStatus.endDate">
<div class="eject_rz t_width_500 show_hide" style="text-align:left;">
	<h4>修改房态</h4>
	<div class="t_xiugai_box">
		<ul class="roomStatus_list">
			<li><strong>当前房型：</strong>
				<p class="width_360">
					<b id="branchNameShow">大床/双床(74337)</b>
				</p></li>
			<li><strong>当前选择时间:</strong>
				<p id="beginDateShow">2012年2月27日</p></li>
			<li><strong>增减保留房：</strong>
				<p>
					<label style="margin-right: 17px;"><input name="ebkHouseStatus.baoliu" id="baoliuAddDiv" value="true" class="baoliu_add" checked="checked" type="radio">增加</label> 
					<label style="margin-left: 20px;"><input name="ebkHouseStatus.baoliu" id="baoliuReduceDiv" value="false" type="radio">减少</label> 
					<input id="baoliuQuantityInputDiv" name="ebkHouseStatus.baoliuQuantity" value="0" type="hidden">
					<input name="quantity" id="baoliuQuantityIdDiv" value="输入增加的数量" class="baoliu" onfocus="if (value =='输入增加的数量' || value =='输入减少的数量'){value =''}" onblur="if (value ==''){value='输入增加的数量' || value =='输入减少的数量'}" type="text"> (为空或非数字不做修改)
				</p></li>
			<li><strong>是否超卖：</strong>
				<p>
					<label style="margin-right: 49px;"><input name="ebkHouseStatus.chaomai" id="chaomaiTrueIdDiv" value="true" checked="checked" type="radio">是</label> 
					<label><input name="ebkHouseStatus.chaomai" id="chaomaiFalseIdDiv" value="false" type="radio">否</label>
					<font style="color:red;">（"是"代表保留房售完后订单自动变为需确认；"否"代表保留房售完后自动关房）</span>
				</p></li>
			<li><strong>是否满房：</strong>
				<p>
					<label><input name="ebkHouseStatus.manfang" id="manfangFalseIdDiv" value="MAN_FANG_FALSE" checked="checked" type="radio">非满房</label> 
					<label><input name="ebkHouseStatus.manfang" id="manfangTrueIdDiv" value="MAN_FANG_TRUE" type="radio">满房</label>
				</p></li>
			<li><input class="roomStatus_cx" value="保存修改" onclick="checkAndSubmit($('#changeSingleForm'));" type="button"></li>
		</ul>
	</div>
	<span class="close"></span>
</div>
</form>
<script type="text/javascript">
	$("#baoliuAddDiv").click(function() {
		$("#baoliuQuantityIdDiv").val("输入增加的数量");
	});
	$("#baoliuReduceDiv").click(function() {
		$("#baoliuQuantityIdDiv").val("输入减少的数量");
	});

	$("#manfangTrueIdDiv").click(function() {
		$("#baoliuAddDiv, #baoliuReduceDiv").attr("disabled", true);
		$("#chaomaiTrueIdDiv, #chaomaiFalseIdDiv").attr("disabled", true);
		$("#baoliuQuantityId").attr("disabled", true);

	});
	$("#manfangFalseIdDiv").click(function() {
		$("#baoliuAddDiv, #baoliuReduceDiv").attr("disabled", false);
		$("#chaomaiTrueIdDiv, #chaomaiFalseIdDiv").attr("disabled", false);
		$("#baoliuQuantityIdDiv").attr("disabled", false);
	});

	$("#baoliuQuantityIdDiv").change(function() {
		var v = $(this).val();
		var result = 0;
		if (!(v == '输入增加的数量' || v == '输入减少的数量')) {
			var val = parseInt(v);
			if (!isNaN(val)) {
				result = val;
			}
		}
		$("#baoliuQuantityInputDiv").val(result);
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
		;
	});
	$(".plug_calendar_d li").click(function() {
		if(!$(this).attr("metabranchid") || $(this).attr("metabranchid") < 0) {
			return false;
		}
		$("#changeSingleForm #branchNameShow").html($(this).attr("branchName"));
		$("#changeSingleForm #beginDateShow").html($(this).attr("date"));
		$("#changeSingleForm #metaProductBranchIdDiv").val($(this).attr("metaBranchId"));
		$("#changeSingleForm #beginDateDiv").val($(this).attr("date"));
		$("#changeSingleForm #endDateDiv").val($(this).attr("date"));
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
</script>