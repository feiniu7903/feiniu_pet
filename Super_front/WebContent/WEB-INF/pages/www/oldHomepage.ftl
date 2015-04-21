<!doctype html> 
<html> 
<head> 
<meta charset="utf-8" />
<title>${comSeoIndexPage.seoTitle}</title>
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
<meta name="keywords" content="${comSeoIndexPage.seoKeyword}"/> 
<meta name="description" content="${comSeoIndexPage.seoDescription}"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="baidu-site-verification" content="ysdnBoqK4gDOHWdg" />
<meta property="qc:admins" content="276353266764651516375" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_main/main.css"/>
</head>
<body> 
<@s.set var="pageMark" value="'homepage'" />


<div class="hh_shortcut_box">
  <div class="hh_shortcut clearfix">
	<ul class="hh_shortcut_nav clearfix">
		<li><a href="http://www.lvmama.com/stored/goStoredSearch.do" rel="nofollow" target="_blank">旅游卡</a></li>
		<li class="hh_link">
			<a href="http://www.lvmama.com/myspace/index.do" class="hh_mylvmama" rel="nofollow">我的驴妈妈<i class="hh_icon_triangleB"></i></a>
			<div class="hh_lvmama_sub">
				<a href="http://www.lvmama.com/myspace/order.do" rel="nofollow">我的订单</a>
				<a href="http://www.lvmama.com/myspace/account/points.do" rel="nofollow">我的积分</a>
				<a href="http://www.lvmama.com/myspace/account/coupon.do" rel="nofollow">我的优惠券</a>
			</div>
		</li>
		<li class="hh_link">
			<a href="http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do" class="hh_mylvmama" rel="nofollow" target="_blank">会员卡<i class="hh_icon_triangleB"></i></a>
			<div class="hh_lvmama_sub">
				<a href="http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do" rel="nofollow" target="_blank">申请会员卡</a>
				<a href="http://www.lvmama.com/login/loginBindCard.do" rel="nofollow" target="_blank">激活会员卡</a>
			</div>
		</li>
		<li><a href="http://www.lvmama.com/points" rel="nofollow">积分商城</a></li>
		<li><a href="http://www.lvmama.com/public/help" rel="nofollow">帮助中心</a></li>
		<li><a target="_self" href="javascript:bookmark()" rel="nofollow">收藏本站</a></li>
		<li class="sitemap"><a href="http://www.lvmama.com/public/site_map">网站地图</a></li>
		<li class="hh_icon_phone"><a href="http://www.lvmama.com/others/lp/index.html" rel="nofollow" target="_blank">手机版</a></li>
	</ul>
	<span class="hh_loginSpanArea" id="hh_loginSpanArea"></span>
  </div>
</div>

<!-- 瀑布广告 -->
<div id="indexSilde" class="main" style="position:relative;overflow:hidden;height:0;">
	<div id="xslide1" style="position:absolute;z-index:11;top:0;display:none;"><a target="_blank" href="http://www.lvmama.com/zt/lvyou/shzijia/?losc=lvmama_banner"><img src="http://pic.lvmama.com/img/new_v/ob_main/bycar-banner.jpg" width="980" height="60"></a>
	</div>
	<div id="xslide2" style="position:relative;top:0;z-index:10;"><a target="_blank"  href="http://www.lvmama.com/zt/lvyou/shzijia/?losc=lvmama_slide"><img src="http://pic.lvmama.com/img/new_v/ob_main/bycar-slide.jpg" width="980" height="400"></a>
	</div>
</div>

<!--shortcut end-->
<div style="width:980px;margin:0 auto;">
	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('x003d9a0818f60f40001','js',null)"/>
</div>
<div class="hh_header clearfix">
	<@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''"><span class="hh_hotline1">4006-040-${showDifferntHotLine?if_exists}</span></@s.if><@s.else><span class="hh_hotline">1010-6060</span></@s.else>
	<a class="hh_logo" href="http://www.lvmama.com/">驴妈妈旅游网:中国自助游领军品牌</a>

<div class="hh_adPro">
  <@s.if test='#pageMark==null || #pageMark=="homepage"'>
    <!-- 首页_顶部广告－（适用于网站首页＆其他页面） -->
    <@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('s0c15aa35c24b7a50001','js',null)"/>
	<!-- 首页_顶部广告/End -->
  </@s.if>
  <@s.elseif test='#pageMark=="ticket" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isTicket())'>
  	<!-- 门票_顶部广告－（适用于打折门票频道首页＆内页） -->
  	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('w0c15ade9f7eec3a0001','js',null)"/>
	<!-- 门票_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="freetour" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isFreeness())'>
  	<!-- 自由行_顶部广告－（适用于周边自由行频道首页＆内页） -->
  	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('e0c15b03b062a35e0001','js',null)"/>
	<!-- 自由行_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="around" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isGroup() && prodCProduct.prodProduct.subProductType=="SELFHELP_BUS")'>
	<!-- 跟团_顶部广告－（适用于周边跟团游频道首页＆内页） -->
	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('g0c15c7d9ee33be90001','js',null)"/>
	<!-- 跟团_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="destroute" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isGroup() && prodCProduct.prodProduct.subProductType!="SELFHELP_BUS")'>
  	<!-- 长途_顶部广告－（适用于长途游频道首页＆内页） -->
  	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('t0c15d2c45212dab0001','js',null)"/>
	<!-- 长途_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="abroad" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isForeign())'>
  	<!-- 出境_顶部广告－（适用于出境游频道首页＆内页） -->
  	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('g0c15d9b809b97550001','js',null)"/>
	<!-- 出境_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="tuangou"'>
  	<!-- 团购_顶部广告－（适用于团购首页） -->
  	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('x0c15e745d0a61de0001','js',null)"/>
	<!-- 团购_顶部广告/End -->
  </@s.elseif>
  <@s.elseif test='#pageMark=="hotel" || (#pageMark=="productDetail" && prodCProduct.prodProduct.isHotel())'>
 	<!-- 国内酒店_顶部广告－（适用于国内酒店首页） -->
 	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('g0c15e960f4d0ecf0001','js',null)"/>
	<!-- 国内酒店_顶部广告/End -->
  </@s.elseif>
 </div>
