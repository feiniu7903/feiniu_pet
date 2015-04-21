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


	
</head>
<body class="train">
<@s.set var="pageMark" value="'train'" />
<#include "/common/header.ftl">
<input type="hidden" value="ticketPage" id="pageName">
	<!-- wrap\\ 1 -->
	<div class="wrap wrap-quick">
	    <div class="col-w-ticket">
	        <div class="ticket-search train-search"><form id="searchForm" onsubmit="return false">
	        <input id="departureCity" type="hidden" name="departureCity">
	        <input id="arrivalCity" type="hidden" name="arrivalCity">
	            <div class="innerbox">
	                <div class="head">
	                    <h1><span class="xicon icon-train"></span> 快铁驴行-国内火车票预订</h1>
	                </div>
	                <div class="content form-hor">
	                    <div class="form-columns">
	                        <div class="control-group">
	                            <label class="control-label" for=""><i class="req">*</i>出发城市：</label>
	                            <div class="controls">
	                                <input id="start-address" name="fromCity" cityID="#departureCity" class="input-text autoCInput" type="text" placeholder="中文/拼音" autocomplete="off">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label" for=""><i class="req">*</i>到达城市：</label>
	                            <div class="controls">
	                                <input cityID="#arrivalCity" name="toCity" class="input-text autoCInput" id="purpose-address" type="text" placeholder="中文/拼音" autocomplete="off">
	                            </div>
	                        </div>
	                        <a href="javascript:;" id="js-change-city" class="xicon change-city" title="调换出发地和目的地">换</a>
	                        <div class="hr_a"></div>
	                    </div>
	                    <div class="form-columns">
	                        <div class="control-group">
	                            <label class="control-label" for=""><i class="req">*</i>出发日期：</label>
	                            <div class="controls">
	                                <div id="js-dinput-date" class="dinput dinput-date">
	                                    <input class="input-date" type="text" maxlength="10" placeholder="2013-06-10" readonly="readonly" name="fromDate" value="${defaultDate}">
	                                    <div class="date-info">
	                                        <i class="icon-date"></i>
	                                        <span class="text-info">${showWeekDay}</span>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label" for="">车次：</label>
	                            <div class="controls">
	                                <input class="input-text input-small" type="text" name="lineName" placeholder="如T26">
	                            </div>
	                        </div>
	                        <p><button id="searchButton"  class="xicon searchbtn" type="button" onclick="goTrainSearch(this.form)">搜索</button></p>
	                    </div>
	                    
	                </div>
	            </div></form>
	        </div> <!-- //.ticket-search -->
	        <div class="hr_d"></div>
	        
	        
	       <div class="cbox train-cbox">
        <div class="ctitle">
            <!--<h3>热门线路</h3>-->
            <ul class="tabnav">
                <li class="selected"><a href="javascript:;" hidefocus="false">高铁<i class="css_arrow"></i></a></li>
                <li><a href="javascript:;" hidefocus="false">动车<i class="css_arrow"></i></a></li>
                <li><a href="javascript:;" hidefocus="false">普通列车<i class="css_arrow"></i></a></li>
            </ul>
        </div>
        <div class="cCitys JS_tabnav">
                <a href="javascript:;" hidefocus="false" class="selected">上海</a>
                <a href="javascript:;" hidefocus="false">北京</a>
                <a href="javascript:;" hidefocus="false">杭州</a>
                <a href="javascript:;" hidefocus="false">南京</a>
        </div>
        <div class="JS_tabsboxWrap">
            <div class="content JS_tabsbox tabsBoxSelected" style="clear:both;display:block" >
            <div class="tabcon selected">
                <ul class="train-list train-list-first">
                    <li><a href="javascript:;" key="shanghai-beijing"><dfn>&yen;<i>553.0</i>起</dfn><span>上海 — 北京</span></a></li>
                    <li><a href="javascript:;" key="shanghai-hangzhou"><dfn>&yen;<i>73.0</i>起</dfn><span>上海 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-kunshan"><dfn>&yen;<i>24.5</i>起</dfn><span>上海 — 昆山</span></a></li>
                    <li><a href="javascript:;" key="shanghai-nanjing"><dfn>&yen;<i>134.5</i>起</dfn><span>上海 — 南京</span></a></li>
                    <li><a href="javascript:;" key="shanghai-suzhou"><dfn>&yen;<i>29.5</i>起</dfn><span>上海 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-jinan"><dfn>&yen;<i>398.5</i>起</dfn><span>上海 — 济南</span></a></li>
                </ul>
                <ul class="train-list">
                	<li><a href="javascript:;" key="shanghai-wuxi"><dfn>&yen;<i>49.5</i>起</dfn><span>上海 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="shanghai-changzhou"><dfn>&yen;<i>69.5</i>起</dfn><span>上海 — 常州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-hefei"><dfn>&yen;<i>195.0</i>起</dfn><span>上海 — 合肥</span></a></li>
                    <li><a href="javascript:;" key="shanghai-tianjin"><dfn>&yen;<i>508.5</i>起</dfn><span>上海 — 天津</span></a></li>
                    <li><a href="javascript:;" key="shanghai-xuzhou"><dfn>&yen;<i>279.0</i>起</dfn><span>上海 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-qingdao"><dfn>&yen;<i>518.0</i>起</dfn><span>上海 — 青岛</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="shanghai-ningbodong"><dfn>&yen;<i>146.5</i>起</dfn><span>上海 — 宁波东</span></a></li>
                    <li><a href="javascript:;" key="shanghai-zhenjiang"><dfn>&yen;<i>104.5</i>起</dfn><span>上海 — 镇江</span></a></li>
                    <li><a href="javascript:;" key="shanghai-qufu"><dfn>&yen;<i>344.0</i>起</dfn><span>上海 — 曲阜</span></a></li>
                    <li><a href="javascript:;" key="shanghai-bengbu"><dfn>&yen;<i>214.0</i>起</dfn><span>上海 — 蚌埠</span></a></li>
                    <li><a href="javascript:;" key="shanghai-taian"><dfn>&yen;<i>374.0</i>起</dfn><span>上海 — 泰安</span></a></li>
                    <li><a href="javascript:;" key="shanghai-zibo"><dfn>&yen;<i>433.5</i>起</dfn><span>上海 — 淄博</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-shanghai"><dfn>&yen;<i>553.0</i>起</dfn><span>北京 — 上海</span></a></li>
                    <li><a href="javascript:;" key="beijing-nanjing"><dfn>&yen;<i>443.5</i>起</dfn><span>北京 — 南京</span></a></li>
                    <li><a href="javascript:;" key="beijing-xian"><dfn>&yen;<i>513.5</i>起</dfn><span>北京 — 西安</span></a></li>
                    <li><a href="javascript:;" key="beijing-qingdao"><dfn>&yen;<i>314.0</i>起</dfn><span>北京 — 青岛</span></a></li>
                    <li><a href="javascript:;" key="beijing-zhengzhou"><dfn>&yen;<i>309.0</i>起</dfn><span>北京 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="beijing-jinan"><dfn>&yen;<i>184.5</i>起</dfn><span>北京 — 济南</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-taiyuan"><dfn>&yen;<i>194.0</i>起</dfn><span>北京 — 太原</span></a></li>
                    <li><a href="javascript:;" key="beijing-tianjin"><dfn>&yen;<i>54.5</i>起</dfn><span>北京 — 天津</span></a></li>
                    <li><a href="javascript:;" key="beijing-hangzhou"><dfn>&yen;<i>538.5</i>起</dfn><span>北京 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="beijing-wuhan"><dfn>&yen;<i>519.5</i>起</dfn><span>北京 — 武汉</span></a></li>
                    <li><a href="javascript:;" key="beijing-guangzhou"><dfn>&yen;<i>862.0</i>起</dfn><span>北京 — 广州</span></a></li>
                    <li><a href="javascript:;" key="beijing-shijiazhuang"><dfn>&yen;<i>128.5</i>起</dfn><span>北京 — 石家庄</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-hefei"><dfn>&yen;<i>427.5</i>起</dfn><span>北京 — 合肥</span></a></li>
                    <li><a href="javascript:;" key="beijing-suzhou"><dfn>&yen;<i>523.5</i>起</dfn><span>北京 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="beijing-xuzhou"><dfn>&yen;<i>309.0</i>起</dfn><span>北京 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="beijing-changsha"><dfn>&yen;<i>649.0</i>起</dfn><span>北京 — 长沙</span></a></li>
                    <li><a href="javascript:;" key="beijing-wuxi"><dfn>&yen;<i>513.5</i>起</dfn><span>北京 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="beijing-taian"><dfn>&yen;<i>214.0</i>起</dfn><span>北京 — 泰安</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-shanghai"><dfn>&yen;<i>73.0</i>起</dfn><span>杭州 — 上海</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-beijing"><dfn>&yen;<i>538.5</i>起</dfn><span>杭州 — 北京</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-nanjing"><dfn>&yen;<i>117.5</i>起</dfn><span>杭州 — 南京</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-jinan"><dfn>&yen;<i>383.5</i>起</dfn><span>杭州 — 济南</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-tianjin"><dfn>&yen;<i>494.0</i>起</dfn><span>杭州 — 天津</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-suzhou"><dfn>&yen;<i>110.0</i>起</dfn><span>杭州 — 苏州</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-hefei"><dfn>&yen;<i>178.5</i>起</dfn><span>杭州 — 合肥</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-ningbodong"><dfn>&yen;<i>73.5</i>起</dfn><span>杭州 — 宁波东</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-xuzhou"><dfn>&yen;<i>265.5</i>起</dfn><span>杭州 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-wenzhou"><dfn>&yen;<i>153.0</i>起</dfn><span>杭州 — 温州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-wuxi"><dfn>&yen;<i>122.5</i>起</dfn><span>杭州 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-changzhou"><dfn>&yen;<i>143.5</i>起</dfn><span>杭州 — 常州</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-wuhan"><dfn>&yen;<i>284.5</i>起</dfn><span>杭州 — 武汉</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-qufu"><dfn>&yen;<i>330.5</i>起</dfn><span>杭州 — 曲阜</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-zaozhuang"><dfn>&yen;<i>292.5</i>起</dfn><span>杭州 — 枣庄</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-jiaxing"><dfn>&yen;<i>34.5</i>起</dfn><span>杭州 — 嘉兴</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-zhenjiang"><dfn>&yen;<i>178.5</i>起</dfn><span>杭州 — 镇江</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-jiashan"><dfn>&yen;<i>42.0</i>起</dfn><span>杭州 — 嘉善</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-shanghai"><dfn>&yen;<i>134.5</i>起</dfn><span>南京 — 上海</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>443.5</i>起</dfn><span>南京 — 北京</span></a></li>
                    <li><a href="javascript:;" key="nanjing-hangzhou"><dfn>&yen;<i>117.5</i>起</dfn><span>南京 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-suzhou"><dfn>&yen;<i>94.5</i>起</dfn><span>南京 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-xuzhou"><dfn>&yen;<i>149.5</i>起</dfn><span>南京 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-tianjin"><dfn>&yen;<i>393.5</i>起</dfn><span>南京 — 天津</span></a></li>
                    
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-jinan"><dfn>&yen;<i>279.0</i>起</dfn><span>南京 — 济南</span></a></li>
                    <li><a href="javascript:;" key="nanjing-qingdao"><dfn>&yen;<i>398.5</i>起</dfn><span>南京 — 青岛</span></a></li>
                    <li><a href="javascript:;" key="nanjing-wuxi"><dfn>&yen;<i>79.5</i>起</dfn><span>南京 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="nanjing-ningbodong"><dfn>&yen;<i>191.0</i>起</dfn><span>南京 — 宁波东</span></a></li>
                    <li><a href="javascript:;" key="nanjing-changzhou"><dfn>&yen;<i>59.5</i>起</dfn><span>南京 — 常州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-hefei"><dfn>&yen;<i>60.5</i>起</dfn><span>南京 — 合肥</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-changsha"><dfn>&yen;<i>333.0</i>起</dfn><span>南京 — 长沙</span></a></li>
                    <li><a href="javascript:;" key="nanjing-kunshan"><dfn>&yen;<i>114.5</i>起</dfn><span>南京 — 昆山</span></a></li>
                    <li><a href="javascript:;" key="nanjing-qufu"><dfn>&yen;<i>224.0</i>起</dfn><span>南京 — 曲阜</span></a></li>
                    <li><a href="javascript:;" key="nanjing-bengbu"><dfn>&yen;<i>79.5</i>起</dfn><span>南京 — 蚌埠</span></a></li>
                    <li><a href="javascript:;" key="nanjing-wenzhou"><dfn>&yen;<i>270.5</i>起</dfn><span>南京 — 温州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-zhenjiang"><dfn>&yen;<i>29.5</i>起</dfn><span>南京 — 镇江</span></a></li>
                </ul>
            </div>
        </div>
            <div class="content JS_tabsbox" style="clear:both;display:none">
            <div class="tabcon selected">
                <ul class="train-list train-list-first">
                    <li><a href="javascript:;" key="shanghai-beijing"><dfn>&yen;<i>309.0</i>起</dfn><span>上海 — 北京</span></a></li>
                    <li><a href="javascript:;" key="shanghai-wuhan"><dfn>&yen;<i>258.0</i>起</dfn><span>上海 — 武汉</span></a></li>
                    <li><a href="javascript:;" key="shanghai-hangzhou"><dfn>&yen;<i>46.5</i>起</dfn><span>上海 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-hefei"><dfn>&yen;<i>150.0</i>起</dfn><span>上海 — 合肥</span></a></li>
                    <li><a href="javascript:;" key="shanghai-zhengzhou"><dfn>&yen;<i>236.5</i>起</dfn><span>上海 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-wenzhou"><dfn>&yen;<i>175.5</i>起</dfn><span>上海 — 温州</span></a></li>
                </ul>
                <ul class="train-list">
                	
                    <li><a href="javascript:;" key="shanghai-ningbodong"><dfn>&yen;<i>96.0</i>起</dfn><span>上海 — 宁波东</span></a></li>
                	<li><a href="javascript:;" key="shanghai-xiamen"><dfn>&yen;<i>328.0</i>起</dfn><span>上海 — 厦门</span></a></li>
                    <li><a href="javascript:;" key="shanghai-fuzhou"><dfn>&yen;<i>261.5</i>起</dfn><span>上海 — 福州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-nanchang"><dfn>&yen;<i>237.0</i>起</dfn><span>上海 — 南昌</span></a></li>
                    <li><a href="javascript:;" key="shanghai-yiwu"><dfn>&yen;<i>89.5</i>起</dfn><span>上海 — 义乌</span></a></li>
                    <li><a href="javascript:;" key="shanghai-suzhou"><dfn>&yen;<i>22.5</i>起</dfn><span>上海 — 苏州</span></a></li>
                </ul>
                <ul class="train-list">                
                    <li><a href="javascript:;" key="shanghai-nanjing"><dfn>&yen;<i>87.5</i>起</dfn><span>上海 — 南京</span></a></li>
                    <li><a href="javascript:;" key="shanghai-taizhou"><dfn>&yen;<i>139.5</i>起</dfn><span>上海 — 台州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-jinhua"><dfn>&yen;<i>103.0</i>起</dfn><span>上海 — 金华</span></a></li>
                    <li><a href="javascript:;" key="shanghai-changzhou"><dfn>&yen;<i>49.5</i>起</dfn><span>上海 — 常州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-wuxi"><dfn>&yen;<i>34.5</i>起</dfn><span>上海 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="shanghai-shaoxing"><dfn>&yen;<i>60.0</i>起</dfn><span>上海 — 绍兴</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-shenyang"><dfn>&yen;<i>206.0</i>起</dfn><span>北京 — 沈阳</span></a></li>
                    <li><a href="javascript:;" key="beijing-qingdao"><dfn>&yen;<i>249.0</i>起</dfn><span>北京 — 青岛</span></a></li>
                    <li><a href="javascript:;" key="beijing-haerbin"><dfn>&yen;<i>306.5</i>起</dfn><span>北京 — 哈尔滨</span></a></li>
                    <li><a href="javascript:;" key="beijing-changchun"><dfn>&yen;<i>265.5</i>起</dfn><span>北京 — 长春</span></a></li>
                    <li><a href="javascript:;" key="beijing-taiyuan"><dfn>&yen;<i>152.0</i>起</dfn><span>北京 — 太原</span></a></li>
                    <li><a href="javascript:;" key="beijing-shanghai"><dfn>&yen;<i>309.0</i>起</dfn><span>北京 — 上海</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-beidaihe"><dfn>&yen;<i>81.5</i>起</dfn><span>北京 — 北戴河</span></a></li>
                    <li><a href="javascript:;" key="beijing-jinan"><dfn>&yen;<i>124.5</i>起</dfn><span>北京 — 济南</span></a></li>
                    <li><a href="javascript:;" key="beijing-suzhou"><dfn>&yen;<i>292.0</i>起</dfn><span>北京 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="beijing-dalian"><dfn>&yen;<i>283.5</i>起</dfn><span>北京 — 大连</span></a></li>
                    <li><a href="javascript:;" key="beijing-jilin"><dfn>&yen;<i>285.5</i>起</dfn><span>北京 — 吉林</span></a></li>
                    <li><a href="javascript:;" key="beijing-weifang"><dfn>&yen;<i>194.0</i>起</dfn><span>北京 — 潍坊</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-nanjing"><dfn>&yen;<i>258.5</i>起</dfn><span>北京 — 南京</span></a></li>
                    <li><a href="javascript:;" key="beijing-shijiazhuang"><dfn>&yen;<i>81.5</i>起</dfn><span>北京 — 石家庄</span></a></li>
                    <li><a href="javascript:;" key="beijing-tianjin"><dfn>&yen;<i>39.5</i>起</dfn><span>北京 — 天津</span></a></li>
                    <li><a href="javascript:;" key="beijing-zibo"><dfn>&yen;<i>164.0</i>起</dfn><span>北京 — 淄博</span></a></li>
                    <li><a href="javascript:;" key="beijing-zhengzhou"><dfn>&yen;<i>214.0</i>起</dfn><span>北京 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="beijing-wuhan"><dfn>&yen;<i>379.5</i>起</dfn><span>北京 — 武汉</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-shanghai"><dfn>&yen;<i>46.5</i>起</dfn><span>杭州 — 上海</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-xiamen"><dfn>&yen;<i>281.5</i>起</dfn><span>杭州 — 厦门</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-wenzhou"><dfn>&yen;<i>129.0</i>起</dfn><span>杭州 — 温州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-fuzhou"><dfn>&yen;<i>215.0</i>起</dfn><span>杭州 — 福州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-ningbodong"><dfn>&yen;<i>49.5</i>起</dfn><span>杭州 — 宁波东</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-nanchang"><dfn>&yen;<i>190.5</i>起</dfn><span>杭州 — 南昌</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-wenling"><dfn>&yen;<i>100.0</i>起</dfn><span>杭州 — 温岭</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-taizhou"><dfn>&yen;<i>92.5</i>起</dfn><span>杭州 — 台州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-yiwu"><dfn>&yen;<i>40.5</i>起</dfn><span>杭州 — 义乌</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-ruian"><dfn>&yen;<i>135.5</i>起</dfn><span>杭州 — 瑞安</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-jinhua"><dfn>&yen;<i>54.5</i>起</dfn><span>杭州 — 金华</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-wuxi"><dfn>&yen;<i>80.0</i>起</dfn><span>杭州 — 无锡</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-suzhou"><dfn>&yen;<i>69.5</i>起</dfn><span>杭州 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-nanjing"><dfn>&yen;<i>79.0</i>起</dfn><span>杭州 — 南京</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-changsha"><dfn>&yen;<i>242.5</i>起</dfn><span>杭州 — 长沙</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-zhengzhou"><dfn>&yen;<i>233.5</i>起</dfn><span>杭州 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-yueqing"><dfn>&yen;<i>118.5</i>起</dfn><span>杭州 — 乐清</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-quanzhou"><dfn>&yen;<i>260.5</i>起</dfn><span>杭州 — 泉州</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-shanghai"><dfn>&yen;<i>87.5</i>起</dfn><span>南京 — 上海</span></a></li>
                    <li><a href="javascript:;" key="nanjing-kunshan"><dfn>&yen;<i>74.5</i>起</dfn><span>南京 — 昆山</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>258.5</i>起</dfn><span>南京 — 北京</span></a></li>
                    <li><a href="javascript:;" key="nanjing-zhengzhou"><dfn>&yen;<i>204.0</i>起</dfn><span>南京 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-suzhou"><dfn>&yen;<i>63.5</i>起</dfn><span>南京 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-hefei"><dfn>&yen;<i>60.5</i>起</dfn><span>南京 — 合肥</span></a></li>
                    
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-xiamen"><dfn>&yen;<i>360.5</i>起</dfn><span>南京 — 厦门</span></a></li>
                    <li><a href="javascript:;" key="nanjing-hangzhou"><dfn>&yen;<i>79.0</i>起</dfn><span>南京 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-wuxi"><dfn>&yen;<i>53.5</i>起</dfn><span>南京 — 无锡</span></a></li>
                    <li><a href="javascript:;" key="nanjing-changzhou"><dfn>&yen;<i>39.5</i>起</dfn><span>南京 — 常州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-ningbodong"><dfn>&yen;<i>128.5</i>起</dfn><span>南京 — 宁波东</span></a></li>
                    <li><a href="javascript:;" key="nanjing-wuhan"><dfn>&yen;<i>167.0</i>起</dfn><span>南京 — 武汉</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-jinan"><dfn>&yen;<i>189.0</i>起</dfn><span>南京 — 济南</span></a></li>
                    <li><a href="javascript:;" key="nanjing-tianjin"><dfn>&yen;<i>278.5</i>起</dfn><span>南京 — 天津</span></a></li>
                    <li><a href="javascript:;" key="nanjing-quanzhou"><dfn>&yen;<i>168.5</i>起</dfn><span>南京 — 泉州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-xuzhou"><dfn>&yen;<i>104.5</i>起</dfn><span>南京 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-yichang"><dfn>&yen;<i>253.0</i>起</dfn><span>南京 — 宜昌</span></a></li>
                    <li><a href="javascript:;" key="nanjing-fuzhou"><dfn>&yen;<i>294.0</i>起</dfn><span>南京 — 福州</span></a></li>
                </ul>
            </div>
        </div>
            <div class="content JS_tabsbox" style="clear:both;display:none">
            <div class="tabcon selected">
                <ul class="train-list train-list-first">
                    <li><a href="javascript:;" key="shanghai-guangzhou"><dfn>&yen;<i>201.0</i>起</dfn><span>上海 — 广州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-xian"><dfn>&yen;<i>180.5</i>起</dfn><span>上海 — 西安</span></a></li>
                    <li><a href="javascript:;" key="shanghai-chengdu"><dfn>&yen;<i>254.5</i>起</dfn><span>上海 — 成都</span></a></li>
                    <li><a href="javascript:;" key="shanghai-shenzhen"><dfn>&yen;<i>234.0</i>起</dfn><span>上海 — 深圳</span></a></li>
                    <li><a href="javascript:;" key="shanghai-hangzhou"><dfn>&yen;<i>21.5</i>起</dfn><span>上海 — 杭州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-beijing"><dfn>&yen;<i>156.5</i>起</dfn><span>上海 — 北京</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="shanghai-nanchang"><dfn>&yen;<i>105.0</i>起</dfn><span>上海 — 南昌</span></a></li>
                    <li><a href="javascript:;" key="shanghai-lasa"><dfn>&yen;<i>402.5</i>起</dfn><span>上海 — 拉萨</span></a></li>
                    <li><a href="javascript:;" key="shanghai-wulumuqi"><dfn>&yen;<i>385.5</i>起</dfn><span>上海 — 乌鲁木齐</span></a></li>
                    <li><a href="javascript:;" key="shanghai-kunming"><dfn>&yen;<i>278.5</i>起</dfn><span>上海 — 昆明</span></a></li>
                    <li><a href="javascript:;" key="shanghai-zhengzhou"><dfn>&yen;<i>128.5</i>起</dfn><span>上海 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-changsha"><dfn>&yen;<i>148.5</i>起</dfn><span>上海 — 长沙</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="shanghai-taiyuan"><dfn>&yen;<i>180.5</i>起</dfn><span>上海 — 太原</span></a></li>
                    <li><a href="javascript:;" key="shanghai-suzhou"><dfn>&yen;<i>12.5</i>起</dfn><span>上海 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="shanghai-haerbin"><dfn>&yen;<i>273.5</i>起</dfn><span>上海 — 哈尔滨</span></a></li>
                    <li><a href="javascript:;" key="shanghai-nanjing"><dfn>&yen;<i>40.5</i>起</dfn><span>上海 — 南京</span></a></li>
                    <li><a href="javascript:;" key="shanghai-guiyang"><dfn>&yen;<i>229.0</i>起</dfn><span>上海 — 贵阳</span></a></li>
                    <li><a href="javascript:;" key="shanghai-lanzhou"><dfn>&yen;<i>240.0</i>起</dfn><span>上海 — 兰州</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-xian"><dfn>&yen;<i>148.5</i>起</dfn><span>北京 — 西安</span></a></li>
                    <li><a href="javascript:;" key="beijing-haerbin"><dfn>&yen;<i>98.5</i>起</dfn><span>北京 — 哈尔滨</span></a></li>
                    <li><a href="javascript:;" key="beijing-huhehante"><dfn>&yen;<i>72.0</i>起</dfn><span>北京 — 呼和浩特</span></a></li>
                    <li><a href="javascript:;" key="beijing-beidaihe"><dfn>&yen;<i>36.5</i>起</dfn><span>北京 — 北戴河</span></a></li>
                    <li><a href="javascript:;" key="beijing-guangzhou"><dfn>&yen;<i>251.0</i>起</dfn><span>北京 — 广州</span></a></li>
                    <li><a href="javascript:;" key="beijing-dalian"><dfn>&yen;<i>138.5</i>起</dfn><span>北京 — 大连</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-chengdu"><dfn>&yen;<i>229.0</i>起</dfn><span>北京 — 成都</span></a></li>
                    <li><a href="javascript:;" key="beijing-chongqing"><dfn>&yen;<i>224.0</i>起</dfn><span>北京 — 重庆</span></a></li>
                    <li><a href="javascript:;" key="beijing-wuhan"><dfn>&yen;<i>148.5</i>起</dfn><span>北京 — 武汉</span></a></li>
                    <li><a href="javascript:;" key="beijing-nanchang"><dfn>&yen;<i>152.5</i>起</dfn><span>北京 — 南昌</span></a></li>
                    <li><a href="javascript:;" key="beijing-changsha"><dfn>&yen;<i>189.5</i>起</dfn><span>北京 — 长沙</span></a></li>
                    <li><a href="javascript:;" key="beijing-hangzhou"><dfn>&yen;<i>189.5</i>起</dfn><span>北京 — 杭州</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="beijing-lanzhou"><dfn>&yen;<i>189.5</i>起</dfn><span>北京 — 兰州</span></a></li>
                    <li><a href="javascript:;" key="beijing-zhengzhou"><dfn>&yen;<i>93.0</i>起</dfn><span>北京 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="beijing-shenzhen"><dfn>&yen;<i>254.5</i>起</dfn><span>北京 — 深圳</span></a></li>
                    <li><a href="javascript:;" key="beijing-changchun"><dfn>&yen;<i>83.5</i>起</dfn><span>北京 — 长春</span></a></li>
                    <li><a href="javascript:;" key="beijing-wulumuqi"><dfn>&yen;<i>313.5</i>起</dfn><span>北京 — 乌鲁木齐</span></a></li>
                    <li><a href="javascript:;" key="beijing-shijiazhuang"><dfn>&yen;<i>41.5</i>起</dfn><span>北京 — 石家庄</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-beijing"><dfn>&yen;<i>189.5</i>起</dfn><span>杭州 — 北京</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-xian"><dfn>&yen;<i>166.5</i>起</dfn><span>杭州 — 西安</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-guangzhou"><dfn>&yen;<i>192.0</i>起</dfn><span>杭州 — 广州</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-wuhan"><dfn>&yen;<i>128.5</i>起</dfn><span>杭州 — 武汉</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-shanghai"><dfn>&yen;<i>21.5</i>起</dfn><span>杭州 — 上海</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-zhengzhou"><dfn>&yen;<i>65.5</i>起</dfn><span>杭州 — 郑州</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-shenzhen"><dfn>&yen;<i>212.5</i>起</dfn><span>杭州 — 深圳</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-hefei"><dfn>&yen;<i>31.5</i>起</dfn><span>杭州 — 合肥</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-chengdu"><dfn>&yen;<i>271.0</i>起</dfn><span>杭州 — 成都</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-kunming"><dfn>&yen;<i>268.5</i>起</dfn><span>杭州 — 昆明</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-changsha"><dfn>&yen;<i>128.5</i>起</dfn><span>杭州 — 长沙</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-yiwu"><dfn>&yen;<i>10.5</i>起</dfn><span>杭州 — 义乌</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="hangzhou-guiyang"><dfn>&yen;<i>213.0</i>起</dfn><span>杭州 — 贵阳</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-fuyang"><dfn>&yen;<i>44.0</i>起</dfn><span>杭州 — 阜阳</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-nanchang"><dfn>&yen;<i>91.0</i>起</dfn><span>杭州 — 南昌</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-chongqing"><dfn>&yen;<i>224.0</i>起</dfn><span>杭州 — 重庆</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-haerbin"><dfn>&yen;<i>250.5</i>起</dfn><span>杭州 — 哈尔滨</span></a></li>
                    <li><a href="javascript:;" key="hangzhou-lanzhou"><dfn>&yen;<i>251.0</i>起</dfn><span>杭州 — 兰州</span></a></li>
                </ul>
            </div>
            <div class="tabcon">
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>152.5</i>起</dfn><span>南京 — 西安</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>229.0</i>起</dfn><span>南京 — 成都</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>40.5</i>起</dfn><span>南京 — 上海</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>130.5</i>起</dfn><span>南京 — 北京</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>81.0</i>起</dfn><span>南京 — 南昌</span></a></li>
                    <li><a href="javascript:;" key="nanjing-beijing"><dfn>&yen;<i>206.0</i>起</dfn><span>南京 — 广州</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-zhengzhou"><dfn>&yen;<i>45.0</i>起</dfn><span>南京 — 郑州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-xuzhou"><dfn>&yen;<i>25.5</i>起</dfn><span>南京 — 徐州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-suzhou"><dfn>&yen;<i>29.5</i>起</dfn><span>南京 — 苏州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-huangshan"><dfn>&yen;<i>22.5</i>起</dfn><span>南京 — 黄山</span></a></li>
                    <li><a href="javascript:;" key="nanjing-shenzhen"><dfn>&yen;<i>234.0</i>起</dfn><span>南京 — 深圳</span></a></li>
                    <li><a href="javascript:;" key="nanjing-haerbin"><dfn>&yen;<i>221.0</i>起</dfn><span>南京 — 哈尔滨</span></a></li>
                </ul>
                <ul class="train-list">
                    <li><a href="javascript:;" key="nanjing-yangzhou"><dfn>&yen;<i>16.5</i>起</dfn><span>南京 — 扬州</span></a></li>
                    <li><a href="javascript:;" key="nanjing-nantong"><dfn>&yen;<i>43.5</i>起</dfn><span>南京 — 南通</span></a></li>
                    <li><a href="javascript:;" key="nanjing-kunming"><dfn>&yen;<i>278.5</i>起</dfn><span>南京 — 昆明</span></a></li>
                    <li><a href="javascript:;" key="nanjing-taiyuan"><dfn>&yen;<i>152.5</i>起</dfn><span>南京 — 太原</span></a></li>
                    <li><a href="javascript:;" key="nanjing-wulumuqi"><dfn>&yen;<i>360.0</i>起</dfn><span>南京 — 乌鲁木齐</span></a></li>
                    <li><a href="javascript:;" key="nanjing-chongqing"><dfn>&yen;<i>206.0</i>起</dfn><span>南京 — 重庆</span></a></li>
                </ul>
            </div>
        </div>
        </div>
    </div>
