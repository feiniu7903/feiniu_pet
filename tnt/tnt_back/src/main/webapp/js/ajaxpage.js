var initAjaxPages = function(beforeUrl, afterUrl, htmlDiv) {
	if (!htmlDiv)
		htmlDiv = "queryBox";
	var hrefs = $(".Pages a");
	if (hrefs) {
		hrefs.each(function(i, tag) {
			if (tag) {
				var href = tag.href;
				if (href.indexOf(beforeUrl) != -1
						|| href.indexOf(afterUrl) != -1) {
					var url = href.replace(beforeUrl, afterUrl);
					url = url.replace("page=2", "page=1");
					$.ajax({
						url : url,
						success : function(response) {
							$("#" + htmlDiv).html(response);
							return false;
						}
					});
				}
			}
		});
	}
};
var setAjaxPages = function(beforeUrl, afterUrl, htmlDiv) {
	if (!htmlDiv)
		htmlDiv = "queryBox";
	var hrefs = $(".Pages a");
	if (hrefs) {
		hrefs.each(function(i, tag) {
			if (tag) {
				var href = tag.href;
				if (href.indexOf(beforeUrl) != -1
						|| href.indexOf(afterUrl) != -1) {
					var url = href.replace(beforeUrl, afterUrl);
					tag.href = "#";
					$(tag).click(function() {
						$.ajax({
							url : url,
							success : function(response) {
								$("#" + htmlDiv).html(response);
							}
						});
					});
				}
			}
		});
	}
};

var dialogAjaxPages = function(fun) {
	var hrefs = $("#dialogPage .Pages a");
	if (hrefs) {
		hrefs.each(function(i, tag) {
			if (tag) {
				var url = tag.href;
				if (url.indexOf("page") != -1) {
					tag.href = "#";
					$(tag).click(function() {
						var dialog = eval(fun);
						if (dialog) {
							dialog.reloadUrl(url);
						}
					});
				}
			}
		});
	}
};

var postSubmitPages = function(formElement, pageElement) {
	var hrefs = $("#lv_page .Pages a");
	if (hrefs) {
		hrefs.each(function(i, tag) {
			if (tag) {
				var url = tag.href;
				var index = url.indexOf("page=");
				if (index != -1) {
					tag.href = "#";
					$(tag).click(function() {
						var page_param = url.substring(index, url.length);
						var p = page_param.split("=")[1];
						var start = url.indexOf("?");
						var startUrl = url.substring(0, start);
						url = startUrl + "?" + page_param;
						var f = $("#" + formElement);
						if (f) {
							f.action = url;
							var pe = pageElement ? pageElement : "page";
							$("#" + pe).val(p);
							if (f.validate && !f.validate().form()) {
								return;
							} else {
								f.submit();
							}
						}
					});
				}
			}
		});
	}
};