<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		function closeAddInvoiceDialg(){
			$("select").each(function(){$(this).show();});
			$('#addInvoiceDialg').hide();
		}
		function closeEditInvoiceDialg(){
			$("select").each(function(){$(this).show();});
			$('#editInvoiceDialg').hide();
		}
		</script>
	</head>

	<body>
		<div class="view-window" style="display: ">
			<form id="addOrUpdateInvoiceForm">
				<input type="hidden" name="ordInvoice.orderId" value="${orderId }" />
				<input type="hidden" name="ordInvoice.invoiceId"
					value="${ordInvoice.invoiceId }" />
				<table width="100%" class="receipt">
					<tbody>
						<tr>
							<td>
								发票抬头：
								<input name="ordInvoice.title" type="text" id="title"
									value="${ordInvoice.title }" />
							</td>
							<td>
								发票内容：
								<select name="ordInvoice.detail" id="detail"
									value="${ordInvoice.detail }">
									<s:iterator value="invoiceDetails" id="detail">
										<option value="${detail.code }">
											${detail.name }
										</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								发票金额：以订单实际金额为准<%--
								<input name="ordInvoice.amountYuan" type="text" id="amount"
									value="${ordInvoice.amountYuan }" />
									 --%>
							</td>
							<td>
								发票备注：
								<input name="ordInvoice.memo" type="text" id="memo"
									value="${ordInvoice.memo }" />
							</td>
						</tr>
						<tr>
							<td>送货方式:
							<s:select name="ordInvoice.deliveryType" list="deliveryTypeList" listKey="code" listValue="name"></s:select>
							</td>
						</tr>
					</tbody>
				</table>
				<p class="viewwindowp">
					<s:if test="ordInvoice.invoiceId==null">
						<input type="button" name="btnSaveInvoice" class="button" value="保存"
							onclick="addInvoice();">
							&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="关闭" name="closebtn"
							onclick="closeAddInvoiceDialg();">
					</s:if>
					<s:else>
						<input type="button" class="button" value="保存"
							onclick="editInvoice();">
						&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="关闭" name="closebtn"
							onclick="closeEditInvoiceDialg();">
					</s:else>

				</p>
			</form>
		</div>
	</body>
</html>
