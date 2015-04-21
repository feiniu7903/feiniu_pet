<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>淘宝搬单</title>
		<jsp:include page="head.jsp"></jsp:include>
		<script type="text/javascript" src="../js/tmall/tmallOrder.js"></script>	
		<script type="text/javascript" src="../js/ui/jquery-ui-1.8.5.js"></script>	
		<script type="text/javascript">
		$(document).ready(function(){
		 	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		 	$("#createTimeStart").datepicker({dateFormat:'yy-mm-dd',
				changeMonth: true,
				changeYear: true,
				showOtherMonths: true,
				selectOtherMonths: true,
				buttonImageOnly: true
				}); 
			$("#search_button").click(function(){
					$("#search_group_form").submit();
				});
				})
				</script>
	</head>
	<body>
		<s:set name="nowTime" value="new java.util.Date()"></s:set>
		<div class="wapper_accounts">
			<div class="rad5 wapper_list wapper_list_cash">
				<h3 class="order_check">淘宝搬单列表</h3>
				<div class="cash_seach" >
					<form method="post" id="search_group_form" action="getOrderList.do">
						<ul class="order_top_list">
							<li class="other_list">
								<label> 买家昵称：</label> 
								<input name="buyerNick" type="text" class="input_text02 input_combox" 
									value="<s:property value="buyerNick"/>"/>
							</li>
							<li class="other_list">
								<label>销售产品ID：</label> 
								<input  name="productId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value="productId"/>"/>
							</li>
							<li class="other_list">
								<label>分销商订单号：</label> 
								<input name="tmallId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value="tmallId"/>"/>
							</li>
							<li class="other_list">
								<label>类别：</label>
								<s:select 
									list="#{'':'请选择',
											'false':'人工搬单',
											'true':'系统搬单'}"
									name="systemOrder">
								</s:select>
							</li>
							
							<li class="other_list">
								<label>处理状态：</label>
								<s:select 
									list="#{'':'请选择',
											'create':'系统处理中',
											'failure':'人工下单',
											'unpay':'处理支付',
											'success':'已处理'}"
									name="status">
								</s:select>
							</li>
							
							<li class="other_list">
								<label>产品类型：</label>
								<s:select 
									list="#{'':'请选择',
											'TICKET':'门票',
											'HOTEL':'酒店',
											'ROUTE':'线路',
											'OTHER':'其他'}"
									name="productType">
								</s:select>
							</li>
							    
							<li class="other_list">
							   <label>下单日期：</label>
							   <input id="createTimeStart" name="createTimeStart" type="text" class="date" onClick="WdatePicker()" value="<s:date name="createTimeStart" format="yyyy-MM-dd"/>" />
							</li>
							<li class="">
								<input id="search_button" type="button" value="查 询" 
									style="margin-left: 15px;" 
									class="btn_sub" />
							</li>
						</ul>
						
					</form>
					<div class="order_list">
						<div class="payment_list tb_model_cont">
							<div class="rad5 tabw100 blue_skin_tb">
								<table cellspacing="0" cellpadding="0" border="0" class="rad5 tabw100 blue_skin_tb">
									<thead>
										<tr bgcolor="#eeeeee">
										    <td>编号</td>
											<td>淘宝订单号</td>
											<td>买家昵称</td>				
											<td>订购产品	</td>
											<td>产品类型</td>
											<td>订单状态</td>
											<td>警告 </td>
											<td>驴妈妈订单号</td>		
											<td>自动搬单</td>		
											<td>下单时间</td>			
											<td>状态</td>
										</tr>
									</thead>
									<tbody>
									<s:iterator value="pagination.records" var="group" status="L">
										<tr>
										    <td width="50">${L.index+1}</td>
											<td width="105">${group.tmallOrderNo}</td>
											<td width="110">${group.buyerNick}</td>
											<td >${group.productName}</td>
									        <td width="60">
									          <s:if test="#group.productType=='TICKET'">
											            门票
								              </s:if>
								              <s:elseif test="#group.productType=='HOTEL'">
								                                             酒店
								              </s:elseif>
								               <s:elseif test="#group.productType=='ROUTE'">
								                                             线路
								              </s:elseif>
								              <s:elseif test="#group.productType=='OTHER'">
								                                             其他
								              </s:elseif>
									        </td>
									        <td width="60">
									         <s:if test="#group.orderStatus=='NORMAL'">
											            正常
								              </s:if>
								              <s:elseif test="#group.orderStatus=='CANCEL'">
								                                             取消
								              </s:elseif>
								              <s:elseif test="#group.orderStatus=='FINISHED'">
								                                             完成
								              </s:elseif>
									        </td>
									        <td width="80">
									          <s:if test="#group.gtMaxNum=='true'&&#group.certificate=='true'&&#group.resourceConfirmStatus!='AMPLE'&&#group.resourceConfirmStatus!=null">
											             超过单笔订购最大量   实体票  需要资源审核
								              </s:if>
								              <s:elseif test="#group.gtMaxNum=='true'&&#group.certificate=='true'&&#group.resourceConfirmStatus=='AMPLE'">
								                                           超过单笔订购最大量   实体票  
								              </s:elseif>
								              <s:elseif test="#group.gtMaxNum=='true'&&#group.certificate=='false'&&#group.resourceConfirmStatus!='AMPLE'&&#group.resourceConfirmStatus!=null">
								                                           超过单笔订购最大量   需要资源审核 
								              </s:elseif>
								              <s:elseif test="#group.gtMaxNum=='false'&&#group.certificate=='true'&&#group.resourceConfirmStatus!='AMPLE'&&#group.resourceConfirmStatus!=null">
								                                            实体票  需要资源审核
								              </s:elseif>
								               <s:elseif test="#group.gtMaxNum=='true'&&#group.certificate=='false'&&#group.resourceConfirmStatus=='AMPLE'&&#group.paymentTarget!='TOSUPPLIER'">
								                                           超过单笔订购最大量
								              </s:elseif>
								               <s:elseif test="#group.gtMaxNum=='false'&&#group.certificate=='true'&&#group.resourceConfirmStatus=='AMPLE'">
								                                            实体票
								              </s:elseif>
								               <s:elseif test="#group.gtMaxNum=='false'&&#group.certificate=='false'&&#group.resourceConfirmStatus!='AMPLE'&&#group.resourceConfirmStatus!=null">
								                                             需要资源审核
								              </s:elseif>
								              <s:elseif test="#group.gtMaxNum=='false'&&#group.paymentTarget=='TOSUPPLIER'">
								                                             景区支付
								              </s:elseif>
								               <s:elseif test="#group.gtMaxNum=='true'&&#group.paymentTarget=='TOSUPPLIER'">
								                                             超过单笔订购最大量  景区支付
								              </s:elseif>
									        </td>
									        <td width="85">${group.lvOrderId}</td>
											<td width="60">
											  <s:if test="#group.systemOrder=='true'">
											             是
								              </s:if>
								              <s:elseif test="#group.systemOrder=='false'">
								                                             否
								              </s:elseif>
											</td>
											<td width="150">
										         <s:date name="#group.createTime" format="yyyy-MM-dd HH:mm:ss"/>
									        </td>
											<td width="60" id="processStatus_${group.tmallMapId}">
											   <s:if test="#group.status=='success'">
										            <a  class="budget_see"  href="javascript:showLogFn('${group.tmallMapId }');">已处理</a> 
								              </s:if>
								              <s:elseif test="#group.status=='failure'">
								                  <a  class="budget_see"  href="javascript:manualProcessFn('${group.tmallMapId }');">人工下单</a>      
								              </s:elseif>
								              <s:elseif test="#group.status=='unpay'">
								                  <a  class="budget_see"  href="javascript:ProcessUnpayStatus('${group.tmallMapId }',1);">处理支付</a>      
								              </s:elseif>
											<s:elseif test="#group.status=='create'">
								                  <a  class="budget_see"  href="javascript:void(0)">系统处理中</a>      
								              </s:elseif>
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
	</body>
</html>
