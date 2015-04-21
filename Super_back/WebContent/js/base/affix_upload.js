function open_upload_dialog(objectId,objectType){
	window.open("/super_back/common/upload.do?objectId="+objectId+"&objectType="+objectType,"upload_dialog","");
}

function checkForm(){
	if($.trim($("#upload_file").val())==''){
		alert("上传文件不可以为空");
		return false;
	}
	
	var file=$("#upload_file").val();
	if(file.lastIndexOf(".")==-1){
		alert("文件类型错误");
		return false;
	}
	
	if($.trim($("#affixNameId").val())==''){
		alert("文件名称不可以为空");
		return false;
	}
	if($.trim($("#upload_affix_memo").val())==''){
		alert("文件描述不可以为空");
		return false;
	}
	
	return true;
}

$(function(){
	$("a.delete_affix").click(function(){
		if(!confirm("您确定要删除该文件")){
			return false;
		}
		var result=$(this).attr("result");
		$.post(delete_op_url,{"affixId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_"+result).remove();
				alert("操作成功");
			}else{
				alert(data.msg);
			}
				
		});
	});
})