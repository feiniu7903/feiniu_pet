<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跟团游_驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" type="text/css" rel="stylesheet" />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" type="text/css" rel="stylesheet"/>
<link href="http://pic.lvmama.com/styles/v7style/routeV1_1.css" type="text/css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/jquery.suggest.css" type="text/css" rel="stylesheet"  />
<script src="http://pic.lvmama.com/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/topMsg_comment.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js"></script>
</head>

<body>
<#include "/common/setKeywor3.ftl">
<div class="mainContainer">
	<!--左侧 开始-->
	<div class="leftContainer">
    	<div class="route-search">
       	<h3>跟团游搜索</h3>
              <script>
              jQuery(function($){
                              var $fromPlace = $("#fromPlace");
                              var $fromPlace_keyword = $("#fromPlace_keyword");
                              var $toPlace = $("#toPlace");
                              var $toPlace_keyword = $("#toPlace_keyword");
                              $fromPlace.mouseover(function(){$fromPlace_keyword.show();})
                                        .mouseout(function(){$fromPlace_keyword.hide();})
                              $toPlace.mouseover(function(){$toPlace_keyword.show();})
                                        .mouseout(function(){$toPlace_keyword.hide();})
                              })
                function getAddress(ads){
            document.getElementById("dd").value = ads;
            document.getElementById("fromPlace_keyword").style.display = "none";
    
        }
                function toAddress(toads){
            document.getElementById("gg").value = toads;
            document.getElementById("toPlace_keyword").style.display = "none";
                }
              </script>
         <form id="searchDest" onsubmit="return true;" action="http://www.lvmama.com/product/product_search.do" method="post">
         <p class="srcbox2tit">近千条旅游度假线路供您选择：</p>
          <div id="fromPlace" class="srclp">
          <label>出发地：</label><input type="text" id="dd" class="srclinput" name="fromDest">
           <ul id="fromPlace_keyword" name="fromPlace_Name" style="display: none;">
                <li><a href="javascript:getAddress('上海')">上海</a></li>
                <li><a href="javascript:getAddress('杭州')">杭州</a></li>
                <li><a href="javascript:getAddress('南京')">南京</a></li>
          </ul>
         </div>
         <div id="toPlace">
         <label>目的地：</label><input type="text" id="gg" class="srclinput" name="toDest">
         <ul id="toPlace_keyword" name="toPlace_Name" style="display: none;">
                <li><a href="javascript:toAddress('上海')">上海</a></li>
                <li><a href="javascript:toAddress('杭州')">杭州</a></li>
                <li><a href="javascript:toAddress('南京')">南京</a></li>
                <li><a href="javascript:toAddress('苏州')">苏州</a></li>
                <li><a href="javascript:toAddress('常州')">常州</a></li>
                <li><a href="javascript:toAddress('无锡')">无锡</a></li>
                <li><a href="javascript:toAddress('三亚')">三亚</a></li>
                <li><a href="javascript:toAddress('丽江')">丽江</a></li>
                <li><a href="javascript:toAddress('厦门')">厦门</a></li>
                <li><a href="javascript:toAddress('青岛')">青岛</a></li>
                <li><a href="javascript:toAddress('桂林')">桂林</a></li>
                <li><a href="javascript:toAddress('西安')">西安</a></li>
                <li><a href="javascript:toAddress('成都')">成都</a></li>
                <li><a href="javascript:toAddress('北京')">北京</a></li>
                <li><a href="javascript:toAddress('贵阳')">贵阳</a></li>
                <li><a href="javascript:toAddress('西宁')">西宁</a></li>
                <li><a href="javascript:toAddress('张家界')">张家界</a></li>
                <li><a href="javascript:toAddress('九寨沟')">九寨沟</a></li>
         </ul>
         </div>
         <p class="srclibtn"><input type="image" src="http://pic.lvmama.com/img/index1008/serbtn.gif"></p>
         </form> 
        <p class="srclastp"><a href="javascript:openwindow('http://www.lvmama.com/destRoute/destrote!toRfq.do','rft',900,800)">团体客户电子询价&nbsp;>></a></p>
      </div>
      
      <ul class="left-tips">
      	<li><a href="http://www.lvmama.com/public/lines_agreement" target="_blank">国内跟团游预订须知</a></li>
        <li><a href="http://www.lvmama.com/public/about_rule" target="_blank">出镜旅游预订须知</a></li>
        <li><a href="http://www.lvmama.com/public/passport" target="_blank">如何办理签证？</a></li>
        <!--<li><a href="#">旅游保险问题解答</a></li>-->
      </ul>
      
      <div class="left-box hot-commend"> <h3>热门推荐</h3>
          <ul>
                <li><a href="http://www.lvmama.com/route/20523"><img src="http://pic.lvmama.com/recommand/gentuanyou_expo.jpg"></a><span><a href="http://www.lvmama.com/route/20523">上海世博</a></span></li>
                <li><a href="http://www.lvmama.com/lp/putuoshan"><img src="http://pic.lvmama.com/recommand/gentuanyou_putuoshan.jpg"></a><span><a href="http://www.lvmama.com/lp/putuoshan">普陀佛国</a></span></li>
                <li><a href="http://www.lvmama.com/route/20409"><img src="http://pic.lvmama.com/recommand/hengdian8060.jpg"></a><span><a href="http://www.lvmama.com/route/20409">清凉横店</a></span></li>
                <li><a href="http://www.lvmama.com/route/20002"><img src="http://pic.lvmama.com/recommand/wuzhen8060.jpg"></a><span><a href="http://www.lvmama.com/route/20002">梦回乌镇</a></span></li>
          </ul>
      </div>
      
      
      <div class="left-box">
      	<h3>长三角热门目的地</h3>
        <div class="place-link">
        <h4>浙江</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%AE%89%E5%90%89">安吉</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%BB%99%E5%B1%85">仙居</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E9%9B%81%E8%8D%A1%E5%B1%B1">雁荡山</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%AD%A6%E4%B9%89">武义</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E7%BB%8D%E5%85%B4">绍兴</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%AE%81%E6%B3%A2">宁波</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E8%B1%A1%E5%B1%B1">象山</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%B8%B4%E5%AE%89">临安</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%9D%AD%E5%B7%9E">杭州</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%B9%8C%E9%95%87">乌镇</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%8D%83%E5%B2%9B%E6%B9%96">千岛湖</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%A1%90%E5%BA%90">桐庐</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%99%AE%E9%99%80%E5%B1%B1">普陀山</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%B5%8A%E6%B3%97">嵊泗</a>
        
        <h4>江苏</h4>
        <a href="http://www.lvmama.com/dest/changzhoukonglongyuan">常州恐龙园</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%97%A0%E9%94%A1">无锡</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E8%8B%8F%E5%B7%9E">苏州</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E8%BF%9E%E4%BA%91%E6%B8%AF">连云港</a><a href="http://www.lvmama.com/dest/tianmuhushanshuiyuan">天目湖</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%8D%97%E4%BA%AC">南京</a><a href="http://www.lvmama.com/dest/jiangsu_zhenjiang">镇江</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%89%AC%E5%B7%9E">扬州</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%91%A8%E5%BA%84">周庄</a><a href="http://www.lvmama.com/dest/shajiabang">沙家浜</a>
        
        <h4>其他</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E9%BB%84%E5%B1%B1">黄山</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%B9%9D%E5%8D%8E%E5%B1%B1">九华山</a><a href="http://www.lvmama.com/dest/sanqingshanfengjingqu">三清山</a><a href="http://www.lvmama.com/dest/dongpinggongyuan">崇明</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%BA%90%E5%B1%B1">庐山</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%BA%95%E5%86%88%E5%B1%B1">井冈山</a>
        </div>
      </div>
      
      <div class="left-box">
      	<h3>国内长线旅游目的地</h3>
        <div class="place-link">        
        <h4>海南</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%B8%89%E4%BA%9A">三亚</a><a href="http://www.lvmama.com/dest/wuzhizhou">蜈支洲岛</a><a href="http://www.lvmama.com/dest/tianyahaijiao">天涯海角</a><a href="http://www.lvmama.com/dest/nanshanwenhua">南山文化苑</a>
        　　　 
        <h4>云南</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%98%86%E6%98%8E">昆明</a><a href="http://www.lvmama.com/dest/yunnan_dalibaizu">大理</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E4%B8%BD%E6%B1%9F">丽江</a><a href="http://www.lvmama.com/dest/yunnan_lijiang">香格里拉</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E8%A5%BF%E5%8F%8C%E7%89%88%E7%BA%B3">西双版纳</a>
        
        <h4>山东</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E9%9D%92%E5%B2%9B">青岛</a><a href="http://www.lvmama.com/dest/shandong_taian">泰山</a><a href="http://www.lvmama.com/dest/shandong_jiningshiqufushi">曲阜</a>
        
        <h4>广西</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%A1%82%E6%9E%97">桂林</a><a href="http://www.lvmama.com/dest/guangxi_yangshuohttp://www.lvmama.com/dest/guangxi_yangshuo">阳朔</a>
        
        <h4>福建</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%8E%A6%E9%97%A8">厦门</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%AD%A6%E5%A4%B7%E5%B1%B1">武夷山</a><a href="http://www.lvmama.com/dest/fujian_xiamen">鼓浪屿</a><a href="http://www.lvmama.com/dest/zhongguo_fujian">土楼</a>
        　　 
        <h4>四川</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E6%88%90%E9%83%BD">成都</a><a href="http://www.lvmama.com/dest/zhongguo_sichuan">九寨沟</a>
        　　　 
        <h4>贵州</h4>
        <a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E8%B4%B5%E9%98%B3">贵阳</a><a href="http://www.lvmama.com/dest/guizhou_anshun">安顺</a>
        
        <h4>湖南</h4>
        <a href="http://www.lvmama.com/dest/hunan_changshahttp://www.lvmama.com/dest/hunan_changsha">长沙</a><a href="http://www.lvmama.com/product/product_search.do?fromDest=&toDest=%E5%BC%A0%E5%AE%B6%E7%95%8C">张家界</a><a href="http://www.lvmama.com/dest/fenghuanggucheng">凤凰古城</a>
        
        <h4>北京</h4>
        <a href="http://http://www.lvmama.com/dest/gugong">故宫</a><a href="http://www.lvmama.com/dest/badaling">八达岭长城</a><a href="http://www.lvmama.com/dest/niaochao">鸟巢</a><a href="http://www.lvmama.com/dest/yiheyuan">颐和园</a>
        
        <h4>西安</h4>
        <a href="http://www.lvmama.com/dest/shannxi_xian">华山</a><a href="http://www.lvmama.com/dest/bingmayong">兵马俑</a><a href="http://www.lvmama.com/dest/shannxi_xian">大雁塔</a><a href="http://www.lvmama.com/dest/famensi">法门寺</a><a href="http://www.lvmama.com/dest/qianling">乾陵</a>
        
        <h4>宁夏</h4>
        <a href="http://www.lvmama.com/dest/zhongweishapotou">沙坡头</a><a href="http://www.lvmama.com/dest/shahu">沙湖</a><a href="http://www.lvmama.com/dest/helanshan1">贺兰山</a><a href="http://www.lvmama.com/dest/xixiawangling">西夏王陵</a><a href="http://www.lvmama.com/dest/zhenbeibao">镇北堡华夏西部影视城</a>
        
        <h4>新疆</h4>
        <a href="http://www.lvmama.com/dest/tianshantianchi">天山天池</a><a href="http://www.lvmama.com/dest/xinjiang_aletai">喀纳斯</a><a href="http://www.lvmama.com/dest/guanyuting">观鱼亭</a><a href="http://www.lvmama.com/dest/xinjiang_tulufan">吐鲁番盆地</a>
        
        <h4>大连</h4>
        <a href="http://www.lvmama.com/dest/liaoning_dalian">旅顺</a><a href="http://www.lvmama.com/dest/jinshitan">金石滩</a><a href="http://www.lvmama.com/dest/xinghaiguangchang">星海广场</a>
        </div>
      </div>
      
      
      <!--<div class="left-box">
      	<h3>出境游目的地</h3>
        <div class="place-link">　 
        <h4>亚洲</h4>
        <a href="#">日本</a><a href="#">韩国</a><a href="#">柬埔寨</a><a href="#">泰国</a><a href="#">马来西亚</a>
        
        </div>
      </div>-->
      
      <!--摆放两个广告位-->
    </div>
    <!--左侧 结束-->
    
    <!--右侧 开始-->
    <div class="rightContainer">
    	<div class="slide-service">
        	<div class="r_slider">
 	<script type="text/javascript">
		//<![CDATA[
		//焦点图
		$(function(){
			var allNum = 0;
			var i, j;
			var indexNum = $(".Slides li:last-child").index();
			$(".Slides li:first-child").fadeIn(1000); 
			$(".SlideTriggers").remove(); 
	
			$(".Slides").after("<ul class='SlideTriggers'><li class='Current'>1</li></ul>");
			for (i = 1; i <= indexNum; i++)
			{
				j = i+1;
				$(".SlideTriggers").append("<li>"+j+"</li>")
			}
	
			$(".SlideTriggers li").mouseover(function(){
				var num = $(this).index();
				allNum = num;
				$(this).addClass("Current").siblings().removeClass("Current");
				$(".Slides li").eq(num).fadeIn(800).siblings().hide();
			});
	
			function imgScroll(){
				$(".SlideTriggers li").eq(allNum).addClass("Current").siblings().removeClass("Current");
				$(".Slides li").eq(allNum).fadeIn(1000).siblings().hide();
				allNum += 1;
				if(allNum>indexNum) allNum=0;
			}
	
			setInterval(imgScroll,3000);
		});
		//]]>
	</script>
            <div id="DestpicFlow">
                <ul class="Slides">
                  <li style="display: block; position: absolute; top: 0pt; left: 0pt; z-index: 1; opacity: 1;"><a target="_blank" href="http://www.lvmama.com/route/20160"><img src="http://pic.lvmama.com/img/dest/zhangjiajie_460x200.jpg"></a></li>
                  <li style="display: none; position: absolute; top: 0pt; left: 0pt; z-index: 1; opacity: 1;"><a target="_blank" href="http://www.lvmama.com/route/20002"><img src="http://pic.lvmama.com/img/dest/wuzhen_460x200.jpg"></a></li>
                  <li style="display: none; position: absolute; top: 0pt; left: 0pt; z-index: 1; opacity: 1;"><a target="_blank" href="http://www.lvmama.com/lp/putuoshan/"><img src="http://pic.lvmama.com/img/dest/putuoshan_460x200.jpg"></a></li>
                  <li style="display: none; position: absolute; top: 0pt; left: 0pt; z-index: 1; opacity: 1;"><a target="_blank" href="http://www.lvmama.com/route/20266"><img src="http://pic.lvmama.com/img/dest/konglongyuan_460x200.jpg"></a></li>
                  <li style="display: none; position: absolute; top: 0pt; left: 0pt; z-index: 1; opacity: 1;"><a target="_blank" href="http://www.lvmama.com/route/20196"><img src="http://pic.lvmama.com/img/dest/yunnan_460x200.jpg"></a></li>
                </ul>
            </div>
            </div>
    	</div>
            
            <script>
              jQuery(function($){
					var $menu = $(".route-list>ul>li");
					var $content_all = "div[class='route-pro']";
					var $content = $(".route-list").siblings("div[class='route-pro']");
					$content.not(":first").css("display","none");
					$menu.click(function(){
						var $num = $menu.index(this);
						$(this).addClass("current").siblings("li").removeClass("current");
						$content.eq($num).show().siblings($content_all).hide();
					})
			})
			</script>
       <div class="route-list">
       	<h3>出发城市</h3><!--<a href="#" class="more">更多出发地</a>-->
       	<ul><li class="current">上海</li><li>杭州</li><li>南京</li></ul>
       </div>
       <div id="sh" class="route-pro">
        	<h4>上海当季推荐热门线路</h4>
            <ul>
            		<li><strong>￥178</strong><del>￥299</del>
            		<a target="_blank" title="【中秋特推】临安大明山、西径飞瀑、浙西大龙湾放飞心情2日游" href="http://www.lvmama.com/route/20756">【中秋特推】临安大明山、西径飞瀑、浙西大龙湾放飞心情2日游<span>&nbsp;只此一班不容错过！</span></a>
            		</li>
            		<li><strong>￥179</strong><del>￥299</del>
            		<a target="_blank" title="【中秋特推】仙居景星岩、高迁古村、黄岩大瀑布沉醉2日游" href="http://www.lvmama.com/route/20744">【中秋特推】仙居景星岩、高迁古村、黄岩大瀑布沉醉2日游<span>&nbsp;行程安排合理适合朋友家人！</span></a>
            		</li>
            		<li><strong>￥1998</strong><del>￥2260</del>
            		<a target="_blank" title="【国庆特推】青岛/崂山海滨休闲双飞3日游" href="http://www.lvmama.com/route/20876">【国庆特推】青岛/崂山海滨休闲双飞3日游<span>&nbsp;赠送青岛奥帆中心“蓝色畅想”大型演出门票！</span></a>
            		</li>
            		<li><strong>￥1460</strong><del>￥1690</del>
            		<a target="_blank" title="【经典热卖】“彩云之滇”昆明大理丽江双飞6日游（爱心大放送）" href="http://www.lvmama.com/route/20196">【经典热卖】“彩云之滇”昆明大理丽江双飞6日游（爱心大放送）<span>&nbsp;赠送崇明明珠湖门票</span></a>
            		</li>
            		<li><strong>￥1460</strong><del>￥1690</del>
            		<a target="_blank" title="【经典热卖】“尽情海南”双飞5日超享版" href="http://www.lvmama.com/route/20102">【经典热卖】“尽情海南”双飞5日超享版<span>&nbsp;精品三亚观光游，送小鱼温泉</span></a>
            		</li>
            </ul>
        	<h4>上海周边短途线路</h4>
            <ul>
            		<li><strong>￥258</strong><del>￥288</del>
            		<a target="_blank" title="【国庆特推】神仙居、天烛仙境 高迁古村经典2日游" href="http://www.lvmama.com/route/20936">【国庆特推】神仙居、天烛仙境 高迁古村经典2日游<span>&nbsp;全程景点无自理，探访仙人居住地</span></a>
            		</li>
            		<li><strong>￥308</strong><del>￥358</del>
            		<a target="_blank" title="【国庆特推】千岛湖中心湖区、瑶琳仙境、天目溪漂流、红石湾经典2日游" href="http://www.lvmama.com/route/20885">【国庆特推】千岛湖中心湖区、瑶琳仙境、天目溪漂流、红石湾经典2日游<span>&nbsp;国庆天天开班</span></a>
            		</li>
            		<li><strong>￥408</strong><del>￥468</del>
            		<a target="_blank" title="【国庆特推】千岛湖中心湖区、白云源、龙门古镇经典3日游" href="http://www.lvmama.com/route/20915">【国庆特推】千岛湖中心湖区、白云源、龙门古镇经典3日游<span>&nbsp;专业打造，绝对超值</span></a>
            		</li>
            		<li><strong>￥268</strong><del>￥298</del>
            		<a target="_blank" title="【国庆特推】安吉龙王山、竹博园、中南百草园2日游" href="http://www.lvmama.com/route/20895">【国庆特推】安吉龙王山、竹博园、中南百草园2日游<span>&nbsp;全程无自理景点，天天开班！</span></a>
            		</li>
            		<li><strong>￥368</strong><del>￥398</del>
            		<a target="_blank" title="【国庆特推】淳安千岛湖中心湖、临安大明山经典山水组合2日" href="http://www.lvmama.com/route/20828">【国庆特推】淳安千岛湖中心湖、临安大明山经典山水组合2日<span>&nbsp;经典线路，火热上线！</span></a>
            		</li>
            		<li><strong>￥248</strong><del>￥278</del>
            		<a target="_blank" title="【国庆特推】宁国原生态休闲度假2日游" href="http://www.lvmama.com/route/20893">【国庆特推】宁国原生态休闲度假2日游<span>&nbsp;清爽秋天，感受原味大自然！</span></a>
            		</li>
            		<li><strong>￥378</strong><del>￥428</del>
            		<a target="_blank" title="【国庆特推】雁荡山灵岩、灵峰日景龙穿峡2日游（阳光价）" href="http://www.lvmama.com/route/20816">【国庆特推】雁荡山灵岩、灵峰日景龙穿峡2日游（阳光价）<span>&nbsp;神奇山水地貌，体验销魂夜景</span></a>
            		</li>
            		<li><strong>￥278</strong><del>￥328</del>
            		<a target="_blank" title="【国庆特推】横店精品全景、梦幻谷狂欢精彩2日游" href="http://www.lvmama.com/route/20970">【国庆特推】横店精品全景、梦幻谷狂欢精彩2日游<span>&nbsp;看明星、要签名就去横店！</span></a>
            		</li>
            		<li><strong>￥240</strong><del>￥376</del>
            		<a target="_blank" title="【唯美古镇】梦里水乡乌镇1日游" href="http://www.lvmama.com/route/20870">【唯美古镇】梦里水乡乌镇1日游<span>&nbsp;2人起团购！另有特大惊喜！双人套餐火热订购中</span></a>
            		</li>
            		<li><strong>￥278</strong><del>￥308</del>
            		<a target="_blank" title="【山湖风光】太湖源、小九寨沟、柳溪江漂流、天目大峡谷特色2日游（阳光价）" href="http://www.lvmama.com/route/20905">【山湖风光】太湖源、柳溪江漂流、天目大峡谷特色2日游（阳光价）<span>&nbsp;国庆期间开班</span></a>
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/lp/putuoshan/" target="_blank"><img src="http://pic.lvmama.com/img/dest/putuoshan_750x60.jpg" alt="普陀山" /></a></div>
            <h4>上海国内长线旅游线路</h4>
            <ul>
            		<li><strong>￥1330</strong><del>￥1590</del>
            		<a target="_blank" title="【国庆特推】厦门集美鼓浪屿双飞三星3日游" href="http://www.lvmama.com/route/20230">【国庆特推】厦门集美鼓浪屿双飞三星3日游<span>&nbsp;赴黄金海岸，感受海岛风情！</span></a>
            			
            		</li>
            		<li><strong>￥2790</strong><del>￥2880</del>
            		<a target="_blank" title="【国庆特推】北京双飞五日游（挂三星纯玩团）" href="http://www.lvmama.com/route/20820">【国庆特推】北京双飞五日游（挂三星纯玩团）<span>&nbsp;游览北京精华景点，圆您京都之梦</span></a>
            			
            		</li>
            		<li><strong>￥1490</strong><del>￥1600</del>
            		<a target="_blank" title="【国庆特推】海上花园——厦门鼓浪屿、客家文化——永定土楼4日游" href="http://www.lvmama.com/route/20841">【国庆特推】海上花园——厦门鼓浪屿、客家文化——永定土楼4日游<span>&nbsp;动车往返，贴心服务 </span></a>
            			
            		</li>
            		<li><strong>￥1250</strong><del>￥1420</del>
            		<a target="_blank" title="【中秋特惠】青岛三星双飞4日半自助游" href="http://www.lvmama.com/route/20175">【中秋特惠】青岛三星双飞4日半自助游<span>&nbsp;赠送青岛奥帆中心“蓝色畅想”大型演出门票</span></a>
            			
            		</li>
            		<li><strong>￥3640</strong><del>￥3940</del>
            		<a target="_blank" title="【中秋特惠】祈福南山-三亚半自助五日游" href="http://www.lvmama.com/route/20958">【中秋特惠】祈福南山-三亚半自助5日游<span>&nbsp;纯玩无购物，行程轻松自由</span></a>
            			
            		</li>
            		<li><strong>￥972</strong><del>￥1360</del>
            		<a target="_blank" title="【驴妈妈特惠】厦门集美鼓浪屿双飞三日游标准等（新版）" href="http://www.lvmama.com/route/20989">【驴妈妈特惠】厦门集美鼓浪屿双飞三日游标准等（新版）<span>&nbsp;爱心大放送，全城最实惠！</span></a>
            			
            		</li>
            		<li><strong>￥2710</strong><del>￥3090</del>
            		<a target="_blank" title="【探湘西魂】张家界天子山袁家界凤凰古城贵族之旅双飞5日游" href="http://www.lvmama.com/route/20160">【探湘西魂】张家界天子山袁家界凤凰古城贵族之旅双飞5日游<span>&nbsp;四星住宿景点更丰富</span></a>
            			
            		</li>
            		<li><strong>￥1330</strong><del>￥1590</del>
            		<a target="_blank" title="（国庆尊享）云台山少林寺龙门石窟动车往返挂四四日游" href="http://www.lvmama.com/route/20319">【仙山圣地】桂林、漓江、阳朔特惠A等双飞4日游<span>&nbsp;2晚宿三星特惠双飞之旅，价格经济实惠</span></a>
            			
            		</li>
            		<li><strong>￥3880</strong><del>￥4080</del>
            		<a target="_blank" title="【千里寻梦】香格里拉、昆明、大理、丽江双飞品质8日游" href="http://www.lvmama.com/route/20694">【千里寻梦】香格里拉、昆明、大理、丽江双飞品质8日游<span>&nbsp;参观纳西古建筑，欣赏独特风貌</span></a>
            			
            		</li>
            		<li><strong>￥3860</strong><del>￥4180</del>
            		<a target="_blank" title="宁夏沙湖/沙坡头＋内蒙古阿拉善秘境/通湖草原双飞5日游（3晚宿三星）" href="http://www.lvmama.com/route/20572">宁夏沙湖/沙坡头＋内蒙古阿拉善秘境/通湖草原双飞5日游<span>&nbsp;3晚宿三星，舒适品质享受！</span></a>
            			
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/route/20664" target="_parent"><img src="http://pic.lvmama.com/img/dest/jipuzhai_750x60.jpg" alt="柬埔寨" /></a></div>
        	<h4>上海出境游热门线路</h4>
            <ul>
            		<li><strong>￥5900</strong><del>￥6110</del>
            		<a target="_blank" title="本州阪东线6日特价游" href="http://www.lvmama.com/route/20930">本州阪东线6日特价游<span>&nbsp;适合初次赴日的线路，体验日本温泉，领略日本文化精髓</span></a>
            			
            		</li>
            		<li><strong>￥10200</strong><del>￥10500</del>
            		<a target="_blank" title="魅力初秋之北海道+东京6日精华游" href="http://www.lvmama.com/route/20843">魅力初秋之北海道+东京6日精华游<span>&nbsp;直飞航班，泡日式温泉、享日本美食、购物、娱乐四不误</span></a>
            			
            		</li>
            		<li><strong>￥2838</strong><del>￥2988</del>
            		<a target="_blank" title="首尔+雪岳山+金沙海滨品质5日游（五花酒店)" href="http://www.lvmama.com/route/21001">首尔+雪岳山+金沙海滨品质5日游（五花酒店)<span>&nbsp;深度体验南怡岛浪漫风情！浪漫休闲之旅</span></a>
            			
            		</li>
            		<li><strong>￥4150</strong><del>￥4300</del>
            		<a target="_blank" title="普吉直飞4晚6天豪华游" href="http://www.lvmama.com/route/20949">普吉直飞4晚6天豪华游<span>&nbsp;直飞普吉，海岛与城市；自然与现代；休闲与购物</span></a>
            			
            		</li>
            		<li><strong>￥5799</strong><del>￥5999</del>
            		<a target="_blank" title="东京一地5日欢快游(全日空系列)" href="http://www.lvmama.com/route/20859">东京一地5日欢快游(全日空系列)<span>&nbsp;适合朋友、情侣出游，充裕购物时间，让您在东京BUY不停！</span></a>
            			
            		</li>
  <li><strong>￥2850</strong><del>￥3000</del>
            		<a target="_blank" title="首尔一地5日自由行" href="http://www.lvmama.com/route/20998">首尔一地5日自由行<span>&nbsp;出行前搞定所有行程，让您的此次旅游更加胸有成竹，舒适方便！</span></a>
            			
            		</li>
  <li><strong>￥3788</strong><del>￥3988</del>
            		<a target="_blank" title="首尔+济州+釜山“KTX动车及温泉”特色5日游（同行返400）（四花酒店）" href="http://www.lvmama.com/route/21005">首尔+济州+釜山“KTX动车及温泉”特色5日游（同行返400）（四花酒店）</a>
            			
            		</li>
  <li><strong>￥10900</strong><del>￥11200</del>
            		<a target="_blank" title="北海道东京名蟹温泉6日精华游" href="http://www.lvmama.com/route/20878">北海道东京名蟹温泉6日精华游<span>&nbsp;既可在北海道赏名所迷人风光，又可领略东京都市繁华！</span></a>
            			
            		</li>
  <li><strong>￥8950</strong><del>￥9450</del>
            		<a target="_blank" title="本州东阪6日豪华游（全程五星）" href="http://www.lvmama.com/route/20997">本州东阪6日豪华游（全程五星）<span>&nbsp;住东京市区五星级酒店,深度体验神秘的东瀛国度！</span></a>
            			
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/route/20175" target="_blank"><img src="http://pic.lvmama.com/img/dest/qindao_750x60.jpg" alt="青岛" /></a></div>
       </div>
       <div id="hz" class="route-pro">
       		<h4>杭州当季推荐热门线路</h4>
            <ul>
