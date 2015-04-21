var LOCAL_STRTORAGE_HOTEL_CITYKEY = "local_storage_hotel_citykey";
var LOCAL_STRTORAGE_HOTEL_CITY = "local_storage_hotel_city"; // 线路所在城市 
var LOCAL_STRTORAGE_HOTEL_CHECKINDATE="local_storage_checkindate";//入住日期
var LOCAL_STRTORAGE_HOTEL_LEAVEDATE="local_storage_hotel_leavedate";//离开日期
var LOCAL_STRTORAGE_HOTEL_CHECKINDATEZHWEEK="local_storage_checkindatezHweek";//入住星期
var LOCAL_STRTORAGE_HOTEL_LEAVEDATEZHWEEK="local_storage_hotel_leavedatezHweek";//离开星期
var LOCAL_STRTORAGE_HOTEL_DAYTIMES="local_storage_hotel_daytimes";//住几晚
var LOCAL_STRTORAGE_HOTEL_HOTELPRICE="local_storage_hotel_hotelprice";//价格
var LOCAL_STRTORAGE_HOTEL_HOTELPRICEMIN="local_storage_hotel_hotelpricemin";//价格小
var LOCAL_STRTORAGE_HOTEL_HOTELPRICEMAX="local_storage_hotel_hotelpricemax";//价格大
var LOCAL_STRTORAGE_HOTEL_HOTELKEYWORD="local_storage_hotel_hotelkeyword";//关键字
var LOCAL_STRTORAGE_HOTEL_HOTELKEYWORDID="local_storage_hotel_hotelkeywordid";//关键字ID

/**
 * 酒店搜索方法
 */
function selectHotelList(){
	//数据编码
	$("#cityName").val(encodeURIComponent($("#cityName").val()));
	$("#queryText").val(encodeURIComponent($("#queryText").val()));
	
	$("#searchPriceValue").val(encodeURIComponent($("#searchPriceValue").val()));
	$("#searchBrandValue").val(encodeURIComponent($("#searchBrandValue").val()));
	$("#searchAreaValue").val(encodeURIComponent($("#searchAreaValue").val()));
	
	$('#hotel_search_list').submit();
}
/**
 *酒店搜索周边酒店 
 */
function selectCircumHotelList(){
	var cityName=$("#locationCityName").val();
	var latitude=$("#locationLatitude").val();
	var longitude=$("#locationLongitude").val();
	var radius=10;
	var arrivalDate=$("#arrivalDate").val();
	var departureDate=$("#departureDate").val();
	
	var url=contextPath+'/hotel/hotelCircumLlist.htm?pageIndex=1&pageSize=10&ajax=false&cityName='+cityName+'&latitude='+latitude+'&longitude='+longitude+'&radius='+radius+'&arrivalDate='+arrivalDate+'&departureDate='+departureDate;
	window.location.href=url;
}
/**
 *周边酒店加载更多数据
 */
function circumHotelShowMoreDate(){
	var url = contextPath+'/hotel/hotelCircumLlist.htm';
	var pageIndex=$("#pageIndex").val();
	$("#pageIndex").val(++pageIndex);
	$.ajax({
		url : url,
		data : $('#hotel_search_list').serialize(),
		type : "POST",
		success : function(data) {
			$("#data_list").append(data);
			$("#pageIndex").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
			}
			//酒店距离处理大于1000--KM，小于1000--M
			distanceJudge();
		},
		error:function() {
			//lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}
/**
 * 查询城市列表--搜索页
 * @param tag
 */
function getCities(){
	window.location.href=contextPath+'/hotel/getCities.htm';
}
/**
 * 根据关键字/酒店名/商圈--搜索页
 */
function searchKeyWord(){
	var cityId =$("#cityId").val();//城市ID 查询中的cityid或citycode
	window.location.href=contextPath+'/hotel/getLandMarks.htm?cityId='+cityId;
}

/**
 * 选择价格事件--搜索页
 * @param min
 * @param max
 */
function searchPrice(){
	//搜索页面隐藏
	$("#hotel_search_title").hide();
	$("#hotel_search_text").hide();
	//价格页面显示
	$("#hotel_price_title").show();
	$("#hotel_price_text").show();
}
/**
 * 选择某区间价格后事件--价格页
 * @param min
 * @param max
 */
function selectPrice(min,max,text){
	//价格页面隐藏搜索页面显示
	priceBack();
	
	//价格区间和文本存入locstor
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELPRICE,text);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELPRICEMIN,min);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELPRICEMAX,max);
	if(min>=0 && max>=0){
		var priceValue=min+","+max;
		setLocalStorage("searchPriceValue",priceValue);
		$("#searchPriceValue").val(priceValue);
	}else{
		$("#searchAreaValue").val("");
	}
	
	//价格赋值给搜索页面
	$("#hotel_price_check_text").text(text);
	$("#lowRate").val(min);
	$("#highRate").val(max);
	
	
}
/**
 * 价格页面返回--价格页
 * @param min
 * @param max
 */
function priceBack(){
	//搜索页面显示
	$("#hotel_search_title").show();
	$("#hotel_search_text").show();
	//价格页面隐藏
	$("#hotel_price_title").hide();
	$("#hotel_price_text").hide();
}
//搜索框搜索城市&点击搜索按钮
function initCityAutoCompleteDatas() {
	var datasArray = [];
	var keyword = $("#citykeyword").val();
	if($.trim(keyword)==""){
		lvToast(false,"请输入入住城市",LT_LOADING_CLOSE);
		return false;
	}
	var j = 0;
	if(null != cityListJson && null != keyword && $.trim(keyword)!="") {
		for(var i = 0; i < cityListJson.length;i++) {
			if(cityListJson[i].pinyin.indexOf(keyword.toLowerCase())>-1 || cityListJson[i].name.indexOf(keyword)>-1 ) {
				datasArray[j++] = cityListJson[i];
			}
		}
	}
	//搜索城市
	initAutoCompleteDatas(datasArray); 
}
//搜索城市 显示
function initAutoCompleteDatas(datasArray) {
	var str = [];
	if(datasArray!=null && datasArray.length>0){
		for(var i = 0 ;i < datasArray.length;i++) {
			var obj = datasArray[i];
			str.push("<li onclick=\"chooseCity("+obj.id+",'"+obj.name+"','"+obj.pinyin+"');\">"+obj.name+"</li>");
		}
		$("#search_success").html(str.join(""));
		$("#search_autocomplete").show();
	}else{
		lvToast(false,"暂时未开通此城市",LT_LOADING_CLOSE);
		$("#search_success").html(str.join(""));
		$("#search_autocomplete").show();
		return false;
	}
}
/**
 * 城市恢复默认
 * @param id
 * @param name
 * @param pinyin
 */
function defaultCity() {
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_CITY,"");
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_CITYKEY,"");
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORD,"");//选择入住城市后关键字清空
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORDID,"");//选择入住城市后关键字ID清空
	
	window.location.href=contextPath+"/hotel/hotel.htm";
}
/**
 * 选中城市. 
 * @param name
 * @param pinyin
 */
function chooseCity(id,name,pinyin) {
	lvToast(CONTENT_LOGDING,LT_LOADING_MSG,LT_LOADING_TIME);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_CITY,name);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_CITYKEY,id);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORD,"");//选择入住城市后关键字清空
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORDID,"");//选择入住城市后关键字ID清空
	
	window.location.href=contextPath+"/hotel/hotel.htm";
}

/**
 * 点击关键字搜索按钮
 */
