var LT_MESSAGE_ERROR="网络异常,请稍后再试";
/*程序自定义函数*/
function popupModal_calback(){
	  $('a[_disappear]').bind('click',function(){
		 popupModal(false, '', null, 0,true);
		 $('a[_disappear]').unbind('click');
	  });
};

/**
 * 点击常用游玩人列表
 */
function contact_traveler(){
	//头部切换
	$("#travelerAddEditTitle").show();
	$("#travelerTitle").hide();
	//列表隐藏
	$("#travelerList").hide();
	$("#contactList").show();
	
	$(".common-order-result").hide();
	
	$("#travelerAddEditTitle .back").attr("href","javascript:contact_traveler_back();");
}
/**
 * 常用游玩人返回
 */
function contact_traveler_back(){
	//头部切换
	$("#travelerAddEditTitle").hide();
	$("#travelerTitle").show();
	//列表隐藏
	$("#travelerList").show();
	$("#contactList").hide();
	
	$(".common-order-result").show();
}
/**
 * 新增,编辑游玩人
 */
function contact_traveler_add_edit(){
	//头部切换
	$("#contactList").hide();
	$("#travelerAddEdit").show();
	//$("#header_finish").show();
	
	$("#header_finish .btn1").attr("onclick","editContact_ok();");
	
	$("#travelerAddEditTitle .back").attr("href","javascript:contact_traveler_add_edit_back();");
}
/**
 * 新增,编辑游玩人返回
 */
function contact_traveler_add_edit_back(){
	//头部切换
	$("#contactList").show();
	$("#travelerAddEdit").hide();
	//$("#header_finish").hide();
	$("#header_value").html('常用游玩人');
	
	$("#header_finish .btn1").attr("onclick","choose_person_ok();");
	
	$("#travelerAddEditTitle .back").attr("href","javascript:contact_traveler_back();");
}


/**
 *选择游玩人列表--赋值给需要游玩人的页面列表
 */
function choose_person_ok(){
	var index_traveler=0;
	//选择验证================================
	var select_num=0;
	var fillTravellerNum=$("#fillTravellerNum").val();
	$(".ic_check_a").each(function(index){
		var thisClass=$(this).attr("class");
		if(thisClass.indexOf("selected")>=0){
			select_num++;
		}
	});
	if(select_num==0){
		popupModal(true, "请选择游玩人！", popupModal_calback, 0,true);
		//alert(verifyValue);
		return false;
	}
	if(select_num>fillTravellerNum){
		popupModal(true, "已超过需选择的游玩人数！", popupModal_calback, 0,true);
		//alert(verifyValue);
		return false;
	}
	//================================
	$(".ic_check_a").each(function(index){
		var thisClass=$(this).attr("class");
		if(thisClass.indexOf("selected")>=0){
			$("#traveler_name_"+index_traveler).val($("#receiver_name_"+index).val());
			$("#traveler_mobile_"+index_traveler).val($("#mobile_mumber_"+index).val());
			
			var traveler_cert_type=$("#traveler_cert_type_"+index).val();
			
			if(traveler_cert_type=="" || traveler_cert_type=="ID_CARD"){//身份证
				$("#cert_type_"+index_traveler).val("ID_CARD");
				$("#contact_cert_no_"+index_traveler).val($("#traveler_cert_no_"+index).val());
				
				$("#only_huzhao_used_"+index_traveler).hide();
			}else{//护照
				$("#cert_type_"+index_traveler).val("HUZHAO");
				$("#contact_cert_no_"+index_traveler).val($("#traveler_cert_no_"+index).val());
				//日期性别
				$("#contact_sex_"+index_traveler).val($("#traveler_gender_"+index).val());
				$("#contact_birthDay_"+index_traveler).val($("#traveler_birthday_"+index).val());
				
				$("#only_huzhao_used_"+index_traveler).show();
			}
			index_traveler++;
		}
	});
	contact_traveler_back();//显示隐藏对应页面
}
/**
 * 提交订单，验证组装游玩人
 */
