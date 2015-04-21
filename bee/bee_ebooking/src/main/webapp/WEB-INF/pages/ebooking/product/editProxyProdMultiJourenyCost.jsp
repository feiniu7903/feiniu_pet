<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
<script type="text/javascript">
function goBack(id) {
	window.location.href="${contextPath }/ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId="+id;
	
}
</script>
</head>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkMultiContent.do?paraMultiId=${ebkMultiJourney.multiJourneyId }&&ebkProdProductId=${ebkProdProduct.ebkProdProductId}" id="confirm" method="post">
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
					        行程展示:${ebkMultiJourney.journeyName }
					    <a href="#" onclick="goBack('${ebkProdProduct.ebkProdProductId}')">返回行程</a>
		            	<p class="fysm_t"><b>费用包含：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[0].content" messagetitle="费用包含"><s:if test="ebkProdProduct.ebkProdContents[0].content==null">机票：
签证：
交通：
住宿：
用餐：
导服：
门票：
							</s:if><s:else>${ebkProdProduct.ebkProdContents[0].content}</s:else>
		                	</textarea>
		                	<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentId" value="${ebkProdProduct.ebkProdContents[0].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentType" value="COSTCONTAIN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		                <p class="fysm_t"><b>费用不包含：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[1].content" messagetitle="费用不包含"><s:if test="ebkProdProduct.ebkProdContents[1].content==null">单房差：
保险：
护照费，签证相关的例如未成年人公证，认证等相关材料制作费用
一切私人费用（例如洗衣、电话、传真、上网、收费电视节目、游戏、宵夜、酒水、邮寄、机场和酒店行李搬运服务、购物、行程列明以外的用餐或宴请等费用）
							</s:if><s:else>${ebkProdProduct.ebkProdContents[1].content}</s:else>
		                	</textarea>
		                	<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentId" value="${ebkProdProduct.ebkProdContents[1].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentType" value="NOCOSTCONTAIN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		            </li>
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit saveEbkMultiCost" id="saveEbkMultiCost">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
				</s:if>
			</div>
		</div>
	</form>

	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>