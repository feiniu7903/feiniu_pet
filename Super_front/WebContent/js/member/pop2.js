
(function ($) {
	$.pop = function (options) {
		var getWidth = function () {
			var version = parseInt($.prototype.jquery.match(/\d/gim)[1]);
			var width;
			if (version > 1) {
				width = $(window).width();
			} else {
				width = document.body.scrollWidth ? document.body.scrollWidth : document.documentElement.scrollWidth;
			}
			return width / 2;
		};
		var settings = $.extend(true, {bg:"floatbox-background", box:"floatbox-box", content:"", button:"", desc:"", fade:false, ajax:null, bgConfig:{position:($.browser.msie) ? "absolute" : "fixed", zIndex:8, width:"100%", height:"100%", top:"0px", left:"0px", backgroundColor:"#000", opacity:"0.1", display:"none"}, boxConfig:{position:($.browser.msie) ? "absolute" : "fixed", zIndex:9, width:getWidth() + "px", marginLeft:"-" + (getWidth() / 2) + "px", height:"auto", top:"50%", left:"50%", display:"none"}}, options);
		var showBox = function () {
			var content = typeof settings.content === "string" ? settings.content : settings.content.clone();
			$("<div></div>").bind("click", function () {
				closeBox();
			}).attr("id", settings.bg).css(settings.bgConfig).width(($.browser.msie) ? document.body.clientWidth : "100%").height(($.browser.msie) ? document.body.clientHeight : "100%").appendTo("body");
			$("<div></div>").attr({id:settings.box, role:"alertdialog"}).html(content).append(settings.button).css(settings.boxConfig).appendTo("body").css("margin-top", "-" + $("#" + settings.box).height() / 2 + "px").find(".close-floatbox").bind("click", function () {
				closeBox();
			}).end();
			if (settings.fade) {
				$("#" + settings.bg).fadeIn(200, function () {
					$("div#" + settings.box).fadeIn(200);
				});
			} else {
				$("#" + settings.bg).show().parent().find("#" + settings.box).show();
			}
			if (settings.ajax) {
				$.ajax({type:settings.ajax.params === "" ? "GET" : "POST", url:settings.ajax.url, data:settings.ajax.params, beforeSend:function () {
					$("#" + settings.box).html(settings.ajax.before);
				}, success:function (data) {
					$("#" + settings.box).html(data).append(settings.button).find(".close-floatbox").bind("click", function () {
						closeBox();
					}).end();
				}, complete:function (XMLHttpRequest, textStatus) {
					if (settings.ajax.finish) {
						settings.ajax.finish(XMLHttpRequest, textStatus);
					}
				}});
				$("body, html").css({height:"100%", width:"100%"});
				$("#" + settings.box).css("top", document.documentElement.scrollTop + ($(window).height() / 2) + "px");
			}
		};
		var closeBox = function () {
			$("select").show();
			$("object").show();
			if (settings.fade) {
				$("#" + settings.box).fadeOut(200, function () {
					$("#" + settings.bg).fadeOut(200, function () {
						$("#" + settings.box).remove();
						$("#" + settings.bg).remove();
					});
				});
			} else {
				$("#" + settings.box + ",#" + settings.bg).hide();
				setTimeout(function () {
					$("#" + settings.box).remove();
					$("#" + settings.bg).remove();
				}, 500);
			}
		};
		var init = function () {
			showBox();
			$(document).one("keypress", function (e) {
				var escKey = $.browser.mozilla ? 0 : 27;
				if (e.which === escKey) {
					closeBox();
				}
			}).one("keydown", function (e) {
				var escKey = $.browser.mozilla ? 0 : 27;
				if (e.which === escKey) {
					closeBox();
				}
			});
			if ($.browser.msie) {
				$("body, html").css({height:"100%", width:"100%"});
				$(window).bind("scroll", function () {
					$("#" + settings.box).css("top", document.documentElement.scrollTop + ($(window).height() / 2) + "px");
				});
			}
		};
		init();
	};
})(jQuery);

