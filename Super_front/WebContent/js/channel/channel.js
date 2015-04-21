/**
 * 门票，周边，国内，酒店，出境频道 下面推荐景点js
 * @param url ajax请求地址
 * @author nixianjun
 */
function getAjaxRecommendScenic(url,fromPlaceId,fromPlaceName){
	    $("a[paramDataCode]").bind("click", function(){
	         var paramDataCode=$(this).attr("paramDataCode"); 
             var paramId=$(this).attr("paramId"); 
             var paramDataSearchName=$(this).attr("paramDataSearchName"); 
 			 $.ajax({
			   type: "POST",
			   url: url,
			   data: {paramDataCode: paramDataCode,fromPlaceId:fromPlaceId,fromPlaceName:fromPlaceName,paramDataSearchName:paramDataSearchName},
			   async: true,
			   dataType:"html",
			   success: function(data){
			        $("#"+paramId).html(data); 
 			   },
			   error:function(msg){
			     alert( "Data fail: " + msg );
			   }
			});
 		});
	}