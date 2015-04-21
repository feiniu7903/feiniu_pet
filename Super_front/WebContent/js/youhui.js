$(function(){
    $("#couponActivity").find("div.selectbox-drop").find("li").click(function(){
        var dataValue = $(this).attr("data-value");
        var payment = $(this).attr("payment");
        initYouhuiContent($(this));
        if(dataValue == 'userCoupon'){
            $("#myCoupon").show();
            $("#couponInfo_youhui").html("");
            $("#myCash").hide();
        }else if(dataValue == 'userCash'){
            $("#myCash").show();
            $("#couponInfo_youhui").html("");
            $("#myCoupon").hide();
        }else{
            $("#myCoupon").hide();
            $("#myCash").hide();
            if(dataValue == "" ){
                $("#couponInfo_youhui").html("");
            }else{
                $("#productId_youhui").val(dataValue);
                $("#couponPayment").val(payment);
                $("#couponChecked").val("true");
            }
        }

        updatePrice_youhui();
    });
});

function userCouponCode(obj){
    initYouhuiContent($(obj));
    if(obj == 'code_youhui' || obj == 'couponCode_radio'){
        var couponCode= $("#code_youhui").val();
        if(couponCode == ""){
            $("#couponInfo_youhui").html("");
            $("#code_youhui").focus();
            if(obj == 'code_youhui'){
                $("#couponInfo_youhui").html("请输入优惠券代码");
                return;
            }
        }else{
            $("#couponCode").val(couponCode);
            $("#couponChecked").val("true");
        }
    }else{
        $("#code_youhui").val($(obj).val());
        $("#couponCode").val($(obj).attr("couponCode"));
        $("#couponChecked").val("true");
    }
    updatePrice_youhui();
}


function checkCode_youhui(obj){
	initYouhuiContent($(obj));
	$(obj).parent().find("#couponChecked").val("true");
	var code_youhui = $(obj).parent().find("#code_youhui");
	var productId = $(obj).parent().find("#productId");
	var info = $(obj).parent().find("#couponInfo_youhui");
	var orderTotal = 0;
	
	if($(obj).attr("type")=='button'||$(obj).parent().find("#code_youhui").attr("type")=="text"){
		var couponCodeUseStatus = $("#couponCodeUseStatus").val();
		if(couponCodeUseStatus == 'unuse' || couponCodeUseStatus == 'beginCodeUse'){
				//使用优惠券，判断优惠券是否为空
				if ($.trim(code_youhui.val())=="") {
					info.html("请输入优惠券代码");
					code_youhui.focus();
					$("#youhuiquan").html(0);
					$("#dingdanjiesujinger").html($("#heji").html());
					$(obj).parent().find("#couponChecked").val("false");
					return false;
				}else{
					 $("#couponCodeUseStatus").val("beginCodeUse");
				}
		}else{
			//取消使用优惠券，清空优惠券输入框
			$("#youhuiContent").find("#code_youhui").val("");
			$("#couponCodeUseStatus").val("unuse");
			$("#checkCodeBtn").val("确定使用");
			$(obj).parent().find("#couponChecked").val("false");
		}
	} else {
		//使用优惠活动，清空优惠券输入框
		$("#youhuiContent").find("#code_youhui").val("");
		$("#couponCodeUseStatus").val("unuse");
		$("#checkCodeBtn").val("确定使用");
        if($(obj).parent().find("#availableCash").html()!=null){
            $("#myCash").show();
        }
	}
	
	var productIds="";
	$("input[name='paramName']").each(function(){
		productIds+=","+$(this).attr("productId");
	});
	updatePrice_youhui();
}

function initYouhuiContent(obj){



	/*$("span[id='couponInfo_youhui']").each(function(){
		$(this).html("");
	});
	$(obj).parent().find("input[name='couponChose']").each(function(){
		$(this).attr("checked",true);
	});
	$("div[class='coupon-list']").each(function(){
		$(this).find("#couponChecked").val("false");
	});*/

    $("#couponChecked").val("false");
    $("#couponCode").val("");
    $("#productId_youhui").val("");
    $("#couponPayment").val("");
    $("#cashValueJian").html("0");
    $("#cashValue_input").val("");
    $("#cashValue").val("0");
}





