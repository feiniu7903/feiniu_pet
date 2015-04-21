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
AbstractProduct.prototype.toHtml=function(){};
AbstractProduct.prototype.toString=function(){};


function Hotel(jpId,branchId,name,branch,quantity,btime,etime,smallImage,branchName,zhBreakfast,bedType,description,prodJourneyId,productId,hotelStar){
	AbstractProduct.call(this,jpId,branchId,name,branch,quantity);
	this._btime=btime;
	this._etime=etime;
	this._smallImage=smallImage;
	this._branchName=branchName;
	this._zhBreakfast=zhBreakfast;
	this._bedType=bedType;
	this._description=description;
	this._prodJourneyId=prodJourneyId;
	this._productId=productId;
	this._hotelStar=hotelStar;
}

function Traffic(jpId,branchId,jpIds,name,branch,time,flgihtInfo){
	AbstractProduct.call(this,jpId,branchId,name,branch,0);//交通产品直接以人数定产品数
	this._time=time;
	this._jpIds = jpIds;
	this._flightInfo = flgihtInfo;
}
function Ticket(jpId,branchId,name,branch,quantity,time,smallImage,prodJourneyId,tt,productId){
	AbstractProduct.call(this,jpId,branchId,name,branch,quantity);
	this._time=time;
	this._smallImage=smallImage;
	this._prodJourneyId=prodJourneyId;
	this._tt=tt;
	this._productId=productId;
}
function Product(jpId,branchId,name,branch,quantity,time,smallImage,prodJourneyId,tt,productId){
	AbstractProduct.call(this,jpId,branchId,name,branch,quantity);
	this._time=time;
	this._smallImage=smallImage;
	this._prodJourneyId=prodJourneyId;
	this._tt=tt;
	this._productId=productId;
}

Hotel.prototype.toHtml=function(){
	var html="";
	html=html+'  <div class="dayBox1">';
	html=html+'      <div class="dayBox1_L">'+(this._smallImage!=undefined&&this._smallImage!=null&&this._smallImage!=''?'<img width="87" height="58" src="http://pic.lvmama.com/pics/'+this._smallImage+'">':'')+'</div>';
	html=html+'      <div class="dayBox1_R">';
	html=html+'          <h5><b class="jiudianText" tt="hotel" prodJourneyId="'+this._prodJourneyId+'" productId="'+this._productId+'">'+this._name+'</b>';
	if(this._hotelStar!=""){
		switch (this._hotelStar) {
		case "1":
			html=html+'				<span class="tuan-diamond vm-tuan" title="经济型（驴妈妈用户评定为1.5钻）"><i style="width:30%;"></i></span>';
			break;
		case "2":
			html=html+'				<span class="tuan-star" title="国家旅游局评定为二星级"><i style="width:40%;"></i></span>';
			break;
		case "3":
			html=html+'				<span class="tuan-diamond vm-tuan" title="舒适型（驴妈妈用户评定为2.5钻）"><i style="width:50%;"></i></span>';
			break;
		case "4":
			html=html+'				<span class="tuan-star" title="国家旅游局评定为三星级"><i style="width:60%;"></i></span>';
			break;
		case "5":
			html=html+'				<span class="tuan-diamond vm-tuan" title="高档型（驴妈妈用户评定为3.5钻）"><i style="width:70%;"></i></span>';
			break;
		case "6":
			html=html+'				<span class="tuan-star" title="国家旅游局评定为四星级"><i style="width:80%;"></i></span>';
			break;
		case "7":
			html=html+'				<span class="tuan-diamond vm-tuan" title="豪华型（驴妈妈用户评定为4.5钻）"><i style="width:90%;"></i></span>';
			break;
		case "8":
			html=html+'				<span class="tuan-star" title="国家旅游局评定为五星级"><i style="width:100%;"></i></span>';
			break;

		default:
			break;
		}
	}
	html=html+'			</h5>';
	
	
	
	html=html+'          <table class="dayTable">';
	html=html+'              <tr>';
	html=html+'                  <th>房型</th>';
	html=html+'                  <th width="80">早餐</th>';
	html=html+'                  <th width="80">床型</th>';
	html=html+'                  <th width="250">房间数</th>';
	html=html+'                  <th width="80">&nbsp;</th>';
	html=html+'              </tr>';
	html=html+'              <tr>';
	html=html+'                  <td><a class="dayTableName" href="javascript:void(0)" target="_self">'+this._branchName+'</a></td>';
	html=html+'                  <td>'+this._zhBreakfast+'</td>';
	html=html+'                  <td>'+this._bedType+'</td>';
	html=html+'                  <td>'+this._quantity+'</td>';
	html=html+'                  <td></td>';
	html=html+'              </tr>';
	html=html+'              <tr class="dayTableText">';
	html=html+'              	<td colspan="5">';
	html=html+'                      <p>';
	html=html+'                      '+this._description;
	html=html+'                      </p>';
	html=html+'                      <span class="TableTextHide">隐藏</span>';
	html=html+'                  </td>';
	html=html+'              </tr>';
	html=html+'          </table>';
	html=html+'      </div>';
	html=html+'  </div>';
	return html;
};
Hotel.prototype.toString=function(){
	return "hotel_"+this._branchId+"_"+this._jpId+"_"+this._quantity+"_"+this._btime+"_"+this._etime;
};

