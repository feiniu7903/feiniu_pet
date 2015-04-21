var bd_status = globe.cookie("bd_framework") || globe.getUrlParam("bd_framework") || $localStorage.getItem("bd_framework");//判断框内框外1 框内
/*订单返回详情页用*/
function order_skip(requestPath,productId,branchId,canOrderToday) {
	//lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	var location=window.location.href;
	url=requestPath+'/order/order_fill.htm?productId='+productId+'&branchId='+branchId+'&canOrderToday='+canOrderToday;
	window.location.href = url;
}
/*进入门票订单填写页*/
function order_ticket(requestPath,productId,branchId,canOrderToday) {
	url=requestPath+'/ticketorder/order_fill.htm?productId='+productId+'&branchId='+branchId+'&canOrderToday='+canOrderToday;
	window.location.href = url;
}
//==================================================================================================
//新弹框确定事件
function logoutNow(location) {
	var backStatus=$("#backStatus").val();
	if(backStatus!=null && backStatus=='home'){
		window.location.href='http://'+hostName;
		delSion();
	}else{
		if(null!=location){
			//返回页面
			var count=parseInt(location);
			window.history.go(-count);
			delSion();
		}
	}	
	popupModal(false, '', null, 0,false);
}
//新弹框取消事件
function popupModal_logOut(){
		popupModal(false, '', null, 0,false);
		$("#backStatus").val("");
		$("#backButton").val("");
}
//点击返回--判断产品是否可售做相应操作
function url_back_now(location){
	var productItems=$("#productItems").val();
	$("#backButton").val("status");
	 if(productItems!=null && productItems!=''){
		//第一个TRUE是半段弹出框是否显示true显示false隐藏,第二个TRUE判断几个按钮TRUE一个FALSE是两个
		popupModal(true, "您的订单尚未完成，是否确定要离开当前页面？", null, 0,false);
		$('.ic_roseo').click(function(){
			var backButton=$("#backButton").val();
			if(backButton!=null && backButton=='status'){
				logoutNow(location);
			}
		});
		$('.ic_yellow').click(function(){
			popupModal_logOut();
		});
		
	}else{
		union_back();
	}
}
//HOME返回--判断产品是否可售做相应操作
 function home_back(){
	 var productItems=$("#productItems").val();
	 $("#backButton").val("status");
	 if(productItems!=null && productItems!=''){
		//第一个TRUE是半段弹出框是否显示true显示false隐藏,第二个TRUE判断几个按钮TRUE一个FALSE是两个
		popupModal(true, "您的订单尚未完成，是否确定要离开当前页面？", null, 0,false); 
		$("#backStatus").val("home");
		$('.ic_roseo').bind('click',function(){
			var backButton=$("#backButton").val();
			if(backButton!=null && backButton=='status'){
				logoutNow(location);
			}
		});
		$('.ic_yellow').bind('click',function(){
			popupModal_logOut();
		});
	 }else{
		 window.location.href='http://'+hostName;
	 }
 }
//删除session属性
function delSion() {
	//删除session属性
	$.ajax({
        type: "post",
        url: contextPath+'/order/del_sion.htm',
        success: function () {
        },
        error: function () {
        }
    });
}
//====================================================================================
$(function() {
	// 非今日票，默认没有游玩时间 
	var visitTime=$("#visitTime").val();
	if(visitTime!=null && "" == visitTime) {
		$("#timeHolder").html("请选择游玩日期");
		$("#visitTime").hide();
	}
	//提交订单
	$("#baidu_order_submit").click(function(){
		$("#login_click_type").val("");
		baiduLoginStatus();
	});
});
//点击订单列表
function myOrderList(){
	$("#login_click_type").val("1");
	baiduLoginStatus();
}
function clickType(){
	var login_click_type=$("#login_click_type").val();
	if(login_click_type!=null && login_click_type=="1"){
		return true;
	}else{
		return false
	}
}
/**
 * 百度活动预订
 */
function baidu_order_ticket(requestPath,productId,branchId,canOrderToday){
		var url=requestPath+'/bdorder/baidu_order_fill.htm?productId='+productId+'&branchId='+branchId+'&canOrderToday='+canOrderToday;
		window.location.href = url;
}
/**
 * 判断是否百度登陆
 */
