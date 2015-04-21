// JavaScript Document
var SysSecond;
var InterValObj;
var t = n =0, count;// 轮播参数
var LT_LOADING_MSG = "请稍等..."; // 提示信息
var CONTENT_LOGDING = "loading"; // 提示信息
var LT_LOADING_TIME = 10000; // 加载等待时间 30秒
var LT_LOADING_CLOSE = 3000; // 关闭时间3秒

window.onload = function(){
	setTimeout(scrollTo,0,0,0);	
}

window.touchmove = function(){
	return false;
};

// 关闭整个层 
function hide_search_tab(obj,t_index){
	$(".lv-transparent-div").hide();
	$(".lv-cascade-search li a div").hide();
	$(".lv-cascade-search li a .lv-narrow-icon").eq(t_index).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
	$(".lv-cascade-div div.lv-tab").hide();
}
// 点击按钮  
function search_trigger(obj,index) {
	try {
		var display = $(".lv-transparent-div").css("display");
		if('block' == display) {
			var lv_index = $(".lv-transparent-div").attr('data-value');
			if(lv_index != index) {
				$(".lv-transparent-div").attr('data-value',index);
			} 
			hide_search_tab(obj,index);
			$(".lv-transparent-div").attr('data-value',index);
			return lv_index;
		}
		$(".lv-transparent-div").attr('data-value',index);
		return "10000";
	}catch(e){
		
	}
}

