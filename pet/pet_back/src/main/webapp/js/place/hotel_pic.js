$(function(){
	
	var cropzoom;
	var init_cropzoom = function(cropzoomWidth,cropzoomHeight,selectorWidth,selectorHeight){
		cropzoom = $("#cropzoom_container").cropzoom({
			width : cropzoomWidth,
			height : cropzoomHeight,
			bgColor : '#CCC',
			enableRotation : true,
			enableZoom : true,
			expose:{
                elementMovement:'#movement'
            },
			selector : {
				w : selectorWidth,
				h : selectorHeight,
				centered : true,
				borderColor : 'blue',
				borderColorHover : 'red',
				animated : false,
				maxWidth : selectorWidth,
				maxHeight : selectorHeight,
				showPositionsOnDrag : false,
				showDimetionsOnDrag : false
			},
			image : {
				source : tempImg,
				width : tempImgWidth == "" ? selectorWidth : tempImgWidth,
				height : tempImgHeight == "" ? selectorHeight : tempImgHeight,
				minZoom : 10,
				maxZoom : 200,
				startZoom : 100,
				snapToContainer : false
			}
		});
	}
	$("#imagePath").change(function(){
		$("#uploadImg-btn").click();
	});
	$("#uploadImg-btn").click(function(){
		if($('#imagePath').val()==""){
			$("#imagePath").click();
			return;
		}
	
		var val= $("#imagePath").val();
		var k = val.substr(val.indexOf("."));
		if(!(k.toLowerCase()==".jpg" || k.toLowerCase()==".png")){
		    alert("上传图片格式为jpg或者png");
		    return;
		}
		$("#uploadImageForm").ajaxSubmit({
			type: 'post',
			url:  '/pet_back/place/uploadImg.do',
			dataType:  'json',
			success: function(data) {
				if(data.success==true){
					var photeId = $("input[name='placePhoto.placePhotoId']").val();
					var placeId = $("input[name='placePhoto.placeId']").val();
					var imgSizeStyle = $("#imgSizeStyle").val();
					$("#pic_edit_dialog").dialog("close");
					if(photeId!=""){
						if(imgSizeStyle=='L'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&placePhotoIds='+ photeId +'&stage=3','图片编辑','L','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}else if(imgSizeStyle=='M'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&placePhotoIds='+ photeId +'&stage=3','图片编辑','M','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}else if(imgSizeStyle=='S'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&placePhotoIds='+ photeId +'&stage=3','图片编辑','S','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}
					}else{
						if(imgSizeStyle=='L'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&stage=3','图片上传','L','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}else if(imgSizeStyle=='M'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&stage=3','图片上传','M','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}else if(imgSizeStyle=='S'){
							picEditer('${basePath}/place/placePhotoEdit.do?placeId='+placeId+'&stage=3','图片上传','S','/pet_back/'+data.sourceImg,data.sourceImgWidth,data.sourceImgHeight);
						}
					}
				}else{
					alert("上传失败");
				}
			}
	    });			
	});
	var placeId = $("input[name='placePhoto.placeId']").val();
	var tempImg = $("#imgUrl").val();
	var tempImgWidth = $("#imgWidth").val();
	var tempImgHeight = $("#imgHeight").val();
	
	
	if($("#imgSizeStyle").val()=='L'){
		$("#photoTypeReal").val("LARGE");
	}else if($("#imgSizeStyle").val()=='M'){
		$("#photoTypeReal").val("MIDDLE");
	}else if($("#imgSizeStyle").val()=='S'){
		$("#photoTypeReal").val("SMALL");
	}
	if($("#placePhotoTypeTemp").val()==''){
		$("#placePhotoType option[value='']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='外观'){
		$("#placePhotoType option[value='外观']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='内景'){
		$("#placePhotoType option[value='内景']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='大堂'){
		$("#placePhotoType option[value='大堂']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='前台'){
		$("#placePhotoType option[value='前台']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='客房'){
		$("#placePhotoType option[value='客房']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='餐厅'){
		$("#placePhotoType option[value='餐厅']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='会议室'){
		$("#placePhotoType option[value='会议室']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='娱乐休闲设施'){
		$("#placePhotoType option[value='娱乐休闲设施']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='商务中心'){
		$("#placePhotoType option[value='商务中心']").attr("selected", true);
	}else if($("#placePhotoTypeTemp").val()=='其他'){
		$("#placePhotoType option[value='其他']").attr("selected", true);
	}
	if(tempImg!=""){
		$("#restoreImage").show();
		if(tempImgWidth  !="" ){//上传成功之后隐藏上传图片按钮
			$("#uploadImg-btn").hide();
		}else{
			$("#uploadImg-btn").val("更换图片");
		}
		if($("#imgSizeStyle").val()=='L'){
			
			init_cropzoom(1000, 630, 900, 600);
			
		}else if($("#imgSizeStyle").val()=='M'){
			
			init_cropzoom(400, 300, 200 , 150);
			
		}else if($("#imgSizeStyle").val()=='S'){
			
			init_cropzoom(400, 210, 200 , 100);
			
		}
	}
	
	$("#restoreImage").click(function(){
		cropzoom.restore();
	});
    
    $("#crop").click(function(){
    	if(confirm("确定保存？")){
    		if($("#placePhotoType").val()==""){
        		alert("图片类型必须选择！");
        		return;
        	}
    		if($("#seq").val()==""){
        		alert("排序值不能为空！");
        		return;
        	}else if(!/^[0-9]*$/.test($("#seq").val())){
        		alert("排序值只能输入数字！");
        		return;
        	}
        	if($("#fileName").val()==""){
        		alert("图片名字不能为空！");
        		return;
        	}
        	if($("#cropzoom_container").html()!=""){
        		//新增图片信息
        		cropzoom.send('/pet_back/crop.jsp','POST',{'imageSource':$("#imgUrl").val().replace("/pet_back/","")},function(imgRet){
        			if(imgRet.indexOf("jpg")!=-1){
        				$('#uploadImageForm').ajaxSubmit({
        					type: 'post',
        					url:  '${basePath}/place/saveOrUpdatePlacePhotoVer2.do',
        					dataType:  'json',
        					success: function(data) {
        						if(data.success==true){
        							alert("操作成功");
        							$("#pic_edit_dialog").dialog("close");
        							reopendPicListDialog(placeId);
        						}else{
        							alert("操作失败");
        						}
        					}
        			    }); 
        			}
            	    	
                });  
        	}else{
        		//更新图片信息
        		if($("input[name='placePhoto.placePhotoId']").val()!=""){
        			$('#uploadImageForm').ajaxSubmit({
    					type: 'post',
    					url:  '${basePath}/place/saveOrUpdatePlacePhotoVer2.do',
    					dataType:  'json',
    					success: function(data) {
    						if(data.success==true){
    							alert("操作成功");
    							$("#pic_edit_dialog").dialog("close");
    							reopendPicListDialog(placeId);
    						}else{
    							alert("操作失败");
    						}
    					}
    			    }); 
        		}else{
            		alert("新增图片信息时，请先上传新图片！");
            		return;	
        		}
        	}
    	}
   });
});