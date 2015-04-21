$.fn.lvtip = function(option){
    var opt = {
        templete : 1, 
        place : "bottom", 
        offsetX : 0, 
        offsetY : 0, 
        trigger : "mouseenter",  // other is "click"
        event : "bind",  // other is "live" live方法有问题，后添加元素ui不被调用
        hovershow : undefined  //time is 200
    }
    opt = $.extend(opt,option);
    var templeteStr = "";
    switch(opt.templete){
        case 1:
            templeteStr = '<div class="tooltip tooltip1" style="display:none;" id="lvtip'+opt.templete+'">'+
                        '    <div class="tooltip-arrow"></div>'+
                        '       <div class="tooltip-outer">'+
                        '       <div class="tooltip-shadow"></div>'+
                        '       <div class="tooltip-inner">'+
                        '           <h5 class="tooltip-title"></h5>'+
                        '           <div class="tooltip-content">'+
                        '               <p></p>'+
                        '           </div>'+
                        '           </div>'+
                        '       </div>'+
                        '   </div>';
            break;
        case 2:
            templeteStr = '<div class="tooltip tooltip2" style="display:none;" id="lvtip'+opt.templete+'">'+
                        '       <div class="tooltip2-arrow"><em></em><i></i></div>'+
                        '       <div class="tooltip-outer">'+
                        '           <h5 class="tooltip-title"></h5>'+
                        '           <div class="tooltip-content">'+
                        '               <p></p>'+
                        '           </div>'+
                        '       </div>'+
                        '   </div>';
            break;
        case 3:
            templeteStr = '<div class="tooltip tooltip2" style="display:none;" id="lvtip' + opt.templete + '">' +
                        '       <div class="tooltip-outer">' +
                        '           <h5 class="tooltip-title"></h5>' +
                        '           <div class="tooltip-content">' +
                        '               <p></p>' +
                        '           </div>' +
                        '       </div>' +
                        '   </div>';
            break;
        default :

            break;
    }
    if($("#lvtip"+opt.templete).length==0){
        $("body").append(templeteStr);
    }
    $(this)[opt.event](opt.trigger,function(){
        var title = $(this).attr("tip-title");
        var content = $(this).attr("tip-content");
        var obj = $("#lvtip"+opt.templete);
        clearTimeout(obj.data("timeId"));
        if(title){
            obj.find(".tooltip-title").html(title).show();
        }else{
            obj.find(".tooltip-title").html("").hide();
        }
        if(content){
            obj.find(".tooltip-content p").html(content);
        }else{
            obj.hide();
            return;
        }
        var left = $(this).offset().left;
        var top = $(this).offset().top;
        switch(opt.place){
            case "top":
                left-=(obj.outerWidth()-$(this).outerWidth())/2;
                top-=$(obj).outerHeight();
                break;
            case "bottom":
                left-=(obj.outerWidth()-$(this).outerWidth())/2;
                top+=$(this).outerHeight();
                break;
            case "left":
                left-=$(obj).outerWidth();
                top-=($(obj).outerHeight()-$(this).outerHeight())/2;
                break;
            case "right":
                left+=$(this).outerWidth();
                top-=($(obj).outerHeight()-$(this).outerHeight())/2;
                break;
            case "top-left":
                top-=$(obj).outerHeight();
                break;
            case "top-right":
                left-=$(obj).outerWidth()-$(this).outerWidth();
                top-=$(obj).outerHeight();
                break;
            case "bottom-left":
                top+=$(this).outerHeight();
                break;
            case "bottom-right":
                left-=$(obj).outerWidth()-$(this).outerWidth();
                top+=$(this).outerHeight();
                break;
        }
        obj.addClass(opt.place).css({
            left : left + opt.offsetX,
            top : top + opt.offsetY
        }).fadeIn(200);
    })[opt.event]("mouseleave",function(){
        this.timeId = setTimeout(function(){
            $("#lvtip"+opt.templete).hide();
        },opt.hovershow);
        $("#lvtip"+opt.templete).data("timeId",this.timeId);
    });
    var obj = this;
    $("#lvtip"+opt.templete).mouseenter(function(){
        clearTimeout(obj.timeId);
    }).mouseleave(function(){
        $(this).hide();
    });
}