/**
 * 构造一个自由行的产品项
 * @param {Object} pjId
 * @param {Object} jpId
 * @param {Object} branchId
 * @param {Object} name
 * @param {Object} branch
 * @param {Object} time
 * @param {Object} quantity
 * @memberOf {TypeName} 
 */
function AbstractProduct(jpId,branchId,name,branch,quantity){
	this._jpId=jpId;
	this._branchId=branchId;
	this._name=name;
	this._branch=branch;
	this._quantity=quantity;
	
	this.isSame=function(jpId){
		return this._jpId===jpId;
	};
};
AbstractProduct.prototype.toHtml=function(){}
AbstractProduct.prototype.toString=function(){}


function Hotel(jpId,branchId,name,branch,quantity,btime,etime){
	AbstractProduct.call(this,jpId,branchId,name,branch,quantity);
	this._btime=btime;
	this._etime=etime;
}

function Traffic(jpId,branchId,jpIds,name,branch,time,flgihtInfo){
	AbstractProduct.call(this,jpId,branchId,name,branch,0);//交通产品直接以人数定产品数
	this._time=time;
	this._jpIds = jpIds;
	this._flightInfo = flgihtInfo;
}

function Product(jpId,branchId,name,branch,quantity,time){
	AbstractProduct.call(this,jpId,branchId,name,branch,quantity);
	this._time=time;
}

Hotel.prototype.toHtml=function(){
	var $tr=$("#tr_jp_"+this._jpId);
	//var breakfast=$tr.find("td.breakfast").text();
	//var bedType=$tr.find("td.bedType").text();
	//var broadband=$tr.find("td.broadband").text();	
	var nights=$tr.attr("nights");
	return this._name+"&nbsp;"+this._branch
			//+"&nbsp;"+breakfast
			//+"&nbsp;"+broadband
			+"&nbsp;"+this._quantity+"间"+"&nbsp;"+nights+"晚";
};
Hotel.prototype.toString=function(){
	return "hotel_"+this._branchId+"_"+this._jpId+"_"+this._quantity+"_"+this._btime+"_"+this._etime;
};

Product.prototype.toHtml=function(){
	var $tr=$("#tr_jp_"+this._jpId);
	return this._name+"&nbsp;"+this._branch+"&nbsp;数量："+this._quantity+"&nbsp;游玩日期："+this._time;
};
Product.prototype.toString=function(){
	return "product_"+this._branchId+"_"+this._jpId+"_"+this._quantity+"_"+this._time;
};

Traffic.prototype.toHtml=function(){
	return this._flightInfo;
};
Traffic.prototype.toString=function(){
	return "traffic_"+this._jpIds+"_"+this._time;
};

