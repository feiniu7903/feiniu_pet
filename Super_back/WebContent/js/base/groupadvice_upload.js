function checkForm(){
	if($.trim($("#upload_file").val())==''){
		alert("上传文件不可以为空");
		return false;
	}
	
	var file=$("#upload_file").val();
	if(file.lastIndexOf(".")==-1){
		alert("文件类型错误");
		return false;
	}else {
	   var point=file.lastIndexOf(".");
	   if(file.substr(point)!=".docx" && file.substr(point)!=".doc" && file.substr(point)!=".pdf"){
	      alert("请选择后缀名为“.pdf“,“.doc”或”.docx“的文件");
	      return false;
	   }
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