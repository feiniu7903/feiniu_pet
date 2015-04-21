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
		<title>淘宝线路产品</title>
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
				if (confirm("是否确定同步淘宝线路产品信息？")) {
					$("#update_button").attr("value", "同步中...");
					$("#update_button").attr("disabled", true);
					$.ajax({
		                   url: "${basePath}/tmall/syncTaobaoTravelProduct.do",
		                   dataType:'json',
		                   type: "POST",
		                   success: function(myJSON){
		                	   if (myJSON.success == 'true') {
		                		   alert(myJSON.meg);
			                	   $("#search_group_form").submit();
		                	   } else {
		                		   alert("同步淘宝线路产品失败！");
		                		   $("#update_button").attr("disabled", false);
		                		   $("#update_button").attr("value", "同步淘宝线路产品信息");
		                	   }
		                   }
		            });
				}
			});
		});
		
		// 显示选择套餐页面
		function showTravelCombo(productId, travelComboId, tbTitle, tbComboName, productName) {
			if (productId == undefined || productId == '' || productId == 0) {
				alert("请先关联产品ID！");
				return;
			}
            // 获取驴妈妈套餐
            var _obj01 = '';
            var _obj02 = '';
            var _obj03 = '';
            
            var _branchId01 = $("#t_h_" + travelComboId + "_1").val();
            var _branchId02 = $("#t_h_" + travelComboId + "_2").val();
            var _branchId03 = $("#t_h_" + travelComboId + "_3").val();
			$.ajax({
			    url: "${basePath}/tmall/querySelectTravelCombo.do",
			    dataType:'json',
			    data: {productId : productId},
			    type: "POST",
			    async: false,
			    success: function(myJSON){
			        for(var i=0;i<myJSON.length;i++){ 
			        	if (_branchId01 && _branchId01 == (myJSON[i].prodBranchId + '')) {
			        		_obj01 += '<input type="checkbox" name="s_h_1_branchName" value="' + myJSON[i].prodBranchId + '" checked="checked" onchange="checkSelectOne(1, this)" />';
			        	} else {
			        		_obj01 += '<input type="checkbox" name="s_h_1_branchName" value="' + myJSON[i].prodBranchId + '" onchange="checkSelectOne(1, this)" />';
			        	}
			        	_obj01 += '  ' + myJSON[i].branchName + '  ';
			        	
			        	if (_branchId02 && _branchId02 == (myJSON[i].prodBranchId + '')) {
			        		_obj02 += '<input type="checkbox" name="s_h_2_branchName" value="' + myJSON[i].prodBranchId + '" checked="checked" onchange="checkSelectOne(2, this)"  />';
			        	} else {
			        		_obj02 += '<input type="checkbox" name="s_h_2_branchName" value="' + myJSON[i].prodBranchId + '" onchange="checkSelectOne(2, this)" />';
			        	}
			        	_obj02 += '  ' + myJSON[i].branchName + '  ';
			        	
			        	if (_branchId03 && _branchId03 == (myJSON[i].prodBranchId + '')) {
			        		_obj03 += '<input type="checkbox" name="s_h_3_branchName" value="' + myJSON[i].prodBranchId + '" checked="checked" onchange="checkSelectOne(3, this)" />';
			        	} else {
			        		_obj03 += '<input type="checkbox" name="s_h_3_branchName" value="' + myJSON[i].prodBranchId + '" onchange="checkSelectOne(3, this)" />';
			        	}
			        	_obj03 += '  ' + myJSON[i].branchName + '  ';
			        }
			    }
			});
            
			var content = '<div style="float:left;margin-left:-1px;width:400px;height:180px;">';
			content += '<input id="h_travelComboId" type="hidden" value="' + travelComboId + '" />';
			content += '<table>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >淘宝线路标题：</td><td >' + tbTitle + '</td></tr>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >淘宝套餐名称：</td><td >' + tbComboName + '</td></tr>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >驴妈妈产品名称：</td><td >' + productName + '</td></tr>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >成人：</td><td >' + _obj01 + '</td></tr>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >儿童：</td><td >' + _obj02 + '</td></tr>';
			content += '<tr style="height:30px;" ><td align="right" width="30%" >房差：</td><td >' + _obj03 + '</td></tr>';
			content += '</table></div>';
			
			art.dialog({
				id : "confirm-dialog",
				title : "设置产品类别",
				lock : true,
				content : content, 
				okValue : '保存',
				ok : function() {
					// 保存  套餐
					saveTravelComboType();
				},
				cancelValue : '取消',
				cancel : true,
			});
		}
		
		// 只能选择一个类别
		function checkSelectOne(_num, _this){
			var num = $("input[name='s_h_" + _num + "_branchName']:checked").size();
			if (num > 1) {
				alert("只能选择一个产品类别！");
				$(_this).attr("checked", false);
			}
		}
		
		// 保存套餐
		function saveTravelComboType() {
			var shBranchId01 = $("input[name='s_h_1_branchName']:checked").val();
			var shBranchId02 = $("input[name='s_h_2_branchName']:checked").val();
			var shBranchId03 = $("input[name='s_h_3_branchName']:checked").val();
			if (shBranchId01 == undefined) {
				shBranchId01 = '';
			}
			if (shBranchId02 == undefined) {
				shBranchId02 = '';
			}
			if (shBranchId03 == undefined) {
				shBranchId03 = '';
			}
			//alert("shBranchId01=" + shBranchId01);
			//alert("shBranchId02=" + shBranchId02);
			//alert("shBranchId03=" + shBranchId03);
			if (shBranchId01 != '' && shBranchId02 != '' && shBranchId01 == shBranchId02) {
				art.dialog({
					title : '消息',
					content : '成人票和儿童票所选的类别不能相同！',
					ok : true,
					okValue : '确定',
					lock : true
				});
				return;
			}
			if (shBranchId01 != '' && shBranchId03 != '' && shBranchId01 == shBranchId03) {
				art.dialog({
					title : '消息',
					content : '成人票和房差所选的类别不能相同！',
					ok : true,
					okValue : '确定',
					lock : true
				});
				return;
			}
			if (shBranchId02 != '' && shBranchId03 != '' && shBranchId02 == shBranchId03) {
				art.dialog({
					title : '消息',
					content : '儿童票和房差所选的类别不能相同！',
					ok : true,
					okValue : '确定',
					lock : true
				});
				return;
			}
			var prodBranchIds = "1:" + shBranchId01;
			prodBranchIds = prodBranchIds + ",2:" +  shBranchId02;
			prodBranchIds = prodBranchIds + ",3:" +  shBranchId03;
			//alert(prodBranchIds);
			$.ajax({
                   url: "${basePath}/tmall/saveTravelComboType.do",
                   dataType:'json',
                   data: {travelComboId : $("#h_travelComboId").val(), prodBranchIds : prodBranchIds},
                   type: "POST",
                   success: function(myJSON){
                   	if (myJSON.success == 'true') {
						art.dialog({
							title : '消息',
							content : '操作成功',
							ok : function() {
								// 更新列表
								$("#search_group_form").submit();
							},
							okValue : '确定',
							lock : true
						});
                   	} else {
                   		art.dialog({
							title : '消息',
							content : '操作失败,' + myJSON.error,
							ok : true,
							okValue : '确定',
							lock : true
						});
                   	}
                   }
             });
		}
		
		// 更新线路产品
		function updateTaobaoTravelInfo(_itemId) {
			if (confirm("是否确定更新淘宝产品Id为" + _itemId + "的信息？")) {
				$.ajax({
	                   url: "${basePath}/tmall/updateTaobaoTravelInfo.do",
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
		
		// 更新线路的同步
		function updateTicketIsSync(_travelComboId, _tbItemId) {
			var mgm;
			if ($("#is_sync_" + _travelComboId).attr("checked")) {
				mgm = "不同步";
			} else {
				mgm = "同步";
			}
			if (confirm("是否确定" + mgm + "？")) {
				// 0 表示不同步， 1表示同步
				var _isSync;
				if ($("#is_sync_" + _travelComboId).attr("checked")) {
					$("#sync_text_" + _travelComboId).text("不同步");
					_isSync = "0";

				} else {
					$("#sync_text_" + _travelComboId).text("同步");
					_isSync = "1";

				}
				$.ajax({
	                   url: "${basePath}/tmall/updateTravelIsSync.do",
	                   dataType:'json',
	                   type: "POST",
	                   data: {travelComboId : _travelComboId, isSync : _isSync, itemId : _tbItemId},
	                   success: function(myJSON){
	                	   if (myJSON.success == 'true') {
	                		   alert("设置成功！");
	                	   } else {
	                		   updateIsSyncCheckbox(_travelComboId);
	                		   alert("设置失败！");
	                	   }
	                   }
	            }); 
			} else {
				updateIsSyncCheckbox(_travelComboId);
			}
		}
		
		// 更新状态
		function updateIsSyncCheckbox(_travelComboId) {
			if ($("#is_sync_" + _travelComboId).attr("checked")) {
 			   $("#is_sync_" + _travelComboId).attr("checked", false);
 			   $("#sync_text_" + _travelComboId).text("同步");
 		   	} else {
 			   $("#is_sync_" + _travelComboId).attr("checked", true);
 			   $("#sync_text_" + _travelComboId).text("不同步");
 		   	}
		}
		
		// 上传信息（同步线路的价格日历）
		function updateTravelCombo(_productId, _travelComboId) {
			if (confirm("是否确定上传信息？")) {
				if (_productId == undefined || _productId == '' || _productId == 0) {
					alert("请先关联产品ID！");
					return;
				}
				if ($("#is_sync_" + _travelComboId).attr("checked")) {
					alert("请先将同步状态修改为同步！");
					return;
		 		}
				$.ajax({
	                   url: "${basePath}/tmall/updateTravelCombo.do",
	                   dataType:'json',
	                   type: "POST",
	                   data: {travelComboId : _travelComboId},
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
				<h3 class="order_check">淘宝线路产品</h3>
				<div class="cash_seach" >
					<form method="post" id="search_group_form" action="<%=basePath%>tmall/toTravelSyncList.do">
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
								<input name="type_prodBranchId" type="text" class="input_text02 input_combox"  onkeyup="value=value.replace(/[^\d]/g,'')"
									value="<s:property value='%{#parameters.type_prodBranchId[0]}'/>"/>
							</li>

							<%--<li class="other_list">
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
									style="margin-left: 15px;" />
							</li>
							<li class="">
								<input id="update_button" type="button" value="同步淘宝线路产品信息"
									style="margin-left: 15px;" />
									<!-- class="btn_sub" -->
                                <a href="#log" class="showLogDialog" param="{'parentType':'TAOBAO_PROD','objectType':'TAOBAO_TRAVEL_PROD','parentId':'2'}">
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
										    <td>淘宝线路标题</td>
											<td>套餐名称</td>
											<td>产品ID</td>
											<td>产品名称</td>
											<td>类别名称 </td>
											<td>同步</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
									<s:iterator value="pagination.records" var="group" status="L">
										<tr>
										    <td width="30">${L.index+1 }</td>
										    <td width="50">
										    	<a href="#log" class="showLogDialog" param="{'parentType':'TAOBAO_PROD','objectType':'TAOBAO_TRAVEL_PROD','parentId':${group.tbItemId }}">
										    		${group.tbItemId }
										    	</a>
										    </td>
											<td width="50">${group.tbTitle }</td>
									        <td width="110">${group.tbComboName }</td>
									        <td width="60">${group.productId }</td>
									        <td width="80">${group.productName }</td>
									        <td width="85">${group.branchName }</td>
											<td width="50" >
												<input id="is_sync_${group.travelComboId}" type="checkbox" onclick="updateTicketIsSync('${group.travelComboId}', '${group.tbItemId }')"
													<s:if test="#group.isSync == 1">
														
													</s:if>
													<s:else>
														checked="checked"
													</s:else>
												 />
												 <span id="sync_text_${group.travelComboId}">
												 	<s:if test="#group.isSync == 1">
														同步
													</s:if>
													<s:else>
														不同步
													</s:else>
												 </span>
											</td>
											<td width="60" >
												<a class="budget_see" href="javascript:showTravelCombo('${group.productId }', '${group.travelComboId }', '${group.tbTitle }', '${group.tbComboName }', '${group.productName }')">
													设置产品类别
												</a>
												<a class="budget_see" href="javascript:updateTaobaoTravelInfo(${group.tbItemId })">
													更新
												</a>
												<a class="budget_see" href="javascript:updateTravelCombo('${group.productId }', '${group.travelComboId }')">
													上传
												</a>
												<s:iterator value="#group.comboTypes" var="comboTypeObj">
													<input type="hidden" id="t_h_${group.travelComboId }_${comboTypeObj.comboType }" value="${comboTypeObj.prodBranchId }" />
												</s:iterator>
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
