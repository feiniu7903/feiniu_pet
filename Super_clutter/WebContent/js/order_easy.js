
var LT_ORDER_SUBMIT = "订单提交中...";
var LT_ORDER_SUBMIT_ERROR = "哎呀,网络不给力,请稍后再试试吧";
var LT_ORDER_CANCLE = "订单取消中...";
var LT_ORDER_CANCLE_ERROR = "取消订单失败...";
var LT_ORDER_CANCLE_SUCCESS = "已经成功取消该订单!";

var  LT_ORDER_RESOURCE_MSG_1 = "订单还在审核中哦，请稍后再试";
var  LT_ORDER_RESOURCE_MSG_2 = "资源审核未通过 抱歉，预订的该产品已经全部卖完了";
var  LT_ORDER_RESOURCE_MSG_3 = "订单还在审核中哦，请稍后再试";

function initPerPrice(obj,num) {
	//var index = $(obj).attr("data-value"); // 索引
	//var perPrice = $(obj).siblings("#sell_Price_"+index).attr("data-value");//单价
	$(obj).siblings(".lv-ticket-num").html(num);
	$(obj).siblings("#quantity").val(num);
	$(obj).siblings("#branchNum").val(num);
	//主产品数量为0时，附加产品（+）按钮为灰色 
	initAdditionalProdPlusStyle(obj);
	//初始化实体票的附加产品
	initPsyAddtionalProd(obj,null);
	
	// 对于线路如果非附加产品改变 ，则附加产品数量做相应的改变 
	clickPlusOrReduceButton(obj);
	validateCoupon(false,null,null);
}
/*弹框返回函数*/
function popupModal_calback(){
	  $('a[_disappear]').click(function(){
		 popupModal(false, '', null, 0,true);
	  });
};

$(function() {
	// 非今日票，默认没有游玩时间 
	if(typeof(visitTime) != 'undefined' && "" == visitTime) {
		$("#timeHolder").css({width:"120px",color:"#d11f7f"}).html("请选择游玩日期");
		$("#scroller").hide();
	}
	
	/* 票数加*/
	$(".lv-plus").click(function(){
		// 如果没有选择游玩时间
		if(typeof(visitTime) != 'undefined' && isEmpty(visitTime) && 'true' != canOrderToday ) {
			popupModal(true, "请选择游玩时间", popupModal_calback, 0,true); 
			return false;
		}
		
		var num = parseInt($(this).siblings(".lv-ticket-num").html())+1;
		// 获取最多订票数 .
		var max = $(this).siblings("#quantity").attr('max');
		if(num > max) {
			 //lvToast(false,"最多订购数量"+max+"张",5000);
		} else {
			if(num == max) {
				// + 按钮变灰色   -按钮红色 $(this).children("a") 
				$(this).children("span").attr("class","ic_order_plus2");
			}
			$(this).siblings(".lv-reduce").children("span").attr("class","ic_order_reduce");
			// 线路附加产品修改 
			
			initPerPrice(this,num);
		}
	});
	
	/*票数减*/
	$(".lv-reduce").click(function(){
		var ticketNum = parseInt($(this).siblings(".lv-ticket-num").html());
		var min = $(this).siblings("#quantity").attr('min');
		if(ticketNum <= min) {
			//lvToast(false,"最少订购数量"+min+"张",5000);
		} else {
			if(ticketNum == ++min) {
				$(this).children("span").attr("class","ic_order_reduce2");
			}
			$(this).siblings(".lv-plus").children("span").attr("class","ic_order_plus");
			if(ticketNum>0){
				//优惠券全部取消选中 
				cancelAllCoupon();
				initPerPrice(this,ticketNum-1);
			}else{
				$(this).siblings(".lv-ticket-num").html(0);
				$(this).siblings("#quantity").val(0);
			}
		}
		
	});
	
	// 线路,门票初始化.
	iniAddtional();

});
//简易预订下单
function orderSubmitJianYi() {
	//提交表单验证
	if(!initTikcetOrderTravllerForm()) {
		return false;
	}
    //$("#order_submit").submit();
    var url = contextPath+'/order/order_submit_jianyi.htm';
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		success : function(data) {
			window.location.href = contextPath + "/order/order_succ_v3.htm?workOrderId="+data.workOrderId;
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		}
	});
}

