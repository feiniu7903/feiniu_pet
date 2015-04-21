
$(function(){
	//查看订单详细
	$(".snspt_ordindex").click(function(){
		var arr = $("input[id='" + $(this).attr("orderId") + "']");
		var orderItemMetaIds = "";
		var i;
		for(i=0;i<arr.length;i++){
			orderItemMetaIds = orderItemMetaIds + arr[i].value + ",";
		}
		$.ajax({
	   		url: '/ebooking/eplace/getOrderInfo.do',
	    	dataType: 'html',
	  		data: {
	  			orderItemMetaIds:orderItemMetaIds
	  		},
	  		success: function(datas){
			     var html_cont;
                 html_cont=datas;
				 lv_alert(1100,html_cont,"订单详情","btnC_ord"); 
			},
			error : function(){
				alert("无法加载订单数据");
			} 
		});
    });
	//查看订单子子项详细
	$("#orderItemMetaId").click(function(){
		$.ajax({
	   		url: '/ebooking/eplace/getOrderInfoByMetaOrderId.do',
	    	dataType: 'html',
	  		data: {
	  			orderItemMetaId:$(this).attr("orderItemMetaId")
	  		},
	  		success: function(datas){
			     var html_cont;
                 html_cont=datas;
				 lv_alert(1100,html_cont,"订单详情","btnC_ord"); 
			},
			error : function(){
				alert("无法加载订单数据");
			} 
		});
    });
	
	//通关
	$(".snspt_optBtn").click(function(){
		var hiddenValue='<input type="hidden" id="hiddenValue" value="'+$(this).attr('data_attr')+'">';
		var obj=eval('('+$(this).attr("data_attr")+')');
		var paidOrderShowStr="<div class='snspt_tgpop'>是否确定通关？</div>"+hiddenValue;
		var orderId = obj.orderId;
		var isAperiodic = obj.isAperiodic;
		if(obj.paymentTarget=='TOLVMAMA'){
			if(isAperiodic=="true"){
				$.ajax({
					type : 'POST',
					url : '/ebooking/eplace/checkActivateOrderDateSpace.do',
					async : false,
					cache : false,
					data : {
						orderId : orderId
					},
					dataType : 'json',
					success : function(data) {
						if(data.success) {
							setTimeout(function(){lv_alert(400,paidOrderShowStr,"通关处理","btnC");},400);
						} else {
							if(data.canPass) {
								paidOrderShowStr="<div class='snspt_tgpop'>"+data.message+"</div>" + hiddenValue;
								setTimeout(function(){lv_alert(400,paidOrderShowStr,"通关处理","btnC");},400);
							} else {
								alert(data.message);
								return false;
							}
						}
					}
				});
			} else {
				setTimeout(function(){lv_alert(400,paidOrderShowStr,"通关处理","btnC");},400);
			}
		}else{
			$.ajax({
				url: '/ebooking/eplace/doPassPortInfo.do',
				dataType: 'html',
				data: obj,
				success: function(datas){
					var html_cont;
					html_cont=datas+hiddenValue;
					setTimeout(function(){lv_alert(600,html_cont,"通关处理","btnC");},400);
				},
				error : function(){
					alert("通关数据传输出错");
				} 
			});
		}
    })//click
    
	$(".snspt_tg_opt_s").live("click",function(){
		var hiddenValue=$('#hiddenValue').val();
		var obj=eval('('+hiddenValue+')');
		obj.quantity = new Array();
		obj.orderItemMetaId = new Array();
		var i = 0;
		$(".snspt_tab3 select[name='quantity']").each(function() {
			obj.orderItemMetaId[i] = $(this).attr("orderItemMetaId");
			obj.quantity[i] = $(this).val();
			i++;
		});
		obj.remark="";
		if($('#remark').size() > 0) {
			obj.remark=$('#remark').val();
		}
		$.post('/ebooking/eplace/passInfo.do',$.param(obj).replace(/\%5B\%5D/gi,""),
	  		function(datas){
				 if(datas.returnFlag){
				   var html_cont="<div class='snspt_tgpop'><i class='snspt_tg_s'></i>通关成功</div>";
				 }else{
				   var html_cont="<div class='snspt_tgpop'><i class='snspt_tg_f'></i>通关失败</div><p>"+datas.message; 
				 }
				 lv_alert(400,html_cont,"通关处理"); 
			},'json');
    })//click
})

$(function(){
	$(".msg_getTkt").click(function(){
		var data=eval("("+$(this).attr("data_attr")+")");
		$.ajax({
	   		url: '/ebooking/eplace/getUserLogJson.do',
	    	dataType: 'json',
	  		data: data,
	  		success: function(datas){
	  			var html_cont="<table class='snspt_poptab'>";
	  			html_cont+="<tr><th width='80'>留言时间</th><th width='80'>用户名</th><th>详情</th></tr>";
			     $.each(datas,function(index,value){
			    	 html_cont+="<tr><td>"+value.orderCreateTime+"</td><td>"+value.contactName+"</td><td>"+ value.userMemo +"</td></tr>";
				 })
				 html_cont+="</table>";
				 lv_alert(500,html_cont,"取票人留言（备注）");  
			},
			error : function(){
				alert("取票人留言数据传输出错");
			}
		})//ajax
	});
});//