function chooseTraveller_ok_v5(){
	// 获取选择的游玩人
	//验证弹框用
	var numberOfCustomers=0;
	var ruzhuNum=[];
	var verifyValue=null;
	// 获取填写的游玩人
	var choosedTraveller = [];
		
	//处理一个游玩人情况
	$(".need_traveler_list").each(function(index){
		ruzhuNum[index++]=$(this);
	});
	
	$(".need_traveler_list").each(function(index){
		//统计多少是哪个游玩人
		numberOfCustomers++;
		
		var traveler_name=$("#traveler_name_"+index);
		var traveler_mobile=$("#traveler_mobile_"+index);
		var cert_type=$("#cert_type_"+index);
		var contact_cert_no=$("#contact_cert_no_"+index);
		var contact_birthDay=$("#contact_birthDay_"+index);
		var contact_sex=$("#contact_sex_"+index);
		
		var traveNameParentClass=$(traveler_name).parent('div').attr("class");
		var traveMobileParentClass=$(traveler_mobile).parent('div').attr("class");
		var contactNoParentClass=$(contact_cert_no).parent('div').attr("class");
		//=============================================================================验证开始
		//名称验证
		if(traveNameParentClass.indexOf("required")>=0){
			//验证为空
			if(isEmpty(traveler_name.val())) {
				if(ruzhuNum!=null && ruzhuNum.length==1){
					verifyValue="请输入游玩人姓名";
				}else{
					verifyValue="请输入游玩人"+numberOfCustomers+"姓名";
				}
				return false;
			}
			//验证格式
			//无
		}
		//手机验证
		if(traveMobileParentClass.indexOf("required")>=0){
			//验证为空
			if(isEmpty(traveler_mobile.val())) {
				if(ruzhuNum!=null && ruzhuNum.length==1){
					verifyValue="请输入游玩人手机号码";
				}else{
					verifyValue="请输入游玩人"+numberOfCustomers+"手机号码";
				}
				return false;
			}
			//验证格式
			if(!verify_mobile(traveler_mobile.val())){
				if(ruzhuNum!=null && ruzhuNum.length==1){
					verifyValue="请输入正确的游玩人手机号码";
				}else{
					verifyValue="请输入正确的游玩人"+numberOfCustomers+"手机号码";
				}
				return false;
			}
		}else{//不必填但格式错误
			if(!isEmpty(traveler_mobile.val())) {
				if(!verify_mobile(traveler_mobile.val())){
					if(ruzhuNum!=null && ruzhuNum.length==1){
						verifyValue="请输入正确的游玩人手机号码";
					}else{
						verifyValue="请输入正确的游玩人"+numberOfCustomers+"手机号码";
					}
					return false;
				}
			}
		}
		//证件验证
		if(contactNoParentClass.indexOf("required")>=0){
			
			//验证为空
			if(isEmpty(contact_cert_no.val())) {
				if(ruzhuNum!=null && ruzhuNum.length==1){
					verifyValue="请输入游玩人证件号码";
				}else{
					verifyValue="请输入游玩人"+numberOfCustomers+"证件号码";
				}
				return false;
			}
			
			//验证格式
			if(cert_type.val()!=null && cert_type.val()=="ID_CARD"){//身份证
				if("true"!=validateIdCard(contact_cert_no.val())){
					if(ruzhuNum!=null && ruzhuNum.length==1){
						verifyValue="请输入正确的游玩人证件号码";
					}else{
						verifyValue="请输入正确的游玩人"+numberOfCustomers+"证件号码";
					}
					return false;
				}
			}else{//护照
				//护照号不为空即可-上面已验证
				//********
				//验证出生日期
				if(isEmpty(contact_birthDay.val())) {
					if(ruzhuNum!=null && ruzhuNum.length==1){
						verifyValue="请输入游玩人出生日期";
					}else{
						verifyValue="请输入游玩人"+numberOfCustomers+"出生日期";
					}
					return false;
				}
				// 生日
				if(!verify_date_string(contact_birthDay.val())) {
					if(ruzhuNum!=null && ruzhuNum.length==1){
						verifyValue="请输入正确的游玩人出生日期";
					}else{
						verifyValue="请输入正确的游玩人"+numberOfCustomers+"出生日期";
					}
					return false;
				}
			}
		}else{//不必填但格式错误
			if(cert_type.val()!=null && cert_type.val()=="ID_CARD"){//身份证
				if(!isEmpty(contact_cert_no.val())) {
					if("true"!=validateIdCard(contact_cert_no.val())){
						if(ruzhuNum!=null && ruzhuNum.length==1){
							verifyValue="请输入正确的游玩人证件号码";
						}else{
							verifyValue="请输入正确的游玩人"+numberOfCustomers+"证件号码";
						}
						return false;
					}
				}
			}else{//护照
				if(!isEmpty(contact_cert_no.val()) || !isEmpty(contact_birthDay.val())) {
					if(isEmpty(contact_cert_no.val())) {
						if(ruzhuNum!=null && ruzhuNum.length==1){
							verifyValue="请输入游玩人证件号码";
						}else{
							verifyValue="请输入游玩人"+numberOfCustomers+"证件号码";
						}
						return false;
					}else{
						//护照不为空则正确
					}
					
					// 出生日期
					if(isEmpty(contact_birthDay.val())) {
						if(ruzhuNum!=null && ruzhuNum.length==1){
							verifyValue="请输入游玩人出生日期";
						}else{
							verifyValue="请输入游玩人"+numberOfCustomers+"出生日期";
						}
						return false;
					}else{
						if(!verify_date_string(contact_birthDay.val())) {
							if(ruzhuNum!=null && ruzhuNum.length==1){
								verifyValue="请输入正确的游玩人出生日期";
							}else{
								verifyValue="请输入正确的游玩人"+numberOfCustomers+"出生日期";
							}
							return false;
						}
					}
				}
			}
		}
		//==================================================================================验证结束
		var data_value=traveler_name.val()+"-"+traveler_mobile.val()+"-"+"TRAVELLER";
		//如果证件号不为空则说明填写了证件--根据cert_type判断是选择哪个证件
		if(!isEmpty(contact_cert_no.val())){
			if(cert_type.val()!=null && cert_type.val()=="ID_CARD"){//身份证
				data_value=data_value+"-"+contact_cert_no.val()+"-"+cert_type.val();
			}else{
				var birthDayRule=contact_birthDay.val()
				if(birthDayRule.indexOf("-")>=0){
					birthDayRule=birthDayRule.replace(/-/g,'');
					birthDayRule=birthDayRule.substring(0,4)+"/"+birthDayRule.substring(4,6)+"/" + birthDayRule.substring(6,8);
					
				}else{
					birthDayRule=birthDayRule.substring(0,4)+"/"+birthDayRule.substring(4,6)+"/" + birthDayRule.substring(6,8);
				}
				data_value=data_value+"-"+contact_cert_no.val()+"-"+cert_type.val()+"-"+birthDayRule+"-"+contact_sex.val();
			}
		}
		
		//data-value="${contact.receiverName!}-${contact.mobileNumber!}-TRAVELLER-${contact.certNo!}-${contact.certType!}<#if contact.certType?? && contact.certType=="HUZHAO">-${contact.birthday?if_exists?replace("-","/")}-${contact.gender!}
		choosedTraveller.push(data_value);
	});
	
	//数据验证
	if(!isEmpty(verifyValue)){
		popupModal(true, verifyValue, popupModal_calback, 0,true);
		//alert(verifyValue);
		return false;
	}
	
	// 校验填写游玩人的个数
	$("#traveller_list_hidden").val(choosedTraveller.join(":"));
	return true;
}
//手机号格式校验
function verify_mobile(m) {
	if (!m.match(/^1[3|4|5|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		return false;
	} else {
		return true;
	}
}
//日期校验
function verify_date_string(sDate) {
	if(null == sDate || $.trim(sDate) == '') {
		return false;
	}
	// 去掉空格 
	sDate = $.trim(sDate);
	
	// 支持yyyyMMdd格式
	if(sDate.indexOf("-") == -1) {
		var pattern=/^[1-2]\d{3}(0?[1-9]|1[0-2])(0?[1-9]|[12][0-9]|3[0-1])$/ ;
		if(!pattern.test(sDate)){
			return false;
		}
		// 格式转换
		if(sDate.length == 8) {
			sDate = sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-" + sDate.substring(6,8);
		}
	}
	var mp = /^[1-2]\d{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[0-1])$/;
	if (!mp.test(sDate)){
		return false;
	}
	var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var iaDate = new Array(3);
	var year, month, day;

	iaDate = sDate.split("-");
	year = parseFloat(iaDate[0]);
	month = parseFloat(iaDate[1]);
	day = parseFloat(iaDate[2]);
	if (year < 1900 || year > 2200){
		return false;
	}
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
		iaMonthDays[1] = 29;
	if (month < 1 || month > 12){
		return false;
	}
	if (day < 1 || day > iaMonthDays[month - 1]){
		return false;
	}
	return true;
} 
//================================================================================5.0版JSEND
$(function() {
	/*//选择证件类型事件绑定
	$("#zhengjianlixing_id dd").click(function() {
		var personType = $(this).attr("data-type");
		personTypeStyle("dd","personType4",personType); // 选中证件类型
		personTypeChoose_back(personType);
	});*/
	// 游玩人选择框绑定
	bindTravellerChoose('0'); 
});

//游玩人选择框绑定
function bindTravellerChoose(type) {
	$("li[id^='traveller2'] span:first-child").click(function() {
		// 获取父节点数据
		if(type=="0" && !valid_traveller_is_right($(this))) {
			return false;
		}
		var className = $(this).attr("class");
		if(className == 'ic_check_right') {
			className = 'ic_check_right2';
		} else {
			className = 'ic_check_right';
		}
		$(this).removeClass().addClass(className);
	});
}

/**
 * 校验游玩人信息是否全
 */
function valid_traveller_is_right(obj) {
	if(null != obj) {
		var values = $(obj).parent().attr("data-value");
		if(null != values) {
			var v = values.split("-");
			// 测试1-18149707365-TRAVELLER-83727181-HUZHAO-2006/08/26-M
			if(v.length == 5) {
				if(v[0]=='' || v[1]==''|| v[2]==''|| v[3]==''|| v[4]=='') {
					popupModal(true, "请完善游玩人信息", popupModal_calback, 0,true); 
					return false;
				} 
			} else if(v.length == 7) {
				if(v[0]=='' || v[1]==''|| v[2]==''|| v[3]==''|| v[4]==''|| v[5]==''|| v[6]=='') {
					popupModal(true, "请完善游玩人信息", popupModal_calback, 0,true); 
					return false;
				} 
			}
		}
	}
	return true;
}


//新增，编辑选中证件类型后回调
function onPersonTypeChoose(obj){
	var dataValue = $(obj).val();
	if(dataValue == "HUZHAO") {
		$("#only_huzhao_used").show();
	} else {
		$("#only_huzhao_used").hide();
	}
}
//需要游玩人列表选中证件类型回调
function onPersonListTypeChoose(index,obj){
	var dataValue = $(obj).val();
	if(dataValue == "HUZHAO") {
		$("#only_huzhao_used_"+index).show();
	} else {
		$("#only_huzhao_used_"+index).hide();
	}
}
// 选择游玩人相关
function  chooseTraveller() {
	// 显示和隐藏 
	traverllerBack(2);
	
}
// 选择游玩人，点击选中
function chooseTraveller_ok() {
	// 获取选择的游玩人
	var choosedTraveller = [];
	//var travellerList = [];
	$("#people_list").children("li").each(function(index,element){
		var s_className = $(this).children("span:first-child").attr('class');
		// 如果选中
		if('ic_check_right' == s_className) {
			var data_value = $(this).attr("data-value");
			choosedTraveller.push(data_value);
			//travellerList.push("<span class="+((index%2==0)?"line":"")+">" + $('#traveler_id'+$(this).attr("data-id")).html() + "</span>");
		}
	});
	// 校验填写游玩人的个数
	$("#traveller_list_hidden").val(choosedTraveller.join(":"));
	/*if(!validate_traveller()) {
		$("#traveller_list_hidden").val('');
		return false;
	}*/
	//$("#traveller_view_p").html(travellerList.join(""));
	//traverllerBack(1);
}

// 获取游玩人当前选中的id 
function getCurrentChoosedIds() {
	var choosedTravellerIds = [];
	$("#people_list").children("li").each(function(index,element){
		var s_className = $(this).children("span:first-child").attr('class');
		// 如果选中
		if('ic_check_right' == s_className) {
			choosedTravellerIds.push($(this).attr("data-id"));
		}
	});
	
	return null ==choosedTravellerIds ?"": choosedTravellerIds.join(",");
}


/**
 * 新增和修改联系人
 * @param receiverId
 */
function editContact(receiverId,name,mobile,personType,certType,certNo,birthday,gender) {
	$("#contact3ReceiverId").val(receiverId);
	$("#contact3Name").val(name);
	$("#contact3Tel").val(mobile);
	$("#personType3").val(personType);
	$("#certType3").val(certType);
	$("#contact3CertNo").val(certNo);
	if(birthday.indexOf("-")>=0){
		birthday=birthday.replace(/-/g,'');
	}
	$("#contact3BirthDay").val(birthday);
	$("#contact3Sex").val(gender);
	// 修改 
	if('' != receiverId) {
		$("#delete_contact_id").show();
		$("#header_value").html('编辑游玩人');
	} else {
		$("#delete_contact_id").hide();
		$("#header_value").html('新增游玩人');
		certType = "ID_CARD";
	}
	
	if('HUZHAO' ==certType ) {
		$('#only_huzhao_used').show();
	}  else {
		$('#only_huzhao_used').hide();
	}
	contact_traveler_add_edit();
	/*if("ID_CARD" == certType) {
		$("#certType3").val("身份证");
	} else if("HUZHAO" == certType) {
		$("#certType3").val("护照");
	}else {
		$("#certType3").val("其它");
	}
	*/
}

// 新增完成 
function editContact_ok() {
	// 新增
	if(!validate_addContace()){
		return false;
	}
	var contact3ReceiverId=$("#contact3ReceiverId").val();
	var personType3=$("#personType3").val();
	var contact3Name=$("#contact3Name").val();
	var contact3Tel=$("#contact3Tel").val();
	var certType3=$("#certType3").val();
	var contact3CertNo=$("#contact3CertNo").val();
	var contact3BirthDay=$("#contact3BirthDay").val();
	var contact3Sex=$("#contact3Sex").val();
	
	//判断用户填写的数据--只有姓名是必填项，如果证件号为空则说明证件信息没填
	if(contact3CertNo==null && isEmpty(contact3CertNo)) {
		certType3="";
		contact3BirthDay="";
		contact3Sex="";
	}
	var param = {"choosedIds":getCurrentChoosedIds(),
			"receiverId":contact3ReceiverId,
			"personType":personType3,
			"receiverName":contact3Name,
			"mobileNumber":contact3Tel,
			"certType":certType3,
			"certNo":contact3CertNo,
			"birthday":contact3BirthDay,
			"gender":contact3Sex,
			"ajax":true};
	$.post(contextPath+"/user/add_contact.htm",param,function(data){
		$("#people_list").html(data);
		//bindTravellerChoose('1');// 绑定游玩人选择框事件
		contact_traveler_add_edit_back();
		//执行JS使异步修改后的游玩人列表可选择
		$("#people_list").checkBox({
	        initNum : -1
	    });//游玩人复选
	});
}

// 删除游玩人
function deleteContact_calback() {
	/**/
	$('a[_disappear]').bind('click',function(){
		 popupModal(false, '', null, 0,false);
		 deleteContact_s();
		 $('a[_disappear]').unbind('click');
	 });
	$('a[_disappear_cancel]').bind('click',function(){
		 popupModal(false, '', null, 0,false);
		 $('a[_disappear_cancel]').unbind('click');
	 });
}

/**
 * 删除 
 */
function deleteContact_s() {
	var param = {"choosedIds":getCurrentChoosedIds(),
			"receiverId":$("#contact3ReceiverId").val(),
			"ajax":true};
	$.post(contextPath+"/user/remove_contact.htm",param,function(data){
		$("#people_list").html(data);
		//bindTravellerChoose('1');// 绑定游玩人选择框事件
		//traverllerBack(2);
		contact_traveler_add_edit_back();
		//执行JS使异步修改后的游玩人列表可选择
		$("#people_list").checkBox({
	        initNum : -1
	    });//游玩人复选
	});
}

function deleteContact() {
	popupModal(true, "确定删除该游玩人", deleteContact_calback, 0,false); 
}

//提交表单
function fill_traveller_submit() {
	$('#fill_traveller_submit_hide').show();
	$('#fill_traveller_submit_show').hide();
	if(!initFillTravellerForm()) {
		$('#fill_traveller_submit_show').show();
		$('#fill_traveller_submit_hide').hide();
		return false;
	}
	var url;
	//用户未登录则是匿名下单
	if((!isEmpty(user_status) && user_status==0) && (!isEmpty(reserve_type) && reserve_type=="TICKET")){
		url = contextPath+'/ticketorder/order_submit.htm';
	}else{
		url = contextPath+'/order/order_submit.htm';
	}
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		beforeSend : function() {
		},
		success : function(data) {
			if(data.code == '1') {
				//qing. 跳百度支付页面
				if(hostName!=null && hostName=="qing.lvmama.com"){
					window.location.href = contextPath + "/bdorder/baidu_order_success.htm?orderId="+data.orderId;
				}else{
					// 页面跳转 --区分匿名下单和正常下单
					if((!isEmpty(user_status) && user_status==0) && (!isEmpty(reserve_type) && reserve_type=="TICKET")){
						//window.location.href = contextPath + "/ticketorder/order_success.htm?orderId="+data.orderId;
						window.location.href = contextPath + "/ticketorder/order_success.htm?orderId="+data.orderId+"&userNo="+data.userNo+"&userStatus="+data.userStatus;
					}else{
						window.location.href = contextPath + "/order/order_success.htm?orderId="+data.orderId;
					}
				}
			} else {
				popupModal(true, data.msg, popupModal_calback, 0,true); 
			}
		},
		error:function() {
			popupModal(true, LT_MESSAGE_ERROR, popupModal_calback, 0,true); 
		},
		complete:function() {
			$('#fill_traveller_submit_show').show();
			$('#fill_traveller_submit_hide').hide();
		}
	});
}


