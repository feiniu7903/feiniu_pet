$(function() {
	// input框颜色 
	$("input.lv-input-search").focus(function(e) {
		$("input.lv-input-search").parent().parent().addClass("lv-input-focus");
	});
	$("input.lv-input-search").blur(function(e) {
		$("input.lv-input-search").parent().parent().removeClass("lv-input-focus");
	});	
	
	
	// 自动补全查询
	$("#keyword").live('keyup',function() {
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
			keySearch($("#freetrip_search #keyword").val());
		}
	});
	
	// 初始化列表信息.
	initTabs();
	
});

// 搜索
function keySearch(value) {
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	$("#freetrip_search #subjects").val(" ");
	$("#freetrip_search #toDest").val(encodeURIComponent(value));
	$("#freetrip_search").submit();
}

// 初始化table 
function initTabs() {
	var sub = $("#subject").val();
	if($.trim(sub) == '' || sub =='0') {
		sub = "全部主题";
	}
	$("#span_subject").html(decodeURIComponent(sub));
	
	var sort = $("#sort").val();
	var sort_title = $("#sort"+sort).attr('data-value');
	if('seq' == sort_title) {
		sort_title = "默认排序";
	}
	$("#span_sort").html(decodeURIComponent(sort_title));
}

//点击自动补全列表. 
function searchTip(id,name,stage) {
	$('#search_autocomplete').hide();
	//lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	// 提交表单 
	$("#freetrip_search #keyword").val(name);
	$("#freetrip_search #subject").val("");
	$("#freetrip_search #toDest").val(encodeURIComponent(name));
	$("#freetrip_search").submit();
}


function freetripSearch(obj,value,type){
	// 隐藏弹出层 
	hideDiv(obj);
	// 给表单赋值
	if('sort' == type ) {
		$("#sort").val(value);
	} else if('subject' == type) {
		$("#subject").val(value);
	}
	// 提交表单前中文转码 
	$("#toDest").val(encodeURIComponent($("#toDest").val()));
	$("#subject").val(encodeURIComponent($("#subject").val()));
	$("#freetrip_search").submit();
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
	var param = {"keyWorld":encodeURIComponent(keyword),"stage":"2"};
	$.ajax({type:"POST", url:contextPath+"/search_auto_complete", data:param, dataType:"json", success:function (data) {
		if(data.code=='success'){
			var datas = data.datas;
			if(null != datas) {
				if(datas.length > 0) {
					initDatas(datas);
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
function initDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 10;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"searchTip('"+obj.id+"','"+obj.name+"','"+obj.stage+"');\">"+obj.name+"</li>");
		//str.push("<div class=\"lv-seprator-line\"></div>");
	}
	$("#search_success").html(str.join(""));
	$("#search_autocomplete").show();
}

/**
 * 自由行列表ajax查询 
 * @param p
 */
function getData(){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var keyword = $("#hidden_keyword").val();
	var toDest = $("#toDest").val();
	var subject = $("#subject").val();
	var sort = $("#sort").val();
	var subProductType = $("#subProductType").val();
	var page = $("#page").val();
	
	var param = {"keyword":encodeURIComponent(keyword),"subProductType":subProductType,"toDest":encodeURIComponent(toDest),
			"subject":encodeURIComponent(subject),"sort":sort,"page":++page,"ajax":true};
	$.post(contextPath+"/route/list",param,function(data){
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


