<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>在线预授权_驴妈妈旅游网</title>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_globalV1_0.css,/styles/super_v2/orderV2.css,/styles/new_v/header-air.css"/>

<style type="text/css">
#tipsWindow span {cursor: pointer;position: absolute;right: 20px;top: 20px;}
#tipsWindow .ord_ysqpop_txt {line-height: 20px;padding: 15px 0;}
#pageOverlay{display:none;position:absolute; top:0; left:0; z-index:99; width:100%; height:100%; background:#000; filter:alpha(opacity=70); opacity:0.7;}
</style>
<#include "/common/coremetricsHead.ftl">
</head>


<body>
<div class="w-980">
    <!--== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
	<!--===== 头部文件区域 E ======-->

	<!--===== 导航条 S ======-->
	    <@navigation productType="${order.mainProduct.productType?if_exists}" 
			subProductType="${order.mainProduct.subProductType?if_exists}" 
			resourceConfirm="${order.resourceConfirm}"
			eContract = "${order.isNeedEContract()?string('true','false')}" 
			stepView="payView"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
	<!--===== 导航条 E ======-->
	
	<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
   	    <div class="ord_hintbox">
        	<a href="http://www.lvmama.com/product/${order.mainProduct.productId?if_exists}" target="_blank" class="ord_hintbox_prolink">${order.mainProduct.productName?if_exists}</a> <a class="ord_dtl_link" href="#">订单详情>></a>
       	    <span class="price">应付总额：<strong>&yen;${order.oughtPayYuanFloat?if_exists}</strong>元</span>
        </div><!--ord_hintbox-->
        <div class="ord_dtl_pop" style="display: none;">
		     <div class="ord_dtlpop_arrow"></div>
		     <#include "/WEB-INF/pages/buy/orderInfo.ftl"/>
		</div>
        <!--===== hint S ======-->
         <div class="ord_tipbox">
            <div class="atr_pay_hint">
               <em><#if order.isNeedEContract()>您已成功签约.<#else>您的订单已提交成功.</#if></em>
               <#if order.hasNeedPrePay()>
	               <p>请使用预授权进行支付，支付成功后我们会优先为您确认资源。</p>
	               <p>注：请于${lastWaitPaymentTimeOfString}之前完成支付，逾期订单将失效</p>
               <#else>
               		<p>我们将尽快确认资源，以手机短信的形式通知到您，请耐心等待。</p>
               </#if>
               <ul class="atr_pay_listtxt">
               	<#if order.hasNeedPrePay()>
               	  <li>• 由于您下单的时间很接近或超过了最晚取消时间，此订单订购成功后为不退不改订单，如有疑问可致电客服中心。</li>
				  <li>• 一旦资源确认，您无需再次登录网站进行支付操作。</li>             
               	<#else>
                  <li>• 在资源确认前，您可使用预授权，我们会优先为您确认资源，一旦资源确认，您无需再次登录网站进行支付操作。</li>
				  <!--<a href="${base}/view/view.do?orderId=${order.orderId}&preSalePowered=false" target="_blank">不使用银行卡预授权</a>-->
                  <li>• 您也可以不使用银行卡预授权，待资源确认后，您可在指定的时间内再次登录驴妈妈进行支付。</li>
                </#if>
                  <li>• 您可在 我的驴妈妈><a href="http://www.lvmama.com/myspace/order.do" target="_blank">我的订单</a>中查看订单状态</li>
               </ul>
            </div>
         </div><!--ord_tipbox-->
        <ul class="o_cont_newtit">
           <li class="o_cont_tit_crt">在线预授权</li>
           <li id="ord_calltit">百付电话支付</li>
       </ul><!--o_cont_newtit-->
 	    <div class="o_container_new">
           <div class="ord_atr_callbox">
	        <dl class="ord_tipbox">
                <dt>什么是预授权？<dt>
                <dd>1、在您进行预授权后，应付金额在银行卡中冻结，一旦产品资源得到确认，您的订单款项将自动扣除，即订单支付成功。</dd>
                <dd>2、若您预订的产品资源无法确认，则您授权的预授权款项将自动解冻，返回您的银行账户，无需您任何操作及手续费。</dd>
			</dl>
       	    <div class="ord_col_tit">信用卡支持的银行</div>
            <div class="ord_inner ord_bank clearfix">
                <div class="ord_banklist">
                 <span class="ord_bk_zs"></span>
                 <span class="ord_bk_js"></span>
                 <span class="ord_bk_zg"></span>
                 <span class="ord_bk_gs"></span>
                 <span class="ord_bk_ny"></span>
                 <span class="ord_bk_jt"></span>
                 <span class="ord_bk_zx"></span>
                 <span class="ord_bk_ms"></span>
                 <span class="ord_bk_gd"></span>
                 <span class="ord_bk_pa"></span>
                 <span class="ord_bk_yz"></span>
                 <span class="ord_bk_gf"></span>
                 <span class="ord_bk_sh"></span>
                 <span class="ord_bk_pf"></span>
                 <span class="ord_bk_xy"></span>
                 <span class="ord_bk_sz"></span>
                 <span class="ord_bk_bj"></span>
                 <a href="#" class="ord_bk_more">更多银行>></a>
                 </div>
                 <div class="ord_bank_pop">
                    <div class="ord_bank_inner">
                      <h3 class="ord_bank_poptit">更多支持银行-信用卡</h3>
                      <ul class="bkpop_list">
                         <li>
                            <em>B-F</em>
                            <span>包商银行</span> 
                            <span>长沙银行</span> 
                            <span>常熟农村商业银行 </span>
                            <span>承德银行 </span>
                            <span>成都农商银行 </span>
                            <span>重庆农村商业银行 </span>
                            <span>重庆银行 </span>
                            <span>大连银行 </span>
                            <span>东营银行 </span>
                            <span>福建农信社 </span>
                            <span>鄂尔多斯银行 </span>
                         </li>
                         <li>
                            <em>G-H </em>
                            <span>贵阳银行</span> 
                            <span>广州银行 </span>
                            <span>广州农村商业银行</span> 
                            <span>赣州银行 </span>
                            <span>哈尔滨银行 </span>
                            <span>湖南省农村信用社联合社</span> 
                            <span>徽商银行 </span>
                            <span>河北银行 </span>
                            <span>杭州银行股份有限公司</span> 
                         </li>
                         <li>
                            <em>J-P</em>
                            <span>江苏银行</span>  
                            <span>江苏省农村信用社联合社</span>  
                            <span>江阴农村商业银行</span>  
                            <span>金华银行 </span> 
                            <span>九江银行 </span> 
                            <span>兰州银行 </span> 
                            <span>龙江银行 </span> 
                            <span>南昌银行 </span> 
                            <span>南京银行</span> 
                         </li>
                         <li>
                            <em>Q-T</em> 
                            <span>青海银行</span>  
                            <span>齐鲁银行 </span> 
                            <span>上海农商银行 </span> 
                            <span>上饶银行 </span> 
                            <span>顺德农村商业银行</span>  
                            <span>台州银行</span> 
                         </li>
                         <li>
                            <em>W-Z</em> 
                            <span>威海市商业银行</span>  
                            <span>潍坊银行</span>  
                            <span>温州银行</span>
                            <span>乌鲁木齐商业银行</span> 
                            <span>无锡农村商业银行</span> 
                            <span>宜昌市商业银行</span> 
                            <span>银川市商业银行</span> 
                            <span>尧都农村商业银行</span> 
                            <span>鄞州银行</span> 
                            <span>浙江稠州商业银行</span> 
                            <span>浙江泰隆商业银行</span> 
                            <span>浙江民泰商业银行</span> 
                            <span>吴江农村商业银行</span>
                         </li>
                      </ul>
                    </div>
                    <div class="ord_bkpop_arrow"></div>
                 </div>
            </div>  
            <div class="ord_col_tit">储蓄卡支持的银行</div>
            <div class="ord_inner ord_bank ord_banklist clearfix">
                 <span class="ord_bk_jt"></span>
                 <span class="ord_bk_js"></span>
                 <span class="ord_bk_ny"></span>
                 <span class="ord_bk_gd"></span>
                 <span class="ord_bk_sh"></span>
                 <span class="ord_bk_yz"></span>
                 <span class="ord_bk_pa"></span>
                 <span class="ord_bk_sz"></span>
                 <span class="ord_bk_zx"></span>
                 <span class="ord_bk_xy"></span>
                 <span class="ord_bk_gf"></span>
                 <span class="ord_bk_pf"></span>
            </div>
            
            <div class="ord_act_box">
            	<input type="hidden" name="orderId" id="orderId" value="${order.orderId}"/>
            	<input type="hidden" name="payAmonut" id="payAmount" value="${order.oughtPayYuan-order.actualPayFloat}"/>
                <input type="button" value="使用预授权" class="ord_atr_btn"/>
                <#if !order.hasNeedPrePay()>
             		<a href="${base}/view/view.do?orderId=${order.orderId}&preSalePowered=false">不使用预授权，待资源确认后支付>></a>
             	</#if>
            </div> 
          </div><!--ord_atr_callbox--> 
           <div class="ord_atr_callbox">
                <ul class="ord_step_hint">
                     <li class="ord_step_cont">提交订单，并致电客服（1010 6060）<br/>告知订单号</li>
                     <li class="ord_step_arrow"></li>
                     <li class="ord_step_cont">客服人员在核实您的个人信息后，帮<br/>您转入银行卡电话支付语音系统</li>
                     <li class="ord_step_arrow"></li>
                     <li class="ord_step_cont">按语音提示操作，支付成<br/>功，接受短信</li>
                </ul>
           
       	    <div class="ord_col_tit">信用卡支持的银行</div>
            <div class="ord_inner ord_bank clearfix">
                <div class="ord_banklist">
                 <span class="ord_bk_zs"></span>
                 <span class="ord_bk_js"></span>
                 <span class="ord_bk_zg"></span>
                 <span class="ord_bk_gs"></span>
                 <span class="ord_bk_ny"></span>
                 
                 <span class="ord_bk_jt"></span>
                 <span class="ord_bk_ms"></span>
                 <span class="ord_bk_gd"></span>
                 <span class="ord_bk_pa"></span>
                 <span class="ord_bk_gf"></span>
                 
                 <span class="ord_bk_sh"></span>
                 <span class="ord_bk_pf"></span>
                 <span class="ord_bk_xy"></span>
                 <span class="ord_bk_sz"></span>
                 </div>
                 
            </div>  
            <div class="ord_col_tit">储蓄卡支持的银行</div>
            <div class="ord_inner ord_bank ord_banklist clearfix">
                 <span class="ord_bk_gs"></span>
                 <span class="ord_bk_js"></span>
                 <span class="ord_bk_ny"></span>
                 <span class="ord_bk_gd"></span>
                 <span class="ord_bk_sh"></span>
                 <span class="ord_bk_pa"></span>
                 <span class="ord_bk_sz"></span>
                 <span class="ord_bk_zx"></span>
            </div>
            <#if !order.hasNeedPrePay()>
            <div class="ord_act_box">
             	<a href="${base}view/view.do?orderId=${order.orderId}&preSalePowered=false">不使用预授权，待资源确认后支付>></a>
            </div> 
            </#if>
          </div><!--ord_atr_callbox--> 
        </div><!--o_container_new-->
