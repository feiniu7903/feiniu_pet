var editor;
$(function(){
	
	$("select[name=branch.branchType]").live("change",function(){
		var $form=$(this).parents("form");			
		if($(this).find("option:selected").val()==""){
			$form.find("#branch_branchName").val('');
		}else{
			$form.find("#branch_branchName").val($(this).find("option:selected").text());
		}
	});
	$("select.changeType").live("change",function(){
		var $form=$(this).parents("form");
		var $branchType = $form.find("select[name=branch.branchType] :selected");
		var val = "";
		if($branchType.val() != "") {
			val = $branchType.text();
		}
		if(current_product_type == 'TRAFFIC'){
			var $berth = $form.find("select[name=branch.berth] :selected");
			if($berth.val() != "") {
				if(val != "") {
					val = $berth.text() + "-" + val;
				} else {
					val = $berth.text();
				}
			}
		}
		$form.find("#branch_branchName").val(val);
	});
	
	//添加新的类别
	$(".newBranch").click(function(){
		$(".section").hide();
		var $form=$("form[name=branchForm]");
		$form.find("input[type=hidden],input[type=text]").val('');
		$form.html($("#branchFormDiv form").html()).show();
		createEditor();
	});
	
	function createEditor() {
		if (!isTicket) {
			return;
		}
		if (editor) {
			removeEditor();
		}
		
		editor = KindEditor.create('textarea[name="branch.description"]', {
			newlineTag : 'p',
			filterMode : true,
			items : ['bold', 'undo', 'redo'],
			htmlTags : {strong : [], p :[], h4:[]},
			afterBlur : function() {editor.html(showHtml(editor.html()));}
		});
		editor.clickToolbar('bold', function() {
			this.html(showHtml(editor.html()));
		});
	}
	
	function removeEditor() {
		if (!isTicket) {
			return;
		}
		editor.remove();
		editor = null;
	}
	
	$("a.setdef").live("click",function(){
		var $tr=$(this).parents("tr");
		var pk=$tr.attr("prodBranchId");	
		if($.trim(pk)==''){
			alert("类别不存在");
			return false;
		}
		
		var visible = $tr.attr("visible");
		if(visible != 'undefined'&& $.trim(visible)!=''){
			if(visible == 'false'){
				alert("该类别是否前台显示属性为否，不能设为默认.");
				return false;
			}
		}
		
		var additional = $tr.attr("additional");
		if(additional != 'undefined'&& $.trim(additional)!=''){
			if(additional == 'true'){
				alert("该类别是否附加属性为是，不能设为默认.");
				return false;
			}
		}
		
		$.post("/super_back/prod/changeDefBranch.do",{"prodBranchId":pk},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#branch_tb").find("td.def").html("&nbsp;");
				$("#tr_"+pk).find("td.def").html("是");
			}else{
				alert(data.msg);
			}
		});
	});
	
	//类别上下线
	$("a.online").live("click",function(){		
		var $this=$(this);
		var $tr=$(this).parents("tr");
		var pk=$tr.attr("prodBranchId");
		var branchProductId = $tr.attr("branchProductId");
		if(pk=='undefined'){
			alert("类型不存在");
			return false;
		}
		$.post("/super_back/prod/changeOnlineBranch.do",{"prodBranchId":pk,"branchProductId":branchProductId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				$this.html(data.online?"下线":"上线");
				$this.parents("tr").find("td.online").html(data.online?"上线":"下线");
			}else{
				alert(data.msg);
			}
		});
	});
	function fillForm($form,branch){	
		$form.find("input[name=branch.prodBranchId]").val(branch.prodBranchId);
		$form.find("input[name=branch.productId]").val(branch.productId);
		//$form.find("input[name=branch.defaultBranch]").val(branch.defaultBranch);
		if($.trim(branch.additional)!=''){
			$form.find("input[name=branch.additional][value="+branch.additional+"]").attr("checked",true);			
		}
		$form.find("select[name=branch.branchType] option[value="+branch.branchType+"]").attr("selected",true);
		if(current_product_type == 'TRAFFIC'){
			if($form.find("select[name=branch.berth]").length>0){
				$form.find("select[name=branch.berth] option[value="+branch.berth+"]").attr("selected",true);
			}else if($form.find("select[name=branch.stationStationId]").length>0){
				$form.find("select[name=branch.stationStationId] option[value="+branch.stationStationId+"]").attr("selected",true);
			}
		}
		$form.find("input[name=branch.branchName]").val(branch.branchName);
		$form.find("input[name=branch.priceUnit]").val(branch.priceUnit);
		$form.find("input[name=branch.adultQuantity]").val(branch.adultQuantity);
		$form.find("input[name=branch.childQuantity]").val(branch.childQuantity);
		$form.find("input[name=branch.minimum]").val(branch.minimum);
		$form.find("input[name=branch.maximum]").val(branch.maximum);
		if (branch.description) {
			$form.find("textarea[name=branch.description]").val(showHtml(branch.description));
			if (editor) {
				editor.html(showHtml(branch.description));
			}
		} else {
			$form.find("textarea[name=branch.description]").val('');
			if (editor) {
				editor.html('');
			}
		}
		if(current_product_type=='HOTEL'){
			$form.find("input[name=branch.bedType]").val(branch.bedType);
			$form.find("input[name=branch.breakfast][value="+branch.breakfast+"]").attr("checked",true);
			$form.find("input[name=branch.broadband][value="+branch.broadband+"]").attr("checked",true);
			$form.find("input[name=branch.extraBedAble][value="+branch.extraBedAble+"]").attr("checked",true);
		}
		if($.trim(branch.visible)!=''){
			$form.find("input[name=branch.visible][value="+branch.visible+"]").attr("checked",true);			
		}
		if($.trim(branch.weixinLijian)!=''){
			$form.find("input[name=branch.weixinLijian][value="+branch.weixinLijian+"]").attr("checked",true);			
		}
	}
	//编辑
	$("a.edit").live("click",function(){
		var $tr=$(this).parent().parent();
		var pk=$tr.attr("prodBranchId"); 
		$(".section").hide();
		$.post("/super_back/prod/getBranch.do",{"prodBranchId":pk},function(dt){ 
			var data=eval("("+dt+")");
			if(data.success){
				var $form=$("form[name=branchForm]");
				$form.find("input[type=hidden],input[type=text]").val('');
				fillForm($form,data.branch);
				$("#branchFormDiv").show();
				$form.show();
				var sensitiveValidator=new SensitiveWordValidator($form, false);
				sensitiveValidator.validate();
				
				createEditor();
			}
		});
	});
	
	//类别逻辑删除
	$("a.deleteBranch").live("click",function(){
		if(window.confirm("确定要删除该类别吗？")){
			var $tr=$(this).parent().parent();
			var pk=$tr.attr("prodBranchId"); 
			$(".section").hide();
			$.post("/super_back/prod/deleteBranch.do",{"prodBranchId":pk, "productId":current_product_id},function(dt){ 
				var data=eval("("+dt+")");
				if(data.success){
					//做敏感词标识
					alert("类别删除成功.");
					window.location.reload();
					//$("#tr_"+pk).remove();
				}else{
					alert(data.msg);
				}
			});
		}
	});
	
	$("a.dest").live("mouseover",function(){
		var $this=$(this);
		var $div=$this.parent().find("div.dest");
		var pos=$this.offset();
		$div.css("text-align","left").css("left",(pos.left-100)+"px").css("top",(pos.top+20)).show();		
	});
	$("a.dest").live("mouseout",function(){
		var $this=$(this);
		var $div=$this.parent().find("div.dest");
		$div.hide();
	});
	
	/**
	 * 检测表单.
	 * @param {Object} $form
	 */
	function validation($form){
		if($.trim($form.find("input[name=branch.branchName]").val())==''){
			alert("类别名称不可以为空");
			return false;
		}
		
		if($.trim($form.find("select[name=branch.branchType] :selected").val())==''){
			alert("类别类型必须选中.");
			return false;
		}
		if(current_product_type == 'TRAFFIC'){
			if($.trim($form.find("select[name=branch.berth] :selected").val())==''){
				alert("舱位类型必须选中.");
				return false;
			}
		}
		var quantity=0;
		var tmp=$form.find("input[name=branch.adultQuantity]").val();
		
		if($.trim(tmp)!=''){
			if(parseInt(tmp)!=tmp||tmp.indexOf(".")!=-1){
				alert("成人数必须是整数");
				return false;
			}
			quantity+=parseInt(tmp);
		}
		tmp=$form.find("input[name=branch.childQuantity]").val();
		if($.trim(tmp)!=''){
			if(parseInt(tmp)!=tmp||tmp.indexOf(".")!=-1){
				alert("儿童数必须是整数");
				return false;
			}
			
			quantity+=parseInt(tmp);
		}
		
		if(quantity<1){
			alert("人数必须大于0");
			return false;
		}
		
		tmp=$form.find("input[name=branch.minimum]").val();
		if($.trim(tmp)==''){
			alert("最小起订量不可以为空");
			return false;
		}
		if(isNaN(parseInt(tmp))||parseInt(tmp)<0){
			alert("最小起订量不可以小于0");
		}
		
		tmp=$form.find("input[name=branch.maximum]").val();
		if($.trim(tmp)==''){
			alert("最大起订量不可以为空");
			return false;
		}
		if(isNaN(parseInt(tmp))||parseInt(tmp)<1){
			alert("最大起订量不可以小于1");
			return false;
		}
		
		if(current_product_type=='HOTEL'){
			tmp=$form.find("input[name=branch.breakfast]").val();
			if(tmp=='undefined'||$.trim(tmp)==''){
				alert("早餐请选择");
				return false;
			}
			
			tmp=$form.find("input[name=branch.broadband]").val();
			if(tmp=='undefined'||$.trim(tmp)==''){
				alert("宽带请选择");
				return false;
			}
		}
		/**
		var defaultBranch = $form.find("input[name=branch.defaultBranch]").val();
		var visible = $form.find("input:radio[name=branch.visible]:checked").val();
		if(visible != 'undefined' && $.trim(visible)!=''){
			if(visible == 'false' && defaultBranch == 'true'){
				alert("该类别是默认类别，是否前台显示属性不能为否");
				return false;
			}
		}
		
		var additional = $form.find("input:radio[name=branch.additional]:checked").val();
		if(additional != 'undefined' && $.trim(additional)!=''){
			if(additional == 'true' && defaultBranch == 'true'){
				alert("该类别是默认类别，是否附加属性不能为是");
				return false;
			}
		}*/
		
		return true;
	}
	
	$("form[name=branchForm] .close").live("click",function(){
		var $form=$(this).parents("form");
		$form.hide();
		removeEditor();
	});
	//保存类别
	$(".saveForm").live("click",function(){
		//var str=$(this).attr("form");		
		var $form=$(this).parents("form");
		$form.find("input[name=branch.productId]").val(current_product_id);
		if(!validation($form)){
			return;
		}

		if (isTicket) {
			$form.find("textarea[name=branch.description]").val(formatHtml(editor.html()));
		}
		//editor.sync();

		var sensitiveValidator=new SensitiveWordValidator($form, true);
		if(!sensitiveValidator.validate()) {
			return;
		}
		$.ajax({
			url:$form.attr("action"),
			data:$form.serialize(),
			type:'POST',
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){					
					//$form.find("input[type=text]").val('');
					//$form.find("textarea").val('');
					//$form.hide();
					//fillBranchTd(data.branch,data.hasNew);
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	
	function bindSuggest(){
		$("#searchMeta").jsonSuggest({
			url:"/super_back/meta/searchMetaList.do",
			maxResults: 10,
			minCharacters:1,
			emptyKeyup:false,
			onSelect:function(item){				
				$("input[name=metaProductId]").val(item.id);
				var $select=$("#branchItem_metaBranchId");
				$select.empty();
				$.post("/super_back/meta/getMetaBranchJSON.do",{"metaProductId":item.id},function(dt){
					try{
						var data=eval("("+dt+")");
						if(data.success){
							if(!data.find){
								alert("没有取到采购产品的类别信息");							
							}else{								
								for(var i=0;i<data.list.length;i++){					
									$select.append("<option value='"+data.list[i].branchId+"'>"+data.list[i].branchName+"</option>");
								}
								$select.find("option:first").attr("selected","selected");
							}
						}else{
							alert(data.msg);
						}
					}catch(ex){}
				});
			}
		});
	}
	
	$("a.pack").live("click",function(){
		var $tr=$(this).parent().parent();
		var pk=$tr.attr("prodBranchId"); 
		$(".section").hide();
		$.post("/super_back/prod/getBranchPack.do?dt="+new Date().getTime(),{"prodBranchId":pk},function(dt){
			$("#branchItemDiv").html(dt).show();		
			bindSuggest();
		});
	});
	$("a.deleteItem").live('click',function(){
		if(!confirm("确定要删除当前打包的类别")){
			return false;
		}		
		
		var $tr=$(this).parent().parent();
		var result=$tr.attr("result");
		if(!result){
			alert("打包的类别信息不存在，不可以操作");
			return false;
		}

		$.post("/super_back/prod/deleteBranchItem.do",{"branchItemId":result},function(data){
			if(data.success){
				alert("操作成功");
				$tr.remove();
			}else{
				alert(data.msg);
			}
		},"json");
	});
	function fillBranchItem(item){
		var $tr=$("<tr/>");
		$tr.attr("id","tr_item_"+item.branchItemId);
		$tr.attr("result",item.branchItemId);
		var $td=$("<td/>");
		$td.html(item.metaProduct.productName+"("+item.metaProduct.metaProductId+")");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(item.metaBranch.branchName+"("+item.metaBranch.metaBranchId+")");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(item.quantity);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html("成人:"+item.metaBranch.adultQuantity+"<br/>儿童:"+item.metaBranch.childQuantity)
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html("<a href='#timeprice' class='showTimePrice' tt='META_PRODUCT' param='{\"metaBranchId\":"+item.metaBranchId+"}'>修改价格</a>&nbsp;<a href='#delete' class='deleteItem' >删除</a>");
		$tr.append($td);
		
		$("#meta_branch_tb").append($tr);
	}
	function addBranchItem($form){
		var metaBranchId=$form.find("select[name=branchItem.metaBranchId] :selected").val();
		if($.trim(metaBranchId)==''){
			alert("采购产品类别不可以为空");
			return;
		}
		
		var quantity=$form.find("input[name='branchItem.quantity']").val();
		if(isNaN(quantity)){
			alert("数量必须是数字");
			return;
		}
		if (isTicket) {
			if (editor) {
				$form.find("textarea[name=branch.description]").val(formatHtml(editor.html()));
			}
		}
		$.ajax({
			url:'/super_back/prod/addBranchItem.do',
			type:'POST',
			data:$form.serialize(),
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					fillBranchItem(data.item);
					$form.find("select[name=branchItem.metaBranchId]").empty();
					$form.find("input[name=branchItem.quantity]").val('');
					$form.find("input[name=searchMeta]").val('');
				}else if(data.code==2){
					addBranchItemConfirmDeleteTimePrice($form);
				}else{
					alert(data.msg);
				}
			}
		});
	}
	
	/**
	 * 添加并且判断是否需要删除销售时间价格表
	 * @param {Object} $form
	 */
	function addBranchItemConfirmDeleteTimePrice($form){
		var flag=confirm("系统发现销售产品的时间价格表与采购产品时间价格表不匹配，系统将删除多余销售产品时间价格表");
		$form.find("input[name=direction]").val(flag);
		addBranchItem($form);
	}
	
	//添加类别打包信息
	$("input.addBranchItem").live("click",function(){
		$(this).attr("disabled",true);
		var str=$(this).attr("ff");		
		var $form=$("form[name="+str+"]");
		$form.find("input[name=direction]").val('');	
		addBranchItem($form);
		$(this).removeAttr("disabled");
	});
	
	
	var ajaxUpload;
	var fileInput=$("#uploadFile")
	$(document).ready(function(){
		
		ajaxUpload=new AjaxUpload(fileInput,{
			action:'/super_back/prod/uploadIcon.do',
			autoSubmit:false,
			name:'file',
			onSubmit:function(file,ext){
				if(ext) {
					ext = ext.toLowerCase();
				}
				if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)){
					var data={};
					var $form=$("#uploadIconDiv form");
					data["prodBranchId"]=$form.find("input[name=prodBranchId]").val();
					this.setData(data);
					this.disable();
					return true;
				}else{
					alert("文件格式错误");
					return false;
				}
			},
			onComplete:function(file,dt){
				var data=eval("("+dt+")");
				if(data.success){
					alert("成功");
					$("#uploadIconDiv td.image").html("<img src='http://pic.lvmama.com/pics/"+data.image+"'>");
				}else{
					alert(data.msg);
				}
				this.enable();
			}
		});
	});
	
	$("#uploadFileBtn").click(function(){
		ajaxUpload.submit();
	})
	$("a.upload").live("click",function(){
		var $tr=$(this).parents("tr");
		var pk=$tr.attr("prodBranchId");
		var $form=$("#uploadIconDiv");
		var image=$(this).attr("img");
		if(image!='undefined'&&$.trim(image)!=''){
			$form.find("td.image").html("<img src='"+image+"'/>");
		}
		$form.find("input[name=prodBranchId]").val(pk);
		$(".section").hide();
		$form.show();
	});
	
	$("#uploadIconDiv").bind("changeHtml",function(){
		if(ajaxUpload){
			ajaxUpload.hide();			
		}
	});
});

