<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8" />
		<title><#if product??>${product.productName}</#if>_积分商城—驴妈妈旅游网</title>
		<link href="http://pic.lvmama.com/styles/points/points_mall.css" rel="stylesheet" />
		<link href="http://pic.lvmama.com/min/index.php?g=commonCss" rel="stylesheet"/>
		<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css">
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" > 
		<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">
		<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/widget.css" /> 
		<#include "/common/coremetricsHead.ftl">
	</head>
	
	<body>
		<!-----------头部文件区域 S-------------->
		<#include "/common/header.ftl">
		<div class="wrap">
			<div class="lv-crumbs oldstyle">
					<p>
						<span>您当前所处的位置：</span>
						<span class="crumbs-arrow"></span>
						<span><a href="/points">积分商城</a></span>
						<span class="crumbs-arrow"></span>
						<span><#if product??>${product.productName}</#if></span>
					</p>
			</div>
			<!----------------------------mainLeft-------------------------->
			<#include "/WEB-INF/pages/shop/mainLeft.ftl">
			<!----------------------------mainRight-------------------------->
			
			<div class="col-w fr">
            	<#if product??>
            		<form id="myForm" method="POST" action="/shop/initOrder.do">
            		<input type="hidden" name="productId" id="productId" value="${product.productId}" />
					<#if raffleCode??><input type="hidden" name="raffleCode" id="raffleCode" value="${raffleCode}" /></#if>
					<@s.token></@s.token>
            		<div class="point_details clearfix">
            			<div class="slide-points">
            				<div id="slide_point_details" class="slide_point_details">
            					<div class="point_slidebox J_slidebox">
            						<@s.iterator value="product.fourAbsolutePictureUrl" var="url">
	            						  <div class="slide_item">
	            						  	<a href="#"><i class="middle-full"></i><img src="${url}" ></a>
	            						  </div>
            						  </@s.iterator>
            					</div>
            					<ul class="point_slidesnav J_slidenav">
        						  <@s.iterator value="product.fourAbsolutePictureUrl" var="url">
        						  	<li><a href="#"><img src="${url}" alt="" /></a></li>
        						  </@s.iterator>
            					</ul> 
            				</div>
            			</div>
        				<div class="details_info">
			                <h2>${product.productName}</h2>
			                <#if product.endTime??>
			                	<p class="count_time">剩余时间：<span class="details_countdown">${product.lifeTime}</span></p>
			                </#if>
			                <p>市场价：<del>${product.marketPrice}</del>元</p>
			                <p>所需积分：<dfn class="num"><i>${product.pointChange}</i></dfn>分</p>
			                <p>当前库存：<span class="orange">${product.stocks}</span>件</p>
			                <p>
			                	<#if product.isCanSell == "Y">
			                		<input type="button" class="btn btn-big btn-orange" id="btnSubmit"  value="&nbsp;&nbsp;&nbsp;开始抽奖&nbsp;&nbsp;&nbsp;">
			                	<#else>
			                		<button class="btn btn-big btn-inverse disabled" disabled>&nbsp;&nbsp;&nbsp;开始抽奖&nbsp;&nbsp;&nbsp;</button>
			                	</#if>
			                </p>
     				   </div>
     				   <#if checkDes!=null && checkDes!="" >
     				   		<div class="xh_boxinfo">
				                <h5>提示：</h5>
				                <p>${checkDes}</p>
			            	</div>
     				   </#if>
            		</div>
            		<div class="points-box point_info">
        				<div class="ptitle">
           					<h4>商品详情 <small>（以下信息仅为参考，如有疑问请联系客服）</small></h4>
        				</div>
        				<div class="content">
        					${product.content}
        				</div>
            		</div>
            		</from>
            	</#if>
			</div>
    	</div>
    	
    	<#if shopUser??>
    		<!--<input type="hidden" name="productId" id="productId" value="${product.productId}" />-->
		 </#if>
		<!-----------底部文件区域 S -------------->
		<#include "/common/orderFooter.ftl">
		
		 <#if showRaffleResult?? & showRaffleResult == "true">
		 	 <div id="zebi" style="display:none;"></div>
		     <div class="lotteryPrompt" id="ShowlotteryPrompt" style="z-index:9999;">
        		<div class="lotteryPromptMid">
        			<h3>抽奖提示</h3>
            		<div class="close" data-hide="lotteryPrompt"></div>
            		<#if raffleCode??>
            			<p class="winning">恭喜您抽奖成功</p>
            			<a href="javascript:void(0)" class="write clickHide" data-hide="lotteryPrompt" onclick="$('#myForm').submit()">去填写订单信息</a>
            		<#else>
            			<p class="sorry">很遗憾，再接再厉！</p>
            			<p><a href="http://www.lvmama.com/points">看看其他商品？</a>
            			<a href="javascript:void(0)" class="again clickHide" data-hide="lotteryPrompt" id="againlotteryPrompt">再抽一次</a></p>    
            		</#if>
        		</div>
        </#if>	
	
	<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/v3/plugins.js,/js/v3/app.js"></script> 
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/slides.jquery.js,/js/v3/jcarousellite.min.js,/js/v3/points.js"></script> 
	<script src="http://pic.lvmama.com/js/common/losc.js"></script> 
	<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/v3/lvmm_pop.js"></script>

	<script type="text/javascript">
	$(document).ready(function () {
	
	var _lvmm_pop1_num=0;
	var _pop_str = "${checkShopProduct}";
	_lvmm_pop1=new Lvmm_pop({
			popW:500,
			popTop:110,
			popWarpClass:"common_pop",//pop外层样式
			popStyleClass:"",//pop风格样式
			popClose:"common_pop_close",//close样式
			pop_tit:"兑换提示：",
			pop_str: _pop_str,
			pop_btn: false,//是否有按钮
			popBtnSure:"common_btn red_common_btn",//确认样式
			popBtnReset:"common_btn grey_common_btn",//取消样式
			//btnevt:btnTest1,//确认触发函数
			popCover:""//遮罩样式
	});

	$("#nowClose").live('click',function(){$("#common_pop_ID"+_lvmm_pop1_num).hide();}) 
	
		var $body = $(document);
        var ht = $body.height();
        var wh = $body.width(); 
        $('#zebi').css({width:wh,height:ht,background:'#000',opacity:'0.3','z-index': '1999',position: 'absolute',top:'0',left:'0'}).show();
		$('#btnSubmit').click(function(){
			<#if shopUser??>
				<#if checkShopProduct!=null && checkShopProduct!="">
				  if(_lvmm_pop1_num!=0){
						$("#common_pop_ID"+_lvmm_pop1_num).show();
					}else{
					   _lvmm_pop1_num=_lvmm_pop1.common_showPop();
					}
				<#else>
					if(confirm("幸运转起来，马上抽奖了。每次抽奖将扣除"+${product.pointChange}+"积分。")){
						$('#myForm').submit();
					}
				</#if>
			<#else>
				//$('.login01').show();
				$(UI).ui("login");
			</#if>	
		})
		
		$('#againlotteryPrompt').click(function(){
			<#if shopUser??>
				<#if checkShopProduct!=null && checkShopProduct!="">
				  if(_lvmm_pop1_num!=0){
						$("#common_pop_ID"+_lvmm_pop1_num).show();
					}else{
					   _lvmm_pop1_num=_lvmm_pop1.common_showPop();
					}
				<#else>
					if(confirm("幸运转起来，马上抽奖了。每次抽奖将扣除"+${product.pointChange}+"积分。")){
						$('#myForm').submit();
					}
				</#if>
			<#else>
				//$('.login01').show();
				$(UI).ui("login");
			</#if>	
		})
		
		$('.close').click(function(){
				$('.login01').hide();
				$('.lotteryPrompt').hide();
				 $('#zebi').hide();
		});
		<#if errorText??>
			alert('${errorText}');
		</#if>			
	
	});
	</script>
	<script>
      cmCreatePageviewTag("奖品详情", "D1001", null, null);
 	</script>
</body>
	<script type="text/javascript">
			$(function(){
				$("#closeAgain").click(function(){
					$('#ShowlotteryPrompt').css('display','none');
					$('#zebi').hide();
				});
			});
</script>

</html>
