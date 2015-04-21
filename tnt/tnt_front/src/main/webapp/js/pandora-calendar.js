(function(global, $, pandora, undefined) {
	"use strict"
	if (pandora.calendar) {
		return;
	}
	if (!Array.prototype.forEach) {
		Array.prototype.forEach = function(fn, scope) {
			var i, len;
			for (i = 0, len = this.length; i < len; ++i) {
				if (i in this) {
					fn.call(scope, this[i], i, this);
				}
			}
		};
	}
	function toDate(date) {
		var d = null;
		if (typeof date === "string") {
			d = date.split("-");
			d = new Date(d[0], d[1] === "12" ? "11" : d[1] - 1, d[2]);
		}
		return d;
	}
	function Factory(options) {
		var options = options || {}, defaults = Factory.defaults;
		for ( var i in defaults) {
			if (options[i] === undefined) {
				options[i] = defaults[i];
			}
			;
		}
		;
		toDate(options.date);
		return new Calendar(options);
	}
	function Calendar(options) {
		this._init(options);
	}
	Calendar.prototype = {
		constructor : Calendar,
		_init : function(options) {
			this.options = options;
			this.cacheDate = options.date;
			this.warp = this.warp || $($.trim(this.options.template.warp));
			this.warp.attr("data-render", options.autoRender);
			this.loadCal();
		},
		loadCal : function() {
			var options = this.options, that = this;
			if (options.autoRender) {
				$(options.target).append(this.warp);
				this.render();
				this.bindEvent();
			} else {
				$(options.trigger).bind(options.triggerEvent, function(e) {
					if (e.stopPropagation)
						e.stopPropagation();
					else
						window.event.cancelBubble = true;
					$(options.target).append(that.warp);
					that.$trigger = $(this);
					that._isRange = $(this).attr("data-range");
					var offset = that.offset();
					if ($(".ui-calendar[data-render=false]").length > 1) {
						that.warp.detach();
					}
					var offsetLeftAmount = options.offsetAmount.left;
					var offsetTopAmount = options.offsetAmount.top;
					that.warp.css({
						position : "absolute",
						zIndex : 9999,
						left : offset.left + offsetLeftAmount,
						top : offset.top + offsetTopAmount
					});
					that.render();
					that.bindEvent();
				});
				this.triggerBlur();
			}
		},
		replaceWith : function(str, obj) {
			for ( var i in obj) {
				str = str.replace("{{" + i + "}}", obj[i]);
			}
			return str;
		},
		mend : function(value) {
			return value.toString().length === 1 ? "0" + value : value;
		},
		render : function() {
			var options = this.options;
			this.warp.html("");
			this.warp.append(this.createHead());
			this.warp.append(this.createCalWarp());
			if (this.options.frequent) {
				this.warp.css("width", this.warp.find("div.calmonth").width());
			}
			if (typeof options.sourceFn === "function") {
				try {
					var flag = options.sourceFn(this);
					if (flag == false)
						return false;
				} catch (err) {
					alert(err);
					return false;
				}
			}
			this.warp.show();
			if (typeof options.completeCallback === "function") {
				options.completeCallback();
			}
		},
		createHead : function() {
			var html = "";
			html = this
					.replaceWith(
							this.options.template.calControl,
							{
								stylePrev : 'style="display:'
										+ (this.options.control ? this.options.showPrev ? "black"
												: "black"
												: "black") + '"',
								styleNext : 'style="display:'
										+ (this.options.control ? this.options.showNext ? "black"
												: "none"
												: "black") + '"'
							});
			return html;
		},
		createCalWarp : function() {
			var that = this, options = that.options, month = options.date
					.getMonth(), year = options.date.getFullYear(), html = "";
			html += that.createBody(month, year);
			if (!options.frequent) {
				html += that.createBody((month + 1) % 12,
						month === 11 ? year + 1 : year);
			}
			html = this.replaceWith(options.template.calWarp, {
				content : html
			});
			return html;
		},
		createBody : function(month, year) {
			var html = "", options = this.options;
			html += this.replaceWith(options.template.calTitle, {
				month : this.replaceWith(options.titleTip, {
					year : year,
					month : month + 1
				})
			});
			html += this.replaceWith(options.template.calBody, {
				month : month + 1,
				date : this.createDay(month, year)
			});
			html = this.replaceWith(options.template.calMonth, {
				content : html
			});
			return html;
		},
		setToday : function(d) {
			var nowDate = new Date(new Date().getFullYear(), new Date()
					.getMonth(), new Date().getDate()), date = new Date(d.year,
					d.month - 1, d.day), festival = this.options.festival, index = d.year
					+ "-" + this.mend(d.month) + "-" + this.mend(d.day), day = 0;
			day = (date.getTime() - nowDate.getTime()) / (1000 * 3600 * 24);
			switch (day) {
			case 0:
				return "今天";
				break;
			case 1:
				return "明天";
				break;
			case 2:
				return "后天";
				break;
			default:
				return festival[index] ? festival[index] : d.day;
			}
		},
		createDay : function(month, year) {
			var html = "", day = "", that = this, options = that.options, dates = this
					.getDatesByMonth(month, year);
			dates.forEach(function(d) {
				d.forEach(function(d) {
					day += that.replaceWith(options.template.day, {
						week : d === "" ? "" : 'week="'
								+ new Date(d.year, d.month - 1, d.day).getDay()
								+ '"',
						dateMap : d === "" ? "" : 'date-map="' + d.year + "-"
								+ that.mend(d.month) + "-" + that.mend(d.day)
								+ '"',
						day : d === "" ? "" : that.setToday(d),
						className : 'class="' + that.getClass(d) + '"'
					});
				});
				html += that.replaceWith(options.template.weekWarp, {
					week : day
				});
				day = "";
			});
			return html;
		},
		getClass : function(date) {
			var options = this.options, fatalism = options.fatalism, cacheDate = this.cacheDate, startDate = this
					.addDays(cacheDate, options.startDelayDays), endDate = this
					.addDays(new Date(startDate.getFullYear(), startDate
							.getMonth(), startDate.getDate()), fatalism), className = [], d1 = null, d2 = null, d3 = null, index = 0, val1 = "", val2 = "";
			if (date === "") {
				className.push(options.classNames.nodate);
			} else {
				d1 = new Date(date.year, date.month - 1, date.day);
				d2 = new Date(cacheDate.getFullYear(), cacheDate.getMonth(),
						cacheDate.getDate());
				if (this.$trigger !== undefined) {
					d3 = toDate(this.$trigger.val())
				}
				if (this._isRange) {
					index = $(options.cascade.trigger).index(this.$trigger);
					val1 = $(options.cascade.trigger).eq(index - 1).val()
							.split("-");
					val1[2] = (parseInt(val1[2], 10) + options.cascade.days)
							.toString();
					val2 = toDate(this.$trigger.val());
					d2 = new Date(val1[0], val1[1] === "12" ? "11"
							: val1[1] - 1, val1[2]);
				}
				
				/*if (fatalism == 0 && (d1 - d2) < 0) {
					className.push(options.classNames.nodate);
				} else if (fatalism == 0) {
					className.push(options.classNames.caldate);
				}*/
				className.push(options.classNames.caldate);
				
				if (this._isRange === "true" && val1 !== "") {
					if (val2 - d2 >= 0) {
						if (d1 - val2 < 0) {
							className.push(options.classNames.interval);
						}
						if (date.year == val2.getFullYear()
								&& date.month - 1 == val2.getMonth()
								&& date.day == val2.getDate()) {
							className.push(options.classNames.selectDay);
						}
					}
				} else {
					if (cacheDate.getFullYear() == date.year
							&& cacheDate.getMonth() == date.month - 1
							&& cacheDate.getDate() == date.day) {
						className.push(options.classNames.today);
					}
					if (d3 !== null && date.year == d3.getFullYear()
							&& date.month - 1 == d3.getMonth()
							&& date.day == d3.getDate()) {
						className.push(options.classNames.selectDay);
					}
				}
				
				if (fatalism > 0) {
					if (startDate.getFullYear() == date.year
							&& startDate.getMonth() == date.month - 1
							&& date.day < startDate.getDate()) {
						className.push(options.classNames.nodate);
					}
					var d3 = new Date(endDate.getFullYear(),
							endDate.getMonth(), endDate.getDate());
					if ((d1 - d3) / (1000 * 60 * 60 * 24) > -1) {
						className.push(options.classNames.nodate);
					} else {
						if ((d1 - d2) >= 0) {
							className.push(options.classNames.caldate);
						} else {
							className.push(options.classNames.nodate);
						}
					}
				}
				var nowDate = new Date(new Date().getFullYear(), new Date()
						.getMonth(), new Date().getDate()), d4 = new Date(
						date.year, date.month - 1, date.day), day = -1, festivalIndex = date.year
						+ "-"
						+ this.mend(date.month)
						+ "-"
						+ this.mend(date.day);
				day = (d4.getTime() - nowDate.getTime()) / (1000 * 3600 * 24);
				if (day >= 0 && day <= 2) {
					className.push(options.classNames.festival);
				} else {
					if (options.festival[festivalIndex]) {
						className.push(options.classNames.festival);
					}
				}
				if (options.isTodayClick) {
					if (cacheDate.getFullYear() == date.year
							&& cacheDate.getMonth() == date.month - 1
							&& cacheDate.getDate() == date.day) {
						className = [ options.classNames.nodate ];
					}
				}
			}
			return className.join(" ");
		},
		addDays : function(date, days) {
			var nd = new Date(date);
			nd = nd.valueOf();
			nd = nd + days * 24 * 60 * 60 * 1000;
			nd = new Date(nd);
			return nd;
		},
		getDatesByMonth : function(month, year) {
			var result = [], firstDate = new Date(year, month, 1), lastDate = new Date(
					year, month + 1, 0), lastOfMonth = lastDate.getDate(), curr = 1, line = 1;
			year = firstDate.getFullYear();
			month = firstDate.getMonth() + 1;
			for ( var i = 0; i < 6; i++) {
				result.push([ '', '', '', '', '', '', '' ]);
			}
			for ( var i = firstDate.getDay(); i < 7; i++) {
				result[0][i] = {
					year : year,
					month : month,
					day : curr++
				};
			}
			while (curr <= lastOfMonth) {
				for ( var i = 0; i < 7; i++) {
					if (curr > lastOfMonth) {
						break;
					}
					result[line][i] = {
						year : year,
						month : month,
						day : curr++
					};
				}
				line++;
			}
			return result;
		},
		changeMonth : function(op) {
			var options = this.options, date = options.date, mos = options.mos, cacheDate = this.cacheDate;
			options.date = new Date(date.getFullYear(), date.getMonth() + op, 1);
			if (options.control) {
				options.showPrev = options.date.getFullYear() > cacheDate
						.getFullYear() ? true
						: options.date.getMonth() === cacheDate.getMonth() ? false
								: true;
				options.showNext = mos <= 0 ? true
						: ((date.getFullYear() - cacheDate.getFullYear()) * 12
								+ date.getMonth() + op - cacheDate.getMonth() + 1) >= mos ? false
								: true;
			}
			this.render();
			this.bindEvent();
		},
		bindEvent : function() {
			this.monthChangeEvent();
			this.selectDate();
			this.moveEvent();
		},
		monthChangeEvent : function() {
			var that = this;
			this.warp.find("span.month-prev").bind("click", function() {
				that.changeMonth(-1);
			});
			this.warp.find("span.month-next").bind("click", function() {
				that.changeMonth(1);
			});
		},
		selectDate : function() {
			var that = this;
			this.warp
					.find("td div[class^=caldate]")
					.click(
							function() {
								that._weekIndex = parseInt($(this).parent("td")
										.attr("week"));
								if (!that.options.autoRender) {
									that.warp.detach();
									that.$trigger.blur();
									that.$trigger.val($(this).parent("td")
											.attr("date-map"));
									that.selectedDate = $(this).parent("td")
											.attr("date-map");
									if (typeof that.options.selectDateCallback === "function") {
										that.options.selectDateCallback(that);
									}
								} else {
									if (that.options.allowMutiSelected) {
										that.selectedDate = $(this)
												.parent("td").attr("date-map");
										var $box = $(this).parent("td");
										if ($box.hasClass("calSelected")) {
											$box.removeClass("calSelected");
											if (typeof that.options.cancelDateCallback === "function") {
												that.options
														.cancelDateCallback(that);
											}
										} else {
											$box.addClass("calSelected");
											if (typeof that.options.selectDateCallback === "function") {
												that.options
														.selectDateCallback(that);
											}
										}
									}
								}
							});
		},
		moveEvent : function() {
		},
		offset : function() {
			var $trigger = this.$trigger, offset = $trigger.offset(), left = offset.left, top = offset.top
					+ $trigger.outerHeight(true);
			return {
				left : left,
				top : top
			};
		},
		triggerBlur : function() {
			var that = this, options = that.options;
			$(document)
					.click(
							function(e) {
								var target = $(e.target);
								if (!target.hasClass(options.triggerClass)
										&& !target.parents().hasClass(
												options.warpClass)
										&& !target
												.hasClass(options.classNames.monthPrev)
										&& !target
												.hasClass(options.classNames.monthNext)) {
									that.warp.detach();
								}
							});
		}
	};
	Calendar.version = "1.0";
	Factory.defaults = {
		date : new Date(),
		target : "body",
		trigger : ".calendar",
		triggerEvent : "click",
		triggerClass : "calendar",
		warpClass : "ui-calendar",
		selectDateCallback : null,
		cancelDateCallback : null,
		completeCallback : null,
		autoRender : false,
		allowMutiSelected : false,
		sourceFn : null,
		tipText : "yyyy-mm-dd",
		frequent : false,
		titleTip : "{{year}}年{{month}}月",
		todayInfos : [ "", "", "" ],
		rangeColor : "#F0F5FB",
		hoverColor : "#d9e5f4",
		control : true,
		showPrev : false,
		showNext : true,
		startDelayDays : 0,
		fatalism : 0,
		mos : 6,
		offsetAmount : {
			left : 0,
			top : 0
		},
		isTodayClick : false,
		weeks : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
		classNames : {
			week : [ "sun", "mon", "tue", "wed", "thu", "fri", "sat" ],
			caldate : "caldate",
			nodate : "nodate",
			today : "today",
			hover : "hover",
			selectDay : "selectDay",
			interval : "interval",
			festival : "calfest",
			monthPrev : "month-prev",
			monthNext : "month-next"
		},
		cascade : {
			days : 1,
			trigger : ".calendar",
			isTodayClick : false
		},
		template : {
			warp : '<div class="ui-calendar"></div>',
			calControl : '<span class="month-prev" {{stylePrev}} title="上一月">‹</span><span class="month-next" {{styleNext}} title="下一月">›</span>',
			calWarp : '<div class="calwarp clearfix">{{content}}</div>',
			calMonth : '<div class="calmonth">{{content}}</div>',
			calTitle : '<div class="caltitle"><span class="mtitle">{{month}}</span></div>',
			calBody : '<div class="calbox">'
					+ '<i class="monthbg">{{month}}</i>'
					+ '<table cellspacing="0" cellpadding="0" border="0" class="caltable">'
					+ '<thead>' + '<tr>' + '<th class="sun">日</th>'
					+ '<th class="mon">一</th>' + '<th class="tue">二</th>'
					+ '<th class="wed">三</th>' + '<th class="thu">四</th>'
					+ '<th class="fri">五</th>' + '<th class="sat">六</th>'
					+ '</tr>' + '</thead>' + '<tbody>' + '{{date}}'
					+ '</tbody>' + '</table>' + '</div>',
			weekWarp : '<tr>{{week}}</tr>',
			day : '<td {{week}} {{dateMap}} >' + '<div {{className}}>'
					+ '<span class="calday">{{day}}</span>'
					+ '<span class="calinfo"></span>'
					+ '<span class="calprice"></span>'
					+ '<span class="calactive"></span>' + '</div>' + '</td>'
		},
		festival : {
			'2014-01-01' : '元旦',
			'2014-01-30' : '除夕',
			'2014-01-31' : '春节',
			'2014-02-14' : '元宵',
			'2014-04-05' : '清明',
			'2014-05-01' : '五一',
			'2014-06-02' : '端午',
			'2014-09-08' : '中秋',
			'2014-10-01' : '国庆'
		}
	};
	$.fn.calendar = pandora.calendar = Factory;
	global.pandora = pandora;
}(this, jQuery, this.pandora || {}));