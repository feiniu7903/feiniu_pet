function putErrorSpan(id,msg){
	$("#"+id).siblings(".tureTipsBox").hide();
	var html = "<span class='tip-icon tip-icon-error'></span>"+msg;
	$("#"+id).siblings(".errorTipsBox").html(html);
}
function putOkSpan(id){
	$("#"+id).siblings(".errorTipsBox").html("");
	$("#"+id).siblings(".tureTipsBox").show();
}
jQuery.extend(jQuery.validator.messages, {
	required : "必选字段",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 (ISO).",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
	minlength : jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
	rangelength : jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
	range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : jQuery.validator.format("请输入一个最大为{0} 的值"),
	min : jQuery.validator.format("请输入一个最小为{0} 的值")
});

jQuery.validator.addMethod("maxCNLen", function(value, element, param) {
	value = value.replace(/[^\x00-\xff]/g, "**");
	var len = value.length;
	return this.optional(element) || len <= param;
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

//邮政编码验证  
jQuery.validator.addMethod("isZipCode", function(value, element) {  
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

//(A-Z,a-z，0-9)
jQuery.validator.addMethod("checkCharacter", function(value, element) {  
    var v = /^[\_a-zA-Z0-9]+$/i;
    return this.optional(element) || (v.test(value));
}, "包含非法字符");

//字符不能全是数字
jQuery.validator.addMethod("isNotNumber", function(value, element) {  
    var v = /^[\d]+$/;
    return this.optional(element) || (!v.test(value));
}, "不能全为数字");

//检验手机号码
jQuery.validator.addMethod("isphone", function(value, element) {  
    var tel = /^1[3|4|5|8][0-9]\d{8}$/;
    return this.optional(element) || (tel.test(value.replace(/\s/g,"")));
}, "请输入11位数字的手机号码");

//检验手机号码
jQuery.validator.addMethod("checkPassword", function(value, element) {  
    var tel = $("#loginPassword").val();
    return this.optional(element) || (tel==value);
}, "两次输入的密码不一致");

var personReg = {
		errorPlacement:function(error,element){
			if(error.html()==""){
				putOkSpan(element.attr("id"));
			}else{
				putErrorSpan(element.attr("id"),error.html());
			}
		},
		success:function(element) {
			putOkSpan(element.attr("id"));
		},
		rules : {
			userName : {
				required : true,
				rangelength:[4,16],
				isNotNumber:true,
				checkCharacter:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	userName: function() {
				            return $("#userName").val();
				        }
				    }
				}
			},
			email : {
				required : true,
				email:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	email: function() {
				            return $("#email").val();
				        }
				    }
				}
			},
			mobileNumber : {
				required : true,
				isphone : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	mobile: function() {
				            return $("#mobileNumber").val();
				        }
				    }
				}
			},
			loginPassword : {
				required : true,
				rangelength:[4,16]
			},
			loginPasswordTwo : {
				required : true,
				checkPassword : true
			},
			realName : {
				required : true,
				maxCNLen : 40
			},
			zipCode : {
				required : true,
				isZipCode : true
			},
			address : {
				required : true
			},
			imageCode : {
				required : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	imageCode: function() {
				            return $("#imageCode").val();
				        }
				    }
				}
			}
		},
		messages : {
			userName : {
				required : "请输入用户名",
				isNotNumber:"用户名不能全是数字",
				checkCharacter:"用户名包含非法字符",
				rangelength:jQuery.validator.format("用户名长度介于 {0} 到 {1} 之间"),
				remote:"用户名已使用"
			},
			email : {
				required : "请输入邮箱",
				remote:"该邮箱已被使用，请更换其他邮箱"
			},
			mobileNumber : {
				required : "请输入手机号码",
				isphone : "请输入11位数字的手机号码",
				remote:"该手机已使用，请更换其他手机号"
			},
			loginPassword : {
				required : "请输入密码",
				rangelength:jQuery.validator.format("密码长度介于 {0} 到 {1} 之间")
			},
			loginPasswordTwo : {
				required : "请再次输入密码"
			},
			realName : {
				required : "请输入真实姓名",
				maxCNLen : "真实姓名不能超过40个字符"
			},
			zipCode : {
				required : "请输入邮编"
			},
			address : {
				required : "请输入通讯地址"
			},
			imageCode : {
				required : "请输入验证码",
				remote:"验证码错误"
			}
		}
	};
var personUpdate = {
		errorPlacement:function(error,element){
			if(error.html()==""){
				putOkSpan(element.attr("id"));
			}else{
				putErrorSpan(element.attr("id"),error.html());
			}
		},
		success:function(element) {
			putOkSpan(element.attr("id"));
		},
		rules : {
			email : {
				required : true,
				email:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	email: function() {
				            return $("#email").val();
				        }
				    }
				}
			},
			mobileNumber : {
				required : true,
				isphone : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	mobile: function() {
				            return $("#mobileNumber").val();
				        }
				    }
				}
			},
			realName : {
				required : true,
				maxCNLen : 40
			},
			zipCode : {
				required : true,
				isZipCode : true
			},
			address : {
				required : true
			},
			imageCode : {
				required : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	imageCode: function() {
				            return $("#imageCode").val();
				        }
				    }
				}
			}
		},
		messages : {
			email : {
				required : "请输入邮箱",
				remote:"该邮箱已被使用，请更换其他邮箱"
			},
			mobileNumber : {
				required : "请输入手机号码",
				isphone : "请输入11位数字的手机号码",
				remote:"该手机已使用，请更换其他手机号"
			},
			realName : {
				required : "请输入真实姓名",
				maxCNLen : "真实姓名不能超过40个字符"
			},
			zipCode : {
				required : "请输入邮编"
			},
			address : {
				required : "请输入通讯地址"
			},
			imageCode : {
				required : "请输入验证码",
				remote:"验证码错误"
			}
		}
	};

