<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
</head>
<body>
<form method="post" action="${basePath}/gateway/payment_gateway!query.do" id="gatewayForm">
<div>
<div class="main2">
<div class="table_box" id=tags_content_1>
<div class="mrtit3">
<table border="0" cellspacing="0" cellpadding="0" class="search_table">
	<tr>
		<td >网关CODE：<input type="text" name="paymentGatewayModel.paramMap['gatewayLike']" value="${paymentGatewayModel.paramMap['gatewayLike']}" /></td>
		<td >网关名称：<input type="text" name="paymentGatewayModel.paramMap['gatewayNameLike']" value="${paymentGatewayModel.paramMap['gatewayNameLike']}" /></td>
		<td >是否允许退款：
		<select name="paymentGatewayModel.paramMap['isAllowRefund']">
			<option value="">全部</option>
			<s:iterator var="paymentGatewayIsAllowRefund" value="paymentGatewayModel.paymentGatewayIsAllowRefund">
				<option value="${paymentGatewayIsAllowRefund.code}" <s:if test="paymentGatewayModel.paramMap['isAllowRefund']==#paymentGatewayIsAllowRefund.code">selected="selected"</s:if>>${paymentGatewayIsAllowRefund.cnName}</option>
			</s:iterator>
		</select>
		</td>
	</tr>
 	<tr>	
		<td >退款网关CODE：<input type="text" name="paymentGatewayModel.paramMap['refundGatewayLike']" value="${paymentGatewayModel.paramMap['refundGatewayLike']}" /></td>
		<td >网关状态：
		<select name="paymentGatewayModel.paramMap['gatewayStatus']">
			<option value="">全部</option>
			<s:iterator var="paymentGatewayStatus" value="paymentGatewayModel.paymentGatewayStatus">
				<option value="${paymentGatewayStatus.code}" <s:if test="paymentGatewayModel.paramMap['gatewayStatus']==#paymentGatewayStatus.code">selected="selected"</s:if>>${paymentGatewayStatus.cnName}</option>
			</s:iterator>
		</select>
		</td>
		<td >网关类型：
		<select name="paymentGatewayModel.paramMap['gatewayType']">
			<option value="">全部</option>
			<s:iterator var="paymentGatewayType" value="paymentGatewayModel.paymentGatewayType">
				<option value="${paymentGatewayType.code}" <s:if test="paymentGatewayModel.paramMap['gatewayType']==#paymentGatewayType.code">selected="selected"</s:if>>${paymentGatewayType.cnName}</option>
			</s:iterator>
		</select>
		</td>
		<td>
			<input type="hidden" name="paymentGatewayModel.paramMap['orderby']" value="CREATE_TIME"/>
			<input type="hidden" name="paymentGatewayModel.paramMap['order']" value="DESC"/>
		</td>
		<td>
			<input name="right_button08Submit" type="submit" value="查询" class="button" />&nbsp;&nbsp;
			<input name="right_button08Submit" type="button" value="新增" class="button" id="gatewayAdd"/>
		</td>
	</tr>
</table>
</form>
</div>
<br />
<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
	
		<tr>
			<th width="7%">网关CODE</th>
			<th width="7%">网关名称</th>
			<th width="7%">是否允许退款</th>
			<th width="7%">退款网关CODE</th>
			<th width="7%">网关状态</th>
			<th width="7%">网关类型</th>
			<th width="7%">退款顺序</th>
			<th width="7%">创建时间</th>
			<th width="7%">操作</th>
		</tr>
		<s:iterator value="paymentGatewayModel.payPaymentGatewayList" var="payPaymentGateway">
			<tr bgcolor="#ffffff">
				<td>
					${payPaymentGateway.gateway}
				</td>
				<td>${payPaymentGateway.gatewayName}</td>
				<td>
					<s:iterator var="paymentGatewayIsAllowRefund" value="paymentGatewayModel.paymentGatewayIsAllowRefund">
						<s:if test="#paymentGatewayIsAllowRefund.code==#payPaymentGateway.isAllowRefund">
							${paymentGatewayIsAllowRefund.cnName}
						</s:if>
					</s:iterator>
				</td>
				<td>${payPaymentGateway.refundGateway}</td>
				<td>
					<s:iterator var="paymentGatewayStatus" value="paymentGatewayModel.paymentGatewayStatus">
						<s:if test="#paymentGatewayStatus.code==#payPaymentGateway.gatewayStatus">
							${paymentGatewayStatus.cnName}
						</s:if>
					</s:iterator>
				</td>
				<td>
					<s:iterator var="paymentGatewayType" value="paymentGatewayModel.paymentGatewayType">
						<s:if test="#paymentGatewayType.code==#payPaymentGateway.gatewayType">
							${paymentGatewayType.cnName}
						</s:if>
					</s:iterator>
				</td>
				<td>
					${payPaymentGateway.refundOrder}
				</td>
				<td>
					<s:date name="#payPaymentGateway.createTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<s:if test="#payPaymentGateway.refundGateway=='CASH_ACCOUNT' || #payPaymentGateway.refundGateway=='CASH_BONUS'">
						<a href="#" onclick="javascript:openModify('${payPaymentGateway.paymentGatewayId}');">修改</a>
					</s:if>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
</table>
</div>
</div>
</div>


<div id="gatewayDiv"/>
</body>
<script type="text/javascript">
	$("#gatewayAdd").click(function (){
		$("#gatewayDiv").showWindow({
			url:"${basePath}/gateway/payment_gateway!load.do",
			data:{'paymentGatewayModel.target':'add'}
		});
	})
	function openModify(paymentGatewayId){
		$("#gatewayDiv").showWindow({
			url:"${basePath}/gateway/payment_gateway!openModify.do",
			data:{'paymentGatewayModel.paymentGatewayId':paymentGatewayId}
		});
	}
</script>
</html>