</div><!--980-->
<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<!--===== 底部文件区域 E ======-->

<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js?r=8391"></script>
<!--==意见反馈入口S==-->
<style>
#u_container{width:11px; height:69px; position:fixed;}#usercenter_float{background:url(http://pic.lvmama.com/img/bg_floating.gif) no-repeat 0 0; display:block; width:11px; height:53px; line-height:15px; padding:5px 4px 11px;}a#usercenter_float{color:#404040;text-decoration:none;}a#usercenter_float:hover{color:#c06;text-decoration:none;}
</style>
<!--<div id="u_container">
	<a id="usercenter_float" target="_blank" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do">意见反馈</a>
</div>-->

<!--addpop-->
<div id="tipsWindow"><span class="ysq_popclose"><img src="http://pic.lvmama.com/img/icons/close.gif">&nbsp;关闭</span>
        <h3>预授权遇到问题？</h3>
        <p class="ord_ysqpop_txt"><img src="http://pic.lvmama.com/img/icons/warning.gif">&nbsp;预授权完成前请不要关闭此窗口。完成预授权后请根据你的情况点击下面的按钮：</p>
        <strong>请在新开网页上完成预授权后再选择。</strong>
        <div class="tbtn">
            <input type="button" id="completePayBtn" value="已完成预授权" name="completePayBtn"   onclick="javascript:completePay()"/>
            <input type="button" target="_blank" value="预授权遇到问题" onclick="javascript:window.open('http://www.lvmama.com/public/order_and_pay#m_1_5')"/>
        </div>
        <a href="javascript:void(0);" id="ysq_return">返回重新选择预授权方式</a>