function clickKeyWordsButton(){
	var keyWords=$("#landmarkkeyword").val();
	chooselandMark(0,"",keyWords,"","");
}
/**
 * 选中关键字(地表，商圈)
 * @param name
 * @param pinyin
 */
function chooselandMark(index,locationId,locationName,locationType,pinyin) {
	//关键字ID NAME 存入LOCALStor
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORDID,locationId);
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORD,locationName);
	var landMark=locationId+","+locationName+","+index;
	setLocalStorage("searchAreaValue",landMark);
	
	window.location.href=contextPath+"/hotel/hotel.htm";
}
/**
 * 关键字默认恢复默认
 * @param id
 * @param name
 * @param pinyin
 */
function defaultlandMark() {
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORD,"");//
	setLocalStorage(LOCAL_STRTORAGE_HOTEL_HOTELKEYWORDID,"");//
	setLocalStorage("searchAreaValue","");//
	
	window.location.href=contextPath+"/hotel/hotel.htm";
}
//搜索框搜索关键字
function initLandMarkAutoCompleteDatas() {
	var datasArray = [];
	var keyword = $("#landmarkkeyword").val();
//	if($.trim(keyword)==""){
//		lvToast(false,"请输入酒店关键字",LT_LOADING_CLOSE);
//		return false;
//	}
	var j = 0;
	if(null != landMarkMapListAll && null != keyword && $.trim(keyword)!="") {
		for(var i = 0; i < landMarkMapListAll.length;i++) {
			if(landMarkMapListAll[i].pinyin.indexOf(keyword.toLowerCase())>-1 || landMarkMapListAll[i].locationName.indexOf(keyword)>-1 ) {
				datasArray[j++] = landMarkMapListAll[i];
			}
		}
		$(".common-order").hide();
		$(".common-nav").hide();
	}else{
		$(".common-order").show();
		$(".common-nav").show();
	}
	//搜索城市
	initLandMarkDatas(datasArray); 
}
///搜索框搜索关键字
function initLandMarkDatas(datasArray) {
	var str = [];
	
	for(var i = 0 ;i < datasArray.length;i++) {
		var obj = datasArray[i];
		str.push("<li onclick=\"chooselandMark(0,"+obj.locationId+",'"+obj.locationName+"','"+obj.locationType+"','"+obj.pinyin+"');\">"+obj.locationName+"</li>");
	}
	$("#search_success").html(str.join(""));
	$("#search_autocomplete").show();
	
}
/**
 * 选中入住/离开日期
 * @param name
 * @param pinyin
 */
function selsctHoteldate(clickType,clickPageType) {
	var checkIndate=$("#arrivalDate").val();//入住日期
	var leaveDate=$("#departureDate").val();//离开日期
	var hotelId=$("#hotelId").val();
	if(hotelId!=null && hotelId!=''){
		window.location.href=contextPath+"/hotel/getDatePicker.htm?arrivalDate="+checkIndate+"&hotelId="+hotelId+"&departureDate="+leaveDate+"&clickType="+clickType+"&clickPageType="+clickPageType;
	}else {
		window.location.href=contextPath+"/hotel/getDatePicker.htm?arrivalDate="+checkIndate+"&departureDate="+leaveDate+"&clickType="+clickType+"&clickPageType="+clickPageType;
	}
}
/**
 * 酒店时间表选中时间时间
 * 时间表在搜索页--详情页---订单填写页用到
 * clickType 区分是点击入住还是离开日期
 * pageType 区分那个页面点击hotel_search==搜索页hotel_detail==详情页hotel_order==订单填写页
 * @param name
 * @param pinyin
 */function hotelChoseDays(checkdate,clickType,clickPageType) {
	 var url;
	 var checkIndate=$("#arrivalDate").val();//入住日期--搜索页面传过来的
	 var leaveDate=$("#departureDate").val();//离开日期--搜索页面传过来的
	 if(clickPageType=="hotel_search"){//搜索页
		 //酒店时间搜索页点击
		 if(clickType==1){//
			 url=contextPath+"/hotel/hotel.htm?arrivalDate="+checkdate+"&departureDate="+leaveDate+"&clickType="+clickType;
		 }else{//
			 url=contextPath+"/hotel/hotel.htm?arrivalDate="+checkIndate+"&departureDate="+checkdate+"&clickType="+clickType;
		 }
	 }else if(clickPageType=="hotel_detail"){//详情页
		 var hotelId=$("#hotelId").val();
		 if(clickType==1){
			 url=contextPath+"/hotel/hotelDetail.htm?arrivalDate="+checkdate+"&departureDate="+leaveDate+"&hotelId="+hotelId+"&clickType="+clickType;
		 }else{
			 url=contextPath+"/hotel/hotelDetail.htm?arrivalDate="+checkIndate+"&departureDate="+checkdate+"&hotelId="+hotelId+"&clickType="+clickType;
		 }
	 }else if(clickPageType=="hotel_order"){//订单填写页
		 if(clickType==1){
			 url=contextPath+"/hotel/hotel.htm?arrivalDate="+checkdate+"&departureDate="+leaveDate;
		 }else{
			 url=contextPath+"/hotel/hotel.htm?arrivalDate="+checkIndate+"&departureDate="+checkdate;
		 }
	 }
	
	window.location.href=url;
}
//获取默认入住时间和离开时间
 function getDefaultDate() {
 	$.ajax({
         type: "post",
         url: contextPath+'/hotel/getDefaultDate.htm',
         success: function (data) {
        	 $("#hotel_checkin_date_text").text(data.arrivalDate);
        	 $("#hotel_checkin_date_zh").text(data.arrivalZhDate);
        	 $("#arrivalDate").val(data.arrivalDate);
        	 $("#arrivalZhDate").val(data.arrivalZhDate);
        	 
        	 $("#hotel_leave_date_text").text(data.departureDate);
        	 $("#hotel_leave_date_zh").text(data.departureZhDate);
        	 $("#departureDate").val(data.departureDate);
        	 $("#departureZhDate").val(data.departureZhDate);
         },
         error: function () {
         }
     });
 }
 
 /**
 *点击筛选条件是把AJAX查询的PAGEINDEX,AJAX参数还原默认 
 */
function defaultParamsDate(){
	 $("#searchPriceValue").val(encodeURIComponent($("#searchPriceValue").val()));
	 $("#searchBrandValue").val(encodeURIComponent($("#searchBrandValue").val()));
	 $("#searchAreaValue").val(encodeURIComponent($("#searchAreaValue").val()));
	 
	 $("#queryText").val(encodeURIComponent($("#queryText").val()));
	 $("#ajax").val(false);
	 $("#pageIndex").val(1);
 }
// /**
// * 列表页筛选---选择区域在酒店列表页
// */
//function chooselandMarkInlist(locationId,locationName){
//	$("#locationId").val(locationId);
//	$("#queryText").val(locationName);
//	defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
//	$('#hotel_search_list').submit();
// }
///**
// * 列表页筛选---选择价格
// */
//function choosePriceInlist(price){
//	var lowRate= price.split(",")[0];
//	var highRate=price.split(",")[1];
//	$("#lowRate").val(lowRate);
//	$("#highRate").val(highRate);
//	defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
//	$('#hotel_search_list').submit();
//}
///**
// * 列表页筛选---选择品牌
// */
//function chooseBrandInlist(brandId,brandName){
//	$("#brandId").val(brandId);
//	//$("#queryText").val(encodeURIComponent(brandName));
//	defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
//	$('#hotel_search_list').submit();
//}
/**
 * 
 * 综合筛选
 * @param value
 * @param index
 */