function checkCode_youhui_notInfo(){
	var code_youhui = $("#code_youhui");
	if ($.trim(code_youhui.val())=="") {
		updatePrice_youhui();
	}else{
		checkCode_youhui();
	}
	
}

//如果是超级自由行，需要计算当前的相关的附加产品到buyInfo.content当中.
function updateSelfPackContent(uform){
	var selfPack=uform.find("input[name='buyInfo.selfPack']").val();
	if(selfPack=="true"){
		var $list=$("#buyUpdateForm input[sellName]");
		var body="";
		$list.each(function(i){
			var $this=$(this);			
			var num=parseInt($(this).val());
			if(num!=NaN&&num>0){
				var prodBranchId=$this.attr("name").replace("buyInfo.buyNum.product_","");
				if(body!=""){
					body+=";";
				}
				body+="addition_"+prodBranchId+"_"+num;
			}
		});
		var content=uform.find("input[name='content']").val();
		if(body!=""){
			content+=";";
			content+=body;
		}		
		uform.find("input[name='buyInfo.content']").val(content);
	}
}

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

function updatePrice_youhui(){
	var uform = $("#buyUpdateForm");
	updateSelfPackContent(uform);
	var m = uform.getForm({   
	            prefix:''
	 });
    $.ajax(
        {
            type: "POST",
            async: false,
            url: "/buy/ajaxPriceInfo.do",
            data: m,
            dataType: "json",
            success: function (data) {
                if (data.priceInfo.success) {
                    $("#yuanjia").html(data.priceInfo.marketPrice);
                    $("#heji").html(data.priceInfo.price);

                    $("#dingdanjiesujinger").html(data.priceInfo.oughtPay);
                    $("#fanxian").html(data.priceInfo.bonus);

                    if(data.priceInfo.cashValue>0){
                        $("#cashValueJian").html(data.priceInfo.cashValue);
                    }else{
                        $("#youhuiquan").html(data.priceInfo.coupon);
                        $("#cashValueJian").html("0");
                    }
                    $("#availableCash").html(data.priceInfo.availableCash);
                    $("#availableCashShow").html(data.priceInfo.availableCash);

                    //展示优惠系统信息
                    var productBranchId;
                    var productCouponType;
                    var productBranchName;
                    var displayInfo;
                    var validateBusinessCouponInfoList = data.priceInfo.validateBusinessCouponInfoList;
                    $("#earlyCouponShow").hide();
                    $("#moreCouponShow").hide();
                    if (validateBusinessCouponInfoList == null || validateBusinessCouponInfoList.length == 0) {
                        $("#businessCouponDisplayInfo").hide();
                    } else {
                        $("#earlyCoupon").html("");
                        $("#moreCoupon").html("");
                        for (var i = 0; i < validateBusinessCouponInfoList.length; i++) {
                            productBranchId = validateBusinessCouponInfoList[i].productBranchId;
                            productCouponType = validateBusinessCouponInfoList[i].couponType;
                            displayInfo = validateBusinessCouponInfoList[i].displayInfo;
                            if ($("#businessCouponBranchId" + productBranchId).val() != null && $("#businessCouponBranchId" + productBranchId).val() != "") {
                                productBranchName = $("#businessCouponBranchId" + productBranchId).val();
                                if (productCouponType == "EARLY") {
                                    $("#earlyCouponShow").show();
                                    $("#earlyCoupon").html($("#earlyCoupon").html() + "<p class='border ib poptip'><span class='poptip-arrow poptip-arrow-left'><em>◆</em><i>◆</i></span>" + productBranchName + displayInfo + "</p>");
                                } else if (productCouponType == "MORE") {
                                    $("#moreCouponShow").show();
                                    $("#moreCoupon").html($("#moreCoupon").html() + "<p class='border ib poptip'><span class='poptip-arrow poptip-arrow-left'><em>◆</em><i>◆</i></span>" + productBranchName + displayInfo + "</p>");
                                }
                            }
                        }
                        $("#businessCouponDisplayInfo").show();
                    }

                    //更新优惠说明0
//                    if ($(":radio[name='couponChose']:checked").length > 0) {
                    if ($("#couponChecked").val() == "true") {
                        var info = $("#couponInfo_youhui");
                        if (data.priceInfo.info.key == "OK") {
                            info.html("您使用优惠抵扣了<em>￥" + (-data.priceInfo.info.orderYouhuiAmountYuan) + "</em>订单金额");
                            $("#productId_youhui").val(data.priceInfo.info.couponId);

                            //设置优惠券使用状态
                            var couponCodeUseStatus = $("#couponCodeUseStatus").val();
                            if (couponCodeUseStatus == "beginCodeUse") {
                                $("#couponCodeUseStatus").val("used");
                                $("#checkCodeBtn").val("取消使用");
                            }
                        } else {
                            $("div[class='coupon-list']").each(function () {
                                $(this).find("#couponChecked").val("false");
                            });
                            info.html(data.priceInfo.info.value);
                            return false;
                        }
                    }

                } else {
                    alert(data.priceInfo.msg);
                }
            }});
}