// 隐藏地址栏  & 处理事件的时候 ，防止滚动条出现
/*addEventListener('load', function(){ 
        setTimeout(function(){ window.scrollTo(0, 1); }, 100); 
});
*/
String.prototype.startWith=function(str){
if(str==null||str==""||this.length==0||str.length>this.length)
  return false;
if(this.substr(0,str.length)==str)
  return true;
else
  return false;
return true;
}
$(function(){
	/*点击关闭提示下载*/
	$(".ic_del").click(function(){
		$(".down_push").hide();
		try{
			setcookie('isShowDownload', false, 72000, '/', ".lvmama.com",false); //设置cookie 秒
		}catch(e){
			
		}
	});
	/*返回頂部*/
	$(".lv-back-top").click(function(){
		$("html, body").animate({scrollTop:0}, 500);
		return false;
	});	
	
	/*图片轮播*/
	count=$(".lv-banner-list a").length;
	$(".lv-banner-list a:not(:first-child)").hide();
	$(".lv-banner-info").html($(".lv-banner-list a:first-child").find("img").attr('alt'));
	//$(".lv-banner-info").click(function(){window.open($(".lv-banner-list a:first-child").attr('href'), "_blank")});
	$(".lv-banner li").click(function() {
		var i = $(this).text() -1;//获取Li元素内的值，即1，2，3，4
		n = i;
		if (i >= count) return;
		$(".lv-banner-info").html($(".lv-banner-list a").eq(i).find("img").attr('alt'));
		//$(".lv-banner-info").unbind().click(function(){window.open($(".lv-banner-list a").eq(i).attr('href'), "_blank")})
		$(".lv-banner-list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
		$("banner").css("background","");
		$(this).toggleClass("on");
		$(this).siblings().removeAttr("class");
	});
	t = setInterval("showAuto()", 4000);
	$(".lv-banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 4000);});
	
	/* 级联筛选 */
	var _tabIndex;
	$(".lv-cascade-search li").click(function(e) {
			_tabIndex = $(this).index();
			var t_index = search_trigger(this,_tabIndex); // 折叠隐藏 
			if(t_index == _tabIndex ) {
				return;
			}
			if (null != $(this).attr('data-type')) { // 此判断针对自由行
				return;
			}
			$(this).children("a").children("div").show();
			$(this).siblings().children("a").children("div").hide();
			$(this).children("a").children("span.lv-change-color")
					.addClass("lv-color-01");
			$(this).siblings().children("a").children(
					"span.lv-change-color").removeClass("lv-color-01");
			$(this).siblings().find(".lv-narrow-icon").attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
			var _upimg = $(this).children("a").find(".lv-narrow-icon").attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow_up.png');
			$('.lv-tab').hide();
			$(".lv-tab").eq($(this).index()).show();
			var _stanHeight = $(document).height();
			var _obj_top = $(this).offset().top;
			var _objTop = _obj_top + 45;
			$(".lv-transparent-div").show().css({
					height : _stanHeight,
					top : _objTop + 'px'
			});
			// 控制侧边锚点颜色
			$(".lv-junior-narrow ul.lv-narrow-list li.lv-narrow-li:eq(0)")
					.addClass("lv-narrow-current").siblings().removeClass(
							"lv-narrow-current");
			$(".lv-senior-narrow ul.lv-narrow-list li.lv-narrow-li:eq(0)")
					.addClass("lv-narrow-current").siblings().removeClass(
							"lv-narrow-current");

		});

	$(".lv-transparent-div").click(function(){
			$(this).hide();
			$(".lv-cascade-search li a div").hide();
			$(".lv-cascade-search li a .lv-narrow-icon").eq(_tabIndex).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
			$(".lv-cascade-div div.lv-tab").hide();
	});
	
	/*选值，赋值*/
	/*$(".lv-cascade-div div.lv-tab ul.bindclick li.lv-cascade-li").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+index+") span").html($(this).html());
		$(".lv-cascade-search li a .lv-narrow-icon").eq(_tabIndex).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
	});*/
	
	$(".lv-cascade-div div.lv-tab ul.bindclick li.lv-cascade-lifirst").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+index+") span").html($(this).html());
	});
	
	/*$("div.lv-cascade-childrendiv ul.lv-cascade-childrenlist li").click(function(){
	    $(".lv-cascade-search li a .lv-narrow-icon").eq(_tabIndex).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul.lv-cascade-childrenlist").parent("div.lv-cascade-childrendiv").parent("li").parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+index+") span").html($(this).find('span').html());
	});
	$(".lv-cascade-div div.lv-tab ul.bindclick li").mouseover(function(){
		$(this).children("div.lv-cascade-childrendiv").show();
		$(this).siblings("li").children("div.lv-cascade-childrendiv").hide();
	});
	$(".lv-cascade-div div.lv-tab ul.bindclick li").mouseout(function(){
		$(this).children("div.lv-cascade-childrendiv").hide();
	});*/
	
	/*plus*/
	/*$(".lv-plus").click(function(){
		$(this).siblings(".lv-ticket-num").html(parseInt($(this).siblings(".lv-ticket-num").html())+1);
	});*/
	/*plus*/
	/*$(".lv-reduce").click(function(){
		var ticketNum = parseInt($(this).siblings(".lv-ticket-num").html());
		if(ticketNum>0){
			$(this).siblings(".lv-ticket-num").html(ticketNum-1);
		}else{
			$(this).siblings(".lv-ticket-num").html(0);
		}
	});*/
	 
  
	/*倒计时*/
	SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
	InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
	
	/*注册tab切换
	$(".lv-progress-next").click(function(e) {
		$(".lv-progress-list li").eq($(this).parent().parent(".lv-tab").index()).children().children(".lv-progress").removeClass("lv-progress-active").addClass("lv-progress-node");
		$(".lv-progress-list li").eq($(this).parent().parent(".lv-tab").index()).next("li").children().children(".lv-progress").removeClass("lv-progress-node").addClass("lv-progress-active");
		$(this).parent().parent(".lv-tab").next(".lv-tab").show().siblings(".lv-tab").hide();

	});
	*/
	
	/*toast*/
	/*$(".lv-btn-booking").click(function(){
		lvToast(true,"提交成功！",1000);
		//lvToast(false,"您刚刚已经提交过订单请稍后再提交！");
	});*/
	
	$(".lv-toast-success").click(function(){$(this).hide();});
	$(".lv-toast-fail").click(function(){$(this).hide();});
	
	/*城市选择narrow锚点*/
	$(".lv-primary-narrow ul.lv-narrow-list li.lv-narrow-li").click(function(e){
		$(this).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
		var top = $(".lv-primary .lv-wrapper-container").eq($(this).index()).offset().top;
		$("html, body").animate({scrollTop:parseInt(top)}, 500);
	});
	
	/*初级玩法narrow锚点*/
	$(".lv-junior-narrow ul.lv-narrow-list li.lv-narrow-li").click(function(e){
		$(this).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
		var top = $(".lv-junior .lv-wrapper-container").eq($(this).index()).offset().top;
		$("html, body").animate({scrollTop:parseInt(top)}, 500);
	});
	
	/*资深玩法narrow锚点*/
	$(".lv-senior-narrow ul.lv-narrow-list li.lv-narrow-li").click(function(e){
		$(this).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
		var top = $(".lv-senior .lv-wrapper-container").eq($(this).index()).offset().top;
		$("html, body").animate({scrollTop:parseInt(top)}, 500);

	});
	
	/*锚点随页面滚动*/
	$(document).scroll(function(e) {
		/*初级玩法*/
		$(".lv-junior .lv-wrapper-container").each(function(index,element){
			if($(document).scrollTop()>=$(element).offset().top && $(document).scrollTop() !=1 && $(document).scrollTop() !=0){//初始化scrollTop都等于1
				$(".lv-junior-narrow ul.lv-narrow-list li.lv-narrow-li").eq(index).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
			}
		});
		
		/*资深玩法*/
		$(".lv-senior .lv-wrapper-container").each(function(index,element){
			if($(document).scrollTop()>=$(element).offset().top && $(document).scrollTop() !=1 && $(document).scrollTop() !=0){//初始化scrollTop都等于1
				$(".lv-senior-narrow ul.lv-narrow-list li.lv-narrow-li").eq(index).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
			}
		});
    });
	// telephone
	var _height = $(document).height();
	var _width = $(document).width();
	$("a[_tel]").bind("click", function() {
		$(".telBox .p span").html($(this).attr("_tel"));
		$(".telBox").fadeIn();
		$(".model").css({
			width : _width,
			height : _height
		}).show();
	});
	$("a[_dele],.ic_tel").bind("click", function() {
		$(".telBox").fadeOut();
		$(".model").hide();
	});
	
	/*点击展示更多初始化截取字*/
	var slideObj = $(".tab_box");
	var limit_text_obj = slideObj.find('.lv-pad-content');
	var limit_text;
	limit_text_obj.each(function() {
		var _textthis = $(this);
		limit_text = $.trim(_textthis.text());
		var limit_len = limit_text.length;
		if(limit_len>68){
			_textthis.limit('.lv-pad-content',68);
			_textthis.siblings('.spot_note_mor').show();
		}else{
			_textthis.siblings('.spot_note_mor').hide();
		}
	});
	slideObj.live('click',function(){
		var _more_dis = $(this).find('.spot_note_mor').css('display');
		if(_more_dis == 'block'){
			var slideClass = $(this).find('.lv-pad-content');
			var slideMore = slideClass.hasClass('spot_note');
			var _this = $(this).find('.lv-pad-content');
			if(slideMore){
				_this.removeClass('spot_note').addClass('spot_hei');
				_this.siblings('.spot_note_mor').find('a').addClass('ic_up').removeClass('ic_down');
				_this.text(_this.attr('title'));
			}else {
				_this.addClass('spot_note').removeClass('spot_hei');
				_this.siblings('.spot_note_mor').find('a').addClass('ic_down').removeClass('ic_up');
				_this.limit('.lv-pad-content',68);
			}
		}
	})


	/*景点详情伸缩*/ 
	$(".tap_click").click(function(){ 
		$(this).parent().next("li.lv_jdmp_add").slideToggle('fast'); 
	}); 


	/*头部底部判断*/
	var _webFlagUrl = getUrlParam('channel');
	if(_webFlagUrl =='wap'){
		localStorage.setItem("channel",_webFlagUrl);
	}
	try {
		_webFlagUrl = localStorage.channel;
	}catch(err){
		_webFlagUrl = '';
	}
	if(_webFlagUrl == 'wap'){
		$('.lv-header').show();
		$('.lv-footer').show();
	}


});
function lvToast(isSuccess, toastText) {
	lvToast(isSuccess, toastText, 5000)
}
function lvToast(isSuccess, toastText, time) {
	var toastLeft = ($(document).width() - $(".lv-toast").width()) / 2;
	var toastTop = $(document).scrollTop() + 200;
	$(".lv-toast").css({
		"left" : toastLeft,
		"top" : toastTop,
		"opacity" : .7
	});
	$(".lv-toast").children("p").html(toastText);
	if (isSuccess == CONTENT_LOGDING) {
		$(".lv-toast-loading").css({top: toastTop}).show();
		setTimeout(function() {
			$(".lv-toast-loading").css({top: toastTop}).fadeOut(1000);
		}, time);
	} else if (isSuccess) {
		$(".lv-toast-success").show();
		setTimeout(function() {
			$(".lv-toast-success").fadeOut(1000);
		}, time);
	} else {
		$(".lv-toast-fail").show();
		setTimeout(function() {
			$(".lv-toast-fail").fadeOut(1000);
		}, time);
	}
}
function showAuto() {
	n = n >= (count - 1) ? 0 : ++n;
	$(".lv-banner li").eq(n).trigger('click');
}
function dateFormatter(v) {

	if (v instanceof Date) {

		var y = v.getFullYear();
		var m = v.getMonth() + 1;
		if (m < 10) {
			m = "0" + m;
		}
		var d = v.getDate();
		if (d < 10) {
			d = "0" + d;
		}
		var h = v.getHours();
		if (h < 10) {
			h = "0" + h;
		}
		var i = v.getMinutes();
		if (i < 10) {
			i = "0" + i;
		}
		var s = v.getSeconds();
		var ms = v.getMilliseconds();
		if (ms > 0)
			return y + '-' + m + '-' + d;
		if (h > 0 || i > 0 || s > 0)
			return y + '-' + m + '-' + d;
		return y + '-' + m + '-' + d;

	}
	return '';
}
// 将时间减去1秒，计算天、时、分、秒
function SetRemainTime() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒
		var minite = Math.floor((SysSecond / 60) % 60); // 计算分
		var hour = Math.floor((SysSecond / 3600) % 24); // 计算小时
		var day = Math.floor((SysSecond / 3600) / 24); // 计算天
        if(second < 10) {
        	second = "0" + second;
        }
        if(minite < 10) {
        	minite = "0" + minite;
        }
		$("#remainTime").html(minite + ":" + second);
	} else {// 剩余时间小于或等于0的时候，就停止间隔函数
		window.clearInterval(InterValObj);
		// 这里可以添加倒计时时间为0后需要执行的事件
	}
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

