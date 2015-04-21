$(function(){
	
	var metaBranchParam;
	var $packed_prod_branch_dlg;
	
	$("h3.newBranch").click(function(){
		var $form=$("form[name=branchForm]");
		$form.html($("#form_content form").html());
		$form.show();
		//$form.find("input[type=text]").val('');
		//$form.find("select option:eq(0)").attr("selected",true);
		//$form.find("textarea").val('');		
	});
	
	$("select.changeType").live("change",function(){
		var $form=$(this).parents("form");
		var $branchType = $form.find("select[name=branch.branchType] :selected");
		var val = "";
		if($branchType.val() != "") {
			val = $branchType.text();
		}
		var metaProductType = $("#metaProductType").val();
		if(metaProductType == 'TRAFFIC') {
			var $berth = $form.find("select[name=branch.berth] :selected");
			if($berth.val() != "") {
				if(val != "") {
					val = $berth.text() + "-" + val;
				} else {
					val = $berth.text();
				}
			}
		}
		$form.find("input[name=branch.branchName]").val(val);
	});
	
	function showContent(branch,hasNew){
		var $tr=$("<tr/>");
		if(hasNew){
			$tr.attr("id","tr_"+branch.metaBranchId);
			$tr.attr("result",branch.metaBranchId);
		}else{
			$tr=$("#tr_"+branch.metaBranchId);
			$tr.empty();
		}
		var $td=$("<td/>");
		$td.html(branch.branchName+"("+branch.metaBranchId+")");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html("<span>成人："+branch.adultQuantity+"</span><span>儿童："+branch.childQuantity+"</span>");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(branch.description);
		$tr.append($td);
		
		$td=$("<td/>");
		var body="<a href='#edit' class='edit'>修改</a>";		
		body+="<!--<a href='#viewPrice' class='viewPrice'>查看价格</a>--><a href='#timeprice' tt='META_PRODUCT' class='showTimePrice' param='{metaBranchId:"+branch.metaBranchId+",editable:true}'>修改价格</a>";
		body+="<a href='#viewPackedProductBranch' class='viewPackedProductBranch' param='{metaBranchId:"+branch.metaBranchId+"}'>查看关联的销售类别</a>";
		
		/**zx 2012-03-06*/
		body += "<a href='#log' class='showLogDialog' param=\"{'objectType':'META_PRODUCT_BRANCH','objectId':"+branch.metaBranchId+"}\">查看操作日志</a>";
		/**zx 2012-03-06*/
		$td.html(body);
		$tr.append($td);
		if(hasNew){
			$("#branch_tb").append($tr);
		}
	}
	
	//取消该功能
	$("a.valid").live("click",function(){
		var $this=$(this);
		var $tr=$(this).parents("tr");
		var result=$tr.attr("result");
		if($.trim(result)==''){
			alert("类别信息不存在");
			return false;
		}
		
		$.post("/super_back/meta/changeBranchValid.do",{"metaBranchId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$this.html(data.valid=='Y'?"关闭":"开启");
			}else{
				alert(data.msg);
			}
		});
	});
	
	$(".cancelForm").live("click",function(){
		$(this).parents("form").hide();
	});
	function fillForm(branch){
		var $form=$("form[name=branchForm]");
		$form.html($("#form_content form").html());
		$form.show();		
		$form.find("input[name=branch.metaBranchId]").val(branch.metaBranchId);
		$form.find("input[name=branch.branchName]").val(branch.branchName);
		if(branch.totalDecrease){
			$form.find("input[name=branch.totalDecrease][value="+branch.totalDecrease+"]").attr("checked",true);			
		}
		if(branch.sendFax){
			$form.find("input[name=branch.sendFax][value="+branch.sendFax+"]").attr("checked",true);
		}
		if(branch.virtual){
			$form.find("input[name=branch.virtual][value="+branch.virtual+"]").attr("checked",true);
		}
		
		$form.find("input[name=branch.additional][value="+branch.additional+"]").attr("checked",true);
		$form.find("select[name=branch.branchType] option[value="+branch.branchType+"]").attr("selected","true");
		var metaProductType = $("#metaProductType").val();
		if(metaProductType == 'TRAFFIC') {
			if($form.find("select[name=branch.berth]").length>0){
				$form.find("select[name=branch.berth] option[value="+branch.berth+"]").attr("selected",true);
			}else if($form.find("select[name=branch.stationStationId]").length>0){
				$form.find("select[name=branch.stationStationId] option[value="+branch.stationStationId+"]").attr("selected",true);
			}
		}
		$form.find("input[name=branch.adultQuantity]").val(branch.adultQuantity);
		$form.find("textarea[name=branch.description]").val(branch.description);
		$form.find("input[name=branch.childQuantity]").val(branch.childQuantity);
		$form.find("input[name=branch.description]").val(branch.description);
		$form.find("input[name=branch.totalStock]").val(branch.totalStock);
		$form.find("input[name=branch.productIdSupplier]").val(branch.productIdSupplier);
		$form.find("input[name=branch.productTypeSupplier]").val(branch.productTypeSupplier);
		
	}
	$("a.edit").live("click",function(){
		var $tr=$(this).parents("tr");
		var result=$tr.attr("result");
		$.post("/super_back/meta/getBranchJSON.do",{"metaBranchId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				fillForm(data.branch);
			}else{
				alert(data.msg);
			}
		});
	});	
	$("em.saveForm[ff]").live("click",function(){
		var $form=$("form[name="+$(this).attr("ff")+"]");
		$form.find("input[name=branch.metaProductId]").val(current_meta_product_id);
		var type=$form.find("select[name=branch.branchType] :selected").val();
		if($.trim(type)==''){
			alert("类别类型不可以为空");
			return false;
		}
		
		var metaProductType = $("#metaProductType").val();
		if(metaProductType == 'TRAFFIC') {
			var berth=$form.find("select[name=branch.berth] :selected").val();
			if($.trim(berth)==''){
				alert("舱位类型不可以为空");
				return false;
			}
		}
		
		var branchName=$form.find("input[name=branch.branchName]").val();
		if($.trim(branchName)==''){
			alert("名称不可以为空");
			return false;
		}
		
		var adult=$form.find("input[name=branch.adultQuantity]").val();
		var child=$form.find("input[name=branch.childQuantity]").val();
		if($.trim(adult)==''&&$.trim(child)==''){
			alert("人数不可以为空");
			return false;
		}
		
		var v=0,v2=0;
		if($.trim(adult)!=''){
			v=parseInt(adult);
			if(v==isNaN||v<0||v!=adult||adult.indexOf('.')!=-1){
				alert("成人数不正确")
				return false;
			}
		}
		if($.trim(child)!=''){
			v2=parseInt(child);
			if(v==isNaN||v2<0||v2!=child||child.indexOf('.')!=-1){
				alert("儿童数不正确");
				return false;
			}			
		}
		if(v+v2<1){
			alert("成人+儿童数不可以小于1");
			return false;
		}
		
		var isAperiodic = $("#isAperiodic").val();
		var stock = $form.find("input[name=branch.totalStock]").val();
		if($.trim(stock) == '') {
			if(isAperiodic == "true") {
				alert("期票产品的总库存不能为空！");
				return;
			}
		}
		
		$.ajax({
			url:"/super_back/meta/saveBranch.do",
			type:"POST",
			data:$form.serialize(),
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					$form.hide();
					showContent(data.branch,data.hasNew);
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	/****zx 20120324 查看该采购类别关联的销售类别事件****/
	$(".viewPackedProductBranch").live("click",function(){
		var param=$(this).attr("param");
		metaBranchParam = eval("("+param+")");
		if($packed_prod_branch_dlg == null){
			$packed_prod_branch_dlg = $("<div style='display:none'>");			
			$packed_prod_branch_dlg.appendTo($("body"));
		}
		$packed_prod_branch_dlg.load("/super_back/meta/getProdProductAndBranch.do",metaBranchParam,function(){
			$packed_prod_branch_dlg.dialog({
				title:"关联销售产品及类别信息",
				width:1000,
				modal:true				
			});
		});
	});
	/****zx 20120324 查看该采购类别关联的销售类别事件****/
	
});