var companyReg = {
		errorPlacement:function(error,element){
			if(error.html()==""){
				putOkSpan(element.attr("id"));
			}else{
				putErrorSpan(element.attr("id"),error.html());
			}
		},
		success:function(element) {
			putOkSpan(element.attr("id"));
		},
		rules : {
			userName : {
				required : true,
				rangelength:[4,16],
				isNotNumber:true,
				checkCharacter:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	userName: function() {
				            return $("#userName").val();
				        }
				    }
				}
			},
			email : {
				required : true,
				email:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	email: function() {
				            return $("#email").val();
				        }
				    }
				}
			},
			mobileNumber : {
				required : true,
				isphone : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	mobile: function() {
				            return $("#mobileNumber").val();
				        }
				    }
				}
			},
			loginPassword : {
				required : true,
				rangelength:[4,16]
			},
			loginPasswordTwo : {
				required : true,
				checkPassword : true
			},
			realName : {
				required : true,
				maxCNLen : 40
			},
			zipCode : {
				required : true,
				isZipCode : true
			},
			address : {
				required : true
			},
			companyName : {
				required : true
			},
			chargeName : {
				required : true
			},
			serviceTel : {
				required : true
			},
			imageCode : {
				required : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	imageCode: function() {
				            return $("#imageCode").val();
				        }
				    }
				}
			}
		},
		messages : {
			userName : {
				required : "请输入用户名",
				isNotNumber:"用户名不能全是数字",
				checkCharacter:"用户名包含非法字符",
				rangelength:jQuery.validator.format("用户名长度介于 {0} 到 {1} 之间"),
				remote:"用户名已使用"
			},
			email : {
				required : "请输入邮箱",
				remote:"该邮箱已被使用，请更换其他邮箱"
			},
			mobileNumber : {
				required : "请输入手机号码",
				isphone : "请输入11位数字的手机号码",
				remote:"该手机已使用，请更换其他手机号"
			},
			loginPassword : {
				required : "请输入密码",
				rangelength:jQuery.validator.format("密码长度介于 {0} 到 {1} 之间")
			},
			loginPasswordTwo : {
				required : "请再次输入密码"
			},
			realName : {
				required : "请输入真实姓名",
				maxCNLen : "真实姓名不能超过40个字符"
			},
			zipCode : {
				required : "请输入邮编"
			},
			address : {
				required : "请输入通讯地址"
			},
			companyName : {
				required : "请输入公司名称"
			},
			chargeName : {
				required : "请输入客服负责人姓名"
			},
			serviceTel : {
				required : "请输入客服电话"
			},
			imageCode : {
				required : "请输入验证码",
				remote:"验证码错误"
			}
		}
	};

var companyUpdate = {
		errorPlacement:function(error,element){
			if(error.html()==""){
				putOkSpan(element.attr("id"));
			}else{
				putErrorSpan(element.attr("id"),error.html());
			}
		},
		success:function(element) {
			putOkSpan(element.attr("id"));
		},
		rules : {
			email : {
				required : true,
				email:true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	email: function() {
				            return $("#email").val();
				        }
				    }
				}
			},
			mobileNumber : {
				required : true,
				isphone : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	mobile: function() {
				            return $("#mobileNumber").val();
				        }
				    }
				}
			},
			realName : {
				required : true,
				maxCNLen : 40
			},
			zipCode : {
				required : true,
				isZipCode : true
			},
			address : {
				required : true
			},
			companyName : {
				required : true
			},
			chargeName : {
				required : true
			},
			serviceTel : {
				required : true
			},
			imageCode : {
				required : true,
				remote:{
				    url: "/ajax/checkUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	imageCode: function() {
				            return $("#imageCode").val();
				        }
				    }
				}
			}
		},
		messages : {
			email : {
				required : "请输入邮箱",
				remote:"该邮箱已被使用，请更换其他邮箱"
			},
			mobileNumber : {
				required : "请输入手机号码",
				isphone : "请输入11位数字的手机号码",
				remote:"该手机已使用，请更换其他手机号"
			},
			realName : {
				required : "请输入真实姓名",
				maxCNLen : "真实姓名不能超过40个字符"
			},
			zipCode : {
				required : "请输入邮编"
			},
			address : {
				required : "请输入通讯地址"
			},
			companyName : {
				required : "请输入公司名称"
			},
			chargeName : {
				required : "请输入客服负责人姓名"
			},
			serviceTel : {
				required : "请输入客服电话"
			},
			imageCode : {
				required : "请输入验证码",
				remote:"验证码错误"
			}
		}
	};