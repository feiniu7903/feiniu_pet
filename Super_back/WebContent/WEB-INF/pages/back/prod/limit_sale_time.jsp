<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("input.longDateFormat").datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 5,
			stepSecond : 5,
			showButtonPanel : false
		});
		$("input.shortDateFormat").datetimepicker({
			showTimepicker : false,
			showButtonPanel : false
		});
	});
</script>
	<div id="productSearchTab">
		<ul>
			<li><a href="#tabs_1">限售日期</a></li>
			<li><a href="#tabs_2">限售时间点</a></li>
		</ul>
		<div id="tabs_1">
		<form name="form1">
	     <input type="hidden" name="saleTime.productId"
		value="${product.productId}" />
			<table>
				<tr>
					<td colspan="3" align="left">设置</td>
				</tr>
				<tr>
					<td>限制销售开始时间：</td>
					<td><input type="text" name="saleTime.limitSaleTime"
						class="longDateFormat" readonly="readonly" /></td>
					<td>限制销售游玩时间：</td>
					<td><input type="text" name="saleTime.limitVisitTime"
						style="width: 100px;" class="longDateFormat" readonly="readonly" /></td>
					<td><input type="button" class="saveLimitSale" value="确定" /></td>
				</tr>
			</table>
			</form>
		</div>
		<div id="tabs_2">
		<form name="form2">
		<input type="hidden" name="saleTime.productId"
		value="${product.productId}" />
		<input type="hidden" value="HOURRANGE" name="saleTime.limitType"/>
			<table>
				<tr>
					<td><b>限售开始时间<span class="require">[*]</span>：
					</b></td>
					<td colspan="5"><input type="text" class="text1"
						name="saleTime.limitHourStart" /> <font color="red">格式如：09:00</font>
					</td>
				</tr>
				<tr>
					<td><b>限售结束时间<span class="require">[*]</span>：
					</b></td>
					<td colspan="5"><input type="text" class="text1"
						name="saleTime.limitHourEnd" /> <font color="red">格式如：18:00</font>
					</td>
				</tr>
				<tr>
					<td><input type="button" class="saveLimitHourSale" value="确定" /></td>
				</tr>
			</table>
		</form>
		</div>
	</div>
	<div id="limitSaleDataDiv">
				<s:include value="/WEB-INF/pages/back/prod/limt_sale_list.jsp" />
	</div>
<SCRIPT type="text/javascript">
	//加载限售日期、限售时间点tabs
	$(function() {
		$('#productSearchTab').tabs();
	});
</SCRIPT>