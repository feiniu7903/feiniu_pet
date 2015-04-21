<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<c:if test="${orderDetail.needInvoice=='true'}">
	<strong>发票信息</strong>
	<table style="font-size: 12px" cellspacing="1" cellpadding="4"
		border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
		<tr bgcolor="#f4f4f4" align="center">
			<td height="30">
				发票抬头
			</td>
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
				发票号
			</td>
			<td>
				送货方式
			</td>
			<td>快递单号</td>
			<td>&nbsp;</td>
		</tr>
		<c:forEach var="invoice" items="invoiceList">
			<tr bgcolor="#ffffff" align="center">
				<td height="25">
					${invoice.title }
				</td>
				<td>
					${invoice.zhDetail }
				</td>
				<td>
					${invoice.amountYuan}
				</td>
				<td>
					${invoice.memo }
				</td>
				<td>
					${invoice.zhStatus}
				</td>
				<td>
					${invoice.invoiceNo}
				</td>
				<td>
					${invoice.zhDeliveryType}
				</td>
				<td>
					${invoice.expressNo}
				</td>
				<td>
					<a href="javascript:openWin('<%=request.getContextPath()%>/log/viewSuperLog.zul?parentId=${invoice.invoiceId}&parentType=ORD_INVOICE',700,400)">查看日志</a>
				</td>
			</tr>
			<c:if test="${invoice.hasNotNullDelivery}">
				<tr>
					<td colspan="8" bgcolor="#ffffff" style="padding-left: 50px;">
						<table width="85%">
							<tr>
								<td>
									地址
								</td>
								<td>
									收件人
								</td>
								<td>
									电话
								</td>
								<td>
									邮编
								</td>
							</tr>
							<tr>
								<td>
									${invoice.deliveryAddress.getFullAddress}									
								</td>
								<td>
									${invoice.deliveryAddress.name}
								</td>
								<td>
									${invoice.deliveryAddress.tel}
									${invoice.deliveryAddress.mobile}
								</td>
								<td>
									${invoice.deliveryAddress.postcode}
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</c:if>