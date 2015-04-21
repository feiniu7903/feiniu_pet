
$(function(){
	
	function showFileInfo(data){
		var $form=$("#supplierAptitudeForm");
		var $opt=$form.find("select[name=dataType] :selected");
		
		var $input=$form.find("input[tt="+$opt.val()+"]");
		$input.val(data.file);
		var $div=$form.find("div.fileList");
		var txt=$opt.text();
		if($div.find("span[tt="+$opt.val()+"]").length==0){
			var body="<span tt='"+$opt.val()+"'>"+txt+"<a href='javascript:void(0)' class='deleteAptitudeFile'>删除</a>|";
			$div.append(body);
		}
		alert(txt+" 上传成功");
	}
	$("a.deleteAptitudeFile").click(function(){
		var $span=$(this).parents("span");
		var tt=$span.attr("tt");
		$("#supplierAptitudeForm").find("input[tt="+tt+"]").val("");
		$span.remove();
	});
	
	$("#uploadFile").fileUpload({onComplete:function(file,dt){
		var data=eval("("+dt+")");
		if(data.success){
			showFileInfo(data);
		}else{
			alert(data.msg);
		}
	}});
	$("input.aditudeSubmit").click(function(){
		var $form=$(this).parents("form");
		$form.validateAndSubmit(function($f,dt){		
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
			}else{
				alert(data.msg);
			}
		});
	});
});