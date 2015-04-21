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
    <script type="text/javascript">
    jQuery(function($) {
	    	$("#headerSearch").keydown(function(event) {
	        	$("#headerSearch").unbind("blur");
		        $("#headerSearch").unbind("focus");
		        if (event.keyCode == 13) {
		            var keyword = $("#headerSearch").val();
		            window.location="http://www.lvmama.com/search/purse.do?keyword="+UniformResourceLocator.encode(keyword);
		        }
		    });
		    
		   $("#headerSearch").focus(function() {
		       $(this).val("");
		   });
		   $("#headerSearch").blur(function() {
		       $(this).val("上海");
		  });
		    
		    $("#desComm").click(function() {
		        var keyword = $("#headerSearch").val();
		        window.location="http://www.lvmama.com/search/purse.do?keyword="+UniformResourceLocator.encode(keyword);
		    });
		    
		    $("#ticket>ul[class=pro-list-tagmenu]>li:eq(0)").addClass("current");
	    });
    </script>
</head>
<body>
<div class="lv-nav">
            <h2>特价旅游</h2>
            <span>
            	<a href="/purse/ticket/index.do">预订首页</a> | <a href="/purse/order.do">我的订单</a> | <a href="/purse/help/order.do">预订帮助</a> | <a href="/purse/help/service.do">客服</a></span>
        </div>
        <div class="lvmama-body">

            <div class="left-side w-356">
            	<div class="bgc-div border1">
                <span class="b-span">全国 10000 种打折门票 1 折起订</span>
                <input name="headerSearch" type="text" class="ticket-inp" value="上海" id="headerSearch" /><button type="button"  id="desComm" class="button">搜索</button>
                </div><!搜索>
                <div class="pro-list" id="ticket">
                  <script>
                  jQuery(function($){
					$("#ticket").chajian({pro_tagmenu:".pro-list-tagmenu>li",pro_tagdetail:"ul[name='pro_list']"});
				  });
                  </script>

                    <ul class="pro-list-tagmenu">
                    	<@s.iterator value="ticketDest">
                    		<li><span><@s.property value="title" /></span></li>
                    	</@s.iterator>
                    </ul>
                    <ul class="pro-list" name="pro_list">
	                    <@s.iterator value="ticketTag1">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="ticketTag4">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="ticketTag3">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                         <@s.iterator value="ticketTag2">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul> 
                    <ul class="pro-list display-none" name="pro_list">
                        <@s.iterator value="ticketTag5">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul>
                    <ul class="pro-list display-none" name="pro_list">
                         <@s.iterator value="ticketTag6">
	                        <li><p class="s2-price"><del>￥<@s.property value="marketPrice" /></del><strong>￥<@s.property value="memberPrice" /></strong></p><a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url" />"><@s.property value="title" /></a></li>
	                    </@s.iterator>
                    </ul>           
                  </div>
                  <span class="lv-tel">驴妈妈客服：<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></span>
            </div>
            
            
            <div class="right-side w-172">
            	 <@s.iterator value="picProduct">
	                    	<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" /><@s.property value="url"/>"> <img src="<@s.property value="imgUrl"/>" width="172" height="120" class="col-pro-pic" /></a>
	             </@s.iterator>
            </div>
        </div>
</body>
</html>

