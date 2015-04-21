//倒计时
var time_now_server, time_now_client, time_end, time_server_client, timerID;

//class加nav1  显示为进行中
//class加nav2  显示为已结束
//class加nav3  为敬请期待hover上去的样式
//已售罄样式 class加btn2
//APP秒杀  519见~的标签改为扫一扫需要去除当前class(.section3 .btn),增加(.section3 .btn3)
function dialogWindow(message){
	var _scrolltop = $(document).scrollTop()+150;
	var height_w =$(document).height();
	$('.bg_tm').css({'height':height_w,'width':$(document.body).width()}).show();
	 $('#tanchu').show().css({'top':_scrolltop});
	 $('#tanchu').find('p').text(message)
}
function gundong(_listName,_runH){
		var first_html =  _listName.find('li').first().html();
		_listName.find('li').last().after('<li>'+first_html+'</li>');
		_listName.find('li').first().animate({'margin-top':-_runH},500,function(){
			$(this).remove();
		}); 
	}	
function winnerslist() {
	$.ajax({
 		type : 'get',
 		url : "http://www.lvmama.com/dacun/getwinnerslist.do",
 		async:false,dataType : "jsonp",jsonp: "jsoncallback",
   		jsonpCallback:"success_jsonpCallback", 
 		success : function(json) {
 			if(json!=null){
 				var _html = '';
 				for(var i =0;i<json.length;i++){
 					_html = _html + '<li><em>恭喜</em><span>'+json[i].userName+'</span><em>获得</em><b>'+json[i].lpName+'</b></li>';
 				}
 				$(".md_list").html(_html);
 				//获奖名单滚动
 			    var _listName = $('.md_list');
 				var _geshu = _listName.find('li').length;
 				var _runH  = _listName.find('li').height(); //向上运动的高度；  
 				if(_geshu>17){
 					setInterval(function(){ gundong(_listName,_runH);},2000);
 				}
 				
 			}
 		},
 		error:function(){
 		}
 	});
 }


$(function () {
	//获取服务器时间戳
	$.ajax({
		url : "http://www.lvmama.com/519dacun/ajaxgetSystemNowDate.do",
		type : 'get',
		async : false,
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsoncallback",
		success : function(json) {
			$('#shengyuTimeLongId').html(json.shengyuTimelong);
 		}
	});
	
    $(".timerBind_one,.timerBind_two").find("li").bind({
        "mouseover": function () {
            var $this = $(this);
            if (!($this.hasClass("nav1") || $this.hasClass("nav2")) && !$this.hasClass("nav3"))
                $this.addClass("nav3");
        },
        "mouseout": function () {
            var $this = $(this);
            $this.removeClass("nav3");
        }
    });
    winnerslist();
    setInterval("winnerslist()", 1800 * 1000);
	setInterval("activeSwitch()", 10 * 1000);	
});
 

