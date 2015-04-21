// JavaScript Documentspzhe


$(function(){ 
	//全局所有产品超链接后缀增加losc统计代码
	var losc = '?losc=035185&ict=i '; //losc代码； 
	var box = '.itemBox a,.cpList a'; //需要添加losc的a标签的class名； 
	$(box).each(function(){ 
	var herf = $(this).attr('href'); 
	$(this).attr('href',herf+losc) 
	}); 

	
$('.btnList li').click(function(){
	var _num = $(this).index();
	var _Newnum = _num+1;
	$(this).addClass('nav_li').siblings().removeClass('nav_li');
	$(this).parents('.wrap').attr('class','wrap classStyle'+_Newnum);
	$(this).parents('.wrap').find('.cpBox').eq(_num).show().siblings('.cpBox').hide();
});

$('.fdcList li').click(function(){
	$(this).addClass('nav_li').siblings().removeClass('nav_li');
});



/*右侧导航第一屏不显示*/
$(window).scroll(function(){ 
	var scrolltop   = $(document).scrollTop();
	//var _top        = $('.nav_r').offset().top;
	if(scrolltop<400 || scrolltop>2900){$('.sp_pop').hide();}
	else{$('.sp_pop').show();}
});
	

/*抽奖JS*/
	$('.js_start span').click(function(){
		var mthis=this;
		$.ajax({
            type: "get",
            url: "http://www.lvmama.com/checkjsonp/login.do",
            async:false,dataType : "jsonp",jsonp: "jsoncallback",
       		jsonpCallback:"success_jsonpCallback",
            success: function(data){
	                 if(data.flag == "true"){
	                	 $.ajax({
		         	            type: "get",
		         	            url: "http://www.lvmama.com/zhuanti/choujiang.do",
		         	            data:{projectName:'童心童乐专题'},
		         	            async:false,dataType : "jsonp",jsonp: "jsoncallback",
 		               		jsonpCallback:"success_jsonpCallback", 
		         	            success: function(data){
		         		                 if(data.success == "true"){
		         		                	var stopNum = data.winner;
		         		                	if(stopNum==1) 
		         		                	{ 
		         		                		$('.state2').show(); 
		         		                		$('.state1').hide(); 
		         		                		$('.state3').hide();
		         		                		$('.state4').hide();
		         		                	}else if(stopNum==3){ 
		         		                		$('.state3').show(); 
		         		                		$('.state1').hide(); 
		         		                		$('.state2').hide();
		         		                		$('.state4').hide();
		         		                	}else if(stopNum==5){
		         		                		$('.state3').hide(); 
		         		                		$('.state1').hide(); 
		         		                		$('.state2').hide();
		         		                		$('.state4').show();
		         		                	}else{ 
		         		                		$('.state1').find('h3').html(data.lpname);
		         		                		$('.state1').show(); 
		         		                		$('.state2').hide(); 
		         		                		$('.state3').hide();
		         		                		$('.state4').hide();
		         		                	}
		         		             		$(mthis).parent().choujiang({
		         		             			  canshu: stopNum, 
		         		             			oTanList: $('.tanchu li'),      /*弹出层列表的class，每个列表是一个奖品的文字*/
		         		             			  thisLi: 'this_li',            /*当前高亮状态的li的class名字*/
		         		             			 oTanbox: $('.PopBox'),         /* 弹出层的class*/
		         		             			  oClose: $('.close_Btn'),         /*弹出层关闭按钮*/
		         		             			newStart: false                  /*true设置每次从第一个位置开始抽奖，false设置接着中奖位置继续抽奖。*/
		         		             		});
		         		                 }else{
		         		                	 if(null!=data.message){
		         		                		//dialogWindow(data.message)
		         		                		pandora.dialog({title: "",content: "<p style='padding:0 10px; text-align:center;color:#000;font-size:16px;margin:0;'>"+ data.message + "</p>"});
	  		         		                 }
		         		                 }
		         	            }
	                	 });
	                 }else{
	                	 $(UI).ui("login");
	                 }
            }
		});
		
	});	
	
	function getOrders() {
		$.ajax({
	 		type : 'get',
	 		url : "http://www.lvmama.com/zhuanti/getOrderNum.do",
	 		async:false,
	 		dataType : "jsonp",jsonp: "jsoncallback",
	   		jsonpCallback:"success_jsonpCallback", 
	   		success : function(json) {
	   			//判断后台返回的json数值
	   			if(json.money<=1000){
	   				var html_juankuan = '';
	   				html_juankuan=html_juankuan+'<div class=shuzi>00'+json.money+'</div>'; 
	   				$(".tjYList").html(html_juankuan);
	   			}
	   			else if(json.money>1000 &&json.money<10000){
	   				var html_juankuan = '';
	   				html_juankuan=html_juankuan+'<div class=shuzi>00'+json.money+'</div>'; 
	   				$(".tjYList").html(html_juankuan);
	   			}
	   			else if(json.money>=10000 &&json.money<100000){
	   				var html_juankuan = '';
	   				html_juankuan=html_juankuan+'<div class=shuzi>0'+json.money+'</div>'; 
	   				$(".tjYList").html(html_juankuan);
	   			}else if(json.money>=100000 &&json.money<1000000){
	   				var html_juankuan = '';
	   				html_juankuan=html_juankuan+'<div class=shuzi>'+json.money+'</div>'; 
	   				$(".tjYList").html(html_juankuan);
	   			}else if(json.money>=1000000){
	   				var html_juankuan = '';
	   				html_juankuan=html_juankuan+'<div class=shuzi>999999</div>'; 
	   				$(".tjYList").html(html_juankuan);
	   			}
	   			
	   		},
	   		error:function(){
	   			
	   		},
	 	});
	 }	
	
	function winnerslist() {
		$.ajax({
	 		type : 'get',
	 		url : "http://www.lvmama.com/zhuanti/getwinnerslist.do",
	 		async:false,
			data:{projectName: '童心童乐专题'},
			dataType : "jsonp",jsonp: "jsoncallback",
	   		jsonpCallback:"success_jsonpCallback", 
	 		success : function(json) {
	 			if(json!=null){
	 				var _html = '';
	 				for(var i =0;i<json.length;i++){
	 					_html = _html + '<li><span title='+json[i].userName+'>用户名：'+json[i].userName+'</span><b>奖品：'+json[i].lpName+'</b></li>';
	 				}
	 				$(".md_list").html(_html);
	 				var _listName = $('.js_md_list'); //获奖名单ul的class；
	 				var _startN   = 11; //超过5个开始滚动；
	 				var _runH  = _listName.find('li').height(); //向上运动的高度；
			  
	 				var _geshu = _listName.find('li').length;
	 				if(_geshu>_startN){
	 					setInterval(function(){ gundong(_listName,_runH);},2000);
	 				}
	 				function gundong(_listName,_runH){
	 					var first_html =  _listName.find('li').first().html();
	 					_listName.find('li').last().after('<li>'+first_html+'</li>');
	 					_listName.find('li').first().animate({'margin-top':-_runH},500,function(){
	 						$(this).remove();
	 					}); 
	 				}		
	 			}
	 			getOrders();
	 		},
	 		error:function(){
	 		}
	 	});
	 }	

	
	
/*	获奖名单滚动*/
	$(function(){ 
		winnerslist();
	});
	
	
	$('.js_info_banner').qiehuan1({
		  btnL:'.btn_info_l',
		  btnR:'.btn_info_r'
	});


	$('.info_list dd input').focusin(function(){ 
		$(this).parent().addClass('inputNow');
	});
	$('.info_list dd input').focusout(function(){
		$(this).parent().removeClass('inputNow').removeClass('inputNo');
	});
	
	$(".info_tijiao").click(function(){
		var name=$('.info_name').val();
		var mobile=$('.info_shouji').val();
		var email=$('.info_mail').val();
		var _mobile = /^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		var _zip = /^[1-9][0-9]{5}$/;
		var _email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(name=="" || name == $(".info_name").siblings('span').text()){
			$(".info_name").focus();
			$(".info_name").parent().removeClass('inputYes').addClass('inputNo').siblings().removeClass('inputNo');
			return false;
		}else{
			$(".info_name").parent().removeClass('inputNo');
		};
		if(mobile==""){
			$(".info_shouji").focus();
			$(".info_shouji").parent().removeClass('inputYes').addClass('inputNo').siblings().removeClass('inputNo');
			return false;
		}else if(!_mobile.test(mobile)){
			$(".info_shouji").focus();
			$(".info_shouji").parent().removeClass('inputYes').addClass('inputNo').siblings().removeClass('inputNo');
			return false;
		}else{
			$(".info_shouji").parent().removeClass('inputNo');
		};
		
		if(email==""){
			$(".info_mail").focus();
			$(".info_mail").parent().removeClass('inputYes').addClass('inputNo').siblings().removeClass('inputNo');
			return false;
		}
		else if(!_email.test(email)){
			$(".info_mail").focus();
			$(".info_mail").parent().removeClass('inputYes').addClass('inputNo').siblings().removeClass('inputNo');
			return false;
		}else{
			$(".info_mail").parent().removeClass('inputNo');
		};
		
		
	});


});




 //渐变切换
