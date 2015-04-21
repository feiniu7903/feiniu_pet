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
	textNumObj.val(porintNum);
	miunsObj.attr("buyPeopleNum",porintNum*people);
	miunsObj.attr("value",porintNum);
	$("[saleNumType]").each(function(){
		createOption("paramName",$(this).attr("id"),'');
	});
	updatePage();
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
$(document).ready(function(){
	$("[saleNumType]").each(function(){ 
		createOption("paramName",$(this).attr("id"),''); 
		//下面的代码处理产品当中存在快递并且都是任选的情况下默认让第1个选中数量1
		var pstype=$(this).attr("pstype");
		if(pstype=='TAX'){
			$("input[pstype=TAX]:eq(0)").attr("checked","checked"); 
		}else if(pstype=='INSURANCE'){
			$("input[pstype=INSURANCE]:eq(0)").attr("checked","checked");
		}else if(pstype=='EXPRESS'){
			$("input[pstype=EXPRESS]:eq(0)").attr("checked","checked");
		}
	});
	updatePage();
	$("[productAdditional]").click(function(){
		updatePage();
	});
});


/**
 * 更新右则订单合计小结
 * @param obj
 * @param paramId
 * @param sumDddition
 */
function updateRightShow(eventType) {	
	//主产品
	var mainHtml="";
	var minHtml="";
	$("[mainBranchName]").each(function(){
		if($(this).val()!='0'){
			mainHtml+='<dd><span class="num"><dfn>&yen;'+$(this).attr("sellPrice")+'</dfn>&times;<i class="price">'+$(this).val()+'</i></span><p>'+$(this).attr("mainBranchName")+'</p></dd>';
		}
	});
	$("#mainHtml").html(mainHtml);
	$("[minBranchName]").each(function(){
		if($(this).val()!='0'){
			minHtml+='<dd><span class="num"><dfn>&yen;'+$(this).attr("sellPrice")+'</dfn>&times;<i class="price">'+$(this).val()+'</i></span><p>'+$(this).attr("minBranchName")+'</p></dd>';
		}
	});
	if(minHtml == ""){
		$("#additionalProductCost").addClass("hide");
	}else{
		$("#additionalProductCost").removeClass("hide");
		$("#minHtml").html(minHtml);
	}
	
	//更新游玩人数量
	
	//查询当前游玩人数量 
	var flag = false; //是否有保险
	var peopleNum = $("input[name='buyPeopleNum']").val();
	var playNum = $("#play-edit").find("dl").length;
	if( eventType == "insurance" || playNum == 0 || playNum-1 != peopleNum){
		//按保险或着其它条件判断需要游玩与否
		$("input:[pstype='INSURANCE']").each(function(){
			if($(this).attr("checked") == "checked"){
				flag = true;
			}
		});
	   var first=$("input[name='first']").val();
	   var other=$("input[name='other']").val();
	    var firstPlay = true; //第一游玩人
		var otherPlay = true; //其他游玩人
		if(first=='[];'){
			firstPlay=false;
		}
		if(other=='[];'){
			otherPlay=false;
		}
		
		//1保险存在  显示订单数量同等的游玩人
		if(flag==true){
			//alert('1保险存在  显示订单数量同等的游玩人')
			showPlay(peopleNum,flag);
		}else{
			//第一游玩人存在和其他游玩人存在
			if(firstPlay ===true&& otherPlay === true){
				showPlay(peopleNum,flag);
			}
			//没有保险 第一游玩人存在   其他游玩人不存在  显示一行
			else if(firstPlay ===true && otherPlay === false){
				//alert('没有保险 第一游玩人存在   其他游玩人不存在  显示一行');
				showPlay(1,flag);
			}else if(firstPlay === false && otherPlay === true){
				//第一游玩人不存在，其他游玩人存在，显示全部
				showPlay(peopleNum,flag);
			}else{
				//alert('都不显示')
				showPlay(0,flag); //不显示
			}
				
		}
			
	}
 	
 	//更新快递
 	var addressFlag = false;
 	$("input:[pstype='EXPRESS']").each(function(){
		if($(this).attr("checked") == "checked"){
			addressFlag=true;
		}
	});
 	showAddress(addressFlag);
}

/**
 * 更新隐藏input附件产品的数量\radio
 */
function updateAdditionRadio(eventType){
	updatePage(eventType);
}

