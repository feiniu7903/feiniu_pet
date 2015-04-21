
// 弹窗全局配置
(function (d) {
    d["skin"] = "dialog-blue";
    d["mask"] = false;
    d["okClassName"] = "btn-ok";
    d["cancelClassName"] = "btn-cancel";
}(pandora.dialog.defaults));



$(function () {
    
    /*房态控制 滚动*/
    var $window = $(window);
    var xhousing = $("#xhousing");
    var currentHItem;
    
    xhousing.delegate(".J_itemauto","mouseenter",function(){
        currentHItem = this;
    });
    
    //滚动按钮标志
    xhousing.find(".xhprev").addClass("disabled")
    
    xhousing.delegate(".xhnext","click",function(){
        var ulist = $(currentHItem).find("ul.xulist");
        var litem = ulist.find("li").outerWidth(true);
        var startPos = parseInt(ulist.css("margin-left"));
        var xboxNum = parseInt(ulist.attr("data-num")) || 0;
        var endPos = ulist.width()-ulist.parent().width()+startPos;
        
        if(endPos < 20){
            $(this).addClass("disabled");
            return;
        }else if(startPos%litem == -1){
            $(this).prev().removeClass("disabled");
            ulist.animate({"margin-left": startPos-litem},300);
            ulist.attr("data-num",xboxNum+1)
        }
    })
    xhousing.delegate(".xhprev","click",function(){
        var ulist = $(currentHItem).find("ul.xulist");
        var litem = ulist.find("li").outerWidth(true);
        var startPos = parseInt(ulist.css("margin-left"));
        var xboxNum = parseInt(ulist.attr("data-num")) || 0;
        if(startPos > -10){
            $(this).addClass("disabled");
            return;
        }else if(startPos%litem == -1){
            $(this).next().removeClass("disabled");
            ulist.animate({"margin-left": startPos+litem},300)
            ulist.attr("data-num",xboxNum-1)
        }
    })
    
    xboxListItemAuto();
    
    //自动设定列表项宽度（房态控制）
    function xboxListItemAuto(){
        $("ul.xulist").each(function(){
            var xbox = $(this);
            var xboxwidth = ($window.width()>1000) ? $window.width()-143 :857;
            var xboxitemw = Math.ceil(0.25*(xboxwidth));
            
            xbox.parent().css("width",xboxwidth);
            $(".J_fixed").css("width",$(".xhtitle-outer").width());
            
            var xboxLiNum = xbox.find("li").length;
            var xboxNum = parseInt(xbox.attr("data-num")) || 0;;
            xbox.css({"width":(xboxitemw+1)*xboxLiNum,"margin-left": -xboxNum*(xboxitemw+1)-1}).find("li").css("width",xboxitemw);
            
            
            
        })
    }
    
    $window.resize(function(){
        xboxListItemAuto();
    })
})

$(function(){
    /*获取元素位置*/
    function elt_position(elt,position,fixed){
        if(elt.length){
            var fixed = (fixed ? $(document).scrollTop() :0);
            var elt_pos = elt.offset();
            switch(position){
                case "top":
                    return elt_pos.top - fixed;
                    break;
                case "bottom":
                    return elt_pos.top + elt.outerHeight(true);
                    break;
                case "left":
                    return elt_pos.left;
                    break;
                case "right":
                    return elt_pos.top + elt.outerWidth(true) -fixed;
                    break;
                default :
                    return elt_pos.top;
                ;
            }
        }else{
            return -1; //不存在该元素
        }
    }
    
    
    var J_itemauto = $(".J_itemauto");
    var obj= {};
    
    $(window).scroll(function(){
        var scrollTop = $(document).scrollTop();
        
        function currentItem(){
            for(i in obj){
                if(obj[i][0]< scrollTop && scrollTop < obj[i][1]){
                    return i;
                }
            }
        }
        
        function xhaffix(){
            var J_itemauto = $(".J_itemauto");
            J_itemauto.find(".J_fixed").removeClass("affix");
            J_itemauto.eq(currentItem()).find(".J_fixed").addClass("affix")
        }
        
        xhaffix();
        
    })
    
    
    
    $(".J_itemauto").each(function(i){
        obj[i] = [elt_position($(this),"top"),elt_position($(this),"bottom")-70]
        
    })
    
    
})













