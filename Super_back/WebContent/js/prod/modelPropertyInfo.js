/******************
 * 模块属性页面.
 * 
 * 使用方式<a href="#modelProperty" class="showModelProperty" param="{id:'XXXX',objectType:'XXX'}">编辑</a>
 */
$(function(){
	var $property_dlg;
	
	$(".showModelProperty").live("click",function(){
		var param=$(this).attr("param");
		if($.trim(param)==''){
			return false;
		}
		
		var jsonDate=eval("("+param+")");		
		var title;
		
		if (jsonDate.objectType=="modify") {
		    $property_dlg=$(this).data("modifyModelProperty");
		    title = "修改模块属性"
		}else if (jsonDate.objectType=="add") {
			
			$property_dlg=$(this).data("addModelProperty");
		    title = "新增模块属性"
		}		
		
		if($property_dlg==null){
			$property_dlg=$("<div class='editModelPropertyDiv'/>");
			$property_dlg.appendTo($("body"));
		}
		
		$.post("/super_back/prod/editModelProperty.do", jsonDate, function(dt){
			$property_dlg.html(dt).dialog({
				modal:true,
				width:500,
				title:title,
				close:function(event,ui) {
					//$(".editModelPropertyDiv").remove(); //关闭窗口时把加载的代码删除
					$("#searchForm").submit(); 
				}
			});
			
		});	
	});
	    
	/**
	 * 点击确定按钮时触发
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$('.editModelPropertyDiv span.opt_btn_re').live("click", function() {	
		var params = "{";
		
		var id = $(".editModelPropertyDiv").find("input[name=productModelProperty.id]").val();
		if (id!="") {
			params += "id:'" + id + "',";
		}
		
		var property = $(".editModelPropertyDiv").find("input[name=productModelProperty.property]").val();
		if (property == "") {
			alert("名称必填");
			$(".editModelPropertyDiv").find("input[name=productModelProperty.property]").focus();
			return;
		}
		params += "property:'" + property + "',";
		
		var orderNum = $(".editModelPropertyDiv").find("input[name=productModelProperty.orderNum]").val();
		if (orderNum != "" && isNaN(orderNum)) {
			alert("排序值要输入数字类型");
			$(".editModelPropertyDiv").find("input[name=productModelProperty.orderNum]").val("");
			$(".editModelPropertyDiv").find("input[name=productModelProperty.orderNum]").focus();
			return;
		}
		params += "orderNum:'" + orderNum + "',";
		
		var thesaurus = $(".editModelPropertyDiv").find("input[name=productModelProperty.thesaurus]").val();
		if (thesaurus!="") {
			params += "thesaurus:'" + thesaurus + "',";
		}				
		
		var pingying = $(".editModelPropertyDiv").find("input[name=productModelProperty.pingying]").val();
		if (pingying!="") {
			params += "pingying:'" + pingying + "',";
		}
		
		var firstModelId = $(".editModelPropertyDiv").find("select[name=productModelProperty.firstModelId]").val();
		if (firstModelId!="") {
			params += "firstModelId:'" + firstModelId + "',";
		}
		
		var secondModelId = $(".editModelPropertyDiv").find("select[name=productModelProperty.secondModelId]").val();
		if (secondModelId!="") {
			params += "secondModelId:'" + secondModelId + "',";
		}
		
		var productType = "";
		$(".editModelPropertyDiv").find("[name='productModelProperty.productType']").each(function(){
			if($(this).attr("checked")) {
				productType += $(this).val()+";";   
   			}    	
		})			
		if (productType=="") {
			alert("产品类型必选");
			return;
		}
		params += "productType:'" + productType.substring(0,productType.length-1) + "'}";
				
	    $.post("/super_back/prod/saveOrUpdateProperty.do", eval("("+params+")"), function(json){	
	    	if (id=="") {
	    		clearForm($(".editModelPropertyDiv").find("#editModelPropertyForm"));	
	    	}
	    	var data = eval("("+json+")")
	    	alert(data.msg); 
	    	$("#searchForm").submit();
		});
	});
	
	function clearForm(objE){//objE为form表单  
        $(objE).find(':input').each(  
            function(){  
                switch(this.type){  
                    case 'passsword':  
                    case 'select-multiple':  
                    case 'select-one':  
                    case 'text':  
                    case 'textarea':  
                        $(this).val('');  
                        break;  
                    case 'checkbox':  
                    case 'radio':  
                        this.checked = false;  
                }  
            }     
        );  
    }
})