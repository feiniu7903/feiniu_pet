<!doctype html> 
<html> 
<#setting url_escaping_charset="UTF-8">
<head> 
<meta charset="utf-8" />
	<title>${comSeoIndexPage.seoTitle}</title>
   	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="${comSeoIndexPage.seoKeyword}"/>
	<meta name="description" content="${comSeoIndexPage.seoDescription}"/>
	
	
	<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css,/styles/v3/channel.css" > 
		<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/train.css" > 
	<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/train/trainquery.css" >

	
</head>
<body class="train">
<@s.set var="pageMark" value="'train'" />
<#include "/common/header.ftl">
<div class="wrap">
    <div class="train-head">
        <span>您的位置</span>
        <span> ：</span>
        <span><a href="http://www.lvmama.com">驴妈妈首页</a></span>
        <i></i>
        <span>
            <a rel="nofollow" href="http://www.lvmama.com/train" hidefocus="false">火车票</a>
        </span>
        <i></i>
        <span>火车车次</span>
    </div>
    <div class="train-main">
        <div class="train-query"> 
				<span class="train-query-title">车次查询</span> 
		<form id="searchFormCheci" action="/"  method="post" style="display:inline-block;">
	    		<input type="text" id="train-num-input" class="train-num-input" placeholder="请输入车次，如G223">
        			<a id="station-search" href="javascript:;" hidefocus="false" onClick="goTrainSearchCheci(this.form)">
            			<img class="searchStation" src="http://pic.lvmama.com/img/v3/train/trainquery.gif">
        			</a>
    	</form>
				<em>热门查询车次：</em> 
				<span class="train-links"> 
					<a href="http://www.lvmama.com/train/checi/D73">D73</a> 
					<a href="http://www.lvmama.com/train/checi/D61">D61</a> 
					<a href="http://www.lvmama.com/train/checi/D6008">D6008</a> 
					<a href="http://www.lvmama.com/train/checi/D352">D352</a> 
				</span> 
			 
		</div>
        <div class="train-content">
            <h2>全国火车车次</h2>
 
            <p class="train-type-choose">
                <span>车型选择：</span>
                <a <@s.if test='${category==null}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi">全部</a>
                <a <@s.if test='${category=="gaotie"}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi/gaotie">G-高铁</a>
                <a <@s.if test='${category=="dongche"}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi/dongche">D-动车</a>
                <a <@s.if test='${category=="tekuai"}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi/tekuai">T-特快</a>
                <a <@s.if test='${category=="kuaiche"}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi/kuaiche">K-快速</a>
                <a <@s.if test='${category=="qita"}'>class="selected"</@s.if> hidefocus="false" href="http://www.lvmama.com/train/checi/qita">其他</a>
            </p>
 
            <div class="train-all">
            	<#if lineInfos?? && lineInfos?size gt 0>
            <#list lineInfos as bean>
            	<a href="http://www.lvmama.com/train/checi/${bean.fullName}" data-type="${bean.fullName ? substring(0,1)}">${bean.fullName}</a>
            </#list>
            </#if>
            </div>
        </div>
    </div>

    <div class="train-slide">
        <div class="train-order">
            <h3>火车票预订</h3>
 
            <form id="searchForm" method="get" action="/">
            <input id="departureCity" type="hidden" name="departureCity">
	        <input id="arrivalCity" type="hidden" name="arrivalCity">
                <p>
                    <span class="warning">*</span>
                    <span class="warning-txt">出发城市</span>
                    <input id="start-address" name="fromCity" type="text" class="input_text autoCInput"
                           autocomplete="off" placeholder="中文/英文" cityid="#departureCity" >
                </p>
 
                <p>
                    <span class="warning">*</span>
                    <span class="warning-txt">到达城市</span>
                    <input id="purpose-address" name="toCity" type="text" class="input_text autoCInput"
                           placeholder="中文/英文"  cityid="#arrivalCity" >
                </p>
 
                <p>
                    <span class="warning">*</span>
                    <span class="warning-txt">出发日期</span>
                    <input readonly="readonly" name="fromDate" type="text" value="${defaultDate}" class="input_text calendar">
                </p>
                <a href="javascript:;" onclick="goTrainSearch(this.form)" id="searchButton">
                    <img src="http://www.lvmama.com/images/searchBtn.gif" class="searchCity"/>
                </a>
            </form>
        </div>
        <div class="train-tool">
            <h3>火车票实用工具</h3>
            <a href="http://www.lvmama.com/train">火车票预订</a>
            <a href="http://www.lvmama.com/train/shikebiao">火车时刻表</a>
            <a href="http://www.lvmama.com/train/checi">车次查询</a>
        </div>
    </div>
