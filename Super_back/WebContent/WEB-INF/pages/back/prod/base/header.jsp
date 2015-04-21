<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table border="0" cellspacing="0" cellpadding="0" class="newTableB" style="border-collapse: collapse;">
<tr>
	<td><em>产品ID：</em></td>
	<td><s:property value="product.productId"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>产品名称：<span class="require">[*]</span></em></td>
	<td><s:textfield cssClass="text1 sensitiveVad"  name="product.productName" id="productName" maxLength="100"/></td>
	<td><em>产品编号：<span class="require">[*]</span></em></td>
	<td><s:textfield cssClass="text1" name="product.bizcode" maxLength="30"/></td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><s:checkbox name="product.additional" fieldValue="true" cssClass="checkbox checkbox2" />仅能捆绑销售</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>所属公司：<span class="require">[*]</span></em></td>
	<td><s:select cssClass="text1" name="product.filialeName" list="filialeNameList" listKey="code" listValue="name"/></td>
	<td><em>产品经理：<span class="require">[*]</span></em></td>
	<td><s:hidden name="product.managerId"/><input type="text" class="text1" id="inputUserId" name="permUser" value="${permUserRealName}" /></td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>取票人信息：</em></td>
	<td>
		<s:checkboxlist list="contactInfoOptionsList" name="productContactInfoOptions" listKey="code" listValue="name"/>
		<s:if test="product.productId != null && product.productType == 'TICKET'">
			<input id="addBlackList" type="button" value="下单控制"></input>
			<input id="queryBlackList" type="button" value="控制查询"></input>
		</s:if>
	</td>
	<td><em>上下线时间：</em></td>
	<td><input type="text" class="date text1" name="product.onlineTime" value="<s:property value="product.viewOnlineTime" />" /><i>-</i>
	<input type="text" class="date text1" name="product.offlineTime" value="<s:property value="product.viewOfflineTime" />" />
	<!-- 原来的显示
	<s:textfield cssClass="date text1" name="product.onlineTime"/> 
	<i>-</i><s:textfield cssClass="date text1" name="product.offlineTime"/>-->
	</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>第一游玩人：</em></td>
	<td><s:checkboxlist list="travellerInfoOptionsList" name="firstTravellerInfoOptions" listKey="code" listValue="name"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>其他游玩人：</em></td>
	<td><s:checkboxlist list="travellerInfoOptionsList" name="productTravellerInfoOptions" listKey="code" listValue="name"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<s:if test="productType != OTHER">
<tr>
	<td><em>是否为不定期产品：</em></td>
	<td>
		<s:if test="product.productId == null">
			<s:radio name="product.isAperiodic" list="#{'true':'是','false':'否'}"></s:radio><font color="red">（用于控制此销售产品的销售方式）</font>
		</s:if>
		<s:else>
			${product.zhIsAperiodic }
			<s:hidden name="product.isAperiodic" />
		</s:else>
	</td>
	<s:if test="productType=='HOTEL' || productType=='ROUTE'">
		<td><em>不定期产品提前预约天数：</em></td>
		<td>
		<s:textfield name="product.aheadBookingDays" />天</td>
		<td>&nbsp;</td>
	</s:if>
	<s:else>
		<td colspan="3">&nbsp;</td>
	</s:else>
</tr>
</s:if>
<s:if test="!product.hasSelfPack()">
<tr>
	<td><em>可显示的销售时间价格表天数：</em></td>
	<td><s:select list="showSaleDaysList" name="product.showSaleDays" listKey="code" listValue="code"/>天</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td><em>是否发送短信：</em></td>
	<td><s:radio list="BooleanList" name="product.sendSms" listKey="code" listValue="name" labelSeparator="&nbsp;"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
	<td><em>是否允许返现：</em></td>
	<td><s:radio name="product.isRefundable" list="#{'Y':'是','N':'否'}"  listKey="key" listValue="value" labelSeparator="&nbsp;"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<!-- 
<tr>
	<td class="refundBlock"><em>返现类型：</em></td>
	<td class="refundBlock"><s:radio list="#{'Y':'手动','N':'自动'}" name="product.isManualBonus" listKey="key" listValue="value" labelSeparator="&nbsp;"/></td>
	<td class="refundBlock"><em>返现金额(元)：</em></td>
	<td class="refundBlock"><input type="text" name="product.maxCashRefund" value=${product.maxCashRefund==null?0:product.cashRefundY } /></td>
	<td>&nbsp;</td>
</tr>
 -->
<tr class="refundBlock">
 	<td colspan="2" style="border-top:dotted 1px #A1C5E6;height:5px;">
 	</td>
</tr>
<tr class="refundBlock">
 	<td >
 		<em>返现类型：</em>
 	</td>
 	<td>
 		<s:radio list="#{'N':'比例返现','Y':'数值返现'}" name="product.isManualBonus" listKey="key" listValue="value" labelSeparator="&nbsp;"/>
 		<span style="font-weight:bold;">非特殊情况，请使用比例返现（按照毛利控制）</span>
 	</td>
 	<td colspan="2">&nbsp;</td>
