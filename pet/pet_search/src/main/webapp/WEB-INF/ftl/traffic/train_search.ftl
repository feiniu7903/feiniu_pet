<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>${pageTitle!"景点门票_驴妈妈旅游网"}</title>
<meta name="keywords" content="${pageKeyword!"景点门票_驴妈妈旅游网"}" />
<meta name="description" content="${pageDescription!"景点门票_驴妈妈旅游网"}" />
<#include "/WEB-INF/ftl/common/meta.ftl" >
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/train.css" > 
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/dialog.css"/>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css"/> 
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/tip.css " rel="stylesheet" type="text/css">


</head>
<body class="train">
<#include "/WEB-INF/ftl/common/header.ftl" >
<form id="orderForm" name="orderForm" method="POST" action="/buy/fill.do">
	<input type="hidden" name="buyInfo.prodBranchId"/>
	<input type="hidden" name="buyInfo.productType" value="TRAFFIC"/>
	<input type="hidden" name="buyInfo.subProductType" value="TRAIN"/>
	<input type="hidden" name="buyInfo.paymentTarget" value="TOLVMAMA"/>
	<input type="hidden" name="buyInfo.visitTime" id="buyInfo_visitTime"/>
	<input type="hidden" id="buyInfo_content" value=""/>
	<input type="hidden" name="buyInfo.localCheck" value=""/>
</form>
<div class="search_wrap">
	<!--搜索框-->
	<div class="searchbox">
        <form id="searchForm" method="get" onsubmit="return false">
        	<input id="departureCity" type="hidden" name="departureCity" value="${departurePinyin}"/>
        	<input id="arrivalCity" type="hidden" name="arrivalCity" value="${arrivalPinyin}"/>
            <span class="warning">*</span><span class="warning-txt">出发城市</span><input  cityID="#departureCity" id="start-address" type="text"
                                                                                  class="input_text autoCInput"
                                                                                  autocomplete="off" name="fromCity"
                                                                                  value="${fromDest}">
		<a href="javascript:;" id="js-change-city"><img src="http://www.lvmama.com/images/icon_change.gif" class="changeCity" /></a>
            <span class="warning">*</span><span class="warning-txt">到达城市</span><input cityID="#arrivalCity" id="purpose-address" name="toCity"
                                                                                  type="text"
                                                                                  class="input_text autoCInput"
                                                                                  value="${toDest}">
		     <span class="warning">*</span><span class="warning-txt">出发日期</span><input readonly="readonly" id="fromDate" name="fromDate" type="text" class="input_text calendar" value="${trainSearchVO.date}">
		    <span class="warning-txt">车次</span><input type="text" name="lineName" value="${trainSearchVO.lineName}" class="input_text lineName">
        	<a href="javascript:;" id="searchButton"><img src="http://www.lvmama.com/images/searchBtn.gif" class="searchBtn"/></a>		    
        </form>
    </div>
    <#if showBookFlag>
    <div class="railway_warning"><a href="javascript:;" id="js-close"><img src="http://www.lvmama.com/images/train_close.gif" width="7" height="7"></a>只可预订36小时后及20日内的车票。</div> 
    </#if>
	<!--搜索框 end-->
<div class="train-main">
<div class="train-calendar">
    <a class="cal-left " href="javascript:;"><i></i></a>

    <div class="cal-days">
        <ul>
        </ul>
    </div>
    <a class="cal-right" href="javascript:;"><i></i></a>
