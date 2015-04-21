/**
*扩展JQuery Valiate 添加自定义的错误验证
*@说明 如果没有指定错误消息显示的位置，则默认显示验证元素后面，指定消息显示位置的方法是
*<div id="eleIdError"></div>,eleIdError为验证元素的id或者name或者errorEle的值+Error
*@author mayonghua
*@date 2013-10-28
*/

$.validator.setDefaults({
	success:function(element){
		if(element.parent().parent().find("div.e_error").size()>0){
			element.parent().parent().find("div.e_error").eq(0).show();
		};
		element.parent().remove();
	},
	errorPlacement:function(error,element){
		//构建错误消息模板
		var errorHtml = '<div class="e_error">'
						+'<i class="e_icon icon-error"></i>'
						+'</div>';
		var errorEle = $(errorHtml);
		//添加错误消息
		errorEle.append(error);
		//首先判断是否指定了错误消息显示的位置
		var elementNameOrId = element.attr("errorEle")||element.attr("name")||element.attr("id");
		var specErrorEle= $("#"+elementNameOrId+"Error");
		if(specErrorEle.size()>0){
			//将错误消息绘制到指定元素里面
			specErrorEle.find("div.e_error").hide();
			specErrorEle.append(errorEle);
		}else {
			//将错误消息绘制到元素后面
			element.after(errorEle);
		}
	}
});
