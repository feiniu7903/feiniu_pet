$(function(){
	$("#couponActivity").find("div.selectbox-drop").find("li").click(function(){
        var dataValue = $(this).attr("data-value");
		var payment = $(this).attr("payment");
		clearCoupon();
		if(dataValue == 'userCoupon'){
			$("#myCoupon").show();
			$("#couponInfo_youhui").html("");
            $("#myCash").hide();
		}else if(dataValue == 'userCash'){
			$("#myCash").show();
			$("#couponInfo_youhui").html("");
            $("#myCoupon").hide();
		}else{
			$("#myCoupon").find("input.input-radio").each(function(){
				$(this).attr("checked",false);
			});
			$("#myCoupon").hide();
            $("#myCash").hide();
			if(dataValue == "" ){
				$("#couponInfo_youhui").html("");
			}else{
				$("#youhui_couponId").val(dataValue);
				$("#couponPayment").val(payment);
				$("#couponChecked").val("true");
			}
		}	
		updatePrice_youhui();
	});
});
function userCash(){

    var cvi = $("#cashValue_input");
    var cash = $.trim($(cvi).val());
    var type = "^[0-9]*[1-9][0-9]*$";
    var re = new RegExp(type);
    if (cash.match(re) == null) {
        alert("请输入大于零的整数!");
        $(cvi).val("");
        $(cvi).focus();
        return;
    }

    if(parseInt(cash) > parseInt($("#availableCash").html())){
        alert("抵现金额不能大于" + $("#availableCash").html());
        $(cvi).val("");
        $(cvi).focus();
        return;
    }

    $("#cashValue").val(cash);

    updatePrice_youhui();
}
function userCouponCode(obj){
	clearCoupon();
	if(obj == 'couponCode_input' || obj == 'couponCode_radio'){
		var couponCode= $("#couponCode_input").val();
		if(couponCode == ""){
			$("#couponInfo_youhui").html("");
			$("#couponCode_input").focus();
			if(obj == 'couponCode_input'){
				$("#couponInfo_youhui").html("请输入优惠券代码");
			}
		}else{
			$("#couponCode").val(couponCode);
			$("#couponChecked").val("true");
		}
	}else{
		$("#youhui_couponId").val($(obj).val());
		$("#couponCode").val($(obj).attr("couponCode"));
		$("#couponChecked").val("true");
	}
	updatePrice_youhui();
}

function updatePrice_youhui(){
	var formData=$("#buyUpdateForm").serialize();
    $.ajax(
        {
            type: "POST",
            async: false,
            url: "/buy/ajaxPriceInfo.do",
            data: formData,
            dataType: "json",
            success: function (data) {
                if (data.priceInfo.success) {
                    var allCouponShow = false;
                    $("#heji").html(data.priceInfo.price);
                    if (data.priceInfo.info != null && data.priceInfo.info.orderYouhuiAmountYuan != '0') {
                        $("#youhuiquan").html(data.priceInfo.info.orderYouhuiAmountYuan);
                        $("#youhuiquanShow").show();
                        allCouponShow = true;
                    }else if (data.priceInfo.cashValue != '0') {
                        $("#cashValuePrice").html(data.priceInfo.cashValue);
                        $("#cashValueShow").show();
                        allCouponShow = true;
                    }else  {
                        $("#cashValueShow").hide();
                        $("#youhuiquanShow").hide();
                        if (data.priceInfo.info != null) {
                            $("#youhuiquan").html(data.priceInfo.info.orderYouhuiAmountYuan);
                        }
                    }
                    $("#dingdanjiesujiage").html(data.priceInfo.oughtPay);

                    $("#availableCash").html(data.priceInfo.availableCash);
                    $("#availableCashShow").html(data.priceInfo.availableCash);

                    $("#fanxian").html(data.priceInfo.bonus);
                    //展示早/多优惠系统信息
                    var productCouponType;
                    var amountYuan;
                    var validateBusinessCouponInfoList = data.priceInfo.validateBusinessCouponInfoList;
                    $("#earlyCouponShow").hide();
                    $("#moreCouponShow").hide();
                    if (validateBusinessCouponInfoList != null && validateBusinessCouponInfoList.length > 0) {
                        var earlyAmountYuan = 0;
                        var moreAmountYuan = 0;
                        for (var i = 0; i < validateBusinessCouponInfoList.length; i++) {
                            productCouponType = validateBusinessCouponInfoList[i].couponType;
                            amountYuan = validateBusinessCouponInfoList[i].amountYuan;
                            if (productCouponType == "EARLY") {
                                earlyAmountYuan += amountYuan;
                                $("#earlyCoupon").html(earlyAmountYuan);
                                $("#earlyCouponShow").show();
                                allCouponShow = true;
                            } else if (productCouponType == "MORE") {
                                moreAmountYuan += amountYuan;
                                $("#moreCoupon").html(moreAmountYuan);
                                $("#moreCouponShow").show();
                                allCouponShow = true;
                            }
                        }
                        $("#businessCouponDisplayInfo").show();
                    } else {
                        $("#businessCouponDisplayInfo").hide();
                    }
                    if (allCouponShow) {
                        $("#allCouponShow").show();
                    } else {
                        $("#allCouponShow").hide();
                    }
                    if (data.priceInfo.info != null && data.priceInfo.info.key == "OK") {
                        $("#couponInfo_youhui").html("您使用优惠抵扣了<em>￥" + (-data.priceInfo.info.orderYouhuiAmountYuan) + "</em>订单金额");
                        $("#youhui_couponId").val(data.priceInfo.info.couponId);
                    }else if (data.priceInfo.cashValue != '0'){
                        $("#couponInfo_youhui").html("您使用奖金抵扣了<em>￥" + (-data.priceInfo.cashValue) + "</em>订单金额");
                    } else {
                        clearCoupon();
                        if (data.priceInfo.info != null && data.priceInfo.info.value != "") {
                            $("#couponInfo_youhui").html(data.priceInfo.info.value);
                        }
                    }
                } else {
                    clearCoupon();
                    //alert(data.priceInfo.msg);
                }
            }
        }
    );
}
function clearCoupon(){
	$("#couponChecked").val("false");
	$("#cashValue").val("");
	$("#couponCode").val("");
	$("#youhui_couponId").val("");
	$("#couponPayment").val("");
}
function showCouponByPoint(subProductType, couponId, point,checkbox){
	if(checkbox.checked){
		pandora.dialog({						
		    title: "积分兑换优惠券",						
		    content:$("#pointChangeCouponDiv"),						
		    wrapClass: "dialog-middle",
		    okValue: "兑换",
		    cancel: true,
		    ok: function(){
		    	$.getJSON("http://login.lvmama.com/nsso/ajax/pointChangeCoupon.do?subProductType="+ subProductType+"&couponId="+couponId+ "&jsoncallback=?",function(json){
		    		if (json.success) {
		    			$("#couponCode_input").val(json.errorText);
		    			var currentPoiont = $("#currentUserPoint").html();
						$("#currentUserPoint").html(currentPoiont-point);
		    			$("#confirmSumbitCoupon").trigger("click");
					} else {
						alert(json.errorText);
					}
				});
		    }
		 });
	}
}