var DIV_HEAD = '<div class="xtext">';
var DIV_END = '</div>';
var H4_HEAD = '<h4>';
var H4_END = '</h4>';
var PAGE_REGX = /^\s*<p>[\s\S]*/;

function showHtml(html) {
	if (!isTicket) {
		return html;
	}
	/*
	html = html.replace(/<div\s*\S*>/g, '')
				.replace(/<span\s*\S*>/g, '')
				.replace(/<p\s*\S*>/g, '<p>')
				.replace(/<h4\s*\S*>/g, '<h4>')
				.replace(/<\/div>/g, '');
	*/
	html = trimTag(html);
	html = html.replace(/<h4>/g, '<myh4>').replace(/<h4>\s*<\/h4>/g, '');
	html = html.replace(/<strong>/g, '<myh4>').replace(/<\/strong>/g, H4_END);
	html = addH4Tag(html);
	html = addPageTag(html);
	return trimTag(html);
}

function formatHtml(html) {
	if (!isTicket) {
		return html;
	}
	/*
	html = html.replace(/<div\s*\S*>/g, '')
				.replace(/<span\s*\S*>/g, '')
				.replace(/<p\s*\S*>/g, '<p>')
				.replace(/<h4\s*\S*>/g, '<h4>')
				.replace(/<\/div>/g, '');
	*/ 
	html = trimTag(html);
	html = html.replace(/<h4>/g, '<myh4>').replace(/<h4>\s*<\/h4>/g, '');
	html = html.replace(/<strong>/g, '<myh4>').replace(/<\/strong>/g, H4_END);
	//html = DIV_HEAD + html;
	html = addH4Tag(html);
	html = addPageTag(html);
	html = addDivTag(html);
	return trimTag(html);
}

