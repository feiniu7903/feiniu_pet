<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——行程展示</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/base/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
<script type="text/javascript" src="<%= basePath%>js/prod/sensitive_word.js"></script>
<link href="<%=request.getContextPath()%>/themes/base/showLoading.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=request.getContextPath()%>/themes/suggest/jquery.suggest.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript">
	$(function() {
		var $edit_multi_journey_div = null;
		var $edit_fee_detail_div = null;
		$(".editMultiJourney")
				.click(
						function() {
							var productId = $("#productId").val();
							if(productId == "") {
								alert("数据异常！");
								return fasle;
							}
							if ($edit_multi_journey_div == null) {
								$edit_multi_journey_div = $("<div style='display:none' id='edit_multi_journey_div'>");
								$edit_multi_journey_div.appendTo($("body"));
							}
							var tit = $(this).attr("title");
							var data = {'productId': productId};
							var d = $(this).attr("data");
							if(typeof(d) != 'undefined') {
								data ['viewMultiJourney.multiJourneyId'] = d;
							}
							var cy = $(this).attr("copy");
							if(typeof(cy) != 'undefined') {
								data ['copy'] = cy;
							}
							$edit_multi_journey_div.load(
									"/super_back/view/toEditMultiJourney.do", data,
									function() {
										$edit_multi_journey_div.dialog({
											title : tit + "行程列表",
											width : 800,
											modal : true
										});
									});
						});
		
		$(".editFeeDetail")
		.click(
				function() {
					var productId = $("#productId").val();
					if(productId == "") {
						alert("数据异常！");
						return fasle;
					}
					if ($edit_fee_detail_div == null) {
						$edit_fee_detail_div = $("<div style='display:none' id='edit_fee_detail_div'>");
						$edit_fee_detail_div.appendTo($("body"));
					}
					var data = {'productId': productId};
					var d = $(this).attr("data");
					if(typeof(d) != 'undefined') {
						data ['multiJourneyId'] = d;
					}
					$edit_fee_detail_div.load(
							"/super_back/view/toEditFeeDetail.do", data,
							function() {
								$edit_fee_detail_div.dialog({
									title : "费用说明",
									width : 1000,
									modal : true
								});
							});
				});
		
		$(".changeValidStatus").click(function() {
			var productId = $("#productId").val();
			if(productId == "") {
				alert("数据异常！");
				return fasle;
			}
			var d = $(this).attr("data"); 
			if(typeof(d) == 'undefined' || d == "") {
				alert("数据异常!");
				return false;
			}
			var status = $(this).attr("status");
			if(status == 'Y') {
				if(!confirm("设置为无效，将会删除对应的时间价格表，是否确认操作？")){
					return false;
				}
			}
			$.ajax( {
				type : "POST",
				url : "/super_back/view/changeValidStatus.do",
				data: {'viewMultiJourney.productId':productId,'viewMultiJourney.multiJourneyId':d},
				async : false,
				timeout : 3000,
				success : function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("操作成功");
						$("#queryMultiJourneyBtn").click();
					} else {
						alert(data.msg);
					}
				}
			});
		});
	});
	
	function checkFormBeforeSubmit() {
		var re = /^[1-9]\d*$/;
		var days = $.trim($("input[name='viewMultiJourney.days']").val());
		if ($.trim(days) != "" && !re.test(days)){
			alert("行程天数必须为大于0的正整数!");
			return false;
		}
		var nights = $.trim($("input[name='viewMultiJourney.nights']").val());
		if ($.trim(nights) != "" && !re.test(nights)){
			alert("晚数必须为大于0的正整数!");
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<div class="main main02">
		<div class="row1">
			<h3 class="newTit">
				销售产品信息
				<s:if test="product != null">
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productId != null">
    			产品ID:${product.productId }
    		</s:if>
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productName != null">
    			产品名称：${product.productName }
    		</s:if>
				</s:if>
				<s:if test="product.productId != null">
					<jsp:include page="/WEB-INF/pages/back/prod/goUpStep.jsp"></jsp:include>
				</s:if>
			</h3>
			<div class="nav">
				<jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
			</div>
		</div>
		<div class="row2">
			<s:form action="view/queryMultiJourneyList.do" method="post" onsubmit="return checkFormBeforeSubmit();">
				<input type="hidden" value="${product.productId }" name="productId" id="productId" />
				<table class="newTable" width="90%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td>录入时间：</td>
						<td><input type="text" class="date" readonly="readonly" name="createBeginDate" value="<s:date name="createBeginDate" format="yyyy-MM-dd"/>" />~<input
							type="text" class="date" readonly="readonly" name="createEndDate" value="<s:date name="createEndDate" format="yyyy-MM-dd"/>" /></td>
						<td>行程名称：</td>
						<td><s:textfield name="viewMultiJourney.journeyName" /></td>
						<td>行程天数：</td>
						<td><s:textfield name="viewMultiJourney.days" size="3" />天<s:textfield name="viewMultiJourney.nights" size="3" />晚</td>
						<td>是否有效：</td>
						<td><s:select list="#{'':'请选择','Y':'有效','N':'无效'}" name="viewMultiJourney.valid"></s:select></td>
						<td><input type="submit" class="button" value="查询" id="queryMultiJourneyBtn" /></td>
					</tr>
				</table>
			</s:form>
			<input type="button" class="editMultiJourney button" value="新建" title="新建" />
			<dl class="trave">
				<dt>
					<strong class="add_margin_left">行程列表</strong>
				</dt>
				<dd>
					<form class="mySensitiveForm">
					<table class="newTable" width="90%" border="0" cellspacing="0"
						cellpadding="0" id="multiJourney">
						<tr class="newTableTit">
							<td style="width: 15%">行程名称</td>
							<td style="width: 10%">行程天数</td>
							<td style="width: 25%">内容描述</td>
							<td style="width: 15%">录入时间</td>
							<td style="width: 10%">是否有效</td>
							<td style="width: 25%">操作</td>
						</tr>
						<s:iterator value="pagination.records" var="journey">
							<tr>
								<td>${journeyName }<input type="hidden" name="journeyName${multiJourneyId }" value="<s:property value='journeyName' />" class="sensitiveVad" /></td>
								<td>${days }天${nights }晚</td>
								<td><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content,20)"/><input type="hidden" name="content${multiJourneyId }" value="<s:property value='content' />" class="sensitiveVad" /></td>
								<td><s:date name="createTime" format="yyyy-MM-dd HH:ss:mm" /></td>
								<td>${zhValid }</td>
								<td>
									<a href="javascript:void(0)" class="editMultiJourney" data="${multiJourneyId }" title="修改">修改</a>|
									<a href="/super_back/view/toViewJourney.do?productId=${product.productId }&&multiJourneyId=${multiJourneyId }">行程明细</a>|
									<a href="javascript:void(0)" class="changeValidStatus" data="${multiJourneyId }" status="${valid }">
										<s:if test='valid=="Y"'>无效</s:if>
										<s:else>有效</s:else>
									</a>|
									<a href="javascript:void(0)" class="editMultiJourney" data="${multiJourneyId }" copy="true" title="复制">复制</a>|
									<a href="javascript:voie(0)" class="editFeeDetail" data="${multiJourneyId }">费用说明</a>|
									<a href="#log" class="showLogDialog" param="{'objectType':'VIEW_MULTI_JOURNEY','objectId':${multiJourneyId}}">操作日志</a>
								</td>
							</tr>
						</s:iterator>
					</table>
					</form>
					<table width="90%" border="0" align="center">
						<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
					</table>
				</dd>
			</dl>
			<!--travel end-->
		</div>
		<!--row5 end-->
	</div>
	<!--main01 main05 end-->
</body>
</html>