/**
	 * 填充数量框
	 * @param {Object} $sel
	 * @param {Object} adult
	 * @param {Object} child
	 */
	function fillQuantitySelect($sel,adult,child){
		var stock=parseInt($sel.attr("stock"));
		var type=$sel.attr("tt");
		var quantity=$sel.attr("quantity");
		var require=$sel.attr("require")=='true';
		var defaultBranch=$sel.attr("defaultProduct")=='true';
		var begin=0;
		var end=0;
		if(type=='HOTEL'){
			begin=Math.ceil(adult/quantity);
			end=adult;			
		}else{
			//alert($sel.attr("require")==true);
			if(require){
				begin=1;
			}else{
				begin=0;
			}
			end=(adult+child);
		}
		if(stock!=-1&&end>stock){
			end=stock;
		}
		$sel.empty();
		for(var i=begin;i<=end;i++){
			var $opt=$("<option/>");
			$opt.text(i).val(i);
			$sel.append($opt);
		}
		if(type!='HOTEL'){
			if(defaultBranch){
				$sel.find("option:last").attr("selected",true);
			}
			if(require){
				$sel.find("option:first").attr("selected",true);
			}
		}
		var $opt=$sel.find("option:selected");
		$sel.data("defaultV",$opt.val());
	}
	
	/**
	 * 显示时间下拉框
	 * @param {Object} $sel
	 */
	function fillDateSelect($sel){
		var times=eval("("+$sel.attr("timeList")+")");		
		$sel.empty();
		for(var i=0;i<times.length;i++){
			var str=times[i].time;
			var $opt=$("<option/>");
			$opt.text(str);
			$opt.val(str);
			$opt.attr("stock",times[i].stock);
			$sel.append($opt);
		}
		$sel.data("defaultV",$sel.find("option:first").val());
	}
	
	function updateAllDateSelectBox(){
		var $select=$("select[timeList]");
		$select.each(function(i){
			fillDateSelect($(this));
		});
	}
	/**
	 * 更新所有的相差的价格
	 */
	/*function updateAllDisCount(){
		var $selected=$("input.useProduct:checked");
		$selected.each(function(i){
			changeJourneyProduct($(this));
		});
	}*/
	
	/**
	 * 变更选中的单选框，需要更新对应的产品的价格.
	 * @param {Object} $input
	 */
	function changeJourneyProduct($input){
		var journeyProductId=$input.val();
		var name=$input.attr("name");
		var $span=$("b.discount[journeyProductId="+journeyProductId+"]");
		var price=$span.attr("price");
		$span.html('&nbsp;');//当前的酒店显示空白
		var $list=$("b.discount[journey="+name+"]");
		
		$list.each(function(i){
			var $this=$(this);
			if($this.attr("journeyProductId")!=journeyProductId){
				var cur_price=$(this).attr("price");
				var new_price=cur_price-price;
				var body="￥";
					if(new_price>0){//如果当前的类别是正数就在前面加上+号;
						body+="+";
					}
					body+=new_price;
				$this.html(body);
			}
		});
	}
	function updateSelectBox(){
		var $form=$("#select-travel");
		var adult=parseInt($("#buyInfo_adult").val());
		var child=parseInt($("#buyInfo_child").val());
		$select=$form.find("select[name^='jp']");
		
		$select.each(function(i){
			var $this=$(this);
			fillQuantitySelect($this,adult,child);
		});
	}
	/**
	 * 检查行程是选对应的策略生效正确后提交
	 */
	function checkJourneySelecteAndSubmit(){
		var $list=$("#free_detail input[name^=policy]");	
		var content=$("#buyInfo_content").val();		
		try{
			if($.trim(content)===''){
				throw "您没有选中任何一款产品,不可以预订操作";
			}
			$list.each(function(i){
				var $this=$(this);
				if($this.val()=='true'){//使用了必选
					var type=$this.attr("tt");
					var prodJourneyId=$this.attr("prodJourneyId");					
					if(type=='hotel'){//酒店与交通的判断.						
						var product=$("ul.prod_journey[tt=hotel][prodJourneyId="+prodJourneyId+"]").data("product");						
						if(product===undefined||product==null){
							throw "您还未选择入住酒店";
						}
					}else{//线路门票的判断
						var array=$("ul.prod_journey[tt="+type+"][prodJourneyId="+prodJourneyId+"]").data("product");
						var all_len=$("select[id^=buyJourney_"+type+"_"+prodJourneyId+"]").length;
						if(all_len>0 && (array==undefined||array.length==0)){//判断选中的是否为0及总数大于0.
							if(type == 'ticket'){
								throw "您还有未选择的门票";								
							}else if(type=='route'){
								throw "您还有未选择的当地游";
							}
						}
					}
				}
			});
			document.getElementById("orderForm").submit();
		}catch(ex){
			alert(ex);			
		}
	}
	
	/**
	 * 设置默认的单选按钮避免产品当中没有存在默认的选中的产品.
	 * 现在只有酒店。以后会添加交通.
	 */
	function setDefaultRadio(){
		var $list=$("input[tt=hotel][name^=policy_],input[tt=traffic][name^=policy_]");
		$list.each(function(i){
			var $this=$(this);
			var prodJourneyId=$this.attr("prodJourneyId");
			var type=$this.attr("tt");
			var $input=$("input[name=buyJourney_"+type+"_"+prodJourneyId+"][defaultProduct=true]");
			if($input.attr("checked")==undefined||$input.attr("checked")==false){
				$input.attr("checked",true);
				$input.trigger("change");
			}else{
				var selected=$("input[name=buyJourney_"+type+"_"+prodJourneyId+"]:checked").val();
				if(selected==undefined){
					var $sel=$("input[name=buyJourney_"+type+"_"+prodJourneyId+"]:first");
					$sel.attr("checked",true);
					$sel.trigger("change");
				}
			}
		});
	}
	
	/**
	 * 显示产品
	 * @param {Object} prodId
	 */
	/**function showProductInfo($link){
		var prodId=$link.attr("productId"); 
		if(!prodId){
			return false;
		}
		var $div=$("#select-travel div.free_pop_new");
		var $content=$div.find("div.free_pop_new_inner");
		if($link.data("info")==undefined){
			$.post("/product/productInfo.do",{"productId":prodId},function(dt){
				$content.html(dt);
				$link.data("info",dt);
			});
		}else{
			$content.html($link.data("info"));
		}
		var offset=$link.position();

		$div.css("top",(offset.top+20)+"px").css("left",(offset.left)+"px").show(500);		
	}*/
