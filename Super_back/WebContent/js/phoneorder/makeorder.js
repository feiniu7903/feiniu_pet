
function queryUsr() {
	var m = $('#userForm').getForm( {
		prefix : ''
	});
	if (m.userName == "") {
		alert("请输入用户名或者手机号码查询！");
		return false;
	}
	$("#userItem").refresh(m);
}

//获取LVCC信息
function getLVCC() {
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/doGetLVCC.do",
		async : false,
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(d) {
			if (d.success) {
				var info = d.info;
				if (info != undefined && info != "") {
					alert(info);
				} else {
					$('#userId').val(d.userId);
					$('#userName').val(d.userName);
					doOperator("selectReceiversList", "");
					doOperator("receiversList", "");
					doOperator("visitorList", "");
					doOperator("emergencyContactList", "");
					if ($('#isPhysical').val() == "true") {
						doOperator("addressList", "");
					}
				}
			} else {
				alert(d.msg);
			}
		}
	});
}

//修改用户信息
function showEditUser(userId) {
	var $editUser = $('#edituser');
	$editUser.load($editUser.attr('href'), {
		'userId' : userId
	}, function() {
		$editUser.dialog( {
			title : "修改用户信息",
			width : 500,
			modal : true
		})
	});
}

function window_callcenter(param) {
	window
			.open(param);
}

function doShowChannelDialog() {
	var $div = $('#channelRegisterDiv');
	$div.load($div.attr('href'), function() {
		$div.dialog( {
			title : "用户渠道注册",
			width : 500,
			modal : true
		})
	});
}

function doChannelRegist(url) {
	var mobile = $.trim($('#channelMobile').val());
	if (mobile == '') {
		alert("请输入手机号！");
		return;
	}
	if (mobile.length != 11) {
		alert("手机号必须为11位！");
		return;
	}
	var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	if (!MOBILE_REGX.test(mobile)) {
		alert("手机号格式不正确");
		return;
	}
	var m = $("#channelRegistForm").serialize();
	$.ajax( {
		type : 'POST',
		url : url,
		data : m,
		success : function(da) {
			var d = eval("(" + da + ")");
			if (d.success) {
				alert(d.info);
				$('#channelRegisterDiv').dialog("close");
			} else {
				alert(d.msg);
			}
		}
	});
}

function confirmUser(userId) {
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/doConfirmUser.do",
		async : false,
		data : {
			'userId' : userId
		},
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(d) {
			if (d.success) {
				$('#userName').val(
						$("input[name='userRadio']:checked").next().val());
				doOperator("selectReceiversList", "");
				doOperator("receiversList", "");
				doOperator("visitorList", "");
				doOperator("emergencyContactList", "");
				if ($('#isPhysical').val() == "true") {
					doOperator("addressList", "");
				}
			}
		}
	});
}

//添加订单备注信息
function addOrderMemo() {
	if ($.trim($('#memocontent').val()) == '') {
		alert("请输入备注内容！");
		return false;
	}
	if ($.trim($('#memocontent').val()).length > 2000) {
		alert("备注内容不能超过2000个字符！");
		return false;
	}
	var m = $("#orderMemoForm").serialize();
	$('#memoList').refresh(m);
}

//添加发票
function addOrderInvoice() {
	if ($('#hasInvoice').val() == "true") {
		alert("只能添加一张发票！");
		return;
	}
	if ($.trim($('#invoicedetail').val()) == '') {
		alert("请输入抬头！");
		return false;
	}
	if ($.trim($('#invoiceamount').val()) == '') {
		alert("请输入金额！");
		return false;
	}
	var m = $("#addInvoiceForm").serialize();
	$('#invoiceList').refresh(m);
	$('#addInvoiceDiv').dialog("close");
}

//添加地址
function addOrderAddress() {
	var m = $("#addAddressForm").serialize();
	if ($('#addProvince').val() == '' || $('#addCity').val() == ''
			|| $.trim($('#addAddress').val()) == '') {
		alert("地址不能为空！");
		return;
	}
	if ($.trim($('#addReceiverName').val()) == '') {
		alert("联系人不能为空！");
		return;
	}
	if ($.trim($('#addMobileNumber').val()) == '') {
		alert("联系电话不能为空！");
		return;
	}
	$('#addAddressDiv').dialog("close");
	$('#addressList').refresh(m);
}

