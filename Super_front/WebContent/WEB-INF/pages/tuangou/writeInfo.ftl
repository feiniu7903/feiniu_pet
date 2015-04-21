<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css,/styles/v3/channel.css" >
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/selectbox.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/forms.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/arrow.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/step.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/base/reset.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/group-buy.css">
</head>
<body class="groupBuy">
<div class="order-header wrap">
    <div class="header-inner">
        <a class="logo" href="http://www.lvmama.com/">自助天下游 就找驴妈妈</a>
        <p class="serverTime">
            服务热线时间：8:00-20:00（全年无休）
        </p>
        <p class="serverPhone">
            <strong>400-6040-616</strong>（免长话费）
        </p>
    </div>
</div>
<div class="wrap">
	 <#include "/WEB-INF/pages/tuangou/navigation.ftl">
    <@navigation stepView="3"/>
	<!--显示当前页的步骤-->
	<form id="buyTuanForm" name="orderForm" method="post" > 
	<input type ="hidden" id="firstTravellerInfoOptions" value="${firstTravellerInfoOptions?if_exists};"/>
    <input type ="hidden" id="travellerInfoOptions" value="${travellerInfoOptions?if_exists};"/>
    
    <#list request.getParameterNames() as parameterName>
		<#if parameterName != 'struts.token" && parameterName != "struts.token.name && parameterName.substring("contact.") != -1'>
			<#if parameterName == "contact.brithday" >
				 <#if Request[parameterName] ? exists >
					<input type="hidden" name="${parameterName}" value="${Request[parameterName]?string('yyyy-MM-dd')}"/>
				 </#if>
			<#else>
				<input type="hidden" name="${parameterName}" value="${Request[parameterName]}"/>
			</#if>
		</#if>
	</#list>
	<input type = "hidden" name = "vs" value = "${vs}"/>
    
    <input type ="hidden" id="buyNum" value="<@s.property value='buyInfo.buyNum.product_${buyInfo.prodBranchId?if_exists}'/>"/>
		<div class="buy-main border">
		    <div class="buy-ticket">
			   <!--选择出行日期-->
				<#include "/WEB-INF/pages/tuangou/tuan_yuding.ftl"> 
		       <!--取票人信息-->
		       <@s.if test="!contactInfoOptions.isEmpty()">
		       		<#include "/WEB-INF/pages/tuangou/tuangouTakeMan.ftl">  
		       </@s.if>
		       <!--游玩人信息-->
		      <@s.if test="!travellerInfoOptions.isEmpty() || !firstTravellerInfoOptions.isEmpty()" >
		      	 	<#include "/WEB-INF/pages/tuangou/tuangouPlay.ftl">
		      </@s.if>
		         
		         <div class="buy-info">
		            <dl>
		                <dt>&nbsp;</dt>
		                <dd>
		                <a href="javascript:history.back();" style="color:#06c;">&lt; 返回上一步</a>
		               <input class="buy-order pbtn pbtn-big pbtn-orange" type="button" value="同意以下预订协议并提交订单">
		               <span class="red buy-tip"></span>
		                </dd>
		            </dl>
		            <dl>
		                <dt>&nbsp;</dt>
		                <dd>
		                    <div class="buy-agree">
		                         <h5>驴妈妈旅游网预订条款</h5><br/>
		                         <strong>1.驴妈妈预订条款的确认</strong><br/>
		                    	  驴妈妈旅游网（以下简称“驴妈妈”）各项服务的所有权与运作权归景域旅游运营集团所有。本服务条款具有法律约束力。一旦您点选“同意以下预订协议并提交订单"成功提交订单后，即表示您自愿接受本协议之所有条款，并同意通过驴妈妈订购旅游产品。<br/>
		                         <strong>2．服务内容</strong><br/>
								 2.1 　驴妈妈服务的具体内容由景域旅游运营集团根据实际情况提供，驴妈妈对其所提供之服务拥有最终解释权。<br/>
							     2.2 　景域旅游运营集团在驴妈妈上向其会员提供相关网络服务。其它与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费等）均由会员自行负担。<br/>		
		                   		
		                   		<strong>1.驴妈妈预订条款的确认</strong><br/>
								驴妈妈旅游网（以下简称“驴妈妈”）各项服务的所有权与运作权归上海景域文化传播有限公司（以下简称景域旅游运营集团）所有。本服务条款具有法律约束力。一旦您点选“确认下单"成功提交订单后，即表示您自愿接受本协议之所有条款，并同意通过驴妈妈订购旅游产品。
								
								<strong>2．服务内容</strong><br/>
								2.1 　驴妈妈服务的具体内容由景域旅游运营集团根据实际情况提供，驴妈妈对其所提供之服务拥有最终解释权。<br/>
								2.2 　景域旅游运营集团在驴妈妈上向其会员提供相关网络服务。<br/>
								
								<strong>3. 特别提示</strong><br/>
								所有订单请提前2天预订，部分支持手机电子票（二维码）的景点，支持当天预订当天游玩。<br/>
								
								<strong>4. 订单生效</strong><br/>
								订单生效后，您应按订单中约定的时间按时入园。如您未准时出发或未按时入园，视为您主动放弃，驴妈妈不承担责任。<br/>
								
								<strong>5. 解除已生效订单</strong><br/>
								5.1 景点订单<br/>
								5.1.2如您通过其它平台通过预付费预订，在预订有效期内到驴妈妈平台进行预约，订单生效后，若要主动解除已生效订单，您须在行程前一天中午12点前通知驴妈妈解除所做预订，包括解除整张订单的全部内容或减少出行人数，同时需支付解除内容费用总额的5%的作为违约金，违约金在您购买的平台进行扣除。<br/>
								5.1.3 如景点有最新优惠活动，而通过本站合作的其他平台上的售价高过于景点活动“优惠价”，本网站承诺会将游客已支付的高出“优惠价” 的差额退通过本站合作的其他平台退还到游客指定的账户内。<br/>
								
								<strong>5.2特殊产品订单</strong><br/>
								部分产品由于资源限制等特殊性，一旦预订不予退费！（此信息会在产品相关页面作提示）<br/>
								
								<strong>6. 更改已生效订单</strong><br/>
								订单生效后，您主动要求更改该订单内的任何项目，请务必在行程前一天中午12点前通知驴妈妈您的更改需求。驴妈妈会尽量满足 您的需求，但您必须全额承担因变更带来的损失及可能增加的费用。<br/>
								
								<strong>7. 因驴妈妈原因取消您的已生效订单</strong><br/>
								在您按要求付清所有产品费用后，如因驴妈妈原因，致使您的产品取消，驴妈妈应当立即通知您。<br/>
								
								<strong>8. 不可抗力</strong><br/>
								您和驴妈妈双方因不可抗力(包括但不限于地震、台风、雷击、水灾、火灾等自然原因,以及战争、政府行为、黑客攻击、电信部门技术管制等原因)不能履行或不能继续履行已生效订单约定内容的，双方均不承担违约责任，但法律另有规定的除外。<br/>
								
								<strong>9. 解决争议适用法律法规约定</strong><br/>
								在您的预订生效后，如果在本须知或订单约定内容履行过程中，您对相关事宜的履行发生争议，应与驴妈妈友好协商解决；协商不成的，您只同意按照中华人民共和国的相关法律法规来解决争议。<br/>
								
								<strong>10. 其它</strong><br/>
								本须知未尽的其他事项，在驴妈妈确认给您的订单中另行约定。同时合同双方需承担对等的义务。<br/>
								祝您旅途愉快! <br/>
								如有疑问，请拨打驴妈妈客服电话: 4006040616，工作时间为：8:00-20:00<br/>
		                   			
		                    </div>
		                </dd>
		            </dl>
		        </div>
		        <h3 class="buy-head3"></h3>
		    </div>
		</div>
    </form>
</div>
<div id="order-footer" class="lv-footer wrap">
</div>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-select.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
<script src="/js/tuangou/tuan_playperson.js"></script>
<script src="/js/tuangou/tuan_distribution.js"></script>
<script src="/js/tuangou/tuan_check.js"></script>
<!--<script src="http://s2.lvjs.com.cn/js/common/copyright.js"></script>-->

</body>
</html>