/* 专题跳转，app拦截url设置 */
function redirectSpecial(type, id) {
	window.location.href = "/clutter/"+type + "/" + id;
}

//超出的文字自动+省略号
jQuery.fn.limit=function(elem,_num){
	var self = $(elem);
	  var objString = $.trim($(this).text());
	  var objLength = $.trim($(this).text()).length;
	  if(objLength > _num){
		$(this).attr("title",objString);
	   objString = $(this).text(objString.substring(0,_num) + "...");
	  }
}

// 页面跳转.
function union_skip(url) {
	//lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.location.href = url;
}
//团购页面跳转.
function union_skip_group(url,orderCount,remainTime) {
	//lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.location.href = url+"?orderCount="+orderCount+"&remainTime="+escape(remainTime);
}
// 后退
function union_back() {
	window.history.go(-1);
}

// 定位
function lt_postion(lat, lon) {
	lvToast(true, LT_LOADING_MSG, LT_LOADING_TIME);
	window.location.href = contextPath + "/position.htm?lat=" + lat + "&lon="
			+ lon;
}

// 清空输入框内容 id 输入框id
function union_clear_context(id) {
	$("#search_autocomplete").hide();
	if ($("#" + id).length > 0) {
		$("#" + id).val("");		
	}
}


/**
 * 判断是否登录 .
 * 
 * @param objectId
 * @param objectType
 * @param objectImageUrl
 * @param objectName
 */
