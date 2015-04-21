/**
 * 中国好温泉列表ajax查询 
 * @param p
 */
function getHotSpringList(tag){
	var page = $("#page").val();
	if(tag == "1") {// 重新查询 ,	去除翻页信息 
		page = 0;
	}
	var param = {"keyword":encodeURI($("#keyword").val()),"stage":$("#stage").val(),
			"subjects":$("#subjects").val(),"sort":$("#sort").val(),"page":++page,"ajax":true};
	$.ajax({type:"POST", 
			url:contextPath+"/placeHotSpring",
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
 * 中国好首页地区搜素温泉列表
 * @param p
 */
function getPlaceHotSpring(place){
	var strHref = document.location.href; 
	if(null != strHref && "" != strHref) { 
		var index = strHref.indexOf('channel=wap'); 
		if(index != -1) { 
			$("#channel").val('wap'); 
		} 
	} 
	
	$("#keyword").val(encodeURI(place));
	$("#subjects").val(encodeURI("温泉"));
	
	$("#key_search").submit();
};