<li><strong>￥348</strong><del>￥428</del>
            		<a target="_blank" title="【国庆精彩狂欢】东阳横店影视城2日游" href="http://www.lvmama.com/route/20459">【国庆精彩狂欢】东阳横店影视城2日游<span>&nbsp;体验影视人生，享受横店美食</span></a>
            			
            		</li>
<li><strong>￥148</strong><del>￥218</del>
            		<a target="_blank" title="【中秋尊享】江南水乡周庄1日游" href="http://www.lvmama.com/route/20390">【中秋尊享】江南水乡周庄1日游<span>&nbsp;观古镇品美食，评江南风光</span></a>
            			
            		</li>
<li><strong>￥3080</strong><del>￥3280</del>
            		<a target="_blank" title="【中秋尊享】北京五星品质纯玩团5日游" href="http://www.lvmama.com/route/20742">【中秋尊享】北京五星品质纯玩团5日游<span>&nbsp;游览北京精华景点，圆您京都之梦</span></a>
            			
            		</li>
<li><strong>￥2880</strong><del>￥3190</del>
            		<a target="_blank" title="【中秋特别推荐】昆大丽双飞四＋五星纯玩6日游" href="http://www.lvmama.com/route/20772">【中秋特别推荐】昆大丽双飞四＋五星纯玩6日游<span>&nbsp;赠印象丽江、观音峡、三塔、古维索道门票</span></a>
            			
            		</li>