$(function(){
	$(".msg_optr").click(function(){
		var data=eval("("+$(this).attr("data_attr")+")");
		$.ajax({
	   		url: '/ebooking/eplace/getPassPortLogJson.do',
	    	dataType: 'json',
	  		data: data,
	  		success: function(datas){
	  			var html_cont="<table class='snspt_poptab'>";
	  			html_cont+="<tr><th width='80'>留言时间</th><th width='80'>用户名</th><th>详情</th></tr>";
			     $.each(datas,function(index,value){
			    	 html_cont+="<tr><td>"+value.createDate+"</td><td>"+value.operatorName+"</td><td>"+ value.performMemo +"</td></tr>";
				 })
				 html_cont+="</table>";
				 lv_alert(500,html_cont,"操作人留言（备注）");  
			},
			error : function(){
				alert("操作人留言数据传输出错");
			}
		})//ajax
	})//click	
});//

$(function(){
	$(".log_optr").click(function(){
		var data=eval("("+$(this).attr("data_attr")+")");
		$.ajax({
	   		url: '/ebooking/eplace/getPassPortLog.do',
	    	dataType: 'html',
	  		data: data,
	  		success: function(datas){
				 lv_alert(600,datas,"操作日志"); 	 
			},
			error : function(){
				alert("操作日志数据传输出错");
			}
		})//ajax
	})//click	
});//

$(function(){
	    var cssArgs=new Array("http://pic.lvmama.com/styles/global_backpop.css");
		if($("body:has('#snspt_newpop')").length==0){ 
			for(var i=0;i<cssArgs.length; i++){
				var lv_poplink = document.createElement("link");
				lv_poplink.setAttribute("type", "text/css");
				lv_poplink.setAttribute("rel", "stylesheet");
				lv_poplink.setAttribute("href",cssArgs[i]);
			    $("head").append(lv_poplink);
			}
		}
})
	function lv_alert(popW,alert_str,alert_tit,alert_btn){
		var spcbtn="";
		var combtn="<p class='lv_pop_btnbox'><a href='javascript:void(0)' class='lv_pop_btn_rl lv_pop_optbtn'>确定</a></p>";
		if(alert_btn=="btnC"){
			spcbtn=$(".snspt_pop_contwrap").html();
			
		}else if(alert_btn=="btnC_ord"){
			
			spcbtn=$(".snspt_pop_contwrap1").html();
		}else{
			spcbtn=combtn;
		}
		var p="<div class='pageOver' id='pageOver'></div>";
		($('#pageOver').attr("id")==undefined)?p=p:p='';
		var lv_pop_obj=""+
		"<div class='lv_pop lv_pop_w1' id='snspt_newpop'>"+
			"<div class='lv_pop_inner'>"+
				  "<div class='lv_pop_close'></div>"+
				  "<div class='lv_pop_tit'></div>"+
				  "<div class='lv_pop_cont lv_pop_cont_c'>"+  
				  "</div>"+
				  spcbtn+
			"</div>"+
		"</div>"+p;
		/*"<div id='pageOver'></div>"
		"<iframe id='popCover' frameborder='0' allowTransparency='true'><iframe>"*/
        if($("body:has('#snspt_newpop')").length==0){ 
			$("body").append(lv_pop_obj);
		}else{
			$("#snspt_newpop").replaceWith(lv_pop_obj);
		}
		$("div.lv_pop_cont").html(alert_str);
		$('div.lv_pop_tit').text(alert_tit);
		$('div.lv_pop_w1').css('width',popW);
		sucshow("div.lv_pop",".pageOver","#popCover");
		$(".lv_pop_optbtn").bind("click",function(){$('#queryPassPortForm').submit();});
	}//lv_alert
	
function sucshow(showPop,black_bg,ifr){ 
   var fc = $(showPop);
   var h=$(window).scrollTop()+($(window).height()-fc.height())/2;
   ($(window).height()>fc.height())?h=h:h=$(window).scrollTop();
   fc.show().css({
		left : "50%",
		marginLeft : -fc.outerWidth(false)/2,
		top : h+'px'
   });
   if(black_bg!=null){
	   var dh=document.body.scrollHeight;
	   var wh=window.screen.availHeight;
	   var yScroll;
       dh>wh?yScroll =dh:yScroll = wh; 
	   (yScroll>h+fc.height())?yScroll=yScroll:yScroll=h+fc.height();
	   $(black_bg).css("height", yScroll);
	   $(black_bg).show();
   }
   //$("select").hide();
}//弹出层

close_evt(".lv_pop_close",".lv_pop",".pageOver","#popCover"); // 关闭弹出层
close_evt(".lv_pop_optbtn",".lv_pop",".pageOver","#popCover");// 关闭弹出层
close_evt(".snspt_ord_opt_s",".lv_pop",".pageOver","#popCover");// 关闭弹出层
close_evt(".snspt_tg_opt_c",".lv_pop",".pageOver","#popCover");// 关闭弹出层
function close_evt(close_btn,popdiv,black_bg,ifr){    
	$(close_btn).live("click",function(){
		$(this).parents(popdiv).hide();
		if(black_bg!=null){
			$(black_bg).hide();
		}
		$("select").show();
    });
	
}//弹出层关闭	
/*alert——addpop样式*/
function changePrice() {
	var price = 0;
	$(".snspt_tab3 select[name='quantity']").each(function() {
		price += $(this).val() * $(this).attr("price");
	});
	price = price / 100;
	$("#payMoneyId").html(price);
}
