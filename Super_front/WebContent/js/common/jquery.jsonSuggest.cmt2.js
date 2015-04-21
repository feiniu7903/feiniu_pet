
(function ($) {
	$.suggest2 = function (input, options) {
		var $input = $(input).attr("autocomplete", "off");
		var $results = $(document.createElement("ul"));
		var timeout = false;
		var prevLength = 0;
		var cache = [];
		var cacheSize = 0;
		$results.addClass(options.resultsClass).appendTo("body");
		resetPosition();
		$(window).load(resetPosition).resize(resetPosition);
		$input.blur(function () {
			setTimeout(function () {
				$results.hide();
			}, 200);
		});
		try {
			$results.bgiframe();
		}
		catch (e) {
		}
		if ($.browser.mozilla) {
			$input.keypress(processKey2);
		} else {
			$input.keydown(processKey2);
		}
		$input.keyup(processKey);
		function resetPosition() {
			var offset = $input.offset();
			$results.css({top:(offset.top + input.offsetHeight) + "px", left:offset.left + "px"});
		}
		function processKey(e) {
			if (/^32$|^9$/.test(e.keyCode) && getCurrentResult()) {
				if (e.preventDefault) {
					e.preventDefault();
				}
				if (e.stopPropagation) {
					e.stopPropagation();
				}
				e.cancelBubble = true;
				e.returnValue = false;
				selectCurrentResult();
			} else {
				if ($input.val().length != prevLength) {
					if (timeout) {
						clearTimeout(timeout);
					}
					timeout = setTimeout(suggest2, options.delay);
					prevLength = $input.val().length;
				}
			}
		}
		function processKey2(e) {
			if (/27$|38$|40$/.test(e.keyCode) && $results.is(":visible")) {
				if (e.preventDefault) {
					e.preventDefault();
				}
				if (e.stopPropagation) {
					e.stopPropagation();
				}
				e.cancelBubble = true;
				e.returnValue = false;
				switch (e.keyCode) {
				  case 38: // up
					prevResult();
					break;
				  case 40: // down
					nextResult();
					break;
				  case 27: // escape
					$results.hide();
					break;
				}
			} else {
				if ($input.val().length != prevLength) {
					if (timeout) {
						clearTimeout(timeout);
					}
					timeout = setTimeout(suggest2, options.delay);
					prevLength = $input.val().length;
				}
			}
		}
		function suggest2() {
			var q = $.trim($input.val());
			if (q.length >= options.minchars) {
				cached = checkCache(q);
				if (cached) {
					displayItems(cached["items"]);
				} else {
					$.get(options.source, {q:q}, function (txt) {
						$results.hide();
						if (options.onSuccess) {
							if (txt.indexOf("\u6ca1\u6709") == -1) {
								$("#info").show();
							} else {
								$("#info").hide();
							}
						}
						var items = parseTxt(txt, q);
						displayItems(items);
						addToCache(q, items, txt.length);
					});
				}
			} else {
				$results.hide();
			}
		}
		function checkCache(q) {
			for (var i = 0; i < cache.length; i++) {
				if (cache[i]["q"] == q) {
					cache.unshift(cache.splice(i, 1)[0]);
					return cache[0];
				}
			}
			return false;
		}
		function addToCache(q, items, size) {
			while (cache.length && (cacheSize + size > options.maxCacheSize)) {
				var cached = cache.pop();
				cacheSize -= cached["size"];
			}
			cache.push({q:q, size:size, items:items});
			cacheSize += size;
		}
		function displayItems(items) {
			if (!items) {
				return;
			}
			if (!items.length) {
				$results.hide();
				return;
			}
			var html = "";
			$("#number_listbox").empty();
			for (var i = 0; i < items.length; i++) {
				$(items[i]).appendTo($("#number_listbox"));
			}
		}
		function parseTxt(txt, q) {
			var items = [];
			var tokens = txt.split(options.delimiter);
			for (var i = 0; i < tokens.length; i++) {
				var token = $.trim(tokens[i]);
				if (token) {
					token = token.replace(new RegExp(q, "ig"), function (q) {
						return q;
					});
					items[items.length] = token;
				}
			}
			return items;
		}
		function getCurrentResult() {
			if (!$results.is(":visible")) {
				return false;
			}
			var $currentResult = $results.children("li." + options.selectClass);
			if (!$currentResult.length) {
				$currentResult = false;
			}
			return $currentResult;
		}
		function selectCurrentResult() {
			$currentResult = getCurrentResult();
			if ($currentResult) {
				$input.val($currentResult.val());
				// form submit
				// setKeyWords();
				$results.hide();
				if (options.onSelect) {
				}
			}
		}
		function nextResult() {
			$currentResult = getCurrentResult();
			if ($currentResult) {
				$currentResult.removeClass(options.selectClass).next().addClass(options.selectClass);
			} else {
				$results.children("li:first-child").addClass(options.selectClass);
			}
		}
		function prevResult() {
			$currentResult = getCurrentResult();
			if ($currentResult) {
				$currentResult.removeClass(options.selectClass).prev().addClass(options.selectClass);
			} else {
				$results.children("li:last-child").addClass(options.selectClass);
			}
		}
	};
	$.fn.suggest2 = function (source, options) {
		if (!source) {
			return;
		}
		options = options || {};
		options.source = source;
		options.onSuccess = options.onSuccess || false;
		options.delay = options.delay || 100;
		options.resultsClass = options.resultsClass || "ac_results";
		options.selectClass = options.selectClass || "ac_over";
		options.matchClass = options.matchClass || "ac_match";
		options.minchars = options.minchars || 1;
		options.delimiter = options.delimiter || "\n";
		options.onSelect = options.onSelect || false;
		options.maxCacheSize = options.maxCacheSize || 65536;
		this.each(function () {
			new $.suggest2(this, options);
		});
		return this;
	};
})(jQuery);

