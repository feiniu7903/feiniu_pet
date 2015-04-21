/**
 * @author shangzhengyuan
 */
function errorTimePriceMessage(errorMsg){
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
		case -102: return "价格、库存参数验证不通过";break;
		case -103: return "价格参数验证不通过";break;
		case -104: return "库存参数验证不通过";break;
		case -200: return "时间价格/库存信息不存在";break;
		case -79: return "不是未提交状态，不能修改";break;
		case -201: return "日期不存在";break;
		case -202: return "请检查参数，验证不通过";break;
		case -404: return "未找到要操作的信息";break;
		case -500: return "系统出错";break;
		default: "未知错误 代码="+errorMsg;
	}
}
$(function(){
	/*icon指上去提示文字*/
	$('.text_ts').ui('lvtip', {
		hovershow : 200
	});
	/*产品信息切换*/
	$('.xzxx_tab li').live('click',function() {
		var tabId = $(this).attr("id");
		var tab_class= $(this).attr("class");
		var toShowEbkProduct= $("#toShowEbkProduct").val();
		if("tab_this"==tab_class)return false;
		var prodId = $("#ebkProdProductId").val();
		if(undefined==prodId || null==prodId || ""==prodId)return false;
		if(tabId=="EBK_AUDIT_TAB_TIME_PRICE"){
			window.location.href=$("#basepathhiddenid").val()+"product/branch/ebkProdBranch.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct;
		}
		if(tabId=="EBK_AUDIT_TAB_RELATION"){
			window.location.href=$("#basepathhiddenid").val()+"product/relation/query.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct;
		}
		if(tabId=="EBK_AUDIT_TAB_BASE"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProductInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		if(tabId=="EBK_AUDIT_TAB_RECOMMEND"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdRecommendInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		if(tabId=="EBK_AUDIT_TAB_TRIP"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdTripInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		if(tabId=="EBK_AUDIT_TAB_COST"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdCostInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct;
		}
		if(tabId=="EBK_AUDIT_TAB_PICTURE"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdPictureInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		if(tabId=="EBK_AUDIT_TAB_TRAFFIC"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdTrafficInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		if(tabId=="EBK_AUDIT_TAB_OTHER"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdOtherInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
		//多行程管理
		if(tabId=="EBK_AUDIT_TAB_MULTITRIP"){
			window.location.href=$("#basepathhiddenid").val()+"ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId="+prodId+"&toShowEbkProduct="+toShowEbkProduct; 
		}
	});
	$(".jia_box samp").live("click",function(){
		$(this).parent().parent().remove($(this).parent());
	});
	$(":input,select,textarea").focus(function(){
		$("#errorMessageSpan").text("");
	});
	$(".checkedEbkProdPropertycancel").live("click",function(){
		$(".dialog-close").trigger("click");
	});
	function abroadProxyValidate(){
		var ebkproductviewtypehiddenid = $("#ebkproductviewtypehiddenid").val();
		if("ABROAD_PROXY"!=ebkproductviewtypehiddenid){
			return true;
		}
		var initialNum = $("[name='ebkProdProduct.initialNum']").val();
		var econtractTemplate = $("select[name='ebkProdProduct.econtractTemplate']:selected").val();
		var regionName = $("select[name='ebkProdProduct.regionName']:selected").val();
		var country = $("[name='ebkProdProduct.country']").val();
		var city = $("select[name='ebkProdProduct.city']:selected").val();
		var visaType = $("[name='ebkProdProduct.visaType']:checked").val();
		var isMultiJourney = $("[name='ebkProdProduct.isMultiJourney']:checked").val();
		if(undefined!=initialNum && ''!=initialNum && !/^\d+$/.test(initialNum)){
			$("#errorMessageSpan").text("最少成团人数请输入数字");
			return false;
		}else if(undefined!=initialNum && ''!=initialNum && /^\d+$/.test(initialNum) && parseInt(initialNum)>100){
			$("#errorMessageSpan").text("最少成团人数过大");
			return false;
		}/**
		if(undefined!=econtractTemplate && (null==econtractTemplate || ''==econtractTemplate)){
			$("#errorMessageSpan").text("请选择合同模板");
			return false;
		}*/
		if(undefined!=regionName &&(null==regionName || ''==regionName)){
			$("#errorMessageSpan").text("请选择区域划分");
			return false;
		}
		if(undefined!=country && (null==country || ''==country)){
			$("#errorMessageSpan").text("请选择签证国家");
			return false;
		}
		if(undefined!=city && (null==city || ''==city)){
			$("#errorMessageSpan").text("请选择送签城市");
			return false;
		}
		if($("[name='ebkProdProduct.visaType']").size()>0 && (null==visaType || ''==visaType)){
			$("#errorMessageSpan").text("请选择签证类型");
			return false;
		}
		var ebkId = $("#ebkProdProductId").val();
		if($("[name='ebkProdProduct.isMultiJourney']").size()>0 && (null==isMultiJourney || ''==isMultiJourney) && ''==ebkId){
			$("#errorMessageSpan").text("请选择是否多行程");
			return false;
		}
		return true;
	}
	$(".saveEbkProdProductBaseInfo").click(function(){
		var is_submit = true;
		is_submit = abroadProxyValidate();
		if(!is_submit)return false;
		$("select,:text,:input:hidden").each(function(){
			if($(this).attr("id")=="placeId"||$(this).attr("id")=="placeName"){
				if($("#placeAddBox span").length==0){
					is_submit = false;
					$("#errorMessageSpan").text( $(this).attr("messagetitle")+"不能为空");
					return false;
				}
			} else if((undefined == $(this).val() || null==$(this).val() || ''==$(this).val()) && undefined !=$(this).attr("messagetitle")){
				var a=$(this).attr("id");
				if (a.indexOf("search_prop_id_")<0) {
					is_submit = false;
					$("#errorMessageSpan").text( $(this).attr("messagetitle")+"不能为空");
					return false;
				}
			}
		});
		if($("#SUP_PERFORM_TARGET_tb tr").length<=1){
			is_submit = false;
			$("#errorMessageSpan").text("请选择履行方式");
			return false;
		}
		if($("#SUP_B_CERTIFICATE_TARGET_tb tr").length<=1){
			is_submit = false;
			$("#errorMessageSpan").text("请选择订单确认方式");
			return false;
		}
		if($("#SUP_SETTLEMENT_TARGET_tb tr").length<=1){
			is_submit = false;
			$("#errorMessageSpan").text("请选择结算信息");
			return false;
		}
		if(!is_submit){
			return false;
		}
		$("#errorMessageSpan").text("");
		var index_property = 0;
		$(".jia_box :hidden[name$='.modelPropertyId']").each(function(){
			$(this).attr({"name":"ebkProdProduct.ebkProdModelPropertys["+(index_property)+"].modelPropertyId"});
			$(this).next(":hidden[name$='.ebkPropertyType']").attr({"name":"ebkProdProduct.ebkProdModelPropertys["+(index_property)+"].ebkPropertyType"});
			index_property++;
		});
		index_property=0;
		$(".jia_box :hidden[name$='.placeId']").each(function(){
			$(this).attr({"name":"ebkProdProduct.ebkProdPlaces["+(index_property++)+"].placeId"});
		});
		$.ajax({ 
			type:"POST", 
			url:$("#basepathhiddenid").val()+'ebooking/product/saveEbkProduct.do?r='+Math.random(), 
			data:$("#confirm").serialize(), 
			async: false, 
			success:function (result) { 
				if(result.success=='true' || result.success){
					$("#ebkProdProductId").val(result.productId);
					var url2=$("#basepathhiddenid").val()+"ebooking/product/editEbkProductInit.do?ebkProdProductId="+result.productId+"&r="+Math.random();
					window.location.href=url2;
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					alert("产品保存失败 原因："+errorMessage);
				}
			} 
		}); 
	});
	$("select[name='ebkProdProduct.subProductType']").change(function(){
		var subProductType = $(this).val();
		if(undefined !=subProductType && null!=subProductType && "" != subProductType){
			$.ajax({ 
				type:"POST", 
				url:$("#basepathhiddenid").val()+'product/showEbkProdModelPropertyList.do', 
				data:{subProductType:subProductType}, 
				dataType:"html",
				success:function (result) { 
					$("#showEbkProdPropertyListId").html(result);
				} 
			}); 
			
		}
	});
	/*旅游区域，游玩特色，产品主题，表单弹出层内容*/
	$('.js_property').live('click',function(){
		var checkedId = '';
		$(this).next(".jia_box").find("span").each(function(){
			checkedId = checkedId + $(this).attr("id")+"--";
		});
		var data = {"firstModelId": $(this).attr("firstModelId"),"secondModelId": $(this).attr("secondModelId"),"subProductType":$("#subProductType").val(),"checkedPropertyIds":checkedId};
		var titleName = $(this).attr("showtitle");
		var editEbkProd={
				url : $("#basepathhiddenid").val()+"ebooking/product/showPropertyList.do",
				data :data,
				type : 'POST',
				dataType : "html",
				success : function(data) {
					$.dialog({
						title: titleName,
						content: data,
						width:800
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editEbkProd);
	});
	/*选中产品属性 */
	$(".checkedEbkProdProperty").live("click",function(){
		var propertyList = $("#show_property_div :checked");
		if(undefined==propertyList ||  null==propertyList){$(".dialog-close").trigger("click");return false;}
		var firstModelId=propertyList.attr("firstModelId");
		var secondModelId =propertyList.attr("secondModelId");
		$(".jia_box[firstModelId="+firstModelId+"][secondModelId="+secondModelId+"]").empty();
		propertyList.each(function(){
			var span_html = $("<span/>");
			span_html.attr({"class":"ebkProdModelPropertys","id":$(this).val()});
			var hidden_id = $("<input type='hidden'/>");
			var hiiden_second = $("<input type='hidden'/>");
			hidden_id.attr({"name":"ebkProdModelPropertys.modelPropertyId"}).val($(this).val());
			hiiden_second.attr({"name":"ebkProdModelPropertys.ebkPropertyType"}).val(secondModelId);
			span_html.append(hidden_id).append(hiiden_second).append($(this).attr("title")).append(" <a class='colsecurrentspan'><font color='red'>X</font></a>");
			$(".jia_box[firstModelId="+firstModelId+"][secondModelId="+secondModelId+"]").append(span_html);
		});
		$(".dialog-close").trigger("click");
	});
	$(".colsecurrentspan").live("click",function(){
		$(this).parent().empty().remove();
	});
	

	/*基本信息，对象选择*/
	$('.js_target').live('click', function() {
		var tt = $(this).attr("tt");
		if($.trim(tt) == ''&&$.trim($("#supplierId").val()) == ''){
			alert("要更换的对象不存在");
			return false;
		}
		var titleName = $(this).prev().text();
		var editEbkProd={
				url : $("#basepathhiddenid").val()+"/ebooking/product/showTargetList.do",
				data :{supplierId:$("#supplierId").val(),targetType:tt},
				type : 'POST',
				dataType : "html",
				success : function(data) {
					$.dialog({
						title: titleName,
						content: data,
						width:760
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editEbkProd);
	});
	$("a.choose").live("click", function(){
		
		var tt=$(this).attr("tt");
		var result=$(this).attr("result");
		if($.trim(result)==''){
			alert("对象不存在");
			return false;
		}
		if($.trim(tt)==''){
			alert("选择的对象不存在");
			return false;
		}
		
		var $table=$("#"+tt+"_tb");
		$table.find("tr:gt(0)").remove();
		var showData = new Array;
		showData = result.split("--");

		var $tr=$("<tr/>");
		$tr.attr("id",tt+"_"+showData[0]);
		
		for(var i=0;i<showData.length;i++){
			var $td=$("<td/>");
			if(i==0){
				if(tt=='SUP_PERFORM_TARGET'){
					$td.html(showData[i]+"<input name='ebkProdProduct.ebkProdTargets[0].targetId' value='"+showData[0]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[0].targetName' value='"+showData[1]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[0].targetType' value='"+tt+"' type='hidden'>");
				}
				if(tt=='SUP_B_CERTIFICATE_TARGET'){
					$td.html(showData[i]+"<input name='ebkProdProduct.ebkProdTargets[1].targetId' value='"+showData[0]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[1].targetName' value='"+showData[1]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[1].targetType' value='"+tt+"' type='hidden'>");
				}
				if(tt=='SUP_SETTLEMENT_TARGET'){
					$td.html(showData[i]+"<input name='ebkProdProduct.ebkProdTargets[2].targetId' value='"+showData[0]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[2].targetName' value='"+showData[1]+"' type='hidden'>"+"<input name='ebkProdProduct.ebkProdTargets[2].targetType' value='"+tt+"' type='hidden'>");
				}
			}else{
				$td.html(showData[i]);
			}
			
			$tr.append($td);
			$table.append($tr);
		}
		$(".dialog-close").trigger("click");
		});
	//发车信息
	$(".saveEbkProdTrafficInit").click(function(){
		var is_submit = true;
		$(":text[name$=content]").each(function(){
			if(undefined == $(this).val() || null==$(this).val() || ''==$(this).val()){
				is_submit = false;
				$("#errorMessageSpan").text("请填写第"+($(":text[name$=content]").index($(this))+1)+"行发车信息的内容");
				return false;
			}else if($(this).val().length>50){
				is_submit = false;
				$("#errorMessageSpan").text("第"+($(":text[name$=content]").index($(this))+1)+"行发车信息过长");
				return false;
			}
		});
		if(!is_submit){
			return false;
		}
		$.ajax({ 
			type:"POST", 
			url:$("#basepathhiddenid").val()+'ebooking/product/saveEbkProdTrafficContent.do', 
			data:$("#confirm").serialize(), 
			async: false, 
			success:function (result) {  
				if(result.success=='true' || result.success){
					alert("保存成功！");
					$("#confirm").attr("action",$("#basepathhiddenid").val() +"/ebooking/product/editEbkProdTrafficInit.do");
					$("#confirm").submit();
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					alert("产品保存失败 原因："+errorMessage);
				}
			} 
		}); 
	});
	//其它条款
	$(".saveEbkProdOtherInit,.saveEbkProdCost,.saveEbkProdRecommendInit").click(function(){
		var is_submit = true;
		var messagetitle;
		$("textarea[name$=content]").each(function(){
			messagetitle = $(this).attr("messagetitle");
			if ('购物说明' != messagetitle && '推荐项目' != messagetitle && '产品特色' != messagetitle) {
				if(undefined == $(this).val() || null==$(this).val() || ''==$(this).val()){
					is_submit =false;
					$("#errorMessageSpan").text("  "+messagetitle+"不能为空");
					return false;
				}
			}
		});
		if(!is_submit){
			return false;
		}
		$.ajax({ 
			type:"POST", 
			url:$("#basepathhiddenid").val()+'/ebooking/product/saveEbkProdContent.do', 
			data:$("#confirm").serialize(), 
			async: false, 
			success:function (result) {
				if(result.success=='true' || result.success){
					alert("保存成功！");
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					alert("产品保存失败 原因："+errorMessage);
				}
			} 
		}); 
	});
	//多行程--费用描述
	$(".saveEbkMultiCost").click(function(){
		var is_submit = true;
		var messagetitle;
		$("textarea[name$=content]").each(function(){
			if(undefined == $(this).val() || null==$(this).val() || ''==$(this).val()){
				is_submit =false;
				messagetitle = $(this).attr("messagetitle");
				$("#errorMessageSpan").text("  "+messagetitle+"不能为空");
				return false;
			}
		});
		if(!is_submit){
			return false;
		}
		
		document.getElementById("confirm").submit();
		/*$.ajaxSubmit({ 
			type:"POST", 
			url:'${contextPath }/ebooking/product/saveEbkMultiContent.do', 
			//data:$("#confirm").serialize(), 
			//async: false, 
			success:function (result) {
				if(result.success=='true' || result.success){
					alert("保存成功！");
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					alert("产品保存失败 原因："+errorMessage);
				}
			} 
		});*/
	});
	/*编辑图片*/
	$('.js_min_edit').live('click',function(){ 
		var pictureId = $(this).attr("id");
		if('undefined'==pictureId || null==pictureId || ''==pictureId){
			alert("未找到图片值，编辑失败");
			return false;
		}
		var imgType = $(this).parent().parent().attr("data-type");
		var img_width = 530;
		var img_height=440;
		var imgSizeStyle = "M";
		if(imgType=='EBK_PROD_PRODUCT_BIG'){
			img_width = 1200;
			img_height=630;
			imgSizeStyle = "L";
		}else if(imgType=='EBK_PROD_PRODUCT_SMALL'){
			var img_width = 600;
			var img_height=210;
			imgSizeStyle = "S";
		}
		var data = {pictureId:pictureId,ebkProdProductId:$("#ebkProdProductId").val(),imgSizeStyle:imgSizeStyle};
		var editPic={
				url : $("#basepathhiddenid").val()+'/ebooking/product/editEbkProdPicInit.do',
				data :data,
				type : 'POST',
				dataType : "html",
				success : function(data) {
					$.dialog({
						title: '图片编辑',
						content: data,
						width:img_width
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editPic);
	});
});

function openTemplate(target) {
	var data = {"templateTarget": target};
	var titleName = "查看模板";
	var loadTemplate={
			url : $("#basepathhiddenid").val()+"/ebooking/product/loadTemplate.do",
			data :data,
			type : 'POST',
			dataType : "html",
			success : function(data) {
				$.dialog({
					title: titleName,
					content: data,
					width:800
				});
			},
			error : function() {
			}
		}; 
	$.ajax(loadTemplate);
}
