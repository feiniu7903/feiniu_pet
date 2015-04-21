//COOKIE   CMS出发城市
var SELECT_CITY=null;
//自动加载JS方法
$(function() {
	//====================================
	SELECT_CITY=getCookie("SELECT_CITY");
	if(SELECT_CITY==null || SELECT_CITY==""){
		SELECT_CITY=getCookie("CITY_IP_SITE");
	}
	SELECT_CITY=decodeURIComponent(SELECT_CITY);
	//=====================================
	$("body").on("keyup input","#keyword",function(event) {
		keyboartClick();
		var lKeyCode = (navigator.appName=="Netscape")?event.which:window.event.keyCode;
		if(lKeyCode==13){
			var keyword=$("#keyword").val();
			searchTip("",keyword,"");
		}
	});
	getParms();
	getTicketParms();
});
function keyboartClick(){
	//===============================首页搜索用
	$(".type_vale").each(function(index){
		var spanParentClass=$(this).parent('li').attr("class");
		if(spanParentClass!=null && spanParentClass=="active"){
			if($(this).attr("data-value")=="TICKET"){
				$("#searchStatus").val("TICKET");
				return false;
			}else{
				$("#searchStatus").val("ROUTE");
				return false;
			}
		}
	});
	//===============验证选中那个TAB=END
	var searchStatus=$("#searchStatus").val();
	//区分是门票还是线路
	if(!isEmpty(searchStatus) && searchStatus=="TICKET"){
		searchTicketDatas();
	}else{
		searchRouteDatas();
	}
	//删除关键字显示隐藏
	var _clearVal = $(this).val();
	if(_clearVal != ''){
		$('#clear_password').show();
	}else {
		$('#clear_password').hide();
	}
}
//自动补全搜索. --门票
function searchTicketDatas() {
	var keyword = $("#keyword").val();
	if($.trim(keyword) == '') {
		$("#search_autocomplete").hide();
		return false;
	}
	var param = {"keyWorld":encodeURIComponent(keyword)};
	
	$.ajax({type:"POST", url:contextPath+"/search_auto_complete", data:param, dataType:"json", success:function (data) {
		if(data.code=='success'){
			var datas = data.datas;
			if(null != datas && datas.length > 0) {
				initAutoCompleteDatas(datas);
			}else {
				$("#search_success").html("<li><a>没有找到符合条件的结果</a></li>");
			}
		}else{
			$("#search_success").html("<li><a>没有找到符合条件的结果</a></li>");
		}
	}});
}
//自动补全搜索--线路
function searchRouteDatas() {
	var keyword = $("#keyword").val();
	if($.trim(keyword) == '') {
		$("#search_autocomplete").hide();
		return false;
	}
	var fromDest=SELECT_CITY;
	//var fromDest="上海";
	var param = {"fromDest":encodeURIComponent(fromDest),"keyWorld":encodeURIComponent(keyword),"stage":1};
	$.ajax({type:"POST", url:contextPath+"/search_auto_complete", data:param, dataType:"json", success:function (data) {
		if(data.code=='success'){
			var datas = data.datas;
			if(null != datas && datas.length > 0) {
				initAutoCompleteDatas(datas);
			}else {
				$("#search_success").html("<li><a>没有找到符合条件的结果</a></li>");
			}
		}else{
			$("#search_success").html("<li><a>没有找到符合条件的结果</a></li>");

		}
	}});
}
//初始自动补全化数据 构建HTML
function initAutoCompleteDatas(datasArray) {
	var str = [];
	for(var i = 0 ;i < datasArray.length && i < 25;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"searchTip('"+obj.id+"','"+obj.name+"','"+obj.stage+"');\"><a>"+obj.name+"</a></li>");
	}
	$("#search_success").html(str.join(""));
	$("#search_success").show();
	$("#search_autocomplete").show();
}

