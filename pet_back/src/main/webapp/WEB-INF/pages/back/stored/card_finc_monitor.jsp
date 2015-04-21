<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<title>储值卡管理_储值卡财务监控管理</title>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_item.js"
			type="text/javascript"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
			
		<script type="text/javascript">
	$(function() {
		$("input[name='beginTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='endTime']").datepicker({dateFormat:'yy-mm-dd'});
	});
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		
	</head>
	<body>



		<div id="tg_order">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<form name='frmWaitOrder' id="frmWaitOrder" method='post'
							action='<%=basePath%>ordItem/opListWaitResult.do'>
							<input type="hidden" name="permId" value="${permId}"></input>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											出库时间：
										</td>
										<td>
											<input name="beginTime" type="text"
												value="${beginTime}" />
												~
											<input name="endTime" type="text"
												value="${endTime}" />
										</td>
										<td colspan="2" align="center">
											<input type='submit' name="btnQryOrdItemList" value="查询" class="right-button08" />
										</td>
									</tr>
								</table>
							</div>
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="6%">
											日期
										</td>
										<td width="6%">
											出库卡数量
										</td>
										<td width="6%">
											出库时间
										</td>
										<td width="5%">
											接收人
										</td>
										<td width="5%">
											制单人
										</td>
										<td width="8%">
											操作
										</td>
									</tr>
									<s:iterator value="waitAuditOrderList">
										<s:iterator value="allOrdOrderItemMetas">
										<s:if test='taken!="TAKEN"'>
											<tr bgcolor="#ffffff">
												<td><input type="checkbox" name="checkAuditName"
														value="${orderItemMetaId}"></input></td>
												<td height="30">
													${orderId}
												</td>
												<td>
													<span class="perm"><s:property value="takenOperator"/></span>
												</td>
												<td height="30">
													${orderItemMetaId}
												</td>
												<td>
													<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${metaProductId}&productType=${productType }',700,700)">${productName }</a>
												</td>
												<td>
													${settlementPriceYuan}
												</td>
												<td>
													<s:if test="productType=='HOTEL'">
											<s:property value="hotelQuantity"/>
									</s:if>
									<s:else>
											<s:property value="productQuantity*quantity"/>
								</s:else>
												</td>
												<td>
													${userName }
												</td>
												<td>
													${contact.name }
												</td>
												<td>
													<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<s:date name="visitTime" format="yyyy-MM-dd" />
												</td>
												<td>
													<s:if test="stockReduced">
														有库存下单
													</s:if>
													<s:else>
														无库存下单
													</s:else>
												</td>
												<td>
													${zhResourceStatus }
												</td>
											</tr>
											</s:if>
										</s:iterator>
									</s:iterator>
								</tbody>
							</table>
						</form>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

			<table width="90%" border="0" align="center">
			</table>
		</div>
		
	</body>
</html>
