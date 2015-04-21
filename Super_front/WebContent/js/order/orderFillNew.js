/*alert——addpop样式*/
var lv_popjs = document.createElement("script");
lv_popjs.setAttribute("type", "text/javascript");
lv_popjs.setAttribute("src", "http://pic.lvmama.com/js/pop.js");
document.getElementsByTagName("body")[0].appendChild(lv_popjs);
/*alert——addpop样式*/
var flagSeckill = true;

function addPerson(paramId) {
    var buyPeopleNum = 0;
    var paramObjs = $("input[name='" + paramId + "']");
    for (var i = 0; i < paramObjs.length; i++) {
        buyPeopleNum += parseInt($(paramObjs[i]).attr("buyPeopleNum"));
    }
    if (buyPeopleNum > 0) {
        var contactHtml = $("#receiversList").html();
        $("#tra_info").html("");
        for (var i = 1; i < buyPeopleNum; i++) {
            $("#tra_info").append(createContactContent(i, contactHtml));
        }
    }
}

function checkOrder(obj, minAmt) {
    var min = parseInt(minAmt);
    var heji = $("#heji").html();
    if (parseInt(heji) < min) {
        lv_alert("合计金额不满足优惠条件");
        $(obj).attr("checked", false);
        $(obj).parent().find("couponCodeId").val("");
        return false;
    }
    checkOrderCoupon($(obj));
    return true;
}

function addCouponTotal() {
    var amount = 0;
    $("#textNum" + mainProductId).attr("couponPrice", 0);
    $("input[id='amount']").each(function() {
        var obj = $(this);
        var hasAdd = $(this).attr("hasAdd");
        if (hasAdd == "false") {
            if (obj.val() != "") {
                amount += parseInt(obj.val());
                $(this).attr("hasAdd", true);
            }
        }

    });
    var couponPrice = $("#textNum" + mainProductId).attr("couponPrice");
    var couponPrice = parseInt(couponPrice) + ( - amount);
    $("#textNum" + mainProductId).attr("couponPrice", couponPrice);
    //alert($("#textNum"+mainProductId).attr("couponPrice"));
    lv_alert($("#textNum" + mainProductId).attr("couponPrice"));
    //$("#youhuiquan").html((-amount));
    updatePrice();
}

function confirmUse(obj) {
    var ckbox = $(obj);
    if (ckbox.attr("name") == 'prod_coupon_' + ckbox.attr("productId")) {
        $("input[name='prod_coupon_" + ckbox.attr("productId") + "']").each(function() {
            if (ckbox != $(this) && ckbox.parent().find("#youhuiContent").css("display") == "none") {
                $(this).parent().find("#youhuiContent").hide();
                var info = $(this).parent().find("#couponInfo");
                $(this).parent().find("#couponCodeId").val("");
                $(this).parent().find("#amount").val("");
                $(this).parent().find("#code").val("");
                //$(this).parent().find("#couponId").val("");
                $(this).parent().find("#amount").attr("hasAdd", "false");
                var productNumInput = $("input[name='buyInfo.buyNum.product_" + $(this).attr("productId") + "']");
                productNumInput.attr("couponPrice", 0);
                info.html("");

            }

        });
    } else {
        $("input[name='ord_coupon']").each(function() {
            if (ckbox != $(this) && ckbox.parent().find("#youhuiContent").css("display") == "none") {
                var info = $(this).parent().find("#couponInfo");
                $(this).parent().find("#youhuiContent").hide();
                $(this).parent().find("#couponCodeId").val("");
                $(this).parent().find("#amount").val("");
                //$(this).parent().find("#couponId").val("");
                $(this).parent().find("#code").val("");
                info.html("");
                $("#orderCouponPrice").val(0);
            }

        });

    }

    if (ckbox.attr("checked") == true) {
        ckbox.parent().find("#youhuiContent").fadeIn();
    } else {

        var productId = ckbox.parent().find("#productId").val();
        var couponId = ckbox.parent().find("#couponId").val();
        var withCode = ckbox.parent().find("#withCode").val();
        var amountObj = ckbox.parent().find("#amount");
        var couponCodeIdObj = ckbox.parent().find("#couponCodeId");
        ckbox.parent().find("#code").val("");
        couponCodeIdObj.val("");
        ckbox.parent().find("#couponInfo").html("");
        ckbox.parent().find("#youhuiContent").fadeOut();
        ///amountObj.val("");
        delCouponTotal(amountObj);
    }
    updatePrice();
}

