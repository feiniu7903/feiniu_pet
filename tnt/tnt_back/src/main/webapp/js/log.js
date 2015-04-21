/******************
 * 日志页面.
 * 
 * 使用方式<a href="#log" class="showLogDialog" param={"objectId":XXXX,"objectType":"XXX"}">日志</a>
 */
$(function(){
	var log_param_data;
	var $log_dlg;
	$(".showLogDialog").live("click",function(){
		var param=$(this).attr('param');
		if($.trim(param)==''){
			return false;
		}
		
		log_param_data=eval("("+param+")");
		$log_dlg=$(this).data("log");
		if($log_dlg==null){
			$log_dlg=$("<div style='display:none'/>");
			$log_dlg.appendTo($("body"));
		}
		
		$.post("/super_back/log/loadLogs.do",log_param_data,function(dt){
			$.dialog({
				modal:true,
				width:840,
				title:"日志详情展示",
				content:$log_dlg.html(dt)
			});
		});		
	});
	$(".pagination a.page").live("click",function(){
		var tt=$(this).attr("tt");
		var page=$(this).attr("page");
		
		if(tt=='custom'){
			var tmp=$(".pagination input[name=page]").val();
			if($.trim(tmp)==''){
				alert("页码为空");
				return false;
			}
			page=parseInt(tmp);
			var total_page=parseInt($(".pagination input[name=page]").attr("totalPage"));
			if(isNaN(page)){
				alert("页码错误");
				return false;
			}
			if(page>total_page){
				alert("页数过大.");
				return false;
			}
			if(page<0){
				alert("页数要大于0");
				return false;
			}
		}
		
		log_param_data["page"]=page;
		
		
		$.post("/super_back/log/loadLogs.do",log_param_data,function(dt){
			$log_dlg.html(dt);
		});
	});
})