function baiduLoginStatus(){
	if(!clickType()){
		if(!initTikcetOrderTravllerFormBaidu()) {
			return false;
		}
	}
	//提交操作
	var mobile=$('#mobile').val();
    var url = contextPath+'/bdorder/baidu_login_status.htm';
    
	$.ajax({
		url : url,
		type : "POST",
		success : function(data) {
			// 百度已经登录
			if(data!=null && data.status=="0") {
				if(clickType()){
					window.parent.location.href ="http://qing.lvmama.com/clutter/order/myorder.htm";
				}else{
					baiduOrderSubmit();
				}
			} else {//未登录
				if(loginStatus){
					baidu_login(mobile);
				}else{
					globe.lvToast(false,"页面尚未加载完，请耐心等待...",LT_LOADING_CLOSE);
				}
			}
		},
		error:function() {
			globe.lvToast(false,"哎呀,网络不给力,请稍后再试试吧",LT_LOADING_CLOSE);
		}
	});
}
function onSuccess() {
	clouda.mbaas.account.closeLoginDialog();	
	$("#wrapper").show();
	//判断用户点击的是提交订单还是右上角订单列表
	if(clickType()){
		window.parent.location.href ="http://qing.lvmama.com/clutter/order/myorder.htm";
	}else{
		baiduOrderSubmit();
	}
}
function onFail() {
	//clouda.mbaas.account.closeLoginDialog();	
	//alert('登陆失败');
	$("#wrapper").show();
}
function baidu_login(mobile){
	 var options = 
     { 
     'redirect_uri': "http://qing.lvmama.com/static/baidulogin.html", 
     'scope': 'basic',
     'login_type': 'sms', 
     'mobile': mobile, 
     'display':'mobile', 
     'login_mode': 1,
     'onsuccess': onSuccess, 
     'onfail': onFail
     }; 
	 $("#wrapper").hide();
     clouda.mbaas.account.login(options);
}
/**
 * 百度提交订单
 * @returns {Boolean}
 */
function baiduOrderSubmit() {
	var baidutype;//1是框内0是框外
	if(bd_status==1){
		baidutype=1;
	}else{
		baidutype=0;
	}
	//初始化验证
	if(!initTikcetOrderTravllerFormBaidu()) {
		return false;
	}
	//提交操作
    var url = contextPath+'/bdorder/baidu_order_submit.htm';
    var idCard=$("#idCard").val();
    if(idCard==null || typeof(idCard) == 'undefined'){
    	idCard="";
    }
    var parm={"baidutype":baidutype,"productId":$("#productId").val(),"branchId":$("#branchId").val(),"quantity":$("#quantity").val(),"canOrderToday":$("#canOrderToday").val(),"mobile":$("#mobile").val(),"userName":encodeURIComponent($("#userName").val()),"idCard":idCard,"visitTime":$("#visitTime").val()}
    //遮罩层
    globe.createShadowBody();
   // globe.lvToast(false,"订单提交中，请稍后...",5000);
    //按钮转化防止重复提交
    $("#baidu_order_submit_hide").show();
    $("#baidu_order_submit").hide();
	$.ajax({
		url : url,
		data : parm,
		type : "POST",
		success : function(data) {
			// 如果可以直接提交 
			if(data!=null && data.orderId!=null) {
				 // 页面跳转 
				window.location.href = contextPath + "/bdorder/baidu_order_success.htm?orderId="+data.orderId;
				$("#baidu_order_submit_hide").hide();
			    $("#baidu_order_submit").show();
			} else {
				globe.lvToast(false,data.msg,LT_LOADING_CLOSE);
				var qingPlaceUrl=$("#qingPlaceUrl").val();
				if(data.msg=="用户已经购买不能继续下单"){//延迟三秒
					//三秒后请求链接
					setTimeout(function() {
						window.location.href=qingPlaceUrl;
					},3000); 
				}
				//按钮转化防止重复提交
				$("#baidu_order_submit_hide").hide();
			    $("#baidu_order_submit").show();
			    globe.revmoePop();//失败去除遮罩
			}
		},
		error:function() {
			globe.lvToast(false,"哎呀,网络不给力,请稍后再试试吧",LT_LOADING_CLOSE);
			//按钮转化防止重复提交
			$("#baidu_order_submit_hide").hide();
		    $("#baidu_order_submit").show();
		    globe.revmoePop();//失败去除遮罩
		}
	});
}
/**
 * 百度支付
 */
function payNow(orderId,objectName,objectDesc,objectPageUrl){
	setLocalStorage("lt_order_current_pay_order_id",orderId);
	baiduPay.pay(orderId,objectName,objectDesc,objectPageUrl);
}
/**
 * 初始化门票表单 
 * @returns {Boolean}
 */
