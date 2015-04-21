function checkMobile(mobile){
	var format = "^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}$";
	var myReg = new RegExp(format);
	return myReg.test(mobile);
}

function checkUserName(userName) {
	var format = "^([^u4E00-u9FA5]|[a-zA-Z0-9_\-]){2,16}$";
	var myReg = new RegExp(format);
	return  myReg.test(userName);
}

function checkPostCode(postCode) {
	var format = "^[0-9]{6}$";
	var myReg = new RegExp(format);
	return  myReg.test(postCode);
}

/**
 * 更新附加产品，并更新价格
 * @return
 */
function updateAddition(obj,paramId,sumDddition){ 
	updateBaoxianCount();
	updateAdditionWithoutPriceUpdated(obj, paramId, sumDddition);
	initYouhuiContent();
	updatePrice_youhui();
}

/**
 * 更新附件产品，但不同时更新价格
 * @param obj
 * @param paramId
 * @param sumDddition
 */
function updateAdditionWithoutPriceUpdated(obj,paramId,sumDddition) {	
	
	var paramObj = "#"+paramId; 
	var selectNum = parseInt($(obj).val()); 
	$(paramObj).val(selectNum); 
	$("#"+sumDddition).html(parseInt(($(paramObj).attr("sellPrice"))*selectNum)+"");
	
	if(selectNum==0){ 
		$("#tra_id").attr("checked", 'true'); 
		$("#tra_id").attr("disabled",''); 
		$("#radioWriteId").attr("checked",'');
		$("#tra_info").html(""); 	
	}
	if(selectNum>0){ 
		$("#tra_id").attr("checked", ''); 
		$("#tra_id").attr("disabled",'true'); 
		$("#radioWriteId").attr("checked",'true');
		addPerson('paramName'); 
	}
	
	var tt=$(obj).attr("tt");
	if(typeof(tt)=='string'&&tt=='baoxianSelect') {
		//if(selectNum==0) { //按是否是保险，是否为0时显示对应的提示信息
		//	$("#insure_info_"+paramId.replace("addition","")).find(".without").show();
		//} else {
		//	$("#insure_info_"+paramId.replace("addition","")).find(".without").hide();
		//}
        //最新修改，nicky
		var baoxian=$("select[tt='baoxianSelect']");
		 var i=0;
		$.each(baoxian,function(key,val){
 		    i=i+parseInt(val.value);
		});
		if(i>0){
		$(".without").hide();
		}else {
		$(".without").show();
		}
	}	
}

/**
 * 计算合计
 * <input type="hidden" couponPrice="0" sellName="sellName" cashRefund=""  marketPrice="" sellPrice="${product.sellPriceYuan}" value="1"    />
 * sellName="sellName" 
 * sellPrice="${product.sellPrice}"
 * cashRefund=""
 * @return
 */