function getAllPrice(){
	var datas = new Array();
	var uform = $("#buyUpdateForm");
	updateSelfPackContent(uform);
	var m = uform.getForm({   
	            prefix:''
	 }); 
	$.ajax({type:"POST",async:false,url:"/buy/ajaxPriceInfo.do",data:m, dataType:"json", success:function (data) {
		if(data.priceInfo.success){
			price=data.priceInfo.price;
			quantity = data.priceInfo.orderQuantity;
			datas[0]=price;
			datas[1]=quantity;	
		}else{
			throw data.priceInfo.msg;
		}
	}});
	return datas;	
}
function delCouponTotal_youhui(obj){
	if(obj.attr("hasAdd")=="true"){
		var amount=obj.val();
		var couponPrice = $("#textNum"+mainProductId).attr("couponPrice");
		var couponPrice = parseInt(couponPrice)-(-amount);
		$("#textNum"+mainProductId).attr("couponPrice",couponPrice);
		// alert($("#textNum"+mainProductId).attr("couponPrice"));
		updatePrice_youhui();
		obj.removeAttr("hasAdd");
	}
}


function checkOrder_youhui(obj,minAmt){
	var min = parseInt(minAmt);
	var heji = $("#heji").html()
	if (parseInt(heji)<min) {
		alert("合计金额不满足优惠条件");
		$(obj).attr("checked",false);
		$(obj).parent().find("couponCodeId").val("");
		return false;
	}
	checkOrderCoupon_youhui($(obj));
	return true;
}

function checkOrderCoupon_youhui(obj){
	var content   = obj.parent();
		var info = content.find("#couponInfo");
		var productId = content.find("#productId").val();
		var couponId =  content.find("#couponId").val();
		var withCode = content.find("#withCode").val();
		var couponTarget = content.find("#couponTarget").val();
		var couponCodeIdObj = content.find("#couponCodeId");
		$.ajax({type:"POST",async:false,url:"/check/validateCouponCodeOrOrder.do",
			data:{couponId:couponId,couponTarget:couponTarget,withCode:withCode}, 
			dataType:"json", 
			success:function (data) {
				if(data.info.key=="OK"){
				couponCodeIdObj.val(data.info.coupon.couponCodeId);
				// var couponPrice =
				// $("#textNum"+productId).attr("couponPrice");
				}
			}});
}


//积分兑换优惠券 JS
function changeCouponByPoint(subProductType, couponId, point){
	$("#changeCouponByPointButton").attr("disabled", "disabled");
	if(confirm("请问您确定要用积分兑换此优惠券吗？"))
	{
		$.getJSON("http://login.lvmama.com/nsso/ajax/pointChangeCoupon.do?subProductType="+ subProductType+"&couponId="+couponId+ "&jsoncallback=?",function(json){
			if (json.success) {
				$("#code_youhui").val(json.errorText);
				$("#couponCodeUseStatus").val("unuse");
				$("#checkCodeBtn").trigger("click");
				var currentPoiont = $("#currentUserPoint").html();
				$("#currentUserPoint").html(currentPoiont-point);
				currentPoiont = $("#currentUserPoint").html();
				if(currentPoiont <= 0){
					$("#pointChangeCouponDiv").hide();
				}
				$("#changeCouponByPointButton").removeAttr('disabled');
			} else {
				alert(json.errorText);
				$("#changeCouponByPointButton").removeAttr('disabled');
			}
			
		});
	}
	else
	{
		$("#changeCouponByPointButton").removeAttr('disabled');
	}
	
}