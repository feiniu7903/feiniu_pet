<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<title><@s.property value="seoIndexPage.seoTitle"/></title>
<meta name="keywords" content="<@s.property value="seoIndexPage.seoKeyword"/>">
<meta name="description" content="<@s.property value="seoIndexPage.seoDescription"/>">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/form.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v3/page-common.css,/styles/v3/holidayInfo.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/v3/typo.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/tip.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/orderV2.css,/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>
<body class="holiday">
<div style="background:#fff;">
<#include "/WEB-INF/pages/common/header.ftl">
</div>
<div class="wrap">
<div class="h-head">
    <span>您当前所处的位置</span>
    <span> ：</span>
    <span>度假酒店</span>
    <i></i>
    <span> <a href="http://www.lvmama.com/search/hotel/${placeSearchInfo.city?if_exists}.html" rel="nofollow" target="_blank">${placeSearchInfo.city?if_exists}度假酒店</a></span>
    <i></i>
    <span>${placeSearchInfo.name?if_exists}</span>
</div>
<div class="h-main">
<div class="h-big-box">
    <div class="h-hotel-intro">
        <div class="h-hotel-titles">
            <div class="tuan-big-head">
                <span class="h-head-hotel">${placeSearchInfo.name?if_exists}</span>
                <span class="h-en-name">${placeSearchInfo.enName?if_exists}</span>
                <div class="tuan-head-star">
                    <#if placeHotel.hotelStar == '1'><span class="h-tuan-diamond" title="经济型（驴妈妈用户评定为1.5钻）"><i style="width:30%"></i></span></#if>
                    <#if placeHotel.hotelStar == '2'><span class="h-tuan-star" title="国家旅游局评定为二星级"><i style="width:40%"></i></span></#if>
                    <#if placeHotel.hotelStar == '3'><span class="h-tuan-diamond" title="舒适型（驴妈妈用户评定为2.5钻）"><i style="width:50%"></i></span></#if>
                    <#if placeHotel.hotelStar == '4'><span class="h-tuan-star" title="国家旅游局评定为三星级"><i style="width:60%"></i></span></#if>
                    <#if placeHotel.hotelStar == '5'><span class="h-tuan-diamond" title="高档型（驴妈妈用户评定为3.5钻）"><i style="width:70%"></i></span></#if>
                    <#if placeHotel.hotelStar == '6'><span class="h-tuan-star" title="国家旅游局评定为四星级"><i style="width:80%"></i></span></#if> 
                    <#if placeHotel.hotelStar == '7'><span class="h-tuan-diamond" title="豪华型（驴妈妈用户评定为4.5钻）"><i style="width:90%"></i></span></#if>
                    <#if placeHotel.hotelStar == '8'><span class="h-tuan-star" title="国家旅游局评定为五星级"><i style="width:100%"></i></span></#if>
                </div>
            </div>
            <div class="tuan-intro">
                <div class="tuan-introduce">
                    <div class="tuan-tags">
                        <#assign topicStr=''/>
                        <#if holiDayHotelFrontList?? && holiDayHotelFrontList.size() gt 0>
                            <#list holiDayHotelFrontList as productSearchInfo>
                                <#if productSearchInfo.topic??>
                                    <#list productSearchInfo.topic?split(",") as topic>
                                        <#if topicStr?index_of("${topic}") == -1>
                                            <#assign topicStr="${topicStr},${topic}"/>
                                        </#if>
                                    </#list>
                                </#if>
                            </#list>
                        </#if>
                        <#if holidayHotelTuanGouList?? && holidayHotelTuanGouList.size() gt 0>
                            <#list holidayHotelTuanGouList as productSearchInfo>
                                <#if productSearchInfo.topic??>
                                    <#if productSearchInfo.topic??>
                                        <#list productSearchInfo.topic?split(",") as topic>
                                            <#if topicStr?index_of("${topic}") == -1>
                                            <#assign topicStr="${topicStr},${topic}"/>
                                            </#if>
                                        </#list>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                        <#list topicStr?split(",") as topic>
                            <#if topic != ""><a href="http://www.lvmama.com/search/hotel/${topic}.html" rel="nofollow" target="_blank">${topic}</a></#if>   
                        </#list>
                        <#if placeHotel.hotelTopic??><#list placeHotel.hotelTopic?split(",") as topic><a href="http://www.lvmama.com/search/hotel/${topic}.html" rel="nofollow" target="_blank">${topic}</a></#list></#if>
                    </div>
                    <p class="h-intro-word">
                      ${recommend.noticeContent?if_exists}
                    </p>
                </div>
            </div>
            <div class="h-order-outer">
                <p>
                    <dfn>&nbsp;
                        <#if placeSearchInfo.productsPrice??> 
                            ¥<i class="h-price">${placeSearchInfo.productsPrice?if_exists}</i> 起
                        </#if>
                    </dfn>
                </p>
                <p class="color-grey">&nbsp;
                    <#if placeSearchInfo.weekSales??>
                        <#if placeSearchInfo.weekSales != "0">近一周${placeSearchInfo.weekSales?if_exists}人预订</#if>
                    </#if>      
                </p>
                <p>
                    <a class="h-order-btn" href="#active"></a>
                </p>
            </div>
    </div>