<li><strong>￥2660</strong><del>￥2780</del>
            		<a target="_blank" title="【中秋尊享】桂林、漓江黄金水道、阳朔、冠岩双卧5日三星休闲团" href="http://www.lvmama.com/route/20737">【中秋尊享】桂林、漓江黄金水道、阳朔、冠岩双卧5日三星休闲团<span>&nbsp;赠旅游代金券100元</span></a>
            			
            		</li>
            </ul>
        	<h4>杭州周边短途线路</h4>
            <ul>
<li><strong>￥488</strong><del>￥578</del>
            		<a target="_blank" title="【中秋首选】雁荡山、楠溪江2日游" href="http://www.lvmama.com/route/20497">【中秋首选】雁荡山、楠溪江2日游<span>&nbsp;纯玩无购物,只此一班不容错过！</span></a>
            			
            		</li>
<li><strong>￥498</strong><del>￥568</del>
            		<a target="_blank" title="【中秋特荐】海天佛国普陀山2日游" href="http://www.lvmama.com/route/20145">【中秋特荐】海天佛国普陀山2日游<span>&nbsp;祈福求运，畅游山水</span></a>
            			
            		</li>
<li><strong>￥673</strong><del>￥783</del>
            		<a target="_blank" title="【中秋特推】苏州、周庄、上海3日游（单程）" href="http://www.lvmama.com/route/20649">【中秋特推】苏州、周庄、上海3日游（单程）<span>&nbsp;现代都市加唯美古镇，您的首选</span></a>
            			
            		</li>
