<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>合同签约_驴妈妈旅游网</title>
<link type="text/css"  rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css"  rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" type="text/css"  href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<script src="/js/buy/jquery.PrintArea.js" type="text/javascript"></script>
<#include "/common/coremetricsHead.ftl">
</head>

<body>
 <object id="WebBrowser" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" width="0" ></object>
<!--===== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
<!--===== 头部文件区域 E ======-->

<!--===== 导航条 S ======-->
        <@navigation productType="${order.mainProduct.productType?if_exists}" 
			subProductType="${order.mainProduct.subProductType?if_exists}" 
			resourceConfirm="${order.resourceConfirm}"
			eContract = "${order.isNeedEContract()?string('true','false')}" 
			stepView="signingView"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
<!--===== 导航条 E ======-->

		<!--===== 页面信用贴士S ======-->
		<#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		<#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		<@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
		<!--===== 页面信用贴士E ======-->

<!--===== 头部文件区域 S ======-->
 <div class="tips-info tips-info-auto">
 <p class="tips-title">
     <strong>为了保障双方的合法权益，我们将与您使用在线签约方式签署合同，此合同在得到资源确认并付款完成时正式生效。</strong>
</p>
 </div>
<div class="o-container">
 <form id="hetongForm" action="/view/view.do" method="post" onsubmit="return agreeOrder()">
 <input type="hidden" name="orderId" value="${order.orderId?if_exists}"/>
 <input type="hidden" name="isSigning" value="Y"/>


<!-- 合同tit S-->
<h3 class="atr_online_top"><s></s>
<#if contractTemplate?? && contractTemplate == 'PRE_PAY_ECONTRACT' >
	预付款产品协议书
<#else>
	旅游合同
</#if>
<span class="atr_online_print"   target="_self" id="btnPrint">打印</span></h3>
<!-- 合同tit E-->

<!-- 合同范本 S-->
	<!--<h3><s></s>合同范本<em class="hto-explain">（请仔细阅读合同范本，具体出游信息以您填写的订单为准，付款完成后您可以在"我的订单"下载包含完整订单信息的旅游合同）</em></h3>-->
    <div class="user-info">
        <div class="atr_ifrbox">
            <iframe src='/ord/initContract.do?orderId=${orderId?if_exists}' id="contractTemplateIframe" frameborder="no" class="atr_online_ifm" name="contractTemplateIframe"></iframe>
            <iframe src='/ord/initViewOrderTravel.do?orderId=${orderId?if_exists}&productId=<@s.if test="null!=id"><@s.property value="id"/></@s.if><@s.else><@s.property value="order.mainProduct.productId"/></@s.else>' id="contractTemplateIframe1" frameborder="no" class="atr_online_ifm1" name="contractTemplateIframe1"></iframe>
            <div class="atr_acp">
            	<#if contractTemplate?? && contractTemplate == 'PRE_PAY_ECONTRACT' >
					<b class="atr_imp_point">*</b><input type="checkbox"  id="lvmamahetong3"/>同意驴妈妈预付款服务协议。  <a target="_blank" href="http://www.lvmama.com/econtract/${order.mainProduct.productId?if_exists}">了解电子合同</a>
				<#elseif contractTemplate?? && contractTemplate == 'BJ_ONEDAY_ECONTRACT' >
					<b class="atr_imp_point">*</b><input type="checkbox"  id="lvmamahetong3"/>我接受－－我已完整阅读<<通知函>>或旅游合同、补充条款、行程单、预订须知，并接受所有内容。 
				<#else>
					<p>
						<b class="atr_imp_point">*</b><label><input type="checkbox" id="lvmamahetong1"/><b>已仔细阅读预订页面信息与合同条款，并已了解合同条款内容、行程安排、重要提示等信息，同意遵守。</b></label>
					</p>
					<p>
						<b class="atr_imp_point">*</b><label><input type="checkbox" id="lvmamahetong2"/><b>本人确认本合同内所有参团人员身体健康良好，适合参加此次行程中所有项目，（参团人员不包含70周岁以上老人、身体障碍人士、孕妇等各类需特殊照顾人群，如有需要请主动联系我司客服人员。） 
</b></label>
					</p>
					<p class="textindent24"><b>如遇不成团愿意接受：(可多选，如不勾选视为不同意)</b></p>
					<p class="textindent48">
						<label><input type="checkbox" name="agree3" value="true"/>1 、本人同意转至组团社指定旅行社出团； </label>
					</p>
					<p class="textindent48">
						<label><input type="checkbox" name="agree4" value="true"/>2 、本人同意延期出团； </label>
					</p>
					<p class="textindent48">
						<label><input type="checkbox" name="agree5" value="true"/>3 、本人同意改签其他线路出团； </label>
					</p>
				</#if>
            </div>
        </div>
	</div>
<!-- 合同范本 E-->

<!--订单联系人信息 S-->
	<h3><s></s>邮箱信息</h3>
	<dl class="user-info">
    	<dt><b class="atr_imp_point">*</b>签约邮箱：</dt>
    	<dd>   
    	<input type="text" name="contactEmail" id="contactEmail" class="inp-txt"/>
    	<span class="hto-explain">（您阅读并签署旅游合同后，我们会将旅游合同发送到您的邮箱；您也可以在"我的订单中"下载合同）</span>
    	</dd>
    </dl>
<!--订单联系人信息 E-->

	<div class="atr_btnbox">
    	<input type="submit" value="签约付款" class="btn-submit" />
    </div>
    </form>
</div>
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<script type='text/javascript'>
		$(function(){
			//打印
			$("#btnPrint").bind("click",function(event){
				//$(document.body).printArea();
				var newwin= window.open ('${base}/ord/viewOrderContractAndTravel.do?orderId=${orderId}', 'newwindow', 'height=500, width=800, top=0, left=0, toolbar=no,menubar=no, scrollbars=yes, resizable=no,location=no, status=no'); 
			});
		});
</script>

<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<#include "/common/ga.ftl"/>
<script language="javascript">    
	function agreeOrder(){
	    var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	    var contactEmail = $('#contactEmail').val()
		if(contactEmail==''){
		    alert("联系人邮箱不能为空！");
		    return false;
		}	
		if(!EMAIL_REGX.test(contactEmail)){
		   alert("请输入正确的邮箱地址！");
		   return false;
		}
		if($('#lvmamahetong3').length> 0 && $('#lvmamahetong3').attr("checked")==false){
		    alert("您需要同意驴妈妈预付款服务协议才能下单！");
		    return false;
		}
		if($('#lvmamahetong1').length> 0 && $('#lvmamahetong1').attr("checked")==false){
		    alert("您需要同意合同条款才能下单！");
		    return false;
		}
		if($('#lvmamahetong2').length> 0 && $('#lvmamahetong2').attr("checked")==false){
		    alert("您需要确定团队游玩人身体健康良好，适合参加此次行程中所有项目才能下单！");
		    return false;
		}
		return true;
	}
</script>
<script>
	<#if order.mainProduct.productType == "HOTEL">
		cmCreatePageviewTag("签合约-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
	<#else>
		cmCreatePageviewTag("签合约-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
	</#if>
    
</script>
</body>
</html>