<div id="h-slide">
    <a id="big-prev" class="big-prev" style="display: none;">
        <i></i>
    </a>
    <ul id="js-slide-content">
        <#list placePhotoList as placePhoto>
            <#if placePhoto_index == 0><li class="show">
            <#else><li>
            </#if>
                <img src="http://pic.lvmama.com${placePhoto.imagesUrl?if_exists}" width="732" height="478"></img>
                <div class="content-info">
                    <h3>${placePhoto.placePhotoName?if_exists}</h3>
                    <p>${placePhoto.placePhotoContext?if_exists}</p>
                </div>      
            </li>
        </#list>
    </ul>
    <a id="big-next" class="big-next" style="display: none;">
        <i></i>
    </a>
    <!--<div class="nav-layer"></div>-->
</div>
<div id="h-nav"> <!-- 当鼠标移入大图时增加“h-nav”的“small-active”属性-->
        <span id="small-prev" class="slide-prev"></span>
        <div id="small-images-wrap">
            <ul id="js-slide-nav">
                <#list placePhotoList as placePhoto>
                    <#if placePhoto_index == 0><li class="on">
                    <#assign sharePic="http://pic.lvmama.com${placePhoto.imagesUrl?if_exists}"/>
                    <#else><li>
                    </#if>
                        <img src="http://pic.lvmama.com${placePhoto.imagesUrl?if_exists}" width="61" height="50"></img>
                        <a class="small-img-layer"></a>
                        <!--<div class="small-img-content">${placePhoto_index+1}/${placePhotoList.size()}</div>-->
                    </li>
                </#list>
            </ul>
        </div>
        <span id="small-next" class="slide-next"></span>
    </div>
</div>

<!--你能这样玩-->
<#assign playingFalg=""/>
<#if otherRecommendList?? && otherRecommendList.size() gt 0>
    <#list otherRecommendList as otherRecommend>
        <#if otherRecommend.recommentType == "PLAYING">
            <#assign playingFalg = '${playingFalg}<h3 class="h-spa-head"><span>${otherRecommend.recommendName?if_exists}</span></h3><div class="lv-article typo">${otherRecommend.recommentContent?if_exists}</div>'/>
            </#if>
    </#list>
</#if>
<div class="h-banner">
    <div class="h-banner-wrap">
        <div class="h-banner-out">
            <a class="a-nav h-banner-active" href="#active" hidefocus="false">产品预订 </a>
            <a class="a-detail" href="#detail" hidefocus="false">酒店详解</a>
            <#if playingFalg != ""><a class="a-nav" href="#strategy" hidefocus="false">你能这样玩</a></#if>
            <#if (placeSearchInfo.latitude?? && placeSearchInfo.longitude??) || (trafficInfoList?? && trafficInfoList.size() gt 0)><a class="a-nav" href="#map" hidefocus="false">地图交通 </a></#if>
           <div class="cf"></div>
        </div>
    </div>
