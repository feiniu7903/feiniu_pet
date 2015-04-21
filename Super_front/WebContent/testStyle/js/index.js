// JavaScript Document



$(function(){ 

/*人气套餐，自由组合切换*/
	$('.tcNav li').click(function(){ 
		var _num = $(this).index();
		$(this).addClass('tcNavThis').siblings().removeClass('tcNavThis');
		$('.tcNavList').eq(_num).show().siblings('.tcNavList').hide();
	})
	

/*人气套餐展开*/
	$('.tc_btn_js').toggle(function(){
		var $TaocanTime = $(this).parents('tr').next('.taocanTime');
		if($TaocanTime.css('display')=='none'){
		$TaocanTime.show().find('.taocanTimeBox').slideDown(300);
		$TaocanTime.siblings('.taocanTime').find('.taocanTimeBox').slideUp(300,function(){$(this).parents('.taocanTime').hide()});
		}else{
			$TaocanTime.show().find('.taocanTimeBox').slideUp(300,function(){$(this).parents('.taocanTime').hide()});
			}
	},function(){
		var $TaocanTime = $(this).parents('tr').next('.taocanTime'); 
		if($TaocanTime.css('display')=='none'){
			$TaocanTime.show().find('.taocanTimeBox').slideDown(300);
			$TaocanTime.siblings('.taocanTime').find('.taocanTimeBox').slideUp(300,function(){$(this).parents('.taocanTime').hide()});
			}else{
				$TaocanTime.find('.taocanTimeBox').slideUp(300,function(){$(this).parents('.taocanTime').hide()});
				}
		
	});
	$('.tc_btn2_js').click(function(){ 
		$(this).parents('tr').prev('tr').find('.tc_btn_js').click();
	});
	
	
/*出游人数加减*/
	var $zuheNumber = $('#zuheNumber');
	$('.numberJian').click(function(){ 
		var _num = parseInt($zuheNumber.val());
		if(_num>1){
			$zuheNumber.attr('value',_num-=1);
		}
	}); 
	$('.numberJia').click(function(){ 
		var _num = parseInt($zuheNumber.val());
		$zuheNumber.attr('value',_num+=1);
	}); 
	$zuheNumber.focusout(function(){ 
		var _num = parseInt($zuheNumber.val());
		if(_num<1){
			$zuheNumber.attr('value','1');
			}
	}); 
	
/*自由组合选择出行日期*/
	$('.zuheTimeB').hover(function(){ 
		$(this).addClass('zuheTimeBClick');
	},function(){ 
		$(this).removeClass('zuheTimeBClick');
	});
	
/*自由组合酒店详情下拉展开*/
	$('.dayTableName').live('click',function(){ 
		$(this).parents('tr').next('.dayTableText').show().find('p').slideDown(300);
	});
	$('.TableTextHide').live('click',function(){ 
		$(this).siblings('p').slideUp(300,function(){$(this).parents('.dayTableText').hide();});
	});
	
	$('.xuanBnt').live('click',function(){ 
		if($(this).hasClass('xuanBnt2')){
			$(this).removeClass('xuanBnt2');
			$(this).text('选择');
		}else{
			$(this).addClass('xuanBnt2');
			$(this).text('已选')
			}
	});
	$('.tcNav').find('li').live('click',function(){
		var text = $.trim($(this).html());
		if("人气套餐"==text){
			$('#zuheyuding_pack').hide();
			$('#zuheBox').hide();
			$('#tapcamBox').show();
			$('.recommend_travel').hide();
			$(".h3_tit").each(function(){
				if($.trim($(this).children().text())=="推荐行程"){
					$(this).hide();
				}
			});
			$(".row_3").hide();
			
		}
		if("自由组合"==text){
			$('#zuheyuding_pack').show();
			$('#tapcamBox').hide();
			$('#zuheBox').show();
			$('.recommend_travel').show();
			$(".h3_tit").each(function(){
				if($.trim($(this).children().text())=="推荐行程"){
					$(this).show();
				}
			});
			$(".row_3").show();
		}
	});
	
	$(".zuheNumber").find('.numberJian').live('click',function(){
		var text = $.trim($(".tcNav").find(".tcNavThis").text());
		loadJourneyPack("");
		superFreeReload($(".self_time_price_one").attr('data-pid'),$(".quickBooker_select").val());
	});
	$(".zuheNumber").find('.numberJia').live('click',function(){
		var text = $.trim($(".tcNav").find(".tcNavThis").text());
		loadJourneyPack("");
		superFreeReload($(".self_time_price_one").attr('data-pid'),$(".quickBooker_select").val());
	});	
	queryNearDayOfCanCell();
});
var needLoadPack = true;
var needLoadJourney = true;
function queryNearDayOfCanCell(){
	var bid = $(".self_time_price_one").attr('data-bid');
	var pid = $(".self_time_price_one").attr('data-pid');
	$.ajax({
		url:"http://www.lvmama.com/product/queryNearDayOfCanCell.do",dataType:"json",async:true,data:{productId:pid,branchId:bid},
		success:function(data){
			if(data!=null && data.jsonMap.success){
				needShowRecommend(data.jsonMap.day);
			}
		}
	});
}
function needShowRecommend(today){
	if(today!=null){
		$(".quickBooker_select").append("<option value='"+today+"'>"+today+" </option>");
		var arr_week=new Array("周日","周一","周二","周三","周四","周五","周六");
		var day = new Date(today);
		$("#zuheTimeBDate").html((day.getMonth()+1)+"月"+day.getDate()+"日（"+arr_week[day.getDay()]+"）");
		
		if($.trim($(".tcNavThis").html())=="人气套餐"){
			loadJourneyPack(today);
			$('.recommend_travel').hide();
			$(".row_3").hide();
			$(".h3_tit").each(function(){
				if($.trim($(this).children().text())=="推荐行程"){
					$(this).hide();
				}
			});
		}
		superFreeReload($(".self_time_price_one").attr('data-pid'),today);
	}
}
//加减天数
function addDay(day,n) {
    var newDay = day.getTime() + n * 24 * 60 * 60 * 1000;
    return new Date(newDay);
}
function getDataStr(day) {
	var month = day.getMonth()+1;
	month = month>10?month:"0"+month;
	var date = day.getDate();
	date = date>10?date:"0"+date;
	var str = ""+day.getFullYear()+"-"+ month +"-"+date;
    return str;
}

