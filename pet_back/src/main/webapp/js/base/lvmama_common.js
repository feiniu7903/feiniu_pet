(function($) {
	$.fn.showTip = function(options) {
		var obj = $(this);
		obj.css("cursor", "pointer");
		//$(this).append(divContent);
		var divContent = $("<div style=\"display:none;\"></div>");
		divContent.css("position", "absolute");
		divContent.css("width", "500px");
		divContent.css("height", "200px");
		divContent.css("overflow", "auto")
		divContent.css("border", "1px");
		divContent.css("border-color", "black");
		divContent.css("border-style", "solid")
		divContent.css("background-color", "white")
		divContent.css("z-index", "10005")
		$("body").append(divContent);
		var timeout;
		$(this).bind("mouseover", function() {
			var top = $(this).offset().top;
			var left = $(this).offset().left;

			divContent.css("top", top);
			divContent.css("left", left + $(this).width());
			var url = options.url;
			var param = options.param;
			divContent.attr("href", url);
			timeout = setTimeout(function() {
				divContent.fadeIn();
				if ($.trim(divContent.html()) == "") {
					divContent.reload(param);
				}
			}, 1000)

			//$(this).unbind("mouseover");
			});
		divContent.bind("click",function(event){event.stopPropagation();});
		$(document).bind("click", function(event) {
			if (event.currentTarget === this) {
				divContent.hide();
			} else {
			}
		});
		//divContent.bind("mouseover",function(){});
		$(this).bind("mouseout", function() {
			if (timeout != null) {
				clearTimeout(timeout);
			}
			//divContent.hide();
			});
	}
})(jQuery);

function openWin(url, width, height) {
	window
			.open(
					url,
					"_blank",
					"height="
							+ height
							+ ", width="
							+ width
							+ ", top=250, left=350, toolbar=no,menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
}