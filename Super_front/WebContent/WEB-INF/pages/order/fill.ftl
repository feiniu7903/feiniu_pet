<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="dns-prefetch" href="//s1.lvjs.com.cn">
		<link rel="dns-prefetch" href="//s2.lvjs.com.cn">
		<link rel="dns-prefetch" href="//s3.lvjs.com.cn">
		<title>填写订单信息_${mainProdBranch.prodProduct.productName?if_exists}_标准订单_驴妈妈旅游网</title>
		<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/calendar.css,/styles/v4/modules/forms.css" > 
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/dialog.css,/styles/v4/modules/button.css"/>		
		<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/v4/modules/pandora-dialog.js"></script>
		<#include "/common/coremetricsHead.ftl">
		<style>
			.xdl .email_text_Box{width:130px;}
			.email_orderfill .zhfs_v_success i{margin-top:5px;}
		</style>
	</head>
	<body class="order">
		<!-- 订单公共头部开始 -->
		<div class="order-header wrap">
		    <div class="header-inner">
			<a class="logo" href="http://www.lvmama.com/">自助天下游 就找驴妈妈</a>
			<p class="welcome">
				<@s.if test='isLogin()'>
					您好，<b><@s.property value="getUser().userName" /></b>
				</@s.if>
			</p>
			<p class="info">24小时服务热线：<strong>1010-6060</strong></p>
			
		    </div>
		</div> <!-- //.lv-header -->
		<div class="wrap">
		<!--===== 导航条 S ======-->
		<#include "/WEB-INF/pages/order/orderPayView/navigation.ftl">
	    <@navigation productType="${mainProdBranch.prodProduct.productType?if_exists}" 
			subProductType="${mainProdBranch.prodProduct.subProductType?if_exists}" 
			resourceConfirm="${mainProductNeedResourceConfirm}"
			eContract = "${mainProductEContract}" 
			stepView="fill"
			payToSupplier="${mainProdBranch.prodProduct.payToSupplier}" />
	<!--===== 导航条 E ======-->
		 <#-- 设置线路类与非线路类表单的提交地址 -->
		<#assign information=""/>
        <@s.if test = '(mainProdBranch.prodProduct.productType == "ROUTE" &&  mainProdBranch.prodProduct.subProductType != "FREENESS")||mainProdBranch.prodProduct.isTrain() || !travellerInfoOptions.isEmpty()'>
 			<#assign information="true"/>
        </@s.if>
        <input type ="hidden" name="first" value="${firstTravellerInfoOptions?if_exists};"/>
  	    <input type ="hidden" name="other" value="${travellerInfoOptions?if_exists};"/>
    	<input type="hidden" name="information" value="${information?if_exists}"/>
        <input type="hidden" name="buyPeopleNum" value="0"/>	
		<!-- 公共头部结束  -->
		<form id="buyUpdateForm" name="orderForm" method="post" action="/buy/updateNew.do">
			<@s.token></@s.token>			
			<input name="_leaveTime" type="hidden" id="_leaveTime" value="${buyInfo.leaveTime?if_exists}" />
			<input type="hidden" name="mainProductNeedResourceConfirm" value="${mainProductNeedResourceConfirm}"/>
			<input type="hidden" name="mainProductEContract" value="${mainProductEContract}"/>
			<input type="hidden" name="submitOrder" value="true"/>
			<input type="hidden" name="buyInfo.productId" value="${buyInfo.productId?if_exists}">
			<input type="hidden" name="buyInfo.prodBranchId" value="${buyInfo.prodBranchId?if_exists}">
			<input type="hidden" value="${buyInfo.visitTime?if_exists}" id="allVisitDate" name="buyInfo.visitTime"/>
			<input type="hidden" value="${mainProdBranch.productId?if_exists}" id="_mainProductId" name="product_mainProductId"/>			
			<input type="hidden" name="buyInfo.productType" value="${mainProdBranch.prodProduct.productType?if_exists}"/>
			<input type="hidden" name="buyInfo.channel" value="${buyInfo.channel?if_exists}"/>
			<input type="hidden" id="_mainSubProductType" name="buyInfo.subProductType" value="${mainProdBranch.prodProduct.subProductType?if_exists}"/>
			<input type="hidden" name="buyInfo.days" value="${buyInfo.days?if_exists}"/>
			<#-- 百度团购参数 -->
			<input type="hidden" name="tn"  id="tn" value="${tn}"/>
			<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
			
            <@s.if test='mainProdBranch.prodProduct.payToLvmama=="true"'>
				<input type="hidden" name="buyInfo.paymentTarget" value="TOLVMAMA"/>
			</@s.if>
			<@s.elseif test='mainProdBranch.prodProduct.payToSupplier=="true"'>
				<input type="hidden" name="buyInfo.paymentTarget" value="TOSUPPLIER"/>
			</@s.elseif>
            <input name="buyInfo.selfPack"  type="hidden" value="${buyInfo.selfPack?if_exists}"/>
            <input name="buyInfo.adult"  type="hidden" value="${buyInfo.adult}"/>
            <input name="buyInfo.child"  type="hidden" value="${buyInfo.child}"/>
            <input name="content"  type="hidden" value="${buyInfo.content}"/>
            <input name="buyInfo.content" type="hidden" value="${buyInfo.content}"/>
            <input name="orderCouponPrice"  type="hidden" id="orderCouponPrice" value="0"/>
            <input name="buyTime" type="hidden" value="${buyInfo.visitTime?if_exists}" id="buyTime"/>
            <input name="buyInfo.seckillBranchId" type="hidden" value="${buyInfo.seckillBranchId?if_exists}"/>
            <input name="buyInfo.seckillId" type="hidden" value="${buyInfo.seckillId?if_exists}"/>    
            <input name="buyInfo.waitPayment" type="hidden" value="${buyInfo.waitPayment?if_exists}"/>
            <input name="buyInfo.seckillToken" type="hidden"/>       
			<div class="order-main border equalheight-box">
				<#include "/WEB-INF/pages/order/s1-sidebar.ftl">
				<div class="main equalheight-item">
					<!-- 预订信息 -->
					<#include "/WEB-INF/pages/order/s1-order-info.ftl">
					<!-- 附加产品 -->
					<#include "/WEB-INF/pages/order/s1-append-pro.ftl">
					
					<!-- 订单联系人信息 -->
					<@s.if test='travellerInfoOptions!="null"'>
						<#include "/WEB-INF/pages/order/s1-contact.ftl">
					</@s.if>
					<!--取票人信息-->
					<@s.if test='mainProdBranch.prodProduct.productType=="TICKET" || mainProdBranch.prodProduct.subProductType == "FREENESS"'>
						<#include "/WEB-INF/pages/order/s1-take-titck-man.ftl">
					</@s.if>
					   
					<!-- 游玩人信息 -->
					<@s.if test='!mainProdBranch.prodProduct.subProductType=="FREENESS_FOREIGN"|| !mainProdBranch.prodProduct.subProductType=="FREENESS_FOREIGN" '>
					</@s.if>	
					<#include "/WEB-INF/pages/order/s1-contact-play.ftl">
					
					<!-- 紧急联系人信息 -->
					<@s.if test='mainProdBranch.prodProduct.subProductType=="GROUP" || mainProdBranch.prodProduct.subProductType=="FREENESS_LONG" ||mainProdBranch.prodProduct.subProductType=="GROUP_LONG"&& mainProdBranch.prodProduct.isEContract=="true" '>
						<#include "/WEB-INF/pages/order/s1-contact-em.ftl">
					</@s.if>
					<!-- 预订须知 -->
					<#include "/WEB-INF/pages/order/s1-booking-policy.ftl">
					<!-- 优惠信息 -->
					<#include "/WEB-INF/pages/order/s1-coupon.ftl">
					<!-- 同意协议/提交订单 -->
					<#include "/WEB-INF/pages/order/s1-submit-order.ftl">
				</div>
			</div>
		</div>
		</from>

		<!-- 订单底部 -->
		<div id="order-footer"></div>
		<div id="ticketHiddenDiv" style="display:none;"></div>

		<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
		
		<script type="text/javascript"> 
			document.domain='lvmama.com'; 
			function union_login(url){ 
				window.open(url); 
			} 
			$(function(){
				var isGoOn = false;
				var tmpdataobj = {
					sso_email_c : {
						active : "",
						empty : "email地址不能为空",
						email : "请输入有效的Email地址"
					}
					
				};
				
				var tmpArr = [];
				for(var n in tmpdataobj){
					var elt = $("#"+n);
					elt.length>0 && tmpArr.push(elt);
				}
				var input_s = $("input.email_text_Box");
				tmpArr.sort(function(a,b){
					return input_s.index(a) > input_s.index(b);
				});
				var dataobj = {};
				for(var i=0;i<tmpArr.length;i++){
					dataobj[tmpArr[i].attr("id")] = tmpdataobj[tmpArr[i].attr("id")];
				}
				for(var n in dataobj){
					$("#"+n).val("").ui("validate",dataobj[n]);
				}
			});
		</script> 
		
		<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-calendar.js"></script>
		<!--<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js,/js/v4/order-page.js"></script>-->
		<!--<script src="http://pic.lvmama.com/js/v4/order-page.js"></script>-->
		<script src="/js/order/order-page.js"></script>
		
		<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
		
		<#include "/WEB-INF/pages/order/subOrder.ftl"/>
		
	<script type="text/javascript">
 	   function fillData() {
            var that = this,
                url = "http://www.lvmama.com/product/timePriceJson.do?productId=${buyInfo.productId?if_exists}&branchId=${buyInfo.prodBranchId?if_exists}";
   
            function setData(data) {
	
                if (data === undefined) {
                    return;
                }

                data.forEach(function (arr) {
                    var $td = that.warp.find("td[date-map=" + arr.date + "]");
                    
                    $td.find("span").eq(1).html(arr.number);
                    
                     if (arr.number !== ""&&arr.number=="售完") {
                    	$td.find("div").unbind("click");
                    	$td.find("div").removeClass("caldate").addClass("nodate");
                    }
                    
                    if (arr.price !== "") {
                        $td.find("span").eq(2).html("<dfn>¥" + arr.price + "</dfn>");
                    }else{
                    	$td.find("div").unbind("click");
                    	$td.find("div").removeClass("caldate").addClass("nodate");
                    }
					
                    $td.find("span").eq(3).html(arr.active);
                });
                
                var year = that.options.date.getFullYear(),
                	month = that.options.date.getMonth(),
                	d = new Date(year, month + 1, 0),
                	l = data.length;
                	len =d.getDate() - l,
                	$td = null,
                	date = "";
                                
               // if(len){
                	
               // 	for(var i = 1; i <= len; i++){
               // 		date = year + "-" + that.mend(month + 1) +"-" + that.mend(i+l);
               // 		$td = that.warp.find("td[date-map=" + date + "]");
               // 		$td.find("div").unbind("click");
               // 		$td.find("div").removeClass("caldate").addClass("nodate");
                //	}
               // }

            }

            $.ajax({
                 url: url,
                 type: "POST",
                 dataType: "jsonp",
                 jsonp: 'callback',
                 success: function (json) {
                     var data = json[that.options.date.getMonth() + 1];
                       //data2 = json[((that.options.date.getMonth() + 2) % 12 === 0 ? 12 : (that.options.date.getMonth() + 2) % 12)];
                      //setData(data2);
                      setData(data);
                  },
                 error: function () { }
             });
           
            

        }
        function  callbackClick(){
          var time_1= $("#input_visitTime").val();
           $("#allVisitDate").val(time_1);
           var sub_box=$("#ticketHiddenDiv");
          var _form=$('<form method="post" >'+
          '<input type="hidden" name="buyInfo.productId" value="${buyInfo.productId?if_exists}" />'+
          '<input type="hidden"  name="buyInfo.prodBranchId" value="${buyInfo.prodBranchId?if_exists}" />'+
          '<input type="hidden"  name="buyInfo.channel" value="${buyInfo.channel?if_exists}" />'+
          '<input type="hidden"  name="buyInfo.visitTime" value="'+time_1+'" />'+
          '</form>');
          sub_box.prepend(_form);
          _form.submit();
         }
	    pandora.calendar({
            sourceFn: fillData,selectDateCallback:callbackClick,
            frequent: true
        });
	</script>
	<!--===== 加载JS代码 ======-->
	<script src="/js/order/youhui.js" type="text/javascript"></script>
	<script src="/js/order/emergencyContact.js" type="text/javascript"></script>
	<script src="/js/order/orderBuyBase.js" type="text/javascript"></script>
	<script src="/js/order/orderFillNew.js" type="text/javascript"></script>
	<script src="http://pic.lvmama.com/js/common/losc.js"></script>
	<script>
		<#if mainProdBranch.prodProduct.productType == "HOTEL">
			cmCreatePageviewTag("填写订单-"+"<@s.property value="mainProdBranch.prodProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="mainProdBranch.prodProduct.subProductType"  escape="false"/>", "Q0001", null, null);
			cmCreateConversionEventTag("填写订单-${mainProdBranch.prodProduct.productType?if_exists}-DJJD-${mainProdBranch.prodProduct.subProductType?if_exists}","1","订单流程");
		<#else>
			cmCreatePageviewTag("填写订单-"+"<@s.property value="mainProdBranch.prodProduct.productType"  escape="false"/>-"+"<@s.property value="mainProdBranch.prodProduct.subProductType"  escape="false"/>", "Q0001", null, null);
			cmCreateConversionEventTag("填写订单-${mainProdBranch.prodProduct.productType?if_exists}-${mainProdBranch.prodProduct.subProductType?if_exists}","1","订单流程");
		</#if>
        
    </script>
	</body>
</html>