Product.prototype.toHtml=function(){
	var html="";
	html=html+'        <li>';
	html=html+'            <div class="dayBox2_L">'+(this._smallImage!=undefined&&this._smallImage!=null&&this._smallImage!=''?'<img width="87" height="58" src="http://pic.lvmama.com/pics/'+this._smallImage+'">':'')+'</div>';
	html=html+'            <div class="dayBox2_R">';
	html=html+'                <h5><span class="jingdianText" tt="'+this._tt+'" prodJourneyId="'+this._prodJourneyId+'" productId="'+this._productId+'">'+this._name+'</span></h5>';
	html=html+'                <p>'+this._branch+'：<span>'+this._quantity+'</span></p>';
	html=html+'            </div>';
	html=html+'        </li>';
	//return this._name+"&nbsp;"+this._branch+"&nbsp;数量："+this._quantity+"&nbsp;游玩日期："+this._time;
	return html;
};
Product.prototype.toString=function(){
	return "product_"+this._branchId+"_"+this._jpId+"_"+this._quantity+"_"+this._time;
};

Ticket.prototype.toHtml=function(){
	var html="";
	var maxNum = 0;
	$(".free_input").each(function(){
		maxNum = maxNum + Number($(this).val());
	});
	html=html+'        <li>';
	html=html+'            <div class="dayBox2_L">'+(this._smallImage!=undefined&&this._smallImage!=null&&this._smallImage!=''?'<img width="87" height="58" src="http://pic.lvmama.com/pics/'+this._smallImage+'">':'')+'</div>';
	html=html+'            <div class="dayBox2_R">';
	html=html+' 		   <h5><span class="jingdianText" tt="'+this._tt+'" prodJourneyId="'+this._prodJourneyId+'" productId="'+this._productId+'">'+this._name+'</span></h5>';
	html=html+' 		   <p>'+this._branch+'：<span><select onchange="chanageNum(\'buyJourney_ticket_'+ this._prodJourneyId +'_'+this._jpId+'\','+this._prodJourneyId+',this);">';
	for(var i=1;i<=maxNum;i++){
		if(i==this._quantity){
	html=html+'			   <option selected="selected" value="'+i+'">'+i+'</option>';
		}else{
	html=html+'			   <option value="'+i+'">'+i+'</option>';
		}
	}
	html=html+'			   </select></span></p>';
	html=html+' 		   </div>';
	html=html+'        </li>';
	return html;
};
Ticket.prototype.toString=function(){
	return "product_"+this._branchId+"_"+this._jpId+"_"+this._quantity+"_"+this._time;
};
Traffic.prototype.toHtml=function(){
	return this._flightInfo;
};
Traffic.prototype.toString=function(){
	return "traffic_"+this._jpIds+"_"+this._time;
};
//超级自由行的行程操作时使用
$(document).ready(function(){
	$("#buyInfo_content").change(function(){
		$.ajax({url:"/buy/ajaxPriceInfo.do",
			type:"POST",
			async:false,
			data:$("#orderForm").serialize(),
			dataType:"json",
			success:function(data){
				if(data.priceInfo.success){
					if(data.priceInfo.orderDiscountPrice>0){
						$(".zuhe_yh").show();
						$("#freePreferential").html(data.priceInfo.orderDiscountPrice);
					}
					$("#freeTotalPrices").html(data.priceInfo.price);
				}else{
					alert(data.priceInfo.msg);
				}
			}
		});
	});
	//选中酒店或大交通一个产品
	$(".superFreeSubMain .xuanBnt").bind("click",function(){
		SperFreeProductSelect(this);
	});	

	//保存选中的产品
	$(".superFreeBtnSave").click(function(){
		var tt=$(this).attr("tt");
		var prodJourneyId=$(this).attr("prodJourneyId");
		var $div=$(".superFreeProdJourney[tt="+tt+"][prodJourneyId="+prodJourneyId+"]");
		var resutl = doProdJourneySelected($div);
		if(resutl){
			hideDetail($div);
		}
		changeSelectPrice();
	});
	
	updateSelectBox();	
	updateAllDateSelectBox();
	updateSuperFreeRequire();
	showAllProdJourneySelected();
	changeSelectPrice();
	
	$.each($(".superFreeProdJourney"),function(index,item){
		var tt=$(item).attr("tt");
		var prodJourneyId=$(item).attr("prodJourneyId");		
		$(".superFreeProdJourneyAddBtn[prodJourneyId="+prodJourneyId+"][tt='"+tt+"']").popup(
					{ 
						trigger: ".superFreeProdJourneyAddBtn[prodJourneyId="+prodJourneyId+"][tt='"+tt+"']", 
						lock: true, 
						fixed:true, 
						title:$('.superFreeSubTitle[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"]').html(), 
						popupObj: '.superFreeSubMain[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"]' ,
						closeClick:function(){
							//关闭事件 关闭是还原选择状态
							var $select_All_list=$(".superFreeSubMain[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'] .xuanBnt");
							$.each($select_All_list,function(index,item){
								if($(item).data("selected")=="1"){
									$(item).hasClass("xuanBnt2")?null:$(item).addClass("xuanBnt2");
								}else{
									$(item).hasClass("xuanBnt2")?$(item).removeClass("xuanBnt2"):null;
								}														
							});
						}
					});
	});
});
function chanageNum(ticketId,pjId,ojb){
	$("#"+ticketId).val($(ojb).val());
	var $div=$(".superFreeProdJourney[tt='ticket'][prodJourneyId='"+pjId+"']");
	doProdJourneySelected($div);
	changeSelectPrice();
}
//添加选中日历事件
function TimePriceOneAddClickEvent(){
	if($('.self_time_price_one').length>0){
		var $tp=$('.self_time_price_one');
		pid=$tp.attr('data-pid');
		pid=pid?pid:'';
		if($('.self_time_price_one').children().length>0){
			var arr_week=new Array("周日","周一","周二","周三","周四","周五","周六");
			var startDate= $(".quickBooker_select").val();
			if(startDate!=undefined && startDate!=""){
				date =new Date();
				date.setFullYear(startDate.split("-")[0],startDate.split("-")[1]-1,startDate.split("-")[2]);
				$("#zuheTimeBDate").html((date.getMonth()+1)+"月"+date.getDate()+"日（"+arr_week[date.getDay()]+"）");
				$('.search_pp_calendar_d_box').unbind("click");
				$('.search_pp_calendar_d_box').bind("click",function(event){
					if($(this).data('sellbale')){	
						startDate=$(this).find('.search_pp_calendar_day_date').text();
						date =new Date();
						date.setFullYear(startDate.split("-")[0],startDate.split("-")[1]-1,startDate.split("-")[2]);
						//如果选择的是自由套餐
						loadJourneyPack(startDate);
						superFreeReload(pid,startDate);
						$("#zuheTimeBDate").html((date.getMonth()+1)+"月"+date.getDate()+"日（"+arr_week[date.getDay()]+"）");
						$(".zuheTimeB").removeClass("zuheTimeBClick");
					}
				});
			}
		}
	}
}
/**
 * 显示每个行程段当中各类型已经选中的产品,第一次有效，
 * 并且把值保存在data当中供重新进入使用
 */
