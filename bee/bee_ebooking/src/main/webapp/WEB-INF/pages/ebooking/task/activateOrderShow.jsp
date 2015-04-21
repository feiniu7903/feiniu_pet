<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="${contextPath }/js/base/jquery.form.js"></script>

</head>
<body id="body_ddgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>订单处理</li>
    	<li>酒店订单处理</li>
    </ul>
</div><!--以上是公用部分-->

<!--订单详情-->
<form action="${contextPath }/ebooking/task/taskImportPassword.do" id="confirm" method="post">
<input name="ebkTaskId" value="${ebkTask.ebkTaskId }" type="hidden">
<input name="orderId" value="${ebkTask.orderId }" type="hidden">
<input id="versionId" name="version" value="${ebkTask.ebkCertificate.version}" type="hidden">
<input id="useStatus" name="useStatus" value="${ebkTask.ebkCertificate.useStatus}" type="hidden">
<input id="CertificateStatus" name="CertificateStatus" value="ACCEPT" type="hidden">
<div class="dingdan_cl" id="print_content">
	<h3>订单详情</h3>
    <ul class="dingdan_cl_list">
		<li>
            <span class="cl_p_1">订单号：</span>
            <p class="cl_p_1"><samp style="margin-right:80px;">${ebkTask.orderId }</samp>订单状态：${ebkTask.zhOrderStatus}　　　　类型：${ebkTask.ebkCertificate.zhEbkCertificateType }</p>
        </li>
        <li>
			<span>订单有效期：</span><p class="red bold">
			<s:iterator value="validContentList">
				<s:property/><br/>
			</s:iterator></p>
		<li>
        <li>
        	<p style="float:none;">
				<span>订单密码券：</span><input type="text" id="passwordCertificate" name="passwordCertificate">
			</p>
			<p style="padding:10px 0 0 108px;float:none;clear:both;">
				<span class="red bold">密码券使用说明：</span><br>
				&nbsp;1、一次只可输入一个密码券<br>
				&nbsp;2、密码券使用成功后，订单被激活，请按订单提示，输入游客信息或游玩日期<br>
				&nbsp;3、密码券由纯数字组成，请注意填写<br>
				&nbsp;4、订单提交后，密码券立即作废，不可重复使用同一个密码券<br>
			</p>
       	</li>
        <li class="li_hr"></li>
        <li class="clearfix">
        	<ul class="xhcow" style="float:left;width:400px;">
		        <li>
		            <span>客人姓名：</span>
		            <s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.certificate.travellerList" id="person" status="per">
		            	<p><s:if test="#per.index!=0">、</s:if>${person.name }</p> 
		            </s:iterator>
		        </li>
		        <li>
		            <span>客人手机：</span>
		            ${ebkTask.ebkCertificate.mobile}
		        </li>
		        <li class="search_ul_b_3">
		        	<span>入住日期：</span>
		        	<input id="Calendar88" type="text" name="visitTimeStart" readonly="readonly" 
		        	value="<s:date name="ebkTask.ebkCertificate.visitTime" format="yyyy-MM-dd" />">
		        </li>
		        <li>        
		            <span>预订房型：</span>   
		            <p>
		            <s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.entity.subProductType=='SINGLE_ROOM'">     
			            <s:iterator id="item" value="ebkTask.ebkCertificate.ebkCertificateData.items">
					             <font class="orange">${item.baseInfo.metaBranchName }、<s:property value="ebkTask.ebkCertificate.ebkCertificateItemList.get(0).roomQuantity"/>间</font> /
					             <s:property value="ebkTask.ebkCertificate.ebkCertificateItemList.get(0).nights"/>晚
					             <s:if test="#night.breakfastCount != null"> 
					                                      、共 <font class="orange"><s:property value="breakfastCount"/> 份早餐</font></s:if> 
					               <s:if test="ebkTask.resourceConfirm == 'false'"><font class="orange">(保留房)</font></s:if><br>
			            </s:iterator>
			       	 </s:if>
			       	 <s:else>
			       	  <s:iterator id="item" value="ebkTask.ebkCertificate.ebkCertificateData.items">
			             <font class="orange">${item.baseInfo.metaBranchName }、${item.baseInfo.quantity }间</font> /${item.baseInfo.nights}晚
			             <s:if test="item.baseInfo.breakfastCount != null"> 
			                                      、共 <font class="orange">${item.baseInfo.breakfastCount} 份早餐</font></s:if> 
			               <s:if test="ebkTask.resourceConfirm == 'false'"><font class="orange">(保留房)</font></s:if> 
			             <br>
			             </s:iterator>
		            </s:else>
		            </p>
		        </li>
		        <li> 	
		            <span>付款方式：</span>
		            <p>${ebkTask.ebkCertificate.zhPaymentTarget }</p>
		        </li>
		        <li>
					<span>
			            <s:if test="ebkTask.totalSuggestionPrice != null">
			           		 建议售价：
			            </s:if>
			            <s:else>
			          		 结算价：
			            </s:else>
			        </span>
					<p>
						<s:iterator value="time" var="t">
							<s:date name="visitTime" format="yyyy-MM-dd" /> 
			            	RMB <font class="orange">
				            	<s:if test="ebkTask.totalSuggestionPrice != null">
				            		 ${suggestPriceYuan*ebkTask.roomQuantity }  
				            	</s:if> 
				            	<s:else>
				            		${settlementPriceYuan*ebkTask.roomQuantity }  
								</s:else>
							</font> 元<br>
						</s:iterator>
						共计 RMB <font class="orange bold"><s:property value="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.totalSettlementPrice"/></font> 元（含服务费）
					</p>
				</li>
		        <s:if test="ebkTask.paymentStatus == 'PAYED'">
		        <li>
		            <span>支付状态：</span>
		            <p><font class="orange bold">已支付</font></p>
		        </li>	
		        </s:if>
		        <li>
					<span>特殊要求：</span>
		            <p class="">
		            	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">${ebkTask.ebkCertificate.allUserMemo }</s:if>
		            	<s:else>无</s:else>
		            </p>
		        </li>
		    </ul>
		    <ul class="xhcow" style="float:left;width:480px;">
		        <li class="margin_b_5">
		        	<span>确认结果：</span>
		        	 <s:if test="ebkTask.ebkCertificate.CertificateStatus == 'REJECT'">
		        	    <p class="red bold">${ebkTask.ebkCertificate.zhCertificateTypeStatus }</p>
		        	 </s:if>
		        	 <s:else>
		        	    <p class="green bold">${ebkTask.ebkCertificate.zhCertificateTypeStatus}</p>
		        	 </s:else>
		        </li>
				 <s:if test="ebkTask.ebkCertificate.zhCertificateStatus == 'REJECT'">
			        <li class="margin_b_5">
			        	<span>不接受原因：</span><p class="green bold">${ebkTask.ebkCertificate.reason }</p>
			        </li>
		        </s:if>
		        
		        <li class="margin_b_5">
		        	<span>确认人：</span><p>${ebkTask.confirmUser }</p>
		        </li>
		        <li class="margin_b_5">
		        	<span>确认时间：</span><p><s:date name="ebkTask.confirmTime" format="yyyy-MM-dd HH:mm:ss"/></p>
		        </li>
		         <li>
		        	<span>酒店确认号：</span>
		            <p><input class="hotel_num" name="supplierOrderNo" type="text" value="${ebkTask.ebkCertificate.supplierNo }">
		        </li>
		        <li>
		        	<span>备注：</span>
		            <p id="memo" class="">
		            	<textarea class="beizhu_in" id="memo" name="memo" >${ebkTask.ebkCertificate.memo }</textarea>
		            </p>
		        </li>
	        </ul>
	    </li>
		<li style="text-align:center;">
			<a href="javascript:void(0)" class="submit_but" style="width:120px" onclick="toActivationPassword('${ebkTask.orderId}')">确认提交</a>
		<li>
    </ul>