//添加取票人
function addUsrReceiver() {
	var userId = $('#userId').val();
	if (userId == undefined || userId == "") {
		alert("请选择用户！");
		return;
	}
	var m = $("#addUsrReceiverForm").serialize();
	var id = $('#addUsrReceiverForm').getForm().id;
	if ($.trim($('#addReceiverName').val()) == '') {
		alert("姓名不能为空！");
		return;
	}
	
	
	var mobile = $('#addReceiverMobile').val();
	if ($.trim(mobile) == '') {
		alert("手机号不能为空！");
		return;
	}
	if (mobile.length != 11) {
		alert("手机号必须为11位！");
		return;
	}
	
	var addName =$('#addReceiverName').val();
	if(addName.length >20){
		alert("姓名不能超过20个字节！");
		return;
	}
	
	var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	if (!MOBILE_REGX.test(mobile)) {
		alert("手机号格式不正确");
		return;
	}
	if ($('#cardTypeRequired').val() == 'true' && $('#addReceivercardType').val() == '') {
		alert("证件类型不能为空！");
		return;
	}
	if (!validUsrReceiver("addReceiver")) {
		return;
	}
	$('#usrReceiverDiv').dialog("close");
	$('#selectReceiversList').refresh(m);
	if (id != undefined && id != "") {
		var $receiverObj = $('td[receiverId="receiver' + id + '"]');
		if ($receiverObj.size() > 0) {
			doOperator('receiversList', id);
		}
		var $visitorObj = $('td[receiverId="visitor' + id + '"]');
		if ($visitorObj.size() > 0) {
			doOperator('visitorList', id + '&update');
		}
		var $emergencyObj = $('td[receiverId="emergencyContact' + id + '"]');
		if ($emergencyObj.size() > 0) {
			doOperator('emergencyContactList', id);
		}
	}
}

//根据身份证类型显示需要填写的数据
function onCardTypeSelect(prex) {
	var cardType = $('#' + prex + 'cardType').val();
	if (cardType != "") {
		if (cardType == 'CUSTOMER_SERVICE_ADVICE') {
			$('#' + prex + 'cardNumSpan').hide();
			$('#' + prex + 'birthdaySpan').hide();
		} else if (cardType == 'ERTONG') {
			$('#' + prex + 'birthdaySpan').show();
			$('#' + prex + 'cardNumSpan').hide();
		} else {
			$('#' + prex + 'cardNumSpan').show();
			$('#' + prex + 'birthdaySpan').show();
		}
		if(cardType != 'ID_CARD') {
			$('#' + prex + 'genderSpan').show();
		} else {
			$('#' + prex + 'genderSpan').hide();
		}
	} else {
		$('#' + prex + 'cardNumSpan').hide();
		$('#' + prex + 'birthdaySpan').hide();
		$('#' + prex + 'genderSpan').hide();
	}
}

