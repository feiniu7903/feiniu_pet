<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css">
	<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
	<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var orderId = '${orderDetail.orderId }';
			var contactId = '<s:if test="orderDetail.contact!=null"><s:property value="orderDetail.contact.personId " default="false"/></s:if>';
			var userId = '${orderDetail.userId }';
			var isPhysical = <s:property value="orderDetail.physical" default="false"/>;
			var existedTraveller = new Array(),index = 0,contactReceiverId;
			var workTaskId = "<%=session.getAttribute("workTaskId")%>";
			
			function send_sms(){
				$("#btn_later").attr("disabled","disabled");
				var dataMember = {mobile:"${orderDetail.contact.mobile}"};
				$.ajax({type:"POST", url:"<%=basePath%>ord/laterProcess.do", data:dataMember, dataType:"html", success:function (data) {
						if("success".indexOf(data)>-1){
							alert("手机号：<s:if test="orderDetail.contact!=null"><s:property value="orderDetail.contact.mobile" default="false"/></s:if>,短信已经通知用户!");
						}else{
							alert("短信发送失败!");
						}
				}});
			}
		$(document).ready(function() {
			$("#youhuiContent").loadUrlHtml();
			$("#invoiceDiv").loadUrlHtml();
			$("#addressDiv").loadUrlHtml();
			$("#memoDiv").loadUrlHtml();
			$("#editReceiverDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#addReceiverDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#addAddressDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#editAddressDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#addInvoiceDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#editInvoiceDialg").lvmamaDialog( {
				modal : false,
				width : 600,
				height : 350,
				close : function() {
				}
			});
			$("#infoQuestion").lvmamaDialog( {
				modal : false,
				width : 580,
				height : 60,
				close : function() {
				}
			});
			document.getElementById("redail").checked = ${orderDetail.redail == null ? false : orderDetail.redail};
			if(isPhysical) {
				$("#addressDiv").show();
				$("#addressBtns").show();
			}

			var needInv = ${orderDetail.needInvoice == null ? false : orderDetail.needInvoice};
			if(needInv) {
				document.getElementById("isNeedInvoice").checked = true;
				$("#isNeedInvoice").click();
				document.getElementById("isNeedInvoice").checked = true;
			}
	});		
			function updatePerson(personId,personOrderId){
				$('#editReceiverDialg').attr("href","<%=basePath%>/ord/person.do");
				$("#editReceiverDialg").reload( {
					personId : personId,
					orderId:personOrderId
				});
				$('#editReceiverDialg').openDialog();
			}
			
			function checkApprove(orderId){ 
				$.ajax({type:"POST", url:"<%=basePath%>ord/checkApprove.do",data:{orderId:orderId} ,success:function (result) { 
					var res = eval(result); 
					if (res) { 
						alert("订单已被回收！"); 
						closeDetailDiv('approveDiv') 
					} else {
						doApprovePass('doInfoApprovePass.do') 
					} 
				}}); 
			}

			function reSendOrder(orderId){
				$.post("${basePath}/ord/reSendSupplierOrder.do",{"orderId":orderId},function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						alert("操作成功");
					}else{
						alert(data.msg);
					}
				});
			}
			function reSendCancelOrder(orderId){
				$.post("${basePath}/ord/reSendCancelSupplierOrder.do",{"orderId":orderId},function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						alert("操作成功");
					}else{
						alert(data.msg);
					}
				});
			}
			function reSendPayOrder(orderId){
				$.post("${basePath}/ord/reSendPaySupplierOrder.do",{"orderId":orderId},function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						alert("操作成功");
					}else{
						alert(data.msg);
					}
				});
			}
		</script>
	</head>
	<body>
		<input type="hidden" value="<s:property value="orderDetail.physical" default="false"/>" name="physical"/>
		<input type='hidden' value='approveDiv' id='refresh' />
		<!--=========================我的审核任务弹出层==============================-->
		<div class="orderpoptit">
			<strong>审核订单：</strong>
			<p class="inputbtn">
				<s:if test="session.getAttribute('workTaskId')==null">
					<input type="button" class="button" value="关闭"
						onclick="javascript:closeDetailDiv('approveDiv');">
				</s:if>
			</p>
		</div>
		<div class="orderpopmain">
			<table style="font-size: 12px" width="100%" border="0"
				class="contactlist">
				<tr>
					<td width="25%">
						订单号：${orderDetail.orderId }
					</td>
					<td width="25%">
						下单时间：${orderDetail.zhCreateTime }
					</td>
					<td width="25%">
						下单人：${orderDetail.userName }
					</td>
					<td width="25%">
						支付等待时间：
						<%--						<s:select name="orderDetail.waitPayment"--%>
						<%--							list="#{30:'30分钟',60:'1小时',1440:'24小时',-1:'不限'}" id="waitPayment">--%>
						<%--						</s:select>--%>
						<%--						--%>
						<s:select id="waitPayment" list="payWaitItemList" listKey="attr01"
							listValue="name" headerKey="" headerValue="请选择" name="payWait"
							value="orderDetail.waitPayment"></s:select>
					</td>
				</tr>
				<tr>
					<td>
						应付金额：${orderDetail.oughtPayYuan }
					</td>
					<td>
						实付金额：${orderDetail.actualPayYuan }
					</td>
					<td>
						支付状态：${orderDetail.zhPaymentStatus }
					</td>
					<td>
						审核状态：${orderDetail.zhInfoApproveStatus }
					</td>
				</tr>
				<tr>
					<td colspan="4">
						订单来源渠道：${orderDetail.zhProductChannel }
					</td>
				</tr>
				<tr>
					<td colspan="3">
						用户备注：${orderDetail.userMemo }
					</td>
					<td>
						<input type="button" value="保 存" class="right-button02"
							onclick="doModifyWaitPayment('doModifyWaitPayment.do');">
					</td>
				</tr>
			</table>

			<!--=============商品清单=============-->
			<div class="popbox">
				<strong>商品清单</strong>
				<p class="paytime">
					游玩时间：
					<s:if test="orderDetail.orderType=='HOTEL'">
						<s:iterator value="orderDetail.ordOrderItemProds">
							<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
							<s:else>
					${zhVisitTime}
					</s:else>
						</s:iterator>
					</s:if>
					<s:else>
					${orderDetail.zhVisitTime }
					</s:else>
				</p>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont05">
					<tr class="CTitle">
						<td height="22" align="center" colspan="13">
							商品清单
						</td>
					</tr>
				</table>				
				<!-- 超级自由行，显示主产品 -->
				<s:if test="orderDetail.hasSelfPack()">
					主产品
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td height="35" width="10%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>								
								<td width="10%">
									人数
								</td>								
							</tr>
							<tr bgcolor="#ffffff">
								<td height="30">
									${orderDetail.mainProduct.productId }
								</td>
								<td>
									<a class="showImportantTips" href="javascript:void(0)"
									productId="${orderDetail.mainProduct.productId}"  prodBranchId="${orderDetail.mainProduct.prodBranchId}">${orderDetail.mainProduct.productName}</a>
								</td>								
								<td>
									${orderDetail.mainProduct.quantity }
								</td>
							</tr>							
						</tbody>
					</table>
				</s:if>
				<!-- 线路块根据产品信息分块放 -->
				<s:if test="orderDetail.orderType == 'FREENESS'">
					<!-- 门票块 -->
					门票信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td height="35" width="10%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="10%">
									市场价
								</td>
								<td width="10%">
									会员价
								</td>
								<td width="10%">
									数量
								</td>
								<td width="10%">
									总金额
								</td>
								<td width="10%">
									产品类型
								</td>
								<td width="10%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
								<s:if test="#prod.productType=='TICKET'">
									<tr bgcolor="#ffffff">
										<td height="30">
											${prod.productId }
										</td>
										<td>
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${prod.productId}">${prod.productName}</a>
										</td>
										<td>
											${prod.marketPriceYuan }
										</td>
										<td>
											${prod.priceYuan }
										</td>
										<td>
											${prod.quantity }
										</td>
										<td>
											${prod.priceYuan*prod.quantity }
										</td>
										<td>
											${prod.zhProductType }
										</td>
										<td>
											${zhVisitTime}
										</td>
									</tr>
								</s:if>
							</s:iterator>
						</tbody>
					</table>
					<!-- 门票块 -->
					<!-- 酒店块 -->
					酒店信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td height="35" width="10%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="10%">
									市场价
								</td>
								<td width="10%">
									会员价
								</td>
								<td width="10%">
									数量
								</td>
								<td width="10%">
									总金额
								</td>
								<td width="10%">
									产品类型
								</td>
								<td width="10%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
								<s:if test="#prod.productType=='HOTEL'">
									<tr bgcolor="#ffffff">
										<td height="30">
											${prod.productId }
										</td>
										<td>
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${prod.productId}"  prodBranchId="${prod.prodBranchId}">${prod.productName}</a>
										</td>
										<td>
											${prod.marketPriceYuan }
										</td>
										<td>
											${prod.priceYuan }
										</td>
										<td>
											${prod.hotelQuantity }
										</td>
										<td>
											${prod.priceYuan*prod.quantity }
										</td>
										<td>
											${prod.zhProductType }
										</td>
										<td>
											<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
											<s:else>
					${zhVisitTime}
					</s:else>
										</td>
									</tr>
								</s:if>							
							</s:iterator>
						</tbody>
					</table>
					<!-- 酒店块 -->
					<!-- 线路块 -->
					线路信息
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td height="35" width="10%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="10%">
									市场价
								</td>
								<td width="10%">
									会员价
								</td>
								<td width="10%">
									数量
								</td>
								<td width="10%">
									总金额
								</td>
								<td width="10%">
									产品类型
								</td>
								<td width="10%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
								<s:if test="#prod.productType=='ROUTE'">
									<tr bgcolor="#ffffff">
										<td height="30">
											${prod.productId }
										</td>
										<td>
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${prod.productId}"  prodBranchId="${prod.prodBranchId}">${prod.productName}</a>
										</td>
										<td>
											${prod.marketPriceYuan }
										</td>
										<td>
											${prod.priceYuan }
										</td>
										<td>
											${prod.hotelQuantity }
										</td>
										<td>
											${prod.priceYuan*prod.quantity }
										</td>
										<td>
											${prod.zhProductType }
										</td>
										<td>
											${zhVisitTime}
										</td>
									</tr>
								</s:if>								
							</s:iterator>
						</tbody>
					</table>
					<!-- 线路块 -->
				</s:if>
				<s:else>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#666666" width="100%" class="newfont05">
						<tbody>
							<tr bgcolor="#eeeeee">
								<td height="35" width="10%">
									序号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="10%">
									市场价
								</td>
								<td width="10%">
									会员价
								</td>
								<td width="10%">
									数量
								</td>
								<td width="10%">
									总金额
								</td>
								<td width="10%">
									产品类型
								</td>
								<td width="10%">
									游玩时间
								</td>
							</tr>
							<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
								<s:if test="#prod.productType!='OTHER'">
									<tr bgcolor="#ffffff">
										<td height="30">
											${prod.productId }
										</td>
										<td>
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${prod.productId}" prodBranchId="${prod.prodBranchId}">${prod.productName}</a>
										</td>
										<td>
											${prod.marketPriceYuan }
										</td>
										<td>
											${prod.priceYuan }
										</td>
										<td>
											<s:if test="#prod.productType=='HOTEL'">
										${prod.hotelQuantity }
									</s:if>
											<s:else>
										${prod.quantity }
									</s:else>
										</td>
										<td>
											${prod.priceYuan*prod.quantity }
										</td>
										<td>
											${prod.zhProductType }
										</td>
										<td>
											<s:if test="subProductType=='SINGLE_ROOM'">
					${dateRange}
					</s:if>
											<s:else>
					${zhVisitTime}
					</s:else>
										</td>
									</tr>
								</s:if>
							</s:iterator>
						</tbody>
					</table>
				</s:else>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#666666" width="100%" class="newfont06">
					<tbody>
						<tr class="CTitle">
							<td height="20" align="center" colspan="13">
								附加产品
							</td>
						</tr>
						<tr bgcolor="#eeeeee">
							<td height="35" width="10%">
								序号
							</td>
							<td width="18%">
								产品名称
							</td>
							<td width="10%">
								市场价
							</td>
							<td width="10%">
								会员价
							</td>
							<td width="10%">
								数量
							</td>
							<td width="10%">
								总金额
							</td>
							<td width="10%">
								产品类型
							</td>
							<td width="10%">
								游玩时间
							</td>
						</tr>
						<s:iterator id="prod" value="orderDetail.ordOrderItemProds">
							<s:if test="#prod.productType=='OTHER'">
								<tr bgcolor="#ffffff">
									<td height="30">
										${prod.productId }
									</td>
									<td>
										<a class="showImportantTips" href="javascript:void(0)"
										productId="${prod.productId}" prodBranchId="${prod.prodBranchId}">${prod.productName}</a>
									</td>
									<td>
										${prod.marketPriceYuan }
									</td>
									<td>
										${prod.priceYuan }
									</td>
									<td>
										<s:if test="#prod.productType=='HOTEL'">
										${prod.hotelQuantity }
									</s:if>
										<s:else>
										${prod.quantity }
									</s:else>
									</td>
									<td>
										${prod.priceYuan*prod.quantity }
									</td>
									<td>
										${prod.zhProductType }
									</td>
									<td>
										${prod.zhVisitTime }
									</td>
								</tr>
							</s:if>
						</s:iterator>
					</tbody>
				</table>
				<p class="alignr2">
					合计：${orderDetail.oughtPayFloat }元
					<br />
					共节省：${orderDetail.saveAmountYuan}元
				</p>
			</div>
			<!--popbox end-->

			<!--=============用户信息=============-->
			<div class="popbox">
				<strong>用户信息</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td width="16%" height="25">
								卡号
							</td>
							<td width="16%">
								用户名
							</td>
							<td width="16%">
								用户姓名
							</td>
							<td width="16%">
								手机号
							</td>
							<td width="18%">
								现金账户余额
							</td>
							<td width="18%">
								返现账户余额
							</td>
						</tr>
						<tr bgcolor="#ffffff" align="center">
							<td height="25">
								${orderDetail.user.cardId }
							</td>
							<td>
								${orderDetail.user.userName }
							</td>
							<td>
								${orderDetail.user.realName }
							</td>
							<td>
								${orderDetail.user.mobileNumber }
							</td>
							<td></td>
							<td>
							</td>
						</tr>
					</tbody>
				</table>

				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="30" width="8%">
								<br>
								类别
							</td>
							<td width="8%">
								姓名
							</td>
							<td width="6%">
								联系电话
							</td>
							<td width="9%">
								Email
							</td>
							<td width="10%">
								证件类型
							</td>
							<td width="10%">
								证件号码
							</td>
							<td width="5%">
								邮编
							</td>
							<td width="17%">
								地址
							</td>
							<td width="5%">
								座机号
							</td>
							<td width="5%">
								传真
							</td>
							<td width="11%">
								传真接收人
							</td>
						</tr>
						<s:if test="orderDetail.contact!=null">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									取票人/联系人
								</td>
								<td>
									${orderDetail.contact.name }
								</td>
								<td>
									${orderDetail.contact.mobile }
								</td>
								<td>
									${orderDetail.contact.email }
								</td>
								<td>
									${orderDetail.contact.zhCertType }
								</td>
								<td>
									${orderDetail.contact.certNo }
								</td>
								<td>
									${orderDetail.contact.postcode }
								</td>
								<td>
									${orderDetail.contact.address }
								</td>
								<td>
									${orderDetail.contact.tel }
								</td>
								<td>
									${orderDetail.contact.fax }
								</td>
								<td>
									${orderDetail.contact.faxTo }
								</td>
							</tr>
							<script>
