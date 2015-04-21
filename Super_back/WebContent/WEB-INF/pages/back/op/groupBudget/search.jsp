<%@page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>团计划</title>
		<jsp:include page="head.jsp"></jsp:include>
		<script type="text/javascript">
			$(document).ready(function(){
				if($("#visitTimeStartInput").val() == "" && $("#visitTimeEndInput").val() == ""){
					var startDate = new Date();
					var startStr = startDate.getFullYear() + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
					$("#visitTimeStartInput").val(startStr);
					startDate.setDate(startDate.getDate() + 7);
					var endDate = startDate;
					var endStr = endDate.getFullYear() + "-" + (endDate.getMonth()+1) + "-" + endDate.getDate();
					$("#visitTimeEndInput").val(endStr);
				}
				$("#search_button").click(function(){
					if(!/^\d*$/.test($("#productIdInput").val())){
						art.dialog({
							lock:true,
							fixed:true,
							title:"提示",
							content:"产品ID输入错误"
						});
						return false;
					}
					$("#search_group_form").submit();
				});
				$("#productManager").combox("getProductManagerForAutocomplete.do");
				$("#productManager").val($("#productManagerNameHd").val());
				$("#productManager_val").attr("label",$("#productManagerNameHd").val());
				$("#productManager_val").val($("#productManagerHd").val());
				if($("#isExistOrderHd").val() == "1"){
					$("#isExistOrderCb").attr("checked","checked");
				}
				if($("#isExistOrderHd").val() == "0"){
					$("#isExistOrderCb").removeAttr("checked");
				}
			}
			);
			function doBudgetTravelGroupCode(travelGroupCode){
				window.location = "doBudget.do?travelGroupCode=" + encodeURIComponent(travelGroupCode);
			}
			//进入实际成本表
			function doFinalBudgetHandler(travelGroupCode){
				//判断此团是否有订单
				$("#groupCodeHd").val(travelGroupCode);
				$.post("hasOrder.do",
						{
							groupCode:travelGroupCode
						},
						function(data){
							data = eval("(" + data + ")");
							if(data.result == 1){
								window.location = "doFinalBudget.do?travelGroupCode=" + encodeURIComponent($("#groupCodeHd").val());
							}else{
								art.dialog({
									lock:true,
									fixed:true,
									title:"提示",
									content:"该团没有订单，不能生成实际成本表"
								});
								return;
							}
						});
			}
			//导出Excel
			function exportHandler(){
				if(!/^\d*$/.test($("#productIdInput").val())){
						art.dialog({
							lock:true,
							fixed:true,
							title:"提示",
							content:"产品ID输入错误"
						});
						return false;
					}
				$("#search_group_form").attr("action","exportGroupList.do");
				$("#search_group_form").submit();
				$("#search_group_form").attr("action","getGroupList.do");
			}
			function finGLGroupCost(travelGroupCode){
				$.post("finGLGroupCost.do",
						{
							travelGroupCode:encodeURIComponent(travelGroupCode)
						},
						function(data){
							data = eval("(" + data + ")");
							if(data.result){
								art.dialog({
									lock:true,
									fixed:true,
									title:"成功",
									content:"团成本入账成功"
								});
								("团成本入账成功");
							}else{
								art.dialog({
									lock:true,
									fixed:true,
									title:"错误",
									content:"团成本入账失败"
								});
							}
						}
				);
			}
			/**
			//查看订单
			function showOrderHandler(travelGroupCode){
				jQuery("#orderTbl").GridUnload();
				jQuery("#orderTbl").jqGrid(
					{ 
						url:'getOrderByGroupCode.do?travelGroupCode='+travelGroupCode, 
						datatype: "json", 
						scrollOffset :0,
						colNames:['团号', '订单号', '销售产品名称', '销售产品ID','销售单价','订购数量','订单产品金额','优惠金额','优惠名称','应付金额','实付金额','联系人'],
						colModel:[ {name:'orderId',width:100}, 
						           {name:'settlePrice',width:90}, 
						           {name:'quantity', width:100}, 
						           {name:'settleAmount',width:100}
						          ], 
			           jsonReader: {
			        	 repeatitems:false,
			        	 root:"records",			//数据集
			     	     total: "totalPages",        // 总页数
			     	     page: "page",            // 当前页
			     	     records: "totalRecords"    // 总记录数 
			     	    },
						rowNum:10, 
						rowList:[10,20,30], 
						pager: '#prdPageDiv', 
						sortname: 'id', 
						viewrecords: true, 
						sortorder: "desc"
					}); 
				jQuery("#orderTbl").jqGrid('navGrid','#orderPageDiv',{edit:false,add:false,del:false});
				
				art.dialog({
					fixed:true,
					lock:true,
					title:"订单明细[<span id='travelGroupCodeSpan'>" + travelGroupCode + "</span>]",
					content:document.getElementById("orderWin")
				});
			}
			//导出订单
			function exportOrderHandler(){
				$.get("exportOrderListByGroupCode.do?"+$("#travelGroupCodeSpan").html());
			}
			**/
		</script>
	</head>
	<body>
		<input type="hidden" id ="groupCodeHd"/>
		<input type="hidden" id="productManagerHd" value="<s:property value="productManager"/>"/>
		<input type="hidden" id="productManagerNameHd" value="<s:property value="productManagerName"/>"/>
		<input type="hidden" id="isExistOrderHd" value="<s:property value="isExistOrder"/>"/>
		<s:set name="nowTime" value="new java.util.Date()"></s:set>
		<div class="wapper_accounts">
			<div class="rad5 wapper_list wapper_list_cash">
				<h3 class="order_check">团列表</h3>
				<div class="cash_seach">
					<form method="post" id="search_group_form" action="getGroupList.do">
						<ul class="order_top_list">
							<li class="other_list">
								<label> 产品名称：</label> 
								<input name="productName" type="text" class="input_text02 input_combox" 
									value="<s:property value="productName"/>"/>
							</li>
							<li class="other_list">
								<label>产品ID：</label> 
								<input id="productIdInput" name="productId" type="text" 
									class="input_text02 input_combox" 
									value="<s:property value="productId"/>"/>
							</li>
							<li class="other_list">
								<label>团号：</label> 
								<input name="travelGroupCode" type="text" class="input_text02 input_combox" 
									value="<s:property value="travelGroupCode"/>"/>
							</li>
							<li class="other_list2">
								<label>出团日期：</label> 
								<input id="visitTimeStartInput" name="visitTimeStart" type="text" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									class="input_text01 Wdate"
									value="<s:property value="visitTimeStart"/>">
									~
								<input id="visitTimeEndInput" name="visitTimeEnd" type="text" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									class="input_text01 Wdate" 
									value="<s:property value="visitTimeEnd"/>">
							</li>
							<li class="other_list">
								<label>结算状态：</label>
								<s:select 
									list="#{'':'请选择',
											'UNCOST':'未做成本',
											'COSTED':'已做成本',
											'CONFIRMED':'确认成本',
											'CHECKED':'已核算'}"
									name="settlementStatus">
								</s:select>
							</li>
							<li class="other_list">
								<label>是否存在订单：</label>
								<input id="isExistOrderCb" type="checkbox" name="isExistOrder" checked="checked" for="isExistOrderCb"/>
							</li>
							
							<li class="other_list">
								<label>产品经理：</label>
								<input type="hidden" label="" value="" name="productManager" id="productManager_val" />
								<input id="productManager" name="productManagerName" type="text" class="input_text02 table_input_style" />
							</li>
							<li class="">
								<input id="search_button" type="button" value="查 询" 
									style="margin-left: 15px;" 
									class="btn_sub" />
								<input type="button" value="导出Excel" 
									style="margin-left: 15px;" 
									class="btn_sub" onclick="exportHandler()"/>
							</li>
						</ul>
						
					</form>
					<div class="order_list">
						<div class="payment_list tb_model_cont">
							<div class="rad5 tabw100 blue_skin_tb">
								<table cellspacing="0" cellpadding="0" border="0" class="rad5 tabw100 blue_skin_tb">
									<thead>
										<tr bgcolor="#eeeeee">
											<td>团号</td>
											<td>产品ID</td>				
											<td>产品名称	</td>			
											<td>团实际收入</td>			
											<td>活动折让</td>			
											<td>团利润</td>			
											<td>总人数</td>			
											<td>成人</td>			
											<td>儿童</td>		
											<td>出团日期	</td>		
											<td>回团日期	</td>		
											<td>结算状态</td>		
											<td>操作	</td>
										</tr>
									</thead>
									<tbody>
									<s:iterator value="pagination.records" var="group">
										<tr>
											<td width="200">${group.travelGroupCode}</td>
											<td width="80">${group.productId}</td>
											<td width="15%">${group.productName}</td>
											<td width="140"><fmt:formatNumber pattern="#,##0.00" value="${group.actIncoming}" /></td>
											<td width="120"><fmt:formatNumber pattern="#,##0.00" value="${group.actAllowance}" /></td>
											<td width="100"><fmt:formatNumber pattern="#,##0.00" value="${group.actProfit}" /></td>
											<td width="80">${group.paySuccessNum}</td>
											<td width="80">${group.actAdult}</td>
											<td width="80">${group.actChild}</td>
											<td width="80">
												<s:if test="#group.visitTime != null">
													<s:date name="#group.visitTime" format="yyyy-MM-dd"/>
												</s:if>
											</td>
											<td width="80">
												<s:if test="#group.backTime != null">
													<s:date name="#group.backTime" format="yyyy-MM-dd"/>
												</s:if>
											</td>
											<td width="100">
												<s:if test="#group.settlementStatus == 'UNCOST'">
													未做成本
												</s:if>
												<s:elseif test="#group.settlementStatus == 'COSTED'">
													已做成本
												</s:elseif>
												<s:elseif test="#group.settlementStatus == 'CONFIRMED'">
													确认成本
												</s:elseif>
												<s:elseif test="#group.settlementStatus == 'CHECKED'">
													已核算
												</s:elseif>
											</td>
											<td width="200">
<%--											<a href="#" onclick="doBudgetTravelGroupCode('${group.travelGroupCode}')">团预算表</a>
 												<s:if test="#group.settlementStatus != 'UNBUDGET' && #group.visitTime.getTime() < #nowTime.getTime()"> --%>
<%-- 													<a href="doFinalBudget.do?travelGroupCode=${group.travelGroupCode}"> --%>
<!-- 														团实际成本 -->
<!-- 													</a> -->
<%-- 												</s:if> --%>
												
												<a href="#" onclick="doFinalBudgetHandler('${group.travelGroupCode}')">团实际成本</a>
												<!-- 
												<a href="#"
													   onclick="showOrderHandler('${group.travelGroupCode}')">
														查看订单
													</a>
												 -->
											</td>
										</tr>
									</s:iterator>
									</tbody>
								</table>
								<table width="90%" border="0" align="center">
									<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看订单窗口 -->
		<!-- 
		<div id="orderWin" style="display: none;height: 400px;">
			<table id="orderTbl" />
			<div id="orderPageDiv"></div>
			<button value="导出Excel" onclick="exportOrderHandler()"></button>
		</div>
		-->
	</body>
</html>
