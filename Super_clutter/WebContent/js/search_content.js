$(function() {
	/*选值，赋值*/
	$(".lv-cascade-div div.lv-tab ul.bindclick li").click(function(){
		$(".lv-transparent-div").hide();
		$(".lv-cascade-search li a div").hide();
		$(".lv-cascade-div div.lv-tab").hide();
		var index = $(this).parent("ul").parent("div.lv-tab").index();
		$(".lv-cascade-search li:eq("+(--index)+") span").html($(this).html());
		
		// 初始化值. 
		var s_type = $(this).attr('data-type');
		var s_value = $(this).attr('data-value');
		var s_name = 'cities';
		if("cities" == s_type) { // 目的地
			s_name = 'toDest';
			if(s_value == '0') {
				s_value = '';
			}
		} else if("productTypes" == s_type) { // 类别 
			s_name = 'productType';
		} else if("sortTypes" == s_type) { // 排序 
			s_name = 'sort';
		}
		$("#g_search #"+s_name+"").val(s_value);
		$("#g_search").submit();
	});
	
	initSearchSelect();
});

function initSearchSelect() {
	var to_dest = $("#g_search #toDest").val();
	if(null != to_dest) {
		$("#tag_to_dest").html($("#li"+to_dest).html());
	}
	
	var productType = $("#g_search #productType").val();
	if(null != productType) {
		$("#tag_productType").html($("#li"+productType).html());
	}
	
	var sort = $("#g_search #sort").val();
	if(null != sort) {
		$("#tag_sort").html($("#li"+sort).html());
	}
}
