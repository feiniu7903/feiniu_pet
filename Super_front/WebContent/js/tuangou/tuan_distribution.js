/**
 * 	团购分销前台JS
 * @author zenglei
 */
$(document).ready(function(){
    if($('.time-price-one').length>0){
        var $tp=$('.time-price-one');
        pid=$tp.attr('data-pid'),bid=$tp.attr('data-bid'),validEndTime=$tp.attr('validEndTime');
        pid=pid?pid:'';
        bid=bid?bid:'';
        $calFree=$('.calendar_free');
        $.ajax({
            url:"http://www.lvmama.com/product/tuanTimePriceTable.do",dataType:"html",async:true,data:{
                productId:pid,branchId:bid,validEndTime:validEndTime,r:Math.random()
            }
            ,success:function(data){
                $tp.append(data);
            }
            ,error:function(x,e,c){}
        }).complete(function(){
            var $tp=$('.time-price-one');
            pid=$tp.attr('data-pid'),bid=$tp.attr('data-bid');
            $('.search_pp_calendar_d_box').on('click',function(){
                var startDate=$(this).find('.search_pp_calendar_day_date').text();
                $("#yuyueDateId").html(startDate);
                $("input[name='buyInfo.visitTime']").val(startDate);
            });
            $calFree.live('click',function(e){
                var cn=e.target.className,$subTp=$(this).find('.search_pp_calendar_box');
                if(cn==='search_pp_cal_nextm_icon'){
                    $subTp.hide();
                    $(e.target).closest('.search_pp_calendar_box').next().show()
                }else if(cn==='search_pp_cal_nevm_icon'){
                    $subTp.hide();
                    $(e.target).closest('.search_pp_calendar_box').prev().show()
                }
            });
        });
        
        //此代码　目的在于返回上一步
        //初始化所有值
        $("input[name='buyInfo.visitTime']").val("");
        $(".oper-numbox").find(".op-number").val("1");
        $("input[name='verifycode']").val("");
        refreshCheckCode('image');
    }
    //券号验证
    $("#tuanNextId").click(function(){
    	if($("#couponCodeId").val() == ""){
    		error_tip(this,$("#couponCodeId"),"请输入券号");
    	}else if($("input[name='verifycode']").val() == ""){
    		error_tip(this,$("input[name='verifycode']"),"请输入验证码");
    	}else{
    		var formData=$("#tuanCouponFormId").serialize();
    		$.ajax({url:"/product/ajaxValidateTuanCoupon.do",data:formData, dataType:"json",
    			success:function(json){
    				if(json.info.success){
    					$("#tuanCouponFormId").submit();
    				}else{
    					error_tip($("#tuanNextId"),null,json.info.msg);
    					refreshCheckCode('image');
    				}
    			}
    		});
    	}
    	return false;
    });
    //详情页验证
    $("#tuanDetailsNextId").click(function(){
    	var flag = false;
    	var textInput=null;
    	var couponArrText=new Array(),k=0;
    	$(".buy-order-info").find(".couponInput").each(function(){
    		if(trim($(this).find(":text").val()) == ""){
    			textInput = $(this);
    			flag = true;
    			return false;//跳出each循环
    		}else{
    			couponArrText[k++]=$(this).find(":text").val();
    		}
    	});
    	//时间
    	if($("input[name='buyInfo.visitTime']").val()==""){
    		error_tip(this,null,"请选择出行日期");
    	}else if(flag){
    		error_tip(this,$(textInput).find(":text"),"请输入券号");
    	}else if(couponArrText.length != unique(couponArrText).length){
    		error_tip(this,null,"请勿输入相同的券码");
    	}else if($("input[name='verifycode']").val() == ""){
    		error_tip(this,$("input[name='verifycode']"),"请输入验证码");
    	}else{
    		var formData=$("#tuanDetailFormId").serialize();
    		//检验券码、以及验证码
    		$.ajax({url:"/product/ajaxValidateTuanCoupon.do",data:formData, dataType:"json",
    			success:function(json){
    				if(json.info.success){
    					 var flag = true;
    					 //检验库存、、
    					 var fromDataTwo = formData;
    					 if(formData.indexOf("tuanCouponLists") != -1){
    						 fromDataTwo = formData.substring(0,formData.indexOf("tuanCouponLists")-1);
    					 }
    					 $.ajax({
    			             type: "POST",async: false,url: "/buy/ajaxCheckSock.do",data: fromDataTwo,dataType: "json",
    			             success: function(data) {
    			                 if (!data.success) {
    			                	error_tip($("#tuanDetailsNextId"),null,data.msg);
    			                	refreshCheckCode('image');
    			                 	flag = false;
    			                 }
    			             }
    			         });
    					 if(flag){
    						 $('form').attr("action","/buy/distributionFill.do");
    			             $('form').submit();
    					 }
    				}else{
    					if(json.info.couponcode != null){
    						$(".buy-order-info").find(".couponInput").each(function(){
    				    		if($(this).find(":text").val() == json.info.couponcode){
    				    			$(this).find(":text").focus();
    				    		}
    				    	});
    					}
    					error_tip($("#tuanDetailsNextId"),null,json.info.msg);
    					refreshCheckCode('image');
    				}
    			}
    		});
    	}
    	return false;
    });
    $("#submitTuanOrderId").click(function(){
    	//检验该产品是否可售以及库存
    	var flag = true;
    	 $.ajax({
             type: "POST",async: false,url: "/buy/ajaxCheckSock.do",data: $("#buyTuanForm").serialize(),dataType: "json",
             success: function(data) {
                 if (!data.success) {
                 	alert(data.msg);
                 	flag = false;
                 }
             }
         });
    	 return flag;
    });
    //加减购买份数
    //减
    $(".oper-numbox").delegate(".J_reduce", "click", function() {
        var _number = updateOperator($(this).next().attr("id"),"miuns");
        $(this).next().val(_number);
        //券号
        changeCouponInput(_number);
    });
    //加
    $(".oper-numbox").delegate(".J_increase", "click", function() {
    	var _number =updateOperator($(this).prev().attr("id"),"add");
        $(this).prev().val(_number);
        //券号
        changeCouponInput(_number);
    });
    //文本框
    $(".oper-numbox").delegate(".op-number","blur",function(){
    	var _number =updateOperator($(this).attr("id"),"input");
    	$(this).val(_number);
    	 changeCouponInput(_number);
    });
});
function error_tip(elt,sel,msg){
	$(".distribution_error").remove();
    strHtml = '&nbsp;&nbsp;<span class="distribution_error" style="color:red">' + msg + '</span>';
    sel == null ? null : $(sel).focus();
    elt == null ? null : $(elt).after(strHtml);
}
function updateOperator(paramId,operator){
	var porintNum = 0;
	var miunsObj = $("#param"+paramId);
	var textNumObj = $("#"+miunsObj.attr("textNum"));
	var min = parseInt(miunsObj.attr("minAmt")) <= 0 ? 1 : parseInt(miunsObj.attr("minAmt"));
	var max = parseInt(miunsObj.attr("maxAmt")) >= 20 ? 20 : parseInt(miunsObj.attr("maxAmt"));
	var textNum = parseInt(textNumObj.val());
	if(operator=='miuns'){
		if (!checkMiuns(min,textNum)) {
			porintNum = min;
			alert("最小起订量为" + min);
		}else{
			if(operator=='input'){
				porintNum = min;
			}else{
				porintNum = (textNum-1);
				porintNum = porintNum>0?porintNum:0;
			}
		}
		
	}
	if(operator=='add'){
		if (!checkAdd(textNum,max)) {
			porintNum = max;
			alert("最大订量为" + max);
		}else{
			if(operator=='input'){
				porintNum = max;
			}else{
				porintNum = textNum+1;
			}
		}
	} 
	if(operator=='input'){
		 if(textNum==max){
			porintNum = textNum;
		}else if (!checkMiuns(min,textNum)&&min!=textNum) {
			porintNum = min;
			alert("最小订量为" + min);
		}else if (!checkAdd(textNum,max)) {
			porintNum = max;
			alert("最大订量为" + max);
		}else{
			 porintNum = textNum;
		}
	} 
	porintNum <= min ? $(".oper-numbox").find(".J_reduce").addClass('op-disable-reduce') : $(".oper-numbox").find(".J_reduce").removeClass('op-disable-reduce');
	porintNum >= max ? $(".oper-numbox").find(".J_increase").addClass('op-disable-reduce') : $(".oper-numbox").find(".J_increase").removeClass('op-disable-reduce');
	return porintNum;
}
function checkMiuns(min,textNum){
	if (textNum >-1) {
		if (textNum <= min) {
		} else {
			 return true;
		}
	} 
	return false;
}
function checkAdd(textNum,max){
	if (textNum >-1) {
		if(max==-1){
			return true;
		}else if (textNum >= max) {
			return false;
		} else {
			return true;
		}
	} 
	return false;
}
function changeCouponInput(_number){
	var couponSize = $(".buy-order-info").find(".couponInput").size();
	if(_number > couponSize){
		for(var i = couponSize ; i < _number ; i++){
			var couponInputFlag = '<dl class="couponInput"> ' +
                    ' <dt>券&nbsp;&nbsp;&nbsp;号：</dt> ' +
                    ' <dd><input type="text" class="buy-ticket-num" name="tuanCouponLists['+i+'].distributionCouponCode" onkeyup="value=value.replace(/[^\\d\\w]/g,\'\');"></dd> '+
                    ' </dl> ';
			$(".buy-order-info").find(".couponInput:last").after(couponInputFlag);
		}
	}else if(_number < couponSize){
		for(var i = _number ; i < couponSize ; i++){
			$(".buy-order-info").find(".couponInput:last").remove();
		}
	}
}
//切换验证码
function refreshCheckCode(s) {
    var elt = document.getElementById(s);
    elt.src = elt.src + "?_=" + (new Date).getTime();
}
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
function unique(_this){
	var a = {}; 
	for(var i=0; i<_this.length; i++){
		if(typeof a[_this[i]] == "undefined"){
			a[_this[i]] = 1;
		}	
	}
    _this.length = 0;
    for(var i in a){
    	_this[_this.length] = i;
    }
    return _this;
}