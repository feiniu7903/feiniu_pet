/*******************************************************************************
* Copyright (C) lvmama Corporation. All rights reserved. 
* 
* Version: 1.0 
* Author: Zhu.Xing.Chen 
* Email: zhux.chen@qq.com
* Create Date: 2013-12-04
* Description: pandora-autocomplete
*
* Revision History:
* Date         Author               Description
* 
* Demo:
*
*********************************************************************************/
(function (global, $, pandora, undefined) {
    "use strict"  // 严格模式

    if (pandora.autocomplete) {
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

    function Factory(options) {
        var options = options || {},
            defaults = Factory.defaults;

        // 合并默认配置
        for (var i in defaults) {

            if (options[i] === undefined) {
                options[i] = defaults[i];
            };

        };

        return new Autocomplete(options);
    }

    function Autocomplete(options) {
        this._init(options)
    }

    Autocomplete.prototype = {
        constructor: Autocomplete,

        _init: function (options) {
            this.options = options;
            this.warp = this.warp || $($.trim(this.options.template.warp));

            $("body").append(this.warp);
            this.bindEvent();
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
         * 过滤特殊字符
         */
        filterSpecialChar: function (str) {
            var options = this.options,
                reg = new RegExp(options.filterSpecialCharReg),
                result = "";

            for (var i = 0, len = sstr.length; i < len; i++) {
                result += s.substr(i, 1).replace(reg, options.filterRule);
            }

            return result;
        },

        reset: function () {
            var offset = this.offset();

            this.warp.css({
                position: "absolute",
                zIndex: 9999,
                left: offset.left,
                top: offset.top
            });
        },

        /**
         * 点击执行操作
         */
        execFn: function (obj) {
            var $trigger = $(this.options.trigger),
                map = obj.attr("data-map").split("|"),
                url = "";

            if (this.options.isTarget) {
                // 结合url 
                $trigger.val(obj.html());
                $(".J_searchId").val(map[0]);
                $(".J_longitude").val(map[1]);
                $(".J_latitude").val(map[2]);
                $(".J_keyword").val(map[3]);
                $(".J_parentId").val(map[4]);
                $(".J_searchType").val(map[5]);
                $(".J_ranktype").val('');
                url = this.options.targetUrl + obj.html()+ ".html";
                $trigger.parents("form[id=form]").attr("action", url);
                $("#form").submit();
            } else {
                $trigger.val(obj.html());
            }
        },

        createBody: function () {
            var that = this,
                options = that.options,
                keyword = $(options.trigger).val(),
                url = options.ajaxUrl + "?callback=?&keyword=" + encodeURIComponent(keyword),
                herderHtml = "",
                html = "";

            herderHtml = that.replaceWith(options.template.header, {
                keyword: keyword
            });

            $.getJSON(url, function (data) {

                if (data === undefined) {
                    that.warp.html("<div class='auto-error'>对不起 ,找不到:<span>" + keyword + "</span></div>").show();
                    return;
                }
                var i=0;
                data.placeListJson.forEach(function (arr) {
                    var icon = "",
                        className = "",
                        str = "";

                    if (arr.type == 2 || arr.type == 3) {
                        className = arr.type == 2 ? options.classNames.landmarks : options.classNames.hotel;

                        icon = that.replaceWith(options.template.icon, {
                            className: 'class="icon ' + className + '"'
                        });

                    }
                    if(i==0){
                    	$(".J_searchId").val(arr.id);
                        $(".J_longitude").val(arr.longitude);
                        $(".J_latitude").val(arr.latitude);
                        $(".J_keyword").val(arr.name);
                        $(".J_parentId").val(arr.parentId);
                        $(".J_searchType").val(arr.type);
                        $(".J_ranktype").val('');
                        $(".J_isAutocompleteFirstOne").val(1);
                    }
                    str = "" + arr.id + "|" + arr.longitude + "|" + arr.latitude + "|" + arr.name + "|" + arr.parentId + "|" + arr.type + "";

                    html += that.replaceWith(options.template.list, {
                        dateMap: 'data-map="' + str + '"',
                        icon : icon,
                        item: arr.name
                    });
                    i++;
                })

                html = that.replaceWith(that.options.template.body, {
                    content: html
                });

                html = herderHtml + html;
                that.warp.html(html).show();

            });

            if (typeof this.options.sourceFn === "function") {

                // this.fillData() 改写
                // 改变作用域 解决回调函数 this.warp 对象问题
                this.options.sourceFn.call(this);
            }

        },

        /**
         * 填充数据源
         * fillData 数据的变化多样化所以对方自定义
         */
        //fillData: function () {
        //    var that = this,
        //        html = "";

        //    $.ajax({
        //        type: "POST",
        //        url: "",
        //        success: function (data) {
        //            var len = data.length;

        //            if (len == 0) {
        //                that.warp.hide;
        //            } else {

        //                for (var i = 0; i < len; i++) {

        //                    html += that.replaceWith(that.options.template.list, {
        //                        dateMap: 'date-map="' + data[i].supplierId + '"',
        //                        item: data[i].supplierName
        //                    });

        //                }

        //                html = that.replaceWith(that.options.template.body, {
        //                    content: html
        //                });

        //                that.warp.html(html).show();
        //            }

        //        }
        //    });

        //},

        offset: function () {
            var $trigger = $(this.options.trigger),
                offset = $trigger.offset(),
                left = offset.left,
                top = offset.top + $trigger.outerHeight(true);

            return { left: left, top: top };
        },

        /**
         * 绑定事件
         */
        bindEvent: function () {
            this.keyEvent();
            this.liClick();
        },

        keyEvent: function () {
            var that = this,
                html = "";

            $(this.options.trigger).keyup(function (e) { // 按键起来

                $(that.options.topTrigger).hide(); // 隐藏热门推荐对话框

                if (/^(38|40|13|9)$/.test(e.keyCode)) {
                    return;
                }
               
                if ($.trim(this.value) === "") {
                    that.warp.hide();
                } else {
                    that.reset();
                    that.createBody();
                }

            }).blur(function () {
                setTimeout(function () {
                    that.warp.hide();
                },50)
            }).keydown(function (e) { // 按键按下
                var keyCode = e.keyCode,
                    li = that.warp.find("li.active");

                switch (keyCode) {
                    case 40:
                        li = li.length == 0 ? that.warp.find("li:first") : li.next();
                        li.addClass("active").siblings(".active").removeClass("active");
                        break;
                    case 38:
                        li = li.length == 0 ? that.warp.find("li:first") : li.prev();
                        li.addClass("active").siblings(".active").removeClass("active");
                        break;
                    case 13:
                        that.execFn(li.find("a"));
                        that.warp.hide();
                        break;
                }
            });
        },

        liClick: function () {
            var that = this;

            that.warp.delegate("li", "mousedown", function () {
                that.execFn($(this).find("a"));
            });

        }

    };

    Factory.defaults = {
        trigger: ".autocomplete", //对话框触发点/触发事件对象
        topTrigger: ".J_city", // 热门推荐对话框

        ajaxUrl: "", // 智能补全向后台发送的ajax url 
        sourceFn:null,// 数据源

        isFilterSpecialChar: true, // 是否过滤特殊字符
        filterSpecialCharReg: "[`~～•￥%\＠＄＾＆＿｛｝｜＂＜＞－＝［］＼＇．／•｛｝｜［］／＃％＊＋!@#$^&*=|{}':'\\[\\].<>/?~！@#￥……&*——|{}【】‘；：\"”“'。，、？+_《》]",
        filterRule:" ", // 过滤规则

        isTarget: false, // 点击是否直接打开
        targetUrl: "", // 打开的url

        classNames: {
            hover: "active", // 鼠标滑过li 样式
            icon: "icon",
            landmarks: "icon-xlocal", // 地标
            hotel: "icon-xhotel" // 酒店
        },

        template: {
            warp: '<div class="ui-autocomplete"></div>',
            header: '<div class="auto-tip">搜索“<b>{{keyword}}</b>”</div><div class="auto-box"></div>',
            body: '<ul>{{content}}</ul>',
            list: '<li {{className}}>{{icon}}<a href="javascript:;" {{dateMap}}>{{item}}</a></li>',
            icon: '<i {{className}}></i>'
        }
    };

    // 前端框架 pandora 对象 
    $.fn.autocomplete = pandora.autocomplete = Factory;

    global.pandora = pandora;
}(this, jQuery, this.pandora || {}));