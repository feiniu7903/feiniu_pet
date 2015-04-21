function verifyUser() {
	var userName = document.getElementById("userName");
	var callerid = document.getElementById("callerid");
	var callerEmail = document.getElementById("callerEmail");
	var memberShipCard = document.getElementById("memberShipCard");
	if(userName.value==""&&callerid.value==""&&callerEmail.value==""&&memberShipCard.value==""){
		alert("请至少填写一个查询条件进行核实账号！");
		return false;
	}
	return true;
}
function resetPwdByEmail() {
	if($("#callUserInfo_userId").val()==""){
		alert("请先核实用户账号信息,在进行密码找回功能！"); 
		return false;
	}
	if("N".indexOf($("#callUserInfo_isUsers").val())>-1){
		alert("此功能只提供给驴妈妈会员用户！");
		return false;
	}
	if($("#callUserInfo_email").val()==""){
		alert("该用户没有填写Email！"); 
		return false;
	}
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/resetPwdByEmail.do";
	usrInfofrm.submit();
}

function resetPwdByMoblie() {
	if($("#callUserInfo_userId").val()==""){
		alert("请先核实用户账号信息,在进行密码找回功能！"); 
		return false;
	}
	if("N".indexOf($("#callUserInfo_isUsers").val())>-1){
		alert("此功能只提供给驴妈妈会员用户！");
		return false;
	}
	if($("#callUserInfo_mobileNumber").val()==""){
		alert("该用户没有填写手机号码！"); 
		return false;
	}
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/resetPwdByMoblie.do";
	usrInfofrm.submit();
}

function doRegistUser() {
	$("#IDBtnRegistUser").attr("disabled",true);
	var mobile = $.trim($("#callerid").val());
	if (mobile == '') {
		alert("请输入手机号！");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	if (mobile.length != 11) {
		alert("手机号必须为11位！");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	if (!MOBILE_REGX.test(mobile)) {
		alert("手机号格式不正确");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	$("#moblieNumber").val($("#callerid").val());
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/saveUsrUsers.do";
	usrInfofrm.submit();
	$("#IDBtnRegistUser").attr("disabled",false);
}
function registCustomer() {
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/saveUsrCustomer.do";
	usrInfofrm.submit();
}
function btn_updateBaseUser() {
	if($("#callUserInfo_userId").val()==""){
		alert("请先核实用户账号信息,在进行基本修改功能！"); 
		return false;
	}
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/updateUsersInfo.do";
	usrInfofrm.submit();
}

(function($) {
	var selectProvice = function (proviceId,cityId) {
		$.ajax( {
			url : ctx + "/call/getCityListByProvinceId.do",
			data : "provinceId=" + proviceId ,
			type: "POST",
			success : function(result) {
				var usrCityStr = '<option value="">请选择</option>';
				for(var i = 0 ; i < result.usrCityList.length; i ++) {
					var usrCity = result.usrCityList[i];
					if(cityId!=null&&cityId.indexOf(usrCity.cityId)>-1){
						usrCityStr += '<option value="'+usrCity.cityId+'" selected>'+usrCity.cityName+'</option>';
					}else{
						usrCityStr += '<option value="'+usrCity.cityId+'">'+usrCity.cityName+'</option>';	
					}
				}
				$('#usrCity').html(usrCityStr);
			}
		});
	}; 

	$(function(){
		selectProvice($('#usrProviced').val(),$('#usrCityId').val());
		$('#usrProvice').change( function() {
			selectProvice($(this).val(),$('#usrCityId').val());
		}); 
	});
})(jQuery);