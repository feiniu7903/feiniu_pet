$(function(){
	$("#btn_recommendSeqValue_ok").click(function(){
		if($("#recommendSeqValue").val()==""){
			alert("请输入排序值!");
			$("#recommendSeqValue").focus();
			return;
		}
		$.ajax({
			type:"post",
	        url:"updateRecommendSeq.do",
	        data:"recommendSeq="+$("#recommendSeqValue").val()+"&containerProductId="+$("#containerProductId").val()+"&oldRecommendSeq="+$("#oldRecommendSeq").val(),
	        error:function(){
	            alert("与服务器交互错误!请稍候再试!");
	        },
	        success:function(data){
	        	if(data=="success"){
	        		alert("设置成功！");
	        		var toparentplace="";
	        		if($("#toParentPlace").val()==null||$("#toParentPlace").val()==""){
	        			toparentplace="";
	        		}else{
	        			toparentplace=$("#toParentPlace").val();
	        		}
	        			
	        		window.location.href="container.do?containerCode="+$("#containerCode").val()+"&fromPlace="+$("#fromPlace").val()+"&toPlace="+$("#toPlace").val()+"&toParentPlace="+toparentplace+"&subProductType="
	        		+$("#subProductType").val()+"&productId="+$("input[name='productId']").val()+"&productName="
	        		+$("input[name='productName']").val()+"&sortType="+$("#sortType").val();
	        		closeWin('recommendSeq');
	        	}else{
	        		alert("设置失败!");
	        	}
	        }
		});
	});
	setFromPlace($("#containerCode").val());
});

function setFromPlace(containerCode){
	$.ajax({
		type:"post",
        url:"getFromPlaceList.do",
        data:"containerCode="+containerCode,
        error:function(){
            alert("与服务器交互错误!请稍候再试!");
        },
        success:function(data){
        	var obj = eval('('+ data +')');
        	data=obj.data;
        	var result="<option value=''>--请选择--</option>";
        	for(var i=0;i<data.length;i++){
        		if($("#fromPlaceHid").val()==data[i].fromPlaceId){
        			result+="<option value='"+data[i].fromPlaceId+"' selected>"+data[i].fromPlaceName+"</option>";
        		}else{
        			result+="<option value='"+data[i].fromPlaceId+"'>"+data[i].fromPlaceName+"</option>";
        		}
        		
        	}
        	$("#fromPlace").empty();
        	$("#fromPlace").append(result);
        }
	});
	$("#toPlace").empty();
	$("#fromPlace").empty();
	$("#toParentPlace").empty();
	
	if(containerCode=="DZMP_RECOMMEND"){
		setToPlace("");
	}
	setProductType(containerCode);
	if($("#fromPlaceHid").val()!=""){
		setToPlace($("#fromPlaceHid").val());
	}
	
}
function setToPlace(fromPlaceId){
	$.ajax({
		type:"post",
        url:"getToPlaceList.do",
        data:"containerCode="+$("#containerCode").val()+"&fromPlace="+fromPlaceId,
        error:function(){
            alert("与服务器交互错误!请稍候再试!");
        },
        success:function(data){
        	var obj = eval('('+ data +')');
        	data=obj.data;
        	var result="<option value=''>--请选择--</option>";
        	for(var i=0;i<data.length;i++){
        		if($("#toPlaceHid").val()==data[i].toPlaceId){
        			result+="<option value='"+data[i].toPlaceId+"' selected>"+data[i].toPlaceName+"</option>";
        		}else{
        			result+="<option value='"+data[i].toPlaceId+"'>"+data[i].toPlaceName+"</option>";
        		}
        		
        	}
        	$("#toPlace").empty();
        	$("#toPlace").append(result);
        }
	});
	if($("#toPlaceHid").val()!="")
		setToParentPlace($("#toPlaceHid").val());
}
function setToParentPlace(toPlaceId){
	$.ajax({
		type:"post",
        url:"getToParentPlaceList.do",
        data:"containerCode="+$("#containerCode").val()+"&toParentPlace="+toPlaceId,
        error:function(){
            alert("与服务器交互错误!请稍候再试!");
        },
        success:function(data){
        	var obj = eval('('+ data +')');
        	data=obj.data;
        	var result="<option value=''>--请选择--</option>";
        	for(var i=0;i<data.length;i++){
        		if($("#toParentPlaceHid").val()==data[i].toPlaceId){
        			result+="<option value='"+data[i].toPlaceId+"' selected>"+data[i].toPlaceName+"</option>";
        		}else{
        			result+="<option value='"+data[i].toPlaceId+"'>"+data[i].toPlaceName+"</option>";
        		}
        		
        	}
        	$("#toParentPlace").empty();
        	$("#toParentPlace").append(result);
        }
	});
}
function openRecommendSeq(container_product_id,seq){
	$("#recommendSeqValue").val(seq);
	$("#containerProductId").val(container_product_id);
	$("#oldRecommendSeq").val(seq);
	
	openWin('recommendSeq');
}
function showContainerOprationLog(container_product_id){
	$.ajax({
		type:"post",
        url:"getContainerOprationLog.do",
        data:"containerProductId="+container_product_id,
        error:function(){
            alert("与服务器交互错误!请稍候再试!");
        },
        success:function(data){
        	var obj = eval('('+ data +')');
        	data=obj.data;
        	$("#oprationContent").empty();
        	$("#oprationContent").append("<table class=\"zhanshi_table\" cellspacing=\"0\" cellpadding=\"0\"><tbody id='tbody'><tr><th>创建时间</th><th>操作人</th><th>操作内容</th></tr></tbody>");
        	for(var i=0;i<data.length;i++){
        		$("#tbody").append("<tr><td>"+data[i].createTime+"</td><td>"+data[i].oprName+"</td><td>"+data[i].content+"</td></tr>");
        	}
        	$("#tbody").append("</table>");
        }
	});
	openWin('showContainerOprationLog');
}

