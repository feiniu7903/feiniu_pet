
$(function(){
	firstChannelChange('queryListForm','s_firstChannel','s_seconedChannel'); // 查询form
	firstChannelChange('addMobileVersionForm','firstChannel','seconedChannel'); // 新增form 
});

/**
 * 一级投放渠道. 
 * @param form id 
 */
function firstChannelChange(form,firstChange,secondChanel) {
	var fchange = $("#" +form +" #"+firstChange).val();
	/*var options = "<option value = \"WL\" >网龙</option>" + 
				  "<option value = \"APPSTORE\">appstore</option>" +
				  "<option value = \"LVMM\" >官网</option>" +
                  "<option value = \"ANDROID_91\" >91手机助手</option>"+
				  "<option value = \"ANDROID_360\" >360手机助手</option>" +
				  "<option value = \"TONGBUTUI\" >同步推</option>" +
				  "<option value = \"WEIFENGYUAN\" >威锋源</option>" +
				  "<option value = \"PP\" >PP助手</option>" +
				  "<option value = \"GOOGLEPLAY\">googlePlay</option>";*/
	
	
	var options = "";
	if('IPHONE' == fchange) {
		options = "<option value = \"APPSTORE\">appstore</option>" +
				  "<option value = \"IOS_91\" >91手机助手</option>"+
				  "<option value = \"TONGBUTUI\" >同步推</option>" +
				  "<option value = \"WEIFENGYUAN\" >威锋源</option>" +
				  "<option value = \"PP\" >PP助手</option>";
	} else if('ANDROID' == fchange) {
		options = "<option value = \"LVMM\" >官网</option>" +
                  "<option value = \"ANDROID_91\" >91手机助手</option>"+
				  "<option value = \"ANDROID_360\" >360手机助手</option>" +
				  "<option value = \"GOAPK\" >安智市场</option>" +
				  "<option value = \"QQ\" >腾讯应用中心</option>" +
				  "<option value = \"XIAOMI\" >小米应用商店</option>" +
				  "<option value = \"GFAN\" >机锋市场</option>" +
				  "<option value = \"HIAPK\" >安卓市场</option>" +
				  "<option value = \"WANDOUJIA\" >豌豆荚</option>" +
				  "<option value = \"APPCHINA\" >应用汇</option>" +
				  "<option value = \"BAIDU\" >百度应用中心</option>" +
				  "<option value = \"SAMSUNG\" >三星应用商店</option>" +
				  "<option value = \"NDUO\" >N多市场</option>" +
				  "<option value = \"LENOVO\" >联想乐商店</option>" +
				  "<option value = \"ANDROID_3G\" >3G安卓市场</option>" +
				  "<option value = \"HUAWEI\" >智慧云</option>" +
				  "<option value = \"WOSTORE\" >联通沃商店</option>" +
				  "<option value = \"MUMAYI\" >木蚂蚁</option>" +
				  "<option value = \"EOEMARKET\" >优亿市场</option>" +
				  "<option value = \"YIDONGMM\" >移动MM</option>" +
				  "<option value = \"ZTE\" >中兴商店</option>" +
				  "<option value = \"MEIZU\" >魅族商店</option>" +
				  "<option value = \"COOLMART\" >酷派商店</option>" +
				  "<option value = \"CROSSMAO\" >十字猫</option>" +
				  "<option value = \"ALIPAY\" >支付宝</option>" +
				  "<option value = \"UC\" >UC</option>" +
				  "<option value = \"MADHOUSE\" >亿动广告</option>" +
				  "<option value = \"GOOGLEPLAY\">googlePlay</option>"+
				  "<option value = \"sanxing_wallet\">三星钱包</option>"+
				  "<option value = \"PANKU1\">盘库1</option>"+
				  "<option value = \"PANKU2\">盘库2</option>"+
				  "<option value = \"PANKU3\">盘库3</option>"+
				  "<option value = \"PANKU4\">盘库4</option>"+
				  "<option value = \"PANKU5\">盘库5</option>"+
				  "<option value = \"OPPO\">oppo商店</option>"+
				  "<option value = \"GUODONG\">GUODONG</option>"+
				  "<option value = \"ALL\" >其它</option>" ;
	} else if('IPAD' == fchange) {
		options = "<option value = \"APPSTORE\">appstore</option>" +
		  "<option value = \"IOS_91\" >91手机助手</option>"+
		  "<option value = \"WEIFENGYUAN\" >威锋源</option>"+
		  "<option value = \"TONGBUTUI\" >同步推</option>";
    } else if('WP8' == fchange) {
		options = "<option value = \"WPMARKET\">WP商店</option>" +
		  "<option value = \"ALL\" >其它</option>";
    }
	
	// 如果是查询则加上全部 
	if('queryListForm' == form) {
		options = "<option value = \"\" >全部</option>"+ options;
	}
	$("#" +form +" #"+secondChanel).html(options);
	
	// 选中二级渠道 
	if(null != search_second_channel && "" != search_second_channel && 's_seconedChannel' == secondChanel ) {
		$("#" +form +" #"+secondChanel).find("option[value='"+search_second_channel+"']").attr("selected",true);
	}
	
}

