
//预订功能固定位置
function yd_fixed(){
	var d = $("#free_yd");
	var top = d.offset().top;
	var timeId = null;
	if(ie()>6){
		$(window).scroll(function(){
			if($(this).scrollTop() > top){
				if(!d.attr("fixed")){
					var left = $("#free_detail").offset().left;
					d.attr("fixed",true);
					d.css({
						position : "fixed",
						top : 0,
						left : left,
						zIndex : 98,
						width : d.width()
					});
				}
			}else{
				if(d.attr("fixed")){
					d.css({
						position:"",
						left : "",
						zIndex : "98"
					});
					d.removeAttr("fixed");
				}
			}
		}).resize(function(){
			if(d.attr("fixed")){
				var left = $("#free_detail").offset().left;
				d.css("left",left);
			}
		});
	}
}
//预订人数加加减减
function yd_minsplus(){
	$("#free_mins").click(function(){
		var elt = $("#free_input");
		var tmp = parseInt(elt.val());
		if(elt.val()=="" || isNaN(elt.val())){
			tmp = 1;
		}
		tmp--;
		if(tmp<1){
			tmp = 1;
		}
		elt.val(tmp);
		$("em.rs").html(tmp);
		var arr = []
		for(var i=0;i<tmp;i++){
			arr.push("<option value="+(i+1)+">"+(i+1)+"</option>");
		}
		$("select.free_select_num").html(arr.join(""));
	});
	$("#free_plus").click(function(){
		var elt = $("#free_input");
		var tmp = parseInt(elt.val());
		if(elt.val()=="" || isNaN(elt.val())){
			tmp = 0;
		}
		tmp++;
		elt.val(tmp);
		$("em.rs").html(tmp);
		var arr = []
		for(var i=0;i<tmp;i++){
			arr.push("<option value="+(i+1)+">"+(i+1)+"</option>");
		}
		$("select.free_select_num").html(arr.join(""));
	});
	$("#free_input").blur(function(){
		var elt = $("#free_input");
		var tmp = elt.val();
		if(tmp=="" || isNaN(tmp)){
			elt.val("1");
		}
		tmp = parseInt(tmp);
		$("em.rs").html(tmp);
		var arr = []
		for(var i=0;i<tmp;i++){
			arr.push("<option value="+(i+1)+">"+(i+1)+"</option>");
		}
		$("select.free_select_num").html(arr.join(""));
	});
}
function other(){
	//点击行进行单选
	$("input:radio").each(function(){
		var elt = $(this);
		elt.parent().parent().click(function(){
			elt.attr("checked",true);
		});
	});
	//点击‘更改’的切换效果
	var prev = null;
	var prevIndex = -1;
	$("a.free_change_a").live("click",function(e,effect1,effect2){
		var index = (1+1)%4 || 4;
		if(prev!=null && prev!=this){
			var eltPt = $(prev).parent().parent();
			eltPt.next()[effect2 || "slideUp"]();
			eltPt.removeClass("free_yd_linedata_active");
			eltPt.find("span.free_yd_cur").removeClass("free_yc_"+prevIndex+"_active");
			eltPt.find("li.free_yl_3 em").removeClass("active");
			eltPt.find("em.free_ca_jiao").removeClass("free_ca_jiao_active");
		}
		var eltPt = $(this).parent().parent();
		eltPt.next()[effect1 || "slideToggle"]();
		eltPt.toggleClass("free_yd_linedata_active");
		eltPt.find("span.free_yd_cur").toggleClass("free_yc_"+index+"_active");
		eltPt.find("li.free_yl_3 em").toggleClass("active");
		eltPt.find("em.free_ca_jiao").toggleClass("free_ca_jiao_active");
		prev = this;
		prevIndex = index;
	});
	//调整行高
	$("li.free_yl_2").each(function(){
		$(this).parent().css("height",$(this).height());
	});
	//航班、酒店、景点、当地游 hover效果
	$("ul.free_yd_linedata").live("mouseenter",function(){
		var index = (i+1)%4 || 4;
		$(this).addClass("free_yd_linedata_hover");
		$(this).find("span.free_yd_cur").addClass("free_yc_"+index+"_hover");
		$(this).find("li.free_yl_3 em").addClass("hover");
	}).live("mouseleave",function(){
		var index = (i+1)%4 || 4;
		$(this).removeClass("free_yd_linedata_hover");
		$(this).find("span.free_yd_cur").removeClass("free_yc_"+index+"_hover");
		$(this).find("li.free_yl_3 em").removeClass("hover");
	});
	var prev_fhc = null;
	$("tr.free_hdl_content").each(function(){
		$("tr.free_hdl_content").live("mouseenter",function(){
			$(this).addClass("free_hdl_content_hover");
		}).live("mouseleave",function(){
			$(this).removeClass("free_hdl_content_hover");
		}).live("click",function(){
			if(prev_fhc!=null){
				$(prev_fhc).removeClass("free_hdl_content_active");
			}
			$(this).addClass("free_hdl_content_active");
			prev_fhc = this;
		});
	});
	//点击取消收回层
	$("a.free_cannel_a").each(function(){
		$(this).click(function(){
			$(this).parent().parent().prev().find("a.free_change_a").click();
		});
	});
	//航班、酒店、景点、当地游 修改信息的保存
	$("input.free_save_btn").each(function(i){
		$(this).mouseover(function(){
			this.src = "http://pic.lvmama.com/img/new_v/ob_detail/free/free_save_btn_hover.png";
		}).mouseout(function(){
			this.src = "http://pic.lvmama.com/img/new_v/ob_detail/free/free_save_btn.png";
		}).click(function(){
			switch((i+1)%4){
				case 1: //日期
					var radio = $("input[name='free_radio_hk']:checked");
					if(radio.size()==0){
						alert("请选择上航班！");
						return;
					}
					var main = radio.parent().parent();
					var str = main.find(".start_city").text()+"→"+main.find(".end_city").text()+"　"+main.find(".hk").text()+" "+main.find(".hkh").text()+
						"　"+main.find(".start_time").text()+" "+main.find(".start_jc").text()+"起飞　"+main.find(".end_time").text()+" 抵达"+
						main.find(".end_jc").text()+"　机型"+main.find(".jx").text();
					$("li.free_yl_2").eq(i).html(str);
					break;
				case 2://酒店
					var radio = $("input[name='free_radio_house']:checked");
					if(radio.size()==0){
						alert("请选择上酒店！");
						return;
					}
					var all = radio.parent().parent().find("td");
					var fhright = radio.parents(".free_hl_right");
					var title = fhright.find("h1.free_hotel_title").text();
					var lightNum = fhright.parent().parent().prev().find("em.rzsc").text();
					var str = title+"　"+all.eq(0).text()+"　"+all.eq(1).text()+"　"+all.eq(2).text()+"　"+all.eq(3).text()+"宽带　"+all.eq(5).text()+"间　"+lightNum+"晚";
					$("li.free_yl_2").eq(i).html(str);
					break;
				case 3://景点
				case 0://当地游
					var tr = $(this).parent().parent().find("tr.free_hdl_content");
					var arr = [];
					tr.each(function(i){
						var td = $(this).find("td");
						var tmp = (i+1)+"、"+td.eq(0).text()+"　人数："+td.eq(3).find("select").val()+"　游玩日期："+td.eq(2).find("select").val();
						arr.push(tmp);
					});
					var str = "";
					if(arr.length==1){
						str = arr.join("").substring(2);
					}else{
						str = "<b>"+arr.length+"个景点</b><br/>"+arr.join("<br/>");
					}
					var fy2 = $("li.free_yl_2").eq(i).html(str);
					fy2.parent().css("height",fy2.height());
					break;
			}
			$(this).parent().parent().prev().find("a.free_change_a").trigger("click");
			this.blur();
		});
	});
}
function outerinit(){
	//友情提示浮层
	$("#free_cha").click(function(){
		$(this).parent().fadeOut(400);
	});
	window.free_yd_main_list = $("#free_yd_main_list").html();
	$("#recommend_xc").click(function(){
		$("#free_yd_main_list").html(window.free_yd_main_list);
		fuceng(); //浮层功能初始化
		other(); //其他功能初始化
	});
	//窗口大小变动时，调整酒店浮层位置
	$(window).resize(function(){
		var elt = $("#free_hotel_detail");
		if(ie()<=6){
			var left = ($(window).width() - elt.width())/2;
			var top = ($(window).height() - elt.height())/2 + $(window).scrollTop();
			elt.css({
				left : left,
				top : top
			});
		}else{
			var left = ($(window).width() - elt.width())/2;
			var top = ($(window).height() - elt.height())/2;
			elt.css({
				left : left,
				top : top
			});
		}
	});
}
function fuceng(){
	//酒店浮层
	$("a.free_hotel_a").each(function(){
		$(this).click(function(){
			var elt = $("#free_hotel_detail");
			if(ie()<=6){
				var left = ($(window).width() - elt.width())/2;
				var top = ($(window).height() - elt.height())/2 + $(window).scrollTop();
				elt.css({
					left : left,
					top : top
				}).show();
				elt.find(".free_hotel_bbg").css("height",elt.height());
			}else{
				var left = ($(window).width() - elt.width())/2;
				var top = ($(window).height() - elt.height())/2;
				elt.css({
					left : left,
					top : top,
					position : "fixed"
				}).fadeIn(200);
			}
		});
	});
	$("#free_hb_close").click(function(){
		$("#free_hotel_detail").fadeOut(200);
	});
	//房间浮层
	var timeId = null;
	$("a.free_hotel_house").each(function(){
		$(this).mouseover(function(){
			clearTimeout(timeId);
			var elt = $("#free_hotel_house");
			var left = $(this).offset().left + $(this).width() + 20;
			var top = $(this).offset().top - 36;
			elt.css({
				left : left,
				top : top
			}).show();
		}).mouseout(function(){
			timeId = setTimeout(function(){
				$("#free_hotel_house").hide();
			},300);
		});
	});
	$("#free_hotel_house").unbind("mouseenter").mouseenter(function(){
		clearTimeout(timeId);
	}).unbind("mouseleave").mouseleave(function(){
		timeId = setTimeout(function(){
			$("#free_hotel_house").hide();
		},300);
	});
	//景点浮层
	var timeId = null;
	$("a.free_scenic_a").each(function(){
		$(this).mouseover(function(){
			clearTimeout(timeId);
			var elt = $("#free_scenic_desc");
			var left = $(this).offset().left + $(this).width() + 20;
			var top = $(this).offset().top - 36;
			elt.css({
				left : left,
				top : top
			}).show();
		}).mouseout(function(){
			timeId = setTimeout(function(){
				$("#free_scenic_desc").hide();
			},300);
		});
	});
	$("#free_scenic_desc").unbind("mouseenter").mouseenter(function(){
		clearTimeout(timeId);
	}).unbind("mouseleave").mouseleave(function(){
		timeId = setTimeout(function(){
			$("#free_scenic_desc").hide();
		},300);
	});
}
function big_banner(banner_panel,img_panel,bar_list,bar_crt){
	var allNum = 0;
	var i, j;
	var indexNum = $(img_panel).find("li:last-child").index();
	$(img_panel).find("li:first-child").fadeIn(1000); 

	$(bar_list).find("li").mouseover(function(){
		var num = $(this).index();
		allNum= num;
		$(this).addClass(bar_crt).siblings().removeClass(bar_crt);
		$(img_panel).find("li").eq(num).fadeIn(800).siblings().hide();
	}); 
	function imgScroll(){
		$(bar_list).find("li").eq(allNum).addClass(bar_crt).siblings().removeClass(bar_crt);
		$(img_panel).find("li").eq(allNum).fadeIn(1000).siblings().hide();
		allNum += 1;
		if(allNum>indexNum) allNum=0;
	}           
	var anima = setInterval(imgScroll,3000);            
	$(banner_panel).hover(
	  function () {
		clearInterval(anima);
	  },
	  function () {
		anima = setInterval(imgScroll,3000);
	  }
	);  
}
function animate(slt,attr,start,end,speed,callback,isOpacity){
	var bro = $.browser;
	if(bro.mozilla){
		attr = "-moz-" + attr;
	}else if(bro.safari){
		attr = "-webkit-" + attr;
	}else if(bro.msie && navigator.appVersion.indexOf("MSIE 9.0")>=0){
		attr = "-ms-" + attr;
	}else{
		if(typeof callback == 'function'){
			callback();
		}
		return;
	}
	var time = 15;
	speed = speed+speed%time;
	var startNum = parseFloat(start.substring(start.indexOf("(")+1,start.indexOf(")")));
	var endNum = parseFloat(end.substring(end.indexOf("(")+1,end.indexOf(")")));
	var step = (endNum - startNum)/speed*time;
	var i = 0;
	var deg = "";
	var tmpArrs = start.replace(/\([\d\.]+\)/,"(-)").split("-");
	if(start.indexOf("deg")>0){
		deg = "deg";
		tmpArrs = start.replace(/\([\d\.]+deg\)/,"(-)").split("-");
	}
	var p = {start:start,startNum:startNum,endNum:endNum,step:step,i:i,time:time,attr:attr,speed:speed,clearId:null,tmpArrs:tmpArrs,startNum2:startNum};
	if(!isOpacity){
		if(p.endNum>=p.startNum){
			slt.css("opacity","0").fadeTo(speed,1);
		}else{
			slt.fadeTo(speed,0);
		}
	}
	var loop = function(p){
		if(p.i*p.time>p.speed){
			if(typeof callback == 'function'){
				callback();
			}
			return;
		}else{
			var nowPosition = p.startNum2+p.step;
			p.startNum2 = nowPosition;
			if(p.endNum>=p.startNum){
				if(nowPosition>p.endNum){
					nowPosition = p.endNum;
				}
			}else{
				if(nowPosition < p.endNum){
					nowPosition = p.endNum;
				}
			}
			nowPosition += deg;
			var value = p.tmpArrs[0] + nowPosition + p.tmpArrs[1];
			slt.css(p.attr,value);
			p.clearId = setTimeout(function(){
				loop(p);
			},p.time);
		}
		p.i++;
	}
	clearTimeout(p.clearId);
	loop(p);
}
function load(url,callback){
	if(url instanceof Array){
		var loadedNum = 0;
		var len = url.length;
		for(var i=0;i<len;i++){
			load(url[i],function(){
				loadedNum++;
				if(loadedNum==len){
					typeof callback=='function'&&callback();
				}
			});
		}
	}else if(typeof url == 'string'){
		var eltType = "",
			attr = "";
		if(/\.js$/.test(url)){
			eltType = "script";
			attr = "src";
		}else if(/\.css$/.test(url)){
			eltType = "link";
			attr = "href";
		}
		var elt = document.createElement(eltType);
		elt[attr] = url;
		if(elt.onload==null){
			elt.onload = function(){
				typeof callback=='function'&&callback();
			}
		}else{
			elt.onreadystatechange = function(){
				if(elt.readyState=="complete" || elt.readyState=="loaded"){
					typeof callback=='function'&&callback();
				}
			}
		}
		document.getElementsByTagName("head")[0].appendChild(elt);
	}
}
function ie(){
	if($.browser.msie){
		return parseInt($.browser.version);
	}else{
		return 9999;
	}
}
$(function(){
	if(ie()<=8){
		jQuery.fx.off = true;
	}
	yd_fixed(); //开始预订功能浮层锁定
	yd_minsplus(); //预订浮层，加加减减功能
	fuceng(); //浮层功能初始化
	other(); //其他功能初始化
	outerinit();//层外功能
	//big_banner(".dtl_focusbox",".dtl_crtimg",".dtl_focuslist","dtl_focus_crt"); //轮播图
	//load("/js/free/My97DatePicker/WdatePicker.js");//载入my97日历控件
})