function delCouponTotal(obj) {
    if (obj.attr("hasAdd") == "true") {
        var amount = obj.val();
        var couponPrice = $("#textNum" + mainProductId).attr("couponPrice");
        var couponPrice = parseInt(couponPrice) - ( - amount);
        $("#textNum" + mainProductId).attr("couponPrice", couponPrice);
        //alert($("#textNum"+mainProductId).attr("couponPrice"));
        updatePrice();
        obj.removeAttr("hasAdd");
    }
}

function checkOrderCoupon(obj) {
    var content = obj.parent();
    var info = content.find("#couponInfo");
    var productId = content.find("#productId").val();
    var couponId = content.find("#couponId").val();
    var withCode = content.find("#withCode").val();
    var couponTarget = content.find("#couponTarget").val();
    var couponCodeIdObj = content.find("#couponCodeId");
    $.ajax({
        type: "POST",
        async: false,
        url: "/check/validateCouponCodeOrOrder.do",
        data: {
            couponId: couponId,
            couponTarget: couponTarget,
            withCode: withCode
        },
        dataType: "json",
        success: function(data) {
            if (data.info.key == "OK") {
                couponCodeIdObj.val(data.info.coupon.couponCodeId);
                //var couponPrice = $("#textNum"+productId).attr("couponPrice");
            }
        }
    });
}
/**
 * 
 * @param {Object} i
 * @param {Object} obj
 * @deprecated 已弃用,参见route_person.ftl中的setTravelerInfos(index,obj,flag).
 */
function addReceInfo(i, obj) {
    $("#recrName" + i).val($(obj).find("option:selected").attr("name"));
    $("#receiverId" + i).val($(obj).find("option:selected").val());
    $("#cardType" + i).val($(obj).find("option:selected").attr("cardType"));
    $("#cardNum" + i).val($(obj).find("option:selected").attr("cardNum"));
    $("#mobileNumber" + i).val($(obj).find("option:selected").attr("mobile"));
}

function copyContactInfo(i) {
    $("#recrName" + i).val($("#buyRouteName").attr("value"));
    $("#mobileNumber" + i).val($("#buyRouteMobile").attr("value"));
}

function createContactContent(i, op) {
    var selectHtml = "";
    var useOffen = "";
    if (op != null && op.length > 5) {
        selectHtml = "<select onchange=\"addReceInfo(" + i + ",this)\">" + op + "</select>";
        useOffen = "<input type=\"checkbox\" checked=\"true\" align=\"absmiddle\" name=\"travellerList[" + i + "].useOffen\" />保存到常用姓名";
    }
    return "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\"><tbody><tr><th>其他游玩人" + (i) + "</th><th></th><th align=\"right\"><span class=\"savename\">" + useOffen + "</span></th>" + "</tr> <tr><td width=\"70\"><span>*</span>姓　　名：</td><td width=\"190\"><input type=\"text\" rName=\"rName\" id=\"recrName" + i + "\" name=\"travellerList[" + i + "].receiverName\" class=\"text_inp\" /></td>" + "<td>" + selectHtml + "</td></tr>" + "<tr><td><span>*</span>联系电话：</td><td><input type=\"text\" rMobileNumber=\"rMobileNumber\" id=\"mobileNumber" + i + "\" name=\"travellerList[" + i + "].mobileNumber\" class=\"text_inp\" />" + "<tr><td><span>*</span>证件类型：</td><td><select id=\"cardType" + i + "\" name=\"travellerList[" + i + "].cardType\"><option value=\"\">请选择证件类型</option><option value=\"ID_CARD\" selected=\"selected\">身份证</option><option value=\"HUZHAO\">护照</option><option value=\"CUSTOMER_SERVICE_ADVICE\">客服联系我时提供</option></select></td><td></td></tr>" + "<div id=\"cardNumDivId" + i + "\" style=\"visibility:none\"><tr><td><span>*</span>证件号码：</td><td><input type=\"text\" onKeyDown=\"return checkType('cardType" + i + "',this)\" id=\"cardNum" + i + "\" rTraveller=\"rTraveller\" name=\"travellerList[" + i + "].cardNum\" class=\"text_inp\" /></td><td></td></tr></div>" + "<input type=\"hidden\" id=\"receiverId" + i + "\" name=\"travellerList[" + i + "].receiverId\" class=\"text_inp\" /></td><td></td></tr></tbody></table>";
}