</div>
<a href="#active" id="active" hidefocus="false"></a>
<#include "holidayProductList.ftl"/>
</dl>
</div>

<a id="detail" name="detail" hidefocus="false"></a>
<div class="h-description">
<#if hotelRoomList?? && hotelRoomList.size() gt 0>
    <div class="view-title">
        <h3 class="">
            <span>房型介绍</span>
        </h3>
    </div>
    <#list hotelRoomList as hotelRoom>
        <#if hotelRoom_index == 0>
            <h3 class="h-house-type">
                <span class="h-house-name">
                    <i class="h-house-index">${hotelRoom_index+1}</i>${hotelRoom.roomName?if_exists}
                </span>
                <a href="#" class="h-detail-up  h-detail-change">
                    <span>收起</span>
                    <i></i>
                </a>
            </h3>
            <div class="h-house-wrap h-wrap-active">
                <i class="triangle-bottom"></i>
                <i class="triangle-up"></i>
                <div class="h-house-inner" style="display: block;">
                    <div class="h-house-details">
                        <ul>
        <#else>
            <h3 class="h-house-type">
                <span class="h-house-name">
                    <i class="h-house-index">${hotelRoom_index+1}</i>${hotelRoom.roomName?if_exists}
                </span>
                <a href="#" class="h-detail-down  h-detail-change">
                    <span>展开</span>
                    <i></i>
                </a>
            </h3>
            <div class="h-house-wrap h-house-wrap-none">
                <i class="triangle-bottom"></i>
                <i class="triangle-up"></i>
                <div class="h-house-inner" style="display: none;">
                    <div class="h-house-details">
                        <ul>
        </#if>  
                    <#if hotelRoom.buildingArea??><li>建筑面积：${hotelRoom.buildingArea?if_exists}平方米</li></#if>
                    <#if hotelRoom.roomFloor??><li>楼层：${hotelRoom.roomFloor?if_exists}层</li></#if>
                    <#if hotelRoom.bigBedWide?? || hotelRoom.doubleBedWide ??>
                            <li>
                                床宽：<#if hotelRoom.bigBedWide??><#if hotelRoom.bigBedWide != '0'>大床宽${hotelRoom.bigBedWide?if_exists}米</#if></#if>
                                <#if hotelRoom.doubleBedWide??><#if hotelRoom.doubleBedWide != '0'>双床宽${hotelRoom.doubleBedWide?if_exists}米</#if></#if>
                            </li>
                    </#if>
                    <#if hotelRoom.addBed??>
                            <li>可加床：
                                <#if hotelRoom.addBed=='2'>
                                        <#if hotelRoom.addBedCost??>
                                            <#if hotelRoom.addBedCost != '0'>${hotelRoom.addBedCost?if_exists}元/床/间夜<#else>免费</#if>
                                        <#else>免费
                                        </#if>
                                    <#else>不可加床
                                    </#if>
                            </li>
                    </#if>
                    <#if hotelRoom.isNonsmoking??>
                            <li>无烟房：
                                    <#if hotelRoom.isNonsmoking == '1'>有无烟楼层</#if>
                                    <#if hotelRoom.isNonsmoking == '2'>有无烟房</#if>
                                    <#if hotelRoom.isNonsmoking == '3'>该房可做无烟处理</#if>
                                    <#if hotelRoom.isNonsmoking == '4'>无</#if>
                            </li>
                    </#if>  
                    <#if hotelRoom.maxLive??>
                            <li>最多入住人数：${hotelRoom.maxLive?if_exists}人</li>
                    </#if>
                    <#if hotelRoom.broadband??>
                            <li>宽带：
                                <#if hotelRoom.broadband=='1'>免费
                                <#elseif hotelRoom.broadband=='2'>无
                                <#else>收费${hotelRoom.broadbandCost?if_exists}元/天
                                </#if>
                            </li>
                    </#if>
                    <#if hotelRoom.roomNum??>
                            <li>房间数量：${hotelRoom.roomNum?if_exists}</li>
                    </#if>
                    <#if hotelRoom.isWindow??>
                            <li>窗户：
                                <#if hotelRoom.isWindow == '1'>有窗
                                <#elseif hotelRoom.isWindow == '2'>无窗
                                <#elseif hotelRoom.isWindow == '3'>部分无窗
                                </#if>
                            </li>
                    </#if>  
                </ul>
            </div>
            <div class="lv-article typo">
            <#assign roomRecommend = "${hotelRoom.roomRecommend?if_exists}" >
            <#assign res = roomRecommend?matches("<img.+?src=\"?(.+?)\"?.*?/>")> 
            <#list res as m> 
                <#assign roomRecommend = roomRecommend?replace("src","data-original") > 
            </#list>
            <#assign res = roomRecommend?matches("<img.+?data-original=\"?(.+?)\"?.*?/>")> 
            <#list res as m> 
                <#assign roomRecommend = roomRecommend?replace(m,m?substring(0,m?length-2)+' class="lazy" src="http://pic.lvmama.com/img/v3/holiday/loadingbig.gif" '+m?substring(m?length-2,m?length)) >  
            </#list>
            ${roomRecommend}
            </div>
        </div>    
    </div>
    </#list>
