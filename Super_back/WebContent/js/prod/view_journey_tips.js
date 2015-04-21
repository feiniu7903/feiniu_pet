$(function(){
	$("#saveTipButton").click(function(){
		var $form=$("#journeyTipForm");
		var journeyTipTitle = $form.find("input[name=viewJourneyTips.tipTitle]").val();
		if($.trim(journeyTipTitle)==''){
			alert("贴士标题不能为空");
			return false;
		}
		//校验敏感词
		var sensitiveValidator=new SensitiveWordValidator($form, true);
		if(!sensitiveValidator.validate()) {
			return;
		}
		$.ajax({
			url:$form.attr("action"),data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					$("span.msg").show();
					$("span.msg").html('<font color="red">贴士保存成功</font>').fadeOut(5000);
					addJourneyTips(data);
					cleanAddJourneyTipsForm($form);
				}else{
					alert(data.msg);
				}
			}
		})
	});
	
	function addJourneyTips(data){
		var $tr=$("<tr/>");
		$tr.attr("id",data.journeyTips.tipId);
		
		var $td=$("<td/>");
		$td.html(data.journeyTips.tipTitle);
		var $tipTitleLabel = $("<input type='hidden' name='tipTitle"+data.journeyTips.tipId+"' value='"+data.journeyTips.tipTitle+"' class='sensitiveVad' />");
		$td.append($tipTitleLabel);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.journeyTips.tipContent);
		var $tipContentLabel = $("<input type='hidden' name='tipContent"+data.journeyTips.tipId+"' value='"+data.journeyTips.tipContent+"' class='sensitiveVad' />");
		$td.append($tipContentLabel);
		$tr.append($td);
		
		$td=$("<td/>");
		var content = "<a href='javascript:deleteJourneyTips("+data.journeyTips.journeyId+","+data.journeyTips.tipId+");'>删除</a>";
		$td.html(content);
		$tr.append($td);
		$("#journeyTipsTable").append($tr);
		
		var $form = $("form.mySensitiveForm").last(); 
		var sensitiveValidator=new SensitiveWordValidator($form, false);
		sensitiveValidator.validate();
	}
	
	function cleanAddJourneyTipsForm($form){
		$form.find("input[name=viewJourneyTips.tipTitle]").val("");
		$('#tipContent').val("");
		$form.find("label[name=sensitive_label]").remove();
	}
	
	/** zx */
	
});

function deleteJourneyTips(journeyId,tipId){
	$.post("/super_back/view/doDeleteJourneyTips.do",{"journeyId":journeyId,"tipId":tipId,"productId":current_product_id},function(dt){
	var data=eval("("+dt+")");
	if(data.success){
		alert("贴士删除成功");
			deleteJourneyTipFromTipList(tipId);
		}else{
			alert(data.msg);
		}
	});
}

function deleteJourneyTipFromTipList(tipId){
		$('#'+tipId).remove();
}


