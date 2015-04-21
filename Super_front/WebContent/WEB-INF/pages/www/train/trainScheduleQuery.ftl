<!doctype html> 
<html> 
<#setting url_escaping_charset="UTF-8">
<head> 
<meta charset="utf-8" />
	<title>${comSeoIndexPage.seoTitle}</title>
   	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="${comSeoIndexPage.seoKeyword}"/>
	<meta name="description" content="${comSeoIndexPage.seoDescription}"/>
	
	
	<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css,/styles/v3/channel.css,/styles/v4/modules/tip.css" > 
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
        <span><a  rel="nofollow" href="http://www.lvmama.com/train/shikebiao" hidefocus="false">火车时刻表</a></span>
        <i></i>
        <span><#if lineStation??>${lineStation.stationName}</#if>到全国火车时刻表</span>
    </div>
    <div class="train-main">
        <div class="train-query">
        
            <span class="train-query-title">站站查询</span>
            <form id="searchFormZhan" action="/" method="post" style="display:inline-block;"> 
				<input id="departureZhan" type="hidden" name="departureZhan"> 
				<input id="arrivalZhan" type="hidden" name="arrivalZhan"> 
					<input type="text" id="start-station" name = "fromZhan" placeholder="出发站" class="autoCInput" cityID="#departureZhan"> 
					<a id="station-change" href="javascript:;" hidefocus="false"> 
						<img class="changeStation" src="http://www.lvmama.com/images/icon_change.gif"> 
					</a> 
					<input type="text" id="arrival-station" name="toZhan" placeholder="到达站" class="autoCInput" cityID="#arrivalZhan"> 
					<a id="station-search" href="javascript:;" hidefocus="false" onClick="goTrainSearchZhan(this.form)"> 
						<img class="searchStation" src="http://pic.lvmama.com/img/v3/train/trainquery.gif"> 
					</a> 
			</form>
            <em>热门查询站站：</em>
            <span class="train-links">
                <a href="http://www.lvmama.com/train/shikebiao/shanghai-beijing.html">上海-北京</a>
                <a href="http://www.lvmama.com/train/shikebiao/shanghai-hangzhou.html">上海-杭州</a>
                <a href="http://www.lvmama.com/train/shikebiao/shanghai-nanjing.html">上海-南京</a>
            </span>
        </div>
        <div class="train-content">
            <h2><#if lineStation ??>${lineStation.stationName}</#if>火车时刻表<span>共有<i><#if lineInfos??>${lineInfos.size()}</#if></i>列车次</span></h2>
            <p class="train-choose">
                <span>车型选择：</span>
                <a class="selected" hidefocus="false" href="javascript:;" data-type="all">全部</a>
                <a hidefocus="false" href="javascript:;" data-type="G">G-高铁</a>
                <a hidefocus="false" href="javascript:;" data-type="D">D-动车</a>
                <a hidefocus="false" href="javascript:;" data-type="T">T-特快</a>
                <a hidefocus="false" href="javascript:;" data-type="K">K-快速</a>
                <a hidefocus="false" href="javascript:;" data-type="other">其他</a>
            </p>
            <table class="train-station" cellspacing="0" cellpadding="0">
                <colgroup>
                    <col style="width:130px;">
                    <col style="width:130px;">
                    <col style="width:525px;">
                </colgroup>
                <thead>
                <tr>
                    <th>车次/车型</th>
                    <th>发站/到站</th>
                    <th class="train-head-center">停靠站以及到达时刻</th>
                </tr>
                </thead>
                <tbody>
                <#if lineInfos?? && lineInfos?size gt 0>
                	<#list lineInfos as bean>
                <tr data-type="${bean.fullName ? substring(0,1)}">
                    <td>
                        <a class="train-num" target="_blank" id="${bean.lineInfoId}" href="http://www.lvmama.com/train/checi/${bean.fullName}">${bean.fullName}</a>
                    </td>
                    <td>
                        <span class="<@s.if test='${bean.arrivalTime}==-1'>icon_start</@s.if><@s.else>icon_pass</@s.else>">${bean.startStationName}</span>
                        <br>
                        <span class="icon_end">${bean.endStationName}</span>
                    </td>
                    <td>
                    <#if lineStopsMap?? && lineStopsMap?size gt 0>
                    	<#list lineStopsMap?keys as lineStopsKey>
                    		<#if lineStopsKey==bean.lineInfoId>
                    		<#assign valu=lineStopsMap[lineStopsKey]>
                    			<#list valu as stopsBean>
                    				<span class="station-time">
                            			<i>${stopsBean.stationName}</i>
                            			<em>${stopsBean.departureTimeStr}</em>
                        			</span>
                    			</#list>
                    		</#if>
                    	</#list>
                    </#if>
                    </td>
                </tr>
                	</#list>
                </#if>
                </tbody>
            </table>
            	<div id="js-error" class="noResults" style="display: none;"> 
					<span class="tip-icon-big tip-icon-big-error noResultImg"></span> 
					<div class="noResultTxt"> 
					<p class="noResultCity">抱歉，您查询的<span><#if lineStation??>${lineStation.stationName}</#if></span>暂无该车站</p> 
					<p>查询结果仅供参考，如有变动，请以火车站信息为准。</p> 
					<p class="try-other">您可以试试：查询其它车站</p> 
					</div> 
				</div>
            <div class="train-pages"></div>
            <div class="train-tabs">
                <h4>
                    <a href="#" class="train-selected"><#if lineStation??>${lineStation.stationName}</#if>相关火车时刻表</a>
                </h4>
                <dl class="trains-wrap">
                    <dd>
                    <#if lineStations?? && lineStations?size gt 0>
                    <#list lineStations as stationBean>
                        <a href="http://www.lvmama.com/train/shikebiao/${stationBean.stationPinyin}">${stationBean.stationName}火车时刻表</a>
                    </#list>
                    </#if>
                    </dd>
                </dl>
            </div>
            <div class="train-hot-city">
                <h4>热门城市火车时刻表</h4>
                <div class="train-citys-wrap">
                	<a href="http://www.lvmama.com/train/shikebiao/shanghai">上海</a><a href="http://www.lvmama.com/train/shikebiao/beijing">北京</a><a href="http://www.lvmama.com/train/shikebiao/hangzhou">杭州</a><a href="http://www.lvmama.com/train/shikebiao/nanjing">南京</a><a href="http://www.lvmama.com/train/shikebiao/suzhou">苏州</a>
                	<a href="http://www.lvmama.com/train/shikebiao/xuzhou">徐州</a><a href="http://www.lvmama.com/train/shikebiao/wuxi">无锡</a><a href="http://www.lvmama.com/train/shikebiao/qingdao">青岛</a><a href="http://www.lvmama.com/train/shikebiao/guangzhou">广州</a><a href="http://www.lvmama.com/train/shikebiao/jinan">济南</a>
                	<a href="http://www.lvmama.com/train/shikebiao/tianjin">天津</a><a href="http://www.lvmama.com/train/shikebiao/changzhou">常州</a><a href="http://www.lvmama.com/train/shikebiao/taian">泰安</a><a href="">蚌埠</a><a href="http://www.lvmama.com/train/shikebiao/ningbo">宁波</a><a href="http://www.lvmama.com/train/shikebiao/wuhan">武汉</a><a href="http://www.lvmama.com/train/shikebiao/zhengzhou">郑州</a><a href="http://www.lvmama.com/train/shikebiao/changsha">长沙</a><a href="http://www.lvmama.com/train/shikebiao/chongqing">重庆</a><a href="http://www.lvmama.com/train/shikebiao/chengdu">成都</a>
                	<a href="http://www.lvmama.com/train/shikebiao/dalian">大连</a><a href="http://www.lvmama.com/train/shikebiao/lanzhou">兰州</a><a href="http://www.lvmama.com/train/shikebiao/xian">西安</a><a href="http://www.lvmama.com/train/shikebiao/shenzhen">深圳</a>
                </div>
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
	

<script type="text/javascript" src="http://www.lvmama.com/js/train/autoComplete.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/jquery.calendar.js"></script>
<script src="http://pic.lvmama.com/js/v3/train/page.js"></script>
<script src="http://pic.lvmama.com/js/v3/train/trainpage.js"></script> 

</body>
</html>
