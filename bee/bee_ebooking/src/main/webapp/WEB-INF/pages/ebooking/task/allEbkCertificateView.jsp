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
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://e.lvmama.com/ebooking/js/base/jquery.form.js"></script>
</head>
<body id="body_ddgl">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>订单处理</li>
    	<li>线路订单处理</li>
    </ul>
</div><!--以上是公用部分-->
      
<!--订单详情-->
<div class="dingdan_cl" id="print_content">
	<h3>查看${ebkTask.ebkCertificate.zhEbkCertificateType}</h3>
    <ul class="dingdan_cl_list">
    	<li>
            <span class="cl_p_1">订单号：</span>
            <p class="cl_p_1">
            <s:if test="ebkTask != null">
            ${ebkTask.orderId}　　　　订单状态：${ebkTask.zhOrderStatus}　　　　类型：${ebkTask.ebkCertificate.zhEbkCertificateType }
            </s:if>
            <s:else>
            无订单
            </s:else>
            </p>
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
					<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
					<th>结算单价</th>
					</s:if>
				</tr>
				
				<s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.items" id="item">
					<tr>
						<td><s:property value="#item.baseInfo.metaBranchName"/></td>
						<td><s:property value="#item.baseInfo.quantity"/></td>
						<td><s:property value="#item.baseInfo.adultQuantity"/></td>
						<td><s:property value="#item.baseInfo.childQuantity"/></td>
						<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
						<td><s:property value="#item.baseInfo.settlementPrice"/></td>		
						</s:if>
					</tr>
				</s:iterator>
				<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
				<tr>
					<td colspan="5" class="tr"><b>结算总价：</b> <b class="orange"><s:property value="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.totalSettlementPrice"/></b>元</td>
				</tr>
				</s:if>
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
        <li>
        	<span>特殊要求：</span>
            <p class="width_800">
            	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">${ebkTask.ebkCertificate.allUserMemo }</s:if>
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
	<span class="print">
	<a id="print" target="_blank" href="${contextPath }/ebooking/task/showCert.do?certificateId=${ebkTask.ebkCertificate.ebkCertificateId}" title="打印">打印订单</a>
	</span>
</div>
<!--公用底部-->
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>