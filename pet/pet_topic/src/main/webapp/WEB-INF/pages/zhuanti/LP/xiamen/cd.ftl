<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" />
<title>厦门著名景点_厦门哪里好玩_厦门哪些地方好玩-驴妈妈旅游网</title>
<meta name="keywords" content="厦门著名景点,厦门旅游" />
<meta name="description" content="驴妈妈向您推荐厦门一些著名旅游景点,厦门哪里好玩,介绍厦门哪些地方好玩,厦门有哪些地方好玩,有还有厦门特色的美食和厦门的一些特色旅馆介绍." />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/xiamen/lv_weekend.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<base target="_blank" />
<style type="text/css">
<!--
.STYLE1 {
	font-size: 14px;
	color: #333333;
}
.more_btn{ position:absolute;left:350px;top:20px;z-index:9999;}
-->
</style>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="wrap">
     <div class="banner">
  		  <h1>厦门著名景点</h1>
		  <h2>厦门哪里好玩</h2>
          <img src="http://pic.lvmama.com/img/zt/xiamen/banner11.jpg" width="980" height="190"  alt="厦门著名景点"/>
          <img src="http://pic.lvmama.com/img/zt/xiamen/banner12.jpg" width="980" height="190"  alt="厦门哪里好玩"/>
          <!--<ul class="nav">
           	<li class="curbg"><a href="#t1" target="_self">厦门本岛篇</a></li>
            <li><a href="http://www.lvmama.com/guide/zt/gulangyu/" target="_blank">鼓浪屿篇</a></li>

            <li><a href="#t4" target="_self">线路产品推荐</a></li>
          </ul>-->
          <ul class="nav_new">
          	<li class="fisrtLi"><a href="http://www.lvmama.com/zt/lvyou/xiamen/">上　海</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/xiamen/gz.html">广　州</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/xiamen/bj.html">北　京</a></li>
            <li class="nav_li"><a href="http://www.lvmama.com/zt/lvyou/xiamen/cd.html">成　都</a></li>
          </ul>
      </div><!--banner end-->
	<div class="top_img">
            <div class="slide_bg">
            	<div class="slide_contain">
                	<ul id="slide" class="slide">
                       	<@s.iterator value="map.get('${station}_cd_img')"  var="var" status="st">
	                		<li>	
		                      <img src="${imgUrl?if_exists}" height="235" width="630"/>
		                      <dl>
			                       <dt><a href="${url?if_exists}">${title?if_exists}</a></dt>
			                       <dd>${remark?if_exists}</dd>
		                       </dl>
	                        </li>
                         </@s.iterator> 
                    </ul>
                    <ul class="img_num" id="img_num">
                    		<li class="cur">●</li>
                            <li>●</li>
                            <li>●</li>
                            <li>●</li>
                    </ul>
                </div>
            </div><!--slide_bg end-->
            <div class="strategy">
            	<h3><strong>在这里享受时间</strong></h3>
                <p class="stra_text">阳光、沙滩、海浪、仙人掌，还有一位老船长。正如这歌词一般，厦门就是这么一个慵懒的地方，时光在这里无限延展，如梦如幻。<br/>
                厦门是多姿多彩的，不管何时前来，总能发现她的魅力。厦门是文艺青年，她漫步在全国最美校园厦大；厦门是小资青年，她在鼓浪屿的民宿中喝咖啡；厦门是吃货青年，她在小吃街中寻觅蚵仔煎的踪影；厦门也是2B青年，她在方特欢乐世界中尖叫不已…… 你还能什么？快来厦门吧 ！
		</p>
            </div>
    	</div><!--top_img end-->
   	  <div class="main">
	  <!--周末游玩线路推荐-->    <div class="weekend_recom cpFjJsBox">
			<ul class="typeNav">
            	<li class="fisrtLi typeNav_li">机票+酒店</li>
                <li>跟团游</li>
                <li>门　票</li>
                <li>门票+酒店</li>
            </ul>
           <h3 id="t4"><em>厦门游玩线路推荐</em><span></span><a href="http://www.lvmama.com/search/route/成都-厦门.html" class="more_btn">更多&gt;&gt;</a></h3> 
              <div class="cpJsBox" style="display:block;">
	                 <dl class="cpBox">
		               <@s.iterator value="map.get('${station}_cd_freeLine1')"  var="var" status="st">
	                      <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_freeLine1')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
	                </dl>
	                <dl class="cpBox">
	                   <@s.iterator value="map.get('${station}_cd_freeLine2')"  var="var" status="st">
	                      <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_freeLine2')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
	                </dl>
				</div>
            <div class="cpJsBox">
                 <dl class="cpBox">
                       <@s.iterator value="map.get('${station}_cd_group1')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_group1')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
                <dl class="cpBox">
                      <@s.iterator value="map.get('${station}_cd_group2')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_group2')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
			</div>
            <div class="cpJsBox">
                 <dl class="cpBox">
                      <@s.iterator value="map.get('${station}_cd_ticket1')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_ticket1')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
                <dl class="cpBox">
                       <@s.iterator value="map.get('${station}_cd_ticket2')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                    <dd>
	                    <@s.iterator value="map.get('${station}_cd_ticket2')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
			</div>
            <div class="cpJsBox">
                 <dl class="cpBox">
                      <@s.iterator value="map.get('${station}_cd_hotel1')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                      <dd>
	                    <@s.iterator value="map.get('${station}_cd_hotel1')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
                <dl class="cpBox">
                     <@s.iterator value="map.get('${station}_cd_hotel2')"  var="var" status="st">
	                       <@s.if test="#st.first"> <dt><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="140" alt="鼓浪屿" /></a></dt></@s.if>
	                   </@s.iterator> 
	                      <dd>
	                    <@s.iterator value="map.get('${station}_cd_hotel2')"  var="var" status="st">
	                       <p><strong><a href="${url?if_exists}">${title?if_exists}</a></strong><em><font>驴妈妈会员价:<strong>${memberPrice?if_exists?replace(".0","")}</strong>元起</font></em></p>
		                 </@s.iterator>
	                    </dd>
                </dl>
			</div>


   </div><!--weekend_recom end--> 
    	<div class="city_sy">
        	<h3 id="t1" class="gonglue"><em>厦门印象之——校园</em><span>重拾记忆中青涩的校园时光</span></h3>
            <div class="city_main">
            		<div class="info fl">
                    		<p><a href="http://www.lvmama.com/guide/2013/1011-175480.html" class="info_img mar_img"><img src="http://pic.lvmama.com/img/zt/xiamen/xiada1.jpg" height="210" width="200" alt="鼓浪屿情调小地" /></a><a href="http://www.lvmama.com/guide/2013/1011-175480.html" class="info_img"><img src="http://pic.lvmama.com/img/zt/xiamen/xiada2.jpg" height="210" width="200" alt="" /></a></p>
                            <dl>
                            	<dt><h4 class="title"><a href="http://www.lvmama.com/guide/2013/1011-175480.html">1、不谈恋爱？那你考什么厦大！</a><i>The first recommended</i></h4></dt>
                                <dd><strong>推荐理由：</strong>北大清华作为高校的"学术骨干"，一直给人严肃、且高高在上难以接近的感觉，就好像一位老学者。而厦门大学似乎对此并不屑，依山傍海、椰林树影的厦大，却好似一位多情的少女，有着自己的一片浪漫……</dd>
 								<@s.iterator value="map.get('${station}_cd_xiaoyuan1')"  var="var" status="st">
                                <dd><a href="${url?if_exists}">
                            		推荐产品：${title?if_exists}</a></dd>
								</@s.iterator>                                
                            </dl>
                    </div><!--info end-->
                    <div class="info info2">
                    		<p><a href="http://www.lvmama.com/guide/2013/1011-175481.html" class="info_img"><img src="http://pic.lvmama.com/img/zt/xiamen/jimei1.jpg" height="210" width="200" alt="集美学村" /></a><a href="http://www.lvmama.com/guide/2013/1011-175481.html" class="info2_img mar_img"><img src="http://pic.lvmama.com/img/zt/xiamen/jimei2.jpg" height="100" width="210" alt="" /></a><a href="http://www.lvmama.com/guide/2013/1011-175481.html" class="info2_img"><img src="http://pic.lvmama.com/img/zt/xiamen/jimei3.jpg" height="100" width="210" alt="" /></a></p>
                            <dl>
                            	<dt><h4 class="title"><a href="http://www.lvmama.com/guide/2012/0604-163330-1.html">2、集美学村，嘉庚精神你感受一下。</a><i>The second recommended</i></h4></dt>
                                <dd><strong>推荐理由：</strong>厦门是一个文艺和灵气的地方，除了"最美校园"厦大之外，集美学村也是一个充满文艺范儿的地方，值得一去。说起集美学村，不得不提起陈嘉庚，陈嘉庚是抗日战争、解放战争时期的爱国领袖、企业家、教育家……</dd>
                                <@s.iterator value="map.get('${station}_cd_xiaoyuan2')"  var="var" status="st">
                                <dd><a href="${url?if_exists}">
                            		推荐产品：${title?if_exists}</a></dd>
								</@s.iterator>
                                
                      </dl>
              </div><!--info2 end-->
              <div class="clear">
                    <!--info3 end-->
                     <!--info4 end-->
                      <!--info5 end-->
              </div><!--清除浮动结束-->
            </div>
			
			<!--city_main end-->
        </div><!--city_sy end-->
        
        <br/>
        <h3 id="t2"><em>厦门印象之——海滩</em><span>厦大白城、椰风寨、观音山，厦门三大海滩。</span></h3>
        <p align="center" class="czx_bigimg">
            <a href="http://www.lvmama.com/guide/2013/1012-175504.html"><img src="http://pic.lvmama.com/img/zt/xiamen/haitan.jpg" width="889" height="285" alt="厦门海滩" /></a>
            </p>    
            <div align="center"><br/>
            </div>
            

        <h3><em>厦门印象之——美食</em><span>蚵仔煎、鱼丸汤、沙茶面，闽南美食一次吃个够！</span></h3> 