function activeSwitch() {
    var date = { year: 2014, month: 5, day: 10, hour: 12, minute: 10, second: 59, shengyuTimelong: 0 };
    $.ajax({
        url: "http://www.lvmama.com/519dacun/ajaxgetSystemNowDate.do",
        type: 'get',
        async: false,
        dataType: "jsonp",
        jsonp: "jsoncallback",
        jsonpCallback: "success_jsoncallback",
        success: function (json) {
            date.year = json.year ? json.year : 1970;
            date.month = json.month ? json.month : 1;
            date.day = json.day ? json.day : 1;
            date.hour = json.hour ? json.hour : 0;
            date.minute = json.minute ? json.minute : 0;
            date.second = json.second ? json.second : 0;

            var activeDay = new Date(date.year + "/" + date.month + "/" + date.day + " " + date.hour + ":" + date.minute + ":" + date.second);
            var month = activeDay.getMonth() + 1;
            var day = activeDay.getDate();
            var hour = activeDay.getHours();
            var minutes = activeDay.getMinutes();
            if (month == 5 && day == 19) {
                var ul_one = $(".timerBind_one");
                var ul = $(".timerBind_two");
                switch (hour) {
                    case 8:
                    case 9:
                        if (ul.find("li:first").hasClass("nav1"))
                            break;
                        ul.find("li:first").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(0)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 10:
                    case 11:
                        if (ul.find("li:eq(1)").hasClass("nav1"))
                            break;
                        ul.find("li:lt(1)").attr("class", "nav2").find("span").text("已结束");
                        ul.find("li:eq(1)").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(1)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 12:
                    case 13:
                        if (ul.find("li:eq(2)").hasClass("nav1"))
                            break;
                        ul.find("li:lt(2)").attr("class", "nav2").find("span").text("已结束");
                        ul.find("li:eq(2)").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(2)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 14:
                    case 15:
                        if (ul.find("li:eq(3)").hasClass("nav1"))
                            break;
                        ul.find("li:lt(3)").attr("class", "nav2").find("span").text("已结束");
                        ul.find("li:eq(3)").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(3)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 16:
                    case 17:
                        if (ul.find("li:eq(4)").hasClass("nav1"))
                            break;
                        ul.find("li:lt(4)").attr("class", "nav2").find("span").text("已结束");
                        ul.find("li:eq(4)").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(4)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 18:
                    case 19:
                        if (ul.find("li:eq(5)").hasClass("nav1"))
                            break;
                        ul.find("li:lt(5)").attr("class", "nav2").find("span").text("已结束");
                        ul.find("li:eq(5)").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:gt(5)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        if (ul.find("li:last").hasClass("nav1"))
                            break;
                        ul.find("li:last").attr("class", "nav1").find("span").text("进行中");
                        ul.find("li:lt(6)").attr("class", "nav2").find("span").text("已结束");
                        break;
                }
                switch (hour) {
                    case 10:
                    case 11:
                        if (ul_one.find("li:first").hasClass("nav1"))
                            break;
                        ul_one.find("li:first").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:gt(0)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 12:
                    case 13:
                        if (ul_one.find("li:eq(1)").hasClass("nav1"))
                            break;
                        ul_one.find("li:lt(1)").attr("class", "nav2").find("span").text("已结束");
                        ul_one.find("li:eq(1)").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:gt(1)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 14:
                    case 15:
                        if (ul_one.find("li:eq(2)").hasClass("nav1"))
                            break;
                        ul_one.find("li:lt(2)").attr("class", "nav2").find("span").text("已结束");
                        ul_one.find("li:eq(2)").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:gt(2)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 16:
                    case 17:
                        if (ul_one.find("li:eq(3)").hasClass("nav1"))
                            break;
                        ul_one.find("li:lt(3)").attr("class", "nav2").find("span").text("已结束");
                        ul_one.find("li:eq(3)").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:gt(3)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 18:
                    case 19:
                        if (ul_one.find("li:eq(4)").hasClass("nav1"))
                            break;
                        ul_one.find("li:lt(4)").attr("class", "nav2").find("span").text("已结束");
                        ul_one.find("li:eq(4)").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:gt(4)").removeAttr("class").find("span").text("敬请期待");
                        break;
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        if (ul_one.find("li:last").hasClass("nav1"))
                            break;
                        ul_one.find("li:last").attr("class", "nav1").find("span").text("进行中");
                        ul_one.find("li:lt(5)").attr("class", "nav2").find("span").text("已结束");
                        break;
                }
                if(hour>=8){
                	if($("div.appPro a.activeBtn:eq(0)").hasClass("btn3"))
                		return;
                	$("div.appPro a.activeBtn").removeClass(".btn").addClass("btn3");
                }
                if(hour>=10){
                	if($("ul.proList3 a.activeBtn:eq(0)").text()=="立刻抢购")
                		return;
            		$("ul.proList3 a.activeBtn").text("立刻抢购");
            	}
            }
            if (month >= 5 && day > 19) {
                $(".timerBind_one,.timerBind_two").find("li").attr("class", "nav2").find("span").text("已结束");
            }
        }
    });
}