contactReceiverId = '${orderDetail.contact.receiverId }';
</script>
						</s:if>
					</tbody>
				</table>

				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04"
					style="margin-bottom: 20px;">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="30" width="8%">
								类别
							</td>
							<td width="8%">
								姓名
							</td>
							<td width="6%">
								联系电话
							</td>
							<td width="9%">
								Email
							</td>
							<td width="11%">
								证件类型
							</td>
							<td width="10%">
								证件号码
							</td>
							<td width="15%">
								备用联系方式
							</td>
							<td width="5%">
								邮编
							</td>
							<td width="20%">
								地址
							</td>
						</tr>
						<s:iterator id="person" value="orderDetail.personList">
							<s:if test="#person.personType == 'TRAVELLER'">
								<tr bgcolor="#ffffff" align="center">
									<td height="25">
										游客
									</td>
									<td>
										${person.name }
									</td>
									<td>
										${person.tel }
									</td>
									<td>
										${person.email }
									</td>
									<td>
										${person.zhCertType }
									</td>
									<td>
										${person.certNo }
									</td>
									<td>
										${person.memo }
									</td>
									<td>
										${person.postcode }
									</td>
									<td>
										${person.address }
									</td>
								</tr>
								<script>
existedTraveller[index++] = '${person.receiverId }';
</script>
							</s:if>
							<s:if test="#person.personType == 'ADDRESS'">
								<script>
