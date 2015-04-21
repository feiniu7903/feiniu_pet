<!DOCTYPE html>
<html> 
<head>
<script>function utmx_section(){}function utmx(){}(function(){var
k='11078313-14',d=document,l=d.location,c=d.cookie;
if(l.search.indexOf('utm_expid='+k)>0)return;
function f(n){if(c){var i=c.indexOf(n+'=');if(i>-1){var j=c.
indexOf(';',i);return escape(c.substring(i+n.length+1,j<0?c.
length:j))}}}var x=f('__utmx'),xx=f('__utmxx'),h=l.hash;d.write(
'<sc'+'ript src="'+'http'+(l.protocol=='https:'?'s://ssl':
'://www')+'.google-analytics.com/ga_exp.js?'+'utmxkey='+k+
'&utmx='+(x?x:'')+'&utmxx='+(xx?xx:'')+'&utmxtime='+new Date().
valueOf()+(h?'&utmxhash='+escape(h.substr(1)):'')+
'" type="text/javascript" charset="utf-8"><\/sc'+'ript>')})();
</script><script>utmx('url','A/B');</script>

<meta charset="utf-8" >
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/">
<link rel="dns-prefetch" href="//s1.lvjs.com.cn">
<link rel="dns-prefetch" href="//s2.lvjs.com.cn">
<link rel="dns-prefetch" href="//s3.lvjs.com.cn">
<title>${comSeoIndexPage.seoTitle}</title>
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" > 
<meta name="keywords" content="${comSeoIndexPage.seoKeyword}" > 
<meta name="description" content="${comSeoIndexPage.seoDescription}" >
<meta property="qc:admins" content="276353266764651516375" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css,/styles/v3/index.css" >

<#include "/common/coremetricsHead.ftl">	
</head>
    <body class="home" id="xhindex"> 
        <input type="hidden" id="fromPlaceName" value="${fromPlaceName}"/>
        <@s.set var="pageMark" value="'homepage'" />
        <!-- 公共头部开始  -->
<!-- topbar\\ -->
<div class="lv-topbar">
    <div class="tapbar-inner wrap">
        <ul class="top-link">
            <li class="dropdown"><a class="lvlink" href="http://www.lvmama.com/myspace/index.do" rel="nofollow">我的驴妈妈 <i class="icon-barr"></i></a>
                <div class="top-sub">
                    <a href="http://www.lvmama.com/myspace/order.do" rel="nofollow">我的订单</a>
                    <a href="http://www.lvmama.com/myspace/account/points.do" rel="nofollow">我的积分</a>
                    <a href="http://www.lvmama.com/myspace/account/coupon.do" rel="nofollow">我的优惠券</a>
                    <a href="http://www.lvmama.com/login/loginBindCard.do" rel="nofollow" target="_blank">我的会员卡</a>
                    <a href="http://www.lvmama.com/stored/goStoredSearch.do" rel="nofollow" target="_blank">我的礼品卡</a>
                </div>
            </li>
            <!--<li><a class="lvlink" href="http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do" rel="nofollow">申请会员卡</a></li>-->
            <li><a class="lvlink" href="http://www.lvmama.com/points" rel="nofollow">积分商城</a></li>
            <li><a class="lvlink" href="http://www.lvmama.com/tuangouyuyue" rel="nofollow"><i class="icon-quan"></i>团购预约</a></li>
            <li><a class="lvlink" href="http://www.lvmama.com/public/help" rel="nofollow">帮助</a></li>
            <li class="dropdown join-weixin"><a class="lvlink" href="javascript:void(0);" rel="nofollow"><i class="icon-weixin"></i>+微信</a>
                <div class="top-sub"><span class="wechatcode"></span></div>
            </li>
            
            <li class="sitemap"><a class="lvlink" target="_self" href="javascript:bookmark()" rel="nofollow">收藏本站</a></li>
            <li><a class="lvlink" onclick="_gaq.push(['_trackEvent', 'weibo', 'click', 'weiboshouye', 5]);" href="http://e.weibo.com/lvmamas" rel="nofollow"><i class="icon-weibo"></i>+微博</a></li>
            <li class="sitemap"><a href="http://www.lvmama.com/public/site_map">网站地图</a></li>
            <li class="site-mobile"><a class="lvlink" href="http://shouji.lvmama.com/" rel="nofollow" target="_blank"><i class="icon-mobile"></i>手机版</a></li>
        </ul>
        <span id="J_login" class="lv-login"></span>
    </div>
</div> <!-- //.topbar -->

<div class="lv-header">
    <div class="header-inner wrap">
        <span id="hotline" class="lv-hotline">1010-6060</span>
        <a class="lv-logoAB" href="http://www.lvmama.com/lvmama">旅游网</a>
        <div id="adPro" data-type="ad" class="lv-adPro"></div>
    </div>
</div> <!-- //.lv-header -->

