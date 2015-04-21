
$(function() {
	/*选值，赋值*/
	$(".lv-cascade-div div.lv-tab ul.bindclick li").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+index+") span").html($(this).html());
		$(".lv-cascade-search li a .lv-narrow-icon").eq(index).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
		// 初始化值. 
		var s_type = $(this).attr('data-type');
		var s_value = $(this).attr('data-value');
		var s_placeName = $(this).attr('data-place-name');
		var s_name = 'cities';
		if("cities" == s_type) { // 目的地
			s_name = 'toDest';
			if(s_value == '0') {
				s_value = '';
			}
			
			if(s_placeName == '全部目的地') {
				s_placeName = '';
			}
			
		    setLocalStorage(LOCAL_STRTORAGE_GROUPBUY_CITY,s_placeName);
		    setLocalStorage(LOCAL_STRTORAGE_GROUPBUY_CITY_ID,s_value);
		    s_value = s_placeName;
		} else if("productTypes" == s_type) { // 类别 
			s_name = 'productType';
		} else if("sortTypes" == s_type) { // 排序 
			s_name = 'sort';
		}
		$("#g_search #"+s_name+"").val(s_value);
		getGroupBuyListData('1');
	});
	/*$(".ic_peo em").html(getUrlParam("orderCount")+"人已购");
	$(".ic_time em").html(unescape(getUrlParam("remainTime")));*/
});

/**
 * ajax加载数据.
 */
function getGroupBuyListData(tag){
	$("#loadingP").show();
	var productType = $("#productType").val();
	var toDest = $("#toDest").val();
	var sort = $("#sort").val();
	var page = $("#page").val();
	if(tag == "1") {// 重新查询 ,	去除翻页信息 
		page = 0;
	}
	if('' == sort) {
		sort = 'orderCount';
	}
	
	var param = {"productType":productType,"toDest":encodeURIComponent(toDest),
			"sort":sort,"page":++page,"ajax":true};
	
	$.ajax({type:"POST", 
		url:contextPath+"/groupbuy",
		contentType:"application/x-www-form-urlencoded; charset=utf-8", 
		data:param, 
		dataType:"html", // 返回html格式数据. 
		success:function(data){
			$("#loadingP").hide(); // 隐藏遮罩层
			if(tag == "1") { // 重新查询  
				$("#data_list").html(data);
			} else { // 点击更多 
				$("#data_list").append(data);
			}
			$("#page").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
				//lvToast(true,"没有更多的数据了!",LT_LOADING_CLOSE);
			} else {
				$("#show_more").show();
			}
      }
});
	
};


//加载数据. 
function  initDatas(c_city) {
	if(null == c_city || "" == c_city || c_city== "undefined" ){
		c_city = DEFAULT_CITY;
	}
	var c_place_name = c_city;
	if('全部' == c_city) {
		c_place_name = '';
	}
	/*var cityId = getCityIdByCity(c_city);
	if('0' == cityId) {
		cityId = '';
	}*/
	$('#toDest').val(c_place_name);
	$('#tag_to_dest').html(c_city);
	getGroupBuyListData('1');
}

function getCityIdByCity(cityName) {
	if(null != cityJson) {
		for(var i =0; i < cityJson.length;i++) {
			if(cityJson[i].name == cityName) {
				return cityJson[i].placeId;
			}
		}
	}
	return cityName;
}

