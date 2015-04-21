/** 
 * @param {Object} form  必须为jquery对象
 * @param {Object} showConfirm  是否弹出确认框
 * @memberOf {flag} 
 * @return 是否校验通过
 */
function SensitiveWordValidator(form, showConfirm) {
	this.form=form;
	this.showConfirm = showConfirm;
	return this;
}

SensitiveWordValidator.prototype.validate = function() {
	var $form = this.form;
	if($form.length < 1) { return true;}
	$form.find("label[name=sensitive_label]").remove();
	var array = new Array();
	$form.find(".sensitiveVad").each(function() {
		var name = $(this).attr("name");
		var value = $(this).val();
		if($.trim(value) != '') {
			array.push(name + ":" + $.trim(value));
		}
	});
	//做敏感词标识,如果该页校验出有敏感词并保存,则直接修改产品标识,否则需做产品所有信息校验
	var $hasSW = $form.find("input[name=hasSensitiveWord]");
	if($hasSW.length < 1) {
		$hasSW = $("<input type='hidden' name='hasSensitiveWord' value='N' />");
		$form.append($hasSW);
	} else {
		$hasSW.val("N");
	}
	
	//记录产品id,供后台使用
	if($form.find("input[name$='.productId']").length < 1 && $form.find("input[name='productId']").length < 1) {
		if(typeof(current_product_id) != "undefined") {
    		$form.append("<input type='hidden' name='productId' value="+current_product_id+" />");
		}
	}	
	
	if(array.length < 1) {
		return true;
	}
	var data = {}, flag = true, isShowConfirm = this.showConfirm;
	data['dataList'] = array;
	//校验敏感词
	$.ajax({
		url:"/super_back/prod/validateSensitiveWords.do",
		data: $.param(data, true),
		type:"POST",
		async : false,
		cache : false,
		success:function(data){
			var dt = eval("(" + data +")");
            if (dt.success) {
            	for ( var i = 0; i < dt.result.length; i++) {
					var res = dt.result[i];
					var $label = $("<label name='sensitive_label' style='color:red;'>有敏感词"+res.msg+"</label>");
					$form.find("input[name="+res.name+"]").after($label);
					$form.find("textarea[name="+res.name+"]").after($label);
				}
        		if(dt.result.length > 0) {
        			$hasSW.val("Y");
        		}
            	if(dt.result.length > 0 && isShowConfirm) {
            		flag = confirm("有敏感词！是否继续提交？");
            	}
            } else {
                alert(dt.msg);
            }
		}
	});
	return flag;
};

//页面加载后校验是否有敏感词
$(function() {
	$(document).ready(function() {
		var $form = $("form.mySensitiveForm"); 
		if($form.length > 0) {
			var sensitiveValidator=new SensitiveWordValidator($form, false);
			sensitiveValidator.validate();
		}
	});
});
