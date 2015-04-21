$(function(){
	$("input.date").datepicker({dateFormat:'yy-mm-dd'});
	
	//变更状态操作
	$("a.changeStatus").click(function(){
		if(!confirm("您确定要修改团的状态")){
			return false;
		}
		var result=$(this).attr("result");
		var v=$(this).attr("v");
		//取消的话需填写原因
		if(v == 'CANCEL') {
			$('#cancelReasonResult').val(result);
			$('#cancelReason').dialog( {
				width : 400,
				modal : true
			})
		} else {
			var $this=$(this);
			$.post(op_handle_url+"/change_status.do",{"travelGroupId":result,"status":v},function(dt){	
				var data=eval("("+dt+")");
				if(data.success){
					alert("操作成功");				
					$("#status_"+result).text($("#travelGroupStatus option[value="+v+"]").text());
					if(v=="END"){
						$("a[v=CONFIRM][result="+result+"]").remove();
						$("a[v=CANCEL][result="+result+"]").remove();
					}else if(v=="CONFIRM"){
						$("#make_time_"+result).text(data.makeTime);
					}else if(v=="CANCEL"){
						$("a[v=CONFIRM][result="+result+"]").remove();
						$("a[v=END][result="+result+"]").remove();
					}
					$this.remove();
				}
			});
		}
	});
	
	$('#cancelReasonBtn').live('click', function() {
		var desc = $.trim($('#cancelReasonDesc').val());
		if(desc == '') {
			alert("必须填写取消原因！");
			return false;
		}
		var result = $('#cancelReasonResult').val();
		$.post(op_handle_url+"/change_status.do",{"travelGroupId":result,"status":'CANCEL',"memo": desc},function(dt){	
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				$('#cancelReason').dialog("close");
				$("#status_"+result).text($("#travelGroupStatus option[value='CANCEL']").text());
				$("a[v=CONFIRM][result="+result+"]").remove();
				$("a[v=END][result="+result+"]").remove();
				$("a[result='"+result+"'][v='CANCEL']").remove();
				$('#cancelReasonDesc').val("");
			}
		});
	});
	
	//修改团的可发通知书状态
	$("a.groupwordabled").click(function(){
		if(!confirm("确定本团可以发通知书了吗？")){
			return false;
		}
		var $this=$(this);
		var result=$this.attr("result");
		$.post(op_handle_url+"/change_groupword_abled.do",{"travelGroupId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("变更完成");
				$this.remove();
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("a.sort").click(function(){
		var result=$(this).attr("result");
		$("#sort").val(result);
		document.getElementById("search_form").submit();
	});
	
	//修改计划人数
	$("a.change_initial_num").click(function(){
		var result=$(this).attr("result");
		var current=$(this).attr("current_num");
		var $this=$(this);
		var tg_code=$("#code_"+result).text();
		var $dlg=$("#change_initial_div");
		$dlg.find("span.msg").empty();
		$dlg.find("td.title").text("更改团号:"+tg_code+" 的计划人数");
		$dlg.find("input[name=num]").val(current);
		$dlg.dialog({
			modal:true,
			title:"变更团计划人数",
			width:400,
			buttons:{				
				"保存":function(){
					var count=$dlg.find("input[name=num]").val();
					if(count<-1){
						$dlg.find("span.msg").text("数据不可以小于-1");
					}else
					{
						$dlg.find("span.msg").empty();
					}
						
					$.post(op_handle_url+"/initial_group_num.do",{"travelGroupId":result,"count":count},function(dt){
						var data=eval("("+dt+")");
						if(data.success){
							$this.attr("current_num",count);
							$("#inital_group_num_"+result).text(count<0?"不限":count);	
							$("#remain_"+result).text(data.remain);
							if(status=='CONFIRM'){
								$("#make_time_"+result).text(data.makeTime);
							}
							$dlg.dialog("close");
						}else
						{
							$dlg.find("span.msg").text(data.msg);
						}
					});
				},
				"取消":function(){
					$dlg.dialog("close");
				}
			}
		});
	});
})