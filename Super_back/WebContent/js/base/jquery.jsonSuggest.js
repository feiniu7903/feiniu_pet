(function ($) {
	$.suggest = function (input, options) {
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
					timeout = setTimeout(suggest, options.delay);
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
				  case 27: //	escape
					$results.hide();
					break;
				}
			} else {
				if ($input.val().length != prevLength) {
					if (timeout) {
						clearTimeout(timeout);
					}
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
				}
			}
		}
		function suggest() {
			var q = $.trim($input.val());
			if (q.length >= options.minchars) {
				cached = checkCache(q);
				if (cached) {
					displayItems(cached["items"]);
				} else {
					$.ajax({type:"POST", url:options.source, data:{name:q}, success:function (txt){
						var ele = "";
						for (var i=0;i<txt.comPlaceList.length;i++) {
							var p = txt.comPlaceList[i];
						  ele+="<a  id=\""+p.placeId+"\" val=\""+p.name+"\">"+p.name+"</a>\n";
						}

						$results.hide();
						var items = parseTxt(ele, q);
						displayItems(items);
						addToCache(q, items, ele.length);
					}});
					
					
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
			for (var i = 0; i < items.length; i++) {
				html += "<li>" + items[i] + "</li>";
			}
			$results.html(html).show();
			$results.children("li").mouseover(function () {
				$results.children("li").removeClass(options.selectClass);
				$(this).addClass(options.selectClass);
			});
			$results.children("li").click(function (e) {
				options.callback($(this));
				//selectCurrentResult();
				selectVal($(this));
			});
		}

		function parseTxt(txt, q) {
			var items = [];
			var tokens = txt.split(options.delimiter);
			for (var i = 0; i < tokens.length; i++) {
				var token = $.trim(tokens[i]);
				
				if (token) {
						var name=$(token).attr("val");
						var id = $(token).attr("id");
						$(token).attr("val","")
						token = $(token).html();
					token = token.replace(new RegExp(q, "ig"), function (q) {
						return "<span onClick=\"\" id=\""+id+"\" name=\""+name+"\" class=\"" + options.matchClass + "\">" + q + "</span>";
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
			alert($currentResult.val());
			if ($currentResult) {
				$input.val($currentResult.val());
				//form submit
//				setKeyWords();
				$results.hide();
				if (options.onSelect) {
					options.onSelect.apply($input[0]);
				}
			}
		}
		
		function selectVal(obj){
			var i = obj.find("span");
			$input.val(i.attr("name"));
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

	$.fn.suggest = function (source, options) {
		if (!source) {
			return;
		}
		options = options || {};
		options.source = source;
		options.delay = options.delay || 100;
		options.resultsClass = options.resultsClass || "ac_results";
		options.selectClass = options.selectClass || "ac_over";
		options.matchClass = options.matchClass || "ac_match";
		options.minchars = options.minchars || 1;
		options.delimiter = options.delimiter || "\n";
		options.onSelect = options.onSelect || false;
		options.maxCacheSize = options.maxCacheSize || 65536;
		options.callback = options.callback || "#";
		this.each(function () {
			new $.suggest(this, options);
		});
		return this;
	};
})(jQuery);