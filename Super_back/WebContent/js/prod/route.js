$(function(){
	$("input[type=checkbox][name=productEContract]").click(function(){
		if($(this).attr("checked")){
			$("#productEContract_content").show();			
		}else{
			$("#productEContract_content").hide();
		}
	});
	
	$("input[name=productTravelFormalities][value=OTHERS]").click(function(){
		$("input[name=prodEContract.otherTravelFormalities]").attr("disabled",$(this).attr("checked")?false:true);
	});
	
	$("input[name=productGroupTypes]").click(function(){
		$(":radio[name=product.groupType][value="+$(this).val()+"]").attr("checked",$(this).attr("checked"));
		if("AGENCY"==$(this).val()){
			$(":text[name=prodEContract.agency]").removeAttr("disabled");
		}else{
			$(":text[name=prodEContract.agency]").attr("disabled","disabled");
		}
	});
	
	$(document).ready(function(){
		if($("input[name=productEContract]").attr("checked")){
			$("#productEContract_content").show();
		}
		
		if($("input[name=productTravelFormalities][value=OTHERS]").attr("checked")){
			$("input[name=prodEContract.otherTravelFormalities]").attr("disabled","");
		}else{
			$("input[name=prodEContract.otherTravelFormalities]").attr("disabled","disabled");
		}	
		
		$("input[name=prodEContract.otherTravelFormalities]").attr("disabled",$("input[name=productTravelFormalities][value=OTHERS]").attr("checked")?false:true);
		$("input[name=prodEContract.agency]").attr("disabled",$("input[name=productGroupTypes][value=AGENCY]").attr("checked")?false:true);
		$(":radio[name=product.groupType]").bind("click  change",function(){
			$(":radio[name=productGroupTypes][value="+$(this).val()+"]").attr("checked",$(this).attr("checked"));
			if("AGENCY"==$(this).val()){
				$(":text[name=prodEContract.agency]").removeAttr("disabled");
			}else{
				$(":text[name=prodEContract.agency]").attr("disabled","disabled");
			}
		});
	})
})