<div class="czx">
<dl>
            	<dt class="fl"><a href="http://www.lvmama.com/guide/2013/1012-175487.html"><img src="http://pic.lvmama.com/img/zt/xiamen/meishi1.jpg" width="250" height="274" alt="厦门美食推荐" /></a></dt>
                <dd class="wid_630 fr">
                		<h4 class="title"><a href="http://www.lvmama.com/guide/2013/1012-175487.html">厦门"台湾小吃街"，吃货必去之地，50元吃翻天！</a><i>First recommended</i></h4>
                        <p><strong>推荐理由：</strong>
厦门美食风味独特，充满浓郁的闽南特色，蚵仔煎、鱼丸汤、烧仙草等美食早已闻名全国。那么在厦门，有没有一个地方可以一次性吃到所有厦门美食呢？就在厦门的"台湾小吃街"！</p>
 <@s.iterator value="map.get('${station}_cd_meishi1')"  var="var" status="st">
	                        <p class="czx_link">
	                        <a href="${url?if_exists}">
	                                                                                                产品推荐：${title?if_exists}</a></p> 
                        </@s.iterator>                         <p class="czx_img"><a href="http://www.lvmama.com/info/chinalife/2012-0813-123541-8.html"><img src="http://pic.lvmama.com/img/zt/xiamen/meishi2.jpg" width="300" height="130" /></a><a href="http://www.lvmama.com/info/chinalife/2012-0813-123541-10.html"><img src="http://pic.lvmama.com/img/zt/xiamen/meishi3.jpg" width="140" height="130" /></a><a href="http://www.lvmama.com/info/chinalife/2012-0813-123541.html"><img src="http://pic.lvmama.com/img/zt/xiamen/meishi4.jpg" width="140" height="130" /></a></p>
                </dd>
            </dl>
            </div>
            <br/>
