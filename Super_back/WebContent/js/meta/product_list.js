$(function(){
	$("#searchSupplierName").jsonSuggest({
		url:"/super_back/supplier/searchSupplier.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#comSupplierId").val(item.id);
		}
	});
	
	$("input.newProduct").click(function(){
		var $form=$(this).parents("form");
		var productType=$form.find("input[name=productType]").val();
		window.location.href="/super_back/meta/toAddProduct.do?productType="+productType;
	})
	
	$("a.changeValid").click(function(){
		var $this=$(this);
		var result=$this.attr("result");
		if(!result){
			alert("对象不存在");
			return false;
		}
		$.post("/super_back/meta/changeValid.do",{"metaProductId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$this.parents("tr").find("td.valid").html(data.strValid);
				//如果是权限可以就直接变更值
				if((data.valid=='Y'&&close_valid=='true')||(data.valid=='N'&&open_valid=='true')){
					$this.html(data.butValid);					
				}else{//如果权限不足就直接删除
					$this.remove();
				}
			}else{
				alert(data.msg);
			}
		});
	
	});
})