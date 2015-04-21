 
function onclickServiceType(serviceTypeId,serviceTypeName){
	 $("#serviceType").val(serviceTypeName);
	 getSecondChildConnType(serviceTypeId);
	 if($("#isCallBack").val()=='true'){
		 if(serviceTypeName=="产品咨询"){
			 $("tr.productClass").show();
		 }else{
			 $("tr.productClass").hide();
		 }
	 }
	 $("#subServiceType").val("");
	 $("#callTypeId").val("");
}

function onclickSubServiceType(subServiceTypeId,subServiceTypeName){
	$("#callTypeId").val(subServiceTypeId);
	$("#subServiceType").val(subServiceTypeName);
	if(($("#memo").val()==null || $.trim($("#memo").val())=="") && ("海外酒店"==$.trim(subServiceTypeName) || "国际机票"==$.trim(subServiceTypeName))){
		$("#memo").val("转接供应商");
	}
}

function verifyUser(){
	var userName = document.getElementById("userNameLeft").value;
	var callerid = document.getElementById("calleridLeft").value;
	var callerEmail = document.getElementById("callerEmailLeft").value;
	var memberShipCard = document.getElementById("memberShipCardLeft").value;
	if(userName ==""&&callerid ==""&&callerEmail ==""&&memberShipCard ==""){
		alert("请至少填写一个查询条件进行核实账号！");
		return false;
	} 
	$("#userFrm").submit();
}

