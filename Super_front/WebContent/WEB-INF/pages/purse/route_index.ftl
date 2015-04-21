<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="version" content="4.0" />
    <title>财付通-我的钱包</title>
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js?r=2913"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/dest_tips.js?r=2913"></script>
    
</head>
<body>
<div class="lv-nav">
            <h2>在线预订</h2>
            <span>
           		 <a href="/purse/route/index.do">预订首页</a> 
            | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>
    <script type="text/javascript">
    <!--
	jQuery(function($){
					var tab_menu = $("*[name='tab_menu']>li");
					var tab_content = "*[name='tab_content']";
					tab_menu.mouseover(function(){
						var index_num = tab_menu.index(this);
						$(this).addClass("s-current").siblings().removeClass("s-current");
						$(tab_content).eq(index_num).css("display","block").siblings(tab_content).css("display","none");
						})
						
						$("#route>ul[class=pro-list-tagmenu]>li:eq(0)").addClass("current");
					})
					
	
		function submitSearch(ticket){
		 		var toDest = $("#toDest"+ticket).val();
		 		var fromDest = $("#fromDest"+ticket).val();
		 		var isticket = $("#ticket"+ticket).val();
		        window.location="http://www.lvmama.com/search/purse!purseDestSearch.do?fromDest="+UniformResourceLocator.encode(fromDest)+"&toDest="+UniformResourceLocator.encode(toDest)+"&isTicket="+isticket;
		}
	//-->
    </script>

    
        <div class="lvmama-body">
            <div class="left-side w-172 visible">
            	<div class="dest-search">
                	<ul class="s-tab" name="tab_menu"><li class="s-current">跟团游</li><li>自由行</li></ul>
                    <ul name="tab_content">
                    	<li id="fromDest" class="fromPlace"  onmouseover="destInfo('fromDest','fromPlace_keyword','fromDest3','fromDest')">
                    		<label>出发地：</label><input name="fromDest3" id="fromDest3" type="text" size="12" readonly="readonly" />
                    		<ul id="fromPlace_keyword" class="fromPlace_keyword" name="fromPlace_Name" style="display: none;"> 
					          	</ul>
                    	</li>
                    	<li id="toDest" class="toPlace" onmouseover="destInfo('toDest','toPlace_keyword','toDest3','toDest')">
                    	<label>目的地：</label><input name="toDest3" id="toDest3" type="text" size="12" readonly="readonly" />
                    		<ul id="toPlace_keyword" class="fromPlace_keyword" name="fromPlace_Name" style="display: none;"> 
					        </ul>
                    	</li>
                        <li><input name="ticket3" id="ticket3" type="hidden" value="4" />
                        <button type="button" onclick="submitSearch(3);" class="button btn-mg-l">搜索</button></li>
                    </ul>
                    <ul class="display-none" name="tab_content">
                    	<li id="toDestLi" class="toPlace" onmouseover="destInfo('toDestLi','toPlace_keyword4','toDest4','Dest')">
                    	<label>目的地：</label><input name="fromDest4" id="fromDest4" type="hidden" /><input id="toDest4" type="text" size="12" readonly="readonly" />
		                    	<ul id="toPlace_keyword4" class="fromPlace_keyword" name="fromPlace_Name" style="display: none;"> 
					          	</ul>
                    	</li>
                        <li><input name="ticket4" id="ticket4" type="hidden" value="3" />
                        <button type="button"  onclick="submitSearch(4);"  class="button btn-mg-l">搜索</button></li>
                    </ul>

                </div>
            	 <@s.iterator value="picProduct">
	                    	<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url"/>"> <img src="<@s.property value="imgUrl"/>" width="172" height="120" alt="${title}" class="col-pro-pic" /></a>
	             </@s.iterator>
                <span class="lv-tel">客服热线：<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></span>
            </div>
            
            
            <div class="right-side w-356">
            	<div class="c-c06 mg-b10">热门线路推荐：</div>
                <div class="pro-list" id="route">
                	<script>
	                  jQuery(function($){
						$("#route").chajian({pro_tagmenu:".pro-list-tagmenu>li",pro_tagdetail:"ul[name='pro_list']"});
					  });
	                  </script>
                	<ul class="pro-list-tagmenu">
		            	<@s.iterator value="routeDest">
		            		<li><span><@s.property value="title" /></span></li>
		            	</@s.iterator>
                	</ul>
                	<ul class="pro-list" name="pro_list">
	                    <@s.iterator value="routeTag1">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="routeTag2">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="routeTag3">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                         <@s.iterator value="routeTag4">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul> 
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="routeTag5">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                         <@s.iterator value="routeTag6">
	                        <li><p class="s2-price"><del>￥${marketPrice?if_exists?replace(".0","")}</del><strong>￥${memberPrice?if_exists?replace(".0","")}</strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />" title="${title}"><#if title?length lt 16 >${title}<#else>${title?string[0..15]}...</#if></a></li>
	                    </@s.iterator>
                    </ul>           
                </div>
                  
            </div>
        </div>
        
       
</body>
</html>

