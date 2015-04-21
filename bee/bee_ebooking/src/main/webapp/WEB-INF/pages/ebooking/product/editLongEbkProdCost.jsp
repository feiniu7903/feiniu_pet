<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<s:include value="./common/ebkCommonResource.jsp"></s:include>
</head>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProdContent.do" id="confirm" method="post">
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
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
					<li style="display: block;">
		            	<p class="fysm_t"><b>费用包含：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[0].content" messagetitle="费用包含"><s:if test="ebkProdProduct.ebkProdContents[0].content==null">交通：
住宿：
所含景点：
用餐：
导服：
儿童：
参考航班或车次：
							</s:if><s:else>${ebkProdProduct.ebkProdContents[0].content}</s:else>
		                	</textarea>
		                	<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentId" value="${ebkProdProduct.ebkProdContents[0].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentType" value="COSTCONTAIN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		                <p class="fysm_t"><b>费用不包含：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[1].content" messagetitle="费用不包含"><s:if test="ebkProdProduct.ebkProdContents[1].content==null">赠送项目：
单房差：
因交通延阻、罢工、天气、飞机机器故障、航班取消或更改时间等不可抗力原因所引致的额外费用。
酒店内不包含的个人消费项目。
							</s:if><s:else>${ebkProdProduct.ebkProdContents[1].content}</s:else>
		                	</textarea>
		                	<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentId" value="${ebkProdProduct.ebkProdContents[1].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentType" value="NOCOSTCONTAIN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		            </li>
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit saveEbkProdCost">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
				</s:if>
			</div>
		</div>
	</form>
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>