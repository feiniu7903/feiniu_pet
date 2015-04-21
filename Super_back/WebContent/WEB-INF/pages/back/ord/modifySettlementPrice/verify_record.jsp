<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>修改结算价审核列表</title>

		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<%@ include file="/WEB-INF/pages/back/base/timepicker.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ord/verify_record.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<div class="main02 wrap">
			<div class="row_ul">
				<form action="<%=basePath%>ord/queryVerifyList.do" method="post" id="search_form">
					<table border="0" cellspacing="0" cellpadding="0" class="newInput" width="100%">
						<tr>
							<td height="30" align="right">
								<label>订单号：</label>
							</td>
							<td height="30">
								<input id="ordIdInput" name="orderId" type="text" class="input_text02 table_input_style" maxlength="10"
									value="<s:property value="orderId"/>" />
							</td>
							<td colspan="3">
								<input id="searchBtn" type="button" class="right-button08" value="查 询" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="row2">
				<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
					<tr bgcolor="#eeeeee">
						<td>订单号</td>
						<td>供应商ID</td>
						<td>供应商名称</td>
						<td>销售产品ID</td>
						<td>销售产品名称</td>
						<td>采购产品ID</td>
						<td>采购产品名称</td>
						<td>采购产品类别</td>
						<td>打包数量</td>
						<td>购买数量</td>
						<td>总数</td>
						<td>游玩时间</td>
						<td>结算单价</td>
						<td>结算总价</td>
						<td>修改时间</td>
						<td>修改人</td>
						<td>修改后的结算单价</td>
						<td>修改后的结算总价</td>
						<td>修改结果</td>
						<td>修改类型</td>
						<td>修改原因</td>
						<td>备注</td>
						<td width="100px">操作</td>
					</tr>
					<s:iterator value="pagination.items" var="product">
						<tr id="tr_<s:property value="productId"/>" bgcolor="#ffffff">
							<td>
								<s:property value="orderId" />
							</td>
							<td>
								<s:property value="supplierId" />
							</td>
							<td>
								<s:property value="supplierName" />
							</td>
							<td>
								<s:property value="productId" />
							</td>
							<td>
								<s:property value="productName" />
							</td>
							<td>
								<s:property value="metaProductId" />
							</td>
							<td>
								<s:property value="metaProductName" />
							</td>
							<td>
								<s:property value="branchName" />
							</td>
							<td>
								<s:property value="productQuantity" />
							</td>
							<td>
								<s:property value="quantity" />
							</td>
							<td>
								<s:property value="totalQuantity" />
							</td>
							<td>
								<s:date name="visitTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:property value="settlementPriceYuan" />
							</td>
							<td>
								<s:property value="totalSettlementPriceYuan" />
							</td>
							<td>
								<s:property value="modifyDateStr" />
							</td>
							<td>
								<s:property value="operatorName" />
							</td>
							<td>
								<s:property value="modifyActualSettlementPriceYuan" />
							</td>
							<td>
								<s:property value="modifyTotalSettlementPriceYuan" />
							</td>
							<td>
								<s:property value="zhChangeResult" />
							</td>
							<td>
								<s:property value="zhChangeType" />
							</td>
							<td>
								<s:property value="zhReason" />
							</td>
							<td style="word-break:break-all;">
								<s:property value="remark" />
							</td>
							<td>
								<a href="javascript:void(0);" id="<s:property value="recordId" />" onClick="doVerified(this, 'VERIFIED')">审核通过</a>
								<a href="javascript:void(0);" id="<s:property value="recordId" />" onClick="doVerified(this, 'REJECTED')">驳回</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<table width="100%" border="0" align="center">
				<tr>
					<td>总条数：<s:property value="pagination.totalResultSize" /></td>
					<td  align="right">
						<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
					</td>
				  </tr>
			</table>
		</div>
	</body>
</html>