//验证游玩人信息
function validUsrReceiver(prex) {
	var cardType = $('#' + prex + 'cardType').val();
	var cardNum = $.trim($('#' + prex + 'cardNum').val());
	var birthday = $.trim($('#' + prex + 'birthday').val());
	if (cardType != "") {
		if (cardType == 'CUSTOMER_SERVICE_ADVICE') {
		} else {
			if (cardType != 'ERTONG' && cardNum == '') {
				alert("证件号不能为空！");
				return false;
			}
			//验证身份证号
			if (cardType == 'ID_CARD') {
				var City = {
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
				if (City[parseInt(cardNum.substr(0, 2))] == null) {
					alert("错误的身份证号码！");
					return false;
				}
				sBirthday = cardNum.substr(6, 4) + "-"+ Number(cardNum.substr(10, 2)) + "-"+ Number(cardNum.substr(12, 2));
				var d = new Date(sBirthday.replace(/-/g, "/"));
				if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
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
			}
			
			if (birthday == '') {
				alert("必须输入出生日期！");
				return false;
			}
			var BRITHDAY_REGX = /^\d{4}-\d{1,2}-\d{1,2}$/;
			if (!BRITHDAY_REGX.test(birthday)) {
				alert("请按正确格式输入！");
				return;
			}
			if (cardType == 'ERTONG') {
				//验证年龄是否小于12周岁
				var birthdayArr = birthday.split("-");
				var birthYear = birthdayArr[0];
				var birthMonth = birthdayArr[1];
				var birthDay = birthdayArr[2];

				var curDate = new Date();
				var nowYear = curDate.getFullYear();
				var nowMonth = curDate.getMonth() + 1;
				var nowDay = curDate.getDate();
				var returnAge = 0;
				if (nowYear == birthYear) {
					returnAge = 0;//同年 则为0岁
				} else {
					var ageDiff = nowYear - birthYear; //年之差
					if (ageDiff > 0) {
						if (nowMonth == birthMonth) {
							var dayDiff = nowDay - birthDay;//日之差
							if (dayDiff < 0) {
								returnAge = ageDiff - 1;
							} else {
								returnAge = ageDiff;
							}
						} else {
							var monthDiff = nowMonth - birthMonth;//月之差
							if (monthDiff < 0) {
								returnAge = ageDiff - 1;
							} else {
								returnAge = ageDiff;
							}
						}
					} else {
						returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
					}
				}
				if (returnAge > 12) {
					alert("儿童年龄大于12岁，不能投保！");
					var receiverId = $('#toubaoReceiverId').val();
					$('#toubao_' + receiverId).attr('checked', false);
					return false;
				}
			}
		}
		if(cardType != 'ID_CARD') {
			if($("input[name$='.gender']:checked").length < 1) {
				alert("请选择性别！");
				return false;
			}
		}
	}
	return true;
}

//保存投保信息
function addTouBaoReceiver() {
	var $toubaoForm = $('#toubaoForm');
	if (!validUsrReceiver("")) {
		return;
	}
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/doSaveTouBao.do",
		async : false,
		data : $toubaoForm.serialize(),
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(d) {
			if (d.success) {
				var receiverId = $('#toubaoReceiverId').val();
				data = {
					id : receiverId + "&save"
				};
				$.ajax( {
					type : "POST",
					dataType : "json",
					url : "/super_back/phoneOrder/doSaveTouBao.do",
					async : false,
					data : data,
					timeout : 3000,
					error : function(a, b, c) {
						if (b == "timeout") {
							alert("请求超时");
						} else if (b == "error") {
							alert("请求出错");
						}
					},
					success : function(d) {
						doOperator('visitorList', "");
						var id = $("#toubaoReceiverId").val();
						if (id != undefined && id != "") {
							var $receiverObj = $('td[receiverId="receiver' + id + '"]');
							if ($receiverObj.size() > 0) {
								doOperator('receiversList', id);
							}
							var $emergencyObj = $('td[receiverId="emergencyContact' + id + '"]');
							if ($emergencyObj.size() > 0) {
								doOperator('emergencyContactList', id);
							}
						}
					}
				});
				$('#toubaoDiv').dialog("close");
			}
		}
	});
}

//修改投保信息
function updateTouBaoReceiver() {
	var $toubaoForm = $('#toubaoForm');
	if (!validUsrReceiver("")) {
		return;
	}
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/doSaveTouBao.do",
		async : false,
		data : $toubaoForm.serialize(),
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(d) {
			if (d.success) {
				doOperator('visitorList', "");
				$('#toubaoDiv').dialog("close");
				var id = $("#toubaoReceiverId").val();
				if (id != undefined && id != "") {
					var $receiverObj = $('td[receiverId="receiver' + id + '"]');
					if ($receiverObj.size() > 0) {
						doOperator('receiversList', id);
					}
					var $emergencyObj = $('td[receiverId="emergencyContact' + id + '"]');
					if ($emergencyObj.size() > 0) {
						doOperator('emergencyContactList', id);
					}
				}
			}
		}
	});
}