/**
 * 检查游玩人证件类型.
 * @param {Object} id
 * @param {Object} obj
 * @return {TypeName} 
 */
function checkType(id, obj) {
    var s = $("#" + id);
    if (s.val() == "") {
        lv_alert("请选择证件类型");

        s.focus();
        $("#" + obj).val("");
        return false;
    }
    var card = s.val();
    
    if (card == "ID_CARD") {
        obj = $("#" + obj);
        var is_id_card_no = isIdCardNo(obj);
        if (!is_id_card_no) {
            obj.val("");
            return false;
        }
    }
    /*
	当选中游玩人的证件类型为"客服联系我时提供"时,隐藏证件号码文本框,并设置文本框的值为"客服联系我时提供";
	当选中游玩人的证件类型为非"客服联系我时提供"时,显示证件号码文本框,并设置文本框的值为空字符串.
	*/
    var cardNumDivId = "cardNumDivId" + id.replace(/cardType/g, "");
    var cardNum = "cardNum" + id.replace(/cardType/g, "");
    var childrenBrithdayInfoSpan = "childrenBrithdayInfoSpan" + id.replace(/cardType/g, "");
    
    if (card == "CUSTOMER_SERVICE_ADVICE") {
        $("#" + cardNumDivId).hide();
        $("#" + cardNum).val("客服联系我时提供");
    } else {
        $("#" + cardNumDivId).show();
        if ($("#" + cardNum).val() == "客服联系我时提供") {
            $("#" + cardNum).val("");
        }
    }
   
    //当选中的cardType为"请选择证件类型"、"身份证"、"客户联系我时提供"其中之一时,仅显示证件号码输入项,"儿童出生年月"、"成人出生年月"如果存在则删除.
    if (card == "" || card == "ID_CARD" || card == "CUSTOMER_SERVICE_ADVICE") {
        $("#" + childrenBrithdayInfoSpan).hide();
    } else {
        $("#" + childrenBrithdayInfoSpan).show();
    }
    var genderSpan = "genderSpan" + id.replace(/cardType/g, "");
   
    //当不是选择身份证时，需要选择性别
    if (card != "ID_CARD") {
        $("#" + genderSpan).show();
    } else {
        $("#" + genderSpan).hide();
    }
    return true;
}

/**
 * @param {Object} obj
 * @deprecated 已弃用,参见ticket_person.ftl,hotel_person.ftl中的updateFetchTicketInfo2(obj).
 */
function updateFetchTicketInfo(obj) {
    $("#fetchTicketUserName").val($(obj).find("option:selected").attr("receiverName"));
    $("#fetchTicketUserMobile").val($(obj).find("option:selected").attr("mobile"));
    $("#fetchTicketUserReceiverId").val($(obj).val());
}

function updateBuyTicketInfo(obj) {
    $("#buyTicketName").val($(obj).find("option:selected").attr("name"));
    $("#buyTicketPersonId").val($(obj).find("option:selected").val());
    $("#buyTicketMobile").val($(obj).find("option:selected").attr("mobile"));
    $("#buyTicketEmail").val($(obj).find("option:selected").attr("email"));
}

/**
 * 自由行-订单联系人信息.
 * @param {Object} obj
 */
function updateBuyRouteInfo(obj) {
    $("#buyRouteName").val($(obj).find("option:selected").attr("name"));
    $("#buyTicketPersonId").val($(obj).find("option:selected").val());
    $("#buyRouteMobile").val($(obj).find("option:selected").attr("mobile"));
    $("#buyRouteEmail").val($(obj).find("option:selected").attr("email"));
}

function getJsonData() {
    var ordNum = $("[ordNum]");
    var param = "";
    for (var i = 0; i < ordNum.length; i++) {
        var id = ordNum[i].getAttribute("id");
        var obj = $("#" + id);
        param += "'ordNum." + (ordNum[i].getAttribute("id")) + "':'" + obj.attr("value") + "',";
    }
    var param = "{" + param + "productId:'" + $("#_mainProductId").val() + "',choseDate:'" + $("#allVisitDate").val() + "'";
    var endDate = $("#_leaveTime").val();
    if (endDate != "null" && endDate != "") {
        param += ",endDate:'" + endDate + "'";
    }
    param += ",'submitOrder':true";
    param += "}";
    return eval("(" + param + ")")
}