//初始化表单数据
function initFillTravellerForm() {
	// 初始化选择的游玩人并验证
	if(!chooseTraveller_ok_v5()){
		return false;
	}
	
	// 校验 联系人
	/*if(!validate_contact()) {
		return false;
	}*/
	// 校验 紧急联系人
	if(!validate_emergency()) {
		return false;
	}
	
	//校验 游玩人联系人
//	if(!validate_traveller()) {
//		return false;
//	}
	
	// 用户数据列表. 
	var personListItems = "";
	// 联系人
	var contact = inintContact();
	// 游玩人
	var tarveller = initTraveller();
	// 紧急联系人 
	var emegency = initEmegency();
	// 实体票收件人
	var expContact = initExpContact();
	if('' != contact) {
		personListItems = contact;
	}
	if('' != tarveller ) {
		personListItems = (''== personListItems?"":(personListItems+":"+tarveller));
	}
	if('' != emegency ) {
		personListItems = (''== personListItems?"":(personListItems+":"+emegency));
	}
	if('' != expContact ) {
		personListItems = (''== personListItems?"":(personListItems+":"+expContact));
	}
	
	$("#personListItems").val(personListItems);
	
	return true;
}

//联系人
function inintContact() {
	var str = "";
	if($("#contact1_lx").length > 0) {
		str = $("#contact1_lx").val() +"-"+ $("#contact1_tel").val() +"-"+ "CONTACT";
		if($("#contact1_cardNum").length > 0) {
			str = str +"-"+ $("#contact1_cardNum").val()+"-"+ "ID_CARD";
		}
	}
	return str;
}
//游玩人 TRAVELLER
function initTraveller() {
	var str = "";
	if($("#traveller_list_hidden").length > 0) {
		str = $("#traveller_list_hidden").val();
	}
	return str;
}
//紧急联系人 
function initEmegency() {
	var str = "";
	if($("#emergency_lx").length > 0) {
		str = $("#emergency_lx").val() +"-"+ $("#emergency_tel").val() +"-"+ "EMERGENCY_CONTACT";
	}
	return str;
}