</div>
<div class="wrap train-foot">
    <div class="train-foot-content">
        <p>
            <b>驴妈妈旅游网</b>为您提供<a href="http://www.lvmama.com/train">火车票查询预订</a>服务,还提供<a href="http://www.lvmama.com/train/shikebiao">火车时刻表</a>,以及火车时刻表查询信息服务，火车票网上预订,就找驴妈妈！
        </p>
        <p>
            <b class="train-foot-hot">热门导航</b><a href="http://www.lvmama.com/train">火车票预订</a><a href="http://www.lvmama.com/train/shikebiao">火车时刻表</a><a href="http://www.lvmama.com/train/checi">车次查询</a>
        </p>
    </div>
</div>
<div>
<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('aee5a1856692de1c0001','js',fromPlaceCode)" />
	<!-- 底通/End -->
	<!-- footer start-->
	<#include "/common/footer.ftl">
	<div class="hh_cooperate">
	</div>
</div>
 
<div id="js-hot-city" class="hot-city-warp">
    <div class="hot-city"><strong>热门城市</strong> （可直接输入中文名/拼音）</div>
    <div class="hot-city-list">
        <ul id="js-city-list" class="city-list layoutfix">
            <li><a href="javascript:;" hidefocus="false" key="shanghai">上海</a></li>
            <li><a href="javascript:;" hidefocus="false" key="hangzhou">杭州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="suzhou1">苏州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="nanjing">南京</a></li>
            <li><a href="javascript:;" hidefocus="false" key="wuxi">无锡</a></li>
            <li><a href="javascript:;" hidefocus="false" key="beijing">北京</a></li>
            <li><a href="javascript:;" hidefocus="false" key="guangzhou">广州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="shenzhen">深圳</a></li>
            <li><a href="javascript:;" hidefocus="false" key="tianjin">天津</a></li>
            <li><a href="javascript:;" hidefocus="false" key="chengdu">成都</a></li>
            <li><a href="javascript:;" hidefocus="false" key="chongqing">重庆</a></li>
            <li><a href="javascript:;" hidefocus="false" key="xian">西安</a></li>
            <li><a href="javascript:;" hidefocus="false" key="xuzhou">徐州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="zhengzhou">郑州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="wuhan">武汉</a></li>
            <li><a href="javascript:;" hidefocus="false" key="changsha">长沙</a></li>
            <li><a href="javascript:;" hidefocus="false" key="lanzhou">兰州</a></li>
            <li><a href="javascript:;" hidefocus="false" key="dalian">大连</a></li>
            <li><a href="javascript:;" hidefocus="false" key="jinan">济南</a></li>
            <li><a href="javascript:;" hidefocus="false" key="qingdao">青岛</a></li>
        </ul>
    </div>
</div>


	<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/new_v/ui_plugin/jquery.wb_focusImg.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/super_front/ticket_new.js"></script>
	
<script type="text/javascript" src="http://www.lvmama.com/js/train/lv_page.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/autoComplete.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/jquery.calendar.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/js/v3/train/trainpage.js"></script>
</body>
</html>
