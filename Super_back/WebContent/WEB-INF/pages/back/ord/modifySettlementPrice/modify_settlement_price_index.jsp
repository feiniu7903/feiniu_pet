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
		<title>修改结算价</title>

		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/ord/modify_settlement_price.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ord/modify_settlement_price_common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<div class="main02 wrap">
			<div class="row_ul">
				<form action="<%=basePath%>ord/queryList.do" method="post" id="search_form">
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
									branchId="<s:property value="productBranchId"/>">
									<option value="">采购产品类别</option>
								</select>&nbsp;&nbsp; 
							</td>
							<td height="30" align="right">
								<label class="data_list">游玩日期：</label>
							</td>
							<td height="30">
								<input type="text" name="visitDateStart" id="visitDateStart" class="input_text01 Wdate" onclick="WdatePicker()"
									value="<s:property value="visitDateStart"/>" />
								~ 
								<input type="text" id="visitDateEnd" name="visitDateEnd" class="input_text01 Wdate" onClick="WdatePicker()"
									value="<s:property value="visitDateEnd"/>" />
							</td>
							<td height="30" align="right"><label>下单时间：</label></td>
							<td height="30">
								<input id="createOrderTimeBegin" name="createOrderTimeBegin" type="text" class="input_text01 Wdate" onClick="WdatePicker()"
									value="<s:property value="createOrderTimeBegin"/>" /> ~
								<input id="createOrderTimeEnd" name="createOrderTimeEnd" type="text" class="input_text01 Wdate" onClick="WdatePicker()"
									value="<s:property value="createOrderTimeEnd"/>" />
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
								<label class="data_list">支付时间：</label>
							</td>
							<td height="30">
								<input id="payTimeStart" name="payTimeStart" type="text" class="input_text01 Wdate" onClick="WdatePicker()"
									value="<s:property value="payTimeStart"/>" /> 
								~ 
								<input id="payTimeEnd" name="payTimeEnd" type="text" class="input_text01 Wdate" onClick="WdatePicker()"
									value="<s:property value="payTimeEnd"/>" />
							</td>
							<td height="30" align="right">
								<label> 结算总价： </label>
							</td>
							<td><input type="text" name="totalSettlePriceBegin" value="<s:property value="totalSettlePriceBegin"/>"/>~
							<input type="text" name="totalSettlePriceEnd" value="<s:property value="totalSettlePriceEnd"/>"/></td>
						</tr>
						<tr>
							<td height="30" align="right">
								<label>订单号：</label>
							</td>
							<td height="30">
								<input id="ordIdInput" name="orderId" type="text" class="input_text02 input_combox" maxlength="10"
									value="<s:property value="orderId"/>" />
							</td>
							<td height="30" align="right">
								<label>结算对象：</label>
							</td>
							<td height="30">
								<input id="settlementTargetInput" name="targetName" autocomplete="off" type="text" class="input_text02 table_input_style"
									value="<s:property value="targetName"/>" /> 
								<input type="hidden" name="targetId" id="comTargetId" value="<s:property value="targetId"/>"/>
							</td>
							<td>有退款的订单</td>
							<td><s:select list="#{'':'请选择','Y':'有','N':'无'}"	name="refundment"></s:select></td>
							<td>
								<input id="searchBtn" type="button" class="right-button08" value="查 询" onClick="validateOrderId()" />
								<input id="updateBatchPrice" type="button" class="right-button08" value="批量修改" alt="<%=basePath %>" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<font color='red' size="5">${hasParamMessage}</font>
			<div class="row2">
				<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
					<tr bgcolor="#eeeeee">
						<td align="center" width="30px">
							<input type="checkbox" name="checkall" id="checkall" />
						</td>
						<td>订单号</td>
						<td>结算状态</td>
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
						<td>取票人</td>
						<td>游玩时间</td>
						<td>结算单价</td>
						<td>实际结算价</td>
						<td>结算总价</td>
						<td>审核状态</td>
						<td width="120px">操作</td>
					</tr>
					<s:iterator value="pagination.items" var="product">
						<tr id="tr_<s:property value="productId"/>" bgcolor="#ffffff">
							<td align="center" width="30px">
								<input type="checkbox" name="checkname" id="${metaProductId}"
								 value="<s:property value="orderItemMetaId" />"
								 title="${metaBranchId}" alt="${actualSettlementPriceYuan }" />
							</td>
							<td>
								<s:property value="orderId" />
							</td>
							<td>
								<s:property value="zhSettlementStatus" />
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
								<s:property value="pickTicketPerson" />
							</td>
							<td>
								<s:date name="visitTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:property value="settlementPriceYuanStr" />
							</td>
							<td>
								<s:property value="actualSettlementPriceYuanStr" />
							</td>
							<td>
								<s:property value="totalSettlementPriceYuanStr" />
							</td>
							<td>
								<s:if test="status != 'VERIFIED'">
									<s:property value="zhStatus" />
								</s:if>
							</td>
							<td>
								<s:if test="status != 'UNVERIFIED' ">
									<a href="javascript:void(0);" id="<s:property value="orderItemMetaId"/>" 
										name="<s:property value="actualSettlementPriceYuanStr"/>"
										alt="<s:property value="totalSettlementPriceYuanStr"/>"
										productQuantity="<s:property value="productQuantity"/>"
										quantity="<s:property value="quantity"/>"
										sellPrice="<s:property value="sellPrice"/>"
										title="<s:property value="totalQuantity"/>"
									 	onClick="updatePrice(this, 1, '<%=basePath%>')">修改单价</a>
									<a href="javascript:void(0);" id="<s:property value="orderItemMetaId"/>" 
										name="<s:property value="totalSettlementPriceYuanStr"/>"
										alt="<s:property value="actualSettlementPriceYuanStr"/>"
										title="<s:property value="totalQuantity"/>"
										productQuantity="<s:property value="productQuantity"/>"
										quantity="<s:property value="quantity"/>"
										sellPrice="<s:property value="sellPrice"/>"
										onClick="updatePrice(this, 2, '<%=basePath%>')">修改总价</a>
									<mis:checkPerm permCode="3331" permParentCode="${permId}">
									<a href="javascript:void(0);" onClick="update_travel_group_code_virtual('<%=basePath%>','<s:property value="orderItemMetaId"/>','<s:property value="orderId"/>')" >
										修改结算属性
									</a>
									</mis:checkPerm>
									<mis:checkPerm permCode="3330" permParentCode="${permId}">
									<a href="javascript:void(0);" onClick="create_settle_item_fun('<%=basePath%>','<s:property value="orderItemMetaId"/>')">
										生成结算子项
									</a>
									</mis:checkPerm>
									<mis:checkPerm permCode="3331" permParentCode="${permId}">
									<a href="#log" class="showLogDialog" param="{'objectType':'ORD_SETTLE_VILID_ERROR','objectId':<s:property value="orderItemMetaId"/>}">结算验证日志</a>
									</mis:checkPerm>
								</s:if>
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
		<div id="edit_settlement_div"  style="display:none; width: 400px;">
			<form id="edit_settlement_form" method="post"> 
				<table>
					<tr height="50">
						<td align="right">团号：</td>
						<td align="left">
							<input type="text" value="" name="travelGroupCode"/>
						</td>
					</tr>
					<tr height="50">
						<td align="right">库存类型(是否虚拟库存)：</td>
						<td align="left"><s:radio list="#{'true':'是','false':'否'}" name="virtual"/></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="confirm_div" style="display:none; width: 200px;"></div>
		<div id="update_div" style="display:none; width: 400px;">
			<form id="update_form" method="post"> 
				<table>
					<tr height="50">
						<td width="150" align="right"><span class="type"></span></td>
						<td align="left">
							<input id="settlementPrice2add" type="text" name="settlementPrice2add" />
							<span class="msg" style="color:red"></span>
							<input id="ordItemId2add" type="hidden" name="ordItemId2add" />
							<input id="changeType2add" type="hidden" name="changeType2add" />
							<input id="priceBeforeUpdate" type="hidden" name="priceBeforeUpdate" />
							<input id="totalPriceBeforeUpdate" type="hidden" name="totalPriceBeforeUpdate" />
						</td>
					</tr>
					<tr height="50">
						<td align="right">修改原因：</td>
						<td align="left">
							<select name="reason2add" class="cash_name" id="reason2add">
								<s:iterator value="resultList" id="item">
									<option value="${item.code}">${item.cnName}</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr height="50">
						<td align="right">备注：</td>
						<td align="left">
							<textarea id="remark2add" name="remark2add" rows="4" cols="21"></textarea>
							<span class="msg_remark" style="color:red"></span>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>