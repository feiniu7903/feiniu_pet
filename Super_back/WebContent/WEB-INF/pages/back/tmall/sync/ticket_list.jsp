<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>淘宝门票产品</title>
		<jsp:include page="../head.jsp"></jsp:include>
		<script type="text/javascript" src="../js/tmall/tmallOrder.js"></script>	
		<script type="text/javascript" src="../js/ui/jquery-ui-1.8.5.js"></script>	
		<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
			 	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	/* 		 	$("#createTimeStart").datepicker({dateFormat:'yy-mm-dd',
					changeMonth: true,
					changeYear: true,
					showOtherMonths: true,
					selectOtherMonths: true,
					buttonImageOnly: true
				}); */
				// 查询
				$("#search_button").click(function(){
					$("#search_group_form").submit();
				});
				// 更新
				$("#update_button").click(function(){
					if (confirm("是否确定同步淘宝门票产品信息？")) {
						$("#update_button").attr("value", "同步中...");
						$("#update_button").attr("disabled", true);
						$.ajax({
			                   url: "${basePath}/tmall/syncTaobaoTicketProduct.do",
			                   dataType:'json',
			                   type: "POST",
			                   success: function(myJSON){
			                	   if (myJSON.success == 'true') {
			                		   alert(myJSON.meg);
				                	   $("#search_group_form").submit();
			                	   } else {
			                		   alert("同步淘宝门票产品失败！");
			                		   $("#update_button").attr("disabled", false);
			                		   $("#update_button").attr("value", "同步淘宝门票产品信息");
			                	   }
			                   }
			            });
					}
				});
			});
			
			// 更新门票的同步
			function updateTicketIsSync(_ticketSkuId, _tbItemId) {
				var mgm;
				if ($("#is_sync_" + _ticketSkuId).attr("checked")) {
					mgm = "不同步";
				} else {
					mgm = "同步";
				}
				if (confirm("是否确定" + mgm + "？")) {
					// 0 表示不同步， 1表示同步
					var _isSync;
					if ($("#is_sync_" + _ticketSkuId).attr("checked")) {
						$("#sync_text_" + _ticketSkuId).text("不同步");
						_isSync = "0";

					} else {
						$("#sync_text_" + _ticketSkuId).text("同步");
						_isSync = "1";

					}
					$.ajax({
		                   url: "${basePath}/tmall/updateTicketIsSync.do",
		                   dataType:'json',
		                   type: "POST",
		                   data: {ticketSkuId : _ticketSkuId, isSync : _isSync, itemId : _tbItemId},
		                   success: function(myJSON){
		                	   if (myJSON.success == 'true') {
		                		   alert("设置成功！");
		                	   } else {
		                		   updateIsSyncCheckbox(_ticketSkuId);
		                		   alert("设置失败！");
		                	   }
		                   }
		            }); 
				} else {
					updateIsSyncCheckbox(_ticketSkuId);
				}
			}
			
			// 更新状态
			function updateIsSyncCheckbox(_ticketSkuId) {
				if ($("#is_sync_" + _ticketSkuId).attr("checked")) {
     			   $("#is_sync_" + _ticketSkuId).attr("checked", false);
     			   $("#sync_text_" + _ticketSkuId).text("同步");
     		   	} else {
     			   $("#is_sync_" + _ticketSkuId).attr("checked", true);
     			   $("#sync_text_" + _ticketSkuId).text("不同步");
     		   	}
			}
			
			// 更新门票产品
			function updateTaobaoTicketInfo(_itemId) {
				if (confirm("是否确定更新淘宝产品Id为" + _itemId + "的信息？")) {
					$.ajax({
		                   url: "${basePath}/tmall/updateTaobaoTicketInfo.do",
		                   dataType:'json',
		                   type: "POST",
		                   data: {itemId : _itemId},
		                   success: function(myJSON){
		                	   if (myJSON.success == 'true') {
		                		   alert("更新成功！");
			                	   $("#search_group_form").submit();
		                	   } else {
		                		   alert("更新失败！");
		                	   }
		                   }
		            });
				}
			}
			
			// 上传信息（同步门票的价格日历）
			function updateTicketSku(_productId, _ticketSkuId) {
				if (confirm("是否确定上传信息？")) {
					if (_productId == undefined || _productId == '' || _productId == 0) {
						alert("请先关联产品ID！");
						return;
					}
					if ($("#is_sync_" + _ticketSkuId).attr("checked")) {
						alert("请先将同步状态修改为同步！");
						return;
			 		}
					$.ajax({
		                   url: "${basePath}/tmall/updateTicketSku.do",
		                   dataType:'json',
		                   type: "POST",
		                   data: {ticketSkuId : _ticketSkuId},
		                   success: function(myJSON){
		                	   if (myJSON.success == 'true') {
		                		   alert("上传信息成功！");
		                	   } else {
		                		   alert("上传信息失败！");
		                	   }
		                   }
		            }); 
				}
			}
		</script>
	</head>
	<body>
		<div class="wapper_accounts">
			<div class="rad5 wapper_list wapper_list_cash">
				<h3 class="order_check">淘宝门票产品</h3>
				<div class="cash_seach" >
					<form method="post" id="search_group_form" action="<%=basePath%>tmall/toTickeSyncList.do">
						<ul class="order_top_list">
                            <li class="other_list">
                                <label>淘宝ID：</label>
                                <input name="tbItemId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
                                       value="<s:property value='%{#parameters.tbItemId[0]}'/>"/>
                            </li>
                            <li class="other_list">
                                <label>淘宝产品名称：</label>
                                <input name="tbTitle" type="text" class="input_text02 input_combox"
                                       value="<s:property value='%{#parameters.tbTitle[0]}'/>"/>
                            </li>
                            <li class="other_list">
                                <label>产品类型：</label>
                                <s:select
                                        list="#{'':'请选择',
											'1':'实体票',
											'2':'电子票'}"
                                        name="tbTicketType" value="%{#parameters.tbTicketType[0]}">
                                </s:select>
                            </li>
                            <li class="other_list">
                                <label>产品ID：</label>
                                <input  name="productId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
                                        value="<s:property value='%{#parameters.productId[0]}'/>"/>
                            </li>
							<li class="other_list">
								<label>主站产品名称：</label>
								<input name="productName" type="text" class="input_text02 input_combox" 
									value="<s:property value='%{#parameters.productName[0]}'/>" />
							</li>
							<li class="other_list">
								<label>类别ID：</label> 
								<input name="prodBranchId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value='%{#parameters.prodBranchId[0]}'/>"/>
							</li>

