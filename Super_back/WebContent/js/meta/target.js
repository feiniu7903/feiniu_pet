$(function(){
	//更换对象
	$("a.change").live("click",function(){
		var tt = $(this).attr("tt");
		if($.trim(tt) == ''){
			alert("要更换的对象不存在");
			return false;
		}
		var result=$(this).attr("result");
		var $div = $("#target_list_div");
		var data;
		if(result != undefined && $.trim(result) != "") {
			data = {"metaProductId": current_meta_product_id, "type": tt, "preTargetId": result};
		} else {
			data = {"metaProductId": current_meta_product_id, "type": tt};
		}
		
		$div.empty().load("/super_back/meta/showTargetList.do", data, function() {
			$div.dialog({
				title : "对象列表",
				width : 1000,
				modal : true
			});
		});
	});
	
	$("a.choose").live("click", function(){
		//重复点击控制
		var me=$(this);
		var status=$(me).data("status");
		if(typeof(status) != "undefined"&&status=="off"){
			return;
		}
		$(me).data("status","off");
		
		var tt=$(this).attr("tt");
		var result=$(this).attr("result");
		
		if($.trim(result)==''){
			alert("对象不存在");
			return false;
		}
		if($.trim(tt)==''){
			alert("选择的对象不存在");
			return false;
		}
		
		var preTargetId = $.trim($("#preTargetId").val());
		$.post("/super_back/meta/changeTarget.do",{"metaProductId":current_meta_product_id,"targetId":result,"type":tt,"preTargetId":preTargetId},function(dt){
			$(me).data("status","on");
			var data = eval("("+dt+")");
			if(data.success){
				fillForm(data.list,tt);
				$("dt").find("a[class='change'][tt='"+tt+"']").hide();
				if(tt == 'META_B_CERTIFICATE') {
					$("div.chuangzhen label").html(data.faxTemplate);
				}
				$("#target_list_div").dialog("close");
			}else{
				alert(data.msg);
			}
		});
	});
	
	function getTargetDetailUrl(tt){
		var url="";
		if(tt=='META_PERFORM'){
				url="/super_back/targets/performtarget/detailperformtarget.zul?targetId="
			}else if(tt=='META_B_CERTIFICATE'){
				url="/super_back/targets/certificatetarget/detailcertificatetarget.zul?targetId=";
			}else if(tt=='META_SETTLEMENT'){
				url="/super_back/targets/settlementtarget/detailsettlementtarget.zul?targetId=";
			}
		
		return url;
	}
	function fillForm(list,tt){
		var $table=$("#"+tt+"_tb");
		$table.find("tr:gt(0)").remove();
		var url=getTargetDetailUrl(tt);
		for(var i=0;i<list.length;i++){
			var data=list[i];
			var $tr=$("<tr/>");
			$tr.attr("id",tt+"_"+data.targetId);
			var $td=$("<td/>");
			$td.html(data.targetId);
			$tr.append($td);
			
			$td=$("<td/>");
			$td.html("<a href='#showTarget' class='showTarget' url='"+url+data.targetId+"'>"+data.name+"</a>");
			$tr.append($td);
			
			if(tt=='META_PERFORM'){
				$td=$("<td/>");
				$td.html(data.zhCertificateType);
				$tr.append($td);
				
				$td=$("<td/>");
				$td.html(data.performInfo);
				$tr.append($td);
				
				$td=$("<td/>");
				$td.html(data.paymentInfo);
				$tr.append($td);
			}else if(tt=='META_B_CERTIFICATE'){
				$td=$("<td/>");
				$td.html(data.viewBcertificate);
				$tr.append($td);
				
				$td=$("<td/>");
				$td.html(data.memo);
				$tr.append($td);
			}else if(tt=='META_SETTLEMENT'){
				$td=$("<td/>");
				$td.html(data.zhSettlementPeriod);
				$tr.append($td);
				
				$td=$("<td/>");
				$td.html(data.memo);
				$tr.append($td);
			}
			
			$td=$("<td/>");
			$td.html("<a href='#change' class='change' tt='"+tt+"' result='"+data.targetId+"'>更换</a>");
			$tr.append($td);
			$table.append($tr);
		}
	}
	
	$("a.showTarget").live("click",function(){
		$("#showTargetDiv").empty().load($(this).attr("url")+"&closeable=false",null,function(dt){
			$(this).dialog({
				title:"采购产品对象相关信息",
				width:1000
			})
		})
	});
})