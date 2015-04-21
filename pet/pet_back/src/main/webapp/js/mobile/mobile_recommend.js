

$(function(){
	$("#uploadImgClick").click(function(){
		closeWin("uploadImg");
	});
});

// 打开上传页面 
function uploadImg(recommendInfoId){
	document.getElementById("uploadImgIframeObc").src=basePath+"/mobile/mobileRecommendInfo!uploadImg.do?sonBlock.id="+$("#sonBlockId").val()+"&mobileRecommendInfo.id="+recommendInfoId;
	 $('#uploadImgIframeObc').load(function openIframeWin(){ 
		openWin("uploadImg");
	   $('#uploadImgIframeObc').unbind("load",openIframeWin); //移除事件监听
    }); 
}

//打开HD上传页面 
function uploadHDImg(recommendInfoId){
	document.getElementById("uploadImgIframeObc").src=basePath+"/mobile/mobileRecommendInfo!uploadHDImg.do?sonBlock.id="+$("#sonBlockId").val()+"&mobileRecommendInfo.id="+recommendInfoId;
	 $('#uploadImgIframeObc').load(function openIframeWin(){ 
		openWin("uploadImg");
	   $('#uploadImgIframeObc').unbind("load",openIframeWin); //移除事件监听
    }); 
}


// 推荐目的地.新增 or 修改 recommendInfo信息 
/**
 * formObj  form id 
 * id       block的id
 * type     类别 ： 首页推荐index ，自由行freeTour ，目的地dest 
 */
function saveRecommendInfoForm(formObj,id,type){
	if(null == id ) {
		id = "";
	}
	
	// 提交自由行表单 
	if("dest" == type) {
		saveRecommendInfoDest(formObj,id);
	} else {
		if(!checkLength(formObj,id)){
			return false;
		}
		saveRecommendInfo(formObj,id,type);
	}
	
}

// 保存首页推荐 
function saveRecommendInfo(formObj,id,type){
	var param = {"mobileRecommendInfo.id":$("#"+formObj+" #id").val(),
				"mobileRecommendInfo.seqNum":$("#"+formObj+" #seq").val(),
				"mobileRecommendInfo.url":$("#"+formObj+" #url").val(),
				"mobileRecommendInfo.hdUrl":$("#"+formObj+" #hdUrl").val(),
				"mobileRecommendInfo.isValid":$("#"+formObj+" #isValid").val(),
				"mobileRecommendInfo.recommendHDImageUrl":$("#"+formObj+" #imgHDUrl").val(),
				"mobileRecommendInfo.recommendImageUrl":$("#"+formObj+" #imgUrl").val(),
				"mobileRecommendInfo.beginDate":$("#"+formObj+" #beginDate"+id).val(),
				"mobileRecommendInfo.endDate":$("#"+formObj+" #endDate"+id).val(),
				"mobileRecommendInfo.recommendTitle":$("#"+formObj+" #recommendTitle").val(),
				"mobileRecommendInfo.recommendBlockId":$("#sonBlockId").val(),
				"mobileRecommendInfo.recommendContent":$("#"+formObj+" #recommendContent").val(),
				"pageChannel":type
	           };
	$.ajax({type:"POST", url:basePath+"/mobile/mobileRecommendInfo!saveMobileRecommendInfo.do", data:param, dataType:"json", success:function (data) {
		if(data.flag=='true'){
			alert(data.msg);
			endFunction($("#sonBlockId").val(),"index");
			// 关闭弹出层 . 
			parent.closeWin('getRecommendInfoSource');
		}else{
			alert("更新出错！");		
		}
	}});
}

