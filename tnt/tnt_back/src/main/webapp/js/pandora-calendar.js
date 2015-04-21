/*******************************************************************************
* Copyright (C) lvmama Corporation. All rights reserved. 
* 
* Version: 1.0 
* Author: Zhu.Xing.Chen 
* Email: zhux.chen@qq.com
* Create Date: 2013-09-25
* Description: pandora-calendar
*
* Revision History:
* Date         Author               Description
* 
* Demo:
*
*********************************************************************************/
(function (global, $, pandora, undefined) {
    "use strict"  // 严格模式

    if (pandora.calendar) {
        return;
    }

    // ES5 15.4.4.18
    // http://es5.github.com/#x15.4.4.18
    // https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/array/forEach
    if (!Array.prototype.forEach) {

        Array.prototype.forEach = function (fn, scope) {
            var i, len;

            for (i = 0, len = this.length; i < len; ++i) {

                if (i in this) {
                    fn.call(scope, this[i], i, this);
                }

            }

        };

    }

    function toDate(obj) {

        if (typeof obj.date === "string") {
            var date = obj.date.split("-");

            obj.date = new Date(
                date[0],
                date[1] === "12" ? "11" : date[1] - 1,
                date[2]
            );
        }
        
    }

    function Factory(options) {
        var options = options || {},
            defaults = Factory.defaults;

        // 合并默认配置
        for (var i in defaults) {

            if (options[i] === undefined) {
                options[i] = defaults[i];
            };

        };

        toDate(options);

        return new Calendar(options);
    }

    function Calendar(options) {
        this._init(options)
    }

    Calendar.prototype = {
        constructor: Calendar,

        _init: function (options) {
            this.options = options;
            this.cacheDate = options.date;
            this.warp = this.warp || $($.trim(this.options.template.warp));
            this.warp.attr("data-render", options.autoRender); // 用于解决页面同时加载的2种模式

            this.loadCal();
            this.bindEvent();
        },

        /**
         * 加载日历显示的方式
         */
        loadCal: function () {
            var options = this.options,
                that = this;

            if (options.autoRender) {

                // 处理 autoRender 和 点击 render模式
                options.target.html("");
                options.target.append(this.warp);
                this.render();
            } else {

                $(options.trigger).bind(options.triggerEvent, function () {
                    options.target.append(that.warp);
                    that.$trigger = $(this);
                    var offset = that.offset();
                    
                    if ($(".ui-calendar[data-render=false]").length > 1) {
                        that.warp.detach();
                    }

                    that.warp.css({
                        position: "absolute",
                        zIndex: 9999,
                        left: offset.left,
                        top: offset.top
                    });

                    that.render();
                    that.bindEvent();
                    that.triggerBlur();
                });

            }

        },

        /**
        * 替换匹配的内容
        * @param {String} 参数 str
        * @param {Object} 参数 obj
        * @return {String}
        */
        replaceWith: function (str, obj) {

            for (var i in obj) {
                str = str.replace("{{" + i + "}}", obj[i]);
            }

            return str;
        },

        /**
         *  补零
         *  @param {Number} 月份、几号
         */
        mend: function (value) {
            return value.toString().length === 1 ? "0" + value : value;
        },

        /**
         * 绘制日历
         */
        render: function () {
            var options = this.options;

            this.warp.html("");
            this.warp.append(this.createHead());
            this.warp.append(this.createCalWarp());

            if (this.options.frequent) {
                this.warp.css("width", options.frequentW === "100%" ? options.frequentW : this.warp.find("div.calmonth").width());
            }

            if (typeof options.sourceFn === "function") {

                // this.fillData() 改写
                // 改变作用域 解决回调函数 this.warp 对象问题
                options.sourceFn.call(this);
            }

            this.warp.show();
        },
        
        /**
         * 填充数据源
         * fillData 数据的变化多样化所以对方自定义
         */
        //fillData: function () {
        //    var that = this,
        //        data = this.options.source;

        //    data.forEach(function (arr) {
        //        var $td = that.warp.find("td[date-map=" + arr.date + "]");
        //        $td.find("span").eq(1).html(arr.number);
        //        $td.find("span").eq(2).html("<dfn>¥" + arr.price + "</dfn>");
        //        $td.find("span").eq(3).html(arr.active);
        //    });

        //},

        /**
        * 绘制日历头部翻页按钮
        */
        createHead: function () {
            var html = "";

            html = this.replaceWith(this.options.template.calControl, {
                stylePrev: 'style="display:' + (this.options.control ? this.options.showPrev ? "black" : "none" : "black") + '"',
                styleNext: 'style="display:' + (this.options.control ? this.options.showNext ? "black" : "none" : "black") + '"'
            });

            return html;
        },

        /**
         *  填充日历
         */
        createCalWarp: function () {
             
           var that = this,
               options = that.options,
               month = options.date.getMonth(),
               year = options.date.getFullYear(),
               html = "";

            html += that.createBody(month, year);

            // 显示一个月/二个月
            if (!options.frequent) {
                html += that.createBody((month + 1) % 12, month === 11 ? year + 1 : year);
            }

            html = this.replaceWith(options.template.calWarp, {
                content: html
            });

            return html;
        },

        /**
         * 生成日历的日期部分
         */
        createBody: function (month, year) {
            var html = "",
                options = this.options;
           
            html += this.replaceWith(options.template.calTitle, {
                month: this.replaceWith(options.titleTip, {
                    year: year,
                    month: month + 1
                })
            });

            html += this.replaceWith(options.template.calBody, {
                month: month + 1,
                date: this.createDay(month, year)
            });

            html = this.replaceWith(options.template.calMonth, {
                content: html
            });

            return html;
        },

        /**
         * 生成 day html
         */
        createDay: function (month, year) {
            var html = "",
                day = "",
                that = this,
                options = that.options,
                dates = this.getDatesByMonth(month, year);

            dates.forEach(function (d) {

                d.forEach(function (d) {
                    day += that.replaceWith(options.template.day, {
                        week: d === "" ? "" : 'week="' + new Date(d.year, d.month - 1, d.day).getDay() + '"',
                        dateMap: d === "" ? "" : 'date-map="' + d.year + "-" + that.mend(d.month) + "-" + that.mend(d.day) + '"',
                        day: d === "" ? "" : d.day,
                        className: 'class="' + that.getClass(d) + '"'
                    });
                });

                html += that.replaceWith(options.template.weekWarp, {
                    week: day
                });

                day = "";
            });

            return html;
        },

        /**
		 * 计算日期的样式
		 * @param {Date} date 日期
		 * @returns {String} 计算得到的Class
		 */
        getClass: function (date) {
            var options = this.options,
                fatalism = options.fatalism,
                cacheDate = this.cacheDate,

                // 用于火车票起始时间
                startDate = this.addDays(cacheDate, options.startDelayDays),

                // 用于火车票结束时间
                endDate = this.addDays(new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()), fatalism), 
                className = [];

            if (date === "") {
                className.push(options.classNames.nodate);
            } else {
                
                var d1 = new Date(date.year, date.month - 1, date.day),
                    d2 = new Date(cacheDate.getFullYear(), cacheDate.getMonth(), cacheDate.getDate());

                // 过去日期
                if (fatalism == 0 && (d1 - d2) < 0) {
                    className.push(options.classNames.nodate);
                } else if (fatalism == 0) {
                    className.push(options.classNames.caldate);
                }

                // 当天日期
                if (cacheDate.getFullYear() == date.year && cacheDate.getMonth() == date.month - 1 && cacheDate.getDate() == date.day) {
                    className.push(options.classNames.today);
                }

                // 选中的颜色

                // 火车票逻辑
                if (fatalism > 0) {

                    if (startDate.getFullYear() == date.year && startDate.getMonth() == date.month - 1 && date.day < startDate.getDate()) {
                        className.push(options.classNames.nodate);
                    } 

                    var d3 = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate());

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

                // 节假日

            }

            return className.join(" ");
        },

        /**
         * 日期加上天数后的新日期
         */
        addDays: function (date, days) {
            var nd = new Date(date);
            nd = nd.valueOf();
            nd = nd + days * 24 * 60 * 60 * 1000;
            nd = new Date(nd);

            return nd;
        },

        /**
         * 按月份生成6x7日期数组
         * @param {Number} 月份 month (0 1 2 3 4 5 6 7 8 9 10 11)
         * @param {Number} 年份 year (2013)
         */
        getDatesByMonth: function (month, year) {
            var result = [],
                firstDate = new Date(year, month, 1),
                lastDate = new Date(year, month + 1, 0),
                lastOfMonth = lastDate.getDate(), // 获取当月天数
                curr = 1, // 第一行
                line = 1; // 后续行

            // 修正year和month
            year = firstDate.getFullYear();
            month = firstDate.getMonth() + 1;

            for (var i = 0; i < 6; i++) {
                result.push(['', '', '', '', '', '', '']);
            }

            //firstDate.getDay() 获取当月1号是星期几索引
            for (var i = firstDate.getDay() ; i < 7; i++) {
                result[0][i] = { year: year, month: month, day: curr++ };
            }

            while (curr <= lastOfMonth) {

                for (var i = 0; i < 7; i++) {

                    if (curr > lastOfMonth) {
                        break;
                    }

                    result[line][i] = { year: year, month: month, day: curr++ };
                }

                line++;
            }

            return result;
        },

        /**
         * 换月
         */
        changeMonth: function (op) {
            var options = this.options,
                date = options.date,
                mos = options.mos,
                cacheDate = this.cacheDate;

            options.date = new Date(
                date.getFullYear(),
                date.getMonth() + op,
                1
            );

            // 设置换月按钮显示
            if (options.control) {
                options.showPrev = date.getFullYear() > cacheDate.getFullYear() ?
                    true : date.getMonth() + op === cacheDate.getMonth() ? false : true;

                options.showNext = mos <= 0 ? true : ((date.getFullYear() - cacheDate.getFullYear()) * 12 +
                    date.getMonth() + op - cacheDate.getMonth() + 1) >= mos ? false : true;
            }

            this.render();
            this.bindEvent();
        },

        /**
        * 绑定事件
        */
        bindEvent: function () {
            this.monthChangeEvent();
            this.selectDate();
            //this.moveEvent();
        },

        /**
         * 月份切换事件
         */
        monthChangeEvent: function () {
            var that = this;

            this.warp.find("span.month-prev").bind("click", function () {
                that.changeMonth(-1);
            });

            this.warp.find("span.month-next").bind("click", function () {
                that.changeMonth(1);
            });
        },

        /**
         * 选中日期  
         */
        selectDate: function () {
            var that = this;

            this.warp.find("td div[class=caldate]").click(function () {

                // 待优化
                if (that.options.autoRender) {

                    if (typeof that.options.selectDateCallback === "function") {
                        that.options.selectDateCallback();
                    }

                } else {
                    that.$trigger.val($(this).parent("td").attr("date-map"));
                    that.warp.detach();
                }
                
            });

        },

        /**
		 * 移动事件
		 */
        moveEvent: function () {

        },

        /**
         *  设置 calendar 位置
         */
        offset: function () {
            var $trigger = this.$trigger,
                offset = $trigger.offset(),
                left = offset.left,
                top = offset.top + $trigger.height() + 1;

            return { left: left, top: top };
        },

        /**
         * 鼠标离开
         */
        triggerBlur: function () {
            var that = this,
                options = that.options;

            $(document).click(function (e) {
                var target = $(e.target);

                if (!target.hasClass(options.triggerClass) && !target.parents().hasClass(options.warpClass) &&
                    !target.hasClass(options.classNames.monthPrev) && !target.hasClass(options.classNames.monthNext)) {
                    that.warp.detach();
                }

            });

        }
    };

    // 日历插件版本
    Calendar.version = "1.0";

    Factory.defaults = {
        date: new Date(),
        target: $("body"),

        trigger: ".calendar", //对话框触发点/触发事件对象
        triggerEvent: "click",
        triggerClass: "calendar",
        warpClass: "ui-calendar",

        selectDateCallback: null,

        autoRender: false,

        sourceFn: null, // 数据源

        tipText: "yyyy-mm-dd", //文本日期格式

        frequent: false, // 单月显示
        frequentW:"100%",
        titleTip: "{{year}}年{{month}}月",

        rangeColor: "#F0F5FB",
        hoverColor: "#d9e5f4",

        control: true, // 控制翻页按钮是否显示
        showPrev: false,
        showNext: true,

        startDelayDays: 0, // 在开始的基础上叠加天数
        fatalism: 0, // 天数
        mos: 6, //月份

        classNames: {
            week: ["sun", "mon", "tue", "wed", "thu", "fri", "sat"],
            caldate: "caldate",
            nodate: "nodate", // 禁用和空
            today: "today", // 今天
            hover: "hover", // 鼠标滑过效果
            festival: "calfest", // 节日
            monthPrev: "month-prev",
            monthNext: "month-next"
        },

        template: {
            warp: '<div class="ui-calendar"></div>',
            calControl: '<span class="month-prev" {{stylePrev}} title="上一月">‹</span><span class="month-next" {{styleNext}} title="下一月">›</span>',
            calWarp: '<div class="calwarp clearfix">{{content}}</div>',
            calMonth: '<div class="calmonth">{{content}}</div>',
            calTitle: '<div class="caltitle"><span class="mtitle">{{month}}</span></div>',
            calBody: '<div class="calbox">' +
                        '<i class="monthbg">{{month}}</i>' +
                        '<table cellspacing="0" cellpadding="0" border="0" class="caltable">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th class="sun">日</th>' +
                                    '<th class="mon">一</th>' +
                                    '<th class="tue">二</th>' +
                                    '<th class="wed">三</th>' +
                                    '<th class="thu">四</th>' +
                                    '<th class="fri">五</th>' +
                                    '<th class="sat">六</th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody>' +
                                '{{date}}' +
                            '</tbody>' +
                        '</table>' +
                    '</div>',
            weekWarp: '<tr>{{week}}</tr>',
            day: '<td {{week}} {{dateMap}} >' +
                    '<div {{className}}>' +
                        '<span class="calday">{{day}}</span>' +
                        '<span class="calinfo"></span>' +
                        '<span class="calprice"></span>' +
                        '<span class="calactive"></span>' +
                    '</div>' +
                 '</td>'
        }
    };

    // 前端框架 pandora 对象 
    $.fn.calendar = pandora.calendar = Factory;

    global.pandora = pandora;
}(this, jQuery, this.pandora || {}));