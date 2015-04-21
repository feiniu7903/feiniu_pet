<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>订单处理后台_Ebooking待处理订单监控</title>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<!-- LVMAMA -->
<script type="text/javascript" src="${basePath}js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}js/base/lvmama_dialog.js"></script>
<!-- url -->
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/ebooking/base.css" />
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
</head>
<body>
<div>
	<!-- 订单详情 -->
	<div class="dingdan_cl" id="print_content">
	<h3>查看${ebkTask.ebkCertificate.zhEbkCertificateType}</h3>
    <ul class="dingdan_cl_list">
    	<li>
            <span class="cl_p_1">订单号：</span>
            <p class="cl_p_1">${ebkTask.orderId}　　　　订单状态：${ebkTask.zhOrderStatus}　　　　类型：${ebkTask.ebkCertificate.zhEbkCertificateType }</p></p>
        </li>
     	<li>
			<s:if test="ebkTask.ebkCertificate.ebkCertificateType=='CHANGE'">
				<span>变更说明：</span>
	            <p><b class="c_em">${ebkTask.ebkCertificate.ebkCertificateData.changeInfo}</b></p>
			</s:if>
		</li>
        <li>
            
            <span>出发时间：</span>
            <p>${ebkTask.ebkCertificate.zhVisitTime}</p>
        </li>
        <li>
         
            <span>产品ID：</span>
            <p>${ebkTask.ebkCertificate.mainMetaProductID}</p>
        </li>
        <li>
            
          <span>产品名称：</span>
          <p>${ebkTask.ebkCertificate.mainMetaProductName }</p>
         </li>
        <li>
			<table class="table-xline table-thdcenter">
			    <tr>
					<th>类别</th>
					<th>份数</th>
					<th>成人数</th>
					<th>儿童数</th>
					<th>结算单价</th>
				</tr>
				
				<s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.items" id="item">
					<tr>
						<td>${item.baseInfo.metaBranchName}</td>
						<td>${item.baseInfo.quantity}</td>
						<td>${item.baseInfo.adultQuantity}</td>
						<td>${item.baseInfo.childQuantity}</td>
						<td>${item.baseInfo.settlementPrice}</td>		
					</tr>
				</s:iterator>
				<tr>
					<td colspan="5" class="tr"><b>结算总价：</b> <b class="orange"><s:property value="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.totalSettlementPrice"/></b>元</td>
				</tr>
			</table>
			<table class="table-xline table-thdcenter">
			    <tr>
					<th>游玩人姓名</th>
					<th>手机号</th>
					<th>证件类型</th>
					<th>证件号码</th>
				</tr>
				<s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.certificate.travellerList" id="person">
				<tr>
					<td>${person.name}</td>				
					<td>${person.mobile}</td>
					<td>${person.zhCertType}</td>
					<td>${person.certNo}</td>
				</tr>
				</s:iterator>
			</table>
		</li>
		<li><span>特殊要求：</span>
			<p class="width_800">
	           	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">
	           		${ebkTask.ebkCertificate.allUserMemo }
	           	</s:if>
	           	<s:else>无</s:else>
			</p>
			</li>
        <li class="li_hr"></li>
        <li>
        	<span>确认结果：</span>
        	
        	<s:if test="ebkTask.ebkCertificate.CertificateStatus == 'REJECT'">
        	    <p class="red bold">${ebkTask.ebkCertificate.zhCertificateTypeStatus }</p>
        	 </s:if>
        	 <s:else>
        	    <p class="green bold">${ebkTask.ebkCertificate.zhCertificateTypeStatus}</p>
        	 </s:else>

        </li>
		<li>
        	<span>确认人：</span>
			<p>${ebkTask.confirmUser }</p>
        </li>
		<li>
        	<span>确认时间：</span>
			<p>${ebkTask.zhConfirmTime }</p>
        </li>
        <s:if test="ebkTask.ebkCertificate.ebkCertificateType=='ENQUIRY'">
        	<s:if test="ebkTask.ebkCertificate.certificateStatus=='ACCEPT'">
				<li>
		        	<span>支付等待时间：</span>
					<p>${ebkTask.ebkCertificate.zhRetentionTime }</p>
		        </li>
	        </s:if>
        </s:if>
		<li>
        	<span>供应商备注：</span>
			<p>${ebkTask.ebkCertificate.memo }</p>
        </li>
    </ul>
</div>
</div>
</body>
</html>