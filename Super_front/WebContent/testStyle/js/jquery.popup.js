(function ($) {
    $.popup = {
        init: function (settings) {
            $(settings.trigger)[settings.eventType](function () {
                settings.lock ? $.popup._loadOverlay(settings) : null;
                if (settings.popupObj !== '') {
                    if ($(settings.popupObj).parent().attr("class") === 'js-popup-layer') {
                        $(settings.popupObj).parent().show();
                        return;
                    }
                }
                $.popup._construct(settings);
            });
        },
        _construct: function (settings) {
            ///<summary> _construct()方法 构建弹出层
            ///<<para> 参数描述 </para>
            ///</summary>
            var doc = document,
                width = settings.width,
                height = settings.height,
                top = (doc.documentElement.clientHeight - height) / 2,
                left = ($(document).width() - width) / 2,
                fixed = settings.fixed ? 'fixed' : 'absolute',
                popupLayer = $(doc.createElement("div")).addClass(settings.popupLayerClass).css({
                    "top": top, "left": left, width: width, height: height, position: fixed
                }),  //初始化最外层容器
                popupTitle = $(doc.createElement("div")).addClass(settings.popupTitleClass).css("width", "100%"),
                popupClose = $(doc.createElement("span")).addClass(settings.popupCloseClass),
                popupContent = null;
            if (settings.popupObj === '') {
                popupContent = $(doc.createElement("div")).html(settings.content);
                popupLayer.append(popupTitle.append(settings.title).append(popupClose)).append(popupContent).append("<iframe frameborder='0' border='0' style='width:100%;height:100%;position:absolute;z-index:-1;left:0;top:0;filter:Alpha(opacity=0);'></iframe>");
                $("body").append(popupLayer);
            } else {
                $("body").append(popupLayer);
                popupLayer.append(popupTitle.append(settings.title).append(popupClose));
                $(settings.popupObj).appendTo(popupLayer).show();
                popupLayer.append("<iframe frameborder='0' border='0' style='width:100%;height:100%;position:absolute;z-index:-1;left:0;top:0;filter:Alpha(opacity=0);'></iframe>");
            }
            popupClose.click(function () {
                $.popup.close(settings);
            });
        },
        _loadOverlay: function (settings) {
            ///<summary> _loadOverlay()方法 加载遮罩
            ///<<para> 参数描述 </para>
            ///</summary>
            if (this.overlay) {
                this.overlay.show();
                return;
            }
            this.overlay = $(document.createElement("div"));
            this.overlay.css({ position: "absolute", "z-index": 988, overflow:"hidden", left: 0, top: 0, zoom: 1, width: document.documentElement.clientWidth || document.body.clientWidth, height: $(document).height() }).appendTo($(document.body));
            this.overlay.append("<div  class=" + settings.popupOverlayClass + "></div><iframe frameborder='0' border='0' style='width:100%;height:100%;position:absolute;z-index:1;left:0;top:0;filter:Alpha(opacity=0);'></iframe>");
        },
        doresize: function () {
            ///<summary> doresize()方法 是实现弹出层拖动效果
            ///<<para> 参数描述 </para>
            ///</summary>
        },
        doEffects: function (settings, way) {
            ///<summary> doEffects()方法 是实现弹出层特效
            ///<<para> 参数描述 </para>
            ///</summary>
            var jqPopupLayer = $("." + settings.popupLayerClass);
            if (way === "close") {
                if (typeof settings.closeClick === 'function') {
                    var closeResult = settings.closeClick();
                    if(closeResult===false){
                    	return;
                    }
                }
                jqPopupLayer.hide();
                settings.lock ? this.overlay.hide() : null;
            }
        },
        close: function (settings) {
            this.doEffects(settings, "close");
        }
    };
    $.fn.popup = function (options) {
        ///<summary> popup()方法 参数初始化
        ///<para> 参数描述 
        ///trigger 触发的元素或id,必填参数
        ///content 消息内容
        ///title 标题
        ///width 外层宽度
        ///height 外层高度
        ///padding 内容与边界填充距离
        ///popupLayerClass 弹出层容器的class名称
        ///lock 是否锁屏
        ///fixed 是否固定定位
        ///useFx 是否特效
        ///isDrag 是否拖动
        ///eventType 触发事件类型
        ///zIndex 对话框叠加高度值
        ///</para>
        ///</summary>
        var settings = {
            trigger: '',
            content: '',
            title: '',
            width: 966,
            height: 469,
            padding: '10px',
            popupLayerClass: 'js-popup-layer',
            popupTitleClass: 'js-popup-title',
            popupCloseClass: 'js-popup-close',
            popupOverlayClass: 'js-overlay',
            lock: false,
            fixed: false,
            useFx: false,
            isDrag: false,
            eventType: 'click',
            popupObj: '',
            closeClick:null,
            zIndex: 9999
        };
        $.extend(true, settings, options || {});
        $.popup.init(settings);
    }
}(jQuery));