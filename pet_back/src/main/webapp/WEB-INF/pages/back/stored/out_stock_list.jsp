<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>储值卡管理_卡出库管理_出库单查询</title>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" 	href="<%=request.getContextPath()%>/themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript">
	var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
	var STOCK_ID_FORMAT = /^[0-9]*$/;
	$(function() {
		$("input[name='beginTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='endTime']").datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='beginTime']").bind("blur",function(){
			var beginTime = $("input[name='beginTime']").val();
			if(beginTime != ""){
				if(!DATE_FORMAT.test(beginTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='beginTime']").val("");
				}
			}
		})
		$("input[name='endTime']").bind("blur",function(){
			var endTime = $("input[name='endTime']").val();
			if(endTime != ""){
				if(!DATE_FORMAT.test(endTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='endTime']").val("");
				}
			}
		})
		$("input[name='stockId']").bind("blur",function(){ 
			var stockId = $("input[name='stockId']").val().trim(); 
			if(stockId != ""){
				if(!STOCK_ID_FORMAT.test(stockId)){ 
					alert("您填写的出库单号格式不正确,只能为数字!"); 
					$("input[name='stockId']").val(""); 
				} 
			}
		})

		$("a.status").click(function(){
					var $dlg=$("#status_dialog");
					var stockId=$(this).attr("stockId");
					var $td=$("#card_status_"+stockId);
					var current=$td.attr("result");
					
					$dlg.dialog({
						"title":"修改储值卡状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("textarea").val();
								if(newVal===current){
									alert("请填写作废原因 !!");
									return false;
								}else{
									$.post("<%=basePath%>stored/cancleOutStockByParam.do",{"stockId":stockId,"cancleReason":newVal},function(dt){
										var data=eval("("+dt+")");
										if(data.flag){
											$("#count_"+stockId).html("0");
											$("#status_"+stockId).html("作废");
											$("#cancle_"+stockId).html("");
											$("#active_"+stockId).html("");
											$dlg.dialog("close");
										} else {
											alert("抱歉，该出库单不符合作废条件，不能执行作废操作。");
											$dlg.dialog("close");
											
										}
									});
								}
							},
							"取消":function(){
								$dlg.dialog("close");
							}
						}
					});
				});
	});
	function showDetailDiv(divName, stockId) {
		document.getElementById(divName).style.display = "block";
		document.getElementById("bg").style.display = "block";
		//请求数据,重新载入层
		$("#" + divName).reload({"stockId":stockId});
	}
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
	</head>
	<body>



		<div id="tg_outStock">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='location.href="<%=basePath%>stored/outStockDispatcher.do"'>
							出库单查询
						</li> 
						<li class="tags_none" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/outStockGenerate.do"'>
							出库单生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmCardStockList' id="frmCardStockList" method='post'
							action='<%=basePath%>stored/outStockList.do'>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											出库单号：
										</td>
										<td>
											<input name="stockId" type="text" value="${stockId}" />
										</td>
										<td>
											出库时间：
										</td>
										<td>
											<input name="beginTime" type="text"
												value="<s:date name="beginTime" format="yyyy-MM-dd"/>" />
												~
											<input name="endTime" type="text"
												value="<s:date name="endTime" format="yyyy-MM-dd"/>" />
										</td>
										<td colspan="2" align="center">
										 <!--mis:checkPerm permCode="3053">-->
											<input type='submit' name="outStockList" value="查询" class="right-button08" />
											<!--mis:checkPerm-->
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
											出库单号
										</td>
										<td width="6%">
											出库卡数量
										</td>
										<td width="6%">
											出库时间
										</td>
										<td width="6%">
											出库单状态
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
									<s:iterator value="outStockPage.items" var="cardStock">
											<tr bgcolor="#ffffff">
												<td>
													<a href="javascript:showDetailDiv('cardStockDetailDiv', '${cardStock.stockId}');">${cardStock.stockId}</a>
												</td>
												<td id="count_${cardStock.stockId}">
													<a href="<%=basePath%>stored/cardList.do?outStockId=${cardStock.stockId}">${cardStock.totalCount}</a>
												</td>
												<td>
													${cardStock.createTime}
												</td>
												<td id="status_${cardStock.stockId}">
													<s:if test="#cardStock.status == 'NORMAL'">
													正常
													</s:if>
													<s:elseif test="#cardStock.status == 'CANCEL'">
													作废
													</s:elseif>
												</td>
												<td id="accepter_${cardStock.stockId}">
													${cardStock.accepter}
												</td>
												<td>
													${cardStock.operatorName}
												</td>
												<td>
													<a href="javascript:openWin('http://super.lvmama.com/super_back/log/viewSuperLog.zul?objectId=${cardStock.stockId}&objectType=STORED_CARD_STOCK',700,400)">查看日志</a>
													<s:if test="#cardStock.status == 'NORMAL'">
														<span id="cancle_${cardStock.stockId}">
														 <!--mis:checkPerm permCode="3054">-->
															<a href="<%=basePath%>stored/outStockCardExport.do?stockId=${cardStock.stockId}">导出</a>
															<!--mis:checkPerm-->
															 <!--mis:checkPerm permCode="3055">-->
															<a href="javascript:void(0);" class="status" stockId="${cardStock.stockId}">作废</a>
															<!--mis:checkPerm-->
														</span>
														<span id="active_${cardStock.stockId}">
														    <!--mis:checkPerm permCode="3056">-->
															<a href="javascript:showDetailDiv('cardStockActiveDiv', '${cardStock.stockId}');">激活</a>
															<!--mis:checkPerm-->
														</span>
														<span id="update_${cardStock.stockId}">
														    <!--mis:checkPerm permCode="3056">-->
															<a href="javascript:showDetailDiv('cardStockDetailToUpdateDiv', '${cardStock.stockId}');">修改</a>
															<!--mis:checkPerm-->
														</span>
													</s:if>
												</td>
											</tr>
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
				<tr bgcolor="#ffffff">
					<td colspan="2">
						总条数：<s:property value="outStockPage.totalResultSize"/>
					</td>
					<td colspan="7" align="right">
						<s:property escape="false" 
							value="@com.lvmama.comm.utils.Pagination@pagination(outStockPage)"/>
					</td>
				</tr>
			</table>
			<div class="orderpop" id="cardStockDetailDiv" style="display: none;" href="<%=basePath%>stored/outCardStockDetail.do">
			</div>
			<div class="orderpop" id="cardStockDetailToUpdateDiv" style="display: none;" href="<%=basePath%>stored/outCardStockDetailToUpdate.do">
			</div>
			<div class="orderpop" id="cardStockActiveDiv" style="display: none; width: 40%;" href="<%=basePath%>stored/activeCardVerify.do">
			</div>
			<div id="status_dialog" style="display: none">
				<div>储值卡作废原因</div>
				<form>
					<s:textarea cols="42" rows="10" ></s:textarea>
				</form>
			</div>
			<div id="bg" class="bg" style="display: none;">
				<iframe style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
				</iframe>
			</div>
		</div>
	</body>
</html>
