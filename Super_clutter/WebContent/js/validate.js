String.prototype.trim = function () {
	return this.replace(/^\s+|\s+$/g, "");
};

var aCity = {11 : "北京",12 : "天津",13 : "河北",14 : "山西",15 : "内蒙古",21 : "辽宁",22 : "吉林",
	23 : "黑龙江",31 : "上海",32 : "江苏",33 : "浙江",34 : "安徽",35 : "福建",36 : "江西",
	37 : "山东",41 : "河南",42 : "湖北",43 : "湖南",44 : "广东",45 : "广西",46 : "海南",
	50 : "重庆",51 : "四川",52 : "贵州",53 : "云南",54 : "西藏",61 : "陕西",62 : "甘肃",
	63 : "青海",64 : "宁夏",65 : "新疆",71 : "台湾",81 : "香港",82 : "澳门",91 : "国外"
};

// 身份证校验 
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

// 手机号校验
function isMobile(m) {
	if(isEmpty(m)) {
		lvToast(false,"请输入订单联系人的手机号码",LT_LOADING_CLOSE);
		return false;
	}
	if (!m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		lvToast(false,"请输入正确的订单联系人的手机号码",LT_LOADING_CLOSE);
		return false;
	} else {
		return true;
	}
}

//手机号校验
function isMobile_new(m) {
	if(isEmpty(m)) {
		return false;
	}
	if (!m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		return false;
	} else {
		return true;
	}
}

// 校验是否为空
function isEmpty(m) {
	if (null == m || m.trim() == "") {
		return true;
	} else {
		return false;
	}
}

// 是否是数字
function isNumber(m) {
	if(!isEmpty(m)) {
		var reg = /^\d+$/;
		if (m.constructor === String) {
			if (m.match(reg)) {
				return true;
			} else {
				return false;
			}
		}
	}
	return false;
}

function isCityChecked(id){
	if(isEmpty(id)) {
		return false;
	}
	var city = $("#"+id).val();
	if(isEmpty(city) || "请选择" == city ) {
		return false;
	}
	return true;
}