/**
 * 下单页面(选择产品数量页面，填写游玩人信息页面)提交前的js数据校验
 * @returns {Boolean} 数据是否完善
 */
function subOrders() {
	//验证显示模块是否保存
    var forms = $("input[valid=true]"),
	len = forms.length,
    i = 0,
    j = 0;
    var result = true;
	//if ($("input[name=address]:checked").length === 0) {
	    //$("#js-address").show();
	    //result = false;
	//}
	for (; i < len; i++) {
		if(formSubmitValid[forms.eq(i).val()].formID === "#frast-submit"){
			if($(formSubmitValid[forms.eq(i).val()].formID).is(":hidden")){
				continue;
			}
		}
	    if (!formSubmitValid[forms.eq(i).val()].isValid) {
	    	j = i;
	        result = false;
	        break;
	    }
	}
	if (!result) {
		$(window).scrollTop($(formSubmitValid[forms.eq(j).val()].formID).parents("div[class^=order-content]").offset().top);
		$(formSubmitValid[forms.eq(j).val()].formID).nextAll("span").remove();
		$(formSubmitValid[forms.eq(j).val()].formID).after("<span style=\"color:red;\" name=\"error\"> 请保存信息</span>")
		return false;
	}
	
    var num = 0;
    var limitFlag = false;
    var selfPack = $("form[name=orderForm] input[name='buyInfo.selfPack']").val() == 'true';
    if (!selfPack && $("#allVisitDate").length > 0) {
        var jsonDate = getJsonData();
        $.ajax({
            type: "POST",
            async: false,
            url: "/product/price.do",
            data: jsonDate,
            dataType: "json",
            success: function(data) {
                var json = data.jsonMap;
                if (json.flag == 'N') {
                    lv_alert(json.error);
                    limitFlag = true;
                    return;
                }
            }
        });
        if (limitFlag) {
            return false;
        }
    }
    $("input[sellName='sellName']").each(function() {
        num += parseInt($(this).val());
    });
    if (num == 0 && $("input[sellName='sellName']").not("input[id^='addition']").length > 0) {
        $("input[sellName='sellName']").eq(0).focus();
        lv_alert("产品订购数量不能都为0");
        return false;
    }

    if(!limitFlag){
    	//检查订单产品的库存
        $.ajax({
            type: "POST",
            async: false,
            url: "/buy/ajaxCheckSock.do",
            data: $("#buyUpdateForm").serialize(),
            dataType: "json",
            success: function(data) {
                if (!data.success) {
                	if (data.msg != '未选购产品') {
                		lv_alert(data.msg);
                	}
                    limitFlag = true;
                    return false;
                }
            }
        });
        if (limitFlag) {
            return false;
        }
    }
    
    var blackFlag = false;
	$.ajax({
		type: "POST",
		async: false,
		url: "/product/validateBlackByProds.do",
		data: $("#buyUpdateForm").serialize(),
		dataType: "json",
		success: function(data) {
			if (!data.flag) {
				lv_alert(data.msg);
			}else{
				blackFlag = true;
			}
		}
	});
    if(blackFlag){
    	//秒杀
    	var seckillBranchId = $("input[name='buyInfo.seckillId']").val();
    	var isSubmitFlag = false;
    	if(seckillBranchId != null && seckillBranchId != ""){
    		if(!limitFlag){
    			if(flagSeckill){
    				flagSeckill = false;
    				var formData = $("#buyUpdateForm").serialize(); 
    				var fromDataTwo = formData;
    				if(formData.indexOf("contact") != -1){
    					fromDataTwo = formData.substring(0,formData.indexOf("contact")-1);
    				}
    				$.ajax({
    					type: "POST",
    					async: false,
    					url: "/seckill/order.do",
    					data: fromDataTwo,
    					dataType: "json",
    					success: function(data) {
    						if (!data.flag) {
    							lv_alert(data.msg);
    							limitFlag = true;
    							flagSeckill = true;
    						}else{
    							$("input[name='buyInfo.seckillToken']").val(data.token);
    							isSubmitFlag = true;
    						}
    					}
    				});
    			}
    			return isSubmitFlag;
    		}
    	}else{
    		return true;
    	} 
    }else{
    	return false;
    }
}

