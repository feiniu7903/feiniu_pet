<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<link href="${basePath}/css/base/houtai.css" rel="stylesheet" type="text/css" /> 
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
</head>
<script type="text/javascript">
	$(function() {
		$(".addMultiJourney").live("click",function(){
			var productId = $("#productId").val();
			var days = $.trim($("#showDate").val());
			if(productId == "") {
				alert("数据异常！");
				return fasle;
			}
			var ebkMultiShow={
					url : "/ebooking/view/toEditMultiJourney.do",
					data :{productId:productId,tourDays:days},
					type : 'POST',
					dataType : "html",
					success : function(data) {
						if($(".dialog:visible").size()>0)return false;
						$.dialog({
							title: '添加行程列表',
							content: data,
							width:800
						});
					},
					error : function() {
					}
				}; 
			$.ajax(ebkMultiShow);
		});
		
		$(".editMultiJourney").live("click",function(){
			var productId = $("#productId").val();
			if(productId == "") {
				alert("数据异常！");
				return fasle;
			}			
			var data = $(this).attr("data");
			var cy = $(this).attr("isCopy");			
			var ebkMultiShow={
					url : "/ebooking/view/toEditMultiJourney.do",
					data :{productId:productId,multiJourneyId:data,isCopy:cy},
					type : 'POST',
					dataType : "html",
					success : function(data) {
						if($(".dialog:visible").size()>0)return false;
						$.dialog({
							title: '修改行程列表',
							content: data,
							width:800
						});
					},
					error : function() {
					}
				}; 
			$.ajax(ebkMultiShow);
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
				url : "/ebooking/view/changeValidStatus.do",
				data: {'ebkMultiJourney.productId':productId,'ebkMultiJourney.multiJourneyId':d},
				async : false,
				timeout : 3000,
				success : function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("操作成功");
						var url2="<%=request.getContextPath()%>/ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId="+productId;
						window.location.href=url2;
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
	
	$(".saveTourDays").live("click",function(){
		
		var re = /^[1-9]\d*$/;
		var days = $.trim($("#showDate").val());
		if(days=="" || null==days){
			alert("请输入行程天数!");
			return false;
		}
		if (!re.test(days)){
			alert("行程天数必须为大于0的正整数!");
			return false;
		}
		var ebkProdProductId = $("#ebkProdProductId").val();
		$.ajax( {
			type : "POST",
			url : "/ebooking/view/saveEbkProdTourDays.do",
			data: {'tourDays':days,'productId':ebkProdProductId},
			async : false,
			timeout : 3000,
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("操作成功!");
				} else {
					alert(data.msg);
				}
			}
		});
	
	});
	
	
</script>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProdMultiJourney.do" id="confirm" method="post">
		<input name="ebkProdProduct.ebkProdProductId" id="ebkProdProductId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		<input name="ebkProdProduct.supplierId" id="supplierId" value="${ebkProdProduct.supplierId }" type="hidden">
		<input name="ebkProdProduct.productType" id="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="toShowEbkProduct" value="${toShowEbkProduct }" id="toShowEbkProduct" type="hidden">
		
		<!--新增产品基础信息-->
		<div class="xzxx_box">
			<span class="fp_btn kcwh_btn_t">提交审核</span>
			<ul class="xzxx_tab">
				<li class="tab_this" id="EBK_AUDIT_TAB">产品基础信息</li>
				<li id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
				<li id="EBK_AUDIT_TAB_RELATION">关联销售产品</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
					<li style="display: block;">
		            	<p class="xcts_t">
		            		<span class="red_ff4444">*</span>行程天数：
		            		<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
		            			<input type="text" id="showDate" maxlength="10" name="tripSaveDayNum" value="${ebkProdProduct.tourDays}">
							</s:if>
							<s:else>
							${ebkProdProduct.tourDays}
							</s:else>
		            	</p> 
		            	<div class="main main02">
							<div class="rows"><h3 class="add_margin_left">行程列表</h3></div>
							<input type="hidden" value="${ebkProdProduct.ebkProdProductId }" name="productId" id="productId" />
							<table class="newTable" width="90%" border="0" cellspacing="0"
								cellpadding="0" id="multiJourney">
								<tr class="table01">
									<td style="width: 15%">行程名称</td>
									<td style="width: 10%">行程天数</td>
									<td style="width: 25%">内容描述</td>
									<td style="width: 10%">是否有效</td>
									<td style="width: 25%"><a href="javascript:void(0)" class="addMultiJourney" data="${ebkProdProduct.ebkProdProductId }" title="新增">新增一条</a></td>
								</tr>
								<s:iterator value="ebkMultiJourneyList" var="journey">
									<tr>
										<td>${journeyName }<input type="hidden" name="journeyName${multiJourneyId }" value="<s:property value='journeyName' />" class="sensitiveVad" /></td>
										<td>${days }天${nights }晚</td>
										<td><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content,45)"/><input type="hidden" name="content${multiJourneyId }" value="<s:property value='content' />" class="sensitiveVad" /></td>
										<td>状态:${zhValid }
											<a href="javascript:void(0)" class="changeValidStatus" data="${multiJourneyId }" status="${valid }">
												<s:if test='valid=="Y"'>无效</s:if>
												<s:else>有效</s:else>
											</a>
										</td>
										<td>
											<a href="javascript:void(0)" class="editMultiJourney" data="${multiJourneyId }" title="修改">修改</a>|
											<a href="/ebooking/view/toEbkMultiJourney.do?productId=${ebkProdProduct.ebkProdProductId }&multiJourneyId=${multiJourneyId }&days=${days }">行程描述</a>|
											<a href="/ebooking/view/toEbkMultiCost.do?productId=${ebkProdProduct.ebkProdProductId }&multiJourneyId=${multiJourneyId }">费用说明</a>|
											<a href="javascript:void(0)" class="editMultiJourney" data="${multiJourneyId }" isCopy="true" title="复制">复制</a>
										</td>
									</tr>
								</s:iterator>
							</table>
						</div>
						<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
							<span class="fp_btn saveTourDays">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
						</s:if>
					</li>
				</ul>
				
			
				
				
			</div>
	</div>
	</form>
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>

