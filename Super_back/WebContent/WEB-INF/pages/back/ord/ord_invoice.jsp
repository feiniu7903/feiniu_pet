<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>	 
	</head>
	

	<body>
		<strong>发票信息</strong>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
					<s:if test="isEditable()">
					<td>&nbsp;</td>
					</s:if>
					<td height="30">
						发票抬头
					</td>
					<td>订单号列表</td>
					<td>
						发票内容
					</td>
					<td>
						发票金额
					</td>
					<td>
						发票备注
					</td>
					<td>
						状态
					</td>
					<td>
						送货方式
					</td>
					<td>
						快递号
					</td>
					<td>
						操作
					</td>
				</tr>
				<s:iterator value="invoiceList" id="invoice">
					<tr bgcolor="#ffffff" align="center">
						<input type="hidden" name="invoiceRadio" />
						<s:if test="isEditable()">
						<td><s:if test="#invoice.status=='UNBILL'"><input type="radio" name="invoiceId" value="${invoice.invoiceId}"/></s:if></td>
						</s:if>
						
						<td height="25">
							${invoice.title }
						</td>
						<td>
							${invoice.orderids}
						</td>
						<td>
							${invoice.zhDetail }
						</td>
						<td>
							<s:if test="#invoice.invoiceId<0">以订单金额为准</s:if><s:else>${invoice.amountYuan }</s:else>
						</td>
						<td>
							${invoice.memo }
						</td>
						<td>
							${invoice.zhStatus}
						</td>
						<td>
							${invoice.zhDeliveryType}
						</td>
						<td>
							${invoice.expressNo}
						</td>
						<td>
							<s:if test="#invoice.invoiceId<0">
								<a href="javascript:showEditInvoiceDialg('${invoice.invoiceId }');">修改</a>
								<a href="javascript:deleteInvoice('${invoice.invoiceId }')">删除</a>
							</s:if>
							<s:else>
								<s:if test="#invoice.status=='UNBILL'">
								<a href="javascript:cancelInvoice('${invoice.invoiceId}')">取消</a>
								</s:if>
								<s:if test="(#invoice.status=='APPROVE' || #invoice.status=='BILLED')&&#invoice.redFlag!='true'">
								<a href="javascript:reqRedInvoice('${invoice.invoiceId}')">申请红冲</a>
								</s:if>
								
							</s:else>
							<a href="javascript:openWin('<%=request.getContextPath()%>/log/viewSuperLog.zul?parentId=${invoice.invoiceId}&parentType=ORD_INVOICE',700,400)">查看日志</a>
						</td>
					</tr>
						<s:if test="#invoice.hasNotNullDelivery()">
					<tr>
						<td colspan="9"  bgcolor="#ffffff" style="padding-left:50px;">
							<table width="85%">
								<tr>
									<td>地址</td>
									<td>收件人</td>
									<td>电话</td>
									<td>邮编</td>						
								</tr>
								<tr>
									<td>${invoice.deliveryAddress.province} ${invoice.deliveryAddress.city} ${invoice.deliveryAddress.address}</td>
									<td>${invoice.deliveryAddress.name}</td>
									<td>${invoice.deliveryAddress.tel}  ${invoice.deliveryAddress.mobile}</td>
									<td>${invoice.deliveryAddress.postcode}</td>
								</tr>								
							</table>
						</td>
					</tr>
						</s:if>
				</s:iterator>
			</tbody>
		</table>
		<div class="btn09">
		<%--
			<input type="button" value="新增发票" class="right-button08"
				name="passed7" onclick="showAddInvoiceDialg();">
		 --%>请从“订单管理--发票申请”当中添加发票
		</div>
	</body>
</html>
