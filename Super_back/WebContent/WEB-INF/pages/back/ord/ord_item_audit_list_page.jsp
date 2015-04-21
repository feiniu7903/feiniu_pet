<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<s:iterator var="item" value="auditOrderMetaList">
	<tr bgcolor="#ffffff">
		<td>
			<s:if test="#item.ordOrder.hasApproveAble()">
				<input type="checkbox" name="checkname"
					value="${orderItemMetaId}"></input>
			</s:if>	
			<s:else>
				必须预授权才可统单
			</s:else>													
		</td>
		<td height="30">
			<a href="javascript:openWin('/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=${orderId }',700,700)">${orderId}</a>
			<s:if test="supplierFlag=='true' && (productType=='HOTEL' or productType=='ROUTE')">
				<span style="color: red;">(EBK订单)</span>
			</s:if>
		</td>
		<td height="30">
			${orderItemMetaId}
		</td>
		<td>
			<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${metaProductId}&metaBranchId=${metaBranchId}&productType=${productType }',700,700)">${productName }</a>
		</td>
		<td>
			<s:if test='#item.productType=="HOTEL"'>
				${item.hotelQuantity }
			</s:if>
			<s:else>
				<s:property value="productQuantity*quantity"/>
			</s:else>
		</td>
		<td>
			${ordOrder.userName }
		</td>
		<td>
			${ordOrder.contact.name }
		</td>
		<td>
			<s:date name="ordOrder.createTime" format="yyyy-MM-dd HH:mm:ss" />
		</td>
		<td>
			<s:date name="ordOrder.dealTime" format="yyyy-MM-dd HH:mm:ss" />
		</td>
		<td>
			<s:date name="visitTime" format="yyyy-MM-dd HH:mm:ss" />
		</td>
		<td>
			<s:property value="retentionTime"/>
		</td>
		<td>
			<s:if test="resourceStatus=='BEFOLLOWUP'">
				<span style="color: red;" > 待跟进</span>
			</s:if>
			<s:else>
				${zhResourceStatus }
			</s:else>
		</td>
		<td>
		<s:if test="supplierFlag=='true'">
			${zhCertificateStatus}
		</s:if>
		</td>
		<td>
			<input type="button" name="btnAuditOrd" value="审 核" class="right-button02"
				onclick="javascript:showDetailDiv('approveDiv', ${orderId }, ${orderItemMetaId})">
			<s:if test="resourceStatus!='BEFOLLOWUP'">
				<input type="button" name="btnItemRefundOrder" value="退 单" class="right-button02"
					onclick="javascript:window.location='<%=basePath%>ordItem/doCancelOrderItem.do?id=${orderItemMetaId}&productType=${productType}&tab=2&permId=${permId}';">
			</s:if>
			<s:if test="supplierFlag=='true'">
			<s:if test="certificateStatus=='REJECT'">
					<input type="button" name="btnItemResendOrder" value="重发订单" class="right-button08" 
						onclick="return reSendOrder('${orderItemMetaId}')">
					<s:if test="ebkCertificateType!='ENQUIRY' && resourceStatus=='AMPLE'">
						<input type="button" name="btnWasteOrder" value="废单(单笔订单监控)" class="right-button09"
							onclick="window.location.href='<%=basePath%>ord/order_monitor_list.do?pageType=single&orderId=${orderId}';">
					</s:if>
			</s:if>
			</s:if>
		</td>
	</tr>
</s:iterator>
