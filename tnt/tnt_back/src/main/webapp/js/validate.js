var changeChannel = function(select) {
	var channelId = select.value;
	var url = "changeChannel?channelId=" + channelId;
	$.getJSON(url, function(data) {
		var json = eval(data);
		var element = "<option value=''>--请选择--</option>";
		$(json).each(
				function(i) {
					var entry = $(json)[i];
					var array = entry.split(":");
					element += "<option value='" + array[0] + "'>" + array[1]
							+ "</option>";
				});
		document.getElementById("companyTypeId").innerHTML = element;
	});
};

var changeProductType = function(select, to) {
	var productType = select.value;
	var url = "changeProductType?productType=" + productType;
	$.getJSON(url, function(data) {
		var json = eval(data);
		var element = "<option value=''>--请选择--</option>";
		$(json).each(
				function(i) {
					var entry = $(json)[i];
					var array = entry.split(":");
					element += "<option value='" + array[0] + "'>" + array[1]
							+ "</option>";
				});
		document.getElementById(to).innerHTML = element;
	});
};

var companyChannel = {
	rules : {
		channelName : {
			required : true,
			maxCNLen : 50
		}
	},
	messages : {
		channelName : {
			required : "请输入渠道名称",
			maxCNLen : jQuery.format("渠道名称不能大于{0}个字 符")
		}
	}
};

var user = {
	rules : {
		failReason : {
			required : true,
			maxCNLen : 300
		},
		queryStartDate : {
			dateISO : true
		},
		queryEndDate : {
			dateISO : true
		},
		queryStartContractStartDate : {
			dateISO : true
		},
		queryEndContractStartDate : {
			dateISO : true
		},
		queryStartContractEndDate : {
			dateISO : true
		},
		queryEndContractEndDate : {
			dateISO : true
		}
	},

	messages : {
		failReason : {
			required : "请输入理由",
			maxCNLen : jQuery.format("理由不能大于{0}个字 符")
		},
		queryStartDate : {
			dateISO : "请输入正确的日期格式"
		},
		queryEndDate : {
			dateISO : "请输入正确的日期格式"
		},
		queryStartContractStartDate : {
			dateISO : "请输入正确的日期格式"
		},
		queryEndContractStartDate : {
			dateISO : "请输入正确的日期格式"
		},
		queryStartContractEndDate : {
			dateISO : "请输入正确的日期格式"
		},
		queryEndContractEndDate : {
			dateISO : "请输入正确的日期格式"
		}
	}
};

var companyType = {
	rules : {
		channelId : {
			required : true
		},
		companyTypeName : {
			required : true,
			maxCNLen : 50
		},
		companyTypeCode : {
			required : true,
			maxCNLen : 50
		}
	},
	messages : {
		channelId : {
			required : "请选择分销商渠道"
		},
		companyTypeName : {
			required : "请输入类型名称",
			maxCNLen : jQuery.format("类型不能大于{0}个字 符，中文算2个字符")
		},
		companyTypeCode : {
			required : "请输入code名称",
			maxCNLen : jQuery.format("code不能大于{0}个字 符")
		}
	}
};

var commissionRule = {
	rules : {
		productType : {
			required : true
		},
		subProductType : {
			required : function() {
				return $("#productType").val() == 3;
			}
		},
		payOnline : {
			required : true,
			maxCNLen : 50
		},
		minSales : {
			digits : true,
			maxCNLen : 10,
			required : "#maxSales:blank"
		},
		maxSales : {
			digits : true,
			maxCNLen : 10,
			required : "#minSales:blank"
		},
		discountRate : {
			required : true,
			digits : true,
			maxCNLen : 3,
			max : 100
		}
	},
	messages : {
		productType : {
			required : "请选择产品类型"
		},
		subProductType : {
			required : "请选择产品子类型"
		},
		payOnline : {
			required : "请选择支付方式"
		},
		minSales : {
			digits : "月销售额最小值必须为数字",
			maxCNLen : jQuery.format("月销售额最小值不能大于{0}个字 符")
		},
		maxSales : {
			digits : "月销售额最大值必须为数字",
			maxCNLen : jQuery.format("月销售额最大值不能大于{0}个字 符")
		},
		discountRate : {
			required : "请输入分销返佣点的值",
			digits : "分销返佣点必须为数字",
			maxCNLen : jQuery.format("分销返佣点值不能大于{0}个字 符"),
			max : jQuery.format("分销返佣点值不能大于{0}")
		}
	}
};

var tntAnnouncement = {
	rules : {
		title : {
			required : true,
			maxCNLen : 200
		},
		body : {
			required : true,
			maxCNLen : 500
		}
	},
	messages : {
		title : {
			required : "请输入标题",
			maxCNLen : jQuery.format("标题不能大于{0}个字 符")
		},
		body : {
			required : "请输入内容",
			maxCNLen : jQuery.format("内容不能大于{0}个字 符")
		}
	}
};

var tntFAQ = {
	rules : {
		question : {
			required : true,
			maxCNLen : 200
		},
		answer : {
			required : true,
			maxCNLen : 500
		}
	},
	messages : {
		question : {
			required : "请输入问题",
			maxCNLen : jQuery.format("问题不能大于{0}个字 符")
		},
		answer : {
			required : "请输入答案",
			maxCNLen : jQuery.format("答案不能大于{0}个字 符")
		}
	}
};

