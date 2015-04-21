<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form action="${basePath}/place/uploadImg.do" id="uploadImageForm" name="uploadImageForm" method="post" enctype="multipart/form-data">
	<input name="toEditPicture.pictureId" value="${toEditPicture.pictureId}" type="hidden">
	<input id="imgUrl" value="http://pic.lvmama.com/pics/${toEditPicture.pictureUrl}" type="hidden">
	
	<input id="imgWidth"  type="hidden" value="<s:property value="#request.tempImgWidth"/>"/>
	<input id="imgHeight"  type="hidden" value="<s:property value="#request.tempImgHeight"/>">
	<img src="http://pic.lvmama.com/pics/${toEditPicture.pictureUrl}" style="display:none;" id="hiddenImgSrc">
	<input type="hidden" id="imgSizeStyle" value="<s:property value="#request.imgSizeStyle"/>">
	<div class="crop">
		<div style="float:left;">
			<div class="clear"></div>
			<input class="btn btn-small w5 hide"  type="button" value="恢复图片" id="restoreImage" ><br/><br/>
			<input class="btn btn-small w5"  type="button" value="保存图片" id="crop"><br/><br/>
			<div id="movement" style="width:80px"></div>
		</div>
		
		<div id="cropzoom_container"></div>
	</div>
</form>

<script type="text/javascript">
	$(function() {
		$("#uploadImageForm").ready(function(){
		$("#imgSizeStyle").val($("#imgSizeStyle").val());
		
		var tempImg = $("#imgUrl").val();
		var tempImgWidth = $("#hiddenImgSrc").width();
		var tempImgHeight = $("#hiddenImgSrc").height();

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
					animated : true,
					maxWidth : selectorWidth,
					maxHeight : selectorHeight,
					showPositionsOnDrag : true,
					showDimetionsOnDrag : true
				},
				image : {
					source : tempImg,
					width : tempImgWidth == "" ? selectorWidth : tempImgWidth,
					height : tempImgHeight == "" ? selectorHeight : tempImgHeight,
					minZoom : 10,
					maxZoom : 200,
					startZoom : 100,
					snapToContainer : true
				}
			});
		}
	
		if(tempImg!=""){
			$("#restoreImage").show();
			if($("#imgSizeStyle").val()=='L'){
				init_cropzoom(1000, 630, 900, 600);
				
			}else if($("#imgSizeStyle").val()=='M'){
				
				init_cropzoom(400, 300, 320 , 240);
				
			}else if($("#imgSizeStyle").val()=='S'){
				
				init_cropzoom(400, 210, 200 , 100);
				
			}
		}
		
		$("#restoreImage").click(function(){
			cropzoom.restore();
		});
	    
		$("#crop").click(function(){

	    	if(confirm("确定保存？")){
	    		cropzoom.send('${basePath}ebooking/product/saveCutEbkProdPicInit.do',
	    				'POST',
	    				{'imageSource':$("#imgUrl").val(),"toEditPicture.pictureId":$(":hidden[name='toEditPicture.pictureId']").val()},
	    				function(data){
	    					data = eval("("+data+")");
			    			if(data.success==true){
								alert("操作成功");
								$("img[currentpictureid="+$(":hidden[name='toEditPicture.pictureId']").val()+"]").attr("src","http://pic.lvmama.com/pics/"+data.fileUrl);
								$(".dialog-close").trigger("click");
							}else{
								alert("操作失败");
							}
	    				}
	   			);
    		};
		});
   		});
	});
</script>