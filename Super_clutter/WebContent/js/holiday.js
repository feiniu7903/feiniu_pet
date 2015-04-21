var lv_type_free_tour = "FREE_TOUR";
var lv_type_group = "GROUP";
var lv_route_freeness = "FREENESS";
var lv_route_group = "GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS";
var lv_route_freeness_long = "FREENESS_FOREIGN,FREENESS_LONG";
var lv_holiday_subjects = "lv_holiday_subjects";

$(function() {
	// input框颜色 
	$("input.lv-input-search").focus(function(e) {
		$("input.lv-input-search").parent().parent().addClass("lv-input-focus");
	});
	$("input.lv-input-search").blur(function(e) {
		$("input.lv-input-search").parent().parent().removeClass("lv-input-focus");
	});	
	
	
	// 自动补全查询
	$("#keyword,#citykeyword").keyup(function() {
		searchAutoComplete();
		var _clearVal = $(this).val();
		if(_clearVal != ''){
			$('#clear_password').show();
		}else {
			$('#clear_password').hide();
		}
	});
	var _clearValload = $('.lv-input-search').val()
	if(_clearValload != ''){
		$('#clear_password').show();
	}else {
		$('#clear_password').hide();
	}
	$('#clear_password').live('click',function(){
		if(_clearValload != ''){
			$('#clear_password').show();
		}else {
			$('#clear_password').hide();
		}
	});
	
	// keyword关键字搜索 
	$("#keyword").keypress(function(e) {
		var key = e.which;
		if( key == 13) {
			keySearch($("#holiday_search #keyword").val());
		}
	});
	
	// 如果是列表页面, 初始化列表信息.
	if($("#page").length > 0) {
		initTabs();
	}
	
});

// 搜索
function keySearch(value) {
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	$("#holiday_search #subject").val(" ");
	$("#holiday_search #toDest").val(encodeURIComponent(value));
	$("#fromDest").val(encodeURIComponent($("#fromDest").val()));
	$("#holiday_search").action=contextPath+"/holiday/holiday_search";
	$("#holiday_search").submit();
}

/**
 * 度假首页查询  
 * id   objectId
 * name  中文名字
 * type  自由行 ， 跟团游 
 */
function destSearch(id,name,type) {
	// 提交表单前中文转码 
	$("#fromDest").val(encodeURIComponent($("#fromDest").val()));
	$("#toDest").val(encodeURIComponent(name));
	$("#subProductType").val(getSubProductTypeByType(type));
	$("#holiday_search").attr("action",contextPath+"/holiday/list");
	$("#holiday_search").submit();
}

// 
function getSubProductTypeByType(type) {
	if(type == lv_type_free_tour) {
		return lv_route_freeness;
	} else if(type == lv_type_group) {
		return lv_route_group;
	} else {
		return "";
	}
}


// 初始化table 
function initTabs() {
	// 类别 
	var subProductType = $("#subProductType").val();
	if(subProductType == lv_route_freeness) {
		subProductName = "自由行(景+酒)";
	}else if(subProductType == lv_route_freeness_long) {
		subProductName = "自由行(机+酒)";
	}else if(subProductType == lv_route_group) {
		subProductName = "跟团游";
	}else{
		subProductName = "全部类型";
	}
	$("#span_subProductType").html(decodeURIComponent(subProductName));
	
	// 主题
	var sub = $("#subject").val();
	if($.trim(sub) == '' || sub =='0') {
		sub = "全部主题";
	}
	$("#span_subject").html(decodeURIComponent(sub));
	
	// 排序
	var sort = $("#sort").val();
	var sort_title = $("#sort"+sort).attr('data-value');
	if('seq' == sort_title) {
		sort_title = "默认";
	}
	$("#span_sort").html(decodeURIComponent(sort_title));
	
	// 初始化subjects 
	var subjects = getLocalStorage(lv_holiday_subjects);
	if(null != subjects && subjects.length > 0) {
		$("#subjects_id").html(decodeURIComponent(subjects));
		removeLocalStorage(lv_holiday_subjects);
	}
}