function allSearch(value,index){
		if(index==0){//价格
			if(value!=0){//0是没选
				var lowRate= value.split(",")[0];
				var highRate=value.split(",")[1];
				$("#lowRate").val(lowRate);
				$("#highRate").val(highRate);
				
				$("#searchPriceValue").val(value);
			}else{
				$("#lowRate").val("");
				$("#highRate").val("");
				
				$("#searchPriceValue").val("");
			}
		}else if(index==1){//品牌
			if(value!=0 || value==''){//0是没选
				$("#brandId").val(value);
				
				$("#searchBrandValue").val(value);
			}else{
				$("#brandId").val("");
				
				$("#searchBrandValue").val("0");
			}
		}else if(index==2){//区域
			if(value!=0){//0是没选
				var locationId= value.split(",")[0];
				var locationName=value.split(",")[1];
				$("#locationId").val(locationId);
				$("#queryText").val(locationName);
				
				$("#searchAreaValue").val(value);
			}else{
				$("#locationId").val("");
				$("#queryText").val("");
				
				$("#searchAreaValue").val("");
			}
			//三次索引分别是价格、品牌、商圈
			//第三次进来则进行搜索
			//前两次赋值
			defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
			$('#hotel_search_list').submit();
		}
}
/**
 * 列表页筛选---选择星级类型
 */
function chooseStarInlist(starValue){
	$("#starRate").val(starValue);
	defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
	$('#hotel_search_list').submit();
}
/**
 * 列表页筛选---选择排序
 */
function chooseSortInlist(sortValue){
	$("#sort").val(sortValue);
	defaultParamsDate();//选择筛选条件吧PAGEINDEX,AJAX还原默认值
	$('#hotel_search_list').submit();
}

/**
 *加载更多数据 
 */
function showMoreDate(){
	var url = contextPath+'/hotel/hotelSearchList.htm';
	var pageIndex=$("#pageIndex").val();
	$("#pageIndex").val(++pageIndex);
	$("#ajax").val(true);
	$.ajax({
		url : url,
		data : $('#hotel_search_list').serialize(),
		type : "POST",
		success : function(data) {
			$("#data_list").append(data);
			$("#pageIndex").val($("#tmp_p").val());
			$("#tmp_p").remove();
			if($("#lastPage_flag").length != 0){
				$("#show_more").hide();
			}
		},
		error:function() {
			//lvToast(false,LT_ORDER_SUBMIT_ERROR,LT_LOADING_CLOSE);
		}
	});
}
/**
 * 酒店周边景点ajax加载更多
 * @param p
 */
function getHotelSpotticketMoreData(){
	var sort = "juli";
	var page = $("#pageIndex").val();
	var latitude = $("#latitude").val();
	var longitude = $("#longitude").val();
	
	var param = {"stage":2,"sort":sort,"pageIndex":++page,"windage":100000,"latitude":latitude,"longitude":longitude};
	$.post(contextPath+"/hotel/spotticket_ajax.htm",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#pageIndex").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
		}
	});
};
//====================================================================================================
/**
 *酒店预订 
 */
function hotelOrderFill(hotelName,hotelTypeName,arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId){
	 
	//名称和类型写入LOCSTOR防止登录乱码
	setLocalStorage("hotel_name_text",hotelName);
	setLocalStorage("hotel_type_name_text",hotelTypeName);
	
	url=contextPath+'/order/hotel_order_fill.htm?arrivalDate='+arrivalDate+'&departureDate='+departureDate+'&hotelId='+hotelId+'&roomTypeId='+roomTypeId+'&ratePlanId='+ratePlanId+'&numberOfRooms='+1;
	window.location.href = url;
	
}

/**
 * 房间加
 * @param arrivalDate
 * @param departureDate
 * @param hotelId
 * @param roomTypeId
 * @param ratePlanId
 */
function hotelNumberAdd(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId){
	hotelNumberVerify(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId);//参数验证
	if($("#hotelRoomNumber").html()==null || parseInt($("#hotelRoomNumber").html())<1){
		lvToast(false,"入住房间必须大于1",LT_LOADING_CLOSE);
		return false;
	}
	if($("#hotelRoomNumber").html()!=null && parseInt($("#hotelRoomNumber").html())>=10){
		//lvToast(false,"最多只能预订10间房哦",LT_LOADING_CLOSE);
		return false;
	}
	var numberOfRooms=parseInt($("#hotelRoomNumber").html())+1;
	var param = {"arrivalDate":arrivalDate,"departureDate":departureDate,"hotelId":hotelId,"roomTypeId":roomTypeId,"ratePlanId":ratePlanId,"numberOfRooms":numberOfRooms};
	hotelChangeNumber(param);
}
/**
 * 房间减
 * @param arrivalDate
 * @param departureDate
 * @param hotelId
 * @param roomTypeId
 * @param ratePlanId
 */
function hotelNumberDec(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId){
	hotelNumberVerify(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId);//参数验证
	if($("#hotelRoomNumber").html()==null || parseInt($("#hotelRoomNumber").html())<1){
		lvToast(false,"入住房间必须大于1",LT_LOADING_CLOSE);
		return false;
	}
	var numberOfRooms=parseInt($("#hotelRoomNumber").html());
	if(numberOfRooms<=1){
		//点击减号当数量小于等于一时不做操作
	}else{
		numberOfRooms--;
		var param = {"arrivalDate":arrivalDate,"departureDate":departureDate,"hotelId":hotelId,"roomTypeId":roomTypeId,"ratePlanId":ratePlanId,"numberOfRooms":numberOfRooms};
		hotelChangeNumber(param);
	}
	
}
/**参数验证
 * @param arrivalDate
 * @param departureDate
 * @param hotelId
 * @param roomTypeId
 * @param ratePlanId
 */
function hotelNumberVerify(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId){
	if(arrivalDate==null || arrivalDate=="") {
		lvToast(false,"入住时间不可为空",LT_LOADING_CLOSE);
		return false;
	}
	if(departureDate==null || departureDate=="") {
		lvToast(false,"离店时间不可为空",LT_LOADING_CLOSE);
		return false;
	}
	if(hotelId==null || hotelId=="") {
		lvToast(false,"酒店参数错误",LT_LOADING_CLOSE);
		return false;
	}
	if(roomTypeId==null || roomTypeId=="") {
		lvToast(false,"酒店参数错误",LT_LOADING_CLOSE);
		return false;
	}
	if(ratePlanId==null || ratePlanId=="") {
		lvToast(false,"酒店参数错误",LT_LOADING_CLOSE);
		return false;
	}
}
/**
 * 酒店订单--改变房间数量
 */
