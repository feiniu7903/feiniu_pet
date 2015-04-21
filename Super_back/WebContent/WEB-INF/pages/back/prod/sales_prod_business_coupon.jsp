<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增/编辑优惠(活动)</title>
		<link href="<%=basePath%>style/prod/ui-common.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>style/prod/ui-components.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>style/prod/panel-content.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
 		<div>
 			<form id='saveBusinessCouponForm' method='post' action='<%=basePath%>prod/saveProdBusinessCoupon.do'>
 				<s:hidden name="isAperiodic" id="isAperiodic" />
		 	    <input id="businessCoupon_businessCouponId" type="hidden" name="businessCoupon.businessCouponId" value="${businessCoupon.businessCouponId}" />
		 	    <input id="businessCoupon_valid" type="hidden" name="businessCoupon.valid" value="${businessCoupon.valid}" />
		 	    <input id="beforBeginDate" type="hidden" name="beforBeginDate" value='<s:date name="businessCoupon.beginTime" format="yyyy-MM-dd"/>' />
		 	    <input id="beforEndDate" type="hidden" name="beforEndDate" value='<s:date name="businessCoupon.endTime" format="yyyy-MM-dd"/>' />
		 	    <s:if test="businessCouponId == null">
		 	    	<input id="businessCoupon_productId" type="hidden" name="businessCoupon.productId" value="${productId}" />
		 	    </s:if>
		 	    <s:else>
		 	    	<input id="businessCoupon_productId" type="hidden" name="businessCoupon.productId" value="${businessCoupon.productId}" />
		 	    	<input id="businessCoupon_branchId" type="hidden" name="businessCoupon.branchId" value="${businessCoupon.branchId}" />
		 	    </s:else>
		 	    <input id="businessCoupon_metaType" type="hidden" name="businessCoupon.metaType" value="${businessCoupon.metaType}"  />
		 	    <input id="businessCoupon_favorMode" type="hidden" name="businessCoupon.favorMode" value="AMOUNT" />
		 	    
		 		<table class="p_table form-inline">  
			 		 
			 		 <tr>
			 			<td width="140px"  class="p_label"><span style="color:red">*</span>优惠名称：</td ><td >
			 				<input id="businessCoupon_couponName" name="businessCoupon.couponName"  type="text"  value="${businessCoupon.couponName}"  />
			 			</td>
			 		</tr>
		 		 	<s:if test="businessCouponId == null">
		 		 		<!-- 新增动作 -->
			 			<tr>
			 				<td width="140px"  class="p_label"><span style="color:red">*</span>优惠类型：</td ><td >
			 				<s:if test='isSaleChannel=="Y"'>
			 					<s:radio id="businessCoupon_couponType" name="businessCoupon.couponType"  value="'SALE'" list="%{#{'SALE':'特卖会'}}" onclick="couponTypeOnChange(this,this.value)"/>
			 				</s:if>
			 				<s:else>
			 					<s:radio id="businessCoupon_couponType" name="businessCoupon.couponType"  list="%{#{'EARLY':'早订早惠','MORE':'多订多惠'}}" onclick="couponTypeOnChange(this,this.value)" />
			 				</s:else>	
			 				</td>
			 			</tr>
		 			</s:if>
		 			<s:else> 
		 				<tr>
			 				<td width="140px"  class="p_label"><span style="color:red">*</span>优惠类型：</td ><td >${businessCoupon.couponTypeZh}</td>
			 			</tr>
		 				<!-- 编辑动作 -->
		 				<div style="display:none;">
			 			        <s:radio id="businessCoupon_couponType" name="businessCoupon.couponType"  list="%{#{'EARLY':'早订早惠','MORE':'多订多惠','SALE':'特卖会'}}" onclick="couponTypeOnChange(this,this.value)" />
			 			</div>
		 			</s:else>
		 			 <tr>
			 		 	<td class="p_label"> </td><td id="tipTxt"> </td>  
			 		 </tr>
			 		 <!-- 特卖类型 -->
			 		 <tr id="SALE_TYPE"> 
		 				<td width="140px"  class="p_label"><span style="color:red">*</span>特卖类型：</td >
			 			<td>
			 				<s:radio id="businessCoupon_saleType" name="businessCoupon.saleType" list="%{#{'NORMAL_TUANGOU':'普通团购','SECKILL':'秒杀','TRAVEL_AROUND':'周游客','VIP_DAY':'会员日'}}" />
			 			</td>		
		 			</tr>
			 		 <s:if test="businessCouponId == null">
			 		 	<!-- 新增动作 -->
		 				<tr id="branch_tr">
		 				<td class="p_label"><span style="color:red">*</span>产品可选类别： </td>
		 				<td id="MulBranch">
		 					<s:iterator value="allBranchCodeItemList" var="codeItem" status="c">				
								<input id="branchId_<s:property value="#c.index"/>" name="branchIdCheckbox" type="checkbox" value="${codeItem.code}" <s:if test="checked"> checked="checked" </s:if> />
								${name}&nbsp;&nbsp;
							</s:iterator>
						</td>
						<!-- 秒杀类别单选 -->
						<td id="singleBranch">
		 					<s:iterator value="allBranchCodeItemList" var="codeItem" status="c">				
								<input id="branchId_<s:property value="#c.index"/>" name="branchIdRadio" type="radio" value="${codeItem.code}" <s:if test="checked"> checked="checked" </s:if> />
								${name}&nbsp;&nbsp;
							</s:iterator>
							（说明：秒杀类别单选,同时只支持一个类别, 类别不能再次修改）
						</td>
		 			</tr>
			 		 </s:if>
		 			<div style="display:none;">
			 				<s:radio  id="businessCoupon_couponTarget" name="businessCoupon.couponTarget" list="%{#{'BRANCH':'产品单一类别'}}" /> 
			 		</div>
		 			
		 			<tr id="termOfValidity">
		 				<td class="p_label"> </td>
		 				<td>
				 			 下单有效期：<input name="businessCoupon.beginTime" id="businessCoupon_beginTime" type="text" class="date"  value="<s:date name="businessCoupon.beginTime" format="yyyy-MM-dd"/>"  />
				 			~<input name="businessCoupon.endTime" id="businessCoupon_endTime"  type="text" class="date" value="<s:date name="businessCoupon.endTime" format="yyyy-MM-dd"/>"  />
		  				 </td>
		 			</tr>
		 			<tr id="termOfPlayTime">
		 				<td class="p_label"> </td>
		 				<td>
				 			 游玩日期：
				 			 <s:if test='isAperiodic == "true"'>
				 			 <input name="businessCoupon.playBeginTime" id="playBeginTime" type="text" class="date" disabled="disabled" />
				 			~<input name="businessCoupon.playEndTime" id="playEndTime"  type="text" class="date" disabled="disabled" />
				 			 </s:if>
				 			 <s:else>
				 			 <input name="businessCoupon.playBeginTime" id="playBeginTime" type="text" class="date"  value="<s:date name="businessCoupon.playBeginTime" format="yyyy-MM-dd"/>"  />
				 			~<input name="businessCoupon.playEndTime" id="playEndTime"  type="text" class="date" value="<s:date name="businessCoupon.playEndTime" format="yyyy-MM-dd"/>"  />
				 			</s:else>
				 			<label id="playTimeTxt">  </label>
		  				 </td>
		 			</tr>
		 			<tr id="aheadNum">
		 				<td class="p_label"> </td>
		 				<td> 提前预订天数 <s:textfield  id="businessCoupon_aheadNum" name="businessCoupon_aheadNum" value="%{businessCoupon.argumentX}"  maxlength="10" />天</td>
		 			</tr>
		 			 
			 		 <tr id="EARLY_MORE_NEED">
			 		 	<td class="p_label"> </td><td>
			 		 	<s:if test='isAperiodic == "true"'>不定期产品统一按订购份数来计算</s:if>
			 		 	<s:else>
			 		 	（说明：酒店按间夜来计算，线路/门票产品按订购份数来计算） </s:else></td>
			 		 </tr>
		 			<!-- 按金额早买 -->
		 			<tr id="AMOUNT_EARLY_TR">
		 				<td class="p_label"><span class="red">*</span>优惠细则：</td>
			 			<td>
			 			    <span id="AMOUNT_EARLYDAY_QUANTITY_PRE_SPAN">
			 			    	<input type="hidden" id="AMOUNT_EARLYDAY_QUANTITY_PRE_favorType" name="businessCoupon.favorType"  value="AMOUNT_EARLYDAY_QUANTITY_PRE" />
			 			    	<input type="hidden" id="AMOUNT_EARLYDAY_QUANTITY_PRE_TD_X" name="businessCoupon.argumentX" />每份(间夜)，销售价优惠金额
			 			    	<input type="text" id="AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y"  name="argumentYYuan" 
			 			    		<s:if test="amountMode == 'amountYuan'"> value="${businessCoupon.argumentYYuan}" </s:if>
			 			    		<s:else> value="${businessCoupon.argumentY}"</s:else> maxlength="11"/>元(请填写正数)。
			 			    </span> 
			 			 </td>
		 			</tr>
		 			<!-- 按金额多买 -->
		 			<tr id="AMOUNT_MORE_TR"> 
		 				<td class="p_label"><span class="red">*</span>优惠细则：</td>
			 			<td>
			 			  <span id="AMOUNT_MORE_QUANTITY_INTERVAL_SPAN">
			 				 	 <input type="radio" id="AMOUNT_MORE_QUANTITY_INTERVAL_RA" name="businessCoupon.favorType" value="AMOUNT_MORE_QUANTITY_INTERVAL"  <s:if test='businessCoupon.favorType=="AMOUNT_MORE_QUANTITY_INTERVAL"'>checked</s:if> 
			 				 			onclick="FavorTypeOnChange(this,'AMOUNT_MORE_QUANTITY_PRE_SPAN','AMOUNT_MORE_QUANTITY_WHOLE_SPAN')" /> 每满
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_INTERVAL_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份，销售总价优惠
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_INTERVAL_TD_Y" name="argumentYYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentYYuan}"</s:if><s:else>value="${businessCoupon.argumentY}"</s:else> maxlength="11"/>元(请填写正数)。
				 				 
			 			   </span>
			 			   <br/>
			 			  <span id="AMOUNT_MORE_QUANTITY_PRE_SPAN">
				 				 <input type="radio" id="AMOUNT_MORE_QUANTITY_PRE_RA" name="businessCoupon.favorType" value="AMOUNT_MORE_QUANTITY_PRE"  <s:if test='businessCoupon.favorType=="AMOUNT_MORE_QUANTITY_PRE"'>checked</s:if> 
				 					 	onclick="FavorTypeOnChange(this,'AMOUNT_MORE_QUANTITY_INTERVAL_SPAN','AMOUNT_MORE_QUANTITY_WHOLE_SPAN')" /> 满
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_PRE_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份起，销售单价优惠
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_PRE_TD_Y" name="argumentYYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentYYuan}"</s:if><s:else>value="${businessCoupon.argumentY}"</s:else> maxlength="11"/>元(请填写正数)。
				 			</span>
				 			<br/>
				 			<span id="AMOUNT_MORE_QUANTITY_WHOLE_SPAN">
				 				 <input type="radio" id="AMOUNT_MORE_QUANTITY_WHOLE_RA" name="businessCoupon.favorType" value="AMOUNT_MORE_QUANTITY_WHOLE"  <s:if test='businessCoupon.favorType=="AMOUNT_MORE_QUANTITY_WHOLE"'>checked</s:if> 
				 					 	onclick="FavorTypeOnChange(this,'AMOUNT_MORE_QUANTITY_INTERVAL_SPAN','AMOUNT_MORE_QUANTITY_PRE_SPAN')" /> 满
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份后，每增加
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_Y" name="businessCoupon.argumentY"  value="${businessCoupon.argumentY}" maxlength="11"/>份，销售总价优惠
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_Z" name="argumentZYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentZYuan}"</s:if><s:else>value="${businessCoupon.argumentZ}"</s:else> maxlength="11"/>元(请填写正数)。
				 		 	</span>
			 			 </td>		
		 			</tr>
		 			<!-- 特卖会 购买限制 IP限制禁掉 -->
		 			<tr id="SALE_BUY_LIMIT">
		 				<td class="p_label"><span style="color:red">*</span>数量控制：</td>
		 				<td>
		 					<s:if test="businessCouponId == null">
			 					<span>虚拟销量：
			 						<input  type="text" id="USER_BUY_LIMIT" name="prodSeckillRule.userBuyLimit"  value="0" maxlength="11"/>
			 						</span>
			 					<span>允许放入人数倍数：
			 						<input  type="text" id="IP_BUY_LIMIT" name="prodSeckillRule.ipBuyLimit"  value="1" maxlength="11"/>
			 					</span>(整数倍)
			 				</s:if>
			 		 		<s:else>
			 		 			<span>虚拟销量：
			 						<input  type="text" id="USER_BUY_LIMIT" name="prodSeckillRule.userBuyLimit"  value="${businessCoupon.seckillRuleVOs[0].userBuyLimit}" maxlength="11"/>
			 						</span>
			 					<span>允许放入人数倍数：
			 						<input  type="text" id="IP_BUY_LIMIT" name="prodSeckillRule.ipBuyLimit"  value="${businessCoupon.seckillRuleVOs[0].ipBuyLimit}" maxlength="11"/>
			 					</span>(整数倍)
		 					</s:else>
		 				</td>	
		 			</tr>
		 			<!-- 特卖会 支付时间 -->
		 			<tr id="SALE_PAY_TIME">
		 				<td class="p_label"><span style="color:red">*</span>支付时间：</td>
		 				<td>
		 					<span>支付有效期：
		 					<s:if test="businessCouponId == null">
		 						<input  type="text" id="PAY_VALID_TIME" name="prodSeckillRule.payValidTime"  value="30" maxlength="11"/>分钟
		 					</s:if>
		 					<s:else>
		 						<input  type="text" id="PAY_VALID_TIME" name="prodSeckillRule.payValidTime"  value="${businessCoupon.seckillRuleVOs[0].payValidTime }" maxlength="11"/>分钟
		 					</s:else>
		 					</span>
		 				</td>	
		 			</tr>
		 			<!-- 特卖会 优惠时段-->
		 			<tr id="SALE_TR"> 
		 				<td class="p_label"><span style="color:red">*</span>优惠时段：</td>
			 			<td >
			 				<table id="SALE_TIME_RULE">
			 				<s:if test="businessCouponId == null">
			 				<span style="padding-left:80px"><a onclick="addSaleTr();" href="javascript:void(0)">添加</a></span>
			 				<span style="padding-left:20px"><a onclick="delSaleTr()" href="javascript:void(0)">删除</a></span>&nbsp;&nbsp;<span style="color:red">(说明：输入数量不能大于产品库存数量,否则会导致超卖)</span><br/>
			 					<tr><td>
			 					<input type="checkbox" id="SALE_RULE"/>规则1：时段：<input name="prodSeckillRule.startTime" id="prodSeckillRule_startTime_1" type="text" value="<s:date name="prodSeckillRule.startTime" format="yyyy-MM-dd HH:mm:ss"/>"  class="seckillStartDate"/>~<input name="prodSeckillRule.endTime" id="prodSeckillRule_endTime_1"  type="text" value="<s:date name="prodSeckillRule.endTime" format="yyyy-MM-dd HH:mm:ss"/>"  class="seckillEndDate"/>优惠：<input  type="text" id="PRDUCE_PRICE_1" name="prodSeckillRule.reducePrice"  value="${prodSeckillRule.reducePrice }" style="width:50px;"/>元（请填写正数）&nbsp;数量：<input type="text" id="AMOUNT_1" name="prodSeckillRule.amount" value="${prodSeckillRule.amount }" style="width:30px;"/>
			 					</td></tr>
			 				</s:if>
			 				<s:else>
			 					<span style="padding-left:80px"><a onclick="updateSaleTr();" href="javascript:void(0)">添加</a></span>
			 					<span style="padding-left:20px"><a onclick="delSaleTr()" href="javascript:void(0)">删除</a></span>&nbsp;&nbsp;<span style="color:red">(说明：输入数量不能大于产品库存数量,否则会导致超卖)</span><br/>
			 					<s:iterator value="businessCoupon.seckillRuleVOs" id="seckillRuleVO" status="num">
			 					<input type="hidden" class="seckillRuleIds" name="seckillRuleIds[${num.index}]" value="${seckillRuleVO.id}"/>
			 					<tr><td>
			 						<input type="hidden" name="prodSeckillRule.id" class="SALE_RULE_ID" value="${seckillRuleVO.id}"/>
			 						<input type="checkbox" id="SALE_RULE"/>规则${num.index+1}：时段：<input name="prodSeckillRule.startTime"  id="prodSeckillRule_startTime_${num.index+1}" type="text" value="<s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>"  class="seckillStartDate"/>~<input name="prodSeckillRule.endTime" id="prodSeckillRule_endTime_${num.index+1}"  type="text" value="<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>"  class="seckillEndDate"/>优惠：<input  type="text" id="PRDUCE_PRICE_${num.index+1}" name="prodSeckillRule.reducePrice"  value="${seckillRuleVO.reducePriceY }" style="width:50px;"/>元（请填写正数）&nbsp;数量：<input type="text" id="AMOUNT_${num.index+1}" name="prodSeckillRule.amount" value="${seckillRuleVO.amount }" style="width:30px;"/>
			 					</td></tr>	
			 					</s:iterator>
			 				</s:else>
			 				</table>
			 			</td>		
		 			</tr>
		 			<!-- 优惠时段规则 -->
		 			
		 			
		 			<!-- 特卖会优惠细则添加 -->
		 			<tr id="SALE_TR_ADD_OR_DEL">
		 				<td></td>
		 				<td id="tr"></td>
		 			</tr>
		 		</table>
		 		<p class="tc mt10"><input type="button" id="btnSubmit" class="btn btn-small w3" value="提交" /></p> 			 			 			
 		    </form>
 	    </div>
 	    
		<script type="text/javascript">
		    var checkedSelect;
		    var businessCouponId = $("#businessCoupon_businessCouponId").val();
		    var indexTr=2;//规则数
		    
		    //优惠类型:早点早惠切换
		    function couponTypeOnChange(arg,value) {
		    	if(value == "EARLY"){
		    		$("#termOfPlayTime").show();
		    		$("#termOfValidity").show();
		    		$("#MulBranch").show();
		    		$("#singleBranch").hide();
		    		$("#SALE_TR").hide();
		    		$("#SALE_TYPE").hide();
		    		$("#SALE_BUY_LIMIT").hide();
		    		$("#SALE_PAY_TIME").hide();
		    		$("#AMOUNT_MORE_TR").hide();
					$("#aheadNum").show();
					$("#AMOUNT_EARLY_TR").show();
				    $("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").removeAttr("disabled");
				    $("#tipTxt").html("<font>（说明：用户提前X天预订某一游玩日期的产品，可享受优惠）</font>");
				    $("#playTimeTxt").html("");
		    	}else if(value == "MORE"){
		    		$("#termOfPlayTime").show();
		    		$("#termOfValidity").show();
		    		$("#MulBranch").show();
		    		$("#singleBranch").hide();
		    		$("#SALE_TR").hide();
		    		$("#SALE_TYPE").hide();
		    		$("#SALE_BUY_LIMIT").hide();
		    		$("#SALE_PAY_TIME").hide();
		    		$("#aheadNum").hide();
		    		$("#AMOUNT_EARLY_TR").hide();
		    		$("#AMOUNT_MORE_TR").show();
		    		$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").attr("disabled","disabled");
		    		//$("#AMOUNT_MORE_TR input[type='text']").val("");
		    		$("#AMOUNT_MORE_TR input[type='radio']").attr("checked",false);
		    		$("#tipTxt").html("<font>（说明：用户预订的订单，订购的份数（间夜）达到一定数量，可享受优惠）</font>");
		    		$("#playTimeTxt").html("<font>（可选填）</font>");
		    	}else if(value == "SALE"){//特卖会
		    		$("#termOfPlayTime").hide();
		    		$("#termOfValidity").hide();
		    		$("#aheadNum").hide();
		    		$("#MulBranch").hide();
		    		$("#singleBranch").show();
		    		$("#aheadNum").hide();
		    		$("#SALE_TYPE").show();
		    		$("#SALE_BUY_LIMIT").show();
		    		$("#SALE_PAY_TIME").show();
		    		$("#EARLY_MORE_NEED").hide();
		    		$("#AMOUNT_MORE_TR").hide();
		    		$("#AMOUNT_EARLY_TR").hide();
		    		$("#SALE_TR").show();
		    		$("#tipTxt").html("<font>（说明：在限制的时间内可以以优惠价格购买该产品，时间结束恢复原价）</font>");		    		
		    	}
		    	 	$("#AMOUNT_MORE_QUANTITY_INTERVAL_TD_X").attr("disabled","disabled");
		    	 	$("#AMOUNT_MORE_QUANTITY_INTERVAL_TD_Y").attr("disabled","disabled");
		    	  	$("#AMOUNT_MORE_QUANTITY_PRE_TD_X").attr("disabled","disabled");
		    	  	$("#AMOUNT_MORE_QUANTITY_PRE_TD_Y").attr("disabled","disabled");
		    	  	
		    	  	$("#AMOUNT_MORE_QUANTITY_WHOLE_TD_X").attr("disabled","disabled");
		    	  	$("#AMOUNT_MORE_QUANTITY_WHOLE_TD_Y").attr("disabled","disabled");
		    		$("#AMOUNT_MORE_QUANTITY_WHOLE_TD_Z").attr("disabled","disabled");
			} 
		    
			//多买多惠<优惠细则切换>
			function FavorTypeOnChange(obj,hideSpanId_1,hideSpanId_2) {
				checkedSelect=$(obj).val();
				$(obj).attr("checked",true);
				$("#" + checkedSelect + "_SPAN input[type='text']").removeAttr("disabled");
				$("#" + hideSpanId_1 + " input[type='text']").attr("disabled","disabled");
				$("#" + hideSpanId_1 + " input[type='text']").val("");
				
				$("#" + hideSpanId_2 + " input[type='text']").attr("disabled","disabled");
				$("#" + hideSpanId_2 + " input[type='text']").val("");
			}
			
			//特卖会规则增加
			function addSaleTr(){
				
				$("#SALE_TIME_RULE").append("<tr><td><input type='checkbox' id='SALE_RULE'/>规则"+indexTr+"："
	 				+"时段：<input name='prodSeckillRule.startTime' id='prodSeckillRule_startTime_"+indexTr+"' type='text' value='<s:date name='prodSeckillRule.startTime' format='yyyy-MM-dd HH:mm:ss'/>' class='seckillStartDate' />~<input name='prodSeckillRule.endTime' id='prodSeckillRule_endTime_"+indexTr+"'  type='text' value='<s:date name='prodSeckillRule.endTime' format='yyyy-MM-dd HH:mm:ss'/>'  class='seckillEndDate'/>"
		 			+"优惠：<input  type='text' id='PRDUCE_PRICE_"+indexTr+"' name='prodSeckillRule.reducePrice'  value='${prodSeckillRule.reducePrice}' style='width:50px;'/>元（请填写正数）&nbsp;"
		 			+"数量：<input type='text' id='AMOUNT_"+indexTr+"' name='prodSeckillRule.amount' value='${prodSeckillRule.amount}' style='width:30px;'/></td></tr>");
				indexTr++;
				//加载时间插件
				 $('input.seckillStartDate').datetimepicker({
	            	  timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
	             });
		    	 $('input.seckillEndDate').datetimepicker({
	            	 timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
				});
			}
			//特卖会修改增加
			function updateSaleTr(){
				//获取当前的规则数
				var num = $("input[name='prodSeckillRule.startTime']").size()+1;
				$("#SALE_TIME_RULE").append("<tr><td><input type='checkbox' id='SALE_RULE'/>规则"+num+"："
	 				+"时段：<input name='prodSeckillRule.startTime' id='prodSeckillRule_startTime_"+num+"' type='text' value='<s:date name='prodSeckillRule.startTime' format='yyyy-MM-dd HH:mm:ss'/>' class='seckillStartDate' />~<input name='prodSeckillRule.endTime' id='prodSeckillRule_endTime_"+num+"'  type='text' value='<s:date name='prodSeckillRule.endTime' format='yyyy-MM-dd HH:mm:ss'/>' class='seckillEndDate' />"
		 			+"优惠：<input  type='text' id='PRDUCE_PRICE_"+num+"' name='prodSeckillRule.reducePrice'  value='${prodSeckillRule.reducePrice}' style='width:50px;'/>元（请填写正数）&nbsp;"
		 			+"数量：<input type='text' id='AMOUNT_"+num+"' name='prodSeckillRule.amount' value='${prodSeckillRule.amount}' style='width:30px;'/></td></tr>");
				//加载时间插件
				$('input.seckillStartDate').datetimepicker({
	            	  timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
	             });
		    	 $('input.seckillEndDate').datetimepicker({
	            	 timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
				});
			}
			//特卖会规则删除
			function delSaleTr(){
				$("input[id='SALE_RULE']:checkbox").each(function(){
					if($(this).attr("checked")){
						$(this).parent().parent().remove();
					}
				});
			}
			
		    $(function(){
		    	//初始化时分秒插件
		    	$('input.seckillStartDate').datetimepicker({
	            	  timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
	             });
		    	 $('input.seckillEndDate').datetimepicker({
	            	 timeFormat: "hh:mm:ss",
	                  dateFormat: "yy-mm-dd"
				});
		    	//初始化
		    	$("input.date").datepicker({dateFormat: 'yy-mm-dd'});
				$("#btnSubmit").click(function(){
				
					var couponType = $("input[name='businessCoupon.couponType']:checked").val();
					var favorMode = $("#businessCoupon_favorMode").val();
					var idValue = ",#businessCoupon_businessCouponId,#businessCoupon_valid,#beforBeginDate,#beforEndDate,#businessCoupon_productId,#businessCoupon_branchId,#businessCoupon_couponType,#businessCoupon_couponTarget,#businessCoupon_metaType";
					var reg = /^[0-9]*[1-9][0-9]*$/;
					var yuanreg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
					
					if(couponType == null){
						alert("请选择优惠类型");
					    $("#businessCoupon_couponType").focus();
					    return false;
					}
				 
				 	if($("#businessCoupon_couponName").val()==""){
				    	alert("请输入优惠名称");
				     	return false;
				 	}
				 	
				 	if(businessCouponId == ""){
				 		//特卖会单选类别
				 		if(couponType== "SALE"){
				 			if($("input[name='branchIdRadio']:checked").val()==null){
				 				alert("请选择产品类别");
				 				$("#branchId_0").focus();
								return false;
				 			}
				 		}else{
					 		if(($("input:checkbox[name='branchIdCheckbox'][checked='true']")).length<1){
								alert("请选择产品类别");
								$("#branchId_0").focus();
								return false;
							} 
				 		}
				 	}
					if(couponType == "EARLY" || couponType == "MORE"){
						if($("#businessCoupon_beginTime").val()==""||$("#businessCoupon_endTime").val()==""){
					    	alert("请输入下单有效期");
					     	return false;
					 	}
					}
					
					
					if($("#playBeginTime").val()!=""){
						if($("#playEndTime").val()==""){
							alert("请填写游玩结束日期");
							return false;
						}
					}
					if($("#playEndTime").val()!=""){
						if($("#playBeginTime").val()==""){
							alert("请填写游玩起始日期");
							return false;
						}
					}
					
					//游玩时间校验比较
					if(($("#playBeginTime").val()!="" && $("#playEndTime").val()!="")){
						var startTime = $("#playBeginTime").val();
						var endTime = $("#playEndTime").val();
						if(compareDate(startTime, endTime, "游玩时间") == false){
							return;
						}
					}
					
					if(couponType == "EARLY"){
						if(($("#playBeginTime").val()==""||$("#playEndTime").val()=="")&&($("#businessCoupon_aheadNum").val()=="")){
							alert("完整游玩日期和提前天数必填一项或二项");
							return false;
						}
					}
					
					//下单有效期校验比较
					if(($("#businessCoupon_beginTime").val()!="" && $("#businessCoupon_endTime").val()!="")){
						var startTime = $("#businessCoupon_beginTime").val();
						var endTime = $("#businessCoupon_endTime").val();
						if(compareDate(startTime, endTime,"下单有效期") == false){
							return;
						}
					}
					if(couponType == "EARLY" && $("#businessCoupon_aheadNum").val()!="" && !reg.test($("#businessCoupon_aheadNum").val())){
						alert("预订天数请正确填写整数");
						$("#businessCoupon_aheadNum").focus();
						return false; 
					}
					
					//早买状态下获取X,Y值
					if(couponType == "EARLY" && favorMode == "AMOUNT"){
						if($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").val()==""){
							alert("请填写正数的优惠细则");
							$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").focus();
							return false;
						}else if(($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").val()!=""&&!yuanreg.test($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").val()))){
							alert("请填写正数的优惠细则");
							$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Y").focus();
							return false;
						}
						//早买设置argumentX值
						$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_X").val($("#businessCoupon_aheadNum").val());
						
						idValue = idValue + ",#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_X,#AMOUNT_EARLYDAY_QUANTITY_PRE_favorType";
					}
					
					 //关注多订多惠下的每个优惠细则前的radio选择项
					 if(couponType == "MORE" &&(checkedSelect == null || $("#"+checkedSelect+"_RA").attr("checked")==false)){
						  alert("必须选一优惠细则");
						  return false;
					 } else if(couponType == "MORE" && $("#"+checkedSelect+"_RA").attr("checked") == true){
						  var ary = checkedSelect.split("_");
						  var amountMoreCheckValue  = $("#AMOUNT_MORE_QUANTITY_WHOLE_RA").attr("checked");

						  if(favorMode != ary[0]){
							  alert("当前优惠方式下的优惠细则没选激活");
							  return false;
						 }else if($("#"+checkedSelect+"_TD_Y").attr("disabled") == true){
							  alert("选中的优惠细则没激活");
							  return false;
						 }else if(document.getElementById(checkedSelect+"_TD_X")&&$("#"+checkedSelect+"_TD_X").val()==""){
							 $("#"+checkedSelect+"_TD_X").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Y")&&$("#"+checkedSelect+"_TD_Y").val()==""){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(amountMoreCheckValue!=""&&document.getElementById(checkedSelect+"_TD_Z")&&$("#"+checkedSelect+"_TD_Z").val()==""){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if($("#"+checkedSelect+"_TD_X").val()!=""&&!reg.test($("#"+checkedSelect+"_TD_X").val())){
							 $("#"+checkedSelect+"_TD_X").focus();
							 alert("请填写优惠金额为正整数!");
							 return false;
						 }else if(amountMoreCheckValue==""&&$("#"+checkedSelect+"_TD_Y").val()!=""&&!yuanreg.test($("#"+checkedSelect+"_TD_Y").val())){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠金额为正值!");//金额
							 return false;
						 }else if(amountMoreCheckValue!=""&&$("#"+checkedSelect+"_TD_Y").val()!=""&&!reg.test($("#"+checkedSelect+"_TD_Y").val())){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠金额为正整数!");
							 return false;
						 }else if(amountMoreCheckValue!=""&&$("#"+checkedSelect+"_TD_Z").val()!=""&&!yuanreg.test($("#"+checkedSelect+"_TD_Z").val())){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠金额为正值!");
							 return false;
						 }
					}
					 
					//特卖会字段校验
					if(couponType == "SALE"){
						if($("input[name='businessCoupon.saleType']:checked").val()==null){
							alert("请选择特卖类型");
							$("#businessCoupon_saleType").focus();
							return false;
						}
						 if($("#USER_BUY_LIMIT").val()==""){
							alert("请填写虚拟销量!");
							$("#USER_BUY_LIMIT").focus();
							return false;
						}
						 var regLimitUser = /^([0-9]|([0-9]*[1-9][0-9]*))$/; 
						if($("#USER_BUY_LIMIT").val()!=""&&!regLimitUser.test($("#USER_BUY_LIMIT").val())){
							alert("请填写正确虚拟销量!");
							$("#USER_BUY_LIMIT").focus();
							return false;
						} 
						 if($("#IP_BUY_LIMIT").val()==""){
								alert("请填写允许放入人数倍数!");
								$("#USER_BUY_LIMIT").focus();
								return false;
							}
						 if($("#IP_BUY_LIMIT").val()!=""&&!reg.test($("#IP_BUY_LIMIT").val())){
								alert("请填写整数倍!");
								$("#IP_BUY_LIMIT").focus();
								return false;
							} 
						if($("#PAY_VALID_TIME").val()==""){
							alert("请填写支付有效期");
							$("#PAY_VALID_TIME").focus();
							return false;
						}
						if($("#PAY_VALID_TIME").val()!=""&&!reg.test($("#PAY_VALID_TIME").val())){
							alert("请填写正确有效期!");
							$("#PAY_VALID_TIME").focus();
							return false;
						}
						
						//校验优惠时段
						var $startTime = $("input[name='prodSeckillRule.startTime']");
						for(var i=0;i<$startTime.length;i++){
							var bb = $($startTime.get(i));
							if(bb.val()==""){
								alert("请填写优惠开始时段");
								return false;
							}
						}
						var $endTime = $("input[name='prodSeckillRule.endTime']");
						for(var i=0;i<$endTime.length;i++){
							var bb = $($endTime.get(i));
							if(bb.val()==""){
								alert("请填写优惠结束时段");
								return false;
							}
						}
						//优惠金额校验
						var $reducePrice = $("input[name='prodSeckillRule.reducePrice']");
						for(var i=0;i<$reducePrice.length;i++){
							var aa = $($reducePrice.get(i));
							if(aa.val()==""){
								alert("请填写优惠值");
								return false;
							}
							var reducePriceReg = /^([0-9]|([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
							if(aa.val()!=""&&!reducePriceReg.test(aa.val())){
								alert("请填写正确优惠金额");
								return false;
							}
						}
						//优惠时段数量校验
						var $amount = $("input[name='prodSeckillRule.amount']");
						for(var i=0;i<$amount.length;i++){
							var bb = $($amount.get(i));
							if(bb.val()==""){
								alert("请填写优惠时段数量");
								return false;
							}
							if(bb.val()!=""&&!reg.test(bb.val())){
								alert("请填写正确优惠时段的数量");
								return false;
							}
						}
						//比较开始时间结束时间
						for(var i=1;i<=indexTr;i++){
							if($("#prodSeckillRule_startTime_"+i).val()!=null&&$("#prodSeckillRule_endTime_"+i).val()!=null){
								if($("#prodSeckillRule_startTime_"+i).val()!=""&&$("#prodSeckillRule_endTime_"+i).val()!=""){
									var startTime = $("#prodSeckillRule_startTime_"+i).val();
									var endTime = $("#prodSeckillRule_endTime_"+i).val();
									if(saleCompareDate(startTime,endTime,"规则"+i)== false){
										return;
									}
								}
							}
							
						}
						
					}
					
					
					 var branchIdCodes = "";
					//获取所有选中的产品类别
					if(couponType == "SALE"){
						branchIdCodes = $("input[name='branchIdRadio']:checked").val();
					}else{
						
						$("input[name='branchIdCheckbox']:checked").each(function(){
							branchIdCodes += "," + $(this).val();
						});
						branchIdCodes = branchIdCodes.substring(1);
					}
					dispose();
					
					//表单提交  (只提交能编辑的input值)
					var arr = [];
					$("#saveBusinessCouponForm").find("input:radio:checked,textarea,input:text:not([disabled]),input[name='prodSeckillRule.id'],.SALE_RULE_ID,.seckillRuleIds,select" + idValue).each(function(){
					    arr.push(this.name+'='+this.value);
					});
					//传入couponType区别特卖会
					var data = arr.join("&")+"&branchIdCodes=" + branchIdCodes+"&couponTypeMark="+couponType;
					$.ajax({
						url: "<%=basePath%>prod/saveProdBusinessCoupon.do",
		        	 	data: data,
		        	 	beforeSend: function(XMLHttpRequest){
		                     XMLHttpRequest.setRequestHeader("RequestType", "ajax");
		                },
						type:"post",
		        	 	dataType:"json",
		        	 	success: function(result) {
		        	 		
		        	 		if (result.success) {
		        	 			window.location.href="<%=basePath%>prod/queryProdBusinessCouponList.do?productId=${productId}&metaType=${businessCoupon.metaType}&_=" + (new Date).getTime();
		        	 		} else {
		        	 			alert(result.errorMessage);
		        	 		}
		        	 	}
		        	 });			
				});
				
				//编辑页加载时依据值显示逻辑
				$("input:radio:checked").click();
				$("input:text:disabled").val("");
			
			});
		    
		    //比较日前大小
		    function compareDate(checkStartDate, checkEndDate, dateName) {   
		    	var arys1= new Array();   
		    	var arys2= new Array();   
			    if(checkStartDate != null && checkEndDate != null) {
			    	  arys1 = checkStartDate.split('-');
			          var sdate = new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);   
			    	  arys2 = checkEndDate.split('-');
			    	  var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);   
				    if(sdate > edate) {
				    	alert(dateName + "开始时间大于结束时间");      
				    	return false;
				    }
			   }   
		    }
		    //比较秒杀日期
		    function saleCompareDate(StartDate,EndDate,name){
		    	var start=new Date(StartDate.replace("-", "/").replace("-", "/"));  
		    	var end=new Date(EndDate.replace("-", "/").replace("-", "/"));
		    	if(start>end){
		    		alert(name+"开始时间大于结束时间");
		    		return false;
		    	}
		    }
		    $(function() {
		    	$(document).ready(function() {
		    		if(businessCouponId == ""){
			    		var isAperiodic = $("#isAperiodic").val();
			    		if(isAperiodic == "true") {
			    			var moreObject = $("input[name='businessCoupon.couponType'][value='MORE']");
			    			moreObject.attr("checked", true);
			    			$("input[name='businessCoupon.couponType'][value!='MORE']").attr("disabled", true);
			    			couponTypeOnChange(moreObject, 'MORE');
			    		}
		    		}
		    	});
		    });
		    //替换提交name
		    function dispose(){
		    	$("input[name='prodSeckillRule.startTime']").each(function(i){
		    		$(this).attr("name","seckillRuleVOs["+i+"].startTime");
		    	});
				$("input[name='prodSeckillRule.endTime']").each(function(i){
		    		$(this).attr("name","seckillRuleVOs["+i+"].endTime");
		    	});
				$("input[name='prodSeckillRule.reducePrice']").each(function(i){
		    		$(this).attr("name","seckillRuleVOs["+i+"].reducePrice");
		    	});
				$("input[name='prodSeckillRule.amount']").each(function(i){
		    		$(this).attr("name","seckillRuleVOs["+i+"].amount");
		    	});
				//如果是更新，提交对应的规则ID
				$("input[name='prodSeckillRule.id']").each(function(i){
		    		$(this).attr("name","seckillRuleVOs["+i+"].id");
		    	});
		    }
		</script> 	    
	</body>
</html>