$(function(){	
	
	/**
	 * 地图需要变更标志
	 */
	var changeFlag=false;
	
	/**
	 * 生成行程产品的字符串
	 * 该方法生成当前选中的所有的产品数据的字符串并存放于buyInfo.content当中
	 */
	function buildContent(){
		var hotel=buildHotel();
		var product=buildTicketRoute();
		var body="";
		if(hotel!=''){
			body+=hotel;
		}
		if(product!=''){
			if(body!=''){
				body+=",";
			}
			body+=product;
		}
		var $input=$("#orderForm").find("#buyInfo_content");
		$input.val(body);
		$input.trigger("change");
		
	}	
	
	/**
	 * 生成酒店的字符串.
	 * 酒店需要从选中的单选框当中去取对应的酒店信息.
	 */
	function buildHotel(){
		var $selected=$("input[name^=buyJourney_hotel_]:checked");
		var body="";
		$selected.each(function(i){
			var $this=$(this);
			var jpId=$this.val();
			var $sel=$("select[name=jp_"+jpId+"]");
			var branchId=$sel.attr("branchid");
			var num=$sel.find("option:selected").val();
			var begin=$sel.attr("beginTime");
			var end=$sel.attr("endTime");
			if(i>0){
				body+=",";
			}
			body+="hotel_"+branchId+"_"+jpId+"_"+num+"_"+begin+"_"+end;		
			var $div=$this.parents("div.journey_section");			
			var oldJpId=$div.data("jpId");
			if(oldJpId!=jpId){
				changeFlag=true;
			}
			$div.data("jpId",jpId);
			
		    //触发地图所需数据
			if (changeFlag) {
				var placeBranchId = $this.attr("placeBranchId");
				if (placeBranchId!=null){	
					var setValue = "true";
					splicePlaceParamForMap(placeBranchId, setValue, "HOTEL");
				}
			}
		});
		return body;
	}
	
	function buildTicketRoute(){
		var body="";
		$("select[name^=product_date_]").each(function(i){
			var $this=$(this);
			var jpId=$this.attr("journeyProductId");
			var $numSel=$("select[name=jp_"+jpId+"]");
			var num=parseInt($numSel.find("option:selected").val());
			var oldVal=$numSel.data("cur");
			if(num!=oldVal&&(num==0||oldVal==0)){
				changeFlag=true;
			}
			$numSel.data("cur",num);
			if(num!=NaN&&num>0){
				if($.trim(body)!=''){
					body+=",";
				}				
				var branchId=$this.attr("prodBranchId");
				var begin=$this.find("option:selected").val();
				body+="product_"+branchId+"_"+jpId+"_"+num+"_"+begin;
			}
			
			//触发地图所需数据
			if (changeFlag) {
				var placeBranchId = $("select[name=jp_"+jpId+"]").attr("placeBranchId");			
				if (placeBranchId!=null){				
					var setValue = "false";
					if(num!=NaN&&num>0) {
						setValue = "true";
					}
					splicePlaceParamForMap(placeBranchId, setValue, "TICKET");
				}
			}
		});
		return body;
	}
	
    
	
	
	
	$(document).ready(function(){	
		$("form[name=orderForm] select,form[name=orderForm] input[type=radio]").change(function(){
			changeFlag=false;
			buildContent();
		});		
		$("#buyInfo_content").change(function(){
			$.ajax({url:"/buy/ajaxPriceInfo.do",
				type:"POST",
				async:false,
				data:$("#orderForm").serialize(),
				dataType:"json",
				success:function(data){
					if(data.priceInfo.success){
						$("#free_yd b.free_price").html("￥"+data.priceInfo.price);
					}else{
						alert(data.priceInfo.msg);
					}
				}
			});
			if(changeFlag){
				var placeParam = $("#placeParamId").val();
				var toId = $("#toId").html();
				$("#map_iframe").attr("src","http://www.lvmama.com/dest/googleMap/getCoordinateByPlaceIds.do?mapZoom=9&width=930px&height=390px&toId="+toId+"&placeParam="+placeParam);
			}
		});
		//生存所选产品字符串		
		buildContent();
	});
})