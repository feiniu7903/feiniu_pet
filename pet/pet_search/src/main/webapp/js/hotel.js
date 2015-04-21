$(function(){
    //图片延迟加载
    $("img.lazy").lazyload({ 
        threshold : 310,
        failure_limit : 3
    });
    
    //读取热门城市
    var url = "http://www.lvmama.com/dest/newplace/commAction!getRecommendInfoWithIdJson.do?callback=?";
    var thisObj = this;
    $.getJSON(url, {recommendBlockId : 7142}, function(txt) {
        var arrayRm = txt.rm,
        html = "";
        for(var i=0;i<arrayRm.length;i++){
            html += '<li><a href="javascript:;">'+arrayRm[i].name+'</a></li>';
        }
        $('#js-city-list').append(html);
    });
    
    //浏览历史延迟加载
    var load_history_img = function(){ 
        $("img.lazy-history").each(function(i,n){
            $(n).attr("src",$(n).attr("data-original"));
        });
    }
    window.setTimeout(load_history_img,4000);
    
});