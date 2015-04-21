<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<style>
.calendar {
	border: 2px solid #dedede;
	background: #f9f9f9;
	padding: 10px 20px;
	width: 655px;
	position: relative;
	margin: 10px auto;
}

.calendar .close {
	display: block;
	text-indent: -9999px;
	position: absolute;
	top: 10px;
	right: 10px;
}

.calendar_top {
	line-height: 30px;
	color: #666;
	padding: 0 10px;
	font-size: 12px;
	margin: 0;
}

.calendar_main {
	border: 1px solid #dedede;
	margin: 0 auto;
	padding: 0;
	font-size: 12px;
}

.calendar_main p {
	margin: 0;
}

.calendar_main td {
	height: 55px;
	width: 62px;
	line-height: 15px;
	border-top: 1px solid #E0E8EB;
	border-left: 1px solid #E0E8EB;
	background: #fff;
	padding: 3px;
	text-align: right;
	color: #BCBCBC;
	font-weight: 700;
}

.table_tit td {
	height: 18px;
	line-height: 18px;
	text-align: center;
	font-weight: 700;
	background: #FBFFF1;
	color: #333;
	border-top: none;
}

.table_tit .weekend {
	color: #CE0506;
}

.calendar_main .month {
	border-left: none;
	vertical-align: middle;
	text-align: center;
	color: #333;
	padding: 0;
}

.month_text {
	height: 356px;
	position: relative;
	background: #F8F8F8;
}

.month_text p {
	padding: 50px 0;
	text-align: center;
}

.month_text .current_month {
	padding: 86px 0;
	border-bottom: 1px solid #E0E8EB;
	background: #fff;
}

.month_text .next_month {
	padding: 159px 0;
	background: #fff;
}

.month_text span {
	cursor: pointer;
}

.front_month {
	position: absolute;
	top: 22px;
	left: 25px;
}

.back_month {
	position: absolute;
	bottom: 22px;
	left: 25px;
}

.calendar_main .next_month_td,.next_month_tr td {
	background: #f8f8f8;
}

.calendar_main .month_top {
	background: #fff;
}

.month_text .next_month_two {
	background: #ffffff;
	padding: 147px 0;
}

.calendar_main .calendar_pro {
	color: #333;
}

.calendar_pro small {
	margin-right: 5px;
}

.calendar_pro span {
	font-weight: 500;
	color: #666;
}