// 更新 type :freeTour自由行 ；dest：目的地
function updateRecommendInfo(formObj,id,type){
	var longitude = $("#"+formObj+" #longitude").val();
	var latitude = $("#"+formObj+" #latitude").val();
	var imgUrl = $("#"+formObj+" #imgUrl").val();
	var price = $("#"+formObj+" #price").val();
	var recommendContent = $("#"+formObj+" #recommendContent").val();
	if(!isStringEmpty(longitude)) {
		longitude = $.trim(longitude);
	}
	if(!isStringEmpty(latitude)) {
		latitude = $.trim(latitude);
	}
	var imgHDUrl = $("#"+formObj+" #imgHDUrl").val();
	if(isStringEmpty(imgHDUrl)) {
		// alert("HD图片地址不能空!");
		 //return false;
		 imgHDUrl = "";
	}
	if(isStringEmpty(imgUrl)) {
		 alert("图片地址不能空!");
		 return false;
	}
	var seq = $("#"+formObj+" #seq").val();
	if(isStringEmpty(seq)) {
		 alert("排序值不能为空!");
		 return false;
	}
	if(isNaN(seq)){
		   alert("排序值只能为数字"); 
		   return false; 
	}
	
	// 目的地 
	var tag ='';
	if('dest' == type) {
		tag = $("#"+formObj).find('input[name="tag"]:checked').val();
		if(isStringEmpty(tag)) {
			alert('标签不能为空!');
			return false;
		}
	}
	var param = {"mobileRecommendInfo.id":id,
				"mobileRecommendInfo.longitude":longitude,
				"mobileRecommendInfo.latitude":latitude,
				"mobileRecommendInfo.recommendHDImageUrl":imgHDUrl,
				 "mobileRecommendInfo.recommendImageUrl":imgUrl,
				 "mobileRecommendInfo.seqNum":seq,
				 "mobileRecommendInfo.price":price,
				 "mobileRecommendInfo.recommendContent":recommendContent,
				 "mobileRecommendInfo.tag":tag
	             };
	$.ajax({type:"POST", url:basePath+"/mobile/mobileRecommendInfo!updateRecommendInfoSeq.do", data:param, dataType:"json", success:function (data) {
		if(data.flag=='true'){
			alert(data.msg);
			endFunction($("#sonBlockId").val(),"freeTour");
		}else{
			alert("更新出错！");		
		}
	}});
}

