<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>淘宝搬单</title>
		<link href="themes/default/easyui.css" type="text/css" rel="stylesheet"></link>
        <link href="themes/icon.css" type="text/css" rel="stylesheet"></link>
        <link href="themes/main.css" type="text/css" rel="stylesheet"></link>
        <script src="js/base/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="js/base/jquery.easyui.min.js" type="text/javascript"></script>
		<jsp:include page="head.jsp"></jsp:include>
		<script type="text/javascript" src="../js/tmall/tmallOrder.js"></script>	
		<script type="text/javascript">
		$(document).ready(function(){
			$("#search_button").click(function(){
				
				var mobile=$("input[name='buyerMobile']").val();
				var reg =/^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/; 
				if(mobile.length>0){
				if(!reg.test(mobile)){
					alert("手机号码不合法");
					return;
				}
				}
					$("#search_group_form").submit();
				});
				});
				</script>
	</head>
	<body>
		<s:set name="nowTime" value="new java.util.Date()"></s:set>
		<div class="wapper_accounts">
			<div class="rad5 wapper_list wapper_list_cash">
				<h3 class="order_check">淘宝搬单失败列表</h3>
				<div class="cash_seach">
					<form method="post" id="search_group_form" action="getFailedOrderList.do">
						<ul class="order_top_list">
							<li class="other_list">
								<label> 买家昵称：</label> 
								<input name="buyerNick" type="text" class="input_text02 input_combox" 
									value="<s:property value="buyerNick"/>"/>
							</li>
							<li class="other_list">
								<label>销售产品ID：</label> 
								<input  name="productId" type="text" class="input_text02 input_combox" onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value="productId"/>"/>
							</li>
							<li class="other_list">
								<label>分销商订单号：</label> 
								<input name="tmallId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value="tmallId"/>"/>
							</li>
							<li class="other_list">
								<label>订购人手机号：</label>
								<input name="buyerMobile" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value="buyerMobile"/>"/>
							</li>
							<!-- 
							<li class="other_list">
								<label>处理状态：</label>
								<s:select 
									list="#{'':'请选择',
											'processed':'已处理',
											'unprocess':'未处理'}"
									name="processStatus">
								</s:select>
							</li> -->
							
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
											<td>失败原因</td>
											<td>状态</td>		
											<td>操作</td>		
										</tr>
									</thead>
									<tbody>
									<s:iterator value="pagination.records" var="group" status="L">
										<tr>
										    <td width="60">${L.index+1}</td>
											<td width="120">${group.tmallOrderNo}</td>
											<td width="120">${group.buyerNick}</td>
											<td width="35%">${group.productName}</td>
											<td width="15%">${group.failedReason}</td>
											<td width="40">
											  <s:if test="#group.status=='failure'">未下单</s:if>
								              <s:elseif test="#group.status=='unpay'">未支付</s:elseif>
											</td>
											<td width="40" id="processStatusX_${group.tmallMapId}">
											  <s:if test="#group.status=='success'">
										            <a  class="budget_see"  href="javascript:void(0);">已处理</a> 
								              </s:if>
								              <s:elseif test="#group.status=='failure'">
								                  <a  class="budget_see"  href="javascript:updateProcessStatus('${group.tmallMapId }',0);">重新下单</a>      
								              </s:elseif>
								               <s:elseif test="#group.status=='unpay'">
								                  <a  class="budget_see"  href="javascript:updateProcessStatus('${group.tmallMapId }',1);">线下支付</a>      
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