</div>
<script>
//日期的滚动;
    function scrollCalendar(chooseDay){
        //填充日期内容
        var month = [ '周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            today = new Date(),
            tYear = today.getFullYear(),
            tMonth = today.getMonth()+ 1,
            tDate = today.getDate() + 3,
            tDay = today.getDay() + 3 ,
            allowDays = 16,
            monthLength = new Date(tYear,tMonth,0).getDate(),
            arr = [],
            indexLine = chooseDay.indexOf('-'),
            monthDay = chooseDay.substring(indexLine+1),
            oneDay, oneWeek,
            newDate = 1,
            dataDate;
        for(var i=0; i<=allowDays; i++){
            if(monthLength >= tDate){
            	var ttDate = tDate<10 ? '0'+tDate : tDate;
                var ttMonth = tMonth<10 ? '0'+tMonth :tMonth;
                oneDay = ttMonth + '-' + ttDate;
                oneWeek = month[tDay%7];
				dataDate = tYear + '-' + oneDay;
                if(oneDay == monthDay){
                    arr.push('<li class="cal-active"><a href="javascript:;" data-date = '+dataDate+'><em>'+oneDay+'</em><i>'+oneWeek+'</i></a></li>');
                }else{
                    arr.push('<li><a href="javascript:;" data-date = '+dataDate+' ><em>'+oneDay+'</em><i>'+oneWeek+'</i></a></li>');
                }
                tDate++;
                tDay++;
            }else{
            	 var newMonth = (tMonth+1)%12 === 0 ? 12 : (tMonth+1)%12;
                 var ttMonth = newMonth<10 ? '0'+newMonth :newMonth;
                 var ttDate = newDate<10 ? '0'+newDate : newDate;
                 oneDay = ttMonth + '-' + ttDate;
                 oneWeek = month[tDay%7];
 				if(newMonth == 1){
 					dataDate = tYear+1 + '-' + oneDay;
 				}else{
 					dataDate = tYear + '-' + oneDay;
 				}
                 if(oneDay == monthDay){
                     arr.push('<li class="cal-active"><a href="javascript:;" data-date = '+dataDate+' ><em>'+oneDay+'</em><i>'+oneWeek+'</i></a></li>');
                 }else{
                     arr.push('<li><a href="javascript:;" data-date = '+dataDate+' ><em>'+oneDay+'</em><i>'+oneWeek+'</i></a></li>');
                 }
                 newDate++;
                 tDay++;
            }
        }
        $('.cal-days ul').html(arr.join(''));
        //设置选中日期的内容显示，居中
        var activeLiIndex = $('.cal-days ul li').index($('.cal-active'));
        if(activeLiIndex>3 && activeLiIndex <= 13){
            $('.cal-days ul').css('left', (3 - activeLiIndex)*103 + 'px');
        }else if(activeLiIndex>13){
            $('.cal-days ul').css('left', (3 - 13)*103 + 'px');
        }
        var index = activeLiIndex-3;
        var wholeLen = $('.cal-days ul li').length - 7;
        //设置按钮是否激活
        index = index>wholeLen ? wholeLen:(index < 0 ? 0:index);
        if(index == 0){
            $('.cal-right i').addClass('cal-tri-active');
        }else if(index == wholeLen){
            $('.cal-left i').addClass('cal-tri-active');
        }else{
            $('.cal-left i').add('.cal-right i').addClass('cal-tri-active');
        }
        //绑定向右按钮，从而使日期滚动
        $('.cal-right ').bind('click', function(){
            if(index == wholeLen){
                $('.cal-right i').removeClass('cal-tri-active');
                return false;
            }
            var ll = parseInt($('.cal-days ul').css('left')) - 103; 
            $('.cal-days ul').css('left', ll+'px');
            //$('.cal-days ul').animate({'left': '-=' + '103px'},200);
            $('.cal-left i').addClass('cal-tri-active');
            index++;
            if(index == wholeLen){
                $('.cal-right i').removeClass('cal-tri-active');
            }
        });
        //绑定向左按钮，从而使日期滚动
        $('.cal-left').bind('click', function(){
            if(index==0){
                $('.cal-left i').removeClass('cal-tri-active');
                return false;
            }
            var ll = parseInt($('.cal-days ul').css('left')) + 103; 
            $('.cal-days ul').css('left', ll+'px');
            //$('.cal-days ul').animate({'left': '+=' + '103px'},200);
            $('.cal-right i').addClass('cal-tri-active');
            index--;
            if(index==0){
                $('.cal-left i').removeClass('cal-tri-active');
            }
        });
      //点击日期 
        $('.cal-days').find('a').bind('click', function(){ 
        var date = $(this).attr('data-date');
        var $form=$("#searchForm");
      	 var param="";
      	 var departureCity =$form.find("input[name='departureCity']").val();
      	 var arrivalCity =$form.find("input[name='arrivalCity']").val();
      	 var fromCity = $.trim($form[0].fromCity.value);
      	 var toCity = $.trim($form[0].toCity.value);
      	 var lineName = $form.find("input[name='lineName']").val();
      	 var url="http://www.lvmama.com/search/train/"+departureCity+"-"+arrivalCity+".html";
      	 param="?date="+date;
      	 if($.trim(lineName)!=''){
      		 param=param+"&line="+lineName;
      	 }
      	 url+=param;
      	 window.location.href=url;
        
        });
    }
    var chooseDay = $("#fromDate").val();
    scrollCalendar(chooseDay);
</script>
    <!--筛选条件-->
    <div class="search_screen clearfix">
    	<div class="railway_search_tips"><b>${fromDest}—${toDest}</b>（共<em>${trainBeanList.size()}</em>个车次）</div>
    	<dl id="js-result-filters" class="search_screen_list">
        	<dt>车型选择：</dt>
            <dd type="0"><a href="javascript:;" class="show_all active">全部</a>
            <ul id="type0" class="search_screen_item">
                                        <li>
                    <a href="javascript:;">G-高铁</a>
                </li>
                <li>
                    <a href="javascript:;">D-动车</a>
                </li>
                <li>
                    <a href="javascript:;">T-特快</a>
                </li>
                <li>
                    <a href="javascript:;">K-快速</a>
                </li>
                <li>
                    <a href="javascript:;">其他</a>
                </li>
                </ul>                       
            </dd>                    
            <dt>发车时间：</dt>
        <dd type="1"><a href="javascript:;" class="show_all  active">全部</a>
            <ul class="search_screen_item">
                <li>
                    <a href="javascript:;">凌晨(0-6点)</a>
                </li>
                <li>
                    <a href="javascript:;">上午(6-12点)</a>
                </li>
                <li>
                    <a href="javascript:;">中午(12-13点)</a>
                </li>
                <li>
                    <a href="javascript:;">下午(13-18点)</a>
                </li>
                <li>
                    <a href="javascript:;">晚上(18-24点)</a>
                </li>
            </ul>
        </dd>
            <dt>到达时间：</dt>
        <dd type="2"><a href="javascript:;" class="show_all active">全部</a>
            <ul class="search_screen_item">
                <li>
                    <a href="javascript:;">凌晨(0-6点)</a>
                </li>
                <li>
                    <a href="javascript:;">上午(6-12点)</a>
                </li>
                <li>
                    <a href="javascript:;">中午(12-13点)</a>
                </li>
                <li>
                    <a href="javascript:;">下午(13-18点)</a>
                </li>
                <li>
                    <a href="javascript:;">晚上(18-24点)</a>
                </li>
            </ul>
        </dd>
             
            <#if formStationList??>
	            <dt>出发车站：</dt>
	            <dd type="3"><a class="show_all active" href="javascript:;">全部</a>
	            	<ul class="search_screen_item">
						<#list formStationList?keys as itemKey>
						     <li> <a href="javascript:;" pinyin="${itemKey}">${formStationList[itemKey]}</a></li>
						</#list>		
					</ul>                       
	            </dd>  
            </#if>  
            <#if toStationList??>
	            <dt style="display:none">到达车站：</dt>
	            <dd style="display:none" type="4"><a class="show_all active" href="javascript:;">全部</a>
	            	<ul class="search_screen_item">
						<#list toStationList?keys as itemKey>
						     <li><a href="javascript:;" pinyin="${itemKey}">${toStationList[itemKey]}</a></li>
						</#list>		
					</ul>                       
	            </dd>  
            </#if>    
            <dt style="display:none">是否始发：</dt>
        	<dd style="display:none" type="5"><a class="show_all active" href="javascript:;">全部</a>
            <ul class="search_screen_item">
                <li>
                    <a href="javascript:;">始发</a>
                </li>
                <li>
                    <a href="javascript:;">过路</a>
                </li>
            </ul>
        	</dd>         
    	</dl>
    	<a href="javascript:;" class="popu-open" hidefocus="false">更多<span class="icon_down"></span></a>
    </div>
    <!--筛选条件 end--> 
    
    <!--搜索结果列表-->
    <p class="train-tips">温馨提示：若无票我们会第一时间短信通知。暂不代购儿童票、学生票、军人票，敬请谅解。</p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_result" id="js-result">  
    <colgroup>
    <col style="width:140px;">
    <col style="width:140px;">
    <col style="width:115px;">
    <col style="width:115px;">
    <col style="width:275px;">
        </colgroup> 
                                           
    	<thead>
        	<tr>
            	<th>车次</th>
            	<th>发站/到站</th>
                <th class="sort-tag sort-up" data-type="startTime" data-up = "true">发时/到时<i></i></th>
    			<th class="sort-tag" data-type="timing"  data-up = "true">运行时长<i></i></th>
                <th>参考报价</th>
            </tr>
        </thead>
        <#if trainBeanList?? && trainBeanList?size gt 0>
    	<#list trainBeanList as trainBean>                                    
        <tbody class="sort-ele" filter="${trainBean.first.charCategory} ${trainBean.first.zhDepartureTime} ${trainBean.first.zhArrivalTime} ${trainBean.first.departureStationName} ${trainBean.first.arrivalStationName} <@s.if test='${trainBean.first.startStation}==true'>true</@s.if><@s.else>false</@s.else>"  line_info="${trainBean.first.lineInfoId}" visitTime='${trainSearchVO.date}'>
        	<tr>
            	<td>
                <span class="railway_num">${trainBean.first.lineName}</span>
				<a class="railway_station" href="javascript:;" >详情<span class="icon_down"></span></a>                                            
                </td>
                <td>
				<span class="departureStation <@s.if test='${trainBean.first.startStation}==true'>icon_start</@s.if><@s.else>icon_pass</@s.else>" >${trainBean.first.departureStationName}</span><br>
				<span class="arrivalStation <@s.if test='${trainBean.first.endStation}==true'>icon_end</@s.if><@s.else>icon_pass</@s.else>" >${trainBean.first.arrivalStationName}</span>
				</td>
				<td>
				<span class="railway_time sort-val">${trainBean.first.zhDepartureTime}</span><br>${trainBean.first.zhArrivalTime}
                </td>
				<td class="sort-val">${trainBean.zhTakeTime}</td>
				<td>
                    <ul class="railwayBtn" >
						<#list trainBean.ticketList as ticket>
                	    
                	     <li <#--<#if ticket_index gt 1>style="display:none"</#if>-->>
                	    	<p>
                	    		<#if ticket.pullmanTicket>
                	    			<#list ticket.pullmanList as pullman>
                	    				<#if pullman_index gt 0>${pullman.zhSeatType?replace("硬卧","")}<#else>${pullman.zhSeatType}</#if><span>&yen;${pullman.priceYuan?string("##.#")}</span>
                	    			</#list>
                	    		<#else>
                	    				${ticket.zhSeatType}：<span>&yen;${ticket.priceYuan?string("##.#")}</span>
                	    		</#if>
                	    	</p>
                	    	<#-- 暂停火车票预订功能  -->
                	   		<a class="nopar"  href="javascript:;"></a>
                	    </li>
                	    
                	    </#list>
                    </ul>
                </td>                                       
                
        	</tr>                                                               
        </tbody>
        </#list> 
        </#if>                                                    
    </table>  

    <input type="hidden" value="${trainBeanList.size()}" id="js_error_show">
    <div id="js-error" class="noResult"  >
    	<span class="tip-icon-big tip-icon-big-error noResultImg"></span>

    	<div class="noResultTxt"><p class="noResultCity">抱歉，暂无“<span>${fromDest}</span>—<span>${toDest}</span>”的列车路线！</p>

        <p>建议您重新选择出发和到达城市，或扩大筛选范围。</p></div>
	</div>
    
</div>
<div class="train-sidebar">
    <div class="qa">
        <h3>常见问题</h3>
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
    <#if (recommendHotelList?? && recommendHotelList?size gt 0) || (recommendPlaceList?? && recommendPlaceList?size gt 0)>
    <div class="tabs">
        <ul class="tabs-nav">
        <#if recommendHotelList?? && recommendHotelList?size gt 0 >
            <li class="selected">热门酒店</li>
        </#if>
        <#if recommendPlaceList?? && recommendPlaceList?size gt 0>
            <li>热门景点</li>	
        </#if>
        
        </ul>
        <div class="tabs-content clearfix">
        <#if recommendHotelList?? && recommendHotelList?size gt 0 >
        
            <div class="tabcon selected">
                <ul class="list">
                <@s.iterator value="recommendHotelList" status="st" >
                <li>
                        <a href="http://www.lvmama.com/hotel/v${placeId?if_exists}" target="_blank"><img width="60" height="40"
                                        src="${smallImageUrl?if_exists}"
                                         alt=""/></a>

                        <p><a  href="http://www.lvmama.com/hotel/v${placeId?if_exists}" target="_blank" title="${name?if_exists}" >${name?if_exists}</a><dfn>&yen;<i>${sellPrice?if_exists}</i>起</dfn></p>
                    </li>
                </@s.iterator>
                </ul>
                <a class="con-more" href="http://www.lvmama.com/search/hotel.html">更多酒店>></a>
            </div>
        </#if>
        <#if recommendPlaceList?? && recommendPlaceList?size gt 0>
            <div class="tabcon">
                <ul class="list">
                    <@s.iterator value="recommendPlaceList" status="st" >
                <li>
                        <a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank"><img width="60" height="40" src="${smallImageUrl?if_exists}" 
                                         alt=""/></a>

                        <p><a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank" title="${name?if_exists}">${name?if_exists}</a><dfn>&yen;<i>${sellPrice?if_exists}</i>起</dfn></p>
                    </li>
                </@s.iterator>
                </ul>
                <a class="con-more" href="http://www.lvmama.com/ticket">更多景点>></a>
            </div>
        </#if>
            
        </div>
    </div>
   </#if>
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
<#include "/WEB-INF/ftl/common/footer.ftl">
<script type="text/javascript" src="http://www.lvmama.com/js/train/lv_page.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/autoComplete.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/jquery.calendar.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/train/searchTrain.js"></script>

<script type="text/javascript" src="http://www.lvmama.com/js/train/sortModel.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js "></script>
<input type="hidden" id="login_hidden" value="${login}"/>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>

</body>
</html>