function hotelChangeNumber(param){
	
	$.ajax({
		url :contextPath+'/order/hotel_change_number.htm',
		data : param,
		type : "POST",
		success : function(data) {
			$("#hotelRoomNumber").text(param.numberOfRooms);
			$("#hotelTotalPrice").text(data.totalPrice);
			if(param.numberOfRooms>1){
				$(".lv-reduce").removeClass("disable");
			}else {
				$(".lv-reduce").addClass("disable");
			}
			if(param.numberOfRooms>=10){
				$(".lv-plus").addClass("disable");
			}else {
				$(".lv-plus").removeClass("disable");
			}
			//获取新的到店时间===========start
			var arraveTimeString=""
			if(data!=null && data.guaranteeRules!=null && data.guaranteeRules.length>0){
				arraveTimeString+="<span class=\"fl t_l1 f-color-2\">到店时间</span>";
				arraveTimeString+="<select class=\"sex fr\" name=\"expProvince\" id=\"expProvince\">";
				for(var i = 0 ;i < data.guaranteeRules.length;i++) {
					var o=data.guaranteeRules[i];
					arraveTimeString+="<option txt=\"\" optionValue="+o.option+" class=\"expProvinceOpion\" boolean="+o.needGuarantee+" value="+i+" data-key=\"\">"+o.option+"</option>";
				}
				arraveTimeString+="</select>";
			}
			$("#arraveTime").html(arraveTimeString);
			
			$(".expProvinceOpion").each(function(){
				$(this).attr("txt",data.guaranteeDescription);
			});
			//给到店时间赋时间
			arrivalDateSelect($("#expProvince"));
			//==============================end
			
			//获取已经输入的入住人
			var textValues=[];
			var j=0;
			$(".customertext").each(function(index){
				var value=$(this).val();
				textValues[j++]=value;
			});
			//改变入住人输入框
			var str="";
			for(var i = 0 ;i < param.numberOfRooms;i++) {
				//
				if(textValues.length<i){
					textValues[i]="";
				}
				str+=addCustomerName(i+1,textValues[i]);
			}
			//手机号获取
			var mobileNum=$("#contactMobile").val();
			if(mobileNum!=null && mobileNum!=''){
				str+="<div class=\"common-order-line\"><label>"+"<span class=\"t_l1\">"+"手机号"+"</span><input type=\"text\" value="+mobileNum+" class=\"lv-text-input\" id=\"contactMobile\" placeholder=\"用于接收订单通知\"></label></div>";
			}else{
				str+="<div class=\"common-order-line\"><label>"+"<span class=\"t_l1\">"+"手机号"+"</span><input type=\"text\" class=\"lv-text-input\" id=\"contactMobile\" placeholder=\"用于接收订单通知\"></label></div>";
			}
			
			$("#customerNamesList").html(str);
			
			//入住人--入住人1切换
			var ruzhuNum=[];
			var m=0;
			$(".customertext").each(function(index){
				ruzhuNum[m++]=$(this).parent().find(".t_l1");
			});
			if(ruzhuNum!=null && ruzhuNum.length==1){
						ruzhuNum[0].text("入住人");
			}
		},
		error:function() {
			lvToast(false,"更改数量异常！",LT_LOADING_CLOSE);
		}
	});
}
/**
 * 改变住房数量相应改变入住人数量
 * @param i
 */
function addCustomerName(i,value){
	var str="";
	if(value!=null && value!=''){
		str= "<div class=\"common-order-line\"><label>"+"<span class=\"t_l1\">"+"入住人"+i+"</span><input type=\"text\" class=\"lv-text-input customertext\" value="+value+" placeholder=\"请填写姓名\"></label></div>";
	}else{
		str= "<div class=\"common-order-line\"><label>"+"<span class=\"t_l1\">"+"入住人"+i+"</span><input type=\"text\" class=\"lv-text-input customertext\" placeholder=\"请填写姓名\"></label></div>";
	}
	
	return str;
}
/**
 *选择到店时间 
 */
function arriveDate(needGuarantee,guaranteeDescription){
	if(needGuarantee!=null && needGuarantee=='true'){
		$("#guaranteeDescription").show();
		$("#guaranteeDescription div").text(guaranteeDescription);
		$("#hotelOrderSubmit").hide();
		$("#guaranteeOrder").show();
	}else{
		$("#guaranteeDescription").hide();
		$("#guaranteeDescription div").text("");
		$("#hotelOrderSubmit").show();
		$("#guaranteeOrder").hide();
	}
//	//处理(担保￥xxx)隐藏
//	var option=$('#expProvince option:selected').text();//选中的文本;
//	if(option.indexOf("(")>=0){
//		var selectText=option.split("(")[0];
//		$('#expProvince option:selected').text(selectText);
//	}
}
/**
 *提交订单--区分是否担保clickType==0无担保，clickType==1有担保
 */
function hotelOrderSubmit(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId,customerType,customerIPAddress,clickType){
	hotelNumberVerify(arrivalDate,departureDate,hotelId,roomTypeId,ratePlanId);//参数验证
	
	var numberOfRooms=parseInt($("#hotelRoomNumber").html());
	var option=$('#expProvince option:selected').text();//选中的文本;
	var numberOfCustomers=0;
	var customerNames="";
	var contactName="";
	var contactMobile=$("#contactMobile").val();
	var totalPrice=$("#hotelTotalPrice").text();
	
//	var guaranteeType=$("#guaranteeType").val();//是否是第一晚担保
//	var guaranteePrice=$("#guaranteePrice").val();//担保金额/如果是第一晚担保并且金额不为空则guaranteePrice/否则将totalPrice赋值给guaranteePrice
//	if(guaranteeType!=null && guaranteeType=='FirstNightCost' && guaranteePrice!=null && guaranteePrice!=''){
//		guaranteePrice=guaranteePrice;
//	}else{
//		guaranteePrice=totalPrice;
//	}
	if(numberOfRooms==null || numberOfRooms<=0){
		lvToast(false,"房间数不能为零！",LT_LOADING_CLOSE);
		return false;
	}
	if(totalPrice==null  || totalPrice==''){
		lvToast(false,"总价不能为空！",LT_LOADING_CLOSE);
		return false;
	}
	
	//入住人姓名验证==================================================start
	var customerNameVerify=null;
	var ststusText=null;
	$(".customertext").each(function(index){
		var value=$(this).val();
		//统计多少了入住人
		numberOfCustomers++;
		if(value==null || value==''){
			customerNameVerify=0;
			return false;
		}
		if(value!=null && (value.indexOf("测试")>=0 || value.indexOf("ceshi")>=0)){
			ststusText=0;
			return false;
		}
		//第一个赋值给联系人
		if(index==0){
			contactName=value;
		}
		//入住人以逗号分隔
		customerNames+=value+",";
	});
	var ruzhuNum=[];
	var num=0;
	$(".customertext").each(function(index){
		ruzhuNum[num++]=$(this);
	});
	if(customerNameVerify!=null && customerNameVerify==0){
		if(ruzhuNum!=null && ruzhuNum.length==1){
			lvToast(false,"请输入入住人姓名！",LT_LOADING_CLOSE);
		}else{
			lvToast(false,"请输入入住人"+numberOfCustomers+"姓名！",LT_LOADING_CLOSE);
		}
		return false;
	}
	if(ststusText!=null && ststusText==0){
		if(ruzhuNum!=null && ruzhuNum.length==1){
			lvToast(false,"入住人姓名不能有敏感词语！",LT_LOADING_CLOSE);
		}else{
			lvToast(false,"入住人"+numberOfCustomers+"不能有敏感词语！",LT_LOADING_CLOSE);
		}
		return false;
	}
	//================================================================================end
	// 校验手机号
	if(!isMobile(contactMobile)){
		return false;
	}
	//channel=firstChannel+"_"+secondChannel;
	var param = {"arrivalDate":arrivalDate,"departureDate":departureDate,"firstChannel":"TOUCH","secondChannel":"LVMM","hotelId":hotelId,"roomTypeId":roomTypeId,"ratePlanId":ratePlanId,"numberOfRooms":numberOfRooms,"numberOfCustomers":numberOfCustomers,"customerIPAddress":customerIPAddress,"option":option,"contactName":contactName,"contactMobile":contactMobile,"totalPrice":totalPrice,"customerNames":customerNames,"customerType":customerType};
	if(clickType==0){//无担保
		$.ajax({
			url :contextPath+'/hotel/create.do',
			data : param,
			type : "POST",
			success : function(datas) {
				if(datas!=null && datas.data!=null && datas.data.resultCode==0){
					//alert(datas.data.orderId);
					window.location.href = contextPath+'/order/hotel_order_success.htm?orderId='+datas.data.orderId;
				}
				if(datas!=null && datas.message!=null && datas.message!=""){
					lvToast(false,datas.message,LT_LOADING_CLOSE);
					return false;
				}
			},
			error:function(datas) {
				lvToast(false,"提交失败",LT_LOADING_CLOSE);
				return false;
			}
		});
	}else{//有担保
		contactName=encodeURIComponent(contactName);
		option=encodeURIComponent(option);
		customerNames=encodeURIComponent(customerNames);
		var requesturl=contextPath+'/order/guarantee_order.htm?arrivalDate='+arrivalDate+'&departureDate='+departureDate+'&hotelId='+hotelId+'&roomTypeId='+roomTypeId+'&ratePlanId='+ratePlanId+'&numberOfRooms='+numberOfRooms+"&numberOfCustomers="+numberOfCustomers+"&customerIPAddress="+customerIPAddress+"&option="+option+"&contactName="+contactName+"&contactMobile="+contactMobile+"&totalPrice="+totalPrice+"&customerNames="+customerNames+"&customerType="+customerType;
		window.location.href = requesturl;
	}
}
//手机号校验
function isMobile(m) {
	if(m==null || m=='') {
		lvToast(false,"请输入酒店入住人的手机号码",LT_LOADING_CLOSE);
		return false;
	}
	if (!m.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/)
			|| m.length != 11) {
		lvToast(false,"请输入正确的酒店入住人的手机号码",LT_LOADING_CLOSE);
		return false;
	} else {
		return true;
	}
}
/**
 *酒店有担保--提交订单
 */