</div>

</form>

<script type="text/javascript">

	$(function() {
	
		$(".search_ul_b_3").ui("calendar", {
			input : "#Calendar88",
			parm : {
				dateFmt : "yyyy-MM-dd"
			}
		});
	});

	function toActivationPassword(orderId) {
		var visitTime = $("#Calendar88").val();
		if(visitTime==''){
			alert("请选择入住日期！");
			return false;
		}
		if(timeCompareNow(visitTime)){
			alert("入住日期不能今日之前！");
			return false;
		}
		var imPassword = $("#passwordCertificate").val();
		if(imPassword==''){
			alert("请输入密码券！");
			return false;
		}
		$.ajax({
			type : 'POST',
			url : '${contextPath }/ebooking/task/checkPasswordCertificate.do',
			async : false,
			cache : false,
			data : {
				orderId : '${ebkTask.orderId}', passwordCertificate:imPassword, visitTimeStart:visitTime
			},
			dataType : 'json',
			success : function(data) {
				if(data.success) {
					doOperate();
				} else {
					if(data.canPass) {
						if(confirm(data.message)){
							doOperate();
						}
					} else {
						alert(data.message);
					}
				} 
			}
		});
	}
	
	function doOperate() {
		var options = {
				url:$("#confirm").attr("action"),
				success:function(data){
					if(data.success) {
						alert("激活成功");
						location = "${contextPath }/ebooking/task/orderck.do?ebkTaskId=${ebkTask.ebkTaskId}";
						window.opener.location.reload();
					} else {
						bClicked = false;
						alert(data.msg);
					}
				}
			};
		$("#confirm").ajaxSubmit(options);
	}
	
	function mobileVali(mobile){
		if (mobile == '') {
			alert("请输入手机号！");
			return false;
		}
		if (mobile.length != 11) {
			alert("手机号必须为11位！");
			return false;
		}
		var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if (!MOBILE_REGX.test(mobile)) {
			alert("手机号格式不正确！");
			return false;
		}
		return true;
	}
	
	function timeCompareNow(time) {
	    var arr = time.split("-");
	    var starttime = new Date(arr[0], arr[1], arr[2]);
	    var starttimes = starttime.getTime();
	
	    var lktime = new Date();    
	    lktime = new Date(lktime.getFullYear() , (lktime.getMonth() + 1) , lktime.getDate());   
	    var lktimes = lktime.getTime();
	    if (starttimes >= lktimes) {
	        return false;
	    }
	    else
	        return true;
	}
</script>

	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>