//操作前做校验
function beforeDoOperator(widget) {
	var receiverId = $('#receiverId').val();
	if(receiverId == undefined || receiverId == "") {
		alert("请先选择联系人！");
		return;
	}
	if(widget == "visitorList") {
		$.ajax( {
			type : "POST",
			dataType : "json",
			url : "/super_back/phoneOrder/checkTravellerInfoOptions.do",
			async : false,
			data : {'id' : receiverId},
			timeout : 3000,
			error : function(a, b, c) {
				if (b == "timeout") {
					alert("请求超时");
				} else if (b == "error") {
					alert("请求出错");
				}
			},
			success : function(d) {
				if (d.success) {
					doOperator(widget, receiverId);
				} else {
					alert(d.msg);
				}
			}
		});
	} else {
		doOperator(widget, receiverId);
	}
}

//做修改删除操作及数据
function doOperator(divId, id) {
	$('#' + divId).load($('#' + divId).attr('href'), {
		'id' : id
	});
}

//算总金额
function countAmount_2() {
	var amou = 0;
	$('span[name="amount_1"]').each(function() {
		amou += parseFloat($(this).text());
	});
	$('#amount_2').text(amou);
}
function checkResource() {
	var checked = $('#resourceConfirm').attr('checked');
	if (checked) {
		var confirm = window.confirm("您确定此资源已经资源审核（不会出现在资源审核列表）?");
		if (!confirm) {
			$('#resourceConfirm').attr('checked', false);
			return;
		}
	}
}

//后退操作
function goBack() {
	$('#indexForm').submit();
}

//保存请求操作(修改人数)，下单
function saveOperator(funName, data) {
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/" + funName + ".do",
		async : false,
		data : data,
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
			if (data.success) {
				if (funName == 'doSaveOrder') {
					alert("下单成功！");
				} else if (funName == 'doOperateProdBranchItemList') {
					var lastModifyTime = data.lastModifyTime;
					if(lastModifyTime != null && lastModifyTime != undefined && lastModifyTime != "") {
						$("#lastModifyTimeSpan").html("<b>最晚修改或取消时间：</b>" + lastModifyTime);
					} else {
						$("#lastModifyTimeSpan").html("");
					}
					//计算总金额
		doOperator("orderAmount", "");
		doOperator("businessCoupon", "");
	}
} else {
	alert(data.msg);
}
}
	});
}