function checkReceiver() {
    var fetchTicketUserName = $.trim($("#fetchTicketUserName").val());
    if (fetchTicketUserName == "") {
        lv_alert("取票人姓名不能为空!");
        return false;
    }
    var fetchTicketUserMobile = $.trim($("#fetchTicketUserMobile").val());
    if (!checkMobile(fetchTicketUserMobile) || fetchTicketUserMobile.length != 11 || isNaN(fetchTicketUserMobile)) {
        lv_alert("取票人手机不能为空或格式不正确!");
        return false;
    }
    var buyTicketName = $.trim($("#buyTicketName").val());
    if (buyTicketName == "") {
        lv_alert("取票人姓名不能为空!");
        return false;
    }
    var buyTicketMobile = $.trim($("#buyTicketMobile").val());
    if (buyTicketMobile == '' || !checkMobile(buyTicketMobile)) {
        lv_alert("取票人手机不能为空或格式不正确!");
        return false;
    }
}

function routeCheckReceiver() {
    var buyTicketName = $.trim($("#buyTicketName").val());
    if (buyTicketName == "") {
        lv_alert("订票人姓名不能为空!");
        return false;
    }
    var buyTicketMobile = $.trim($("#buyTicketMobile").val());
    if (!checkMobile(buyTicketMobile)) {
        lv_alert("订票人手机不能为空或格式不正确!");
        return false;
    } else if (buyTicketMobile.length != 11) {
        lv_alert("手机号码必须是11位!");
        return false;
    } else if (isNaN(buyTicketMobile)) {
        lv_alert("手机号码必须是11位数字");
        return false;
    }
}

function purseSubmit() {
    var num = 0;
    $("input[sellName='sellName']").each(function() {
        num += parseInt($(this).val());
    });
    if (num == 0) {
        lv_alert("产品订购数量不能都为0");
        $("input[sellName='sellName']").eq(0).focus();
        return false;
    }
    return true;
}

function checkDate(date) {
    var year = date.substring(0, 4);
    var month = date.substring(4, 6);
    var day = date.substring(6, 8);
    var time = new Date(year, month - 1, day);
    var e_year = time.getFullYear();
    var e_month = time.getMonth() + 1;
    var e_day = time.getDate();
    if (year != e_year || month != e_month || day != e_day) {
        return false;
    }
    return true;
}
//--身份证号码验证-支持新的带x身份证
function isIdCardNo(obj) {
	    var cardNum = obj.val();
	if (cardNum.length == 0) {
		return true;
	}
	// 11-15，21-23，31-37 ，41-46 ，50-54 ，61-65,81-82
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	var iSum = 0;
	cardNum = cardNum.replace("\uff38", "X");
	cardNum = cardNum.replace("x", "X");
	if (!/^\d{17}(\d|x)$/i.test(cardNum)) {
		alert("输入身份证号码长度不对！");
		return false;
	}
	cardNum = cardNum.replace(/x$/i, "a");
	if (area[parseInt(cardNum.substr(0, 2))] == null) {
		alert("错误的身份证号码！");
		return false;
	}
	sBirthday = cardNum.substr(6, 4) + "-" + Number(cardNum.substr(10, 2))
			+ "-" + Number(cardNum.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
			.getDate())) {
		alert("错误的身份证号码！");
		return false;
	}
	for ( var i = 17; i >= 0; i--) {
		iSum += (Math.pow(2, i) % 11) * parseInt(cardNum.charAt(17 - i), 11);
	}
	if (iSum % 11 != 1) {
		alert("错误的身份证号码！");
		return false;
	}
	return true; 
};

