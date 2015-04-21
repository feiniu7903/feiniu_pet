// JavaScript Document

$(function(){
	// 关闭弹窗
	$("div.p_title button.close").bind('click',function(){
		$(this).parents("div.p_modal").hide();
		//close_modal();
	});

	$("button.btn-close").bind('click',function(){
		$(this).parents("div.p_modal").hide();
	});
	
	function close_modal(_this){
		$("div.p_modal").hide();
	};
	
	function show_modal(_this){
		$("div.p_modal").show();
	};
	
	// 创建新材料-添加、删除
	$("button.add_stuff_item").click(function(){
		add_stuff();
	});
	
	$(".stuff button.close").live('click',function(){
		$(this).parents(".stuff").remove();
		
	});
	
	function add_stuff(){
		var add_stuff_item = $("tbody.add-stuff-info")
		var add_stuff_info = add_stuff_item.html();
		add_stuff_item.before(add_stuff_item.clone().removeClass("add-stuff-info"))
	};

    // 添加、删除-新信息栏
	$("button.J_btn_additem").click(function(){
		J_additem();
	});
	
	$("table.J_additem_box button.close").live('click',function(){
		$(this).parents("tbody").remove();
	});
	
	function J_additem(){
		var additem = $("tbody.J_additem_info")
		var additem_info = additem.html();
		additem.before(additem.clone().removeClass("J_additem_info"))
	};

	// 备注及修改
	var _cancel_click = null;
	$("a.link-remark").live("click",function(){
		_cancel_click = $(this).parents("td").html();
		var textarea_remark = '<p class="remark"><textarea style="width:90%;" placeholder="//增加备注："></textarea><a class="J_save_remark" href="#">保存</a>　　<a class="J_cancel_remark" href="#">取消</a></p>';
		$(this).parents("td").append(textarea_remark);
		$(this).remove();
		return false;
	});

	$("a.J_save_remark").live("click",function(){
		var _save_info = $(this).prev().val();
		var _edit_remark = '<a class="J_edit_remark" href="#">修改备注</a>';
		$(this).parent().html("备注：" + _save_info).before(_edit_remark);
		//if($(this).prev().prev().val()){
		//	$(this).next().hide().end().hide().prev().show().prev().attr({"disabled":"disabled"}).addClass("disabled");
		//}

	});

	$("a.J_cancel_remark").live("click",function(){
		if (_cancel_click) {
			$(this).parents("td").html(_cancel_click);
			return false;
		};
	});

	$("a.J_edit_remark").live("click",function(){
		_cancel_click = $(this).parents("td").html();
		var _textarea_value = $(this).next().html().replace("备注：","");
		$(this).next().html('<textarea style="width:90%;" placeholder="//增加备注：">'+ _textarea_value +'</textarea><a class="J_save_remark" href="#">保存</a>　　<a class="J_cancel_remark" href="#">取消</a>').end().hide();
	});

	// 通过、不通过
	$("a.J_oper_yes").live("click",function(){
		$(this).parent().prev().attr("class","status-yes").html("(√)");
		//$(this).replaceWith(this.innerText);
		$(this).addClass("gray").siblings().removeClass("gray");
		return false;
	});
	$("a.J_oper_no").live("click",function(){
		$(this).parent().prev().attr("class","status-no").html("(×)");
		$(this).addClass("gray").siblings().removeClass("gray");
		return false;
	});


	
	//测试使用
	var _btn_showmodal = '<p class="tc">这是辅助开发按钮，所有的弹窗配备有和弹窗标题一致的注释<br>'
    	+ '<button class="btn btn-large btn-primary J_btn_showmodal" type="button"> 此页面的所有弹窗 </button>'
    + '</p>';
    if($("div.p_modal").length > 0){
    	$("div.iframe-content").append(_btn_showmodal);
    }

	$(".J_btn_showmodal").toggle(function(){
			$("div.p_modal").show();
		},
		function(){
			$("div.p_modal").hide();
		}
	);

});