function addFavoritor(objectId, objectType, objectImageUrl, objectName) {
	$.ajax({
		type : "POST",
		url : contextPath + "/islogin.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : {},
		dataType : "json",
		success : function(data) {
			// 已经登录
			if (data.code == '1') {
				f_add(objectId, objectType, objectImageUrl, objectName);
			} else {
				window.location.href = contextPath
						+ "/user/user_favoritor.htm?objectType=" + objectType
						+ "&objectId=" + objectId;
			}
		},
		error : function(e, statusText, error) {

		}
	});
}

/**
 * 取消
 */
function cancelFavoritor(objectId, objectType) {
	$.ajax({
		type : "POST",
		url : contextPath + "/islogin.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : {},
		dataType : "json",
		success : function(data) {
			// 已经登录
			if (data.code == '1') {
				f_cancel(objectId);
			} else {
				window.location.href = contextPath
						+ "/user/user_favoritor.htm?objectType=" + objectType
						+ "&objectId=" + objectId;
			}
		},
		error : function(e, statusText, error) {

		}
	});
}

// 添加收藏
function f_add(objectId, objectType, objectImageUrl, objectName) {
	var param = {
		"objectId" : objectId,
		"objectImageUrl" : objectImageUrl,
		"objectName" : encodeURIComponent(objectName),
		"objectType" : objectType
	};
	$.ajax({
		type : "post",
		url : contextPath + "/user/submit_favoritor.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.code == '1') {
				lvToast(true, "已收藏成功!", LT_LOADING_CLOSE);
				$("#favoritor_in").hide();
				$("#favoritor_out").show();
			}else {
				lvToast(false, "收藏失败，请重试!", LT_LOADING_CLOSE);
			}
		},
		error : function(e, statusText, error) {
			lvToast(false, "收藏失败，请重试!", LT_LOADING_CLOSE);
		}
	});
}
// 取消收藏 .
function f_cancel(objectId) {
	var param = {
		"objectId" : objectId
	};
	$.ajax({
		type : "POST",
		url : contextPath + "/user/cancel_favoritor.htm",
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.code == '1') {
				lvToast(true, "已取消收藏!", LT_LOADING_CLOSE);
				$("#favoritor_in").show();
				$("#favoritor_out").hide();
			} else {
				lvToast(false, "取消收藏失败，请重试!", LT_LOADING_CLOSE);
			}
		},
		error : function(e, statusText, error) {
			lvToast(false, "取消收藏失败，请重试!", LT_LOADING_CLOSE);
		}
	});
}