function guaranteeOrderSubmit(){
	//酒店填写订单传来参数 start
	var arrivalDate=$("#arrivalDate").val();
	var departureDate=$("#departureDate").val();
	var hotelId=$("#hotelId").val();
	var roomTypeId=$("#roomTypeId").val();
	var ratePlanId=$("#ratePlanId").val();
	var numberOfRooms=$("#numberOfRooms").val();
	var numberOfCustomers=$("#numberOfCustomers").val();
	var customerIPAddress=$("#customerIPAddress").val();
	var option=$("#option").val();
	var contactName=$("#contactName").val();
	var contactMobile=$("#contactMobile").val();
	var totalPrice=$("#totalPrice").val();
	var customerNames=$("#customerNames").val();
	var customerType=$("#customerType").val();
	//end
	
	
	//担保参数--卡号，日期证件类型等start
	var ccNo=null;
	var ccCvv=null;
	var ccExpirationYear=null;
	var ccExpirationMonth=null;
	var ccHolderName=null;
	var ccIdType=null;
	var ccIdNo=null;
	var saveCardFlag=null;
	//end
	
	var clickType=$("#clickType").val();
	if(clickType==0){//使用其他信用卡页面(列表使用其他，无信用卡时的使用页面)
		ccNo=$("#ccNo").val();
		ccCvv=$("#ccCvv").val();
		ccExpirationYear =$('#ccExpirationYear option:selected').text();
		ccExpirationMonth =$('#ccExpirationMonth option:selected').text();
		ccHolderName=$("#ccHolderName").val();
		ccIdType =$('#ccIdType option:selected').val();
		ccIdNo=$("#ccIdNo").val();
		//是否保存信用卡信息
		var flag=$("#saveCardFlagSW").attr("class");
		if(flag.indexOf("selected")>=0){
			saveCardFlag="true";
		}else{
			saveCardFlag="false";
		}
		
		if(!isNumber(ccNo)){
			lvToast(false,"请输入正确的信用卡号",LT_LOADING_CLOSE);
			return false;
		}
		if(!isNumber(ccCvv)){
			lvToast(false,"请输入正确的卡背面末3位数字",LT_LOADING_CLOSE);
			return false;
		}
		if(ccHolderName==null || $.trim(ccHolderName) ==""){
			lvToast(false,"请输入持卡人姓名",LT_LOADING_CLOSE);
			return false;
		}
//		if(!isNumber(ccIdNo)){
//			lvToast(false,"请输入正确的证件号",LT_LOADING_CLOSE);
//			return false;
//		}
	}else{//列表页选择信用卡提交担保
		//查找选中的信用卡
		//$(".radioOption div label").each(function(index){
		ccNoLong=null;
		ccNoShort=null;
		$(".ic_radio_a").each(function(index){
			var thisClass=$(this).attr("class");
			if(thisClass.indexOf("selected")>=0){
				ccNoShort=$("#credid_short_card_"+index).val();
				ccNoLong=$("#credid_long_card_"+index).val();
				ccCvv=$("#cvv2_"+index).val();
				ccExpirationYear =$('#exp_year_'+index+' option:selected').text();
				ccExpirationMonth =$('#exp_month_'+index+' option:selected').text();
				ccHolderName=$('#identify_card_name_'+index).val();
				ccIdNo=$("#identify_card_no_"+index).val();
				ccIdType=$("#identify_card_type_"+index).val();
				saveCardFlag=$("#save_credit_card_"+index).val();
				return false;
			}
		});
		if(!isNumber(ccNoShort)){
			lvToast(false,"请输入正确的信用卡末4位数字",LT_LOADING_CLOSE);
			return false;
		}
		if(!isNumber(ccCvv)){
			lvToast(false,"请输入正确的卡背面末3位数字",LT_LOADING_CLOSE);
			return false;
		}
		ccNo=ccNoLong+ccNoShort;
	}
	var param = {"ccNo":ccNo,"ccCvv":ccCvv,"ccExpirationYear":ccExpirationYear,"firstChannel":"TOUCH","secondChannel":"LVMM","ccExpirationMonth":ccExpirationMonth,"ccHolderName":ccHolderName,"ccIdType":ccIdType,"ccIdNo":ccIdNo,"saveCardFlag":saveCardFlag,"arrivalDate":arrivalDate,"departureDate":departureDate,"hotelId":hotelId,"roomTypeId":roomTypeId,"ratePlanId":ratePlanId,"numberOfRooms":numberOfRooms,"numberOfCustomers":numberOfCustomers,"customerIPAddress":customerIPAddress,"option":option,"contactName":contactName,"contactMobile":contactMobile,"totalPrice":totalPrice,"customerNames":customerNames,"customerType":customerType};
	$.ajax({
		url :contextPath+'/hotel/create.do',
		data : param,
		type : "POST",
		success : function(datas) {
			if(datas!=null && datas.data!=null && datas.data.resultCode==0){
				//alert(datas.data.orderId);
				window.location.href = contextPath+'/order/hotel_order_success.htm?orderId='+datas.data.orderId;
			}
			if(datas!=null && datas.message!=null && datas.message!=""){
				lvToast(false,datas.message,LT_LOADING_CLOSE);
				return false;
			}
		},
		error:function(datas) {
			lvToast(false,"提交失败",LT_LOADING_CLOSE);
			return false;
		}
	});
}

/**
 *点击是否保存担保信息 
 */
function saveCardFlagSW(){
	var flag=$("#saveCardFlagSW").attr("class");
	if(flag.indexOf("selected")>=0){
		$("#saveCardFlagSW").removeClass("selected");
	}else{
		$("#saveCardFlagSW").addClass("selected");
	}
}

/**
 * 酒店取消订单
 * @param orderId
 */
