
$(function() {
    /*	
	$(".lv-cascade-search li").click(function(e) {
		$(".lv-transparent-div").show();
		$(this).children("a").children("div").show();
		$(this).siblings().children("a").children("div").hide();
		$(".lv-tab").eq($(this).index()).show().siblings(".lv-tab").hide();
	});*/
	
	$(".lv-cascade-div div.lv-tab ul.bindmouse li").mouseover(function(){
		$(this).children("div.lv-cascade-childrendiv").show();
		$(this).siblings("li").children("div.lv-cascade-childrendiv").hide();
	});
	$(".lv-cascade-div div.lv-tab ul.bindmouse li").mouseout(function(){
		$(this).children("div.lv-cascade-childrendiv").hide();
	});
	
	/*选值，赋值*/
	// 第一级省份不能搜索 。 
	$(".lv-cascade-div div.lv-tab ul.bindclick li").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+(index)+") span").html($(this).html());
		$(".lv-cascade-search li a .lv-narrow-icon").eq(index).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
		initParams(this);
	});
	
	// 第二级 可以搜索  
	$("div.lv-cascade-childrendiv ul.lv-cascade-childrenlist li").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul.lv-cascade-childrenlist").parent("div.lv-cascade-childrendiv").parent("li").parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+(index)+") span").html($(this).html());
		$(".lv-cascade-search li a .lv-narrow-icon").eq(index).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');

		// 初始化参数
		initParams(this);
	});
	
	// 自动补全查询
	$("#keyword").keyup(function() {
		searchAutoComplete();
		var _clearVal = $(this).val();
		if(_clearVal != ''){
			$('#clear_password').show();
		}else {
			$('#clear_password').hide();
		}
	});
	var _clearValload = $('#keyword').val()
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
			// 提交表单 
			$("#hidden_keyword").val(encodeURIComponent($("#keyword").val()));
			$("#key_search").submit();
		}
	});
	
});



// 选择下拉列表 - 初始化表单数据 
function initParams(obj) {
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var s_type = $(obj).attr('data-type'); // 类别 
	var s_value = $(obj).attr('data-value'); // 值 
	var s_subjects = $(obj).attr('data-subjects'); // 值 
	var s_name = 'cities';
	if("cities" == s_type) { // 目的地
		s_name = 'toDest';
		// 地区改变时 ，主题和地区向对应. 
		changeSubjects(s_subjects);
		// 查询值保持localstorage中
		setLocalStorage(LOCAL_STRTORAGE_CURRENT_CITY,s_value);
	} else if("subjects" == s_type) { // 类别 
		s_name = 'subjects';
	} else if("sort" == s_type) { // 排序 
		s_name = 'sort';
	}
	
	$("#"+s_name+"").val(s_value);
	//$("#keyword").val(''); // 清空关键字
	//$("#hidden_keyword").val(''); // 清空关键字
	getPlaceListData("1");
}

// 改变主题 
function changeSubjects(s_subjects) {
	var str = ' <li data-type="subjects" onclick="bindClick(this);" data-value=" ">全部主题</li>';
	if(null != s_subjects && s_subjects.length > 0) {
		s_subjects = s_subjects.replace("[","").replace("]","");
		if($.trim(s_subjects)!='') {
			s_subjects = s_subjects.split(',');
			for(var i = 0; i < s_subjects.length;i++) {
				str += '<li data-type="subjects" onclick="bindClick(this);" data-value="'+$.trim(s_subjects[i])+'">'+s_subjects[i]+'</li>';
			}
		}
	}
	$("#tab_subjects").html(str);
}

// 增加事件 。
function bindClick(obj) {
	$(".lv-transparent-div").hide();
	$(".lv-cascade-search li a div").hide();
	$(".lv-cascade-div div.lv-tab").hide();
	var index = $(obj).parent("ul").parent("div.lv-tab").index();
	$(".lv-cascade-search li:eq("+(index)+") span").html($(this).html());
	$(".lv-cascade-search li a .lv-narrow-icon").eq(index).attr('src', 'http://pic.lvmama.com/img/mobile/touch/img/narrow.png');
	initParams(obj);
}