<div id="pnav" class="pnav clearfix">
    <div class="wrap">
        <ul class="pnav-main">
            <li id="home"><a href="http://www.lvmama.com" rel="nofollow">首页</a></li>
            <li id="ticket"><a href="http://www.lvmama.com/ticket">景点门票</a></li>
            <li id="freetour"><a href="http://www.lvmama.com/freetour">周边游</a></li>
            <li id="destroute"><a href="http://www.lvmama.com/destroute">国内游</a></li>
            <li id="abroad"><a href="http://www.lvmama.com/abroad">出境游</a></li>
            <li id="liner"><a href="http://www.lvmama.com/youlun/">邮轮</a><i class="icon-new"></i></li>
            <li id="hotel">
            	<a href="http://hotels.lvmama.com/hotel" rel="nofollow">酒店</a><i class="icon-pnavHotel"></i>
            </li>
            <li id="holiday"><a href="http://www.lvmama.com/search/hotel.html">度假酒店</a></li>
            <!--<li id="globalhotel"><a href="http://hotel.lvmama.com" rel="nofollow">海外酒店</a></li>-->
            <li id="train"><a href="http://www.lvmama.com/train" rel="nofollow">火车票</a></li>
            <li id="flight"><a href="http://flight.lvmama.com" rel="nofollow">国际机票</a></li>
            <li id="custom"><a href="http://www.lvmama.com/company" rel="nofollow">定制游</a></li>
            <li id="tuangou"><a href="http://www.lvmama.com/tuangou">特卖会</a><i class="icon-tg"></i></li>
        </ul>
        <ul class="pnav-small">
            <li id="lvcomment"><a href="http://www.lvmama.com/comment" rel="nofollow">点评</a></li>
            <li id="lvguide"><a href="http://www.lvmama.com/guide">攻略</a></li>
            <li id="lvplace"><a target="_blank" href="http://www.lvmama.com/zt/s/"><span>专题</span></a></li>
            <li id="lvinfo"><a href="http://www.lvmama.com/info">资讯</a></li>
            <li id="lvbbs"><a href="http://bbs.lvmama.com" rel="nofollow">社区</a></li>
        </ul>
    </div>
