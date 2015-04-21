<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>反查询信息</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<body>
<div style="margin:20px;">
	<h3>做账信息</h3>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table" id="query_fin_gl_interface_id">
		<tr>
			<th width="20" align="center">票号</th>
			<th width="20" align="center">批量合并号 </th>
			<th width="20" align="center">制单日期(支付)</th>
			<th width="20" align="center">摘要</th>
			<th width="20" align="center">借方科目编码(支付平台)</th>
			<th width="20" align="center">借方金额</th>
			<th width="20" align="center">贷方科目编码</th>
			<th width="20" align="center">贷方金额</th>
			<th width="20" align="center">产品编码</th>
			<th width="30" align="center">产品名称</th>
			<th width="30" align="center">供应商ID</th>
			<th width="20" align="center">自定义项1(订单号)</th>
			<th width="100" align="center">自定义项10(团号)</th>
			<th width="20" align="center">自定义项4(游玩时间)</th>
			<th width="20" align="center">状态</th>
			<th width="20" align="center">备注</th>
			<th width="10" align="center">凭证类型</th>
			<th width="10" align="center">帐套号</th>
			<th width="15" align="center">U8凭证号</th>
		</tr>	
		<tr>
			<td>${finGLInterface.tickedNo}</td>
			<td>${finGLInterface.batchNo}</td>
			<td>
				<s:if test="finGLInterface.makeBillTime!=null">
					<s:date name="finGLInterface.makeBillTime" format="yyyy-MM-dd"/>
				</s:if>
			</td>
			<td>${finGLInterface.summary}</td>
			<td>${finGLInterface.borrowerSubjectCode}</td>
			<td>${finGLInterface.borrowerAmountFmt}</td>
			<td>${finGLInterface.lenderSubjectCode}</td>
			<td>
				<s:if test="finGLInterface.lenderSubjectCode!=null">
					${finGLInterface.lenderAmountFmt}
				</s:if>
			</td>
			<td>${finGLInterface.productCode}</td>
			<td>${finGLInterface.productName}</td>
			<td>${finGLInterface.supplierCode}</td>
			<td>${finGLInterface.ext1}</td>
			<td>${finGLInterface.ext10}</td>
			<td>${finGLInterface.ext4}</td>
			<td>
				<s:if test="finGLInterface.receivablesStatus!=null">
					<s:if test="finGLInterface.receivablesStatus=='success'">
						入账成功
					</s:if>
					<s:else>
						入账失败
					</s:else>
				</s:if>
				<s:else>
					未发送
				</s:else>
			</td>
			<td>${finGLInterface.receivablesResult}</td>
			<td>${finGLInterface.proofType}</td>
			<td>${finGLInterface.accountBookId}</td>
			<td>${finGLInterface.inoId}</td>				
		</tr>
	</table>
</div>
<div style="margin:20px;">
	<h3>流水记录</h3>
	<table class="gl_table" width="90%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<th>流水号</th>
			<th>我方交易金额(元)</th>
			<th>银行交易金额(元)</th>
			<th>我方交易时间</th>
			<th>银行交易时间</th>
			<th>交易类型</th>
			<th>对账网关</th>
			<th>银行对账日期</th>
			<th>创建时间</th>
			<th>备注</th>
			<th>订单号</th>
			<th>记账状态</th>
			<th>记账时间</th>
			<th>费用类型</th>
			<th>状态</th>
			<th>是否取消</th>
			<th>取消人</th>
			<th>取消日期</th>
			<th>创建人</th>
			<th>关联红字流水</th>
			<th>关联对账结果id</th>
		</tr>
		<s:iterator value="finBizItemList" var="finBizItem">
			<tr bgcolor="#ffffff">
				<td>${finBizItem.bizItemId}</td>
				<td>
				<s:if test="#finBizItem.amount!=null">
					${finBizItem.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>
				<s:if test="#finBizItem.bankAmount!=null">
					${finBizItem.bankAmount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td><s:date name="#finBizItem.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#finBizItem.transactionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.transactionTypeZH}</td>
				<td>${finBizItem.gatewayZH}</td>
				<td><s:date name="#finBizItem.bankReconTime" format="yyyy-MM-dd"/></td>
				<td><s:date name="#finBizItem.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.memo}</td>
				<td>${finBizItem.orderId}</td>
				<td>${finBizItem.glStatusZH}</td>
				<td><s:date name="#finBizItem.glTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.transactionTypeZH}</td>
				<td>${finBizItem.bizStatusZH}</td>
				<td>
					<s:if test='#finBizItem.cancelStatus=="Y"'>是</s:if>
					<s:elseif test='#finBizItem.cancelStatus=="N"'>否</s:elseif>
				</td>
				<td>${finBizItem.cancelUser}</td>
				<td><s:date name="#finBizItem.cancelTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${finBizItem.createUser}</td>
				<td>${finBizItem.bizNo}</td>
				<td>${finBizItem.reconResultId}</td>
			</tr>
		</s:iterator>
	</table>
</div>
<div style="margin:20px;">
	<h3>勾兑记录</h3>
	<table class="gl_table" width="90%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<th>勾兑Id</th>
			<th>订单号</th>
			<th>我方对账流水号</th>
			<th>银行对账流水号</th>
			<th>我方网关交易号</th>
			<th>银行网关交易号</th>
			<th>我方交易金额(元)</th>
			<th>银行交易金额(元)</th>
			<th>我方交易时间</th>
			<th>银行交易时间</th>
			<th>交易类型</th>
			<th>网关名称</th>
			<th>对账状态</th>
			<th>对账结果</th>
			<th>对账日期</th>
			<th>备注</th>
			<th>记账状态</th>
		</tr>
		<s:iterator value="finReconResultList" var="finReconResult">
			<tr bgcolor="#ffffff">
				<td>${finReconResult.reconResultId}</td>
				<td>${finReconResult.orderId}</td>
				<td>${finReconResult.paymentTradeNo}</td>
				
				<td>${finReconResult.bankPaymentTradeNo}</td>
				<td>${finReconResult.gatewayTradeNo}</td>
				<td>${finReconResult.bankGatewayTradeNo}</td>
				<td>
				<s:if test="#finReconResult.amount!=null">
					${finReconResult.amount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td>
				<s:if test="#finReconResult.bankAmount!=null">
					${finReconResult.bankAmount/100}
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td><s:date name="#finReconResult.callbackTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#finReconResult.transactionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				
				
				<td>${finReconResult.transactionTypeZH}</td>
				<td>${finReconResult.gatewayZH}</td>
				
				<td>${finReconResult.reconStatusZH}</td>
				<td>${finReconResult.reconResult}</td>
				<td><s:date name="#finReconResult.bankReconTime" format="yyyy-MM-dd"/></td>
				<td>${finReconResult.memo}</td>
				<td>${finReconResult.glStatusZH}</td>
			</tr>
		</s:iterator>
	</table>
</div>

</body>
</html>