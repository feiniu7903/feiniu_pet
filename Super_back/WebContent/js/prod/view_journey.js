$(function(){
	
	function fillProductJourneyForm($form,journey){	
		$form.find("input[name=viewJourney.productId]").val(journey.productId);
		$form.find("input[name=viewJourney.journeyId]").val(journey.journeyId);
		$form.find("input[name=viewJourney.multiJourneyId]").val(journey.multiJourneyId);
		$form.find("input[name=viewJourney.seq]").val(journey.seq);
		$form.find("input[name=viewJourney.title]").val(journey.title);
		$form.find("input[name=viewJourney.hotel]").val(journey.hotel);
		$form.find("textarea[name=viewJourney.content]").val(journey.content);
		//处理行程交通;
		if(journey.traffic != null && journey.traffic != '' && $.trim(journey.traffic) != ''){
			var trafficList = journey.traffic.split(",");
			$form.find("input[name=trafficListValues]").attr("checked",false);
			for(var i = 0 ; i < trafficList.length ; i++){
				var temp = trafficList[i];
				$form.find("input[name=trafficListValues][value="+temp+"]").attr("checked",true);
			}
		}
		//处理行程用餐;
		$form.find("input[name=viewJourney.dinner]").val(journey.dinner);
		/**if(journey.dinner != null && journey.dinner != '' && $.trim(journey.dinner) != ''){
			var dinner = journey.dinner.split(",");
			for(var i = 0 ; i < dinner.length ; i++){
				var temp = dinner[i];
				var dinnerInfo = temp.split("：");
				if($.trim(dinnerInfo[0]) == '早餐'){
					if(dinnerInfo[1] != null && dinnerInfo[1] != '' && $.trim(dinnerInfo[1]) != ''){
						$form.find("select[name=breakfast] option[value="+$.trim(dinnerInfo[1])+"]").attr("selected",true);
					}
				}
				if($.trim(dinnerInfo[0]) == '午餐'){
					if(dinnerInfo[1] != null && dinnerInfo[1] != '' && $.trim(dinnerInfo[1]) != ''){
						$form.find("select[name=lunch] option[value="+$.trim(dinnerInfo[1])+"]").attr("selected",true);
					}
				}
				if($.trim(dinnerInfo[0]) == '晚餐'){
					if(dinnerInfo[1] != null && dinnerInfo[1] != '' && $.trim(dinnerInfo[1]) != ''){
						$form.find("select[name=supper] option[value="+$.trim(dinnerInfo[1])+"]").attr("selected",true);
					}
				}
			}
		}*/
	}
	
	//编辑行程
	$("a.editJourney").live("click",function(){
		
		$('#showTips').empty();
		$('#showTips').hide();
		$('#showJourneyImages').empty();
		$('#showJourneyImages').hide();
		$('#saveButton').show();
		
		var $tr=$(this).parent().parent();
		var pk=$tr.attr("journeyId");
		$.post("/super_back/view/editJourney.do",{"journeyId":pk},function(dt){ 
			var data=eval("("+dt+")");
			if(data.success){
				var $form=$("form[name=productJourneyForm]");
				$form.find("input[type=hidden],input[type=text]").val('');
				if(data.productJourney != null){
					fillProductJourneyForm($form,data.productJourney);
					$("#addJourney").show();
					
					var sensitiveValidator=new SensitiveWordValidator($form, false);
					sensitiveValidator.validate();
				}else{
					alert('行程信息提取失败');
				}
			}else{
				alert(data.msg);
			}
		});
	});
	
	/** zx */
	$("#productJourneySaveButton").click(function(){
		//alert('s');
		var str=$(this).attr("ff");
		var $form=$("form[name="+str+"]");
		
		var productJourneySeq = $form.find("input[name=viewJourney.seq]").val();
		if($.trim(productJourneySeq)==''){
			alert("第几天不可以为空");
			return false;
		}
		
		var productJourneyTitle = $form.find("input[name=viewJourney.title]").val();
		if($.trim(productJourneyTitle)==''){
			alert("行程标题不可以为空");
			return false;
		}
		var productJourneyContent = $form.find("textarea[name=viewJourney.content]").val();
		if($.trim(productJourneyContent)!=''&&productJourneyContent.length>2000){
			alert("行程内容长度太长");
			return false;
		}
		/**
		var trafficListValues = $form.find("input[name=trafficListValues]:checked");
		if(typeof(trafficListValues)=='undefined'||trafficListValues.size()==0){
			alert("交通方式必须选中一个");
			return false;
		}
		
		
		var productJourneyDinner = $form.find("input[name=viewJourney.dinner]").val();
		if($.trim(productJourneyDinner)==''){
			alert("行程用餐不可以为空");
			return false;
		}
		
		var productJourneyHotel = $form.find("input[name=viewJourney.hotel]").val();
		if($.trim(productJourneyHotel)==''){
			alert("行程住宿不可以为空");
			return false;
		}
		*/
		var sensitiveValidator=new SensitiveWordValidator($form, true);
		if(!sensitiveValidator.validate()) {
			return;
		}
		$.ajax({
			url:$form.attr("action"),data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					alert("操作成功");
					window.location.reload();
					
					
					//$("span.msg").show();
					//if(data.hasNew == 'true'){
					//	$("span.msg").html('<font color="red">保存成功</font>').fadeOut(5000);
					//	addProductJourney(data);
					//}else{
					//	
					//}
					//cleanAddJourneyForm($form);
					//$('#addJourney').hide();
				}else{
					alert(data.msg);
				}
			}
		})
	});
	
	function addProductJourney(data){
		var $tr=$("<tr/>");
		$tr.attr("id","tr_"+data.productJourney.journeyId);
		$tr.attr("journeyId",data.productJourney.journeyId);
		$tr.attr("productId",data.productJourney.productId);
		
		var $td=$("<td/>");
		$td.html(data.productJourney.seq);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.productJourney.title);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.productJourney.dinner);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.productJourney.hotel);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.productJourney.trafficDesc);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.productJourney.content);
		$tr.append($td);
		
		$td=$("<td/>");
		var content = "<a href='#editJourney' class='editJourney'>修改</a>|<a href='view/doDelete.do?journeyId="+data.productJourney.journeyId+"&productId="+data.productJourney.productId+"'>删除</a>" +
				"|<a href='javascript:addTips("+data.productJourney.productId+","+data.productJourney.journeyId+")'>小贴士维护</a>|" +
				"<a href='javascript:uploadJourneyPic("+data.productJourney.productId+","+data.productJourney.journeyId+")'>上传图片</a>";
		$td.html(content);
		$tr.append($td);
		$("#productJourney").append($tr);
	}
	/**
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
				//var data=eval("("+dt+")");
				//if(data.success){
				//	$('#productJourneyPicName').val("");
				//	$('#uploadJourneyPic').hide();
				//	$("span.msg").show();
				//	$("span.msg").html('<font color="red">图片上传成功</font>').fadeOut(5000);
				//}
				//this.enable();
				
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
			
			$("#journeyImageTable").append($tr);
		}
	}
	
	
	$("#productJourneyUploadFileButton").click(function(){
		var $form=$("#uploadProductJourneyPicForm");
		var productJourneyPicName = $form.find("input[name=productJourneyPicName]").val();
		if($.trim(productJourneyPicName)==''){
			alert("图片名称不能为空");
			return false;
		}
		ajaxUpload.submit();
	})*/
	/**
	$("#saveTipButton").click(function(){
		var $form=$("#journeyTipForm");
		var journeyTipTitle = $form.find("input[name=viewJourneyTips.tipTitle]").val();
		if($.trim(journeyTipTitle)==''){
			alert("贴士标题不能为空");
			return false;
		}
		$.ajax({
			url:$form.attr("action"),data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					$("span.msg").show();
					$("span.msg").html('<font color="red">贴士保存成功</font>').fadeOut(5000);
					addJourneyTips(data);
					cleanAddJourneyTipsForm($form);
				}else{
					alert(data.msg);
				}
			}
		})
	});
	
	function addJourneyTips(data){
		var $tr=$("<tr/>");
		$tr.attr("id",data.journeyTips.tipId);
		
		var $td=$("<td/>");
		$td.html(data.journeyTips.tipTitle);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.journeyTips.tipContent);
		$tr.append($td);
		
		$td=$("<td/>");
		var content = "<a href='javascript:deleteJourneyTips("+data.journeyTips.journeyId+","+data.journeyTips.tipId+");'>删除</a>";
		$td.html(content);
		$tr.append($td);
		$("#journeyTipsTable").append($tr);
	}
	
	function cleanAddJourneyTipsForm($form){
		$form.find("input[name=viewJourneyTips.tipTitle]").val("");
		$('#tipContent').val("");
	}
	*/
	/** zx */
	
})