// 取消订单.
function cancelOrder(orderId) {
		$.ajax({
			url : contextPath+'/order/order_cancel.htm',
			data : {"orderId":orderId},
			type : "POST",
			beforeSend : function() {
				//lvToast(CONTENT_LOGDING,LT_ORDER_CANCLE,LT_LOADING_CLOSE);
				//$('#editRealMsgImg').show();
			},
			success : function(data) {
				$(".lv-toast-success").hide();
				if(data.operatorStatu) {
					//lvToast(CONTENT_LOGDING,LT_ORDER_CANCLE_SUCCESS,LT_LOADING_CLOSE);
					$('#order_pay'+orderId).hide();
					$('#order_status'+orderId).html('已取消').removeClass('lv-color-01').addClass('f_9f9f9f');
				} else {
					popupModal(true, LT_ORDER_CANCLE_ERROR, popupModal_calback, 0,true); 
				}
			},
			error:function() {
				popupModal(true, LT_ORDER_CANCLE_ERROR, popupModal_calback, 0,true); 
			}
		});
}

// 初始化默认日期 价格
function initDefaultDate() {
	if(jsonData && null != jsonData && null != jsonData.datas) {
		var datas = jsonData.datas;
		for(var i =0; i < datas.length;i++ ) {
			var t = datas[i];
			if(t.sellable) {
				$("#scroller").val(t.date);
				$("#sell_Price_0").val(t.sellPriceYuan);
				break;
			}
		}
	}
}

/**
 * 更新radio选中状态. 
 */
function updateRadioClass(checkRadio) {
	if(null != checkRadio) {
		try {
			// 上次选中
			var c_code =$("#tempCouponCode").attr("data-value");
			if("" != c_code ) {
				var c_check = $("input[type=radio][value="+c_code+"]"); 
				var brother = c_check.prev(); // 上一个兄弟节点  
				brother.removeClass();
				brother.addClass("ic_radio_right");
				brother.attr("checked","checked");
			}
			
			// 取消目前选中
			var c_check2 = $("input[type=radio][value="+checkRadio.val()+"]"); 
			if("" != c_check2) {
				var brother2 = c_check2.prev();
				brother2.removeClass();
				brother2.addClass("ic_radio2_right");
				brother2.attr("checked","");
			}
		}catch(e){
			
		}
	}
}



/**
 * 优惠券活动相关. 
 * @returns {Boolean}
 */
function validateCoupon(validateCoupon,success,fail){
	// 如果游玩时间为空 
	if(isEmpty(visitTime) && 'true' != canOrderToday ) {
		if(validateCoupon == true) {
			popupModal(true, "请选择游玩日期!", popupModal_calback, 0,true); 
		}
		return false;
	}
	if(null == validateCoupon) {
		validateCoupon = false;
	}
	
    url=contextPath+'/mobile/order/validateAjaxPrice.do?validateCoupon='+validateCoupon;
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		success : function(data) {
			if(data.code==-1){
				popupModal(true, data.message, popupModal_calback, 0,true); 
//				// 更新radio选中状态。
//				if(validateCoupon) {
//					updateRadioClass(checkRadio);
//				}
				if(fail!=null){fail();}
				
				return false;
			}
			
//			// 记录上次选中的radio 
//			if(null != checkRadio) {
//				$("#tempCouponCode").attr("data-value",checkRadio.val());
//			}
			if(success!=null){success(data);}
			// 修改总价格 
			if(null != data.oughtPay) {
				$("#total_price").text("￥"+data.oughtPay);
			} else {
				$("#total_price").text("");
			}
			if(data.businessCouponInfoList!=null&&data.businessCouponInfoList.length>0){
				var html ="<ul>";
				for(var i=0;i<data.businessCouponInfoList.length;i++){
					var businessCoupon = data.businessCouponInfoList[i];
					html +="<li>"+"<span class=\"reward_ti3\">"+businessCoupon.title+"</span><span class=\"reward_ti2\">"+businessCoupon.desc+"</span></li>";
				}
				html +="</ul>"
				$("#businessCouponContent").html(html);
			}
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		}
	});
}

// 门票
function orderSubmit() {
	$('#order_fill_ticket_next_hide').show();
	$('#order_fill_ticket_next_show').hide();
	if(!initTikcetOrderTravllerForm()) {
		$('#order_fill_ticket_next_hide').hide();
		$('#order_fill_ticket_next_show').show();
		return false;
	}
	
    var url = contextPath+'/order/order_validate_traveler.htm';
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		beforeSend : function() {
			$('#order_fill_ticket_next_hide').show();
			$('#order_fill_ticket_next_show').hide();
		},
		
		success : function(data) {
			// 如果可以直接提交 
			if(data.code == '1') {
				$("#t_productType").val("");
				$('#order_submit').action = contextPath+"/order/order_submit.htm";
				routeFormSubmit();
			} else {
				$('#order_submit').submit();
			}
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		},
		complete:function() {
			$("#t_productType").val("TIKCET");
			$('#order_submit').action = contextPath+"/order/order_fill_traveler.htm";
			$('#order_fill_ticket_next_hide').hide();
			$('#order_fill_ticket_next_show').show();
		}
	});
}


