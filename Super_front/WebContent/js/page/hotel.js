$(document).ready(function(){
	$('.hot_a').one('click',function(event){
		$(this).attr('href',encodeURI($(this).attr('href')));
	});
	$(".text_query").dest_suggest({rightInput:91,inputBottom:23,hideSelect:true});
	$("#roundPlaceName").dest_roundPlace({rightInput:120});
	$(".hotelNameParm").dest_hotelName({rightInput:120});
		$("#keyword").focus(function(){
			if($(this).val()=="中文/拼音") {
				$(this).val("");
			}
		});
	});
	
	function subHotelForm(){
		if(($.trim($(".hotelNameParm").val())=="中文/拼音"||$.trim($(".hotelNameParm").val())=="")&&($.trim($(".text_query").val())=="中文/拼音"||$.trim($(".text_query").val())=="")&&($.trim($("#roundPlaceName").val())=="中文/拼音"||$.trim($("#roundPlaceName").val())=="")){
			alert("请选择或输入城市或周边景区或酒店名称！");
		} else {
			var roundPlaceName = ($.trim($("#roundPlaceName").val())=='中文/拼音'?"":$.trim($("#roundPlaceName").val()));
			var cityValue = ($.trim($("#city").val())=='中文/拼音'?"":$.trim($("#city").val()));
			var keyword =   ($.trim($("#keyword").val())=='中文/拼音'?"":$.trim($("#keyword").val()));
			//window.open("http://www.lvmama.com/search/placeSearch!hotelSearch.do?roundPlaceName="+encodeURI(roundPlaceName)+"&keyword=" +encodeURI(keyword)+ "&priceRange="+$("#priceRange").val()+"&stage=3&city="+encodeURI(cityValue)+"&hotelLevels="+$("#hotelLevels").val());
	  	    window.open("http://www.lvmama.com/search/hotel-city-"+encodeURI(cityValue)+"-place-"+encodeURI(roundPlaceName)+"-kw-" +encodeURI(keyword)+ "-price-"+$("#priceRange").val()+"-levels-"+$("#hotelLevels").val()+"-stage-3-1.html")
		}
	}

	$(document).ready(function() {
		//$(".focusImg_p").wb_focusImg();								//focusIme Slide Effect
		
		$(".scrollText").wb_scrollText();
		
		$(".row1").wb_tab({
			_tabs	: ".inTabs",		//Tabs Container Class Name Define
			_panes	: ".inPane",		//Panes Container Class Name Define
			_currentTab	: "inCurrent"
		});
		
		$(".row2").wb_tab();
		$(".inColLeft").wb_tab({
			_panes	: ".scrollPane"		//Panes Container Class Name Define
		});
		$(".inColRight").wb_tab({
			_panes	: ".scrollPane"		//Panes Container Class Name Define
		});
		
		$(".scrollPane").wb_accordion();
		$(".scrollLoading").wb_scrollLoading();
	});