//自动补全搜索. 
function searchAutoComplete() {
	var keyword = $("#keyword").val();
	if($.trim(keyword) == '') {
		$("#search_autocomplete").hide();
		return false;
	}
	var param = {"keyWorld":keyword,"stage":$("#stage").val()};
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

// 初始自动补全化数据 . 
function initAutoCompleteDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 25;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"searchTip('"+obj.id+"','"+obj.name+"','"+obj.stage+"');\">"+obj.name+"</li>");
		//str.push("<div class=\"lv-seprator-line\"></div>");
	}
	$("#search_success").html(str.join(""));
	$("#search_success").show();
	$("#search_autocomplete").show();
}

//点击自动补全列表. 
function searchTip(id,name,stage) {
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_CLOSE);
	$("#search_success").hide();
	if("2" == stage) { // 跳转详情页 
		window.location.href = "http://"+hostName+"/ticket/piao-"+id+"/";
	} else { // 跳转搜索页.
		$("#keyword").val(name);
		$("#hidden_keyword").val(encodeURIComponent(name));
		$("#key_search").submit();
	}
}

/**
 * 景点列表ajax查询 
 * @param p
 */
function getPlaceListData(tag){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var page = $("#page").val();
	if(tag == "1") {// 重新查询 ,	去除翻页信息 
		page = 0;
	}
	var param = {"keyword":encodeURI($("#hidden_keyword").val()),"stage":$("#stage").val(),"toDest":$("#toDest").val(),
			"subjects":$("#subjects").val(),"sort":$("#sort").val(),"page":++page,"ajax":true};
	$.ajax({type:"POST", 
			url:contextPath+"/place",
			contentType:"application/x-www-form-urlencoded; charset=utf-8", 
			data:param, 
			dataType:"html", // 返回html格式数据. 
			success:function(data){
				$(".lv-toast-loading").hide(); // 隐藏遮罩层
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


/**
 * 景点列表ajax查询 
 * @param p
 */
function getCommentData(){
	globe.lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var placeId = $("#placeId").val();
	var page = $("#page").val();
	var param = {"placeId":placeId,"page":++page,"ajax":true};
	$.get(contextPath+"/comment_ajax",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#page").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
			//lvToast(true,"没有更多的数据了!",LT_LOADING_CLOSE);
		}
	});
};


/**
 * 周边景点ajax查询 
 * @param p
 */
function getMoreData(){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var sort = "juli";
	var page = $("#page").val();
	
	var param = {"stage":2,"sort":sort,"page":++page,"windage":100000,"mobilePlace.baiduLatitude":latitude,"mobilePlace.baiduLongitude":longitude,"ajax":true};
	$.post(contextPath+"/spotticket/stnearby.htm",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#page").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
			lvToast(true,"没有更多的数据了!",LT_LOADING_CLOSE);
		}
	});
};


//加载数据. 
function  initDatas(c_city) {
	if(null == c_city || "" == c_city || c_city== "undefined" ){
		c_city = DEFAULT_CITY;
	}
	$('#toDest').val(c_city);
	$('#spanCities').html(c_city);
	getPlaceListData('1');
}

/**
 * 选中城市. 
 * @param name
 * @param pinyin
 */
function chooseCity(name,pinyin) {
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	setLocalStorage(LOCAL_STRTORAGE_ROUTE_CITY,name);
	$("input[name='keyword']").val(encodeURI(name));
	$("#key_search").submit();
	$("input[name='keyword']").val("");
}

//初始化数据 . 
function initAutoCompletePlaceDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 25;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"searchTip('"+obj.id+"','"+obj.name+"','"+obj.stage+"');\">"+obj.name+"</li>");
		//str.push("<div class=\"lv-seprator-line\"></div>");
	}
	$("#search_success").html(str.join(""));
	$("#search_autocomplete").show();
}

//初始化数据 . 
function initCityAutoCompletePlaceDatas() {
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
	initAutoCompletePlaceDatas(datasArray); 
}

/*搜索*/
function btnClick(){
	$("#search_success").hide();
	var keyword = $("#keyword").val();
	if(null == keyword || $.trim(keyword)=='') {
		// 请输入景点目的地或关键词
		lvToast(false,"请输入景点目的地或关键词",LT_LOADING_CLOSE);
		return false;
	}
	$("input[name='keyword']").val(encodeURI(keyword));
	$("#key_search").submit();
	$("input[name='keyword']").val("");
	$("input[name='hidden_keyword']").val("");
}