<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
    	<title>${pageTitle!"酒店_驴妈妈旅游网"}</title>
	<meta name="keywords" content="${pageKeyword!"酒店_驴妈妈旅游网"}" />
	<meta name="description" content="${pageDescription!"酒店_驴妈妈旅游网"}" />
    <link type="image/x-icon" href="http://www.lvmama.com/favicon.ico" rel="shortcut icon">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v4/modules/arrow.css,/styles/v4/modules/tags.css,/styles/v4/modules/pager.css,/styles/v4/modules/tip.css,/styles/v4/modules/calendar.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css" />
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/pa-base.css,/styles/new_v/header-air.css,/styles/v4/modules/arrow.css,/styles/v4/modules/tags.css,/styles/v4/modules/pager.css,/styles/v4/modules/tip.css,/styles/v4/modules/calendar.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css&t=2014012310476" />
    <link rel="stylesheet" href="http://pic.lvmama.com/js/ui/lvmamaUI/css/jquery.common.css" />
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/hotel.css" />
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/base.css,/styles/v5/common.css,/styles/new_v/header-air.css" >
    
</head>
<body class="hotel">
	<script type="text/javascript" src="http://pic.lvmama.com/min/index.php?f=/js/v4/responsive.js"></script>
    <!-- 公共头部开始  --> 
    <#include "/WEB-INF/ftl/common/header.ftl" >
    <!-- 公共头部开始  -->

    <!-- 面包屑  -->
    <div class="wrap">
        <ul class="breadcrumb">
            <li><a href="http://www.lvmama.com">驴妈妈首页</a> > </li>
            <li><a href="http://hotels.lvmama.com/hotel">酒店预订</a> > </li>
            <#if (verHotelSearchVO.keyword??) && ((verHotelSearchVO.keyword?contains("酒店")) ||(verHotelSearchVO.keyword?contains("宾馆"))||(verHotelSearchVO.keyword?contains("客栈"))||(verHotelSearchVO.keyword?contains("旅馆"))  ) >
            <li><a href="javascript:;">${verHotelSearchVO.keyword}</a> </li>
            <#else>
             <li><a <#if (verHotelSearchVO.actionUrl??)> href=${verHotelSearchVO.actionUrl}<#else>href="javascript:;"</#if>>${verHotelSearchVO.keyword}酒店预订</a></li>
            </#if>
           
        </ul>
    </div>
    <!-- 面包屑  -->

    <!-- 条件塞选  -->
    <div class="wrap container">
        <!-- 搜索  -->
        <div class="search-box clearfix">
            <form id="form" method="post" action="http://hotels.lvmama.com/list/${verHotelSearchVO.keyword}.html">
                <input type="hidden" class="J_searchId" name="searchId" value="${verHotelSearchVO.searchId}" />
                <input type="hidden" class="J_longitude" name="longitude" value="${verHotelSearchVO.longitude}"/>
                <input type="hidden" class="J_latitude" name="latitude" value="${verHotelSearchVO.latitude}" />
                <input type="hidden" class="J_keyword" name="keyword" value="${verHotelSearchVO.keyword}" />
                <input type="hidden" class="J_parentId" name="parentId" value="${verHotelSearchVO.parentId}"/>
                <input type="hidden" class="J_searchType" name="searchType" value="${verHotelSearchVO.searchType}"/>
                <input type="hidden" class="J_hotelprice" name="hotelprice" data-marked="J" value="${verHotelSearchVO.hotelprice}" />
                <input type="hidden" class="J_hotelStar" name="hotelStar" data-marked="J" value="${verHotelSearchVO.hotelStar}"  />
                <input type="hidden" class="J_minproductsprice" name="minproductsprice" data-marked="J" value="${verHotelSearchVO.minproductsprice}"  />
                <input type="hidden" class="J_maxproductsprice" name="maxproductsprice" data-marked="J" value="${verHotelSearchVO.maxproductsprice}"  />
                <input type="hidden" class="J_hotel_brand" name="hotel_brand" data-marked="J" value="${verHotelSearchVO.hotel_brand}" />
                <input type="hidden" class="J_facilities" name="facilities" data-marked="J" value="${verHotelSearchVO.facilities}"  />
                <input type="hidden" class="J_room_type" name="room_type" data-marked="J" value="${verHotelSearchVO.room_type}"  />
                <input type="hidden" class="J_ranktype" name="ranktype" data-marked="J" value="${verHotelSearchVO.ranktype}" />
                <input type="hidden" class="J_issale" name="issale" data-marked="J" value="${verHotelSearchVO.issale}"  />
                <input type="hidden" class="J_returnmoney" name="returnmoney" data-marked="J" value="${verHotelSearchVO.returnmoney}"  />
                <input type="hidden" class="J_actionUrl" name="actionUrl" data-marked="J" value="${verHotelSearchVO.actionUrl}"  />
                <input type="hidden" class="currentPage" name="currentPage" id="currentPage" />
                <input type="hidden" class="searchrecord" name="searchrecord" id="searchrecord" />
                <input type="hidden" class="J_activeFlag" name="activeFlag" id="activeFlag" value="${verHotelSearchVO.activeFlag}"/>
                <!-- <input type="hidden" class="beginBookTime" name="beginBookTime" id="beginBookTime"  value="${verHotelSearchVO.beginBookTime}" /> -->
                <!-- <input type="hidden" class="endBookTime" name="endBookTime" id="endBookTime" value="${verHotelSearchVO.endBookTime}"  /> -->
                  <input type="hidden" class="J_isAutocompleteFirstOne" name="" id="" value=""  />
                 
                <ul>
             	    <li>目的地 <input class="J_autocomplete" type="text" autocomplete="off" value="${searchvo.keyword}" placeholder="城市/地区/区或特定酒店名称" /></li>                
                    <li>入住日期 <input type="text" class="J_calendar" value="${verHotelSearchVO.beginBookTime}" name="beginBookTime"  readonly="readonly" autocomplete="off" data-check="checkIn"  />
                        <div class="date-info">
	                        <span>${verHotelSearchVO.beginBookTimeDate}</span>
                            <i class="icon icon-date"></i>
	                    </div>
                    </li>
                    <li>退房日期<input type="text" class="J_calendar" data-range="true" name="endBookTime" value="${verHotelSearchVO.endBookTime}" readonly="readonly" autocomplete="off" data-check="checkOut" />
                        <div class="date-info">
	                        <span>${verHotelSearchVO.endBookTimeDate}</span>
                            <i class="icon icon-date"></i>
	                    </div>
                    </li>
                    <li><a class="J_submit icon hotel-submit" href="javascript:;"></a></li>
                   
                </ul>
            </form>
        </div>
        <!-- 搜索  -->

        <!-- 条件过滤  -->
        <div class="filter first-filter clearfix">
            <span class="filter-type">酒店位置</span>
            <div class="filter-wrap">
                <ul class="J_fil_item filter-item hotel-place">
                    <li class="item-all item-all-active"><a href="javascript:;" rel="nofollow">不限</a></li>
                    <#list  catageoryList as key>
                     <li><a href="javascript:;" rel="nofollow">${key.dictName}<i class="ui-arrow-bottom gray-ui-arrow-bottom"></i></a></li>
	                </#list>
                </ul>
           </div>
        <#list  catageoryContentList as tmps>
           <div class="J_periphery periphery">
               <ul class="list1">
                <#list tmps?keys as key>
                 <#assign vo = tmps.get(key)/>
                        <#if (key_index >49) ><#break></#if>
                        <li><a href="javascript:;" longitude="${vo.longitude}" latitude="${vo.latitude}" parentId="${vo.parentId}" searchId="${vo.placeId}" title="${key}" rel="nofollow">${key}</a></li>
                </#list>
               </ul>
           </div>
         
	    </#list>   
          
        </div>
        <div class="filter clearfix">
            <span class="filter-type">酒店价格</span>
            <div class="filter-wrap">
                <ul class="J_filter_price  filter-item">
                    <li <#if (verHotelSearchVO.hotelprice == '') >class="item-all active" <#else> class="item-all" </#if> ><a href="javascript:;" name="hotelprice" value="" rel="nofollow">不限</a></li>
                    <li <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("1")) >class="active"</#if>><a href="javascript:;" name="hotelprice" value="1" rel="nofollow">600元以上</a></li>
                    <li <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("2")) >class="active"</#if>><a href="javascript:;" name="hotelprice" value="2" rel="nofollow">300-600元</a></li>
                    <li <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("3")) >class="active"</#if>><a href="javascript:;" name="hotelprice" value="3" rel="nofollow">150-300元</a></li>
                    <li <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("4")) >class="active"</#if>><a href="javascript:;" name="hotelprice" value="4" rel="nofollow">150元以下</a></li>
                    <li class="interval-box">
                        <span>自定义</span>
                        <input class="J_price" type="text" name="minproductsprice" value="${verHotelSearchVO.minproductsprice}" />
                        <span>-</span>
                        <input class="J_price" type="text" name="maxproductsprice" value="${verHotelSearchVO.maxproductsprice}" />
                        <a class="J_price_clear interval-clear" href="javascript:;">清除</a>
                        <a class="J_price_fix interval-fix" href="javascript:;">确定</a>
                    </li>
                </ul>
           </div>
        </div>
        <div class="filter clearfix">
            <span class="filter-type">酒店星级</span>
            <div class="filter-wrap">
                <ul class="J_filter filter-item">
                    <li <#if (verHotelSearchVO.hotelStar =='') >class="item-all active" <#else> class="item-all" </#if>><a href="javascript:;" name="hotelStar" value="" rel="nofollow">不限</a></li>
                    <li  <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("100-101")) >class="active"</#if>><a href="javascript:;" name="hotelStar" value="100-101" rel="nofollow"><i class="icon icon-checkbox"></i>五星/豪华型</a></li>
                    <li <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("102-103")) >class="active"</#if>><a href="javascript:;" name="hotelStar" value="102-103" rel="nofollow"><i class="icon icon-checkbox"></i>四星/高档型</a></li>
                    <li <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("104-105")) >class="active"</#if>><a href="javascript:;" name="hotelStar" value="104-105" rel="nofollow"><i class="icon icon-checkbox"></i>三星/舒适型</a></li>
                    <li <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("106-107")) >class="active"</#if>><a href="javascript:;" name="hotelStar" value="106-107" rel="nofollow"><i class="icon icon-checkbox"></i>二星/简约型</a></li>
                </ul>
           </div>
        </div>
        <#if hotelbrands ?? && hotelbrands?size gt 0 >
        <div class="filter clearfix">
            <span class="filter-type">酒店品牌</span>
            <div class="filter-wrap">
                <ul class="J_filter filter-item">
                    <li <#if (verHotelSearchVO.hotel_brand =='') >class="item-all active" <#else> class="item-all" </#if>><a href="javascript:;" name="hotel_brand" value="" rel="nofollow">不限</a></li>
                    <#list hotelbrands?keys as key>
                    <#if hotelbrands?? && hotelbrands.get(key) ??>
                         <#assign vo = hotelbrands.get(key)/>
                     </#if>
                     <#if (key_index >8) ><#break></#if>
                               <li <#if (verHotelSearchVO.hotel_brand??) && (verHotelSearchVO.hotel_brand?contains(vo.hotelbrand)) >class="active"</#if>><a href="javascript:;" name="hotel_brand" value="${vo.hotelbrand}" rel="nofollow"><i class="icon icon-checkbox"></i>${key}</a></li>
                    </#list>
                </ul>
           </div>
           <a class="J_more more-cnt" href="javascript:;" rel="nofollow">更多</a>
        </div>
        </#if>
        <#if facilities ?? && facilities?size gt 0 >
        <div class="J_filter_all filter clearfix" <#if (verHotelSearchVO.activeFlag !='0') >style="display:none;" </#if>>
            <span class="filter-type">酒店设施</span>
            <div class="filter-wrap">
                <ul class="J_filter filter-item">
                    <li  <#if (verHotelSearchVO.facilities =='') >class="item-all active" <#else> class="item-all" </#if>><a href="javascript:;" name="facilities" value="" rel="nofollow">不限</a></li>
                    <#list facilities?keys as key>
                    <#if facilities?? && facilities.get(key) ??>
                         <#assign value = facilities.get(key)/>
                     </#if>
                         <li <#if (verHotelSearchVO.facilities??) && (verHotelSearchVO.facilities?contains(value)) >class="active"</#if>>
                         	<a href="javascript:;" name="facilities" value="${value}" rel="nofollow"><i class="icon icon-checkbox"></i>${key}</a>
                         </li>
                    </#list>
                </ul>
           </div>
        </div>
        </#if>
        <div class="J_filter_all filter clearfix" <#if (verHotelSearchVO.activeFlag !='0') >style="display:none;" </#if>>
            <span class="filter-type">房型要求</span>
            <div class="filter-wrap">
                <ul class="J_filter filter-item">
                    <li <#if (verHotelSearchVO.room_type =='') >class="item-all active" <#else> class="item-all" </#if> ><a href="javascript:;" name="room_type" value="" rel="nofollow">不限</a></li>
                    <li <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("1")) >class="active"</#if>><a href="javascript:;" name="room_type" value="1" rel="nofollow"><i class="icon icon-checkbox"></i>大床房</a></li>
                    <li <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("2")) >class="active"</#if>><a href="javascript:;" name="room_type" value="2" rel="nofollow"><i class="icon icon-checkbox"></i>双床房</a></li>
                    <li <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("3")) >class="active"</#if>><a href="javascript:;" name="room_type" value="3" rel="nofollow"><i class="icon icon-checkbox"></i>三人间</a></li>
                    <li <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("4")) >class="active"</#if>><a href="javascript:;" name="room_type" value="4" rel="nofollow"><i class="icon icon-checkbox"></i>家庭房</a></li>
                    <li <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("5")) >class="active"</#if>><a href="javascript:;" name="room_type" value="5" rel="nofollow"><i class="icon icon-checkbox"></i>套房</a></li>
                </ul>
           </div>
        </div>
        <div class="clearfix last-filter">
            <div class="all-search-list">
                <div class="hotel-num">
                    <span class="f16">${pageConfig.totalResultSize}</span>家酒店
                </div>
                <#if ((verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelStar??) && (hotelbrands??) && (verHotelSearchVO.room_type??) && (facilities??)) &&  
                	(verHotelSearchVO.hotelprice != '' || verHotelSearchVO.hotelStar != '' || verHotelSearchVO.hotel_brand != '' || verHotelSearchVO.facilities != '' || verHotelSearchVO.room_type != '')>
	                <span class="fl">您已选择：</span>
	                <ul class="J_clear_item">
	                    <!--  //价格 -->
	                    <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("4")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelprice" value="4">150元以下<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("3")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelprice" value="3">150-300元<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("2")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelprice" value="2">300-600元<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("1")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelprice" value="1">600元以上<em>X</em></a>
	                    </li>
	                    </#if>
	                    <#if (verHotelSearchVO.hotelprice??) && (verHotelSearchVO.hotelprice?contains("0")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelprice" value="0">${verHotelSearchVO.minproductsprice}-${verHotelSearchVO.maxproductsprice}<em>X</em></a>
	                    </li>
	                    </#if>
	                      <!--  //星级 -->
	                     <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("100-101")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelStar" value="100-101" >五星/豪华型<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("102-103")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelStar" value="102-103">四星/高档型<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("104-105")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelStar" value="104-105">三星/舒适型<em>X</em></a>
	                    </li>
	                    </#if>
	                    
	                     <#if (verHotelSearchVO.hotelStar??) && (verHotelSearchVO.hotelStar?contains("106-107")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="hotelStar" value="106-107">二星/经济型<em>X</em></a>
	                    </li>
	                    </#if>
	                      <!--  //品牌 -->
	                  <#list hotelbrands?keys as key>
	                    <#if hotelbrands?? && hotelbrands.get(key) ??>
	                         <#assign vo = hotelbrands.get(key)/>
	                     </#if>
	                     <#if (verHotelSearchVO.hotel_brand??) && (verHotelSearchVO.hotel_brand?contains(vo.hotelbrand)) >
	                        <li>
							<a href="javascript:;" title="删除" name="hotel_brand" value="${vo.hotelbrand}">${key}<em>X</em></a>
							 </li>
	                     </#if>
	                  </#list>
	                   <!--  设施 -->
	                  <#list facilities?keys as key>
	                    <#if facilities?? && facilities.get(key) ??>
	                         <#assign value = facilities.get(key)/>
	                     </#if>
	                     <#if (verHotelSearchVO.facilities??) && (verHotelSearchVO.facilities?contains(value)) >
	                      <li>
	                     	<a href="javascript:;" title="删除"  name="facilities" value="${value}">${key}<em>X</em></a>
	                     	 </li>
	                     	</#if>
	                    </#list>
	                    
	                     <!--  //房型-->
	                    <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("1")) >
	                    <li>
	                    <a href="javascript:;" title="删除"  name="room_type" value="1"> 大床房<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("2")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="room_type" value="2"> 双床房<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("3")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="room_type" value="3"> 三人间<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("4")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="room_type" value="4"> 家庭房<em>X</em></a>
	                    </li>
	                    </#if>
	                     <#if (verHotelSearchVO.room_type??) && (verHotelSearchVO.room_type?contains("5")) >
	                    <li>
	                    <a href="javascript:;" title="删除" name="room_type" value="5"> 套房<em>X</em></a>
	                    </li>
	                    </#if>
	                    
	                    <li class="clear-all"><a class="J_clear_filter" href="javascript:;">清除所有条件</a></li>
	                </ul>
	        </#if>
            </div>
            <a href="javascript:;" class="J_fil_ctl filter-control <#if  verHotelSearchVO.activeFlag == "0" >active </#if>" rel="nofollow">酒店设施及房型要求<i class="ui-arrow-bottom gray-ui-arrow-bottom"></i></a>
        </div>
    </div>

    <!-- 内容 -->
    <div class="J_main wrap main">
        <div class="hotel-wrap">
            <div class="sortbar">
                <div class="col">
                    <ul class="J_sort">
                        <li <#if (verHotelSearchVO.ranktype??) && (verHotelSearchVO.ranktype?contains("1")) >class="active"</#if> ><a href="javascript:;" name="ranktype" value="1" rel="nofollow">驴妈妈推荐</a></li>
                        <li 
                        	<#if (verHotelSearchVO.ranktype??) && ((verHotelSearchVO.ranktype?contains("3"))||(verHotelSearchVO.ranktype?contains("4"))) > class="active"
                        	</#if> >
                            <a href="javascript:;" <#if (verHotelSearchVO.ranktype??) && (verHotelSearchVO.ranktype?contains("4")) >class="sort-up" title="按价格从低到高排序" <#else> title="按价格从高到低排序"</#if>   name="ranktype" value="4" rel="nofollow">价格<i class="icon icon-trend-bottom"></i></a>
                         </li>
                         
	                    <#if (verHotelSearchVO.longitude??)&& (verHotelSearchVO.longitude!='') && (verHotelSearchVO.longitude!='0.0') >
                        	<li <#if (verHotelSearchVO.ranktype??) && (verHotelSearchVO.ranktype?contains("2")) >class="active"</#if> ><a href="javascript:;" class="sort-up" title="按距离从近到远排序" name="ranktype" value="2" rel="nofollow">距离<i class="icon icon-trend-bottom"></i></a></li>
	                    </#if>
                    </ul>
                </div>
                <div class="end">
                    <ul class="J_activity">
                        <li <#if (verHotelSearchVO.issale??) && (verHotelSearchVO.issale?contains("y")) >class="active"</#if>><a href="javascript:;" name="issale" value='y' title="促销" rel="nofollow"><i class="icon icon-checkbox"></i>促销</a></li>
                        <!-- <li><a href="javascript:;" name="returnmoney" title="返现" rel="nofollow"><i class="icon icon-checkbox"></i>返现</a></li> -->
                    </ul>
                </div>
            </div>
            <!-- 列表 -->
            <div class="hotel-list">
                 <#include "/WEB-INF/ftl/verhotel/verhotels.ftl" >
            </div>
            <!-- 列表 -->

        </div>
        <!-- 侧栏 -->
        <div class="sidebar">
        	<div class="J_map sidebar-inner">
	            <div id="container" class="map"></div>
	            <div class="history">
	                <h2>我浏览的酒店记录</h2>
	                <ul id="jilu">
	                      <#list hotelCookie as ls>
	                		<li>
	                        <a class="item-img" href="javascript:;" hidefocus="false">
		                        <#if ls.imageUrl?? && ls.imageUrl?length gt 0>
		                        	<img src="http://pic.lvmama.com${ls.imageUrl?if_exists}" width="60" height="40" alt=${ls.name?if_exists} />
		                        <#else>
		                        	<img src="http://pic.lvmama.com/img/cmt/img_80_60.png" width="60" height="40" alt=${ls.name?if_exists} />
		                        </#if>
	                        </a>
	                        <p>
	                            <a href="http://hotels.lvmama.com/hotel/${ls.productId}.html" title="${ls.name?if_exists}}" target="_blank" >${ls.name?if_exists}</a>
	                            <#if ls.productsPrice != "-0">
		            				<dfn>¥<i>${ls.productsPrice?if_exists}</i></dfn> 起
		            			</#if>
	                        </p>
	                        <span class="icon close" title="删除记录"></span>
	                      </li>
	                	</#list>
	                </ul>
	            </div>
            </div>
        </div>
        <!-- 侧栏 -->
        
       
    </div>
    <div class="wrap">
  <div class="xhotel-info">
        <div class="xtitle"><h4 class="xtit"><span>${verHotelSearchVO.districtName}酒店信息</span><i class="icon icon-plus"></i></h4></div>
        <div class="xcontent">
            <p>${verHotelSearchVO.districtName}酒店预订，驴妈妈酒店竭诚为您服务！您可查询${verHotelSearchVO.districtName}酒店实时价格、酒店评价，还有${verHotelSearchVO.districtName}酒店推荐，有${verHotelSearchVO.districtName}商务酒店、快捷酒店、青年旅舍等各类${verHotelSearchVO.districtName}酒店、宾馆任您选择。如果您是来${verHotelSearchVO.districtName}旅游，可尽量选择距景点较近的酒店，景点门票购买、跟团游也可在驴妈妈一站完成哦。${verHotelSearchVO.districtName}酒店预订，找驴妈妈酒店网，只为您住得安心，玩得开心！</p>
            
            <hr />
           <div class="xtitle"><h4 class="xtit"><span>${verHotelSearchVO.districtName}酒店品牌</span><i class="icon icon-plus"></i></h4></div>
            <p>
            <#list hotelbrands?keys as key>
            <#if hotelbrands?? && hotelbrands.get(key) ??>
                 <#assign vo = hotelbrands.get(key)/>
             </#if>
             			<a href="http://hotels.lvmama.com/${verHotelSearchVO.districtPinYin}/brand${vo.hotelbrand}/">${key}</a> |
            </#list>
            </p><hr/>
            <#list  catageoryList as key0>
            <#if "1"==key0.isHasTree>
            	<h4>${verHotelSearchVO.districtName}${key0.dictName}周边酒店</h4>
            	 <p>
            	<#list  catageoryContentList as tmps>
            	 <#if tmps_index == key0_index>
		                <#list tmps?keys as key>
		                 <#assign vo = tmps.get(key)/>
		                 <a href="http://hotels.lvmama.com/${verHotelSearchVO.districtPinYin}/place${vo.placeId}/">${key}</a> |
		                </#list>
		           </#if>
			    </#list>  
			     </p> 
            	<hr />
             </#if>
	        </#list>
            
        </div>
    </div>
    </div>
    
    <!-- 页脚 -->
    <script src="http://pic.lvmama.com/js/v6/public/footer.js"></script>
 
    <!-- 新窗口打开 -->
    <a id="J_href" class="hide"></a>
    <!-- 超出20天报错 -->
    <div class="J_date_msg date_msg">
        <div class="tiptext tip-warning tip-info">
            <div class="tip-arrow tip-arrow-7">
                <em>◆</em>
                <i>◆</i>
            </div>
            <p>如您需入住20天以上，请致电客服热线<span>1010-6060</span>。</p>
        </div>
    </div>

    <!-- 关键字报错 -->
    <div class="J_key_msg key_msg">
        <div class="tiptext tip-warning tip-info">
            <div class="tip-arrow tip-arrow-7">
                <em>◆</em>
                <i>◆</i>
            </div>
            <p>您查询的城市不存在,请重新选择！</p>
        </div>
    </div>

    <!-- 加载.. -->
    <div class="J_loding hide">
        <div class="loding">
            <img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/loadingGIF46px.gif" width="46" height="46" />
            <p>驴妈妈拼命加载中，请稍后...</p>
            <span>发表点评即返现，更高优惠等你来拿</span>
        </div>
    </div>

    <!-- 更多酒店品牌 -->
    <div class="J_more_hotel more-all" style="display:none"> 
		<div class="more-title"> 
			<a class="J_more_close more-close">×</a> 
			<h2>更多酒店品牌</h2> 
		</div> 
		<ul class="more-hotel"> 
			<#list hotelbrands?keys as key>
            <#if hotelbrands?? && hotelbrands.get(key) ??>
                 <#assign vo = hotelbrands.get(key)/>
             </#if>
             <#if (key_index >49) ><#break></#if>
                        <li><input type="checkbox" name="hotel_brand" value="${vo.hotelbrand}" />${key}</li>
            </#list>
		</ul> 
		<div class="text-right"><button class="J_btn pbtn pbtn-small pbtn-orange">确定</button></div> 
	</div>
    
    <!-- 热门城市推荐 -->
    
    <div style="width:360px; display:none" class="J_city auto auto-city auto-city-simple">
        <div class="auto-tip">热门城市(可直接输入城市/城市拼音)</div>
        <div class="auto-box">
            <ul class="inline">
                
            </ul>
        </div>
    </div>
    
    <div class="J_room_day room-rate"></div>
    
    <!-- 友情链接 -->
    <#if src == "seo" && seoFriendsLink?exists && seoFriendsLink.size() gt 0>
	  	<div class="hh_cooperate"> 
			<p><b>友情链接：</b>
			<span> 
				<#list seoFriendsLink?keys as linkName> 
					<a href="http://${seoFriendsLink.get(linkName)}" target="_blank">${linkName}</a> 
				</#list> 
			</span>
			</p> 
		</div>
    </#if>
    
    <script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
    <script src="http://pic.lvmama.com/js/v4/modules/pandora-dialog.js"></script>
    <script src="http://pic.lvmama.com/js/v4/modules/pandora-calendar.js"></script>
  <!-- 	<script src="http://s2.lvjs.com.cn/js/v4/modules/pandora-autocomplete.js"></script>-->
     <script src="http://api.map.baidu.com/getscript?v=1.4&ak=&services=&t=20130906090653&_=1387765019494"></script>
    <!-- <script src="http://s2.lvjs.com.cn/js/v4/hotel.js"></script>   -->
       <script src="http://www.lvmama.com/search/js/pandora-autocomplete.js"></script> 
    <script src="http://www.lvmama.com/search/js/verhotel.js"></script>
    <script src="http://pic.lvmama.com/js/common/losc.js"></script>
    <script type="text/javascript"> 
    	function verSetPage(i)
			{
			
			$("#currentPage").val(i)
			$("#form").submit(); 
			}	
			
		 $(".xhotel-info").find(".xtitle").click(function(){
        if($(this).hasClass("active")){
            $(this).removeClass("active").next().hide();
        }else{
            $(this).addClass("active").next().show();
        }
    })	
    </script> 
</body>
</html>