var global_change_map=false;
function doProdJourneySelected($this){
	var tt=$this.attr("tt");
	var prodJourneyId=$this.attr("prodJourneyId");		
	var $div=$("div.free_hotel_list[category='"+tt+"_"+prodJourneyId+"']");
	if(tt=='hotel'||tt=='traffic'){//如果是酒店只选中一个:checked
		var $selected=$div.find("input.useProduct:checked");
		var product=null;
		if(typeof($selected)!=undefined&&$selected.length>0){
			var $tr=$("#tr_jp_"+$selected.val());	
			if(tt=='hotel'){
				var $jpSelect=$("select[name=jp_"+$selected.val()+"]");
				var branchId=$jpSelect.attr("branchid");
				var btime=$jpSelect.attr("beginTime");
				var etime=$jpSelect.attr("endTime");
				var quantity=$jpSelect.find("option:selected").val();
				//Hotel(jpId,branchId,name,branch,quantity,btime,etime)
				product=new Hotel($selected.val(),
								branchId,
								$tr.attr("pname"),
								$tr.attr("bname"),
								quantity,
								btime,
								etime);
			}else{
				var branchIds=$selected.attr("branchIds");
				var jpIds=$selected.attr("jpIds");
				var goTime=$selected.attr("goTime");
				var direction=$tr.attr("direction");
				var $td=$tr.find("td.trafficInfo");
				var flightInfo="";
				flightInfo+=$td.find("em.start_city").text();
				flightInfo+="<img src='http://pic.lvmama.com/img/new_v/ob_detail/arrow.gif'/>";
				flightInfo+=$td.find("em.end_city").text();
				flightInfo+="&nbsp;&nbsp;";
				flightInfo+=$td.find("em.start_jc").text();
				flightInfo+=$td.attr("flightNo")+"&nbsp;&nbsp;";
				flightInfo+=$td.find("b.start_time").text();
				flightInfo+=$td.find("em.start_jc").text();
				flightInfo+="起飞";
				flightInfo+=" &nbsp;&nbsp;&nbsp;&nbsp; ";
				flightInfo+=$td.find("em.end_time").text();
				flightInfo+="抵达";
				flightInfo+=$td.find("em.end_jc").text();
				//goFlightInfo+=机型再加
				
				if(direction=='ROUND'){//是来回程
					flightInfo+="<br/>";
					$tr2=$("#tr_jp_"+$selected.val()+"_2");			
					$td=$tr2.find("td.trafficInfo");
					flightInfo+=$td.find("em.start_city").text();
					flightInfo+="<img src='http://pic.lvmama.com/img/new_v/ob_detail/arrow.gif'/>";
					flightInfo+=$td.find("em.end_city").text();
					flightInfo+="&nbsp;&nbsp;";
					flightInfo+=$td.find("em.start_jc").text();
					flightInfo+=$td.attr("flightNo")+"&nbsp;&nbsp;";
					flightInfo+=$td.find("b.start_time").text();
					flightInfo+=$td.find("em.start_jc").text();
					flightInfo+="起飞";
					flightInfo+=" &nbsp;&nbsp;&nbsp;&nbsp; ";
					flightInfo+=$td.find("em.end_time").text();
					flightInfo+="抵达";
					flightInfo+=$td.find("em.end_jc").text();
				}
				//Traffic(jpId,branchId,branchIds,jpIds,name,branch,time,flgihtInfo)
				product = new Traffic($selected.val(),
						$selected.attr("branchid"),
						jpIds,
						$tr.attr("pname"),
						$tr.attr("bname"),
						goTime,
						flightInfo);
			}
		}
		var oldProduct=$this.data("product");
		if(oldProduct==undefined||(product!=null&&!product.isSame(oldProduct._jpId))){//表示已经修改
			var placeBranchId=$selected.attr("placeBranchId");
			var map_type=tt.toUpperCase();
			splicePlaceParamForMap(placeBranchId, "true", map_type);
			if(oldProduct!=undefined){
				var $oldSelected=$("input.useProduct[value="+oldProduct._jpId+"]");
				placeBranchId=$oldSelected.attr("placeBranchId");
				splicePlaceParamForMap(placeBranchId, "false", map_type);
			}
		}
		$this.data("product",product);
		if(product!=null){
			$this.find("li.content").html(product.toHtml());			
		}else{
			var zhType="";
			if(tt=='hotel'){
				zhType="酒店";
			}else if(tt=='traffic'){
				zhType="大交通";
			}
			$this.find("li.content").html("<b>未选中"+zhType+"</b>");
		}
	}else{
		var $branch_list=$div.find("select[name^=jp_]");		
		var array=new Array();
		var oldArray=$this.data("product");
		$branch_list.each(function(i){
			var $sock=$(this);
			var val=parseInt($sock.find("option:selected").val());
			var jpId=$sock.attr("journeyProductId");
			if(val!=NaN&&val>0){
				//Product(jpId,branchId,name,branch,quantity,time)
				var $product_date=$("select[name^=product_date_"+jpId+"]");
				//var jpId=$product_date.attr("journeyProductId");
				var branchId=$product_date.attr("prodBranchId");
				var time=$product_date.find("option:selected").val();
				var $tr=$("#tr_jp_"+jpId);
				
				var product=new Product(
						jpId,
						branchId,
						$tr.attr("pname"),
						$tr.attr("bname"),
						val,
						time);
				
				array.push(product);
			}
		});

		
		if(array.length==0&&oldArray!=undefined&&oldArray.length>0){
			for(var i=0;i<oldArray.length;i++){
				var placeBranchId = $("select[name=jp_"+oldArray[i]._jpId+"]").attr("placeBranchId");				
				splicePlaceParamForMap(placeBranchId, "false", "TICKET");
			}
		}else if(oldArray==undefined||oldArray.length==0&&array.length>0){
			for(var i=0;i<array.length;i++){
				var placeBranchId = $("select[name=jp_"+array[i]._jpId+"]").attr("placeBranchId");
				splicePlaceParamForMap(placeBranchId, "true", "TICKET");
			}
		}else{//新旧都有的情况
			for(var i=0;i<array.length;i++){
				for(var j=0;j<oldArray.length;j++){
					if(!oldArray[j].isSame(array[i]._jpId)){
						var placeBranchId = $("select[name=jp_"+oldArray[j]._jpId+"]").attr("placeBranchId");				
						splicePlaceParamForMap(placeBranchId, "false", "TICKET");
					}
				}
			}
			
			for(var i=0;i<oldArray.length;i++){
				for(var j=0;j<array.length;j++){
					if(!array[j].isSame(oldArray[i]._jpId)){
						var placeBranchId = $("select[name=jp_"+array[j]._jpId+"]").attr("placeBranchId");				
						splicePlaceParamForMap(placeBranchId, "true", "TICKET");
					}
				}
			}
		}		
		$this.data("product",array);
		var $li=$this.find("li.content");
		if(array.length==0){
			$li.html("<b>未选择景点</b>");			
		}else{
			var body="<b>"+array.length+"个景点</b><br/>";			
			for(var i=0;i<array.length;i++){
				body+=array[i].toHtml();
				body+="<br/>";
			}			
			$li.html(body);
		}
		$li.parent().css("height",$li.height());
	}
}
/**
 * 显示每个行程段当中各类型已经选中的产品,第一次有效，
 * 并且把值保存在data当中供重新进入使用
 */
