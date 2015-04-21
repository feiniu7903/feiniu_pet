function liveBtn(startDate,passType,passSubType,pid,bid){
	
	var body=$('body'),$lyd=$('.l_yuding'),ht=body.outerHeight(),wh=body.outerWidth();
	$lyd.css({
		position:'absolute',top:$(document).scrollTop()+200,left:$(document).outerWidth()/2
	}).show();
	if(startDate){
		!$.datepicker && $("head").append('<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ui_plugin/calendar.css"/>');
		UI.js(!$.datepicker,"http://pic.lvmama.com/js/ui/jquery-ui-datepicker.custom.min.js",function(){
				if(!$.datepicker){
					$.datepicker.regional['zh-CN'] = {
						closeText: '关闭',
						prevText: '&#x3c;上月',
						nextText: '下月&#x3e;',
						currentText: '今天',
						monthNames: ['一月','二月','三月','四月','五月','六月',
						'七月','八月','九月','十月','十一月','十二月'],
						monthNamesShort: ['一','二','三','四','五','六',
						'七','八','九','十','十一','十二'],
						dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
						dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
						dayNamesMin: ['日','一','二','三','四','五','六'],
						weekHeader: '周',
						dateFormat: 'yy-mm-dd',
						firstDay: 1,
						isRTL: false,
						showMonthAfterYear: true,
						yearSuffix: '年'};
					$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
				}
				var dateArray=startDate.split('-'),year=dateArray[0],month=dateArray[1],day=dateArray[2];
				$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
				$("#live_date").datepicker({
					showOtherMonths:true,selectOtherMonths:true,firstDay:0,dateFormat:'yy-mm-dd',minDate:new Date(year-0,month-1,day-0+1),maxDate:new Date(year-0,month-1,day-0+1+27),onSelect:function(dateText,inst){
						$('.day_num').text(intervalDay(startDate,dateText)-0+1);
						$.ajax({
							url:"http://www.lvmama.com/product/price.do?ordNum.param"+bid+"="+$('.num').text(),dataType:"json",data:{
								branchId:bid,productId:pid,choseDate:startDate,endDate:dateText
							}
							,success:function(data){
								var $liveDate=$('#live_date');
								if(data.jsonMap.flag=='Y'){
									$liveDate.attr({
										style:''
									})
								}
								else{
									errorLiveDateTip(data.jsonMap.error)
								}
							}
							,error:function(x,e,c){}
						})
					}
				})
		});
	}
	$lyd.find('.l_close').on('click',function(){
		$('#zhebi').remove();
		$lyd.remove();
		$('#nextForm').empty()
	});
	$lyd.find('.remove_num_btn').on('click',function(){
		var $num=$(this).parents('li').find('.num'),val=$num.text(),min=$num.data('miniMum'),max=$num.data('maxiMum');
		val>min?$num.text(val-1):''
	});
	$lyd.find('.add_num_btn').on('click',function(){
		var $num=$(this).parents('li').find('.num'),val=$num.text(),min=$num.data('miniMum'),max=$num.data('maxiMum');
		val<max?$num.text(1+ +val):''
	});
	
	$lyd.find('.l_next').on('click',function(){
		var $list=$('.li-params');
		var len=$list.length;
		var formHtml='';
		var $liveDate=$('#live_date');
		if($liveDate.length>0){
			var ldate=$liveDate.val();
			if(ldate==''||ldate=='null'||ldate==undefined){
				errorLiveDateTip();
				return false
			};
			$liveDate.attr({
				style:''
			})
		};
		var $dayNum=$('.day_num'),$nowDate=$('.nowDate');
		$dayNum.length>0?formHtml+='<input type="text" name="buyInfo.days" value="'+$dayNum.text()+'" />':'';
		$nowDate.length>0?formHtml+='<input type="text" name="buyInfo.visitTime" value="'+$nowDate.text()+'" />':'';
		if(len>0){
			var params='?productId='+pid+'&choseDate='+startDate;
			if($list.filter("[type=true]").length==0){
				$list.eq(0).attr("isOk","true");
				var fbid=$list.eq(0).attr('bId');
				formHtml+='<input type="text" name="buyInfo.prodBranchId" value="'+fbid+'" />';
				params+='&branchId='+fbid;
			}
			for(var i=0,l=len;i<len;i++){
				var $listEq=$list.eq(i);
				var inLen=$listEq.find('.num').length;
				var fbid=$list.eq(i).attr('bId');
				var fnum=$list.eq(i).find('.num').text();
				if(inLen>0){
					var type=$listEq.attr('type');
					if($listEq.attr("isOk")!="true"){
						if(len==1||type==true||type=='true'){
							formHtml+='<input type="text" name="buyInfo.prodBranchId" value="'+fbid+'" />';
							params+='&branchId='+fbid
						}
						else if(len>1&&len==1){
							formHtml+='<input type="text" name="buyInfo.prodBranchId" value="'+fbid+'" />';
							params+='&branchId='+fbid
						}
					}
					params+='&ordNum.param'+fbid+'='+fnum;
					formHtml+='<input type="text" name="buyInfo.buyNum.product_'+fbid+'" value="'+fnum+'" />'
				}
			};
			$.ajax({
				url:"http://www.lvmama.com/product/price.do"+params,dataType:"json",async:true,success:function(data){
					if(data.jsonMap.flag=='N'){
						errorLiveDateTip(data.jsonMap.error);
						return false
					};
					$('#nextForm').empty();
					$('#nextForm').append(formHtml);
					$('#nextForm')[0].submit();
					return true
				}
			})
		}
		else{
			return false;
		}
	})
};
function forwardWin(productId,startDate,forwardWin,locationObj){
	$('#'+forwardWin).remove();
	var html='<form method="post" target= "_blank" action="http://www.lvmama.com/product/'+productId+'" id="'+forwardWin+'" style="display:none;">'+'<input type="hidden" name="checkDate" value="'+startDate+'" />'+'</form>';
	$(locationObj).append(html);
	$('#'+forwardWin)[0].submit()
};
function numAlert(productId,branchId,startDate,passType,passSubType){
	$('#zhebi').remove();
	$('.l_yuding').remove();
	$('#nextForm').empty();
	var formSubmitUrl="/buy/fill.do"; 
	if(passType=="ticket"){ 
		formSubmitUrl="/orderFill/ticket-"+productId; 
	} 
	var html='<div id="zhebi" style="display:none;"></div>'+'<div class="l_yuding" style="display:none;z-index:9990;">'+'<div class="pop_yuding_tit">开始预订</div>'+'<i class="l_close"></i>'+'<div class="pop_yuding_inner">'+'<form method="post" action="http://www.lvmama.com'+formSubmitUrl+'" id="nextForm" style="display:none;"></form>'+'<!-- --><p style="display:none;color:red;font-weight: 700;line-height: 45px;text-align: center;border: 1px solid #E2EAAD;background: #FDFFF2;width:272px;padding: 5px 40px;" id="live-date-error" >离店日期不能为空!</p>'+'<div class="l_yuding_mid">'+'<ul id="list_num" class="l_pro_window"></ul>'+'<p class="l_introduce" style="display:none;">'+' 注：部分景区对儿童身高有特殊要求，请以公告为准。</p>'+'<p class="zoom"><a href="javascript:void(0);" class="l_next">下一步</a></p>'+'</div>'+'</div>'+'</div>'; 
	var $body=$('body');
	var ht=$body.outerHeight();
	var wh=$body.outerWidth();
	$body.append(html);
	$('#zhebi').css({
		width:wh,height:ht,background:'#000',opacity:'0.5','z-index':'1999',position:'absolute',top:'0',left:'0'
	}).show();
	var $prodNum=$('.prod-num');
	var inhtml='';
	$.ajax({
		url:"http://www.lvmama.com/product/price.do",dataType:"json",async:true,data:{
			productId:productId,branchId:branchId,choseDate:startDate
		}
		,success:function(data){
			if(passType=='hotel'){
				$('.l_introduce').hide();
				inhtml+='<li><label class="date-price-fc-label">您选择的入住日期：</label><b class="date-price-fc-margin"><span class="nowDate">'+startDate+'</span></b></li>';
				if(passSubType==='singleRoom'){
					inhtml+='<li><label class="date-price-fc-label">请选择离店日期：</label><input id="live_date" readOnly="true" name="text" type="text" class="l_date date-price-fc-margin" data-pass="false" />住：<strong class="day_num">0</strong> 晚</li>'
				};
				if(data.jsonMap.flag=='Y'){
					$.each(data.jsonMap.price,function(index,item){
						inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" ><!--请选择房间数：-->'+'<dl><dd class="dd_left date-price-fc-label" title="'+item.branchName+'">'+item.branchName+'：</dd>'+'<dd class="dd_center"><span class="l_jian_btn remove_num_btn"></span><span data-mini-mum="'+item.minimum+'" data-maxi-mum="'+item.maximum+'" class="l_number num">'+item.minimum+'</span><span class="l_jia_btn add_num_btn"></span>'+(item.priceUtil!=null?item.priceUtil:'')+'</dd>'+'<dd class="dd_right"><strong class="date-price-fc-price">&yen;'+item.price+'</strong><i class="date-price-fc-font"> 间</i></dd></dl>'+'</li>'
					});
					$('#list_num').append(inhtml)
				}
				else{
					if(data.jsonMap.error){
						$('#list_num').append('<li style="padding-top: 15px;">'+data.jsonMap.error+'</li>')
					}
					else{
						$('#list_num').append('<li style="padding-top: 15px;">产品已售完!</li>')
					}
				}
			}
			else if(passType=='ticket'){
				$('.l_introduce').show();
				inhtml+='<li><label class="date-price-fc-label">您选择的游玩日期：</label><b class="date-price-fc-margin"><span class="nowDate">'+startDate+'</span></b></li>';
				if(data.jsonMap.flag=='Y'){
					$.each(data.jsonMap.price,function(index,item){
						if(index==0){
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" ><!-- 选择人数： -->'
						}
						else{
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" >'
						};
						inhtml+='<dl><dd class="dd_left date-price-fc-label" title="'+item.branchName+'">'+item.branchName+'：</dd>'+'<dd class="dd_center"><span class="l_jian_btn remove_num_btn"></span><span data-mini-mum="'+item.minimum+'" data-maxi-mum="'+item.maximum+'" class="l_number num">'+item.minimum+'</span><span class="l_jia_btn add_num_btn"></span>'+(item.priceUtil!=null?item.priceUtil:'')+'</dd>'+'<dd class="dd_right"><strong class="date-price-fc-price">&yen;'+item.price+'</strong><i class="date-price-fc-font"> / 份</i></dd></dl>'+'</li>'
					});
					$('#list_num').append(inhtml)
				}
				else{
					if(data.jsonMap.error){
						$('#list_num').html('<li style="padding-top: 15px;">'+data.jsonMap.error+'</li>')
					}
					else{
						$('#list_num').html('<li style="padding-top: 15px;">产品已售完!</li>')
					}
				}
			}
			else if(passType==''||passType==undefined){
				$('.l_introduce').hide();
				inhtml+='<li><label class="date-price-fc-label">您选择的入住日期：</label><b class="date-price-fc-margin"><span class="nowDate">'+startDate+'</span></b></li>';
				if(data.jsonMap.flag=='Y'){
					$.each(data.jsonMap.price,function(index,item){
						if(index==0){
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" ><!-- 选择人数： -->'
						}
						else{
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" >'
						};
						inhtml+='<dl><dd class="dd_left date-price-fc-label" title="'+item.branchName+'">'+item.branchName+'：</dd>'+'<dd class="dd_center"><span class="l_jian_btn remove_num_btn"></span><span data-mini-mum="'+item.minimum+'" data-maxi-mum="'+item.maximum+'" class="l_number num">'+item.minimum+'</span><span class="l_jia_btn add_num_btn"></span>'+(item.priceUtil!=null?item.priceUtil:'')+'</dd>'+'<dd class="dd_right"><strong class="date-price-fc-price">&yen;'+item.price+'</strong><i class="date-price-fc-font"> / 份</i></dd></dl>'+'</li>'
					});
					$('#list_num').append(inhtml)
				}
				else{
					if(data.jsonMap.error){
						$('#list_num').html('<li style="padding-top: 15px;">'+data.jsonMap.error+'</li>')
					}
					else{
						$('#list_num').html('<li style="padding-top: 15px;">产品已售完!</li>')
					}
				}
			}
			else if(passType=='superFree'){
				$('.l_introduce').hide();
				inhtml+='<li>您选择的入住日期：<b><span class="nowDate">'+startDate+'</span></b></li>';
				if(data.jsonMap.flag=='Y'){
					$.each(data.jsonMap.price,function(index,item){
						if(index==0){
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" >选择人数：'+'<dl><dd class="dd_left" title="'+item.branchName+'">'+item.branchName+'</dd><dd class="dd_center">（<strong>&yen;'+item.price+'</strong>）</dd>'+'<dd class="dd_right"><span class="l_jian_btn remove_num_btn"></span><span data-mini-mum="'+item.minimum+'" data-maxi-mum="'+item.maximum+'" class="l_number num">'+item.minimum+'</span><span class="l_jia_btn add_num_btn"></span>'+(item.priceUtil!=null?item.priceUtil:'')+'</dd></dl>'+'</li>'
						}
						else{
							inhtml+='<li class="li-params" bId="'+item.branchId+'" type="'+item.branchDefault+'" >'+'<dl><dd class="dd_left" title="'+item.branchName+'">'+item.branchName+'</dd><dd class="dd_center">（<strong>&yen;'+item.price+'</strong>）</dd>'+'<dd class="dd_right"><span class="l_jian_btn remove_num_btn"></span><span data-mini-mum="'+item.minimum+'" data-maxi-mum="'+item.maximum+'" class="l_number num">'+item.minimum+'</span><span class="l_jia_btn add_num_btn"></span>'+(item.priceUtil!=null?item.priceUtil:'')+'</dd></dl>'+'</li>'
						}
					});
					$('#list_num').append(inhtml)
				}
				else{
					if(data.jsonMap.error){
						$('#list_num').html('<li style="padding-top: 15px;">'+data.jsonMap.error+'</li>')
					}
					else{
						$('#list_num').html('<li style="padding-top: 15px;">产品已售完!</li>')
					}
				}
			}
		}
		,error:function(x,e,c){
			$('#list_num').html('<li>产品不存在!</li>')
		}
		,complete:function(){
			liveBtn(startDate,passType,passSubType,productId,branchId)
		}
	})
};
function toggleArrow(e){
	var $tar=$(e.target);
	if(!$tar.hasClass('tv_hide_calendar')){
		$tar.addClass('tv_hide_calendar')
	}
	else{
		$tar.removeClass('tv_hide_calendar')
	}
};


