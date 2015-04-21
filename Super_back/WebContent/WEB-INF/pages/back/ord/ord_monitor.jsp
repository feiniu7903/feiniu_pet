<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>

		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			var orderId, contactId;
$(document).ready(function () {
	orderId = $("#orderId").val();
	contactId = $("#contactId").val();
	$("#usrReceiversList").loadUrlHtml();
	$("#youhuiContent").loadUrlHtml();
	$("#memoDiv").loadUrlHtml();
	$("#editReceiverDialg").dialog({autoOpen:false, width:600, height:350, buttons:{"\u63d0\u4ea4":function () {
		editReceiver();
	}, "\u6e05\u7a7a":function () {
	}, "\u5173\u95ed":function () {
		$(this).dialog("close");
	}}, close:function () {
		$("#addReceiverDialg").html("");
	}});
	$("#addReceiverDialg").dialog({autoOpen:false, width:600, height:350, buttons:{"\u63d0\u4ea4":function () {
		addReceivers();
	}, "\u6e05\u7a7a":function () {
	}, "\u5173\u95ed":function () {
		$(this).dialog("close");
	}}, close:function () {
		$("#editReceiverDialg").html("");
	}});
});
			</script>
	</head>

	<body>
		<!--=========================我的审核任务弹出层==============================-->
		<input type="hidden" id="orderId"
			value="${monitorOrderDetail.orderId }" />
		<input name="userId" type="hidden" id="userId" value="${monitorOrderDetail.userId }" />
		<input type="hidden" id="contactId"
			value="${monitorOrderDetail.contact.personId }" />
		<div class="orderpoptit">
			<strong>审核订单：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeApproveDiv()">
			</p>
		</div>
		<div class="orderpopmain">
			<table width="100%" border="0" class="contactlist">
				<tr>
					<td width="25%">
						订单号：${monitorOrderDetail.orderId }
					</td>
					<td width="25%">
						下单时间:${monitorOrderDetail.zhCreateTime }
					</td>
					<td width="25%">
						下单人：${monitorOrderDetail.userName }
					</td>
					<td width="25%">
						支付等待时间：
						<s:select name="monitorOrderDetail.waitPayment"
							list="#{30:'30分钟',60:'1小时',1440:'24小时',2880:'48小时',-1:'不限'}" id="waitPayment">
						</s:select>
					</td>
				</tr>
				<tr>
					<td>
						应付金额：${monitorOrderDetail.oughtPayYuan }
					</td>
					<td>
						实付金额：${monitorOrderDetail.actualPayYuan }
					</td>
					<td>
						支付状态：${monitorOrderDetail.zhPaymentStatus }
					</td>
					<td>
						订单状态：${monitorOrderDetail.zhOrderStatus }
					</td>
				</tr>
				<tr>
					<td colspan="4">
						订单来源渠道：${monitorOrderDetail.zhProductChannel }
					</td>
				</tr>
				<tr>
					<td colspan="3">
						用户备注：${monitorOrderDetail.userMemo }
					</td>
					<td>
						<input type="button" value="保 存" class="right-button02"
							onclick="doModifyWaitPayment('/ord/doModifyWaitPayment.do');">
					</td>
				</tr>
			</table>

			<!--=============商品清单=============-->
			<div class="popbox">
				<strong>商品清单</strong>
				<p class="paytime">
					游玩时间：
					<s:if test="monitorOrderDetail.orderType=='HOTEL'">
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
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr class="CTitle">
							<td height="22" align="center" colspan="13">
								商品清单
							</td>
						</tr>
						<tr bgcolor="#eeeeee">
							<td height="35" width="5%">
								序号
							</td>
							<td width="16%">
								产品名称
							</td>
							<td width="7%">
								市场价
							</td>
							<td width="7%">
								会员价
							</td>
							<td width="5%">
								数量
							</td>
							<td width="8%">
								总金额
							</td>
							<td width="8%">
								产品类型
							</td>
							<td width="5%">
								优惠
							</td>
							<td width="7%">
								供应商
							</td>
							<td width="10%">
								游玩时间
							</td>
						</tr>
						<s:iterator id="prod" value="monitorOrderDetail.ordOrderItemProds">
							<s:if test="#prod.productType!='OTHER'">
								<tr bgcolor="#ffffff">
									<td height="30">
										${prod.productId }
									</td>
									<td>
										${prod.productName }
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
										<s:if test="#prod.additional=='false'">
												无
											</s:if>
										<s:else>
												有
											</s:else>
									</td>
									<td>
										<s:iterator id="meta" value="#prod.ordOrderItemMetas">
											<a href="javascript:openWin('/pet_back/sup/detail.do?supplierId=${meta.supplier.supplierId}',700,700)">	${meta.supplier.supplierName }</a><br />
										</s:iterator>
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
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont06">
					<tbody>
						<tr class="CTitle">
							<td height="20" align="center" colspan="13">
								附加产品
							</td>
						</tr>
						<tr bgcolor="#eeeeee">
							<td height="35" width="5%">
								序号
							</td>
							<td width="18%">
								产品名称
							</td>
							<td width="8%">
								市场价
							</td>
							<td width="8%">
								会员价
							</td>
							<td width="8%">
								数量
							</td>
							<td width="8%">
								总金额
							</td>
							<td width="10%">
								产品类型
							</td>
							<td width="10%">
								游玩时间
							</td>
						</tr>
						<s:iterator id="prod" value="monitorOrderDetail.ordOrderItemProds">
							<s:if test="#prod.productType=='OTHER'">
								<tr bgcolor="#ffffff">
									<td height="30">
										${prod.productId }
									</td>
									<td>
										${prod.productName }
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
				<p class="alignr2">
					合计：${monitorOrderDetail.actualPayFloat }元
					<br />
					共节省：元
				</p>
			</div>
			<!--popbox end-->

			<!--=============用户信息=============-->
			<div class="popbox">
				<strong>用户信息</strong>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#B8C9D6"
					width="100%" class="newfont03">
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
								${monitorOrderDetail.user.cardId }
							</td>
							<td>
								${monitorOrderDetail.user.userName }
							</td>
							<td>
								${monitorOrderDetail.user.realName }
							</td>
							<td>
								${monitorOrderDetail.user.mobileNumber }
							</td>
							<td></td>
							<td>
							</td>
						</tr>
					</tbody>
				</table>

				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#B8C9D6"
					width="100%" class="newfont04">
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
							<td width="6%">
								操作
							</td>
						</tr>
						<s:if test="monitorOrderDetail.contact!=null">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									取票人/联系人
								</td>
								<td>
									${monitorOrderDetail.contact.name }
								</td>
								<td>
									${monitorOrderDetail.contact.tel }
								</td>
								<td>
									${monitorOrderDetail.contact.email }
								</td>
								<td>
									${monitorOrderDetail.contact.certType }
								</td>
								<td>
									${monitorOrderDetail.contact.certNo }
								</td>
								<td>
									${monitorOrderDetail.contact.postcode }
								</td>
								<td>
									${monitorOrderDetail.contact.address }
								</td>
								<td>
									${monitorOrderDetail.contact.mobile }
								</td>
								<td>
									${monitorOrderDetail.contact.fax }
								</td>
								<td>
									${monitorOrderDetail.contact.faxTo }
								</td>
								<td>
									<a
										href="javascript:doDelete('/ord/doDeletePerson.do', '${monitorOrderDetail.contact.personId }')">删除</a>
								</td>
							</tr>
						</s:if>
					</tbody>
				</table>

				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#B8C9D6"
					width="100%" class="newfont04" style="margin-bottom: 20px;">
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
							<td width="6%">
								操作
							</td>
						</tr>
						<s:iterator id="person" value="monitorOrderDetail.personList">
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
								<td>
									<a
										href="javascript:doDelete('/ord/doDeletePerson.do', '${person.personId }')">删除</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<!-- 联系人部分 -->
				<!--=============发票信息=============-->
				<div id="usrReceiversList"
					href="<%=basePath%>usrReceivers/loadList.do"
					param="{userId:'${monitorOrderDetail.userId }'}">
				</div>
				<div class="btn09">
					<input type="button" value="新增" class="right-button08"
						name="passed" onclick="showAddReceiverDialg();">
					<input type="button" value="修改" class="right-button08"
						name="passed" onclick="showEditReceiverDialg();">
					<input type="button" value="保存为旅客" class="right-button08"
						onclick="addVisitor();">
					<input type="button" value="保存为取票人" class="right-button08"
						onclick="addContactor();">
				</div>
				<div class="popbox">
					<strong>发票信息</strong>
					<table cellspacing="1" cellpadding="4" border="0" bgcolor="#B8C9D6"
						width="100%" class="newfont03">
						<tbody>
							<tr bgcolor="#f4f4f4" align="center">
								<td width="21%" height="25">
									发票号码
								</td>
								<td width="14%">
									发票抬头
								</td>
								<td width="14%">
									发票内容
								</td>
								<td width="15%">
									发票金额
								</td>
								<td width="11%">
									发票备注
								</td>
								<td width="11%">
									开票时间
								</td>
								<td width="14%">
									操作
								</td>
							</tr>
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									EX0215879562
								</td>
								<td>
									个人
								</td>
								<td>
									内容
								</td>
								<td>
									300
								</td>
								<td>
									备注信息
								</td>
								<td>
									2010-09-31
								</td>
								<td>
									<a href="#">删除</a>
									<a href="javascript:void(0)" name="passed6">查看</a>
								</td>
							</tr>
						</tbody>
					</table>
					<p class="alignr">
						<input type="button" value="新增地址" class="right-button08"
							name="passed2">
						<input type="button" value="新增开票记录" class="right-button08"
							name="passed7">
					</p>
				</div>
				<!--popbox end-->
					<div href="<%=basePath%>ord/loadMemos.do" id="memoDiv"
					param="{'orderId':'${monitorOrderDetail.orderId }'}"></div>
				<!--=============订单备注=============-->
					<p class="ordersuml2" id="youhuiContent" href="<%=basePath%>ajax/loadUsedYouhui.do" param="{orderId:'${monitorOrderDetail.orderId }'}">
					
					</p>
				<!--popbox end-->
				<p class="submitbtn">
					<input type="button"
						onclick="javascript:httpRequest('/ord/doInfoApprovePass.do', {'orderId': orderId});"
						value="信息审核通过" class="left-button08">
					<input type="button"
						onclick="javascript:httpRequest('/ord/doOrderApprovePass.do', {'orderId': orderId});"
						value="订单审核通过" class="left-button08">
					<select name="cancelReason" id="cancelReason">
						<s:iterator id="reason" value="cancelReasons">
							<option value="${reason.code }">
								${reason.name }
							</option>
						</s:iterator>
					</select>
					<s:if test="monitorOrderDetail.isCancelAble()">
						<input type="button" onclick="doCancelOrder();" value="废 单"
							class="right-button08">
					</s:if>
					<s:else>
						<span style="color: red">该订单不可退改或过了最晚废单时间</span>
					</s:else>
					<input name="redail" type="checkbox" id="redail" onclick="doNeedReplay();" />
					需重播
				</p>
				<p class="submitbtn2">
					<input type="button" class="button" value="关闭"
						onclick="javascript:closeApproveDiv()">
				</p>
			</div>
		</div>
		<!-- 对话框 -->
		<div id="addReceiverDialg"
			href="<%=basePath%>usrReceivers/toAddReciever.do">
		</div>

		<div id="editReceiverDialg">
		</div>
		<!--orderpopmain end-->
		<script type="text/javascript">
			document.getElementById("redail").checked = ${monitorOrderDetail.redail};
		</script>
	</body>
</html>