function showAllProdJourneySelected(){
	var $list=$("ul.prod_journey");
	global_change_map=false;
	$list.each(function(i){
		doProdJourneySelected($(this));
	});
}

/**
 * 显示选中的产品的价格
 * @memberOf {TypeName} 
 */
function changeSelectPrice(){
	var $list=$("ul.prod_journey");
	var body="";
	$list.each(function(i){
		var $this=$(this);
		var tt=$this.attr("tt");
		if(tt=='hotel'||tt=='traffic'){
			var product=$this.data("product");
			if(product!=undefined){
				if(body!=''){
					body+=";";
				}
				body+=product.toString();
			}
		}else{
			var array=$this.data("product");
			if(array!=undefined){
				if(array.length>0){
					for(var i=0;i<array.length;i++){
						if(body!=''){
							body+=";";
						}
						body+=array[i].toString();
					}
				}
			}
		}
	});
	$("#buyInfo_content").val(body).trigger("change");
}

function hideDetail($this){	
	$this.find("a.free_change_a").trigger("click");
}

/**
 * 拼接获取地图请求参数
 * @param {Object} placeBranchId 景区类别ID
 * @param {Object} setValue 设置值.true选中，false取消选中
 */
function splicePlaceParamForMap(placeBranchId, setValue, productType){
	if(placeBranchId==undefined||placeBranchId==''){
		return;
	}
	var pbId = placeBranchId.split("_");
	if (pbId.length==2 && pbId[0]!="") {
		var placeParams = $("#placeParamId").val();
		var pps = placeParams.split(";");
		var data = "";
		
		for (var i=0; i<pps.length; i++) {
			var ps = pps[i].split(",");
			if (ps.length==6) {					
				if (ps[0]==pbId[0] && ps[5]==pbId[1] && ps[4]!=setValue){						
					pps[i] = modifyPlaceParam(ps, setValue);						
				} else if(productType=="HOTEL" && ps[1]=="HOTEL" && ps[4]=="true") {
					pps[i] = modifyPlaceParam(ps, "false");
				}
				data = data + pps[i] + ";";	
			    
		    }
		}
		global_change_map=true;
		$("#placeParamId").val(data);
	}
}
/**
 * 修改景区参数
 * @param {Object} ps
 * @param {Object} setValue
 * @return {TypeName} 
 */