<h3><em>时尚厦门新玩法</em><span>游乐园、泡温泉，厦门还能更精彩。</span></h3> 
<div class="czx">
<dl>
            	<dt class="fr"><a href="http://www.lvmama.com/guide/2013/1015-175539.html"><img src="http://pic.lvmama.com/img/zt/xiamen/fangte4.jpg" width="250" height="274" alt="厦门方特梦幻王国" /></a></dt>
                <dd class="wid_630 fl">
                		<h4 class="title czx_h4"><a href="http://www.lvmama.com/guide/2013/1015-175539.html">1、厦门方特梦幻王国</a><i>The first recommendation</i></h4>
                        <p><strong>推荐理由：</strong>爱玩的朋友一定知道"方特"的鼎鼎大名，没错，那就是"方特欢乐世界"游乐园！"方特"在全国有11个大型游乐园，其中一个就在厦门，其好玩程度丝毫不亚于欢乐谷……</p>
 <@s.iterator value="map.get('${station}_cd_xinwanfa1')"  var="var" status="st">
	                        <p class="czx_link">
	                        <a href="${url?if_exists}">
	                                                                                                产品推荐：${title?if_exists}</a></p> 
                        </@s.iterator>                         <p class="czx_img"><a href="http://www.lvmama.com/guide/2013/1015-175539.html"><img src="http://pic.lvmama.com/img/zt/xiamen/fangte1.jpg" width="300" height="130" /></a><a href="http://www.lvmama.com/guide/2013/1015-175539.html"><img src="http://pic.lvmama.com/img/zt/xiamen/fangte2.jpg" width="140" height="130" /></a><a href="http://www.lvmama.com/guide/2013/1015-175539.html"><img src="http://pic.lvmama.com/img/zt/xiamen/fangte3.jpg" width="140" height="130" /></a></p>
                </dd>
            </dl>
        </div>