function showTipsList(productId,journeyId){
		$.post("/super_back/view/toViewJourneyTips.do",{"journeyId":journeyId,"productId":productId},function(dt){
			$('#showTips').html(dt);
			$('#showTips').show();
		});
}
/**
function deleteJourneyTips(journeyId,tipId){
	$.post("/super_back/view/doDeleteJourneyTips.do",{"journeyId":journeyId,"tipId":tipId},function(dt){
	var data=eval("("+dt+")");
	if(data.success){
		$("span.msg").show();
		$("span.msg").html('<font color="red">贴士删除成功</font>').fadeOut(5000);
			deleteJourneyTipFromTipList(tipId);
		}else{
			alert(data.msg);
		}
	});
}

function deleteJourneyTipFromTipList(tipId){
		$('#'+tipId).remove();
}
*/
function showJourneyImageList(productId,journeyId){
	$('#showJourneyImages').load("/super_back/view/toViewJourneyImages.do",{"journeyId":journeyId},function(dt){
		//$('#showJourneyImages').html(dt);
		$("div[iid=ajaxupload]").remove();//避免已经存在div
		$('#uploadJourneyPicJourneyId').val(journeyId);
		$('#uploadJourneyPicProductId').val(productId);
		$('#showJourneyImages').show();
	});
}

function cleanAddJourneyForm($form){
	$form.find("input[name=viewJourney.journeyId]").val("");
	$form.find("input[name=viewJourney.seq]").val("");
	$form.find("input[name=viewJourney.title]").val("");
	$form.find("input[name=trafficListValues]").attr("checked",false);
	$('#viewJourneyContent').val("上午\n\n下午\n\n晚上");
	$form.find("input[name=viewJourney.dinner]").val("");
	//$("#breakfast").val('含');
	//$("#lunch").val('含');
	//$("#supper").val('含');
	$form.find("input[name=viewJourney.hotel]").val("");
}