/*搜索*/
function btnClick(){
	$("input[name='keyword']").val(encodeURI($("#hidden_keyword").val()));
	$("#key_search").submit();
	$("input[name='keyword']").val("");
}
document.onkeydown=function(event){
    var e = event || window.event;      
    if(e && e.keyCode==13){
    	btnClick();
    }
}

$.fn.filtrateTab = function (option, callback , crumbsCallback) {   //筛选切换
    var _this = {
        tabName: "tabChange",   //详细条目class
        tabSwitch: ".tab_select li",        //字段html标签
        maskName: ".lv-mask",   //遮罩默认名称
        maskTop:33,              //需要在头部空出的高度
        submit : undefined ,
        jump : undefined
    };
    $.extend(_this, option);
    var _bar = $(this),
        _obj = $(this).find(_this.tabSwitch),
        _item = $(this).find("." + _this.tabName),
        _mask = _bar.find(_this.maskName),
        maskHeight = $("body").height(),
        _btnState = 0;
    _mask.css({"top":_this.maskTop , "height":maskHeight});
    _obj.each(function (index) {
        var _btn = $(this),
        	_itemX = _item.eq(index);
        if(_itemX.find("li").length=="0"){
        	_btn.addClass("disable");
        }
        if(!_btn.hasClass("disable")){
        	if(_btn.length){
                var mbp = new MBP.fastButton(_btn.get(0), function () {
                    if(_btn.hasClass("active")){
                    	itemHide();
                    }else{                    	
                        _mask.css("display","block");
                        _obj.removeClass("active");
                        _btn.addClass("active");
                        _item.css("display","none");
                        _item.eq(index).css("display","block");
                        maskHeight = $("body").height();
                        _mask.css({"height":maskHeight});
                        if(_itemX.hasClass("filtrate-wrap")){
                			if(_btnState){
                				_btn.addClass("selected");
                			}else{
                				_btn.removeClass("selected");
                			}
                            _itemX.find(".filtrate-list-l1 li").removeClass("active");
                            _itemX.find(".filtrate-list-l1 li:eq(0)").addClass("active");
                            _itemX.find(".filtrate-list-l2 .tabChangeL2").css("display","none");
                            _itemX.find(".filtrate-list-l2 .tabChangeL2:eq(0)").css("display","block");
                            _itemX.find(".filtrate-list-l2 .tabChangeL2").find(".t_list:eq(0)").css("display","block");
                            _itemX.find(".filtrate-list-l3 .tabChangeL3").css("display","none");
                    		_itemX.find(".tabChangeL2").each(function(index){
                    			var nowIndex = $(this).attr("data-index");
                    			if(nowIndex!=-1){
                    				_itemX.find(".filtrate-list-l1 li").eq(index).addClass("selected");
                    				$(this).find(".radioOption .ic_radio_a").removeClass("selected");
                    				$(this).find(".radioOption:eq("+nowIndex+") .ic_radio_a").addClass("selected");
                    			}else{
                    				_itemX.find(".filtrate-list-l1 li").eq(index).removeClass("selected");
                    				$(this).find(".radioOption .ic_radio_a").removeClass("selected");
                    			}
                    		});
                        }
                    }
                });        		
        	}
        	if(_itemX.hasClass("filtrate-wrap")){
                btnTriggerHide(_itemX);
        		_itemX.tabChange({
                    tabName : "tabChangeL2"
                });
                _itemX.find(".tabChangeL2").each(function(index){
                    var filtrateWrapL2=$(this),
                        filtrateWrapL3 = filtrateWrapL2.find(".filtrate-list-l3 .tabChangeL3"),
                        selectedIndex = filtrateWrapL2.attr("data-index");
                    //console.info("fw3Child:"+filtrateWrapL3.length+"|| id:");
                    if(filtrateWrapL3.length){
                        if(filtrateWrapL3.find(".t_li").length){
                            //btnTriggerHide(filtrateWrapL3.find(".t_li"));
                            //console.info("Has L3:"+_itemX.find(".filtrateListL3 .t_li").length);
                        }
                        filtrateWrapL2.find(".t_list:eq(0) .t_li").each(function(childIndex){
                            var fw3Title = filtrateWrapL3.eq(childIndex).find(".t_title");
                            if(fw3Title.length);{
                                var fw3TitleBtn = new MBP.fastButton(fw3Title.get(0) , function(){
                                    filtrateWrapL2.find(".t_list:eq(0)").css("display","block");
                                    filtrateWrapL3.css("display","none");
                                });
                            }
                            var fw3DadBtn = new MBP.fastButton($(this).get(0) , function(){
                                filtrateWrapL2.find(".t_list:eq(0)").css("display","none");
                                filtrateWrapL3.eq(childIndex).css("display","block");
                            });
                        });
                    }
                    //console.info("selectedIndex="+selectedIndex);
                    if(selectedIndex!=-1){
                    	_btn.addClass("selected");
                    	_btnState = 1;
                    }
                	filtrateWrapL2.radioBox({
                		initNum : selectedIndex,
                		selectedVal : function(){
                			filtrateWrapL2.find(".radioOption").eq(this).find(".ic_radio_a").attr("item-index",this);
                			_itemX.find(".filtrate-list-l1 li").eq(index).addClass("selected");
                			//_btn.addClass("selected");
                		}
                	});
                });
            	_itemX.find("#filtrateSubmit").MBPBtn({},function(){
            		_btnState = 0;
        			_btn.removeClass("selected");
            		_itemX.find(".tabChangeL2").each(function(){
            			var nowIndex = $(this).find(".selected").attr("item-index"),
            				val = 0;
            			if(nowIndex){
                			$(this).attr("data-index",$(this).find(".selected").attr("item-index"));
                			_btnState = 1;
                			_btn.addClass("selected");
                			val = $(this).find(".selected").parent().attr("data-val")
                			//console.info(val);
            			}else{
            				$(this).attr("data-index","-1");   
            			}
            			itemHide();
                        if (typeof(_this.submit) != 'undefined') {
                        	_this.submit.apply(val, arguments);
                        }
            		});
            	});
            	_itemX.find("#filtrateReset").MBPBtn({},function(){
        			//_btn.removeClass("selected");
            		_itemX.find(".tabChangeL2").each(function(index){
            			var nowIndex = $(this).attr("data-index");
                        _itemX.find(".filtrate-list-l1 li").removeClass("active");
                        _itemX.find(".filtrate-list-l1 li:eq(0)").addClass("active");
                        _itemX.find(".filtrate-list-l2 .tabChangeL2").css("display","none");
                        _itemX.find(".filtrate-list-l2 .tabChangeL2:eq(0)").css("display","block");
                        _itemX.find(".filtrate-list-l2 .tabChangeL2").find(".t_list:eq(0)").css("display","block");
                        _itemX.find(".filtrate-list-l3 .tabChangeL3").css("display","none");                        
        				_itemX.find(".filtrate-list-l1 li").eq(index).removeClass("selected");
        				$(this).find(".radioOption .ic_radio_a").removeClass("selected");
            		});
            	});
        	}else{
        		_itemX.find("li").each(function(){
                    var _itemBtn = $(this);
                    if(_itemBtn.length){
                        var liMbp = new MBP.fastButton(_itemBtn.get(0) , function(){
                        	itemHide();
                            _btn.find("span:eq(0)").html(_itemBtn.html());
                            if (typeof(crumbsCallback) != 'undefined') {
                                crumbsCallback.apply(_itemBtn, arguments);
                            }
                        });                	
                    }
                });
        	}
        }
    });
    var closeObj1 =  _bar.find(_this.maskName);
    btnTriggerHide(closeObj1);
    if(_bar.find(_this.tabSwitch+".disable").length){
        var closeObj2 =  _bar.find(_this.tabSwitch+".disable");
        btnTriggerHide(closeObj2);
    }
    function itemHide(){
        _obj.removeClass("active");
        _item.css("display","none");
        _mask.css("display","none");    	
    }
    function btnTriggerHide(btn){
        var btnTrigger = new MBP.fastButton(btn.get(0), itemHide);
    }
    if (typeof(callback) != 'undefined') {
        callback.apply(this, arguments);
    }
    return this;
};