function frist_loadPack(){
	if(needLoadPack){
		var startDate = $(".quickBooker_select").val();
		var todayStr = getDataStr(addDay(new Date(),2));
		if(startDate!=NaN&&startDate!=null){
			if(todayStr!=$.trim(startDate)){
				loadJourneyPack(startDate);
			}
			needLoadPack=false;
		}
	}
}
function frist_loadJourney(){
	if(needLoadJourney){
		var startDate = $(".quickBooker_select").val();
		var todayStr = getDataStr(addDay(new Date(),2));
		if(startDate!=NaN&&startDate!=null){
			if(todayStr!=$.trim(startDate)){
				superFreeReload($(".self_time_price_one").attr('data-pid'),startDate);
			}
			needLoadJourney=false;
		}
	}
}
function loadJourneyPack(startDate){
	if(!needLoadPack){
		$('#tapcamBox').addClass('loading').html('');
	}
	needLoadPack=false;
	var $tp=$(".self_time_price_one");
	startDate = $(".quickBooker_select").val();	
	var child = $("input[branchTypes*='CHILD']").val();
	child = child==undefined?0:child;
	var adult = $("input[branchTypes*='ADULT']").val();
	adult = adult==undefined?0:adult;
	var params='buyInfo.productId='+ $tp.attr('data-pid')+'&buyInfo.adult='+adult+'&buyInfo.child='+child+'&buyInfo.visitTime='+startDate+'&buyInfo.prodBranchId='+$tp.attr('data-bid');
	$.ajax({
		url:"http://www.lvmama.com/product/showJourneyPack.do",dataType:"html",async:true,data:params,success:function(data){
			$(".taocanBox").removeClass('loading').html($.trim(data));
		}
		,error:function(x,e,c){
			$('#tapcamBox').removeClass('loading');
		}
		,complete:function(){
			$(".jiudianText_AA").each(function(){
				var jiudianText_AA = $(this);
				$(".jiudianText_AA[prodJourneyId="+jiudianText_AA.attr("prodJourneyId")+"][tt='"+jiudianText_AA.attr("tt")+"'][productId="+jiudianText_AA.attr("productId")+"]").popup(
						{ 
							trigger: ".jiudianText_AA[prodJourneyId="+jiudianText_AA.attr("prodJourneyId")+"][tt='"+jiudianText_AA.attr("tt")+"'][productId="+jiudianText_AA.attr("productId")+"]", 
							lock: true, 
							fixed:true, 
							title:$('.superFreeSubTitleByProductPack[prodJourneyId='+jiudianText_AA.attr("prodJourneyId")+'][tt="'+jiudianText_AA.attr("tt")+'"][productId='+jiudianText_AA.attr("productId")+']').html(), 
							popupObj:'.superFreeSubMainByProductPack[prodJourneyId='+jiudianText_AA.attr("prodJourneyId")+'][tt="'+jiudianText_AA.attr("tt")+'"][productId='+jiudianText_AA.attr("productId")+']'
				});	
			});
			$(".ticketText_AA").each(function(){
				var ticketText_AA = $(this);
				$(".ticketText_AA[prodJourneyId="+ticketText_AA.attr("prodJourneyId")+"][tt='"+ticketText_AA.attr("tt")+"'][productId="+ticketText_AA.attr("productId")+"]").popup(
						{ 
							trigger: ".ticketText_AA[prodJourneyId="+ticketText_AA.attr("prodJourneyId")+"][tt='"+ticketText_AA.attr("tt")+"'][productId="+ticketText_AA.attr("productId")+"]", 
							lock: true, 
							fixed:true, 
							title:$('.superFreeSubTitleByProductPack[prodJourneyId='+ticketText_AA.attr("prodJourneyId")+'][tt="'+ticketText_AA.attr("tt")+'"][productId='+ticketText_AA.attr("productId")+']').html(), 
							popupObj:'.superFreeSubMainByProductPack[prodJourneyId='+ticketText_AA.attr("prodJourneyId")+'][tt="'+ticketText_AA.attr("tt")+'"][productId='+ticketText_AA.attr("productId")+']'
				});	
			});
		}
	});
}

function submitOrder(obj){
	var child = $("input[branchTypes*='CHILD']").val();
	child = child==undefined?0:child;
	var adult = $("input[branchTypes*='ADULT']").val();
	adult = adult==undefined?0:adult;
	$("#pack_content").val($(obj).attr("content"));
	$("#pack_adult").val(adult);
	$("#pack_child").val(child);
	$("#pack_visiTime").val($(".quickBooker_select").val());
	if($("#rapidLoginDialog").length > 0){
		showLogin(function(){document.getElementById("packOrderForm").submit();});
	}else{
		document.getElementById("packOrderForm").submit();
	}
}
	