<br/>
<div class="city_main">
            		<div class="info fl">
                    		<p><a href="http://www.lvmama.com/guide/2013/1017-175563.html" class="info_img mar_img"><img src="http://pic.lvmama.com/img/zt/xiamen/riyuegu1.jpg" height="210" width="200" alt="厦门日月谷温泉" /></a><a href="http://www.lvmama.com/guide/2013/1017-175563.html" class="info_img"><img src="http://pic.lvmama.com/img/zt/xiamen/riyuegu2.jpg" height="210" width="200" alt="" /></a></p>
                            <dl>
                            	<dt><h4 class="title"><a href="http://www.lvmama.com/guide/2013/1017-175563.html">2、厦门日月谷温泉</a><i>The second recommended</i></h4></dt>
                                <dd><strong>推荐理由：</strong>厦门日月谷温泉渡假村座落于厦门天竺山麓，这里是闽南旅游圈中心地带，山峦环抱、景色绮丽，连时光也在此乍然驻足，等待您的，是全身心的放松与平静......</a></dd>
                             	<@s.iterator value="map.get('${station}_cd_xinwanfa2')"  var="var" status="st">
                               	 <dd><a href="${url?if_exists}">推荐产品：${title?if_exists}</a></dd>
                                 </@s.iterator>  
                            </dl>
                    </div><!--info end-->
                    <div class="info info2">
                    		<p><a href="http://www.lvmama.com/guide/2013/1017-175562.html" class="info_img"><img src="http://pic.lvmama.com/img/zt/xiamen/cuifeng1.jpg" height="210" width="200" alt="厦门翠丰温泉" /></a><a href="http://www.lvmama.com/guide/2013/1017-175562.html" class="info2_img mar_img"><img src="http://pic.lvmama.com/img/zt/xiamen/cuifeng2.jpg" height="100" width="210" alt="" /></a><a href="http://www.lvmama.com/guide/2013/1017-175562.html" class="info2_img"><img src="http://pic.lvmama.com/img/zt/xiamen/cuifeng3.jpg" height="100" width="210" alt="" /></a></p>
                            <dl>
                            	<dt><h4 class="title"><a href="http://www.lvmama.com/guide/2013/1017-175562.html">3、厦门翠丰温泉</a><i>The third recommended</i></h4></dt>
                                <dd><strong>推荐理由：</strong>厦门翠丰温泉度假酒店位于闽南金三角枢纽地带、景色优美的同安汀溪水库风景区，是新加坡诚毅度假酒店集团首家五星级温泉度假酒店……</a></dd>
                               	<@s.iterator value="map.get('${station}_cd_xinwanfa3')"  var="var" status="st">
                               	 <dd><a href="${url?if_exists}">推荐产品：${title?if_exists}</a></dd>
                                 </@s.iterator>
                            </dl>
              </div><!--info2 end-->
              <div class="clear">
                    <!--info3 end-->
                     <!--info4 end-->
                      <!--info5 end-->
              </div><!--清除浮动结束-->
        </div>