//实体票收件人 
function initExpContact() {
	var str = "";
	if($("#exp_contact_hidden").length > 0) {
		str = $("#exp_contact_hidden").val();
	}
	return str;
}


/**
 * 页面切换
 * @param page
 */
function traverllerBack(page) {
	$("header[id^='header']").hide();
	$("section[id^='section']").hide();
	$("#header"+page).show();
	$("#section"+page).show();
}

/**
 * 显示/隐藏
 * @param tagName  标签名称
 * @param prefix   id的前缀
 * @param showName 要显示id的后缀 
 */
function personTypeStyle(tagName,prefix,showName) {
	$(tagName+"[id^='"+prefix+"']").removeClass().children("span").removeClass().addClass("ic_check_right2");
	$("#"+prefix+showName).addClass("paper_cur").children("span").removeClass().addClass("ic_check_right");
	
}


/**
 * 校验联系人 -- 暂不用
 */
function validate_contact() {
	if(!validate_username("contact1_lx")) {
		return false;
	}
	
	if(!validate_mobile("contact1_tel")) {
		return false;
	}
	
	if($("#contact1_cardNum").length > 0 ) {
		if(!valida_id_card("contact1_cardNum")) {
			return false;
		}
	}
	return true;
}

/**
 * 校验紧急联系人
 */
