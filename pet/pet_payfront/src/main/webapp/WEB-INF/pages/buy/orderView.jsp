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
<meta http-equiv="Pragma" content="no-cache">

<!-- 订单通用样式 -->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/v4/modules/arrow.css,/styles/v4/modules/button.css,/styles/v4/modules/forms.css,/styles/v4/modules/selectbox.css,/styles/v4/modules/step.css,/styles/v4/modules/tags.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/tables.css,/styles/v4/modules/bank.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css" >

<!--  jQuery以及通用js -->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
<script src="http://pic.lvmama.com/js/v4/order-page.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
</head>
<body class="order">

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
		</div> <!-- //.main -->
	
		<!-- 支付方式 -->
		<div class="paynewbox clearfix">
	    	<div class="paytips">
                <p>友情提示：您的预订信息已提交，请在 <b class="red">${lastPayTime }</b> 前完成付款，<span class="timebox">剩余付款时间：<b class="countdown red">${lastPayWaitTime }</b></span></p>
            </div>
            
            <div class="dot_line"></div>
            
	    	<div class="check-text"><label class="checkbox inline">
	    		<s:if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
					<input class="input-checkbox moneyAccountCheck" name="ownpro" type="checkbox">使用驴妈妈存款账户余额
				</s:if>
				<s:else>
					<input class="input-checkbox" name="ownpro" type="checkbox" disabled="disabled">使用驴妈妈存款账户余额
				</s:else>
				</label>
			</div>
			<div class="check-text"><label class="checkbox inline"><input class="input-checkbox storedCheck" name="ownpro" type="checkbox">使用驴妈妈储值卡</label></div>
	
			<%--<div class="check-text">
				<label class="checkbox inline">
				<s:if test="isCanBounusPay">
					<input class="input-checkbox bounusPayCheck" name="ownpro" type="checkbox">使用奖金账户余额
				</s:if>
				<s:else>
					<input class="input-checkbox" name="ownpro" type="checkbox" disabled="disabled">使用奖金账户余额
				</s:else>
				</label>
			</div>--%>
			
            <div class="dot_line"></div>
            
			<h4 class="pay-price">您还需继续付款 <dfn><i>${oughtPayYuan }</i></dfn> 元</h4>
			
		</div>  
		<!-- 选择支付方式 -->
		<div class="payment form-inline">
	    	<div class="pay-title ui-tab-trigger">
	        	<h4 class="pay-type">付款方式：</h4>
		    	<ul class="tabnav order-tabnav clearfix J-tabs">
		        	<li class="selected"><a href="javascript:;">支付平台/银行卡</a></li>
		        	<li><a href="javascript:;">电话支付</a></li>
		    	</ul>
			</div>
			<div class="tab-switch payment-list J-switch">
		    	<div class="tabcon selected">
		    		<div class="xhcontent">
		         		<ul class="bank-list clearfix">
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'tenpay')"><i class="bank tenpay" title="财付通"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'tenpayDirectpay')"><i class="bank tenpay-quick" title="财付通快捷"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'weixinWeb')"><i class="bank weixinpay" title="微信扫码"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'alipay')"><i class="bank alipay" title="支付宝"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'alipayScannerCode')"><i class="bank alipayCode" title="支付宝扫码"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'alipayDirectpay')"><i class="bank alipay-quick" title="支付宝快捷"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'unionpay')"><i class="bank unionpay" title="银联"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'unionpayDirect')"><i class="bank unionpay" title="银联快捷"></i></label></li>
				             <li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'chinaMobile')"><i class="bank cmpay" title="中国移动手机支付"></i></label></li>
		         		</ul>
		         		<div class="dot_line"></div>
		         		<ul class="bank-list clearfix">
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="beforeSubmitGateway(this,'icbc')"><i class="bank icbc" title="中国工商银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CMB')"><i class="bank cmb" title="中国招商银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CCB')"><i class="bank ccb" title="中国建设银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BOCB2C')"><i class="bank boc" title="中国银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'ABC')"><i class="bank abc" title="中国农业银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank','COMM')"><i class="bank comm" title="中国交通银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CEBBANK')"><i class="bank ceb" title="中国光大银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SPDB')"><i class="bank spdb" title="浦发银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'GDB')"><i class="bank cgb" title="广发银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CITIC')"><i class="bank ecitic" title="中信银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CIB')"><i class="bank cib" title="兴业银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'CMBC')"><i class="bank cmbc" title="中国民生银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BJBANK')"><i class="bank bjbank" title="北京银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'HZCBB2C')"><i class="bank hzbank" title="杭州银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SHBANK')"><i class="bank shbank" title="上海银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'BJRCB')"><i class="bank bjrcb" title="北京农村商业银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'SPABANK')"><i class="bank pingan" title="平安银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'FDB')"><i class="bank fudian" title="富滇银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'NBBANK')"><i class="bank nbcb" title="宁波银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'POSTGC')"><i class="bank psbc" title="中国邮政储蓄银行"></i></label></li>
							<li><label class="radio inline"><input class="input-radio" name="bankpay" type="radio" clickEvent="alipayWithBank(this,'ningboBank', 'WZCBB2C-DEBIT')"><i class="bank wzcb" title="温州银行"></i></label></li>
		         		</ul>
		 			</div>
		 			<div class="order-btn"><button class="pbtn pbtn-big pbtn-orange" onclick="selectBanks(this)">&nbsp;&nbsp;下一步&nbsp;&nbsp;</button></div>
		 		</div>
		 		<div class="tabcon">
		     		<div class="xhcontent">
						<p><img src="http://pic.lvmama.com/img/v4/order/paytype2.png" alt="电话支付" /></p>
						<p class="typetit">以下银行的<span class="orange">借记卡</span>，支持电话支付，无需开通网银也能支付：<span class="orange">（单笔及日累计额度均为4万）</span></p>
		    			<ul class="bank-list clearfix">
							<li><label class="radio inline"><i class="bank cmb   " title="招商银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ccb   " title="中国建设银行"></i></label></li>
							<li><label class="radio inline"><i class="bank boc   " title="中国银行"></i></label></li>
							<li><label class="radio inline"><i class="bank icbc  " title="中国工商银行"></i></label></li>
							<li><label class="radio inline"><i class="bank comm  " title="交通银行"></i></label></li>
							<li><label class="radio inline"><i class="bank cmbc  " title="中国民生银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ceb   " title="中国光大银行"></i></label></li>
							<li><label class="radio inline"><i class="bank pingan" title="平安银行"></i></label></li>
							<li><label class="radio inline"><i class="bank cgb   " title="广发银行"></i></label></li>
							<li><label class="radio inline"><i class="bank shbank" title="上海银行"></i></label></li>
							<li><label class="radio inline"><i class="bank spdb  " title="浦发银行"></i></label></li>
							<li><label class="radio inline"><i class="bank cib   " title="兴业银行"></i></label></li>
							<li><label class="radio inline"><i class="bank sdb   " title="深圳发展银行"></i></label></li>
						</ul>
						<p class="typetit">以下银行的<span class="orange">信用卡</span>，支持电话支付，无需开通网银也能支付：<span class="orange">（单笔及日累计额度均为4万）</span></p>
						<ul class="bank-list clearfix">
							<li><label class="radio inline"><i class="bank icbc  " title="中国工商银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ccb   " title="中国建设银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ceb   " title="中国光大银行"></i></label></li>
							<li><label class="radio inline"><i class="bank shbank" title="上海银行"></i></label></li>
							<li><label class="radio inline"><i class="bank pingan" title="平安银行"></i></label></li>
							<li><label class="radio inline"><i class="bank sdb   " title="深圳发展银行"></i></label></li>
							<li><label class="radio inline"><i class="bank ecitic" title="中信银行"></i></label></li>
						</ul>
					</div>
		   		</div><!--ord_atr_callbox--> 
			</div>
		</div>
	</div>
