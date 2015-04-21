<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单支付_驴妈妈旅游网</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="页面关键字">
<meta name="description" content="页面描述">

<!-- 订单通用样式 -->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >

<style type="text/css">
#bankTipsLayer{
width:800px; /*需要修改*/
height:auto;
padding:0;
margin:0 auto;
font-size:12px;
}
#bankTipsLayer:after {
content: "."; 
display: block;
height: 0; 
clear: both; 
visibility: hidden;
}
/*	表格内容*/
#bankTipsLayerCase ul{}
#bankTipsLayerCase ul li{
float:left;
width:160px; /*需要修改*/
}
</style> 

</head>
<body class="order">

<input type="hidden" name="orderId" id="orderId" value="${orderId}"/>
<input type="hidden" name="payAmonut" id="payAmount" value="${payAmountFen}"/>

<!-- 订单公共头部开始 -->
<div class="order-header wrap">
    <div class="header-inner">
        <a class="logo" href="http://www.lvmama.com/">自助天下游 就找驴妈妈</a>
        <p class="welcome">您好，<b>${user.userName }</b> </p>
        <p class="info">24小时服务热线：<strong>1010-6060</strong></p>
        
    </div>
</div> <!-- //.lv-header -->



<!-- 公共头部结束  -->


<div class="wrap">
    
    
	<!-- 操作步骤 -->
	<ol class="ui-step ui-step-3">
	    <li class="ui-step1 ui-step-start ui-step-done">
	        <div class="ui-step-arrow">
	            <i class="arrow_r1"></i>
	            <i class="arrow_r2"></i>
	        </div>
	        <span class="ui-step-text">1.填写订单信息</span>
	    </li>
	    <li class="ui-step2 ui-step-active">
	        <div class="ui-step-arrow">
	            <i class="arrow_r1"></i>
	            <i class="arrow_r2"></i>
	        </div>
	        <span class="ui-step-text">2.选择付款方式付款</span>
	    </li>
	    <li class="ui-step3 ui-step-end">
	        <div class="ui-step-arrow">
	            <i class="arrow_r1"></i>
	            <i class="arrow_r2"></i>
	        </div>
	        <span class="ui-step-text">3.预订成功</span>
	    </li>
	</ol> <!-- //操作步骤 -->
        
    
    <div class="order-main paynew">
		<div class="main">
			<div class="hr_a"></div>
            <div class="c-title">
                <h3>您预订：${orderName }</h3>
                <div class="total-price">
                    <span class="price-num"><dfn>${orderAmountYuan }</dfn> 元</span>
                    <strong>订单金额：</strong>
                </div>
            </div>
        </div> 
            
        <div class="tipbox tip-success tip-nowrap">
            <span class="tip-icon-big tip-icon-big-success"></span>
            <div class="tip-content">
                <h3 class="tip-title">您的订单已提交成功！</h3>
                
                <s:if test="prepayAble">
	                <s:if test="hasNeedPrePay">
	                	<p class="tip-explain">由于资源紧张，需要先行付款才能确定是否有资源。若无资源，会直接将订单钱款退回到您的账户。请您使用 <a class="link-verify active" href="#">预授权支付<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></a></p>
	                </s:if>
	                <s:else>
	                	<p class="tip-explain">我们会尽快对您的订单进行审核，结果通过电话联系 <b class="red"><s:property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)"/></b>，您也可使用 <a class="link-verify" href="#">预授权支付<i class="ui-arrow-bottom blue-ui-arrow-bottom"></i></a></p>
	                </s:else>
	            </s:if>
	            <s:else>
                	<p class="tip-explain">我们会尽快对您的订单进行审核，结果通过电话联系 <b class="red"><s:property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)"/></b>，您也可以在 <a href="http://www.lvmama.com/myspace/order.do">我的订单</a>查看订单状态。</p>
                </s:else>
                
            </div>
        </div>
            
        <div class="dot_line"></div>
        
        <!-- 选择支付方式 -->
        <s:if test="hasNeedPrePay">
       		<div class="payment pay-verify form-inline">
		</s:if>
		<s:else>
       		<div class="payment pay-verify form-inline hide">
		</s:else>
			<div class="pay-title ui-tab-trigger">
				<h4 class="pay-type">付款方式：</h4>
				<ul class="tabnav order-tabnav clearfix J-tabs">
                	<li class="selected"><a href="javascript:;">在线预授权</a></li>
            	</ul>
          	</div>
            <div class="tab-switch payment-list J-switch">
            	<div class="tabcon selected">
                    <div class="xhcontent">
               		 	<div class="tiptext tip-warning">
							<div class="tip-other">
                                <b>什么是预授权？</b> <br>1.在您进行预授权后，应付金额在银行卡中冻结，一旦产品资源得到确认，您的订单款项将自动扣除，即订单支付成功。<br>
                                2.若您预定的产品资源无法确认，则您授权的预授权款项将自动解冻，返回您的银行账户，无需您任何操作及手续费。
                            </div>
                        </div>
                        <p class="typetit">信用卡支持的银行</p>
						<ul class="bank-list clearfix">
					      	<li><label class="radio inline"><i class="bank cmb   " title="招商银行"></i></label></li>
			          		<li><label class="radio inline"><i class="bank ccb   " title="中国建设银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank boc   " title="中国银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank icbc  " title="中国工商银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank comm  " title="交通银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank ecitic" title="中信银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank cmbc  " title="中国民生银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank ceb   " title="中国光大银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank pingan" title="平安银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank psbc  " title="中国邮政储蓄银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank cgb   " title="广发银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank shbank" title="上海银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank spdb  " title="浦发银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank cib   " title="兴业银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank sdb   " title="深圳发展银行"></i></label></li>
						    <li><label class="radio inline"><i class="bank bjbank" title="北京银行"></i></label></li>
						    <li><a href="#" class="ord_bk_more">更多银行>></a></li>
						</ul>
						<p class="typetit">储蓄卡支持的银行</p>
                        <ul class="bank-list clearfix">
							<li><label class="radio inline"><i class="bank comm  " title="交通银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ccb   " title="中国建设银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ceb   " title="中国光大银行"></i></label></li>
							<li><label class="radio inline"><i class="bank shbank" title="上海银行"></i></label></li>
							<li><label class="radio inline"><i class="bank psbc  " title="中国邮政储蓄银行"></i></label></li>
							<li><label class="radio inline"><i class="bank pingan" title="平安银行"></i></label></li>
							<li><label class="radio inline"><i class="bank sdb   " title="深圳发展银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ecitic" title="中信银行"></i></label></li>
							<li><label class="radio inline"><i class="bank cib   " title="兴业银行"></i></label></li>
							<li><label class="radio inline"><i class="bank cgb   " title="广发银行"></i></label></li>
							<li><label class="radio inline"><i class="bank spdb  " title="浦发银行"></i></label></li>
						</ul>
                        <div class="order-btn">
	                        <s:if test="hasNeedPrePay">
                        		<button class="pbtn pbtn-big pbtn-orange ord_atr_btn">&nbsp;&nbsp;预授权支付&nbsp;&nbsp;</button>
							</s:if>
							<s:else>
                        		<button class="pbtn pbtn-big pbtn-orange ord_atr_btn">&nbsp;&nbsp;预授权支付&nbsp;&nbsp;</button>&nbsp;&nbsp;<a href="http://www.lvmama.com/myspace/order.do">不使用预授权，待资源确认后支付>></a>
							</s:else>
                        </div>
                   </div>
               </div>
           </div> <!-- //选择支付方式 -->
		</div>
   </div> 