function validate_emergency() {
	var nameValue=$("#emergency_lx").val();
	var mobileValue=$("#emergency_tel").val();
	if($("#emergency_lx").length > 0 ){
		if(isEmpty(nameValue)) {
			popupModal(true, "请输入紧急联系人姓名", popupModal_calback, 0,true); 
			return false;
		}
	}
	if($("#emergency_tel").length > 0 ){
		if(isEmpty(mobileValue)) {
			popupModal(true, "请输入紧急联系人手机号码", popupModal_calback, 0,true); 
			return false;
		}
		
		if(!verify_mobile(mobileValue)) {
			popupModal(true, "请输入正确的紧急联系人的手机号码", popupModal_calback, 0,true); 
			return false;
		}
	}
	return true;
}

/**
 * 校验游玩人
 */
function validate_traveller() {
	var needQ = $("#fillTravellerNum").val();
	if(needQ > 0) {
		var actureQ = $("#traveller_list_hidden").val();
		if(null == actureQ || $.trim(actureQ) == '') {
			popupModal(true, "请选择游玩人", popupModal_calback, 0,true); 
			return false;
		} else {
			var aq = actureQ.split(":");
			if(needQ != aq.length) {
	            popupModal(true, "您只能选择"+needQ+"个游玩人" , popupModal_calback, 0,true); 
				return false;
			}
		}
	} 
	return true;
}

