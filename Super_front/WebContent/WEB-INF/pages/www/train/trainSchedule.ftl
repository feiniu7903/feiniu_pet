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
        <span>火车时刻表</span>
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
            <h2>全国火车时刻表</h2>
            <p class="train-letter">
                <span>按拼音首字母查询</span>
                <span>
                    <a href="#A" class="letter-active">A</a>
                    <a href="#B" class="letter-active">B</a>
                    <a href="#C" class="letter-active">C</a>
                    <a href="#D" class="letter-active">D</a>
                    <a href="#E" class="letter-active">E</a>
                    <a href="#F" class="letter-active">F</a>
                    <a href="#G" class="letter-active">G</a>
                </span>
                <span>
                    <a href="#H" class="letter-active">H</a>
                    <a href="#I" class="letter-active">I</a>
                    <a href="#J" class="letter-active">J</a>
                    <a href="#K" class="letter-active">K</a>
                    <a href="#L" class="letter-active">L</a>
                    <a href="#M" class="letter-active">M</a>
                    <a href="#N" class="letter-active">N</a>
                </span>
                <span>
                    <a href="#O" class="letter-active">O</a>
                    <a href="#P" class="letter-active">P</a>
                    <a href="#Q" class="letter-active">Q</a>
                    <a href="#R" class="letter-active">R</a>
                    <a href="#S" class="letter-active">S</a>
                    <a href="#T" class="letter-active">T</a>
                </span>
                <span>
                    <a href="#U" class="letter-active">U</a>
                    <a href="#V" class="letter-active">V</a>
                    <a href="#W" class="letter-active">W</a>
                    <a href="#X" class="letter-active">X</a>
                    <a href="#Y" class="letter-active">Y</a>
                    <a href="#Z" class="letter-active">Z</a>
                </span>
            </p>
            <dl class="area-times-wrap">
            <#if scheduleMap?? && scheduleMap?size gt 0>
            <#list scheduleMap?keys as scheduleKey>
            	<#assign val=scheduleMap[scheduleKey]>
                <dd class="area-times-first">
                    <i><a name="${scheduleKey}">${scheduleKey}</a></i>
                    <div class="area-times">
                    <#if val?? && val?size gt 0>
                    	<#list val as bean>
                        <a href="http://www.lvmama.com/train/shikebiao/${bean.stationPinyin}">${bean.stationName}时刻表</a>
                        </#list>
                    </#if>    
                    </div>
                </dd>
            </#list>
            </#if>
            </dl>
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
	<#include "/WEB-INF/pages/staticHtml/friend_link/train_shikebiao.ftl">
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