<li><strong>￥753</strong><del>￥843</del>
            		<a target="_blank" title="【中秋特推】苏州、周庄、上海3日游（来回程）" href="http://www.lvmama.com/route/20653">【中秋特推】苏州、周庄、上海3日游（来回程）<span>&nbsp;现代都市加唯美古镇，您的首选</span></a>
            			
            		</li>
<li><strong>￥608</strong><del>￥698</del>
            		<a target="_blank" title="【中秋特荐】普陀山、朱家尖3日" href="http://www.lvmama.com/route/20251">【中秋特荐】普陀山、朱家尖3日<span>&nbsp;行程直达普陀山，祈福求运的最佳选择</span></a>
            			
            		</li>
<li><strong>￥410</strong><del>￥510</del>
            		<a target="_blank" title="【游山玩水】千岛湖、林海归真2日游" href="http://www.lvmama.com/route/20463">【游山玩水】千岛湖、林海归真2日游<span>&nbsp;七里扬帆，一路秀水</span></a>
            			
            		</li>
<li><strong>￥278</strong><del>￥318</del>
            		<a target="_blank" title="【影视基地】欢乐横店全景品质2日游" href="http://www.lvmama.com/route/20476">【影视基地】欢乐横店全景品质2日游<span>&nbsp;体验影视人生，享受横店美食</span></a>
            			
            		</li>
