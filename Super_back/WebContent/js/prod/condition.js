$(function(){
	var current_load_condition_url;//保存，以备后面的更新使用
	/**
	 * 编辑提示信息，需要在链接上添加产品对象属性，便于采购与销售通用.
	 * @memberOf {TypeName} 
	 */
	$("a.condition").click(function(){
		current_load_condition_url=$(this).attr("url");
		var result=$(this).attr("result");
		var objectType=$(this).attr("tt");
		$("#conditionDiv").load(current_load_condition_url,{"objectId":result,"objectType":objectType},function(){
			$(this).dialog({
				modal:true,
				title:"产品提示信息",
				width:750
			})
		});		
	});
	
	/**
	 * 加载内容.
	 */
	function loadConditionList(objectId,objectType){
		$("#conditionListDiv").load(current_load_condition_url,{"objectId":objectId,"objectType":objectType,"all":false});
	}
	
	$("#conditionDiv input.saveCondition").live("click",function(){
		var $form=$(this).parents("form");
		var objectId=$form.find("input[name=condition.objectId]").val();
		var objectType=$form.find("input[name=condition.objectType]").val();
		var length=$form.find("textarea[name=condition.content]").val().length;
		if(length>2000){
			alert("内容不可以超出2000字符");
			return false;
		}
		$.ajax({
			url:$form.attr("action"),
			data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					loadConditionList(objectId,objectType);					
				}else{
					alert(data.msg);
				}
			}
		})
	});
	
	$("#conditionDiv a.deleteCondition").live("click",function(){
		var result=$(this).attr("result");
		$.post("/super_back/prod/deleteCondition.do",{"condition.conditionId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_condition_"+result).remove();				
			}else{
				alert(data.msg);
			}
		});
	})
})