</#if>

<div class="view-title h-hotel-title">
    <h3 class="">
        <span>酒店介绍</span>
    </h3>
</div>
<p class="h-time-soon">
    <#if placeHotel.hotelOpenTime??><span class="h-pad-right">${placeHotel.hotelOpenTime?string('yyyy年MM月')}开业</span></#if>
    <#if placeHotel.hotelDecorationTime??><span class="h-pad-right">${placeHotel.hotelDecorationTime?string('yyyy年MM月')}装修</span></#if>
    <#if placeHotel.hotelRoomNum??><span class="h-pad-right">${placeHotel.hotelRoomNum?if_exists}间房</span></#if>
    <span><#if placeHotel.hotelPhone??>电话：${placeHotel.hotelPhone?if_exists}</#if>   
    <#if placeHotel.hotelFax??>传真：${placeHotel.hotelFax?if_exists}</#if>    
    <#if placeHotel.hotelZipCode??>电邮：${placeHotel.hotelZipCode?if_exists}</#if></span>
</p>
<div class="h-word-indent">
    <span class="h-hotel-info">${placeSearchInfo.summary?if_exists}</span>
</div>
    <!-- 特色服务 -->
<#if otherRecommendList?? && otherRecommendList.size() gt 0>
    <#list otherRecommendList as otherRecommend>
        <#if otherRecommend.recommentType == "SPECIAL">
        <h3 class="h-spa-head">
            <span>${otherRecommend.recommendName?if_exists}</span>
        </h3>
        <div class="lv-article typo">
            <!--  替换  -->
            <#assign recommentContent = "${otherRecommend.recommentContent?if_exists}" >
            <#assign res = recommentContent?matches("<img.+?src=\"?(.+?)\"?.*?/>")> 
            <#list res as m> 
                <#assign recommentContent = recommentContent?replace("src","data-original") >   
            </#list>
            <#assign res = recommentContent?matches("<img.+?data-original=\"?(.+?)\"?.*?/>")> 
            <#list res as m> 
                <#assign recommentContent = recommentContent?replace(m,m?substring(0,m?length-2)+' class="lazy" src="http://pic.lvmama.com/img/v3/holiday/loadingbig.gif" '+m?substring(m?length-2,m?length)) >  
            </#list>
            ${recommentContent?if_exists}
        </div>
        </#if>
    </#list>
</#if>