<li><strong>￥120</strong><del>￥140</del>
            		<a target="_blank" title="【唯美古镇】江南名镇、醉美水乡－乌镇1日游" href="http://www.lvmama.com/route/20422">【唯美古镇】江南名镇、醉美水乡－乌镇1日游<span>&nbsp;含交通+门票+导服，绝对超值！</span></a>
            			
            		</li>
<li><strong>￥393</strong><del>￥453</del>
            		<a target="_blank" title="【唯美古镇】苏州周庄2日游（单程）" href="http://www.lvmama.com/route/20643">【唯美古镇】苏州周庄2日游（单程）<span>&nbsp;梦回古城，寻梦盘门</span></a>
            			
            		</li>
<li><strong>￥433</strong><del>￥513</del>
            		<a target="_blank" title="【唯美古镇】苏州周庄2日游（来回程）" href="http://www.lvmama.com/route/20645">【唯美古镇】苏州周庄2日游（来回程）<span>&nbsp;赏姑苏美景，品美食小吃</span></a>
            			
            		</li>
<li><strong>￥363</strong><del>￥423</del>
            		<a target="_blank" title="【唯美古镇】苏州同里2日游（单程）" href="http://www.lvmama.com/route/20647">【唯美古镇】苏州同里2日游（单程）<span>&nbsp;游东方小威尼斯,感受江南风光</span></a>
            			
            		</li>
