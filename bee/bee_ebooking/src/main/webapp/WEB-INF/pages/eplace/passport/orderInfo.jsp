<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="snspt_tab4">
	  <tbody><tr>
	    <th>订单号</th>
	    <th>游玩时间</th>
	    <s:if test="#request.isAperiodic == 'true'">
	    	<th>有效期</th>
		</s:if>
	    <th>取票人</th>
	    <th>手机号</th>
	    <th>产品名称</th>
	    <th>订购票数</th>
	    <th>单价</th>
	    <th>总价</th>
	    <th>游玩状态</th>
	    <th>付款方式</th>
	  </tr>
	  <s:iterator value="#request.orderItemMetas" var="item">
	  	<tr>
		    <td>${item.orderId}</td>
		    <td>${item.strVisitTime }</td>
		    <s:if test="#request.isAperiodic == 'true'">
		    	<td>${item.validBeginTime }至${item.validEndTime }
		    	<s:if test='#item.invalidDateMemo != null && #item.invalidDateMemo != ""'>
		    		(${item.invalidDateMemo })
		    	</s:if>
		    	</td>
			</s:if>
		    <td>${item.contactName}</td>
		    <td>${item.contactMobile}</td>
		    <td>${item.metaProductName}<br>(${item.branchName })</td>
		    <td>${item.productQuantity * item.quantity}</td>
		    <td>
		    	<s:if test="#item.paymentTarget == \"TOSUPPLIER\"">
		    		${item.sellPrice / 100}元
		    	</s:if>
		    </td>
		    <td>
				<s:if test="#item.paymentTarget == \"TOSUPPLIER\"">
		    		${item.sellPrice * item.quantity / 100}元
		    	</s:if>
			</td>
		    <td class="f_red">
				<s:if test="#item.performStatus == \"UNPERFORMED\"">
		    		未游玩
		    	</s:if>
				<s:if test="#item.performStatus == \"PERFORMED\" || #item.performStatus == \"AUTOPERFORMED\"">
		    		已游玩
		    	</s:if>
		    </td>
		    <td class="f_green">
				<s:if test="#item.paymentTarget == \"TOLVMAMA\"">
		    		在线支付
		    	</s:if>
				<s:if test="#item.paymentTarget == \"TOSUPPLIER\"">
		    		景区支付
		    	</s:if>
			</td>
		 </tr>
	  </s:iterator>
	</tbody>
</table>

<em class="snspt_tit1">取票人留言</em>
	<textarea class="snspt_pop_txts" rows="" cols="" name="">${request.userMemo }</textarea>
<em class="snspt_tit1">游客信息</em>

<table class="snspt_tab3">
 <tbody><tr>
   <th>游客姓名</th>
   <th>联系电话</th>
   <th>证件类型</th>
   <th>证件号码</th>
   <th>地址</th>
 </tr>
 <s:iterator value="#request.ordPersons">
	<s:if test="personType=='TRAVELLER'">
	<tr>
		<td>${name}</td>
		<td>${mobile}</td>
		<td>
			<s:if test="certType=='HUZHAO'">护照</s:if>
			<s:elseif test="certType=='ID_CARD'">身份证</s:elseif>
			<s:elseif test="certType=='OTHER'">其他</s:elseif>
		</td>
		<td>${certNo}</td>
        <td>${address }</td>
	</tr>
	</s:if>
</s:iterator>
</tbody>
</table>
<em class="snspt_tit1">配送地址</em>

<table class="snspt_tab3">
 <tbody><tr>
   <th>收件人</th>
   <th>手机号码</th>
   <th>邮编</th>
   <th>地址</th>
 </tr>
 <s:iterator value="#request.ordPersons">
	<s:if test="personType=='ADDRESS'">
		<s:if test="address!=null">
			<tr>
				<td>${name}</td>
				<td>${mobile}</td>
				<td>${postcode}</td>
		        <td>${address }</td>
			</tr>
		</s:if>
	</s:if>
</s:iterator>
</tbody>
</table>
 
      <p class="lv_pop_btnbox">
           <a id="closeOrderBtn" href="javascript:void(0)" class="lv_pop_btn_rl snspt_ord_opt_s"
           		onclick="$('.lv_pop_close').click();">关闭</a>
      </p>