</div>

<div id="bankTipsLayer"  class="hide">
	 <div id="bankTipsLayerCase">
	 	<!--此处开始循环 -->
	 	<ul>
			 <li>B-F</li>
			 <li>G-H</li>
			 <li>J-P</li>
			 <li>Q-T</li>
			 <li>W-Z</li>
		 </ul>
		 <ul>
			<li>包商银行</li>
			<li>贵阳银行</li>
			<li>江苏银行</li>
			<li>青海银行</li>
			<li>威海市商业银行</li>
		 </ul>
		 <ul>
			<li>长沙银行</li>
			<li>广州银行</li>
			<li>江苏省农村信用社联合社</li>
			<li>齐鲁银行</li>
			<li>潍坊银行</li>
		 </ul>
		 <ul>
			<li>常熟农村商业银行</li>
			<li>广州农村商业银行</li>
			<li>江阴农村商业银行</li>
			<li>上海农商银行</li>
			<li>温州银行</li>
		 </ul>
		 <ul>
			<li>承德银行</li>
			<li>赣州银行</li>
			<li>金华银行</li>
			<li>上饶银行</li>
			<li>乌鲁木齐商业银行</li>
		 </ul>
		 <ul>
			<li>成都农商银行</li>
			<li>哈尔滨银行</li>
			<li>九江银行</li>
			<li>顺德农村商业银行</li>
			<li>无锡农村商业银行</li>
		 </ul>
		 <ul>
			<li>重庆农村商业银行</li>
			<li>湖南省农村信用社联合社</li>
			<li>兰州银行</li>
			<li>台州银行</li>
			<li>宜昌市商业银行</li>
		 </ul>
		 <ul>
			<li>重庆银行</li>
			<li>徽商银行</li>
			<li>龙江银行</li>
			<li>&nbsp;&nbsp;</li>
			<li>银川市商业银行</li>
		 </ul>
		 <ul>
			<li>大连银行</li>
			<li>河北银行</li>
			<li>南昌银行</li>
			<li>&nbsp;&nbsp;</li>
			<li>尧都农村商业银行</li>
		 </ul>
		 <ul>
			<li>东营银行</li>
			<li>杭州银行股份有限公司</li>
			<li>南京银行</li>
			<li>&nbsp;&nbsp;</li>
			<li>鄞州银行</li>
		 </ul>
		 <ul>
			<li>福建农信社</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>浙江稠州商业银行</li>
		 </ul>
		 <ul>
			<li>鄂尔多斯银行</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>浙江泰隆商业银行</li>
		 </ul>
		 <ul>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>浙江民泰商业银行</li>
		 </ul>
		 <ul>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;</li>
			<li>吴江农村商业银行</li>
		 </ul>
		 <!--到此循环结束 -->
	 </div>
