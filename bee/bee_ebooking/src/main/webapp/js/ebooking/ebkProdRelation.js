/*查询关联产品*/
function errorTimePriceMessageRelation(errorMsg){
	switch  (errorMsg){
		case 0: return "未登录";break;
		case 1: return "产品编码为空";break;
		case 2: return "未获取产品信息";break;
		case 3: return "没权限访问该产品";break;
		case -1: return "没有产品类别信息";break;
		case -2: return "没有产品类别编码";break;
		case -100: return "没有找到参数";break;
		case -1000: return "编辑类型不存在";break;
		case -101: return "验证不通过";break;
		case -79: return "不是未提交状态，不能修改";break;
		case -3001: return "关联产品不存在";break;
		case -3002: return "关联产品类别不存在";break;
		case -3003: return "关联产品编号不存在";break;
		case -3004: return "此产品类别已关联";break;
		case -500: return "系统出错";break;
		default: "未知错误 代码="+errorMsg;
	}
}
 //读取销售产品的类别列表
function showBranchSel(id) {
	var $sel = $("select[name='productBranchId']");
	$sel.empty();
	if (!id) {
		alert("没有选中要操作的产品");
	} else {
		$.post($('#basepathhiddenid').val()+"/product/relation/getProdBranchListJSON.do", {
			"ebkProdRelation.relateProductId" : id
		}, function(dt) {
			var data = eval("(" + dt + ")");
			if (data.success) {
				if (data.find) {
					for ( var i = 0; i < data.list.length; i++) {
						var $opt = $("<option/>");
						$opt.text(data.list[i].branchName);
						$opt.val(data.list[i].branchId);
						$sel.append($opt);
					}
				}
			} else {
				alert(data.msg);
			}
		});
	}
} 
$(function(){
	$(document.body).ready(function(){
		$("#productType").attr("tagName","select");
	});
$("#ebkRelationProduct").jsonSuggest({
	url : $('#basepathhiddenid').val()  + "/product/relation/searchProductJSON.do",
	maxResults : 20,
	minCharacters : 2,
	width:120,
	emptyKeyup:false,
	param:["#productType"],
	onSelect : function(item) {
		$("#ebkRelationProduct").attr("productid",item.id);
		$("#ebkRelationProduct").attr("productname",item.text);
		showBranchSel(item.id);
	}
});
$(".addEbkProdRelation").click(function() {
	var productType = $("select[name=productType]").val();
	if(undefined==productType || null==productType || ''==$.trim(productType)){
		alert("没有选择产品类型");
		return false;
	}
	var relationProductName = $("#ebkRelationProduct").val();
	if(undefined==relationProductName || null==relationProductName || ''==$.trim(relationProductName)){
		alert("没有选中要关联的产品");
		return false;
	}
	var productBranchId = $("select[name=productBranchId]").val();
	if(undefined==productBranchId || null==productBranchId || ''==$.trim(productBranchId)){
		alert("没有选中要关联的产品类别");
		return false;
	}
	$.post($('#basepathhiddenid').val()+"/product/relation/addRelation.do", {
		"ebkProdRelation.ebkProductId" : $("#ebkProdProductId").val(),
		"ebkProdRelation.relateProductId" : $("#ebkRelationProduct").attr("productid"),
		"ebkProdRelation.relateProdBranchId" : productBranchId,
		"ebkProdRelation.relateProductName" : $("#ebkRelationProduct").attr("productname"),
		"ebkProdRelation.relateProdBranchName":$("select[name=productBranchId] :selected").text(),
		"ebkProdRelation.relateProductType" : $("select[name=productType]").val(),
		"ebkProductViewType":$("#ebkproductviewtypehiddenid").val(),
		"ebkProdProductId":$("#ebkProdProductId").val()
	}, function(data) {
		if (data.success) {
			alert("增加关联产品成功");
			window.location.href=$('#basepathhiddenid').val()+"/product/relation/query.do?ebkProdProductId="+$("#ebkProdProductId").val();
		} else {
			var msg = errorTimePriceMessageRelation(data.message);
			alert(msg);
		}
	});
});
$(".ebkProdRelationDelete").click(function(){
	var relationid = $(this).attr("relationid");
	var relateprodbranchid= $(this).attr("relateprodbranchid");
	if(undefined==relationid || null==relationid){
		alert("没有要删除的关联产品");
		return false;
	}
	if(undefined==relateprodbranchid || null==relateprodbranchid){
		alert("没有要删除的关联产品");
		return false;
	}
	$.post($('#basepathhiddenid').val()+"/product/relation/deleteRelation.do", {
		"ebkProdRelation.relationId" : relationid,
		"ebkProdRelation.relateProdBranchId":relateprodbranchid,
		"ebkProductViewType":$("#ebkproductviewtypehiddenid").val(),
		"ebkProdProductId":$("#ebkProdProductId").val()
	}, function(data) {
		if (data.success) {
			alert("删除关联产品成功");
			window.location.href=$('#basepathhiddenid').val()+"/product/relation/query.do?ebkProdProductId="+$("#ebkProdProductId").val();
		} else {
			var msg = errorTimePriceMessageRelation(data.message);
			alert(msg);
		}
	});
});
$(".showRelationProductBranchTimePrice").live("click",function(){
	var ebkProdProductId = $("#ebkProdProductId").val();
	var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
	var prodBranchId = $(this).parent().attr("prodbranchid");
	var viewprodbranchpricestore={
			url:$('#basepathhiddenid').val()+"/product/branch/queryEbkRelationProdTimePrice.do",
			type : 'POST',
			data :{ebkProdBranchId:prodBranchId,ebkProductViewType:ebkProductViewType,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
			dataType : "html",
			success : function(data) {
				$.dialog({
					title: '时间价格库存表',
					content: data,
					width:940
				});
			},
			error : function() {
			}
	}; 
	$.ajax(viewprodbranchpricestore);
});
$(".viewprodRelationtimepricebtn").live("click",function(){
	var relationProduct =$(":hidden[name=relationProduct]").val();
	if(relationProduct!='Y'){
		return false;
	}
	var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
	var ebkProdProductId = $("#ebkProdProductId").val();
	var prodBranchId = $(this).attr("prodbranchid");
	var monthType = $(this).attr("tt");
	var currentDate = new Date();
	var currPageDate = $(this).attr("currpagedate"); 
	var date_current =new Date(currPageDate.replace(/-/g,"/"));
	var month_middle = (date_current.getFullYear()*12+date_current.getMonth())-(currentDate.getFullYear()*12+currentDate.getMonth());
	if("UP"==monthType && month_middle<-2){
		return;
	}else if(month_middle>14){
		return;
	}
	var viewprodbranchpricestore={
			url:$('#basepathhiddenid').val()+"/product/branch/queryEbkRelationProdTimePrice.do",
			type : 'POST',
			data :{ebkProdBranchId:prodBranchId,ebkProductViewType:ebkProductViewType,ebkProdProductId:ebkProdProductId,currPageDate:currPageDate,monthType:monthType,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
			dataType : "html",
			success : function(data) {
				$(".dialog-close").trigger("click");
				$.dialog({
					title: '时间价格库存表',
					content: data,
					width:940
				});
			},
			error : function() {
			}
	}; 
	$.ajax(viewprodbranchpricestore);
});
$(".showDateAllInfo").live('mouseover',function(){
	$("#dateAllInfoDiv [columnname]").each(function(){$(this).text("");});
	var xOffset = $(this).offset().left+$(this).width(); 
	var show_date = $(this).attr("date");
	var timePriceId = $(this).attr("timepriceid");
	var ebkProdProductStatus = $(this).attr("ebkProdProductStatus");
	$("#dataAllInfoTable").removeClass("weitijiao");
	$("#dataAllInfoTable").removeClass("daishenhe");
	$("#dataAllInfoTable").removeClass("jujue");
	if(timePriceId!="" && timePriceId!=undefined && timePriceId!="null" &&  ebkProdProductStatus=="UNCOMMIT_AUDIT"){
		$("#dataAllInfoTable").addClass("weitijiao");
	}else if(timePriceId!="" && timePriceId!=undefined && timePriceId!="null" && ebkProdProductStatus=="PENDING_AUDIT"){
		$("#dataAllInfoTable").addClass("daishenhe");
	}else if(timePriceId!="" && timePriceId!=undefined && timePriceId!="null" && ebkProdProductStatus=="REJECTED_AUDIT"){
		$("#dataAllInfoTable").addClass("jujue");
	}
	$("#dateAllInfoDiv [show_date]").text(show_date);
	$(this).find("[columnname][showold]").each(function(){
		var show_text = undefined!=$(this).text()&&null!=$(this).text()&&''!=$(this).text()?$(this).text():$(this).val();
		if("aheadHour"==$(this).attr("columnname") && null!=$(this).val() && ''!=$(this).val() ){
			show_text = parseFloat(parseInt($(this).val())/60).toFixed(2);
		}
		$("#dateAllInfoDiv [columnname="+$(this).attr("columnname")+"][showold]").text(show_text);
	});
	if($(this).index()>=5){
		xOffset = $(this).offset().left-$(this).width()-$(this).width(); 
	}
	$("#dateAllInfoDiv").show().css({'left':xOffset,'top':$(this).offset().top});
	});
	$(".showDateAllInfo").live('mouseout',function(){ 
		$("#dateAllInfoDiv [columnname]").each(function(){$(this).text("");});
		$("#dateAllInfoDiv").hide(); 
	});
});