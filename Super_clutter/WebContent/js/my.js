
/**
 * 我的收藏列表ajax查询 
 * @param p
 */
function getData(type){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var page = $("#"+type+"Page").val();
	var param = {"collectionType":type,"page":++page};
	$.get(contextPath+"/user/mycollection_ajax.htm",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#"+type+"_data_list").append(data);
		$("#"+type+"Page").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#"+type+"_lastPage_flag").length != 0){
			$("#"+type+"_show_more").hide();
			//lvToast(true,"没有更多的数据了!",LT_LOADING_CLOSE);
		}
	});
};


// 点评 
function getCommentData(){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var page = $("#page").val();
	var param = {"page":++page};
	$.get(contextPath+"/user/envaluate_ajax.htm",param,function(data){
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

function getBonusData(type){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	var page = $("#page").val();
	var param = {"page":++page,"bonusType":type,"ajax":true};
	$.get(contextPath+"/user/bonusInfo.htm",param,function(data){
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