// mobileRecommendInfo表单长度校验  
function checkLength(formObj,id){
	   var seq = $("#"+formObj+" #seq").val(); // 排序值 
	   var title = $("#"+formObj+" #recommendTitle").val(); // 标题
	   var startDate = $("#"+formObj+" #beginDate"+id).val();
	   var endDate = $("#"+formObj+" #endDate"+id).val();
	   if(null == title || $.trim(title) == '') {
		   alert('标题不能为空!');
		   return false;
	   }
	   if(null == seq || $.trim(seq) == '') {
		   alert('排序值不能为空!');
		   return false;
	   }
	   if(null == startDate || null == endDate || $.trim(startDate) == '' || $.trim(endDate) == '') {
		   alert('有效期范围不能为空!');
		   return false;
	   } else if(!dataCompare(startDate,endDate)) { 
		   alert('结束日期必须大于开始日期!');
		   return false;
	   }
	   
	   if(isNaN(seq)){
		   alert("排序值只能为数字"); 
		   return false; 
	   }
	   if(title !="" && title.length>32){ 
		   alert("标题不能超过26汉字"); 
		   return false; 
	   }
	   if($("#"+formObj+" #recommendContent").val()!=""&&$("#"+formObj+" #recommendContent").val().length>100){
					alert("内容不能超过50汉字"); 
					return false; 
		}
		return true;
}

	function endFunction(id,pageChannel){
		parent.document.getElementById("iframeObc").src=basePath+"/mobile/mobileRecommendInfo.do?recommendBlockId="+id+"&pageChannel="+pageChannel+"&"+new Date();
	}
	
	// 获取选择的checkbox值 
	function getCheckedValue(id){  //jquery获取复选框值    
		  var chk_value =[];    
		  $('input[name='+id+']:checked').each(function(){    
			  chk_value.push($(this).val());    
		  });
		  return chk_value.join(",");
	} 

	// 绑定formId, 目的地id ，标题 ，类别  
	function bindDest2departure(formObj,placeId,title,stage,pageChnnel){
		var tags ,param={};
		var seq = $("#seq").val();
		if(null == seq || "" == seq) {
			alert('排序值不能为空!');
			return false;
		}
		if(pageChnnel == 'dest') {  // 目的地参数. 
			tags =  getCheckedValue('tag');
			if(null == tags || "" == tags) {
				alert('请选择标签');
				return false;
			}
			param = {"mobileRecommendInfo.id":$("#"+formObj+" #id").val(),
					"mobileRecommendInfo.seqNum":$("#seq").val(),
					"mobileRecommendInfo.hdUrl":$("#"+formObj+" #hdUrl").val(),
					"mobileRecommendInfo.url":$("#"+formObj+" #url").val(),
					"mobileRecommendInfo.isValid":$("#"+formObj+" #isValid").val(),
					"mobileRecommendInfo.recommendHDImageUrl":$("#imgHDUrl").val(),
					"mobileRecommendInfo.recommendImageUrl":$("#imgUrl").val(),
					"mobileRecommendInfo.recommendTitle":title,
					"mobileRecommendInfo.recommendBlockId":$("#"+formObj+" #recommendBlockId").val(),
					"mobileRecommendInfo.recommendContent":$("#"+formObj+" #recommendContent").val(),
					"mobileRecommendInfo.objectId":placeId,
					"mobileRecommendInfo.objectType":stage,
					"mobileRecommendInfo.tag":tags,
					"pageChannel":"dest"
		           };
		} else if(pageChnnel == 'freeTour') { // 自由行 参数 
			tags =  $("#imgUrl").val();
			var longitude = $("#longitude").val();
			var latitude = $("#latitude").val();
			var price = $("#"+formObj+" #price").val();
			var recommendContent = $("#"+formObj+" #recommendContent").val();
			
			if(!isStringEmpty(longitude)) {
				longitude = $.trim(longitude);
			}
			if(!isStringEmpty(latitude)) {
				latitude = $.trim(latitude);
			} 
			var imgHDUrl = $("#imgHDUrl").val();
			if(null == tags || "" == tags) {
				alert('请选择图片');
				return false;
			}
/*			if(null == imgHDUrl || "" == imgHDUrl) {
				alert('请选择HD图片');
				return false;
			}*/
			param = {"mobileRecommendInfo.id":$("#"+formObj+" #id").val(),
					"mobileRecommendInfo.seqNum":$("#seq").val(),
					"mobileRecommendInfo.hdUrl":$("#"+formObj+" #hdUrl").val(),
					"mobileRecommendInfo.url":$("#"+formObj+" #url").val(),
					"mobileRecommendInfo.isValid":$("#"+formObj+" #isValid").val(),
					"mobileRecommendInfo.recommendHDImageUrl":imgHDUrl,
					"mobileRecommendInfo.recommendImageUrl":tags,
					"mobileRecommendInfo.recommendTitle":title,
					"mobileRecommendInfo.recommendBlockId":$("#"+formObj+" #recommendBlockId").val(),
					"mobileRecommendInfo.recommendContent":$("#"+formObj+" #recommendContent").val(),
					"mobileRecommendInfo.longitude":longitude,
					"mobileRecommendInfo.latitude":latitude,
					"mobileRecommendInfo.objectId":placeId,
					"mobileRecommendInfo.objectType":stage,
					"mobileRecommendInfo.price":price,
					 "mobileRecommendInfo.recommendContent":recommendContent,
					"pageChannel":"freeTour"
		           };
		}
		
		$.ajax({type:"POST", url:basePath+"/mobile/mobileRecommendInfo!saveMobileRecommendInfoDest.do", data:param, dataType:"json", success:function (data) {
			if(data.flag=='true'){
				alert(data.msg);
				endFunction($("#sonBlockId").val(),pageChnnel);
				// 关闭弹出层 
				parent.closeWin('getRecommendInfoSource');
			}else{
				alert(data.msg);
			}
		}});
	}
	
	
	// 全选 
	function checkedAll(obj) {
		$("input[type=checkbox]").attr('checked',$(obj).attr('checked'));
	};

	// 删除所选记录 
	function delAll() {
		var ids = getCheckedValue('recommendInfoId_seq');
		if( null == ids || "" == ids ) {
			alert('请选择至少一条记录!');
			return false;
		}
		if(confirm("是否彻底删除所选数据？")==true){
			var param = {"delIds":getCheckedValue('recommendInfoId_seq')};
			$.ajax({type:"POST", url:basePath+"/mobile/mobileRecommendInfo!delMobileRecommendInfo.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("删除成功!");
					window.location.reload(true);
				}else{
					alert("删除出错!");		
				}
		    }});
		}
	}
	
	
	
	
	/**
	 * 日期比较 ，结束日期必须大于开始日期 
	 * @param startDate  开始日期
	 * @param endDate    结束日期 
	 * @returns  true or false
	 */
	function dataCompare(startDate,endDate) {
		if(StringToDate(startDate) >= StringToDate(endDate)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 字符串转换日期格式. 
	 * @param DateStr
	 * @returns
	 */
	function StringToDate(DateStr) 	{  
	   var converted = Date.parse(DateStr); 
	   var myDate = new Date(converted); 
	   if (isNaN(myDate))  {  
	      var arys= DateStr.split('-'); 
	      myDate = new Date(arys[0],--arys[1],arys[2]); 
	   } 
	   return myDate; 
	};
	
	// 判断某个form中的tag是否为空 
	function isEmpty(formobj,id) {
		var t = $("#"+formobj+" #"+id).val();
		return isStringEmpty(t);
	}
	
	// 判断对象是否为空. 
	function isStringEmpty(t) {
		if(null == t || $.trim(t) == '') {
			return true;
		}
		return false;
	}
	
	function chkSearchSource(form){
			
		return true;
	}
	
	// /^([+-]?)\\d*\\.\\d+$/ 
	