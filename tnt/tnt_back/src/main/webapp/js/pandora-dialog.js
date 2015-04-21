(function (global, $, undefined) {
    "use strict" // 严格模式

    if (global.pandora && global.pandora.dialog) {
        return;
    }

    var universe = null,
        count = 0,
        expando = "dialog" + (+new Date),
        isIE6 = window.VBArray && !window.XMLHttpRequest,

        // dialog 自适应大小
        data = {},
        timer = null,

        isArray = function (obj) {

            // ECMAScript5 isArray 方法 兼容不支持的浏览器
            if (typeof Array.isArray === "undefined") {
                return Object.prototype.toString.call(obj) === "[object Array]";
            }

            return Array.isArray(obj);
        };

    // 获取 iframe 内部的高度
    function getIframeHeight(iframe) {
        var D = iframe[0].contentWindow.document;
        if (D.body.scrollHeight && D.documentElement.scrollHeight) {
            return Math.min(D.body.scrollHeight, D.documentElement.scrollHeight);
        } else if (D.documentElement.scrollHeight) {
            return D.documentElement.scrollHeight;
        } else if (D.body.scrollHeight) {
            return D.body.scrollHeight;
        }
    }

    /**
     * 工厂模式 + 单体模式
     * 用于创建 dialog 对象
     */
    function Factory(config, ok, cancel) {
        var config = config || {},
            defaults = Factory.defaults,
            list = [];

        if (typeof config === "string") {
            config = {
                content: config
            }
        }

        // 合并默认配置
        for (var i in defaults) {
            if (config[i] === undefined) {
                config[i] = defaults[i];
            };
        };

        config.id = expando + count;

        // 针对 fixed 属性支持不好，禁用此特性
        if (isIE6) {
            config.fixed = false;
        }

        if (!config.button || !config.button.push) {
            config.button = [];
        };

        // 确定按钮
        if (ok !== undefined) {
            config.ok = ok;
        };

        if (config.ok) {
            config.button.push({
                className: config.okClassName,
                value: config.okValue,
                callback: config.ok
            });
        };

        // 取消按钮
        if (cancel !== undefined) {
            config.cancel = cancel;
        };

        if (config.cancel) {
            config.button.push({
                className: config.cancelClassName,
                value: config.cancelValue,
                callback: config.cancel
            });
        };

        count++;

        return Dialog.list[config.id] = universe ? universe._init(config) : new Dialog(config);
    }

    function Dialog(config) {
        this._init(config);
    }

    Dialog.prototype = {
        constructor: Dialog,

        _init: function (config) {
            var wrap = null,
                that = this;

            that.config = config;

            // wrap 是一个 $(dialog) 对象
            that.wrap = wrap = that.wrap || $($.trim(that._getTemplate(config)));

            if (universe === null) {
                $("body").prepend(that.wrap);
            }

            if (config.wrapClass !== "") {
                wrap.addClass(config.wrapClass);
            }

            /* 皮肤 */
            wrap.addClass(config.skin);
            
            that.button.apply(that, config.button);
            that.title(config.title);
            that.content(config.content);
            that.size(config.width, config.height);

            // hack
            that._zIndex();
            //that.reset();
            that.time(config.time);
            

            if (config.mask === true) {
                that._mask(config);
            }
            
            that.wrap.show();
            that.reset(); // 解决对象有内容而没添加到页面而 height() 为 0 bug
            that._bindEvent(config);

            if (config.drag === true) {
                that._drag();
            }

            // 模态窗口 弹出子的模态窗口
            universe = null;

            if (typeof config.initialize === "function") {
                config.initialize.call(this);
            }

            return that;
        },

        /**
         * 获取 Dialog 模板
         * REVIEW
         * @param {Object} 获取模板的名称、类型 例如：template-dialog , default
         */
        _getTemplate: function (config) {
            //var template = "";

            //$.ajax({
            //    type: "GET",
            //    async: false,
            //    url: config.templateUrl,
            //    dataType: "html",
            //
            //    success: function (data) {
            //        template = $(data).find("#" + config.templateNmae + " > div[data-template=" + config.templateType + "]").html();
            //    },
            //
            //    error: function (e) {
            //
            //    }
            //
            //});

            return template;
        },

        // 创建iframe
        _createIframe: function (url) {
            var that = this;
            this.iframe = $('<iframe scrolling="auto" frameborder="0" marginwidth="0" marginheight="0" width="100%" height="100%"></iframe>');

            // 开始请求iframe
            this.iframe.attr("src", url);
            
            this.iframe.one("load", function () {

                // 如果 dialog 已经隐藏了，就不需要触发 onload
                if (!that.wrap.is(":visible")) {
                    return;
                }

                clearInterval(that._interval);
                that._interval = setInterval(function () {
                    that._syncWH();
                }, 300);

            });

            return this.iframe;
        },

        _syncWH: function () {
            var h,
                w,
                wh = {},
                wrap = this.wrap,
                $dialogBody = wrap.find("div.dialog-body"),
                padding = parseInt($dialogBody.css("paddingLeft"), 10) + parseInt($dialogBody.css("paddingRight"), 10),
                margin = parseInt($dialogBody.css("marginLeft"), 10) + parseInt($dialogBody.css("marginRight"), 10);

            try {
                this._errCount = 0;
                h = getIframeHeight(this.iframe);
            } catch (err) {
                // 页面跳转也会抛错，最多失败6次
                this._errCount = (this._errCount || 0) + 1;

                if (this._errCount >= 6) {
                    // 获取失败则给默认高度 300px
                    // 跨域会抛错进入这个流程
                    h = this.config.initialHeight;
                    clearInterval(this._interval);
                    delete this._interval;
                }

            }

            // 去除dialog 固定高度
            wrap.css("height", "auto");
            wrap.find("div.dialog-content").css({ "height": h });

            clearInterval(this._interval);
            delete this._interval;
        },

        /**
         * 用于计算当dialog高度固定值的时候内容自适应
         */
        _bodyWH: function () {
            var h,
                wrap = this.wrap,
                $dialogBody = wrap.find("div.dialog-body"),
                $dialogHeader = wrap.find("div.dialog-header"),
                $dialogFooter = wrap.find("div.dialog-footer"),
                bpm = this._gaugeValue($dialogBody).y,
                hh = $dialogHeader.height() + this._gaugeValue($dialogHeader).y,
                fh = $dialogFooter.height() + this._gaugeValue($dialogFooter).y;

            h = parseInt(this.config.height, 10) - bpm - hh - fh;
            wrap.find("div.dialog-body").css({ "height": h, "overflow": "auto" });
        },

        /**
         * 计算边距值
         * param {$} jQuery 对象
         */
        _gaugeValue: function (obj) {
            var val = {};

            val.y = parseInt(obj.css("paddingTop"), 10) + parseInt(obj.css("paddingBottom"), 10) +
                    parseInt(obj.css("marginTop"), 10) + parseInt(obj.css("marginBottom"), 10);

            val.x = parseInt(obj.css("paddingLeft"), 10) + parseInt(obj.css("paddingRight"), 10) +
                    parseInt(obj.css("marginLeft"), 10) + parseInt(obj.css("marginRight"), 10);

            return val;
        },

        /**
         * 遮罩层
         */
        _mask: function (config) {

            this._mask = function () {
                $("[data-mask=overlay]").show();
            }
            
            // 有待完善 HACK
            if ($("[data-mask=overlay]").size() === 0) {
                var div = document.createElement('div');

                $(div).attr("data-mask", "overlay").addClass(config.maskClass)
                    .css("zIndex", config.zIndex - 1);

                if (isIE6) {
                    $(div).css({
                        position: 'absolute',
                        width: $(window).width() + 'px',
                        height: $(document).height() + 'px'
                    });
                }

                $("body").prepend(div);
            }

            this._ismask = true;
        },

        /**
         * 隐藏遮罩层
         * REVIEW
         */
        _unmask: function () {
            var list = Dialog.list,
                count = 0;

            for (var i in list) {
                
                if (list.hasOwnProperty(i)) {
                    
                    if(list[i]._ismask === true){
                        count++;
                    }

                }
            }

            if (this._ismask === true && count === 1) {
                $("[data-mask=overlay]").hide();
            }

            return this;
        },

        /**
         * 拖动窗口
         * 
         */
        _drag: function () {
            var disX = 0,
                disY = 0,
                wrap = this.wrap[0],
                handle = this.wrap.find("div.dialog-header")[0];

            handle.style.cursor = "move";
            handle.onmousedown = function (event) {
                var event = event || window.event;
                disX = event.clientX - wrap.offsetLeft;
                disY = event.clientY - wrap.offsetTop;

                document.onmousemove = function (event) {
                    var event = event || window.event,
                        iL = event.clientX - disX,
                        iT = event.clientY - disY,
                        maxL = document.documentElement.clientWidth - wrap.offsetWidth,
                        maxT = document.documentElement.clientHeight - wrap.offsetHeight;

                    //iL <= 0 && (iL = 0);
                    //iT <= 0 && (iT = 0);
                    //iL >= maxL && (iL = maxL);
                    //iT >= maxT && (iT = maxT);

                    wrap.style.left = iL + "px";
                    wrap.style.top = iT + "px";

                    return false
                };

                document.onmouseup = function () {
                    document.onmousemove = null;
                    document.onmouseup = null;

                    //http://baike.baidu.com/link?url=aNx0f9xGYhqWVXmOIBPSN54rVDlw7xj-YQLKcgpjQ01v6xCYWYmd7SJMwxvpkwgitEG6sP4z1j6FzZh5Ebr7Wq
                    //该函数从当前线程中的窗口释放鼠标捕获，并恢复通常的鼠标输入处理
                    if (this.releaseCapture) {
                        this.releaseCapture();
                    }
                };

                //http://baike.baidu.com/link?url=-soUC66QKQKfSk7yEPTiEEI7tYgDaKgOEY0t7QvkDvSX6_YdccDpFNXa5Q8eE_8vkiYtk42RmXaY4QMROT50iK
                //该函数在属于当前线程的指定窗口里设置鼠标捕获
                if (this.setCapture) {
                    this.setCapture();
                }

                return false
            };
        },

        /**
         * 置顶对话框
         */
        _zIndex:function(){
            var index = Factory.defaults.zIndex++;
            this.wrap.css("zIndex", index);
        },

        /**
         * 绑定事件 采用事件冒泡
         */
        _bindEvent: function (config) {
            var that = this;

            // 事件监听 (事件冒泡)
            that.wrap.bind("click", function (e) {
                var target = $(e.target),
                    index = 0;

                if (target.attr("data-dismiss") === "dialog") {
                    that.close(this);
                    return false;
                } else {

                    if (target.parent().is("[data-btn=btns]")) { // target[0][expando + "callback"]
                        index = target.index();
                        that._execCallback(index);
                    }
                    
                }
            });

           that._loopy();
        },

        // 监听窗口大小
        _loopy: function () {
            var that = this;
            
            timer = setTimeout(function () {
                clearTimeout(timer);

                var elem = that.wrap,
                    width = elem.width(),
                    height = elem.height();

                // hack data 全局变量 
                if (width !== data.w || height !== data.h) {

          
                    if (that.config.dialogAuto) {

                        that.wrap.css({
                            "position": "absolute",
                            "top": ($(window).scrollTop() + that.config.dialogAutoTop) + "px",
                            "left": parseInt(($(window).width() - width) / 2, 10) + "px"
                        });

                        data.w = width;
                        data.h = height;
                    } else {
                        that.reset();
                    }

                }

                that._loopy();
            }, 150);
        },

        /**
         * 卸载事件
         * REVIEW
         */
        _unbindEvent: function () {
            this.wrap.unbind("click");
        },

        /**
         * 执行回调函数 
         */
        _execCallback: function (index) {
            var fns = this.config.button,
                fn = fns[index].callback;

            return typeof fn !== "function" || fn.call(this) !== false ? this.close() : this;
        },


        /**
         * 设置标题
         * @param {String}	标题内容
         * TODO
         */
        title: function (title) {
            this.wrap.find("div.dialog-header").html(title);

            return this;
        },

        /**
         * 设置内容
         * TODO
         * @param {String, HTMLElement} 内容 (可选)
         */
        content: function (content) {
            var rulReg = /^(https?:\/\/|\/|\.\/|\.\.\/)/,
                that = this,
                prev, next, parent, display;

            if (rulReg.test(content)) {

                // 调用创建iframe 
                content = this._createIframe(content);
            }

            if (typeof content !== "string") {

                if ($(content)[0].nodeType === 1) {
                    content = $(content)[0];
                    display = content.style.display;
                    prev = content.previousSibling;
                    next = content.nextSibling;
                    parent = content.parentNode;

                    this._elemBack = function () {

                        if (prev && prev.parentNode) {
                            prev.parentNode.insertBefore(content, prev.nextSibling);
                        } else if (next && next.parentNode) {
                            next.parentNode.insertBefore(content, next);
                        } else if (parent) {
                            parent.appendChild(content);
                        };

                        content.style.display = display;
                        that._elemBack = null;
                    };

                    $(content).show();
                }
            }

            this.wrap.find("div.dialog-content").html(content);

            return this;
        },

        /**
         * 重置 dialog 位置 例如窗口发生变化的时候
         * hack
         */
        reset: function () {
            var wrap = this.wrap,
                wh = $(window).height(),
                oh = wrap.height(),
                ow = wrap.width(),
                top,
                left;
                
            if (this.config.dialogAuto) {
                wrap.css({
                    "position": "absolute",
                    "top": ($(window).scrollTop() + this.config.dialogAutoTop) + "px",
                    "left": parseInt(($(window).width() - ow) / 2, 10) + "px"
                });
                
                return ;
            }

            // dialog 给了固定值
            //if (this.config.height !== "" && this.config.height !== "auto") {
            //    this._bodyWH();
            //}

            if (wh - oh >= 50) {
                wrap.css("position", this.config.fixed ? "fixed" : "absolute");
                this.offsets();
            } else {
                top = $(window).scrollTop() + 10;
                left = ($(window).width() - ow) / 2,

                wrap.css({
                    "position": "absolute",
                    "top": parseInt(top, 10) + "px",
                    "left": parseInt(left, 10) + "px"
                });
            }

            data.w = ow;
            data.h = oh;
        },

        /**
         * hack
         */
        loading: function (content) {
            var html = '<div style="text-align:center">' +
                       '<img width="46" height="46" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/loadingGIF46px.gif"' +
                       'alt="loading..." ></div>';
            html = content ? content : html;

            this.content(html);

            return this;
        },

        /**
         * 尺寸
         * @param {Number, String} 宽度
         * @param {Number, String} 高度
         */
        size: function (width, height) {

            if (typeof width === "number") {
                width += "px";
            }

            if (typeof height === "number") {
                height += "px";
            }

            this.wrap.css({
                "width": width,
                "height": height
            });

            return this;
        },

        /**
         * 自定义按钮
         * REVIEW
         */
        button: function () {
            var ags = [].slice.call(arguments),
                len = ags.length,
                i = 0,
                j = 0,
                classNameLen = 0,
                className = "",
                button = null,
                buttonAgs = null;

            for (; i < len; i++) {
                buttonAgs = ags[i];
                button = document.createElement("button");

                if (isArray(buttonAgs.className)) {
                    for (classNameLen = buttonAgs.className.length; j < classNameLen; j++) {
                        className += buttonAgs.className[j] + " ";
                    }
                } else {
                    className = buttonAgs.className === undefined ? "" : buttonAgs.className;
                }

                //button[expando + "callback"] = expando; 
                $(button).attr({
                    "class": "pbtn pbtn-small " + className + ""
                }).html(buttonAgs.value);

                $(this.wrap).find("[data-btn=btns]").append(button);
            }

            return this;
        },

        /**
         * 设置 dialog 位置
         * TODO 待修改
         * @param {Number} 
         */
        offsets: function (x, y) {
            var wrap = this.wrap,
                ww = $(window).width(),
                wh = $(window).height(),
                ow = wrap.width(),
                oh = wrap.height(),
                left = (ww - ow) / 2,
                top = (wh - oh) * 382 / 1000;
            
            if (isIE6){
                top += $(window).scrollTop();
            }
            
            wrap.css({
                "top": parseInt(top) + "px",
                "left": parseInt(left) + "px"
            });

            return this;
        },
        
        /** 
         * 定时关闭 
         * @param {Number} 单位毫秒, 无参数则停止计时器 
         */ 
        time: function (time) { 
            var that = this, 
            timer = this._timer; 

            if (timer) { 
                clearTimeout(timer); 
            } 

            if (time) { 

                // 卸载所有按钮事件及清空所有按钮 
                that._unbindEvent(); 
                $(that.wrap).find("[data-btn=btns]").html(""); 

                this._timer = setTimeout(function () { 
                    that.close(); 
                }, time); 

            }; 

            return this; 
        },

        /**
         * 关闭
         * TODO
         */
        close: function () {
            var wrap = this.wrap,
                config = this.config,
                beforeunload = config.beforeunload;

            if (beforeunload && beforeunload.call(this) === false) {
                return this;
            };

            this._unmask();
            this._unbindEvent();

            // 清除定时器
            this.time(); 
            delete Dialog.list[config.id];

            // 清除dialog自适应大小定时器
            clearTimeout(timer);

            if (this._elemBack) {
                this._elemBack();
            };

            if (universe !== null) {
                wrap.remove();
            } else {
                universe = this;

                wrap.attr("style","");
                wrap.hide();
                wrap.find("div.dialog-body").attr("style", "");
                wrap.attr("class", "dialog");
                wrap.find("[data-title=title]").html("");
                wrap.find("[data-content=content]").html("");
                wrap.find("[data-btn=btns]").html("");
            }
            
            return this;
        },


        /**
         * 对外提供可扩展 Dialog 对象属性、方法的接口
         * REVIEW
         * @param {Object} 
         */
        extend: function (object) {
            var fn = Dialog.prototype;
            for (var i in object) {
                fn[i] = object[i];
            }

            return this;
        }
    };

    // Dialog 对象列表
    Dialog.list = {};
    Dialog.version = "1.0";

    // 浏览器窗口改变后重置对话框位置
    $(window).bind('resize', function () {
        var dialogs = Dialog.list;
        for (var id in dialogs) {
            dialogs[id].reset();
        };
    });

    Factory.defaults = {
        templateNmae: "template-dialog",
        templateType: "default",
        templateUrl: "/pandora/docs/assets/js/modules/template.html",

        button: null, // 按钮数组 参数{value, className, callback} 按钮名称 样式名 回调函数 ----此具体使用请参考示例341 按钮组的调用 
        fixed: true, // 跟随
        mask: true, // 遮罩
        drag: false, // 拖动 

        // 对于dialog 内容切换
        dialogAuto: false,
        dialogAutoTop: 60,

        // iframe 默认给个初始高度300
        initialHeight: "300px",

        initialize: null, // 对话框初始化后执行的函数
        beforeunload: null, // 对话框关闭前执行的函数

        ok: null,   // 确定按钮回调函数
        cancel: null, // 取消按钮回调函数
        okValue: "确定", // 确定按钮文本
        cancelValue: "取消", // 取消按钮文本
        okClassName: "",
        cancelClassName: "",

        content: "",
        title: "消息提醒",
        
        time: null, //自动消失定时器

        width: "",  // 默认宽度400，由css控制
        height: "",

        skin: "dialog-default",   // 皮肤
        wrapClass: "", // dialog 规格 通过className 名 控制 可选参数：dialog-mini dialog-middle dialog-big dialog-large
        maskClass: "overlay",
        zIndex: 4000
    };
    
    var template = '<div class="dialog">'
                 + '    <div class="dialog-inner clearfix">'
                 + '        <a class="dialog-close" data-dismiss="dialog">&times;</a>'
                 + '        <div class="dialog-header" data-title="title"></div>'
                 + '        <div class="dialog-body">'
                 + '            <div class="dialog-content clearfix" data-content="content">'
                 + '            </div>'
                 + '        </div>'
                 + '        <div class="dialog-footer" data-btn="btns" >'
                 + '        </div>'
                 + '    </div>'
                 + '</div>';

    var pandora = {};

    // 扩展一些常用的静态方法
    $.alert = pandora.alert = function (content, callback) {
        return Factory({
            fixed: true,
            mask: true,
            wrapClass: "dialog-mini",
            content: content,
            ok: true,
            beforeunload: callback
        });
    }
    
    // 定时信息 
    $.msg = pandora.msg = function (content, time) {
        return Factory({
            fixed: true,
            mask: false,
            wrapClass: "dialog-msg",
            content: content,
            time: time ? time : 2000
        });
    }

    $.confirm = pandora.confirm = function (content, ok, cancel) {
        return Factory({
            fixed: true,
            lock: true,
            content: content,
            ok: ok ? ok : true,
            cancel: cancel ? cancel : true
        });
    };
    
    $.loading = pandora.loading = function (content, callback) {
        return Factory({
            fixed: true,
            lock: true,
            mask: true,
            wrapClass: "dialog-loading",
            content: '<p><img src="//pic.lvmama.com/img/common/loading.gif" alt="loading" /></p>' + content,
            initialize: callback
        });
    };
    
    // 调用方式采用 $("").dialog() 和 pandora.dialog() 两种方式
    $.dialog = $.fn.dialog = pandora.dialog = Factory;
    global.pandora = pandora;
}(this, jQuery));