function initTikcetOrderTravllerFormBaidu() {
	if($("#visitTime").length>0 && $("#visitTime").val() == null ||$("#visitTime").val() == "" ) {
		globe.lvToast(false,"请选择游玩日期!",LT_LOADING_CLOSE);
		return false;
	}
	
	// 校验用户名
	if(!validateUserName()) {
		globe.lvToast(false,"请输入订单联系人的姓名",LT_LOADING_CLOSE);
		return false;
	}
	
	// 校验手机号
	if(!isMobileBaidu($('#mobile').val())){
		return false;
	}
	
	// 校验身份证. 
	if($("#needIdCard").val() == "true" && $("#idCard").length > 0) {
		var card = $("#idCard").val();
		if(isEmpty(card)) {
			globe.lvToast(false,"请输入联系人的身份证号码",LT_LOADING_CLOSE);
			return false; 
		}
		
		var code = validateIdCard(card);
		if("true" != code) {
			globe.lvToast(false,"请输入正确的订单联系人的身份证号码",LT_LOADING_CLOSE);
			return false;
		}
	}
	
	// 驴行协议勾选. 
	if(!isCheckedAgreementBaidu()){
		return false ;
	}
	
	return true;
}
//判断是否合法的用户名 
function validateUserName() {
	if($("#userName").length > 0) {
		var m = $("#userName").val();
		if(!isEmpty(m) && m.length < 50) {
			return true;
		} else {
			return false;
		};
	};
};
//手机号校验
function isMobileBaidu(m) {
	if(isEmpty(m)) {
		globe.lvToast(false,"请输入订单联系人的手机号码",LT_LOADING_CLOSE);
		return false;
	}
	if (!m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		globe.lvToast(false,"请输入正确的订单联系人的手机号码",LT_LOADING_CLOSE);
		return false;
	} else {
		return true;
	}
}
//校验是否为空
function isEmpty(m) {
	if (null == m || m.trim() == "") {
		return true;
	} else {
		return false;
	}
}
//身份证校验 
function validateIdCard(sId) {
	if (!isEmpty(sId)) {
		var iSum = 0;
		if (!/^\d{17}(\d|x)$/i.test(sId)) {
			return "您输入的身份证长度或格式错误！";
		}
		sId = sId.replace(/x$/i, "a");
		if (aCity[parseInt(sId.substr(0, 2))] == null) {
			return "错误的身份证号码！";
		}
		sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-"
				+ Number(sId.substr(12, 2));
		var d = new Date(sBirthday.replace(/-/g, "/"));
		if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
				.getDate())) {
			return "身份证日期信息有误！";
		}
		for ( var i = 17; i >= 0; i--){
			iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
		}
		if (iSum % 11 != 1) {
			return "错误的身份证号码！";
			//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")
		}
		return 'true';
	}
	return '请输入联系人的身份证号码';
}
var aCity = {11 : "北京",12 : "天津",13 : "河北",14 : "山西",15 : "内蒙古",21 : "辽宁",22 : "吉林",
		23 : "黑龙江",31 : "上海",32 : "江苏",33 : "浙江",34 : "安徽",35 : "福建",36 : "江西",
		37 : "山东",41 : "河南",42 : "湖北",43 : "湖南",44 : "广东",45 : "广西",46 : "海南",
		50 : "重庆",51 : "四川",52 : "贵州",53 : "云南",54 : "西藏",61 : "陕西",62 : "甘肃",
		63 : "青海",64 : "宁夏",65 : "新疆",71 : "台湾",81 : "香港",82 : "澳门",91 : "国外"
	};
//勾选驴行协议. 
function isCheckedAgreementBaidu() {
	var check = $("#agreement").attr('checked');
	if(check==false) {
		var msg_type = "票务预订协议";
		globe.lvToast(false,"您需要同意驴妈妈"+msg_type+"才能预订哦",LT_LOADING_CLOSE);
		return false;
	}
	return true;
}
//页面跳转.
function time_price_skip(url) {
	/*lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.open(url);*/
	//联系人信息保存LOC
	var userName=$("#userName").val();
	var mobile=$("#mobile").val();
	var idCard=$("#idCard").val();
	var econtractEmail=$("#econtractEmail").val();
	if(userName!=null && userName!=""){
		setLocalStorage("link_user_name",userName);
	}
	if(mobile!=null && mobile!=""){
		setLocalStorage("link_user_mobile",mobile);
	}
	if(idCard!=null && idCard!=""){
		setLocalStorage("link_user_idCard",idCard);
	}
	if(econtractEmail!=null && econtractEmail!=""){
		setLocalStorage("link_user_email",econtractEmail);
	}
	
	window.location.href=url;
	
}
//设置localStroage
function setLocalStorage(key,value){
	try {
	window.localStorage.setItem(key,value);
	} catch(err){
		
	}
}
//根据key获取值.
function getLocalStorage(key) {
	try {
		return null == window.localStorage.getItem(key)?"":window.localStorage.getItem(key);
	} catch(err){
		
	}
}