</tr>
<tr class="refundBlock return_auto">
 	<td >
 		<em>返现值为产品毛利的：</em>
 	</td>
 	<td colspan="2"><input type="text" style="margin-right:5px;" name="product.bounsScale" value="<s:property value="product.bounsScale" />" class="text1" />
 			<span >%</span>&nbsp;&nbsp;<span style="color:red;">返现比如超过各中心审核峰值，需要完善以下三项信息，以便审核</span></td>
 	<td colspan="1">&nbsp;</td>
</tr>
<tr class="refundBlock return_person">
 	<td >
 		<em>返现金额：</em>
 	</td>
 	<td colspan="2">
	 	<input type="text"
	 		 style="margin-right:1px"
	 		 class="text1" name="product.maxCashRefund" value=${product.maxCashRefund==null?0:product.cashRefundY } />
	 	<span>元</span>&nbsp;&nbsp;<span style="color:red;">凡使用数值返现设置，均需要完善以下三项信息，以便审核</span>
 	</td>
 	<td colspan="1">&nbsp;</td>
</tr>
<tr class="refundBlock">
 	<td >
 		<em>投放原因：</em>
 	</td>
 	<td><s:textarea name="product.bounsReason" cols="75" rows="5"></s:textarea></td>
 	<td colspan="3">
	</td>
</tr>
<tr class="refundBlock">
 	<td >
 		<em>预计投放时长：</em>
 	</td>
 	<td>
 		<input type="text" class="date text1" name="product.bounsStart" value="<s:property value="product.bounsStartTime" />" />
 		<i>-</i>
		<input type="text" class="date text1" name="product.bounsEnd" value="<s:property value="product.bounsEndTime" />" />
 	</td>
 	<td colspan="3">&nbsp;</td>
</tr>
<tr class="refundBlock">
 	<td >
 		<em>预计投放总金额：</em>
 	</td>
 	<td>
 		<input 
 			type="text"
			name="product.bounsLimitYuan"
			value="<s:property value="product.bounsLimitYuan" />"
			class="text1" style="margin-right:0" />
			<span>元</span>
	</td>
 	<td colspan="2">&nbsp;</td>
</tr>
<tr class="refundBlock">
 	<td colspan="2" style="border-top:dotted 1px #A1C5E6;height:5px;">
 	</td>
</tr>

<s:if test="productType=='TICKET'">
<tr>
	<td><em>产品是否可以发起随时退？</em></td>
	<td><s:radio list="#{'Y':'是，可以发起','N':'否，不可以发起'}" name="product.freeBackable" listKey="key" listValue="value" labelSeparator="&nbsp;"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
	<td><em>短信内容：</em></td>
	<td><s:textarea name="product.smsContent" cols="75" rows="5"></s:textarea></td>
	<td colspan="3">
	<span id="normalSpan" <s:if test='product.IsAperiodic()'>style="display:none;"</s:if>>请勿重复输入模板中已包含的信息：<br/>
	 1.将此短信交景区验证，本凭证验证后即失效，切勿转发，多人请一起入园<br/>
	 2.如需帮助请致电驴妈妈旅游网客服中心10106060（免长话费）<br/>
	短信内容请尽量简短</span>
	<span id="aperiodicSpan" <s:if test='!product.IsAperiodic()'>style="display:none;"</s:if>>
	请勿重复输入门票不定期模版中已包含的信息：<br/>
	1、请在有效期XXXX-XX-XX至XXXX-XX-XX内将此短信交景区验证，本凭证验证后即失效，多人请一起入园。<br/>
	2、如需帮助请致电驴妈妈旅游网客服中心10106060（免长话费）
	</span>
	</td>
</tr>
</s:if>
<s:else>
<tr>
	<td><em>短信内容：</em></td>
	<td><s:textarea name="product.smsContent" cols="50" rows="5"></s:textarea></td>
	<td colspan="3">
		<span id="aperiodicSpan" style="display:none;">
		请勿重复输入线路不定期产品模版中已包含的信息：<br/>
		1、有效期XXXX-XX-XX至XXXX-XX-XX，具体出游时间请提前X天致电商家进行预约确认<br/>
		2、如需帮助请致电驴妈妈旅游网客服中心10106060（免长话费）
	</span>
	</td>
</tr>
</s:else>
</s:if>
<div id="addBlackListDiv" style="display: none"></div>
<div id="queryBlackListDiv" style="display: none"></div>
<script type="text/javascript">
$(function(){
    $("#addBlackList").click(function() {
    	$("#addBlackListDiv").load("<%=request.getContextPath()%>/prodblack/insertSkip.do?prodBlackList.productId=${product.productId}",function() {
    		$(this).dialog({
    			modal:true,
    			title:"新增黑名单",
    			width:700,
    			height:450
        	});
    	});
    });
    $("#queryBlackList").click(function() {
    	$("#queryBlackListDiv").load("<%=request.getContextPath()%>/pub/showBlackDialog.do?productId=${product.productId}",function() {
    		$(this).dialog({
    			modal:true,
    			title:"黑名单",
    			width:1000,
    			height:500
        	});
    	});
    });
});
</script>
</table>