function doRegistUser() {
	$("#IDBtnRegistUser").attr("disabled",true);
	var mobile = $.trim($("#calleridLeft").val());
	if (mobile == '') {
		alert("请输入手机号！");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	if (mobile.length != 11) {
		alert("手机号必须为11位！");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	if (!MOBILE_REGX.test(mobile)) {
		alert("手机号格式不正确");
		$("#IDBtnRegistUser").attr("disabled",false);
		return false;
	}
	$("#moblieNumber").val($("#calleridLeft").val());
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/saveUsrUsers.do";
	usrInfofrm.submit();
	$("#IDBtnRegistUser").attr("disabled",false);
}

function btn_updateBaseUser() {
	if($("#callUserInfo_userId").val()==""){
		alert("请先核实用户账号信息,在进行基本修改功能！"); 
		return false;
	}
	var usrInfofrm=document.getElementById("usrInfofrm");
	usrInfofrm.action=ctx+"/call/updateUsersInfo.do";
	usrInfofrm.submit();
}

var getSecondChildConnType = function (callTypeId) {
	$.ajax( {
		url : ctx + "/callCenter/getChildConnType.do",
		data : "connType.parentId=" + callTypeId,
		type: "POST",
		success : function(result) {
			var childConnType = "";
			for(var i = 0 ; i < result.connTypeList.length; i ++) {
				var connType = result.connTypeList[i];
				childConnType += '<span class="tab1-span"><input type="radio" name="secondCallType" onclick="onclickSubServiceType('+connType.callTypeId+',\''+connType.callTypeName+'\')" value="'+connType.callTypeId+'">'+connType.callTypeName+'</span>';
			}
			childConnType += '<br/>';
			$('#secondCallTypePanel').html(childConnType); 
		}
	});
};

function selectProductServer(productType,prodType,subProductType){
	var $div=$("#productTypeForm");
	var serviceTypeName=$("#serviceType").val();
	if(serviceTypeName=="产品咨询"){
		if($div.find("div.div").length>0){
			var $content=$div.find("div.div");
			$content.appendTo($("#productTypeContainer"));
		}
		$("#"+productType).show().appendTo($div);
		$("#productTypeContainer").find("div.div").hide();
		if(productType!='visa'&&productType!='train'){
			$("#other").show();
		}else{
			$("#other").hide();
		}
		if(productType=='route'){		
			if(subProductType=='GROUP_FOREIGN'||subProductType=='FREENESS_FOREIGN'){
				$("select[name=connRecord.productZone]").html($("#zone_foreign").html());
			}else{
				$("select[name=connRecord.productZone]").html($("#zone_province").html());
			}
			$("#zone").show();
		}else{
			$("#zone").hide();
		}
		
		//非酒店的时候增加产品助选
		if(productType != 'hotel'){
			$("#productSuggest").jsonSuggest({
				url:"/super_back/prod/searchProductJSON.do",
				maxResults: 10,
				width:300,
				emptyKeyup:false,		
				param:["#currentProductType","#currentSubProductType","#useType"],
				minCharacters:1,
				onSelect:function(item){					
					$("#productId").val(item.id);
					if($.trim(item.id)!==''){
						loadProductInfo(item.id);
					}
				}
			});
			$("#productSuggestSpan").show();
			//当选择酒店的时候，不助选,去掉这个条件
		}else{
			//去掉产品
			$("#productSuggestSpan").hide();
		}
	}
	$("#currentProductType").val(prodType);
	$("#currentSubProductType").val(subProductType);
	$("#productSuggest").val("");
	$("#productId").val("");
}

function bindJsonSuggest($input){
	$input.jsonSuggest({
		url:"/super_back/prod/searchPlace.do",
		maxResults: 20,
		minCharacters:1,
		onSelect:function(item){
			var name=$input.attr("iid");
			$("#"+name).val(item.text);
		}
	});
}

$(function(){
	$("input[place]").each(function(){
		bindJsonSuggest($(this));		
	});
	
	function loadProductInfo(pid){		
		$.post("/super_back/prod/getDetailJSON.do",{"productId":pid},function(data){
			if(data.success){
				$("#filialeNameSelect").find("option[value="+data.filialeName+"]").attr("selected",true);
				if($("#productPanel input[place=fromPlace]").length>0){
					var $place=$("#productPanel input[place=fromPlace]");
					$place.val(data.fromPlaceName);
					$("#"+$place.attr("iid")).val(data.fromPlaceName);
				}
				
				if($("#productPanel input[place=toPlace]").length>0){
					var $place=$("#productPanel input[place=toPlace]");
					$place.val(data.toPlaceName);
					$("#"+$place.attr("iid")).val(data.toPlaceName);
				}
				if(typeof(data.day)!=undefined&&typeof(data.subProductType)!=undefined){
					if($("#productPanel input[name=connRecord.day]").length>0){
						if(data.subProductType!='SINGLE_ROOM'){
							$("#productPanel input[name=connRecord.day]").val(data.day);
						}else if(data.subProductType=='SINGLE_ROOM'){
							var visitTime=$("#productPanel input[name=connRecord.visitTime]").val();
							var leaveTime=$("#productPanel input[name=leaveTime]").val();
							if($.trim(visitTime)!=''&&$.trim(leaveTime)!=''){
								var date1=new Date(Date.parse(visitTime.replace(/-/g,   "/")));
								var date2=new Date(Date.parse(leaveTime.replace(/-/g,   "/")));
								var date3=date2.getTime()-date1.getTime();  //时间差的毫秒数  
								  
								//计算出相差天数  
								var days=Math.floor(date3/(24*3600*1000));
								$("#productPanel input[name=connRecord.day]").val(days);
							}
						}
					}
				}
				if(typeof(data.productZone)!=undefined&&$("input[name=connRecord.productZone]").length>0){
					$("input[name=connRecord.productZone]").find("option[tt="+data.productZone+"]").attr("selected",true);
				}
			}
		},"json");
	}
	
	$("input.date").datepicker({dateFormat:'yy-mm-dd',
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true,
		selectOtherMonths: true,
		buttonImageOnly: true
	});	
	
	$(document).ready(function(){
		var userId=$("#userNo").val();
		if($.trim(userId)!=''){
			$.post("/super_back/call/queryOrder.do",{"userId":userId},function(data){
				if(typeof(data.orderInfoMap)!=='undefined'){
					$("#finishedOrdersCountSpan").text(data.orderInfoMap.finishedOrdersCount);
					$("#finishedOrdersAmountSpan").text(data.orderInfoMap.finishedOrdersAmount);
					
					var len=data.orderInfoMap.orderList.length;
					if(len>0){
						var body='';
						for(var i=0;i<len;i++){
							var order=data.orderInfoMap.orderList[i];
							body+='<TR class="tab1-cc-tr'+(i%2)+'">	'+
							'	<TD align="left" valign="middle">										'+
							'	<input type="radio" name="orderchecked" data-orderId=\''+order.orderId+'\' data-mobilePhoneNo=\''+order.contactMobileNo+'\'/>'+
							'		<a href="javascript:showDetailDiv(\'historyDiv\',\''+order.orderId+'\');">'+order.orderId +'</a>'+
							'	</TD>																			'+
							'	<TD align="left" valign="middle">												'+
									order.zhCreateTime						  									 +
							'	</TD>																			'+
							'	<TD align="left" valign="middle">												'+
							 		order.mainProduct.productName												 +
							'	</TD>																			'+
							'	<TD align="left" valign="middle">												'+
							        order.mainProduct.quantity                                                   +
							'	</TD>																			'+
							'	<TD align="left" valign="middle">												'+
							        order.actualPayYuan															 +
							'	</TD>																			'+
							' </TR>';
						}
						$("#orderListId").html(body);						
					}
				}
			},"json");
			
			//新订单列表
			var url = "/vst_order/ord/order/queryOrderForLVCC.do?callback=?";
			$.getJSON(url,
					{"userId":userId},
					function(data){
						if(typeof(data)!=='undefined'){
							$("#finishedOrdersCountSpanNew").text(data.completeOrderNum);
							$("#finishedOrdersAmountSpanNew").text(data.completeOrderMoney);
							var len=data.orderList.length;
							if(len>0){
								var body='';
								for(var i=0;i<len;i++){
									var order=data.orderList[i];
									body+='<TR class="tab1-cc-tr'+(i%2)+'">	'+
									'	<TD align="left" valign="middle">										'+
									'	<input type="radio" name="orderchecked" data-orderId=\''+order.orderId+'\' data-mobilePhoneNo=\''+order.contactMobile+'\'/>'+
									'		<a title="点击查看订单详情" href="/vst_order/order/orderStatusManage/showOrderStatusManage.do?orderId='+order.orderId+'" target="_blank">'+order.orderId +'</a>'+
									'	</TD>																			'+
									'	<TD align="left" valign="middle">												'+
											order.orderTime						  									 +
									'	</TD>																			'+
									'	<TD align="left" valign="middle">												'+
									 		order.productName												 +
									'	</TD>																			'+
									'	<TD align="left" valign="middle">												'+
									        order.buyCount                                                   +
									'	</TD>																			'+
									'	<TD align="left" valign="middle">												'+
									        order.orderMoney															 +
									'	</TD>																			'+
									' </TR>';
								}
								$("#newOrderListId").html(body);						
							}
						}
			});
		}
		initPopSmsForm();
	});

	var options = {
			target: '#orderFeedBackList',
			beforeSubmit: function () {
				return checkForm();
			},
			success: function () {
				alert('添加成功！');
			//发送电话号码到转接平台
			send();	
			}
		}; 
		
		$('#feedBackForm').ajaxForm(options);
		$('#findFeedBackForm').ajaxForm({beforeSubmit:function() {$('#orderFeedBackList').html('请稍等，正在查找......');},target: '#orderFeedBackList'});
		$('#feedBackForm input,#feedBackForm textarea').change(function () {
			$('#feedBackFormSubmitButton').unbind('click');
			$('#feedBackFormSubmitButton').bind('click',function() {
				$('#feedBackFormSubmitButton').unbind('click');
				$('#feedBackForm').submit();
			});
		});
});

function send() {
	if($.trim($("#serviceType").val())=='转接供应商'){
		//var callNo = "4001201340";
		//海外酒店业务，由于合作方booking电话变更，我方要求更换。
		//由原来的4001201340 ，改为 4008822810。
		var callNo = "4008822810";
		
		if($.trim($("#subServiceType").val())=='国际机票'){
			callNo = "13439635700";
			if(new Date().getHours()>=9 && new Date().getHours()<18){
				callNo = "4006656685";
			}
		} else if($.trim($("#subServiceType").val())=='手机端酒店') {
			callNo = "4006690230";
		}
		var url= $("#ipccUrl").val() + "/default.jsp?supplierphoneno="+callNo;
		window.location.href=url;
	}
}

var checkForm = function () {
	if($.trim($("#serviceType").val())==''){
		alert("服务类型必选");
		return false;
	} else {
		if($.trim($("#serviceType").val())=='产品咨询'){
			if($.trim($("#currentProductType").val()) == '') {
				alert("产品类型必选");
				return false;
			}
		}
	}
	if($.trim($("#subServiceType").val())==''){
		alert("服务项必选");
		return false;
	}
	return true;
};

function showDetailDiv(divName, orderId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"orderId":orderId});
}