</div><!--header end-->
<div class="hh_nav">
	<ul class="hh_nav_main">
		<li id="main"><a href="http://www.lvmama.com" rel="nofollow">首页</a></li>
		<li id="ticket"><a href="http://www.lvmama.com/ticket">景点门票</a></li>
		<li id="freetour"><a href="http://www.lvmama.com/freetour">周边自由行</a></li>
		<li id="around"><a href="http://www.lvmama.com/around">周边跟团游</a></li>
		<li id="destroute"><a href="http://www.lvmama.com/destroute">国内游</a></li>
		<li id="abroad"><a href="http://www.lvmama.com/abroad">出境游</a></li>
		<li id="tuangou"><a href="http://www.lvmama.com/tuangou" rel="nofollow">旅游团购</a></li>
		<li id="hotel"><a href="http://www.lvmama.com/hotel" rel="nofollow">特色酒店</a></li>
		<li id="globalhotel"><a href="http://www.lvmama.com/globalhotel" rel="nofollow">境外酒店</a><i class="xh_new1"></i></li>
		<li id="company"><a href="http://www.lvmama.com/company" rel="nofollow">定制游</a><i class="xh_new2"></i></li>
	</ul>
	<p class="hh_nav_other">
	    <a href="http://www.lvmama.com/comment" rel="nofollow"><span>点评</span></a>
		<a href="http://www.lvmama.com/guide"><span>攻略</span></a>
		<a href="http://www.lvmama.com/zt/s/"><span>专题</span></a>
		<a href="http://www.lvmama.com/info"><span>资讯</span></a>
		<a href="http://bbs.lvmama.com" rel="nofollow"><span>社区</span></a>
	</p>
</div><!--nav end-->

<ul class="hh_sub_recom clearfix">
	<li><i class="hh_icon_now"></i>当前城市：</li>
	<li>
	    <dl class="hh_now_place" id="hh-now-place">
			<dt><strong class="hh_now_city">${fromPlaceName}<i class="hh_icon"></i></strong></dt>
			<dd class="hh_now_city_group">
				<p style="overflow: hidden;zoom:1;">
				    <a href="#" onclick="switchIndex('SH','79','上海');">上海</a>
    				<a href="#" onclick="switchIndex('BJ','1','北京');">北京</a>
    				<a href="#" onclick="switchIndex('CD','279','成都');">成都</a>
    				<a href="#" onclick="switchIndex('GZ','229','广州');">广州</a>
				</p>
				<p style="overflow: hidden;zoom:1;">
                    <a href="#" onclick="switchIndex('HZ','100','杭州');">杭州</a>
                    <a href="#" onclick="switchIndex('NJ','82','南京');">南京</a>
                    <a href="#" onclick="switchIndex('SZ','231','深圳');">深圳</a>
					<a href="http://www.lvmama.com/dest/hainan_sanya?indexin" onclick="">三亚</a>
                </p>
			</dd>
		</dl>
		<form method="post" id="switchIndexForm">
    		<input type="hidden" name="fromPlaceCode" id="fromPlaceCode" value="${fromPlaceCode}" />
    		<input type="hidden" name="fromPlaceId" id="fromPlaceId" value="${fromPlaceId}" />
    		<input type="hidden" name="fromPlaceName" id="fromPlaceName" value="${fromPlaceName}" />
		</form>
    </div><!--now_place end-->
    <div class="hh_header_recom">
        <span>周边游推荐：<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_topRecommend_around')" status="status"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.iterator></span>
        <span>国内游推荐：<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_topRecommend_destroute')" status="status"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.iterator></span>
        <span>出境游推荐：<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_topRecommend_abroad')" status="status"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.iterator></span>
    	</div></li>