</div>

<div id="payWaitDiv" class="hide">
    <h4>请根据您的情况点击以下按钮：</h4>
    <p><a class="pbtn pbtn-big payOkAfter" href="#" onclick="payOKButton()">&nbsp;&nbsp;已完成付款&nbsp;&nbsp;</a>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <a class="pbtn pbtn-big payOkAfter" href="#" onclick="payOKButton()">&nbsp;&nbsp;付款遇到问题&nbsp;&nbsp;</a></p>
</div>


<!-- 订单底部 -->
<div id="order-footer" class="lv-footer"></div>


<!-- jQuery以及通用js-->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
<script src="http://pic.lvmama.com/js/v4/order-page.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>

<script type="text/javascript">
$(function(){
	
	$(".link-verify").click(function(){ 
		$(this).toggleClass("active"); 
		$(".pay-verify").toggleClass("hide"); 
	}); 

    // tabs
    function JS_tab_nav(tab_nav,tab_con,selected,tri_type){
        $tab_obj=$(tab_nav);
        $tab_obj.bind(tri_type,function(){
            var tab_li_index = $(tab_nav).index(this);
            $(this).addClass(selected).siblings().removeClass(selected);
            $(tab_con).eq(tab_li_index).show().siblings(tab_con).hide();
            // return false;
        });
    };
    JS_tab_nav(".J-tabs li:not('.paylink')",".J-switch>.tabcon","selected","click");

	$(".ord_bk_more").click(function(){
	    // “更多银行”弹出层
	    pandora.dialog({
	        title: "更多支持银行-信用卡",
	        content: $("#bankTipsLayer"),
	        wrapClass: "dialog-big"
	    });
	});

	$(".ord_atr_btn").click(function(){
		window.open('${constant.paymentUrl}pay/chinaPre.do?objectId=${orderId}&amount=${payAmountFen}&objectType=${objectType}&paymentType=${paymentType}&bizType=${bizType}&signature=${signature}&noCardFlag=CHINAPAY_PRE','lvmamaPay');
		openPayWaitWindow();

	});
	
});
	
	function openPayWaitWindow(){
		// 付款状态
	    pandora.dialog({
	        title: "付款状态",
	        content: $("#payWaitDiv")
	    });
	}
	
	function payOKButton(){
		var address = "http://hotels.lvmama.com/vst_front/order/view.do?orderId=${orderId}";
		window.location.href = address;
	}
	
</script>
</body>
</html>
