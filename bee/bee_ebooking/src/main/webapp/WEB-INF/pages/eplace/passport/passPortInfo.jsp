<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<em class="snspt_tit1">订单信息</em>
<table class="snspt_tab2">
	<tr>
		<td class="snspt_tab2_l">订单号：</td>
		<td>${passPortInfo.orderId}</td>
	</tr>
	<tr>
		<td class="snspt_tab2_l">取票人：</td>
		<td>${passPortInfo.name}</td>
	</tr>
	<tr>
		<td class="snspt_tab2_l">手机号：</td>
		<td>${passPortInfo.mobile}</td>
	</tr>
	<tr>
		<td class="snspt_tab2_l">预计游玩日期：</td>
		<td>${passPortInfo.visitTime}</td>
	</tr>
</table>
<em class="snspt_tit1">产品信息</em>
<table class="snspt_tab3">
	<tr>
		<th>产品名称</th>
		<th>单价</th>
		<th>预订数量</th>
		<th>实际数量</th>
	</tr>
	<s:iterator value="orderItemMetas" var="item">
	  	<tr>
		    <td>${item.productName}</td>
		    <td>${item.sellPrice / 100}元</td>
		    <td>${item.quantity}</td>
		    <td>
				<select id="quantity_${item.orderItemMetaId}" name="quantity" onchange="changePrice(this);" price="${item.sellPrice}" orderItemMetaId="${item.orderItemMetaId}">
					<s:iterator begin="quantity" end="0" step="-1" var="qu">
						<option value="${qu }">${qu }</option>
					</s:iterator>
				</select>
			</td>
		</tr>
	</s:iterator>
</table>
<p class="snspt_payhint">
	未付款，需向您支付<span id="payMoneyId">${passPortInfo.priceYuan }</span>元！
</p>
<em class="snspt_tit1">操作人留言</em>
<textarea id="remark" name="remark" cols="" rows="" class="snspt_pop_txts"></textarea>