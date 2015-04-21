function putErrorSpan(id,msg){
	var html = "<span class='tip-icon tip-icon-error'></span>"+msg;
	id.siblings("span").html(html);
}
function putOkSpan(id){
	id.siblings("span").html("<span class='tip-icon tip-icon-success'></span>");
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

//检验是否价格数字
jQuery.validator.addMethod("isFloatNumber", function(value, element) {  
	var reg= /^\d+[\.]?\d{0,2}$/g;
	var v = ""+value;
	if(v.substr(-1) == "."){
		return false;
	}
    return this.optional(element) || reg.test(value.replace(/\s/g,""));
}, "请输入不超过2位小数的数字");
var cashMoneyDraw = {
		errorPlacement:function(error,element){
			if(error.html()==""){
				putOkSpan(element);
			}else{
				putErrorSpan(element,error.html());
			}
		},
		success:function(element) {
			putOkSpan(element);
		},
		rules : {
			drawAmountY : {
				required : true,
				isFloatNumber : true,
				remote:{
				    url: "/userspace/cashAccount/drawAjaxCheck.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	drawAmountY: function() {
				            return $("#drawAmo untY").val();
				        }
				    }
				}
			},
			memo : {
				required : true,
				remote:{
				    url: "/userspace/cashAccount/drawAjaxCheck.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式  
				    data: {                     //要传递的数据
				    	payword: function() {
				            return $("#memo").val();
				        }
				    }
				}
			},
			kaiHuHang : {
				required : true
			},
			bankAccountName : {
				required : true
			},
			bankAccount : {
				required : true
			}
		},
		messages : {
			drawAmountY : {
				required : "请输入提现金额",
				isFloatNumber : "请输入正确的提现金额",
				remote : "输入的提现金额大于可用金额"
			},
			memo : {
				required : "请输入支付密码",
				remote : "支付密码错误"
			},
			kaiHuHang : {
				required : "请输入转账银行"
			},
			bankAccountName : {
				required : "请输入账户姓名"
			},
			bankAccount : {
				required : "请输入银行账号"
			}

		}
	};