<#if placeHotel.integratedFacilities?? || placeHotel.roomFacilities?? || placeHotel.recreationalFacilities?? || placeHotel.diningFacilities?? || placeHotel.services??>
<div class="view-title">
    <h3 class="">
        <span>酒店设施</span>
    </h3>
</div>
<div class="h-hotel-set">
    <dl>
        <#if placeHotel.integratedFacilities??>
            <dd>
                <h3>综合设施</h3>
                <ul>
                    <#list placeHotel.integratedFacilities?split(",") as integrated>
                        <li>
                            <#assign tmp_str = integrated?replace("免费","<span class='color-green'>免费</span>") > 
                            <#assign res = integrated?matches("\\([^)]+\\)")> 
                            <#list res as m> 
                                <#assign tmp_str = integrated?replace(m,"<span class='color-grey'>"+m"</span>") >  
                            </#list>
                            ${tmp_str}
                        </li>
                    </#list>
                </ul>
            </dd>
        </#if>
        <#if placeHotel.roomFacilities??>
            <dd>
                <h3>客房设施 </h3>
                <ul>
                    <#list placeHotel.roomFacilities?split(",") as room>
                        <li>
                            <#assign tmp_str = room?replace("免费","<span class='color-green'>免费</span>") > 
                            <#assign res = room?matches("\\([^)]+\\)")> 
                            <#list res as m> 
                                <#assign tmp_str = room?replace(m,"<span class='color-grey'>"+m"</span>") >  
                            </#list>
                            ${tmp_str}
                        </li>
                    </#list>
                </ul>
            </dd>
        </#if>
        <#if placeHotel.recreationalFacilities??>
            <dd>
                <h3>娱乐设施</h3>
                <ul>
                    <#list placeHotel.recreationalFacilities?split(",") as recreational>
                        <li>
                            <#assign tmp_str = recreational?replace("免费","<span class='color-green'>免费</span>") > 
                            <#assign res = recreational?matches("\\([^)]+\\)")> 
                            <#list res as m> 
                                <#assign tmp_str = recreational?replace(m,"<span class='color-grey'>"+m"</span>") >  
                            </#list>
                            ${tmp_str}  
                        </li>
                    </#list>
                </ul>
            </dd>
        </#if>
        <#if placeHotel.diningFacilities??>
            <dd>
                <h3>餐饮设施</h3>
                <ul>
                    <#list placeHotel.diningFacilities?split(",") as dining>
                        <li>
                            <#assign tmp_str = dining?replace("免费","<span class='color-green'>免费</span>") > 
                            <#assign res = dining?matches("\\([^)]+\\)")> 
                            <#list res as m> 
                                <#assign tmp_str = dining?replace(m,"<span class='color-grey'>"+m"</span>") >  
                            </#list>
                            ${tmp_str}
                        </li>
                    </#list>
                </ul>
            </dd>
        </#if>
        <#if placeHotel.services??>
            <dd>
                <h3>服务项目</h3>
                <ul>
                    <#list placeHotel.services?split(",") as service>
                        <li>
                            <#assign tmp_str = service?replace("免费","<span class='color-green'>免费</span>") > 
                            <#assign res = service?matches("\\([^)]+\\)")> 
                            <#list res as m> 
                                <#assign tmp_str = service?replace(m,"<span class='color-grey'>"+m"</span>") >  
                            </#list>
                            ${tmp_str}
                        </li>
                    </#list>
                </ul>
            </dd>
        </#if>
    </dl>
</div>
</#if>
<div class="view-title">
    <h3 class=""><span>酒店政策</span></h3>
