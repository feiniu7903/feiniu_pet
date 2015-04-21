<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购时间价格表</title>
<style type="text/css">
	.calculateHours {
		width: 20px;
	}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		$(".time_price_dlg_div .proLabel tr").hide();
		$(".time_price_dlg_div .proLabel tr.updatePrice").show();
	});
</script>
<script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
</head>
 
<body>
<input type="hidden" id="metaProductProductType" value="${metaProduct.productType}"/>
<input type="hidden" id="metaProductSubProductType" value="${metaProduct.subProductType}"/>
<input type="hidden" id="isAperiodic" value="${metaProduct.isAperiodic}" />
<input type="hidden" id="currPageDate"
			value="<s:date name="currPageDate" format="yyyy-MM-dd"/>" />
		<input type="hidden" id="metaProductId" value="${metaProductId}" />
		<input type="hidden" id="metaBranchId" value="${metaBranchId}" />
		<input type="hidden" id="payToSupplier"
			value="${metaProduct.payToSupplier}" />
<div class="Rilimid">
			<div id="metaTimeDiv" class="timeDiv">
				<s:include value="/WEB-INF/pages/back/calendar/meta_time.jsp"/>
			</div>
			<s:if test="editable">
            <%--<s:if test="!(metaProduct.subProductType=='SINGLE_ROOM' && metaProduct.productType=='HOTEL')">--%>
            <s:if test="!(metaProduct.subProductType=='SINGLE_ROOM' && metaProduct.productType=='HOTEL' && metaProduct.supplierId != 4257)">
            <form onsubmit="return false">
			<input type="hidden" name="timePriceBean.metaBranchId" value="${metaBranchId}"/>
			<input type="hidden" name="timePriceBean.productId" value="${metaProductId}"/>
			<div class="row4Time">
				<div class="newTab">
					<p>
						<b>修改选项：</b>
						<input name="advancedOpt"  class="radio01" type="radio" checked="checked" value="op1" />
						<span>修改价格</span>
						<s:if test="!metaProduct.IsAperiodic()">
							<input name="advancedOpt"  class="radio01" type="radio" value="op2" />
							<span>修改库存</span>
							<input name="advancedOpt"  class="radio01" type="radio" value="op4" />
							<span>修改自动清库存小时数</span>
							<input name="advancedOpt"  class="radio01" type="radio" value="op5" />
							<span>修改最晚取消小时数</span>
						</s:if>
						<input name="advancedOpt"  class="radio01" type="radio" value="op3" />
						<span>修改全部属性</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<s:if test="isTotalDecrease"><b> <font color="red">是否使用总库存：是</font></b></s:if>
						<s:if test="!isTotalDecrease"><b><font color="red">是否使用总库存：否</font></b></s:if>
					</p>
					<p class="textTime_new">
						<b>时间周期：</b>
						<input type="text" class="text1 date" name="timePriceBean.beginDate" readonly="readonly"/>
						<span>-</span>
						<input type="text" class="text1 date" name="timePriceBean.endDate" readonly="readonly"/><span>仅可操作2年内的时间</span>
					</p>
						<p>
						<b><input name="timePriceBean.weekOpen" type="checkbox" value="true" class="checkbox weekOpen" />按星期：</b>
						<input name="timePriceBean.monday" value="true" type="checkbox"
								class="week checkbox" disabled="disabled" />
						<span>星期一</span>
						<input name="timePriceBean.tuesday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期二</span>
						<input name="timePriceBean.wednesday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期三</span>
						<input name="timePriceBean.thursday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期四</span>
						<input name="timePriceBean.friday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期五</span>
						<input name="timePriceBean.saturday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期六</span>
						<input name="timePriceBean.sunday" value="true" type="checkbox"
							class="week checkbox" disabled="disabled" />
						<span>星期日</span>
					</p>
				</div>
				
				<table border="0" cellSpacing="0" cellPadding="0" width="90%"
					class="proLabel">
					<tr>
						<td>
							<b>禁售：</b>
						</td>
						<td colspan="5">
							<input name="timePriceBean.close" value="true" type="checkbox" class="checkbox" />
							<span>是</span>
						</td>
					</tr>
					<tr class="updatePrice">
						<td>
							<b>门市价：</b>
						</td>
						<td>
							<input type="text" size="8" maxlength="8" class="text1" name="timePriceBean.marketPriceF" />
						</td>						
						<td>
							<b>结算价：</b>
						</td>
						<td>
							<input type="text"  size="8"  maxlength="8"   class="text1" name="timePriceBean.settlementPriceF" /><input type="checkbox" name="skipSetPrice" value="true"/>设置为0
						</td>
						<td>
							<b>币种：</b>
						</td>
						<td>
							${metaProduct.currencyName}
						</td>
					</tr>
					<s:if test="metaProduct.productType=='HOTEL'">
					<tr  class="updatePrice">
						<td>
							<b>建议售价：</b>
						</td>
						<td colspan="5">
							<input type="text" size="8" maxlength="8" class="text1" name="timePriceBean.suggestPriceF" />
						</td>
					</tr>
					</s:if>
					<s:if test="!metaProduct.IsAperiodic()">
						<tr class="updateStock">
							<td>
								<b>日库存量：</b>
							</td>
							<td colspan="5">
								<input type="text" class="text1" name="timePriceBean.dayStock" />
								<i>注：-1意味着不限量，大于0的正数意味着库存</i>
							</td>
						</tr>
					</s:if>
					<s:if test="metaProduct.productType=='HOTEL'">
					<tr  class="updateStock" >
						<td>
							<b>早餐：</b>
						</td>
						<td colspan="3">
							 <select class="text1" name="timePriceBean.breakfastCount">
							  <option selected="selected" value="">请选择早餐数量</option>
							  <option value="0">0</option>
							  <option value="1">1</option>
							  <option value="2">2</option>
							  <option value="3">3</option>
							  <option value="4">4</option>
							  <option value="5">5</option>
							  <option value="6">6</option>
							  <option value="7">7</option>
							  <option value="8">8</option>
							  <option value="9">9</option>
							  <option value="10">10</option>
							</select>
						</td>
					</tr>
					</s:if>
					<tr class="updateStock">
						<td>
							<b>资源需确认：</b>
						</td>
						<td>
							<s:if test="metaProduct.IsAperiodic()">
								<input name="timePriceBean.resourceConfirm" class="radio01" type="radio" value="true" disabled="disabled" />
								<span>是</span>
								<input name="timePriceBean.resourceConfirm" class="radio01" checked="checked" type="radio" value="false" />
								<span>否</span>
							</s:if>
							<s:else>
								<input name="timePriceBean.resourceConfirm" class="radio01"
									type="radio" checked="checked" value="true" <s:if test="updateResourceConfirm">disabled="disabled"</s:if>/>
								<span>是</span>
								<input name="timePriceBean.resourceConfirm" class="radio01"
									type="radio" value="false" <s:if test="updateResourceConfirm">disabled="disabled"</s:if>/>
								<span>否</span>
							</s:else>
						</td>
						<td>
							<b>是否可超卖：</b>
						</td>
						<td colspan="3">
							<s:if test="metaProduct.IsAperiodic()">
								<input name="timePriceBean.overSale" class="radio01" type="radio" value="true" disabled="disabled" />
								<span>是</span>
								<input name="timePriceBean.overSale" class="radio01" type="radio" checked="checked" value="false"/>
								<span>否</span>
							</s:if>
							<s:else>
								<input name="timePriceBean.overSale" class="radio01" type="radio"
								value="true"/>
								<span>是</span>
								<input name="timePriceBean.overSale" class="radio01" type="radio" checked="checked" value="false"/>
								<span>否</span>
							</s:else>
						</td>
					</tr>
					<s:if test="!metaProduct.IsAperiodic()">
						<tr class="updateZeroStock">
							<td>
								<b>自动更改资源需审核小时数：</b>
							</td>
							<td colspan="5">
								<input type="text" class="text1" name="timePriceBean.zeroStockHour" />(<font color="red">此小时数仅对有效库存有效</font>，并且过了该时间下单时间价格表显示为0)
							</td>
						</tr>
						<tr class="updateHour">
							<td>
								<b>网站提前预订小时数<span class="require">[*]</span>：</b>
							</td>
							<td colspan="2">
								提前<input type="text" class="text1 calculateHours" name="dayInput" />天
								<input type="text" class="text1 calculateHours" name="hourInput" />时
								<input type="text" class="text1 calculateHours" name="minInput" />分
								<span class="showTimeSpan" style="color:red;"></span>
								<input type="hidden" name="timePriceBean.aheadHourFloat" />
							</td>
							<td colspan="2">
							<s:if test="metaProduct.productType == 'TICKET'">
								1.提前预订，请考虑下述因素：<br/>
								a.传真的景区，请录传真策略最晚时间<br/>
								b.非传真景区，请录入供应商要求最晚时间<br/>
								(传真策略为：<font color="red">自动调用传真策略 [有则显示，无则显示"非传真景区"]</font>)<br/>
								2.可当天预订，请考虑下述因素：<br/>
								a.供应商要求的最晚时间<br/>
								b.最短换票间隔小时数<br/>
								c.最晚换票时间<br/>
								</s:if><s:else>&nbsp;</s:else>
							</td>
							<td rowspan="2">
							案例：<br />
							如若订明天的产品，而该产品限定必须在今天的18点50分之前预订。
							那么，<br />
							a.天数填写，1天<br />
							b.小时数填写，18点<br />
							c.分钟数填写，50分<br />
							<s:if test="metaProduct.productType == 'TICKET'">
								<font color="red">基于0点算<br/></font>
								</s:if><s:else>&nbsp;</s:else>
							</td>
						</tr>
						<tr class="updateHour">
							<td>
								<b>退改策略<span class="require">[*]</span>：</b>
							</td>
							<td colspan="4">
								<input type="radio" name="timePriceBean.cancelStrategy" value="ABLE" />可退改(当提前预订小时数&lt;最晚取消小时数,产品可预授权 )<br />
								提前<input type="text" class="text1 calculateHours" name="dayInput" />天
								<input type="text" class="text1 calculateHours" name="hourInput" />时
								<input type="text" class="text1 calculateHours" name="minInput" />分
								<span class="showTimeSpan" style="color:red;"></span><br />
								<input type="hidden" name="timePriceBean.cancelHourFloat" />
								<input type="radio" name="timePriceBean.cancelStrategy" value="FORBID" />不退不改<br />
								<input type="radio" name="timePriceBean.cancelStrategy" value="MANUAL" />人工确定退改
							</td>
						</tr>
						<s:if test="metaProduct.productType == 'TICKET'">
							<tr>
								<td>
									<b>最早换票/使用时间<span class="require">[*]</span>：</b>
								</td>
								<td colspan="5">
									<input type="text" class="text1" name="timePriceBean.earliestUseTime" />
									<font color="red">如：09:00(分钟必须为10的倍数)</font>
								</td>
							</tr>
							<tr>
								<td>
									<b>最晚换票/使用时间<span class="require">[*]</span>：</b>
								</td>
								<td colspan="5">
									<input type="text" class="text1" name="timePriceBean.latestUseTime" />
									<font color="red">如：18:00(分钟必须为10的倍数)</font>
								</td>
							</tr>
						</s:if>
					</s:if>
				</table>
			</div>
			<!--row4Time end-->
			
			<p><em	class="button button2 saveTimePrice" style="margin: 0 200px;">修改</em></p>
			</form>
            </s:if>
			<a href="#log" class="showLogDialog" param="{'parentType':'META_TIME_PRICE','parentId':${metaBranchId}}">查看操作日志</a>
			</s:if>
		</div>
</body>
</html>


