
$(function(){
	$("#uploadImgClick").click(function(){
		closeWin("uploadImg");
	});
});

function uploadImg(recommendInfoId){
	document.getElementById("uploadImgIframeObc").src=basePath+"/seo/recommendInfo!uploadImg.do?sonBlock.recommendBlockId="+$("#sonBlockId").val()+"&recommendInfo.recommendInfoId="+recommendInfoId;
	 $('#uploadImgIframeObc').load(function openIframeWin(){ 
		openWin("uploadImg");
	   $('#uploadImgIframeObc').unbind("load",openIframeWin); //移除事件监听
    }); 
}

function saveRecommendInfoForm(formObj){
	if(!checkLength(formObj)){
		return false;
	}
	saveRecommendInfo(formObj);
}

function saveRecommendInfo(formObj){
	       var modeType=$("#sonBlockModeType").val();
			if(modeType==4){
				var param = {"recommendInfo.memberPrice":$("#"+formObj+" #memberPrice").val(),
						"recommendInfo.marketPrice":$("#"+formObj+" #marketPrice").val(),
						"recommendInfo.recommendInfoId":$("#"+formObj+" #recommendInfoId").val(),
						"recommendInfo.seq":$("#"+formObj+" #seq").val(),
						"recommendInfo.url":$("#"+formObj+" #url").val(),
						"recommendInfo.imgUrl":$("#"+formObj+" #imgUrl").val(),
						"recommendInfo.title":$("#"+formObj+" #title").val(),
						"recommendInfo.recommendBlockId":$("#sonBlockId").val(),
						"recommendInfo.remark":$("#"+formObj+" #remark").val(),
						"recommendInfo.bakWord1":$("#"+formObj+" #bakWord1").val(),
						"recommendInfo.bakWord2":$("#"+formObj+" #bakWord2").val(),
						"recommendInfo.bakWord3":$("#"+formObj+" #bakWord3").val(),
						"recommendInfo.bakWord4":$("#"+formObj+" #bakWord4").val(),
						"recommendInfo.bakWord5":$("#"+formObj+" #bakWord5").val(),
						"recommendInfo.bakWord6":$("#"+formObj+" #bakWord6").val(),
						"recommendInfo.bakWord7":$("#"+formObj+" #bakWord7").val(),
						"recommendInfo.bakWord8":$("#"+formObj+" #bakWord8").val(),
						"recommendInfo.bakWord9":$("#"+formObj+" #bakWord9").val(),
						"recommendInfo.bakWord10":$("#"+formObj+" #bakWord10").val()
						};
			}else{
				var param = {"recommendInfo.recommendInfoId":$("#"+formObj+" #recommendInfoId").val(),
						"recommendInfo.seq":$("#"+formObj+" #seq").val(),
						"recommendInfo.url":$("#"+formObj+" #url").val(),
						"recommendInfo.imgUrl":$("#"+formObj+" #imgUrl").val(),
						"objectId":$("#"+formObj+" #recommObjectId").val(),
						"recommendInfo.recommObjectId":$("#"+formObj+" #recommObjectId").val(),
						"recommendInfo.title":$("#"+formObj+" #title").val(),
						"recommendInfo.recommendBlockId":$("#sonBlockId").val(),
						"recommendInfo.remark":$("#"+formObj+" #remark").val(),
						"recommendInfo.bakWord1":$("#"+formObj+" #bakWord1").val(),
						"recommendInfo.bakWord2":$("#"+formObj+" #bakWord2").val(),
						"recommendInfo.bakWord3":$("#"+formObj+" #bakWord3").val(),
						"recommendInfo.bakWord4":$("#"+formObj+" #bakWord4").val(),
						"recommendInfo.bakWord5":$("#"+formObj+" #bakWord5").val(),
						"recommendInfo.bakWord6":$("#"+formObj+" #bakWord6").val(),
						"recommendInfo.bakWord7":$("#"+formObj+" #bakWord7").val(),
						"recommendInfo.bakWord8":$("#"+formObj+" #bakWord8").val(),
						"recommendInfo.bakWord9":$("#"+formObj+" #bakWord9").val(),
						"recommendInfo.bakWord10":$("#"+formObj+" #bakWord10").val()
						};
			}
			$.ajax({type:"POST", url:basePath+"/seo/recommendInfo!saveRecommendInfo.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("更新成功了");
					endFunction($("#sonBlockId").val());
				}else{
					alert("更新出错！");		
				}
			}});
		 
}

function checkLength(formObj){
	   if(isNaN($("#"+formObj+" #seq").val())){
		   alert("排序值只能为数字"); 
			return false; 
	   }
	
	   if($("#"+formObj+" #title").val()!=""&&$("#"+formObj+" #title").val().length>500){ 
		   alert("标题不能超过500字"); 
		   return false; 
	   }
	   if($("#"+formObj+" #remark").val()!=""&&$("#"+formObj+" #remark").val().length>500){ 
					alert("简介不能超过500字"); 
					return false; 
		}
		if($("#"+formObj+" #bakWord1").val()!=""&&$("#"+formObj+" #bakWord1").val().length>350){ 
				alert("备用1不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord2").val()!=""&&$("#"+formObj+" #bakWord2").val().length>500){ 
				alert("备用2不能超过500字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord3").val()!=""&&$("#"+formObj+" #bakWord3").val().length>350){ 
				alert("备用3不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord4").val()!=""&&$("#"+formObj+" #bakWord4").val().length>350){ 
				alert("备用4不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord5").val()!=""&&$("#"+formObj+" #bakWord5").val().length>350){ 
				alert("备用5不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord6").val()!=""&&$("#"+formObj+" #bakWord6").val().length>350){ 
				alert("备用6不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord7").val()!=""&&$("#"+formObj+" #bakWord7").val().length>350){ 
				alert("备用7不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord8").val()!=""&&$("#"+formObj+" #bakWord8").val().length>350){ 
				alert("备用8不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord9").val()!=""&&$("#"+formObj+" #bakWord9").val().length>350){ 
				alert("备用9不能超过350字"); 
				return false; 
		}
		if($("#"+formObj+" #bakWord10").val()!=""&&$("#"+formObj+" #bakWord10").val().length>350){ 
				alert("备用10不能超过350字"); 
				return false; 
		}
		return true;
}