</div>
<div id="pageOverlay"></div>
<!--addpop-->

<script type="text/javascript"> 
	function completePay(){	
		$.ajax({
			type: "POST",
			url: "/ajax/isOrderSuccessPay.do",
			async: false,
			data: {orderId: <@s.property value="order.orderId"/> },
			dataType: "json",
			success: function(response) {		    		
				if (response.success == true) {				    			    				
					window.location="/order/toSuccess.do?orderId=" + <@s.property value="order.orderId"/>;						
				}else{			
				    alert("订单未支付成功！");
					closeMsg('tipsWindow','bgColor');
				}
			}
		});
	}
	
	function openBanks(orderId,payAmount){
		window.open('${constant.paymentUrl}pay/chinaPre.do?objectId='+orderId+'&amount=${order.oughtPayFenLong}&objectType=${objectType}&paymentType=${paymentType}&bizType=${bizType}&signature=${signature}&noCardFlag=CHINAPAY_PRE','lvmamaPay');    
	}
	
	$(function(){
		function sucshow(showPop,evtobj,black_bg){ 
			   var w_scroll =parseInt(document.body.offsetWidth/2);
			   var w_object =parseInt($(showPop).width()/2);
			   //var e_obj_top=$(evtobj).offset().top;
			   var e_obj_top=$(window).scrollTop()+$(window).height()/2-$(showPop).height()/2;
			   var l_obj =w_scroll-w_object;
			   var t_obj =e_obj_top;
			   $(showPop).css("left",l_obj);
			   $(showPop).css("top",t_obj);
			   
			   $(showPop).show();
			   var dh=document.body.scrollHeight;
			   var wh=window.screen.availHeight;
			   var yScroll;
			   if(dh>wh){
				   yScroll =dh; 
			   }else{
				   yScroll = wh; 
				   }
			   $(black_bg).css("height", yScroll);
			   $(black_bg).show();
			 }//弹出层
	  close_evt(".ysq_popclose","#tipsWindow","#pageOverlay");//关闭弹出层
	  close_evt("#completePayBtn","#tipsWindow","#pageOverlay");//关闭弹出层
	  close_evt("#ysq_return","#tipsWindow","#pageOverlay");//关闭弹出层
	  function close_evt(close_btn,popdiv,black_bg){	      
				$(close_btn).bind("click",function(){
				$(this).parents(popdiv).hide();
				$(black_bg).hide();
			 });
	   }//弹出层关闭
      
		$(".ord_atr_btn").click(function(){
			openBanks($('#orderId').val(),$('#payAmount').val());
			sucshow("#tipsWindow",".ord_atr_btn","#pageOverlay");
		});
		$(".tit-payway").click(function(){
			$(this).siblings(".ico-payway").slideDown(400,function(){
			$(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-down.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
		})
		.end().parent().siblings().children(".ico-payway").slideUp(400,function(){
			$(this).siblings("p").css({"background":"url('http://pic.lvmama.com/img/icons/btn-right.gif') no-repeat scroll 99% 50% ","background-color":"#f0f0f0"})
   
		});
		})
      });
</script>
<script type="text/javascript"> 

</script>



<script>
jQuery(function($){
FollowDiv = {
	  follow : function(){
		  $("#u_container").css("position","absolute");
		  $(window).scroll(function(){
			  var f_top = $(window).scrollTop() + $(window).height() - $("#u_container").height()-69;
			  $("#u_container").css("top",f_top+"px");
		  });
	  }
}
		var leftPX = ($(window).width()-980)/2+982;
		var topPX = $(window).height()-$("#u_container").height()-69;
		$("#u_container").css({"left":leftPX+"px","top":topPX+"px"})
						   
			if($.browser.msie && $.browser.version == 6) {
				FollowDiv.follow();
			}
});
</script>
<link type="text/css" rel="stylesheet" href="/style/cards01.css">
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ob_order/ord_atr.js"></script>
<#include "/common/ga.ftl"/>
<!--==意见反馈入口E==-->

<script>
     <#if order.mainProduct.productType == "HOTEL">
		cmCreatePageviewTag("预授权支付方式-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
	<#else>
		cmCreatePageviewTag("预授权支付方式-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
	</#if>
    </script>
</body>
</html>