function showAllProdJourneySelected(){
	var $list=$(".superFreeProdJourney");
	$list.each(function(i){
		doProdJourneySelected($(this));
	});
}
/**
 * 显示选中的产品的价格
 * @memberOf {TypeName} 
 */
function changeSelectPrice(){
	var $list=$(".superFreeProdJourney");
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

//选择事件
function SperFreeProductSelect(me){
	var selectObj = null;
	var selected = !$(me).hasClass("xuanBnt2");
	var tt=$(me).attr("tt");
	var prodJourneyId=$(me).attr("prodJourneyId");

	if($(me).attr("require")=="true" && !selected){
		alert("此项为必选项！");
		$(me).removeClass("xuanBnt2");
		return;
	}
	//单选 酒店与交通
	if(tt=="hotel" || tt=="traffic"){
		$.each($(".superFreeSubMain .xuanBnt[tt='"+tt+"'][prodJourneyId="+prodJourneyId+"]"),function(index,item){
			if(me!=item){
				$(item).removeClass("xuanBnt2");
				$(item).text('选择');
			}
			if($(item).hasClass("xuanBnt2")==true ){
				selectObj = item;
			}
		});
		if(selected){
			selectObj = me;
		}
		changeJourneyProduct($(selectObj),!selected);
	}
}
//初始化默认与必选项
function updateSuperFreeRequire(){
	
	$.each($(".superFreeSubMain .xuanBnt[defaultProduct='true']"),function(index,item){
		if(!$(item).hasClass("xuanBnt2")){
			$(item).trigger("click");
		}
	});
	$.each($(".superFreeSubMain .xuanBnt[require='true']"),function(index,item){
		if(!$(item).hasClass("xuanBnt2")){
			$(item).trigger("click");
		}
	});
}
function superFreeReload(pid,date){
	if(date=='undefined'||date==''){
		return false;
	};
	if(!needLoadJourney){
		$('#superFreeDetail').addClass('loading').html('');
	}
	needLoadJourney = false;
	//移除dialog start
	$('.superFreeSubMain').detach();//
	$('.superFreeSubTitle').detach();//
	//移除dialog end
	$('.quickBooker_select').val(date);
	var params='productId='+pid+'&choseDate='+date;
	$.each($('#quickBooker1_tab2 input[ordNum]'),function(index,item){
		var $it=$(item);
		params+='&ordNum.param'+$it.attr('branchid')+'='+$it.val();
	});
	$.ajax({
		url:"http://www.lvmama.com/product/journey.do",dataType:"html",async:true,data:params,success:function(data){
			$('#superFreeDetail').removeClass('loading').html(data);
		}
		,error:function(x,e,c){
			$('#superFreeDetail').removeClass('loading');
		}
		,complete:function(){}
	});
};

function updateSelectBox(){
	var $form=$("#superFreeDetail");
	var adult=parseInt($("#buyInfo_adult").val());
	var child=parseInt($("#buyInfo_child").val());
	$select=$form.find("select[name^='jp']");
	
	$select.each(function(i){
		var $this=$(this);
		fillQuantitySelect($this,adult,child);
	});
}

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
		/*if(require){
			begin=1;
		}else{
			begin=0;
		}*/
		begin=1;
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
 * 变更选中的单选框，需要更新对应的产品的价格.
 * @param {Object} $input
 */
function changeJourneyProduct($object,restart){
	var journeyProductId=$object.attr("value");
	var name=$object.attr("name");
	var $span=$("samp.differenceOfPrices[journeyProductId="+journeyProductId+"]");
	var price=$span.attr("price");
	$span.html('&nbsp;');//当前的酒店显示空白
	var $list=$("samp.differenceOfPrices[journey="+name+"]");
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

		if(restart){
			$this.html("--");
		}
	});
}

