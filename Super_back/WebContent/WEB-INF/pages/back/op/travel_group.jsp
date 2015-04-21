<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>团列表</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/affix_upload.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/lvmama_common.js"></script>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/cc.css" />
		<script type="text/javascript">
			var op_handle_url="<%=request.getContextPath()%>/op/change";
		</script>
		<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		
	</head>
	<body>
		<div  class="table_box">
			<div class="mrtit3" style="padding:10px;">
			<form id="search_form" method="post" action="<%=request.getContextPath()%>/op/travelGroupList.do">
				<table>
					<tr>
						<td>出团日期：</td><td><input type="text" name="startVisit" readonly="readonly" class="date" value="<s:date name="startVisit" format="yyyy-MM-dd"/>" style="width:100px;"/>到<input type="text" name="endVisit" value="<s:date name="endVisit" format="yyyy-MM-dd"/>" readonly="readonly" class="date" style="width:100px;"/></td>
						<td>团号：</td><td><input type="text" name="travelGroupCode" value="<s:property value="travelGroupCode"/>"/></td>
						<td>产品ID：</td><td><input type="text" name="productId" value="<s:property value="productId"/>"/></td>
						<td>产品名称：</td><td>
							<s:select list="productList" name="productName" listKey="productName" listValue="productName" headerKey="" headerValue="不限" cssStyle="width:240px;"></s:select>
							</td>
						<td>发团状态：</td><td>
						<s:select 
							list="#{'':'不限','CONFIRM':'已成团','CANCEL':'取消成团','NORMAL':'未成团'}"
							name="travelGroupStatus"							
						></s:select></td>
					</tr>
					<tr>
						<td>产品类型：</td><td colspan="6">	
								<s:checkboxlist name="subProducts" list="#{'GROUP':'短途跟团游','GROUP_LONG':'长途跟团游','GROUP_FOREIGN':'出境跟团游','FREENESS_LONG':'长途自由行','FREENESS_FOREIGN':'出境自由行','SELFHELP_BUS':'自助巴士班'}"/>					
						</td>
						<td colspan="2"><input type="checkbox" name="existsOrder" <s:if test="existsOrder!=null">checked</s:if> value="true"/>只显示存在订单的团</td>
						<td><input type="button" value="查询" class="right-button08" onclick="beforeSubmit();" />
						<input type='button' value="返  回" class="right-button08" onclick="javascript:history.go(-1)"/></td>
					</tr>
				</table>
				<!-- 该标志不要删除 -->
				<input type="hidden" name="nofirst"  value="true"/>
				<s:hidden name="sort"/>
			</form>
			</div>
		
		<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="100%" class="newfont06"
							style="font-size: 12; text-align: center;">
			<tr bgcolor="#eeeeee">
				<td height="35" width="10%">团号</td>
				<td width="18%">销售产品</td>
				<td width="5%">结算价/销售价</td>
				<td width="6%"><s:if test="sort==1"><a href="#sort" result="0" class="sort" title="改为升序">出团日期&nbsp;<img src="<%=request.getContextPath()%>/img/sort.png"/></a></s:if><s:else><a href="#sort" result="1" class="sort" class="改为降序">出团日期&nbsp;<img src="<%=request.getContextPath()%>/img/sort2.png"/></a></s:else></td>
				<td width="6%">计划人数</td>
				<td width="6%">全额支付人数</td>
				<td width="6%">部分支付人数</td>
				<td width="6%">未付款人数</td>
				<td width="6%">还剩人数</td>
				<td width="6%">最小成团人数</td>
				<td width="6%">行程天数</td>
				<td width="6%">成团日期</td>
				<td width="5%">
					出团状态
				</td>
				<td width="10%">
					操作
				</td>
			</tr>
			<s:iterator value="pagination.records" var="tg">
			<tr bgcolor="#ffffff">
				<td id="code_${tg.travelGroupId}">${tg.travelGroupCode}</td>
				<td>
					<a class="showImportantTips" href="javascript:void(0)"
						productId="${tg.productId}">${tg.productName}</a>
				</td>
				<td>${tg.settlementPriceStr}/${tg.sellPriceStr}</td>
				<td><s:date name="#tg.visitTime" format="yyyy-MM-dd"/></td>
				<td id="inital_group_num_${tg.travelGroupId}"><s:if test="#tg.initialGroupNum <0">不限</s:if><s:else>${tg.initialGroupNum}</s:else></td>
				<td><a href="<%=request.getContextPath()%>/op/opOrderList.do?travelCode=${tg.travelGroupCode}&paymentStatus=PAYED&order_Status=NORMAL">${tg.paySuccessNum}</a></td>
				<td><a href="<%=request.getContextPath()%>/op/opOrderList.do?travelCode=${tg.travelGroupCode}&paymentStatus=PARTPAY&order_Status=NORMAL">${tg.payPartNum}</a></td>
				<td><a href="<%=request.getContextPath()%>/op/opOrderList.do?travelCode=${tg.travelGroupCode}&paymentStatus=UNPAY&order_Status=NORMAL">${tg.payNotNum}</a></td>
				<td id="remain_${tg.travelGroupId}">${tg.remainStr}</td>
				<td>${tg.initialNum}</td>
				<td>${tg.days}</td>
				<td id="make_time_${tg.travelGroupId}"><s:if test="#tg.makeTime!=null"><s:date name="#tg.makeTime" format="yyyy-MM-dd HH:mm"/></s:if></td>
				<td id="status_<s:property value="#tg.travelGroupId"/>">${tg.travelGroupStatusStr}</td>
				<td>
					<a href="<%=request.getContextPath()%>/op/opOrderList.do?travelCode=${tg.travelGroupCode}">查看订单</a>
					<s:if test="#tg.travelGroupStatus=='NORMAL'"><a href="#changeStatus" class="changeStatus" v="CONFIRM" result="${tg.travelGroupId}">铁定成团</a></s:if>
					<s:if test="#tg.travelGroupStatus!='END' && #tg.travelGroupStatus!='CANCEL'"><a href="#changeStatus" class="changeStatus" v="CANCEL" result="${tg.travelGroupId}">取消发团</a></s:if>
					<a href="javascript:openWin('<%=request.getContextPath()%>/common/upload.do?objectId=${tg.travelGroupId}&objectType=OP_TRAVEL_GROUP',500,400)">上传文件</a>
					<%--<s:if test="#tg.travelGroupStatus!='END' && #tg.travelGroupStatus!='CANCEL'"><a href="#changeStatus" class="changeStatus" v="END" result="${tg.travelGroupId}">封团</a></s:if>--%>
					<!-- 取消此功能，改为自动发送出团通知书
						<s:if test="#tg.groupWordAble!='true'"><a href="#changeGroupWordAbled" class="groupwordabled" result="${tg.travelGroupId}">可发出团通知书</a></s:if>
					-->
					<a href="#changInitialNum" class="change_initial_num" result="${tg.travelGroupId}" current_num="${tg.initialGroupNum}">修改计划人数</a>
					<a href="javascript:openWin('<%=request.getContextPath()%>/log/viewSuperLog.zul?parentId=${tg.travelGroupId}&parentType=OP_TRAVEL_GROUP',700,400)">查看日志</a>
				</td>
			</tr>
			</s:iterator>
		</table>
		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
</div>
	<div style="display:none" id="change_initial_div">
		<table width="100%">
			<tr>
				<td class="title"></td>
			</tr>
			<tr>
				<td>计划人数:<input type="text" name="num"/><span class="msg" style="color:red"></span></td>
			</tr>
		</table>
	</div>
	<div style="display:none" id="cancelReason">
		<table width="100%">
			<tr>
				<td>取消原因:</td>
				<td><textarea id="cancelReasonDesc"></textarea>
				<input type="hidden" id="cancelReasonResult" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" value="保存" id="cancelReasonBtn" /></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		function beforeSubmit() {
			var startVisit = $('input[name="startVisit"]').val();
			var endVisit = $('input[name="endVisit"]').val();
			if(startVisit == "" || endVisit == "") {
				alert("必须选择出团日期");
				return;
			}
			document.getElementById("search_form").submit();
		}
	</script>
	</body>
</html>
