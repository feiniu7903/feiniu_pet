/**
 * 扩展JQuery Valiate 添加自定义的错误验证
 * 
 * @说明 如果没有指定错误消息显示的位置，则默认显示验证元素后面，指定消息显示位置的方法是 <div id="eleIdError"></div>,eleIdError为验证元素的id或者name或者errorEle的值+Error
 * @author mayonghua
 * @date 2013-10-28
 */
function putErrorSpan(id, msg) {
	$("#" + id).siblings(".tureTipsBox").hide();
	var html = "<span class='tip-icon tip-icon-error'></span>" + msg;
	$("#" + id).siblings(".errorTipsBox").html(html);
}
function putOkSpan(id) {
	$("#" + id).siblings(".errorTipsBox").html("");
	$("#" + id).siblings(".tureTipsBox").show();
}
$.validator.setDefaults({
	success : function(element) {
		if (element.parent().parent().find("div.e_error").size() > 0) {
			element.parent().parent().find("div.e_error").eq(0).show();
		}
		element.parent().remove();
	},
	errorPlacement : function(error, element) {
		// 构建错误消息模板
		var errorHtml = '<div class="e_error">'
				+ '<i class="e_icon icon-error"></i>' + '</div>';
		var errorEle = $(errorHtml);
		// 添加错误消息
		errorEle.append(error);
		// 首先判断是否指定了错误消息显示的位置
		var elementNameOrId = element.attr("errorEle") || element.attr("name")
				|| element.attr("id");
		var specErrorEle = $("#" + elementNameOrId + "Error");
		if (specErrorEle.size() > 0) {
			// 将错误消息绘制到指定元素里面
			specErrorEle.find("div.e_error").hide();
			specErrorEle.append(errorEle);
		} else {
			// 将错误消息绘制到元素后面
			element.after(errorEle);
		}
	}
});

// 检验手机号码
jQuery.validator.addMethod("isphone", function(value, element) {
	var tel = /^1[3|4|5|8][0-9]\d{8}$/;
	return this.optional(element) || (tel.test(value.replace(/\s/g, "")));
}, "请输入11位数字的手机号码");

jQuery.validator
		.addMethod(
				"clientRemote",
				function(value, element, param) {
					if (this.optional(element)) {
						return "dependency-mismatch";
					}
					var previous = this.previousValue(element);
					if (!this.settings.messages[element.name]) {
						this.settings.messages[element.name] = {};
					}
					previous.originalMessage = this.settings.messages[element.name].remote;
					this.settings.messages[element.name].remote = previous.message;
					param = typeof param === "string" && {
						url : param
					} || param;
					if (previous.old === value) {
						return previous.valid;
					}
					previous.old = value;
					var validator = this;
					this.startRequest(element);
					var data = {};
					data[element.name] = value;
					$
							.ajax($
									.extend(
											true,
											{
												url : param,
												mode : "abort",
												port : "validate"
														+ element.name,
												dataType : "json",
												data : data,
												success : function(response) {
													validator.settings.messages[element.name].remote = previous.originalMessage;
													var valid = response.success;
													if (valid) {
														var submitted = validator.formSubmitted;
														validator
																.prepareElement(element);
														validator.formSubmitted = submitted;
														validator.successList
																.push(element);
														delete validator.invalid[element.name];
														validator.showErrors();
													} else {
														var errors = {};
														var message = response.errorText
																|| validator
																		.defaultMessage(
																				element,
																				"remote");
														errors[element.name] = previous.message = $
																.isFunction(message) ? message(value)
																: message;
														validator.invalid[element.name] = true;
														validator
																.showErrors(errors);
													}
													previous.valid = valid;
													validator.stopRequest(
															element, valid);
												}
											}, param));
					return "pending";
				}, "请修正该字段");