function cancelHotelOrder(orderId,cancelHotelOrderType){
	//Vkd0U1UxSkdWbFZYYldoYVZtMDRlRmt5TVhOTmF6VllVbXhTYVdGc1dtRldhazVPVFZkSmVsSnVUazlXYlhoM1ZUTndWazFYU2xWWmVrWlBWMFZhUjFSclVsTlNSazQyVm14d2JGWnRZM2hXUldoSFpXczFWVlp1YUZOV1JscG9Wa1JLTTAxV2JGZGFSV3hQWWtkNFNWVXdWVFZOVmxZMlZtNWFWVTFyV2tOYVJrNHdWa1UxV1ZGcVJtaFhSbG8yVmtSR2EyRXlSWGRQVmxKUFVqSjNlRlpZY0dGaU1sSllVbXR3YUUxc1duTlhWbVJ2VlVkR1ZXSklaR3RXVkd4RFdWWk9NR0ZHVFhwUmJYaHBWbFZ3ZVZONlJrNU5SMUpaVm01Q1dHSlVSWGhXYWtKTFlqQnplVkpzYUdwTlJHd3dWakJvVjA1V1NraGpSelZVWVRKb01GbHJhRXRTVjBwSVkwWndUMDFxVlhsV01uUnZUVzFTY21OSVdrNVNSbG96V2xaV2MxUnNXa2hqU0ZKcFVrWndXbGt3YUVOa01rcDBUVlJHVkdWVWJIcFpNRll3WWxkS1NHRkhhR3hpVkVaM1dURmFiMk15UmxaaVNHeHBZbFJHY0ZwSE1ERmtSMGw0Vlc1R1lVMUhlRFZaYTJSM1V6RndkR1JFUm1wWFNFSXhXV3hqTlZaWFNraGpla3BZVWpOb00xWXhaR0ZrTVc5NFlrY3hhMkpzY0V4Wk1qRXdUVlpzVmxWdVVtRk5TR2Q1V1ZST1YyRkdiSFJQV0d4cVlURktlbGx0ZUhkU1YwVjZWbXR3YW1KWVVYaFpha3BMWkVkR1ZtTkZiR2xpVkVZeVZtdGpOVTFzYkZoVGJrWmhUVWQzZVZsVVRsZFRiRXBJVFZoT2FVMXNiM2xhUjNSelRtMUtkVlpVUWsxTmFrWXlWa1ZrZDJNeVRuVlJWRTVyWW14d1MxcFhNSGhsVm14WFZXNVNhRmRGV2xwWk1HUnJXVlpXU0dSRVJsVlRSWEF5V1d4YWQyVlhTa2hhUm5CaFlsUkdlbGt5ZEZOa01rcEZXa1JLYUdKWFVrdFZNVkYzVUZFOVBRPT0=
	var hotelOrderTime=Date.parse(new Date())/1000;
	var lvsessionid="waphotelordercancel";
	var hotelOrderSign=hex_md5(orderId.toString()+hotelOrderTime.toString()+lvsessionid+"Vkd0U1UxSkdWbFZYYldoYVZtMDRlRmt5TVhOTmF6VllVbXhTYVdGc1dtRldhazVPVFZkSmVsSnVUazlXYlhoM1ZUTndWazFYU2xWWmVrWlBWMFZhUjFSclVsTlNSazQyVm14d2JGWnRZM2hXUldoSFpXczFWVlp1YUZOV1JscG9Wa1JLTTAxV2JGZGFSV3hQWWtkNFNWVXdWVFZOVmxZMlZtNWFWVTFyV2tOYVJrNHdWa1UxV1ZGcVJtaFhSbG8yVmtSR2EyRXlSWGRQVmxKUFVqSjNlRlpZY0dGaU1sSllVbXR3YUUxc1duTlhWbVJ2VlVkR1ZXSklaR3RXVkd4RFdWWk9NR0ZHVFhwUmJYaHBWbFZ3ZVZONlJrNU5SMUpaVm01Q1dHSlVSWGhXYWtKTFlqQnplVkpzYUdwTlJHd3dWakJvVjA1V1NraGpSelZVWVRKb01GbHJhRXRTVjBwSVkwWndUMDFxVlhsV01uUnZUVzFTY21OSVdrNVNSbG96V2xaV2MxUnNXa2hqU0ZKcFVrWndXbGt3YUVOa01rcDBUVlJHVkdWVWJIcFpNRll3WWxkS1NHRkhhR3hpVkVaM1dURmFiMk15UmxaaVNHeHBZbFJHY0ZwSE1ERmtSMGw0Vlc1R1lVMUhlRFZaYTJSM1V6RndkR1JFUm1wWFNFSXhXV3hqTlZaWFNraGpla3BZVWpOb00xWXhaR0ZrTVc5NFlrY3hhMkpzY0V4Wk1qRXdUVlpzVmxWdVVtRk5TR2Q1V1ZST1YyRkdiSFJQV0d4cVlURktlbGx0ZUhkU1YwVjZWbXR3YW1KWVVYaFpha3BMWkVkR1ZtTkZiR2xpVkVZeVZtdGpOVTFzYkZoVGJrWmhUVWQzZVZsVVRsZFRiRXBJVFZoT2FVMXNiM2xhUjNSelRtMUtkVlpVUWsxTmFrWXlWa1ZrZDJNeVRuVlJWRTVyWW14d1MxcFhNSGhsVm14WFZXNVNhRmRGV2xwWk1HUnJXVlpXU0dSRVJsVlRSWEF5V1d4YWQyVlhTa2hhUm5CaFlsUkdlbGt5ZEZOa01rcEZXa1JLYUdKWFVrdFZNVkYzVUZFOVBRPT0=");
	url=contextPath+"/order/hotel_order_cancel.htm?orderId="+orderId+"&cancelHotelOrderType="+cancelHotelOrderType+"&hotelOrderSign="+hotelOrderSign+"&hotelOrderTime="+hotelOrderTime+"&lvsessionid="+lvsessionid;
	$.ajax({
		url :url,
		type : "POST",
		success : function(datas) {
			if(datas!=null && datas.resultCode==0){
				if(datas.cancelHotelOrderType==1){
					window.location.href = contextPath+'/order/hotel_order_detail.htm?orderId='+datas.orderId;//详情
				}else{
					window.location.href = contextPath+'/order/hotel_order_list.htm?pageIndex=1&pageSize=5';//列表
				}
			}
		},
		error:function() {
			lvToast(false,"取消失败！",LT_LOADING_CLOSE);
		}
	});
}
/**
 * 酒店订单列表更多
 * @param p
 */
function getMoreHorelList(){
	var pageIndex = $("#pageIndex").val();
	var pageSize=$("#pageSize").val();
	
	var param = {"pageIndex":++pageIndex,"pageSize":pageSize,"ajax":true};
	$.post(contextPath+"/order/hotel_order_list.htm",param,function(data){
		$(".lv-toast-loading").hide(); // 隐藏遮罩层
		$("#data_list").append(data);
		$("#pageIndex").val($("#tmp_p").val());
		$("#tmp_p").remove();
		if($("#lastPage_flag").length != 0){
			$("#show_more").hide();
		}
	});
};

/**
 * 使用其他列表
 */
function useOtherCreditCard(){
	//点击使用其他信用卡--列表 title-list隐藏
	$("#identify_card_title").hide();
	$("#identify_card_list").hide();
	//点击使用其他信用卡--其他 title-list显示
	$("#identify_card_other_title").show();
	$("#identify_card_other_text").show();
	
	$("#clickType").val(0);
}
/**
 * 使用其他列表--返回按钮
 */
function useOtherBack(){
	//点击使用其他信用卡--列表 title-list显示
	$("#identify_card_title").show();
	$("#identify_card_list").show();
	//点击使用其他信用卡--其他 title-list隐藏
	$("#identify_card_other_title").hide();
	$("#identify_card_other_text").hide();
	
	$("#clickType").val(1);
}
/**
 *酒店订单填写页后退事件 
 */
