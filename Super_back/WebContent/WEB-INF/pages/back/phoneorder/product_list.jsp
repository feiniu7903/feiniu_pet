<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="${basePath}js/phoneorder/important_tips.js"
	type="text/javascript">
</script>
<script src="${basePath}js/phoneorder/place_info.js"
	type="text/javascript">
</script>
</head>
<body>
	<!-- 门票 -->
	<s:if test="productType == 'TICKET'">
		<table width="100%">
			<s:if test="productList!= null && !productList.isEmpty()">
				<s:iterator value="productList" id="map">
					<tr class="newTableTit">
						<td width="5%"><s:property value="productId" /></td>
						<td><font color="red">${zhIsAperiodic }</font>
						<a href="javascript:void(0)" class="showPlaceInfo"
							productId="<s:property value='productId' />"><s:property
									value="productName" /> </a> &nbsp;&nbsp;&nbsp;( <s:property
								value="zhFilialeName" /> )&nbsp;&nbsp;&nbsp; <a
							class="showImportantTips" href="javascript:void(0)"
							productId="<s:property value='productId' />"><font
								color="red">产品信息<s:if test="isInteriorExist()">★</s:if></font> </a>&nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0)" class="showCouponInfo"
							productId="<s:property value='productId' />">优惠活动 </a></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table width="100%" class="newTable">
								<tr style="font-weight: bold;">
									<td width="18%">票种</td>
									<s:if test="IsAperiodic()">
										<td>产品有效期</td>
									</s:if>
									<td width="8%">最小/最大预订量</td>
									<td width="6%">市场价</td>
									<td width="6%">驴妈妈价</td>
									<td width="8%"></td>
								</tr>
								<s:iterator value="prodBranchList">
									<tr>
										<td>${branchName}<s:if test="hasBusinessCoupon==true">
												<font color="red">(惠)</font>
											</s:if> <s:if test="todayOrderAble=='true'">
												<font color="green">(支持手机端当天预订)</font>
											</s:if>
										</td>
										<s:if test="IsAperiodic()">
											<td><s:date name="validBeginTime" format="yyyy-MM-dd" />至<s:date
													name="validEndTime" format="yyyy-MM-dd" />
											<s:if test='invalidDateMemo != null && invalidDateMemo != ""'>
												(${invalidDateMemo })
											</s:if>		
											</td>
										</s:if>
										<td>${minimum }/${maximum }</td>
										<td>￥${marketPriceYuan }</td>
										<td style="color: red;">￥${sellPriceYuan}</td>
										<td><input type="button"
											onclick="loadLog({id:${productId }, mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
											value="预订" class="button" /></td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr style="font-weight: bold;">
					<td>无搜索结果</td>
				</tr>
			</s:else>
		</table>
	</s:if>
	<s:elseif test="productType == 'TRAFFIC'">
		<s:include value="/WEB-INF/pages/back/phoneorder/search/prod_traffic.jsp"/>
	</s:elseif>
	<!-- 酒店 -->
	<s:elseif test="productType == 'HOTEL'">
		<table width="100%">
			<s:if test="productList != null && !productList.isEmpty()">
				<s:iterator value="productList" id="map">
					<tr class="newTableTit">
						<td width="5%"><s:property value="productId" /></td>
						<td><font color="red">${zhIsAperiodic }</font>
						<a href="javascript:void(0)" class="showPlaceInfo"
							productId="<s:property value='productId' />"><s:property
									value="productName" /> </a> &nbsp;&nbsp;&nbsp; <s:property
								value="zhSubProductType" /> &nbsp;&nbsp;&nbsp; ( <s:property
								value="zhFilialeName" /> )&nbsp;&nbsp;&nbsp; <a
							class="showImportantTips" href="javascript:void(0)"
							productId="<s:property value='productId' />"><font
								color="red">产品信息<s:if test="isInteriorExist()">★</s:if></font> </a>&nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0)" class="showCouponInfo"
							productId="<s:property value='productId' />">优惠活动 </a></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table width="100%" class="newTable">
								<tr style="font-weight: bold;">
									<td height="35" width="10%">房型</td>
									<s:if test="IsAperiodic()">
										<td>产品有效期</td>
									</s:if>
									<td width="10%">可住人数</td>
									<td width="10%">市场价</td>
									<td width="10%">驴妈妈价</td>
									<td width="10%">早餐</td>
									<td width="10%">宽带</td>
									<td></td>
								</tr>
								<s:iterator value="prodBranchList">
									<tr>
										<td><a href="javascript:void(0)" class="description">${branchName}</a>
											<s:if test="hasBusinessCoupon==true">
												<font color="red">(惠)</font>
											</s:if> <s:if test="todayOrderAble=='true'">
												<font color="green">(支持手机端当天预订)</font>
											</s:if>
											<div
												style='display: none; border: 1px solid #000; position: absolute; bottom: -10px; right: 0; background: #fff; width: 700px; height: 30px; padding: 10px; margin: 0 2px;'
												class='description'>${description }</div></td>
										<s:if test="IsAperiodic()">
											<td><s:date name="validBeginTime" format="yyyy-MM-dd" />至<s:date
													name="validEndTime" format="yyyy-MM-dd" />
												<s:if test='invalidDateMemo != null && invalidDateMemo != ""'>
													(${invalidDateMemo })
												</s:if>		
											</td>
										</s:if>
										<td>${adultQuantity+childQuantity }</td>
										<td>￥${marketPriceYuan}</td>
										<td style="color: red;">￥${sellPriceYuan}</td>
										<td>${zhBreakfast }</td>
										<td>${zhBroadband }</td>
										<td><s:if test="#map.subProductType == 'SINGLE_ROOM'">
												<!-- 只能查看时间价格表 -->
												<s:if test="stock == -9999">
													<input type="button"
														onclick="loadLog({id:${productId }, noNext:true , mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, visitDate: $('#hotelBeginVisitDate').val(), leaveDate: $('#hotelEndVisitDate').val()})"
														value="预订" class="button btn_disabled" />
												</s:if>
												<s:else>
													<input type="button"
														onclick="loadLog({id:${productId }, mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, visitDate: $('#hotelBeginVisitDate').val(), leaveDate: $('#hotelEndVisitDate').val(), paramsStr: encodeURI('${paramsStr }')})"
														value="预订" class="button" />
												</s:else>
											</s:if> <s:else>
												<input type="button"
													onclick="loadLog({id:${productId }, mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
													value="预订" class="button" />
											</s:else></td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr style="font-weight: bold;">
					<td>无搜索结果</td>
				</tr>
			</s:else>
		</table>
	</s:elseif>
	<s:elseif test="productType == 'ROUTE'">
		<!--线路, 不定期显示部分 -->
		<s:if test='isAperiodic == "on" || isAperiodic == "true"'>
			<table width="100%">
				<s:if test="productList!= null && !productList.isEmpty()">
					<s:iterator value="productList" id="map">
						<tr class="newTableTit">
							<td width="5%"><s:property value="productId" /></td>
							<td><font color="red">${zhIsAperiodic }</font><a href="javascript:void(0)"
								class="showPlaceInfo"
								productId="<s:property value='productId' />"><s:property
										value="productName" /> </a> &nbsp;&nbsp;&nbsp;( <s:property
									value="zhFilialeName" /> )&nbsp;&nbsp;&nbsp; <a
								class="showImportantTips" href="javascript:void(0)"
								productId="<s:property value='productId' />"><font
									color="red">产品信息<s:if test="isInteriorExist()">★</s:if></font> </a>&nbsp;&nbsp;&nbsp; <a
								href="javascript:void(0)" class="showCouponInfo"
								productId="<s:property value='productId' />">优惠活动 </a></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<table width="100%" class="newTable">
									<tr style="font-weight: bold;">
										<td width="18%">票种</td>
										<td>产品有效期</td>
										<td width="8%">最小/最大预订量</td>
										<td width="6%">市场价</td>
										<td width="6%">驴妈妈价</td>
										<td>产品类型</td>
										<!-- <td>游玩天数</td> -->
										<td width="8%"></td>
									</tr>
									<s:iterator value="prodBranchList">
										<tr>
											<td>${branchName}<s:if test="hasBusinessCoupon==true">
													<font color="red">(惠)</font>
												</s:if>
											</td>
											<td><s:date name="validBeginTime" format="yyyy-MM-dd" />至<s:date
													name="validEndTime" format="yyyy-MM-dd" />
												<s:if test='invalidDateMemo != null && invalidDateMemo != ""'>
													(${invalidDateMemo })
												</s:if>
											</td>
											<td>${minimum }/${maximum }</td>
											<td>￥${marketPriceYuan }</td>
											<td style="color: red;">￥${sellPriceYuan}</td>
											<td>${zhSubProductType }</td>
											<!-- <td>${days }</td> -->
											<td><input type="button"
												onclick="loadLog({id:${productId }, mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
												value="预订" class="button" /></td>
										</tr>
									</s:iterator>
								</table>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr style="font-weight: bold;">
						<td>无搜索结果</td>
					</tr>
				</s:else>
			</table>
		</s:if>
		<s:else>
			<table width="100%" class="newTable">
				<s:if
					test="routeProductListMap != null && routeProductListMap.size > 0">
					<tr class="newTableTit">
						<td height="35" width="5%">ID &nbsp; <font color="red">${zhIsAperiodic }</font>
						</td>
						<td width="25%">产品名称</td>
						<td width="8%">产品类型</td>
						<td width="25%">游玩日期</td>
						<td width="6%">游玩天数</td>
						<td width="8%"></td>
					</tr>
					<s:iterator value="routeProductListMap" id="map">
						<tr>
							<td><s:property value="#map.key.productId" /></td>
							<td><a href="javascript:void(0)"><s:property
										value="#map.key.productName" /> </a> &nbsp;&nbsp;&nbsp; <a
								class="showImportantTips" href="javascript:void(0)"
								productId="<s:property value='#map.key.productId' />"><font
									color="red">产品信息<s:if test="#map.key.isInteriorExist()">★</s:if></font> </a>&nbsp;&nbsp;&nbsp; <a
								href="javascript:void(0)" class="showCouponInfo"
								productId="<s:property value='#map.key.productId' />">优惠活动 </a>
								<s:if test="#map.key.hasBusinessCoupon==true">
									<font color="red">(惠)</font>
								</s:if></td>
							<td><s:property value="#map.key.zhSubProductType" /></td>
							<td><s:iterator value="#map.value" id="datelist">
									<s:property />&nbsp;
							</s:iterator></td>
							<td><s:property value="#map.key.days" /> 天</td>
							<td><input type="button"
								onclick="loadLog({id:${map.key.productId }, testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
								value="预订" class="button" /></td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr style="font-weight: bold;">
						<td>无搜索结果</td>
					</tr>
				</s:else>
			</table>
		</s:else>
	</s:elseif>
	<s:else>
		<table width="100%">
			<s:if test="productList!= null && !productList.isEmpty()">
				<s:iterator value="productList" id="map">
					<tr class="newTableTit">
						<td width="5%"><s:property value="productId" /></td>
						<td><font color="red">${zhIsAperiodic }</font><s:property value="productName" /> &nbsp;&nbsp;&nbsp; <a
							class="showImportantTips" href="javascript:void(0)"
							productId="<s:property value='productId' />"><font
								color="red">产品信息<s:if test="isInteriorExist()">★</s:if></font> </a>&nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0)" class="showCouponInfo"
							productId="<s:property value='productId' />">优惠活动 </a></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table width="100%" class="newTable">
								<tr style="font-weight: bold;">
									<td width="18%">票种</td>
									<td width="8%">最小/最大预订量</td>
									<td width="6%">市场价</td>
									<td width="6%">驴妈妈价</td>
									<td width="8%"></td>
								</tr>
								<s:iterator value="prodBranchList">
									<tr>
										<td>${branchName}<s:if test="hasBusinessCoupon==true">
												<font color="red">(惠)</font>
											</s:if>
											<s:if test="todayOrderAble=='true'">
												<font color="green">(支持手机端当天预订)</font>
											</s:if>
										</td>
										<td>${minimum }/${maximum }</td>
										<td>￥${marketPriceYuan }</td>
										<td style="color: red;">￥${sellPriceYuan}</td>
										<td><input type="button"
											onclick="loadLog({id:${productId }, mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
											value="预订" class="button" /></td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr style="font-weight: bold;">
					<td>无搜索结果</td>
				</tr>
			</s:else>
		</table>
	</s:else>
	<div>
		<s:include value="/WEB-INF/pages/back/phoneorder/pag.jsp" />
	</div>
	<script type="text/javascript">