function updatePrice(){
	var priceNameObjs = $("input[sellName='sellName']");
	var heji = 0;
	var yuanjia=0;
	var gongjieshe = 0;
	var youhuiquan = 0;
	var fanxian = 0;
	var totalyouhui=0;
	for ( var i = 0; i < priceNameObjs.length; i++) {
		var sellPrice = parseInt($(priceNameObjs[i]).attr("sellPrice"));
		var marketPrice = parseInt($(priceNameObjs[i]).attr("marketPrice"));
		youhuiquan += parseInt($(priceNameObjs[i]).attr("couponPrice"))*parseInt($(priceNameObjs[i]).val());
		var buyNum = parseInt($(priceNameObjs[i]).val());
		heji += sellPrice*buyNum;
		yuanjia += marketPrice*buyNum;
		var cashRefund = parseInt($(priceNameObjs[i]).attr("cashRefund"));
		if(cashRefund>0){
			fanxian = cashRefund;
		}
	}
	var orderAmount=0;
	var orderYouhuiObj = null;
	$("div[id='youhuiContent']").each(function(){
		if($(this).css("display")!="none"&&$(this).attr("name")=="prod"){
			var num = $("input[name='buyInfo.buyNum.product_"+$(this).attr("productId")+"']");
			//alert(num.attr("couponPrice"));
			$(this).find("#dikou").html(parseInt(num.attr("couponPrice"))*parseInt(num.val()));
		} else if($(this).css("display")!="none"&&$(this).attr("name")=="order"){
			orderAmount= $(this).find("#youhuitiaojian").val();
			orderYouhuiObj=$(this);
		}
	});
	var orderYouhui =0;
		$("input[name='orderCoupon']").each(function(){orderYouhui+=parseInt($(this).val());});
		if(isNaN(orderYouhui)){orderYouhui=0;};
	$("#heji").html(heji+"");
	$("#yuanjia").html(yuanjia+"");
	$("#gongjieshe").html((yuanjia-heji+youhuiquan)+"");
	
	$("#fanxian").html(fanxian+"");
	var orderCouponPrice = 0;
	if(isNaN(parseInt($("#orderCouponPrice").val()))){
		orderCouponPrice=0;
	} else {
		orderCouponPrice=$("#orderCouponPrice").val();
	}
	var orderMinAmout=0;
	if(orderYouhuiObj!=null){
		orderMinAmout = orderYouhuiObj.find("#youhuitiaojian").val();
	}
	
	if(parseInt(orderMinAmout)!=-1&&orderAmount!=0){
		//var mol =Math.floor(parseInt(heji)/parseInt(orderAmount));
		$("#dingdanjiesujinger").html(parseInt(heji)-(((isNaN(parseInt(youhuiquan))?0:parseInt(youhuiquan))+parseInt(orderCouponPrice)*1))+"");
		$("#youhuiquan").html((((isNaN(parseInt(youhuiquan))?0:parseInt(youhuiquan))+parseInt(orderCouponPrice)*1))+"");
		if(orderYouhuiObj!=null){
			if(mol!=0){
				orderYouhuiObj.find("#dikou").html(parseInt(orderCouponPrice)*1);	
			} else {
				orderYouhuiObj.find("#dikou").html(0);
			}
		}
		
	} else {

		$("#dingdanjiesujinger").html(heji-((parseInt(youhuiquan)+parseInt(orderCouponPrice)))+"");
		$("#youhuiquan").html(parseInt(youhuiquan)+parseInt(orderCouponPrice)+"");
		if(orderYouhuiObj!=null){
			orderYouhuiObj.find("#dikou").html(parseInt(orderCouponPrice));
		}
	}
	
}

$(document).ready(function(){
initOrderCoupon();
//updateInsurance();按新的逻辑，该信息不需要
var first_express_flag=true;
$("select[saleNumType]").each(function(){ 
	createOption("paramName",$(this).attr("id"),''); 
	
	if($(this).attr("saleNumType")=="ALL")//针对要全选的操作,把下拉框隐掉，显示文字出来
	{
		var val=$(this).val();
		$(this).hide();
		var $td=$(this).parent();
		var $span=$("<span/>");
		$span.text(val);
		$td.append($span);
	}
	//下面的代码处理产品当中存在快递并且都是任选的情况下默认让第1个选中数量1
	var pstype=$(this).attr("pstype");
	var saleNumType=$(this).attr("saleNumType");
	if(first_express_flag && saleNumType=='OPT' && pstype=='EXPRESS'){
		var express_first_obj=$("select[pstype=EXPRESS]:first");
		if(express_first_obj[0]==this){
			var len = $("select[pstype=EXPRESS][saleNumType=ALL]").size();
			if(len==0){
				len = $("select[pstype=EXPRESS][minAmt!=0]").size();			
			}
			if(len==0){
				$(this).find("option[value=1]").attr("selected",true);
				$(this).trigger("change");
			}else{
				first_express_flag=false;
			}
		}
	}
});
//qjh
if ($("input[name='buyInfo.productType']").val()!="TICKET"){
	$("select[tt=baoxianSelect]:first option:last").attr("selected",true);
}
$("select[saleNumType]").trigger("change");
updateBaoxianCount();
$("select[saleNumType=ALL]").bind("change",function()
{
	$td=$(this).parent();
	$td.find("span").text($(this).val());
});

});