function hotel_order_back(){
		//第一个TRUE是半段弹出框是否显示true显示false隐藏,第二个TRUE判断几个按钮TRUE一个FALSE是两个
		popupModal(true, "您的订单尚未完成，是否确定要离开当前页面？", null, 0,false);
		//判断用户是从登录页面过来//还是登录状态从态酒店详情过来
		//登录页面过来向后跳转2次
		//酒店详情过来跳转1次
		if(referer != null&&referer.indexOf("login")>0){
			$('.ic_roseo').click(function(){
				window.history.go(-2);
			});
		}else{
			$('.ic_roseo').click(function(){
				union_back();
			});
		}
		//取消按钮
		$('.ic_yellow').click(function(){
			popupModal(false, '', null, 0,false);
		});
}

/**
 * 酒店地图
 * @param name1
 * @param address1
 * @param latitude1
 * @param longitude1
 */
function hotelMap(name1,address1,latitude1,longitude1) {
	var name = escape(name1);
	var address = escape(address1);
	var latitude = Number(latitude1);
	var longitude = Number(longitude1);
	if (longitude == "" || longitude == null
			|| typeof (longitude) == "undefined") {
		lvToast(false, "无法获取当前地理位置,获不到周边景点", 3000);
	} else {
		window.location.href = contextPath+"/hotel/hotelMap.htm?latitude="
				+ latitude
				+ "&longitude="
				+ longitude
				+ "&name="
				+ name
				+ "&address="
				+ address;
	}
}
/**
 * 周边酒店显示距离处理
 * 酒店距离处理大于1000--KM，小于1000--M
 */
function distanceJudge(){
	$(".hotel_distance").each(function(index){
		var distanceString=$(this).html();
		var distanceFloat;
		if(distanceString!=null && distanceString!=""){
			if(distanceString.indexOf(",")>=0){
				distanceString=distanceString.split(",")[0]+distanceString.split(",")[1];
			}
			distanceFloat=parseFloat(distanceString);
			if(distanceFloat>=1000){
				var distanceKm=formatFloat(distanceFloat/1000,1);
				$(this).text(distanceKm+"k");
			}
		}
	});
}
/**
 * float保留位数
 * @param src
 * @param pos
 * @returns {Number}
 */
function formatFloat(src, pos)
{
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
}
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
//是否是数字
function isNumber(m) {
	if(!isEmpty(m)) {
		var reg = /^\d+$/;
		if (m.constructor === String) {
			if (m.match(reg)) {
				return true;
			} else {
				return false;
			}
		}
	}
	return false;
}
//校验是否为空
function isEmpty(m) {
	if (null == m || m.trim() == "") {
		return true;
	} else {
		return false;
	}
}
/**
 *MD5加密 
 */
var hexcase=0;
function hex_md5(a){ if(a=="") return a; return rstr2hex(rstr_md5(str2rstr_utf8(a)))}function hex_hmac_md5(a,b){return rstr2hex(rstr_hmac_md5(str2rstr_utf8(a),str2rstr_utf8(b)))}function md5_vm_test(){return hex_md5("abc").toLowerCase()=="900150983cd24fb0d6963f7d28e17f72"}function rstr_md5(a){return binl2rstr(binl_md5(rstr2binl(a),a.length*8))}function rstr_hmac_md5(c,f){var e=rstr2binl(c);if(e.length>16){e=binl_md5(e,c.length*8)}var a=Array(16),d=Array(16);for(var b=0;b<16;b++){a[b]=e[b]^909522486;d[b]=e[b]^1549556828}var g=binl_md5(a.concat(rstr2binl(f)),512+f.length*8);return binl2rstr(binl_md5(d.concat(g),512+128))}function rstr2hex(c){try{hexcase}catch(g){hexcase=0}var f=hexcase?"0123456789ABCDEF":"0123456789abcdef";var b="";var a;for(var d=0;d<c.length;d++){a=c.charCodeAt(d);b+=f.charAt((a>>>4)&15)+f.charAt(a&15)}return b}function str2rstr_utf8(c){var b="";var d=-1;var a,e;while(++d<c.length){a=c.charCodeAt(d);e=d+1<c.length?c.charCodeAt(d+1):0;if(55296<=a&&a<=56319&&56320<=e&&e<=57343){a=65536+((a&1023)<<10)+(e&1023);d++}if(a<=127){b+=String.fromCharCode(a)}else{if(a<=2047){b+=String.fromCharCode(192|((a>>>6)&31),128|(a&63))}else{if(a<=65535){b+=String.fromCharCode(224|((a>>>12)&15),128|((a>>>6)&63),128|(a&63))}else{if(a<=2097151){b+=String.fromCharCode(240|((a>>>18)&7),128|((a>>>12)&63),128|((a>>>6)&63),128|(a&63))}}}}}return b}function rstr2binl(b){var a=Array(b.length>>2);for(var c=0;c<a.length;c++){a[c]=0}for(var c=0;c<b.length*8;c+=8){a[c>>5]|=(b.charCodeAt(c/8)&255)<<(c%32)}return a}function binl2rstr(b){var a="";for(var c=0;c<b.length*32;c+=8){a+=String.fromCharCode((b[c>>5]>>>(c%32))&255)}return a}function binl_md5(p,k){p[k>>5]|=128<<((k)%32);p[(((k+64)>>>9)<<4)+14]=k;var o=1732584193;var n=-271733879;var m=-1732584194;var l=271733878;for(var g=0;g<p.length;g+=16){var j=o;var h=n;var f=m;var e=l;o=md5_ff(o,n,m,l,p[g+0],7,-680876936);l=md5_ff(l,o,n,m,p[g+1],12,-389564586);m=md5_ff(m,l,o,n,p[g+2],17,606105819);n=md5_ff(n,m,l,o,p[g+3],22,-1044525330);o=md5_ff(o,n,m,l,p[g+4],7,-176418897);l=md5_ff(l,o,n,m,p[g+5],12,1200080426);m=md5_ff(m,l,o,n,p[g+6],17,-1473231341);n=md5_ff(n,m,l,o,p[g+7],22,-45705983);o=md5_ff(o,n,m,l,p[g+8],7,1770035416);l=md5_ff(l,o,n,m,p[g+9],12,-1958414417);m=md5_ff(m,l,o,n,p[g+10],17,-42063);n=md5_ff(n,m,l,o,p[g+11],22,-1990404162);o=md5_ff(o,n,m,l,p[g+12],7,1804603682);l=md5_ff(l,o,n,m,p[g+13],12,-40341101);m=md5_ff(m,l,o,n,p[g+14],17,-1502002290);n=md5_ff(n,m,l,o,p[g+15],22,1236535329);o=md5_gg(o,n,m,l,p[g+1],5,-165796510);l=md5_gg(l,o,n,m,p[g+6],9,-1069501632);m=md5_gg(m,l,o,n,p[g+11],14,643717713);n=md5_gg(n,m,l,o,p[g+0],20,-373897302);o=md5_gg(o,n,m,l,p[g+5],5,-701558691);l=md5_gg(l,o,n,m,p[g+10],9,38016083);m=md5_gg(m,l,o,n,p[g+15],14,-660478335);n=md5_gg(n,m,l,o,p[g+4],20,-405537848);o=md5_gg(o,n,m,l,p[g+9],5,568446438);l=md5_gg(l,o,n,m,p[g+14],9,-1019803690);m=md5_gg(m,l,o,n,p[g+3],14,-187363961);n=md5_gg(n,m,l,o,p[g+8],20,1163531501);o=md5_gg(o,n,m,l,p[g+13],5,-1444681467);l=md5_gg(l,o,n,m,p[g+2],9,-51403784);m=md5_gg(m,l,o,n,p[g+7],14,1735328473);n=md5_gg(n,m,l,o,p[g+12],20,-1926607734);o=md5_hh(o,n,m,l,p[g+5],4,-378558);l=md5_hh(l,o,n,m,p[g+8],11,-2022574463);m=md5_hh(m,l,o,n,p[g+11],16,1839030562);n=md5_hh(n,m,l,o,p[g+14],23,-35309556);o=md5_hh(o,n,m,l,p[g+1],4,-1530992060);l=md5_hh(l,o,n,m,p[g+4],11,1272893353);m=md5_hh(m,l,o,n,p[g+7],16,-155497632);n=md5_hh(n,m,l,o,p[g+10],23,-1094730640);o=md5_hh(o,n,m,l,p[g+13],4,681279174);l=md5_hh(l,o,n,m,p[g+0],11,-358537222);m=md5_hh(m,l,o,n,p[g+3],16,-722521979);n=md5_hh(n,m,l,o,p[g+6],23,76029189);o=md5_hh(o,n,m,l,p[g+9],4,-640364487);l=md5_hh(l,o,n,m,p[g+12],11,-421815835);m=md5_hh(m,l,o,n,p[g+15],16,530742520);n=md5_hh(n,m,l,o,p[g+2],23,-995338651);o=md5_ii(o,n,m,l,p[g+0],6,-198630844);l=md5_ii(l,o,n,m,p[g+7],10,1126891415);m=md5_ii(m,l,o,n,p[g+14],15,-1416354905);n=md5_ii(n,m,l,o,p[g+5],21,-57434055);o=md5_ii(o,n,m,l,p[g+12],6,1700485571);l=md5_ii(l,o,n,m,p[g+3],10,-1894986606);m=md5_ii(m,l,o,n,p[g+10],15,-1051523);n=md5_ii(n,m,l,o,p[g+1],21,-2054922799);o=md5_ii(o,n,m,l,p[g+8],6,1873313359);l=md5_ii(l,o,n,m,p[g+15],10,-30611744);m=md5_ii(m,l,o,n,p[g+6],15,-1560198380);n=md5_ii(n,m,l,o,p[g+13],21,1309151649);o=md5_ii(o,n,m,l,p[g+4],6,-145523070);l=md5_ii(l,o,n,m,p[g+11],10,-1120210379);m=md5_ii(m,l,o,n,p[g+2],15,718787259);n=md5_ii(n,m,l,o,p[g+9],21,-343485551);o=safe_add(o,j);n=safe_add(n,h);m=safe_add(m,f);l=safe_add(l,e)}return Array(o,n,m,l)}function md5_cmn(h,e,d,c,g,f){return safe_add(bit_rol(safe_add(safe_add(e,h),safe_add(c,f)),g),d)}function md5_ff(g,f,k,j,e,i,h){return md5_cmn((f&k)|((~f)&j),g,f,e,i,h)}function md5_gg(g,f,k,j,e,i,h){return md5_cmn((f&j)|(k&(~j)),g,f,e,i,h)}function md5_hh(g,f,k,j,e,i,h){return md5_cmn(f^k^j,g,f,e,i,h)}function md5_ii(g,f,k,j,e,i,h){return md5_cmn(k^(f|(~j)),g,f,e,i,h)}function safe_add(a,d){var c=(a&65535)+(d&65535);var b=(a>>16)+(d>>16)+(c>>16);return(b<<16)|(c&65535)}function bit_rol(a,b){return(a<<b)|(a>>>(32-b))};