$(function() {
	$("a.description").live(
			"mouseover",
			function() {
				var $this = $(this);
				var $div = $this.parent().find("div.description");
				var pos = $this.offset();
				$div.css("text-align", "left").css("left", (pos.left) + "px")
						.css("top", (pos.top + 20)).show();
			});
	$("a.description").live("mouseout", function() {
		var $this = $(this);
		var $div = $this.parent().find("div.description");
		$div.hide();
	});
	
	$(".showCouponInfo").click(function() {
		var productId = $(this).attr('productId');
		if (typeof($CouponInfoDiv) == 'undefined') {
			$CouponInfoDiv = $('<div></div>');
			$CouponInfoDiv.appendTo($('body'));
		}
		$CouponInfoDiv.load('/super_back/phoneOrder/showCouponInfo.do', {
			productId : productId
		}, function() {
			$CouponInfoDiv.dialog( {
				title : "可参与优惠",
				width : 500,
				height: 400,
				modal : true
			})
		});
	});
	
	$(".showBusinessCouponInfo").click(function() {
		var productId = $(this).attr('productId');
		var prodBranchId = $(this).attr('prodBranchId');
		
		if (typeof($BusinessCouponInfoDiv) == 'undefined') {
			$BusinessCouponInfoDiv = $('<div></div>');
			$BusinessCouponInfoDiv.appendTo($('body'));
		}
		$BusinessCouponInfoDiv.load('/super_back/phoneOrder/showBusinessCouponInfo.do', {
			productId:productId,prodBranchId:prodBranchId
		}, function() {
			$BusinessCouponInfoDiv.dialog( {
				title : "可参与优惠策略",
				width : 500,
				height: 400,
				modal : true
			})
		});
	});
});
</script>
</body>
</html>
