(function (global, $) {
    "use strict"  // 严格模式

    var mapSource = null,
        $container = null,
        zIndex = 0;

    var map = {
        init: function (option) {
            var point = new BMap.Point(option.longitude, option.latitude); // 创建一个地理位置坐标
            $container = $("#" + option.container);

            mapSource = new BMap.Map(option.container, {
                enableMapClick: false
            });
            mapSource.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别
            mapSource.addControl(new BMap.NavigationControl({
                anchor: BMAP_ANCHOR_TOP_LEFT,
                type: BMAP_NAVIGATION_CONTROL_ZOOM
            }));  //左下角，仅包含缩放按钮
            mapSource.enableScrollWheelZoom(); //启用滚轮放大缩小

            // 第一次初始化覆盖物
            map.createCover(option);
        },
        createCover: function (option) {
            var myCompOverlay = null,
                point = new BMap.Point(option.longitude, option.latitude),
                len = $container.find("div.map-mark").length;

            if (option.num <= len) {
                map.panTo(option.longitude, option.latitude, option.num);
                return;
            }

            // 自定义覆盖物
            function ComplexCustomOverlay(point, num, text) {
                this._point = point;
                this._num = num;
                this._text = text;
            }

            ComplexCustomOverlay.prototype = new BMap.Overlay();

            ComplexCustomOverlay.prototype.initialize = function (map) {
                var $wrap = this._$wrap = $('<div class="map-mark"><span class="map-num">' + this._num + '</span><div class="map-mark-inner">' + this._text + '</div></div>');

                this._map = map;
                mapSource.getPanes().labelPane.appendChild($wrap[0]);

                return $wrap[0];
            };

            ComplexCustomOverlay.prototype.draw = function () {
                var map = this._map,
                    pixel = map.pointToOverlayPixel(this._point);

                this._$wrap.css({
                    left: pixel.x - 13,
                    top: pixel.y - 32
                });
            }

            // 创建一个自定义的覆盖物
            myCompOverlay = new ComplexCustomOverlay(point, option.num, option.text);
            mapSource.addOverlay(myCompOverlay);

            // 
            map.panTo(option.longitude, option.latitude, option.num);
        },
        panTo: function (longitude, latitude, index) {
            mapSource.panTo(new BMap.Point(longitude, latitude));
            $container.find("div.map-mark").removeClass("active");
            $container.find("div.map-mark").eq(index - 1).addClass("active");
        }
    }

    global.mapMark = map;
}(this, jQuery));