$(function(){
	$('.section h3 a').click(function(){
		$(this).parents('.section').find('.popTip').show();
		})
	$('.close').click(function(){
		$('.popTip').hide();
		})
	$('.section3 .btn').mouseover(function(){
		$(this).siblings('.erweima').animate({'top':0},500);
		})
	$('.section3 .btn').mouseout(function(){
		$(this).siblings('.erweima').animate({'top':-183},500);
		})
		
	$('.guanbi').click(function(){
		$('.tip2,.tip,.tip3,.question,.right,.js_right,.wrong,.tip4,.tip5,.yhq,.bg_tm,.denglu,#tanchu').hide();
	});
})


	
	
//1-5折限时抢
$(function(){
	$('.nav li').click(function(){
		var num = $(this).index();
		$(this).parents('.section').find('.pro').eq(num).show().siblings('.pro').hide();
		
	})
	$('.section3 .nav li').click(function(){
		var _num = $(this).index();
		$(this).parents('.section3').find('.pro').eq(_num).show().siblings('.pro').hide();
		})	
		
	$('.proNav li').click(function(){
		var _num = $(this).index();
		$(this).addClass('proNavLi').siblings().removeClass('proNavLi');
		$(this).parents('.pro').find('.proList').eq(_num).show().siblings('.proList').hide()
		
		})
		
		
})


