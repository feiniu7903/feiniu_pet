<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<h3 class="titBotton">其他属性</h3>  	
<div class="rowpro rowpro02">   
		<table border="0" cellSpacing="0" cellPadding="0" class="newTableB" >
			<tr>
				<td><em>支付对象：</em></td>
				<td><s:radio list="payToTargetList" name="payToTarget" listKey="code" listValue="name"/></td>
			</tr>			
			<tr>
				<td><em>EBK/传真任务生成时间：</em></td>
				<td>
					<s:radio name="metaProduct.isResourceSendFax" list="#{'true':'资源审核通过后','false':'订单支付后'}"></s:radio>
				</td>
			</tr>
			<tr>
				<td><em>是否为不定期产品：</em></td>
				<td>
					<s:if test="metaProduct.metaProductId == null">
						<s:radio name="metaProduct.isAperiodic" list="#{'true':'是','false':'否'}"></s:radio>
						<font color="red">（不定期采购仅能使用总库存，且只能被不定期销售打包）</font>
					</s:if>
					<s:else>
						${metaProduct.zhIsAperiodic }
					</s:else>
				</td>
			</tr>
			<s:if test="metaProduct.productType=='TICKET'">
			<tr>
				<td><em>电子通关码有效天数：</em></td>
				<td><s:textfield cssClass="text1" name="metaProduct.validDays"/></td>
			</tr>
			<tr>
				<td><em>电子通过终端显示内容：</em></td>
				<td><s:textarea name="metaProduct.terminalContent" rows="5" cols="50"/></td>
			</tr>
			<tr>
				<td> <em>是否支持手机客户端当天预订：</em></td>
				<td>
				 <input type="checkbox" name="metaProduct.todayOrderAble" value="true" <s:if test='metaProduct.hasTodayOrderAble()'>checked="checked"</s:if> />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					符合下述条件，才能打钩<br/>
				——需要与景区签订了，手机客户端当天预订协议<br/>
				——打勾前请先确定，与景区交互方式，是否是"e景通"或不需要进行通关验证的商务合作模式的景点
				</td>
			</tr>
			<tr>
				<td><em>最短换票间隔小时数<span class="require">[*]</span>：</em></td>
				<td><s:textfield  cssClass="text1" name="metaProduct.lastTicketTimeHour" />小时</td>
			</tr>
			<tr>
				<td></td>
				<td>
				——预订成功到可被换票的最小时间间隔（<font color="red">0=&lt;X&lt;=3，小数位一位，正数</font>）————为之前的手机当天预订
				</td>
			</tr>
			<tr>
				<td><em>最晚换票前多少小时数可售<span class="require">[*]</span>：</em></td>
				<td><s:textfield  cssClass="text1" name="metaProduct.lastPassTimeHour" />小时</td>
			</tr>
			<tr>
				<td></td>
				<td>
				——最晚换票前多少小时数可售，如1小时（<font color="red">0=&lt;X&lt;=24，小数位一位，正数</font>）<br/>
				——不用考虑支付等待时间、最短换票间隔小时数。自动会自动加上去
				</td>
			</tr>
			</s:if>
			<s:if test="metaProduct.productType=='TRAFFIC'">
				<tr>
				<td><em>单程/往返：</em></td>
				<td><s:radio list="directionTypeList" name="metaProduct.direction" listKey="code" listValue="name"/></td>
			</tr>
			<tr>
				<td><em>航班信息：</em></td>
				<td><span><s:hidden name="metaProduct.goFlight" id="goFlight"/>单程：<input type="text" value="${metaProduct.goFlightName }" id="goFlightInput"></span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="backFlightSpan"><s:hidden name="metaProduct.backFlight" id="backFlight"/>往返：<input type="text" value="${metaProduct.backFlightName }" id="backFlightInput"></span>
				</td>
			</tr>
			<tr id="flightDayTr">
				<td><em>行程天数：</em></td>
				<td><s:textfield cssClass="text1" name="metaProduct.days" /></td>
			</tr>
			</s:if>
		</table>            
        </div><!--rowpro end-->  