function doProdJourneySelected($this){
	var result = true;
	var tt=$this.attr("tt");
	var prodJourneyId=$this.attr("prodJourneyId");		
	var $div=$("div.superFreeList[category='"+tt+"_"+prodJourneyId+"']");
	if(tt=='hotel'||tt=='traffic'){//如果是酒店只选中一个:checked
		var $selected=$div.find(".xuanBnt.xuanBnt2");
		var product=null;	
		if(typeof($selected)!=undefined&&$selected.length>0){
			var $tr=$("#tr_jp_"+$selected.attr("value"));
			if(tt=='hotel'){
				var $jpSelect=$("select[name=jp_"+$selected.attr("value")+"]");
				var branchId=$jpSelect.attr("branchid");
				var btime=$jpSelect.attr("beginTime");
				var etime=$jpSelect.attr("endTime");
				var smallImage=$jpSelect.attr("smallImage");
				var branchName=$jpSelect.attr("branchName");
				var zhBreakfast=$jpSelect.attr("zhBreakfast");
				var bedType=$jpSelect.attr("bedType");
				var description=$jpSelect.attr("description");
				var productId=$jpSelect.attr("productId");
				var hotelStar=$jpSelect.attr("hotelStar");
				var quantity=$jpSelect.find("option:selected").val();
				description =$("textarea[id=jp_description_"+$selected.attr("value")+"]").val();
				//Hotel(jpId,branchId,name,branch,quantity,btime,etime)
				product=new Hotel($selected.attr("value"),
								branchId,
								$tr.attr("pname"),
								$tr.attr("bname"),
								quantity,
								btime,
								etime,smallImage,branchName,zhBreakfast,bedType,description,prodJourneyId,productId,hotelStar);
			}else{
				//var branchIds=$selected.attr("branchIds");
				var jpIds=$selected.attr("jpIds");
				var goTime=$selected.attr("goTime");
				var direction=$tr.attr("direction");
				//var $td=$tr.find("td.trafficInfo");
				var html="";
				html=html+' <table class="dayTable">';
				html=html+'        <tr>';
				html=html+'            <th width="80">日期</th>';
				html=html+'            <th width="150">时间/机场</th>';
				html=html+'            <th width="120">航空公司</th>';
				html=html+'            <th width="80">舱位</th>';
				html=html+'            <th width="80">飞行总时长</th>';
				html=html+'        </tr>';
				html=html+'	<tr>';
				html=html+'		<td >'+$tr.find("td.trafficBeginTime").html()+'</td>';
				html=html+'		<td >'+$tr.find("td.trafficInfo").html()+'</td>';
				html=html+'		<td >'+$tr.find("td.trafficAirlineName").html()+'</td>';
				html=html+'		<td>'+$tr.find("td.trafficZhBerth").html()+'</td>';
				html=html+'		<td>'+$tr.find("td.trafficZhFlightTime").html()+'</td>';
				html=html+'	</tr>    ';
				if(direction=='ROUND'){//是来回程
					$tr2=$("#tr_jp_"+$selected.attr("value")+"_2");			
					html=html+'	<tr>';
					html=html+'		<td >'+$tr2.find("td.trafficBeginTime").html()+'</td>';
					html=html+'		<td >'+$tr2.find("td.trafficInfo").html()+'</td>';
					html=html+'		<td >'+$tr2.find("td.trafficAirlineName").html()+'</td>';
					html=html+'		<td>'+$tr2.find("td.trafficZhBerth").html()+'</td>';
					html=html+'		<td>'+$tr2.find("td.trafficZhFlightTime").html()+'</td>';
					html=html+'	</tr>    ';					
				}
				html=html+' </table> ';
				product = new Traffic($selected.attr("value"),
						$selected.attr("branchid"),
						jpIds,
						$tr.attr("pname"),
						$tr.attr("bname"),
						goTime,
						html);
			}
		}
		/*var oldProduct=$this.data("product");
		if(oldProduct==undefined||(product!=null&&!product.isSame(oldProduct._jpId))){//表示已经修改
			var placeBranchId=$selected.attr("placeBranchId");
			var map_type=tt.toUpperCase();
			splicePlaceParamForMap(placeBranchId, "true", map_type);
			if(oldProduct!=undefined){
				var $oldSelected=$("input.useProduct[value="+oldProduct._jpId+"]");
				placeBranchId=$oldSelected.attr("placeBranchId");
				splicePlaceParamForMap(placeBranchId, "false", map_type);
			}
		}*/
		$this.data("product",product);
		if(product!=null){
			$this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html($this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html().replace("添加","更改"));
			
			$this.find(".superFreeProdJourney_content[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html(product.toHtml());
			$this.removeClass("height45");
			if(tt=='hotel'){
				$(".jiudianText[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'][productId="+product._productId+"]").popup(
						{ 
							trigger: ".jiudianText[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'][productId="+product._productId+"]", 
							lock: true, 
							fixed:true, 
							width:750,
							title:$('.superFreeSubTitleByProduct[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"][productId='+product._productId+']').html(), 
							popupObj:'.superFreeSubMainByProduct[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"][productId='+product._productId+']'
						});
			}
			
		}else{
			$this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html($this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html().replace("更改","添加"));
			$this.find(".superFreeProdJourney_content[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html("");
			$this.addClass("height45");
		}
		$.each($div.find(".xuanBnt"),function(index,item){
			if($(item).hasClass("xuanBnt2")){
				$(item).data("selected","1");
			}else{
				$(item).data("selected","0");
			}			
		});
	}else{
		var $select_list=$(".superFreeSubMain[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'] .xuanBnt2");
		$.each($select_list,function(index,item){
			var $select = $(item).parent().parent().find("select[name^=jp_]");
			if($select.val()==0){
				alert("已选择项的数量必须大于0！");
				result=false;
				return;
			}								
		});
		var array=new Array();
		var $select_All_list=$(".superFreeSubMain[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'] .xuanBnt");
		$.each($select_All_list,function(index,item){
			if($(item).hasClass("xuanBnt2")){
				var $sock = $(item).parent().parent().find("select[name^=jp_]");
				var val=parseInt($sock.find("option:selected").val());
				var jpId=$sock.attr("journeyProductId");
				if(val!=NaN&&val>0){
					//Product(jpId,branchId,name,branch,quantity,time)
					var $product_date=$("select[name^=product_date_"+jpId+"]");
					//var jpId=$product_date.attr("journeyProductId");
					var branchId=$product_date.attr("prodBranchId");
					var smallImage= $product_date.attr("smallImage");
					var productId=$product_date.attr("productId");
					var time=$product_date.find("option:selected").val();
					var $tr=$("#tr_jp_"+jpId);
					var product;
					if(tt=='ticket'){
						product=new Ticket( 
								jpId,
								branchId,
								$tr.attr("pname"),
								$tr.attr("bname"),
								val,
								time,smallImage,prodJourneyId,tt,productId);
					}else{
						product=new Product( 
								jpId,
								branchId,
								$tr.attr("pname"),
								$tr.attr("bname"),
								val,
								time,smallImage,prodJourneyId,tt,productId);
					}
					
					array.push(product);
				}
				$(item).data("selected","1");
			}else{
				$(item).data("selected","0");
			}
									
		});
			
		$this.data("product",array);
		if(array!=null && array.length>0){
			$this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html($this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html().replace("添加","更改"));

			var html="";
			html=html+'<div class="dayBox2">';
			html=html+'    <ul class="dayBox2List">';				
			for(var i=0;i<array.length;i++){
				html=html+array[i].toHtml();
			}			
			html=html+'    </ul>';
			html=html+'</div>';	
			$this.find(".superFreeProdJourney_content[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html(html);	
			$this.removeClass("height45");
			for(var i=0;i<array.length;i++){
				var product = array[i];
				$(".jingdianText[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'][productId="+product._productId+"]").popup(
						{ 
							trigger: ".jingdianText[prodJourneyId="+prodJourneyId+"][tt='"+tt+"'][productId="+product._productId+"]", 
							lock: true, 
							fixed:true, 
							width:750,
							title:$('.superFreeSubTitleByProduct[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"][productId='+product._productId+']').html(), 
							popupObj:'.superFreeSubMainByProduct[prodJourneyId='+prodJourneyId+'][tt="'+tt+'"][productId='+product._productId+']'
						});
			}
			
		}else{
			$this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html($this.find(".superFreeProdJourneyAddBtn[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html().replace("更改","添加"));
			$this.find(".superFreeProdJourney_content[tt="+tt+"][prodJourneyId="+prodJourneyId+"]").html("");
			$this.addClass("height45");
		}
	}
	return result;
}
/**
 * 检查行程是选对应的策略生效正确后提交
 */
function checkJourneySelecteAndSubmit(){
	var $list=$("#superFreeDetail input[name^=policy]");	
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
				var product=$(".superFreeProdJourney[tt="+type+"][prodJourneyId="+prodJourneyId+"]").data("product");	
				var now_day=$(".superFreeProdJourney[tt="+type+"][prodJourneyId="+prodJourneyId+"]").attr("now_day");		
				var now_day_str = "第 "+now_day+" 天";
				if(product===undefined||product==null){
					if(type=='traffic'){//交通的判断.	
						throw now_day_str+" 您还未选择大交通";
					}else if(type=='hotel'){//酒店的判断.
						throw now_day_str+" 您还未选择入住酒店";
					}
				}else{//线路门票的判断
					var all_len=$("select[id^=buyJourney_"+type+"_"+prodJourneyId+"]").length;
					if(all_len>0 && (product==undefined||product.length==0)){//判断选中的是否为0及总数大于0.
						if(type == 'ticket'){
							throw now_day_str+" 您还有未选择的门票";								
						}else if(type=='route'){
							throw now_day_str+" 您还有未选择的当地游";
						}
					}
				}
			}
		});
		if($("#rapidLoginDialog").length > 0){
			showLogin(function(){document.getElementById("orderForm").submit();});
		}else{
			document.getElementById("orderForm").submit();
		}
	}catch(ex){
		alert(ex);			
	}
}
function hideDetail($this){	
	$(".js-popup-close").trigger("click");
}