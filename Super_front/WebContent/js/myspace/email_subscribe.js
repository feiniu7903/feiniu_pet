var EMPTY_MSG="<span class=\"tips-ico02\"></span>请输入订阅邮箱";
var INVALID_MSG="<span class=\"tips-ico02\"></span>请输入有效的邮箱地址，以便订阅邮件";
var SUBSCRIBE_MSG="<span class=\"tips-ico02\"></span>该邮箱已订阅了邮件，请输入其它邮箱";
var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
function check_email(email){	
		//1.检查邮箱地址是否已填写
		if(email==''||email==null){
			$('#edm_subscribe_validate_id').html(EMPTY_MSG).show();
			return false;
		}
		//2.检查邮箱地址是否合法
		if(!(EMAIL_REGX.test(email))){
			$('#edm_subscribe_validate_id').html(INVALID_MSG).show();
			return false;
		}
		return true;	
	}
$(function(){
	$(document.body).ready(function(){
		var typeList = $(":hidden[name=type]");
		var viewType = $("[id=subscribed_div][name]");	
		viewType.each(function(){
			var vt = $(this);
			var t = $(this).attr("name");
			$(this).attr("url",window.location.href);
			var isView = false;
			for(var i=0;i<(null !=typeList && typeList.size());i++){
				var value = typeList.eq(i).val();
				if(value==t){
					isView = true;
					vt.next().hide();
					break;
				}
			}
			if(isView==false){
				vt.prev().hide();
				vt.hide();
			}
		});
		$(":hidden[name=type]").each(function(){
	 		$("li[li_type=subscribed][edm_type="+$(this).val()+"]").show();
	 		$("li[li_type=onsubscribed][edm_type="+$(this).val()+"]").hide();
	 	});
		var travelTime=$(":hidden[name^=edmSubscribe][name$=travelTime]").val();
		var travelTime = travelTime.split(",");
		$(":checkbox[name=travelTime]").each(function(){
			var value = $(this).val();
			for(var i=0;i<travelTime.length;i++){
				if(value == travelTime[i]){
					$(this).attr("checked","checked");
					break;
				}
			}
		});
		$("#provinceId").children().each(function(){if($(this).val()==$(":hidden[name=edmProvinceId]").val()){$(this).attr("selected","selected");}});
	});
	$("#email_subscribe_button").click(function(){
		$('#edm_subscribe_validate_id').html("").hide();
		$(".city_error_msg").hide();
		var email = $(':text[name=edmSubscribe.email]').val();
		if(!(check_email(email))){
			return false;
		}
		var province = $("select[name='edmSubscribe.province']").val();
		var city	 = $("select[name='edmSubscribe.city']").val();
		if($.trim(city).length == 0 || null == city || undefined == city || "请选择" == city || "null" == city){
			$(".city_error_msg").show();
			return false;
		}
		var edmSubscribe = new Object();
		edmSubscribe.email=email;
		edmSubscribe.province = province;
		edmSubscribe.city = city;
		$.ajax({
			type: "POST",
			url: "/myspace/ajax/subscribeEmail.do",
			async: false,
			data: {"edmSubscribe.email":email,"edmSubscribe.province":edmSubscribe.province,"edmSubscribe.city":edmSubscribe.city,"edmSubscribe.mustWantedTravel":$(":hidden[name='edmSubscribe.mustWantedTravel']").val(),"edmSubscribe.travelTime":$(":hidden[name='edmSubscribe.travelTime']").val(),"edmSubscribe.channel":"SPACE"},
			dataType: "json",
			success: function(response) {
					if (response.success == false) {
							var msg=response.message;
							$('#edm_subscribe_validate_id').html("<span class=\"tips-ico02\"></span>"+msg).show();
							return false;
					} else {
						var id =response.message; 
						$("input:hidden[name='edmSubscribe.id']").val(id);
						alert("订阅信息保存成功");
					}
			}
		});	
	});
	$("[id=email_subscribe_div][name]").click(function(){
		var current = $(this);
		$('#edm_subscribe_validate_id').html("").hide();
		var email = $(':text[name^=edmSubscribe][name$=email]').val();
		if(!(check_email(email))){
			return false;
		}
		var province = $("select[name='edmSubscribe.province']").val();
		var city	 = $("select[name='edmSubscribe.city']").val();
		if($.trim(city).length == 0 || null == city || undefined == city || "请选择" == city || "null" == city){
			if($('.subscribe-edit-box').is(":hidden")){
				alert("请选择所在地");
				$('.subscribe-edit-box,#edit_subscribe_div').show();
				$(".city_error_msg").show();
			}else{
				$(".city_error_msg").show();
			}
			return false;
		}
		var type = $(this).attr("name");
		$.ajax({
			type: "POST",
			url: "/myspace/ajax/subscribeEmail.do",
			async: false,
			data: {"edmSubscribe.email":email,"edmSubscribe.province":province,"edmSubscribe.city":city,"edmSubscribe.mustWantedTravel":$(":hidden[name='edmSubscribe.mustWantedTravel']").val(),"edmSubscribe.travelTime":$(":hidden[name='edmSubscribe.travelTime']").val(),"edmSubscribe.channel":"SPACE"},
			dataType: "json",
			success: function(response) {
					if (response.success == false) {
						var msg=response.message;
						if($('#edm_subscribe_validate_id').is(":hidden")){
							alert(msg);
						}else{
							$('#edm_subscribe_validate_id').html("<span class=\"tips-ico02\"></span>"+msg).show();
						}
						return false;
					} else {
							var id =response.message; 
							$(":hidden[name='edmSubscribe.id']").val(id);
							$.ajax({
								type: "POST",
								url: "/myspace/ajax/subscribeMail.do",
								async:false,
								data:{"edmSubscribeInfo.edmUserId":id,"edmSubscribeInfo.type":type},
								dateType:"json",
								success:function(result){
									if(result.success){
										current.prev().show();
										current.prev().prev().show();
										current.hide();
									}else{
										alert(result.message);
									}
								}
							});
					}
			}
		});	
	});
	$("[id=subscribed_div][name]").click(function(){
		var current = $(this);
		var type = $(this).attr("name");
		var id=$(":hidden[name^=edmSubscribe][name$=id]").val();
		if(null == id || ""==id){
			alert("很抱歉，退订失败");
		}
		$.ajax({
			type:"POST",
			url: "/myspace/ajax/cancelSubscribe.do",
			async:false,
			data:{"edmSubscribeInfo.edmUserId":id,"edmSubscribeInfo.type":type},
			dateType:"json",
			success:function(response){
				if(response.success == true){
					current.prev().hide();
					current.next().show();
					current.hide();
				}else{
					alert(response.message);
				}
			}
		});
	});
	$("#provinceId").change(function(){
		$(".city_error_msg").hide();
 		var provinceId = $(this).val();
 		if(provinceId==null || $.trim(provinceId).length == 0){
 			$(".city_error_msg").show();
 			$("[name^=edmSubscribe][name$=city]").empty().append("<option>请选择</option>");
 			return;
 		}
		$.getJSON("/edm/getCityByProvinceId.do?provinceId=" + provinceId + "&jsoncallback=?",function(json){
			var list = json.list;
			$("[name^=edmSubscribe][name$=city]").empty();
			for(var i=0;i<list.length;i++){
				var city = list[i];
				$("[name^=edmSubscribe][name$=city]").append("<option value="+city.cityId+">"+city.cityName+"</option>");
			}
			$(".city_error_msg").show();			
		});	
 	});
 	$("#cityId").change(function(){
 		var city = $(this).val();
 		$(".city_error_msg").hide();
 		if(city==null || $.trim(city).length == 0 || city=="null" || city=="请选择"){
 			$(".city_error_msg").show();
 		}
 	});
 	$("#mustWantTravelProvince").change(function(){
 		var provinceId = $(this).val();
 		if(provinceId==null || $.trim(provinceId).length == 0){
 			$("#mustWantTravelCity").empty().append("<option>请选择</option>");
 			return;
 		}
		$.getJSON("/edm/getCityByProvinceId.do?provinceId=" + provinceId + "&jsoncallback=?",function(json){
			var list = json.list;
			$("#mustWantTravelCity").empty();
			for(var i=0;i<list.length;i++){
				var city = list[i];
				$("#mustWantTravelCity").append("<option value="+city.cityId+">"+city.cityName+"</option>");
			}			
		});	
 	});
 	$(".yjdy_btn").click(function(){
 		$("#edm_type_hidden_id").append("<input type='hidden' name='type' value='"+$(this).parent().attr("edm_type")+"'/>");
 		$(this).parent().hide();
 		$("li[li_type=subscribed][edm_type="+$(this).parent().attr("edm_type")+"]").show();
 		check_subscribe_type();
 	});
 	$(".yjdy_no_btn").click(function(){
 		$(":hidden[name=type]").remove("[value="+$(this).parent().attr("edm_type")+"]");
 		$(this).parent().hide();
 		$("li[li_type=onsubscribed][edm_type="+$(this).parent().attr("edm_type")+"]").show();
 		check_subscribe_type();
 	});
 	$(".dy_add").click(function(){
 		if($(".dy_cha").size()>=5){
 			return false;
 		}
 		var mustWantTravelCity=$("#mustWantTravelCity").val();
 		if(null==mustWantTravelCity || "null"==mustWantTravelCity || ""==mustWantTravelCity || "请选择"==mustWantTravelCity){
 			return false;
 		}
 		var element = null;
 		$(".dy_cha").each(function(){
 			if($(this).attr("value")==($("#mustWantTravelProvince").val()+"-"+mustWantTravelCity)){
 				element = $(this).attr("value");
 			};
 		});
 		if(element != null){
 			return false;
 		}
 		$(".dy_chalist").append("<span class=\"dy_cha\" value=\""+$("#mustWantTravelProvince").val()+"-"+mustWantTravelCity+"\">"+$("#mustWantTravelCity").find(":selected").text()+"<img src=\"http://pic.lvmama.com/img/new_v/ob_yjdy/cha.jpg\"/></span>");
 		var mustWantedTravel="";
 		$(".dy_cha").each(function(){
 			mustWantedTravel +=$(this).attr("value")+",";
 		});
 		if(mustWantedTravel.length>0){
 			mustWantedTravel = mustWantedTravel.substring(0,mustWantedTravel.length-1);
 		}
 		$(":hidden[name$=mustWantedTravel]").val(mustWantedTravel);
 	});
 	$(".dy_cha").live("click",function(){
 		$(this).removeAttr("value").remove().empty();
 		var mustWantedTravel="";
 		$(".dy_cha").each(function(){
 			mustWantedTravel +=$(this).attr("value")+",";
 		});
 		if(mustWantedTravel.length>0){
 			mustWantedTravel = mustWantedTravel.substring(0,mustWantedTravel.length-1);
 		}
 		$(":hidden[name$=mustWantedTravel]").val(mustWantedTravel);
 	});
 	$(":checkbox[name=travelTime]").change(function(){
 		var travelTime="";
 		$(":checkbox[name=travelTime]:checked").each(function(){
 			travelTime +=$(this).attr("value")+",";
 		});
 		if(travelTime.length>0){
 			travelTime = travelTime.substring(0,travelTime.length-1);
 		}
 		$(":hidden[name^=edmSubscribe][name$=travelTime]").val(travelTime);
 	});
});	
function show_edit_subscribe(){
	$("#edit_subscribe_div").toggle();
	$(".subscribe-edit-box").toggle();
}