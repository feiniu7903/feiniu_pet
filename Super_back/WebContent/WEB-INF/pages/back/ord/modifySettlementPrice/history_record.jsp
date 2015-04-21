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
		<title>历史记录</title>

		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<%@ include file="/WEB-INF/pages/back/base/timepicker.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ord/history_record.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<div class="main02 wrap">
			<div class="row_ul">
				<form grid_action="<%=basePath%>ord/queryHistoryRecordList.do" method="post" id="search_form" excel_action="<%=basePath%>ord/exportHistoryRecordList.do">
					<table border="0" cellspacing="0" cellpadding="0" class="newInput" width="100%">
						<tr>
							<td height="30" align="right">
								<label>采购产品：</label>
							</td>
							<td height="30">
								<input id="metaProductInput" name="productName" autocomplete="off" type="text" class="input_text02 table_input_style"
									value="<s:property value="productName"/>" />
								<input type="hidden" name="metaProductId" id="metaProductId" value="<s:property value="metaProductId"/>"/>
								<select id="metaBranchTypeSelect" name="productBranchId" class="select_option" style="width: 100px;"
									branchId="<s:property value="productBranchId"/>" >
									<option value="">采购产品类别</option>
								</select>&nbsp;&nbsp; 
							</td>
							<td height="30" align="right">
								<label>结算对象：</label>
							</td>
							<td height="30">
								<input id="settlementTargetInput" name="targetName" autocomplete="off" type="text" class="input_text02 table_input_style"
									value="<s:property value="targetName"/>" /> 
								<input type="hidden" name="targetId" id="comTargetId" value="<s:property value="targetId"/>"/>
							</td>
							<td height="30" align="right">
								<label>修改时间：</label>
							</td>
							<td height="30">
								<input id="modifyDateBegin" name="modifyDateBegin" type="text" class="input_text01 Wdate" onclick="WdatePicker()"
									value="<s:property value="modifyDateBegin"/>" /> ~
								<input id="modifyDateEnd" name="modifyDateEnd" type="text" class="input_text01 Wdate" onclick="WdatePicker()"
									value="<s:property value="modifyDateEnd"/>" />
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								<label> 供应商：</label>
							</td>
							<td height="30">
								<input id="supplierInput" name="supplierName" type="text" autocomplete="off" class="input_text02 table_input_style"
									value="<s:property value="supplierName"/>" />
								<input type="hidden" name="supplierId" id="comSupplierId" value="<s:property value="supplierId"/>"/>
							</td>
							<td height="30" align="right">
								<label class="data_list">修改结果：</label>
							</td>
							<td height="30">
								<select name="operateType" >
									<option value="">请选择</option>
									<s:iterator value="changeResultList" id="item">
										<option value="${item.code}" <s:if test="%{operateType == code}">selected</s:if>>${item.cnName}</option>
									</s:iterator>
								</select>
							</td>
							<td height="30" align="right">
								<label class="data_list">游玩时间：</label>
							</td>
							<td height="30">
								<input id="visitDateStart" name="visitDateStart" type="text" class="input_text01 Wdate" onclick="WdatePicker()"
									value="<s:property value="visitDateStart"/>"  /> 
								~ 
								<input id="visitDateEnd" name="visitDateEnd" type="text" class="input_text01 Wdate" onclick="WdatePicker()"
									value="<s:property value="visitDateEnd"/>"  />
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								<label>订单号：</label>
							</td>
							<td height="30">
								<input id="ordIdInput" name="orderId" type="text" class="input_text02 table_input_style" maxlength="10"
									value="<s:property value="orderId"/>" />
							</td>
							<td height="30" align="right">
								<label>修改原因：</label>
							</td>
							<td height="30">
								<select name="reason" >
									<option value="">请选择</option>
									<s:iterator value="resultList" id="item">
										<option value="${item.code}" <s:if test="%{reason == code}">selected</s:if>>${item.cnName}</option>
									</s:iterator>
								</select>
							</td>
							<td>
							</td>
							<td>
								<s:checkboxlist name="changeType" list="#{'UNIT_PRICE':'修改单价','TOTAL_PRICE':'修改总价'}" />
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<s:checkboxlist name="settlementPay" list="#{'1':'付款后修改','0':'付款前修改'}" />
							</td>
							<td colspan="3">
								<input id="searchBtn" type="button" class="right-button08" value="查 询" />
								<input id="exportBtn" type="button" class="right-button08" value="导出Excel" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="row2">
				<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="1500px" class="newfont06"
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
						<td>审核人</td>
						<td>修改后的结算单价</td>
						<td>修改后的结算总价</td>
						<td>修改结果</td>
						<td>修改类型</td>
						<td>修改原因</td>
						<td>备注</td>
					</tr>
					<s:iterator value="pagination.items" var="product">
						<tr bgcolor="#ffffff">
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
								<s:property value="settlementPriceYuanStr" />
							</td>
							<td>
								<s:property value="totalSettlementPriceYuanStr" />
							</td>
							<td>
								<s:property value="modifyDateStr" />
							</td>
							<td>
								<s:property value="operatorName" />
							</td>
							<td>
								<s:property value="verifiedOperator" />
							</td>
							<td>
								<s:property value="modifyActualSettlementPriceYuanStr" />
							</td>
							<td>
								<s:property value="modifyTotalSettlementPriceYuanStr" />
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
						</tr>
					</s:iterator>
				</table>
			</div>
			<table width="1500px" border="0" align="center">
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