/**
 * 修改version. 
 * @param form
 */
function saveMobileVersion(formObj){
	var c = checkVersionForm(formObj);
	if(!c) {
		return false;
	}
	var param = {"mobileVersion.title":$("#"+formObj+" #title").val(),
			"mobileVersion.id":$("#"+formObj+" #id").val(),
			"mobileVersion.isAuditing":$("#"+formObj+" #isAuditing").val(),
			"mobileVersion.forceUpdate":$("#"+formObj+" #forceUpdate").val(),
			"mobileVersion.content":$("#"+formObj+" #content").val(),
			"mobileVersion.platform":$("#"+formObj+" #platform").val(),
			"mobileVersion.version":$("#"+formObj+" #version").val(),
			"mobileVersion.firstChannel":$("#"+formObj+" #firstChannel").val(),
			"mobileVersion.seconedChannel":$("#"+formObj+" #seconedChannel").val(),
			"mobileVersion.updateUrl":$("#"+formObj+" #updateUrl").val()
			};
	$.ajax({type:"POST", url:basePath+"/mobile/mobileClient!saveMobileVersion.do", data:param, dataType:"json", success:function (data) {
		if(data.flag=='true'){
			alert(data.msg);
			refreshPage();
		}else{
			alert(data.msg);	
		}
	}});
}
/**
 * 修改审核状态. 
 * @param form
 */
function updateAuditeStatus(id,status){
	var param = {"mobileVersion.id":id,
			"mobileVersion.isAuditing":status
			};
	$.ajax({type:"POST", url:basePath+"/mobile/mobileClient!updateAuditing.do", data:param, dataType:"json", success:function (data) {
		if(data.flag=='true'){
			alert("更新成功!");
			refreshPage();
		}else{
			alert("更新出错!");		
		}
	}});
}



// 删除 
function delVersion(versionId){
	if(confirm("是否删除？")==true){
		var param = {"mobileVersion.id":versionId};
		$.ajax({type:"POST", url:basePath + "/mobile/mobileClient!delMobileVersion.do", data:param, dataType:"json", success:function (data) {
			if(data.flag=='true'){
				alert("删除成功!");
				window.location.reload(true);
			}else{
				alert("删除出错!");		
			}
	    }});
	}
}

// 修改审核状态 .


// version 表单校验 
function checkVersionForm(form) {
	 if($.trim($("#"+form+" #title").val())==''){
		 alert("标题不能为空！");
		 return false;
	 } else if($("#"+form+" #title").val().length > 100) {
		 alert("标题最多200个字符！");
		 return false;
	 }
	 if($.trim($("#"+form+" #content").val())==''){
		 alert("内容不能为空！");
		 return false;
	 }else if($("#"+form+" #content").val().length > 100) {
		 alert("内容最多200个字符！");
		 return false;
	 }
	 
	 if($.trim($("#"+form+" #version").val())==''){
		 alert("最新版本号不能为空！");
		 return false;
	 }
	 
	 if($.trim($("#"+form+" #platform").val())==''){
		 alert("平台不能为空！");
		 return false;
	 }
	 
	 if($.trim($("#"+form+" #firstChannel").val())==''){
		 alert("一级渠道不能为空！");
		 return false;
	 }
	 
	 if($.trim($("#"+form+" #seconedChannel").val())==''){
		 alert("二级渠道不能为空！");
		 return false;
	 }
	 
	 if($.trim($("#"+form+" #updateUrl").val())==''){
		 alert("更新地址不能为空！");
		 return false;
	 }else if($("#"+form+" #updateUrl").val().length > 200) {
		 alert("更新地址最多200个字符！");
		 return false;
	 }
	 
	 return true;
}

/**
 * 刷新页面.
 */
function refreshPage() {
	$("#queryListForm").submit();
}