$.fn.swipeImgMove = function (option) {//触摸图片滚动
    var swiptimg = {
    };
    $.extend(swiptimg, option);
    var _width = $(this).width() ,
        _length = $(this).find(".slide").length,
        _wrap = $(this),
        _slides = _wrap.find('.slide'),
        active = _slides.filter('.active'),
        _tab = $("<div/>", {
            "class": "swipe-tab"
        }),
        autoTime,
        _index;
    if (_length == 0) return this;
    for (var j = 0; j < _length; j++) {
        $("<span/>").appendTo(_tab);
    }
    _wrap.prepend($("<figure/>", {"class": "slide"}));
    _wrap.find("figure:first").append(_wrap.find("figure:last").html());
    _wrap.append($("<figure/>", {"class": "slide"}));
    _wrap.find("figure:last").append(_wrap.find("figure:eq(1)").html());
    _tab.appendTo(_wrap);
    $(".swipe-tab span:eq(0)").addClass("cur");
    _length++;
    _slides = _wrap.find('.slide');
    //_index = _slides.index(active);
    _index = 0;
    _slides.on('swipeleft',function (e) {
        if (_index === _slides.length - 1) {
            return;
        }
        _slides.eq(_index + 1).trigger('activate');
    }).on('swiperight',function (e) {
            if (_index === 0) {
                return;
            }
            _slides.eq(_index - 1).trigger('activate');
        }).on('activate',function (e) {
            _slides.eq(_index).removeClass('active');
            jQuery(e.target).addClass('active');
            _index = _slides.index(e.target);
            _tab.find("span").eq((_index - 1) % (_length - 1)).addClass("cur").siblings().removeClass("cur");
        }).on('movestart', function (e) {
            clearInterval(autoTime);
            if ((e.distX > e.distY && e.distX < -e.distY) ||
                (e.distX < e.distY && e.distX > -e.distY)) {
                e.preventDefault();
                return;
            }
            _wrap.addClass('notransition');
            if (_index >= _length) {
                _slides.eq(_index).removeClass('active');
                _index = 1;
                _slides.eq(_index).addClass('active');
            } else if (_index <= 0) {
                _slides.eq(_index).removeClass('active');
                _index = _length - 1;
                _slides.eq(_index).addClass('active');
            }

        })
        .on('move', function (e) {
            var left = 100 * e.distX / _width;
            if (e.distX < 0) {
                if (_slides[_index + 1]) {
                    _slides[_index].style.left = left + '%';
                    _slides[_index + 1].style.left = (left + 100) + '%';
                }
                else {
                    _slides[_index].style.left = left / 4 + '%';
                }
            }
            if (e.distX > 0) {
                if (_slides[_index - 1]) {
                    _slides[_index].style.left = left + '%';
                    _slides[_index - 1].style.left = (left - 100) + '%';
                }
                else {
                    _slides[_index].style.left = left / 5 + '%';
                }
            }
        })
        .on('moveend', function (e) {
            _wrap.removeClass('notransition');
            _slides[_index].style.left = '';
            if (_slides[_index + 1]) {
                _slides[_index + 1].style.left = '';
            }
            if (_slides[_index - 1]) {
                _slides[_index - 1].style.left = '';
            }
            autoTime = setInterval(autoTimeRun,3000);
        });
    autoTime = setInterval(autoTimeRun,3000);
    function autoTimeRun(){//自动滚动方法
        _wrap.removeClass('notransition');
        if (_index == _length) {
            _wrap.addClass('notransition');
            _slides.eq(_index).removeClass('active');
            _index = 0;
            _slides.eq(_index).addClass('active');
        }
        _slides.eq(_index + 1).trigger('swipeleft');

    }
    return $(this);
};
$.fn.triggerLiAct = function () {   //列表手风琴切换
    var _this = $(this).find(".set-btn");
    _this.each(function (index) {
        var _area = $(this);
        _btn = _area.find(".triggerArea") || _area;
        if (!index) {
            _area.addClass("active");
        }
        var mbp = new MBP.fastButton(_btn.get(0), function () {
            _area.siblings().removeClass("active");
            _area.addClass("active");
        });
    });
};