$.fn.tabChange = function (option, callback, crumbsCallback) {//tab切换
    var _this = {
        tabName: "tabChange",   //详细条目class
        tabSwitch: "li"         //字段html标签
    };
    $.extend(_this, option);
    var _obj = $(this).find(_this.tabSwitch);
    var _item = $("." + _this.tabName);
    _obj.each(function (index) {
        var _tabBtn = $(this);
        _item.eq(0).css("display", "block");
        _obj.eq(0).addClass("active");
        //console.log(_this.tabName+"||"+_item.attr("class"));
        if($(_tabBtn).length){
            var mbp = new MBP.fastButton($(_tabBtn).get(0), function () {
                _obj.removeClass("active").eq(index).addClass("active");
                _item.css("display", "none").eq(index).css("display", "block");
                if (typeof(crumbsCallback) != 'undefined') {
                    crumbsCallback.apply(_tabBtn, arguments);
                }
            });
        }
    });
    if (typeof(callback) != 'undefined') {
        callback.apply(this, arguments);
    }
    return this;
};

(function(document){//防止误点击
    window.MBP = window.MBP || {};
    MBP.fastButton = function (element, handler) {
        this.element = element;
        this.handler = handler;
        if (element.addEventListener) {
            element.addEventListener('touchstart', this, false);
            element.addEventListener('click', this, false);
        }
    };
    MBP.fastButton.prototype.handleEvent = function(event) {
        switch (event.type) {
            case 'touchstart': this.onTouchStart(event); break;
            case 'touchmove': this.onTouchMove(event); break;
            case 'touchend': this.onClick(event); break;
            case 'click': this.onClick(event); break;
        }
    };
    MBP.fastButton.prototype.onTouchStart = function(event) {
        event.stopPropagation();
        this.element.addEventListener('touchend', this, false);
        document.body.addEventListener('touchmove', this, false);
        this.startX = event.touches[0].clientX;
        this.startY = event.touches[0].clientY;
    };
    MBP.fastButton.prototype.onTouchMove = function(event) {
        if(Math.abs(event.touches[0].clientX - this.startX) > 10 ||
            Math.abs(event.touches[0].clientY - this.startY) > 10    ) {
            this.reset();
        }
    };
    MBP.fastButton.prototype.onClick = function(event) {
        event.stopPropagation();
        this.reset();
        this.handler(event);
        if(event.type == 'touchend') {
            MBP.preventGhostClick(this.startX, this.startY);
        }
    };
    MBP.fastButton.prototype.reset = function() {
        this.element.removeEventListener('touchend', this, false);
        document.body.removeEventListener('touchmove', this, false);
    };
    MBP.preventGhostClick = function (x, y) {
        MBP.coords.push(x, y);
        window.setTimeout(function (){
            MBP.coords.splice(0, 2);
        }, 2500);
    };
    MBP.ghostClickHandler = function (event) {
        for(var i = 0, len = MBP.coords.length; i < len; i += 2) {
            var x = MBP.coords[i];
            var y = MBP.coords[i + 1];
            if(Math.abs(event.clientX - x) < 25 && Math.abs(event.clientY - y) < 25) {
                event.stopPropagation();
                event.preventDefault();
            }
        }
    };
    if (document.addEventListener) {
        document.addEventListener('click', MBP.ghostClickHandler, true);
    }
    MBP.coords = [];
})(document);
$.fn.MBPBtn = function (option, handler) {
    var _this = {
    		
        };
    $.extend(_this, option);
    $(this).each(function (index) {
        var btn = new MBP.fastButton($(this).get(0), handler);    	
    });
	
};

