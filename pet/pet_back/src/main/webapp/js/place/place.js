$(function() {
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	$("#chk_all").click(function(){
		$("input[name='chk_list']").attr("checked",$(this).attr("checked"));
	});
	$("input[name='chk_list']").each(function () {
        $(this).click(function () {
            if ($("input[name='chk_list']:checked").length == $("input[name='chk_list']").length) {
                $("#chk_all").attr("checked", "checked");
            }
            else $("#chk_all").removeAttr("checked");
        });

    });
	$(".gl_table .seq_check").change(function(){
		$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
		$(this).parent("td").parent("tr").css({"background":"#f5f5f5"});
	})
	$(".p_table .seq_check").change(function(){
		$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
		$(this).parent("td").parent("tr").css({"background":"#f5f5f5"});
	})
	
	$(".tupian_table .seq_check").change(function(){
		$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
		$(this).parent("td").parent("tr").css({"background":"#f5f5f5"});
	})
	
	$(".gl_table .seq_check").blur(function(){		
		if(reg.test(this.value)){			
			if(this.value.substring(0,1)=="-"){					
				if(this.value.length>8){
					alert("排序值只能小于或等于7位的数字!!");					
					this.focus();				
				}
			}else{				
				if(this.value.length>7){
					alert("排序值只能小于或等于7位的数字!");
					this.focus();				
				}
			}			
		}else{			
			alert("排序值只能小于或等于7位的数字!!!");
			this.focus();			
		}
	});
	$("#scenic_add").click(function(){
		openWin("addPlaceName");
	});
	$("#hotel_add").click(function(){
		openWin("addPlaceName");
	});
	$("#btn_open_ok").click(function(){
		if($("#placeName").val()==""){
			alert("请输入名称!");
			$("#placeName").focus();
			return;
		}
		
		$.ajax({
			type:"post",
	        url:"isExistCheck.do",
	        data:"place.name="+$("#placeName").val()+"&stage="+$("#stage").val(),
	        error:function(){
	            alert("与服务器交互错误!请稍候再试!");
	        },
	        success:function(data){
	        	if(data=="Y"){
	        		alert("名称已存在！请更换名称再试!");
	        	}else if(data!="error"){
	        		alert("添加成功!");
	        		closeWin('addPlaceName');
	        		if($("#stage").val()=="1"){
	        			window.location.href="placeList.do?place.placeId="+data;
	        		}else if($("#stage").val()=="2"){
	        			window.location.href="scenicList.do?place.placeId="+data;
	        		}else{
	        			window.location.href="hotelList.do?place.placeId="+data;
	        		}
	        		
	        	}else{
	        		alert("添加失败，请稍后再试!");
	        	}
	        }
		});
		
	});
	
	$("#seq").blur(function(){
		if(!reg.test(this.value)){
			alert("排序值请输入数字!");
			this.focus();
		}
	});
	$("#auto_place").blur(function(){
    	if($("#auto_place").val()!=""){
    		$("#auto_place").val("");
    		$("input[name='place.parentPlaceId']").val("");
        }
    });
	$("#saveseq").click(function(){
		var arrChk=$("input[name='chk_list']:checked");
		var placeIds="";
		if(arrChk.length==0){
			alert("请选择要排序的记录!");
			return;
		}
		if(confirm("您确认要修改排序吗?")){
			$(arrChk).each(function(){
				placeIds+=this.value+"_"+$("#chk_list_"+this.value).val()+",";
			});
			if(placeIds!=""){
				placeIds=placeIds.substring(0,placeIds.length-1);
			}
			$.ajax({
				type:"post",
		        url:"saveSeq.do",
		        data:"placeIds="+placeIds,
		        error:function(){
		            alert("与服务器交互错误!请稍候再试!");
		        },
		        success:function(data){
		        	if(data=="success"){
		        		alert("修改成功!");
		        		$("input[name='chk_list']").attr("checked",false);
		        		$("#chk_all").attr("checked",false);
		        		$(".gl_table .seq_check").each(function(){
		        			$(this).parent("td").parent("tr").css({"background":"#fff"});
		        		});
		        		if($("#stage").val()=="1"){
		        			window.location.href="placeList.do";
		        		}else if($("#stage").val()=="2"){
		        			window.location.href="scenicList.do";
		        		}else{
		        			window.location.href="hotelList.do";
		        		}
		        	}else{
		        		alert("修改失败!");
		        	}
		        }
			});
		}
		
	});
	
	$("#savePhotoSeq").click(function(){
		var arrChk=$("input[name='chk_list']:checked");
		var placePhotoIds="";
		if(arrChk.length==0){
			alert("请选择要排序的记录!");
			return;
		}
		if(confirm("您确认要修改排序吗?")){
			$(arrChk).each(function(){
				placePhotoIds+=this.value+"_"+$("#chk_list_"+this.value).val()+",";
			});
			if(placePhotoIds!=""){
				placePhotoIds=placePhotoIds.substring(0,placePhotoIds.length-1);
			}
			$.ajax({
				type:"post",
		        url:"savePhotoSeq.do",
		        data:"placePhotoIds="+placePhotoIds,
		        error:function(){
		            alert("与服务器交互错误!请稍候再试!");
		        },
		        success:function(data){
		        	if(data=="success"){
		        		alert("修改成功!");
		        		$("input[name='chk_list']").attr("checked",false);
		        		$("#chk_all").attr("checked",false);
		        		$(".gl_table .seq_check").each(function(){
		        			$(this).parent("td").parent("tr").css({"background":"#fff"});
		        		});
		        		var photoUrl=$("#photoUrl").val();
		        		window.location.href=photoUrl;
		        	}else{
		        		alert("修改失败!");
		        	}
		        }
			});
		}
		
	});
	
	$("#saveProductSeq").click(function(){
		var arrChk=$("input[name='chk_list']:checked");
		var placeProductIds="";
		if(arrChk.length==0){
			alert("请选择要排序的记录!");
			return;
		}
		if(confirm("您确认要修改排序吗?")){
			$(arrChk).each(function(){
				placeProductIds+=this.value+"_"+$("#chk_list_"+this.value).val()+",";
			});
			if(placeProductIds!=""){
				placeProductIds=placeProductIds.substring(0,placeProductIds.length-1);
			}
			$.ajax({
				type:"post",
		        url:"saveProductSeq.do",
		        data:"placeProductIds="+placeProductIds,
		        error:function(){
		            alert("与服务器交互错误!请稍候再试!");
		        },
		        success:function(data){
		        	if(data=="success"){
		        		alert("修改成功!");
		        		$("input[name='chk_list']").attr("checked",false);
		        		$("#chk_all").attr("checked",false);
		        		$(".gl_table .seq_check").each(function(){
		        			$(this).parent("td").parent("tr").css({"background":"#fff"});
		        		});
		        		$('#productOrderForm').submit();
		        	}else{
		        		alert("修改失败!");
		        	}
		        }
			});
		}
		
	});
	
	$("input .date").datepicker({dateFormat:'yy-mm-dd',
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			buttonImageOnly: true
	});	

	$("input .date").live("focus",function(){
		$this.datepicker({dateFormat:'yy-mm-dd',
				changeMonth: true,
				changeYear: true,
				showOtherMonths: true,
				selectOtherMonths: true,
				buttonImageOnly: true
		});	
	});
	
});
function textCounter(field, maxlimit,tipsId) {
	if ($("#"+field).val().length > maxlimit){
		$("#"+field).val($("#"+field).val().substr(0, maxlimit));
	}else{
		$("#"+tipsId).html("还能输入"+(maxlimit - $("#"+field).val().length)+"汉字!");
	}
} 
function placeNameCheck(stage){
	if($("#placeName").val()!=""){
		if($("#placeName").val()!=$("#oldPlaceName").val()){
			$.ajax({
				type:"post",
		        url:"isExistCheck.do",
		        data:"place.name="+$("#placeName").val()+"&stage="+stage,
		        error:function(){
		        },
		        success:function(data){
		        	if(data=="Y"){
		        		alert("名称已存在！请更换名称再试!");
		        		$("#placeName").val($("#oldPlaceName").val());
		        	}
		        }
			});
		}
	}else{
		alert("名称不能空!");
	}
}