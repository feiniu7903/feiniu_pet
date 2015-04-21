$(function(){
	var ajaxUpload;
	var fileInput=$("#uploadFile");
	$(document).ready(function(){
		ajaxUpload=new AjaxUpload(fileInput,{
			action:$('#uploadProductJourneyPicForm').attr("action"),
			autoSubmit:false,
			name:'file',
			onSubmit:function(file,ext){
				var $form=$("#uploadProductJourneyPicForm");
				if(ext) {
					ext = ext.toLowerCase();
				}
				if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)){
					var data={};
					data["uploadJourneyPicJourneyId"]=$form.find("input[name=uploadJourneyPicJourneyId]").val();
					data["type"]="BIG";
					data["productJourneyPicName"] = $form.find("input[name=productJourneyPicName]").val();
					data["uploadJourneyPicProductId"] = $form.find("input[name=uploadJourneyPicProductId]").val();
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
					$('#productJourneyPicName').val("");
					$("span.msg").show();
					$("span.msg").html('<font color="red">图片上传成功</font>').fadeOut(5000);
					fillIamgeData(data,data.icon);
				}else{
					alert(data.msg);
				}
				this.enable();
			}
		});
	});
	
	//填充页面
	function fillIamgeData(pic,icon){
		var $tr=$("<tr/>");
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
		var content = "<a href='javascript:deleteJourneyImages("+pic.pictureId+");'>删除</a>";
		$td.html(content);
		
		$tr.append($td);
		
		$("#journeyImageTable").append($tr);
	}
	
	$("#productJourneyUploadFileButton").click(function(){
		var $form=$("#uploadProductJourneyPicForm");
		var productJourneyPicName = $form.find("input[name=productJourneyPicName]").val();
		if($.trim(productJourneyPicName)==''){
			alert("图片名称不能为空");
			return false;
		}
		ajaxUpload.submit();
	});
	
	$("#showJourneyImages").bind("changeHtml",function(){
		if(ajaxUpload){
			ajaxUpload.hide();			
		}
	});
})

function deleteJourneyImages(pictureId){
	$.post("/super_back/view/toDeleteJourneyImages.do",{"pictureId":pictureId},function(dt){
		var data=eval("("+dt+")");
		if(data.success){
			$("span.msg").show();
			$("span.msg").html('<font color="red">图片删除成功</font>').fadeOut(5000);
			$("#tr_"+pictureId).remove();
		}else{
			alert(data.msg);
		}
	});
}