</div>
<div class="h-hotel-policy">
    <dl>
        <dd><h3>入住和离店</h3><ul>
                <li>入住时间：14:00以后</li>
                <li>离店时间：12:00以前</li>
            </ul>
        </dd>
        <dd>
            <h3>押金/预付款</h3>
            <ul>
                <li>入住时需要出示政府核发的身份证件<br/>
                                                    请携带信用卡和现金用以支付押金或额外费用
                </li>
            </ul>
        </dd>
        <#if placeHotel.breakfastType??>
            <dd>
                <h3> 膳食安排</h3>
                <ul>
                    <li>
                            <#list placeHotel.breakfastType?split(",") as type>
                                ${type}
                                <#list placeHotel.breakfastPrice?split(",") as price>
                                    <#if type_index == price_index> 
                                        <#if price == '0'>免费<#else>RMB ${price}</#if>
                                    </#if>
                                </#list>
                            </#list>
                    </li>
                </ul>
            </dd>
        </#if>
        <#if placeHotel.petCarry??>
        <dd>
            <h3>宠物</h3>
            <ul><li><#if placeHotel.petCarry?? && placeHotel.petCarry == "true">可携带宠物<#else>不可携带宠物</#if></li></ul>
        </dd>
        </#if>
        <dd>
            <h3>特殊条款</h3>
            <ul><li>信用卡授权解除需时1-3个月。视不同国家、城市之银行操作时间而定。</li></ul>
        </dd>
        <#if placeHotel.creditCard??>
        <dd>
            <h3>授权信用卡 </h3>
            <ul>
                <#list placeHotel.creditCard?split(",") as card>
                    <#if card?contains('万事达(Master)')><li><span tip-content="境外发行信用卡—-万事达（Master）" class="tags masterCard"></span></li></#if>
                    <#if card?contains('运通(AMEX)')><li><span tip-content="境外发行信用卡—-运通（AMEX）" class="tags amex"></span></li></#if>
                    <#if card?contains('威士(VISA)')><li><span tip-content="境外发行信用卡—-威士（VISA）" class="tags visa"></span></li></#if>
                    <#if card?contains('大来(DinersClub)')><li><span tip-content="境外发行信用卡—-大来（Diners Club）" class="tags dClub"></span></li></#if>
                    <#if card?contains('JCB')><li><span tip-content="境外发行信用卡—-JCB" class="tags jcb"></span></li></#if>
                    <#if card?contains('国内发行银联卡')><li><span tip-content="国内发行银联卡" class="tags uPay"></span></li></#if>
                </#list>
            </ul>
        </dd>
        </#if>
    </dl>
</div>
</div>

<!-- 你能这样玩 -->
<#if playingFalg != "">
<a name="strategy" id="strategy" hidefocus="false"></a>
<div class="strategy">
<div class="h-how-play">
    <div class="view-title">
        <h3 class="">
            <span>你能这样玩</span>
        </h3>
    </div>
    <#assign playingContent = "${playingFalg?if_exists}" >
    <#assign res = playingContent?matches("<img.+?src=\"?(.+?)\"?.*?/>")> 
    <#list res as m> 
        <#assign playingContent = playingContent?replace("src","data-original") >   
    </#list>
    <#assign res = playingContent?matches("<img.+?data-original=\"?(.+?)\"?.*?/>")> 
    <#list res as m> 
        <#assign playingContent = playingContent?replace(m,m?substring(0,m?length-2)+' class="lazy" src="http://pic.lvmama.com/img/v3/holiday/loadingbig.gif" '+m?substring(m?length-2,m?length)) >  
    </#list>
    ${playingContent?if_exists}
