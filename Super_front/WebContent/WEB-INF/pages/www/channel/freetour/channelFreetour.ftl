<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/channel/around/">
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css,/styles/v3/channel.css" >
<#include "/WEB-INF/pages/www/channel/common/seo.ftl">

<#include "/common/coremetricsHead.ftl">
</head>
<body class="freetour">
<@s.set var="pageMark" value="'freetour'" />
<!--头文件-->
<#include "/common/header.ftl">

<!-- 搜索框区域\\ -->
<#include "/WEB-INF/pages/www/channel/freetour/freetour_searchWindow.ftl">
<!-- //搜索框区域 -->

<!-- 静态导航位\\  -->
<div class="wrap" style="margin-bottom:15px;">
<#include "/WEB-INF/pages/www/channel/freetour/freetour_quickMenu.ftl">
<!-- //静态导航位 -->

<!-- kejet广告位 \\-->
<#include "/WEB-INF/pages/www/channel/freetour/freetour_keJetPicture.ftl">
<!-- //kejet广告位  -->
</div>

<div class="wrap">
<!-- 周边游左侧导航\\ -->
<#include "/WEB-INF/pages/www/channel/freetour/freetour_page_left.ftl">
<!-- //周边游左侧导航 -->

<!-- 周边游右侧导航\\ -->
<#include "/WEB-INF/pages/www/channel/freetour/freetour_page_right.ftl">
<!-- //周边游右侧导航 -->
</div>


<div class="padbox wrap" data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|around_2013|around_2013_banner01#1000px#80px" >
</div> <!-- //.广告 -->

<!-- 页面底部\\-->
<#include "/common/footer.ftl">

<div class="hh_cooperate"><!-- 友情链接 start-->
	<#include "/WEB-INF/pages/staticHtml/friend_link/freetour_footer.ftl">
	<!-- footer end-->
</div><!-- E bottom -->
<!-- //页面底部 -->

<!-- 公用js-->
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|around_2013|around_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
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
	    var url="http://www.lvmama.com//homePage/ajaxGetRecommendScenicForFreetour.do";
 		var fromPlaceId='${fromPlaceId?if_exists}';
	    var fromPlaceName='${fromPlaceName?if_exists}';
  		getAjaxRecommendScenic(url,fromPlaceId,fromPlaceName);
	});
     var useId='${userId}';
     if(useId==''){
         useId=0;
     }
     var fplaceName='${fromPlaceName?if_exists}';
 </script>
<!--  猜你喜欢-->
 <!-- 智子云 -->
 <script type="text/javascript" src="http://static.zhiziyun.com/js/rec_model_main.js"></script>
 <script type="text/javascript">
     zz_rec.obj.detail={  
            "channel_name" : "周边游",   //频道名称
            "from_city" : fplaceName, //出发城市
            "user_id" : useId, //网站当前用户id，如果未登录就为0或空字符串
            "page_type" : "ROUTE_GROUP", //当前页面全称，请勿修改
            "rec_item_count" : 3, //推荐数量
            "callbackname":"zz_rec.obj.callback"//本次模板回调函数名称，请勿修改
        };
</script>
 <!--百分点代码：周边游频道-->
<script type="text/javascript" src="http://static1.baifendian.com/service/lvmama/lvmama.js"></script>
<script type="text/javascript">
     window["_BFD"] = window["_BFD"] || {};
    _BFD.BFD_INFO = {
        "channel_name" : "周边游",   //频道名称
        "from_city" : fplaceName, //出发城市
        "user_id" : useId, //网站当前用户id，如果未登录就为0或空字符串
        "num" : "3", //推荐栏展示个数
        "page_type" : "ROUTE_GROUP" //当前页面全称，请勿修改
    };
</script>
<!--AB测试-->
<script type="text/javascript">
    $(function(){
        var flag='${flagForAB}';
        if(flag=='true'){//智子云
                 zz_rec.obj.callback=function (data){
                         var guesslike='';
                       for (var i=0,len=data.length; i<len; i++)
                        {
                          guesslike=guesslike+'<li> <a href="http://www.lvmama.com/product/'+data[i].iid+'?losc=031487" target="_blank"><img src="'
                             +data[i].img +'"></a>  <a target="_blank" href="'+data[i].url +'?losc=031487">'
                             +unescape(data[i].name)+'</a><a class="tagsback"  ><em>热推</em></a>   <p>驴妈妈价：<dfn>¥<i>'
                             +data[i].price+'</i>起</dfn></p></li> ';
                           }
                        $("#baifendian_guesslike").append(guesslike);
               };
               zz_rec.show();
         }else{
              _BFD.show_routeGroup = function(data){
                   var guesslike='';
                   for (var i=0,len=data.length; i<len; i++)
                    {
                      guesslike=guesslike+'<li> <a href="http://www.lvmama.com/product/'+data[i].iid+'?losc=031486" target="_blank"><img src="'
                             +data[i].img +'"></a>  <a target="_blank" href="'+data[i].url +'?losc=031486">'
                             +unescape(data[i].name)+'</a><a class="tagsback"  ><em>热推</em></a>   <p>驴妈妈价：<dfn>¥<i>'
                             +data[i].price+'</i>起</dfn></p></li> ';
                       }
                    $("#baifendian_guesslike").append(guesslike);
                   
                    _BFD.showBind(data,"ROUTE_GROUP");//此行代码请不要修改或删除。
              }
         }
     });
</script>

 <script>
      cmCreatePageviewTag("周边游频道首页", "A0001", null, null);
 </script>
</body>
</html>
