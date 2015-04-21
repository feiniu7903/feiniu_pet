var initAjaxPages = function(beforeUrl, afterUrl, htmlDiv) {
	if (!htmlDiv)
		htmlDiv = "queryBox";
	var hrefs = $(".pagebox a");
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
	var hrefs = $(".pagebox a");
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