</div>
</div>
</#if>
<!--地图交通-->
<#if (placeSearchInfo.latitude?? && placeSearchInfo.longitude??) || (trafficInfoList?? && trafficInfoList.size() gt 0)>
<a name="map" id="map" hidefocus="false"></a>
<div class="map">
<div class="h-map">
    <div class="view-title">
        <h3 class="">
            <span>地图交通</span>
        </h3>
    </div>
    <#if placeSearchInfo.latitude?? && placeSearchInfo.longitude??>
    <div class="h-big-map">
       <iframe scrolling="no" width="736px" height="510px" frameborder="0"  marginheight="0" marginwidth="0" src="http://www.lvmama.com/dest/baiduMap/getBaiduMapCoordinate.do?id=${placeSearchInfo.placeId?c}&windage=0.005&width=730px&height=500px&flag=2"></iframe>
    </div>
    </#if>
   <#if trafficInfoList?? && trafficInfoList.size() gt 0>
        <h3 class="h-spa-head">
            <span>交通信息</span>
        </h3>
            <#assign trafficType=''/>
            <!--迭代出所有的交通类型-->
            <#list trafficInfoList as traffic>
                <#if trafficType?index_of('${traffic.chTrafficInfo}')==-1>
                    <#assign trafficType ='${trafficType},${traffic.chTrafficInfo}'/>
                </#if>
            </#list>
            <!--依次输入交通信息 详情-->
            <#list trafficType?split(",") as type>
                <dl class="h-traffic-detail">
                    <dt>${type}</dt>
                    <dd>
                            <#list trafficInfoList as trafficeInfo>
                                <#if type == trafficeInfo.chTrafficInfo>
                                    <ul>
                                      <li class="h-traffic-name">${trafficeInfo.from?if_exists}</li>
                                      <li class="h-traffic-distance">距离酒店${trafficeInfo.distance?if_exists}公里</li>
                                      <li class="h-traffic-juti">${trafficeInfo.description?if_exists}</li>
                                    </ul>
                                </#if>
                            </#list>
                    </dd>
                </dl>
            </#list>
        <dl class="cf"></dl>
    </#if>
</div>
</div>
</#if>
</div>
<div class="h-small-box">
        <#if placeSearchInfo.latitude?? && placeSearchInfo.longitude??>
        <div class="box">
            <h3>酒店位置</h3>
            <div class="hotel-lin-content">
                    <#if placeSearchInfo.latitude?? && placeSearchInfo.longitude??>
                    <div class="h-small-map">
                        <a href="#map" id="js-map" class="big-map" hidefocus="false"></a>
                        <iframe scrolling="no" frameborder="0"  marginheight="0" marginwidth="0" width="180px" height="180px" src="http://www.lvmama.com/dest/baiduMap/getBaiduMapCoordinate.do?id=${placeSearchInfo.placeId?c}&amp;windage=0.005&amp;width=170px&amp;height=178px&amp;navigationControlFlag=false"></iframe>
                    </div>
                    </#if>
                    <#if placeSearchInfo.address??><div>地址:${placeSearchInfo.address?if_exists}</div></#if>
                    <#if landMarkList?? && landMarkList.size() gt 0>
                    <ul class="h-place-list">
                        <#list landMarkList as langMark>
                            <li>
                                <span class="h-place-name">${langMark.landMarkName}</span>
                                <span class="h-place-distance">（约${langMark.distance}km）</span>
                            </li>                   
                        </#list>
                    </ul>
                    </#if>
            </div>
        </div>
    </#if>
    <#if hotelNoticeList?? && hotelNoticeList.size() gt 0>
        <div class="box">
            <h3>公告</h3>
            <div class="hotel-lin-content">
                <ul class="h-remark">
                        <#list hotelNoticeList as hotelNotice>
                            <li>${hotelNotice.noticeContent?if_exists}<li>
                        </#list>
                </ul>
            </div>
        </div>
    </#if>
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
    <#if historyCookieList?? && historyCookieList.size() gt 0>
        <div class="box">
            <h3>最近浏览</h3>
            <dl>
                    <#list historyCookieList as history>
                        <dd>
                            <a class="item-img" href="http://www.lvmama.com/hotel/v${history.placeId?if_exists}" rel="nofollow" target="_blank">
                                <img class="lazy-history" src="http://pic.lvmama.com/img/v3/holiday/loadingmini.gif" data-original="http://pic.lvmama.com${history.imageUrl?if_exists}" width="85" height="55" alt="">
                            </a>
                            <div class="item-info">
                                <p><a href="http://www.lvmama.com/hotel/v${history.placeId?if_exists}" target="_blank">${history.name?if_exists}</a></p>
                                <p><dfn>&yen;<i>${history.productsPrice?if_exists}</i>起</dfn></p>
                            </div>
                        </dd>
                    </#list>
            </dl>
        </div>
    </#if>
