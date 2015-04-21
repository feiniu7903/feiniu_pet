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
<form action="${contextPath }/ebooking/task/activateOrderShow.do" id="confirm" method="post">
<input name="ebkTaskId" id="ebkTaskId" value="${ebkTask.ebkTaskId }" type="hidden">
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
            <span>客人姓名：</span>
            <s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.certificate.travellerList" id="person" status="per">
            	<p><s:if test="#per.index!=0">、</s:if>${person.name }</p> 
            </s:iterator>
        </li>
        <li>
            <span>客人手机：</span>
            ${ebkTask.ebkCertificate.mobile }
        </li>
       <li>
            
            <span>住店日期：</span>
            <p><font class="orange"><s:date name="ebkTask.ebkCertificate.visitTime" format="yyyy-MM-dd"/></font> 
            	至 <font class="orange"><s:date name="ebkTask.ebkCertificate.leaveTime" format="yyyy-MM-dd"/></font> 
            	（共 <s:property value="ebkTask.ebkCertificate.ebkCertificateItemList.get(0).nights"/>晚）</p>
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
            <p class="width_800">
            	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">${ebkTask.ebkCertificate.allUserMemo }</s:if>
            	<s:else>无</s:else>
            </p>
        </li>
        <li class="li_hr"></li>
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
        	<p>${ebkTask.ebkCertificate.supplierNo  }</p>
        </li>
       
        <li>
        	<span>备注：</span>
            <p id="memo" class="width_800">${ebkTask.ebkCertificate.memo }</p>
        </li>
        <li class="li_hr"></li>
		<li>
			<span>　　</span>
			<a class="submit_but but_width124" target="_blank" href="${contextPath }/ebooking/task/activateOrderShow.do?ebkTaskId=${ebkTask.ebkTaskId}&type=HOTEL">修改订单</a>
			&nbsp;
			<a href="javascript:void(0)" class="submit_but but_width124" onclick="cancelPassword()">取消预订</a>
		<li>
		 <li>
        	<span>　　</span>
            <p class="red bold">　　　　　　　　（修改或取消订单有效期至<s:date name="ebkTask.ebkCertificate.validEndTime" format="yyyy-MM-dd"/>截止，逾期则订单无法修改或取消）</p>
        </li>
    </ul>
	<span class="print">
	<a  id="print" target="_blank" href="${contextPath }/ebooking/task/showCert.do?certificateId=${ebkTask.ebkCertificate.ebkCertificateId}" title="打印">打印订单</a>
	</span>
</div>

</form>

<script type="text/javascript">
	function cancelPassword() {
		if( confirm("确定取消？")){
			$("#useStatus").val("UNACTIVATED");
			var options = {
					url:"${contextPath }/ebooking/task/cancelTaskPassword.do",
					success:function(data){
						if(data.success) {
							alert("取消激活成功");
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
	} 
	
</script>

	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>