</ul><!--hh_sub_recom end-->
<!--以上是头部内容-->
<div class="main">
	<div class="part1 clearfix">
		<div class="part1_l">
			<div class="new_sub_menu">        
        <div class="search_main">
            <div class="select-type now_search" data-select-type="ticket">
                <h3 class="search_tit search_border" ><i class="icon_search01"></i>景点</h3>
                <div class="search_text">
                    <h4 class="search_tit01">上万种门票1张起订</h4>

                    <input name="" type="text" value="景点名或城市名" id="searchTicket" class="input_search" />
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_search_place')" status="status">
                    <p class="search_link" ><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></p>
                    </@s.iterator>

                    <input name="button_search" type="button" id="searchTicketBtn" class="button_search" />
                </div>
            </div><!--search_tab end-->
            <div class="select-type" data-select-type="line">
                <h3 class="search_tit" ><i class="icon_search02"></i>度假</h3>
                <div class="search_text">
                    <h4 class="search_tit02">寻找最适合您的线路</h4>    
                    <p class="vacation clearfix"><label class="radio select-type radio_free  radio_now" data-select-type="freetour"><i class="select_type"></i>周边自由行</label><label class="radio select-type" data-select-type="around"><i class="select_type"></i>周边跟团游</label></p>    
                    <p class="vacation clearfix"><label class="radio select-type" data-select-type="destroute"><i class="select_type"></i>长途游</label><label class="radio select-type" data-select-type="abroad"><i class="select_type"></i>出境游</label></p> 
                    <p class="departure departure_free clearfix">
                    	<input type="hidden" name="searchBlockId" id="searchBlockId" value="8302" />
                        <select class="select02" id="fromDestSelect">
                            <option>上海</option>
                            <option>上海</option>
                            <option>上海</option>
                            <option>上海</option>
                        </select>
                        <label>出发地</label>
                    </p>
                    <p class="purpose clearfix"><label>目的地</label><input name="searchmudi" id="searchOthers" type="text" value="中文/拼音/产品名" inittext="中文/拼音/产品名" class="input_search02" /></p>
                    <input name="button_search" type="button" id="searchOthersBtn" class="button_search" />
                </div>
             </div><!--search_tab end-->
            <div class="select-type" data-select-type="hotel">
                <h3 class="search_tit" ><i class="icon_search03"></i>酒店</h3>
                <div class="search_text">
                    <h4 class="search_tit03">专为旅游度假精选</h4> 
                    <div class="city clearfix">
                        <input type="text" value="中文/拼音" autocomplete="off" class="text_query input_search03" id="city_element" name="txtCity"><label>入住城市</label><span class="icon_city">*</span>
                        <div class="city_list" style="z-index:200;">
                        	<b>热门城市</b>
                            <a href="javascript:void(0)">上海</a>
                            <a href="javascript:void(0)">苏州</a>
                            <a href="javascript:void(0)">杭州</a>
                            <a href="javascript:void(0)">无锡</a>
                            <a href="javascript:void(0)">南京</a>
                        </div><!--city_list end-->
                    </div>
                    <iframe class="iframe"></iframe>	
                    <p class="periphery mb10 clearfix">
                    <input type="text" value="中文/拼音" autocomplete="off" class="closeCity input_text02" name="roundPlaceName" id="scenic_element">
                    <label>周边景点</label></p>
					<p class="periphery mb10 clearfix">
                    <!--<input name="searchmudi" id="hotelName" type="text" value="中文/拼音" class="hotelNameParm input_text02" />-->
                    <input type="text" value="中文/拼音" id="hotel_element" class="hotelNameParm text_unfocus input_text02" name="txtHotel">
                    <label>酒店名称</label></p>
                    <p class="xh_checkbox clearfix"><label>
              <input type="checkbox" value="D" name="hotelType">
              度假酒店</label>
            <label>
              <input type="checkbox" value="P" name="hotelType">
              精品酒店</label>
            <label>
              <input type="checkbox" value="G" name="hotelType">
              高档酒店</label>
            <label>
              <input type="checkbox" value="J" name="hotelType">
              经济酒店</label>
			  <label>
              <input type="checkbox" value="K" name="hotelType">
              客栈/农家乐</label>
          </p>
                    <input name="button_search" type="button" id="searchHotelBtn" class="button_search" />
                </div>
            </div><!--search_tab end-->
            <div class="select-type" data-select-type="dest"> 
				<h3 class="search_tit"><i class="icon_search04"></i>目的地</h3> 
				<div class="search_text"> 
				<h4 class="search_tit04">全球目的地大全</h4> 
				<input name="" type="text" value="" id="searchDest" class="input_search_dest" /> 
				<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_search_dest')" status="status">
                <p class="search_link" ><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></p>
                </@s.iterator>
				<input name="button_search" type="button" id="searchDestBtn" class="button_search" /> 
				</div> 
			</div><!--search_tab end-->  
        </div><!--search_main end-->
        <div class="pro_select">
            <div class="pro_select1">
            	<div class="pro_select_mid">
                    <h3 class="pro_select_tit"><i class="icon_triangleJ"></i>自由行</h3>
                    <p class="pro_select_link">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_season')" status="status">
                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                    </@s.iterator>
                    </p>
                </div><!--pro_select_mid end-->
                <div class="new_sub_menu_con">
                	<div class="new_sub_menu_ul">
                		<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_seasonPlace_title')" status="status">
                    	<dl>
                        	<dd>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_seasonPlace_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	</dd>
                        	<dt>${title?if_exists}</dt>
                        </dl>
                        </@s.iterator>
                    </div><!--new_sub_menu_ul end-->
                    <div class="new_sub_menu_right">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_title')" status="status">
                    	<strong><@s.if test="url!=null && url!=''"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if><@s.else>${title?if_exists}</@s.else></strong>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	 <br />
                        </@s.iterator>
                    </div><!--new_sub_menu_right ebd-->
                </div><!--new_sub_menu_con end-->
            </div><!--pro_select1 end-->
            <div class="pro_select1">
            	<div class="pro_select_mid">
                    <h3 class="pro_select_tit"><i class="icon_triangleJ"></i>周边游</h3>
                    <p class="pro_select_link">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_around')" status="status">
                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                    </@s.iterator></p>
                </div><!--pro_select_mid end-->
                <div class="new_sub_menu_con">
                	<div class="new_sub_menu_ul">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_aroundPlace_title')" status="status">
                    	<dl>
                        	<dd>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_aroundPlace_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	</dd>
                        	<dt>${title?if_exists}</dt>
                        </dl>
                        </@s.iterator>         
                    </div><!--new_sub_menu_ul end-->
                    <div class="new_sub_menu_right">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_title')" status="status">
                    	<strong><@s.if test="url!=null && url!=''"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if><@s.else>${title?if_exists}</@s.else></strong>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	 <br />
                        </@s.iterator>
                    </div><!--new_sub_menu_right ebd-->
                </div><!--new_sub_menu_con end-->
            </div><!--pro_select1 end-->
            <div class="pro_select1">
            	<div class="pro_select_mid">
                    <h3 class="pro_select_tit"><i class="icon_triangleJ"></i>长途游</h3>
                    <p class="pro_select_link">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_destroute')" status="status">
                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                    </@s.iterator>
                    </p>
                </div><!--pro_select_mid end-->
                <div class="new_sub_menu_con">
                	<div class="new_sub_menu_ul">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_destroutePlace_title')" status="status">
                    	<dl>
                        	<dd>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_destroutePlace_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	</dd>
                        	<dt>${title?if_exists}</dt>
                        </dl>
                        </@s.iterator>            
                    </div><!--new_sub_menu_ul end-->
                    <div class="new_sub_menu_right">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_title')" status="status">
                    	<strong><@s.if test="url!=null && url!=''"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if><@s.else>${title?if_exists}</@s.else></strong>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	 <br />
                        </@s.iterator>
                    </div><!--new_sub_menu_right ebd-->
                </div><!--new_sub_menu_con end-->
            </div><!--pro_select1 end-->
            <div class="pro_select1">
            	<div class="pro_select_mid">
                    <h3 class="pro_select_tit"><i class="icon_triangleJ"></i>出境游</h3>
                    <p class="pro_select_link">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_abroad')" status="status">
                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                    </@s.iterator>
                    </p>
                </div><!--pro_select_mid end-->
                <div class="new_sub_menu_con">
                	<div class="new_sub_menu_ul">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_abroadPlace_title')" status="status">
                    	<dl>
                        	<dd>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_abroadPlace_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                     	</dd>
                        	<dt>${title?if_exists}</dt>
                        </dl>
                        </@s.iterator>      
                    </div><!--new_sub_menu_ul end-->
                    <div class="new_sub_menu_right">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_title')" status="status">
                    	<strong><@s.if test="url!=null && url!=''"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if><@s.else>${title?if_exists}</@s.else></strong>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	 <br />
                        </@s.iterator>
                    </div><!--new_sub_menu_right ebd-->
                </div><!--new_sub_menu_con end-->
            </div><!--pro_select1 end-->
            <div class="pro_select1">
            	<div class="pro_select_mid">
                    <h3 class="pro_select_tit"><i class="icon_triangleJ"></i>特色酒店</h3>
                    <p class="pro_select_link">
                    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_recommend_hotel')" status="status">
                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                    </@s.iterator>
                    </p>
                </div><!--pro_select_mid end-->
                <div class="new_sub_menu_con">
                	<div class="new_sub_menu_ul">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_hotel_title')" status="status">
                    	<dl>
                        	<dd>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_hotel_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
	                	</dd>
                        	<dt>${title?if_exists}</dt>
                        </dl>
                        </@s.iterator>   
                    </div><!--new_sub_menu_ul end-->
                    <div class="new_sub_menu_right">
                    	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_title')" status="status">
                    	<strong><@s.if test="url!=null && url!=''"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if><@s.else>${title?if_exists}</@s.else></strong>
                        	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_nav_right_${status.index}')" status="st">
		                     <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
		                    </@s.iterator>
                        	 <br />
                        </@s.iterator>
                    </div><!--new_sub_menu_right ebd-->
                </div><!--new_sub_menu_con end-->
            </div><!--pro_select1 end-->
        </div><!--pro_select end-->
    </div><!--new_sub_menu end-->
			<div class="lv_weekend">
            	<h3 class="tit_C"><a href="http://www.lvmama.com/zt/" class="more_link" target="_blank">更多</a>驴行周末</h3>
            	 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_weekend')" status="status">
	                <@s.if test="#status.isFirst()">
	                	<a class="weekend_img" target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="120" width="240" /></a>
	                </@s.if>
                </@s.iterator>
                <ul class="weekend_list">
	                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_weekend')" status="status">
		                <@s.if test="#status.isFirst()">
			             <li class="now_weekend">
		                    	<strong>${bakWord1?if_exists}</strong><a target="_blank" href="${url?if_exists}" title="${title?if_exists}"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a>
		                        <p title="${remark?if_exists}"><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
		                 </li>
		                </@s.if>
		                <@s.else>
		                <li><strong>${bakWord1?if_exists}</strong><a title="${title?if_exists}" href="${url?if_exists}" target="_blank"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></li>
		                </@s.else>
			     	</@s.iterator>
                </ul>
            </div><!--lv_weekend end-->
		</div><!--part1_l end-->
        <div class="part1_c">
		<!-- 首页焦点总控 -->
			<div id="flash">
				<ul class="Slides">
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('bf98f123b8ed3b0d0001','js',fromPlaceCode)" /></li>
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('uf98f123bd196cd60001','js',fromPlaceCode)" /></li>	
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('pf98f123c13719030001','js',fromPlaceCode)" /></li>	
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('yf98f123c54b3e3e0001','js',fromPlaceCode)" /></li>	
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('gf98f123cb023b5c0001','js',fromPlaceCode)" /></li>	
    					<li><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('nf98f123cf2cbf190001','js',fromPlaceCode)" /></li>		 
				</ul>
				<@s.if test = '"SH" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(1)"/></p>
				</@s.if>
				<@s.if test = '"NJ" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(2)"/></p>
				</@s.if>
				<@s.if test = '"HZ" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(3)"/></p>
				</@s.if>
				<@s.if test = '"BJ" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(4)"/></p>
				</@s.if>
				<@s.if test = '"GZ" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(5)"/></p>
				</@s.if>
				<@s.if test = '"SZ" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(6)"/></p>
				</@s.if>
				<@s.if test = '"CD" == fromPlaceCode'>
					<p class="SlideTriggers"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(7)"/></p>
				</@s.if>	
			</div>
			
		<!-- 首页焦点总控/End -->
		<div class="now_main">
            	<h3 class="tit_B">当季主打</h3>
                <div class="now_sub">
                	<dl>
                        <dd>
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_seasonHot_subject')" status="status">
	                    <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">
	                    <@s.if test="title!=null && title.length()>5"><@s.property value="title.substring(0,5)" escape="false"/></@s.if>
                         <@s.else>${title?if_exists}</@s.else>
	                    </a>
	                    </@s.iterator>
                        </dd>
                    	<dt>主题</dt>
                    </dl>
                	<dl>
                        <dd>
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_seasonHot_destroute')" status="status">
	                    <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">
	                    <@s.if test="title!=null && title.length()>5"><@s.property value="title.substring(0,5)" escape="false"/></@s.if>
                         <@s.else>${title?if_exists}</@s.else>
	                    </a>
	                    </@s.iterator>
                        </dd>
                    	<dt>景点</dt>
                    </dl>
                	<dl>
                        <dd>
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_seasonHot_abroad')" status="status">
	                    <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">
	                    <@s.if test="title!=null && title.length()>5"><@s.property value="title.substring(0,5)" escape="false"/></@s.if>
                         <@s.else>${title?if_exists}</@s.else>
	                    </a>
	                    </@s.iterator>
                        </dd>
                    	<dt>出境</dt>
                    </dl>
                	<dl>
                        <dd>
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_seasonHot_tuangou')" status="status">
	                    <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">
	                    <@s.if test="title!=null && title.length()>5"><@s.property value="title.substring(0,5)" escape="false"/></@s.if>
                         <@s.else>${title?if_exists}</@s.else>
	                    </a>
	                    </@s.iterator>
                        </dd>
                    	<dt>国内</dt>
                    </dl>
                </div>
            </div><!--now_main end-->
			<div class="now_rank">
            	<h3 class="tit_B">当季排行榜</h3>
                <ul class="tab">
                   <li class="current">打折门票</li>
                   <li>周边自由行</li>
                   <li>周边跟团游</li>
                   <li>国内游</li>
                   <li>出境游</li>
                </ul>
                 <div class="rank_panes">
                	<ul style="display: block;" class="rank_list rank_pane">
                	   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_ticket')" status="status">
	                    <li>
                    	<strong class="price"><span>&yen;${memberPrice?if_exists}</span>起</strong>
                        <a class="rank_link" target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        <span class="rank_related">
                        <@s.if test="cmtNum!=null && cmtNum!=''"><a target="_blank" rel="nofollow" href="http://www.lvmama.com/comment/${placeId?if_exists}-1">点评：${cmtNum?if_exists}</a></@s.if>
                        </span>
                        </li>
	                    </@s.iterator>
              		  </ul><!--rank_pro_list end-->
                      <ul class="pro_list rank_pane" style="display: none;">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_freeness')" status="status">
                        <li>
                            <strong class="price"><span>¥${memberPrice?number}</span>起</strong>
                            <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        </li>
	                    </@s.iterator>
               		 </ul><!--pro_list end-->
                     <ul class="pro_list rank_pane" style="display: none;">
                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_around')" status="status">
                        <li>
                            <strong class="price"><span>¥${memberPrice?number}</span>起</strong>
                            <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        </li>
	                    </@s.iterator>
               		 </ul><!--pro_list end-->
                     <ul class="pro_list rank_pane" style="display: none;">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_destroute')" status="status">
                        <li>
                            <strong class="price"><span>¥${memberPrice?number}</span>起</strong>
                            <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        </li>
	                    </@s.iterator>
               		 </ul><!--pro_list end-->
                     <ul class="pro_list rank_pane" style="display: none;">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_ranked_abroad')" status="status">
                        <li>
                            <strong class="price"><span>¥${memberPrice?number}</span>起</strong>
                            <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        </li>
	                    </@s.iterator>
               		 </ul><!--pro_list end-->
                </div>
            </div><!--now_main end-->     
		</div><!--part1_c end-->
		
		<div class="part1_r">			
			<div class="notice">
				<!--<h4 class="notice_tit">公告</h4>-->
				<ul class="notice_text">
					<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_notice')" status="status">
                    <li><a rel="nofollow" target="_blank" href="${url?if_exists}" title="${title?if_exists}" <@s.if test="bakWord1!=null && bakWord1!=''">class="${bakWord1?if_exists}"</@s.if>>
                    <@s.if test="title!=null && title.length()>16"><@s.property value="title.substring(0,16)" escape="false"/></@s.if>
                    <@s.else>${title?if_exists}</@s.else></a></li>
                    </@s.iterator>
				</ul>
				<div class="y_bottom">
					<b>订阅特价、促销信息</b> <a rel="nofollow" target="_blank" href="/edm/showSubscribeEmail.do">详情&gt;&gt;</a>
					<p class="y_s_one">
						<input type="text" id="y_input" class="y_input" style="background: none repeat scroll 0% 0% transparent;">
						<a id="yjdy_btn" class="yjdy_btn" href="javascript:void(0)">订阅</a>
						</p><p id="y_txt_info" class="y_txt_info">请输入你的Email地址</p>
					<p class="y_s_two dn"><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/y_loading.gif"></p>
					<p class="y_s_three dn">订阅成功!<a href="javascript:void(0)" class="y_link_blue" id="y_link_return">返回</a></p>
				 </div>
				 <div id="y_error_msg" class="y_error_msg">
					<i class="y_jiao"></i>
					<label>请输入正确的Email地址</label>
				 </div>
			</div>
			
			<p class="notice_img">
			<a href="http://www.lvmama.com/activity/" rel="nofollow" class="fl act_member" target="_blank" title="会员活动">会员活动</a>
			<a href="http://www.lvmama.com/zt/promo/mkt/" rel="nofollow" class="fr act_theme" target="_blank" title="主题活动">主题活动</a></p>
			
			<!--notice end-->
			<div class="topic">
            	<div class="topic_big">
                   <!-- 活动专区（大）01 -->
                   <@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('gf94ea4b80c0001c0001','js',fromPlaceCode)"/>
					<!-- 活动专区（大）01/End -->
		
					<!-- 活动专区（大）02 -->
					<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('qf94ea79bfe2e5420001','js',fromPlaceCode)"/>
					
					<!-- 活动专区（大）02/End -->
		
					<!-- 活动专区（大）03 -->
					<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('gf94ea9b5425cb4a0001','js',fromPlaceCode)"/>
					
					<!-- 活动专区（大）03/End -->
		
					<!-- 活动专区（大）04 -->
					<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('sf94eac83c3270ca0001','js',fromPlaceCode)"/>
					
					<!-- 活动专区（大）04/End -->
						
					<!-- 活动专区（大）05 -->
					<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('df94eae2276d718f0001','js',fromPlaceCode)"/>
					<!-- 活动专区（大）05/End -->
                </div><!--toppic_big end-->
                <ul class="topic_samll">     
                	<li class="fl">
						<!-- 活动专区（小）01 -->
						<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('yf94eb04486ab37a0001','js',fromPlaceCode)"/>
						<!-- 活动专区（小）01/End -->
			
						<!-- 活动专区（小）02 -->
						<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('zf94eb1ccb069a560001','js',fromPlaceCode)"/>
						
						<!-- 活动专区（小）02/End -->
			
						<!-- 活动专区（小）03 -->
			    		<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('kf94eb3633241e3e0001','js',fromPlaceCode)"/>
						
						<!-- 活动专区（小）03/End -->
                    </li>        
                    <li class="fr"> 
			    <!-- 活动专区（小）04 -->
			    	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('tf94eb4ea8dd19790001','js',fromPlaceCode)"/>
						<!-- 活动专区（小）04/End -->
			
						<!-- 活动专区（小）05 -->
						<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('vf94eb695d9a68860001','js',fromPlaceCode)"/>
						<!-- 活动专区（小）05/End -->
			
						<!-- 活动专区（小）06 -->
						<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('gf94eb82701101630001','js',fromPlaceCode)"/>
						
						<!-- 活动专区（小）06/End -->
                    </li> 
                </ul><!--topic_samll end-->     
            </div>
			<div class="activity">
            	<h4 class="tit"><a href="http://www.lvmama.com/activity/" rel="nofollow" class="more_link" target="_blank">更多</a>会员活动</h4>
                <ul class="activity_list">
                	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_activity')" status="status">
	                	<@s.if test="#status.isFirst()">
	                	<li class="now_activity"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a><strong>本月：</strong>
	                        <a class="now_activity_aImg" target="_blank" href="${url?if_exists}" rel="nofollow"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif"  width="200" height="60"  /></a>
	                    </li>
	                    </@s.if>
	                    <@s.else>
	                     <li><a target="_blank" rel="nofollow" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a><strong>本月：</strong></li>
	                    </@s.else>
                    </@s.iterator>
                </ul>
            </div><!--activity end-->
		</div><!--part1_r end-->
	</div><!--part1 end-->
    <div class="part2 clearfix">
        <div class="cooper">
            <h4 class="tit"><a href="http://www.lvmama.com/zt/promo/mkt/" rel="nofollow" class="more_link" target="_blank">更多</a>品牌合作</h4>
            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_brands')" status="status">
                <p><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="60" width="80"   />
                <a target="_blank" rel="nofollow" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                </p>
            </@s.iterator>
        </div><!--cooper end-->
    	<div class="free">
            	<h3 class="tit_D"><a href="http://www.lvmama.com/freetour" class="more_link" target="_blank">更多</a>自游自在<span class="hh_tit_s2">门票+酒店，自驾游爱好者首选</span></h3>
                <ul class="pro_list">
                     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_freeness')" status="status">
		                <li>
                    	<strong class="price"><span>&yen;${memberPrice?number}</span>起</strong>
                        <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                        </li>
		            </@s.iterator>
                </ul>
            </div><!--free end-->
        <div class="raiders">
            <h3 class="tit_C"><a href="http://www.lvmama.com/guide/" class="more_link" target="_blank">更多</a>官方攻略</h3>
            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_guide')" status="status">
	                <@s.if test="#status.isFirst()">
		                <dl class="raiders_version">
			                <dt><a target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="124" width="89" /></a></dt>
			                <dd><a target="_blank" href="${url?if_exists}"><strong>${title?if_exists}</strong>官方攻略</a></dd>
			                <dd><span>版本：</span><b>${bakWord1?if_exists}</b></dd>
			                <dd style="margin: 0pt;"><span>更新时间：</span><b>${bakWord2?if_exists}</b></dd>
			            </dl>
	                </@s.if>
                </@s.iterator>
            <ul class="raiders_list">
            	<@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_guide')" status="status">
	                <@s.if test="#status.index >= 1">
	                 <li><a target="_blank" href="${url?if_exists}" title="${title?if_exists}"><strong>${title?if_exists}</strong>官方攻略</a></li>
	                </@s.if>
			   </@s.iterator>
            </ul>
        </div><!--raiders end-->
    </div><!--part2 end-->    
	<div class="banner">
		<!-- 中间通栏 -->
		<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('zf94eb99f328f2bc0001','js',null)"/>
		<!-- 中间通栏/End -->
	</div><!--banner end-->
	<div class="part3 clearfix">
			<div class="lv_weekend">
            	<h3 class="tit_C"><a href="http://www.lvmama.com/zt/" class="more_link" target="_blank">更多</a>驴妈妈口碑榜单</h3>
               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_hotComment')" status="status">
	                <@s.if test="#status.isFirst()">
	                	<a class="weekend_img" target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="120" width="240" /></a>
	                </@s.if>
                </@s.iterator>
                <ul class="xh_comment-list">
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_hotComment')" status="status">
                  <@s.if test="#status.index>0&&#status.index<=3">
                    <li><dfn class="score"><i>${bakWord1?if_exists}</i>分</dfn><span class="xh_score"><i style="width:<@s.if test='bakWord2==""'>100</@s.if><@s.else>${bakWord2?if_exists}</@s.else>%" class="xh_score-star"></i></span><b class="num"><@s.property value="#status.index"/></b><a target="_blank" href="${url?if_exists}" class="xh_tit">${title?if_exists}</a></li>
                   </@s.if>
                </@s.iterator>
                </ul>
                <ul class="weekend_list">
	                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_hotComment')" status="status">
	                  <@s.if test="#status.index>3">
		                <li><strong>${bakWord1?if_exists}</strong><a title="${title?if_exists}" href="${url?if_exists}" target="_blank"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></li>
			     	  </@s.if>
			     	</@s.iterator>
                </ul>
            </div><!--lv_weekend end-->    	
        <div class="part3_list">
            <h3 class="tit_B<@s.if test="fromPlaceCode eq 'SH'"></@s.if>"><@s.if test="fromPlaceCode eq 'SH'"><a href="http://www.lvmama.com/zt/lvyou/kailvxing/" class="more_link" target="_blank">更多</a></@s.if><@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_title_1')" status="status">${title?if_exists}</@s.iterator><span class="hh_tit_s2">独家发班，品质保障</span></h3>
             <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_1')" status="status">
                <@s.if test="#status.isFirst()">
                 <dl class="pro_recom">
	                <dt><a target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}"  src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="160" width="220" /></a><span class="price">&yen;<span>${memberPrice?number}</span>起</span></dt>
	                <dd><a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></dd>
	            </dl>
                </@s.if>
		     </@s.iterator>
            <ul class="pro_list">
            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_1')" status="status">
                <@s.if test="#status.index > 0">
	            <li>
                    <strong class="price"><span>&yen;${memberPrice?number}</span>起</strong>
                   <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                </li>
                </@s.if>
		     </@s.iterator>
            </ul>
        </div><!--part3_list end-->
	</div><!--part3 end-->
    <div class="part4 clearfix">
			<div class="lv_weekend">
            	<h3 class="tit_C"><a href="http://www.lvmama.com/zt/" class="more_link" target="_blank">更多</a>驴行风向标</h3>
                <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_wind')" status="status">
	                <@s.if test="#status.isFirst()">
	                	<a class="weekend_img" target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" height="120" width="240" /></a>
	                </@s.if>
                </@s.iterator>
                <ul class="weekend_list">
	                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_left_wind')" status="status">
		                <@s.if test="#status.isFirst()">
			             <li class="now_weekend">
	                    	<strong>${bakWord1?if_exists}</strong><a target="_blank" href="${url?if_exists}" title="${title?if_exists}"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a>
	                        <p title="${remark?if_exists}"><@s.if test="remark!=null && remark.length()>65"><@s.property value="remark.substring(0,65)" escape="false"/>...</@s.if><@s.else>${remark?if_exists}</@s.else></p>
		                 </li>
		                </@s.if>
		                <@s.else>
		                <li><strong>${bakWord1?if_exists}</strong><a title="${title?if_exists}" href="${url?if_exists}" target="_blank"><@s.if test="title!=null && title.length()>15"><@s.property value="title.substring(0,15)" escape="false"/>...</@s.if><@s.else>${title?if_exists}</@s.else></a></li>
		                </@s.else>
			     	</@s.iterator>
                </ul>
            </div><!--lv_weekend end-->
    	<div class="part3_list">
            <h3 class="tit_B<@s.if test="fromPlaceCode eq 'SH'"></@s.if>"><@s.if test="fromPlaceCode eq 'SH'"><a href="http://www.lvmama.com/zt/abroad/shehua.html" class="more_link" target="_blank">更多</a></@s.if><@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_title_2')" status="status">${title?if_exists}</@s.iterator><span class="hh_tit_s2">颠覆传统的个性化出境新体验</span></h3>
             <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_2')" status="status">
                <@s.if test="#status.isFirst()">
                 <dl class="pro_recom">
	                <dt><a target="_blank" href="${url?if_exists}"><img alt="${title?if_exists}" title="${title?if_exists}" to="${imgUrl?if_exists}" height="160" width="220" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif"/></a><span class="price">&yen;<span>${memberPrice?number}</span>起</span></dt>
	                <dd><a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></dd>
	            </dl>
                </@s.if>
		     </@s.iterator>
            <ul class="pro_list">
            <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_recommend_2')" status="status">
                <@s.if test="#status.index > 0">
	            <li>
                    <strong class="price"><span>&yen;${memberPrice?number}</span>起</strong>
                   <a target="_blank" href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                </li>
                </@s.if>
		     </@s.iterator>
            </ul>
        </div><!--part3_list end-->
    </div><!--part4 end-->

      <div class="banner">
		<!-- 底部通栏 -->
		<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('sf94ebb0daaaec4b0001','js',fromPlaceCode)"/>
		<!-- 底部通栏/End -->
	</div><!--banner end-->
	<!--指南及常见问题-->
	<#include "/common/guide_lvmama.ftl">
