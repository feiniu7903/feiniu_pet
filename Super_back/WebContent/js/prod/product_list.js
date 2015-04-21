$(function(){
	$("#searchPlace").jsonSuggest({
		url:"/super_back/prod/searchPlace.do",
		maxResults: 20,
		minCharacters:1,
		onSelect:function(item){
			$("#comPlaceId").val(item.id);
		}
	});
	
	$("a.deleteProduct").click(function(){
		var $this=$(this);
		var result=$this.attr("result");
		
		var title=$this.attr("tit");
		if(!confirm("确定要删除“"+title+"”")){
			return false;
		}
		$.post("/super_back/prod/deleteProduct.do",{"productId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_"+result).remove();
				if($("tr[id^=tr_]").length<6){//少于6条时重启加载
					window.location.reload();
				}
			}else{
				alert(data.msg);
			}
		});
	});
	/**销售产品复制**/
	$("a.copyProduct").click(function(){
		var $this=$(this);
		var result=$this.attr("result");
		var title=$this.attr("tit");
		if(!confirm("确定要复制 “"+title+"”吗？")){
			return false;
		}
		$.post("/super_back/prod/copyProduct.do",{"productId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				window.location.reload();
				alert("已成功复制销售产品的基本信息、标的、描述信息及其他信息");
			}else{
				alert(data.msg);
			}
		});
	});
	/****/
	$("a.online").click(function(){
		var $this=$(this);
		var result=$this.attr("result");
		if(!result){
			alert("要变更的对象不存在.");
			return false;
		}
		
		$.post("/super_back/prod/changeProductOnline.do",{"productId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$this.html(data.onLine=='true'?"下线":"上线");
				$("#tr_"+result).find("td.online").html(data.strOnLine);
                window.location.reload(window.location.href);
			}else{
				alert(data.msg);
			}
		});
	});
	
	function showRecommentDialog(data,productId){
		var $dlg=$("#recommendDiv");
		$dlg.find("input[name=product.recommendInfoFirst]").val(data.recommendInfoFirst);
		$dlg.find("input[name=product.recommendInfoSecond]").val(data.recommendInfoSecond);
		$dlg.find("textarea[name=product.recommendInfoThird]").val(data.recommendInfoThird);
		$dlg.find("input.length,textarea.length").trigger("keyup");
		$dlg.find("input[name=product.productId]").val(productId);
		$dlg.dialog({
			modal:true,
			title:data.productName+" 一句话推荐",
			width:650,
			buttons:{
				"保存":function(){
					var $form=$dlg.find("form");
					var third=$("[name=product.recommendInfoThird]").val();
					if(third.length>50){
						alert("推荐三不可以大于50字符.");
						return false;
					}
					$.ajax({
						url:"/super_back/prod/saveProductRecomment.do",
						data:$form.serialize(),
						type:"POST",
						success:function(dt){
							var res=eval("("+dt+")");
							if(res.success){
								$dlg.dialog("close");
								alert("一句话推荐添加成功");
							}else{
								alert(res.msg);
							}
						}
					});
				}
			}
		});
	}
	
	$("a.recomment").click(function(){
		var $this=$(this);
		var result=$this.attr("result");
		
		$.post("/super_back/prod/loadProductRecomment.do",{"productId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				showRecommentDialog(data,result);
			}
		});
	});
	
	//计算字符数	
	$("#recommendDiv input.length,#recommendDiv textarea.length").keyup(function(){
		var len=$(this).val().length;
		var $parent=$(this).parent("td");
		$parent.find("span.count").html(len);
	});
	
	$("input.newProduct").click(function(){
		var $form=$(this).parents("form");
		var productType=$form.find("input[name=productType]").val();
		window.location.href="/super_back/prod/toAddProduct.do?productType="+productType;
	});
	
	$("a.doCleanCache").click(function() {
		var $this = $(this);
		var param = $this.attr("param");
		$.post("/super_back/prod/doCleanCache.do",{'productId': param},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("清除成功！");
			}
		});
	});
})