function init(){
	var templateId=$("#sms_list").val();
	if(templateId==null||templateId==""){
		$("#sms_content").val("");
		return;
	}
	var orderId=$("#sms_order_id").val();
	$.ajax({
	   type: "get",
	   url: ctx + "/callCenter/getSmsTemplateContent.do?templateId="+templateId+"&orderId="+orderId,
	   dataType: "json",
	   success: function(data){
	     if(data.result=="success"){
	    	 $("#sms_content").val(data.msg);
	     }else{
	    	 $("#sms_content").val("");
	    	 alert(data.msg);
	     }
	   },
	   error:function(){
        alert('网络请求错误，请稍后再试');
       }
	});
	
}

function initPopSmsForm(){
	var smsFormDiv=$("#sms_form_container");
	//初始化异步表单
	$('#send_sms_form').ajaxForm({ 
        dataType:  'json',
        error:function(){
        	alert('网络请求错误，请稍后再试');
        },
        beforeSubmit:function(){
        	var mobile = $.trim($("#sms_mobilePhoneNo").val());
        	var smsTemplateId=$("#sms_list").val();
        	if (mobile =="") {
        		alert("请输入手机号！");
        		return false;
        	}
        	if (!/^13[0-9]{9}|15[012356789][0-9]{8}|18[01256789][0-9]{8}|147[0-9]{8}$/.test(mobile)) {
        		alert("请输入正确的手机格式！");
        		return false;
        	}
        	if(smsTemplateId==""){
        		alert("请选择短信模板！");
        		return false;
        	}
        	$("#submit_form_btn").attr("disabled",true);
        },
        success: function(data){
        	$("#submit_form_btn").attr("disabled",false);
        	alert(data.msg);
        	var userId=$("#user_id").val();
        	var mobile = $.trim($("#sms_mobilePhoneNo").val());
        	location.href=ctx +'/call/callCenter.do?callerid='+mobile+"&userId="+userId;
        } 
    }); 
	
	//订单选择
	$("input[name='orderchecked']").live('click', function() {
		var orderId=$(this).attr("data-orderId");
		var mobilePhoneNo=$(this).attr("data-mobilePhoneNo");
		$("#sms_order_id").val(orderId);
		$("#sms_mobilePhoneNo").val(mobilePhoneNo);
		
		//渲染模板
		init();
	});
	
	//弹出层
	$("#sms_pop_btn").click(function(){
		if($("#sms_order_id").val()==""){//手机号码，未勾选订单号，取来电弹屏上的会员手机号码
			$("#sms_mobilePhoneNo").val($("#calleridLeft").val());
		};
		$(smsFormDiv).dialog({
			title:"短信服务",
			modal: false,
		    height:380,
		    width:460
		});
	});
	
	//新订单的短信弹出层
	$("#sms_pop_btn_new").click(function(){
		if($("#sms_order_id").val()==""){//手机号码，未勾选订单号，取来电弹屏上的会员手机号码
			$("#sms_mobilePhoneNo").val($("#calleridLeft").val());
		};
		$(smsFormDiv).dialog({
			title:"短信服务",
			modal: false,
		    height:380,
		    width:460
		});
	});
	
	//短信模板类型事件
	$("#sms_type_list").change( function() {
		var type=$(this).val();
		$.getJSON( ctx + "/callCenter/getSmsTemplatesByType.do?templateType="+type, function(data){
		  $("#sms_list").empty();
		  var dopt="<option value='' selected='selected' >--请选择模板--</option>";
		  $("#sms_list").append(dopt);
		  $.each(data, function(i,item){
		    var opt="<option value='"+item.id+"' >"+item.name+"</option>";
		    $("#sms_list").append(opt);
		  });
		  $("#sms_list").show();
		  $("#sms_content").val("");
		});
	});
	
	//短信内容拉取
	$("#sms_list").change( function() {
		init();
	});
}