function  initOrderCoupon(){
	var priceNameObjs = $("input[sellName='sellName']");
	var heji = 0;
	var yuanjia=0;
	var gongjieshe = 0;
	var youhuiquan = 0;
	var fanxian = 0;
	var totalyouhui=0;
	for ( var i = 0; i < priceNameObjs.length; i++) {
		var sellPrice = parseInt($(priceNameObjs[i]).attr("sellPrice"));
		var marketPrice = parseInt($(priceNameObjs[i]).attr("marketPrice"));
		youhuiquan += parseInt($(priceNameObjs[i]).attr("couponPrice"))*parseInt($(priceNameObjs[i]).val());
		var buyNum = parseInt($(priceNameObjs[i]).val());
		heji += sellPrice*buyNum;
		yuanjia += marketPrice*buyNum;
		var cashRefund = parseInt($(priceNameObjs[i]).attr("cashRefund"));
		if(cashRefund>0){
			fanxian = cashRefund;
		}
	}
	$("div[id='youhuiContent']").each(function(){
		 if($(this).attr("name")=="order"){
			 //alert();
			 if(parseInt($(this).find("#youhuitiaojian").val())!=-1){
			 	if($(this).parent().find("input[name='ord_coupon']").attr("checked")=="true"){
					if(parseInt(heji)<parseInt($(this).find("#youhuitiaojian").val())){
						$(this).parent().find("input[name='ord_coupon']").attr("disabled","true");
						$(this).parent().find("input[name='ord_coupon']").attr("checked","");
						$(this).parent().find("#youhuiContent").hide();
						var info = $(this).parent().find("#couponInfo");
						$(this).parent().find("#couponCodeId").val("");
						$(this).parent().find("#amount").val("");
						$(this).parent().find("#code").val("");
						//$(this).parent().find("#couponId").val("");
						$(this).parent().find("#amount").attr("hasAdd","false");
						var productNumInput = $("input[name='buyInfo.buyNum.product_"+$(this).attr("productId")+"']");
						$("#orderCouponPrice").val(0);
						info.html("");
					}
			} else {
				$(this).parent().find("input[name='ord_coupon']").attr("disabled","");
			}
			 }
			
			}
	});
}
/**
 * 
 * @param paramId
 * @param selectId
 * @return
 */
function createOption(paramId,selectId,type){
	var selfPack=$("form[name=orderForm] input[name=buyInfo.selfPack]").val()=="true";	
	var selectObj = $("#"+selectId); 
	var branchType=selectObj.attr("btype");
	var buyPeopleNum = 0; 
	if(selfPack){
		var adult=parseInt($("form[name=orderForm] input[name=buyInfo.adult]").val());
		var child=parseInt($("form[name=orderForm] input[name=buyInfo.child]").val());
		if(adult!=NaN){
			buyPeopleNum+=adult;			
		}
		if(child!=NaN){
			buyPeopleNum+=child;
		}
	}else{
		var paramObjs = $("input[name='"+paramId+"']"); 		
		for ( var i = 0; i < paramObjs.length; i++) { 
			buyPeopleNum += parseInt($(paramObjs[i]).attr("buyPeopleNum")); 
		} 
	}
	var option="";
	var oldVal=selectObj.val();
	min=parseInt(selectObj.attr("minAmt"));
	var saleNumType=selectObj.attr("saleNumType");
	var change_required=false;
	var fangcha_type=false;
	if(branchType=='FANGCHA'){//如果是房差时并且是线路产品只有一个人参数时房差的下拉框必须为1
		var productType=$("input[name=buyInfo.productType]").val();		
		var fangchaSize=$("select[btype=FANGCHA]").size();
		if(productType=='ROUTE'&&selfPack!='true'&&fangchaSize==1){//只有一个房差时才有效		
			fangcha_type=true;
			if(buyPeopleNum==1){
				min=1;
				change_required=true;
			}
		}
	}
	
	var notify_change=true;
	switch(saleNumType) {	
	case 'OPT'://最小值-人数值序列,该 操作保持以前的做法不变
		if((selectObj.find("option").length!=buyPeopleNum-min+1)||change_required){ 
			if(min>=buyPeopleNum){ //产品订购数比预订数还少
				option += "<option value=\""+min+"\">"+min+"</option>";
			}else{
				for ( var i = min; i <= buyPeopleNum; i++) { 
					option += "<option value=\""+i+"\">"+i+"</option>";
				} 
			}	
			selectObj.html(option);
			if(oldVal==1&&fangcha_type){//如果之前选中是数量就变成最小值
				selectObj.find("option:first").attr("selected","selected");
			} else if(oldVal>buyPeopleNum) {
				selectObj.find("option:last").attr("selected","selected");				
			} else 	{
				//alert(oldVal+"    "+buyPeopleNum)
				selectObj.find("option[value="+oldVal+"]").attr("selected","selected");
			}				
		}else { //不需要操作更新
			notify_change=true;				
		}
		break;
	case 'ANY'://最小值与人数值
		var current_val=selectObj.val();
		selectObj.empty();			
		var $opt;
		if(min<buyPeopleNum) {
			$opt=$("<option/>").text(min).val(min);
			selectObj.append($opt);
		}
		$opt=$("<option/>").text(buyPeopleNum).val(buyPeopleNum);
		if(current_val!=min) { //如果选框是非小值的时候就需要把后面的选上
			$opt.attr("selected","selected");
		}
		selectObj.append($opt);			
		break;
	case 'ALL'://人数值
		var val=min>buyPeopleNum?min:buyPeopleNum;
		var $all_opt=$("<option/>").text(val).val(val);
		selectObj.empty().append($all_opt);
		break;	
	}
	//获取产品的Id
	var productId = selectId;
	var pattn=/^\d{1}$/; 
	for (var i = 0 ; i < selectId.length ; i++) {	
		if (pattn.exec(selectId.charAt(i))) {
			productId = selectId.substring(i);
			break;
		}
	}
	updateAdditionWithoutPriceUpdated(selectObj, 'addition' + productId,'sumAddition' + productId);
} 
	




