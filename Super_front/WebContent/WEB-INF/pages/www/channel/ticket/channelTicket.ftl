<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/ticket/">
<#include "/WEB-INF/pages/www/channel/common/seo.ftl">
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css,/styles/v3/channel.css" > 

<#include "/common/coremetricsHead.ftl">
</head>
<body class="ticket">
<@s.set var="pageMark" value="'ticket'" />
<!--头文件-->
<#include "/common/header.ftl">

<!-- 搜索框区域\\ -->
<#include "/WEB-INF/pages/www/channel/ticket/ticket_searchWindow.ftl">

<!-- wrap\\ 1 -->
<div class="wrap wrap-quick">

<!-- 左上 quick-menu -->
<#include "/WEB-INF/pages/www/channel/ticket/ticket_quickMenu.ftl">

<!--科捷广告焦点图-->
<#include "/WEB-INF/pages/www/channel/ticket/ticket_keJiePicture.ftl">

	<div class="hr_d"></div>
</div> <!-- //.wrap 1 -->

<!-- wrap\\ 2 -->
<div class="wrap">
   <!--主内容左侧-->
    <#include "/WEB-INF/pages/www/channel/ticket/ticket_page_left.ftl">
    <!--主内容右侧-->
    <#include "/WEB-INF/pages/www/channel/ticket/ticket_page_right.ftl">
</div> <!-- //.wrap 2 -->

<!--科捷广告-->
<div class="padbox wrap" data-type="ad"  data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|ticket_2013|ticket_2013_banner01#1000px#80px">
</div>
<div class="wrap"><a href="http://www.lvmama.com/public/user_security#mp" target="_blank"><img src="http://pic.lvmama.com/img/v5/lybz_banner.gif" width="1000" height="50"></a></div> 

<!-- 页面底部-->
<#include "/common/footer.ftl">
  <!--友情链接 -->  
<#include "/WEB-INF/pages/staticHtml/friend_link/ticket_footer.ftl">


<!--景点门票客户端提示-->

<div class="client_bg">
    <div class="client_box">
        <div class="client_big"> 
            <img class="client_sj" src="http://pic.lvmama.com/img/v3/shouji.png" width="196" height="264" alt="">
            <dl class="client_text">
                <dt>手机预订 更<span class="orange">优惠</span></dt>
                <dd>•客户端预订返现金额是<span class="orange">网站2倍</span></dd>
                <dd>•部分景点门票可在客户端<span class="orange">当天</span>预订</dd>
                <dd>•客户端登录即送<span class="orange">180元</span>优惠券</dd>
                <dd><a class="btn_gd" href="http://shouji.lvmama.com?ch=menpiao" target="_blank"></a></dd>
            </dl>
            <div class="down_box">
                <h4>下载驴妈妈手机客户端</h4>
                <!--<p>免费发送下载地址到手机</p>
                <div class="fs_box"><input type="text" placeholder="请输入手机号码" id="message"><span class="btn_fs"></span></div>-->
                <a class="down_i" href="http://itunes.apple.com/cn/app/id443926246?mt=8" target="_blank"></a>
                <a class="down_a" href="http://m.lvmama.com/rewrite/d.php" target="_self"></a>
				<a class="down_p" href="http://itunes.apple.com/cn/app/id715334091?mt=8" target="_blank"></a>
            </div>
            <div class="down_erweima">
                <p>扫描二维码下载客户端</p>
                <span></span>
            </div>
            <span class="client_close"></span>
        </div>
        <div class="client_small">
            <p>手机预订 更<span class="orange">优惠</span></p>
            <span class="client_show"><i></i></span>
            <span class="client_close"></span>
        </div>
    </div>
</div>


<!--  js-->
<!-- 公用js--> 
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|ticket_2013|ticket_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
<!-- 频道公用js--> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/plugins.js,/js/v3/channel.js"></script> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/app.js"></script>
 <!-- <script src="/js/home/app.js"></script>-->

<!-- 流量统计--> 
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript"></script> 
<script src="/js/channel/channel.js" type="text/javascript"></script> 
<script type="text/javascript">
    <!-- tab按钮获取推荐景点信息-->
	$(function(){
	    var fromPlaceId='${fromPlaceId?if_exists}';
	    var fromPlaceName='${fromPlaceName?if_exists}';
 	    var url="http://www.lvmama.com//homePage/ajaxGetRecommendScenic.do";
 		getAjaxRecommendScenic(url,fromPlaceId,fromPlaceName);
	});
 </script>
 
 <script>
      cmCreatePageviewTag("景点门票频道首页", "A0001", null, null);
</script>
</body>
</html>