.calendar_pro b {
	color: #ff6600;
	font-weight: 500;
	font-family: arial;
}
</style>

 <style type="text/css">
    .back_tip_timeTable{display:none;position:absolute; z-index:999; margin-left:-180px; width:200px; padding:9px;text-align:left;border:1px solid #ccc; background:#efefef;}
    .back_tip_timeTable p{font:normal 12px/18px "Arial";}
 </style>  
	</head>
	<body>
		<s:if test="calendarList == null">
			该商品不可售！
		</s:if>
		<s:else>
		<div class="calendar_free" style="font-size: 12px;">
			<s:if test="product != null && !product.IsAperiodic()">
				<s:if test="justShow == null">
					<s:if test="product.productType == 'HOTEL'">
						<s:if test="product.subProductType == 'SINGLE_ROOM'">
						入住时间：${visitDate}&nbsp;离店时间：${leaveDate}
					</s:if>
						<s:else>
							入住时间：<span id="vDateSpan"></span><br/>
							最晚预订时间：<span id="cannelHourSpan"></span>
						</s:else>
					</s:if>
					<s:else>
					游玩时间：<span id="vDateSpan"></span><br/>
					最晚预订时间：<span id="cannelHourSpan"></span>
					</s:else>
				</s:if>
			</s:if>
			<div class="calendar">
				<form action="${basePath}/phoneOrder/makeorder.do" id="myForm">
					<input type="hidden" name="orderId" value="${orderId}" />
					<s:iterator value="calendarList" status="callist" var="calModel">
						<table width="652" border="0" cellspacing="0" cellpadding="0"
							class="calendar_main"
							<s:if test="#callist.index!=0">style="display:none;" </s:if>
							id="timePrice${month}">
							<tr class="table_tit">
								<td rowspan="7" class="month">
									<div class="month_text">
										<s:if test="#callist.index>0">
											<span class="front_month"
												onclick="showOrHide(event,'timePrice${month}','timePrice${month-1==0?12:month-1}')">▲</span>
										</s:if>
										<p class="current_month">
											<s:property value="month" />
											月
										</p>
										<br />
										<s:if test="flagNextMonth>0">
											<p>
												<s:property value="flagNextMonth" />
												月
											</p>
										</s:if>
										<s:if test="!#callist.last">
											<span class="back_month"
												onclick="showOrHide(event,'timePrice${month}','timePrice${(month%12)+1}')">▼</span>
										</s:if>
									</div>
								</td>
								<td class="weekend">
									星期日
								</td>

								<td>
									星期一
								</td>
								<td>
									星期二
								</td>
								<td>
									星期三
								</td>
								<td>
									星期四
								</td>
								<td>
									星期五
								</td>
								<td class="weekend">
									星期六
								</td>
							</tr>
							<s:iterator value="calendar" var="ca1">
								<tr>
									<s:iterator value="#ca1" status="cal2" var="ca2">
										<s:if
											test="(product != null && product.subProductType != 'SINGLE_ROOM') && (#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' )">
											<td
												<s:if test="justShow==null && !product.IsAperiodic()">onclick="onclickTimePrice('${ca2.specDate}','${ca2.latestScheduledTime}');"</s:if>
												<s:if test="#calModel.isShowNextMonth(#ca2.specDate)"> class="next_month_td calendar_pro"</s:if>
												<s:else>class="calendar_pro"</s:else>
												style="cursor: pointer;">
										</s:if>
										<s:else>
											<td
												<s:if test="#calModel.isShowNextMonth(#ca2.specDate)"> class="next_month_td calendar_pro"</s:if>
												<s:else>class="calendar_pro"</s:else>>
										</s:else>
										<s:date name="#ca2.specDate" format="dd" />
										<br />
										<s:if test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">
											<b>&yen;<s:property value="#ca2.priceF" /> </b>
											<span>  
												<s:if test="#ca2.dayStock==-1">
													<br />
													<font>不限</font>
													 <s:if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '') || (#ca2.cuCouponFlag > 0)">
											   			  <font style="color:red">促</font>
											   		 </s:if>
												</s:if> 
												
												<s:elseif test="#ca2.dayStock>-1&&#ca2.dayStock!=0">
													<br />
													<font><s:property value="#ca2.dayStock" /> </font>
													 <s:if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '') || (#ca2.cuCouponFlag > 0)">
											   			  <font style="color:red">促</font>
											   		 </s:if>
												</s:elseif> 
												
												<s:elseif test="#ca2.overSale=='true'">
													<br />
													<font>可超售</font>
													 <s:if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '') || (#ca2.cuCouponFlag > 0)">
											   			  <font style="color:red">促</font>
											   		 </s:if>
												</s:elseif> 
												
												<s:elseif test="#ca2.onlyForLeave==true">
													<br />
													<font>仅能离店</font>
													 <s:if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '') || (#ca2.cuCouponFlag> 0)">
											   			  <font style="color:red">促</font>
											   		 </s:if>
												</s:elseif> 
												
												<s:elseif test="!#ca2.isSellable(0)">
													<br />
													<font>售完</font>
												</s:elseif> 
												
												<s:if test="#ca2.todayOrderAble=='true'">
													<font color="green"><br/>[手机当天订]
														<s:if test="#ca2.aheadHourByPayKind!=null && #ca2.aheadHourByPayKind!=''">
															<s:property value="#ca2.aheadHourByPayKind"/>
														</s:if>
														<s:if test="#ca2.lastReserveHourByPayKind!=null && #ca2.lastReserveHourByPayKind!=''">
															最晚<s:property value="#ca2.lastReserveHourByPayKind"/>前
														</s:if>
													</font>
												</s:if>
												</span>
										</s:if>
										<s:if test="#ca2.favorJsonParams != null && #ca2.favorJsonParams != ''">
											<!--日历上展示的优惠信息参数-->
							                <input type="hidden" value='${favorJsonParams}'/>
							   			</s:if>
										</td>
									</s:iterator>
								</tr>
							</s:iterator>
						</table>
					</s:iterator>
					<s:if test="justShow == null">
						<input type="hidden" id="productId" name="productId" value="${id}" />
						<input type="hidden" id="testOrder" name="testOrder"
							value="${testOrder}" />
						<s:if test="orderChannel != null">
							<input type="hidden" name="orderChannel" value="${orderChannel}" />
						</s:if>
						<input type="hidden" id="vDate" name="visitDate"
							value="${visitDate}" />
						<input type="hidden" name="leaveDate" value="${leaveDate}" />
						<center>
							<!-- 先显示入口类别 -->
							<s:iterator value="productBranchList">
								<s:if
									test="mainProdBranchId != null && mainProdBranchId == prodBranchId">
							${branchName}(￥<span id="sellPrice_${prodBranchId}">${sellPriceYuan}</span>&nbsp;&nbsp;库：<span
										id="stock_${prodBranchId}" style="color: red;">${stock==-1?"不限":stock}</span>
										&nbsp;&nbsp;资源审核：<span id="resource_${prodBranchId}"
										style="color: red;">${resource}</span>
										) <input type="text" size="3" class="countInput"
										value="${minimum}" minimum="${minimum}" maximum="${maximum}"
										stock="${stock}"
										name="prodBranchItemMap.branch_${prodBranchId}"
										id="branch_${prodBranchId}">
									<br />
									<s:if test="product.IsAperiodic()">
									有效期：<s:date name="validBeginTime" format="yyyy/MM/dd" />-
									<s:date name="validEndTime" format="yyyy/MM/dd" /><br />
									<s:if test='invalidDateMemo != null && invalidDateMemo != ""'>
										(${invalidDateMemo })
									</s:if><br/>
									</s:if>
								</s:if>
							</s:iterator>
							<s:iterator value="productBranchList">
								<s:if
									test="mainProdBranchId != null && mainProdBranchId == prodBranchId"></s:if>
								<s:else>
							${branchName}(￥<span id="sellPrice_${prodBranchId}">${sellPriceYuan}</span>&nbsp;&nbsp;库：<span
										id="stock_${prodBranchId}" style="color: red;">${stock==-1?"不限":stock}</span>
										&nbsp;&nbsp;资源审核：<span id="resource_${prodBranchId}"
										style="color: red;">${resource}</span>
										) <input type="text" size="3" class="countInput"
										value="0" minimum="${minimum}" maximum="${maximum}"
										stock="${stock}"
										name="prodBranchItemMap.branch_${prodBranchId}"
										id="branch_${prodBranchId}">
									<br />
									<s:if test="product.IsAperiodic()">
									有效期：<s:date name="validBeginTime" format="yyyy/MM/dd" />-
									<s:date name="validEndTime" format="yyyy/MM/dd" /><br />
									<s:if test='invalidDateMemo != null && invalidDateMemo != ""'>
										(${invalidDateMemo })
									</s:if><br/>
									</s:if>
								</s:else>
							</s:iterator>
						</center>
						<s:if test="!noNext">
							<center>
								<s:if test="productBranchList!=null && productBranchList.size() > 0">
									<input type="button" onclick="beforeSubmit();" value="下一步"
										id="nextStep" class="button" />
								</s:if>
							</center>
							<input type="hidden" name="paramsStr" value="${paramsStr}" />
							<input type="hidden" name="isAperiodic" id="isAperiodic" value="${product.isAperiodic}" />
							<input type="hidden" name="mainProdBranchId"
								value="${mainProdBranchId}" />
						</s:if>
					</s:if>
				</form>
			</div>
			<!--calendar end-->
		</div>
		<!--calendar_free  end-->
		</s:else>
		<script type="text/javascript">
function showOrHide(event, hide, show) {
	if (event.stopPropagation) {
		event.stopPropagation();
	} else {
		event.cancelBubble = true;
	}
	if ($("#" + show).attr("id") != undefined) {
		$("#" + hide).hide();
		$("#" + show).fadeIn();
	}
}
function onclickTimePrice(date,LatestScheduledTime) {
	var d = $.format.date(date, "yyyy-MM-dd");
	var latetime=$.format.date(LatestScheduledTime, "yyyy-MM-dd HH:mm");
	$('#vDateSpan').text(d);
	$('#cannelHourSpan').text(latetime);
	$('#vDate').val(d);
	$
			.ajax( {
				type : "POST",
				dataType : "json",
				data : {
					visitDate : d,
					id : $('#productId').val(),
					testOrder : $('#testOrder').val()
				},
				url : "/super_back/common/checkDayInfo.do",
				async : false,
				timeout : 3000,
				success : function(data) {
					if (data.success) {
						var saleFlag = false;
						$('span[id^="stock_"]')
								.each(
										function() {
											var prodBranchId = $(this).attr(
													'id').substr(6);
											$('#stock_' + prodBranchId)
													.removeAttr('canSale');
											var flag = false;
											for ( var i = 0; i < data.array.length; i++) {
												var branchId = data.array[i].branchId;
												if (prodBranchId == branchId) {
													var sellPrice = data.array[i].sellPrice;
													var stock = data.array[i].stock;
													var canSale = data.array[i].canSale;
													var resource = data.array[i].resource;
													var isgugongproduct = data.array[i].isGugongProduct;
													var stCon = stock;
													if (stock == null) {
														stCon = "不可售";
													} else if (stock == -1) {
														stCon = "不限";
													}
													$('#branch_' + branchId)
															.val(
																	$(
																			$('#branch_' + branchId))
																			.attr(
																					'minimum'));
													$('#branch_' + branchId)
															.show();
													$('#sellPrice_' + branchId)
															.text(sellPrice);
													$('#resource_' + branchId)
															.text(resource);
													$('#branch_' + branchId)
															.attr('stock',
																	stock);
													if (canSale != null) {
														if (!canSale) {
															if(isgugongproduct){
															   stCon =data.array[i].hourRangeMsg;	
															}else{
																if(data.array[i].hourRangeMsg!=null){
																	stCon=data.array[i].hourRangeMsg;
																}else{
																stCon = "时间限制，不可售";
																}
															 }
															$(
																	'#stock_' + branchId)
																	.attr(
																			'canSale',
																			'false');
															$(
																	'#branch_' + branchId)
																	.hide();
														}
													}
													$('#stock_' + branchId)
															.text(stCon);
													flag = true;
													break;
												}
											}
											if (!flag) {
												$('#branch_' + prodBranchId)
														.hide();
												$('#branch_' + prodBranchId)
														.attr('stock', '0');
												$('#branch_' + prodBranchId)
														.val('0');
												$('#stock_' + prodBranchId)
														.text("不可售");
												$('#stock_' + prodBranchId)
														.attr('canSale',
																'false');
											}
											var attr = $(
													'#stock_' + prodBranchId)
													.attr('canSale');
											if (attr != undefined
													&& attr == 'false') {
											} else {
												saleFlag = true;
											}
										});
						if (!saleFlag) {
							$('#nextStep').attr('disabled', true);
						} else {
							$('#nextStep').removeAttr('disabled');
						}
					}
				}
			});
}
function beforeSubmit() {
	var flag = false, msg = "";
	var isAperiodic = $("#isAperiodic").val();
	//不定期产品不需验证
	if(isAperiodic != null && isAperiodic == "true") {
	} else {
		if ($('#vDate').val() == "") {
			alert("请选择游玩日期！");
			return;
		}
	}
	$('input.countInput').each(
			function() {
				var val = $.trim($(this).val());
				var minimum = parseInt($(this).attr('minimum'));
				var maximum = parseInt($(this).attr('maximum'));
				var stock = parseInt($(this).attr('stock'));
				if (stock != 0) {
					if (val != "" && parseInt(val) > maximum) {
						msg = "不能大于最大订购量！";
						flag = false;
						return false;
					}
					if (val != "" && (stock != -1 && parseInt(val) > stock)) {
						msg = "不能大于库存！";
						flag = false;
						return false;
					}
					if (val != "" && parseInt(val) < minimum) {
						msg = "不能小于最小订购量！";
						flag = false;
						return false;
					}
					if (val != "" && parseInt(val) > 0
							&& parseInt(val) >= minimum
							&& parseInt(val) <= maximum) {
						flag = true;
						return false;
					}
				}
				$(this).val(val);
			});
	if (flag) {
		document.getElementById("myForm").submit();
	} else {
		if (msg != "") {
			alert(msg);
		} else {
			alert("未选购产品！");
		}
	}
}

$(function() {
	//限制文本框输入
	$('input.countInput').bind('keyup', function() {
		var v = $.trim($(this).val());
		if (v.length > 0) {
			if (isNaN(v)) {
				alert("只能输入数字！");
				$(this).val($(this).attr('minimum'));
				return;
			}
			if (v.indexOf(".") > 0) {
				alert("只能输入整数！");
				$(this).val($(this).attr('minimum'));
				return;
			}
		}
		$(this).val(v);
	});
	pushFavorTipInfo();//存放优惠提示信息
});
var HtmlTip="<div class='back_tip_timeTable'>\
    <p></p>\
   </div>";

function pushFavorTipInfo(){ //存放优惠提示信息
	$("td.calendar_pro").mouseenter(function(){ 
		$(this).find(".back_tip_timeTable").hide();
		var priceInfo=$(this).find("input").val();
		if(!priceInfo){ 
			return; 
		} 
		var arr = $.parseJSON(priceInfo); 
		var endArr=[],Html=""; 
		$.each(arr,function(i,n){ 
			if(!n.param){ 
				var rlt=$("#tipTemplete"+n.index).val(); 
			}else{ 
				var valueArr = n.param.split("|"); 
				var rlt=$("#tipTemplete"+n.index).val().replace(/\{(\d+)\}/g,function(a,b){ 
					return valueArr[b-1]; 
				}); 
			} 
			Html+=rlt+"<br/>"; 
		}); 
		//$(this).attr("tip-content",Html);
		if($(this).find(".back_tip_timeTable").length==0){
			$(this).append(HtmlTip);
	    }
		$(this).find(".back_tip_timeTable").show().find("p").html(Html);
	}).mouseleave(function(e){
		$(this).find(".back_tip_timeTable").hide();
	});
	
	             
}
</script>

<!--日历上展示的优惠信息模板-->
<input type="hidden" value="{1}： 立减<font style='color:#ff6600;font-family: arial;'>{2}</font>元/份。" id="tipTemplete1"/>
<input type="hidden" value="{1}，{2}份立减<font style='color:#ff6600;font-family: arial;'>{3}</font>元，{4}份立减<font style='color:#ff6600;font-family: arial;'>{5}</font>元，以此类推。" id="tipTemplete2"/>
<input type="hidden" value="{1}，{2}份起订，每份立减<font style='color:#ff6600;font-family: arial;'>{3}</font>元。" id="tipTemplete3"/>
<input type="hidden" value="{1}，{2}份起订，再订{3}份立减<font style='color:#ff6600;font-family: arial;'>{4}</font>元，再订{5}份立减<font style='color:#ff6600;font-family: arial;'>{6}</font>元，以此类推。" id="tipTemplete4"/>
<input type="hidden" value="<b>预订{1}日产品，可享以下优惠</b>" id="tipTemplete5"/>
<input type="hidden" value="<b>同时多买可享优惠</b>(预订同种产品以最实惠规则让利)" id="tipTemplete6"/>
<input type="hidden" value="<b>当前日期的价格为促销价</b>" id="tipTemplete7"/>

	</body>
</html>