<li><strong>￥413</strong><del>￥493</del>
            		<a target="_blank" title="【唯美古镇】苏州同里2日游（来回程）" href="http://www.lvmama.com/route/20651">【唯美古镇】苏州同里2日游（来回程）<span>&nbsp;观古镇品美食，评江南风光</span></a>
            			
            		</li>
<li><strong>￥155</strong><del>￥195</del>
            		<a target="_blank" title="【名人故里】鲁迅故里、师爷名城－绍兴1日游" href="http://www.lvmama.com/route/20374">【名人故里】鲁迅故里、师爷名城－绍兴1日游<span>&nbsp;访名人故里、观绍兴名景</span></a>
            			
            		</li>
<li><strong>￥280</strong><del>￥340</del>
            		<a target="_blank" title="【名人故里】蒋氏奉化溪口1日游" href="http://www.lvmama.com/route/20445">【名人故里】蒋氏奉化溪口1日游<span>&nbsp;做客蒋介石老家,赏溪口美景</span></a>
            			
            		</li>
<li><strong>￥268</strong><del>￥318</del>
            		<a target="_blank" title="【名人故里】鲁迅故里风光1日游" href="http://www.lvmama.com/route/20418">【名人故里】鲁迅故里风光1日游<span>&nbsp;夏日享受冬季恋歌，绍兴乔波雪地狂！</span></a>
            			
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/lp/putuoshan/" target="_blank"><img src="http://pic.lvmama.com/img/dest/putuoshan_750x60.jpg" alt="普陀山" /></a></div>
            <h4>杭州国内长线旅游热门线路</h4>
            <ul>
<li><strong>￥1750</strong><del>￥1980</del>
            		<a target="_blank" title="【中秋特推】厦门鼓浪屿、集美浪漫海滨双飞4日游" href="http://www.lvmama.com/route/20764">【中秋特推】厦门鼓浪屿、集美浪漫海滨双飞4日游<span>&nbsp;品闽南美食，赏海岛风光</span></a>
            			
            		</li>
<li><strong>￥2980</strong><del>￥3280</del>
            		<a target="_blank" title="【中秋特推】大漠风情内蒙希拉穆仁大草原、银肯响沙湾、成吉思汗陵双飞5日游" href="http://www.lvmama.com/route/20787">【中秋特推】内蒙希拉穆仁大草原、银肯响沙湾、成吉思汗陵双飞5日游<span>&nbsp;特色手把肉</span></a>
            			
            		</li>