$.fn.positionTab = function (option, callback) {//城市筛选方法
    var _this = {
        tabName: "tabChange",   //详细条目class
        tabSwitch: "li"         //字段html标签
    };
    $.extend(_this, option);
    var _obj = $(this).find(_this.tabSwitch);
    var _item = $("." + _this.tabName);
    _obj.each(function (index) {
        var _tabBtn = $(this);
        var mbp = new MBP.fastButton($(_tabBtn).get(0), function () {
            _obj.removeClass("active").eq(index).addClass("active");
            _item.css("display", "none").eq(index).css("display", "block");
            var top = _tabBtn.offset().top;
            $("html, body").animate({scrollTop: parseInt(top)}, 300);
        });
    });
    if (typeof(callback) != 'undefined') {
        callback.apply(this, arguments);
    }
    return $(this);
};

$.fn.radioBox = function(option){
    var _this = {
        initNum : "0",
        radioName : "radioOption",
        radioItemName : "ic_radio_a",
        selectedName : "selected" ,
        selectedVal : undefined ,
        callBack : undefined
    };
    $.extend(_this , option);
    var obj = $(this),
        item = obj.find("."+_this.radioName),
        itemChild = item,
        selected = _this.selectedName,
        i = _this.initNum;
    if(!item.hasClass(_this.radioItemName)){
        itemChild = item.find("."+_this.radioItemName);
    }
    if(i>=0){
        itemChild.eq(i).addClass(selected);    	
    }
    item.each(function(index){
        $(this).MBPBtn({},function(){
            itemChild.removeClass(selected);
            itemChild.eq(index).addClass(selected);
            if (typeof(_this.selectedVal) != 'undefined') {
                _this.selectedVal.apply(index, arguments);
            }
        })
    });
};
