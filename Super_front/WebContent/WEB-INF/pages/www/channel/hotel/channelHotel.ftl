<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<#include "/WEB-INF/pages/www/channel/common/seo.ftl">
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 

<#include "/common/coremetricsHead.ftl">
</head>
<body class="hotel">
<@s.set var="pageMark" value="'hotel'" />
<!--头文件-->
<#include "/common/header.ftl"> 

 <!-- wrap\\ 1 -->
<div class="wrap wrap-quick">

 <!--左侧-->
 <#include "/WEB-INF/pages/www/channel/hotel/hotel_page_left.ftl">
 <!--右侧-->
 <#include "/WEB-INF/pages/www/channel/hotel/hotel_page_right.ftl">
	
</div> <!-- //.wrap 1 -->

<!--广告-->
<div class="padbox wrap" data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_banner01#1000px#80px">
</div> <!-- //.adbox -->

<!-- 页面底部-->
<#include "/common/footer.ftl">
<#include "/WEB-INF/pages/staticHtml/friend_link/hotel_footer.ftl">

<!-- 公用js-->
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|hotel_2013|hotel_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
<!-- 频道公用js--> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/plugins.js,/js/v3/app.js,/js/v3/channel.js"></script> 
<!-- 流量统计--> 
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript"></script> 
 <script type="text/javascript">
    <!-- tab按钮获取推荐景点信息-->
    	function getAjaxRecommendScenicForNational(url,fromPlaceId,fromPlaceName){
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
	    function getAjaxRecommendScenicForAbroad(url,fromPlaceId,fromPlaceName){
	         $("a[paramDataCode2]").bind("click", function(){
	         var paramDataCode=$(this).attr("paramDataCode2"); 
             var paramId=$(this).attr("paramId2"); 
             var paramDataSearchName=$(this).attr("paramDataSearchName2"); 
             var paramBakWord2=$(this).attr("paramBakWord2");
             var paramBakWord3=$(this).attr("paramBakWord3");
 			 $.ajax({
				   type: "POST",
				   url: url,
				   data: {paramDataCode: paramDataCode,fromPlaceId:fromPlaceId,fromPlaceName:fromPlaceName,paramDataSearchName:paramDataSearchName,paramBakWord2:paramBakWord2,paramBakWord3:paramBakWord3},
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
	$(function(){
	    var url="http://www.lvmama.com/homePage/ajaxGetRecommendScenicForHotel.do";
 		var fromPlaceId='${fromPlaceId?if_exists}';
	    var fromPlaceName='${fromPlaceName?if_exists}';
  		getAjaxRecommendScenicForNational(url,fromPlaceId,fromPlaceName);
  		var urlForAbroad="http://www.lvmama.com/homePage/ajaxGetRecommendScenicForAbroadHotel.do";
  		getAjaxRecommendScenicForAbroad(urlForAbroad,fromPlaceId,fromPlaceName);
	});
 </script>
 <script type="text/javascript">
 	$(document).ready(function(){
		$(".hotel-domestic").one("mouseover",function(){
			$("#txtHotelSearch").ui("autoComplete",{
				type : 3,
				url : "http://www.lvmama.com/search/hotelSearch.do",
				recommend : true
			});
		});
		$("#searchHotelBtn").click(function(){
			if($("#txtHotelSearch").val()=="中文/拼音"||$.trim($("#txtHotelSearch").val())===''){
				$("#txtHotelSearch").focus();
				return;
			}
			var fromDest = $("#fromDest").val();
			var newDest = $("#txtHotelSearch").val();
			var hotels = [];
			$("input[name=hotelType]:checked").each(function(){
				hotels.push(this.value);
			});
			if(hotels.length==0){
				location.href = "http://www.lvmama.com/search/hotel/"+newDest+".html";
			}else{
				var url = "http://www.lvmama.com/search/hotel/"+newDest+"-"+hotels.join("")+".html";
				location.href = url;
			}
		});
	});


        </script>
         <script>
     		 cmCreatePageviewTag("酒店频道首页", "HOTEL", null, null);
 		</script>
</body>
</html>