(function($){
	$.qiehuan1 = { 
		init: function (data) {
			this.fangfa(data);
		},
		fangfa: function (data) {
			data.obj.find('ul:first').find('li:first').addClass('imgIndex').show().siblings().hide();
			data.obj.find(data.btnL).click(function(){
				var lr = -1;
				runNow(lr);
			});
			data.obj.find(data.btnR).click(function(){
				var lr = 1;
				runNow(lr);
			});
			function runNow(lr){
				var num = data.obj.find('ul:first').find('.imgIndex').index();
				if(num+lr>data.obj.find('ul:first').find('li').length/data.obj.length-1){
					num = lr= 0 ;
				}
				$('title').text(num+lr);
				data.obj.find('ul:first').find('li').eq(num+lr).addClass('imgIndex').fadeIn(500).siblings().fadeOut(500).removeClass('imgIndex');
			}
		}
	};
	
	$.fn.qiehuan1 = function (options) {
        var data = {
			obj:this
        };
        $.extend(true, data, options || {});
        $.qiehuan1.init(data);
    };
	
})(jQuery);


(function($){ 
	$.choujiang = {
		init: function (settings) {
			var This     = settings.obj ;
			var weizhi   = settings.canshu;
			var btnClose = settings.oClose;
			var thisName = settings.thisLi;
			var li_list  = This.siblings('ul').find('li');
			var li_num   = li_list.length;
			This.children().hide();
			if(This.siblings('ul').find('.'+thisName).length==0){
				li_list.first().addClass(thisName);
			}
			var _num = $('.'+thisName).index();
			if(settings.newStart==true){
				var _num=0;
			};
			function runNow(){ 
				li_list.eq(_num).addClass(thisName).siblings().removeClass(thisName);
				_num+=1;
				if(_num==li_num){ _num=0;}
			};
			
/*抽奖转动方法*/
				var kaishi = setInterval(runNow,60);
				This.children().animate({'left':0},3000,function(){
					clearInterval(kaishi);
					jiansu1 = setInterval(runNow,150);
					}).animate({'left':0},1000,function(){
					clearInterval(jiansu1);
					jiansu2 = setInterval(runNow,250);
					}).animate({'left':0},1000,function(){
						var Stop = setInterval(function(){
							var numNow = $('.'+thisName).index()+1;
							if(numNow==weizhi){
								clearInterval(jiansu2);
								clearInterval(Stop);
								setTimeout(function(){
									tanchu1(settings.oTanbox,settings.oTanList,weizhi);
									This.children().show();
								},1000);
							}
							if(weizhi>li_num || weizhi<1){
								alert('参数错误，此次中奖无效！请刷新页面再试！');
								clearInterval(Stop);
								clearInterval(jiansu2);
							}
						},100);
				});
			
/*弹出层方法*/
			function tanchu1(oTanbox,oTanList,weizhi){ 
				var _windowH   = ($(window).height()-oTanbox.height())/2;
				var _scrolltop = $(document).scrollTop()+_windowH;
				var _boxH    = oTanbox.height();
				if($(window).height()-_boxH<0)_scrolltop = $(document).scrollTop()+20;
				if($('.windowBg').length==0){
					$('body').append('<div class="windowBg" style=" display:none; position:absolute; top:0; left:0; width:100%; background:#333; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity:0.5; opacity:0.5; z-index:998;"></div>');
				};
				oTanbox.show().css({'top':_scrolltop}).animate({},0,function(){
					var height_w =$(document).height();
					$('.windowBg').css({'height':height_w,'width':$(document).width()}).show();
					oTanList.eq(weizhi-1).show().siblings().hide();
				});
			};

/*关闭弹出层方法*/
			btnClose.live('click',function(){
				settings.oTanbox.hide();
				$('.windowBg').hide();
			});
		},
		fangfa: function (settings) {
			/////////扩展其他///////////////
		}
	};
	
	$.fn.choujiang = function (options) {
        var settings = {
			obj:this,
			newStart: false 
        };
        $.extend(true, settings, options || {});
        $.choujiang.init(settings);
    };
    
    $(".huojiang").click(function(){
		var name= $('.name').val();
		var mobile= $('.mobile').val();
		var addr= $(".address").val();
		var zip= $(".zip").val();
		var _mobile = /^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		var _zip = /^[1-9][0-9]{5}$/;
		
		if(name==""){
			alert("请输入姓名");
			return false;
		}
		if(mobile==""){
			alert("请输入手机");
			return false;
		}
		else {
			if(!_mobile.test(mobile)){	
				alert("请输入有效的手机号");
				return false;
			}
		}
		if(zip != "") {	
			if(!_zip.test(zip)){	
				alert("请输入有效的邮政编码");
				return false;
			}
		}else{
			alert("请输入邮政编码");
			return false;
		}
		if(addr==""){
			alert("请输入地址");
			return false;
		}
		$.ajax({
            type: "get",
            url: "http://www.lvmama.com/zhuanti/ajaxAddHuoJiangMessage.do",
            async:false,
            data:{realName:name,mobile:mobile,address:addr,zipcode:zip},
            dataType : "jsonp",jsonp: "jsoncallback",
       		jsonpCallback:"success_jsonpCallback", 
        		success : function(json){
        			if(json.success == "true"){ 
        				$(".PopBox").hide();
        				pandora.dialog({
        					title: "",
        					content: "<p style='padding:0 10px; text-align:center;color:#000;font-size:16px;margin:0;'>领取成功</p>"
        					});
        				
        			} 
        		},error : function(){ 
        			pandora.dialog({
        				title: "",
        				content: "<p style='padding:0 10px; text-align:center;color:#000;font-size:16px;margin:0;'>领取失败</p>"
        				}); 
        		} 
        	});

		/*关闭收货地址弹出框，显示成功弹出框*/
		var _scrolltop = $(document).scrollTop()+150;
		$('.reward').hide();
		$('.tip2').show().css({'top':_scrolltop});
	});
    
	
})(jQuery);