/**
 * 新增编辑游玩人校验
 * 只有姓名是必填
 * 证件验证根据其号码是否填写进行验证，如果不填则不验证
 */
function validate_addContace() {
	if(!validate_username("contact3Name")) {
		return false;
	}
	//手机为非必填项--不为空时验证
	var contact3Tel=$("#contact3Tel").val();
	if(contact3Tel!=null && !isEmpty(contact3Tel)) {
		if(!validate_mobile("contact3Tel")) {
			return false;
		}
	}
	
	var certType = $("#certType3").val();
	if("ID_CARD" == certType) {
		// 证件类型
		var contact3CertNo=$("#contact3CertNo").val();
		if(contact3CertNo!=null && !isEmpty(contact3CertNo)) {
			if(!valida_id_card("contact3CertNo")) {
				return false;
			}
		}
	} else { // 护照 --除了姓名是必填项，当证件号或者出生日期不为空时验证
		var contact3CertNo = $("#contact3CertNo").val();
		var sDate = $("#contact3BirthDay").val();
		if((null != contact3CertNo && $.trim(contact3CertNo) != '') || (null != sDate && $.trim(sDate) != '')) {
			if(!validate_huzhao("contact3CertNo")) {
				return false;
			}
			
			// 生日
			if(!isDateString("contact3BirthDay")) {
				return false;
			}
		}
		// 性别
		if(!validate_sex("contact3Sex")) {
			return false;
		}
	}
	return true;
}

