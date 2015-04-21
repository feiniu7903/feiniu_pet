<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <h3 class="titBotton">其他属性</h3>
 <table  border="0" cellspacing="0" cellpadding="0" class="newTableB">   
 	<tr>
 		<td style="width:145px;"><em>是否可以使用优惠券：</em></td>
 		<td><s:radio name="product.couponAble" list="BooleanList" listKey="code" listValue="name" labelSeparator="&nbsp;"/><a href="javascript:void(0)" class="showCouponInfo" withCode="true" productId="<s:property value='product.productId' />">查看已参与的优惠券 </a></td>
 	</tr>
 	<tr>
 		<td style="width:145px;"><em>是否可以参加优惠活动：</em></td>
 		<td><s:radio name="product.couponActivity" list="BooleanList" listKey="code" listValue="name" labelSeparator="&nbsp;"/><a href="javascript:void(0)" class="showCouponInfo" withCode="false" productId="<s:property value='product.productId' />">查看已参与的优惠活动 </a></td>	
 	</tr>    
    <s:if test="productType=='TICKET'"> 
 	<tr>
 		<td>&nbsp;</td>
 		<td><s:checkbox name="product.physical" cssClass="checkbox" fieldValue="true"/>实体票</td>
 	</tr>  
 	</s:if>
 	<tr>
 		<td><em>销售渠道：</em></td>
 		<td id="channelListTd">
 		<s:iterator value="channelList" id="cl">
 		<input type="checkbox" name="channel" value="<s:property value="code"/>" cssClass="checkbox" <s:if test='#cl.isChecked()'>checked</s:if>/><s:property value="name"/>
 		</s:iterator> 			
 		<br/>团购最小成团人数：<s:textfield name="product.groupMin" disabled="false"/></td>	
 	</tr>     	
</table> 
<script type="text/javascript">
$(function() {
	
	$('#channelListTd').find('input').click(function(){
		if ($(this).val() == 'TUANGOU') {
			$('#channelListTd').find('input[value=LVTU_TEAM_BUYING]').attr('checked', $(this).attr('checked'));
		}
	});
	
	$(".showCouponInfo").click(function() {
		var productId = $(this).attr('productId');
		var withCode = $(this).attr('withCode');
		
		if (typeof($CouponInfoDiv) == 'undefined') {
			$CouponInfoDiv = $('<div></div>');
			$CouponInfoDiv.appendTo($('body'));
		}
		$CouponInfoDiv.load('/super_back/phoneOrder/showCouponInfo.do', {
			productId : productId,
			withCode : withCode
		}, function() {
			$CouponInfoDiv.dialog( {
				title : "可参与优惠",
				width : 500,
				height: 400,
				modal : true
			})
		});
	});
});
</script>