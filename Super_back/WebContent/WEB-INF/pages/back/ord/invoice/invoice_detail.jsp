<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>发票列表</title>		
	</head>
	<body>
		<div class="orderpoptit">
			<strong>发票详细：</strong>
			<p class="inputbtn">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('invoiceDetailDiv');">
			</p>
		</div>
		<div class="orderpopmain">
			<table style="font-size: 12px" width="100%" border="0" id="orderTable"
				class="contactlist">
				<tr>
					<td colspan="3">发票主体：<span id="td_title">${ordInvoice.title}</span></td>
				</tr>
				<tr >
					<td width="33%" id="td_invoice">
						发票ID：${ordInvoice.invoiceId}
					</td>
					<td width="33%">
						发票号：${ordInvoice.invoiceNo}
					</td>
					<td width="33%" >					
						发票金额：<span id="td_price">${ordInvoice.amountYuan}</span>
					</td>
				</tr>
				<tr>
					<td>发票内容：<span id="td_detail">${ordInvoice.zhDetail}</span></td>
					<td>发票状态：${ordInvoice.zhStatus}</td>
					<td>快递单号：${ordInvoice.expressNo}&nbsp;&nbsp;送货方式:${ordInvoice.zhDeliveryType}&nbsp;&nbsp;快递状态：${ordInvoice.zhLogisticsStatus}</td>					
				</tr>
				<tr>
					<td>申请时间：<s:date name="ordInvoice.createTime" format="yyyy-MM-dd HH:mm"/></td>
					<td>出票时间：<s:date name="ordInvoice.billDate" format="yyyy-MM-dd HH:mm"/></td>
					<td colspan="3">送货方式：${ordInvoice.zhDeliveryType}</td>
				</tr>
				<tr>
					<td colspan="3">
						备注：<br/>
						<div id="td_memo">
						${ordInvoice.memo}
						</div>
					</td>
				</tr>
			</table>
			<div>
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont06">
					<tbody>
						<tr class="CTitle">
							<td height="20" align="center" style="font-size: 14px;"
								colspan="6">
								订单列表
							</td>
						</tr>
						<tr bgcolor="#eeeeee">
							<td>订单号</td>
							<td>产品名称</td>
							<td>游玩时间</td>
							<td>应付金额</td>
							<td>奖金支付金额</td>
							<td>去除奖金部分实付金额</td>
						</tr>
						<s:iterator value="ordInvoice.invoiceRelationList" var="r">
						<tr bgcolor="#ffffff">
							<td>${r.orderId}</td>
							<td>
								<s:iterator value="#r.order.ordOrderItemProds">
								<div><s:property value="productName"/> x <s:property value="quantity"/></div>
								</s:iterator>
							</td>
							<td><s:date name="#r.order.visitTime" format="yyyy-MM-dd"/></td>
							<td>${r.order.oughtPayFloat}</td>
							<td>${r.order.bonusPaidAmountYuan}</td>
							<td>${r.order.actualPayExcludeBonusPaidAmountYuan}</td>
						</tr>
						</s:iterator>
					</tbody>
			</table>
			</div>
			<div style="margin-top:20px;">			 
			 <s:if test="ordInvoice.deliveryType!='SELF'">
			 <s:if test="ordInvoice.deliveryAddress==null">
			 <h3 style="color:red">未添加送货地址</h3>
			 </s:if>
			 <s:else>
			 <table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
			 	<tr>
			 		<td bgcolor="#eeeeee" width="100">收件人:</td><td bgcolor="#ffffff">${ordInvoice.deliveryAddress.name}</td>
			 	</tr>
			 	<tr>
			 		<td bgcolor="#eeeeee" >手机号码:</td><td bgcolor="#ffffff">${ordInvoice.deliveryAddress.mobile}</td>
			 	</tr>
			 	
			 	<tr>
			 		<td bgcolor="#eeeeee" >地址:</td><td bgcolor="#ffffff">${ordInvoice.deliveryAddress.province} ${ordInvoice.deliveryAddress.city} ${ordInvoice.deliveryAddress.address}</td>
			 	</tr>
			 	<tr>
			 		<td bgcolor="#eeeeee" >邮编:</td><td bgcolor="#ffffff">${ordInvoice.deliveryAddress.postcode}</td>
			 	</tr>
			 </table>
			 </s:else>
			 </s:if>
			</div>
			<s:if test="showInvoiceForm">
			<div>
				<form onsubmit="return false" id="invoiceForm">变更发票号<input type="hidden" name="invoiceId" value="${ordInvoice.invoiceId}"/>
					<table>
						<tr>
							<td>发票号:</td><td><input type="text" name="invoiceNo"/></td><td><input type="button" class="invoice_input" value="更新"/></td>
						</tr>
					</table>
				</form>
			</div>
			</s:if>
			<s:if test="ordInvoice.status=='BILLED'">
			<div>
				<form onsubmit="return false" id="expressForm"><input type="hidden" name="invoiceId" value="${ordInvoice.invoiceId}"/>
					<table>
						<tr>
							<td>快递单号:</td><td><input type="text" name="expressNo"/></td><td><input type="button" class="express_input" value="更新"/></td>
						</tr>
					</table>
				</form>
			</div>
			</s:if>
			<div style="margin:10px 20px;text-align: center;">
			<s:if test="ordInvoice.status=='UNBILL'">
				<button class="change_curr_status" invoiceId="${ordInvoice.invoiceId}" ok_status="APPROVE">审核通过</button>
			</s:if>
			<%--
			<s:if test="ordInvoice.status=='BILLED'">
				<button class="change_curr_status" ok_status="POST" invoiceId="${ordInvoice.invoiceId}">已邮寄</button>
			</s:if>
			 --%>
			<s:if test="ordInvoice.status!='COMPLETE'&&ordInvoice.status!='CANCEL' && ordInvoice.status!='UNBILL'">
				<s:if test="ordInvoice.status!='BILLED'&& ordInvoice.status!='POST'">
				<button class="printInvoiceBtn" invoiceId="${ordInvoice.invoiceId}">打印发票</button>
				</s:if>
				<button class="change_curr_status" ok_status="COMPLETE" invoiceId="${ordInvoice.invoiceId}">完成</button>
			</s:if>
			
		
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30">
							日志名称
						</td>
						<td>
							内容
						</td>
						<td>
							操作人
						</td>
						<td>
							创建时间
						</td>
						<td>
							备注
						</td>
					</tr>
					<s:iterator value="comLogList" id="log">
						<tr bgcolor="#ffffff" align="center">
							<td height="25">
								${log.logName }
							</td>
							<td>
								${log.content }
							<s:if test="#log.logType=='cancelToCreateNew_new'">
							老订单ID${log.parentId}
							</s:if>
							<s:if test="#log.logType=='cancelToCreateNew_original'">
							新订单ID${log.parentId}
							</s:if>
							</td>
							<td>
								${log.operatorName }
							</td>
							<td>
								<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${log.memo }
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<object id="invoiceActiveX" classid="clsid:B7ED28F2-4843-4A57-98B0-52045508D6BD" style="display:none"></object>
		</div>
	</body>
</html>