<li><strong>￥6180</strong><del>￥6580</del>
            		<a target="_blank" title="【中秋特推】拉萨、日喀则、纳木错双飞一卧8日游" href="http://www.lvmama.com/route/20784">【中秋特推】拉萨、日喀则、纳木错双飞一卧8日游<span>&nbsp;坐上火车去拉萨，去看那美丽的格桑花呀</span></a>
            			
            		</li>
<li><strong>￥2250</strong><del>￥2280</del>
            		<a target="_blank" title="【中秋尊享】三亚双飞五5日游四＋五星品质团" href="http://www.lvmama.com/route/20749">【中秋尊享】三亚双飞五5日游四＋五星品质团<span>&nbsp;两晚近海酒店，含南山文化苑，无加点</span></a>
            			
            		</li>
<li><strong>￥4950</strong><del>￥4980</del>
            		<a target="_blank" title="【中秋尊享】成都、九寨沟、黄龙、峨眉乐山三飞5日游" href="http://www.lvmama.com/route/20751">【中秋尊享】成都、九寨沟、黄龙、峨眉乐山三飞5日游<span>&nbsp;杭州直航，每周一班</span></a>
            			
            		</li>
<li><strong>￥2630</strong><del>￥2680</del>
            		<a target="_blank" title="【岛屿风情】青岛、海阳万米金沙滩、威海、蓬莱＋大连双飞5日挂四纯玩团" href="http://www.lvmama.com/route/20770">【岛屿风情】青岛、海阳万米金沙滩、威海、蓬莱＋大连双飞5日挂四纯玩团<span>&nbsp;晚去晚回</span></a>
            			
            		</li>
<li><strong>￥3080</strong><del>￥3280</del>
            		<a target="_blank" title="【涿鹿中原】游中原西安，尝最速高铁双飞6日品质游" href="http://www.lvmama.com/route/20747">【涿鹿中原】游中原西安，尝最速高铁双飞6日品质游<span>&nbsp;不尽温柔汤泉水，千古风流华清宫！</span></a>
            			
            		</li>
<li><strong>￥3080</strong><del>￥3180</del>
            		<a target="_blank" title="重庆、长江三峡、世纪大坝、黄鹤楼下水双飞5日游（下水内宾船）" href="http://www.lvmama.com/route/20759">重庆、长江三峡、世纪大坝、黄鹤楼下水双飞5日游（下水内宾船）<span>&nbsp;只此一班不容错过！</span></a>
            			
            		</li>
