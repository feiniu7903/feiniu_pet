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
		<title>super后台——采购产品价格表</title>
		<script type="text/javascript" src="<%=basePath%>js/prod/date.js">
		</script>
		<script type="text/javascript">
		$(function(){
			$(document).ready(function(){
				$(".new_price input[name=timePriceBean.priceType]:checked").trigger("change");
			})
		})
</script>
	</head>

	<body>
		<div class="Rilimid Rilimid02">
			<input type="text" style="border: none;" />
			<div id="prodTimeDiv" class="timeDiv">
				<s:include value="/WEB-INF/pages/back/calendar/prod_time.jsp" />
			</div>
			<s:if test="editable">
				<form onsubmit="return false">
					<input type="hidden" name="timePriceBean.prodBranchId"
						value="${prodBranchId}" />
					<input type="hidden" name="visaSelfSign"
						value="${visaSelfSign}" />
					<input type="hidden" name="selfPack"
						value="<s:property value="product.hasSelfPack()"/>" />
					<div class="row4Time">
						<p class="textTime" style="text-align: left">
							<b>产品上线周期：</b>
							<s:date name="product.onlineTime" format="yyyy-MM-dd" />
							~
							<s:date name="product.offlineTime" format="yyyy-MM-dd" />
							<br />
							<b>时间周期：</b>
							<input type="text" class="text1 date"
								name="timePriceBean.beginDate" readonly="readonly" />
							<span>-</span>
							<input type="text" class="text1 date"
								name="timePriceBean.endDate" readonly="readonly" />
							<s:if test="hasMultiJourney()"><br /><br />
								<b>适用行程：</b>
								<s:select name="timePriceBean.multiJourneyId" list="viewMultiJourneyList" listKey="multiJourneyId" listValue="journeyName" headerKey="0" headerValue="请选择" cssStyle="font-size:12px;"></s:select>
								<a style="font-weight:normal;color:blue;" href="/super_back/view/queryMultiJourneyList.do?productId=${product.productId }">查看行程</a>
							</s:if>
                            <s:elseif test="hasMultiJourney()=='true'"><br /><br />
								<b>适用行程：</b>
								<s:select name="timePriceBean.multiJourneyId" list="viewMultiJourneyList" listKey="multiJourneyId" listValue="journeyName" headerKey="0" headerValue="请选择" cssStyle="font-size:12px;"></s:select>
								<a style="font-weight:normal;color:blue;" href="/super_back/view/queryMultiJourneyList.do?productId=${product.productId }">查看行程</a>
							</s:elseif>
							<s:if test="product.productType == 'TICKET'">
								<b style="width:150px">是否支持手机当天预订：</b>
								<s:if test='todayOrderAble !=null && todayOrderAble == "true"'>是</s:if>
								<s:else>否</s:else>
							</s:if>
						</p>
						<ul>
							<li>
								<dl class="new_price">
									<dd>
										<b>禁售：</b>
										<input name="timePriceBean.close" type="checkbox" value="true"
											class="checkbox" />
										<span>是</span>
									</dd>
									<dd>
										<b><input name="timePriceBean.weekOpen" value="true"
												type="checkbox" class="checkbox weekOpen" />按星期：</b>
										<input name="timePriceBean.monday" id="monday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期一</span>
										<input name="timePriceBean.tuesday" type="checkbox"
											value="true" class="week checkbox" disabled="disabled" />
										<span>星期二</span>
										<input name="timePriceBean.wednesday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期三</span>
										<input name="timePriceBean.thursday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期四</span>
										<input name="timePriceBean.friday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期五</span>
										<input name="timePriceBean.saturday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期六</span>
										<input name="timePriceBean.sunday" value="true"
											type="checkbox" class="week checkbox" disabled="disabled" />
										<span>星期日</span>
									</dd>
									
									<s:if test="!product.hasSelfPack()">

									<dd>
										<b>选择价格模式：</b>
										<input type="radio" name="timePriceBean.priceType" value="FIXED_PRICE" checked="checked"/>固定价格&nbsp;
										<s:if test="product.IsAperiodic()">
											<input type="radio" name="timePriceBean.priceType" value="RATE_PRICE" disabled="disabled" />比例价格&nbsp;
											<input type="radio" name="timePriceBean.priceType" value="FIXED_ADD_PRICE" disabled="disabled"/>固定加价<br/>
										</s:if>
										<s:else>
											<input type="radio" name="timePriceBean.priceType" value="RATE_PRICE"/>比例价格&nbsp;
											<input type="radio" name="timePriceBean.priceType" value="FIXED_ADD_PRICE"/>固定加价<br/>
										</s:else>
										<div id="price_type_desc_div">
											<span class="FIXED_PRICE" style="display:none">固定价格是不会根据采购价变化而变化的价格，系统永久默认的销售价，请在销售价格中填写价格</span>
											<span class="RATE_PRICE" style="display:none">比例价格是根据采购价格的变化自动乘以一个比例来计算的价格，系统会自动调整计算，请在销售价格中填写比例。例如：需要在总成本上上浮10%做为销售价，请填写10。</span>
											<span class="FIXED_ADD_PRICE" style="display:none">固定加价是根据采购价格的变化自动加一个固定的价格，系统会根据成本自动调整计算，请在销售价格中填写加价金额，加价金额不支持小数。例如：需要在总成本加80元为销售价，请填写80。</span>
										</div>
									</dd>								
										<dd>
											<b><input name="promotionsFlag" id="promotionsFlag" onclick="checkPromotions()" type="checkbox" class="checkbox" />降价促销：</b>
											<t>&nbsp;</t>
											<input type="radio" name="promotions" id="noPromotions" value="0" checked="checked" disabled="disabled"/>取消&nbsp;
											<input type="radio" name="promotions" id="hasPromotions" value="1" disabled="disabled"/>促销&nbsp;
										</dd>
										<dd class="sellPrice">
											<span class="FIXED_PRICE" style="display:none">
												<b>驴妈妈价：</b>
												<input type="text" class="text1" name="timePriceBean.priceF" />
												<input type="checkbox" name="skipSetPrice" value="true"
													class="checkbox" />
												<t>设置为0</t>
											</span>
											<span class="RATE_PRICE" style="display:none">
												<b>比例价格：</b>
												<input type="text" class="text1" name="timePriceBean.ratePrice" />
											</span>
											<span class="FIXED_ADD_PRICE" style="display:none">
												<b>固定加价：</b>
												<input type="text" class="text1" name="timePriceBean.fixedAddPriceF" />
											</span>
										</dd>
									</s:if>
									
									<s:else>
	                                	自由行产品自动计算
	                                </s:else>
	                                <s:if test="product.IsAperiodic()">
		                                <dd>
											<b>不可游玩日期描述：</b>
											<input name="invalidDateMemo" value="${invalidDateMemo }" type="text" class="text1" style="width:300px" />
										</dd>
									</s:if>
								</dl>
							</li>
						</ul>
					</div>
					<!--row4Time end-->
					<p class="main4Bottom">
						<em class="button button2 saveTimePrice" style="margin: 0 125px;" <s:if test='isCanSave=="false"'>disabled="disabled"</s:if>>保存</em>
					</p>
				</form>
				<a href="#log" class="showLogDialog"
					param="{'parentType':'PROD_TIME_PRICE','parentId':${prodBranchId}}">查看操作日志</a>
			</s:if>
		</div>
	</body>
<script type="text/javascript">
function checkPromotions() {
		if ($("#promotionsFlag").attr("checked")) {
			$("#hasPromotions").removeAttr("disabled");
			$("#noPromotions").removeAttr("disabled");
		}else{
			$("#hasPromotions").attr("disabled","disabled");
			$("#noPromotions").attr("disabled","disabled");
		}
	}
</script>
</html>