function updatePage(eventType){
	$("input[name='paramName']").each(function(){
		var selfPack=$("form[name=orderForm] input[name='buyInfo.selfPack']").val()=="true";
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
			var paramObjs = $("input[name='paramName']"); 		
			for ( var i = 0; i < paramObjs.length; i++) { 
				buyPeopleNum += parseInt($(paramObjs[i]).attr("buyPeopleNum")); 
			} 
		}
		//更新游玩人
		$("input[name='buyPeopleNum']").val(buyPeopleNum);
	});
	$("[productAdditional]").each(function(){ 
		if(!this.checked){
			$("#addition"+$(this).val()).val(0);
		}else{
			$("#addition"+$(this).val()).val($("#input"+$(this).val()).find("li.selected").attr("data-value"));
		}
	});
	$("input:[tt='fromRadio']").each(function(){
		if($(this).attr("checked") == "checked"){
			var radioNum = parseInt($("#"+$(this).attr("id")).val());
			$("#addition"+$(this).attr("id")).val(radioNum);
		}else{
			$("#addition"+$(this).attr("id")).val(0);
		}
	});
	updateRightShow(eventType);
	//触发、优惠活动
	var li = $("#couponActivity").find("div.selectbox-drop").find("li.selected");
	if(typeof li.attr("data-value") != "undefined"){
		if(li.attr("data-value") != "" && li.attr("data-value") != 'userCoupon'){
			li.trigger("click");
			$("#couponActivity").removeClass("selectbox-active active");
		}else{
			updatePrice_youhui();
		}
	}else{
		updatePrice_youhui();
	}
}
function createOption(paramId,inputId,type){
	var selfPack=$("form[name=orderForm] input[name='buyInfo.selfPack']").val()=="true";
	var selectObj = $("#"+inputId); 
	if(selectObj.length ==0){
		return;
	}
	var branchType = selectObj.attr("btype");
	var pstype = selectObj.attr("pstype");
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
	min=parseInt(selectObj.attr("minAmt")); //附加产品最小订购数
	min = min == 0 ? 1 : min;
	max=parseInt(selectObj.attr("maxAmt")); //附加产品最大订购数
	var saleNumType=selectObj.attr("saleNumType"); //附加产品类型 (任选、可选、等量)
	//更新游玩人
	$("input[name=buyPeopleNum]").val(buyPeopleNum);
	if(branchType=='FANGCHA'){//如果是房差时并且是线路产品只有一个人参数时房差的下拉框必须为1
		var productType=$("input[name='buyInfo.productType']").val();		
		var fangchaSize=$("input[btype=FANGCHA]").size();
		if(productType=='ROUTE'&&selfPack!='true'&&fangchaSize==1){//只有一个房差时才有效		
			if(buyPeopleNum==1){
				selectObj.val(1);
			}else{
				selectObj.val(0);
			}
		}
	}else if(pstype=='TAX' || pstype == 'INSURANCE'){//如果是税金或着保险时，则税金的份数与人数一致
		selectObj.val(buyPeopleNum);
	}else{
		switch(saleNumType) {	
		case 'OPT'://最小值-人数值序列,该 操作保持以前的做法不变
				var select_ul = selectObj.find("ul");
				var current_val=selectObj.find("li.selected").attr("data-value");
				select_ul.empty();
				if(min > buyPeopleNum)buyPeopleNum = min;
				if(max < buyPeopleNum)buyPeopleNum = max;
				if(current_val == undefined ){
					current_val = min > 1 ? min:"1";
				}else if(current_val > buyPeopleNum){
					current_val = buyPeopleNum;
				}
				for ( var i = min; i <= buyPeopleNum; i++) { 
					var _selected = "";
					if(current_val == i ){
						_selected="class='selected'";
					}
					$opt=$("<li data-value='"+i+"' "+_selected+">"+i+"</li>");
					select_ul.append($opt);
				} 
				selectObj.find("span.select-value").html(current_val);
				select_ul.find("li").click(function(){
					select_ul.find("li").removeClass("selected");
					var dataValue = $(this).attr("class","selected");
					updatePage();
				});
			break;
		case 'ANY'://最小值与人数值
			var select_ul = selectObj.find("ul");
			select_ul.empty();		
			var $opt;
			var current_val=selectObj.find("li.selected").attr("data-value");
			select_ul.empty();
			if(current_val == undefined ){
				current_val = min > 1 ? min:"1";
			}else if(current_val > buyPeopleNum){
				current_val = buyPeopleNum;
			}
			if(max < buyPeopleNum)buyPeopleNum = max;
			if(min<buyPeopleNum) {
				var min_selected="";
				var buy_selected="";
				if(current_val == min){
					min_selected = "class='selected'";
				}else{
					buy_selected = "class='selected'";
				}
				$opt=$("<li data-value='"+min+"' "+min_selected+">"+min+"</li>");
				select_ul.append($opt);
				$opt=$("<li data-value='"+buyPeopleNum+"' "+buy_selected+">"+buyPeopleNum+"</li>");
				$opt.attr("class","selected");
				select_ul.append($opt);	
			}else{
				$opt=$("<li data-value='"+min+"' class='selected'>"+min+"</li>");
				select_ul.append($opt);
			}
			selectObj.find("span.select-value").html(current_val);
			select_ul.find("li").click(function(){
				select_ul.find("li").removeClass("selected");
				var dataValue = $(this).attr("class","selected");
				updatePage();
			});
			break;
		case 'ALL'://人数值
			var select_ul = selectObj.find("ul");
			select_ul.empty();
			if(max < buyPeopleNum)buyPeopleNum = max;
			var current_val= min > buyPeopleNum ? min : buyPeopleNum;
			$opt=$("<li data-value='"+current_val+"' class='selected'>"+current_val+"</li>");
			select_ul.append($opt);
			selectObj.find("span.select-value").html(current_val);
			break;	
		}
	}
} 