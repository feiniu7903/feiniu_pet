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
		<script language="javascript" src="<%=basePath%>js/ord/ord_item.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>
		<script src="${basePath}js/phoneorder/important_tips.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/ord/branch_pack.js"></script>
		<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<script type="text/javascript">
			var orderId='${orderDetail.orderId }', metaId='${orderItemMeta.orderItemMetaId }';
			$(document).ready(function () {
				$("#retentionTimeId").datepicker({dateFormat:'yy-mm-dd', "minDate":new Date()});
				$("#memoDiv").loadUrlHtml();
			});	
			$(".close_not_pass_layer").click(function(){
				$(".check_not_pass_reason").hide();
			});
			$("input[name=notpassreasonradio]").click(function(){
				var checkedRadio=$('input:radio[name="notpassreasonradio"]:checked').val();
				var input = $("#other_reason_input");
				if(checkedRadio == 'OTHER'){
					input.show();
				}else{
					//input.val("");
					input.hide();
				}
			});

			</script>
		<style type="text/css">
			.check_not_pass_reason{
				display: none;
				border: 1px solid #000000;
				background: none repeat scroll 0 0 #F1F1F1;
			    margin-left: 1px !important;
			    margin-top: -120px;
			    padding: 10px;
			    position: absolute;
			    width: 300px;
			    height: 130px;
			    z-index: 1003;
			   font:normal 12px/22px "宋体";
			}
			.close_not_pass_layer{
				cursor: pointer;
				margin-left: 280px;
				margin-top: -22px;
    			position: absolute;
			}
			table.box{ border-spacing:0; border-collapse:collapse;}
			table.box td{margin:0;padding:0;border:1px solid #aaa}		
			#selectMetaBranchDiv{margin:5px 1px;}	
		</style>
	</head>

	<body>
		<!--=========================资源确认弹出层==============================-->
		<div class="orderpoptit">
			<strong>资源确认审核：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('approveDiv');">
			</p>
		</div>
		<div class="orderpopmain" id="tg_order">
			<table width="100%" border="0" class="contactlist">
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
						<s:if test="orderDetail.hasNeedPrePay()">
						<s:date name="orderDetail.aheadTime" format="yyyy-MM-dd HH:mm"/>
						</s:if><s:else>${orderDetail.zhWaitPayment}</s:else>
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
						订单状态：${orderDetail.zhOrderStatus }
					</td>
				</tr>
				<tr>
					<td>
						订单来源渠道：${orderDetail.zhProductChannel }
					</td>
					<td colspan="3">
						资源保留时间：<s:property value="orderDetail.retentionTime"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						用户备注：${orderDetail.userMemo }
					</td>
					<td></td>
				</tr>
			</table>

			<!--=============需审核的产品=============-->
			<div class="popbox">
				
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tr bgcolor="#eeeeee">
						<td>产品ID</td>
						<td>主产品名称</td>
						<td>前台详情页</td>
						<td>销售价</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td><s:property value="orderDetail.mainProduct.productId"/></td>
						<td>
							<a class="showImportantTips" href="javascript:void(0)"
						productId="<s:property value="orderDetail.mainProduct.productId"/>"  prodBranchId="<s:property value="orderDetail.mainProduct.prodBranchId"/>"><s:property value="orderDetail.mainProduct.productName"/></a></td>
						<td><a href="http://www.lvmama.com/product/<s:property value="orderDetail.mainProduct.productId"/>" target="_blank">前台详情</a></td>
						<td><s:property value="orderDetail.mainProduct.priceYuan"/></td>
					</tr>
				</table>
				<h3 class="paytime" style="font-weight: normal;color:red">
					游玩时间：
					<s:if test="orderDetail.orderType=='HOTEL'">
						<s:iterator value="orderDetail.ordOrderItemProds">
							<s:if test="subProductType=='SINGLE_ROOM'">${dateRange}</s:if>
							<s:else>${zhVisitTime}</s:else>
						</s:iterator>
					</s:if>
					<s:else>
						${orderDetail.zhVisitTime }
					</s:else>					
				</h3>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td height="35" width="6%">
								子单号
							</td>
							<td width="15%">
								采购产品名称
							</td>
							<td width="15%">
								销售产品名称
							</td>
							<td width="5%">结算单价</td>
							<td width="5%">实际结算价</td>
							<td width="5%">结算总价</td>
							<td width="5%">
								数量
							</td>
							<td width="8%">
								产品类型
							</td>
							<td width="10%">
								供应商
							</td>							
							<td width="10%">
								资源保留时间
							</td>
							<td width="16%">
								传真备注
							</td>
							<td>
								
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td height="30">
								${orderItemMeta.orderItemMetaId}
							</td>
							<td>
								<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${orderItemMeta.metaProductId}&productType=${orderItemMeta.productType }',700,700)">${orderItemMeta.productName }</a>
							</td>
							<td><a class="showImportantTips" href="javascript:void(0)"
						productId="${productId }">${productName }</a></td>
							<td>
								${orderItemMeta.settlementPriceYuan}
							</td>
							<td>${ordOrderItemMetaPrice.actualSettlementPriceYuanStr }</td>
							<td>${ordOrderItemMetaPrice.totalSettlementPriceYuanStr }</td>
							<td>
							
							<s:if test='orderItemMeta.productType=="HOTEL"'>
										${orderItemMeta.hotelQuantity }
									</s:if>
									<s:else>
										<s:property value="orderItemMeta.productQuantity*orderItemMeta.quantity"/>
								</s:else>
								
							</td>
							<td>
								${orderItemMeta.zhProductType }
							</td>
							<td>
								<a href="javascript:openWin('/pet_back/sup/detail.do?supplierId=${orderItemMeta.supplier.supplierId}',700,700)">${orderItemMeta.supplier.supplierName }</a>
							</td>							
							<td>
							
							
								<table style="display:<s:if test="orderItemMeta.productType=='HOTEL' && orderItemMeta.paymentTarget=='TOLVMAMA' && orderItemMeta.isResourceSendFax=='true' ">none</s:if>">
									<tr>
										<td><input type="text" readonly="readonly" size="10" id="retentionTimeId" value="${currentDate }"/></td>
										<td>
											<select id="retentionHour">
												<s:bean name="org.apache.struts2.util.Counter" id="counter">
											        <s:param name="first" value="0" />
											        <s:param name="last" value="24" />
											        <s:iterator>  
											             <option value="<s:property/>"><s:property/></option>
											        </s:iterator>
											     </s:bean>
											</select>
										</td>
										<td> 
											<select id="retentionMinute">
												<s:bean name="org.apache.struts2.util.Counter" id="counter">
											        <s:param name="first" value="0" />
											        <s:param name="last" value="60" />
											        <s:iterator>  
											             <option value="<s:property/>"><s:property/></option>
											        </s:iterator>
											     </s:bean>
											</select>
										</td>
									</tr>
								</table>
								
							<s:if test="orderItemMeta.productType=='HOTEL' && orderItemMeta.paymentTarget=='TOLVMAMA' && orderItemMeta.isResourceSendFax=='true'">4小时</s:if>
							</td>
							<td>
								<input name="faxMemo" type="text" value="${orderItemMeta.faxMemo }" id="faxMemo" size="8" />
							</td>
							<td>
								<input type="button" onclick='doSaveFaxMemo();' value="保存传真备注">
								<s:if test="orderDetail.paymentTarget=='TOLVMAMA'">
								<input type="button" onclick='doShowChangeDialog()' value="修改采购产品"/>
								</s:if>
							</td>
						</tr>
					</tbody>
				</table>
				<br/>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td>日期</td>
							<td>是否减库存</td>
						</tr>
				<s:if test="orderDetail.orderType=='HOTEL'">
					<s:iterator value="orderDetail.ordOrderItemProds">	
						<s:if test="subProductType=='SINGLE_ROOM'">
							<s:iterator value="orderItemMeta.allOrdOrderItemMetaTime">
								<tr bgcolor="#ffffff">
									<td><s:property value="visitTime" /></td>
									<td><s:property value="zhStockReduced" /></td>
								</tr>
							</s:iterator>
						</s:if>
					</s:iterator>
				</s:if>
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
								
							</s:if>							
						</s:iterator>
					</tbody>
				</table>
			</div>
			<!--popbox end-->
			<div href="<%=basePath%>ord/loadMemos.do" id="memoDiv" class="popbox"
					param="{'orderId':'${orderDetail.orderId }'}"></div>
			<!--popbox end-->
			<p class="submitbtn">
			<s:if test="!orderDetail.isCanceled()">
				<s:if test="!orderDetail.isApprovePass()">
					<input type="button" onclick="doSaveResourceStatus('AMPLE','${orderDetail.orderId }');" value="资源满足">
					<input type="button" onclick="doSaveResourceStatus('LACK','${orderDetail.orderId }');" value="资源不满足">
				</s:if>
				<input type="button" onclick="doSaveResourceStatus('BEFOLLOWUP','${orderDetail.orderId }');" value="待跟进">
			</s:if>
			<s:else>
				<div style="color:red">订单取消，原因：${orderDetail.cancelReason }</div>
			</s:else>
			</p>
			<!-- 资源不满足层  选择/填写 原因,ADD BY liuboen -->
			<div  class="check_not_pass_reason">
					<h4>请选择不满足原因</h4> <a class="close_not_pass_layer">关闭</a>
						<input type="radio" name="notpassreasonradio" checked="checked" value="NO_RESOURCE"/>没有资源		<br/>
						<input type="radio" name="notpassreasonradio" value="PRICE_CHANGE"/>价格更改<br/>
						<input type="radio" name="notpassreasonradio" value="UNABLE_MEET_REQUIREMENTS"/>游客要求没法满足<br/>
						<input type="radio" name="notpassreasonradio" value="OTHER"  />其他<input type="text" style="display: none;" name="otherReasonInput" id="other_reason_input" maxlength="30"><br/>
						<input type="button" onclick="doSaveResourceStatusAndReason('${orderDetail.orderId }');" value="确定">
			</div>
			<!-- 资源不满足层,END -->
		</div>
		<!--=========================资源确认弹出层 end==============================-->
		<div id="changeOrderItemMateDiv" style="display:none">
		<form onsubmit="return false;"><input type="hidden" name="orderId" value="${orderItemMeta.orderId}"/><input type="hidden" name="orderMetaId" value="${orderItemMeta.orderItemMetaId}"/><input type="hidden" name="subProductType" id="change_subProductType" value="${orderItemMeta.subProductType}"/>
			<input type="hidden" name="productType" id="change_productType" value="${orderItemMeta.productType}"/>
			<input type="hidden" name="branchType"  value="${orderItemMeta.branchType}"/>
			<input type="hidden" name="adultQuantity"  value="${orderItemMeta.adultQuantity}"/>
			<input type="hidden" name="childQuantity"  value="${orderItemMeta.childQuantity}"/>
			<input type="hidden" name="visitTime" value="<s:date name="orderItemMeta.visitTime" format="yyyy-MM-dd"/>"/>
			<span>采购产品或ID:</span><input type="hidden" name="metaProductId"/><input type="text" name="searchMeta" id="searchMeta"/>
			<span>类别:</span><select name="metaBranchId" onchange="selectMetaBranch(this.selectedIndex)"></select>
			<div id="selectMetaBranchDiv"></div>
			<div style="color:red" id="changeOrderItemMateMsg"></div>
		</form>
		</div>
	</body>
</html>