// tab过滤页 
function holidaySearch(obj,value,type){
	// 保存subjects 
	setLocalStorage(lv_holiday_subjects,$("#subjects_id").html());
	// 隐藏弹出层 
	hideDiv(obj);
	// 给表单赋值
	if('sort' == type ) {
		$("#sort").val(value);
	} else if('subject' == type) {
		$("#subject").val(value);
	} else if("route" == type) {
		$("#subProductType").val(value);
		// 全部主题会变  否则 全部主题不变
		removeLocalStorage(lv_holiday_subjects);
	}
	// 提交表单前中文转码 
	$("#toDest").val(encodeURIComponent($("#toDest").val()));
	$("#fromDest").val(encodeURIComponent($("#fromDest").val()));
	$("#subject").val(encodeURIComponent($("#subject").val()));
	$("#holiday_search").submit();
}



function hideDiv(obj) {
	$(obj).hide();
	$(".lv-cascade-search li a div").hide();
	$(".lv-cascade-div div.lv-tab").hide();
	
}

//自动补全搜索. 
function searchAutoComplete() {
	var keyword = $("#keyword").val();
	if($.trim(keyword) == '') {
		$("#search_autocomplete").hide();
		return false;
	}
	var param = {"fromDest":$("#fromDest").val(),"keyWorld":encodeURIComponent(keyword),"stage":$("#stage").val()};
	$.ajax({type:"POST", url:contextPath+"/search_auto_complete", data:param, dataType:"json", success:function (data) {
		if(data.code=='success'){
			var datas = data.datas;
			if(null != datas) {
				if(datas.length > 0) {
					initAutoCompleteDatas(datas);
				} 
			}else {
				$("#search_autocomplete").hide();
			}
		}else{
			$("#search_autocomplete").hide();
		}
	}});
}

//初始化数据 . 
function initAutoCompleteDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 25;i++) {
		var obj = datasArray[i];
		var fromDest;
		if(obj!=null && obj.subName!=null && obj.subName!=""){
			fromDest=obj.name+","+obj.subName;
		}else{
			fromDest=obj.name;
		}
		str.push("<li onclick=\"searchTip('"+obj.id+"','"+obj.name+"','"+fromDest+"','"+obj.stage+"');\">"+obj.name+"</li>");
		//str.push("<div class=\"lv-seprator-line\"></div>");
	}
	$("#search_success").html(str.join(""));
	$("#search_autocomplete").show();
}

//初始化数据 . 
function initCityAutoCompleteDatas() {
	var datasArray = [];
	var keyword = $("#citykeyword").val();
	var j = 0;
	if(null != cityListJson && null != keyword && $.trim(keyword)!="") {
		for(var i = 0; i < cityListJson.length;i++) {
			if(cityListJson[i].pinyin.indexOf(keyword.toUpperCase())>-1 || cityListJson[i].name.indexOf(keyword)>-1 ) {
				datasArray[j++] = cityListJson[i];
			}
		}
	}
	initAutoCompleteDatas(datasArray); 
}

//点击自动补全列表. 
function searchTip(id,name,stage) {
	$('#search_autocomplete').hide();
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	// 提交表单 
	$("#holiday_search #keyword").val(name);
	$("#holiday_search #subject").val("");
	$("#holiday_search #subProductType").val("");
	$("#fromDest").val(encodeURIComponent($("#fromDest").val()));
	$("#holiday_search #toDest").val(encodeURIComponent(name));
	$("#holiday_search").action=contextPath+"/holiday/holiday_search";
	$("#holiday_search").submit();
}

/********** choose city ************/

