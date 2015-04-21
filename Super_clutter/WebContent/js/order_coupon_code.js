
/**
 * 跳转使用优惠券页面.
 */
function point2coupon() {
	if(isEmpty(visitTime)) {
		lvToast(false,"选择游玩日期",LT_LOADING_CLOSE);
		return false;
	}
	if(!productQuantityAvaliable()) {
		lvToast(false,"订购数量总数必须大于0",LT_LOADING_CLOSE);
		return false;
	}
	orderAndCouponPageChange(1);
}

/**
 * 输入优惠券。
 * @param obj
 */
/*function couponChange(obj){
	var oldCode = $("#tempUseCouponCode").val();
	var newCode = $("#coupon_se_id").val();
	if(oldCode == newCode) {
		if( $.trim(newCode) != "") {
			$("#use_coupon_code").hide();
			$("#cancel_coupon_code").show();
		}
	} else {
		$("#use_coupon_code").show();
		$("#cancel_coupon_code").hide();
	}
}
*/

/**
 * 优惠券类别1：直接填写，2：积分兑换，3：直接使用已有
 * @param type
 */
function initCouponCode(textvalue,code,type){

	if(type == 1) {
		$("#point2coupon_header").hide();
		$("#point2coupon_footer").html(textvalue);
		$("#point2coupon_footer").show();
		$("#coupon_se_id").val(code);
	} else if(type == 2){
		
	} else if(type == 3) {
		$("#point2coupon_header").hide();
		$("#point2coupon_footer").html(textvalue);
		$("#point2coupon_footer").show();
	}
	orderAndCouponPageChange(2); // 页面切换
	
}

/**
 * 优惠券类别1：直接填写，2：积分兑换，3：直接使用已有
 * @param type
 */
function cancelUsedCouponCode(code,type){
	cancelCode(code,type); // 取消使用 
	orderAndCouponPageChange(2); // 页面切换
}

/**
 * 取消使用 
 * @param type
 */
function cancelCode(code,type) {
	if(type == 1) {
		$("#point2coupon_header").show();
		$("#point2coupon_footer").hide();
		
		$("#use_coupon_code").show();
		$("#cancel_coupon_code").hide();
		$("#coupon_se_id").val('');
	} else if(type == 2){
		
	} else if(type == 3) {
		$("#point2coupon_header").show();
		$("#point2coupon_footer").hide();
		
		$("#use"+code).show();
		$("#cancel"+code).hide();
	}
	$("#couponCode").val(''); // form表单的值
	$("#couponRadio_id").val('');  // 按钮上面的值
	$("#couponCode").attr("checked","checked");
}

/**
 * 订单 和 优惠券页面切换 
 * @param type 1：显示优惠券页面 ；其它:显示订单页面 
 */
function orderAndCouponPageChange(type) {
	if(type == "1") { // 显示优惠券页面 
		$("#order_fill").hide();
		$("#order_fill_header").hide();
		$("#coupon_fill").show();
	} else {
		/*$("#couponRadio_id").val();
		$("#couponRadio_id").attr("checked","");*/
		
		/*$("#ic_radio_right_id").removeClass();
		$("#ic_radio_right_id").addClass("ic_radio_right");*/
		$("#order_fill").show();
		$("#order_fill_header").show();
		$("#coupon_fill").hide();
	}
}

/**
 * 直接输入的优惠券  
 * @param id
 */
function useCouponCodeById(id) {
	var code = $("#"+id).val();
	if(isEmpty(code)) {
		lvToast(false,"请输入优惠券代码",LT_LOADING_CLOSE);
		return false;
	}
	// 校验优惠券 
	validateAndUserCouponCode(code,1);
}

function cancelCouponCodeById(id) {
	var code = $("#"+id).val();
	$("#couponCode").val('');
	
	if(isEmpty(code)) {
		lvToast(false,"您还没有使用优惠券",LT_LOADING_CLOSE);
		return false;
	}
	cancelUsedCouponCode(code,1);
}

/**
 * 使用优惠券列表的优惠券   
 * @param id
 */
function useCouponCode(code) {
	validateAndUserCouponCode(code,3);
}

/**
 * 取消使用优惠券 
 * @param code
 * @returns {Boolean}
 */
function cancelCouponCode(code){
	if(isEmpty(code)) {
		lvToast(false,"您还没有使用优惠券",LT_LOADING_CLOSE);
		return false;
	}
	cancelUsedCouponCode(code,3);
}

//只判断优惠券是否可用. couponCode 优惠券代码 ,优惠券类别1：直接填写，2：积分兑换，3：直接使用已有
function validateAndUserCouponCode(couponCode,type) {
	var point2couponli = $("#point2coupon");
	
	point2couponli.find("input[name=couponCode]").val(couponCode);
	point2couponli.find("input[name=couponCode]").attr("checked","checked");
	var success = function(data){
		initCouponRadios();
		point2couponli.find("span[name=couponRadios]").eq(0).removeClass();
		point2couponli.find("span[name=couponRadios]").eq(0).addClass("ic_radio_right");
		if(data.code==1 && null != data.validateInfo){
			if(null != lastCheckedRadio) {
				lastCheckedRadio = point2couponli.find("input[name=couponCode]");
			}
			var code = data.validateInfo.returnCouponCode; // 优惠券代码 
			var value = "<span style='color:#D11F7F;'>&yen;-" +data.validateInfo.youhuiAmountYuan+"</span>"; // 中文说明
			$("#tempCouponCode").attr("data-value",code);
			initCouponCode(value,code,type);
		} else {
			$("#couponRadio_id").val('');
			$("#couponRadio_id").attr("checked","");
			$("#ic_radio_right_id").removeClass();
			$("#ic_radio_right_id").addClass("ic_radio2_right");
			lvToast(false,"请选择正确订购数量",LT_LOADING_CLOSE);
		}
		
		
	};
	
	var fail = function(){
		point2couponli.find("input[name=couponCode]").val('');
		point2couponli.find("input[name=couponCode]").attr("checked","");
		// s
		$("#emptyRadio").attr("checked","checked");
	};
	validateCoupon(true,success,fail);

}



/**
 * 积分兑换优惠券    
 * @param id
 */
function usePoint2CouponCode(url) {
	// 如果已经兑换过了。 
	if('' == url) {
		var couponCode = $("#changedCouponPriceId").html();
		if(!isEmpty(couponCode)) {
			useCouponCode(couponCode);
			return false;
		}
	}
	
	$.ajax({
		url : url,
		type : "POST",
		success : function(datas) {
			if(null == datas || datas.code==-1){
				lvToast(false,null==datas?LT_ORDER_SUBMIT_ERROR:datas.message,5000);
				return;
			}
			if(datas.code == 1 && null != datas.data && null != datas.data.code) {
				// this.sendAjaxResult("{\"message\": \"兑换成功\",  \"data\": {\"code\":\""+obj.get("errorText")+"\",\"point\":"+user.getPoint()+"},  \"code\": \"1\" }");
				$("#beforChange").hide();
				$("#afterChange").show();
				$("#changedCouponPriceId").html(datas.data.code);
				useCouponCode(datas.data.code);
			} else {
				orderAndCouponPageChange(2);
			}
		},
		error:function() {
			lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}


