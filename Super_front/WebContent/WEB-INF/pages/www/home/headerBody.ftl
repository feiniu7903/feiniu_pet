<div class="lv_topbar js_box">
    <div class="topbar_box">
     	<a class="lv_collect" href="http://www.lvmama.com/" target="_blank" title="驴妈妈旅游网" rel="sidebar"><i class="lv_icon icon_collect"></i>收藏驴妈妈</a>
    	<div class="lv_city_box">
        	<div class="lv_city_up">
            	<i class="lv_icon icon_city"></i>
                <b class="lv_city" data-city-name="上海" data-city-id="79" data-source="home" fromchannel="">${stationName?if_exists}</b>
                <a class="lv_city_btn" href="javascript:;">[切换]</a>
            </div>
            <div class="lv_city_down Js_LISTFIRST" >
                <p class="my_city_t">可选城市列表</p>
                <dl class="city_down_list">
                    <dt>华北</dt>
                    <dd>
                    <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_HUABEI')" status="st">
                       <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                    </@s.iterator>
                    </dd>
                </dl>
                <dl class="city_down_list">
                      <dt>华东</dt>
                            <dd>
                              <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_HUADONG')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                              </@s.iterator>
                            </dd>
                </dl>
                <dl class="city_down_list">
                     <dt>东北</dt>
                                <dd>
                                   <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_DONGBEI')" status="st">
                                      <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                                   </@s.iterator>
                      </dd>
                </dl>
                <dl class="city_down_list">
                           <dt>中南</dt>
                            <dd>
                               <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_ZHONGNAN')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                </dl>
                <dl class="city_down_list">
                  <dt>西南</dt>
                            <dd>
                             <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_XINAN')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                             </@s.iterator>
                                  
                            </dd>
                </dl>
                 <dl class="city_down_list">
                           <dt>西北</dt>
                            <dd>
                            <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_XIBEI')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                            </@s.iterator>
                                  
                            </dd>
                 </dl>
            </div>
        </div>

        <ul class="top_link">
            <li class="border_l">
                 <a class="lv_link" href="http://www.lvmama.com/points" rel="nofollow">积分商城</a>
             </li>
            <li><a class="lv_link" href="http://www.lvmama.com/public/help" rel="nofollow">帮助</a></li>
            <li class="border_l dropdown">
            	<a class="lv_link" href="javascript:;" rel="nofollow"><i class="lv_icon icon_wx"></i>微信</a>
                <div class="top_down">
                    <img src="http://pic.lvmama.com/img/v3/wechatcode.jpg" width="220" height="90" alt="">
                </div>
            </li>
                <li><a class="lv_link" onclick="_gaq.push(['_trackEvent', 'weibo', 'click', 'weiboshouye', 5]);" href="http://e.weibo.com/lvmamas" rel="nofollow"><i class="lv_icon icon_wb"></i>+微博</a></li>

            <li>
            	<a class="lv_link"  target="_blank" href="http://shouji.lvmama.com/" rel="nofollow"><i class="lv_icon icon_mobile"></i>手机版</a>
            </li>
            <li class="dropdown link_call">
            	<a class="lv_link" href="javascript:;" rel="nofollow"><i class="lv_icon icon_phone"></i>1010-6060<i class="icon_arrow"></i></a>
                <div class="top_down">
                	<img src="http://pic.lvmama.com/img/v6/phone.png" width="174" height="39" alt="">
                	<p class="phone-num">1010-6060</p>
                </div>
            </li>
        </ul>
        <div id="J_login" class="topbar_login">
            <a href="#">登录</a>
            <a href="#" rel="nofollow">注册</a>
          </div>
	</div>
</div>

<!--顶部通栏广告，下线请注释即可-->
<a href="http://piaoliu.lvmama.com/&lm_ad=site_index_top" class="top_banner" target="_blank"><img src="http://pic.lvmama.com/img/v6/top_banner.jpg" width="1200" height="60"></a>

