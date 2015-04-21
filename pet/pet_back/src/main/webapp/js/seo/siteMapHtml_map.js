function chkSiteMap(){
		var isCheck=true;
		if($("#xmlQuantity").val()==""){
			alert("请输入Xml数量");
			isCheck=false;
		}
		if(isNaN($("#xmlQuantity").val())){
			alert("请输入标准的Xml数量");
			isCheck=false;
		}
		if($("#xmlQuantity").val()<3000||$("#xmlQuantity").val()>10000){
			alert("生成Xml文件数量必须在3000到10000中");
			isCheck=false;
		}
		if(isCheck){
			$("#siteMap_sub").attr("disabled","true");
			$("#siteMap_div").html("[如Xml重新生成,请再次点击(网站地图)链接]正在生成文件,请稍候............");	
		}
		return isCheck;
}