/************* v3 ***************/
//判断是否合法的用户名 
function validate_username(id) {
	if($("#"+id).length > 0) {
		var m = $("#"+id).val();
		if(isEmpty(m)) {
			popupModal(true, "请输入游玩人姓名", popupModal_calback, 0,true); 
			return false;
		}
		
		var len = m.length + m.replace(/[^\u4e00-\u9fa5]+/g,"").length;
		if(!(/^[\_a-z0-9\u4e00-\u9fa5]+$/i.test(m))) {
			popupModal(true, "游玩人姓名包含非法字符", popupModal_calback, 0,true); 
			return false;
		}
		/*var len = m.length + m.replace(/[^\u4e00-\u9fa5]+/g,"").length;
		if(!(len>=4 && len<=16)) {
			popupModal(true, "游玩人姓名在4-16个字符内(一个中文两个字符)", popupModal_calback, 0,true); 
			return false;
		}
		
		var len = m.length + m.replace(/[^\u4e00-\u9fa5]+/g,"").length;
		if(!(/^[\_a-z0-9\u4e00-\u9fa5]+$/i.test(m) && (len>=4 && len<=16))) {
			popupModal(true, "游玩人姓名包含非法字符", popupModal_calback, 0,true); 
			return false;
		}
		
		if(/^[\d]+$/.test(m)) {
			popupModal(true, "游玩人姓名不能全为数字", popupModal_calback, 0,true); 
			return false;
		}*/
		return true;
	};
	return true;
	
};