</br>
<!--微博-->
   <iframe width="900" style=" margin-left:40px;" height="500"  frameborder="0" scrolling="no" src="http://widget.weibo.com/livestream/listlive.php?language=zh_cn&width=900&height=500&uid=2850625590&skin=1&refer=1&appkey=&pic=0&titlebar=1&border=1&publish=1&atalk=1&recomm=0&at=0&atopic=%E9%BC%93%E6%B5%AA%E5%B1%BF%E6%94%BB%E7%95%A5&ptopic=%E9%BC%93%E6%B5%AA%E5%B1%BF%E6%94%BB%E7%95%A5&dpc=1"></iframe>
   
<!--微博结束-->
     <div class="review">
           <h3><em>往期回顾</em><span>寻找属于你的景点！</span><a href="http://www.lvmama.com/zt/s/">更多专题&gt;&gt;</a></h3>    
           <p><a href="http://www.lvmama.com/guide/zt/jiuzhou/"><img src="http://pic.lvmama.com/img/zt/xiamen/jiuzhou_165x200.jpg" height="200" width="165" alt="" /></a><a href="http://www.lvmama.com/guide/zt/shaoxing/"><img src="http://pic.lvmama.com/img/zt/xiamen/shaoxing_165x200.jpg" height="200" width="165" alt="" /></a><a href="http://www.lvmama.com/guide/zt/yuantouzhu/"><img src="http://pic.lvmama.com/img/zt/xiamen/yuantouzhu_165x200.jpg" height="200" width="165" alt="" /></a><a href="http://www.lvmama.com/guide/zt/chanxiu/"><img src="http://pic.lvmama.com/img/zt/xiamen/chanxiu_165x200.jpg" height="200" width="165" alt="" /></a><a href="http://www.lvmama.com/guide/zt/dongou/"><img src="http://pic.lvmama.com/img/zt/xiamen/dongou_165x200.jpg" height="200" width="165" alt="" /></a></p>        
    </div><!--review end-->  
	<div class="img_flink">
	


	</div><!--img_flink end-->
	
    <div class="links">
    	<div class="links_lt">
        	<p class="link_tit">旅游攻略<font>Travel recommendation</font></p>
    		<ul>
<li><a href="http://www.lvmama.com/guide/place/huzhou_huzhoushianjixian/" target="_blank">安吉旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhongguo_aomen/" target="_blank">澳门旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/yindunixiya_balidao/" target="_blank">巴厘岛旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/guangxi_beihai/" target="_blank">北海旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhongguo_beijing/" target="_blank">北京旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/jilinsheng_changbaishan/" target="_blank">长白山旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/hunan_changsha/" target="_blank">长沙旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/feilvbin_changtandao/" target="_blank">长滩岛旅游攻略</a></li>

            </ul>
            	<ul>
