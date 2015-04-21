(function ($) {
	$.fn.loadUrlHtml = function () {
		var $this = $(this);
		var url = $this.attr("href");
		var param = $this.attr("param");
		var jsonObj = null;
		if (param != null && "" != param) {
			jsonObj = eval("(" + param + ")");
		}
		if (url != null || url == "") {
			$.ajax({type:"POST", dataType:"html", url:url, async:false, data:jsonObj, beforeSend:function () {
				$this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
			}, timeout:3000, error:function (a, b, c) {
				if (b == "timeout") {
					alert("\u5730\u5740" + url + "\u8bf7\u6c42\u8d85\u65f6");
				} else {
					if (b == "error") {
						alert("\u65e0\u6cd5\u8bf7\u6c42href\u7684\u5730\u5740");
					}
				}
				$this.html("");
			}, success:function (data) {
				$this.html(data);
			}});
		} else {
			alert("\u8bf7\u6307\u5b9ahref\u5c5e\u6027");
		}
	};
	$.fn.refresh = function (json) {
		var $this = $(this);
		var url = $this.attr("href");
		var param = $this.attr("param");
		var jsonObj = null;
		if (param != null && "" != param) {
			jsonObj = eval("(" + param + ")");
		}
		if (url != null || url == "") {
			$.ajax({type:"POST", dataType:"html", url:url, async:false, data:json, beforeSend:function () {
				$this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
			}, timeout:3000, error:function (a, b, c) {
				if (b == "timeout") {
					alert("\u5730\u5740" + url + "\u8bf7\u6c42\u8d85\u65f6");
				} else {
					if (b == "error") {
						alert("\u65e0\u6cd5\u8bf7\u6c42href\u7684\u5730\u5740");
					}
				}
				$this.html("");
			}, success:function (data) {
				$this.html(data);
			}});
		} else {
			alert("\u8bf7\u6307\u5b9ahref\u5c5e\u6027");
		}
	};
	$.fn.reload = function (options) {
		var $this = $(this);
		var url = $this.attr("href");
		if (url != null || url == "") {
			$.ajax({type:"POST", dataType:"html", url:url, async:false, data:options, beforeSend:function () {
				$this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
			}, timeout:3000, error:function (a, b, c) {
				if (b == "timeout") {
					alert("\u5730\u5740" + url + "\u8bf7\u6c42\u8d85\u65f6");
				} else {
					if (b == "error") {
						alert("\u65e0\u6cd5\u8bf7\u6c42href\u7684\u5730\u5740");
					}
				}
				$this.html("");
			}, success:function (data) {
				$this.html(data);
			}});
		} else {
			alert("\u8bf7\u6307\u5b9ahref\u5c5e\u6027");
		}
	};
})(jQuery);