function setProductType(containerCode){
	if(containerCode!=null){
		if(containerCode=="GNY_RECOMMEND"){
			$("#subProductType").empty();
			$("#subProductType").append("<option value=''>--请选择--</option>");
			if($("#subProductTypeHid").val()=="GROUP_LONG"){
				$("#subProductType").append("<option value='GROUP_LONG' selected>长途跟团游</option>");
			}else{
				$("#subProductType").append("<option value='GROUP_LONG'>长途跟团游</option>");
			}
			if($("#subProductTypeHid").val()=="FREENESS_LONG"){
				$("#subProductType").append("<option value='FREENESS_LONG' selected>长途自由行</option>");
			}else{
				$("#subProductType").append("<option value='FREENESS_LONG'>长途自由行</option>");
			}
			
		}else if(containerCode=="ZBY_RECOMMEND"){
			$("#subProductType").empty();
			$("#subProductType").append("<option value=''>--请选择--</option>");
			if($("#subProductTypeHid").val()=="GROUP"){
				$("#subProductType").append("<option value='GROUP' selected>短途跟团游</option>");
			}else{
				$("#subProductType").append("<option value='GROUP'>短途跟团游</option>");
			}
			if($("#subProductTypeHid").val()=="SELFHELP_BUS"){
				$("#subProductType").append("<option value='SELFHELP_BUS' selected>自助巴士班</option>");
			}else{
				$("#subProductType").append("<option value='SELFHELP_BUS'>自助巴士班</option>");
			}
		}else if(containerCode=="CJY_RECOMMEND"){
			$("#subProductType").empty();
			$("#subProductType").append("<option value=''>--请选择--</option>");
			if($("#subProductTypeHid").val()=="GROUP_FOREIGN"){
				$("#subProductType").append("<option value='GROUP_FOREIGN' selected>出境跟团游</option>");
			}else{
				$("#subProductType").append("<option value='GROUP_FOREIGN'>出境跟团游</option>");
			}
			if($("#subProductTypeHid").val()=="FREENESS_FOREIGN"){
				$("#subProductType").append("<option value='FREENESS_FOREIGN' selected>出境自由行</option>");
			}else{
				$("#subProductType").append("<option value='FREENESS_FOREIGN'>出境自由行</option>");
			}
			
			
		}
	}
}
function showOrHide(container_product_id,isValid){
	
	var showMsg="";
	var updateValue="";
	if(isValid=="Y"){
		updateValue="N";
		showMsg="隐藏";
	}else{
		updateValue="Y";
		showMsg="显示";
	}
	if(confirm("确定要设置成"+showMsg)){
		$.ajax({
			type:"post",
	        url:"showOrHide.do",
	        data:"isValid="+updateValue+"&containerProductId="+container_product_id,
	        error:function(){
	            alert("与服务器交互错误!请稍候再试!");
	        },
	        success:function(data){
	        	if(data=="success"){
	        		alert("设置成功！");
	        		
	        		var toparentplace="";
	        		if($("#toParentPlace").val()==null||$("#toParentPlace").val()==""){
	        			toparentplace="";
	        		}else{
	        			toparentplace=$("#toParentPlace").val();
	        		}
	        			
	        		window.location.href="container.do?containerCode="+$("#containerCode").val()+"&fromPlace="+$("#fromPlace").val()+"&toPlace="+$("#toPlace").val()+"&toParentPlace="+toparentplace+"&subProductType="
	        		+$("#subProductType").val()+"&productId="+$("input[name='productId']").val()+"&productName="
	        		+$("input[name='productName']").val()+"&sortType="+$("#sortType").val();
	        		closeWin('recommendSeq');
	        	}else{
	        		alert("设置失败!");
	        	}
	        }
		});
	}
	
}
