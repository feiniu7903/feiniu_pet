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
		<title>储值卡管理后台_卡入库管理_入库单生成</title>
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
	function showSaveIntoStockMessage(){
		var beginSerialNoStr = $("input[name='beginSerialNo']").val();
		var endSerialNoStr = $("input[name='endSerialNo']").val();
		if(beginSerialNoStr == "" || endSerialNoStr == ""){
			alert("请填写起始、结束流水号，并请注意：如果需要多次查询，流水号范围不可出现交叉。");
			return;
		}
		var amountStrArray = $("input[name='stockIdList']");
		var amountStr = "";
		$.each(amountStrArray, function(key, val) {
			amountStr = amountStr + ',' + val.value;
		});
				
		$.ajax( {
			type : "POST",
			dataType : "json",
			data : {beginSerialNo:beginSerialNoStr,endSerialNo:endSerialNoStr,amountStr:amountStr},
			url : "<%=basePath%>stored/intoStockSave.do",
			async : false,
			success : function(data) {
				if (data.jsonMsg == "ok") {
					alert("储值卡成功入库!入库单单号为：" + data.stockId);
					window.location="<%=basePath%>stored/intoStockGenerate.do";
				} else if (data.jsonMsg == "nothing") {
					alert("抱歉,未查到该批次号区间内的储值卡");
					window.location="<%=basePath%>stored/intoStockGenerate.do";
				}
			}
		});
	}
	
	function showQueryCardMessage(){
		var beginSerialNoStr = $("input[name='beginSerialNo']").val();
		var endSerialNoStr = $("input[name='endSerialNo']").val();
		if(beginSerialNoStr == "" || endSerialNoStr == ""){
			alert("请填写起始、结束流水号，并请注意：如果需要多次查询，流水号范围不可出现交叉。");
			return;
		}
		var amountStrArray = $("input[name='stockIdList']");
		var amoutStrArrayTemp = new Array();
		$.each(amountStrArray, function(key, val) {
			amoutStrArrayTemp.push(val.value);
		});
		
		var amountStrJson = JSON.stringify(amoutStrArrayTemp);
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {beginSerialNo:beginSerialNoStr,endSerialNo:endSerialNoStr},
			url : "<%=basePath%>stored/showIntoStockCardQueryMessage.do",
			async : false,
			success : function(data){
				if(data.jsonMsg == "nothing"){
					alert("抱歉,此范围内未查询到任何储值卡,请核实这些储值卡是否符合入库条件!");
				}
			}
		});
	}
	
	function deleteRow(obj){
	    var index=obj.parentNode.rowIndex;
	    var table = document.getElementById("stockListTable");
	    table.deleteRow(index);
	}
	
	function deleteAmount(amountStr){
		var notInAmountStr = $("#notInAmountList").val();
		if (notInAmountStr==null || notInAmountStr == ''){
			notInAmountStr = amountStr;
		}else{
			notInAmountStr = notInAmountStr + ',' + amountStr;
		}
		$("#notInAmountList").val(notInAmountStr);
		$("#frmStockCardQuery").submit();
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
						<li class="tags_none" id="tags_title_1" onclick='location.href="<%=basePath%>stored/intoStockDispatcher.do"'>
							入库单查询
						</li> 
						<li class="tags_at" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/intoStockGenerate.do"'>
							入库单生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmStockCardQuery' id="frmStockCardQuery" method='post'
							action='<%=basePath%>stored/intoStockCardQuery.do'>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											流水号：
										</td>
										<td>
											<input name="beginSerialNo" type="text" value="${beginSerialNo}" />
												~
											<input name="endSerialNo" type="text" value="${endSerialNo}" />
										</td>
										<td>
											<input name="notInAmountList" id="notInAmountList"type="hidden" value="${notInAmountList}" />
										</td>
										<td colspan="2" align="center">
										    <!--mis:checkPerm permCode="3051">-->
											<input type='submit' name="stockCardQuery" value="查询" class="right-button08" onclick="showQueryCardMessage();"/>
											<!--mis:checkPerm-->
										</td>
									</tr>
								</table>
							</div>
							<table id="stockListTable" cellspacing="1"  border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">										
										<td width="6%">
											流水号范围
										</td>
										<td width="6%">
											面值
										</td>
										<td width="6%">
											数量
										</td>
										<td width="6%">
											总金额
										</td>
										<td width="6%">
											操作
										</td>
									</tr>
									<s:if test="cardStatisticsList != null">
									<s:set var="size" value="cardStatisticsList.size"></s:set>
									<s:iterator value="cardStatisticsList" var="statistics" status="index" >
										<s:if test="%{#index.count != #size}">
											<tr bgcolor="#ffffff">
												<td>
													<input type="hidden" name="stockIdList" value=${statistics.amountStr}>										
													${statistics.beginSerialNo} ~ ${statistics.endSerialNo}
												</td>
												<td>
													${statistics.amount}
												</td>
												<td>
													${statistics.totalCount}
												</td>
												<td>
													${statistics.totalAmount}
												</td>
												 <!--mis:checkPerm permCode="3051">-->
												<td><a href="javascript:deleteAmount(${statistics.amountStr})">删除</a></td>
												<!--mis:checkPerm-->
											</tr>
										</s:if>
										<s:if test="%{#index.count == #size}">
											<tr bgcolor="#ffffff">
												<td></td>
												<td>总计</td>
												<td><s:property value="cardStatisticsList[#size-1].totalTotalCount"/></td>
												<td><s:property value="cardStatisticsList[#size-1].totalTotalAmount"/></td>
												<td></td>
											</tr>
										</s:if>
									</s:iterator>
									</s:if>
								</tbody>
							</table>
							<s:if test="cardStatisticsList != null && !cardStatisticsList.isEmpty()">
								<div class="mrtit3">
									<table width="100%" border="0" style="font-size: 12;">
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td colspan="2" align="right">
											 <!--mis:checkPerm permCode="3052">-->
												<input type="button" onclick="showSaveIntoStockMessage();" value="确定" class="right-button08" />
												<!--mis:checkPerm-->
											</td>
										</tr>
									</table>
								</div>
						   </s:if>
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
