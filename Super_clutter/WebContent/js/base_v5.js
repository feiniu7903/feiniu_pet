// JavaScript Document
var SysSecond;
var InterValObj;
var t = n =0, count;// 轮播参数
var LT_LOADING_MSG = "请稍等..."; // 提示信息
var CONTENT_LOGDING = "loading"; // 提示信息
var LT_LOADING_TIME = 10000; // 加载等待时间 30秒
var LT_LOADING_CLOSE = 3000; // 关闭时间3秒
var LT_ORDER_SUBMIT_ERROR = "哎呀,网络不给力,请稍后再试试吧";
//页面跳转.
function union_skip(url) {
	//lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.location.href = url;
}
String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}
/**
 * 判断是否登录 .
 * 
 * @param objectId
 * @param objectType
 * @param objectImageUrl
 * @param objectName
 */
function addFavoritor(objectId, objectType, objectImageUrl, objectName) {
	$.ajax({
		type : "POST",
		url : contextPath + "/islogin.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : {},
		dataType : "json",
		success : function(data) {
			// 已经登录
			if (data.code == '1') {
				f_add(objectId, objectType, objectImageUrl, objectName);
			} else {
				window.location.href = contextPath
						+ "/user/user_favoritor.htm?objectType=" + objectType
						+ "&objectId=" + objectId;
			}
		},
		error : function(e, statusText, error) {

		}
	});
}

/**
 * 取消
 */
function cancelFavoritor(objectId, objectType) {
	$.ajax({
		type : "POST",
		url : contextPath + "/islogin.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : {},
		dataType : "json",
		success : function(data) {
			// 已经登录
			if (data.code == '1') {
				f_cancel(objectId);
			} else {
				window.location.href = contextPath
						+ "/user/user_favoritor.htm?objectType=" + objectType
						+ "&objectId=" + objectId;
			}
		},
		error : function(e, statusText, error) {

		}
	});
}

// 添加收藏
function f_add(objectId, objectType, objectImageUrl, objectName) {
	var param = {
		"objectId" : objectId,
		"objectImageUrl" : objectImageUrl,
		"objectName" : encodeURIComponent(objectName),
		"objectType" : objectType
	};
	$.ajax({
		type : "post",
		url : contextPath + "/user/submit_favoritor.htm",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.code == '1') {
				globe.lvToast(true, "已收藏成功!", LT_LOADING_CLOSE);
				$("#favoritor_in").hide();
				$("#favoritor_out").show();
			}else {
				globe.lvToast(false, "收藏失败，请重试!", LT_LOADING_CLOSE);
			}
		},
		error : function(e, statusText, error) {
			globe.lvToast(false, "收藏失败，请重试!", LT_LOADING_CLOSE);
		}
	});
}
// 取消收藏 .
function f_cancel(objectId) {
	var param = {
		"objectId" : objectId
	};
	$.ajax({
		type : "POST",
		url : contextPath + "/user/cancel_favoritor.htm",
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.code == '1') {
				globe.lvToast(true, "已取消收藏!", LT_LOADING_CLOSE);
				$("#favoritor_in").show();
				$("#favoritor_out").hide();
			} else {
				globe.lvToast(false, "取消收藏失败，请重试!", LT_LOADING_CLOSE);
			}
		},
		error : function(e, statusText, error) {
			globe.lvToast(false, "取消收藏失败，请重试!", LT_LOADING_CLOSE);
		}
	});
}
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}