//点击自动补全列表.-门票-线路
function searchTip(id,name,stage) {
	$("#search_success").hide();
	var searchStatus=$("#searchStatus").val();
	//区分是门票还是线路
	if(!isEmpty(searchStatus) && searchStatus=="TICKET"){
		if("2" == stage) { // 跳转详情页 
			window.location.href ="http://"+hostName+"/ticket/piao-"+id+"/";
		} else { // 跳转搜索页.
			$("#keyword").val(name);
			$("#hidden_keyword").val(encodeURIComponent(name));
			$('#key_search').attr("action",contextPath+"/search/ticket_search_list.htm");
			$("#key_search").submit();
		}
	}else{
		var fromDest=SELECT_CITY;
		//var fromDest="上海";
		// 提交表单 
		$("#holiday_search #fromDest").val(encodeURIComponent(fromDest));
		$("#holiday_search #keyword").val(encodeURIComponent(name));
		$("#holiday_search").attr("action",contextPath+"/search/rote_search_list.htm");
		$("#holiday_search").submit();
	}
	
}
//点击搜索按钮(暂时不用)
function btnClick(){
	$("#search_success").hide();
	var keyword = $("#keyword").val();
	if(null == keyword || $.trim(keyword)=='') {
		lvToast(false,"请输入景点目的地或关键词",LT_LOADING_CLOSE);
		return false;
	}
	$("input[name='keyword']").val(encodeURI(keyword));
	$("#key_search").submit();
	$("input[name='keyword']").val("");
	$("input[name='hidden_keyword']").val("");
}
//IDS搜索门票和线路产品-更多按钮
function getData(){
	var url = contextPath+'/search/ids_search_list.htm';
	
	$("#ids").val(encodeURIComponent($("#ids").val()));
	$("#ajax").val(true);
	var pageIndex=$("#page").val();
	
	$("#page").val(++pageIndex);
	$.ajax({
		url : url,
		data : $('#ids_search_list').serialize(),
		type : "POST",
		success : function(data) {
			$("#data_list").append(data);
			$("#page").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
			}
		},
		error:function() {
			lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}
/**
 * 度假5.0综合筛选
 */
function allSearch(value,index){
	var size= $("#filterDatasSize").val();
	
	var argName=$("#argName_"+index).val();
	
	if(value!=0){
		var kValue= value.split(",")[0];
		$("#"+argName).val(kValue);
		if(kValue==""){
			setLocalStorage("ALLTYPE_"+index+"_0","selected");//处理选择全部
		}
	}else{
		//$("#"+argName).val("");
		$("#"+argName).remove();
		setLocalStorage("ALLTYPE_"+index+"_0","");//处理选择全部
	}
	
	if(size==index+1){//如果是循环最后一次提交表单
		initParams();
		$('#rote_search_list').submit();
	}else{//负责什么都不做
		//========
	}
}
/**
 * 度假5.0类型筛选
 * @param subProductType
 */
function chooseSubProductType(subProductTypeDataVal){
	initParams();
	
	var subProductType= subProductTypeDataVal.split("-")[0];
	var subProductTypeTitle= subProductTypeDataVal.split("-")[1];
	
	$("#subProductType").val(subProductType);
	$("#subProductTypeTitle").val(encodeURIComponent(subProductTypeTitle));
	
	//alert(sort);
	$('#rote_search_list').submit();
}

/**
 * 度假5.0排序筛选
 * @param sort
 */
function chooseSortInlist(sortDataVal){
	initParams();
	
	var sort= sortDataVal.split("-")[0];
	var sortTitle= sortDataVal.split("-")[1];
	
	$("#sort").val(sort);
	$("#sortTitle").val(encodeURIComponent(sortTitle));
	//alert(sort);
	$('#rote_search_list').submit();
}
/**
 * 度假列表更多按钮
 */
function getRoteListData(){
var url = contextPath+'/search/rote_search_list.htm';
	
	$("#ajax").val(true);
	var pageIndex=$("#page").val();
	
	$("#page").val(++pageIndex);
	var param = {"fromDest":encodeURIComponent($("#fromDest").val()),"toDest":encodeURIComponent($("#toDest").val()),
				"keyword":encodeURIComponent($("#keyword").val()),"subject":encodeURIComponent($("#subject").val()),"day":encodeURIComponent($("#day").val()),
				"visitDay":encodeURIComponent($("#visitDay").val()),"traffic":encodeURIComponent($("#traffic").val()),"playLine":encodeURIComponent($("#playLine").val()),
				"playFeature":encodeURIComponent($("#playFeature").val()),"hotelType":encodeURIComponent($("#hotelType").val()),
				"hotelLocation":encodeURIComponent($("#hotelLocation").val()),"playBrand":encodeURIComponent($("#playBrand").val()),"playNum":encodeURIComponent($("#playNum").val()),
				"scenicPlace":encodeURIComponent($("#scenicPlace").val()),"landTraffic":encodeURIComponent($("#landTraffic").val()),
				"landFeature":encodeURIComponent($("#landFeature").val()),"city":encodeURIComponent($("#city").val()),"sort":encodeURIComponent($("#sort").val()),
				"subProductType":encodeURIComponent($("#subProductType").val()),"page":encodeURIComponent($("#page").val()),
				"ajax":encodeURIComponent($("#ajax").val())};
	$.ajax({
		url : url,
		data : param,
		type : "POST",
		success : function(data) {
			$("#data_list").append(data);
			$("#page").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
			}
		},
		error:function() {
			lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}
/**==========================================================================================================
 * 门票5.0综合筛选
 * 点击子城市
 */
function allTicketSearch(value){
	setLocalStorage("TICKETCITY","");//处理选择的地区
	$("#keyword").val(value);
	initTicketParams();
	$('#ticket_search_list').submit();
}
/**
 * 点击全部城市
 * @param value
 */
function allTicketSearchAll(value){
	setLocalStorage("TICKETCITY","selected");//处理选择的地区
	$("#keyword").val(value);
	initTicketParams();
	$('#ticket_search_list').submit();
}
/**
 * 门票5.0类型筛选
 * @param subProductType
 */
function chooseTicketSubProductType(subProductTypeDataVal){
	
	$("#subjects").val(subProductTypeDataVal);
	initTicketParams();
	$('#ticket_search_list').submit();
}

/**
 * 门票5.0排序筛选
 * @param sort
 */
function chooseTicketSortInlist(sortDataVal){
	var sort= sortDataVal.split("-")[0];
	var sortTitle= sortDataVal.split("-")[1];
	
	$("#sort").val(sort);
	$("#sortTitle").val(sortTitle);
	//alert(sort);
	initTicketParams();
	$('#ticket_search_list').submit();
}
/**
 * 门票列表更多按钮
 */
function getTicketListData(){
var url = contextPath+'/search/ticket_search_list.htm';
	
	$("#ajax").val(true);
	var pageIndex=$("#page").val();
	
	$("#page").val(++pageIndex);
	var param = {"keyword":encodeURIComponent($("#keyword").val()),"subjects":encodeURIComponent($("#subjects").val()),
				"sort":encodeURIComponent($("#sort").val()),"page":encodeURIComponent($("#page").val()),"ajax":encodeURIComponent($("#ajax").val()),
				"sortTitle":encodeURIComponent($("#sortTitle").val()),"initKeyword":encodeURIComponent($("#initKeyword").val())};
	$.ajax({
		url : url,
		data : param,
		type : "POST",
		success : function(data) {
			$("#data_list").append(data);
			$("#page").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
			}
		},
		error:function() {
			lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}

/**
 *周末自驾游点击进入度假列表 
 */
function getDrivTravelList(keywordValue){
	$("#fromDest").val(encodeURIComponent(SELECT_CITY));
	$("#keyword").val(encodeURIComponent(keywordValue));
	$("#subProductType").val("FREENESS");
	$("#subProductTypeTitle").val(encodeURIComponent("自由行(景+酒)"));
	
	$("#rote_search_list").submit();
}
/**
 * 初始化参数
 */
function initParams(){
     $("#fromDest").val(encodeURIComponent($("#fromDest").val()));
	 $("#toDest").val(encodeURIComponent($("#toDest").val()));
	 $("#keyword").val(encodeURIComponent($("#keyword").val()));
	 $("#subject").val(encodeURIComponent($("#subject").val()));
	 $("#day").val(encodeURIComponent($("#day").val()));
	 $("#visitDay").val(encodeURIComponent($("#visitDay").val()));
	 $("#traffic").val(encodeURIComponent($("#traffic").val()));
	 $("#playLine").val(encodeURIComponent($("#playLine").val()));
	 $("#playFeature").val(encodeURIComponent($("#playFeature").val()));
	 $("#hotelType").val(encodeURIComponent($("#hotelType").val()));
	 $("#hotelLocation").val(encodeURIComponent($("#hotelLocation").val()));
	 $("#playBrand").val(encodeURIComponent($("#playBrand").val()));
	 $("#playNum").val(encodeURIComponent($("#playNum").val()));
	 $("#scenicPlace").val(encodeURIComponent($("#scenicPlace").val()));
	 $("#landTraffic").val(encodeURIComponent($("#landTraffic").val()));
	 $("#landFeature").val(encodeURIComponent($("#landFeature").val()));
	 $("#city").val(encodeURIComponent($("#city").val()));
	 $("#sort").val(encodeURIComponent($("#sort").val()));
	 $("#subProductType").val(encodeURIComponent($("#subProductType").val()));
	 //$("#page").val(encodeURIComponent($("#page").val()));
	 $("#page").val(1);
	 //$("#ajax").val(encodeURIComponent($("#ajax").val()));
	 $("#ajax").val(false);
	 $("#subProductTypeTitle").val(encodeURIComponent($("#subProductTypeTitle").val()));
	 $("#sortTitle").val(encodeURIComponent($("#sortTitle").val()));
}
function getParms(){//度假列表后退转码
	$("#fromDest").val(decodeURIComponent($("#fromDest").val()));
	 $("#toDest").val(decodeURIComponent($("#toDest").val()));
	 $("#keyword").val(decodeURIComponent($("#keyword").val()));
	 $("#subject").val(decodeURIComponent($("#subject").val()));
	 $("#day").val(decodeURIComponent($("#day").val()));
	 $("#visitDay").val(decodeURIComponent($("#visitDay").val()));
	 $("#traffic").val(decodeURIComponent($("#traffic").val()));
	 $("#playLine").val(decodeURIComponent($("#playLine").val()));
	 $("#playFeature").val(decodeURIComponent($("#playFeature").val()));
	 $("#hotelType").val(decodeURIComponent($("#hotelType").val()));
	 $("#hotelLocation").val(decodeURIComponent($("#hotelLocation").val()));
	 $("#playBrand").val(decodeURIComponent($("#playBrand").val()));
	 $("#playNum").val(decodeURIComponent($("#playNum").val()));
	 $("#scenicPlace").val(decodeURIComponent($("#scenicPlace").val()));
	 $("#landTraffic").val(decodeURIComponent($("#landTraffic").val()));
	 $("#landFeature").val(decodeURIComponent($("#landFeature").val()));
	 $("#city").val(decodeURIComponent($("#city").val()));
	 $("#sort").val(decodeURIComponent($("#sort").val()));
	 $("#subProductType").val(decodeURIComponent($("#subProductType").val()));
	 //$("#page").val(encodeURIComponent($("#page").val()));
	 $("#page").val(1);
	 //$("#ajax").val(encodeURIComponent($("#ajax").val()));
	 $("#ajax").val(false);
	 $("#subProductTypeTitle").val(decodeURIComponent($("#subProductTypeTitle").val()));
	 $("#sortTitle").val(decodeURIComponent($("#sortTitle").val()));
}
/**
 * 门票初始化参数
 * @returns
 */
function initTicketParams(){
	$("#keyword").val(encodeURIComponent($("#keyword").val()));
	$("#subjects").val(encodeURIComponent($("#subjects").val()));
	$("#sort").val(encodeURIComponent($("#sort").val()));
	$("#page").val(1);
	$("#ajax").val(false);
	$("#sortTitle").val(encodeURIComponent($("#sortTitle").val()));
	$("#initKeyword").val(encodeURIComponent($("#initKeyword").val()));
}
/**
 * 门票列表后退转码
 * @returns
 */
function getTicketParms(){
	$("#keyword").val(decodeURIComponent($("#keyword").val()));
	$("#subjects").val(decodeURIComponent($("#subjects").val()));
	$("#sort").val(decodeURIComponent($("#sort").val()));
	$("#page").val(1);
	$("#ajax").val(false);
	$("#sortTitle").val(decodeURIComponent($("#sortTitle").val()));
	$("#initKeyword").val(decodeURIComponent($("#initKeyword").val()));
}


//页面跳转.
function union_skip(url) {
	//lvToast(CONTENT_LOGDING, LT_LOADING_MSG, LT_LOADING_TIME);
	window.location.href = url;
}
//====================================================================校验公共JS
//设置localStroage
function setLocalStorage(key,value){
	try {
	window.localStorage.setItem(key,value);
	} catch(err){
		
	}
}
//根据key获取值.
function getLocalStorage(key) {
	try {
		return null == window.localStorage.getItem(key)?"":window.localStorage.getItem(key);
	} catch(err){
		
	}
}
//校验是否为空
function isEmpty(m) {
	if (null == m || m.trim() == "") {
		return true;
	} else {
		return false;
	}
}
//清空输入框内容 id 输入框id
function union_clear_context(id) {
	$("#search_autocomplete").hide();
	if ($("#" + id).length > 0) {
		$("#" + id).val("");		
	}
}
//读取Cookie
function getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return temp[1];
		}
	}
	return '';
}