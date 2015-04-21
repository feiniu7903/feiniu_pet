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
			 			<td width="140px"  class="p_label">优惠名称：</td ><td >
			 				<input id="businessCoupon_couponName" name="businessCoupon.couponName"  type="text"  value="${businessCoupon.couponName}"  />
			 			</td>
			 		</tr>
		 		 	<s:if test="businessCouponId == null">
		 		 		<!-- 新增动作 -->
			 			<tr>
			 				<td width="140px"  class="p_label">优惠类型：</td ><td>
			 					<s:radio id="businessCoupon_couponType" name="businessCoupon.couponType"  list="%{#{'EARLY':'早订早惠','MORE':'多订多惠'}}" onclick="couponTypeOnChange(this,this.value)" />
			 				</td>
			 			</tr>
		 			</s:if>
		 			<s:else> 
		 				<tr>
			 				<td width="140px"  class="p_label">优惠类型：</td ><td >${businessCoupon.couponTypeZh}</td>
			 			</tr>
		 				<!-- 编辑动作 -->
		 				<div style="display:none;">
			 			        <s:radio id="businessCoupon_couponType" name="businessCoupon.couponType"  list="%{#{'EARLY':'早订早惠','MORE':'多订多惠'}}" onclick="couponTypeOnChange(this,this.value)" />
			 			</div>
		 			</s:else>
		 			 <tr>
			 		 	<td class="p_label"> </td><td id="tipTxt"> </td>  
			 		 </tr>
			 		 
			 		 <s:if test="businessCouponId == null">
			 		 	<!-- 新增动作 -->
		 				<tr id="branch_tr">
		 				<td class="p_label">产品可选类别： </td>
		 				<td>
		 					<s:iterator value="allBranchCodeItemList" var="codeItem" status="c">				
								<input id="branchId_<s:property value="#c.index"/>" name="branchIdCheckbox" type="checkbox" value="${codeItem.code}" <s:if test="checked"> checked="checked" </s:if> >
								${name}&nbsp;&nbsp;
							</s:iterator>
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
					 			 <input name="businessCoupon.playBeginTime" id="playBeginTime" type="text" class="date" disabled="disabled"  />
					 			~<input name="businessCoupon.playEndTime" id="playEndTime"  type="text" class="date" disabled="disabled"  />
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
		 			 
			 		 <tr>
			 		 	<td class="p_label"> </td><td>
			 		 	（说明：
			 		 	<s:if test='isAperiodic == "true"'>不定期产品统一按订购份数来计算</s:if>
			 		 	<s:else>酒店按间夜来计算，线路/门票产品按订购份数来计算</s:else>
			 		 	 ）</td>
			 		 </tr>
		 			<!-- 按金额早买 -->
		 			<tr id="AMOUNT_EARLY_TR">
		 				<td class="p_label"><span class="red">*</span>优惠细则：</td>
			 			<td>
			 			    <span id="AMOUNT_EARLYDAY_QUANTITY_PRE_SPAN">
			 			    	<input type="hidden" id="AMOUNT_EARLYDAY_QUANTITY_PRE_favorType" name="businessCoupon.favorType"  value="AMOUNT_EARLYDAY_QUANTITY_PRE" />
			 			    	<input type="hidden" id="AMOUNT_EARLYDAY_QUANTITY_PRE_TD_X" name="businessCoupon.argumentX" />每份(间夜)，结算价优惠金额
			 			    	<input  type="text" id="AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z"  name="argumentZYuan" 
			 			    		<s:if test="amountMode == 'amountYuan'"> value="${businessCoupon.argumentZYuan}" </s:if>
			 			    		<s:else> value="${businessCoupon.argumentZ}"</s:else> maxlength="11"/>元(请填写正数)。
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
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_INTERVAL_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份，结算总价优惠
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_INTERVAL_TD_Z" name="argumentZYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentZYuan}"</s:if><s:else>value="${businessCoupon.argumentZ}"</s:else> maxlength="11"/>元(请填写正数)。
			 			   </span>
			 			   <br/>
			 			  <span id="AMOUNT_MORE_QUANTITY_PRE_SPAN">
				 				 <input type="radio" id="AMOUNT_MORE_QUANTITY_PRE_RA" name="businessCoupon.favorType" value="AMOUNT_MORE_QUANTITY_PRE"  <s:if test='businessCoupon.favorType=="AMOUNT_MORE_QUANTITY_PRE"'>checked</s:if> 
				 					 	onclick="FavorTypeOnChange(this,'AMOUNT_MORE_QUANTITY_WHOLE_SPAN','AMOUNT_MORE_QUANTITY_INTERVAL_SPAN')" /> 满
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_PRE_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份起，结算单价优惠
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_PRE_TD_Z" name="argumentZYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentZYuan}"</s:if><s:else>value="${businessCoupon.argumentZ}"</s:else> maxlength="11"/>元(请填写正数)。
				 			</span>
				 			<br/>
				 			<span id="AMOUNT_MORE_QUANTITY_WHOLE_SPAN">
				 				 <input type="radio" id="AMOUNT_MORE_QUANTITY_WHOLE_RA" name="businessCoupon.favorType" value="AMOUNT_MORE_QUANTITY_WHOLE"  <s:if test='businessCoupon.favorType=="AMOUNT_MORE_QUANTITY_WHOLE"'>checked</s:if> 
				 					 	onclick="FavorTypeOnChange(this,'AMOUNT_MORE_QUANTITY_PRE_SPAN','AMOUNT_MORE_QUANTITY_INTERVAL_SPAN')" /> 满
				 				 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_X" name="businessCoupon.argumentX"  value="${businessCoupon.argumentX}" maxlength="11"/>份后，每增加
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_Y" name="businessCoupon.argumentY"  value="${businessCoupon.argumentY}" maxlength="11"/>份，结算总价优惠
					 			 <input  type="text" id="AMOUNT_MORE_QUANTITY_WHOLE_TD_Z" name="argumentZYuan"  <s:if test="amountMode == 'amountYuan'">value="${businessCoupon.argumentZYuan}"</s:if><s:else>value="${businessCoupon.argumentZ}"</s:else> maxlength="11"/>元(请填写正数)。
				 		 	</span>
			 			 </td>		
		 			</tr>
		 		</table>
		 		<p class="tc mt10"><input type="button" id="btnSubmit" class="btn btn-small w3" value="提交" /></p> 			 			 			
 		    </form>
 	    </div>
 	    
		<script type="text/javascript">
		    var checkedSelect;
		    var businessCouponId = $("#businessCoupon_businessCouponId").val();
		    
		    //优惠类型:早点早惠切换
		    function couponTypeOnChange(arg,value) {
		    	if(value == "EARLY"){
		    		$("#AMOUNT_MORE_TR").hide();
					$("#aheadNum").show();
					$("#AMOUNT_EARLY_TR").show();
				    $("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").removeAttr("disabled");
				    $("#tipTxt").html("<font>（说明：用户提前X天预订某一游玩日期的产品，可享受优惠）</font>");
				    $("#playTimeTxt").html("");
		    	}else if(value == "MORE"){
		    		$("#aheadNum").hide();
		    		$("#AMOUNT_EARLY_TR").hide();
		    		$("#AMOUNT_MORE_TR").show();
		    		$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").attr("disabled","disabled");
		    		//$("#AMOUNT_MORE_TR input[type='text']").val("");
		    		$("#AMOUNT_MORE_TR input[type='radio']").attr("checked",false);
		    		$("#tipTxt").html("<font>（说明：用户预订的订单，订购的份数（间夜）达到一定数量，可享受优惠）</font>");
		    		$("#playTimeTxt").html("<font>（可选填）</font>");
		    	}
		    	 	$("#AMOUNT_MORE_QUANTITY_INTERVAL_TD_X").attr("disabled","disabled");
		    	 	$("#AMOUNT_MORE_QUANTITY_INTERVAL_TD_Z").attr("disabled","disabled");
		    	 	
		    	  	$("#AMOUNT_MORE_QUANTITY_PRE_TD_X").attr("disabled","disabled");
		    	  	$("#AMOUNT_MORE_QUANTITY_PRE_TD_Z").attr("disabled","disabled");
		    	  	
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
			
		    $(function(){
		    	//初始化
		    	$("input.date").datepicker({dateFormat: 'yy-mm-dd'});
				$("#btnSubmit").click(function(){
				
					var couponType = $("input[name='businessCoupon.couponType']:checked").val();
					var favorMode = $("#businessCoupon_favorMode").val();
					var idValue = ",#businessCoupon_businessCouponId,#businessCoupon_valid,#businessCoupon_productId,#businessCoupon_branchId,#businessCoupon_couponType,#businessCoupon_couponTarget,#businessCoupon_metaType";
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
				 		if(($("input:checkbox[name='branchIdCheckbox'][checked='true']")).length<1){
							alert("请选择产品类别");
							$("#branchId_0").focus();
							return false;
						} 
				 	}
						 
					if($("#businessCoupon_beginTime").val()==""||$("#businessCoupon_endTime").val()==""){
				    	alert("请输入下单有效期");
				     	return false;
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
					
					if(($("#playBeginTime").val()!="" && $("#playEndTime").val()!="")){
						var startTime = $("#playBeginTime").val();
						var endTime = $("#playEndTime").val();
						if(compareDate(startTime, endTime, "游玩时间") == false){
							return;
						}
					}
					
					//下单有效期校验比较
					if(($("#businessCoupon_beginTime").val()!="" && $("#businessCoupon_endTime").val()!="")){
						var startTime = $("#businessCoupon_beginTime").val();
						var endTime = $("#businessCoupon_endTime").val();
						if(compareDate(startTime, endTime, "下单有效期") == false){
							return;
						}
					}
					
					if(couponType == "EARLY"){
						if(($("#playBeginTime").val()==""||$("#playEndTime").val()=="")&&($("#businessCoupon_aheadNum").val()=="")){
							alert("完整游玩日期和提前天数必填一项或二项");
							return false;
						}
					}
					
					 if(couponType == "EARLY" && $("#businessCoupon_aheadNum").val()!="" && !reg.test($("#businessCoupon_aheadNum").val())){
						alert("预订天数请正确填写正整数");
						$("#businessCoupon_aheadNum").focus();
						return false; 
					}
					
					//早买状态下获取X,Z值
					if(couponType == "EARLY" && favorMode == "AMOUNT"){
						if($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").val()==""){
							alert("请填写正数的优惠细则");
							$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").focus();
							return false;
						}else if(($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").val()!=""&&!yuanreg.test($("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").val()))){
							alert("请填写正数的优惠细则");
							$("#AMOUNT_EARLYDAY_QUANTITY_PRE_TD_Z").focus();
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
						 }else if($("#"+checkedSelect+"_TD_Z").attr("disabled") == true){
							  alert("选中的优惠细则没激活");
							  return false;
						 }else if(document.getElementById(checkedSelect+"_TD_X")&&$("#"+checkedSelect+"_TD_X").val()==""){
							 $("#"+checkedSelect+"_TD_X").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Z")&&$("#"+checkedSelect+"_TD_Z").val()==""){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(amountMoreCheckValue!=""&&document.getElementById(checkedSelect+"_TD_Y")&&$("#"+checkedSelect+"_TD_Y").val()==""){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if($("#"+checkedSelect+"_TD_X").val()!=""&&!reg.test($("#"+checkedSelect+"_TD_X").val())){
							 $("#"+checkedSelect+"_TD_X").focus();
							 alert("请填写优惠金额为正整数!");
							 return false;
						 }else if(amountMoreCheckValue!=""&&$("#"+checkedSelect+"_TD_Y").val()!=""&&!reg.test($("#"+checkedSelect+"_TD_Y").val())){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠金额为正整数!");
							 return false;
						 }else if($("#"+checkedSelect+"_TD_Z").val()!=""&&!yuanreg.test($("#"+checkedSelect+"_TD_Z").val())){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠金额为正值!");
							 return false;
						 }
					}
					
					 var branchIdCodes = "";
						//获取所有选中的产品类别
					 $("input[name='branchIdCheckbox']:checked").each(function(){
							branchIdCodes += "," + $(this).val();
					 });
					 branchIdCodes = branchIdCodes.substring(1);
					
					//表单提交  (只提交能编辑的input值)
					var arr = [];
					$("#saveBusinessCouponForm").find("input:radio:checked,textarea,input:text:not([disabled]),select" + idValue).each(function(){
					    arr.push(this.name+'='+this.value);
					});
					var data = arr.join("&")+"&branchIdCodes=" + branchIdCodes;
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
		</script> 	    
	</body>
</html>