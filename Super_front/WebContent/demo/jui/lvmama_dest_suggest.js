(function($) {
	var arrayAf = new Array();
	var arrayGJ = new Array();
	var arrayKn = new Array();
	var arrayPw = new Array();
	var arrayXz = new Array();
	var arrayRm = new Array();
	var dest_div = $("<div class=\"cityAll2\" style=\"zoom:1;display:none;\">"
			+ "<div class=\"cityAllCont\" style=\"display:\" id=\"popCity2\">"
			+ "<div class=\"cityAllContTit\"><span>热门城市</span>(可直接选择城市或输入城市全拼)</div>"
			+ "<ul class=\"cityDetail\">"
			+ "<li class=\"cityDetailBlock\">"
			+ "<p class=\"CountryName11\"><a class=\"CountryNameA\">推荐</a><a>A-F</a><a>G-J</a><a>K-N</a><a>P-W</a><a>X-Z</a></p>"
			+ "<p class=\"CountryNameDetail c2 countryBlock\">" + "</p>"
			+ "<p class=\"CountryNameDetail c2\">" + "</p>"
			+ "<p class=\"CountryNameDetail c2\">" + "</p>"
			+ "<p class=\"CountryNameDetail c2\">" + "</p>"
			+ "<p class=\"CountryNameDetail c2\">" + "</p>"
			+ "<p class=\"CountryNameDetail c2\">" + "</p>" + "</li>" + "</ul>"
			+ "<span class=\"leftCityAll\"></span> " + "</div>" + "</div>");
	var dest_div2 = $("<div id='citySearchDiv' class=\"PycityAll\" style=\"display:none;z-index:9999;\">"
			+ "<div class=\"PycityTit\">s，按拼音排序</div>"
			+ "<ul>"
			+ "</ul>"
			+ "<p class=\"PycityPage\"></p>" + " </div>");
	$.fn.dest_suggest = function(options) {
		var options = options || {};
		options.rightInput = options.rightInput || 0;
		options.inputBottom = options.inputBottom || 20;
		options.hideSelect = options.hideSelect || false;
		var keyInputVal = "sh";
		var preKeyInputHasResultVal = "sh";
		var input = $(this);
		var currentPage = 1;
		var url = "http://dest.lvmama.com/newplace/commAction!destInfos.do?jsoncallback=?";//上线注意这个URL要换成www.lvmama.com
		$.getJSON(url, {recommendBlockId : 7142}, function(txt) {
			arrayAf = txt.af;
			arrayGJ = txt.gj;
			arrayKn = txt.kn;
			arrayPw = txt.pw;
			arrayXz = txt.xz;
			arrayRm = txt.rm;
			// 创建拼音块
			dest_div.find("p").eq(1).html(printTxt(arrayRm));
			dest_div.find("p").eq(2).html(printTxt(arrayAf));
			dest_div.find("p").eq(3).html(printTxt(arrayGJ));
			dest_div.find("p").eq(4).html(printTxt(arrayKn));
			dest_div.find("p").eq(5).html(printTxt(arrayPw));
			dest_div.find("p").eq(6).html(printTxt(arrayXz));
			// 初始化事件
			dest_div.find("#canClick").each(function() {
				$(this).bind("click", function() {
					clickAtag($(this));
				});
			});
		});
		function hideSelect() {
			if (options.hideSelect) {
				$("select").each(function() {
					$(this).hide();
				});
			}
		}
		function showSelect() {
			if (options.hideSelect) {
				$("select").each(function() {
					$(this).show();
				});
			}
		}
		// 阻止事件冒泡
		dest_div2.bind("click", function(event) {
			event.stopPropagation();
		});
		// 阻止事件冒泡
		dest_div.bind("click", function(event) {
			event.stopPropagation();
		});
		function initChoseItem() {
			dest_div2.find("li").each(function() {
				$(this).css("background", "");
			});
		}
		$(document).bind("click", function() {
			showSelect();
			if ($.trim(input.val()) == "") {
				input.val("中文/拼音");
			}
			dest_div.hide();
			dest_div2.hide();
		});
		function hideDestDivs() {
			dest_div.hide();
			var li = dest_div2.find("#keyChose");
			if (li.length != 0) {
				dest_div2.hide();
			}
		}
		function keyChoseEvent(flag) {
			if($('#citySearchDiv').css("display")=='none') {
				return;
			}
			initChoseItem();
			var li = dest_div2.find("#keyChose");
			var liLength = dest_div2.find("li").length;
			var cli = null;
			if (li.length == 0) {
				cli = dest_div2.find("li").eq(0);
			} else {
				initKeyChoseFlag();
				if (flag == "up") {
					var prevTag = li.prev();
					if (prevTag.attr("tagName") == undefined || prevTag.attr("tagName").toLocaleLowerCase() != "li") {// 选中最后一个
						cli = dest_div2.find("li").eq(liLength - 1);
					} else {
						cli = prevTag;
					}
				} else {
					var nextTag = li.next();
					if (nextTag.attr("tagName") == undefined || nextTag.attr("tagName").toLocaleLowerCase() != "li") {// 选中第一个
						cli = dest_div2.find("li").eq(0);
					} else {
						cli = nextTag;
					}
				}
			}
			cli.attr("id", "keyChose");
			cli.css("background", "#F7FFBF");
			input.val(cli.find("a").attr("name"));
		}
		function initKeyChoseFlag() {
			dest_div2.find("li").each(function() {
				$(this).removeAttr("id");
			});
		}
		// 给提示创建分页
		function createPageConfig(pageSize, page) {
			var html = "";
			if (pageSize > 4) {
				pageSize = pageSize;
			}
			var j = 0;
			if (page > 1) {
				html += "<span id=\"prev\"><a href=\"javascript:void(0)\"><-</a></span>";
			}
			for ( var i = page; i <= pageSize; i++) {
				j++;
				if (i == page) {
					html += "<span>" + i + "</span>";
				} else {
					html += "<span id=\"canClick\"><a href=\"javascript:void(0)\">" + i + "</a></span>";
				}
				if (j >= 4) {
					html += "<span id=\"next\"><a href=\"javascript:void(0)\">-></a></span>";
					break;
				}
			}
			// 如果只有一页 那么不创建分页
			if (pageSize == 1) {
				html = "";
			}
			dest_div2.find(".PycityPage").html(html);
			dest_div2.find(".PycityPage").find("#canClick").each(function() {
				$(this).bind("click", function() {
					changeSuggestPreSuccess(parseInt($(this).find("a").text()));
				});
			});
			dest_div2.find("#next").bind("click", function() {
				nextPage();
			});

			dest_div2.find("#prev").bind("click", function() {
				prevPage();
			});
			// 初始化提示的事件
			initSuggestEventAndData();
		}
		function nextPage() {
			changeSuggestPreSuccess(parseInt(currentPage) + 1);
		}
		function prevPage() {
			changeSuggestPreSuccess(parseInt(currentPage) - 1);
		}
		input.bind("click", function(event) {
			if($('input.hotelNameParm').val()=="") {
				$('input.hotelNameParm').val("中文/拼音");
			}
			if($('#roundPlaceName').val()=="") {
				$('#roundPlaceName').val("中文/拼音");
			}
			$(".cityAll2").hide();
			$(".PycityAll").hide();
			event.stopPropagation();
			input.val("");
			showDestSuggest();
		});
		function clickAtag(obj) {
			input.val(obj.text());
			showSelect();
			dest_div.hide();
		}
		$(window).resize(function() {
			initDestDiv();
		});
		// input.bind("keydown",function(){
		// showDestSuggest2();
		// })
		// 绑定键盘事件
		var timer;
		input.bind("keyup", function(event) {
			clearTimeout(timer);
			timer = setTimeout(function() {
				var keycode = event.keyCode * 1;
				if (input.val() != "") {
					// 记录键盘输入的字符
					keyInputVal = input.val();
					dest_div.hide();
					showDestSuggest2();
					if (keycode != 38 && keycode != 40) {
						changeSuggest(1);
					}
				}
			}, 500);
		});
		// 绑定键盘事件
		$(document).bind("keyup", function(event) {
			var keycode = event.keyCode * 1;
			if (input.val() != "") {
				if (keycode == 38 || keycode == 40) {
					processKey(keycode);
					event.stopPropagation();
				} else if (keycode == 13) {
					hideDestDivs();
					showSelect();
				}
			} else {
				dest_div2.find("ul").html("");
				dest_div2.hide();
				showSelect();
			}
		});
		function processKey(keyCode) {
			switch (keyCode) {
			case 38: // up
				// alert("up");
				keyChoseEvent("up");
				break;
			case 40: // down
				keyChoseEvent("down");
				break;
			}
		}
		// 移除键盘事件
		function unbindKeyUp() {
			$("body").unbind("keyup");
		}
		// 公共数据绑定方法 根据页码初始化自动提示数据
		function changeSuggest(page) {
			getInputKeySuggest(keyInputVal, page);
			dest_div2.fadeIn();
		}
		// 调用上次有结果集的suggest分页数据
		function changeSuggestPreSuccess(page) {
			getInputKeySuggest(preKeyInputHasResultVal, page);
			// dest_div2.fadeIn();
		}
		function setSearchTitleInfo(found) {
			var inputVal = keyInputVal;
			if (!found) {
				if (inputVal.length >= 7) {
					dest_div2.find(".PycityTit").html("对不起，没有找到:" + inputVal.substring(0, 7) + "...");
				} else {
					dest_div2.find(".PycityTit").html("对不起，没有找到:" + inputVal);
				}
			} else {
				if (inputVal.length >= 7) {
					dest_div2.find(".PycityTit").html(inputVal.substring(0, 7) + "..." + ",按拼音排序");
				} else {
					dest_div2.find(".PycityTit").html(inputVal + ",按拼音排序");
				}
			}
		}
		// li 绑定点击事件
		function suggestEventBinder() {
			dest_div2.find("li").each(function() {
				$(this).bind("click", function() {
					input.val($(this).find("a").attr("name"));
					showSelect();
					dest_div2.hide();
				});
			});
		}
		// 远程数据获取
		function getInputKeySuggest(keyword, page) {
			if ($.trim(keyword) != "") {
				$.ajax( {url : "http://www.lvmama.com/search/placeSearch!searchCity.do?keyword=" + encodeURI(keyword.toLocaleLowerCase()) + "&page=" + page,
					type : 'GET',
					dataType : 'jsonp',
					jsonp : 'callback',
					success : function(jsonData) {
						if (jsonData.placeListJson != undefined) {
							preKeyInputHasResultVal = keyword;
							// 生成html
							dest_div2.find("ul").html(printKeySuggestHtml(jsonData, keyword));
							// 计算分页页数
							var pageSize = Math.ceil(jsonData.totalResultSize / 10);
							currentPage = jsonData.page;
							// 分页
							createPageConfig(pageSize, currentPage);
							setSearchTitleInfo(true);
						} else {
							setSearchTitleInfo(false);
						}
					},
					error : function() {
						// alert("failure");
					}
				});
			}
		}
		function initSuggestEventAndData() {
			suggestEventBinder();// 初始化点击事件
		}
		// 创建提示每一个li元素
		function printKeySuggestHtml(jsonData, keyword) {
			var html = "";
			for ( var i = 0; i < jsonData.placeListJson.length; i++) {
				html += "<li><a href=\"javascript:void(0)\" name=\""
						+ jsonData.placeListJson[i].name + "\" id=\"" 
						+ jsonData.placeListJson[i].id + "\"><span>"
						+ jsonData.placeListJson[i].pinYin + "</span>"
						+ highlightText(jsonData.placeListJson[i].name, keyword) + "</a></li>";
			}
			return html;
		}
		
		function highlightText(text, keyword) {
			var highlightText = text;
			var len = keyword.length;
			var pos = text.indexOf(keyword);
			if (pos >= 0) {
				var part1 = text.substr(0, pos);
				var part2 = "<b>" + text.substr(pos, len) + "</b>";
				var part3 = text.substr(pos + len);
				highlightText = part1 + part2 + part3;
			}
			return highlightText;
		}
		
		function showDestSuggest() {
			initDestDiv();
			dest_div2.hide();
			//hideSelect();
			dest_div.show();
		}
		function initDestDiv() {
			dest_div.css("top", parseInt(input.offset().top) + options.inputBottom);
			dest_div.css("left", parseInt(input.offset().left) - options.rightInput);
		}
		function showDestSuggest2() {
			hideSelect();
			dest_div2.css("top", parseInt(input.offset().top) + 20);
			dest_div2.css("left", parseInt(input.offset().left));
		}
		function changeCity(obj1, class1, obj2, class2) {
			var tab1 = $("." + obj1), tab2 = $("." + obj2);
			tab1.find("a").each(function(i) {
				$(this).bind("click", function() {
					$(this).addClass(class1).siblings().removeClass(class1);
					tab2.removeClass(class2);
					tab2.eq(i).addClass(class2);
				});
			});
		}
		function printTxt(array) {
			var html = "";
			for ( var i = 0; i < array.length; i++) {
				html += "<a href=\"javascript:void(0)\" id=\"canClick\" title=" + array[i].name + ">" + array[i].name + "</a>";
			}
			return html;
		}
		$("body").append(dest_div);
		$("body").append(dest_div2);
		changeCity("CountryName", "CountryNameA", "c1", "countryBlock");
		changeCity("CountryName11", "CountryNameA", "c2", "countryBlock");
	};
})(jQuery);