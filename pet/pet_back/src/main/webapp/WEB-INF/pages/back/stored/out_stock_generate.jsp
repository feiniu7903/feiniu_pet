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
		<title>储值卡管理_卡出库管理_出库单生成</title>
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
	function showSaveOutStockMessage(){
		var customerStr = $("input[name='customer']").val();
		var accepterStr = $("input[name='accepter']").val();
		var memoStr = $("textarea[name='memo']").val();
		var beginSerialNoStr = $("input[name='beginSerialNo']").val();
		var endSerialNoStr = $("input[name='endSerialNo']").val();
		var notInAmountStr = $("#notInAmountList").val();

		$.ajax( {
			type : "POST",
			dataType : "json",
			data : {customer:customerStr,accepter:accepterStr,memo:memoStr,beginSerialNo:beginSerialNoStr,endSerialNo:endSerialNoStr,notInAmountList:notInAmountStr},
			url : "<%=basePath%>stored/outStockSave.do",
			async : false,
			success : function(data) {
				if (data.jsonMsg == "ok") {
					alert("储值卡成功出库!出库单单号为：" + data.stockId);
					window.location="<%=basePath%>stored/outStockGenerate.do";
				} else if (data.jsonMsg == "nothing") {
					alert("抱歉,未查到该批次号区间内的储值卡");
					window.location="<%=basePath%>stored/outStockGenerate.do";
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
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {beginSerialNo:beginSerialNoStr,endSerialNo:endSerialNoStr},
			url : "<%=basePath%>stored/showOutStockCardQueryMessage.do",
			async : false,
			success : function(data){
				if(data.jsonMsg == "nothing"){
					alert("抱歉,此范围内未查询到任何储值卡,请核实这些储值卡是否符合出库条件!");
				}
			}
		});
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



		<div id="tg_outStock">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_none" id="tags_title_1" onclick='location.href="<%=basePath%>stored/outStockDispatcher.do"'>
							出库单查询
						</li> 
						<li class="tags_at" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/outStockGenerate.do"'>
							出库单生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmStockCardQuery' id="frmStockCardQuery" method='post'
							action='<%=basePath%>stored/outStockCardQuery.do'>
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
										<td align="center">
										    <!--mis:checkPerm permCode="3057">-->
											<input type='submit' name="outStockCardQuery" value="查询" class="right-button08" onclick="showQueryCardMessage();"/>
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
										<td width="8%">
											操作
										</td>
									</tr>
									<s:if test="cardStatisticsList != null">
									<s:set var="size" value="cardStatisticsList.size"></s:set>
									<s:iterator value="cardStatisticsList" var="statistics" status="index" >
										<s:if test="%{#index.count != #size}">
											<tr bgcolor="#ffffff">
												<td>
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
												<td>
												    <!--mis:checkPerm permCode="3058">-->
													<a href="javascript:deleteAmount(${statistics.amountStr})">删除</a>
													<!--mis:checkPerm-->
												</td>
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
							<div class="mrtit3">
									<table width="100%" border="0" style="font-size: 12;">
											<tr>
												<td width="10%">客户：</td>
												<td width="10%"><input name="customer" type="text" value="${customer}"/></td>
												<td width="10%">接收人：</td>
												<td width="10%"><input name="accepter" type="text" value="${accepter}"/></td>
												<td width="60%"></td>
											</tr>
											<tr>
												<td>备注信息：</td>
												<td>
													<textarea name="memo" cols="40" rows="5"></textarea>
												</td>
												<td colspan="3"></td>
											</tr>
										<tr>
											<td colspan="4"></td>
											<td align="right">
											 <mis:checkPerm permCode="3437">
												<input type="button" onclick="showSaveOutStockMessage();" value="确定" class="right-button08" />
											 </mis:checkPerm>
											</td>
										</tr>
									</table>
								</div>
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