</div>
</div>
<div class="vm-fixed">
    <a id="go-top" class="go-top" href="javascript:;" style="display: block;"></a>
    <a class="feedback" target="_blank" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do"></a>
    <a class="bds_tsina" href="javascript:(function(){window.open('http://service.weibo.com/share/share.php?url='+encodeURIComponent(document.location.href)+'&appkey=87692682&language=zh_cn&title=${placeSearchInfo.name?if_exists}，“${recommend.noticeContent?if_exists}”，我很喜欢，分享给大家啦！&pic=${sharePic?if_exists}&searchPic=true');})()"></a>
    <a class="bds_qzone" href="javascript:(function(){window.open('http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+encodeURIComponent(document.location.href)+'&title=${placeSearchInfo.name?if_exists}&desc=${placeSearchInfo.name?if_exists}，“${recommend.noticeContent?if_exists}”，我很喜欢，分享给大家啦！');})()"></a>
    <a class="bds_tqq" href="javascript:(function(){window.open('http://v.t.qq.com/share/share.php?title=${placeSearchInfo.name?if_exists}，“${recommend.noticeContent?if_exists}”，我很喜欢，分享给大家啦！&url='+encodeURIComponent(document.location.href)+'&appkey=09fab729da0a45b2bc22f2d69e16f70b');})()"></a>
</div>
</div>
<!--- nixianjun 8.27-->
<div class="holiday-footer">
    <script src="http://pic.lvmama.com/js/common/copyright.js"></script>
    <div class="hh_cooperate"> 
       <!-- 热门推荐-->
        <#include "/WEB-INF/pages/staticHtml/friend_link/hotel_detail_footer.ftl">
        <!-- 友情链接-->
        <@s.if test="null!=(seoLinksList)&&(seoLinksList).size()>0">
            <p><b>友情链接：</b><span>
             <@s.iterator value="(seoLinksList)" var ="v" status="st">
                <a target="_blank" href="${linkUrl?if_exists }">${linkName?if_exists }</a>
             </@s.iterator>
            </span></p>
        </@s.if>
    </div>
</div>
    <script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ui_plugin/jquery-time-price-table.js"></script> 
    <script src="http://pic.lvmama.com/min/index.php?f=js/v3/plugins/jQuery.slide3.js,/js/v3/holidayInfo.js,/js/common/jquery.lazyload.min.js"></script>
    <script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
	<#if !login>
		<#-- 未登录状态下需要显示快速登录层 S -->
		<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
	</#if>
    <script>
    $('span[class^="tags"]').ui('lvtip', {hovershow:500});
    //图片延迟加载
    $("img.lazy").lazyload({ 
        threshold : 310,
        failure_limit : 3
    });
    //浏览历史延迟加载 
    var load_history_img = function(){ 
        $("img.lazy-history").each(function(i,n){ 
        $(n).attr("src",$(n).attr("data-original")); 
    }); 
    } 
    window.setTimeout(load_history_img,4000);
    </script>
    <script src="http://pic.lvmama.com/js/common/losc.js"></script>
    <script>
    	cmCreateProductviewTag("${placeSearchInfo.placeId?if_exists}", "<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(placeSearchInfo.name)"/>", "P0001");
    	cmCreatePageviewTag("度假酒店详情_<@s.property value="placeSearchInfo.city"/>_<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(placeSearchInfo.name)"/>（可预订商品1）", "P0001", null, null);
    	 $(".ibmclass").live("click",function(){
    		eval("("+$(this).attr("ibmc")+")");
    	});
      	function cmCreateShopAction5s(productId, productName, price, subType){
      		cmCreateShopAction5Tag(productId, productName, "1", price, subType);
			cmDisplayShops();
      	}
    </script>
</body>
</html>