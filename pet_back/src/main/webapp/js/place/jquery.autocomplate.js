(function ($) {
    var index = -1;
    var timeId;
    
    var cssOptions = {
        "border": "1px solid black",
        "background-color": "white",
        "height":"auto",
        "line-height":"20px",
        "overflow-y":"scroll",
        "position": "absolute"/*,
        "font": "normal normal lighter 14px 6px Times New Roman"*/
    };
    
    var defaults = {
        width: "auto",
        highlightColor: "#cecece",
        unhighlightColor: "#FFFFFF",
        css: cssOptions,
        dataType: "xml",
        paramName: "word",
        delay: 500,
        bindId:"place.parentPlaceId",
        max: 30
    };
    
    var keys = {
        UP: 38,
        DOWN: 40,
        DEL: 46,
        TAB: 9,
        ENTER: 13,
        ESC: 27,
        /*COMMA: 188,*/
        PAGEUP: 33,
        PAGEDOWN: 34,
        BACKSPACE: 8,
        A: 65,
        Z: 90
    };
    
    $.fn.extend({
        autocomplete: function (sUrl, settings) {
        
            sUrl = (typeof sUrl === "string") ? sUrl : "";
            var param = !this.attr("id") ? defaults.paramName : this.attr("id"); 
           
            settings = $.extend({}, defaults, {url: sUrl, paramName: param}, settings);
            var autoTip = this.autoTipTemplate(this, settings);
            $("body").append(autoTip);
            var $this = this;
            this.keyup(function (event) {
                $this.keyOperator(event, autoTip, settings);
            });
            /*$("input[type=button]").click(function () {
                $("#result").text("文本框中的【" + search.val() + "】被提交了！");
                $("#auto").hide();
                index = - 1;
            });*/
            return this.each(function () {
                $this.val();
            });
        },
        autoTipTemplate: function (input, settings) {
            var inputOffset = input.offset();
            
            var autoTip = $("<div/>").css(settings.css).hide()
            .css("top", inputOffset.top + input.height() + 5 + "px")
            .css("left", inputOffset.left + "px");
            
            var space = $.browser.mozilla ? 2 : 6;//兼容浏览器
            var tipWidth = (typeof settings.width === "string" && "auto") ? input.width() : settings.width;
            autoTip.width(tipWidth + space + "px");
            autoTip.height("300px");
            
            return autoTip;
        },
        select: function (target, index, settings, flag) {
            var color = flag ? settings.highlightColor : settings.unhighlightColor;
            target.children("div").eq(index).css("background-color", color);
        },
        keyOperator: function (event, autoTip, settings) {
            var evt = event || window.event;
            var autoNodes = autoTip.children("div");
            
            var kc = evt.keyCode;
            var $this = this;
            
            /* 当用户按下字母或是delete 或是退格键kc >= keys.A && kc <= keys.Z || */
            if (kc == keys.BACKSPACE || kc == keys.DEL||((kc >=32) && (kc <= 126))) {
                var wordText = this.val();
                if (wordText.length != 0) {
                    var param = {};
                    param[settings.paramName] = wordText;
                    clearTimeout(timeId);
                    timeId = setTimeout(function () {
                    	$.ajax({
            				type:"post",
            		        url:settings.url,
            		        data:"word="+wordText,
            		        error:function(){
            		            alert("与服务器交互错误!请稍候再试!");
            		        },
            		        success:function(data){

                                var wordObj = $(data);
                                if (settings.dataType == "xml") {
                                    var wordNodes = wordObj.find("word");
                                    autoTip.html("");
                                    wordNodes.each(function (i) {
                                    	//alert($(this).attr("placeId"));
                                        var divNode = $("<div>").attr("id", i);
                                        //将遍历的单词加入到创建的div中，然后把该div追加到auto中
                                        divNode.html($(this).text()).appendTo(autoTip);
                                        //鼠标已进去，添加高亮
                                        divNode.mousemove(function () {
                                            //如果已经存在高亮，去掉高亮改为白色
                                            if (index != -1) {
                                                autoTip.children("div").eq(index).css("background-color", settings.unhighlightColor);
                                            } 
                                            index = $(this).attr("id");
                                            $(this).css("background-color", settings.highlightColor);
                                        });
                                        //鼠标移出,取消高亮
                                        divNode.mouseout(function () {
                                            $(this).css("background-color", settings.unhighlightColor);
                                        });
                                        var placeId=$(this).attr("placeId");
                                        //点击高亮内容
                                        divNode.click(function () {
                                            $this.val($(this).text());
                                            $("input[name='"+settings.bindId+"']").val(placeId);
                                            index = -1;
                                            autoTip.hide();
                                        });
                                    });
                                
                                    if (wordNodes.length > 0) {
                                        autoTip.show();
                                    } else {
                                        autoTip.hide();
                                        index = -1;
                                    }
                                }
            		        }
            			});
                    	
                       // $.post(settings.url, param, function (data) {});
                    }, settings.delay);
                } else {
                	$("input[name='"+settings.bindId+"']").val("");
                    autoTip.hide();
                    index = -1;
                }
            } else if (kc == keys.UP || kc == keys.DOWN) {/*当用户按下上下键*/
                 if (kc == keys.UP) {//向上
                     if (index != -1) {
                         autoNodes.eq(index).css("background-color", settings.unhighlightColor);
                         index--;
                     } else {
                         index = autoNodes.length - 1;
                     }
                     if (index == -1) {
                         index = autoNodes.length - 1;
                     }
                     autoNodes.eq(index).css("background-color", settings.highlightColor);
                 } else {//向下
                     if (index != -1) {
                         autoNodes.eq(index).css("background-color", settings.unhighlightColor);
                     }
                     index++;
                     if (index == autoNodes.length) {
                         index = 0;
                     }
                     autoNodes.eq(index).css("background-color", settings.highlightColor);
                 }            
            } else if (kc == keys.PAGEUP || kc == keys.PAGEDOWN) {
                event.preventDefault();
                 if (kc == keys.PAGEUP) {
                     if (index != -1) {
                         autoNodes.eq(index).css("background-color", settings.unhighlightColor);
                     } 
                     if (autoNodes.length > 0) {
                         index = 0;
                         autoNodes.eq(0).css("background-color", settings.highlightColor);
                     }
                 } else {
                     if (index != -1) {
                         autoNodes.eq(index).css("background-color", settings.unhighlightColor);
                     }
                     index = autoNodes.length - 1;                         
                     autoNodes.eq(index).css("background-color", settings.highlightColor);
                 }            
            } else if (kc == keys.ENTER) {
                //回车键            
                //有高亮内容就补全信息
                if (index != -1) {
                    $this.val(autoNodes.eq(index).text());
                } else {//没有就隐藏
                    $("body").append($("<div/>").text("文本框中的【" + $this.val() + "】被提交了！"));
                    $this.get(0).blur();
                }
                autoTip.hide();
                index = -1;
            } else if (kc == keys.ESC) {
                autoTip.hide();
            }
        }
    });
})(jQuery);