</div><!--main end-->
<!--引导功能-->
<div class="hh_cooperate">
<!-- footer start-->
       <#include "/WEB-INF/pages/staticHtml/friend_link/index_footer.ftl">
<!-- footer end-->
</div>
<script src="http://pic.lvmama.com/js/common/copyright.js"></script>

<div id="tang">
    <div class="bm-box" style="background:url(http://pic.lvmama.com/img/new_v/ob_main/float.jpg) no-repeat;">
    	<i class="close">关闭</i>
        <a class="bm-link" target="_blank" href="http://www.lvmama.com/dest/taiguo_qingmai?losc=lvmama_float"></a>
    </div>
</div>

<script type="text/javascript" src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/super_front/homepage_new.js,/js/common/losc.js"  charset="utf-8"></script>
<script>

$(function(){ 
    $("#tang i.close").click(function(){
	    $("#tang").hide();
	})

	var _indexSilde = $('#indexSilde');
	var _xslide1 = $('#xslide1');
	function shouqi(){ 
		_indexSilde.animate({'height':60},1000,function(){
			_xslide1.fadeIn(500).siblings().fadeOut(500,function(){
				if($.browser.msie && parseInt($.browser.version)<=8){ 
					$.fx.off = true; 
				}
			});
		});
	};
	
	function _showslide(){
		_indexSilde.animate({'height':400},1000,function(){
			setTimeout(shouqi,4000);
		});
	}
	$.fx.off = false;
	setTimeout(_showslide,1000);
	
})
</script>
<script type="text/javascript">
  function switchIndex(placeCode,placeId,placeName){
		var $switchIndexForm = $('#switchIndexForm');
		$('#fromPlaceCode').val(placeCode);
		$('#fromPlaceId').val(placeId);
		$('#fromPlaceName').val(placeName);
		$switchIndexForm[0].submit();
   }
	
   //QQ彩贝用户的黄色帽子 
   $(function(){
	if (getLOSCCookie("orderFromChannel") == 'qqcb') {
		var arrStr = document.cookie.split("; "); 
		var temp;
		var HeadShow = "";
		var hit = 0;
		for(var i = 0,l=arrStr.length;i < l;i++){    
			temp=arrStr[i].split("=");    
			if(temp[0] == "HeadShow") {
				HeadShow = decodeURIComponent(temp[1]).replace(new RegExp("\\+","gm"), " ");
				hit = hit+1;
			}else if(temp[0] == "unUserName"){
			  var username = decodeURIComponent(temp[1]).replace(new RegExp("\\+","gm"), " ");
			  $('#hh_loginSpanArea').html(username!=null?"<span class='hh_welcome'>\u60a8\u597d\uff0c<a href='http://www.lvmama.com/myspace/index.do'>"+username+ 
              "</a></span><a rel='nofollow' href='http://login.lvmama.com/nsso/logout' class='hh_loginlink'>[\u9000\u51fa]</a>":"<span class='hh_welcome'>\u60a8\u597d\uff0c\u6b22\u8fce\u6765\u5230\u9a74\u5988\u5988!</span><a onclick='javascript:loginCas();' class='hh_loginlink'>\u767b\u5f55</a><a href='http://login.lvmama.com/nsso/register/registering.do' rel='nofollow' class='hh_registlink' >\u514d\u8d39\u6ce8\u518c</a>"); 
			  hit = hit+1;
			}
			if(hit >= 2)
			{
			  break;
			}
		}

		var qqcbHtml="<div class='caibei-wrap' id='caibei-wrap'><div class='caibei'><div class='caibei-info'>" + HeadShow + "</div></div></div>";	   
		$("body").prepend(qqcbHtml);	
	}
   });
</script>     
<script type="text/javascript">
<!-- 
var bd_cpro_rtid="Pjc4n1f";
//-->
</script>
<noscript>
<div style="display:none;">
<img height="0" width="0" style="border-style:none;" src="http://eclick.baidu.com/rt.jpg?t=noscript&rtid=Pjc4n1f" />
</div>
</noscript>
</body>
<script src="http://www.lvmama.com/js/myspace/edm_index_subscribe.js"></script>
<script src="http://cpro.baidu.com/cpro/ui/rt.js"></script>
</html>
