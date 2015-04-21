<!DOCTYPE html>
<html> 
<#setting number_format="#.##">
<#if display == "simple">
<#assign base_url = "http://www.lvmama.com/search/hotel/simple/${keyword}"/>
<#else>
<#assign base_url = "http://www.lvmama.com/search/hotel/${keyword}"/>
</#if>
<head>
<meta charset="utf-8" >
<title>${pageTitle!"特色酒店_驴妈妈旅游网"}</title>
<meta name="keywords" content="${pageKeyword!"特色酒店_驴妈妈旅游网"}" />
<meta name="description" content="${pageDescription!"特色酒店_驴妈妈旅游网"}" />
<link rel="canonical"  href="${base_url}.html">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/holiday.css" />
<#include "/WEB-INF/ftl/common/coremetricsHead.ftl">
</head>

<body class="holiday">
    <div class="header" style="background:#fff;">
        <#include "/WEB-INF/ftl/common/header.ftl" >
    </div>
    <div id="banner" class="hotel-banner">
        <span class="hotel-prev" id="J_prev"></span>
        <span class="hotel-next" id="J_next"></span>
        <!--头部背景图-->
        <ul class="slide-content" id="js-slide">
            <!--酒店焦点图01-->
            <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xob3RlbF8yMDEzfGhvdGVsXzIwMTNfYmdmb2N1czAxJmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
            <!--酒店焦点图02-->
            <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xob3RlbF8yMDEzfGhvdGVsXzIwMTNfYmdmb2N1czAyJmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
            <!--酒店焦点图03-->
            <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xob3RlbF8yMDEzfGhvdGVsXzIwMTNfYmdmb2N1czAzJmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
            <!--酒店焦点图04-->
            <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xob3RlbF8yMDEzfGhvdGVsXzIwMTNfYmdmb2N1czA0JmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
        </ul>
        <div class="nav">
            <ul class="slide-nav">
                <li class="active"></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
    </div>
    
    <div  id="list"  class="wrap holiday-wrap">
        <!--搜索框-->
        <div id="header">
            <form class="hotel-search" id="hotel-search" >
                <div class="hotel-domestic">
                    <input  class="autoCInput" id="js-hotel-search"  type="text" autocomplete="off" value="${searchvo.keyword}" placeholder="想要去的城市、景点或酒店名称" />
                    <i class="clear-search"></i>
                    <button class="btn-search" type="button">搜索</button>
                </div>
            </form>
            <ul>
                <#list keyWordHistrory as keyWordHis>
                    <li><a rel="nofollow" href="/search/hotel/${keyWordHis}.html">${keyWordHis}</a></li>
                </#list>
            </ul>
        </div>
     
     <#if pageConfig.items?size gt 0 || filterStr !="" >
        <!--搜索多列条件-->
        <div class="list-container clearfix">
            <div class="list-property">
            <!--搜索结果-->
                <label>共 <span class="color-peach">${pageConfig.totalResultSize}</span> 家度假酒店符合搜索条件</label>
            </div>
            <!--关键词有唯一维度-->
            <#if searchvo.longitude?? || searchvo.local??  >
                <div class="row clearfix">
                    <span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域:</span>
                    <#if searchvo.keywordType=='CITY'>
                        <ul>
                            <li <#if searchvo.local=='1'>class="hotel-select"</#if>><a href="${base_url}-N1.html">${searchvo.localName!searchvo.keyword}本地度假酒店</a></li>
                           <li <#if searchvo.local=='0'>class="hotel-select"</#if>><a href="${base_url}-N0.html">${searchvo.localName!searchvo.keyword}周边度假酒店</a></li>
                        </ul>
                    </#if>
                    <#if searchvo.local== '0'>
                        <!--当查询的是xx周边度假酒店的时候，显示距离车程-->
                        <#if searchvo.keywordType=='CITY'>
                            <div class="distance-number">
                        <#else>
                            <div class="distance-number" style="left:0;" >
                        </#if>
                             <dl>
                                <dt>距离车程:</dt>
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 10>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z10.html"></a><span>10km</span></dd> 
                                
                                <dd <#if searchvo.distance?? && searchvo.distance gte 20>class="distance-select"</#if>><em style="width:18px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 20>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z20.html"></a><span>20km</span></dd> 
                                
                                <dd <#if searchvo.distance?? && searchvo.distance gte 100>class="distance-select"</#if>><em style="width:48px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 100>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z100.html"></a><span>100km <strong> (约1.5小时)</strong></span></dd> 
                                
                                <dd <#if searchvo.distance?? && searchvo.distance gte 200>class="distance-select"</#if>>    <em style="width:118px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 200>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z200.html"></a>   <span>200km <strong> (约3小时)</strong></span></dd> 
                                
                                <dd <#if searchvo.distance?? && searchvo.distance gte 300>class="distance-select"</#if>><em style="width:118px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 300>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z300.html"></a><span>300km <strong> (约4.5小时)</strong></span></dd> 
                                
                                <dd <#if searchvo.distance?? && searchvo.distance gte 400>class="distance-select"</#if>><em style="width:118px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 400>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z400.html"></a><span>400km <strong> (约6小时)</strong></span></dd>
                                 
                                <dd <#if searchvo.distance?? && searchvo.distance gte 500>class="distance-select"</#if>><em style="width:118px;"></em></dd> 
                                <dd><a <#if searchvo.distance?? && searchvo.distance gte 500>class="distance-select-icon" </#if> rel="nofollow"
                                        href="${base_url}-N0Z500.html"></a><span>500km<strong> (约8小时)</strong></span></dd> 
                            </dl>
                        </div>
                    </#if>
                </div>
            </#if>
            <#assign cities_show = cities_base?? && cities_base?size gt 0 && !searchvo.longitude??/>
            <#assign hotelTopics_show = allHotelTopics_base?? && allHotelTopics_base?size gt 0/>
            <#assign prodTopics_show = allProdTopics_base?? && allProdTopics_base?size gt 0/>
            <#assign stars_show = allStars_base?? && allStars_base?size gt 0/>
            
             <!-- 搜素筛选\\ -->
             <!-- #if cities_show || hotelTopics_show || prodTopics_show || stars_show  -->
             <#if cities_show>
                <div class="row clearfix">
                        <span>包含地区:</span>
                        <div class="select-wrap">
                            <ul  class="select-inner">
                                <#list cities_base?keys as key>
                                    <#assign show_num = ""/>
                                    <#if cities?? && cities.get(key) gt 0>
                                        <#assign show_num = "("+cities.get(key)+")"/>
                                    </#if>
                                    <#if searchvo.city?? && searchvo.city?contains(key)>
                                        <li class="hotel-select"><a href="${base_url}<@fp filter="${filterStr}" type="A" val="${key}"  remove=true/>.html#list">${key}<em>${show_num}</em><i class="tag-close">×</i></a></li>
                                    <#else>
                                         <li <#if show_num=="">class="hotel-select-disabled"</#if>><a rel="nofollow" href="<#if show_num=="" >javascript:void(0);<#else>${base_url}<@fp filter="${filterStr}" type="A" val="${key}" single=true />.html#list</#if>"> ${key}<em>${show_num}</em></a></li>
                                    </#if>
                                </#list>
                            </ul>
                            <i class="popu-open" title="更多"></i>
                    </div>
                </div>
             </#if>
                
             <#if prodTopics_show>
                    <div class="row clearfix">
                        <span>当季玩法:</span> 
                         <div class="select-wrap">
                            <ul  class="select-inner">
                                <#list allProdTopics_base?keys as key>
                                    <#assign show_num = ""/>
                                    <#if allProdTopics?? && allProdTopics.get(key) gt 0>
                                        <#assign show_num = "("+allProdTopics.get(key)+")"/>
                                    </#if>
                                    <#if searchvo.prodTopics?? && searchvo.prodTopics?contains(key)>
                                        <li class="hotel-select"><a href="${base_url}<@fp filter="${filterStr}" type="J" val="${key}" single=false remove=true/>.html#list" rel="nofollow">${key}<em>${show_num}</em><i class="tag-close">×</i></a></li>
                                    <#else>
                                         <li <#if show_num=="">class="hotel-select-disabled"</#if>><a rel="nofollow" href="<#if show_num=="" >javascript:void(0);<#else>${base_url}<@fp filter="${filterStr}" type="J" val="${key}" single=false />.html#list</#if>"> ${key}<em>${show_num}</em></a></li>
                                    </#if>
                                </#list>
                            </ul>
                            <i class="popu-open" title="更多"></i>
                        </div>
                    </div>
               </#if>
            
               <#if hotelTopics_show>
                    <div class="row clearfix">
                        <span>酒店印象:</span>
                        <div class="select-wrap">
                            <ul  class="select-inner">
                                <#list allHotelTopics_base?keys as key>
                                    <#assign show_num = ""/>
                                    <#if allHotelTopics?? && allHotelTopics.get(key) gt 0>
                                        <#assign show_num = "("+allHotelTopics.get(key)+")"/>
                                    </#if>
                                    <#if searchvo.hotelTopics?? && searchvo.hotelTopics?contains(key)>
                                        <li class="hotel-select"><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="F" val="${key}" single=false remove=true/>.html#list">${key}<em>${show_num}</em><i class="tag-close">×</i></a></li>
                                    <#else>
                                         <li <#if show_num =="">class="hotel-select-disabled"</#if>><a rel="nofollow" href="<#if show_num=="" >javascript:void(0);<#else>${base_url}<@fp filter="${filterStr}" type="F" val="${key}" single=false />.html#list</#if>"> ${key}<em>${show_num}</em></a></li>
                                    </#if>
                                </#list>
                            </ul>
                            <i class="popu-open" title="更多"></i>
                        </div>
                    </div>
                </#if>
           
                <#if stars_show>
                    <div class="row last-row clearfix">
                        <span>酒店星级:</span>
                        <ul>
                            <#list allStars_base?keys as key>
                                <#assign show_num = ""/>
                                <#if allStars?? && allStars.get(key) gt 0 >
                                        <#assign show_num = "("+allStars.get(key)+")"/>
                                </#if>
                                <#if searchvo.star?? && searchvo.star?contains(key)>
                                    <li class="hotel-select"><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="E" val="${key}" single=false remove=true/>.html#list">
                                        <#if key=="5">
                                            五星级/豪华型
                                        <#elseif key=="4">
                                            四星级/高档型
                                        <#elseif key=="3">
                                            三星级/舒适型
                                        <#elseif key=="2">
                                            二星级/简约型
                                        </#if><span>${show_num}</span><i class="tag-close">×</i></a></li>
                                <#else>
                                     <li <#if show_num=="" >class="hotel-select-disabled"</#if>>
                                        <a rel="nofollow" href="<#if show_num=="" >javascript:void(0);<#else>${base_url}<@fp filter="${filterStr}" type="E" val="${key}" single=false />.html#list</#if>"> 
                                            <#if key=="5">
                                                五星级/豪华型
                                            <#elseif key=="4">
                                                四星级/高档型
                                            <#elseif key=="3">
                                                三星级/舒适型
                                            <#elseif key=="2">
                                                二星级/简约型
                                            </#if><em>${show_num}</em>
                                        </a>
                                     </li>
                                </#if>
                            </#list>
                        </ul>
                </div>
               </#if>
        </div>
       
            <!--筛选和排序-->
            <div class="filters">
                <div class="filter-inner"  id="js-filter-wrap">
                    <div class="filter-wrap clearfix">
                        <div class="sort">
                            <!--
                            <a class="sort-default sort-selected" href="#" >驴妈妈推荐</a>
                            <a class="sort-distance sort-selected" href="#">距离<i class="arrow-down"></i></a> 距离就单项由近到远，直接删除
                            <a class="sort-price last-sort" href="#">价格<i class="arrow-up"></i></a> 价格分拆
                            -->
                            <#if !sort??>
                                <a rel="nofollow" title="驴妈妈推荐排序" class="sort-default sort-selected" href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list"  >驴妈妈推荐</a>
                                <#if searchvo.local=='0'>
                                    <a rel="nofollow" title="距离从近到远" class="sort-distance" href="${base_url}<@fp filter="${filterStr}" type="S" val="10" s=true/>.html#list" >距离<i class="arrow-up"></i></a>
                                </#if>
                                <a rel="nofollow" title="价格由高到低" class="sort-price" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list">价格<i class="arrow-down"></i></a>
                                <a rel="nofollow" title="价格由低到高" class="sort-price last-sort" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list">价格<i class="arrow-up"></i></a>
                            <#elseif sort =="2">
                                <a rel="nofollow" class="sort-default" title="驴妈妈推荐排序" href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list"  >驴妈妈推荐</a>
                                <#if searchvo.local=='0'>
                                    <a rel="nofollow" title="距离从近到远" class="sort-distance" href="${base_url}<@fp filter="${filterStr}" type="S" val="10" s=true/>.html#list" >距离<i class="arrow-up"></i></a>
                                </#if>
                                <a rel="nofollow" title="价格由高到低" class="sort-price sort-selected" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list">价格<i class="arrow-down"></i></a>
                                <a rel="nofollow" title="价格由低到高" class="sort-price last-sort" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list">价格<i class="arrow-up"></i></a>
                             <#elseif sort == "3">
                                <a rel="nofollow"  class="sort-default" title="驴妈妈推荐排序" href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list"  >驴妈妈推荐</a>
                                <#if searchvo.local=='0'>
                                    <a rel="nofollow" title="距离从近到远" class="sort-distance" href="${base_url}<@fp filter="${filterStr}" type="S" val="10" s=true/>.html#list" >距离<i class="arrow-up"></i></a>
                                </#if>
                                <a rel="nofollow" title="价格由高到低" class="sort-price" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list">价格<i class="arrow-down"></i></a>
                                <a rel="nofollow" title="价格由低到高" class="sort-price last-sort sort-selected" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list">价格<i class="arrow-up"></i></a>
                              <#elseif sort == "10">
                                <a rel="nofollow" class="sort-default" title="驴妈妈推荐排序" href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list"  >驴妈妈推荐</a>
                                <#if searchvo.local=='0'>
                                    <a rel="nofollow" title="距离从近到远" class="sort-distance  sort-selected" href="${base_url}<@fp filter="${filterStr}" type="S" val="10" s=true/>.html#list" >距离<i class="arrow-up"></i></a>
                                </#if>
                                <a rel="nofollow" title="价格由高到低" class="sort-price" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list">价格<i class="arrow-down"></i></a>
                                <a rel="nofollow" title="价格由低到高" class="sort-price last-sort" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list">价格<i class="arrow-up"></i></a>
                             </#if>
                             
                        </div>
                        <div class="price-range">
                            <div class="price-inner">
                                <form action="${base_url}<@fp filter="${filterStr}" type="K,O" val="1,1" repeat=true s=true/>.html#list">
                                    <ul>
                                        <li><input class="price-input" type="text" maxlength="5" autocomplete="off" placeholder="最低价" value="${searchvo.startPrice!""}" /></li>
                                        <li class="dash">-</li>
                                        <li><input class="price-input" type="text" maxlength="5" autocomplete="off" placeholder="最高价" value="${searchvo.endPrice!""}" /></li>
                                    </ul>
                                    <ul class="price-btn">
                                        <li class="clear-price">
                                            <#if searchvo.startPrice!="" || searchvo.endPrice!="">
                                                <a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="K,O"  repeat=true s=true remove = true />.html#list"">清除</a>
                                            </#if>
                                        </li>
                                        <li class="confirm-price"><input class="button" type="button" value="确定"></li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                        <div class="conditions">
                            <span class="tuan-checkbox">
                             <#if searchvo.promotion == "1" >
                                <a  rel="nofollow" rel="nofollow" class="tuan-label select-checkbox" href="${base_url}<@fp filter="${filterStr}" type="V"  remove=true/>.html#list">促销</a>
                             <#else>
                                <a  rel="nofollow" rel="nofollow" class="tuan-label" href="${base_url}<@fp filter="${filterStr}" type="V" val ="1" />.html#list">促销</a>
                             </#if>
                             </span>
                        </div> 
                        <div class="styles">
                              <#if display=="simple">
                                <a rel="nofollow" class="style-icon-big " href="http://www.lvmama.com/search/hotel/${params}.html#list"><i class="icon-grid"></i>默认</a>
                              <#else>
                                <a  rel="nofollow" class="style-icon-big styles-select" href="http://www.lvmama.com/search/hotel/${params}.html#list"><i class="icon-grid"></i>默认</a>
                              </#if>
                             <#if display=="simple">
                                 <a  rel="nofollow" class="style-icon-small styles-select" href="http://www.lvmama.com/search/hotel/simple/${params}.html#list"><i class="icon-list"></i>简约</a>
                              <#else>
                                 <a  rel="nofollow" class="style-icon-small" href="http://www.lvmama.com/search/hotel/simple/${params}.html#list"><i class="icon-list"></i>简约</a>
                              </#if> 
                        </div>
                        
                        <div class="result-search">
                           <!--<input  type="text" value="${searchvo.keyword2!""}"  class="input-text input-result-search" placeholder="在结果中查找…" /><i></i>-->
                            <input  id="js-result-search"  type="text"  value="${searchvo.keyword2!""}" placeholder="在结果中查找…" />
                            <i class="clear-search" <#if searchvo.keyword2?? && searchvo.keyword2 !="" > url = "${base_url}<@fp filter="${filterStr}" type="Q" remove=true/>.html#list""</#if>></i>
                            <input url="${base_url}<@fp filter="${filterStr}" type="Q" val="1" s=true/>.html#list" type="button" class="result-button" />
                        </div>
                    </div>
                    <a class="filter-trigger" href="javascript:void(0);">展开<i></i></a>
                </div>
            </div>
       </#if>
         
        <!--罗列酒店结果信息-->
        <div class="content clearfix">
            <div class="tuan-list">
                <#if pageConfig.items?size gt 0>
                    <#include "/WEB-INF/ftl/hotel/hotels.ftl" >
                <#else>
                    <#if useRecommendLogic>
                        <!--无结果推荐-->
                        <div class="list-recommend">
                            <div class="hotel-tips">
                                <i></i>
                                <h3>很抱歉，暂时无法找到符合您要求的酒店</h3>
                                <p>您可以试试更改搜索条件重新搜索，或改订其他酒店</p>
                            </div>
                            <dl>
                                <dt>大家热衷下面的旅游目的地</dt>
                                    <dd><a href="http://www.lvmama.com/search/hotel/79.html"><img src="http://pic.lvmama.com/img/v3/holiday/sh.jpg" width="240" height="159" alt="" /></a><span>上海</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/87.html"><img src="http://pic.lvmama.com/img/v3/holiday/sz.jpg" width="240" height="159" alt="" /></a><span>苏州</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/83.html"><img src="http://pic.lvmama.com/img/v3/holiday/wx.jpg" width="240" height="159" alt="" /></a><span>无锡</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/100.html"><img src="http://pic.lvmama.com/img/v3/holiday/hz.jpg" width="240" height="159" alt="" /></a><span>杭州</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/82.html"><img src="http://pic.lvmama.com/img/v3/holiday/nj.jpg" width="240" height="159" alt="" /></a><span>南京</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/104.html"><img src="http://pic.lvmama.com/img/v3/holiday/nb.jpg" width="240" height="159" alt="" /></a><span>宁波</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/102.html"><img src="http://pic.lvmama.com/img/v3/holiday/qdh.jpg" width="240" height="159" alt="" /></a><span>千岛湖</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/4357.html"><img src="http://pic.lvmama.com/img/v3/holiday/tmh.jpg" width="240" height="159" alt="" /></a><span>天目湖</span></dd> 
                                    <dd><a href="http://www.lvmama.com/search/hotel/92.html"><img src="http://pic.lvmama.com/img/v3/holiday/yz.jpg" width="240" height="159" alt="" /></a><span>扬州</span></dd>
                            </dl>
                        </div>
                     <#else>
                            <!--无结果-->
                        <div class="hotel-warn">
                            <i></i>
                            <h3>很抱歉，没有找到相关的度假酒店</h3>
                            <!-- #if searchvo.prodTopics?? || searchvo.city?? || searchvo.hotelTopics?? || searchvo.star?? -->
                                <p><strong>建议您</strong>适当更改已选择的筛选条件： 
                                <#if searchvo.city??>
                                    <br/>
                                    包含地区：
                                    <a href="${base_url}<@fp filter="${filterStr}" type="A" val="${searchvo.city}" remove=true single=false/>.html#list">[${searchvo.city}×]</a>
                                </#if>
                                <#if searchvo.prodTopics??>
                                    <br/>
                                    当季玩法：
                                    <#list searchvo.prodTopics as t>
                                        <a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="J" val="${t}" remove=true  single=false/>.html#list">[${t}×]</a>
                                    </#list>
                                </#if>
                                <#if searchvo.hotelTopics??>
                                    <br/>
                                    酒店印象：
                                    <#list searchvo.hotelTopics as t>
                                        <a href="${base_url}<@fp filter="${filterStr}" type="F" val ="${t}" remove=true single=false/>.html#list">[${t}×]</a>
                                    </#list>
                                 </#if>
                                <!--<h6>酒店星级：</h6>-->
                                <#if searchvo.star??> 
                                    <br/>
                                    酒店星级：
                                    <#list searchvo.star as t>
                                        <a href="${base_url}<@fp filter="${filterStr}" type="E" val ="${t}" remove=true single=false/>.html">[<#if t=="5">
                                            五星级/豪华型
                                            <#elseif t=="4">
                                            四星级/高档型
                                            <#elseif t=="3">
                                            三星级/舒适型
                                            <#elseif t=="2">
                                            二星级/简约型
                                            </#if>×]<span class="icon-close"></span></a>
                                     </#list>
                                </#if>
                                <#if searchvo.startPrice?? || searchvo.endPrice??> 
                                    <br/>
                                    价格：
                                    <a href="${base_url}<@fp filter="${filterStr}" type="K,O" remove=true repeat=true/>.html#list">[<#if searchvo.endPrice=="">${searchvo.startPrice}元以上<#elseif searchvo.startPrice=="">${searchvo.endPrice}元以下<#else>${searchvo.startPrice}-${searchvo.endPrice}元</#if>×]</a>
                                </#if>
                                <#if searchvo.promotion?? && searchvo.promotion == 1 > 
                                    <br/>
                                    <a href="${base_url}<@fp filter="${filterStr}" type="V" remove=true/>.html#list">[促销×]</a>
                                </#if>
                                <#if searchvo.keyword2?? && searchvo.keyword2!="" > 
                                    <br/>
                                    结果中查找：
                                    <a href="${base_url}<@fp filter="${filterStr}" type="Q" remove=true/>.html#list">[${searchvo.keyword2}×]</a>
                                </#if>
                                <br/>
                                <#if searchvo.local?? >
                                    <a href="${base_url}-N${searchvo.local}.html#list" class="clear-condition">清空筛选条件</a></p>
                                <#else>
                                    <a href="${base_url}.html#list" class="clear-condition">清空筛选条件</a></p>
                                </#if>
                        </div>
                     </#if> 
                </#if>
            </div>
            
            <!--右边栏-->
            <div class="sidebar">
                <div class="box">
                    <h3>为什么选择我们</h3>
                    <dl class="superiority">
                        <dt><i class="is1"></i>价格优廉</dt>
                        <dd>同样价格，多重享受，涵盖吃、住、游、玩。</dd>
                        <dt><i class="is2"></i>套餐丰富</dt>
                        <dd>多达几千种不同目的地、不同品级的套餐产品。 </dd>
                        <dt><i class="is3"></i>资讯完整</dt>
                        <dd>全面多样的目的地资讯，助您身临其境、感同身受。</dd>
                        <dt><i class="is4"></i>服务贴心</dt>
                        <dd>所见即所得，即付即走。只需动动手指、打点行装，走起!</dd>
                    </dl>
                </div>
                <#if  weekHotProd??  || hotelCookie??>
                    
                    <#if weekHotProd?? && weekHotProd?size gt 0 >
                    <!--自由行套餐推荐-->
                    <!--<div class="top-border"></div>-->
                    <div class="box" >
                        <h3>一周自由行套餐热榜</h3>
                        <dl>
                                <#list weekHotProd as key>
                                    <dd>
                                        <p><a href="http://www.lvmama.com/product/${key.productId}" target="_blank">
                                            <#if key.productName?length gt 25>${key.productName?substring(0,25)}...<#else>${key.productName}</#if>
                                        </a></p>
                                        <p><dfn>&yen;<i>${key.sellPrice}</i>起</dfn><span class="tagsback" tip-title="提示标题" tip-content="提示内容" >
                                        <em>返</em><i class="money"><dfn>&yen; ${key.cashRefund}</dfn></i></span></p>
                                    </dd>
                                 </#list>
                        </dl>
                    </div>
                    </#if>
                     <!--浏览历史-->
                    <#if hotelCookie?? && hotelCookie.size() gt 0>
                        <div class="box">
                            <h3>最近浏览</h3>
                            <dl>
                                    <#list hotelCookie as history>
                                        <dd>
                                            <a class="item-img" target="_blank" href="http://www.lvmama.com/hotel/v${history.placeId?if_exists}" rel="nofollow">
                                                <img class="lazy-history" src="http://pic.lvmama.com/img/v3/holiday/loadingmini.gif" data-original="http://pic.lvmama.com${history.imageUrl?if_exists}" width="85" height="55" alt=""></a>
                                            <div class="item-info">
                                                <p><a href="http://www.lvmama.com/hotel/v${history.placeId?if_exists}" target="_blank" rel="nofollow">
                                                    <#if history.name?length gt 20>${history.name?substring(0,17)}...<#else>${history.name}</#if>
                                                </a></p>
                                                <p><dfn>&yen;<i>${history.productsPrice?if_exists}</i>起</dfn></p>
                                            </div>
                                        </dd>
                                    </#list>
                            </dl>
                        </div>
                    </#if>
                </#if>
            </div>
        </div>
        
        <div class="vm-fixed">
            <a id="go-top" class="go-top" href="javascript:;" style="display: block;"></a>
            <a class="feedback" target="_blank" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do"></a>
            <a class="bds_tsina" href="javascript:(function(){window.open('http://service.weibo.com/share/share.php?url='+encodeURIComponent(document.location.href)+'&appkey=87692682&language=zh_cn&title=我发现好多“${searchvo.keyword?if_exists}度假酒店”，看着就想去放松下心情，你也来看看啦&pic=http%3A%2F%2Fpic.lvmama.com${fristPicStr?if_exists}&searchPic=true');})()"></a>
            <a class="bds_qzone" href="javascript:(function(){window.open('http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+encodeURIComponent(document.location.href)+'&title=我发现好多“${searchvo.keyword?if_exists}度假酒店”，看着就想去放松下心情，你也来看看啦');})()"></a>
            <a class="bds_tqq" href="javascript:(function(){window.open('http://v.t.qq.com/share/share.php?title=我发现好多“${searchvo.keyword?if_exists}度假酒店”，看着就想去放松下心情，你也来看看啦&url='+encodeURIComponent(document.location.href)+'&appkey=09fab729da0a45b2bc22f2d69e16f70b');})()"></a>
        </div>
    </div>
    <div class="holiday-footer">
        <script src="http://pic.lvmama.com/js/v6/public/footer.js"></script>
          <!--
        <div class="hh_cooperate"> 
            <p><b>热门精选：</b><span>
             <a target="_blank" href="http://www.lvmama.com/dest/hainan_sanya">三亚旅游</a>
             <a target="_blank" href="http://www.lvmama.com/dest/zhongguo_taiwan">台湾旅游</a>
             <a target="_blank" href="http://www.lvmama.com/dest/zhongguo_beijing">北京旅游</a>
             <a target="_blank" href="http://www.lvmama.com/dest/zhongguo_shanxi">山西旅游</a>
            </span></p>
        </div>
            -->
    </div>

    <!--搜索框的提示-->
    <div class="hot-city-wrap" id="js-hot-city" >
        <#if ip_from_place_name?? && ip_from_place_name!="">
            <label>您当前所在城市：</label>
            <a href="http://www.lvmama.com/search/hotel/${ip_from_place_name}.html">${ip_from_place_name}</a>
        </#if>
         <div class="hot-city">热门旅游目的地</div>
        <div class="hot-city-list">
           <ul class="city-list layoutfix" id="js-city-list">
            </ul>
        </div>
    </div>
    <script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
    <script src="/search/js/jquery.lazyload.min.js" type="text/javascript"></script>
    <script src="http://pic.lvmama.com/js/v3/plugins/autoComplete.js" ></script>
    <script src="/search/js/route.js"  type="text/javascript"></script>
    <script src="/search/js/hotel.js"  type="text/javascript"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/v3/holiday.js"></script>
    <script src="http://pic.lvmama.com/js/common/losc.js"></script>
    <div class="hh_cooperate">
    <#if type =="hotel">
	<#include "/WEB-INF/pages/staticHtml/friend_link/index_search_hotel_footer.ftl">
    </#if>
	</div>
	<script> 
	if(<@s.property value="pageConfig.items.size()"/> > 0){
		cmCreatePageviewTag("度假酒店首页_"+"<@s.property value="searchvo.keyword"/>", "P0001", null, null); 
	}else{
		cmCreatePageviewTag("度假酒店搜索-搜索结果-搜索失败", "P0001", null, null);
	}
	</script>
</body>
</html>