<div class="lv_header" >
    <div class="header_inner clearfix">
        <!--<h1 class="lv_logo"><a href="http://www.lvmama.com/">驴妈妈旅游网</a></h1>-->
		<h1 class="lv_logo"><a href="http://www.wenjuan.com/s/ZbqQFr" target="_blank">驴妈妈旅游网</a></h1>
        <a id="adPro" data-type="ad" class="header_app" href="#" target="_blank" hidefocus="false"></a>
        
        <div class="lv_my_box">
        	<a class="lv_my_btn css3_run" href="http://www.lvmama.com/myspace/index.do" rel="nofollow"><i class="lv_icon icon_my"></i>我的驴妈妈<i class="icon_arrow"></i></a>
            <div class="lv_my_list css3_run">
 				<a href="http://www.lvmama.com/myspace/order.do" rel="nofollow">我的订单</a>
				<a href="http://www.lvmama.com/myspace/account/points.do" rel="nofollow">我的积分</a>
				<a href="http://www.lvmama.com/myspace/account/coupon.do" rel="nofollow">我的优惠券</a>
				<a href="http://www.lvmama.com/login/loginBindCard.do" rel="nofollow" target="_blank">我的会员卡</a>
				<a href="http://www.lvmama.com/stored/goStoredSearch.do" rel="nofollow" target="_blank">我的礼品卡</a>
            </div>
        </div>
        
        <div class="header_search" >
        	<input type="hidden" id="fromPlaceName" value="">
        	<div class="lv_search_box">
            	<div class="search_city js_LISTSECOND">
                	<div class="btn_city js_searchbox" data-city-name="${fromPlaceName}" data-city-id="${fromPlaceId}"><b id="js_cityId">${fromPlaceName}</b><i class="lv_icon icon_jt1"></i></div>
                    <div class="lv_city_down">
                    	<p>热门出发城市</p>
                        <div class="search_city_hot">
                        	  <a data-name="北京" data-id="1" data-code="BJ" href="#">北京</a>
                            <a data-name="上海" data-id="79" data-code="SH" href="#">上海</a>
                            <a data-name="南京" data-id="82" data-code="NJ" href="#">南京</a>
                            <a data-name="杭州" data-id="100" data-code="HZ" href="#">杭州</a>
                            <a data-name="成都" data-id="279" data-code="CD" href="#">成都</a>
                            <a data-name="广州" data-id="229" data-code="GZ" href="#">广州</a>
                            <a data-name="三亚" data-id="272" data-code="SY" href="#">三亚</a>
                        </div>
                        <p>其他出发城市</p>
                        <dl class="city_down_list">
                            <dt>东北</dt>
                            <dd>
                                <a data-name="长春" data-id="57"   href="#">长春</a>
                                <a data-name="沈阳" data-id="42"   href="#">沈阳</a>
                                <a data-name="哈尔滨" data-id="67"  href="#">哈尔滨</a>
                                <a data-name="大连" data-id="43"  href="#">大连</a>
                            </dd>
                        </dl>
                            <dl class="city_down_list">
                            <dt>华北</dt>
                            <dd>
                                <a data-name="北京" data-id="1" href="#">北京</a>
                                <a data-name="天津" data-id="2" href="#">天津</a>
                                <a data-name="石家庄" data-id="4" href="#">石家庄</a>
                                <a data-name="太原" data-id="16" href="#">太原</a>
                                <a data-name="呼和浩特" data-id="28" href="#">呼和浩特</a>
                            </dd>
                        </dl>
                            <dl class="city_down_list">
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
                            <dl class="city_down_list">
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
                            <dl class="city_down_list">
                            <dt>西南</dt>
                            <dd>
                                <a data-name="重庆" data-id="277" href="#">重庆</a>
                                <a data-name="成都" data-id="279" href="#">成都</a>
                                <a data-name="贵阳" data-id="301" href="#">贵阳</a>
                                <a data-name="拉萨" data-id="332" href="#">拉萨</a>
                            </dd>
                        </dl>
                            <dl class="city_down_list">
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
                <input class="lv_search JS_lv_search"  x-webkit-speech  placeholder="邮轮特惠" type="text">
                </form>
                <span class="lv_icon btn_lv_search"></span>
            </div>
            <div class="header_hot">
                <@s.iterator value="recommendInfoMainList.get('${channelPage}_topRecommend_around')" status="st">
                 <a target="_blank"   href="${url?if_exists}" >${title?if_exists}</a>
                </@s.iterator>
            </div>
        </div>
    </div>
</div>

<div class="lv_nav_bg">
	<div class="lv_nav">
    	<ul class="lv_nav_r">
            <li id="lvguide"><a href="http://www.lvmama.com/guide">攻略</a></li>
			<li id="lvcomment"><a href="http://www.lvmama.com/comment/">点评</a></li>
            <li id="lvinfo"><a href="http://www.lvmama.com/info">资讯</a></li>
            <li id="lvbbs"><a href="http://bbs.lvmama.com" rel="nofollow">社区</a></li>
        </ul>
    	<ul class="lv_nav_l css3_run">
        	<li id="home"><a href="http://www.lvmama.com/" rel="nofollow">首页</a></li>
            <li id="ticket"><a href="http://www.lvmama.com/ticket">景点门票</a></li>
            <li id="freetour"><a href="http://www.lvmama.com/freetour">周边游</a></li>
            <li id="destroute"><a href="http://www.lvmama.com/destroute">国内游</a></li>
            <li id="abroad"><a href="http://www.lvmama.com/abroad">出境游</a></li>
            <li id="liner"><a href="http://www.lvmama.com/youlun/">邮轮</a><i class="icon_rm"></i></li>
            <li id="hotel"><a href="http://hotels.lvmama.com/hotel">酒店</a><i class="icon_hotel"></i></li>
            <li id="holiday"><a href="http://www.lvmama.com/search/hotel.html">度假酒店</a></li>
            <li id="flight"><a href="http://flight.lvmama.com" rel="nofollow">国际机票</a></li>
            <li id="tuangou"><a href="http://www.lvmama.com/tuangou" title="团购">特卖会</a><i class="icon_tg"></i></li>
			<li id="custom"><a href="http://www.lvmama.com/company" rel="nofollow">定制游</a></li>
        </ul>
        
    </div>
</div>