/**
 * 目的地自由行
 */
function orderTravelerSubmit() {
	$('#order_fill_freenees_next_hide').show();
	$('#order_fill_freenees_next_show').hide();
	if(!initRouteOrderTravllerForm()) {
		$('#order_fill_freenees_next_hide').hide();
		$('#order_fill_freenees_next_show').show();
		return false;
	}
	
    var url = contextPath+'/order/order_validate_traveler.htm';
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		beforeSend : function() {
			$('#order_fill_freenees_next_hide').show();
			$('#order_fill_freenees_next_show').hide();
		},
		
		success : function(data) {
			// 如果可以直接提交 
			if(data.code == '1') {
				$("#t_productType").val("");
				$('#order_submit').action = contextPath+"/order/order_submit.htm";
				routeFormSubmit();
			} else {
				$('#order_submit').submit();
			}
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		},
		complete:function() {
			$("#t_productType").val("ROUTE");
			$('#order_submit').action = contextPath+"/order/order_fill_traveler.htm";
			$('#order_fill_freenees_next_hide').hide();
			$('#order_fill_freenees_next_show').show();
		}
	});
}

/**
 * 初始化门票表单 
 * @returns {Boolean}
 */
function initTikcetOrderTravllerForm() {
	if($("#visitTime").length>0 && $("#visitTime").val() == null ||$("#visitTime").val() == "" ) {
		popupModal(true, "请选择游玩日期!", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验订单数量 
	var totalQuantity = getProductQuantity("false");
	if(totalQuantity < 1) {
		popupModal(true, "订购数量总数必须大于0", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验用户名
	if(!validateUserName()) {
		popupModal(true, "请输入订单联系人的姓名", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验手机号
	if(!isMobile($('#mobile').val())){
		return false;
	}
	
	// 总价格
	var total_price = $("#total_price").html();
	if(!isEmpty(total_price)) {
		$("#t_totalPrice").val(total_price.replace('￥',''));
	}
	// 总数量（非附加产品）双人 为 2 
	$("#t_totalQuantity").val(totalQuantity);
	
	return true;
}
//简易预订手机号验证
function isMobile(m) {
	if(isEmpty(m)) {
		popupModal(true, "请输入订单联系人的手机号码", popupModal_calback, 0,true); 
		return false;
	}
	if (!m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		popupModal(true, "请输入正确的订单联系人的手机号码", popupModal_calback, 0,true); 
		return false;
	} else {
		return true;
	}
}
// 初始化线路表单
function initRouteOrderTravllerForm() {
	if($("#scroller").length>0 && $("#scroller").val() == null ||$("#scroller").val() == "" ) {
		popupModal(true, "请选择游玩日期!", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验订单数量 
	var totalQuantity = getProductQuantity("false");
	if(totalQuantity < 1) {
		popupModal(true, "订购数量总数必须大于0", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验用户名
	if(!validateUserName()) {
		popupModal(true, "请输入订单联系人的姓名", popupModal_calback, 0,true); 
		return false;
	}
	
	// 校验手机号
	if(!isMobile($('#mobile').val())){
		return false;
	}
	
		// 校验身份证. 
	if($("#needIdCard").val() == "true" && $("#idCard").length > 0) {
		var card = $("#idCard").val();
		if(isEmpty(card)) {
			popupModal(true, "请输入联系人的身份证号码", popupModal_calback, 0,true); 
			return false; 
		}
		
		var code = validateIdCard(card);
		if("true" != code) {
			popupModal(true, "请输入正确的订单联系人的身份证号码", popupModal_calback, 0,true); 
			return false;
		}
	}
	
	// 驴行协议勾选. 
	if(!isCheckedAgreement('2')){
		return false ;
	}
	
	// 总价格
	var total_price = $("#total_price").html();
	if(!isEmpty(total_price)) {
		$("#t_totalPrice").val(total_price.replace('￥',''));
	}
	// 总数量（非附加产品）双人 为 2 
	$("#t_totalQuantity").val(totalQuantity);
	
	//保险的数量
	selectBaoxian();
	
	return true;
}

/**
 * 线路提交表单 
 */
function routeFormSubmit() {
    var url = contextPath+'/order/order_submit.htm';
	$.ajax({
		url : url,
		data : $('#order_submit').serialize(),
		type : "POST",
		beforeSend : function() {
			// lvToast(true,LT_ORDER_SUBMIT,LT_LOADING_CLOSE);
			//$('#editRealMsgImg').show();
		},
		success : function(data) {
			if(data.code == '1') {
			   // 页面跳转 
				window.location.href = contextPath + "/order/order_success.htm?orderId="+data.orderId;
			} else {
				popupModal(true, data.msg, popupModal_calback, 0,true); 
			}
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		}
	});
}


// 校验订购数量 
function validateOrderQuantity() {
	var flag = 1;
	// 校验票
	$("#quantity").each(function(index){
		var max = $(this).attr('max');
		var min = $(this).attr('min');
		var v = $(this).val();
		
		if(isNumber(v)){ // 如果是数字 
			if(parseInt(v)<=0){
				popupModal(true, "订购数量总数必须大于0", popupModal_calback, 0,true); 
			    flag  = 0;
				return false;
			}
			
			if(parseInt(min) <= parseInt(v)  && parseInt(v) <= parseInt(max)) {
				return true;
			} else {
				if(parseInt(min) > parseInt(v)) {
					popupModal(true, "订购数量不能小于最小订购量!", popupModal_calback, 0,true); 
				} else if(parseInt(v) > parseInt(max)) {
					popupModal(true, "订购数量不能大于最大订购量!", popupModal_calback, 0,true); 
				} else {
					popupModal(true, "订购数量不正确!", popupModal_calback, 0,true); 
				}
				flag  = 0;
				return false;
			};
		} else {
			popupModal(true, "订购数量请填写数字类型!", popupModal_calback, 0,true); 
			flag  = 0;
			return false;
		};
    });
	
	if(flag == 1 ) {
		return true;
	} else {
		return false;
	};
};

//校验订购数量 不能为0 
function productQuantityAvaliable() {
	var flag = 0;
	// 校验票
	$("[data-order-quantity]").each(function(index){
		var v = $(this).val();
		if(isNumber(v)){ // 如果是数字 
			if(parseInt(v) > 0) {
				flag = 1;
				return true;
			}
		}
    });
	if(flag == 1 ) {
		return true;
	} else {
		return false;
	};
};

// 判断是否合法的用户名 
function validateUserName() {
	if($("#userName").length > 0) {
		var m = $("#userName").val();
		if(!isEmpty(m) && m.length < 50) {
			return true;
		} else {
			return false;
		};
	};
};

//判断是否合法的用户名 
function validateUserName_new(id) {
	if($("#"+id).length > 0) {
		var m = $("#"+id).val();
		if(!isEmpty(m) && m.length < 50) {
			return true;
		} else {
			return false;
		};
	};
};


// 勾选驴行协议. 
function isCheckedAgreement(type) {
	var check = $("#agreement").attr('checked');
	if(undefined == check || 'undefined' == check || !(check =='checked')) {
		var msg_type = "票务预订协议";
		if(type =='2') {
			msg_type = "委托服务协议";
		}
		popupModal(true, "您需要同意驴妈妈"+msg_type+"才能预订哦", popupModal_calback, 0,true); 
		return false;
	}
	return true;
}



/**
 * ajax加载数据.
 */
function getData(){
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,10000);
	var page = $("#page").val();
	var param = {"page":++page,"ajax":true};
	$.get(contextPath+"/order/myorder.htm",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#page").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
			//lvToast(true,"没有更多的数据了!",3000);
		}
	});
};

// 订单提示信息 . 
function orderMsg(type) {
	if(type=='1') {
		popupModal(true, LT_ORDER_RESOURCE_MSG_1, popupModal_calback, 0,true); 
	} else if(type=='2') {
		popupModal(true, LT_ORDER_RESOURCE_MSG_2, popupModal_calback, 0,true); 
	} else if(type=='3') {
		popupModal(true, LT_ORDER_RESOURCE_MSG_3, popupModal_calback, 0,true); 
	}
}


//页面跳转.
function time_price_skip(url) {
	/*lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.open(url);*/
	//联系人信息保存LOC
	var userName=$("#userName").val();
	var mobile=$("#mobile").val();
	if(userName!=null && userName!=""){
		setLocalStorage("link_user_name",userName);
	}
	if(mobile!=null && mobile!=""){
		setLocalStorage("link_user_mobile",mobile);
	}
	
	window.location.href=url;
	
}

// 支付链接 
function union_skip_pay(url,orderId) {
	// lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	// 是否使用奖金支付
	var obj = $("#ic_check_right_id");
	if(null != obj && obj.attr("class") == "ic_check_right") {
		pay_bounus(orderId);
		return false;
	}
	
	setLocalStorage(LT_ORDER_CURRENT_PAY_ORDER_ID,orderId);
	window.location.href=url;
}

var lastCheckedRadio = null;
$(document).ready(function(){
	if(typeof(visitTime) == 'undefined' || null == visitTime) {
		return false;
	}
	validateCoupon(false,null,null);
	$("#couponActivity").find("li").each(function(){
		$(this).bind("click",function(){
			var obj = $(this);
			var styleSpan = obj.find("span[name=couponRadios]");
			
				if(styleSpan.attr("class")=="ic_radio_right"){
					$("#emptyRadio").attr("checked","");
					obj.find("span[name=couponRadios]").eq(0).removeClass();
					obj.find("span[name=couponRadios]").eq(0).addClass("ic_radio2_right");
					validateCoupon(true,null,null);
					lastCheckedRadio = null;
					if(obj.attr("id")=="point2coupon") {
						obj.find(".reward_ti2").html($("#point2coupon_hidden_context").html());
					}
					
				} else {

					if(obj.attr("id")=="point2coupon"){
						if(obj.find("input[name=couponCode]").attr("checked")==undefined){
							point2coupon();
							return false;
						} 
					}
                    $(this).find("input[name=couponCode]").eq(0).attr("checked","checked");
					
					var success = function(){
						initCouponRadios();
						obj.find("span[name=couponRadios]").eq(0).removeClass();
						obj.find("span[name=couponRadios]").eq(0).addClass("ic_radio_right");
						lastCheckedRadio = $(this).find("input[name=couponCode]").eq(0);
						obj.find("input[name=couponCode]").attr("checked","checked");
						$("#point2coupon").find(".reward_ti2").html($("#point2coupon_hidden_context").html());
					};
					
					var fail = function(){
						$("#emptyRadio").attr("checked","");
						if(null != lastCheckedRadio) {
							lastCheckedRadio.attr("checked","checked");
						}
						obj.find("span[name=couponRadios]").eq(0).removeClass();
						obj.find("span[name=couponRadios]").eq(0).addClass("ic_radio2_right");
					};
					validateCoupon(true,success,fail);
				}
		});
	});
	
	// 判断实体票的附加产品是否存在 , 10.17 
	if($("#exp_li_id").length > 0 ) {
		// 绑定实体票
		$("li[id='exp_li_id']").each(function(){
			$(this).bind("click",function(){
				// 数量不能为0
				initPsyAddtionalProd(null ,$(this));
				
				var success = function(){
					
				};
				var fail = function(){
					
				};
				validateCoupon(true,success,fail);
			});
		});
	}
});

function initCouponRadios(){
	$("span[name='couponRadios']").each(function(){
		$(this).removeClass();
		$(this).addClass("ic_radio2_right");
	});
}


/**
 * 优惠券全部取消选中
 */
function cancelAllCoupon() {
	// 取消全部选中 
	var checkRadio = $("input[name='couponCode']:checked");
	if(null != checkRadio) {
		checkRadio.attr("checked",false);
	}
	// 去掉样式
	var radioClass = $(".ic_radio_right");
	if(null != radioClass) {
		radioClass.removeClass();
		radioClass.addClass("ic_radio2_right");
	}
	$("#point2coupon_header").show();
	$("#point2coupon_footer").hide();
}

/**
 * 奖金支付选中切换 
 * @param id
 */
function bonusPay(id){
	var obj = $("#"+id);
	// 选中 
	if(obj.attr("class") == "ic_check_right") {
		obj.removeClass();
		obj.addClass("ic_check_right2");
		$("#needPay_id").show();
		$("#hasPay_id").hide();
	} else {
		obj.removeClass();
		obj.addClass("ic_check_right");
		$("#needPay_id").hide();
		$("#hasPay_id").show();
	}
}

//确认支付 
function pay_now(orderId) {
	setLocalStorage(LT_ORDER_CURRENT_PAY_ORDER_ID,orderId);
	// 是否使用奖金支付
	var obj = $("#ic_check_right_id");
	var c_val = $("#reward_check").val();
	if(null != obj && obj.attr("class") == "ic_check_right" && null != c_val && c_val == '1' ) {
		pay_bounus(orderId);
		return false;
	} else {
		//直接支付 
		pay_after_bounus(orderId);
	}
}

// 奖金账号支付  
function pay_bounus(orderId) {
	
	var url =contextPath + '/router/rest.do?method=api.com.user.bonusPay&orderId='+orderId+'&lvversion=3.1.0';
	$.ajax({
		url : url,
		type : "POST",
		success : function(data) {
			if(null == data || data.code==-1){
				lvToast(false,null==data?LT_ORDER_SUBMIT_ERROR:data.message,5000);
				return false;
			}
			// 支付成功. 
			if(data.code == 1) {
				$("#reward_check").val('');
				pay_after_bounus(orderId);
			}
		},
		error:function() {
			popupModal(true, LT_ORDER_SUBMIT_ERROR, popupModal_calback, 0,true); 
		}
	});
}

/**
 * 奖金支付支付完成以后，使用银行会或者支付宝支付  
 * @param orderId
 */
function pay_after_bounus(orderId) {
	var pay_path = $('input:radio[name=paypath]:checked').val();
	// 如果支付宝
	if("CREDITCARD" == pay_path) { //信用卡 
		window.location.href= contextPath + "/alipay/pament_parttern.do?payPath=CREDITCARD&firstChannel=CLIENT&orderId="+orderId;
	} else if("DEBITCARD" == pay_path ) { //如果借记卡 
		window.location.href= contextPath + "/alipay/pament_parttern.do?payPath=DEBITCARD&firstChannel=CLIENT&orderId="+orderId;
	} else if("lastUsed" == pay_path ) { //如果是最近使用 
		var pay_url = $("#pay-lastUsed").attr("data-payurl");
		window.location.href=pay_url;
	} else { // 默认支付宝
		if(navigator.geolocation) {
			setLocalStorage("LT_cashierCode","ALIPAY");
			setLocalStorage("LT_cashierName","支付宝");
		}
		var pay_url = $("#pay-alipayWap").attr("data-payurl");
		window.location.href=pay_url;
	}
}



//选中支付方式
function chose_payment_pattern(spanId,radioId) {
	$(".ic_radio_right").removeClass().addClass("ic_radio2_right");
	$("#"+spanId).removeClass().addClass("ic_radio_right");
	$("#"+radioId).attr("checked","checked");
}

// 银行卡支付
function pay_by_cashier(url,cashierCode,name) {
	if(navigator.geolocation) {
		setLocalStorage("LT_cashierCode",cashierCode);
		setLocalStorage("LT_cashierName",name);
	}
	window.location.href = url;
}

/***************** 线路预订相关 ******************/
/**
 * 判断是否线路
 */
function isRoute() {
	if($("#t_productType").length > 0 && $("#t_productType").val() == "ROUTE") {
		return true;
	}
	return false;
}

/**
 * 判断是否附加产品
 */
function isAddtional(obj) {
	if(null != obj && $(obj).attr("data-additinal").length > 0 && $(obj).attr("data-additinal")=="true") {
		return true;
	}
	return false;
}

/**
 * 设置选择保险的数量
 */
function selectBaoxian() {
	$("input[id^='quantity']").each(function(index){
		// 附加产品 
		if(isAddtional(this)) {
			var baoxian = $(this).attr("data-subType");
			if("INSURANCE" == baoxian ) {
				$("#t_baoxianSelect").val('0');
				var value = $(this).val();
				if(null != value && $.trim(value) !="" && value > 0) {
					$("#t_baoxianSelect").val(value);
					return false;
				}
			}
		}
    });
}

/**
 * 获取产品数量
 * 附加产品 true
 * 非附加产品false
 */
function getProductQuantity(flag) {
	var totalQuantity = 0;
	// 校验票
	$("input[id^='quantity']").each(function(index){
		if($(this).attr("data-additinal").length > 0 && $(this).attr("data-additinal")==flag) {
			var adultQ = $(this).attr("data-adultQuantity");
			var childQ = $(this).attr("data-childQuantity");
			var quantity = $(this).val();
			totalQuantity =  totalQuantity + (parseInt(quantity) * (parseInt(adultQ) + parseInt(childQ)));
		}
    });
	return totalQuantity;
}

/**
 * 获取房差产品数量
 */
function getFangchaTypes() {
	var totalQuantity = 0;
	// 校验票
	$("input[id^='quantity']").each(function(index){
		if(isAddtional(this)) {
			var branchType = $(this).attr("data-branchType");
			if(branchType == "FANGCHA") {
				totalQuantity++;
			}
		}
    });
	return totalQuantity;
}

/**
 * 点击加 or 减 按钮
 * @param obj img
 */
function clickPlusOrReduceButton(obj) {
	if(isRoute()) {
		var opt = $(obj).attr("data-opt");
		// 点击非附加产品
		if(!isAddtional($(obj).siblings("#quantity"))) {
			if(null != opt) {
				iniAddtional(opt);
			}
		} else { // 点击附加产品
			setAdditionalQuantity($(obj).siblings("#quantity"),opt,"1",getFangchaTypes());
		}
	}
}

/**
 * 页面加载完成后初始化页面 
 * @param opt
 */
function iniAddtional(opt) {
	if(isRoute()) {
		var fangchaQ = getFangchaTypes();
		if(null == opt || '' == opt) {
			opt = "plus";
		}
		$("input[id^='quantity']").each(function(index){
			// 如果是附加产品
			if(isAddtional(this)) {
				setAdditionalQuantity(this,opt,"0",fangchaQ);// 房差初始化 
				$(this).siblings(".lv-ticket-num").html($(this).val());
			} else {
				$(this).siblings(".lv-ticket-num").html($(this).val());
			}
		});
		// 如果主产品数量为0 ； 则附加产品不能新增（+）号为灰色
		initAdditionalProdPlusStyle(null);
	} else {
		//初始化实体票的附加产品
		initPsyAddtionalProd(null,null);
	}
}

/**
 * 初始化附加产品（+）号的颜色，如果主产品数量为0 ； 则附加产品不能新增（+）号为灰色，不能点击。
 * obj 当前点击的对象 
 */
function initAdditionalProdPlusStyle(obj) {
	var t_opt = "reduce";
	// 如果点击是非附加产品
	if(null == obj || !isAddtional($(obj).siblings("#quantity"))) {
		if(null != obj) {
			t_opt = $(obj).attr('data-opt');
		}
		var totalQuantity = getProductQuantity("false");
		if((totalQuantity == 1 && t_opt =="plus") || (totalQuantity < 1 && t_opt =="reduce" )) { // 产品数量为0 ；
			// 设置所有附加产品数量为0
			$("input[id^='quantity']").each(function(index){
				// 如果是附加产品
				if(isAddtional(this)) {
					//
					if(t_opt == "plus") {
						$(this).siblings(".lv-plus").children("span").attr("class","ic_order_plus");
					} else {
						$(this).siblings(".lv-plus").children("span").attr("class","ic_order_plus2");
					}
				}
			});
		} else {
			
		}
	}
}

/**
 * 根据主产品数据量计算附加产品数量
 * obj quantity当前对象
 * opt 加或者减  plus 加 ；reduce  减
 * tag 标示，0标示点击非附加产品， 1：点击附加产品
 * fangchaQ 房差类别数量（有几种房差） 
 * OPT("任选") 任选，从最小值到最大值
 * ANY("可选") 可选，最小值或产品数多值 
 * ALL("等量") 等量，按产品数全部添加
 *  
 */
function setAdditionalQuantity(qobj,opt,tag,fangchaQ) {
	if(isRoute()) {
		var pro_quantity = getProductQuantity("false");
		// 任选的附加产品不可以选大于游玩人的数量 
		//$(qobj).attr("max",pro_quantity);
		var min = $(qobj).attr("min");
		var num = $(qobj).val();
		var saleNumType = $(qobj).attr("data-sellType");
		var value = $(qobj).val();
		// 以下逻辑有点乱 ， 可以合并 . 
		if(saleNumType == "ANY") {
			//主产品大于0 或者点击附加产品 
			if((value > 0 && tag =="0") || tag =="1") {
				if("plus" == opt) {
					num = pro_quantity;
				} else {
					if(tag == "0") {
						num = pro_quantity;
					} else {
						num = min;
					}
				}
				if(pro_quantity > "0") { // 如果主产品数据量大于0；则附加产品数量根据主产品计算 
					$(qobj).attr("max",pro_quantity);
				} else { // 如果后台设置默认为1 ； 则即使主产品为0 ，附加产品默认也是1 
					$(qobj).attr("max",min);
					num = min;
				}
			} else if(value == 0 && tag == "0") { // 主产品默认为0；点击主产品 
				if(pro_quantity > "0") {
					$(qobj).attr("max",pro_quantity);
				} else { // 如果后台设置默认为1 ； 则即使主产品为0 ，附加产品默认也是1 
					num = min;
				}
			}
		} else if(saleNumType == "ALL") {
			$(qobj).attr("min",pro_quantity);
			$(qobj).attr("max",pro_quantity);
			num =  pro_quantity;
		} else {// OPT("任选") 任选，从最小值到最大值
			if(pro_quantity >=0) {
				$(qobj).attr("max",pro_quantity);
			} 
			if("reduce" == opt && num > pro_quantity ) {
				if(pro_quantity < min) {
					num = min;
				} else {
					num = pro_quantity;
				}
				
			}
		}
		// 根据最大值，最小值和当前值改变加号的颜色 。
		changeImgStyle(qobj,opt,num);
		
		// 房差 ，如果一个游玩人，一种房差
		fangcha_check(qobj,pro_quantity,fangchaQ);
	}
}

// 房差标志位
var t_fangcha_flag = 0; 
// 房差业务逻辑处理
function fangcha_check(qobj,pro_quantity,fangchaQ) {
	var subType = $(qobj).attr("data-branchType");
	if(subType == "FANGCHA") {
		// 如果房差只有一类
		if(fangchaQ==1) {
			if(pro_quantity == 1) { // 且产品数量为1
				t_fangcha_flag = 1;
				$(qobj).siblings(".lv-ticket-num").html(1);
				$(qobj).val(1);
				$(qobj).siblings(".lv-plus").children("img").attr("src","http://pic.lvmama.com/img/mobile/touch/img/plus02.png");
				$(qobj).siblings(".lv-reduce").children("img").attr("src","http://pic.lvmama.com/img/mobile/touch/img/reduce.png");
			} else {
				if(t_fangcha_flag == 1) {
					$(qobj).siblings(".lv-ticket-num").html(0);
					$(qobj).val(0);
					t_fangcha_flag = 0;
				}
				$(qobj).siblings(".lv-plus").children("img").attr("src","http://pic.lvmama.com/img/mobile/touch/img/plus.png");
			}
		} 
	}
}
/**
 * 更改加和减的按钮
 * @param quantity obj  图片对象 附加产品对象 
 * @param opt  reduce 减 和 加   当前点击按钮 
 * @param num  附加产品当前数量
 */
function changeImgStyle(quanObj,opt,num) {
	$(quanObj).siblings(".lv-ticket-num").html(num);
	$(quanObj).val(num);
	var obj = $(quanObj).siblings(".lv-"+opt);
	if(null != obj) {
		var min = $(quanObj).attr("min"); // 最小值 
		var max = $(quanObj).attr("max"); // 最大值
		// num 表示当前值 
		if("reduce" == opt) {// 减
			if(num >= min) {
				if(num <= min) {
					$(obj).children("span").attr("class","ic_order_reduce2");
				}
				if(max > num) {
				    $(obj).siblings(".lv-plus").children("span").attr("class","ic_order_plus");
				}
			}
		} else { // 点击的是+号 
			if(num <=max) {
				if(num == max) {
					// + 按钮变灰色   -按钮红色 $(this).children("a") 
					$(obj).children("span").attr("class","ic_order_plus2");
				} else{
					$(obj).children("span").attr("class","ic_order_plus");
				}
				if(min < num) {
				   $(obj).siblings(".lv-reduce").children("span").attr("class","ic_order_reduce");
				} else {
					 $(obj).siblings(".lv-reduce").children("span").attr("class","ic_order_reduce2");
				}
			}
		}
	}
}

/**
 * 附加产品说明切换
 */
function additionalPageChange(tag) {
	if("1" == tag) {
		$("#addition_product_desc").show();
		$("#order_fill").hide();
		$("#order_fill_header").hide();
		$('html,body').animate({scrollTop: '0px'},0);
	} else {
		$("#addition_product_desc").hide();
		$("#order_fill").show();
		$("#order_fill_header").show();
	}
}
/**********实体票 相关开始*********/
// 初始化实体票的附加产品 ，obj主产品对象； expObj：实体票附加产品
function initPsyAddtionalProd(obj,expObj) {
	if($("#exp_li_id").length > 0 ) {// 如果是实体票且有附加产品
		var totalQuantity = getProductQuantity("false");
		// obj为null表示初始化 
		if(null == obj) {
			if(totalQuantity > 0) {
				// 点击实体票附加产品
				if(null != expObj) {
					// 取消所有样式
					$("span[data-name='expBranchName']").each(function(){
						$(this).removeClass();
						$(this).addClass("ic_radio2_right");
					});
					var c_obj = expObj.find("span[data-name=expBranchName]").eq(0);
					c_obj.removeClass();
					c_obj.addClass("ic_radio_right");
					$("#expBranchId").val(c_obj.attr("data-expBranchId"));
				} else {
					checkTheFirstExpress();
				}
			};
		} else{ // 点击主产品加或者减
			var t_opt = "reduce";
			if(null != obj) {
				t_opt = $(obj).attr('data-opt');
			}
			unCheckAllExpress();
			if(t_opt =="plus") { // 产品数量为0 ；
				checkTheFirstExpress();
			}else if(totalQuantity > 0 && t_opt =="reduce" ){
				checkTheFirstExpress();
			}
		} 
	}
}

/**
 * 默认选中第一个附加产品
 */
function checkTheFirstExpress() {
	var c_obj = $("li[id='exp_li_id']").eq(0).find("span[data-name=expBranchName]").eq(0);
	c_obj.removeClass();
	c_obj.addClass("ic_radio_right");
	$("#expBranchId").val(c_obj.attr("data-expBranchId"));
};

/**
 * 取消选择附加产品
 */
function unCheckAllExpress() {
	$("span[data-name='expBranchName']").each(function(){
		$(this).removeClass();
		$(this).addClass("ic_radio2_right");
		$("#expBranchId").val("");
	});
};

/**
 * 是否选中附加产品（快递费）
 */
function isCheckedExpress() {
	$("span[data-name='expBranchName']").each(function(){
		var className = $(this).attr("class");
		if(className == "ic_radio_right") {
			return true;
		}
	});
	
	return false;
}

/***********实体票 相关结束******/