//更新用户状态
//罗俊说去掉
/**
function updateUserStatus(obj){
	var userStatus = $(obj).val();
	var url = "/vst_order/ord/order/updateUserStatus.do?callback=?";
	$.getJSON(url,
			{"userStatus":userStatus},
			function(res){
				if(typeof(res)!=='undefined'){
					var data=res.result;
					if(data == "success"){
						if(userStatus=="ONLINE"){
							alert("您现在可以接单!");
							$("#onlineTd").css("background-color","green");
							$("#busyTd").css("background-color","white");
							$("#offlineTd").css("background-color","white");
						}else if(userStatus=="BUSY"){
							alert("您现在可以接少量的单!");
							$("#onlineTd").css("background-color","white");
							$("#busyTd").css("background-color","green");
							$("#offlineTd").css("background-color","white");
						}else{
							alert("您现在不可接单!");
							$("#onlineTd").css("background-color","white");
							$("#busyTd").css("background-color","white");
							$("#offlineTd").css("background-color","green");
						}
					}else{
						alert("网络连接异常,请重试!");
					}
				}else{
					alert("网络连接异常,请重试!");
				}
			});
}
**/

//检测当前登录用户的接单状态，然后页面选中
//罗俊说去掉
/**
$(function(){
	$("#user_status_online").attr("checked","");
	$("#user_status_busy").attr("checked","");
	$("#user_status_offline").attr("checked","");
	var url = "/vst_order/ord/order/checkUserStatus.do?callback=?";
	$.getJSON(url,
			function(res){
				if(typeof(res)!=='undefined'){
					var data=res.result;
					if(data == "ONLINE"){
						$("#user_status_online").attr("checked","checked");
						$("#onlineTd").css("background-color","green");
						$("#busyTd").css("background-color","white");
						$("#offlineTd").css("background-color","white");
					}else if(data == "BUSY"){
						$("#user_status_busy").attr("checked","checked");
						$("#onlineTd").css("background-color","white");
						$("#busyTd").css("background-color","green");
						$("#offlineTd").css("background-color","white");
					}else{
						$("#user_status_offline").attr("checked","checked");
						$("#onlineTd").css("background-color","white");
						$("#busyTd").css("background-color","white");
						$("#offlineTd").css("background-color","green");
					}
				}
			});
});
**/