function error_tip(elt, msg) {
    var _this = $(elt);
    strHtml = '<span class="zhfs_state zhfs_v_error"><i></i><label class="label_tip">' + msg + '</label></span>';
    $(_this).siblings(".zhfs_v_error").remove();
    $(_this).after(strHtml);
    _this.addClass("input_border_red");
    var tobj = _this[0];
    if (!_this.data("bindFocus")) {
        _this.data("bindFocus", true);
        _this.mousedown(function() {
            $(_this).siblings(".zhfs_v_error").hide();
            $(_this).removeClass("input_border_red");
        }).keyup(function() {
            $(_this).siblings(".zhfs_v_error").hide();
            $(_this).removeClass("input_border_red");
        });
    }
}
$(function() {
    var lv_popjs = document.createElement("link");
    lv_popjs.id = "xxx";
    lv_popjs.setAttribute("rel", "stylesheet");
    lv_popjs.setAttribute("href", "http://pic.lvmama.com/js/ui/lvmamaUI/css/jquery.common.css");
    document.getElementsByTagName("body")[0].appendChild(lv_popjs);
    $("dl.user-info").each(function() {
        var findThis = $(this);
        findThis.find(":checkbox").click(function() {
            findThis.find(":text").mousedown();
        });
    });
});

function checkVisitorIsExisted() {
    var $form = $("#buyUpdateForm");
    var productType = $form.find("input[name='buyInfo.productType']").val();
    var subProductType = $form.find("input[name='buyInfo.subProductType']").val();
    var selfPack = $form.find("input[name='buyInfo.selfPack']").val();
    var flag = false;
    if (selfPack == "true") {
        flag = true;
    } else {
        if (productType == 'TICKET' || productType == 'HOTEL' || subProductType == 'FREENESS') {
            var travellerInfoOptions = $("#travellerInfoOptions").val();
            if ((travellerInfoOptions != null && travellerInfoOptions != '') || subProductType == 'FREENESS') { //酒店，并且有游客必填信息的话，需要校验是否已填写游客信息或目的地自由行
                var $obj = $form.find("input[name='travellerList[1].receiverName']");
                if ($obj.size() < 1) {
                    return true;
                }
            } else {
                var $obj = $form.find("input[name='contact.receiverName']");
                if ($obj.size() < 1) {
                    return true;
                }
            }
            $.ajax({
                type: "POST",
                async: false,
                url: "/buy/ajaxCheckVisitorIsExisted.do",
                data: $("#buyUpdateForm").serialize(),
                dataType: "json",
                success: function(data) {
                    if (data.success) {
                        flag = true;
                    } else {
                        flag = false;
                        lv_alert(data.msg);
                    }
                }
            });
        } else {
            flag = true;
        }
    }
    return flag;
}

function pop_other_evt() {
    var $form = $("#buyUpdateForm");
    var subProductType = $form.find("input[name='buyInfo.subProductType']").val();
    var travellerInfoOptions = $("#travellerInfoOptions").val();
    if ((travellerInfoOptions != null && travellerInfoOptions != '') || subProductType == 'FREENESS') { //酒店，并且有游客必填信息的话，需要校验是否已填写游客信息或目的地自由行
        $("span.travellerMsg").html("<font color='red'>请输入不同的游玩人姓名</font>");
        $form.find("input[name='travellerList[1].receiverName']").focus();
    } else {
        $("span.contactMsg").html("<font color='red'>请输入不同的入住人姓名</font>");
        $form.find("input[name='contact.receiverName']").focus();
    }
}

/**
  * 计算两个日期的时间差. 
  * date0 = "2008/08/08";
  * date0 = "2012/07/27";
  * date0.dateDiff("y",date2)的值为4.
  */
Date.prototype.dateDiff = function(interval, objDate) {
    //若參數不足或 objDate 不是日期物件則回傳 undefined
    if (arguments.length < 2 || objDate.constructor != Date) return undefined;
    switch (interval) {
        //計算秒差
    case "s":
        return parseInt((objDate - this) / 1000);
        //計算分差
    case "n":
        return parseInt((objDate - this) / 60000);
        //計算時差
    case "h":
        return parseInt((objDate - this) / 3600000);
        //計算日差
    case "d":
        return parseInt((objDate - this) / 86400000);
        //計算週差
    case "w":
        return parseInt((objDate - this) / (86400000 * 7));
        //計算月差
    case "m":
        return (objDate.getMonth() + 1) + ((objDate.getFullYear() - this.getFullYear()) * 12) - (this.getMonth() + 1);
        //計算年差
    case "y":
        return objDate.getFullYear() - this.getFullYear();
        //輸入有誤
    default:
        return undefined;
    }
}

/**
 * 判断此年是否为闰年.
 */
Date.prototype.isLeapYear = function() {
    return ((0 == this.getFullYear() % 4) && ((this.getFullYear() % 100 != 0) || (this.getFullYear() % 400 == 0)));
}