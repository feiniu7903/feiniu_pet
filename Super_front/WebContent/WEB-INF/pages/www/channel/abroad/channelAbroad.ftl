<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/channel/abroad/">
<#include "/WEB-INF/pages/www/channel/common/seo.ftl">

<#include "/common/coremetricsHead.ftl">
</head>

<body class="abroad">
<@s.set var="pageMark" value="'abroad'" />
<!--头文件-->
<#include "/common/header.ftl"> 


<!-- 搜索框区域\\ -->
<#include "/WEB-INF/pages/www/channel/abroad/abroad_searchWindow.ftl">

<!-- wrap\\ 1 -->
<div class="wrap" style="margin-bottom:15px;">

<!-- 左上 quick-menu -->
<#include "/WEB-INF/pages/www/channel/abroad/abroad_quickMenu.ftl">

<!--科捷广告焦点图-->
<#include "/WEB-INF/pages/www/channel/abroad/abroad_keJiePicture.ftl">
	
</div> <!-- //.wrap 1 -->

<!-- wrap\\ 2 -->
<div class="wrap">
   <!--主内容左侧-->
    <#include "/WEB-INF/pages/www/channel/abroad/abroad_page_left.ftl">
    <!--主内容右侧-->
    <#include "/WEB-INF/pages/www/channel/abroad/abroad_page_right.ftl">
</div> <!-- //.wrap 2 -->

<!--科捷广告-->
<div class="padbox wrap" data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|abroad_2013|abroad_2013_banner01#1000px#80px">
</div> <!-- //.广告 -->

<!-- 页面底部-->
<#include "/common/footer.ftl">
 
 <!-- 友情链接-->
 <div class="hh_cooperate">
	<!-- footer start-->
	<#include "/WEB-INF/pages/staticHtml/friend_link/abroad_footer.ftl">
	<!-- footer end-->	
 </div>
 
 <div class="wenquan"><a href="http://www.wenjuan.com/s/AFfiMba" target="_blank"></a></div>
 
<!-- 公用js-->
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|abroad_2013|abroad_2013_flag03&db=lvmamim&border=0&local=yes#300px#60px");
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
<!-- 频道公用js--> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/plugins.js,/js/v3/channel.js"></script>     
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/app.js"></script>
 <!--<script src="/js/home/app.js"></script>-->

<!-- 流量统计--> 
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript"></script> 
<script src="/js/channel/channel.js" type="text/javascript"></script> 
<script type="text/javascript">
    <!-- tab按钮获取推荐景点信息-->
	$(function(){
 	    var url="http://www.lvmama.com//homePage/ajaxGetRecommendScenicForAbroad.do";
 		var fromPlaceId='${fromPlaceId?if_exists}';
	    var fromPlaceName='${fromPlaceName?if_exists}';
  		getAjaxRecommendScenic(url,fromPlaceId,fromPlaceName);
	});
 </script>
 
<script>
      cmCreatePageviewTag("出境游频道首页", "A0001", null, null);
 </script>
</body>
</html>