<%--						<li class="other_list">
                                <label>淘宝状态：</label>
                                <s:select
                                    list="#{'':'请选择',
                                            'onsale':'上架',
                                            'instock':'下架'}"
                                    name="tbAuctionStatus" value="%{#parameters.tbAuctionStatus[0]}">
                                </s:select>
                            </li>
                            <li class="other_list">
                               <label>产品类型：</label>
                               <s:select
                                    list="#{'':'请选择',
                                            'true':'期票',
                                            'false':'非期票'}"
                                    name="isAperiodic" value="%{#parameters.isAperiodic[0]}">
                                </s:select>
                            </li>
							<li class="other_list">
							   <label>产品状态：</label>
							   <s:select 
									list="#{'':'请选择',
											'true':'上线',
											'false':'下线'}"
									name="onLine" value="%{#parameters.onLine[0]}">
								</s:select>
							</li>
							<li class="other_list">
							   <label>所属公司：</label>
								<s:select list="filialeNameList" name="filialeName"
                             			listKey="code" listValue="name" 
                             			value="%{#parameters.filialeName[0]}" ></s:select>
							</li>--%>
							<li class="">
								<input id="search_button" type="button" value="查 询" 
									style="margin-left: 15px;"  
									 />
							</li>
							<li class="">
								<input id="update_button" type="button" value="同步淘宝门票产品信息"
									style="margin-left: 15px; width: 130px"  
									/>
									<!-- class="button" -->
                                <a href="#log" class="showLogDialog" param="{'parentType':'TAOBAO_PROD','objectType':'TAOBAO_TICKET_PROD','parentId':'1'}">
                                    查看更新历史
                                </a>
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
											<td>淘宝ID</td>
                                            <td>淘宝产品名称</td>
                                            <td>规格名称</td>
											<td>产品ID</td>
											<td>销售产品名称</td>				
											<td>类别ID</td>
											<td>类别名称</td>
											<td>同步</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
									<s:iterator value="pagination.records" var="group" status="L">
										<tr>
										    <td width="30">${L.index+1}</td>
										    <td width="40">
										    	<a href="#log" class="showLogDialog" param="{'parentType':'TAOBAO_PROD','objectType':'TAOBAO_TICKET_PROD','parentId':${group.tbItemId }}">
										    		${group.tbItemId }
										    	</a>
										    </td>
                                            <td width="100" >${group.tbTitle }</td>
                                            <td width="100" >${group.tbVidName }</td>
											<td width="50">${group.productId}</td>
											<td width="100">${group.productName}</td>
											<td width="50">${group.prodBranchId}</td>
									        <td width="70">${group.branchName}</td>
									        <td width="60" >
												<input id="is_sync_${group.ticketSkuId}" type="checkbox" onclick="updateTicketIsSync('${group.ticketSkuId}', '${group.tbItemId }')"
													<s:if test="#group.isSync == 1">
														
													</s:if>
													<s:else>
														checked="checked"
													</s:else>
												 />
												 <span id="sync_text_${group.ticketSkuId}">
												 	<s:if test="#group.isSync == 1">
														同步
													</s:if>
													<s:else>
														不同步
													</s:else>
												 </span>
											</td>
											<td width="60" >
												<a class="budget_see" href="javascript:updateTaobaoTicketInfo(${group.tbItemId })">
													更新
												</a>
                                                <a class="budget_see" href="javascript:updateTicketSku('${group.productId }', '${group.ticketSkuId }')">
                                                    上传
                                                </a>
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
