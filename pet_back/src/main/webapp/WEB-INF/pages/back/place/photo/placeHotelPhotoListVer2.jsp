<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	
</style>
<link href="<%=request.getContextPath() %>/css/place/photo.css" rel="stylesheet" type="text/css" >
<script type="text/javascript">
	$("#pic_list_dialog").bind("dialogbeforeclose",function(event,ui){
				var disPicNum = $("#placePhotoList li[alt='1']").length;
				if(disPicNum < 3 ){
					return confirm("列表中显示的图片少于3张,确定关闭？");
				}
	});
	function deletePlacePhoto(url, placePhotoId) {
		if (confirm("确定删除？")) {
			$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				success : function(result) {
					if (result.success) {
						alert(result.message);
						$("#" + placePhotoId).remove();
					} else {
						alert(result.message);
					}
				}
			});
		}
	}

	function picEditer(url, title, style, imgUrl, imgWidth, imgHeight) {
		$.ajaxDialog(url, title, "pic_edit_dialog", function() {

			$("#imgSizeStyle").val(style);
			
			if (typeof imgUrl != "undefined") {
				$("#imgUrl").val(imgUrl);
			}
			if (typeof imgWidth != "undefined") {
				$("#imgWidth").val(imgWidth);
			}
			
			if (typeof imgHeight != "undefined") {
				$("#imgHeight").val(imgHeight);
			}
			
			$.getScript("/pet_back/js/place/hotel_pic.js");

		});
	}
	function reopendPicListDialog(placeId) {
		$("#pic_list_dialog").unbind("dialogbeforeclose");
		$("#pic_list_dialog").dialog("close");
		$.ajaxDialog({
			url:"${basePath}/place/placePhotoListVer2.do?placePhoto.placeId="+placeId+"&stage=3",
			title:"酒店图片",
			id:"pic_list_dialog"
		});
	}
	
	function updatePlacePhotoDisplay(url, placeId) {
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success) {
					alert(result.message);
					reopendPicListDialog(placeId);
				} else {
					alert(result.message);
				}
			}
		});
	}
	var disChange = function() {
		if ($("input[type='radio']:checked").val() == "0") {
			$("#placePhotoList li[alt='']").show();
			$("#placePhotoList li[alt='1']").show();
		} else {
			$("#placePhotoList li[alt='']").hide();
		}
	};
</script>

</head>
<body>
<div class="box" style="width: 1000px;">
		<input type="hidden" id="dialog_auto_open" value="false"/>
		<div class="small-pic">
    	<h2><b>小图</b><small>（<font color="red">维护一张即可</font>尺寸：200*100；格式：png、jpg；文件大小<=5mb）</small></h2>
			<s:if test='placePhotoSmall == null'>
				<INPUT id="imgS" class="btn btn-small w5" type="button" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placeId=${placeId}&stage=3','图片上传','S')" value="上传"/>
			</s:if>
			<s:else>
				<div class="small-box">
					<img id="imgSmall" src='http://pic.lvmama.com${placePhotoSmall.imagesUrl}' width="200" height="100" />
				</div>
				<input class="btn btn-small w5" type="button" value="编辑" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placeId=${placePhotoSmall.placeId}&placePhotoIds=${placePhotoSmall.placePhotoId}&stage=3','图片上传','S')"/>
			</s:else>
		</div>
		<div class="middle-pic">
    	<h2><b>中图</b><small>（<font color="red">维护一张即可</font>尺寸：200*150；格式：png、jpg；文件大小<=5mb）</small></h2>
			<s:if test='placePhotoMid == null'>
				<INPUT id="imgM" class="btn btn-small w5" type="button" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placeId=${placeId}&stage=3','图片上传','M')" value="上传"/>
			</s:if>
			<s:else>
				<div class="middle-box">
					<img id="imgMid"  src='http://pic.lvmama.com${placePhotoMid.imagesUrl}' width="200" height="150" />
				</div>
				<input class="btn btn-small w5" type="button" value="编辑" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placeId=${placePhotoMid.placeId}&placePhotoIds=${placePhotoMid.placePhotoId}&stage=3','图片上传','M')"/>
			</s:else>
		</div>

	<div class="big-pic form-search form-inline">
    	<h2><b>大图</b>
    		<small>（<font color="red">必须上传>=3 张图片</font>尺寸：900*600；格式：png、jpg；文件大小<=5mb）
			<INPUT id="imgL" class="btn btn-small w5" type="button" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placeId=${placeId}&stage=3','图片上传','L')" value="上传"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT name="photoDisplay" id="photoDisplay1" type="radio" value="0" checked="checked"  onchange="disChange();"><label for="photoDisplay1" class="radio" >全部图片</label>
			<INPUT name="photoDisplay" id="photoDisplay2" type="radio" value="1" onchange="disChange();"><label for="photoDisplay2" class="radio">列表中显示的图片</label>
			</small>
		</h2>
		<ul id="placePhotoList">
		<s:iterator value="placePhotoLargerList" var="placePhotoLarger" status="st">
	        	<li id= "${placePhotoId}" alt="${placePhotoDisplay}">
	            	<span class="big-box"><img id='img_${st.index}' src="http://pic.lvmama.com${imagesUrl}" width="300" height="200" title="${placePhotoName}"/></span>
	                <h3>【${placePhotoType}】${placePhotoName}</h3>
					<input class="btn btn-small w5" type="button" value="删除" onclick="javascript:deletePlacePhoto('${basePath}/place/delete.do?placePhotoIds=${placePhotoId}&placeId=${placeId}', '${placePhotoId}')"/>
					<input class="btn btn-small w5" type="button" value="编辑" onclick="javascript:picEditer('${basePath}/place/placePhotoEdit.do?placePhotoIds=${placePhotoId}&placeId=${placeId}', '图片编辑','L')"/>
					<s:if test="placePhotoDisplay == 1">
						<input class="btn btn-small w5" type="button" value="不显示在列表" name="${placePhotoId}" onclick="javascript:updatePlacePhotoDisplay('${basePath}/place/updatePlacePhotoDisplay.do?placePhotoIds=${placePhotoId}&displayFlg=','${placeId}')"/>
					</s:if>
					<s:else>
						<input class="btn btn-small w5" type="button" value="显示在列表" name="${placePhotoId}" onclick="javascript:updatePlacePhotoDisplay('${basePath}/place/updatePlacePhotoDisplay.do?placePhotoIds=${placePhotoId}&displayFlg=1','${placeId}')"/>
					</s:else>
	            </li>
            
		</s:iterator>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(function(){
	//$("li[alt='']").hide();
	$("h3").each(function(){
		if($(this).html().length>30){
			$(this).html($(this).html().substring(0,30)+"...");
		}
	});
})
</script>
</body>
</html>