function trimTag(html) {
	return html.replace(/<br\s*\/>/g, '').replace(/<strong>\s*<\/strong>/g, '')
				.replace(/<h4>\s*<\/h4>/g, '')
				.replace(/<p>\s*<\/p>/g, '')
				.replace(/<\/p>\s*<\/p>/g, '</p>')
				.replace(/<div class="xtext">\s*<\/div>/g, '')
				;
}

function addDivTag(html) {
	var index = html.indexOf('<h4>');
	if (index == -1) {
		return DIV_HEAD + html + DIV_END;
	}
	var before = DIV_HEAD + html.substring(0, index + '<h4>'.length);
	var after = html.substring(index + '<h4>'.length);
	index = after.indexOf('<h4>');
	if (index == -1) {
		return before + after + DIV_END;
	}
	return before + after.substring(0, index) + DIV_END + addDivTag(after.substring(index));
}

function addH4Tag(html) {
	var index = html.indexOf('<myh4>');
	if (index == -1) {
		return html;
	}
	html = html.substring(0, index) + H4_HEAD + html.substring(index + '<myh4>'.length);
	index = html.indexOf('<myh4>');
	if (index == -1) {
		return html;
	}
	return addH4Tag(html);
}

function addPageTag(html) {
	var index = html.indexOf('</h4>');
	if (index == -1) {
		return html;
	}
	var before = html.substring(0, index + '</h4>'.length);
	var after = html.substring(index + '</h4>'.length);
	if (PAGE_REGX.test(after)) {
		return before + addPageTag(after);
	}
	index = after.indexOf('<p>');
	if (index == -1) {
		index = after.indexOf('<h4>');
		if (index == -1) {
			return before + '<p>' + after + '</p>';
		}
	}
	return before + '<p>' + after.substring(0, index) + '</p>' + addPageTag(after.substring(index));
}

function replaceStrongTag(html) {
	var index = html.indexOf('<strong>');
	if (index == -1) {
		return html;
	}
	html = html.substring(0, index) + '<mystrong>' + html.substring(index + '<strong>'.length);
	return replaceStrongTag(html);
}