$(function () {	
    // 数据列表ID
    var ids = '';

    $("div.hotel-object").each(function () {
        var id = $(this).attr("data-sources");
        if (ids == '') {
            ids = id;
        } else {
            ids = ids + ',' + id;
        }

    });

    function maxDay() {
        var d1 = $(".J_calendar").eq(0).val().split('-'),
            d2 = $(".J_calendar").eq(1).val().split('-'),
            day = 0;

        d1 = new Date(parseInt(d1[0], 10), parseInt(d1[1], 10) - 1, parseInt(d1[2], 10));
        d2 = new Date(parseInt(d2[0], 10), parseInt(d2[1], 10) - 1, parseInt(d2[2], 10));
        day = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);

        return (day > 20);
    }

    // 日历
    function selectDateCallback() {
        var d1 = $(this.options.trigger).eq(0).val().split('-'),
            d2 = $(this.options.trigger).eq(1).val().split('-'),
            day = 0,
            nd = null,
            offset = null,
            week = "",
            d3 = $(this.options.trigger).eq(0).val().split('-'),
            d4 = $(this.options.trigger).eq(1).val().split('-'),
            d = {};

        d1 = new Date(parseInt(d1[0], 10), parseInt(d1[1], 10) - 1, parseInt(d1[2], 10));
        d2 = new Date(parseInt(d2[0], 10), parseInt(d2[1], 10) - 1, parseInt(d2[2], 10));
        day = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);
        offset = $(this.options.trigger).eq(1).offset();

        // hack
        if (this.$trigger.attr("data-check") === "checkIn") {
            // 超出20天报错
            if (day > 20) {
                $(".J_date_msg").css({
                    left: offset.left,
                    top: offset.top - this.$trigger.outerHeight(true) - 15
                }).show();
            } else {

                if (day <= 0) {
                    nd = this.addDays(d1, 1);
                    week = this.setToday({
                        year: parseInt(nd.getFullYear(), 10),
                        month: parseInt(nd.getMonth() + 1, 10),
                        day: parseInt(nd.getDate(), 10)
                    });
                    // 填充文案
                    if (typeof week === "string") {
                        $(this.options.trigger).eq(1).siblings("div.date-info").find("span").html(week);
                    } else {
                        $(this.options.trigger).eq(1).siblings("div.date-info").find("span").html(this.options.weeks[this._weekIndex + 1 === 7 ? 0 : this._weekIndex + 1]);
                    }
                    $(this.options.trigger).eq(1).val(nd.getFullYear() + "-" + this.mend(nd.getMonth() + 1) + "-" + this.mend(nd.getDate()));
                }

                $(".J_date_msg").hide();
            }

            d = {
                year: parseInt(d3[0], 10),
                month: parseInt(d3[1], 10),
                day: parseInt(d3[2], 10)
            };
            week = this.setToday(d);

            // 填充文案
            if (typeof week === "string") {
                this.$trigger.siblings("div.date-info").find("span").html(week);
            } else {
                this.$trigger.siblings("div.date-info").find("span").html(this.options.weeks[this._weekIndex]);
            }

            $(this.options.trigger).eq(1).click();
            return false;
        } else {

            // 超出20天报错
            if (day > 20) {
                $(".J_date_msg").css({
                    left: offset.left,
                    top: offset.top - this.$trigger.outerHeight(true) - 15
                }).show();
            } else {
                $(".J_date_msg").hide();
            }

            d = {
                year: parseInt(d4[0], 10),
                month: parseInt(d4[1], 10),
                day: parseInt(d4[2], 10)
            };
            week = this.setToday(d);

            // 填充文案
            if (typeof week === "string") {
                this.$trigger.siblings("div.date-info").find("span").html(week);
            } else {
                this.$trigger.siblings("div.date-info").find("span").html(this.options.weeks[this._weekIndex]);
            }

        }


    }

    var calendar = pandora.calendar({
        trigger: ".J_calendar",
        triggerClass: "J_calendar",
        selectDateCallback: selectDateCallback,
        cascade: {
            days: 1, // 天数叠加一天
            trigger: ".J_calendar",
            isTodayClick: false
        },
        template: {
            warp: '<div class="ui-calendar ui-calendar-mini"></div>',
            calControl: '<span class="month-prev" {{stylePrev}} title="上一月">‹</span><span class="month-next" {{styleNext}} title="下一月">›</span>',
            calWarp: '<div class="calwarp clearfix">{{content}}</div>',
            calMonth: '<div class="calmonth">{{content}}</div>',
            calTitle: '<div class="caltitle"><span class="mtitle">{{month}}</span></div>',
            calBody: '<div class="calbox">' +
                        '<i class="monthbg">{{month}}</i>' +
                        '<table cellspacing="0" cellpadding="0" border="0" class="caltable">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th class="sun">日</th>' +
                                    '<th class="mon">一</th>' +
                                    '<th class="tue">二</th>' +
                                    '<th class="wed">三</th>' +
                                    '<th class="thu">四</th>' +
                                    '<th class="fri">五</th>' +
                                    '<th class="sat">六</th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody>' +
                                '{{date}}' +
                            '</tbody>' +
                        '</table>' +
                    '</div>',
            weekWarp: '<tr>{{week}}</tr>',
            day: '<td {{week}} {{dateMap}} >' +
                    '<div {{className}}>{{day}}</div>' +
                 '</td>'
        }
    });
    
    $(".date-info").click(function (e) {
        var event = e || window.event;

        //IE用cancelBubble=true来阻止而FF下需要用stopPropagation方法
        event.stopPropagation ? event.stopPropagation() : (event.cancelBubble = true);

        var $input = $(this).siblings("input.J_calendar");
        $input.focus();
        $input.click();
        
    });

    // 热门城市推荐
    var $AUTO = $(".J_autocomplete"),
        $CITY = $(".J_city");

    //读取热门城市
    function getCity() {
    	 var d1 = $(".J_calendar").eq(0).val(),
         d2 = $(".J_calendar").eq(1).val();
        var url = "http://www.lvmama.com/dest/newplace/commAction!getRecommendInfoWithIdJson.do?callback=?";
        $.getJSON(url, { recommendBlockId: 7142 }, function (txt) {
            var arrayRm = txt.rm,
                html = "";

            for (var i = 0; i < arrayRm.length; i++) {
                html += '<li><a href="http://hotels.lvmama.com/list/' + arrayRm[i].name + '.html?beginBookTime=' + d1 + '&&endBookTime=' + d2 + '">' + arrayRm[i].name + '</a></li>';
            }

            $CITY.find("ul").html(html);
        });
    }
    getCity();

    $AUTO.click(function () {
        var offset = $(this).offset(),
            top = offset.top + $(this).outerHeight(true),
            left = offset.left;

        // 隐藏错误提示框
        $KEY_MSG.hide();

        if ($(".J_ui_autocomplete").is(":hidden")) {

            $CITY.css({
                position: "absolute",
                zIndex: "9999",
                top: top,
                left: left
            }).show();

        }
    });

    $CITY.find("a").live("click", function () {
        var val = $(this).text(),
            bool = maxDay();

        $CITY.hide();

        $(".J_searchId").val("");
        $(".J_longitude").val("");
        $(".J_latitude").val("");
        $(".J_parentId").val("");
        $(".J_searchType").val("");
        $AUTO.val(val);
        $KEYWORD.val(val);

        if (bool) {
            return false;
        }

        // 执行塞选 submit
        $("#form").submit();
    });

    $(document).click(function (e) {
        var target = $(e.target);

        if (!target.hasClass("J_autocomplete") && !target.parent().hasClass("J_city")) {
            $CITY.hide();

            $AUTO.val($(".J_keyword").val());
            //var firstVal = $('.auto-box').find('li a').eq(0).text();
            //$AUTO.val(firstVal);
        }

    });

    // 自动补全
    pandora.autocomplete({
        trigger: ".J_autocomplete",
        ajaxUrl: "http://www.lvmama.com/search/verhotelAutocomplete.do",
        isTarget: true,
        targetUrl: "http://hotels.lvmama.com/list/",
        template: {
            warp: '<div class="J_ui_autocomplete auto auto-single auto-key" style="display:none"></div>',
            header: '<div class="auto-tip">搜索“<b>{{keyword}}</b>”</div>',
            body: '<div class="auto-box"><ul>{{content}}</ul></div>',
            list: '<li>{{icon}}<a href="javascript:;" hidefocus="false" {{dateMap}}>{{item}}</a></li>',
            icon: '<i {{className}}></i>'
        }
    });

    // 按钮搜索
    var $KEY_MSG = $(".J_key_msg"),
        $KEYWORD = $(".J_keyword"),
        $SUBMIT = $(".J_submit");

    // 您查询的城市不存在,请重新选择！
    $SUBMIT.click(function () {
        var offset = $AUTO.offset(),
            bool = maxDay();

        if ($.trim($AUTO.val()) === "") {
            $AUTO.click();
            return false;
        }

        if (bool) {
            return false;
        }

        if ($.trim($AUTO.val()) === $KEYWORD.val()) {

            // 执行塞选 submit
        	$("#form").attr("action", "http://hotels.lvmama.com/list/" + $KEYWORD.val() + ".html");
            $("#form").submit();

        } else if ($(".J_isAutocompleteFirstOne").val() == 1) {
            $("#form").attr("action", "http://hotels.lvmama.com/list/" + $KEYWORD.val() + ".html");
            $("#form").submit();
        } else {

            $KEY_MSG.css({
                left: offset.left,
                top: offset.top - $AUTO.outerHeight(true) - 15
            }).show();
        }

    });

    // 选中样式名
    var CLASS_NAME = "active",
        CLASS_NAME_ALL = "item-all-active",
        CLASS_HIDE = "hide";

    // 塞选 filter
    var $FILTER = $(".J_filter");

    $FILTER.find("li a").click(function () {
        var name = $(this).attr("name"),
            value = $(this).attr("value"),
            index = $(this).parent("li").index(),
            val = $("#form").find("input[name=" + name + "]").val(),
        	actionUrl = $(".J_actionUrl").val();

        if (index == 0) {
            $("#form").find("input[name=" + name + "]").val("");
        } else {

            if ($(this).parent("li").hasClass(CLASS_NAME)) {
                val = val.replace(value + ",", "").replace(value, "");

                if (val === "") {
                    $("#form").find("input[name=" + name + "]").val("");
                } else {
                    $("#form").find("input[name=" + name + "]").val(val);
                }

            } else {
                if (val === "") {
                    $("#form").find("input[name=" + name + "]").val(value);
                } else {
                    $("#form").find("input[name=" + name + "]").val(val + "," + value);
                }

            }

        }
        //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }

        // 执行submit
        $("#form").submit();
    });

    // 酒店位置
    var $FIL_ITEM = $(".J_fil_item"),
        $PERIPHERY = $(".J_periphery");

    $FIL_ITEM.find("li").click(function () {
        var index = $(this).index();

        if (index == 0) {
            $PERIPHERY.hide();

            $("#form").submit();
            
        } else {
            $FIL_ITEM.find("li").eq(0).removeClass(CLASS_NAME_ALL);
            if ($(this).hasClass(CLASS_NAME)) {
                $(this).removeClass(CLASS_NAME);
                $PERIPHERY.eq(index - 1).hide();
                $FIL_ITEM.find("li").eq(0).addClass(CLASS_NAME_ALL);
            } else {
                $FIL_ITEM.find("li").removeClass(CLASS_NAME);
                $PERIPHERY.hide();
                $(this).addClass(CLASS_NAME);
                $PERIPHERY.eq(index - 1).show();
                
            }

        }

    });

    // 酒店位置 二级菜单
    $PERIPHERY.find("ul.list0").find("li").click(function () {
        var index = $(this).index(),
            $parent = $(this).parents("div.periphery");

        $(this).siblings().removeClass(CLASS_NAME);
        $(this).addClass(CLASS_NAME);

        $parent.find("ul.list1").addClass(CLASS_HIDE);
        $parent.find("ul.list1").eq(index).removeClass(CLASS_HIDE);
    });
    // 酒店位置 塞选
    $PERIPHERY.find("ul.list1 a").click(function () {
        var longitude = $(this).attr("longitude"),
            latitude = $(this).attr("latitude"),
            parentId = $(this).attr("parentId"),
            searchId = $(this).attr("searchId"),
            key = $(this).attr("title");
//        	actionUrl = $(".J_actionUrl").val();

        $(".J_searchId").val(searchId);
        $(".J_longitude").val(longitude);
        $(".J_latitude").val(latitude);
        $(".J_parentId").val(parentId);
        $("#form").attr("action","http://hotels.lvmama.com/list/"+key+".html");
        
        $AUTO.val(key);
        $KEYWORD.val(key);

        // 执行塞选 submit
        $("#form").submit();
    });

    
    // 酒店价格
    var $FILTER_PRICE = $(".J_filter_price");

    $FILTER_PRICE.find("li[class!=interval-box] a").click(function () {
        var name = $(this).attr("name"),
            value = $(this).attr("value"),
            index = $(this).parent("li").index(),
            actionUrl = $(".J_actionUrl").val();
        	
        
        $PRICE.eq(0).val("");

        if (index == 0) {
            $("#form").find("input[name=" + name + "]").val("");
        } else {
            $("#form").find("input[name=" + name + "]").val(value);
        }
        //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }

        // 执行塞选 submit
        $("#form").submit();
    });

    // 自定义价格
    var $PRICE = $(".J_price"),
        $PRICE_CLE = $(".J_price_clear"),
        $PRICE_fix = $(".J_price_fix");

    $PRICE_fix.click(function () {
        var val1 = $PRICE.eq(0).val(),
            val2 = $PRICE.eq(1).val(),
            actionUrl = $(".J_actionUrl").val();

        if (val1 !== "" && val2 !== "") {
            //$PRICE_CLE.show();

            $("#form").find("input[name=minproductsprice]").val(val1);
            $("#form").find("input[name=maxproductsprice]").val(val2);
            $("#form").find("input[name=hotelprice]").val("0");
            
            //seo
            if(actionUrl != null || actionUrl != ""){
            	$("#form").attr("action",actionUrl);
            }

            // 执行塞选 submit
            $("#form").submit();
        }

    });
    
    $PRICE.click(function () {
    	 $("#form").find("input[name=hotelprice]").val('');
    });

    $PRICE_CLE.click(function () {
        $PRICE.val("");
        $("#form").find("input[name=minproductsprice]").val("");
        $("#form").find("input[name=maxproductsprice]").val("");
        $("#form").find("input[name=hotelprice]").val("");
        $(this).hide();

        // 执行塞选 submit
        $("#form").submit();
    });

    $PRICE.keydown(function (e) {
        var code = e.charCode ? e.charCode : e.keyCode;

        if (code == 8 || code == 9) {
            return true;
        }

        code = code >= 96 ? code - 48 : code;

        if ((code < 48 || code > 57)) {
            return false;
        } else {
            return true;
        }
    });

    // 清楚所有塞选条件
    var $CLEAR_FILTER = $(".J_clear_filter");

    $CLEAR_FILTER.live("click", function () {
    	var actionUrl = $(".J_actionUrl").val();
        $("#form").find("input[data-marked=J][type=hidden]").val("");
        // $("#form").find("input[name=ranktype]").val("1");
        
        //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }
        // 执行塞选 submit
        $("#form").submit();
    });

    // 清楚项
    var $CLEAR_ITEM = $(".J_clear_item");

    $CLEAR_ITEM.find("a[class!=J_clear_filter]").live("click", function () {
        //  执行删除某项条件
        var name = $(this).attr("name"),
            value = $(this).attr("value"),
            val = $("#form").find("input[name=" + name + "]").val(),
            actionUrl = $(".J_actionUrl").val();

        val = val.replace(value + ",", "").replace(value, "");
        $("#form").find("input[name=" + name + "]").val(val);
		if("hotelprice"==name){
        	$("#form").find("input[name=minproductsprice]").val('');
        	$("#form").find("input[name=maxproductsprice]").val('');
//        	$("#minproductsprice").val('');
//        	$("#maxproductsprice").val('');
        }
        // 执行塞选 submit
		 //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }
        $("#form").submit();
    });

    // 排序
    var $SOTR = $(".J_sort"),
        CLASS_NAME_UP = "sort-up";

    $SOTR.find("a").click(function () {
        var name = $(this).attr("name"),
            value = $(this).attr("value"),
            index = $(this).parent("li").index(),
            actionUrl = $(".J_actionUrl").val();

        if (index == 1 && $(this).parent("li").hasClass(CLASS_NAME)) {

            if ($(this).hasClass(CLASS_NAME_UP)) {
                $("#form").find("input[name=" + name + "]").val("3");
            } else {
                $("#form").find("input[name=" + name + "]").val(value);
            }
        } else {

            $("#form").find("input[name=" + name + "]").val(value);
        }
        //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }

        // 执行塞选 submit
        $("#form").submit();
    });

    // 限时抢购 返现
    var $ACTIVITY = $(".J_activity");

    $ACTIVITY.find("a").click(function () {
        var name = $(this).attr("name"),
            value = $(this).attr("value"),
        	actionUrl = $(".J_actionUrl").val();
        

        if ($(this).parent("li").hasClass(CLASS_NAME)) {
            $("#form").find("input[name=" + name + "]").val("");
        } else {
            $("#form").find("input[name=" + name + "]").val(value);
        }
        
        //seo
        if(actionUrl != null || actionUrl != ""){
        	$("#form").attr("action",actionUrl);
        }
        // 执行塞选 submit
        $("#form").submit();
    });

    // 酒店品牌 更多
    var $MORE = $(".J_more"),
        $MORE_HOTEL = $(".J_more_hotel"),
        $MORE_HOTEL_INPUT = $(".more-hotel"),
        $BTN = $(".J_btn"),
        $MORE_CLOSE = $(".J_more_close"),
        checkedNum = 0,
        cacheVal = null;

    $MORE.click(function () {
        var offset = $(this).offset(),
            top = offset.top,
            left = offset.left,
            w = $MORE_HOTEL.outerWidth(true);

        $MORE_HOTEL.css({
            position: "absolute",
            top: top + 30,
            left: left - w + 20
        }).show();
      //绝对差值=可视区域高度-(元素距高-已滚动高度+元素外围高度) 
        var absHeight=$(window).height()-($MORE_HOTEL.offset().top-$(document).scrollTop()+$MORE_HOTEL.outerHeight()); 
        if(absHeight<0){ 
        $(document).scrollTop($(document).scrollTop()+Math.abs(absHeight)); 
        } 

        
    });

    $BTN.click(function () {
        $MORE_HOTEL.hide();
        var actionUrl = $(".J_actionUrl").val();

        if (checkedNum > 0) {
        	//seo
        	if(actionUrl != null || actionUrl != ""){
        		$("#form").attr("action",actionUrl);
        	}
            $("#form").submit();
        } else {
            $("#form").find("input[name=" + name + "]").val(cacheVal);
        }
        
    });

    $MORE_CLOSE.click(function () {
        $("#form").find("input[name=" + name + "]").val(cacheVal);
        $MORE_HOTEL_INPUT.find("li").removeClass(CLASS_NAME);
        $MORE_HOTEL_INPUT.find("li input").attr("checked", false);
        $MORE_HOTEL.hide();
    })
    
    $MORE_HOTEL_INPUT.find("li").live("click", function () {
        var $input = $(this).find("input"),
            name = $input.attr("name"),
            value = $input.attr("value"),
            val = $("#form").find("input[name=" + name + "]").val();

        cacheVal = val;

        if (!$(this).hasClass(CLASS_NAME)) {
            checkedNum++;
            if (val === "") {
                $("#form").find("input[name=" + name + "]").val(value);
            } else {
                $("#form").find("input[name=" + name + "]").val(val + "," + value);
            }
            $input.attr("checked", true);
            $(this).addClass(CLASS_NAME);
        } else {
            checkedNum--;
            val = val.replace(value + ",", "").replace(value, "");
            
            if (val === "") {
                $("#form").find("input[name=" + name + "]").val("");
            } else {
                $("#form").find("input[name=" + name + "]").val(val);
            }
            $input.attr("checked", false);
            $(this).removeClass(CLASS_NAME);
        }

    });

    // 更多塞选条件
    var $FILTER_ALL = $(".J_filter_all"),
        $FIL_CTL = $(".J_fil_ctl");
    $FIL_CTL.click(function () {
    	var activeFlag = $(".J_activeFlag").val();
    	
    	if(activeFlag == "")
    	{
    		$(".J_activeFlag").val("0");
    	}else if(activeFlag == "0")
    	{
    		$(".J_activeFlag").val("1");
    	}else if(activeFlag == "1")
    	{
    		$(".J_activeFlag").val("0");
    	}
    	
        if ($(this).hasClass(CLASS_NAME)) {
            $FILTER_ALL.stop().slideUp("slow");
            $(this).removeClass(CLASS_NAME);
        } else {
            $FILTER_ALL.stop().slideDown("slow");
            $(this).addClass(CLASS_NAME);
        }
    });

    // 加载...
    var $LODING = $(".J_loding"),
        LODING = null;

    function loding() {
        LODING = $.dialog({
            wrapClass: "dialog-loding",
            content: $LODING.html()
        });
    }

    // 展开全部房型
    var $MORE_ROOM = $(".J_more_room");

    $MORE_ROOM.click(function () {
        var $room = $(this).parent().siblings("div.J_room"),
            id = $(this).parents("div.hotel-object").attr("data-sources");

        if ($(this).parent().hasClass(CLASS_NAME)) {
            //$room.css("height", "81px");
            $room.find("table").show();
            $room.find("table").eq(1).remove();
            $(this).parent().removeClass(CLASS_NAME);
            $(this).html("展开全部房型");
        } else {
            $(this).parent().addClass(CLASS_NAME);
            $room.css("height", "auto");
            
            // 执行加载剩下的房型数据
            loding();
            roomAllInfo($room, id);
            $(this).html("收起全部房型");
        }

    });
    
	// 礼物 
    function tipFn(){
    	
    	$('.J_tip').ui('lvtip', { 
	    	templete: 3, 
	    	place: 'bottom-left', 
	    	offsetX: 0, 
	    	offsetY: 0, 
	    	events: "live" 
    	});
    	
    	$('.J_tip_arrow').ui('lvtip', { 
	    	templete: 3, 
	    	place: 'bottom-left', 
	    	offsetX: 0, 
	    	offsetY: 10, 
	    	events: "live" 
    	});
    }

    // 展开某产品全部房型 ajax
    function roomAllInfo($room, id) {
        var d1 = $(".J_calendar").eq(0).val(),
            d2 = $(".J_calendar").eq(1).val();
        
        $.ajax({
      	  type: 'POST',
      	  url: "http://hotels.lvmama.com/prod/hotel/getProductBranchList.do",
      	  data: {
	      		startDateStr:d1,
				endDateStr:d2,
				productIds:id
			  },
      	  success: function (data) {
			 if (data) {
	                var hotelProductBranchList = data[0].hotelProductBranchList,
	                    html = "",
	                    tr = "",
	                    len = hotelProductBranchList.length,
	                    l = 0,
	                    breakfast = ["单早", "双早", "三早", "四早"],
	                    hotelSuppGoodsList = null;
	                   

	                for (var i = 0; i < len; i++) {
	                	hotelSuppGoodsList = hotelProductBranchList[i].hotelSuppGoodsList;
	                	
	                    href = "http://hotels.lvmama.com/hotel/" + id + "?currentBrandId="+ hotelProductBranchList[i].productBranchId + "&startDate=" + d1 + "&endDate=" + d2 + "";
	                    l = hotelSuppGoodsList.length;
	                    
	                    tr += '<tr>' +
	                                 '<td class="room-td-1" colspan="7">' +
	                                       '<span class="room-info"><a href="javascript:;" rel="nofollow" class="J_room_info" data-branch="' + hotelProductBranchList[i].productBranchId + '" >' + hotelProductBranchList[i].branchName + '</a></span>' +
	                                       //'<i class="icon gift"></i>' +
	                                       //'<span class="limited-time"><i class="icon l-time"></i>限时抢购</span>' +
	                                       //'<span class="special">今日特价</span>' +
	                                 '</td>' +
	                                 '<td class="room-td-2 cc1" style="display: none;">' + (hotelProductBranchList[i].propValue ? (hotelProductBranchList[i].propValue.bed_type ? (hotelProductBranchList[i].propValue.bed_type[0] ? (hotelProductBranchList[i].propValue.bed_type[0].name ? hotelProductBranchList[i].propValue.bed_type[0].name : "") : ""): "") : "") + '</td>' +
	                                 //取第一个商品
	                                 '<td class="room-td-3 cc1" style="display: none;">' + (hotelSuppGoodsList[0].goodsTimePriceList ? (hotelSuppGoodsList[0].goodsTimePriceList.length > 0 ? (hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast ? (hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast > 0 ? breakfast[parseInt(hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast, 10) - 1] : "无早") : "无早") : "无早") : "无早") + '</td>' +
	                                 '<td class="room-td-2 cc1" style="display: none;">' + (hotelProductBranchList[i].propValue ? (hotelProductBranchList[i].propValue.internet ? ( hotelProductBranchList[i].propValue.internet[0] ? (hotelProductBranchList[i].propValue.internet[0].name ? "宽带"+hotelProductBranchList[i].propValue.internet[0].name : "") : "") : "") : "") + '</td>' +
	                                 '<td class="room-td-6" style="display: none;">' +
	                                 	   (hotelSuppGoodsList[0].dailyAverage > 0 ? '<dfn class="J_room_rate ' + (data[0].hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[0].suppGoodsId + ' hotelDay = ' + data[0].hotelDay + '>¥<i class="f18">' + hotelSuppGoodsList[0].intDailyAverageYuan + '</i></dfn>  ' : 
	                                 		   '<dfn class="J_room_rate ' + (data[0].hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[0].suppGoodsId + ' hotelDay = ' + data[0].hotelDay + '><i class="f18">' + '----'+ '</i></dfn>  ') +
	                                       //'<span class="tagsback"><em>返</em><i>¥20</i></span>' +
	                                 	  ((hotelSuppGoodsList[0].stockStatusOfTimeRange == 2 && hotelSuppGoodsList[0].dailyAverage > 0 && hotelSuppGoodsList[0].isOrNotSelled && hotelSuppGoodsList[0].minStock !== '' && hotelSuppGoodsList[0].minStock < 4) ? '<span class="last-room">剩余' + hotelSuppGoodsList[0].minStock + '间</span>': "") +
	                                 '</td>' +
	                                 '<td class="room-td-7" style="display: none;">' + (hotelSuppGoodsList[0].dailyAverage >= 0 && hotelSuppGoodsList[0].isOrNotSelled ?  '<a hidefocus="false" target="_blank" rel="nofollow" class="icon room-view" href="' + href + '"></a>' : '<span class="be-finished">订完</span>') +
	                                       (hotelSuppGoodsList[0].payTarget == 'PREPAID' ? '<a class="prepaid" href="javascript:;">预付</a>' : "") +
	                                 '</td>'+
	                    '</tr>';

	                    for (var j = 0; j < l; j++) {
	                    	
	                    	var tip = "",
		                    promotionList = [],
		                    giftList = [],
		                	giftTip = "";

	                        if (hotelSuppGoodsList[j].promotionList) {
	                            promotionList = hotelSuppGoodsList[j].promotionList;

	                            for (var z = 0, pl = promotionList.length; z < pl; z++) {

	                                if (z === pl - 1) {
	                                    tip += promotionList[z];
	                                    break;
	                                }

	                                tip += promotionList[z] + "</br>";
	                            }

	                        }
	                        
	                        if (hotelSuppGoodsList[j].giftList) {
	                        	giftList = hotelSuppGoodsList[j].giftList;

	                            for (var z = 0, pl = giftList.length; z < pl; z++) {

	                                if (z === pl - 1) {
	                                	giftTip += giftList[z];
	                                    break;
	                                }

	                                giftTip += giftList[z] + "</br>";
	                            }

	                        }
	                        var bor='';
	                        if(j==l-1){
	                        	bor = 'border_s';
	                        }
	                        tr += '<tr class="J_tr'+i+' '+bor+'">' +
	                                 '<td class="room-td-1">' +
	                                       '<span class="rn">' + hotelSuppGoodsList[j].goodsName + '</span>' +
	                                       //'<i class="icon gift"></i>' +
	                                       //'<span class="limited-time"><i class="icon l-time"></i>限时抢购</span>' +
	                                       //'<span class="special">今日特价</span>' +
	                                       (hotelSuppGoodsList[j].promotionList ? hotelSuppGoodsList[j].promotionList.length > 0 ? '<span class="J_tip_arrow deductible" tip-content="' + tip + '">促销</span>' : "" : "") +
	                                       (hotelSuppGoodsList[j].giftList ? hotelSuppGoodsList[j].giftList.length > 0 ? '<i class="J_tip_arrow icon gift" tip-content="' + giftTip + '"></i>' : "" : "") +
	                                 '</td>' +
	                                 '<td class="room-td-2 cc1">' + (hotelProductBranchList[i].propValue ? (hotelProductBranchList[i].propValue.bed_type ? (hotelProductBranchList[i].propValue.bed_type[0] ? (hotelProductBranchList[i].propValue.bed_type[0].name ? hotelProductBranchList[i].propValue.bed_type[0].name : "") : "")  : "") : "") + '</td>' +
	                                 '<td class="room-td-3 cc1">' + (hotelSuppGoodsList[j].goodsTimePriceList ? (hotelSuppGoodsList[j].goodsTimePriceList.length > 0 ? (hotelSuppGoodsList[j].goodsTimePriceList[0].breakfast ? (hotelSuppGoodsList[j].goodsTimePriceList[0].breakfast > 0 ? breakfast[parseInt(hotelSuppGoodsList[j].goodsTimePriceList[0].breakfast, 10) - 1] : "无早") : "无早") : "无早") : "无早") + '</td>' +
	                                 '<td class="room-td-2 cc1">' + (hotelProductBranchList[i].propValue ? (hotelProductBranchList[i].propValue.internet ? (hotelProductBranchList[i].propValue.internet[0] ? (hotelProductBranchList[i].propValue.internet[0].name ? "宽带"+hotelProductBranchList[i].propValue.internet[0].name : "") : "") : "") : "") + '</td>' +
	                                 '<td class="room-td-6 cc1">' +
	                                       (hotelSuppGoodsList[j].dailyAverage >= 0 ? '<dfn class="J_room_rate ' + (data[0].hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[j].suppGoodsId + ' hotelDay = ' + data[0].hotelDay + '>¥<i class="f18">' + hotelSuppGoodsList[j].intDailyAverageYuan + '</i></dfn>  ' : 
	                                    	   '<dfn class="J_room_rate ' + (data[0].hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[j].suppGoodsId + ' hotelDay = ' + data[0].hotelDay + '><i class="f18">' + '----'+ '</i></dfn>  ') +
	                                       //'<span class="tagsback"><em>返</em><i>¥20</i></span>' +
	                                       ((hotelSuppGoodsList[j].stockStatusOfTimeRange == 2 && hotelSuppGoodsList[j].dailyAverage > 0 && hotelSuppGoodsList[j].isOrNotSelled && hotelSuppGoodsList[j].minStock !== '' && hotelSuppGoodsList[j].minStock < 4) ? '<span class="last-room">剩余' + hotelSuppGoodsList[j].minStock + '间</span>': "") +
	                                 '</td>' +
	                                 '<td class="room-td-7">' + (hotelSuppGoodsList[j].dailyAverage >= 0 && hotelSuppGoodsList[j].isOrNotSelled ?  '<a hidefocus="false" target="_blank" rel="nofollow" class="icon room-view" href="' + href + '"></a>' : '<span class="be-finished">订完</span>') +
	                                 (hotelSuppGoodsList[j].payTarget == 'PREPAID' ? '<a class="J_tip prepaid" href="javascript:;" tip-content="为保障您的预订，需要您预先在网上支付房费。">预付</a>' : "") +
	                                 '</td>'+
	                        '</tr>';
	                    }
	                }

	                $room.find("table").hide();
	                $room.append('<table><tbody>' + tr + '</tbody></table>');
	            }
	          	tipFn();
	            LODING.close();
      	  	 },
      	  error: function(){
      		  LODING.close();
      	  }
      	});
    }
    
    //选择天数
    function getHotelDay(d1,d2){
    	d2 = d2.replace(/-/g,"/");
    	d1 = d1.replace(/-/g,"/");
    	var beginBookDay = (new Date(d1)).getTime();
    	var endBookDay = (new Date(d2)).getTime();
    	return (endBookDay - beginBookDay)/(24*60*60*1000);
    }

    // 执行加载某房型商品
    function roomInfo(that, id, productId, index, roomurl) {
    	
        var d1 = $(".J_calendar").eq(0).val(),
            d2 = $(".J_calendar").eq(1).val(),
            href = "http://hotels.lvmama.com/hotel/" + productId + "?currentBrandId="+ id + "&startDate=" + d1 + "&endDate=" + d2 + "";
        	
        var  hotelDay = getHotelDay(d1,d2);
    	if(roomurl == "1"){
    		var tempwindow =window.open('blank');
        	tempwindow.location = href;
        	LODING.close();
        	return ;
    	}
    	
        $.ajax({
      	  type: 'POST',
      	  url: "http://hotels.lvmama.com/prod/hotel/getProdBranch.do",
      	  data: {
	      		prodBranchId:id,
    			startDateStr:d1,
    			endDateStr:d2
      	  },
      	  success: function (data) {
      		if (data) {
                var hotelSuppGoodsList = data.hotelSuppGoodsList,
                    len = hotelSuppGoodsList.length,
                    breakfast = ["单早", "双早", "三早", "四早"],
                    html = "",
                    $room = $(that).parents("div.J_room"),
                    $parentsTR = $(that).parents("tr"),
                    $parentsTB = $parentsTR.parents("tbody"),
                    $parentTD = $(that).parents("td");

                	$room.css("height", "auto");
                    $parentsTR.find("td").hide();
                    $parentTD.attr("colspan", "7").show();
                    
                    for (var i = 0 ; i < len; i++) {
                    	var tip = "",
	                        promotionList = [],
	                        giftList = [],
	                    	giftTip = "";

                        if (hotelSuppGoodsList[i].promotionList) {
                            promotionList = hotelSuppGoodsList[i].promotionList;

                            for (var z = 0, pl = promotionList.length; z < pl; z++) {

                                if (z === pl - 1) {
                                    tip += promotionList[z];
                                    break;
                                }

                                tip += promotionList[z] + "</br>";
                            }

                        }
                        
                        if (hotelSuppGoodsList[i].giftList) {
                        	giftList = hotelSuppGoodsList[i].giftList;

                            for (var z = 0, pl = giftList.length; z < pl; z++) {

                                if (z === pl - 1) {
                                	giftTip += giftList[z];
                                    break;
                                }

                                giftTip += giftList[z] + "</br>";
                            }

                        }

                        html += '<tr class="J_tr' + index + '">' +
                                 '<td class="room-td-1">' +
                                       '<span class="rn">' + hotelSuppGoodsList[i].goodsName + '</span>' +
                                       //'<i class="icon gift"></i>' +
                                       //'<span class="limited-time"><i class="icon l-time"></i>限时抢购</span>' +
                                       //'<span class="special">今日特价</span>' +
                                       (hotelSuppGoodsList[i].promotionList ? hotelSuppGoodsList[i].promotionList.length > 0 ? '<span class="J_tip_arrow deductible" tip-content="' + tip + '">促销</span>' : "" : "") +
                                       (hotelSuppGoodsList[i].giftList ? hotelSuppGoodsList[i].giftList.length > 0 ? '<i class="J_tip_arrow icon gift" tip-content="' + giftTip + '"></i>' : "" : "") +
                                 '</td>' +
                                 '<td class="room-td-2 cc1">' + (data.propValue ? (data.propValue.bed_type ? (data.propValue.bed_type[0] ? (data.propValue.bed_type[0].name ? data.propValue.bed_type[0].name : "") : "") : ""): "") + '</td>' +
                                 '<td class="room-td-3 cc1">' + (hotelSuppGoodsList[i].goodsTimePriceList ? (hotelSuppGoodsList[i].goodsTimePriceList.length > 0 ? (hotelSuppGoodsList[i].goodsTimePriceList[0].breakfast ? (hotelSuppGoodsList[i].goodsTimePriceList[0].breakfast > 0 ? breakfast[parseInt(hotelSuppGoodsList[i].goodsTimePriceList[0].breakfast, 10) - 1] : "无早") : "无早") : "无早") : "无早") + '</td>' +
                                 '<td class="room-td-2 cc1">' + (data.propValue ? (data.propValue.internet ? (data.propValue.internet[0] ? (data.propValue.internet[0].name ? "宽带"+data.propValue.internet[0].name : "") : "") : "") : "") + '</td>' +
                                 '<td class="room-td-6">' +
                                 	   (hotelSuppGoodsList[i].dailyAverage >= 0 ? '<dfn class="J_room_rate ' + (hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[i].suppGoodsId + ' hotelDay = ' + hotelDay + '>¥<i class="f18">' + hotelSuppGoodsList[i].intDailyAverageYuan + '</i></dfn>  ' : 
                                 		   '<dfn class="J_room_rate ' + (hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + hotelSuppGoodsList[i].suppGoodsId + ' hotelDay = ' + hotelDay + '><i class="f18">' + '----'+ '</i></dfn>  ') +
                                       //'<span class="tagsback"><em>返</em><i>¥20</i></span>' +
                                 	  ((hotelSuppGoodsList[i].stockStatusOfTimeRange == 2 && hotelSuppGoodsList[i].dailyAverage > 0 && hotelSuppGoodsList[i].isOrNotSelled && hotelSuppGoodsList[i].minStock !== '' && hotelSuppGoodsList[i].minStock < 4) ? '<span class="last-room">剩余' + hotelSuppGoodsList[i].minStock + '间</span>': "") +
                                 '</td>' +
                                 '<td class="room-td-7">' + (hotelSuppGoodsList[i].dailyAverage >= 0 && hotelSuppGoodsList[i].isOrNotSelled  ?  '<a hidefocus="false" target="_blank" rel="nofollow" class="icon room-view" href="' + href + '"></a>' : '<span class="be-finished">订完</span>') +
                                       (hotelSuppGoodsList[i].payTarget == 'PREPAID' ? '<a class="J_tip prepaid" href="javascript:;" tip-content="为保障您的预订，需要您预先在网上支付房费。">预付</a>' : "") +
                                 '</td>'+
                        '</tr>';
                    }
                }
                $(that).parents("tr").after(html);
//	            }
            tipFn();
            LODING.close();
          },
      	  error: function(){
      		  LODING.close();
      	  }
      	});
    }
    
    // 执行加载房型数据
    $(".J_room_info").live("click", function () {
        var prodBranchId = $(this).attr("data-branch"),
        	roomurl = $(this).attr("roomurl"),
            $room = $(this).parents("div.J_room"),
            $parentsTR = $(this).parents("tr"),
            $parentsTB = $parentsTR.parents("tbody"),
            $parentTD = $(this).parents("td"),
            productId = $parentsTR.parents("div.J_l").attr("data-sources"),
            index = $parentsTR.index(),
            className = "";
        
        if ($parentTD.attr("colspan") == undefined || $parentTD.attr("colspan") == "" || $parentTD.attr("colspan") == 1) {
            loding();
            roomInfo(this, prodBranchId, productId, index, roomurl);
        } else {
        	
        	if($parentsTB.find("tr").eq(index+1).length > 0){
        		className = $parentsTB.find("tr").eq(index+1).attr("class");
            }
            //$room.css("height", "81px");
            $parentsTR.find("td").show();
            $parentTD.attr("colspan", "").show();
            $parentsTR.parent("tbody").find("tr." + className + "").remove();
        }

    });

    // 所有产品房型列表
    function roomList(ids_ing) {
        var d1 = $(".J_calendar").eq(0).val(),
            d2 = $(".J_calendar").eq(1).val();
        
        	$.ajax({
        	  type: 'POST',
        	  dataType: 'json',
        	  url: "http://hotels.lvmama.com/prod/hotel/getProductBranchList.do",
        	  data: {
	  				startDateStr:d1,
					endDateStr:d2,
					productIds:ids_ing,
					getTopTwo:'true'
			  },
        	  success: function (data) {
                  if (data) {
                      var l = data.length,
                          breakfast = ["单早", "双早", "三早", "四早"];

                      function htmlStr(data) {
                          var josn = data.hotelProductBranchList,
                              len = josn.length,
                              $table = $('<table data-sources="product"><tbody></tbody></table>'),
                              tr = "",
                              price = "",
                          	  producturl = "http://hotels.lvmama.com/hotel/" + data.productId + "?startDate=" + d1 + "&endDate=" + d2 + "";

                          len = len > 2 ? 2 : len;
                          
                          price = data.minPriceOfGoodsList >= 0 ? '<dfn class="f32"><span class="f22">¥</span><i data-sources="price">' + data.integerMinPriceYuan + '</i></dfn><span class="cc1">起</span>' : 
                    		  '<p class="cc1">您选择的日期无法预订</p><a target="_blank" href="'+ producturl +'">更换日期查询</a>';
                    		  
                          $(".J_productId_" + data.productId).find("div.J_s_price").html(price);

                          for (var i = 0; i < len; i++) {
                          	href = "http://hotels.lvmama.com/hotel/" + data.productId + "?currentBrandId="+ josn[i].productBranchId + "&startDate=" + d1 + "&endDate=" + d2 + "";
                          	var tip = "",
                              	promotionList = [],
                              	giftList = [],
                              	giftTip = "",
                              	roomurl = ""; 
                          	if (josn[i].hotelSuppGoodsList[0].promotionList) {
                                  promotionList = josn[i].hotelSuppGoodsList[0].promotionList;

                                  for (var z = 0, pl = promotionList.length; z < pl; z++) {

                                      if (z === pl - 1) {
                                          tip += promotionList[z];
                                          break;
                                      }

                                      tip += promotionList[z] + "</br>";
                                  }

                              }
                              
                              if (josn[i].hotelSuppGoodsList[0].giftList) {
                              	giftList = josn[i].hotelSuppGoodsList[0].giftList;

                                  for (var z = 0, pl = giftList.length; z < pl; z++) {

                                      if (z === pl - 1) {
                                      	giftTip += giftList[z];
                                          break;
                                      }

                                      giftTip += giftList[z] + "</br>";
                                  }

                              }
                              
                              if(josn[i].hotelSuppGoodsList.length == 1){
                            	  roomurl = "1"; 
                              }
                              
                              tr += '<tr>' +
                                       '<td class="room-td-1">' +
                                             '<span class="room-info"><a href="javascript:;" rel="nofollow" class="J_room_info" roomurl="' + roomurl + '" data-branch="' + josn[i].hotelSuppGoodsList[0].productBranchId + '" >' + josn[i].branchName + '</a></span>' +
                                             //'<i class="icon gift"></i>' +
                                             //'<span class="limited-time"><i class="icon l-time"></i>限时抢购</span>' +
                                             //'<span class="special">促销</span>' +
                                             (josn[i].hotelSuppGoodsList[0].promotionList ? josn[i].hotelSuppGoodsList[0].promotionList.length > 0 ? '<span class="J_tip_arrow deductible" tip-content="' + tip + '">促销</span>' : "" : "") +
                                             (josn[i].hotelSuppGoodsList[0].giftList ? josn[i].hotelSuppGoodsList[0].giftList.length > 0 ? '<i class="J_tip_arrow icon gift" tip-content="' + giftTip + '"></i>' : "" : "") +
                                       '</td>' +
                                       '<td class="room-td-2 cc1">' + (josn[i].propValue ? (josn[i].propValue.bed_type ? (josn[i].propValue.bed_type[0]? (josn[i].propValue.bed_type[0].name ? josn[i].propValue.bed_type[0].name : "") : "") : "") : "") + '</td>' +
                                       '<td class="room-td-3 cc1">' + (josn[i].hotelSuppGoodsList[0].goodsTimePriceList ? (josn[i].hotelSuppGoodsList[0].goodsTimePriceList.length > 0 ? ( josn[i].hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast ? (josn[i].hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast > 0 ? breakfast[parseInt(josn[i].hotelSuppGoodsList[0].goodsTimePriceList[0].breakfast, 10) - 1] : "无早") : "无早") : "无早") : "无早") + '</td>' +
                                       '<td class="room-td-2 cc1">' + (josn[i].propValue ? (josn[i].propValue.internet ? (josn[i].propValue.internet[0] ? (josn[i].propValue.internet[0].name ? "宽带"+josn[i].propValue.internet[0].name : "") : "") : "") : "") + '</td>' +
                                       '<td class="room-td-6">' +
                                       	   (josn[i].minPriceOfGoodsList >= 0 ? '<dfn class="J_room_rate ' + (data.hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + josn[i].hotelSuppGoodsList[0].suppGoodsId + ' hotelDay = ' + data.hotelDay + '>¥<i class="f18">' + josn[i].integerMinPriceYuan + '</i></dfn>' : 
                                       		   '<dfn class="J_room_rate ' + (data.hotelDay <2 ? "noline" : "") + '" data-goodsid = ' + josn[i].hotelSuppGoodsList[0].suppGoodsId + ' hotelDay = ' + data.hotelDay + '><i class="f18">' + '----'+ '</i></dfn>') +
                                             //'<span class="tagsback"><em>返</em><i>¥20</i></span>' +
                                       '</td>' +
                                       '<td class="room-td-7">' + (josn[i].hotelSuppGoodsList[0].dailyAverage >= 0 && josn[i].hotelSuppGoodsList[0].isOrNotSelled ? '<a hidefocus="false" target="_blank" rel="nofollow" class="icon room-view" href="' + href + '"></a>' : '<span class="be-finished">订完</span>') +
                                       (josn[i].hotelSuppGoodsList[0].payTarget == 'PREPAID' ? '<a class="J_tip prepaid" href="javascript:;" tip-content="为保障您的预订，需要您预先在网上支付房费。">预付</a>' : "") +
                                       '</td>'+
                              '</tr>';
                          }

                          $table.find("tbody").html(tr);
                          $(".J_productId_" + data.productId).find("div.J_room").removeClass('room_loading').html($table);
                      }
                      
                      for (var j = 0 ; j < l; j++) {
                      	htmlStr(data[j]);
                      }
                  }
                  tipFn();
//                  LODING.close();
              },
        	  error: function(){
//        		  LODING.close();
        	  }
        	});
       
    }
    
    //价格鼠标滑过效果
    var timer ;
	$(".J_room_rate").live("mouseenter",function () { 
		var offset = $(this).offset(),
	    	height = $(this).height(), 
	    	suppGoodsId = $(this).attr("data-goodsid"),
			visitTime = $(".J_calendar").eq(0).val(),
			leaveTime = $(".J_calendar").eq(1).val(),
			time = $(this).attr("hotelDay");
		
		if(time > 20){
			alert("离开时间在入住20天内");
			return false;
		}
		
		if(time > 1){
			$.post("http://hotels.lvmama.com/hotel/queryRate.do",
				{
					visitTime:visitTime,
					leaveTime:leaveTime,
					suppGoods:suppGoodsId,
					quantity:1
				},
				function(data){
					console.debug($(".J_room_day").size());
					$(".J_room_day").html(data);
					
					$(".J_room_day").css({ 
						left: offset.left, 
						top: offset.top + height
					}).show(); 
				}
			);
		}
	}).mouseleave(function () { 
		timer = setTimeout(function () { 
			$(".J_room_day").hide(); 
		}, 200); 
	}); 
	
	$(".J_room_day").mouseenter(function () { 
		clearTimeout(timer); 
	}).mouseleave(function () { 
		$(this).hide(); 
	});


	   // 执行塞选 ajax 请求
    function execAjaxFn() {
		var NumTop = [],
			NumNow = [],
			hotelLength = $('.hotel-list .hotel-object').length,
			loadNum= 5,
			scrollT = $(window).scrollTop()+ $(window).height()+200;
		for(var i=0;i<hotelLength/loadNum;i++){
			NumTop.push($('div.hotel-object').eq(loadNum*i).offset().top);
		};
		for(var i=0;i<NumTop.length;i++){
			if(scrollT>=NumTop[i]){
				for(var j=loadNum*i;j<loadNum*i+5;j++){
					if($('div.hotel-object').eq(loadNum*i).attr('Loaded')!='true'){
						var jiequ = $('div.hotel-object').eq(j).attr("data-sources");
						
						NumNow.push(jiequ);
					}
				};
				$('div.hotel-object').eq(loadNum*i).attr('Loaded','true');
			};
		};
		
		var ids_ing = NumNow.join(',');
		
		//price(); // ä»·æ ¼
		if(ids_ing!=''){
//			loding();
			roomList(ids_ing);
			//alert(ids_ing)
		}if($('div.hotel-object').eq(0).attr('Loaded')!='true'){
//			loding();
		}
		start = true;
    };
    
    execAjaxFn();
	
	var tiMer = null;
	$(window).scroll(function(){ 
		clearTimeout(tiMer);
		tiMer = setTimeout(execAjaxFn,100);
	});
	
	

    // 地图 待优化
    $(window).load(function () {
    	var $MAP = $(".J_l");
        
        if($MAP.length > 0){
        	arrMap = $MAP.eq(0).attr("data-coordinate").split(",");
        	
        	// 地图初始化
            mapMark.init({
                container: "container",
                longitude: arrMap[0],
                latitude: arrMap[1],
                num: 1,
                text: $MAP.eq(0).find("div.hotel-name a").html()
            });
        }

        function refresh() {
            arrMap = [];
            $MAP.each(function (i) {
                arrMap.push([parseInt($(this).offset().top, 10) + 40, i]);
            });
        }

        refresh();

        function activate(arr) {
            var coordinate = [],
                len = $("#container").find("div.map-mark").length,
                l = arr.length,
                bool = true;

            for (var i = 0; i < l; i++) {

                if (arr[i] + 1 <= len) {
                    //map.panTo(option.longitude, option.latitude, option.num);
                    continue;
                } 

                coordinate = $MAP.eq(arr[i]).attr("data-coordinate").split(",");

                mapMark.createCover({
                    longitude: coordinate[0],
                    latitude: coordinate[1],
                    num: arr[i] + 1,
                    text: $MAP.eq(arr[i]).find("div.hotel-name a").html()
                });

                bool = false;
            }

            if (bool && l > 0) {
                coordinate = $MAP.eq(arr[l - 1]).attr("data-coordinate").split(",");
                mapMark.panTo(coordinate[0], coordinate[1], arr[l - 1] + 1);
            }

        }

        function process() {
            var doc = document,
                docHeight = doc.documentElement.clientHeight || doc.body.clientHeight,
                scrollTop = $(window).scrollTop(),
                len = arrMap.length,
                arr = [];

            for (var i = 0; i < len; i++) {
                //j = i + 1 === len ? i : i + 1;
              
                if (arrMap[i][0] < docHeight + scrollTop && arrMap[i][0] > scrollTop) {
                    arr.push(arrMap[i][1]);
                }

            }

            activate(arr);

        }

        var timed = null;

        function throttle() {
            clearTimeout(timed);

            timed = setTimeout(function () {
                $MAP.mouseenter(function () {
                    var arrMap = $(this).attr("data-coordinate").split(","),
                           text = $(this).find("div.hotel-name").find("a").html(),
                           index = $(this).index() + 1;

                    mapMark.panTo(arrMap[0], arrMap[1], index);
                });

                refresh();
                process();
            }, 300);

        }

        process();

        var $SCROLLMAP = $(".J_map"),
	        $MAIN = $(".J_main"),
	        mapTop = $SCROLLMAP.offset().top,
	        mainTop = $MAIN.offset().top;

	    function scrollMap() {
	        var scroll = $(window).scrollTop(),
	            height = $SCROLLMAP.outerHeight(true),
	            mainHeight = $MAIN.outerHeight(true),
	            mainSH = mainHeight + mainTop,
	            listH = $MAIN.find("div.hotel-wrap").outerHeight(true);
	
	        if ($MAP.length < 0 || listH < height) {
	            return;
	        }
	
	        if (mapTop <= scroll) {
	
	            if (mainSH < scroll + height) {
	                $SCROLLMAP.css({
	                    position: "absolute",
	                    top: mainHeight - height
	                });
	            } else {
	                $SCROLLMAP.addClass('fixed');
	                $SCROLLMAP.attr("style", "");
	            }
	
	        } else {
	            $SCROLLMAP.removeClass('fixed');
	        }
	    }

        scrollMap();

        $(window).scroll(function () {
            scrollMap();
            throttle();
            $MAP.unbind("mouseenter");
        });

        // 鼠标滑过效果
        $MAP.mouseenter(function () {
            var arrMap = $(this).attr("data-coordinate").split(","),
                text = $(this).find("div.hotel-name").find("a").html(),
                index = $(this).index() + 1;

            mapMark.panTo(arrMap[0], arrMap[1], index);
        });
    });
    
    var jiluLi = $('#jilu li');
    jiluLi.hover(function(){
    	$(this).find('.close').show();
    },function(){
    	$(this).find('.close').hide();
    })
    jiluLi.find('.close').click(function(){
    	$(this).parent('li').remove();	
    })
});