<li><a href="http://www.lvmama.com/guide/place/sichuan_chengdu/" target="_blank">成都旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/yunnan_dalibaizu/" target="_blank">大理旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/liaoning_dalian/" target="_blank">大连旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/gansu_dunhuangshi/" target="_blank">敦煌旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/sichuan_emeishan/" target="_blank">峨眉山旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhongguo_fujian/" target="_blank">福建旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/fujian_fujianfuzhou/" target="_blank">福州旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/gulangyu/" target="_blank">鼓浪屿旅游攻略</a></li>

            </ul>
            	<ul>
<li><a href="http://www.lvmama.com/guide/place/guangdong_guangzhou/" target="_blank">广州旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhongguo_guizhou/" target="_blank">贵州旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/guangxi_guilin/" target="_blank">桂林旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/heilongjiang_haerbin/" target="_blank">哈尔滨旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhongguo_hainan/" target="_blank">海南旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/yazhou_hanguo/" target="_blank">韩国旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/zhejiang_hangzhou/" target="_blank">杭州旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/shannxi_weinan/" target="_blank">华山旅游攻略</a></li>

            </ul>
            	<ul>
<li><a href="http://www.lvmama.com/guide/place/anhui_huangshan/" target="_blank">黄山旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/shandong_jinan/" target="_blank">济南旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/jiuhuashan/" target="_blank">九华山旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/sichuan_scjiuzhaigou/" target="_blank">九寨沟旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/yunnan_kunming/" target="_blank">昆明旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank">拉萨旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/yunnan_lijiang/" target="_blank">丽江旅游攻略</a></li>
<li><a href="http://www.lvmama.com/guide/place/jiangxi_jiujiang/" target="_blank">庐山旅游攻略</a></li>

            </ul>
         </div>
         <div class="links_rt">
         	<p class="link_tit">特别策划<font>Special planning</font></p>
            <p class="mar_p">责任编辑：李兆斌</p>
			<p>设计：张洁</p> 
            <p>注：本专题中图片以原创为主，部分来自网络，仅供分享使用，侵删。</p>
			<p class="mar_p">版权声明：本期设计、图文归属驴妈妈旅游攻略频道及相应作者；</p>
			<p>版权所有，严禁转载，违者必究。</p>
			<p class="mar_p">驴妈妈  2013年10月</p>
         </div>
    </div><!--links end-->
    </div>
    <!--公用底部-->
       <div class="footer">   		
          <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
		  <p>厦门著名景点_厦门哪里好玩_厦门哪些地方好玩-驴妈妈旅游网</p>  
	   </div>
<!--公用底部end--> 
</div>
<!--wrap end-->
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jiathis_r.js?move=0&amp;btn=r4.gif" charset="utf-8"></script>
<!-- JiaThis Button END -->
<div id="re_top"><img src="http://pic.lvmama.com/img/zt/xiamen/re_top.jpg" height="50" width="52" alt="返回顶部" /></div>
<script type="text/javascript">
		var slidetime,_index=0,len=$("#slide  li").length;
		var width=$("#slide  li").width();
	
		$(function(){
				$(".img_num li").hover(function(){
							 var index=$(this).index();
							 _index=index;
							 showImg(index);
				});
				$(".slide_contain").hover(function(){
							clearInterval(slidetime);
				},function(){
							slidetime=setInterval(function(){
							showImg(_index)
							_index++;
							if(_index==len) {_index=0;}
				  	     }, 5000);
				}).trigger("mouseleave");
				$(window).scroll(function(){
					    var _scroll=$(document).scrollTop();
					/*	if (!window.XMLHttpRequest){
							$("#re_top").css({"top":_scroll+300});	
						}*/
						if(_scroll>0){
							$("#re_top").show();
						//	alert("scroll:"+_scroll);
						}else{
							$("#re_top").hide();
						}
				});
				$("#re_top").click(function(){
							$(document).scrollTop(0);
							
						});
					
		});
		function showImg(index){
					$("#img_num li").removeClass("cur").eq(index).addClass("cur");
					$("#slide").stop().animate({left:-width*index},400)
		}
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/xiamen/index.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script> 

</body>
</html>
