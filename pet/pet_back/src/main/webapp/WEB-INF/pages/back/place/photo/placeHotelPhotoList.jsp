<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript">

$("#savePhotoSeq").click(function(){
	var  arrChk=$("input[name='chk_list']:checked");
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
	        		$("#uploadFormDiv").hide();
	        		reSearchPlacePhotoTable();	
	        	}else{
	        		alert("修改失败!");
	        	}
	        }
		});
	}
	
});
//
	$('#bigImageButton').click(function() {
		$('#message').html("亲！！！开始上传大图了");
		$('#uploadFormDiv').show();
		$('#type').val('LARGE');
		$('#placePhotoId').val();
	});
	$('#middleImageButton').click(function() {
		var tr=$("#image-table-list").find("tr[data-type='MIDDLE']");
		if(tr.length>=1){
			$('#uploadFormDiv').hide();
			alert('中图只能上传一张');
			return;
		}
		
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！开始上传中图了，注意大小！");
		$('#type').val('MIDDLE');
		$('#placePhotoId').val();
	});
	$('#smallImageButton').click(function() {
		var tr=$("#image-table-list").find("tr[data-type='SMALL']");
		if(tr.length>=1){
			$('#uploadFormDiv').hide();
			alert('小图只能上传一张');
			return;
		}
		
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！开始上传小图了，注意大小！");
		$('#type').val('SMALL');
		$('#placePhotoId').val();
	});
	function changePhoto(id){
		$('#uploadFormDiv').show();
		$('#message').html("亲！！！你正在做的是，图片替换哦！");
		$('#placePhotoId').val(id);
	  }
	function reomovePhoto(placeId,photoId){
		if(confirm("确定要删除该图片吗?")){
			url="${basePath}/place/delete.do?placePhoto.placeId="+placeId+"&placePhoto.placePhotoId="+photoId+"&stage=2";
			doAjax(url);
		}
	};
	
	//提交之后，重刷新table
	function checkAndSubmit(url,form) {
	if($("#imagePath").val()!=""){
		var options = {
				url:url,
				type : 'POST',
				dataType:'json',
	 			success:function(data){
	          		   if(data.success==true) {
						 alert("操作成功!");
						 reSearchPlacePhotoTable();	
						 $("#imagePath").val("");
						 } else {
						  alert("操作失败："+data.message);
					    }
	 			},
				error:function(){
	                     alert("出现错误");
	                 }
			};
		$('#'+form).ajaxSubmit(options);
	}else{
		alert("请选择上传图片");
	}
	
}
	function reSerachTable(url,div){
		var optionsTable = {
				url:url,
				type : 'POST',
				dataType: "html",
				success:function(html,textStatus){
               	if(textStatus=="success"){
               		 $("#"+div).html(html);
					}
				},
				error:function(){
                    alert("error");
               }
			};
	   $.ajax(optionsTable);
	}
	//post处理，重刷新
	function doAjax(url) {
		var options = {
				url:url,
				type : 'POST',
				dataType:'json',
	 			success:function(data){
	          		   if(data.success==true) {
	          			 alert("操作成功："+data.message);
	          			reSearchPlacePhotoTable();						
	          		   } else {
						  alert("操作失败："+data.message);
					    }
	 			},
				error:function(){
	                     alert("出现错误");
	                 }
			};
		
		$.ajax(options);
	}
	function reSearchPlacePhotoTable(){
		 var placeId=$('input[name="placePhoto.placeId"]').val();
		 var stage=$('input[name="stage"]').val();
		 $('#uploadFormDiv').hide();
		 reSerachTable('${basePath}/place/placeHotelPhotoListTable.do?placePhoto.placeId='+placeId+'&stage='+stage,'photo_table_div');
	}
</script>

</head>
<body>
<div class="iframe-content">
	<div class="p_box" style="display: block;">
	<input id="photoUrl" type="hidden" value="/pet_back/place/placePhotoList.do?placePhoto.placeId=${placePhoto.placeId}&stage=${stage}">
		<div class="p_top">景点：大图:530*270  中图:200*150  小图:200*100<br/>酒店：大图600*230 中图200*150 小图200*100 </div>
		<div class="uploadDiv" style="display:none;" id="uploadFormDiv">
			<form action="${basePath}/place/saveOrUpdatePlacePhoto.do" id="uploadImageForm" name="uploadImageForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="placePhoto.placePhotoId" value="" id="placePhotoId">
				<input type="hidden" name="placePhoto.type" value="" id="type">
				<input type="hidden" name="stage" value='${stage}'>
				<input type="hidden" name="placePhoto.placeId" value='${placePhoto.placeId}' id="placeId">
			上传图片：<input type="file" name="placePhoto.imagePath" id="imagePath"> <span>请按照所选的尺寸上传,尺寸错误将照成前台显示混乱！</span>
			<br/>
			<input  class="btn btn-small w5" type="button" value="保存" onclick="javascript:checkAndSubmit('${basePath}/place/saveOrUpdatePlacePhoto.do','uploadImageForm');">
			</form></div>
		<span id="message" style="color:red;">${message}</span>
		<div class="p_box" >
			 <input class="btn btn-small w5"  type="button" value="保存排序" id="savePhotoSeq"> 
			 <input class="btn btn-small w5"  type="button" value="上传大图" id="bigImageButton"> 
			 <input class="btn btn-small w5"  type="button" value="上传中图" id="middleImageButton"> 
			 <input class="btn btn-small w5"  type="button" value="上传小图" id="smallImageButton"> 
		</div>
		<div id="photo_table_div">
 		    <%@ include file="./placeHotelPhotoListTable.jsp"%>
		</div>
	</div>
	<div class="img_big">
		<img src="">
	</div>
</div>
</body>
</html>