//修改订购数量时的操作，包括加减按钮和输入文本框
function updateQuantityOperator(object) {
	var $parent = $(object).parent();
	var $countInput = $parent.find('input.countInput');
	if ($.trim($countInput.val()).length == 0) {
		alert("请先输入订购数量！");
		return;
	}
	var count = parseInt($countInput.val());
	var minimum = parseInt($parent.find('input[name="minimum"]').val());
	var maximum = parseInt($parent.find('input[name="maximum"]').val());
	var stock = parseInt($parent.find('input[name="stock"]').val());
	var obj = $(object).attr('class');
	var c = count;
	var c_RelationProduct = 0;
	var c_currentRelationProduct = 0;
	var itemType = $parent.find('input[name="itemType"]').val();
	//判断触发的是不是主产品数量
	if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
		c_currentRelationProduct =  parseInt($parent.find("[name=sumQuantity]").text());
	}
	//单击"-"
	if (obj == "reduce") {
		if (count <= 0) {
			alert("不能小于0！");
			$countInput.val(minimum);
			return;
		}
		if (count <= minimum) {
			alert("不能小于最小订购量！");
			$countInput.val(minimum);
			return;
		}
		c = count - 1;
		if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
			$("[title=saleNumTypeAll]").each(function(){
				$(this).val(parseInt($(this).val()) - parseInt(c_currentRelationProduct));  
			});
		}
		//单击"+"
	} else if (obj == "plus") {
		if (count >= maximum) {
			alert("不能大于最大订购量！");
			$countInput.val(maximum);
			return;
		}
		if (stock != -1 && count >= stock) {
			alert("不能大于库存！");
			$countInput.val(stock);
			return;
		}
		c = count + 1;
		if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
			$("[title=saleNumTypeAll]").each(function(){
				$(this).val(parseInt($(this).val()) + parseInt(c_currentRelationProduct));  
			});
		}
		//直接输入文本框
	} else {
		if (count < minimum) {
			alert("不能小于最小订购量！");
			$countInput.val(minimum);
			return;
		}
		if (count > maximum) {
			alert("不能大于最大订购量！");
			$countInput.val(maximum);
			return;
		}
		if (stock != -1 && count > stock) {
			alert("不能大于库存！");
			$countInput.val(stock);
			return;
		}
		if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
			$("[title=saleNumTypeAll]").each(function(){
				$(this).val(parseInt(c_currentRelationProduct) * c);  
			});
		}
	}
	$countInput.val(c);
	var sellPrice = parseInt($parent.find('input[name="sellPrice"]').val());
	var $amount_1 = $parent.parent().find('span[name="amount_1"]');
	//计算小计
	//触发的主产品时，保险类型为ALL附加产品也要进行重新累计
	if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
		$.each($.find('input[title="saleNumTypeAll"]'),function(index, domEle){
			$(domEle).parent().parent().find('span[name="amount_1"]').text(parseInt($(domEle).parent().find("input.countInput").val())*parseInt($(domEle).parent().find('input[name="sellPrice"]').val())/ 100);
		});
	}
	$amount_1.text(sellPrice * c / 100);
	
	//计算总计
	countAmount_2();
	//将修改后的数量保存到session
	var branchId = parseInt($parent.find('input[name="branchId"]').val());

	//统计所有变更的产品
	var dataAddtion = "";
	if(itemType=="branch" && $.find('input[title="saleNumTypeAll"]').length>0){
		$.each($.find('input[title="saleNumTypeAll"]'),function(index, domEle){
			dataAddtion = dataAddtion + ",{'id' : '"+$(domEle).parent().find('input[name="branchId"]').val()+"', 'quantity' : '"+$(domEle).parent().find("input.countInput").val()+"', 'itemType' : '"+$(domEle).parent().find('input[name="itemType"]').val()+"'}";
		});
	}
	
	var data = {'ids':"[{'id' :'"+branchId+"', 'quantity' : '"+c+"', 'itemType' : '"+itemType+"'}"+dataAddtion +"]"};
	
	
	saveOperator('doOperateProdBranchItemList', data);
}