<li><strong>￥2860</strong><del>￥2980</del>
            		<a target="_blank" title="张家界、袁家界、芙蓉镇、凤凰双飞5日四星纯玩团(特价班）" href="http://www.lvmama.com/route/20768">张家界、袁家界、芙蓉镇、凤凰双飞5日游(特价班）<span>&nbsp;四星纯玩，绝对超值!</span></a>
            			
            		</li>
<li><strong>￥2250</strong><del>￥2280</del>
            		<a target="_blank" title="桂林、大漓江、阳朔、银子岩双飞4日四星品质团" href="http://www.lvmama.com/route/20740">桂林、大漓江、阳朔、银子岩双飞4日四星品质团<span>&nbsp;赠送梦幻漓江，绝对超值</span></a>
            			
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/route/20664" target="_parent"><img src="http://pic.lvmama.com/img/dest/jipuzhai_750x60.jpg" alt="柬埔寨" /></a></div>
            
       </div>
       <div id="nj" class="route-pro">
       		<h4>南京当季推荐热门线路</h4>
            <ul>
<li><strong>￥148</strong><del>￥178</del>
            		<a target="_blank" title="【中秋首选】苏州乐园欢乐水世界精彩1日游" href="http://www.lvmama.com/route/20268">【中秋首选】苏州乐园欢乐水世界精彩1日游<span>&nbsp;绝对超值，值得回味！</span></a>
            			
            		</li>
<li><strong>￥940</strong><del>￥1040</del>
            		<a target="_blank" title="【世博专题】上海世博会二日游" href="http://www.lvmama.com/route/20491">【世博专题】上海世博会二日游<span>&nbsp;二次进园，游确保中国馆</span></a>
            			
            		</li>
<li><strong>￥160</strong><del>￥180</del>
            		<a target="_blank" title="【主题乐园】芜湖方特世界1日游" href="http://www.lvmama.com/route/20324">【主题乐园】芜湖方特世界1日游<span>&nbsp;探秘影视特技、感受科幻神奇！</span></a>
            			
            		</li>
<li><strong>￥168</strong><del>￥198</del>
            		<a target="_blank" title="【主题乐园】常州恐龙园夏天新玩法1日游" href="http://www.lvmama.com/route/20306">【主题乐园】常州恐龙园夏天新玩法1日游<span>&nbsp;重返恐龙世纪，感受梦幻世界</span></a>
            			
            		</li>
<li><strong>￥168</strong><del>￥258</del>
            		<a target="_blank" title="【佛家圣地】无锡灵山大佛1日游" href="http://www.lvmama.com/route/20235">【佛家圣地】无锡灵山大佛1日游<span>&nbsp;纯玩无购物，保证您的游览时间</span></a>
            			
            		</li>
            </ul>
        	<h4>南京周边短途线路</h4>
            <ul>
<li><strong>￥410</strong><del>￥480</del>
            		<a target="_blank" title="【中秋特推】庐山、桃花源漂流、三叠泉3日" href="http://www.lvmama.com/route/20465">【中秋特推】庐山、桃花源漂流、三叠泉3日游<span>&nbsp;畅游庐山，绝对超值！</span></a>
            			
            		</li>
<li><strong>￥458</strong><del>￥508</del>
            		<a target="_blank" title="【经典热门】魅力黄山3日游" href="http://www.lvmama.com/route/20428">【经典热门】魅力黄山3日游<span>&nbsp;畅游黄山，宿山顶，天天发班</span> </a>
            			
            		</li>
<li><strong>￥228</strong><del>￥318</del>
            		<a target="_blank" title="【经典热门】上海欢乐谷1日游" href="http://www.lvmama.com/route/20259">【经典热门】上海欢乐谷1日游<span>&nbsp;天天都精彩，天天都欢乐</span></a>
            			
            		</li>
<li><strong>￥118</strong><del>￥158</del>
            		<a target="_blank" title="【唯美古镇】江南名镇、水乡乌镇风光1日游" href="http://www.lvmama.com/route/20370">【唯美古镇】江南名镇、水乡乌镇风光1日游<span>&nbsp;追忆似水年华魅力古镇，品江南真山水</span></a>
            			
            		</li>
<li><strong>￥288</strong><del>￥378</del>
            		<a target="_blank" title="【唯美古镇】西塘休闲度假2日游" href="http://www.lvmama.com/route/20208">【唯美古镇】西塘休闲度假2日游<span>&nbsp;枕水人家、活着的千年古镇</span></a>
            			
            		</li>
<li><strong>￥308</strong><del>￥398</del>
            		<a target="_blank" title="【游山玩水】千岛湖、西湖、富春江皮筏漂流2日游" href="http://www.lvmama.com/route/20243">【游山玩水】千岛湖、西湖、富春江皮筏漂流2日游<span>&nbsp;两湖一江亲水游</span></a>
            			
            		</li>
<li><strong>￥448</strong><del>￥498</del>
            		<a target="_blank" title="【游山玩水】雁荡山、大龙湫、灵岩避暑3日游" href="http://www.lvmama.com/route/20470">【游山玩水】雁荡山、大龙湫、灵岩避暑3日游<span>&nbsp;纯玩团无购物，游览时间充足</span></a>
            			
            		</li>
<li><strong>￥618</strong><del>￥688</del>
            		<a target="_blank" title="【海滨祈福】海天佛国普陀山2日游" href="http://www.lvmama.com/route/20635">【海滨祈福】海天佛国普陀山2日游<span>&nbsp;周六开班纯玩无购物</span></a>
            			
            		</li>
<li><strong>￥258</strong><del>￥288</del>
            		<a target="_blank" title="【时尚都市】上海都市观光、野生动物园2日游" href="http://www.lvmama.com/route/20480">【时尚都市】上海都市观光、野生动物园2日游<span>&nbsp;每周六开班，赶快行动！</span></a>
            			
            		</li>
<li><strong>￥128</strong><del>￥218</del>
            		<a target="_blank" title="【回归自然】溱湖湿地公园、农耕文化园1日游" href="http://www.lvmama.com/route/20252">【回归自然】溱湖湿地公园、农耕文化园1日游<span>&nbsp;游地质公园，感受农家气息</span></a>
            			
            		</li>
<li><strong>￥188</strong><del>￥278</del>
            		<a target="_blank" title="【经典热门】徽州古城、太平湖游船、蓝水河皮筏漂流2日游" href="http://www.lvmama.com/route/20226">【经典热门】徽州古城、太平湖游船、蓝水河皮筏漂流2日游<span>&nbsp;周六开班，不容错过</span></a>
            			
            		</li>
<li><strong>￥208</strong><del>￥298</del>
            		<a target="_blank" title="【游山玩水】浙西大峡谷、柳溪江漂流2日" href="http://www.lvmama.com/route/20335">【游山玩水】浙西大峡谷、柳溪江漂流2日<span>&nbsp;要漂就漂浙西第一漂</span></a>
            			
            		</li>
<li><strong>￥188</strong><del>￥278</del>
            		<a target="_blank" title="【游山玩水】东天目、双溪漂流、山沟沟2日游" href="http://www.lvmama.com/route/20475">【游山玩水】东天目、双溪漂流、山沟沟2日游<span>&nbsp;周末开班特惠之旅</span></a>
            			
            		</li>
<li><strong>￥218</strong><del>￥358</del>
            		<a target="_blank" title="【游山玩水】浙西大峡谷、大明山、龙井峡漂流2日" href="http://www.lvmama.com/route/20493">【游山玩水】浙西大峡谷、大明山、龙井峡漂流2日<span>&nbsp;峡谷+漂流 激情夏末</span></a>
            			
            		</li>
<li><strong>￥388</strong><del>￥478</del>
            		<a target="_blank" title="【影视基地】快乐横店2日游" href="http://www.lvmama.com/route/20476">【影视基地】快乐横店2日游<span>&nbsp;体验影视人生，享受横店美食</span></a>
            			
            		</li>
<li><strong>￥420</strong><del>￥520</del>
            		<a target="_blank" title="【游山玩水】庐山、桃花源漂流、三叠泉3日" href="http://www.lvmama.com/route/20465">【游山玩水】庐山、桃花源漂流、三叠泉3日<span>&nbsp;中秋特别推荐，游适合亲朋好友</span></a>
            			
            		</li>
<li><strong>￥458</strong><del>￥548</del>
            		<a target="_blank" title="【世界名山】黄山经典3日游" href="http://www.lvmama.com/route/20428">【世界名山】黄山经典3日游<span>&nbsp;中秋适合去黄山，感受黄山天下无山</span></a>
            			
            		</li>
<li><strong>￥260</strong><del>￥290</del>
            		<a target="_blank" title="【世博专题】上海世博1日游（沃尔沃豪华大巴）" href="http://www.lvmama.com/route/20439">【世博专题】上海世博1日游（沃尔沃豪华大巴）<span>&nbsp;畅游世博会，感受异国风情</span></a>
            			
            		</li>
<li><strong>￥618</strong><del>￥688</del>
            		<a target="_blank" title="【海滨祈福】普陀山纯玩2日游" href="http://www.lvmama.com/route/20635">【海滨祈福】普陀山纯玩2日游<span>&nbsp;祈福求运，畅游山水</span></a>
            			
            		</li>
            </ul>
            <div class="guang_g"><a href="http://www.lvmama.com/lp/putuoshan/" target="_blank"><img src="http://pic.lvmama.com/img/dest/putuoshan_750x60.jpg" alt="普陀山" /></a></div>
            
       </div>
       
    </div>
    <!--右侧 结束-->
</div>

<#include "/common/footer.ftl">


<script>
<!--
//search form submit
function searchRoute(){
$("#searchDest").submit();
}
function searchRoute(){
var u = "/destRoute/destrote!routeList.do?destTo="+$("#destTo").val()+"&destFrom="+$("#destFrom").val();
window.location=u;
}


function openwindow(url, name, iWidth, iHeight) {
    var url; //转向网页的地址;
    var name; //网页名称，可为空;
    var iWidth; //弹出窗口的宽度;
    var iHeight; //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=yes,titlebar=no');
}
//-->
</script>

<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
</body>
</html>