/**
 * 加减数量
 *  <input type="hidden" name="paramName" id="paramId" minAmt="最小起订数" maxAmt="最大订购数" textNum="用户订购数" buyPeople="订购总人数" people="成人数+儿童数"/>
 *  
 * @param paramId 
 * @param operator
 * @return
 */
function updateOperator(paramId,operator){
	var porintNum = 0;
	var miunsObj = $("#"+paramId);
	var textNumObj = $("#"+miunsObj.attr("textNum"));
	var min = parseInt(miunsObj.attr("minAmt"));
	var max = parseInt(miunsObj.attr("maxAmt"));
	var people = parseInt(miunsObj.attr("people"));
	var textNum = parseInt(textNumObj.val());
	var branchId = parseInt(miunsObj.attr("productId"));
	var selfPack = $("input[name=buyInfo.selfPack]");
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
	$("input[name$=.quantity]").each(function() {
		$(this).val(porintNum);
	});
	textNumObj.val(porintNum);
	miunsObj.attr("buyPeopleNum",porintNum*people);
	miunsObj.attr("value",porintNum);
	//checkCode_youhui_notInfo();
	$("select[saleNumType]").each(function(){
		createOption("paramName",$(this).attr("id"),'');
	});
	updateBaoxianCount();
	
	if(selfPack.val()=="false"){
		getLastCancelTime(branchId);	
	}
	
	$("#couponCheckedSpec").attr("checked","true");


    //触发、优惠活动
    var li = $("#couponActivity").find("div.selectbox-drop").find("li.selected");
    if(typeof li.attr("data-value") != "undefined"){
        if(li.attr("data-value") != "" && li.attr("data-value") != 'userCoupon'){
            li.trigger("click");
            $("#couponActivity").removeClass("selectbox-active active");
        }else{
            initYouhuiContent();
            updatePrice_youhui();
        }
    }else{
        initYouhuiContent();
        updatePrice_youhui();
    }



}

/**
 *	动态取最晚取消时间
*/
function getLastCancelTime(branchId)
{
	var refundContent = $("#refundContent");
	var visitTime = $("#id").attr("visitTime");
	var branchIds = "";
	$("input[name=paramName]").each(function(){
		if($(this).attr('value')!=0){
			branchIds += $(this).attr('productId');
			branchIds+=",";
		}
	});
	if(branchIds!=""){
   		$.post("/buy/refundContent.do",{"branchIds":branchIds,"visitTime":visitTime},function(dt){
		var data=eval("("+dt+")");
		if(data.success){
			refundContent.html(data.refundContent);
		}else{
			refundContent.html("");
		}
	});
	}else{
		refundContent.html("");
	}
	
}


function updateBaoxianCount()//按保险数量去更新baoxianSelect的值
{
	var c=0;
	$("select[tt=baoxianSelect]").each(function(){
		c+=parseInt($(this).val());		
	});
	
	$("input[name=baoxianSelect]").val(c);
}
/**
 * 被废弃
 */
function updateInsurance(){ //更新保险
	var productId = $("#insuranceProductId").val();
	if(productId!=undefined){ 
		var selectObj = $("#select"+productId); 
		createOption('paramName','select'+productId,'INSURANCE'); 
		updateAddition($("#select"+productId),'addition'+productId,'sumAddition'+productId); 
		addPerson('paramName'); 
	
		$("#tra_id").attr("checked", ''); 
		$("#tra_id").attr("disabled",'true'); 
		$("#radioWriteId").attr("checked",'true');//打勾 
	} 
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

/**
 * 更新单个产品的总价，即单个产品总价 = 单个产品的单价*单个产品的数量
 *  
 *  
 * @param priceElement 单价所在的元素 
 * @param quantityElement 数量所在的元素
 * @param targetElement 需要更新的元素
 * @return
 */
function refreshTotal(priceElement,quantityElement,quantity, targetElement){
	var _price = parseInt($("#"+priceElement).attr("sellPriceYuan"));
	var _quantity = $("#" + quantityElement).val();
	$("#" + targetElement).html(_price * _quantity * quantity);	
}