var recognizance = {
	rules : {
		amount : {
			required : true,
			digits : true,
			maxlength : 10
		},
		amountY : {
			required : true,
			isFloatNumber : true,
			maxlength : 10
		},
		reason : {
			required : true,
			maxCNLen : 300
		},
		billNo : {
			required : true,
			maxCNLen : 50
		},
		billTime : {
			required : true,
			dateISO : true
		},
		stringBillTime : {
			required : true,
			dateISO : true
		},
		bankName : {
			required : true,
			maxCNLen : 50
		},
		bankAccountName : {
			required : true,
			maxCNLen : 50
		},
		bankAccount : {
			required : true,
			digits : true,
			maxCNLen : 50
		}
	},
	messages : {
		amount : {
			required : "请输入金额",
			digits : "请输入正确的金额",
			maxlength : jQuery.format("金额不能大于{0}个字 符")
		},
		amountY : {
			required : "请输入金额",
			isFloatNumber : "请输入正确的金额",
			maxlength : jQuery.format("金额不能大于{0}个字 符")
		},
		reason : {
			required : "请输入设置额度值原因",
			maxCNLen : jQuery.format("原因不能大于{0}个字 符")
		},
		billNo : {
			maxCNLen : jQuery.format("账单号不能大于{0}个字 符")
		},
		bankName : {
			maxCNLen : jQuery.format("银行名称不能大于{0}个字 符")
		},
		bankAccountName : {		
			maxCNLen : jQuery.format("银行账户名不能大于{0}个字 符")
		},
		bankAccount : {
			digits : "请输入正确的银行账户",
			maxCNLen : jQuery.format("银行账号号不能大于{0}个字 符")
		}

	}
};

var addMoney = {
	rules : {
		amountY : {
			required : true,
			isFloatNumber : true,
			maxlength : 10
		},
		reason : {
			required : true,
			maxCNLen : 300
		},
		billNo : {
			required : true,
			maxCNLen : 50
		},
		billTime : {
			required : true,
			dateISO : true
		},
		bankName : {
			required : true,
			maxCNLen : 50
		},
		bankAccountName : {
			required : true,
			maxCNLen : 50
		},
		bankAccount : {
			required : true,
			maxCNLen : 50
		}
	},
	messages : {
		amountY : {
			required : "请输入金额",
			isFloatNumber : "请输入小数点后不超过2位的金额",
			maxlength : jQuery.format("金额不能大于{0}个字 符")
		},
		reason : {
			required : "请输入原因",
			maxCNLen : jQuery.format("原因不能大于{0}个字 符")
		},
		billNo : {
			maxCNLen : jQuery.format("账单号不能大于{0}个字 符")
		},
		bankName : {
			maxCNLen : jQuery.format("银行名称不能大于{0}个字 符")
		},
		bankAccountName: {
			maxCNLen : jQuery.format("银行账户名不能大于{0}个字 符")
		},
		bankAccount : {
			maxCNLen : jQuery.format("银行账号不能大于{0}个字 符")
		}
	}
};

var addFre = {
	rules : {
		freezeAmountY : {
			required : true,
			isFloatNumber : true,
			maxlength : 10
		},
		reason : {
			required : true,
			maxCNLen : 300
		}
	},
	messages : {
		freezeAmountY : {
			required : "请输入金额",
			isFloatNumber : "请输入小数点后不超过2位的金额",
			maxlength : jQuery.format("金额不能大于{0}个字 符")
		},
		reason : {
			required : "请输入原因",
			maxCNLen : jQuery.format("原因不能大于{0}个字 符")
		}
	}
};
var cashCommission = {
	rules : {
		orderCount : {
			required : true,
			digits : true
		},
		totalAmountY : {
			required : true,
			isFloatNumber : true
		},
		performBeginDate : {
			required : true,
			dateISO : true
		},
		performEndDate : {
			required : true,
			dateISO : true
		},
		commisRate : {
			required : true,
			digits : true,
			max : 100
		}
	},
	messages : {
		orderCount : {
			required : "请输入订单总数",
			digits : "请输入正确的订单数"
		},
		totalAmountY : {
			required : "请输入金额",
			isFloatNumber : "请输入小数点后不超过2位的金额"
		},
		commisRate : {
			required : "请输入返佣比率",
			digits : "请输入正确的返佣比率",
			max : jQuery.format("返佣比率不能大于{0}")
		}

	}
};

var prodPolicy = {
	rules : {
		discountY : {
			required : true,
			isFloatNumber : true,
			max : 100,
			min : 0
		},
		reason : {
			required : true,
			maxCNLen : 300
		}
	},
	messages : {
		discountY : {
			required : "请输入策略值",
			isFloatNumber : "请输入正确的策略值",
			max : jQuery.format("策略值不能大于{0}"),
			min : jQuery.format("策略值不能小于{0}")
		},
		reason : {
			required : "请输入设置策略值原因",
			maxCNLen : jQuery.format("原因不能大于{0}个字 符")
		}
	}
};

var tntProduct = {
	rules : {
		productId : {
			digits : true
		},
		branchId : {
			digits : true
		}
	},
	messages : {
		productId : {
			digits : "请输入正确的产品ID"
		},
		reason : {
			digits : "请输入正确的类别ID"
		}
	}
};

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

// 检验是否价格数字
jQuery.validator.addMethod("isFloatNumber", function(value, element) {
	var reg = /^\d+[\.]?\d{0,2}$/g;
	var v = "" + value;
	if (v.substr(-1) == ".") {
		return false;
	}
	return this.optional(element) || reg.test(value.replace(/\s/g, ""));
}, "请输入不超过2位小数的数字");
jQuery.validator.addMethod("maxCNLen", function(value, element, param) {
	value = value.replace(/[^\x00-\xff]/g, "**");
	var len = value.length;
	return this.optional(element) || len <= param;
}, $.validator.format("请确保输入的值长度最多为{0}个字节(一个中文字算2个字节)"));
