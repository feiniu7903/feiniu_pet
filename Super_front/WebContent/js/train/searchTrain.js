$(function () {
	if($("#js_error_show").val()=='0'){
		$("#js-error").show();
	}
	
	var liLen = $(".tabs-nav").find("li").length;
	if(liLen==1){
		$(".tabs-nav").append('<li></li>');
		$(".tabs-nav").find("li").eq(0).addClass("selected");
		$('.tabcon').addClass('selected');
	}
    //右侧tab切换
	$(".tabs-nav").find("li").click(function () { 
		var _changeli = true; 
		$(".tabs-nav").find("li").each(function(i, ele){ 
			if($.trim($(ele).html()) == ''){ 
				_changeli = false; 
			} 
		}) ;
		if(_changeli){ 
			var index = $(this).index(); 
			$(".tabs-nav").find("li").removeClass("selected"); 
			$(this).addClass("selected"); 
			$(".tabcon").removeClass("selected"); 
			$(".tabcon").eq(index).addClass("selected"); 
		} 
		});


    //自动补全,及热门城市的显示
    var autocompleteId = "";
    function autocomplete(id) {
        var jqObj = $(id),
            offset = jqObj.offset(),
            left = offset.left,
            top = offset.top + 25,
            idVal = $(id).attr("id");
        autocompleteId = idVal;
        $("#js-hot-city").hide();
        $(".iflt_autoCListBox").hide();
        setTimeout(function () {
            $("#js-hot-city").css({ left: left, top: top, display: "block" });
        }, 150);
    }

    $("#start-address, #purpose-address").click(function () {
        autocomplete(this);
    });

    $("#js-city-list").find("a").click(function (e) {
        $("#" + autocompleteId)[0].value = $(this).text();
        $($("#" + autocompleteId).attr('cityID')).val($(this).attr('key'));
        $("#js-hot-city").hide();
        if(autocompleteId == 'start-address'){
            $('#purpose-address').click();
        }else if(autocompleteId == 'purpose-address'){
            $('.calendar').click();
            e.stopPropagation();
        }
    });

    $(document).click(function (event) {
        var o = event.srcElement || event.target;
        if (o["id"] !== "start-address" && o["id"] !== "purpose-address") {
            $("#js-hot-city").css({ left: 0, top: 0, display: "none" });
        }
    });

    var auto = new autoComp({
        $out: $("body"),
        fixed: true,
        top: 24
    });
    auto.start();

    
    $("#js-change-city").click(function () {
        var jqStart = $("#start-address"),
            jqPurpose = $("#purpose-address"),
            
            startVal = $.trim(jqStart.val()),
            purposeVal = $.trim(jqPurpose.val());
        
        	jqStart.val(purposeVal);
        	jqPurpose.val(startVal);
        	
        	jqdepartureCity = $(jqStart.attr('cityid')),
            jqarrivalCity = $(jqPurpose.attr('cityid')),
            
            departureCityVal = jqdepartureCity.val(),
            arrivalCityVal = jqarrivalCity.val();
        	
        	jqdepartureCity.val(arrivalCityVal);
        	jqarrivalCity.val(departureCityVal);
        return false;
    });

    
    
    //calendar 
    $(".calendar").calendar({ fatalism: 20 });

    //from submit
    $("#searchButton").click(function () {
       /* var form = $("#searchForm")[0],
            fromCity = $.trim(form.fromCity.value),
            toCity = $.trim(form.toCity.value),
            fromDate = $.trim(form.fromDate.value),
            lineName = $.trim(form.lineName.value);
        if (fromCity === '' || fromCity === '中文/拼音') {
            form.fromCity.click();
            return;
        }
        if (toCity === '' || toCity === '中文/拼音') {
            form.toCity.click();
            return;
        }
        form.submit();*/
        
        var $form=$("#searchForm");
   	 var param="";
   	 var departureCity =$form.find("input[name='departureCity']").val();
   	 var arrivalCity =$form.find("input[name='arrivalCity']").val();
   	 var date = $form.find("input[name='fromDate']").val();
   	 var fromCity = $.trim($form[0].fromCity.value);
   	 var toCity = $.trim($form[0].toCity.value);
   	 if (fromCity === '' || fromCity === '中文/拼音') {
   		 $form[0].fromCity.click();
   	     return;
   	 }
   	 if (toCity === '' || toCity === '中文/拼音') {
   		 $form[0].toCity.click();
   	     return;
   	 }
   	 var lineName = $form.find("input[name='lineName']").val();
   	 var url="http://www.lvmama.com/search/train/"+departureCity+"-"+arrivalCity+".html";
   	 param="?date="+date;
   	 if($.trim(lineName)!=''){
   		 param=param+"&line="+lineName;
   	 }
   	 url+=param;
   	 window.location.href=url;
    });

    //展开车次详情
    function trainInfo(that) {
        $('.showBd').remove();
        if ($(that).parents('tbody').hasClass('tbodyShow')) {
            $('.table_result').find('tbody').removeClass('tbodyShow');
            if ($(that).hasClass('railway_station')) {
                $(that).find('span').attr('class', 'icon_down');
            } else {
                $(that).siblings('a[class=railway_station]').find('span').attr('class', 'icon_down');
            }
            return;
        }

        $('.table_result').find('tbody').removeClass('tbodyShow');
        $('.railway_station').find('span').attr('class', 'icon_down');
        if ($(that).hasClass('railway_station')) {
            $(that).parents('tbody').addClass('tbodyShow');
            $(that).find('span').attr('class', 'icon_up');
        } else {
            $(that).parents('tbody').addClass('tbodyShow');
            $(that).siblings('a[class=railway_station]').find('span').attr('class', 'icon_up');
        }

        var template ='<div class="showBd">' +
                      '   <i class="showArrow"></i>' +
            '            <ul class="numList">' +
            '				<li class="numListTH">' +
            '				    <span class="num_1">站次</span>' +
            '				    <span class="num_2">站名</span>' +
            '				    <span class="num_3">到时</span>' +
            '				    <span class="num_4">开时</span>' +
            '				    <span class="num_5">停留</span>' +
            '			    </li>' +
            '		    </ul>' +
            '            <ul class="js-numList numList">' +
            '			    {list}' +
            '		    </ul>' +
            '            <div class="page_mini"></div>' +
            '        </div>';
        var lineInfoId = $(that).parents('tbody').attr('line_info');
        var visitTime = $(that).parents('tbody').attr('visitTime');
        $.post("http://www.lvmama.com/product/train/lineStop.do",
        		{"lineInfoId":lineInfoId,"visitTime":visitTime},function(data){
        			var trainInfo = eval("("+data+")");
                	var time = trainInfo.takeTime,
                    arr = trainInfo.stopList,
                    len = arr.length,
                    startingStation = $(that).parents('tr').find('span.departureStation').html(), 
                    terminus = $(that).parents('tr').find('span.arrivalStation').html(), 
                    html = '',
                    css = 'class=railway_gray';
    	            for (var i = 0; i < len; i++) {
    	                if (startingStation === arr[i].stopName) {
    	                    css = '';
    	                }
    	                html += '<li  ' + css + '>' +
    	                        '<span class="num_1">'+ (i + 1) + '</span>' +
    	                        '<span class="num_2">' + arr[i].stopName + '</span>' +
    	                        '<span class="num_3">' + arr[i].arrivalTime + '</span>' +
    	                        '<span class="num_4">' + arr[i].departureTime + '</span>';
    	                if(arr[i].takeTime != '' && arr[i].takeTime != '-'){
    	                	html += '<span class="num_5">' + arr[i].takeTime + '分钟</span></li>';
    	                }else{
    	                	html += '<span class="num_5">' + arr[i].takeTime + '</span></li>';
    	                }
                        if (terminus === arr[i].stopName) {
                            css = 'class=railway_gray';
                        }
                    }
        $('body').append(template.replace('{time}', time).replace('{list}', html));
        var position = $(that).parent('td').position();
        $('.showBd').css({'left':position.left, 'top':position.top+70});
        var $pageList = $('.js-numList').find('li'),
            lv_page1 = new lv_page({
                pSize: 10,
                $list: $pageList,
                $pageWrap: $(".page_mini"),
                pageCrt: "page_mini_current",
                dayBox: "lv_page_dayBox",
                pagePrev: "lv_pagePrev",
                pageNext: "lv_pageNext",
                pageInput: "lv_pageInput",
                pageSure: "lv_pageSure"
            });
        lv_page1.start();
   });
    }
    //execute 展开车次详情
    $('.railway_station, .railway_num').live('click', function () {
        trainInfo(this);
    });

    //隐藏弹层
    $(document).bind('click', function(e){
        if(!$(e.target).hasClass('railway_num') && !$(e.target).hasClass('railway_station') && !$(e.target).hasClass('showBd')
            &&!$(e.target).parents().hasClass('showBd')){
            $('.showBd').remove();
            $('.table_result').find('tbody').removeClass('tbodyShow');
            $('.railway_station').find('span').attr('class', 'icon_down');
        }
    });


    //$('.js-par').live('click', function () {
    //    var $li = $(this).parents('tr').find('ul[class=railwayBtn]').find('li'),
    //        $span = $(this).find('span');
    //    if ($span.hasClass('icon_up')) {
    //        $li.hide();
    //        $li.eq(0).show();
    //        $li.eq(1).show();
    //        $span.attr('class', 'icon_down');
    //    } else {
    //        $li.show();
    //        $span.attr('class', 'icon_up');
    //    }
    //});

    //关闭提示信息
    $('#js-close').click(function () {
        $(this).parent().hide();
    });

    //删选部分内容的展开，收起
    $(".popu-open").click(function () { 
    	if($('.railway_search_tips em').html() == 0){ 
    	if ($(this).find("span").attr("class") === "icon_up") { 
    	$(this).siblings("dl").find("dt").eq(3).hide(); 
    	$(this).siblings("dl").find("dd").eq(3).hide(); 
    	$(this).html('更多<span class="icon_down"></span>'); 
    	} else { 
    	$(this).siblings("dl").find("dt").eq(3).show(); 
    	$(this).siblings("dl").find("dd").eq(3).show(); 
    	$(this).html('收起<span class="icon_up"></span>'); 
    	} 
    	}else{ 
    	if ($(this).find("span").attr("class") === "icon_up") { 
    	$(this).siblings("dl").find("dt").eq(4).hide(); 
    	$(this).siblings("dl").find("dt").eq(5).hide(); 
    	$(this).siblings("dl").find("dd").eq(4).hide(); 
    	$(this).siblings("dl").find("dd").eq(5).hide(); 
    	$(this).html('更多<span class="icon_down"></span>'); 
    	} else { 
    	$(this).siblings("dl").find("dt").eq(4).show(); 
    	$(this).siblings("dl").find("dt").eq(5).show(); 
    	$(this).siblings("dl").find("dd").eq(4).show(); 
    	$(this).siblings("dl").find("dd").eq(5).show(); 
    	$(this).html('收起<span class="icon_up"></span>'); 
    	} 
    	} 
    });


    //删选
    var SEARCH_CATE_LIST = '#js-result-filters',
        TRAINS_TYPE = ["G", "D", "T", "K", "X"],
        TIME_RANGES = [[0, 600],[600, 1200],[1200, 1300],[1300, 1800],[1800, 2400]],
        IS_OVER = ["true", "false"];

    var FILTER_TYPE = 0,  //高铁，动车过滤
        FILTER_DEPART = 1, //出发时间段过滤
        FILTER_ARRIVAL = 2, //到达时间段过滤
        FILTER_DEPARTURE_ST = 3, //出发站名
        FILTER_ARRIVAL_ST = 4, //到达站名
        FILTER_IS_START = 5; //是否始发站

    var filterBinary = {};

    function filterResult() {
        var old = document.getElementById('js-result'),
            clone = old.cloneNode(true),
            tbody = $(clone).find('tbody'),
            resultNumber = 0;

        tbody.each(function (i) {
            var filter = tbody.eq(i).attr('filter');
            if (filter && filter.length > 0) {
                filter = filter.split(' ');
                if (
                    compareBinary(filter[FILTER_TYPE], FILTER_TYPE) &&
                        compareBinary(filterToInt(filter[FILTER_DEPART]), FILTER_DEPART) &&
                        compareBinary(filterToInt(filter[FILTER_ARRIVAL]), FILTER_ARRIVAL) &&
                        compareBinary(filter[FILTER_DEPARTURE_ST], FILTER_DEPARTURE_ST) &&
                        compareBinary(filter[FILTER_ARRIVAL_ST], FILTER_ARRIVAL_ST) &&
                        compareBinary(filter[FILTER_IS_START], FILTER_IS_START)
                    ) {
                    tbody.eq(i).show();
                    resultNumber++;
                } else {
                    tbody.eq(i).hide();
                }
            }
        });
        old.parentNode.replaceChild(clone, old);
        if (resultNumber === 0) {
            $('#js-error').show();
        } else {
            $('#js-error').hide();
        }
    }

    function isIndex(arr, index) {
        for (var i = 0, len = arr.length; i < len; i++) {
            if (arr[i] === index) {
                return i;
            }
        }
        return -1;
    }

    function getFilters(that) {
        var type = $(that).parents('dd').attr('type'),
            val = $(that).parents('li').index(),
            index = 0;
        if (filterBinary[type]) {
            index = isIndex(filterBinary[type], val);
            if (index !== -1) {
                filterBinary[type].splice(index, 1);
            } else {
                filterBinary[type].push(val);
            }
        } else {
            filterBinary[type] = [val];
        }
    }

    function map(func, arr) {
        for (var i = 0, len = arr.length; i < len; i++) {
            func(arr[i]);
        }
    }

    function filterToInt(timeString) {
        return parseInt(timeString.replace(/^0/, '').replace(/(^0|\:)/, ''));
    }

    function compareBinary(filter, index) {
        switch (index) {
            case FILTER_TYPE:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_TYPE + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter.toUpperCase() == TRAINS_TYPE[i]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            case FILTER_DEPART:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_DEPART + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter >= TIME_RANGES[i][0] && filter <= TIME_RANGES[i][1]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            case FILTER_ARRIVAL:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_ARRIVAL + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter >= TIME_RANGES[i][0] && filter <= TIME_RANGES[i][1]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            case FILTER_DEPARTURE_ST:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_DEPARTURE_ST + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter === DEPART_STATION_NAME01[i]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            case FILTER_ARRIVAL_ST:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_ARRIVAL_ST + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter === ARRIVAL_STATION_NAME01[i]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            case FILTER_IS_START:
                var _hasChecked = false, _hasMatch = false, _filterBinary = filterBinary["" + FILTER_IS_START + ""];
                if (_filterBinary !== undefined && _filterBinary.length !== 0) {
                    map(function (i) {
                        _hasChecked = true;
                        if (filter === IS_OVER[i]) {
                            _hasMatch = true;
                        }
                    }, _filterBinary);
                }
                if (!_hasChecked) {
                    return true;
                }
                return _hasMatch;
            default:
                return false;
        }
    }

    function checkAll(that) {
        var $checkall = $(that).parents('dd'),
            type = $checkall.attr('type');
        if (filterBinary[type].length === 0) {
            $checkall.find('a.show_all').addClass('active');
            $checkall.find('a.show_all').unbind('click');
        } else {
            $checkall.find('a.show_all').removeClass('active');
            $checkall.find('a.show_all').click(function () {
                var type = $(this).parents('dd').attr('type'),
                    $ul = $(this).parents('dd').find('ul');
                $(this).addClass('active');
                $ul.find('a').removeClass('inputSelected');
                filterBinary[type] = [];
                filterResult();
            });
        }
    }

    //排序
    function sortTrain(){
        $('.table_result').sortModel();
        $('.sort-tag').live('click', function(){
            if($('.sort-ele:visible').length < 2){
                return false;
            }
        });
    }
    sortTrain();

    //点击各删选的情况，进行车次的删选
    $(SEARCH_CATE_LIST).find('ul').find('a').click(function (e) {
        if($(this).hasClass('inputSelected')){
            $(this).removeClass('inputSelected');
        }else{
            $(this).addClass('inputSelected');
        }
        getFilters(this);
        checkAll(this);
        filterResult();
        //排序

        $('.sort-tag').off('click');
        $('.table_result').sortModel();
        return false;
    });

    //整个页面的分页
//    function page() {
//        var $pageList = $('#js-result').find('tbody:visible'),
//            lv_page1 = new lv_page({
//                pSize: 5,
//                $lists: $('#js-result').find('tbody'),
//                $list: $pageList,
//                $pageWrap: $(".pages"),
//                pageCrt: "PageSel",
//                dayBox: "PageLink",
//                pagePrev: "PrevPage",
//                pageNext: "NextPage",
//                pageInput: "lv_pageInput",
//                pageSure: "lv_pageSure"
//            });
//        lv_page1.start();
//    }
//    page();

    //常见问题，文字的收起展开效果
    function onlyone_select(_box, hover_item, select_class, event_type) {
        $(_box).delegate(hover_item, event_type, function () {
            $(this).addClass(select_class).siblings(hover_item).removeClass(select_class);
        });
    };
    onlyone_select("ul.JS_click_select", "li", "selected", 'click');

    

    //点击预订按钮
    $('.railwayBtn').find('a[class!=nopar]').live('click',function(){
        var that = $(this);
        //var txt = $(this).parent('li').find('span').text();
        var arr = []; 
        var ll = $(this).parent('li').find('span').length;
        for(var i =0; i<ll; i++){ 
            arr.push($(this).parent('li').find('span').eq(i).text()); 
        } 
        var $form = $("#orderForm");
        var pbid = $(this).attr("data-pbid");
        $form.find("input[name='buyInfo.prodBranchId']").val(pbid);
    	$form.find("input[name='buyInfo.visitTime']").val($(this).attr("data-tsvo"));
    	$form.find("input[id='buyInfo_content']").attr("name","buyInfo.buyNum.product_"+pbid).val(1);
    	$form.find("input[name='buyInfo.localCheck']").val("false");
    	var trainNum = that.parents('tbody').find('.railway_num').text(); 
    	var trainLevel = that.siblings('p').text(); 
    	trainLevel = trainLevel.substring(0,trainLevel.length-1);
    	$(this).addClass('nopar').parent('li').find('span').eq(0).addClass('price-load').text('');
    	$.post("http://www.lvmama.com/buy/ajaxCheckSock.do",$("#orderForm").serialize(),function(dt){
    		var data=eval("("+dt+")");
    		if(!data.success){	
    			pandora.dialog({
                    title: "温馨提示",
                    content: "非常抱歉，<span class='pop-hot'>"+trainNum+ trainLevel+"</span>当前已无票，请选择其他座次和坐席。",
                    ok: true,
                    okClassName:"pbtn-orange"
                });
                    		that.parent('li').find('span').removeClass('price-load').addClass('unable');
                    		for(var i =0; i<ll; i++){
                    			that.parent('li').find('span').eq(i).text(arr[i]); 
                    		}
    		}else{
    			$("#orderForm").find("input[name='buyInfo.localCheck']").val("");
    			if( $("#login_hidden").length > 0 && $("#login_hidden").val() == "true" ){
    				document.getElementById("orderForm").submit();
    			}else{
    				showLogin(function(){document.getElementById("orderForm").submit();});
    			}
    		}
    	});
        return false;
    });

});