function modifyPlaceParam(ps, setValue) {
	var pps = "";
	ps[4] = setValue;
	for (var j=0; j<ps.length; j++) {
		pps = pps + ps[j] + ",";			
	} 
	return pps.substr(0,pps.length-1);
}
var first_handle=true;
var FREENESS_FOREIGN_FLAG=true;
//超级自由行的行程操作时使用
$(document).ready(function(){
	//选中一个行程当中的产品
	$("input.useProduct").change(function(){
		changeJourneyProduct($(this));
	});	
	//保存选中的产品
	$("input.free_save_btn").click(function(){
		var tt=$(this).attr("tt");
		var prodJourneyId=$(this).attr("prodJourneyId");
		var $ul=$("ul.prod_journey[tt="+tt+"][prodJourneyId="+prodJourneyId+"]");
		global_change_map=false;
		doProdJourneySelected($ul);
		hideDetail($ul);
		changeSelectPrice();
	});
	$("a.free_change_a").click(function(){
		var $this=$(this);
		var open=$this.data("open");
		if(open==undefined||!open){//新开
			open=true;
		}else{//关闭操作
			open=false;
		}
		$this.data("open",open);
		if(open){//打开操作需要把数据回填到对应的表单当中
			var $ul=$this.parents("ul.prod_journey");
			var tt=$ul.attr("tt");
			var prodJourneyId=$ul.attr("prodJourneyId");
			if(tt=='hotel'){
				var product=$ul.data("product");
				var $list=$("div.free_hotel_list[category=hotel_"+prodJourneyId+"] input.useProduct");
				
				$list.each(function(i){
					var $this=$(this);
					var value=$this.val();
					var $sockSelect=$("#jp_"+value);
					if(product!=undefined && product.isSame(value)){
						$sockSelect.find("option[value="+product._quantity+"]").attr("selected",true);
						$this.attr("checked",true);						
					}else{						
						var defaultV=$sockSelect.data("defaultV");
						$sockSelect.find("option[value="+defaultV+"]").attr("selected",true);
					}
				});
				var $input=$("div.free_hotel_list[category=hotel_"+prodJourneyId+"] input.useProduct:checked");			
				changeJourneyProduct($input);
			}else{
				var array=$ul.data("product");
				//alert(array.length);
				var $list=$("div.free_hotel_list[category="+tt+"_"+prodJourneyId+"] select[name^=product_date_]");				
				$list.each(function(i){
					var $this=$(this);
					var jpId=$this.attr("journeyProductId");
					var product=null;
					if(array!=undefined&&array!=null){						
						for(var i=0;i<array.length;i++){
							if(array[i].isSame(jpId)){
								product=array[i];
								break;
							}
						}
					}
					var $sockSelect=$("select[name=jp_"+jpId+"]");
					if(product!=null){
						$this.find("option[value="+product._quantity+"]").attr("selected",true);
						$sockSelect.find("option[value="+product._time+"]").attr("selected",true);
					}else{						
						var defaultV=$this.data("defaultV");//alert("select:"+defaultV)
						$this.find("option[value="+defaultV+"]").attr("selected",true);
							
						defaultV=$sockSelect.data("defaultV");//alert("q:"+defaultV);
						$sockSelect.find("option[value="+defaultV+"]").attr("selected",true);						
					}
				});
			}
		}
	});
	//如果是超级自由行中出境自由行，将地图隐藏，否则显示地图
 	if($("#subProductType").val()=='FREENESS_FOREIGN'){
		$("#free_map").hide();
		FREENESS_FOREIGN_FLAG=false;
	}else {
		$("#free_map").show();
		FREENESS_FOREIGN_FLAG=true;
	}
 	$("#buyInfo_content").change(function(){
		$.ajax({url:"/buy/ajaxPriceInfo.do",
			type:"POST",
			async:false,
			data:$("#orderForm").serialize(),
			dataType:"json",
			success:function(data){
				if(data.priceInfo.success){
					if(data.priceInfo.orderDiscountPrice>0){
						$("#free_yd span.coupon_price_pos").show();
						$("#free_yd b.total_discount_price").html("￥"+data.priceInfo.orderDiscountPrice);
					}
					$("#free_yd b.free_price").html("￥"+data.priceInfo.price);
				}else{
					alert(data.priceInfo.msg);
				}
			}
		});
 		if((global_change_map||first_handle)&&FREENESS_FOREIGN_FLAG){
				first_handle=false;
				FREENESS_FOREIGN_FLAG=false;
				var placeParam = $("#placeParamId").val();
				var toId = $("#toId").html();
				$("#map_iframe").attr("src","http://www.lvmama.com/dest/googleMap/getCoordinateByPlaceIds.do?mapZoom=14&width=870px&height=370px&toId="+toId+"&placeParam="+placeParam);
	      }
	});
	//恢复到初始状态
	$("#recommend_xc").click(function(){
		var $list=$("ul.prod_journey");
		$list.each(function(i){
			$(this).removeData("product");			
			var tt=$(this).attr("tt");			
			if(tt=='hotel'||tt=='traffic'){
				var prodJourneyId=$(this).attr("prodJourneyId");
				var $input=$("div.free_hotel_list[category="+tt+"_"+prodJourneyId+"] input.useProduct:checked");
				if($input!=undefined&&$input.length!=0){
					//alert($input.parent().html());
					$input.attr("checked",false);
				}
			}
		});		
		updateSelectBox();	
		updateAllDateSelectBox();
		setDefaultRadio();
		showAllProdJourneySelected();
		changeSelectPrice();
	});
	updateSelectBox();	
	updateAllDateSelectBox();
	setDefaultRadio();
	
	showAllProdJourneySelected();
	changeSelectPrice();
});