function loadPrice(ojb,month){
	$('.calendar_free').find('.search_pp_calendar_box').hide();
	var cBox = $(ojb).parent().parent().parent();
	if("false"==cBox.next().attr("needReload")){
		cBox.next().show();
	}else{
		$.ajax({
		url:"http://www.lvmama.com/product/selfTimePriceTable.do",dataType:"html",data:{branchId:$('.self_time_price_one').attr('data-bid'),month:month,r:Math.random()
		}
		,success:function(data){
			$('.calendar_free').find('.search_pp_calendar_box[needreload!="false"]').remove();
			$('.calendar_free').find('.search_pp_calendar_box:last-child').after(data);
		}
		}).complete(function(){
			var $tp=$('.self_time_price_one');
			cBox.next().show();
			TimePriceOneAddClickEvent();
			pushTipInfo($tp);
		});
	}
}
$(document).ready(function(){
	if($('.self_time_price_one').length>0){
		var $tp=$('.self_time_price_one');
		pid=$tp.attr('data-pid'),bid=$tp.attr('data-bid'),$calFree=$('.calendar_free'),isSuperFree=$calFree.data('superFree'),passType=$calFree.data('productType'),passSubType=$calFree.data('subProductType');
		pid=pid?pid:'';
		bid=bid?bid:'';
		$.ajax({
			url:"http://www.lvmama.com/product/selfTimePriceTable.do",dataType:"html",async:true,data:{
				productId:pid,branchId:bid,r:Math.random()
			}
			,success:function(data){
				$tp.append(data)
			}
			,error:function(x,e,c){}
		}).complete(function(){
			var $tp=$('.self_time_price_one');
			pid=$tp.attr('data-pid'),bid=$tp.attr('data-bid'),$calFree=$('.calendar_free'),isSuperFree=$calFree.data('superFree'),passType=$calFree.data('productType'),passSubType=$calFree.data('subProductType');
			$('.quickBooker_select').append($('#selectTimePrice').html());
			$('#checkDate').val()==''?'':$('.quickBooker_select').val($('#checkDate').val());
			$calFree.on('click',function(e){
				var cn=e.target.className,$subTp=$(this).find('.search_pp_calendar_box');
				if(cn==='search_pp_cal_nevm_icon'){
					$subTp.hide();
					$(e.target).closest('.search_pp_calendar_box').prev().show()
				}
			});
			frist_loadJourney(); 
			frist_loadPack();
			TimePriceOneAddClickEvent();
			pushTipInfo($tp);
		})
	}
});
function pushTipInfo($tp){
	$tp.find(".search_pp_calendar_d_box").mouseover(function(){
		var priceInfo=$(this).find("input[name='tipData']").val();
		if(!priceInfo){
			return;   
		}
		var arr = $.parseJSON(priceInfo);
		var endArr=[],Html="";
		$.each(arr,function(i,n){
		  if(!n.param){
			var rlt=$("#tipTemplete"+n.index).val();
		  }else{
			  var valueArr = n.param.split("|");
			  var rlt=$("#tipTemplete"+n.index).val().replace(/\{(\d+)\}/g,function(a,b){
				  return valueArr[b-1];
			  });
		  }
			  Html+=rlt+"<br/>"; 
		});
		$(this).attr("tip-content",Html);
	}).ui('lvtip',{
	  templete : 2,
	  place: 'bottom-left',
	  offsetX: 10
    });		
}