</div>
	    
	    <div class="aside-ticket">
	        <div class="sidebox">
	            <div class="iflt_slider">
            		<ul class="iflt_slider_list">
                	<!--a href="" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ob_iflight/pro1.jpg"></a-->
                	<li><a href="" target="_blank"><img src="http://s1.lvjs.com.cn/uploads/pc/place2/16183/1371714699782.jpg"></a></li>
            		</ul>
        		</div>
	        </div>
	        <div class="hr_d"></div>
	        
	        <div class="train-brand">
            <div class="info_item">
                <h4>品牌保障</h4>
                <p>驴妈妈旗下产品</p>
            </div>
            <div class="info_item">
                <h4>出票快速</h4>
                <p>支付后迅速出票</p>
            </div>
            <div class="info_item">
                <h4>取票自由</h4>
                <p>全国范围内取票</p>
            </div>
            <div class="info_item">
                <h4>退款无忧</h4>
                <p>无票全额退款</p>
            </div>
        </div>
	        
	     <div class="sidebox">
            <div class="head">
                <h4>常见问题</h4>
                <a class="link-more" href="http://www.lvmama.com/public/help_447" target="_blank">更多帮助 &raquo;</a>
            </div>
            <div class="content">
                <ul class="train-faq JS_click_select">
                	<li class="selected">
                        <h4><b>Q1.</b>快铁驴行产品预订</h4>
                        <p><b>A1.</b>在车票查询框里输入出发城市、到达城市、出发日期，点击“搜索”。
                        </p>
                    </li>
                    <li>
                        <h4><b>Q2.</b>预订按钮呈灰色</h4>
                        <p><b>A2.</b>铁路局对该车次进行了限制销售的处理，或不在预售时间内。
                        </p>
                    </li>
                    <li>
                        <h4><b>Q3.</b>快铁驴行是否提供配送服务</h4>
                        <p><b>A3.</b>快铁驴行是不提供配送服务的，请到火车站或代售窗口自取。
                        </p>
                    </li>
                    <li>
                        <h4><b>Q4.</b>快铁驴行预订期限</h4>
                        <p><b>A4.</b>只出售3天后20天以内的火车票，当天预订时间为：7:00-21:30
                        </p>
                    </li>
                </ul>
            </div>
        </div>   
	        
	</div> <!-- //.wrap 1 -->
	
	<!-- 底通 -->
	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('aee5a1856692de1c0001','js',fromPlaceCode)" />
	<!-- 底通/End -->
	<!-- footer start-->
	<#include "/common/footer.ftl">
	<div class="hh_cooperate">
	<#include "/WEB-INF/pages/staticHtml/friend_link/train_footer.ftl">
	</div>
	<!-- footer end-->
	</div><!--main end-->
	</div><!--main-cente end-->
	
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
<script type="text/javascript" src="http://www.lvmama.com/js/train/train.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript"></script>
</body>
</html>