//提交订单前检查数据
function checkOrder() {
	var userId = $('#userId').val();
	if (userId == undefined || userId == "") {
		alert("请选择用户！");
		return;
	}
	var testOrder = $('#testOrder').val();
	if (testOrder == "true") {
	} else {
		if($("input[name='userRadio']:checked").length < 1) {
			alert("请选择用户！");
			return;
		}
	}
	var hasQuantity = false;
	$('input[name="countInput"]').each(function() {
		if (parseInt($(this).val()) > 0) {
			hasQuantity = true;
			return false;
		}
	});
	if (!hasQuantity) {
		alert("没有选购产品！");
		return;
	}
	
	//销售产品取票人为必填项时，下单时需验证
	if($('#travellerCardNum').val() == "true"){
		if($('#hasCardNum').val() == "false"){
			alert("该产品的取票人证件号为必填项！");
			return;
		}
	}
	
	if ($('#hasReceiver').val() == "false") {
		alert("请选择取票人！");
		return;
	}
	
	//线路，除目的地自由行外必须选择紧急联系人 
	if ($('#productType').val() == 'ROUTE') {
		if ($('#subProductType').val() != 'FREENESS') {
			if ($('#hasEmergencyContact').val() == "false") {
				alert("请选择紧急联系人！");
				return;
			}
		}
	}
	var count1 = 0, count2 = 0;
	count2 = $('input[id^="toubao_"]:checked').size();
	if ($('input[id^="toubao_"]').size() < 1) {
		alert("请选择游客！");
		return;
	}
	//保险
	var subProductType = $('#subProductType').val();
	if (subProductType != 'INSURANCE') {
		//检验投保份数是否跟保险购买数量相同
		$('input[name="additional"]').each(function() {
			count1 += parseInt($(this).val());
		});
	} else {
		$('input[name="countInput"]').each(function() {
			count1 += parseInt($(this).val());
		});
	}
	if (count1 > count2) {
		alert("您购买了" + count1 + "份保险，请为" + count1 + "位游客投保！");
		return;
	} else if (count1 < count2) {
		alert("您只购买了" + count1 + "份保险，只能为" + count1 + "位游客投保！");
		return;
	}
	var fapiaoReceiverId, shitipiaoReceiverId;
	//检验发票
	if ($('#needInvoice').attr('checked')) {
		if ($('#hasInvoice').val() == "false") {
			alert("请添加发票信息！");
			return;
		} else {
			$fapiao = $('input[name="fapiaoRadio"]:checked');
			fapiaoReceiverId = $fapiao.val();
			var fapiaoAdd = $fapiao.size();
			if (fapiaoAdd < 1) {
				alert("请选择发票地址！");
				return;
			}
		}
	} else {
		$fapiao = $('input[name="fapiaoRadio"]:checked').attr('checked', false);
	}
	if ($('#isPhysical').val() == "true") {
		$shitipiao = $('input[name="shitipiaoRadio"]:checked');
		shitipiaoReceiverId = $shitipiao.val();
		var shitipiaoAdd = $shitipiao.size();
		if (shitipiaoAdd < 1) {
			alert("请选择实体票地址！");
			return;
		}
	}
	
	$('#checkOrder').attr('disabled',true); 
	$
			.ajax( {
				type : "POST",
				dataType : "json",
				url : "/super_back/phoneOrder/checkOrder.do",
				async : false,
				timeout : 3000,
				success : function(data) {
					$('#checkOrder').attr('disabled',false); 
					if (data.success) {
						var msg = '';
						if (data.stock != null && data.stock != undefined
								&& data.stock != "") {
							alert(data.stock);
							return false;
						}
						/*
						if (data.emergencyContact != null && data.emergencyContact != undefined && data.emergencyContact != "") {
							alert(data.emergencyContact);
							return false;
						}*/
						if (data.resourceConfirm == 'true') {
							if (!$('#resourceConfirm').attr('checked')) {
								var confirm = window
										.confirm("订单中有资源需确认，是否继续下单？");
								if (!confirm) {
									$('#resourceConfirm')
											.attr('checked', false);
									return;
								}
							}
						}
						if (data.perOrder == 'true') {
							msg += '\n订单中有提前打款产品，您已核实了吗?';
						}
						if (msg != '') {
							if (!window.confirm(msg))
								return;
						}
						
						if(typeof(data.needPrePay)!="undefined"&&data.needPrePay){
							if(!window.confirm("此订单已过最晚取消时间，只能通过预授权方式支付，一旦资源审核通过后此订单不退不改")){
								return;
							}
						}
						if(typeof(data.confirmMsg)!="undefined"&& data.confirmMsg!= "") {
							if(!window.confirm(data.confirmMsg)){
								return;
							}
						}
						var d = {
							'paymentWaitTime' : $('#paymentWaitTime').val(),
							'needInvoice' : $('#needInvoice').attr('checked'),
							'resourceConfirm' : $('#resourceConfirm').attr(
									'checked'),
							'needRedail' : $('#needRedail').attr('checked'),
							'fapiaoReceiverId' : fapiaoReceiverId,
							'shitipiaoReceiverId' : shitipiaoReceiverId
						};
						var orderChannel = $('#orderChannel').val();
						if (orderChannel != undefined) {
							d.orderChannel = orderChannel;
						}
						var $saveOrderSucc = $('#saveOrderSucc');
						$saveOrderSucc
								.load(
										$saveOrderSucc.attr('href'),
										d,
										function() {
											$saveOrderSucc
													.dialog( {
														title : "下单完成",
														width : 1000,
														modal : true,
														close : function(event,
																url) {
															if($saveOrderSucc.find("div.checkerror").length==0){
																if (orderChannel != undefined) {																
																	var testOrder = $(
																			'#testOrder')
																			.val();
																	if (testOrder == "true") {
																		window.location.href = "/super_back/testOrder/index.do";
																	} else {
																		window.location.href = "/super_back/phoneOrder/index.do";
																	}
																} else {
																	window.location.href = "/super_back/ordChannel/index.do";
																}
															}
														}
													})
										});
					} else {
						alert(data.msg);
					}
				}
			});
}

