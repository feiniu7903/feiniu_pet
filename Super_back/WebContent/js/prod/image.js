$(function(){
	
	//移动表格
	function moveTd(result,type){
		var $tr=$("#tr_"+result);
		var $other;
		if(type=='up'){
			$other=$tr.prev("tr[type=big]");
			$tr.insertBefore($other);
		}else if(type=='down'){
			$other=$tr.next("tr[type=big]")
			$tr.insertAfter($other);	
		}
	}
	
	//移动图片的位置
	$("a.move").live("click",function(){
		var type=$(this).attr("tt");
		var result=$(this).attr("result");
		var $tr=$("#tr_"+result);
		var $other=null;
		if(type=='up'){
			$other=$tr.prev("tr[type=big]");			
		}else{
			$other=$tr.next("tr[type=big]");
		}
		if($other==null||$other=='undefined'||$.isEmptyObject($other)||$("tr[type=big]").size()==1){
			alert("现在已经无法移动该图片了!");
			return false;
		}
		$.post("/super_back/prod/moveImage.do",{"pictureId":result,"type":type},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				moveTd(result,type);
			}else if(data.code==-2){
				alert("现在已经无法移动该图片了");
			}
		});
	});
	
	$("a.delete").live("click",function(){
		var result=$(this).attr("result");
		$.post("/super_back/prod/deleteImage.do",{"pictureId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_"+result).remove();
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("select[name=type]").change(function(){
		var val=$(this).find(":selected").val();
		if(val=='BIG'){
			$("#bigNameLi").css("visibility","visible");
		}else{
			$("#bigNameLi").css("visibility","hidden");
		}
	});
	
	//填充页面
	function fillData(pic,icon){
		var $tr=$("<tr/>");
		if(icon){
			$tr=$("#product_icon");
			$tr.find("td.icon").html("<img src='http://pic.lvmama.com/pics/"+pic.filename+"'/>")
			$tr.show();
		}else{
			$tr.attr("id","tr_"+pic.pictureId);
			$tr.attr("type","big");
			var $td=$("<td/>");
			$td.html(pic.pictureId);
			$tr.append($td);
			
			$td=$("<td/>");
			$td.html(pic.imgname);
			$tr.append($td);
			
			$td=$("<td/>");
			$td.html("<img src='http://pic.lvmama.com/pics/"+pic.filename+"'/>");
			$tr.append($td);
			
			$td=$("<td/>");
			$td.html('<a href="#move" class="move" tt="up" title="图片上移" result="'+pic.pictureId+'">上移</a><a href="#move" class="move" tt="down" title="图片下移" result="'+pic.pictureId+'">下移</a><a href="#delete" class="delete" result="'+pic.pictureId+'">删除</a>');
			$tr.append($td);
			
			$("#image_tb").append($tr);
		}
		
		
	}
	var ajaxUpload;
	var fileInput=$("#uploadFile")
	$(document).ready(function(){
		ajaxUpload=new AjaxUpload(fileInput,{
			action:'/super_back/prod/saveImage.do',
			autoSubmit:false,
			name:'file',
			onSubmit:function(file,ext){
				if(ext) {
					ext = ext.toLowerCase();
				}
				if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)){
					var data={};
					var $form=$("#uploadForm");
					data["productId"]=current_product_id;
					data["type"]=$form.find("select[name=type] :selected").val();
					data["imgname"]=$form.find("input[name=imgname]").val();
					this.setData(data);
					this.disable();
					return true;
				}else{
					alert("文件格式错误");
					return false;
				}
			},
			onComplete:function(file,dt){
				var data=eval("("+dt+")");
				if(data.success){
					fillData(data,data.icon);
				}else{
					alert(data.msg);
				}
				this.enable();
			}
		});
	});
	$("#uploadFileBtn").click(function(){
		ajaxUpload.submit();
	})
})