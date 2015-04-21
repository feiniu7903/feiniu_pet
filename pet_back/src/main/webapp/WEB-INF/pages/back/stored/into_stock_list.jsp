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
		<title>储值卡管理_卡入库管理_入库单查询</title>
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
		$("input[name='beginTime']").datepicker({dateFormat:'yy-mm-dd'});
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
					alert("您填写的入库单号格式不正确,只能为数字!"); 
					$("input[name='stockId']").val(""); 
				} 
			}
		})
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



		<div id="tg_intoStock">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='location.href="<%=basePath%>stored/intoStockDispatcher.do"'>
							入库单查询
						</li> 
						<li class="tags_none" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/intoStockGenerate.do"'>
							入库单生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmCardStockList' id="frmCardStockList" method='post'
							action='<%=basePath%>stored/intoStockList.do'>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											入库单号：
										</td>
										<td>
											<input name="stockId" type="text" value="${stockId}" />
										</td>
										<td>
											入库时间：
										</td>
										<td>
											<input name="beginTime" type="text"
												value="<s:date name="beginTime" format="yyyy-MM-dd"/>" />
												~
											<input name="endTime" type="text"
												value="<s:date name="endTime" format="yyyy-MM-dd"/>" />
										</td>
										<td colspan="2" align="center">
										 <!--mis:checkPerm permCode="3049">-->
											<input type='submit' name="intoStockList" value="查询" class="right-button08" />
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
											入库单号 
										</td>
										<td width="6%">
											入库卡数量
										</td>
										<td width="6%">
											入库时间
										</td>
										<td width="6%">
											入库单状态
										</td>
										<td width="5%">
											制单人
										</td>
										<td width="5%">
											操作
										</td>
									</tr>
									
									<s:iterator value="storedCardStockPage.items" var="cardStock">
											<tr bgcolor="#ffffff">
												<td>
													${cardStock.stockId}
												</td>
												<td>
													<a href="<%=basePath%>stored/cardList.do?intoStockId=${cardStock.stockId}">${cardStock.totalCount}</a>
												</td>
												<td>
													${cardStock.createTime}
												</td>
												<td>
													<s:if test="#cardStock.status=='NORMAL'">
													正常
													</s:if>
												</td>
												<td>
													${cardStock.operatorName}
												</td>
												<td>
													<a href="javascript:openWin('http://super.lvmama.com/super_back/log/viewSuperLog.zul?objectId=${cardStock.stockId}&objectType=STORED_CARD_STOCK',700,400)">查看日志</a>
												     <!--mis:checkPerm permCode="3050">-->	
													<a href="<%=basePath%>stored/intoStockCardExport.do?stockId=${cardStock.stockId}">导出</a>
												    <!--mis:checkPerm-->
												</td>
											</tr>
									</s:iterator>
								</tbody>
							</table>
						</form>
					</div>
					<!--=========================主体内容 end==============================-->
					<div class="orderpop" id="cancleStockDiv" style="display: none;"
					    href="<%=basePath%>stored/cancleStockByParam.do">
				    </div>
				</div>
				<!--main2 end-->
				</div>
				<table width="90%" border="0" align="center">
					<tr bgcolor="#ffffff">
						<td colspan="2">
							总条数：<s:property value="storedCardStockPage.totalResultSize"/>
						</td>
						<td colspan="7" align="right">
							<s:property escape="false" 
								value="@com.lvmama.comm.utils.Pagination@pagination(storedCardStockPage)"/>
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>
