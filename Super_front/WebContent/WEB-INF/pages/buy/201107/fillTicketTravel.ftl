<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>填写订单信息_<@s.property value="mainProdBranch.getFullName()" />_标准订单_驴妈妈旅游网</title>
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_globalV1_0.css,/styles/new_v/header-air.css,/styles/super_v2/orderV2.css,/styles/v4style/myspace_fapiao.css,/styles/new_v/ui_plugin/jquery-ui-datepicker.css"/>
		<script src="http://pic.lvmama.com/min/index.php?f=/js/jquery-1.7.js,/js/ui/jquery-ui-datepicker.custom.min.js"></script>
	</head>
	<body style="background:none;">
		<!--===== 头部文件区域 S ======-->
		<div style="width:980px; margin:0 auto;">
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
		<!--===== 头部文件区域 E ======-->

		<!--===== 导航条 S ======-->
		﻿<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
		<@navigation productType="${mainProdBranch.prodProduct.productType?if_exists}" 
			subProductType="${mainProdBranch.prodProduct.subProductType?if_exists}" 
			resourceConfirm="${mainProductNeedResourceConfirm}"
			eContract = "${mainProductEContract}" 
			stepView="fillRouteTravelView"
			payToSupplier="${mainProdBranch.prodProduct.payToSupplier}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
		<!--===== 导航条 E ======-->
		
		
		<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <@creditTip productName="${mainProdBranch.prodProduct.productName}" productType="${mainProdBranch.prodProduct.productType?if_exists}"/>
		<!--===== 页面信用贴士E ======-->

		<form id="buyUpdateForm" method="post" action="/buy/update.do">
			<@s.token></@s.token>
			<#list request.getParameterNames() as parameterName>
				<#if parameterName != "struts.token" && parameterName != "struts.token.name" && parameterName != "receiverId">
					<input type="hidden" name="${parameterName}" value="${Request[parameterName]}"/>
				</#if>
			</#list>
 			
 			<div class="o-container">
	 			<!--========= 取票/游玩人信息 S ========--> 
	        	<#include "/WEB-INF/pages/buy/201107/ticket/insurer_person.ftl"/>
				<!--========= 取票/游玩人信息 E ========-->
				
			    <#comment>
				<!-- 车辆信息 S-->
				<h3><s></s>车辆信息<em>（*为必填项，请准确填写车辆信息，以便办理救缓服务）</em><span class="explain-write">填写说明</span></h3>
				<dl class="user-info">
			    	<dt><span>*</span>车主姓名：</dt><dd><input type="text" size="30" class="inp-txt" />&nbsp;&nbsp;<select><option value="0">从常用联系人中选</option></select></dd>
			    	<dt><span>*</span>车架号：</dt><dd><input type="text" size="30" class="inp-txt" /><span>（免费接收短信，作为订购与取票凭证）</span></dd>
			    	<dt><span>*</span>居住城市：</dt><dd><select name="a"  class="select-left"><option value="0">上海</option></select><select name="b"><option value="a">上海</option></select></dd>
			    	<!-- <dt><span>*</span>证件类型：</dt><dd><select class="select-left"><option value="0">请选择证件类型</option><option value="1">身份证</option></select></dd>
			    	<dt><span>*</span>证件号码：</dt><dd><input type="text" size="30" class="inp-txt" /></dd> -->
			    </dl>
				<!-- 车辆信息信息 E-->	
			    </#comment>
    			<@s.if test="mainProductNeedResourceConfirm=='true'">
			    <!--========= 订单备注 S ========-->
				<h3><s></s>订单备注</h3>
				<dl class="user-info remark2">
			    	<dt>订单备注：</dt><dd><textarea cols="70" rows="4" id="dingdan_xuqiu" name="buyInfo.userMemo" onfocus="if ($(this).val() == '您对订单的特殊要求') {$(this).val('');}">您对订单的特殊要求</textarea></dd>
			    </dl>    
			    <!--========= 订单备注 E ========-->	
			    </@s.if>
				<!--========= 上车地点 S ========-->
				<@s.if test="prodAssemblyPointList.size>0">
					<h3><s></s>集合地点</h3>
					<div class="user-info remark2">
						<@s.iterator value="prodAssemblyPointList" id="prodAssemblyPoint" status="pa">
							<@s.if test="#pa.first">
								<input type="radio" name="prodAssemblyPoint" value="${prodAssemblyPoint.assemblyPoint}" checked="true" class="car-place"/>${prodAssemblyPoint.assemblyPoint}</em>
							</@s.if>
							<@s.else>
								<input type="radio" name="prodAssemblyPoint" value="${prodAssemblyPoint.assemblyPoint}" class="car-place"/>${prodAssemblyPoint.assemblyPoint}</em>
							</@s.else>
							<br/>
						</@s.iterator>
    				</div>	
				</@s.if>
				<!--========= 上车地点 E ========-->				    
			    			
			    <!--========= 电子合同模板 S ========-->
			    <#if mainProductEContract == "true">
			    	<h3><s></s>合同范本<em>（请仔细阅读合同范本，具体出游信息以您填写的订单为准，付款完成后您可以在“我的订单”下载包含完整订单信息的旅游合同）</em></h3>
			    	<ul>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    	<em><iframe src='/ord/viewContractTemplate.do?productId=${mainProdBranch.productId?if_exists}' width="80%" height="300" id="contractTemplateIframe" frameborder="no" style="border:1px solid #7F9DB9;"></iframe></em></ul>
			    	<ul class="player-info"><input type="checkbox"  name="agreehetong" id="lvmamahetong"/>我已阅读并接受以上合同条款、补充条款及其他所有内容</ul>
			    </#if>
			    <!--========= 电子合同模板  E ========-->
			   
				<div class="order-commit">
    				<input type="button" name="btnConfirmOrder" onclick="btnFormSubmit()" value="确认下单" class="btn-submit" />
			    </div>	  
			</div>
		</form>
		<!--===== 底部文件区域 S ======-->
		<#include "/common/orderFooter.ftl">
		<!--===== 底部文件区域 E ======-->
		
		<#if mainProductEContract == "true">		
			<div id="contractTemplateId" style="opacity:0.1;filter:alpha(opacity=10);background:#FFF; z-index:1;height:1px !important;"></div>
				<script>
		   			var iframe_c=$('#contractTemplateIframe');
		   			$('#contractTemplateId').offset({top:iframe_c.offset().top,left:iframe_c.offset().left});
		   			$('#contractTemplateId').height(iframe_c.height()-10).width(iframe_c.width()-18).show();
				</script>
		</#if>		
		</div>


	
	<!--===== 加载JS代码 ======-->
 	<script src="http://pic.lvmama.com/js/super_v2/order_func.js" type="text/javascript"></script>
	<script src="/js/buy/form.js" type="text/javascript"></script>
	<script src="/js/buyBase.js" type="text/javascript"></script>
	<script src="/js/fillNew.js" type="text/javascript"></script>
	<#include "/WEB-INF/pages/buy/201107/subOrder.ftl" />
	<!-- Google Code for &#27969;&#31243;-&#22635;&#20889;&#35746;&#21333; 540&#22825; Remarketing List -->
	<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 962608731;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "WFDDCN3MxAMQ2_yAywM";
	var google_conversion_value = 0;
	/* ]]> */
	</script>
	<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
	</script>
	<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/962608731/?label=WFDDCN3MxAMQ2_yAywM&amp;guid=ON&amp;script=0"/>
	</div>
	</noscript>

	<script type="text/javascript">
	<!-- 
	var bd_cpro_rtid="nWTYn0";
	//-->
	</script>
	<script type="text/javascript" src="http://cpro.baidu.com/cpro/ui/rt.js"></script>
	<noscript>
	<div style="display:none;">
	<img height="0" width="0" style="border-style:none;" src="http://eclick.baidu.com/rt.jpg?t=noscript&rtid=nWTYn0" />
	</div>
	</noscript>
<#include "/common/ga.ftl"/>
	</body>
</html>