selectedReceiverId = '${person.receiverId }';
selectedPersonId = '${person.personId }';
</script>
							</s:if>
						</s:iterator>
					</tbody>
				</table>
				<div class="btn09">
					<input type="button" value="修改/新增游客" class="right-button09"
						name="passed" onclick="javascript:updatePerson('${orderDetail.contact.personId }','${orderDetail.orderId }')">
				</div>
				<!--=============发票信息=============-->
				是否需要发票：
				<label>
					<input type="checkbox" name="needInvoice" id="isNeedInvoice"
						onclick="needFaPiao(this);doNeedInvoice();" />
					是
				</label>
				<br />
				<div href="<%=basePath%>ord/loadInvoices.do" id="invoiceDiv"
					style="display: none;"
					param="{'orderId':'${orderDetail.orderId }'}"></div>
				<!-- 发票信息 -->
				<!-- 地址信息 -->
				<div href="<%=basePath%>usrReceivers/loadAddresses.do"
					id="addressDiv"
					param="{userId: '${orderDetail.userId }', 'selectedReceiverId': selectedReceiverId}"
					style="display: none;"></div>
				<!-- 地址信息 -->
				<div class="btn09" id="addressBtns" style="display: none;">
					<input type="button" value="新增地址" class="right-button08"
						name="editPassed" onclick="showAddAddressDialg();">
					<input type="button" value="保存配送地址" class="right-button08"
						name="editPassed" onclick="doSaveExpressAdd();">
				</div>
				<br />
				<!--popbox end-->
				<!--=============订单备注=============-->
				<div href="<%=basePath%>ord/loadMemos.do" id="memoDiv"
					param="{'orderId':'${orderDetail.orderId }'}"></div>
				<!-- 订单备注 -->
				<p class="ordersuml2" id="youhuiContent"
					href="<%=basePath%>ajax/loadUsedYouhui.do"
					param="{orderId:'${orderDetail.orderId }'}">

				</p>
				<!--popbox end-->
				<p class="submitbtn">
					<s:if test="orderDetail.infoApproveStatus == 'UNVERIFIED'">
						<input type="button"
							onclick="checkApprove('${orderDetail.orderId }');"
							value="信息审核通过" class="left-button08">
					</s:if>
					<s:if test="orderDetail.approveStatus == 'RESOURCEPASS'">
						<input type="button"
							onclick="javascript:doApprovePass('doOrderApprovePass.do');"
							value="订单审核通过" class="left-button08">
					</s:if>
					<select name="cancelReason" id="cancelReason">
						<s:iterator id="reason" value="cancelReasons">
							<option value="${reason.code }">
								${reason.name }
							</option>
						</s:iterator>
					</select>
					<s:if test="orderDetail.isCancelAble()">
						<input type="button" onclick="doCancelOrder('approveDiv');"
							value="废 单" class="right-button08" />
					</s:if>
					<s:else>
						<span style="color: red">该订单不可退改或过了最晚废单时间</span>
					</s:else>
					<s:if test="orderDetail.approveStatus == 'UNVERIFIED'">
						<input type="button" id="btn_later" value="后续处理"
							class="right-button08" onclick="send_sms();">
					</s:if>
					<input name="redail" type="checkbox" id="redail"
						onclick="doNeedReplay();" />
					需重拨
				</p>
				<p class="submitbtn2">
					<s:if test="session.getAttribute('workTaskId')==null">
					<input type="button" class="button" value="关闭"
						onclick="javascript:closeDetailDiv('approveDiv');">
					</s:if>
					<s:if test="showSupplierChannelFlag">
						<input type="button" value="重新推送订单" class="right-button08" onclick="reSendOrder(${orderDetail.orderId})"/>
					</s:if>
					<s:if test="showSupplierChannelRecancelFlag">
						<input type="button" value="重推取消订单" class="right-button08" onclick="reSendCancelOrder(${orderDetail.orderId})"/>
					</s:if>
						<s:if test="showSupplierChannelRepayFlag">
					<input type="button" value="重推支付订单" class="right-button08" onclick="reSendPayOrder(${orderDetail.orderId})"/>
					</s:if>
				</p>
			</div>
		</div>
		<!-- 对话框 -->
		<div id="addReceiverDialg"
			style="position: absolute; z-index: 10000; display: none;">
		</div>
		<div id="editReceiverDialg"
			style="z-index: 300;margin-top:-700px;margin-left: -100px;display: none;">
		</div>
		<div id="addAddressDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="editAddressDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="addInvoiceDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="editInvoiceDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="infoQuestion" class="view-window"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<!--orderpopmain end-->
		
	</body>
</html>

