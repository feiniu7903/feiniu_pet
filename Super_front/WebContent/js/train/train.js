$(function(){
    var autocompleteId = "";
    function autocomplete(id) {
        var jqObj = $(id),
            offset = jqObj.offset(),
            left = offset.left,
            top = offset.top + 30,
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
        	autocomplete($('#purpose-address')); 
        }else if(autocompleteId == 'purpose-address'){ 
        	$('.input-date').click(); 
        }
        return false;
    });

    $(document).click(function (event) {
        var o = event.srcElement || event.target;
        if (o["id"] !== "start-address" && o["id"] !== "purpose-address") {
            $("#js-hot-city").css({ left: 0, top: 0, display: "none" });
        }
    });

    var auto = new autoComp({
        $out: $("body")
    });
    auto.start();

    //change city
    $("#js-change-city").click(function () {
        var jqStart = $("#start-address"),
            jqPurpose = $("#purpose-address"),
            startVal = $.trim(jqStart.val()),
            purposeVal = $.trim(jqPurpose.val());
            jqdepartureCity = $(jqStart.attr('cityid')),
            jqarrivalCity = $(jqPurpose.attr('cityid')),
            departureCityVal = jqdepartureCity.val(),
            arrivalCityVal = jqarrivalCity.val();
        jqStart.val(purposeVal);
        jqPurpose.val(startVal);
        jqdepartureCity.val(arrivalCityVal);
        jqarrivalCity.val(departureCityVal);
        
        return false;
    });

    //calendar
    $(".date-info").click(function () {
        $(".input-date").focus();
    });
    $("#js-dinput-date, .date-info").calendar({ inputWrap: "#js-dinput-date", input: ".input-date", inputWrapClass: 'dinput-date', fatalism: 20, showWeek: true });

   
   /* $("#searchButton").click(function () {
        var form = $("#searchForm")[0],
            fromCity = $.trim(form.fromCity.value),
            toCity = $.trim(form.toCity.value),
            fromDate = $.trim(form.fromDate.value),
            trainNum = $.trim(form.trainNum.value);
        if (fromCity === '' || fromCity === '中文/拼音') {
            form.fromCity.click();
            return;
        }
        if (toCity === '' || toCity === '中文/拼音') {
            form.toCity.click();
            return;
        }
        form.submit();
    });*/

    // tabs
	function JS_tab_nav(tab_nav,tab_con,selected,tri_type,tab_class){
		$tab_obj=$(tab_nav);
		$tab_obj.bind(tri_type,function(){
			var tab_li_index = $(tab_nav).index(this);
			$(this).addClass(selected).siblings().removeClass(selected);
			$(tab_con).eq(tab_li_index).addClass(tab_class).show().siblings().removeClass(tab_class).hide();
			// return false;
		});
	};
	
	function JS_tab_nav(tab_nav,tab_con,selected,tri_type,tab_class,siblingClass){ 
		$tab_obj=$(tab_nav); 
		$tab_obj.bind(tri_type,function(){ 
		var tab_li_index = $(tab_nav).index(this); 
		$(this).addClass(selected).siblings().removeClass(selected); 
		$(tab_con).eq(tab_li_index).addClass(tab_class).show().siblings().removeClass(tab_class).hide(); 
			if(siblingClass !== ''){ 
				$(siblingClass).find('.'+selected).trigger('click'); 
			} 
		}); 
	}; 
	JS_tab_nav(".JS_tabnav>a", ".tabsBoxSelected>.tabcon", "selected", "click", "", ""); 
	JS_tab_nav(".tabnav>li", ".JS_tabsbox", "selected", "click", "tabsBoxSelected", ".JS_tabnav");


	function onlyone_select(_box, hover_item, select_class, event_type) {
	    $(_box).delegate(hover_item, event_type, function () {
	        $(this).addClass(select_class).siblings(hover_item).removeClass(select_class);
	    });
	};
	onlyone_select("ul.JS_click_select", "li", "selected", 'click');
});	

function goTrainSearch(){
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
}


$('.train-list').find('li').click(function(){
	 var param = $(this).find('a').attr('key');	
	 window.location.href="http://www.lvmama.com/search/train/"+param+".html";
});

//20131029
//单slider播放
var lv_slider=function(opt){
    var init={
        sliderTxtList:"",
        sliderBarList:"",
        autoPlay:true,
        i:0,
        aniTime:2000
    };
    this.init=$.extend(init,opt);
};
lv_slider.prototype={
    start:function(){
        var _this=this,_init=_this.init;
        _init.$sliderList.eq(0).css("z-index",3);
        if(_init.sliderBarList!=""){
            var Html_c="";
            for(var i=1;i<=_init.$sliderList.length; i++){
                Html_c+="<li>"+i+"</li>";
            }
            var Html="<ul class='"+_init.sliderBarList+"'>"+Html_c+"</ul>";
            _init.$sliderBox.append(Html);
            $("."+_init.sliderBarList+">li:first").addClass(_init.sliderBarCrt);
            _this.barEvt();
        }
        if(_init.$sliderTxtList!=null){
            _init.$sliderTxtList.eq(0).show();
        }
        if(_init.autoPlay){
            _this.autoPlay();
        }
    },
    slider:function(){
        var _this=this,_init=_this.init;
        _init.i++;
        _init.i>=_init.$sliderList.length?_init.i=0:1==1;
        _init.$sliderList.eq(_init.i).css("z-index",3).fadeIn(800).siblings().css("z-index",1).fadeOut(800);
        $("."+_init.sliderBarList+">li").eq(_init.i).addClass(_init.sliderBarCrt).siblings().removeClass(_init.sliderBarCrt);
        $(_init.sliderTxtList).eq(_init.i).show().siblings().hide();
    },
    autoPlay:function(){
        var _this=this,_init=_this.init;
        var ani_slider=setInterval(function(){
            _this.slider();
        }, _init.aniTime);
        $("."+_init.sliderBarList+">li").hover(function(){
            clearInterval(ani_slider);
        },function(){
            ani_slider=setInterval(function(){
                _this.slider();
            }, _init.aniTime);
        });
    },
    barEvt:function(){
        var _this=this,_init=_this.init;
        $("."+_init.sliderBarList+">li").click(function(){
            _init.i=$(this).index()-1;
            _this.slider();
        });
    }
};
//单slider播放
var slider1 = new lv_slider({
    $sliderBox: $(".iflt_slider"),
    $sliderList: $(".iflt_slider_list li"),
    sliderBarList: "iflt_slider_btn",
    sliderBarCrt: "iflt_slider_btnCrt"
});
slider1.start();//轮播

