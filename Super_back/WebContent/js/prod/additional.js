$(function(){
	
	//读取销售产品的类别列表
	function showBranchSel(id){
		var $sel=$("select[name=relation_productBranchId]");
		$sel.empty();
		if(!id){
			alert("没有选中要操作的产品");
		}else{
			$.post("/super_back/prod/getProdBranchListJSON.do",{"productId":id},function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					if(data.find){
						for(var i=0;i<data.list.length;i++){
							var $opt=$("<option/>");
							$opt.text(data.list[i].branchName);
							$opt.val(data.list[i].branchId);
							$sel.append($opt);
						}
					}
				}else{
					alert(data.msg);
				}
			});
		}
	}
	function fillAdditional(relation){
		var $tr=$("<tr/>");
		$tr.attr("id","tr_"+relation.relationId);
		$tr.attr("relationId",relation.relationId);
		
		var $td=$("<td/>");
		$td.html(relation.relationProduct.zhSubProductType);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(relation.relatProductId);
		$tr.append($td);
				
		$td=$("<td/>");
		$td.html(relation.relationProduct.productName);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(relation.branch.branchName+"("+relation.prodBranchId+")");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.css("padding","0 10px");
		var body ="<input class='saleNumType' type='radio' name='require_"+relation.relationId+"' ";
			
		body+=" value='OPT'><label>任选</label>";
		body+="<input class='saleNumType' type='radio' name='require_"+relation.relationId+"' ";
		body+=" value='ANY'><label>可选</label>";	
		body +="<input class='saleNumType' type='radio' name='require_"+relation.relationId+"' ";
		
		body+=" value='ALL'><label>等量</label>";		
		$td.html(body);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html("<a href='#timeprice' tt='PROD_PRODUCT' class='showTimePrice' param='{prodBranchId:"+relation.branch.prodBranchId+",editable:false}'>查看</a>");
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(relation.branch.priceUnit);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html("<a href='#delete' class='delete'>删除</a>");
		$tr.append($td);
		$tr.find("input.saleNumType[value="+relation.saleNumType+"]").attr("checked",true);
		
		$("#additiona_tb").append($tr);
	}
	
	$("input.addRelation").click(function(){
		var branchId=$("select[name=relation_productBranchId] :selected").val();
		if($.trim(branchId)==''){
			alert("没有选中要添加的产品类别");
			return false;
		}
		
		$.post("/super_back/prod/editProductAdditional.do",{"prodBranchId":branchId,"productId":current_product_id},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				fillAdditional(data.relation);
			}else{
				alert(data.msg);
			}
		});
	});
	$("input.saleNumType").live("click",function(){
		var val=$(this).val();
		var relationId=$(this).attr("name").replace("require_","");		
		$.ajax({
			type : "POST",
			url : "/super_back/prod/updateSaleNumType.do",
			data : {relationId : relationId,saleNumType:val}
			,success : function(dt) {
				 var data=eval("("+dt+")");
				 if(!data.success){
					 alert(data.msg);
				 }else{
					 alert("操作成功");
				 }
			}
		});
	});
	
	$("a.delete").live("click",function(){
		if(!confirm("确定要删除关联产品")){
			return false;
		}
		var $tr=$(this).parent().parent();
		var relationId=$tr.attr("relationId");
		if($.trim(relationId)==''){
			alert("操作关联产品不存在");
			return false;
		}
		
		$.post("/super_back/prod/deleteProdAdditional.do",{"relationId":relationId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$tr.remove();
			}else{
				alert(data.msg);
			}
		});
	});
	$("#additionalProductSuggest").jsonSuggest({
			url:"/super_back/prod/searchProductJSON.do",
			maxResults: 10,
			width:300,
			emptyKeyup:false,
			param:["#productType"],
			minCharacters:1,
			onSelect:function(item){
				showBranchSel(item.id);
			}
	});
});