// 根据拼音查看 ， 选择出发地  
function charClick(obj) {
	$(obj).addClass("lv-narrow-current").siblings().removeClass("lv-narrow-current");
	var top = $(".lv-primary .lv-wrapper-container").eq($(obj).index()).offset().top;
	$("html, body").animate({scrollTop:parseInt(top)}, 500);
}

/**
 * 选中城市. 
 * @param name
 * @param pinyin
 */
function chooseCity(name,fromDest,pinyin) {
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	setLocalStorage(LOCAL_STRTORAGE_ROUTE_CITY,name);
	
	var holiday_click_status=$("#holidayClickStatus").val();
	//如果是holiday_list_click则是从度假列表点击城市列表
 	if(holiday_click_status!=null && holiday_click_status=='holiday_list_address'){
 		var mudidi_city=getLocalStorage('holiday_mudidi_city');//目的地
 		var subproducttype=getLocalStorage('holiday_subproducttype');//目的地子类型
 		
 		$("#fromDestList").val(encodeURIComponent(name));//出发地
 		$("#toDestList").val(encodeURIComponent(mudidi_city));
 		$("#subProductType").val(getSubProductTypeByType(subproducttype));
 		$("#holiday_search_list").attr("action",contextPath+"/holiday/list");
 		$("#holiday_search_list").submit();
 		
 	}else if(holiday_click_status!=null && holiday_click_status=='holiday_list_keyword'){
 		var mudidi_city=getLocalStorage('holiday_mudidi_city');//目的地
 		
 		$("#fromDestList").val(encodeURIComponent(name));//出发地
 		$("#toDestList").val(encodeURIComponent(mudidi_city));
 		$("#holiday_search_list").submit();
 	}else if(holiday_click_status!=null && holiday_click_status=='holiday_cms_citys'){//CMS
 		if(callUrl!=null && callUrl!=""){
 			window.location.href=callUrl+encodeURIComponent(name)+"&fromDest="+encodeURIComponent(fromDest);	
 		}
 	}else{
		window.location.href=contextPath+"/holiday";
 	}
	/*initDatas(name);
	$("#holiday_search #fromDest").val(encodeURIComponent(name));
	$("#holiday_search").submit();*/
}

/**
 *  折叠 or 隐藏 .
 */
function destSearchMore(type){
	$("a[id^="+type+"]").each(function(index) {
		$(this).slideToggle(0);
	});
}

function getData(){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var keyword = $("#hidden_keyword").val();
	var fromDest= getLocalStorage(LOCAL_STRTORAGE_ROUTE_CITY);
	var toDest = $("#toDest").val();
	var subject = $("#subject").val();
	var sort = $("#sort").val();
	var subProductType = $("#subProductType").val();
	var page = $("#page").val();
	var stage = $("#stage").val();
	
	var param = {"keyword":encodeURIComponent(keyword),"subProductType":subProductType,"toDest":encodeURIComponent(toDest),"fromDest":encodeURIComponent(fromDest),
			"subject":encodeURIComponent(subject),"sort":sort,"page":++page,"ajax":true,"stage":stage};
	$.get(contextPath+"/holiday/list",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#page").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
			//lvToast(true,"没有更多的数据了!",3000);
		}
	});
};

/****************  度假首页  ******************/
//度假首页数据 .周边自由行出境游等 . 
function  initDatas(c_city) {
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	$("#loadingP").show();
	if(null == c_city || "" == c_city || c_city== "undefined" ){
		c_city = DEFAULT_CITY;
	}
	$('#fromDest').val(c_city);
	$('#lv-start').html(c_city);
	var param = {"fromDest":c_city,"ajax":true};
	$.ajax({type:"POST", 
			url:contextPath+"/holiday",
			contentType:"application/x-www-form-urlencoded; charset=utf-8", 
			data:param, 
			dataType:"html", // 返回html格式数据. 
			success:function(data){
				$("#loadingP").hide(); // 隐藏遮罩层
				$("#data_list").html(data);
	        }
	});
	
}