function beforeDoShowDialog(divName, data, title) {
	var userId = $('#userId').val();
	if (userId == undefined || userId == "") {
		alert("请选择用户！");
		return;
	}
	var testOrder = $('#testOrder').val();
	if (testOrder == "true") {
	} else {
		if($("input[name='userRadio']:checked").length < 1) {
			alert("请选择用户！");
			return;
		}
	}
	doShowDialog(divName, data, title);
}

//弹出悬浮框
function doShowDialog(divName, data, title) {
	var $div = $('#' + divName);
	$div.load('/super_back/phoneOrder/doShowDialog.do', data, function() {
		$div.dialog( {
			title : title,
			width : 1000,
			modal : true
		});
	});
}

function doModifyReceiver() {
	var receiverId = $('#receiverId').val();
	if (receiverId == '') {
		alert("请先选择联系人！");
		return;
	}
	doShowDialog('usrReceiverDiv', {
		'to' : 'add_receiver',
		'id' : receiverId + '&receiver',
		'productId' : $('#productId').val()
	}, '修改联系人');
}

var $time_price_dlg = null;
function loadLog(param) {
	if ($time_price_dlg == null) {
		$time_price_dlg = $("<div style='display:none' class='time_price_dlg_div'></div>");
		$time_price_dlg.appendTo($("body"));
	}

	$time_price_dlg.load('/super_back/common/timePrice.do', param, function() {
		$time_price_dlg.dialog( {
			title : "时间价格表",
			width : 650,
			modal : true
		});
	});
}