//手机号校验
function validate_mobile(id) {
	if($("#"+id).length > 0) {
		var m = $("#"+id).val();
		if(isEmpty(m)) {
			popupModal(true, "请输入游玩人的手机号码", popupModal_calback, 0,true); 
			return false;
		}
		
		if (!m.match(/^1[3|4|5|8][0-9]\d{4,8}$/)
				|| m.length != 11) {
			popupModal(true, "请输入正确的手机号码", popupModal_calback, 0,true); 
			return false;
		} else {
			return true;
		}
	}
	return true;
}

// 身份证校验
function valida_id_card(id) {
	var card = $("#"+id).val();
	if(isEmpty(card)) {
		popupModal(true, "请输入游玩人的证件号码", popupModal_calback, 0,true); 
		return false; 
	}
	
	var code = validateIdCard(card);
	if("true" != code) {
		popupModal(true, "请输入正确的身份证号码", popupModal_calback, 0,true); 
		return false;
	}
	return true;
}

// 护照校验
function validate_huzhao(id) {
	var m = $("#"+id).val();
	if(isEmpty(m)) {
		popupModal(true, "请输入游玩人的证件号码", popupModal_calback, 0,true); 
		return false;
	}else{
		return true;
	}
	/*if (!m.match( /^(P|p\d{7})|(G|g\d{8})$/)) {
		popupModal(true, "请输入正确的护照", popupModal_calback, 0,true); 
		return false;
	} else {
		return true;
	}*/
}

//性别校验
function validate_sex(id) {
	if($("#"+id).length > 0) {
		var m = $("#"+id).val();
		if(isEmpty(m)) {
			popupModal(true, "请选择性别", popupModal_calback, 0,true); 
			return false;
		}
	}
	return true;
}

// 日期校验
function isDateString(id) {
	var sDate = $("#"+id).val();
	if(null == sDate || $.trim(sDate) == '') {
		popupModal(true, "请输入游玩人的生日", popupModal_calback, 0,true); 
		return false;
	}
	// 去掉空格 
	sDate = $.trim(sDate);
	
	// 支持yyyyMMdd格式
	if(sDate.indexOf("-") == -1) {
		var pattern=/^[1-2]\d{3}(0?[1-9]|1[0-2])(0?[1-9]|[12][0-9]|3[0-1])$/ ;
		if(!pattern.test(sDate)){
			popupModal(true, "请输入正确的日期格式", popupModal_calback, 0,true); 
			return false;
		}
		// 格式转换
		if(sDate.length == 8) {
			sDate = sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-" + sDate.substring(6,8);
		}
	}
	var mp = /^[1-2]\d{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[0-1])$/;
	if (!mp.test(sDate)){
		popupModal(true, "请输入正确的日期格式", popupModal_calback, 0,true); 
		return false;
	}
	var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var iaDate = new Array(3);
	var year, month, day;

	iaDate = sDate.split("-");
	year = parseFloat(iaDate[0]);
	month = parseFloat(iaDate[1]);
	day = parseFloat(iaDate[2]);
	if (year < 1900 || year > 2200){
		popupModal(true, "请输入正确的年份，有效范围1900-2200", popupModal_calback, 0,true); 
		return false;
	}
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
		iaMonthDays[1] = 29;
	if (month < 1 || month > 12){
		popupModal(true, "请输入正确的日期月份", popupModal_calback, 0,true); 
		return false;
	}
	if (day < 1 || day > iaMonthDays[month - 1]){
		popupModal(true, "请输入正确的日期", popupModal_calback, 0,true); 
		return false;
	}
	$("#"+id).val(sDate);
	return true;
} 
