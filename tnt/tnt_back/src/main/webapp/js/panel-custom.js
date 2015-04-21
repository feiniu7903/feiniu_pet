// JavaScript Document

$(function(){
	// 收缩功能
	var ani_time=100,ani_swi=true;
	$("#oper_aside").click(function(){
		if(ani_swi==true){
			$(this).addClass("icon-arrow-right");
			$("#panel_aside").animate({"left":"-220px","width":"250px"},ani_time,function(){
			  //$("div.aside_box").hide();
			  $("#panel_control").animate({"left":"0px","width":"30px"},ani_time)
			});
			$("#panel_main").animate({"left":"30px"},ani_time);
			ani_swi=false;
		}else{
			$(this).removeClass("icon-arrow-right");
			$("#panel_aside").animate({"left":"0px","width":"210px"},ani_time,function(){
				$("#panel_control").animate({"left":"210px","width":"10px"},ani_time)
				//$("div.aside_box").show();
			});
			$("#panel_main").animate({"left":"220px"},ani_time);
			ani_swi=true;
		}
	});
	
	$("#panel_control").click(function(){
		if(ani_swi==false){
			$("#oper_aside").removeClass("icon-arrow-right");
			$("#panel_aside").animate({"left":"0px","width":"210px"},ani_time,function(){
				$("#panel_control").animate({"left":"210px","width":"10px"},ani_time)
				//$("div.aside_box").show();
			});
			$("#panel_main").animate({"left":"220px"},ani_time);
			ani_swi=true;
		}
	})
	
	// 点击编辑展开及收起
//	$("a.p_btn_edit").bind('click',function(){
//		var _this_item = $(this).parents("tr");
//		if(_this_item.next(".p_detail").css("display")=="table-row"){
//			_this_item.removeClass("current_item").next(".p_detail").hide();
//		}else{
//			$("tr").removeClass("current_item");
//			$("tr.p_detail").hide();
//			_this_item.addClass("current_item").next(".p_detail").show();
//		}
//	});
	
	// 无限极菜单展开收起
	$("span.icon-arrow,#aside_list>li").bind('click',function(){
		var _this_item = $(this).parent().is("li") ? $(this).parent() : $(this);
		if(_this_item.hasClass("expand_item")){
			_this_item.removeClass("expand_item");
		}else{
			_this_item.addClass("expand_item");
		}
	});
	
//	$("span.icon-arrow").bind('click',function(){
//		var _this_item = $(this).parent();
//		selected_toggle(_this_item);
//	});
//	$("#aside_list>li").bind('click',function(){
//		var _this_item =$(this);
//		selected_toggle(_this_item);
//	});
//	
//	function selected_toggle(_this_item){		
//		if(_this_item.hasClass("expand_item")){
//			_this_item.removeClass("expand_item");
//		}else{
//			_this_item.addClass("expand_item");
//		}
//	}
	
	
	// 无限极菜单展开收起
	var prevNode = null;
	$("ul.ul_oper_list li").bind('click',function(e){
		if (e.stopPropagation){ 
			e.stopPropagation();
		}
		if(prevNode!=null && prevNode!=this){
			$(prevNode).removeClass("oper_item_selected");
		}
		$(this).addClass("oper_item_selected");
		prevNode = this;
	});
	
	
	// 关闭弹窗
	function p_close(){
		$("span.p_close").click();
	}
	
	$(".p_close").click(function(){
		$(this).parents(".p_modal").hide();
	});
	
//	$(".btn_upfile").click(function(){
//		
//		$("#upfile_box").show();
//	})
//	
//	$("#ul_oper_list a").bind("focus",function(){
//		if(this.blur){this.blur()};//去除聚焦边线
//	});
//	
//	var _p_height = $(document).height()
//	//$("div.panel_aside").height(_p_height);
//	$("div.modal_slist").height(_p_height - 80);
//	
//	
//	$("input.clear_search").bind('click',function(){
//		$("#ul_oper_list li").removeClass("oper_item_search");
//	});
//	$("#float_modal input.btn_search").click(function(){
//		if($("#modal_id").attr("value") !="" || $("#modal_name").attr("value") !=""){
//			var _search_text = ($("#modal_id").attr("value") !="" ? $("#modal_id").attr("value") : $("#modal_name").attr("value") );
//		}else{
//			alert("请输入查询内容！！！")
//		}
//		$("#ul_oper_list li").removeClass("oper_item_search oper_item_selected");
//		$("a.oper_item:contains('" + _search_text + "')").parent().addClass("oper_item_search").end().parents("li").addClass("expand_item");
//		if($("li.oper_item_search").length < 1){
//			alert("没有搜索结果，请更换查询条件试试");
//		}
//	});
//	
//	$("table.p_table input.p_num").change(function(){
//		$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
//	})
	
});






