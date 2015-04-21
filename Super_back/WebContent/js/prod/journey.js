$(function(){
	$(".newForm").click(function(){
		$("#newFormDiv").find("input[type=text]").val("");
		$("#newFormDiv").find("input[name=prodJourney.productId]").val(current_product_id);
		$("#newFormDiv").show();
	});
	
	function createTd(type){
		$td=$("<td/>");
		$td.addClass(type);
		$td.html('<div class="content"></div><div><span><a href="#edit" class="edit" tt="'+type.toUpperCase()+'">修改产品</a></span></div>');
		return $td;
	}
	
	$(".newPackForm").click(function(){
		showProdcutPackDiv(0);
	});
	/**
	 * 填充添加的信息
	 * @param {Object} data
	 * @param {Object} hasNew
	 */
	function fillTd(data,hasNew){
		var $tr=$("<tr/>");
		if(!hasNew){
			$tr=$("#tr_pj_"+data.prodJourenyId);			
		}else{
			$tr.attr("id","tr_pj_"+data.prodJourenyId);
			$tr.attr("pjId",data.prodJourenyId);
		}
		
		var $td=$("<td/>");
		$td.html(data.prodJourenyId);
		$tr.append($td);
		
		var $td=$("<td class='time'/>");
		$td.html(data.journeyTimeStr);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.fromPlace.name+"到"+data.toPlace.name);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.fromPlace.name);
		$tr.append($td);
		
		$td=$("<td/>");
		$td.html(data.toPlace.name);
		$tr.append($td);
		
		if(hasNew){
			if(has_traffic){				
				$td=createTd("traffic");
				$tr.append($td);
			}
			if(has_hotel){
				$td=createTd("hotel");
				$tr.append($td);
			}
			if(has_ticket){
				$td=createTd("ticket");
				$tr.append($td);
			}
			if(has_route){
				$td=createTd("route");
				$tr.append($td);
			}
			
			$td=$("<td/>");
			$td.addClass("op");
			$td.html('<a href="#delete" class="journey_delete" result="'+data.prodJourneyId+'">删除</a>&nbsp;&nbsp;<a href="#edit" class="edit_journey_time" result="'+data.prodJourenyId+'" time="'+data.journeyTime+'">修改行程时间</a><a href="#log" class="showLogDialog"'+
					 ' param="{\'parentType\':\'PROD_PRODUCT_JOURNEY\',\'parentId\':'+data.prodJourneyId+'}">操作日志</a>');
			$tr.append($td);
			$("#journey_tb").append($tr);
		}else{
			if(has_traffic){
				$td=$tr.find("td.traffic");
				$tr.append($td);
			}
			
			if(has_hotel){
				$td=$tr.find("td.hotel");
				$tr.append($td);
			}
			
			if(has_ticket){
				$td=$tr.find("td.ticket");
				$tr.append($td);
			}
			
			if(has_route){
				$td=$tr.find("td.route");
				$tr.append($td);
			}
			
			$tr.append($tr.find("td.op"));
		}		
	}
	
	$("#journeyFormBtn").click(function(){
		var $form=$("form[name="+$(this).attr("ff")+"]");
		$.ajax({
			url:$form.attr("action"),
			data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					fillTd(data.prodJourney,data.hasNew);
					$("#newFormDiv").hide();
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	$("a.edit").live("click",function(){
		var tt=$(this).attr("tt");
		var $tr=$(this).parents("tr");
		var pjId=$tr.attr("pjId");
		$("#journeyProductDiv").load("/super_back/prod/loadJourneyProductList.do",{"prodJourneyId":pjId,"type":tt}).show();
	});
	//更新行程上的产品数据
	function updateProductShow(pjId,tt){
		$.post("/super_back/prod/loadJourneyUniqueProduct.do",{"prodJourneyId":pjId,"type":tt},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				var $div=$("#tr_pj_"+pjId+" td."+tt.toLowerCase()+" div.content");
				$div.empty();
				if(typeof(data.list)!=='undefined'){
					for(var i=0;i<data.list.length;i++){
						$div.append($("<span>"+data.list[i]+"</span>"));
					}
				}
			}
		});
	}
	
	$("em.saveJourneyProduct").live("click",function(){
		var $form=$("form[name="+$(this).attr("ff")+"]");
		$.ajax({
			url:$form.attr("action"),
			data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					var tt=$form.find("input[name=productType]").val();
					var pjId=$form.find("input[name=prodJourneyProduct.prodJourenyId]").val();
					$("#journeyProductDiv").load("/super_back/prod/loadJourneyProductList.do",{"prodJourneyId":pjId,"type":tt}).show();
					updateProductShow(pjId,tt);
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	$("a.journey_delete").live("click",function(){
		if(!confirm("确定要删除当前的行程")){
			return false;
		}
		
		var $this=$(this);
		var $tr=$this.parents("tr");
		var pjId=$tr.attr("pjId");
		$.post("/super_back/prod/deleteProdJourney.do",{"prodJourneyId":pjId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$tr.remove();
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("a.jp_delete").live("click",function(){
		if(!confirm("确定要删除当前的打包产品")){
			return false;
		}
		var $tr=$(this).parents("tr");
		var jpId=$tr.attr("jpId");
		if(!jpId){
			alert("要删除的对象不存在");
			return false;
		}
		
		var productId=$tr.attr("productId");		
		$.post("/super_back/prod/deleteJourneyProduct.do",{"prodJourneyProduct.journeyProductId":jpId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$tr.remove();
				if($("tr[productId="+productId+"]").size()==0){//不存在时同时把产品对应的信息删除
					$("#product_"+productId).remove();
					var pjId=$("input[name=prodJourneyProduct.prodJourenyId]").val();
					var tt=$("input[name=productType]").val();
					updateProductShow(pjId,tt);
				}
			}else{
				alert(data.msg);
			}
		});
	});
	
	$("a.discount").live("click",function(){
		var $this=$(this);
		var $tr=$this.parents("tr");
		var jpId=$tr.attr("jpId");
		var $dlg=$("#editDiscountDiv");		
		$dlg.find("input[name=journeyProductId]").val(jpId);
		$dlg.find("input[name=discount]").val($this.attr("ds"));
		
		$dlg.dialog({
			modal:true,
			title:"修改优惠金额",
			width:400,
			buttons:{
				"保存":function(){
					var discount=$dlg.find("input[name=discount]").val();
					if($.trim(discount)==''){
						alert("优惠金额不可以为空");
						return false;
					}
					var iDs=parseFloat(discount);
					if(iDs==NaN||iDs<0){
						alert("优惠金额不可以小于0");
						return false;
					}
					jpId=$dlg.find("input[name=journeyProductId]").val();
					
					$.post("/super_back/prod/changeJPDiscount.do", {
						"prodJourneyProduct.journeyProductId" : jpId,
						"prodJourneyProduct.discountYuan" : discount
					}, function(dt) {
						var data=eval("("+dt+")");
						if(data.success){
							$tr.find("td.discount").html(data.discountYuan+"元");
							$this.attr("ds",data.discountYuan);
							$dlg.dialog("close");
						}else{
							alert(data.msg);
						}
					});
				}
			}			
		});
	});
	
	$("input[name=require]").live("click",function(){
		var jpId=$(this).val();
		var checked=$(this).attr("checked");
		var $this=$(this);
		var $form=$(this).parents("form");
		var productType=$form.find("input[name=productType]").val();
		$.post("/super_back/prod/changeJourneyRequire.do",{"prodJourneyProduct.journeyProductId":jpId,"checked":checked},function(dt){
			var data=eval("("+dt+")");
			if(!data.success){
				alert(data.msg);
			}else{
				if(checked&&productType=='HOTEL'){
					$("input[name=require]:checked").not($this).attr("checked",false);
				}
			}
		});
	});
	
	$("input[name=defaultProduct]").live("click",function(){
		var $this=$(this);
		var checked=$(this).attr("checked");
		var jpId=$(this).val();
		$.post("/super_back/prod/changeJourneyDefault.do",{"prodJourneyProduct.journeyProductId":jpId,"checked":checked},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				if(checked){//如果是选中，需要把其他的同类的产品需要去掉选中状态
					$("input[name=defaultProduct]").not($this).attr("checked",false);
				}
			}else{
				alert(data.msg);
			}
		});
		
	});
	
	$("input[name=policy]").live("click",function(){
		var val=$(this).val();
		var $form=$("form[name=journeyProductForm]");
		var data={"prodJourney.prodJourenyId":$form.find("input[name=prodJourneyProduct.prodJourenyId]").val(),
				"type":$form.find("input[name=productType]").val(),
				"checked":val=='true'
			};
		$.post("/super_back/prod/changeJourneyPolicy.do",data,function(dt){
			var data=eval("("+dt+")");
			if(!data.success){
				alert(data.msg);
			}
		});
	});
	
	$("a.edit_journey_time").live("click",function(){
		var $this=$(this);
		var pjId=$(this).attr("result");
		var time=$(this).attr("time");
		var $tr=$this.parents("tr");
		var $dlg=$("#editJourneyTimeDiv");
		var array=time.match(/(\d+)\-(\d+)===(\d+)\-(\d+)/);
		if(array!=null){
			$dlg.find("input[name=prodJourney.minTime.days]").val(array[1]);
			$dlg.find("input[name=prodJourney.minTime.nights]").val(array[2]);
			$dlg.find("input[name=prodJourney.maxTime.days]").val(array[3]);
			$dlg.find("input[name=prodJourney.maxTime.nights]").val(array[4]);
		}
		$dlg.find("input[name=prodJourney.prodJourenyId]").val(pjId);
		$dlg.dialog({
			modal:true,
			title:"修改行程时间",
			width:400,
			buttons:{
				"保存":function(){
					var $form=$dlg.find("form");
					$.ajax({
						url:$form.attr("action"),
						data:$form.serialize(),
						type:"POST",
						success:function(dt){
							var data=eval("("+dt+")");
							if(data.success){
								$this.attr("time",data.journeyTime);
								$tr.find("td.time").html(data.journeyTimeStr);
								$dlg.dialog("close");
							}else{
								alert(data.msg);
							}
						}
					});
				}
			}
		});
	});
	$(".updatePackValid").live("click",function(){
		var packId =$(this).attr("result");
		var online =$(this).attr("online");
		$.post("/super_back/prod/updatePackOnLine.do",{"prodProductJourneyPack.onLine":online,"packId":packId},function(dt){
			var data=eval("("+dt+")");
			if(data.result){
				showPackInfo();
				alert("操作成功！");
			}else{
				alert(data.msg);
			}
		});
		
	});
	$(".edit_Pack").live("click",function(){
		var packId =$(this).attr("result");
		showProdcutPackDiv(packId);
		
	});
	$(".pack_delete").live("click",function(){
		if(!confirm("确定要删除当前的套餐")){
			return false;
		}
		var packId =$(this).attr("result");
		$.post("/super_back/prod/delPack.do",{"packId":packId},function(dt){
			var data=eval("("+dt+")");
			if(data.result){
				showPackInfo();
			}else{
				alert(data.msg);
			}
		});
	});
	showPackInfo();
});

function showProdcutPackDiv(data) {
	$("#create_journey_pack_div").showWindow({
		width : document.body.offsetWidth,
		height : document.body.offsetHeight,
		title : '编辑套餐',
		data : {
			'packId': data
		}
	});
}
function showPackInfo(){
	var productId = $("#productId").val();
	if(!productId){
		return;
	}else{
		$.post("/super_back/prod/getProdPackListJSON.do",{"productId":productId},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				if(data.find){
					to_showPackDiv(data.ProdProductJourneyPacks);
				}
			}
		});
	}
}

function to_showPackDiv(data){
	var html='<div><h3>套餐</h3></div>';
		html = html + '<table width="90%" border="0" cellspacing="0" cellpadding="0" id="journey_tb" class="newTable">';
		html = html + '<tr class="newTableTit">';
		html = html + '<td>套餐名称</td>';
		html = html + '<td>时间天数</td>';
		html = html + '<td>交通</td>';
		html = html + '<td>酒店</td>';
		html = html + '<td>门票</td>';
		html = html + '<td>当地游</td>';
		html = html + '<td>状态</td>';
		html = html + '<td>操作</td>';
		html = html + '</tr>';
		html = html + getContentHtml(data);
		html = html + '</table>';
		
	$("#showPackDiv").html(html);
}
function getContentHtml(data){
	var html="";
	for(var n=0;n<data.length;n++){
		var pack = data[n];
		html = html + '<tr>';
		html = html + '<td align="center">' + pack.packName + '</td>';
		html = html + getProductHtml(pack);
		if("true"==pack.onLine){
			html = html + '<td align="center">上线</td>';
		}else{
			html = html + '<td align="center">下线</td>';
		}
		html = html + '<td class="op">';
		html = html + '<a href="#edit" class="edit_Pack" result="' + pack.packId + '" time="${journey.journeyTime}">修改</a>';
		html = html + '<a href="#delete" class="pack_delete" result="' + pack.packId + '">删除</a>';
		html = html + '<a href="#log" class="showLogDialog" param="{\'parentType\':\'PROD_PACK_PRODUCT\',\'parentId\':' + pack.packId + '}">操作日志</a>';
		if("true"==pack.onLine){
			html = html + '<a href="#edit" class="updatePackValid" result="' + pack.packId + '" online="false">下线</a>';
		}else{
			html = html + '<a href="#edit" class="updatePackValid" result="' + pack.packId + '" online="true">上线</a>';
		}
		
		html = html + '</td>';
		html = html + '</tr>';
	}
	return html;
}
function getProductHtml(pack){
	var html="",tiStr="",traffic="",hotel="",ticket="",route = "";
	for(var j =0;j<pack.prodProductJourneys.length;j++){
		var pp = pack.prodProductJourneys[j];
		tiStr = tiStr +  pp.journeyTimeStr + '<br/><br/>';
		traffic = traffic + getContent("traffic", pp.trafficList)+ '<br/><br/>';
		hotel = hotel + getContent("hotel", pp.hotelList)+ '<br/><br/>';
		ticket = ticket + getContent("ticket", pp.ticketList)+ '<br/><br/>';
		route = route + getContent("route", pp.routeList)+ '<br/><br/>';
	}
	html = html +"<td>" + tiStr + "</td>";
	html = html +"<td>" + traffic + "</td>";
	html = html +"<td>" + hotel + "</td>";
	html = html +"<td>" + ticket + "</td>";
	html = html +"<td>" + route + "</td>";
	return html;
}
function getContent(type, data){
	var s ='';
   		for(var i=0;i<data.length;i++){
   			s = s + '<span>'+ data[i].productName +'</span>';	
   		}
     return s;      
}
function showBranchSel(id){
		var $sel=$("select[name=prodJourneyProduct.prodBranchId]");
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
							$opt.val(data.list[i].branchId);														
							$opt.text(data.list[i].branchName);
							$opt.appendTo($sel);
						}
					}
				}else{
					alert(data.msg);
				}
			});
		}
	}
	function suggest(){			
		$("#productSuggest").jsonSuggest({
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
	}