$(function() {
	//限制文本框输入
	$('#invoiceamount,#addReceiverNum,input.countInput').bind(
			'keyup',
			function() {
				var v = $.trim($(this).val());
				if (v.length > 0) {
					if (isNaN(v)) {
						alert("只能输入数字！");
						if ($(this).attr('class') == 'countInput') {
							$(this).val(
									$(this).parent().find(
											'input[name="minimum"]').val());
						} else {
							$(this).val('');
						}
						$(this).focus();
						return;
					}
					if (v.indexOf(".") > 0) {
						alert("只能输入整数！");
						if ($(this).attr('class') == 'countInput') {
							$(this).val(
									$(this).parent().find(
											'input[name="minimum"]').val());
						} else {
							$(this).val('');
						}
						$(this).focus();
						return;
					}
					if ($(this).attr('class') == 'countInput') {
						updateQuantityOperator(this);
					}
				}
			});

	//修改订购数量时的操作
	$('input.reduce,input.plus').bind('click', function() {
		updateQuantityOperator(this);
	});
	$('input[id^="toubao_"]').live('click', function() {
		var checked = $(this).attr('checked');
		var data = {};
		var receiverId = $(this).attr('receiverId');
		var hasInfo = true;
		if (checked) {
			//没有身份信息或者身份证类型不是身份证(需要选择性别)，则弹出投保信息框
			var cardT = $(this).attr('cardType');
			var gender = $(this).attr('gender');
			if (cardT == "") {
				hasInfo = false;
				doShowDialog('toubaoDiv', {
					'to' : 'toubao',
					'usrReceivers.receiverId' : receiverId,
					'id': receiverId + "&add"
				}, '新增投保信息');
				return false;
			} else {
				if(cardT != 'ID_CARD' && gender == "") {
					hasInfo = false;
					doShowDialog('toubaoDiv', {'to': 'toubao', 'id': receiverId + "&add"}, '新增投保信息');
					return false;
				}
			}
			//如果该人已经有身份信息，则直接保存投保
			data = {
				id : receiverId + "&save"
			};
		} else {
			data = {
				id : receiverId + "&delete"
			};
		}
		if (hasInfo) {
			$.ajax( {
				type : "POST",
				dataType : "json",
				url : "/super_back/phoneOrder/doSaveTouBao.do",
				async : false,
				data : data,
				timeout : 3000,
				error : function(a, b, c) {
					if (b == "timeout") {
						alert("请求超时");
					} else if (b == "error") {
						alert("请求出错");
					}
				},
				success : function(d) {
				}
			});
		}
	});
	//设置省市联动
	$("#addProvince").live('change', function() {
		var val = $(this).val();
		var $selectCity = $("#addCity");
		$selectCity.empty();
		if ($.trim(val) != '') {
			$.post("/super_back/phoneOrder/citys.do", {
				"id" : val
			}, function(dt) {
				var data = eval("(" + dt + ")");
				for ( var i = 0; i < data.list.length; i++) {
					var city = data.list[i];
					var $option = $("<option/>");
					$option.val(city.cityId);
					$option.text(city.cityName);
					$selectCity.append($option);
				}
			});
		} else {
			var $option = $("<option/>");
			$option.val("").text("请选择");
			$selectcity.append($option);
		}
	});

	//自动验证身份证号码，填充出生日期
	$('input[id$="cardNum"]').live(
			'blur',
			function() {
				var cardType, $birthday;
				if ($(this).attr("id").indexOf("addReceiver") != -1) {
					cardType = $('#addReceivercardType').val();
					$birthday = $('#addReceiverbirthday');
				} else {
					cardType = $('#cardType').val();
					$birthday = $('#birthday');
				}
				var cardNum = $.trim($(this).val());
				if (cardType == 'ID_CARD') {
					if (cardNum.length == 0) {
						return false;
					}
					//验证身份证号
					if (!(cardNum.length == 15 || cardNum.length == 18)) {
						return false;
					}
					var bir;
					if (cardNum.length == 18) {
						bir = cardNum.substring(6, 10) + "-"
								+ cardNum.substring(10, 12) + "-"
								+ cardNum.substring(12, 14);
					}
					if (cardNum.length == 15) {
						bir = cardNum.substring(6, 8) + "-"
								+ cardNum.substring(8, 10) + "-"
								+ cardNum.substring(10, 12);
					}
					$birthday.val(bir);
				}
			});

	//房型提示信息
	$("a.description").live(
			"mouseover",
			function() {
				var $this = $(this);
				var $div = $this.parent().find("div.description");
				var pos = $this.offset();
				$div.css("text-align", "left").css("left", (pos.left) + "px")
						.css("top", (pos.top + 20)).show();
			});
	$("a.description").live("mouseout", function() {
		var $this = $(this);
		var $div = $this.parent().find("div.description");
		$div.hide();
	});

	//初始化加载
	$(document).ready(
			function() {
				$('#orderChannel').children("option[value='BACKEND']").attr(
						'selected', true);
				$('#paymentWaitTime').children("option[value='60']").attr(
						'selected', true);
				if ($('#isPhysical').val() == "true") {
					$('.addressInformation').show();
				}
				var userId = $('#userId').val();
				var userName = $('#userName').val();
				if((userId != undefined && userId != "") && (userName != undefined && userName != "")) {
					queryUsr();
					$("input[name='userRadio'][value='" + userId + "']").attr('checked', true);
				}
				//初始化保险附加产品的数量及更新总价格
				countRelationProduct();
				
				//计算总计
				countAmount_2();
				
				//监控用户查询回车
				$('#userName').bind('keypress', function(e) {
					if(e.keyCode == 13) {
						if($.trim($(this).val()) == '') {
							alert("请输入用户名或者手机号码查询！");
							return false;
						} else {
							queryUsr();
						}
					}
				});
			});
});


function countRelationProduct() {
	var $parent = $(this).parent();
	var sum = 0;
	
	$("[name=sumQuantity]").each(function(){
		var count = parseInt($(this).parent().find('input.countInput').val());
		sum = parseInt(sum)+(parseInt($(this).text())*count);  
	});
	$("[title=saleNumTypeAll]").each(function(){
		$(this).val(sum);
		$(this).parent().parent().find('span[name="amount_1"]').text(parseInt($(this).parent().find('input[name="sellPrice"]').val())* sum / 100);
	});

}