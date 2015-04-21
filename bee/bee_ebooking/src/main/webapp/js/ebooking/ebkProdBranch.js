/**
 * @author shangzhengyuan
 */
var show_dialog = true;
function is_number(num){
	if(num==null || num==''){
		return true;
	}
	if(/^\d+$/.test(num)){
		return true;
	}
	return false;
}
function errorTimePriceMessage(errorMsg){
	errorMsg=parseInt(errorMsg);
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
		case -79: return "待审核中，不能修改";break;
		case -201: return "日期不存在";break;
		case -202: return "请检查参数，验证不通过";break;
		case -404: return "未找到要操作的信息";break;
		case -500: return "系统出错";break;
		default: "未知错误 代码="+errorMsg;
	}
}

$(function(){
	$("#ebkProdBranch_branchType").live("change",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var oldType = $("#prod_branch_old_type").val();
		var newType = $(this).val();
		if('VIRTUAL'==oldType){
			alert("共享库存不允许修改成其它类型");
			$("#ebkProdBranch_branchType").val('VIRTUAL');
			return;
		}
		if(null!=oldType&&''!=oldType&&'VIRTUAL'!=oldType&&'VIRTUAL'==newType){
			alert("不允许修改成共享库存");
			$("#ebkProdBranch_branchType").val(oldType);
			return;
		}
		if(null!=$(this).val()&&''!=$(this).val()){
			if('VIRTUAL'==$(this).val()){
				var searchEbkProdBranch = {
						url : $("#basepathhiddenid").val()+"/product/branch/searchEbkProdBranch.do",
						type : 'POST',
						data :{ebkProdProductId:ebkProdProductId,prodBranchType:'VIRTUAL'},
						dataType : "json",
						success : function(data) {
							if(data.success){
								$("#branch_name").val('共享库存');
								$("#branch_name").attr("readonly","true");
							}else{
								alert(data.message);
								$("#ebkProdBranch_branchType").val('');
							}
						}
					};
				$.ajax(searchEbkProdBranch);
			}else{
				$("#branch_name").removeAttr("readonly");
				$("#branch_name").val($(this).children(":selected").text());
			}
		}
	});
	$("#adult_quantity,#child_quantity").blur(function(){if(!is_number($(this).val())){alert("请输入正整数");}});
	
	$(".ebkprodbranchsavebtn").live("click",function(){
		var prod_branch_id = $("#prod_branch_id").val();
		var branch_type =$("#ebkProdBranch_branchType").val();
		var branch_name =$("#branch_name").val();
		var adult_quantity = $("#adult_quantity").val();
		var child_quantity = $("#child_quantity").val();
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		if(null==branch_type || ''==$.trim(branch_type)){
			$("#saveebkprodbranerrormessage").html("请选择产品类型");
			return false;
		}
		if(null==branch_name || ''==$.trim(branch_name)){
			$("#saveebkprodbranerrormessage").html("请输入价格类别名称");
			return false;
		}
		if(branch_name.length>100){
			$("#saveebkprodbranerrormessage").html("价格类别名称过长");
			return false;
		}
		if(null==adult_quantity || ''==$.trim(adult_quantity)){
			adult_quantity = 0;
		}else if(!is_number(adult_quantity)){
			$("#saveebkprodbranerrormessage").html("成人数请输入正整数");
			return false;
		}else if(adult_quantity>1000){
			$("#saveebkprodbranerrormessage").html("成人数过大");
			return false;
		}
		if(null==child_quantity || ''==$.trim(child_quantity)){
			child_quantity = 0;
		}else if(!is_number(child_quantity)){
			$("#saveebkprodbranerrormessage").html("儿童数请输入正整数");
			return false;
		}else if(adult_quantity>1000){
			$("#saveebkprodbranerrormessage").html("儿童数过大");
			return false;
		}
		if(adult_quantity==0 && child_quantity==0){
			$("#saveebkprodbranerrormessage").html("请输入成人数或儿童数");
			return false;
		}
		var ebkProdBranch={"ebkProdBranch.prodBranchId":prod_branch_id,"ebkProdBranch.branchType":branch_type,"ebkProdBranch.branchName":branch_name,"ebkProdBranch.adultQuantity":adult_quantity,"ebkProdBranch.childQuantity":child_quantity,"ebkProdBranch.prodProductId":ebkProdProductId,"ebkProdBranch.defaultBranch":$(":hidden[name='ebkProdBranch.defaultBranch']").val(),ebkProdProductId:ebkProdProductId,prodBranchId:prod_branch_id};
		var url = $("#basepathhiddenid").val()+"/product/branch/updateEbkProdBranch.do";
		if(null==prod_branch_id || $.trim(prod_branch_id)==''){
			url = $("#basepathhiddenid").val()+"/product/branch/insertEbkProdBranch.do";
		}
		var queryebkprodbranch={
				url:$("#basepathhiddenid").val()+"/product/branch/queryEbkProdBranch.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					$("#queryebkprodbranchdiv").html(data);
				},
				error : function() {
				}
		}; 
		var saveebkprodbranch = {
				url : url,
				type : 'POST',
				data :ebkProdBranch,
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("保存成功");
						$(".dialog-close").trigger("click");
						$.ajax(queryebkprodbranch);
						$(".ebkProdBranchListNotEmpty").show();
						$("#queryProdTimePriceStockTbody").hide();
					}else{
						var errorMessage = errorTimePriceMessage(data.message);
						$("#saveebkprodbranerrormessage").text(errorMessage);
					}
				},
				error : function() {
					alert("保存失败");
				}
			};
		$.ajax(saveebkprodbranch);
	});
	$(".ebkprodbranchnew").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var editEbkProdBranch={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdBranch.do",
				data :{ebkProdProductId:ebkProdProductId},
				type : 'POST',
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: ' 新增产品类别',
						content: data,
						width:760
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editEbkProdBranch);
	});

	
	/*新增库存弹出层内容*/
	$('.js_xzkc').live('click',function(){ 
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		  
		var js_xzkc={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,ebkProductViewType:ebkProductViewType,newPriceStock:0},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '新增价格/库存',
						content: data,
						width:760
					});
				},
				error : function() {
				}
			}; 
		$.ajax(js_xzkc);
	});
	/*修改库存弹出层内容*/
	$('.js_xgjgkc').live('click',function(){ 
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var js_xzkc={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,ebkProductViewType:ebkProductViewType,newPriceStock:3},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '修改价格/库存',
						content: data,
						width:760
					});
				},
				error : function() {
				}
			}; 
		$.ajax(js_xzkc);
	});
	/*修改价格弹出层内容*/
	$('.js_xgjg').live('click',function(){ 
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		
		var js_xgjg={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,ebkProductViewType:ebkProductViewType,newPriceStock:1},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '修改已通过审核的价格',
						content: data,
						width:800
					});
				},
				error : function() {
				}
			}; 
		$.ajax(js_xgjg);
	});
	
	/*修改库存弹出层内容*/
	$('.js_xgkc').live('click',function(){ 
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var js_xgkc={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,ebkProductViewType:ebkProductViewType,newPriceStock:2},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '修改已通过审核的库存',
						content: data,
						width:800
					});
				},
				error : function() {
				}
			}; 
		$.ajax(js_xgkc);
	});
	function update_input_status(checkbox){
		var rowid = checkbox.attr("rowid"); 
		var check_status = checkbox.attr("checked");
		if(undefined==check_status)check_status=false;
		checkbox.parent().parent().find(":input[rowid="+rowid+"]:not(.branchcheckbox),select").each(function(){
			$(this).attr("disabled",!check_status);
		});
		if(!check_status){
			checkbox.parent().parent().addClass("nosave");
		}else{
			checkbox.parent().parent().removeClass("nosave");
		}
	}
	$("#branchcheckboxall").live("click",function(){
		var all_box = $(this).attr("checked");
		if("true"==all_box||"checked"==all_box){
			all_box=true;
			$(this).attr("checked",all_box);
		}else{
			all_box=false;
			$(this).removeAttr("checked");
		}
		if(undefined==all_box)all_box=false;
		$(".branchcheckbox").each(function(){$(this).attr("checked",all_box);update_input_status($(this));});
		$(":checkbox.branchcheckbox:checked").each(function(checked_index){
			var index = $(this).attr("trindexnumber");
			var parent_tr = $(".editProdMesgTr"+index);
			parent_tr.find(":input,select").each(function(){
				var current_name = $(this).attr("name");
				current_name = current_name.replace(/[^\[]+(?=\])/g,checked_index);
				$(this).attr({"name":current_name});
			});
		});
	});
	$(".branchcheckbox").live("click",function(){
		var all_box = $(this).attr("checked");
		if("true"==all_box||"checked"==all_box){
			all_box=true;
			$(this).attr("checked",all_box);
		}else{
			all_box=false;
			$(this).removeAttr("checked");
		}
		update_input_status($(this));
		if($(".branchcheckbox:not(:checked)").size()>0){
			$("#branchcheckboxall").removeAttr("checked");
		}else{
			$("#branchcheckboxall").attr("checked",true);
		}
	});
	$("#timepriceweekall").live("click",function(){
		var all_box = $(this).attr("checked");
		if(undefined==all_box)all_box=false;
		$(":input[name='paramModel.weeks']").attr("checked",all_box);
	});
	$(":input[name='paramModel.weeks']:not(#timepriceweekall)").live("click",function(){
		if($(":input[name='paramModel.weeks']:not(:checked)").size()>0){
			$("#timepriceweekall").removeAttr("checked");
		}else{
			$("#timepriceweekall").attr("checked",true);
		}
	});
	$("#ebkProdTimePriceForm").find(":input,select").live("focus",function(){
		$("#validateErrorMesssage").text("");
	});
	
	$(".newpricestock,.updateprice,.updatestock").live("click",function(){
		$("#validateErrorMesssage").text("");
		var is_submit = true;
		var beginDate = $(":text[name='paramModel.beginDate']").val();
		var endDate = $(":text[name='paramModel.endDate']").val();
		var weeks = $(":checkbox[name='paramModel.weeks']:checked");
		var aheadHourDay = $(":text[name='paramModel.aheadHourDay']").val();
		var aheadHour = $(":text[name='paramModel.aheadHour']").val();
		var aheadHourSecend = $(":text[name='paramModel.aheadHourSecend']").val();
		var forbiddenSell = $(":radio[name='paramModel.forbiddenSell']").val();
		var cancelStrategy = $(":radio[name='paramModel.cancelStrategy']");
		var breackfastCount = $("[name='paramModel.breackfastCount']:selected").val();
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var type = $("#type").val();
		if (type == 'w') {
			if(null==beginDate || ''==beginDate){
				$("#validateErrorMesssage").text("开始日期不能为空");
				is_submit = false;
				return false;
			}
			if(null==endDate || ''==endDate){
				$("#validateErrorMesssage").text("结束日期不能为空");
				is_submit = false;
				return false;
			}
			var currentDate = new Date();
			currentDate.setHours(0);
			currentDate.setMinutes(0);
			currentDate.setSeconds(0);
			currentDate.setMilliseconds(0);
			if(Date.parse(beginDate.replace(/-/g,"/"))<currentDate){
				$("#validateErrorMesssage").text("开始日期请输入大于今天的日期");
				is_submit = false;
				return false;
			}
			if(Date.parse(endDate.replace(/-/g,"/"))<currentDate ){
				$("#validateErrorMesssage").text("结束日期请输入大于今天的日期");
				is_submit = false;
				return false;
			}
			if(Date.parse(beginDate.replace(/-/g,"/"))>Date.parse(endDate.replace(/-/g,"/"))){
				$("#validateErrorMesssage").text("开始日期要大于结束日期");
				is_submit = false;
				return false;
			}
			beginDate = Date.parse(beginDate.replace(/-/g,"/"));
			endDate = Date.parse(endDate.replace(/-/g,"/"));
			if((beginDate-currentDate)/1000/3600/24>365){
				$("#validateErrorMesssage").text("开始日期为今后的一年内");
				is_submit = false;
				return false;
			}
			if((endDate-currentDate)/1000/3600/24>365){
				$("#validateErrorMesssage").text("结束日期为今后的一年内");
				is_submit = false;
				return false;
			}
			if((endDate-beginDate)/1000/3600/24>366){
				$("#validateErrorMesssage").text("时间段为一年内");
				is_submit = false;
				return false;
			}
			if(undefined==weeks || (undefined!=weeks && weeks.size()==0)){
				$("#validateErrorMesssage").text("请选择适用星期");
				is_submit = false;
				return false;
			}
		} else {
			if($("#selDate").text().length==18){
				$("#validateErrorMesssage").text("日期不能为空");
				is_submit = false;
				return false;
			}
		}
		
		
		if(undefined!= aheadHourDay && (null==aheadHourDay || ''==aheadHourDay) && (null==aheadHour || ''==aheadHour) && (null==aheadHourSecend || ''==aheadHourSecend)){
			$("#validateErrorMesssage").text("请输入提前预订小时数");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHourDay && ((null!=aheadHourDay && ''!=aheadHourDay &&  !/^\d+$/.test(aheadHourDay)) || (null!=aheadHour && ''!=aheadHour && !/^\d+$/.test(aheadHour)) || (null!=aheadHourSecend && ''!=aheadHourSecend && !/^\d+$/.test(aheadHourSecend)))){
			$("#validateErrorMesssage").text("提前预订小时数请输入正整数");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHour && aheadHour>23){
			$("#validateErrorMesssage").text("提前预订小时数小时请输入0-23的数");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHourSecend && aheadHourSecend>59){
			$("#validateErrorMesssage").text("提前预订小时数分钟请输入0-59的数");
			is_submit = false;
			return false;
		}
		if(undefined!=forbiddenSell && (null==forbiddenSell || ''==forbiddenSell)){
			$("#validateErrorMesssage").text("请选择是否关班");
			is_submit = false;
			return false;
		}
		if(undefined!=cancelStrategy && cancelStrategy.size()>0){
			var is_cancelStrategy = false;
			cancelStrategy.each(function(){if($(this).attr("checked")){
				is_cancelStrategy = true;
			}});
			if(!is_cancelStrategy){
				$("#validateErrorMesssage").text("请选择退改策略");
				is_submit = false;
				return false;
			}
		}
		if(undefined!=breackfastCount && (null==breackfastCount || ''==breackfastCount)){
			$("#validateErrorMesssage").text("请选择早餐份数");
			is_submit = false;
			return false;
		}
		if($("#ebkProdTimePriceStockTbody").find("tr:not(.nosave)").size()==0){
			$("#validateErrorMesssage").text("请选择要编辑的产品类别");
			is_submit = false;
			return false;
		}
		$("#ebkProdTimePriceStockTbody").find("tr:not(.nosave)").each(function(){
			var index_tr=$(this).attr("trindexnumber");
			var branchName = $(this).children("td").eq(1).text();
			var settlementPrice=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].settlementPrice']").val();
			var marketPrice=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].marketPrice']").val();
			var stockType=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].stockType']").val();
			var dayStock=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].dayStock']").val();
			var resourceConfirm=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].resourceConfirm']");
			var overSale=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].overSale']");
			var branchTypeHid = $("#prodBranchTypeHid"+index_tr).val();
			if("VIRTUAL"!=branchTypeHid){
				if(undefined!=settlementPrice && (null==settlementPrice || ''==settlementPrice)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】请输入结算价");
					is_submit = false;
					return false;
				}else if(undefined!=settlementPrice && !/^\d+$/.test(settlementPrice)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】结算价请输入正整数");
					is_submit = false;
					return false;
				}else if(undefined!=settlementPrice && settlementPrice>1000000){
					$("#validateErrorMesssage").text("类别【"+branchName+"】结算价过大");
					is_submit = false;
					return false;
				}
				if(undefined!=marketPrice && (null==marketPrice || ''==marketPrice)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】请输入门市价");
					is_submit = false;
					return false;
				}else if(undefined!=marketPrice && !/^\d+$/.test(marketPrice)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】门市价请输入正整数");
					is_submit = false;
					return false;
				}else if(undefined!=marketPrice && marketPrice>1000000){
					$("#validateErrorMesssage").text("类别【"+branchName+"】门市价过大");
					is_submit = false;
					return false;
				}
				if(ebkProductViewType=='SURROUNDING_GROUP'||ebkProductViewType=='DOMESTIC_LONG'){
					var price=  $(this).find("[name='paramModel.priceStockSimples["+index_tr+"].price']").val();
					if(undefined!=price && (null==price || ''==price)){
						$("#validateErrorMesssage").text("类别【"+branchName+"】请输入销售价");
						is_submit = false;
						return false;
					}else if(undefined!=price && !/^\d+$/.test(price)){
						$("#validateErrorMesssage").text("类别【"+branchName+"】销售价请输入正整数");
						is_submit = false;
						return false;
					}else if(undefined!=price && price>1000000){
						$("#validateErrorMesssage").text("类别【"+branchName+"】销售价过大");
						is_submit = false;
						return false;
					}
				}
			}
			
			if(undefined!=stockType && null==stockType && ''==stockType){
				$("#validateErrorMesssage").text("类别【"+branchName+"】请选择设置库存方式");
				is_submit = false;
				return false;
			}
			if(undefined!=stockType && null!=stockType && "UNLIMITED_STOCK"!=stockType){
				if(undefined!=dayStock && (null==dayStock || ''==dayStock)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】请输入库存量");
					is_submit = false;
					return false;
				}else if(undefined!=dayStock && !/^\d+$/.test(dayStock)){
					$("#validateErrorMesssage").text("类别【"+branchName+"】库存量请输入正整数");
					is_submit = false;
					return false;
				}else if(undefined!=dayStock && dayStock>1000000){
					$("#validateErrorMesssage").text("类别【"+branchName+"】库存量过大");
					is_submit = false;
					return false;
				}
			}
			if(undefined!=resourceConfirm && resourceConfirm.size()>0){
				var is_resourceConfirm = false;
				resourceConfirm.each(function(){if($(this).attr("checked")){
					is_resourceConfirm = true;
				}});
				if(!is_resourceConfirm){
					$("#validateErrorMesssage").text("类别【"+branchName+"】请选择是否资源审核");
					is_submit = false;
					return false;
				}
			}
			if(undefined!=overSale && overSale.size()>0){
				var is_overSale = false;
				overSale.each(function(){if($(this).attr("checked")){
					is_overSale = true;
				}});
				if(!is_overSale){
					$("#validateErrorMesssage").text("类别【"+branchName+"】请选择是否超卖");
					is_submit = false;
					return false;
				}
			}
		});
		
		if(!is_submit){
			return false;
		}
		var ebkProdProductId = $("#ebkProdTimePriceForm").find(":hidden[name='ebkProdProductId']").val();
		if(undefined == ebkProdProductId)
		$("#ebkProdTimePriceForm").append("<input type='hidden' name='ebkProdProductId' value='"+$("#ebkProdProductId").val()+"' />");
		else
			$("#ebkProdTimePriceForm").find(":hidden[name='ebkProdProductId']").val($("#ebkProdProductId").val());
		$(":checkbox.branchcheckbox:checked").each(function(checked_index){
			var index = $(this).attr("trindexnumber");
			var parent_tr = $(".editProdMesgTr"+index);
			parent_tr.find(":input,select").each(function(){
				var current_name = $(this).attr("name");
				current_name = current_name.replace(/[^\[]+(?=\])/g,checked_index);
				$(this).attr({"name":current_name});
			});
		});
		var checked_count = $(":checkbox.branchcheckbox:checked").size();
		$(":checkbox.branchcheckbox:not(:checked)").each(function(checked_index_new){
			var checked_index =checked_index_new+checked_count;
			var index = $(this).attr("trindexnumber");
			var parent_tr = $(".editProdMesgTr"+index);
			parent_tr.find(":input,select").each(function(){
				var current_name = $(this).attr("name");
				current_name = current_name.replace(/[^\[]+(?=\])/g,checked_index);
				$(this).attr({"name":current_name});
			});
		});
				
		var type = document.getElementById("selDate");
		  for(var i = 0;i<type.options.length;i++){
			  type.options[i].selected = 'selected';
		  }
		
		$.ajax({
			url:$("#ebkProdTimePriceForm").attr("action"),
			type:'POST',
			dataType:"json",
			data:$("form").serialize(),
			success : function(data) {
				if(data.success){
					alert("时间价格库存编辑成功");
					$(".dialog-close").trigger("click");
					$("#queryProdTimePriceStockTbody").hide();
				}else{
					var errorMessage = errorTimePriceMessage(data.message);
					var index = data.index;
					var column = data.column;
					if(undefined!=index && undefined!=column){
						var error_td = $(":input[name^='paramModel.priceStockSimples'][name$="+column+"]").parent();
						var error_tr = error_td.parent();
						var error_index = error_td.index(error_tr);
						var branchName = error_tr.children("td").eq(1).text();
						var culomnName = $(".timepricequerythead").eq(error_index).text();
						$("#validateErrorMesssage").text("类别 【"+branchName+"】"+culomnName+errorMessage);
					}else if(undefined!=column){
						var columnName = $(":text[name='paramModel."+column+"']").attr("errorlabel");
						if(undefined == columnName && ''==columnName){
							columnName = $(":text[name='paramModel."+column+"']").parent().prev().text();
						}
						$("#validateErrorMesssage").text(columnName+" "+ errorMessage);
					}else{
						$("#validateErrorMesssage").text(errorMessage);
					}
				}
			},
			error : function() {
			}
		});
	});
	$(".ebkprodbranchupdate").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var prodBranchId = $(this).parent("td").attr("prodbranchid");
		var branchName = $(this).parent().parent().children("td").eq(1).text();
		//var branchtype = $(this).parent("td").attr("branchtype");
		var editEbkProdBranch={
				url : $("#basepathhiddenid").val()+"/product/branch/editEbkProdBranch.do",
				type : 'POST',
				data :{prodBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '修改产品类别>'+branchName,
						content: data,
						width:800
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editEbkProdBranch);
	});
	$(".ebkProdbranchFirst").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var prodBranchId = $(this).parent("td").attr("prodbranchid");
		var branchName = $(this).parent().parent().children("td").eq(1).text();
		var defaultbranch = $(this).attr("defaultbranch");
		if("true"==defaultbranch){
			alert("此类别已是主类别");
			return false;
		}
		var queryebkprodbranch={
				url:$("#basepathhiddenid").val()+"/product/branch/queryEbkProdBranch.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					$("#queryebkprodbranchdiv").html(data);
				},
				error : function() {
				}
		}; 
		var r=confirm("确认设置 类别 "+branchName+" 为主类别");
		var ebkprodbranchdelete={
				url : $("#basepathhiddenid").val()+"/product/branch/updateEbkProdBranchFirst.do",
				type : 'POST',
				data :{prodBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert(branchName+"设置成功");
						$.ajax(queryebkprodbranch);
						$("div.queryebkprodbranchdiv").empty();
					}else{
						var errorMessage = errorTimePriceMessage(data.message);
						alert(errorMessage);
					}
				},
				error : function() {
				}
			}; 
		if(r)
		$.ajax(ebkprodbranchdelete);
	});
	$(".setEbkVirtualBranch").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var prodBranchId = $(this).parent("td").attr("prodbranchid");
		var editEbkProdBranch={
				url : $("#basepathhiddenid").val()+"/product/branch/setEbkVirtualBranchInit.do",
				type : 'POST',
				data :{prodBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '设置库存共享类别',
						content: data,
						width:800
					});
				},
				error : function() {
				}
			}; 
		$.ajax(editEbkProdBranch);
	});

	$(".checkedVirtualBranchOK").live("click",function(){
		var ebkProdProductVirtualId = $("#ebkProdProductVirtualId").val();
		var prodVirtualBranchId = $("#prodVirtualBranchId").val();
		var indexs = 0;
		var checkedBranchIds = "";
		$(":checkbox.virtualBranchcheckbox:checked").each(function(checked_index){
			indexs++;
			var id = $(this).val();
			checkedBranchIds = checkedBranchIds + id + ",";
		});
		if(indexs<2){
			alert("最少需要共享2个类别的库存");
			return false;
		}else{
			checkedBranchIds = checkedBranchIds.substr(0,checkedBranchIds.length-1);
		}
		var checkedVirtualBranchDialog={
				url:$("#basepathhiddenid").val()+"/product/branch/saveEbkVirtualBranch.do",
				type : 'POST',
				data :{prodBranchId:prodVirtualBranchId,ebkProdProductId:ebkProdProductVirtualId,virtualBranchIds:checkedBranchIds},
				dataType : "html",
				success : function(data) {
					alert("设置共享库存类别成功");
					$(".dialog-close").trigger("click");
				},
				error : function() {
				}
		}; 
		$.ajax(checkedVirtualBranchDialog);
		
	});
	
	$(".checkedVirtualBranchCancel").live("click",function(){
		$(".dialog-close").trigger("click");
	});
	
	$(".ebkprodbranchdelete").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var prodBranchId = $(this).parent("td").attr("prodbranchid");
		var defaultbranch = $(this).attr("defaultbranch");
		var branchName = $(this).parent().parent().children("td").eq(1).text();
		if("true"==defaultbranch){
			alert("此类别是主类别，不能删除");
			return false;
		}
		var queryebkprodbranch={
				url:$("#basepathhiddenid").val()+"/product/branch/queryEbkProdBranch.do",
				type : 'POST',
				data :{ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					$("#queryebkprodbranchdiv").html(data);
				},
				error : function() {
				}
		}; 
		var r=confirm("确认删除价格类别 "+branchName);
		var ebkprodbranchdelete={
			url : $("#basepathhiddenid").val()+"/product/branch/deleteEbkProdBranch.do",
			type : 'POST',
			data :{prodBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert(branchName+"删除成功");
					$.ajax(queryebkprodbranch);
					$("div.queryebkprodbranchdiv").empty();
					$("#queryProdTimePriceStockTbody").hide();
					if($(".kcwh_table tr").size()==3){
						$(".ebkProdBranchListNotEmpty").hide();
					}
				}else{
					var errorMessage = errorTimePriceMessage(data.message);
					alert(errorMessage);
				}
			},
			error : function() {
			}
		}; 
		
		var queryvirtualBranch={
			url:$("#basepathhiddenid").val()+"/product/branch/queryVirtualBranch.do",
			type : 'POST',
			data :{prodBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
			dataType : "json",
			success : function(data) {
				if(data.success){
					$.ajax(ebkprodbranchdelete);
				}else{
					if('false'==data.message){
						alert(branchName+"已经设置过共享库存，请清除共享关系再行删除");
					}else{
						var errorMessage = errorTimePriceMessage(data.message);
						alert(errorMessage);
					}
				}
			},
			error : function() {
			}
		}; 
		
		if(r)
			$.ajax(queryvirtualBranch);
	});
	$(".viewprodbranchpricestore").live("click",function(){
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var prodBranchId = $(this).parent("td").attr("prodbranchid");
		var branchName = $(this).parent().parent().children("td").eq(1).text();
		var viewprodbranchpricestore={
				url:$("#basepathhiddenid").val()+"/product/branch/queryEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdBranchId:prodBranchId,ebkProductViewType:ebkProductViewType,ebkProdProductId:ebkProdProductId,branchName:branchName,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					/**
					$.dialog({
						title: '产品类别 【'+branchName+'】时间价格库存表',
						content: data,
						width:800
					});
					*/
					$("#queryProdTimePriceStockTbody").show().empty().append("<div data-title=\"title\" class=\"dialog-header\">"+branchName+" 时间价格库存表</div>").append(data);
					$(":hidden[name=updated][value='true']").each(function(){
						$(this).parentsUntil("table").find(".plug_calendar_day").attr({"color":"red"});
					});
				},
				error : function() {
				}
		}; 
		$.ajax(viewprodbranchpricestore);
	});
	$(".viewprodtimepricebtn").live("click",function(){
		var relationProduct =$(":hidden[name=relationProduct]").val();
		if(relationProduct=='Y'){
			return false;
		}
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var prodBranchId = $(this).attr("prodbranchid");
		var branchName = $(this).attr("branchname");
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
				url:$("#basepathhiddenid").val()+"/product/branch/queryEbkProdTimePrice.do",
				type : 'POST',
				data :{ebkProdBranchId:prodBranchId,ebkProdProductId:ebkProdProductId,branchName:branchName,currPageDate:currPageDate,monthType:monthType,toShowEbkProduct:$(":hidden[name=toShowEbkProduct]").val()},
				dataType : "html",
				success : function(data) {
					/**
					$(".dialog-close").trigger("click");
					$.dialog({
						title: '产品类别 【'+branchName+'】时间价格库存表',
						content: data,
						width:800
					});
					*/
					$("#queryProdTimePriceStockTbody").show().empty().append("<div data-title=\"title\" class=\"dialog-header\"> "+branchName+" 时间价格库存表</div>").append(data);

					$(":hidden[name=updated][value='true']").each(function(){
						$(this).parentsUntil("table").find(".plug_calendar_day").attr({"color":"red"});
					});
				},
				error : function() {
				}
		}; 
		$.ajax(viewprodbranchpricestore);
	});
	function log_view(title){
		var pagelink =$(".dialog").find(".PageLink,.PrevPage");
		if(undefined!=pagelink){
			pagelink.each(function(){
				var href_url = $(this).attr("href");
				$(this).attr("href","javascript:void(0);");
				if(href_url.length>4){
					$(this).live("click",function(){
						var optionsTable = {
								url :href_url,
								type : 'GET',
								dataType : "html",
								success : function(data) {
									$(".dialog-close").trigger("click");
									if($(".dialog:visible").size()>0)return false;
									$.dialog({
										title: title,
										content: data,
										width:800
									});
									log_view();
								},
								error : function() {
								}
						};
						$.ajax(optionsTable);
					});
				}
			});
		}
	}
	$(".viewprodbranchlog").live("click",function(){
		var objectId = $(this).parent().attr("prodbranchid");
		var objecttype = "EBK_PROD_BRANCH";
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"log/queryLogMessage.do?objectType="+objecttype+"&objectId="+objectId,
				type : 'GET',
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '操作日志',
						content: data,
						width:800
					});
					log_view();
				},
				error : function() {
				}
		};
		$.ajax(optionsTable);
	});
	$(".viewprodbranchpricestorelog").live("click",function(){
		var objectId = $(this).parent().attr("prodbranchid");
		var objecttype = "EBK_PROD_TIME_PRICE";
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"log/queryLogMessage.do?objectType="+objecttype+"&objectId="+objectId,
				type : 'GET',
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '时间价格库存操作日志',
						content: data,
						width:800
					});
					log_view();
				},
				error : function() {
				}
		};
		$.ajax(optionsTable);
	});
	$(".editOneDayProdTimePriceStock").live("click",function(){
		$("#dateAllInfoDiv").hide(); 
		var timePriceId = $(this).attr("timepriceid");
		var ebkProdBranchId = $(this).attr("prodbranchid");
		var ebkProdProductId = $(":input[name=ebkProdProductId]").val();
		var currPageDate = $(this).attr("date");
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var optionsTable = {
				url : $("#basepathhiddenid").val()+"product/branch/editOneDayProdTimePriceStock.do",
				data:{timePriceId:timePriceId,ebkProductViewType:ebkProductViewType,ebkProdBranchId:ebkProdBranchId,ebkProdProductId:ebkProdProductId,currPageDate:currPageDate},
				type : 'POST',
				dataType : "html",
				success : function(data) {
					if($(".dialog:visible").size()>0)return false;
					$.dialog({
						title: '编辑 '+currPageDate+'时间价格库存',
						content: data,
						width:800
					});
					var operateStatus = $(":hidden[name='ebkProdTimePrice.operateStatus']").val();
					if(undefined!=operateStatus && ('ADD_OPERATE'==operateStatus || ''==operateStatus)){
						$(".oneDayProdTimePriceStockType option[title]").remove(); 
						if('VIRTUAL'==$("#ebkProdTimePrice_branchType").val()){ 
							$(".oneDayProdTimePriceStockType option[value=UNLIMITED_STOCK]").remove(); 
						}
					}
				},
				error : function() {
				}
		};
		$.ajax(optionsTable);
	});
	$(".editOneDayProdTimePriceStock").live("mouseover",function(){
		$(this).css("cursor","pointer");
	}).live("mouseout",function(){
		$(this).css("cursor","default");
	});
	$(".updateOneDayProdTimePriceStock").live("click",function(){
		$("#validateErrorMesssage").text("");
		var is_submit = true;
		var specDate = $(":input[name='ebkProdTimePrice.specDate']").val();
		var productId = $(":input[name='ebkProdTimePrice.productId']").val();
		var prodBranchId = $(":input[name='ebkProdTimePrice.prodBranchId']");
		var breackfastCount = $("[name='ebkProdTimePrice.breackfastCount']:selected").val();
		var forbiddenSell = $(":radio[name='ebkProdTimePrice.forbiddenSell']:checked").val();
		var aheadHourDay = $(":text[name='aheadHourDay']").val();
		var aheadHour = $(":text[name='aheadHour']").val();
		var aheadHourSecend = $(":text[name='aheadHourSecend']").val();
		var cancelStrategy = $(":radio[name='ebkProdTimePrice.cancelStrategy']:checked").val();
		var settlementPrice = $(":text[name='ebkProdTimePrice.settlementPrice']").val();
		var marketPrice = $(":text[name='ebkProdTimePrice.marketPrice']").val();
		var price = $(":text[name='ebkProdTimePrice.price']").val();
		var stockType = $("select[name='ebkProdTimePrice.stockType']").val();
		var dayStock = $(":text[name='ebkProdTimePrice.dayStock']").val();
		var resourceConfirm = $(":radio[name='ebkProdTimePrice.resourceConfirm']:checked").val();
		var overSale = $(":radio[name='ebkProdTimePrice.overSale']:checked").val();
		var ebkProductViewType = $("#ebkproductviewtypehiddenid").val();
		var aheadHour_current = ((null==aheadHourDay || ''==aheadHourDay)?0:aheadHourDay*60*24) + ((null==aheadHour || ''==aheadHour)?0:aheadHour*60) + ((null==aheadHourSecend || ''==aheadHourSecend)?0:aheadHourSecend*1);
		
		var branchType = $(":input[name='ebkProdTimePrice.branchType']").val();
		
		if(undefined ==forbiddenSell ||  null==forbiddenSell || ''==forbiddenSell){
			$("#validateErrorMesssage").text("请选择关班");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHourDay && (null==aheadHourDay || ''==aheadHourDay) && (null==aheadHour || ''==aheadHour) && (null==aheadHourSecend || ''==aheadHourSecend)){
			$("#validateErrorMesssage").text("请输入提前预订小时数");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHourDay && ((null!=aheadHourDay && ''!=aheadHourDay &&  !/^\d+$/.test(aheadHourDay)) || (null!=aheadHour && ''!=aheadHour && !/^\d+$/.test(aheadHour)) || (null!=aheadHourSecend && ''!=aheadHourSecend && !/^\d+$/.test(aheadHourSecend)))){
			$("#validateErrorMesssage").text("提前预订小时数请输入正整数");
			is_submit = false;
			return false;
		}
		
		if(undefined!= aheadHour && aheadHour>23){
			$("#validateErrorMesssage").text("提前预订小时数小时请输入0-23的数");
			is_submit = false;
			return false;
		}
		if(undefined!= aheadHourSecend && aheadHourSecend>59){
			$("#validateErrorMesssage").text("提前预订小时数分钟请输入0-59的数");
			is_submit = false;
			return false;
		}
		if(aheadHour_current<1){
			$("#validateErrorMesssage").text("请输入提前小时数");
			is_submit = false;
			return false;
		}
		if(undefined ==cancelStrategy ||  null==cancelStrategy || ''==cancelStrategy){
			$("#validateErrorMesssage").text("请选择退订策略");
			is_submit = false;
			return false;
		}
		
		if(undefined ==branchType || null==branchType || ''==branchType||'VIRTUAL'!=branchType){
			if(undefined ==specDate || null==specDate || ''==specDate){
				$("#validateErrorMesssage").text("无日期");
				is_submit = false;
				return false;
			}
			if(undefined ==productId ||  null==productId || ''==productId){
				$("#validateErrorMesssage").text("无产品编码");
				is_submit = false;
				return false;
			}
			if(undefined ==prodBranchId ||  null==prodBranchId || ''==prodBranchId){
				$("#validateErrorMesssage").text("无产品类别编码");
				is_submit = false;
				return false;
			}
			if(undefined!=breackfastCount &&(  null==prodBranchId || ''==prodBranchId)){
				$("#validateErrorMesssage").text("请选择早餐份数");
				is_submit = false;
				return false;
			}
			if(undefined== settlementPrice || null==settlementPrice || ''==settlementPrice){
				$("#validateErrorMesssage").text("请输入结算价");
				is_submit = false;
				return false;
			}
			if(undefined!= settlementPrice && null!=settlementPrice && ''!=settlementPrice &&  !/^\d+$/.test(settlementPrice)){
				$("#validateErrorMesssage").text("结算价请输入正整数");
				is_submit = false;
				return false;
			}else if(undefined!=settlementPrice && settlementPrice>1000000){
				$("#validateErrorMesssage").text("结算价过大");
				is_submit = false;
				return false;
			}
			if(undefined== marketPrice || null==marketPrice || ''==marketPrice){
				$("#validateErrorMesssage").text("请输入门市价");
				is_submit = false;
				return false;
			}
			if(undefined!= marketPrice && null!=marketPrice && ''!=marketPrice &&  !/^\d+$/.test(marketPrice)){
				$("#validateErrorMesssage").text("门市价请输入正整数");
				is_submit = false;
				return false;
			}else if(undefined!=marketPrice && marketPrice>1000000){
				$("#validateErrorMesssage").text("门市价过大");
				is_submit = false;
				return false;
			}
			if(ebkProductViewType=='SURROUNDING_GROUP'||ebkProductViewType=='DOMESTIC_LONG'){
				if(undefined== price || null==price || ''==price){
					$("#validateErrorMesssage").text("请输入销售价");
					is_submit = false;
					return false;
				}
				if(undefined!= price && null!=price && ''!=price &&  !/^\d+$/.test(price)){
					$("#validateErrorMesssage").text("销售价请输入正整数");
					is_submit = false;
					return false;
				}else if(undefined!=price && price>1000000){
					$("#validateErrorMesssage").text("销售价过大");
					is_submit = false;
					return false;
				}
			}
		}
		
		if(undefined==stockType || null==stockType || ''==stockType){
			$("#validateErrorMesssage").text("请选择设置库存");
			is_submit = false;
			return false;
		}
		if("UNLIMITED_STOCK"!=stockType){
			if(undefined== dayStock || null==dayStock || ''==dayStock){
				$("#validateErrorMesssage").text("请输入库存数量");
				is_submit = false;
				return false;
			}
			if(undefined!= dayStock && null!=dayStock && ''!=dayStock &&  !/^\d+$/.test(dayStock)){
				$("#validateErrorMesssage").text("库存数量请输入正整数");
				is_submit = false;
				return false;
			}else if(undefined!=dayStock && dayStock>1000000){
				$("#validateErrorMesssage").text("库存数量过大");
				is_submit = false;
				return false;
			}
		}
		
		if(undefined ==resourceConfirm ||  null==resourceConfirm || ''==resourceConfirm){
			$("#validateErrorMesssage").text("请选择是否资源审核");
			is_submit = false;
			return false;
		}
		if(undefined ==overSale ||  null==overSale || ''==overSale){
			$("#validateErrorMesssage").text("请选择是否超卖");
			is_submit = false;
			return false;
		}
		
		if(!is_submit){
			return false;
		}
		$.ajax({
			url:$("#ebkProdTimePriceForm").attr("action"),
			type:'POST',
			dataType:"json",
			data:$("form").serialize(),
			success : function(data) {
				if(data.success){
					alert("时间价格库存编辑成功");
					var li_current = $("td[tddatevalue='"+specDate+"']");
					var current_stock_type_name = $("select[name='ebkProdTimePrice.stockType'] :selected").attr("title");
					if(undefined==current_stock_type_name){
						current_stock_type_name = "";
					}
					if('UNLIMITED_STOCK'==$("select[name='ebkProdTimePrice.stockType'] :selected").val()){
						current_stock_type_name = "不限";
					}
					li_current.empty();
					$("li.editOneDayProdTimePriceStock[date='"+specDate+"']").attr({"timepriceid":data.timePriceId});
					if(undefined== marketPrice || null==marketPrice || ''==marketPrice){
						li_current.append("<font class=\"menshijia\" columnname=\"marketPrice\" shownew=\"new\"></font><br/>");
					}else{
						li_current.append("<font class=\"menshijia\" columnname=\"marketPrice\" shownew=\"new\">"+marketPrice+"</font><br/>");
					}
					if(ebkProductViewType=='SURROUNDING_GROUP'||ebkProductViewType=='DOMESTIC_LONG'){
						if(undefined== price || null==price || ''==price){
							li_current.append("<font class=\"shoujia\" columnname=\"price\" shownew=\"new\"></font><br/>");
						}else{
							li_current.append("<font class=\"shoujia\" columnname=\"price\" shownew=\"new\">"+price+"</font><br/>");
						}
					}
					if(undefined== settlementPrice || null==settlementPrice || ''==settlementPrice){
						li_current.append("<font class=\"jiesuanjia\" columnname=\"settlementPrice\" shownew=\"new\"></font><br/>");
					}else{
						li_current.append("<font class=\"jiesuanjia\" columnname=\"settlementPrice\" shownew=\"new\">"+settlementPrice+"</font><br/>");
					}
					li_current.append("<font class=\"kucun\" columnname=\"dayStock\" shownew=\"new\">"+current_stock_type_name+dayStock+"</font><br/>");	
					li_current.append("<font class=\"ziyuan\" columnname=\"resourceConfirm\" shownew=\"new\">"+("true"==resourceConfirm?"是":"否")+"</font><br/>");
					li_current.append("<font class=\"caomai\" columnname=\"overSale\" shownew=\"new\">"+("true"==overSale?"是":"否")+"</font>");
					
					if(undefined== forbiddenSell || null==forbiddenSell || ''==forbiddenSell){
						li_current.append("<input type=\"hidden\"  columnname=\"forbiddenSell\" shownew=\"new\" value=\"\"/>");
					}else{
						li_current.append("<input type=\"hidden\"  columnname=\"forbiddenSell\" shownew=\"new\" value=\""+("true"==forbiddenSell?"是":"否")+"\"/>");
					}
					if(undefined== aheadHour_current || null==aheadHour_current || ''==aheadHour_current){
						li_current.append("<input type=\"hidden\"  columnname=\"aheadHour\" shownew=\"new\" value=\"\"/>");
					}else{
						li_current.append("<input type=\"hidden\"  columnname=\"aheadHour\" shownew=\"new\" value=\""+aheadHour_current+"\"/>");
					}
					if(undefined== cancelStrategy || null==cancelStrategy || ''==cancelStrategy){
						li_current.append("<input type=\"hidden\"  columnname=\"cancelStrategy\" shownew=\"new\" value=\"\"/>");
					}else{
						li_current.append("<input type=\"hidden\"  columnname=\"cancelStrategy\" shownew=\"new\" value=\""+("FORBID"==cancelStrategy?"不退不改":"人工确认")+"\"/>");
					}
					li_current.parent().parent().find(".plug_calendar_day").attr({"color":"red"});
					$(".dialog-close").trigger("click");
				}else{
					var errorMessage = errorTimePriceMessage(data.message);
					$("#validateErrorMesssage").text(errorMessage);
				}
			},
			error : function() {
			}
		});
	});
	$("select[name$='stockType']").live("change",function(){
		var day_stock_column =$(this).parent().next().find(":text[name$='dayStock']");
		if(undefined==day_stock_column.val()){
			day_stock_column =$(this).parent().find(":text[name$='dayStock']"); 
		}
		if($(this).val()=='UNLIMITED_STOCK'){
			day_stock_column.attr({"disabled":"true","value":""});
		}else{
			day_stock_column.removeAttr("disabled");
		}
	});
	$(".showDateAllInfo").live('mouseover',function(){
		$("#dateAllInfoDiv [columnname]").each(function(){$(this).text("");});
		var xOffset = $(this).offset().left+$(this).width(); 
		var show_date = $(this).attr("date");
		$("#dateAllInfoDiv [show_date]").text(show_date);
		
		var ebkProdBranchTypeName = $("#ebkProdBranchTypeName").val();
		if("VIRTUAL"==ebkProdBranchTypeName){
			$(".virtualNumClass").hide();
		}else{
			$(".virtualNumClass").show();
		}
		
		var text='';
		$(this).find("[columnname][showold]").each(function(){
			var show_text = undefined!=$(this).text()&&null!=$(this).text()&&''!=$(this).text()?$(this).text():$(this).val();
			if("aheadHour"==$(this).attr("columnname") && null!=$(this).val() && ''!=$(this).val() ){
				show_text = parseFloat(parseInt($(this).val())/60).toFixed(2);
				text = show_text;
			}
			$("#dateAllInfoDiv [columnname="+$(this).attr("columnname")+"][showold]").text(show_text);
		});
		$(this).find("[columnname][shownew]").each(function(){
			var show_text = undefined!=$(this).text()&&null!=$(this).text()&&''!=$(this).text()?$(this).text():$(this).val();
			if("aheadHour"==$(this).attr("columnname") && null!=$(this).val() && ''!=$(this).val() ){
				show_text = parseFloat(parseInt($(this).val())/60).toFixed(2);
				if (''!=text) {
					show_text = "/" + show_text;
				}
			}
			$("#dateAllInfoDiv [columnname="+$(this).attr("columnname")+"][shownew]").text(show_text);
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