</div> <!-- //div.pnav -->
<!-- 公共头部结束  -->

        
        <div data-city-name="${fromPlaceName}" data-city-id="${fromPlaceId}" data-source="home" class="search-box wrap">
            <div class="incity switch-city">
                <p class="city">
                    <a class="switch-info">切换城市</a>
                    <i class="icon-local"></i>
                    <span>您现在
                    <b>${stationName?if_exists}</b>
                    </span>
                </p>
                <div class="citylist LISTFIRST">
                     <#include "/WEB-INF/pages/www/channel/stationDiv.ftl" />
                </div>
            </div>
            
            <div class="from-city switch-city">
                <p class="city"><span class="css-arrow"><i></i></span><span id="cityId"><@s.if test="stationName!=fromPlaceName">请选择出发点</@s.if><@s.else>${fromPlaceName}</@s.else></span></p> 
                <div class="citylist LISTSECOND">
                    <h5>热门出发城市</h5> 
                    <dl>
                        <dd>
                            <a data-name="北京" data-id="1" data-code="BJ" href="#">北京</a>
                            <a data-name="上海" data-id="79" data-code="SH" href="#">上海</a>
                            <a data-name="南京" data-id="82" data-code="NJ" href="#">南京</a>
                            <a data-name="杭州" data-id="100" data-code="HZ" href="#">杭州</a>
                            <a data-name="成都" data-id="279" data-code="CD" href="#">成都</a>
                            <a data-name="广州" data-id="229" data-code="GZ" href="#">广州</a>
                            <a data-name="三亚" data-id="272" data-code="SY" href="#">三亚</a>
                        </dd>
                    </dl>
                    <div class="pa_line"></div>
                        <h5>其他出发城市</h5>
                        <dl>
                            <dt>东北</dt>
                            <dd>
                                <a data-name="长春" data-id="57"   href="#">长春</a>
                                <a data-name="沈阳" data-id="42"   href="#">沈阳</a>
                                <a data-name="哈尔滨" data-id="67"  href="#">哈尔滨</a>
                                <a data-name="大连" data-id="43"  href="#">大连</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>华北</dt>
                            <dd>
                                <a data-name="北京" data-id="1" href="#">北京</a>
                                <a data-name="天津" data-id="2" href="#">天津</a>
                                <a data-name="石家庄" data-id="4" href="#">石家庄</a>
                                <a data-name="太原" data-id="16" href="#">太原</a>
                                <a data-name="呼和浩特" data-id="28" href="#">呼和浩特</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>华东</dt>
                            <dd>
                                <a data-name="上海" data-id="79" href="#">上海</a>
                                <a data-name="南京" data-id="82" href="#">南京</a>
                                <a data-name="杭州" data-id="100" href="#">杭州</a>
                                <a data-name="合肥" data-id="119" href="#">合肥</a>
                                <a data-name="厦门" data-id="137" href="#">厦门</a>
                                <a data-name="济南" data-id="160" href="#">济南</a>
                                <a data-name="南昌" data-id="146" href="#">南昌</a>
                                <a data-name="苏州" data-id="87" href="#">苏州</a>
                                <a data-name="无锡" data-id="83" href="#">无锡</a>
                                <a data-name="宁波" data-id="104" href="#">宁波</a>
                                <a data-name="常州" data-id="86" href="#">常州</a>
                                <a data-name="嘉兴" data-id="108" href="#">嘉兴</a>
                                <a data-name="南通" data-id="88" href="#">南通</a>
                                <a data-name="扬州" data-id="92" href="#">扬州</a>
                                <a data-name="镇江" data-id="93" href="#">镇江</a>
                                <a data-name="绍兴" data-id="111" href="#">绍兴</a>
                                <a data-name="温州" data-id="107" href="#">温州</a>
                                <a data-name="金华" data-id="112" href="#">金华</a>
                                <a data-name="台州" data-id="115" href="#">台州</a>
                                <a data-name="盐城" data-id="91" href="#">盐城</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>中南</dt>
                            <dd>
                                <a data-name="郑州" data-id="180" href="#">郑州</a>
                                <a data-name="武汉" data-id="199" href="#">武汉</a>
                                <a data-name="长沙" data-id="213" href="#">长沙</a>
                                <a data-name="广州" data-id="229" href="#">广州</a>
                                <a data-name="深圳" data-id="231" href="#">深圳</a>
                                <a data-name="南宁" data-id="252" href="#">南宁</a>
                                <a data-name="海口" data-id="271" href="#">海口</a>
                                <a data-name="香港" data-id="398" href="#">香港</a>
                                <a data-name="澳门" data-id="400" href="#">澳门</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>西南</dt>
                            <dd>
                                <a data-name="重庆" data-id="277" href="#">重庆</a>
                                <a data-name="成都" data-id="279" href="#">成都</a>
                                <a data-name="贵阳" data-id="301" href="#">贵阳</a>
                                <a data-name="拉萨" data-id="332" href="#">拉萨</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>西北</dt>
                            <dd>
                                <a data-name="西安" data-id="340" href="#">西安</a>
                                <a data-name="银川" data-id="376" href="#">银川</a>
                                <a data-name="西宁" data-id="367" href="#">西宁</a>
                                <a data-name="乌鲁木齐" data-id="382" href="#">乌鲁木齐</a>
                            </dd>
                        </dl>
                </div>
            </div>
            <form class="form-search">
                <input type="text" x-webkit-speech placeholder="世界杯"  class="input-search"><button type="button" class="btn-search xicon">搜索</button>
            </form>
            <span class="hot-travel">
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_topRecommend_around')" status="st">
                 <a target="_blank"   href="${url?if_exists}" hidefocus="false">${title?if_exists}</a>
                </@s.iterator>
            </span>
        </div>
        <!-- wrap\\ 1 -->
        <div class="wrap wrap-quick">
            <!-- quick-menu -->
            <div class="quick-menu">
                <h3>热门旅游分类</h3>
                <div class="quick-list">
                    <div class="menu-item">
                        <div class="menu-itembox">
                            <i class="xicon icon-ticket"></i><span class="icon-rarr"></span>
                            <h4>
                                <a target="_blank" rel="nofollow" href="http://www.lvmama.com/ticket?losc=shouye">景点门票</a>
                            </h4>
                            <p class="item">
                                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_ticket')" status="status">
                                 <a target="_blank" rel="nofollow" href="${url?if_exists}">${title?if_exists}</a>
                                </@s.iterator>
                            </p>
                        </div>
                        <div class="quick-menu-drop">
                            <div class="drop-item">
                               <h5>热门景点</h5>
                               <div class="item-hor">
                                  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ticket_dest_title')" status="status">
                                   <a target="_blank"  href="${url?if_exists}">${title?if_exists}</a>
                                   </@s.iterator>
                                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_1')" status="st">
                                    <@s.if test="bakWord2==1">
                                          <a rel="nofollow" target="_blank"  class="link-more" href="${url?if_exists}">${title?if_exists}</a>
                                    </@s.if>
                                 </@s.iterator>
                                   
                               </div>
                                <div class="list">
                                   <h5>热销门票</h5>
                                   <div class="item-ver">
                                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ticket_freeProduct_title')" status="st">
                                       <a target="_blank"  href="${url?if_exists}"  title="${title?if_exists}"><i class="c${st.index+1}"></i>
                                       ${title?if_exists}</a>
                                      </@s.iterator>
                                       
                                     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_1')" status="st">
                                        <@s.if test="bakWord2==2">
                                             <a rel="nofollow" target="_blank"  class="link-more fr" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                     </@s.iterator>
                                      
                                   </div>
                                   
                                </div>
                            </div>
                            <div class="drop-list">
                                <p>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_ticket_right_title')" status="status">
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}"><img data-imgsrc="${imgUrl?if_exists}" width="300" height="200" title="${title?if_exists}" /></a>
                                    <a target="_blank"  class="limit" href="${url?if_exists}" title="${remark?if_exists}">
                                        <@s.if test="remark!=null && remark.length()>65">
                                        <@s.property value="remark.substring(0,65)" escape="false"/>...
                                        </@s.if><@s.else>${remark?if_exists}
                                        </@s.else></a>
                                    </@s.iterator>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="menu-item">
                        <div class="menu-itembox">
                            <i class="xicon icon-around"></i><span class="icon-rarr"></span>
                            <h4>                    
                                <a rel="nofollow" target="_blank"  href="http://www.lvmama.com/freetour?losc=shouye">周边自由行</a>
                                </h4>
                            <p class="item">
                                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_around')" status="status">
                                 <a rel="nofollow" target="_blank"   href="${url?if_exists}">${title?if_exists}</a>
                                </@s.iterator>
                            </p>
                        </div>
                        <div class="quick-menu-drop">
                            <div class="drop-item">
                               <h5>热门目的地</h5>
                               <div class="item-hor">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_around_dest_title')" status="status">
                                       <a target="_blank"  href="${url?if_exists}"><i class="c1"></i>
                                       ${title?if_exists}</a>
                                     </@s.iterator>
                                     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_2')" status="status">
                                        <@s.if test="bakWord2==1">
                                              <a rel="nofollow" target="_blank"  class="link-more" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                     </@s.iterator>
                               </div>
                                <div class="list">
                                   <h5>热销产品</h5>
                                   <div class="item-ver">
                                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_around_freeProduct_title')" status="st">
                                       <a target="_blank"  href="${url?if_exists}"  title="${title?if_exists}"><i class="c${st.index+1}"></i>
                                       ${title?if_exists}</a>
                                       </@s.iterator>
                                       
                                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_2')" status="status">
                                        <@s.if test="bakWord2==2">
                                              <a rel="nofollow" target="_blank"  class="link-more fr" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                                   </div>
                                   
                                </div>
                            </div>
                            <div class="drop-list">
                                <p>
                                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_around_right_title')" status="status">
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}"><img data-imgsrc="${imgUrl?if_exists}" width="300" height="200" title="${title?if_exists}" /></a>
                                    <a target="_blank"  class="limit" href="${url?if_exists}" title="${remark?if_exists}">
                                        <@s.if test="remark!=null && remark.length()>65">
                                        <@s.property value="remark.substring(0,65)" escape="false"/>...
                                        </@s.if><@s.else>${remark?if_exists}
                                        </@s.else></a>
                                    </@s.iterator>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="menu-item">
                        <div class="menu-itembox">
                            <i class="xicon icon-destroute"></i><span class="icon-rarr"></span>
                            <h4>    
                              <a rel="nofollow" target="_blank"  href="http://www.lvmama.com/destroute?losc=shouye">国内游</a>
                               </h4>
                                <p class="item">
                                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_destroute')" status="status">
                                  <a rel="nofollow" target="_blank"   href="${url?if_exists}">${title?if_exists}</a>
                                </@s.iterator>
                            </p>
                        </div>
                        <div class="quick-menu-drop">
                            <div class="drop-item">
                               <h5>热门目的地</h5>
                               <div class="item-hor">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_destroute_dest_title')" status="status">
                                       <a target="_blank"  href="${url?if_exists}"><i class="c1"></i>
                                       ${title?if_exists}</a>
                                     </@s.iterator>
                                     
                                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_3')" status="status">
                                        <@s.if test="bakWord2==1">
                                           <a rel="nofollow" target="_blank"  class="link-more" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                                  
                               </div>
                                <div class="list">
                                   <h5>热销产品</h5>
                                   <div class="item-ver">
                                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_destroute_freeProduct_title')" status="st">
                                       <a target="_blank"  href="${url?if_exists}"  title="${title?if_exists}"><i class="c${st.index+1}"></i>
                                       ${title?if_exists}</a>
                                       </@s.iterator>
                                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_3')" status="status">
                                        <@s.if test="bakWord2==2">
                                           <a rel="nofollow" target="_blank"  class="link-more fr" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                                       
                                   </div>
                                </div>
                            </div>
                            <div class="drop-list">
                                <p>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_destroute_right_title')" status="status">
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}"><img data-imgsrc="${imgUrl?if_exists}" width="300" height="200" title="${title?if_exists}" /></a>
                                    <a target="_blank"  class="limit" href="${url?if_exists}" title="${remark?if_exists}">
                                    <@s.if test="remark!=null && remark.length()>65">
                                    <@s.property value="remark.substring(0,65)" escape="false"/>...
                                    </@s.if><@s.else>${remark?if_exists}
                                    </@s.else></a>
                                    </@s.iterator>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="menu-item">
                        <div class="menu-itembox">
                            <i class="xicon icon-abroad"></i><span class="icon-rarr"></span>
                            <h4>
                               <a rel="nofollow" target="_blank"  href="http://www.lvmama.com/abroad?losc=shouye">出境游</a>
                            </h4>
                            <p class="item">
                             <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_abroad')" status="status">
                                  <a rel="nofollow" target="_blank"   href="${url?if_exists}">${title?if_exists}</a>
                             </@s.iterator>
                            </p>
                        </div>
                        <div class="quick-menu-drop">
                            <div class="drop-item">
                               <h5>热门目的地</h5>
                               <div class="item-hor">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_abroad_dest_title')" status="status">
                                       <a target="_blank"  href="${url?if_exists}"><i class="c1"></i>
                                       ${title?if_exists}</a>
                                     </@s.iterator>
                                     
                                     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_4')" status="status">
                                        <@s.if test="bakWord2==1">
                                           <a rel="nofollow" target="_blank"  class="link-more"href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                               </div>
                                <div class="list">
                                   <h5>热销产品</h5>
                                   <div class="item-ver">
                                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_abroad_freeProduct_title')" status="st">
                                       <a target="_blank"  href="${url?if_exists}"  title="${title?if_exists}"><i class="c${st.index+1}"></i>
                                       ${title?if_exists}</a>
                                       </@s.iterator>
                                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_4')" status="status">
                                        <@s.if test="bakWord2==2">
                                             <a rel="nofollow" target="_blank"  class="link-more fr" href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                                   </div>
                                </div>
                            </div>
                            <div class="drop-list">
                                <p>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_abroad_right_title')" status="status">
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}"><img data-imgsrc="${imgUrl?if_exists}" width="300" height="200" title="${title?if_exists}" /></a>
                                    <a target="_blank"  class="limit" href="${url?if_exists}" title="${remark?if_exists}">
                                        <@s.if test="remark!=null && remark.length()>65">
                                        <@s.property value="remark.substring(0,65)" escape="false"/>...
                                        </@s.if><@s.else>${remark?if_exists}
                                        </@s.else></a>
                                    </@s.iterator>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="menu-item menu-last">
                        <div class="menu-itembox">
                            <i class="xicon icon-tuan"></i><span class="icon-rarr"></span>
                            <h4> 
                               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_5')" status="status">
                                <@s.if test="bakWord2==1">
                                      <a rel="nofollow" target="_blank"  href="${url?if_exists}?losc=shouye">${title?if_exists}</a>
                                </@s.if>
                                </@s.iterator>
                            </h4>
                            <p class="item">
                              <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_group')" status="status">
                                  <a rel="nofollow" target="_blank"   href="${url?if_exists}">${title?if_exists}</a>
                             </@s.iterator>
                            </p>
                        </div>
                        <div class="quick-menu-drop">
                            <div class="drop-item">
                               <h5>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_5')" status="status">
                                    <@s.if test="bakWord2==2">
                                         ${title?if_exists}
                                    </@s.if>
                                    </@s.iterator>
                               </h5>
                               <div class="item-hor">
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_group_dest_title')" status="status">
                                       <a target="_blank"  href="${url?if_exists}"><i class="c1"></i>
                                       ${title?if_exists}</a>
                                     </@s.iterator>
                                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_5')" status="status">
                                    <@s.if test="bakWord2==3">
                                   <a rel="nofollow" target="_blank"  class="link-more" href="${url?if_exists}">${title?if_exists}</a>
                                    </@s.if>
                                  </@s.iterator>
                               </div>
                                <div class="list">
                                   <h5> <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_5')" status="status">
                                        <@s.if test="bakWord2==4">
                                              ${title?if_exists}
                                        </@s.if>
                                        </@s.iterator>
                                   </h5>
                                   <div class="item-ver">
                                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_group_freeProduct_title')" status="st">
                                   <a target="_blank"  href="${url?if_exists}"  title="${title?if_exists}"><i class="c${st.index+1}"></i>
                                   ${title?if_exists}</a>
                                       </@s.iterator>
                                       
                                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_name_5')" status="status">
                                        <@s.if test="bakWord2==5">
                                            <a rel="nofollow" target="_blank"  class="link-more fr"  href="${url?if_exists}">${title?if_exists}</a>
                                        </@s.if>
                                      </@s.iterator>
                                   </div>
                                </div>
                            </div>
                            <div class="drop-list">
                                <p>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_group_right_title')" status="status">
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}"><img data-imgsrc="${imgUrl?if_exists}" width="300" height="200" title="${title?if_exists}" /></a>
                                       <a target="_blank"  class="limit" href="${url?if_exists}" title="${remark?if_exists}">
                                        <@s.if test="remark!=null && remark.length()>65">
                                        <@s.property value="remark.substring(0,65)" escape="false"/>...
                                        </@s.if><@s.else>${remark?if_exists}
                                        </@s.else></a>
                                    </@s.iterator>
                                </p>
                            </div>
                        </div>
                    </div><!--驴妈妈自发团 end -->
                </div>
            </div> <!-- //.quick-menu -->
                    
                <div id="slides" class="slide-box pageframe10">
                    <!-- 首页焦点总控 -->
                    <ul class="slide-content" id="js-slides" >
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus01&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus04&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus05&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus06&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus07&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus08&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus09&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                        <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_focus10&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#780px#260px"></li>
                    </ul>
                    <@s.if test = '"SH" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(1)"/></ul>
                        </@s.if>
                        <@s.if test = '"NJ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(2)"/></ul>
                        </@s.if>
                        <@s.if test = '"HZ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(3)"/></ul>
                        </@s.if>
                        <@s.if test = '"BJ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(4)"/></ul>
                        </@s.if>
                        <@s.if test = '"GZ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(5)"/></ul>
                        </@s.if>
                        <@s.if test = '"SZ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(6)"/></ul>
                        </@s.if>
                        <@s.if test = '"CD" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(7)"/></ul>
                        </@s.if>
                        <@s.if test = '"SY" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(68)"/></ul>
                        </@s.if> 
                        <@s.if test = '"CQ" == fromPlaceCode'>
                            <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(169)"/></ul>
                        </@s.if> 
                    <!-- 首页焦点总控 --> 
                </div> <!-- //.slide-box -->
                    
                <div class="hr_d"></div>
                    
                    <div class="col-w">
                        <div class="pro-item pro-item1">
                            <div class="pro-title">
                                <h4>限时特卖</h4>
                                <i>驴妈妈价</i>
                            </div>
                            <div class="pro-list">
                                <ul>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_XianShiTeMai')" status="status">
                                        <li><dfn>¥<i>${bakWord3?if_exists}</i>起</dfn><span>${bakWord1?if_exists}</span> <a target="_blank" title="${title?if_exists}" href="${url?if_exists}">${title?if_exists}</a></li>
                                    </@s.iterator>
                                </ul>
                            </div>
                        </div> <!-- //.pro-item -->
                        
                        <div class="pro-item pro-item2">
                            <div class="pro-title">
                                <h4>热销排行</h4>
                                <em>30天销量</em>
                            </div>
                            <div class="pro-list">
                                <ul>
                                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ReXiaoPaiHang')" status="status">
                                        <li><em>${bakWord2?if_exists}</em><span>${bakWord1?if_exists}</span> <a target="_blank" title="${title?if_exists}" href="${url?if_exists}">${title?if_exists}</a></li>
                                    </@s.iterator>
                                </ul>
                            </div>
                        </div> <!-- //.pro-item -->
                        <div class="clear"></div>
                    </div> <!-- //.col-w -->
                    
                    <div class="aside aside-index">
                        <p class="news" >
                             <@s.if test="initLastOrderData!=''">
                             ${initLastOrderData?if_exists}
                             </@s.if>
                             <@s.else>
                                   用户<span id="userName">李**</span>1分钟前预订了<a rel="nofollow" target="_blank"  id="userOrderUrl" href="http://www.lvmama.com/product/2266">上海欢乐谷</a>
                             </@s.else>
                         </p>
                        
                        <div class="side-box">
                             <a target="_blank"  rel="nofollow"  href="http://m.lvmama.com/static/zt/3.0.0/519/default/hotfocusPc.html"><img src="http://pic.lvmama.com/img/v3/client.jpg" width="220" height="60" alt="" /></a>
                        </div>
                        
                        <div class="news-list">
                            <ul class="ul">
                                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_notice')" status="st">
                                    <li><i class="icon-point"></i>
                                    <a rel="nofollow" target="_blank"  href="${url?if_exists}" title="${title?if_exists}" 
                                    <@s.if test="#st.Odd">
                                      class="pink" </@s.if> >
                                    <@s.if test="title!=null && title.length()>16"> <@s.property value="title.substring(0,16)" 
                                    escape="false"/></@s.if>
                                    <@s.else>${title?if_exists}</@s.else></a>
                                    </li>
                                </@s.iterator>
                            </ul>
                        </div>
                        
                        <div class="border">
                            <div class="subscribe">
                                <h5>订阅特价、促销信息 <small><a rel="nofollow" href="http://www.lvmama.com/main/edm/edmSubscribe!showUpdateSubscribeEmail.do">详细&gt;&gt;</a></small></h5>
                                <p class="form-inline y_s_one" id="y_s_one">
                                    <input id="y_input" class="text" placeholder="请输入您的Email地址">
                                     <a  id="yjdy_btn" class="btn btn-small btn-buff">订阅</a>
                                </p>
                                <p class="y_s_two hide" id="y_s_two"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/y_loading.gif"></p>
                                <p class="y_s_three hide" id="y_s_three">订阅成功!<a href="javascript:void(0)" class="y_link_blue" id="y_link_return">返回</a></p>
                                <div id="y_error_msg" class="y_error_msg">
                                    <i class="y_jiao"></i>
                                    <label>请输入正确的Email地址</label>
                                 </div>
                            </div>
                        </div> <!-- //订阅 -->
                        
                    </div> <!-- //.aside -->
            <div class="hr_a"></div>
        </div> <!-- //.wrap 1 -->               
        
        
        <!-- wrap\\ 2 -->
        <div class="wrap" >
            <div class="col-w">
                <div class="imglink-box clearfix">
                    <ul class="hor">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_holidayImage')" status="status">
                    
                       <li <@s.if test="#status.count==2"> style="margin-right:20px;"</@s.if> ><a target="_blank"  class="text-cover" href="${url?if_exists}">
                                <img to="${imgUrl?if_exists}" width="180" height="120" alt="" />
                                <span></span>
                                <i>${title?if_exists}</i>
                            </a>
                        </li>
                    </@s.iterator>
                    </ul>
                </div> <!-- //.imglink-box -->
                    
                <div class="pro-item">
                    <div class="pro-title">
                        <h4>景点门票</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/ticket">更多&raquo;</a>
                                
                    </div>
                    <div class="pro-list">
                        <ul>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_ticket')" status="status">
                                <li><dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn><a target="_blank"   href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                </li>
                            </@s.iterator>
                        </ul>
                    </div>
                </div> <!-- //.pro-item -->
                        
                <div class="pro-item">
                    <div class="pro-title">
                        <h4>周边游</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/freetour">更多&raquo;</a>
                    </div>
                    <div class="pro-list">
                        <ul>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_around')" status="status">
                                <li><dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn><a target="_blank" title="${title?if_exists}" href="${url?if_exists}">${title?if_exists}</a></li>
                            </@s.iterator>
                        </ul>
                    </div>
                </div> <!-- //.pro-item -->
                        
                <div class="pro-item">
                    <div class="pro-title">
                        <h4>国内游</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/destroute">更多&raquo;</a>
                    </div>
                    <div class="pro-list">
                        <ul>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_destroute')" status="status">
                                <li><dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn><a target="_blank" title="${title?if_exists}" href="${url?if_exists}">${title?if_exists}</a></li>
                            </@s.iterator>
                        </ul>
                    </div>
                </div> <!-- //.pro-item -->
                        
                <div class="pro-item">
                    <div class="pro-title">
                        <h4>出境游</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/abroad">更多&raquo;</a>
                    </div>
                    <div class="pro-list">
                        <ul>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_abroad')" status="status">
                                <li><dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn><a target="_blank" title="${title?if_exists}"  href="${url?if_exists}">${title?if_exists}</a></li>
                            </@s.iterator>
                        </ul>
                    </div>
                </div> <!-- //.pro-item -->
                        
                <div class="clear"></div>
            </div> <!-- //.col-w -->            
                
            <div class="aside aside-index">
                <!-- 活动专区\\ -->
                <div class="imglist">
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_big01&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#220px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_big02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#220px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_big03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#220px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_big04&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#220px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_big05&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#220px#60px"></div>
                </div>
                <div class="imglist-small">
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_L01&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_R01&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_L02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_R02&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_L03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                    <div data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_bottum_R03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#105px#60px"></div>
                </div>
                <!-- //活动专区 -->
            </div> <!-- //.aside -->
                
        </div> <!-- //.wrap 2 -->       
                
        <div class="wrap">
            <div class="col-w"> 
                <div class="pro-item imgtext-list pro-item3">
                    <div class="pro-title">
                        <h4>精品专题</h4>
                        <ul class="tab-nav J-tabs">
                            <li class="active"><a href="javascript:void(0);">驴行周末<i></i></a></li>
                            <li><a href="javascript:void(0);">驴行风向标<i></i></a></li>
                        </ul>
                    </div>
                    <div class="tab-switch J-switch">
                        <div class="tabcon">
                            <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/zt/s/">更多&raquo;</a>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_weekend')" status="status">
                                <@s.if test="#status.isFirst()">
                                    <div class="imgtext-lr">
                                        <a target="_blank" rel="nofollow" class="img" href="${url?if_exists}"><img to="${imgUrl?if_exists}" title="${title?if_exists}" title="${title?if_exists}" width="180" height="120"></a>
                                        <div class="text">
                                            <h6 class="ellipsis"><span class="pink"></span><a target="_blank"  href="${url?if_exists}" title="${title?if_exists}"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></h6>
                                            <p><span class="pink">[导语]：</span><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
                                        </div>
                                    </div>
                                </@s.if>
                            </@s.iterator>
                            <ul class="ellipsis">
                                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_weekend')" status="status">
                                    <@s.if test="#status.isFirst()">
                                    </@s.if>
                                    <@s.else>
                                        <li><span class="pink">${bakWord1?if_exists}</span><a target="_blank"  href="${url?if_exists}" title="${title?if_exists}" target="_blank"><@s.if test="title!=null && title.length()>35"><@s.property value="title.substring(0,35)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></li>   
                                    </@s.else>
                                </@s.iterator>
                            </ul>
                        </div>
                        <div class="tabcon hide">
                            <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/zt/">更多&raquo;</a>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_wind')" status="status">
                                <@s.if test="#status.isFirst()">
                                    <div class="imgtext-lr">
                                        <a target="_blank" rel="nofollow"  class="img" href="${url?if_exists}"><img to="${imgUrl?if_exists}" alt="${title?if_exists}" title="${title?if_exists}" width="180" height="120"></a>
                                        <div class="text">
                                            <h6 class="ellipsis"><span class="pink"></span><a target="_blank"  href="${url?if_exists}" title="${title?if_exists}"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></h6>
                                            <p><span class="pink">[导语]：</span><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
                                        </div>
                                    </div>
                                </@s.if>
                            </@s.iterator>
                            <ul class="ellipsis">
                                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_wind')" status="status">
                                    <@s.if test="#status.isFirst()">
                                    </@s.if>
                                    <@s.else>
                                        <li><span class="pink">${bakWord1?if_exists}</span><a target="_blank"  href="${url?if_exists}" title="${title?if_exists}" target="_blank"><@s.if test="title!=null && title.length()>35"><@s.property value="title.substring(0,35)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></li>   
                                    </@s.else>
                                </@s.iterator>
                            </ul>
                        </div>
                    </div>
                </div> <!-- //.pro-item -->
                        
                <div class="pro-item imgtext-list pro-item4">
                    <div class="pro-title">
                        <h4>独家攻略</h4>
                        <ul class="tab-nav J-tabs">
                            <li class="active"><a href="javascript:void(0);">官方攻略<i></i></a></li>
                            <li><a href="javascript:void(0);">驴友分享<i></i></a></li>
                        </ul>
                    </div>
                    <div class="tab-switch J-switch">
                        <div class="tabcon"><!-- 官方攻略 -->
                            <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/guide/">更多&raquo;</a>
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_guide')" status="status">
                              <@s.if test="#status.isFirst()">
                                 <div class="imgtext-lr">
                                    <a target="_blank" rel="nofollow" class="img" href="${url?if_exists}"><img to="${imgUrl?if_exists}" alt="${title?if_exists}" title="${title?if_exists}" width="89" height="124"></a>
                                    <div class="text">
                                        <h6 class="ellipsis"><a target="_blank"  title="${title?if_exists}"  href="${url?if_exists}">${title?if_exists}</strong>官方攻略</a></h6>
                                        <p class="gray">版本： ${bakWord1?if_exists} 更新时间：${bakWord2?if_exists}</p>
                                        <p><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
                                    </div>
                                 </div>
                            </@s.if>
                            </@s.iterator>
                            <ul class="ellipsis">
                            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_guide')" status="status">
                                 <@s.if test="#status.isFirst()">
                                </@s.if>
                                <@s.else>
                                <li><span class="pink">${title?if_exists}官方攻略：</span><a target="_blank" title="${title?if_exists}"  href="${url?if_exists}" title="${title?if_exists}">
                                  <@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else>
                                </a></li>
                                </@s.else>
                            </@s.iterator>
                            </ul>
                        </div><!-- 官方攻略 end -->
                        <div class="tabcon hide"><!-- 驴友分享 -->
                            <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/guide/">更多&raquo;</a>
                            <ul class="lvyou">
                                  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_hotComment')" status="status">
                                    <li>
                                        <h6 class="ellipsis"><a rel="nofollow" target="_blank" title="${title?if_exists}" href="${url?if_exists}">${title?if_exists}</a></h6>
                                        <p class="gray">作者： ${bakWord1?if_exists}</p>
                                        <p class="limit"><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,55)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
                                    </li>
                                    </@s.iterator>
                            </ul>
                        </div><!-- 驴友分享 end -->
                    </div>
                </div> <!-- //.pro-item -->
        
                <div class="clear"></div>
            </div> <!-- //.col-w -->
                    
            <div class="aside aside-index">             
                <div class="side-box side-active">
                    <div class="side-title">
                        <h4>会员活动</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/shop/brandList.do">更多&raquo;</a>
                    </div>
                    <ul class="ul ellipsis">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_activity')" status="status">
                        <@s.if test="#status.isFirst()">
                        <li><a rel="nofollow" target="_blank" href="${url?if_exists}"><img class="block bc" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" 
                        alt="${title?if_exists}" to="${imgUrl?if_exists}" width="200" height="60" /></a>
                        </li>
                        </@s.if>
                        <@s.else>
                        <li><i class="icon-point"></i><span class="pink">${bakWord1?if_exists}</span><a rel="nofollow" target="_blank"  href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></li>
                        </@s.else>
                    </@s.iterator> 
                    </ul>
                </div><!-- //.side-box -->
                        
                <div class="side-box">
                    <div class="side-title">
                        <h4>品牌合作</h4>
                        <a target="_blank" rel="nofollow" class="link-more" href="http://www.lvmama.com/zt/promo/mkt/">更多&raquo;</a>
                    </div>
                     <div class="imglist-small">
                      <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_brands')" status="status">
                             <a rel="nofollow" target="_blank"  class="item" title="${title?if_exists}" href="${url?if_exists}" >
                                <img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" 
                              src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="60" width="105"  /></a>
                      </@s.iterator>
                    </div>
                </div><!-- //.side-box -->
                        
            </div> <!-- //.aside -->
                
                    
        </div> <!-- //.wrap 3 -->
        <div class="padbox wrap" data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_banner03&db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}#1000px#80px" ></div><!-- //.adbox -->
        </div>
        <script src="http://pic.lvmama.com/js/common/copyright.js"></script>
        
        <div class="hh_cooperate"> 
            <#include "/WEB-INF/pages/staticHtml/friend_link/index_footer.ftl"> 
        </div>      
        <!-- 公用js--> 
    <script type="text/javascript">
        document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
    </script>
        <script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/common/jquery.cookie.js"></script> 
        <!-- 频道公用js--> 
         <script src="http://pic.lvmama.com/min/index.php?f=/js/v3/plugins.js,/js/new_v/ob_yjdy/edm_index_subscribe.js,/js/new_v/ob_common/buyInfo.js"></script> 
          <!--<script src="/js/home/app.js"></script>-->
         <script src="http://pic.lvmama.com/min/index.php?f=/js/v3/app.js"></script>
		 
		 
		 <script>
    $(function () {
        // html代码 
        var _activebox = '<div id="indexSilde" style="width:1000px;margin:0 auto;position:relative;overflow:hidden;height:0;">'
        + '<div id="xslide1" style="position:absolute;z-index:11;top:0;display:none;">'
        + '<a target="_blank" href="http://www.lvmama.com/zt/promo/yingli/?lm_ad=site_index_top">'
        + '<img src="http://pic.lvmama.com/img/v6/coupon.jpg" width="1000" height="60">'
        + '</a></div>'
        + '<div id="xslide2" style="position:relative;top:0;z-index:10;">'
        + '<a target="_blank" href="http://www.lvmama.com/zt/promo/yingli/?lm_ad=site_index_top">'
        + '<img src="http://pic.lvmama.com/img/v6/couponbig.jpg" width="1000" height="400">'
        + '</a></div>'
        + '</div>';

        $('.lv-topbar').after(_activebox);

        // 瀑布广告 
        var _indexSilde = $('#indexSilde');
        var _xslide1 = $('#xslide1');
        function _shouqi() {
            _indexSilde.animate({ 'height': 60 }, 1000, function () {
                _xslide1.fadeIn(500).siblings().fadeOut(500, function () {
                    if ($.browser.msie && parseInt($.browser.version) <= 8) {
                        //$.fx.off = true; 
                    }
                });
            });
        };

        function _showslide() {
            _indexSilde.animate({ 'height': 400 }, 1000, function () {
                setTimeout(_shouqi, 3000);
            });
        }
        //$.fx.off = false; 
        if ($.cookie("top_slide")=="load")
            return;
        setTimeout(_showslide, 1000);
        $.cookie("top_slide", "load", { expires: 7 });
        
    })

</script>
		 
        <script>                
            $(function(){
                //QQ彩贝用户的黄色帽子 
                if (getLOSCCookie("orderFromChannel") == 'qqcb') {
                    var arrStr = document.cookie.split("; "); 
                    var temp;
                    var HeadShow = "";
                    for(var i = 0,l=arrStr.length;i < l;i++){    
                        temp=arrStr[i].split("=");    
                        if(temp[0] == "HeadShow") {
                            HeadShow = decodeURIComponent(temp[1]).replace(new RegExp("\\+","gm"), " ");;
                            break;
                        }
                    }
                    var qqcbHtml='<div class="caibei-wrap" id="caibei-wrap"><div class="wrap"><div class="caibei-info"><i class="icon-caibei"></i>' + HeadShow + '</div></div></div>';
                    $("body").prepend(qqcbHtml);
                }
            });
        </script>
        <script src="http://pic.lvmama.com/js/common/losc.js"></script>
        
        <script>
        	cmCreatePageviewTag("网站首页", "A0001", null, null);
        </script>
    </body>
    
</html>
