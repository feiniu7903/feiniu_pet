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
		<title>优惠券(活动)列表</title>
	</head>
	<body>
 		<div>
 			<form id='saveMarkCouponForm' method='post' action='<%=basePath%>/mark/coupon/addOrModifyMarkCoupon.do'>
		 	    <input id="markCoupon_couponId" type="hidden" name="markCoupon.couponId" value="${markCoupon.couponId}" />
		 	    <input id="markCoupon_valid" type="hidden" name="markCoupon.valid" value="${markCoupon.valid}" />
		 	    <input id="markCoupon_usedCoupon" type="hidden" name="markCoupon.usedCoupon" value="${markCoupon.usedCoupon}" />
		 		<table class="p_table form-inline">
		 		
		 		 	<s:if test="markCoupon !=null && markCoupon.couponId == null">
		 		 		<!-- 新增动作 -->
			 			<tr>
			 				<td width="15%" class="p_label"><span class="red">*</span></td>
			 				<td>
			 					<s:radio id="markCoupon_withCode" name="markCoupon.withCode"  list="%{#{'true':'新增优惠券','false':'新增优惠活动'}}" onclick="withCodeOnChange(this.value)" />
			 				</td>
			 			</tr>
		 			</s:if>
		 			<s:else> 
		 				<!-- 编辑动作 -->
		 				<div style="display:none;">
		 			       <s:radio id="markCoupon_withCode" name="markCoupon.withCode"  list="%{#{'true':'新增优惠券','false':'新增优惠活动'}}" onclick="withCodeOnChange(this.value)" />
		 				</div>
		 			</s:else>
		 			<tr>
		 				<td class="p_label"><span class="red">*</span>发布渠道:</td>
		 				<td>  
		 					<p><input type="text" id="channel1" class="adAuto" /></p>
		 					<p><input type="text" id="channel2" class="adAuto" /></p>
		 					<p><input type="text" id="channel3" class="adAuto" /></p>
		 					<s:hidden name="markCoupon.channelId" />
		  				</td>
		 			</tr> 			
		 			<tr>
		 				<td class="p_label"><span class="red">*</span>名称:</td>
		 				<td>
		 					<s:textfield id="markCouponName" name="markCoupon.couponName" cssClass="newtext1" maxlength="50"/>
		 				</td>
		 			</tr>
		 			<tr>
		 				<td class="p_label"><span class="red">*</span>适用范围:</td>
		 				<td>
		 					<s:radio name="markCoupon.couponTarget" list="%{#{'ORDER':'全场通用','PRODUCT':'非全场通用'}}"/>
		 				</td>
		 			</tr>
		 			
		 			<tr id="payByChannelRow"  <s:if test='markCoupon.withCode=="true"'>style="display:none"</s:if> >
		 				<td class="p_label"><span class="red">*</span>是否按支付渠道:</td>
		 				<td>
			 				    <s:radio name="payByChannel" list="%{#{'true':'是','false':'否'}}"  onclick="payByChannelOnChange(this)"/>
			 					<s:select list="paymentChannelList" id="paymentChannelSelect"
									name="markCoupon.paymentChannel" listKey="code"
									listValue="name"  headerKey="" headerValue="--请选择--"></s:select>
		 				</td>
		 			</tr>
		 			
		 			<tr id="couponTime">
		 				<td class="p_label"><span class="red">*</span>设置优惠劵有效时间:</td>
		 				<td>
		 					<div id="UnFixedVaildDiv">
		 						<input id="unFixedVaildType" name="markCoupon.validType" type="radio" <s:if test='markCoupon.validType=="UNFIXED"'>checked="checked"</s:if> value="UNFIXED"  onchange="unFixedVaildTypeChange(this)"/>按有效期限
		 						&nbsp;&nbsp;&nbsp;
		 						<s:textfield id="markCoupon_termOfValidity" name="markCoupon.termOfValidity"   maxlength="10" value="%{markCoupon.termOfValidity}"/>天（优惠券有效日期=生成时间+期限天数）
		 				    	<br/>
		 					</div>
		 					<div id="FixedVaildDiv">
		 				    <input id="fixedVaildType" name="markCoupon.validType" type="radio" <s:if test='markCoupon.validType=="FIXED"'>checked="checked"</s:if> value="FIXED"  onchange="fixedVaildTypeChange(this)"/>按有效日期	
		 				    <input name="markCoupon.beginTime" id="markCoupon_beginTime" type="text" class="date"  value="<s:date name="markCoupon.beginTime" format="yyyy-MM-dd"/>"  />
		 				    ~<input name="markCoupon.endTime" id="endTime"  type="text" class="date" value="<s:date name="markCoupon.endTime" format="yyyy-MM-dd"/>"  />（即下单有效期）
		 				    </div>
		  				 </td>
		 			</tr>
		
		
		 			<tr id="couponTypeTR">
		 				<td class="p_label"><span class="red">*</span>优惠券类型:</td>
		 				<td> 
		 					<s:radio name="markCoupon.couponType" list="%{#{'A':'A类（规定时间无限次使用）','B':'B类（规定时间只能使用一次）'}}" onclick="couponTypeOnChange(this)"/>
		 				</td>
		 			</tr>
		 			<tr id="firstCodeTR" >
		 				<td class="p_label">号码开头:</td>
		 				<td><span id="firstCodeSpan">${markCoupon.couponType}</span><s:textfield id="markCoupon_firstCode" name="markCoupon.firstCode" cssClass="newtext1" maxlength="11" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')"/></td>
		 			</tr>
		 			<tr id="maxCouponTR" >
		 				<td class="p_label">最大优惠金额:</td>
		 				<td>
		 					<input type="radio" name="maxCouponFlag" id="maxCouponNot" value="-1" onclick="maxCouponOnChange(this.value)"/>不限制最大优惠金额
		 					<input type="radio" name="maxCouponFlag" id="maxCouponYes"  value="0" onclick="maxCouponOnChange(this.value)"/>限制最大优惠金额
		 					<span id="maxCouponDiv" style="display:none;">
		 						<input id="markCoupon_maxCoupon" type="text" name="markCoupon.maxCoupon" value="${markCoupon.maxCouponYuan}"  />元
		 						（有可能会超，且输入数小于等于实际使用数。）
		 					</span>
		 				</td>
		 			</tr>
		 			<tr>
		 			  <td class="p_label"><span class="red">*</span>优惠方式: </td>
		 			    <td>
		 			    	<input type="radio" name="markCoupon.favorMode" value="AMOUNT"
						         <s:if test='markCoupon.favorMode == "AMOUNT"'>checked</s:if>
						          onclick="FavorModeOnChange(this.value)" />按金额
						         <input type="radio" name="markCoupon.favorMode" value="DISCOUNT"
						         <s:if test='markCoupon.favorMode == "DISCOUNT"'>checked</s:if>
						         onclick="FavorModeOnChange(this.value)" />按折扣(销售价上的折扣)
		 				</td>
		 			</tr>
		 			<tr>
		 			    <td class="p_label"><span class="red">*</span>优惠规则计算方式： </td>
		 			    <td> 
		 			    	<s:radio name="markCoupon.favorRule" list="%{#{'AMOUNT':'按订单金额 <br/>','QUANTITY':'订购份数(酒店类产品按照间夜数计算，其他产品按照默认类别订购份数计算)'}}" onclick="FavorRuleOnChange(this.value)" />
		 				</td>
		 			</tr>
		 			<tr id="AMOUNT_AMOUNT_TR"> 
		 				<td class="p_label"><span class="red">*</span>优惠细则:</td>
			 			<td>
			 			    <span id="AMOUNT_AMOUNT_WHOLE_SPAN">
			 			        <input id="AMOUNT_AMOUNT_WHOLE_RA" type="radio" name="markCoupon.favorType" value="AMOUNT_AMOUNT_WHOLE"  <s:if test='markCoupon.favorType=="AMOUNT_AMOUNT_WHOLE"'>checked</s:if> onclick="FavorTypeOnChange(this)"  />满 
			 			    	<input type="text" id="AMOUNT_AMOUNT_WHOLE_TD_X" name="argumentXYuan" value="${markCoupon.argumentXYuan }" maxlength="11"/>元后，销售价一次性优惠
			 			    	<input type="text" id="AMOUNT_AMOUNT_WHOLE_TD_Y" name="argumentYYuan" value="${markCoupon.argumentYYuan }" maxlength="11"/>元(请填写正数)。</span><br/>
			 				<span id="AMOUNT_AMOUNT_INTERVAL_SPAN">
			 				    <input type="radio" id="AMOUNT_AMOUNT_INTERVAL_RA" name="markCoupon.favorType" value="AMOUNT_AMOUNT_INTERVAL"  <s:if test='markCoupon.favorType=="AMOUNT_AMOUNT_INTERVAL"'>checked</s:if> onclick="FavorTypeOnChange(this)"  />满 
				 				<input type="text" id="AMOUNT_AMOUNT_INTERVAL_TD_X" name="argumentXYuan" value="${markCoupon.argumentXYuan }" maxlength="11"/>元后，每满
				 				<input type="text" id="AMOUNT_AMOUNT_INTERVAL_TD_Y" name="argumentYYuan" value="${markCoupon.argumentYYuan }" maxlength="11"/>元，销售价优惠
				 				<input type="text" id="AMOUNT_AMOUNT_INTERVAL_TD_Z" name="argumentZYuan" value="${markCoupon.argumentZYuan }" maxlength="11"/>元(请填写正数)。</span>
			 			 </td>		
		 			</tr>
		 			<tr id="AMOUNT_QUANTITY_TR"> 
		 				<td class="p_label"><span class="red">*</span>优惠细则:</td>
			 			<td>
			 			    <span id="AMOUNT_QUANTITY_WHOLE_SPAN">
			 			    	<input type="radio" id="AMOUNT_QUANTITY_WHOLE_RA" name="markCoupon.favorType" value="AMOUNT_QUANTITY_WHOLE"  <s:if test='markCoupon.favorType=="AMOUNT_QUANTITY_WHOLE"'>checked</s:if> onclick="FavorTypeOnChange(this)" />满
			 			    	 <s:textfield id="AMOUNT_QUANTITY_WHOLE_TD_X" name="markCoupon.argumentX" maxlength="11"/>份后，销售价一次性优惠
			 			    	 <input type="text" id="AMOUNT_QUANTITY_WHOLE_TD_Y" name="argumentYYuan" value="${markCoupon.argumentYYuan }" maxlength="11"/>元(请填写正数)。</span><br/>
			 				<span id="AMOUNT_QUANTITY_INTERVAL_SPAN">
			 				 <input type="radio" id="AMOUNT_QUANTITY_INTERVAL_RA" name="markCoupon.favorType" value="AMOUNT_QUANTITY_INTERVAL"  <s:if test='markCoupon.favorType=="AMOUNT_QUANTITY_INTERVAL"'>checked</s:if> onclick="FavorTypeOnChange(this)" />满
			 				  <s:textfield id="AMOUNT_QUANTITY_INTERVAL_TD_X" name="markCoupon.argumentX" maxlength="11"/>份后，每满
			 				  <s:textfield id="AMOUNT_QUANTITY_INTERVAL_TD_Y" name="markCoupon.argumentY" maxlength="11"/>份，销售价优惠
			 				  <input type="text" id="AMOUNT_QUANTITY_INTERVAL_TD_Z" name="argumentZYuan"  value="${markCoupon.argumentZYuan }" maxlength="11"/>元(请填写正数)。</span><br/>
			 				<span id="AMOUNT_QUANTITY_PRE_SPAN">
			 				 <input type="radio" id="AMOUNT_QUANTITY_PRE_RA" name="markCoupon.favorType" value="AMOUNT_QUANTITY_PRE"  <s:if test='markCoupon.favorType=="AMOUNT_QUANTITY_PRE"'>checked</s:if> onclick="FavorTypeOnChange(this)" />满 
			 				 <s:textfield id="AMOUNT_QUANTITY_PRE_TD_X" name="markCoupon.argumentX" maxlength="11"/>份后，每份销售价优惠
			 				 <input type="text" id="AMOUNT_QUANTITY_PRE_TD_Y" name="argumentYYuan" value="${markCoupon.argumentYYuan }" maxlength="11"/>元(请填写正数)。</span>
			 			 </td>		
		 			</tr>
		 			<tr id="DISCOUNT_AMOUNT_TR"> 
		 				<td class="p_label"><span class="red">*</span>优惠细则:</td>
			 			<td>
			 			    <span id="DISCOUNT_AMOUNT_WHOLE_SPAN">
			 			     <input type="radio" id="DISCOUNT_AMOUNT_WHOLE_RA" name="markCoupon.favorType" value="DISCOUNT_AMOUNT_WHOLE"  <s:if test='markCoupon.favorType=="DISCOUNT_AMOUNT_WHOLE"'>checked</s:if> onclick="FavorTypeOnChange(this)" />满 
			 			      <input type="text" id="DISCOUNT_AMOUNT_WHOLE_TD_X" name="argumentXYuan" value="${markCoupon.argumentXYuan }" maxlength="11"/>元后，销售价一次性享受
			 			      <s:textfield id="DISCOUNT_AMOUNT_WHOLE_TD_Y" name="markCoupon.argumentY" maxlength="11"/>折(请填写正数)。</span><br/>
			 			 </td>		
		 			</tr> 			
		 			<tr id="DISCOUNT_QUANTITY_TR"> 
		 				<td class="p_label"><span class="red">*</span>优惠细则:</td>
			 			<td>
			 			    <span id="DISCOUNT_QUANTITY_WHOLE_SPAN">
			 			     <input type="radio" id="DISCOUNT_QUANTITY_WHOLE_RA" name="markCoupon.favorType" value="DISCOUNT_QUANTITY_WHOLE"  <s:if test='markCoupon.favorType=="DISCOUNT_QUANTITY_WHOLE"'>checked</s:if> onclick="FavorTypeOnChange(this)"  />满 
			 			       <s:textfield id="DISCOUNT_QUANTITY_WHOLE_TD_X" name="markCoupon.argumentX" maxlength="11"/>份后，销售价一次性享受
			 			       <s:textfield id="DISCOUNT_QUANTITY_WHOLE_TD_Y" name="markCoupon.argumentY" maxlength="11"/>折(请填写正数)。</span><br/>
			 				<span id="DISCOUNT_QUANTITY_PRE_SPAN">
			 				 <input type="radio" id="DISCOUNT_QUANTITY_PRE_RA" name="markCoupon.favorType" value="DISCOUNT_QUANTITY_PRE"  <s:if test='markCoupon.favorType=="DISCOUNT_QUANTITY_PRE"'>checked</s:if> onclick="FavorTypeOnChange(this)" />满 
			 				   <s:textfield id="DISCOUNT_QUANTITY_PRE_TD_X" name="markCoupon.argumentX" maxlength="11"/>份后，每满
			 				   <s:textfield id="DISCOUNT_QUANTITY_PRE_TD_Y" name="markCoupon.argumentY" maxlength="11"/>份，超出部分销售价享受
			 				   <s:textfield id="DISCOUNT_QUANTITY_PRE_TD_Z" name="markCoupon.argumentZ" maxlength="11"/>折(请填写正数)。</span>
			 			 </td>		
		 			</tr>		 			 			
		 			<tr>
		 				<td class="p_label">优惠券(活动)描述:</td>
		 				<td>
		 					<table width="95%" class="table">
		 						<tr>
		 						  <td>
		 						    <textarea name="markCoupon.description" rows="5" cols="100" id="markCoupon_description" class="p-textarea">${markCoupon.description} </textarea>
		 						  </td>
		 						</tr>
		 						<tr><td>示例:</td></tr>
		 						<tr><td>1.优惠券有效期:2012.5.1-2012.6.30。</td></tr>
		 						<tr><td>2.订单金额满300元,超出部分每150元优惠5元.(或:订单份数满3人,超出部分每4人可抵用5元)。</td></tr>
		 						<tr><td>3.此券只适用于指定产品。</td></tr>
		 						<tr><td>4.此券不可以与其他优惠同时使用。</td></tr>
		 					</table>
		 				</td>
		 			</tr>
		 			<tr>
		 				<td class="p_label">费用承担部门:</td>
		 				<td>
		 					<table width="95%" class="table">
		 						<tr><td>
		 						<textarea name="markCoupon.memo" rows="1" cols="100" id="markCouponMemo" class="p-textarea">${markCoupon.memo} </textarea>
		 						</td></tr>
		 					</table>
		 				</td>
		 			</tr>
		 		</table>
		 		<p class="tc mt10"><input type="button" id="btnSubmit" class="btn btn-small w3" value="提交" /></p> 			 			 			
 		    </form>
 	    </div>
 	    
		<script type="text/javascript">
		    var favorMode = "${markCoupon.favorMode}";
		    var favorRule = "${markCoupon.favorRule}";
		    var withCode="${markCoupon.withCode}";
		    var couponId="${markCoupon.couponId}";
		    var validType="${markCoupon.validType}";
		    var checkedSelect;
		    var payBuyChannel="${markCoupon.paymentChannel}";
		    
		    if(couponId!=""){
		        if(validType=="UNFIXED"){
		        	$("#fixedVaildType").attr("disabled",true);
		        }
		        if(validType=="FIXED"){
		        	$("#unFixedVaildType").attr("disabled",true);
		        }
		    }
		    
		    if(payBuyChannel!=null&&payBuyChannel!=""){
		       $("input:radio[name='payByChannel'][value='true']").attr("checked",true);
		    }else{
		       $("input:radio[name='payByChannel'][value='false']").attr("checked",true);
		       $("#paymentChannelSelect").attr("disabled","disabled");
		    }
		    
		    if(withCode=="false"){
		    	$("#couponTypeTR,#firstCodeTR,#UnFixedVaildDiv").hide();
		    }
		    
		    function unFixedVaildTypeChange(obj){
		    	var isCheck=$(obj).attr("checked");
		    	if(isCheck==true){
		    		$("#markCoupon_beginTime").val("");
				 	$("#endTime").val("");
		    	}
		    }
		    
		    function fixedVaildTypeChange(obj){
		    	var isCheck=$(obj).attr("checked");
		    	if(isCheck==true){
		    		$("#markCoupon_termOfValidity").val("");
		    	}
		    }
		    function payByChannelOnChange(obj){
		    	var val=$("input:radio[name='payByChannel'][checked='true']").val();
				if (val == 'true') {
					$("#paymentChannelSelect").removeAttr("disabled");
				} else {
					$("#paymentChannelSelect").val('');
					$("#paymentChannelSelect").attr("disabled","true");
				}
		    }
		    
			function withCodeOnChange(value) {
				if (value == 'true') {
					withCode=true;
					$("#couponTypeTR,#firstCodeTR,#UnFixedVaildDiv").show();
					$("#payByChannelRow").hide();
					$("#maxCouponTR").hide();
					maxCouponOnChange(-1);
				} else {
					withCode=false;
					$("#couponTypeTR,#firstCodeTR,#UnFixedVaildDiv").hide();
					$("#payByChannelRow").show();
					$("#fixedVaildType").attr("checked","checked");
					$("#maxCouponTR").show();
					var couponId=$("#markCoupon_couponId").val();
					if(couponId==null ||couponId==""){
						maxCouponOnChange(-1);
					}else{
						maxCouponOnChange('${markCoupon.maxCoupon}');
					}
				}
			}
			
			function couponTypeOnChange(e) {
				$("#firstCodeSpan").html($("input:radio[name='markCoupon.couponType'][checked='true']").val());
			}
			
			function FavorModeOnChange(value) {
				favorMode = value;
				
				$("#AMOUNT_AMOUNT_TR").hide();
				$("#AMOUNT_QUANTITY_TR").hide();
				$("#DISCOUNT_AMOUNT_TR").hide();
				$("#DISCOUNT_QUANTITY_TR").hide();
				$("input:radio[name='markCoupon.favorType']").attr("checked",false);
				$("#" + favorMode + "_" + favorRule + "_TR").show();	
			}
			
			function FavorRuleOnChange(value) {
				favorRule = value;
				
				$("#AMOUNT_AMOUNT_TR").hide();
				$("#AMOUNT_QUANTITY_TR").hide();
				$("#DISCOUNT_AMOUNT_TR").hide();
				$("#DISCOUNT_QUANTITY_TR").hide(); 
				$("input:radio[name='markCoupon.favorType']").attr("checked",false);
				$("#" + favorMode + "_" + favorRule + "_TR").show();	
			}	
				
				function FavorTypeOnChange(obj) {
				checkedSelect=$(obj).val();
				$("#AMOUNT_AMOUNT_TR input[type='text']").attr("disabled","disabled");
				$("#AMOUNT_QUANTITY_TR input[type='text']").attr("disabled","disabled");
				$("#DISCOUNT_AMOUNT_TR input[type='text']").attr("disabled","disabled");
				$("#DISCOUNT_QUANTITY_TR input[type='text']").attr("disabled","disabled");
				$(obj).attr("checked",true);
				$("#" + checkedSelect + "_SPAN input").removeAttr("disabled");
			}
		    $(function(){
		    	//初始化
		    	
		        $("#channel1").ui("adAutoComplete",{type : 1 <s:if test='firstMarkChannel.channelId != null'>, value:"${firstMarkChannel.channelName}", channelId:"${firstMarkChannel.channelId}"</s:if>});
		    	$("#channel2").ui("adAutoComplete",{type : 2, main : "#channel1" <s:if test='secondMarkChannel.channelId != null'>, value:"${secondMarkChannel.channelName}", channelId:"${secondMarkChannel.channelId}"</s:if>}); 
		    	$("#channel3").ui("adAutoComplete",{type : 3, main : "#channel2" <s:if test='markCoupon.channelId != null'>, value:"${markCoupon.channelName}", channelId:"${markCoupon.channelId}"</s:if>});    	
 
		    	$("#channel3").blur(function(){
		    	    $("#markCoupon_firstCode").val($(this).attr("channelId"))
		    	})
		    	
		    	$("input.date").datepicker({dateFormat: 'yy-mm-dd'});
		    	$("#unFixedVaildType").click(function(){
				    $("#markCoupon_termOfValidity").removeAttr("disabled");
				 	$("#markCoupon_beginTime").attr("disabled","disabled");
				 	$("#endTime").attr("disabled","disabled");
				 	
				 	
				});
				
				$("#fixedVaildType").click(function(){
				 	$("#markCoupon_termOfValidity").attr("disabled","disabled");
				 	$("#markCoupon_beginTime").removeAttr("disabled");
				 	$("#endTime").removeAttr("disabled");
				});
			
				$("#btnSubmit").click(function(){
					//校验
					$("#markCoupon_channelId").val($("#channel3").attr("channelId"));
					if ($("#markCoupon_channelId").val() == ""){
						alert("请选择三级渠道");
						$("#channel3").focus();
						return false;
					}
					if($("#markCouponName").val() == ""){
						alert("请输入名称");
					    $("#markCouponName").focus();
					    return false;  
					}
					if($("#unFixedVaildType").attr("checked") == false&&$("#fixedVaildType").attr("checked") == false){
						alert("请输入选择有效时间类型");
						$("#unFixedVaildType").focus();
						return false;
					}
					if($("#unFixedVaildType").attr("checked") == true&&$("#markCoupon_termOfValidity").val()==""){
		 			      alert("请输入有效时间天数");
		 			      $("#markCoupon_termOfValidity").focus();
		 			     return false;  
					 }
					if($("#fixedVaildType").attr("checked") == true&&($("#markCoupon_beginTime").val()==""||$("#endTime").val()=="")){
					      if($("#markCoupon_beginTime").val()==""){
					    	  alert("请输入有效时间开始时间");
						      $("#markCoupon_beginTime").focus();
					      }else{
					    	  alert("请输入有效时间结束时间");
						      $("#endTime").focus();
					      }
					     return false;  
					 }
					if((withCode=="true")&&($("#markCoupon_firstCode").val()=="")){		 
							alert("请输入号码开头");
							$("#markCoupon_firstCode").focus();
							return false;	 
				    }
					if(withCode==true&&($("#markCoupon_firstCode").val().length>=10)){		 
						
						alert("号码开头字符长度过长");
						$("#markCoupon_firstCode").focus();
						return false;	 
			         }
					if($("input:checked[name='markCoupon.withCode']").val()=='false'){
						if($("#payByChanneltrue").attr("checked") == true && $("#paymentChannelSelect").val() == ""){
							alert("请选择支付渠道");
						    $("#paymentChannelSelect").focus();
						    return false;  
						}
						if($("input:checked[name='maxCouponFlag']").val()!=-1){
							if($("#markCoupon_maxCoupon").val()==""){
								alert("请输入最大优惠金额");
								$("#markCoupon_maxCoupon").focus();
								return false;
							}else{
								var maxCoupon=$("#markCoupon_maxCoupon").val();
								var length=maxCoupon.indexOf(".");
								if(length!=-1){
									maxCoupon=maxCoupon.substring(0,length);
								}
								maxCoupon = maxCoupon.replace(".0","");	
								if(maxCoupon>5000000){
									alert("最大优惠金额不能超过5000000，请重新输入！");
									$("#markCoupon_maxCoupon").focus();
									return false;
								}
								$("#markCoupon_maxCoupon").val(maxCoupon);
							}
						}
					}

					 //优惠规则校验 
					 if(($("input:radio[name='markCoupon.favorType'][checked='true']")).length<1){
						 alert("请选择优惠细则");
						 return false;
					 }
					 if( $("#"+checkedSelect+"_RA").attr("checked") == true){
						 if(document.getElementById(checkedSelect+"_TD_X")&&$("#"+checkedSelect+"_TD_X").val()==""){
							 $("#"+checkedSelect+"_TD_X").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Y")&&$("#"+checkedSelect+"_TD_Y").val()==""){
							 $("#"+checkedSelect+"_TD_Y").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Y")&&$("#"+checkedSelect+"_TD_Y").val() < 0&&
								 ((checkedSelect+"_TD_Y").charCodeAt()=="AMOUNT_AMOUNT_WHOLE_TD_Y".charCodeAt()||
								(checkedSelect+"_TD_Y").charCodeAt()=="AMOUNT_QUANTITY_WHOLE_TD_Y".charCodeAt()||
								(checkedSelect+"_TD_Y").charCodeAt()=="AMOUNT_QUANTITY_PRE_TD_Y".charCodeAt())&&
								(!document.getElementById(checkedSelect+"_TD_Z"))
								){
									 $("#"+checkedSelect+"_TD_Y").focus();
									 alert("请填写优惠金额为正值!");
									 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Z")&&$("#"+checkedSelect+"_TD_Z").val()==""){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠细则");
							 return false;
						 }else if(document.getElementById(checkedSelect+"_TD_Z")&&$("#"+checkedSelect+"_TD_Z").val() < 0&&
								 ((checkedSelect+"_TD_Z").charCodeAt()=="AMOUNT_AMOUNT_INTERVAL_TD_Z".charCodeAt()||	 
								(checkedSelect+"_TD_Z").charCodeAt()=="AMOUNT_QUANTITY_INTERVAL_TD_Z".charCodeAt()) ){
							 $("#"+checkedSelect+"_TD_Z").focus();
							 alert("请填写优惠金额为正值!");
							 return false;
						 }
					}
					 var reg = /^(?:0|[1-9][0-9]?|100)$/; 
					 var amountWhole=$("#DISCOUNT_AMOUNT_WHOLE_TD_Y").val(); 
					 var quantityWholeTDY=$("#DISCOUNT_QUANTITY_WHOLE_TD_Y").val(); 
					 var quantityPreTDZ=$("#DISCOUNT_QUANTITY_PRE_TD_Z").val(); 

					 if((amountWhole!=""&&!reg.test(amountWhole))||(quantityWholeTDY!=""&&!reg.test(quantityWholeTDY))||(quantityPreTDZ!=""&&!reg.test(quantityPreTDZ))){ 
						 alert("此处不支持小数点折扣录入，请正确填写整数（非负值）折扣，如输入78表示7.8折"); 
						 return false; 
					 }
					
					//表单提交 
					var arr = [];
					$("#saveMarkCouponForm").find("#markCoupon_channelId,input:radio:checked,textarea,input:text:not([disabled]),select,#markCoupon_couponId,#markCoupon_valid,#markCoupon_withCode,#markCoupon_usedCoupon").each(function(){
					    arr.push(this.name+'='+encodeURIComponent(this.value));
					});
					var data = arr.join("&");
					$.ajax({
		        	 	url: "<%=basePath%>/mark/coupon/addOrModifyMarkCoupon.do",
		        	 	data: data,
		        	 	beforeSend: function(XMLHttpRequest){
		                     XMLHttpRequest.setRequestHeader("RequestType", "ajax");
		                },
						type:"post",
		        	 	dataType:"json",
		        	 	success: function(result) {
		        	 		if (result.success) {
		        	 			alert(result.successMessage);
		        	 		 	//window.location.reload();	
		        	 		 	window.location.href="<%=basePath%>mark/coupon/queryCouponList.do?_=" + (new Date).getTime();
		        	 		} else {
		        	 			alert(result.errorMessage);
		        	 		}
			        			
		        	 	}
		        	 });			
				});
				
				$("input:radio:checked").click();
				$("input:text:disabled").val("");
				
			});
		    
		    function maxCouponOnChange(value){
		    	if(value==-1){
		    		$("#maxCouponNot").attr("checked","checked");
		    		$("#maxCouponDiv").hide();
		    		$("#markCoupon_maxCoupon").val(-1);
		    	}else{
		    		$("#maxCouponYes").attr("checked","checked");
		    		if($("#markCoupon_maxCoupon").val()==-1||value==-1){
		    			$("#markCoupon_maxCoupon").val("");
		    		}else{
		    			$("#markCoupon_maxCoupon").val('${markCoupon.maxCouponYuan}');
		    		}
		    		$("#maxCouponDiv").show();
		    	}
		    }
		</script> 	    
	</body>
</html>