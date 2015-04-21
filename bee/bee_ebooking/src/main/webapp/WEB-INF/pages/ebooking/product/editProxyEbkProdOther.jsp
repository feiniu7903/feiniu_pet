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
				<li id="EBK_AUDIT_TAB_RELATION">关联销售产品</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
		            <li style="display: block;">
		            	<p class="fysm_t"><b>行前须知：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[0].content" messagetitle="行前须知"><s:if test="ebkProdProduct.ebkProdContents[0].content==null"><s:if test="ebkProdProduct.subProductType=='GROUP_FOREIGN'">1、团队行程由领队负责所有的机票及护照，故出发前机票及护照将不配送给客人（如果您是自备签证，请自带护照，如遗忘携带护照，相应损失需自行承担）。
2、此旅游产品为团队游，未经随团领队同意，游客在游览过程中不允许离团（行程中自由活动除外），不便之处敬请谅解。游客个人或结伴未经领队或导游同意私自外出所发生一切意外事故或事件引起的任何责任与后果，均由游客自负，与旅行社无关。
3、为了不耽误您的行程，请您严格按照《出团通知书》要求，在航班起飞前规定时间到达机场集合并办理登机＆出入境相关手续。
4、因不可抗力或者旅行社、履行辅助人已尽合理注意义务仍不能避免的事件，影响旅游行程的，按照下列情形处理： 
a. 合同不能继续履行的，旅行社和旅游者均可以解除合同。合同不能完全履行的，旅行社经向旅游者作出说明，可以在合理范围内变更合同;旅游者不同意变更的，可以解除合同。 
b. 合同解除的，组团社应当在扣除已向地接社或者履行辅助人支付且不可退还的费用后，将余款退还旅游者;合同变更的，因此增加的费用由旅游者承担，减少的费用退还旅游者。 
c. 危及旅游者人身、财产安全的，旅行社应当采取相应的安全措施，因此支出的费用，由旅行社与旅游者分担。 
d. 造成旅游者滞留的，旅行社应当采取相应的安置措施。因此增加的食宿费用，由旅游者承担;增加的返程费用，由旅行社与旅游者分担。
5、如果您需要我公司为您代办本产品的签证，收取材料的截止日期如下：
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料</s:if><s:else>1、为了不耽误您的行程，请您严格按照《出团通知书》要求，在航班起飞前规定时间到达机场集合并办理登机＆出入境相关手续。
2、如果您需要我公司为您代办本产品的签证，收取材料的截止日期如下：
xx/xx出发团队，xx/xx下午17：00截止收取材料
xx/xx出发团队，xx/xx下午17：00截止收取材料</s:else></s:if><s:else>${ebkProdProduct.ebkProdContents[0].content}</s:else></textarea>
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentId" value="${ebkProdProduct.ebkProdContents[0].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].contentType" value="ACITONTOKNOW">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[0].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		                <p class="fysm_t"><b>预订须知：</b>请按照录入规范，进行描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[2].content" messagetitle="预订须知"><s:if test="ebkProdProduct.ebkProdContents[2].content==null"><s:if test="ebkProdProduct.subProductType=='GROUP_FOREIGN'">1、旅游者如系   岁以上（含   岁）人员出游的，本人需充分考虑自身健康状况能够完成本次旅游活动，谨慎出游，建议要有亲友陪同出游，如因旅游者自身身体原因引发疾病或其他损害由旅游者本人承担相关责任。未满18周岁的旅游者请由家属陪同参团。因服务能力所限，不接受18周岁以下旅游者单独报名出游，敬请谅解。本产品网上报价适用持有大陆居民身份证的游客。如您持有其他国家或地区的护照，请电话现询价格，给您造成的不便，敬请谅解。
2、单房差：报价是按照2人入住1间房计算的价格，如出现单男或单女情况，本公司将于出发前两天通知是否可以拼房，如未能拼房者，可选择与同行亲友共享双人房并加床并支付附加费，如客人不愿意与同行亲友共享三人房，就需支付单人房附加费，享用单人房间。
3、最低成团人数   人。若本旅游产品未达到最低成团人数，我司将在游客报名参加本旅游产品时约定的出发日前30日按预订时联系方式通知游客，游客可以选择延期出发、更改旅游产品出行，或要求退回已付的团款，我司不承担任何违约责任。
4、出团通知最晚于出团前   个工作日发送，若能提前确定，我们将会第一时间通知您。
5、护照及签证为旅游者个人证件。如签证是旅游者自行办理，因个人证件问题造成旅游者无法正常出入境的，由旅游者本人承担全部责任；凡持非中国护照的旅游者或自备签证的旅游者，应自行办理本次旅游签证和再次回中国内地大陆的签证，如因签证问题造成出入境受阻，由旅游者承担全部责任。
6、自备签证：如您为自备签证，请在后续附加产品页面中选择签证自理的可选项，以便总卖价中自动扣除相应费用，如因自备旅游签证问题造成行程受阻，相应损失需自行承担。</s:if><s:else>1、旅游者如是   岁以上（含   岁）人员出游的，本人需充分考虑自身健康状况能够完成本次旅游活动，谨慎出游，建议要有亲友陪同出游，如因旅游者自身身体原因引发疾病或其他损害由旅游者本人承担相关责任。未满18周岁的旅游者请由家属陪同旅游。因服务能力所限，不接受18周岁以下旅游者单独报名出游，敬请谅解。本产品网上报价适用持有大陆居民身份证的游客。如您持有其他国家或地区的护照，请电话现询价格，给您造成的不便，敬请谅解。
2、单房差：报价是按照2人入住1间房计算的价格，如您的订单产生单房，驴妈妈将向您收取相应的费用，具体请来电咨询客服。
3、出团通知最晚于出团前   个工作日发送，若能提前确定，我们将会第一时间通知您。
4、护照及签证为旅游者个人证件。如签证是旅游者自行办理，因个人证件问题造成旅游者无法正常出入境的，由旅游者本人承担全部责任；凡持非中国护照的旅游者或自备签证的旅游者，应自行办理本次旅游签证和再次回中国内地大陆的签证，如因签证问题造成出入境受阻，由旅游者承担全部责任。
5、自备签证：如您为自备签证，请在后续附加产品页面中选择签证自理的可选项，以便总卖价中自动扣除相应费用，如因自备旅游签证问题造成行程受阻，相应损失需自行承担。</s:else></s:if><s:else>${ebkProdProduct.ebkProdContents[2].content}</s:else></textarea>
							<input type="hidden" name="ebkProdProduct.ebkProdContents[2].contentId" value="${ebkProdProduct.ebkProdContents[2].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[2].contentType" value="ORDERTOKNOWN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[2].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		                <p class="fysm_t"><b>购物说明：</b>请在下方选择项目类型并按规范描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[3].content" messagetitle="购物说明"><s:if test="ebkProdProduct.ebkProdContents[3].content!=null">${ebkProdProduct.ebkProdContents[3].content}</s:if></textarea>购物名称；营业产品；说明
							<input type="hidden" name="ebkProdProduct.ebkProdContents[3].contentId" value="${ebkProdProduct.ebkProdContents[3].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[3].contentType" value="SHOPPINGEXPLAIN">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[3].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		                <p class="fysm_t"><b>推荐项目：</b>请在下方选择项目类型并按规范描述信息。</p>
		                <div class="fysm_b">
		                	<textarea name="ebkProdProduct.ebkProdContents[1].content" messagetitle="推荐项目"><s:if test="ebkProdProduct.ebkProdContents[1].content!=null">${ebkProdProduct.ebkProdContents[1].content}</s:if></textarea>活动推荐名称；参考价格；详情
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentId" value="${ebkProdProduct.ebkProdContents[1].contentId}">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].contentType" value="RECOMMENDPROJECT">
							<input type="hidden" name="ebkProdProduct.ebkProdContents[1].productId" value="${ebkProdProduct.ebkProdProductId}">
		                </div>
		            </li>
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit saveEbkProdOtherInit">保存</span><span><font color="red" id="errorMessageSpan"></font></span>
				</s:if>
			</div>
		</div>
	</form>
	
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>