</div>

<s:if test='user.mobileNumber!=null&&user.isMobileChecked=="Y"'>
	<s:include value="moneyAccountPay.jsp"></s:include>
</s:if>
<s:include value="storedToPay.jsp"></s:include>
<s:if test="isCanBounusPay">
	<s:include value="bounusPay.jsp"></s:include>
</s:if>

<div id="payWaitDiv" class="hide">
    <h4>请在新开页面付款后选择：</h4>
    <p><a class="pbtn pbtn-big payOkAfter" href="#" onclick="payOKButton()">&nbsp;&nbsp;已完成付款&nbsp;&nbsp;</a>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <a class="pbtn pbtn-big payOkAfter" href="#" onclick="payQuestionButton()">&nbsp;&nbsp;付款遇到问题&nbsp;&nbsp;</a></p>
</div>

<!-- 订单底部 -->
<div id="order-footer" class="lv-footer"></div>



<script type="text/javascript">
	$(function(){
		
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
	
	
		$(".moneyAccountCheck").click(function(){
		    // 使用驴妈妈存款账户余额
		    if(this.checked){
			    pandora.dialog({
			        title: "使用驴妈妈存款账户余额",
			        content: $("#moneyAccountPay"),
			        wrapClass: "dialog-middle",
			        width: "750px"
			    });
		    }
		});
		
		$(".storedCheck").click(function(){
		    // 使用驴妈妈储值卡
		    if(this.checked){
		    	pandora.dialog({
		            title: "使用驴妈妈储值卡",
		            content: $("#storedPayDiv"),
		            wrapClass: "dialog-middle"
		        });
		    }
		});	
		
		$(".bounusPayCheck").click(function(){
		    // 使用奖金账户
		    if(this.checked){
		    	pandora.dialog({
			        title: "使用奖金账户",
			        content: $("#bounusPayDiv"),
		            wrapClass: "dialog-middle"
			    });
		    }
		});
		
	});

	function beforeSubmitGateway(a, gateway, noCardFlag){
		openPayWaitWindow();
		window.open('${constant.paymentUrl}pay/'+gateway+'.do?objectId=${orderId}&amount=${payAmountFen}&objectType=${objectType}&paymentType=${paymentType}&waitPayment=${waitPayment}&approveTime=${approveTime}&visitTime=${visitTime}&bizType=${bizType}&objectName=${orderName}&royaltyParameters=${royaltyParameters}&signature=${signature}&noCardFlag='+noCardFlag,'lvmamaPay');
	}
	
	function alipayWithBank(a, gateway, bankId){
		openPayWaitWindow();
	     window.open('${constant.paymentUrl}pay/'+gateway+'.do?objectId=${orderId}&bankid='+bankId+'&amount=${payAmountFen}&objectType=${objectType}&paymentType=${paymentType}&waitPayment=${waitPayment}&approveTime=${approveTime}&visitTime=${visitTime}&bizType=${bizType}&royaltyParameters=${royaltyParameters}&signature=${signature}','lvmamaPay');
	}
	
	function selectBanks(form){
		var bank = $('input:radio[name="bankpay"]:checked');    
		var clickEvent=bank.attr("clickEvent");
		if(clickEvent!=null){
       		eval("("+clickEvent+")");
		}else{
   			alert("请选择支付方式");
		}
	}

	function openPayWaitWindow(){
		// 付款状态
	    pandora.dialog({
	        title: "付款状态",
	        content: $("#payWaitDiv")
	    });
	}

	// 换一张验证码
	function refreshw(id) {
	    document.getElementById(id).src = "/payfront/account/checkcode.htm?&now=" + new Date();
	};

	function payOKButton(){
		var address = "http://hotels.lvmama.com/vst_front/order/view.do?orderId=${orderId}";
		window.location.href = address;
	}
	function payQuestionButton(){
		window.open("http://www.lvmama.com/public/order_and_pay#m_1_3");
	}
	
</script>
</body>
</html>