/*抽奖JS*/
	$('.js_start span').click(function(){                     
		var mthis=this;
		//查询用户是否登录，
		 $.ajax({
	            type: "get",
	            url: "http://www.lvmama.com/checkjsonp/login.do",
	            async:false,dataType : "jsonp",jsonp: "jsoncallback",
           		jsonpCallback:"success_jsonpCallback",
	            success: function(data){
		                 if(data.flag == "true"){
		                	 $.ajax({
		         	            type: "get",
		         	            url: "http://www.lvmama.com/519dacun/ajaxcheckHuoJiang.do",
		         	            async:false,dataType : "jsonp",jsonp: "jsoncallback",
    		               		jsonpCallback:"success_jsonpCallback", 
		         	            success: function(data){
		         		                 if(data.success == "true"){
 		         		                	 var jieguo=data.winner;
		         		                		$(mthis).parent().choujiang({
			         		             			  canshu: jieguo, 
			         	             			  oTanList: $('.award'),      /*弹出层列表的class，每个列表是一个奖品的文字*/
			         		             			  thisLi: 'this_li',            /*当前高亮状态的li的class名字*/
			         		             			 oTanbox: $('.reward'),         /* 弹出层的class*/
			         		             			  oClose: $('.guanbi'),         /*弹出层关闭按钮*/
			         		             			newStart: false                  /*true设置每次从第一个位置开始抽奖，false设置接着中奖位置继续抽奖。*/
			         		             		});
		         		                 
 		         		                 }else{
 		         		                	 if(null!=data.message){
 		         		                		dialogWindow(data.message)
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
				if(_num==li_num){ _num=0}
			};
			
/*抽奖转动方法*/
				var kaishi = setInterval(runNow,60);
				This.children().animate({'left':0},3000,function(){
					clearInterval(kaishi)
					jiansu1 = setInterval(runNow,150);
					}).animate({'left':0},1000,function(){
					clearInterval(jiansu1)
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
								},1000)
							}
							if(weizhi>li_num || weizhi<1){
								alert('参数错误，此次中奖无效！请刷新页面再试！')
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
					oTanList.eq(weizhi-1).show().siblings('div').hide();
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
	
})(jQuery);



//获奖名单滚动
$(function(){ 

		
	
	});
	
	




//收货信息验证
	$(".info a").click(function(){
		var s_p = $(this).siblings('p');
		var name= s_p.find('.name').val();
		var mobile= s_p.find('.mobile').val();
		var addr= s_p.find(".address").val();
		var zip= s_p.find(".zip").val();
		var _mobile = /^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		var _zip = /^[1-9][0-9]{5}$/;
		
		if(name==""){
			s_p.find(".name").focus();
			alert("请输入姓名");
			return false;
		}
		if(mobile==""){
			s_p.find(".mobile").focus();
			alert("请输入手机");
			return false;
		}
		else {
			if(!_mobile.test(mobile)){	
				s_p.find(".mobile").focus();
				alert("请输入有效的手机号");
				return false;
			}
		}
		if(zip != "") {	
			if(!_zip.test(zip)){	
				s_p.find(".zip").focus();
				alert("请输入有效的邮政编码");
				return false;
			}
		}
		if(addr==""){
			s_p.find(".address").focus();
			alert("请输入地址");
			return false;
		}
		
		$.ajax({type: "get",async:false,dataType : "jsonp",jsonp: "jsoncallback",
			     jsonpCallback:"success_jsonpCallback", 
        		url:"http://www.lvmama.com/519dacun/ajaxAddHuoJiangMessage.do",
        		data:{realName:name,mobile:mobile,address:addr,zipcode:zip}, 
        		success : function(json){
        			if(json.success == true){ 
        				dialogWindow('领取成功');
        			} 
        		},error : function(){ 
        			dialogWindow("领取失败"); 
        		} 
        	});

		/*关闭收货地址弹出框，显示成功弹出框*/
		var _scrolltop = $(document).scrollTop()+150;
		$('.reward').hide();
		$('.tip2').show().css({'top':_scrolltop});
	});


//APP
	$(function(){ 
/////////调用方法////////////////////////////////////////////////////////

	$('.appPro').each(function(){
		$(this).listScroll({
			  run_ul: '.proList', //运动的列表；
			  tab_ul: '.round',     //切换的列表
			  btn_l : '.zuo',    //左按钮
			  btn_r : '.you',    //右按钮
			  tab_name : 'roundLi', //切换的高亮class
			  run_number:4         //运动张数
		});
	})
});
	
/////////////////////////////////////////////////////////////
	(function($){ 
	$.listScroll = {
		init: function (data) {
			this.listEvent(data)
		},
		listEvent: function (data) {
			var migBox = data.obj,
				runUl = migBox.find(data.run_ul),
				tabUl = migBox.siblings(data.tab_ul),
				btnL = migBox.siblings(data.btn_l),
				btnR = migBox.siblings(data.btn_r),
				liW = runUl.find('li:first').outerWidth(true),
				imgLong = runUl.find('li').length,
				tabHtml = '';
				
			runUl.width(liW*imgLong);
			var see = Math.ceil(runUl.parent().width()/liW);
			//限制运动个数不会 超过可视个数;
			if(data.run_number>see){
				data.run_number = see; 
			}
			//创建tab切换的li
			if(see!=data.run_number){
				for(var i=0;i<imgLong;i++){
					tabHtml+= '<li></li>';
				}
			}else{
				for(var i=0;i<Math.ceil(imgLong/data.run_number);i++){
					tabHtml+= '<li></li>';
				}
			}
			tabUl.html(tabHtml).find('li:first').addClass(data.tab_name);
			tabUl.css({'right':'auto','left':'50%','margin-left':-tabUl.find('li').length*tabUl.find('li:first').outerWidth(true)/2})
			tabAdd(0);
			btnL.click(function(){ 
				var runW = liW*data.run_number;
				var index = tabUl.find('.'+data.tab_name).index()-1;
				if(see==data.run_number){var tabl = -(see-data.run_number+1)}else{var tabl = -data.run_number}
				ImgRrun(runW,index,tabl);
			});
			btnR.click(function(){ 
				var runW = -liW*data.run_number;
				var index = tabUl.find('.'+data.tab_name).index()+1;
				if(see==data.run_number){var tabl = see-data.run_number+1}else{var tabl = data.run_number}
				ImgRrun(runW,index,tabl);
			});
			//滚动检测方法
			function ImgRrun(runW,index,tabl){
				var L = runUl.position().left;
				if(L%liW!=0 && liW%liW==0 || L%liW==0 && liW%liW!=0){//处理ie少1px的bug
					L = L-1;
				}
				if(L%liW==0){//检测是否运动完毕
					if(see==data.run_number){
						var num = Math.ceil( Math.abs(L)/(liW*data.run_number));
					}else{
						var num = Math.ceil( Math.abs(L)/(liW*data.run_number)*data.run_number);
					}
					
					if(L==0 && runW>0){//达到最小值返回最后一个
						runUl.animate({'left':-(runUl.width()-see*liW)},500);
						if(see==data.run_number){
							tabAdd(tabUl.find('li').length-(see-data.run_number)-1)
						}else{
							tabAdd(tabUl.find('li').length-see)
						}
					}else if(L==-(runUl.width()-see*liW) && runW<0){//达到最大值返回第一个
						runUl.animate({'left':0},500);	
						tabAdd(0)
					}else{//检测正常左右运动，以及边界检测
						
						var L1 = L+runW;
						if(L+runW>0){
							L1 = 0;
							//alert(num+tabl+(see-data.run_number))
							if(see==data.run_number){
								tabAdd(num+tabl);
							}else{
								tabAdd(0);
							}
							//tabAdd(num+tabl+(data.run_number*liW-liW)/liW);
							runUl.animate({'left':L1},500);
						}else if(L+runW<-(runUl.width()-see*liW)){
							//tabAdd(num+tabl-(data.run_number*liW-liW)/liW);
							if(see==data.run_number){
								tabAdd(num+tabl);
							}else{
								tabAdd(num+(runUl.width()-see*liW-Math.abs(L))/liW);
							}
							
							runUl.animate({'left':-(runUl.width()-see*liW)},500);	
						}else{
							runUl.animate({'left':L1},500);
							tabAdd(num+tabl);
						}
					};
					
				}//滚动完毕执行下一次	
			};//滚动检测方法结束
			//检测是否可以点击切换
			if(see==data.run_number){
				tabUl.find('li').click(function(){ 
					var runW = -liW*data.run_number;
					var index = $(this).index();
					var L = -index*liW*data.run_number;
					if(L>0){L=0;}else if(L<-(runUl.width()-see*liW)){L=-(runUl.width()-see*liW)}
					runUl.animate({'left':L},500);
					$(this).addClass(data.tab_name).siblings().removeClass(data.tab_name);
				}).css('cursor','pointer');
			};
			//给tab添加class方法
			function tabAdd(index){
				tabUl.find('li').removeClass(data.tab_name);
				for(var i=0;i<tabUl.find('li').length;i++){
					if(see==data.run_number && i == index){
						for(var j=0;j<see-data.run_number+1;j++){
							tabUl.find('li').eq(index+j).addClass(data.tab_name);
						}
					}else if(i == index){
						for(var j=0;j<see;j++){
							tabUl.find('li').eq(index+j).addClass(data.tab_name);
						}
					}
				}	
			}//tabAdd

		}
	};
	
	$.fn.listScroll = function (options) {
        var data = {
			obj:this,
			run_number:1
        };
        $.extend(true, data, options || {});
        $.listScroll.init(data);
    };
	
})(jQuery);

$(function(){
//优惠券领取弹出层	
	$("#getMarkCoupon").click(function(){
    	$.ajax({type: "get",url: "http://www.lvmama.com/checkjsonp/login.do",
    		 async:false,dataType : "jsonp",jsonp: "jsoncallback",
    		 jsonpCallback:"success_jsonpCallback", 
    	       success: function(data){
    			if(data.flag == "true"){
    	var _flag = true;
    			
    	$.ajax({type: "get",async:false,dataType : "jsonp",jsonp: "jsoncallback",
    		jsonpCallback:"success_jsonpCallback", 
    		url:"http://login.lvmama.com/nsso/ajax/bindingCouponIdAndUserToDacu.do",
    		data:{couponId :'4466,4467,4468,4469,4470,4471,4472'}, 
    		success : function(json){
    			if(json.success == true){
    				var _scrolltop = $(document).scrollTop()+150;
    				var height_w =$(document).height();
    				$('.bg_tm').css({'height':height_w,'width':$(document.body).width()}).show();
    				$('.yhq').show().css({'top':_scrolltop});
    			}else if(json.success == false){ 
    				dialogWindow(json.errorText);
    			} 
    		},error : function(){ 
    			dialogWindow("领取失败"); 
    		} 
    	}); 
    		}else{ $(UI).ui("login");
    				}
    	      }});
    });     
		
//抢红包弹出层		
	     $('.prize li a').click(function() {

			$.ajax({type: "get",url: "http://www.lvmama.com/checkjsonp/login.do",
			  async:false,dataType : "jsonp",jsonp: "jsoncallback",
			  jsonpCallback:"success_jsonpCallback", 
			  success: function(data){
					if(data.flag == "true"){
							var _flag = true;
							$.ajax({type: "get",async:false,dataType : "jsonp",jsonp: "jsoncallback",
							jsonpCallback:"success_jsonpCallback", 
							url:"http://www.lvmama.com/519dacun/ajaxQuestion.do",
	 						success : function(json){
								if(json.success == "true"){
 									var questionhtml='<h4>提 问：</h4><p>'+json.question+'</p>'+
										'<p><label><input type="radio" name="anwser"  value="1">'+json.anwsera+'</label>'+
										'<label><input type="radio" name="anwser" value="2">'+json.anwserb+'</label></p>'+
										'<input type="hidden" value="'+json.right+'" id="question_anwserid">'+
										'<a href="javascript:void(0)" target="_self"></a>'+
										'<span class="guanbi"></span>';
 									
 								    //显示问题
 									var _scrolltop = $(document).scrollTop() + 150;
 									var height_w = $(document).height();
 									$('.bg_tm').css({
 										'height' : height_w,
 										'width' : $(document.body).width()
 									}).show();
 									$('.js_question').html(questionhtml);
 									 $('.js_question').show().css({
 										'top' : _scrolltop
 									 });
 									$('.guanbi').click(function(){ 
							    			 $('.js_question,.bg_tm').hide();
 									});
 								     $('.question a').click(function() {
 								    	$('.js_question').hide(); 
 								    	var anwser=$("input[name='anwser']:checked").val();
 								    	var right=$('#question_anwserid').val();
 								    	 if(anwser==right){
 								    		$.ajax({type: "get",async:false,dataType : "jsonp",jsonp: "jsoncallback",
 												jsonpCallback:"success_jsonpCallback", 
 												url:"http://www.lvmama.com/519dacun/ajaxQianghongbao.do",
 						 						success : function(json){
 													if(null!=json&&json.success == "true"){
 														var jieguoHtml='<h4>恭喜您回答正确</h4>'+
 														'<p>得到 '+json.jiebie+'元现金红包！</p>'+
 														'<span class="guanbi"></span>';
 														 // 答案正确
 														 $('.js_right').html(jieguoHtml);
 			 								    		 $('.js_right').show().css({
 			 								    			 'top' : _scrolltop
 			 								    		 });
 			 								    		 $('.guanbi').click(function(){
 			 								    			 
 			 								    			 $('.js_right,.bg_tm').hide();
 			 								    		 })
 													}else if(null!=json&&json.success == "false"){
 														$('.tip5').show().css({
 			 												'top' : _scrolltop
 			 											});
 													}
 						 						}
 								    		});
 								    	 }else{
 								    		$.ajax({type: "get",async:false,dataType : "jsonp",jsonp: "jsoncallback",
 												jsonpCallback:"success_jsonpCallback", 
 												url:"http://www.lvmama.com/519dacun/ajaxanwserwrong.do",
 						 						success : function(json){
 						 							
 						 						}
 								    		});
 								    		 // 答案错误
	 										$('.wrong').show().css({
	 											'top' : _scrolltop
	 										});
 								    	 }
 								    })
								}else if(json.success == "false"){ 
									dialogWindow(json.errorText);
								} 
							},error : function(){ 
								dialogWindow("失败"); 
								} 
							}); 
					}else{
						$(UI).ui("login");
					}
			  }
			});
	     
	    	 
	    	
     	});
	
	
	
//侧导航			
	$(window).scroll(function(){ 
		var scrolltop = $(document).scrollTop(); 
		if(scrolltop<400){ 
			$('.sideNav').hide(); 
			}else{ 
			$('.sideNav').show(); 
			} 
		});	
//分享			
	$('.jiathis_streak_share_32x32').hide();
	$('.fx').mouseover(function(){
		$('.fxcontent .jiathis_streak_share_32x32').css('display','block');
		})
	$('.fx').mouseout(function(){
		$('.fxcontent .jiathis_streak_share_32x32').css('display','none');
		})
});
//losc代码	
$(function(){ 
	var losc = '?losc=034852 '; //losc代码；
	var box  = '.proList3 .proImg,.proList3 .proTit,.proList3 .btn,.proList2 .proImg,.proList2 .proTit,.proList2 .btn,.app,.button,.hdList li a';  //需要添加losc的a标签的class名；
	$(box).each(function(){
		var herf = $(this).attr('href');
		$(this).attr('href',herf+losc)
	});
	
});


//延迟加载
$(function(){
    /*=============图片延迟加载=============*/
    $.expr[":"].loading=function(elt,index){
        var height=$(window).height();
        var top=$(elt).offset().top;
        return top>$(window).scrollTop()&&top<(height+$(window).scrollTop())
    };
    $.expr[":"].loaded=function(elt,index){
        var height=$(window).height();
        var top=$(elt).offset().top;
        return top<height
    };
    var loadImg=function(){
        this.src=$(this).css({'opacity':0}).attr("to");
        $(this).removeAttr("to");
		var This = this;
		if($(this).load()){
			$(This).animate({'opacity':1},300,function(){$(This).removeAttr('style')});
		}
        
        this.onerror=function(){
            this.src="http://pic.lvmama.com/img/cmt/img_120_60.jpg"
        }
    };
    var imgTimeId=null;
    var scrollImgLoading=function(){
        clearTimeout(imgTimeId);
        imgTimeId=setTimeout(function(){
            $("img[to]:loading").each(function(){
                loadImg.call(this)
            });
            if($("img[to]").size()==0){
                $(window).unbind('scroll',scrollImgLoading)
            }
        }
        ,200)
    };
    $(window).bind('scroll',scrollImgLoading);
    $("img[to]:loaded").each(function(){
        loadImg.call(this)
    });
    setTimeout(function(){
        $("img[to]:loaded").each(function(){
            loadImg.call(this)
        })
    }
    ,1000);
    /*============延迟加载结束===========*/
   
});

//头部置顶
$(window).scroll(function() {
    var _scrolltop = $(document).scrollTop();
    if (_scrolltop >= 85)
    {
        $('.jsTop').addClass('top2').removeClass('top');
    }
    else
    {
        $('.jsTop').removeClass('top2').addClass('top');
    }

});

//文字滚动
$(function(){
	function ScrollImgLeft(){ 
	var speed=50; 
	var scroll_begin = document.getElementById("begin"); 
	var scroll_end = document.getElementById("end"); 
	var scroll_div = document.getElementById("gundong"); 
	scroll_end.innerHTML=scroll_begin.innerHTML; 
	function Marquee(){ 
	if(scroll_end.offsetWidth-scroll_div.scrollLeft<=0) 
	scroll_div.scrollLeft-=scroll_begin.offsetWidth; 
	else 
	scroll_div.scrollLeft++; 
	} 
	var MyMar=setInterval(Marquee,speed); 
	scroll_div.onmouseover=function() {clearInterval(MyMar);} 
	scroll_div.onmouseout=function() {MyMar=setInterval(Marquee,speed);} 
	}
	ScrollImgLeft(); 
	
});
