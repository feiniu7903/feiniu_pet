// JavaScript Documentspzhe


//$('.tishiBtn1').click(function(){ 
//
//	alert("很抱歉，查询余额/消费记录功能暂时还没有开放");
//	})	
//$('.tishiBtn2').click(function(){ 
//
//	alert("很抱歉，查询/延长有效期功能暂时还没有开放");
//	})	

$('.lvyouBtn').click(function(){ 
	$('.centerBox1').hide();
	$('.centerBox2').show();
	});
$('.lvyouBtn2').click(function(){ 
	$('.centerBox2').hide();
	$('.centerBox1').show();
	})				


$('.navList li').click(function(){ 
	var num = $(this).index();
	$(this).addClass('nav_li').siblings().removeClass('nav_li');
	$('.sanjiao').hide();
	$(this).find('.sanjiao').show();
	$(this).parents('.nrBox_qh').find('.frameBox1').eq(num).show().siblings('.frameBox1').hide();
	});
	
$(function(){ 
	$('.btn1').click(function(){ 
		tanchu();
	});
	$('.closebtn').click(function(){ 
		guanbi();
		$('.cxjgbdBox').hide();
	});	
	function tanchu(){ 
		var _scrolltop = $(document).scrollTop()+200;
		var height_w =$(document).height();
		var width = $('.PopBox').width();
		$('.PopBox').css({'margin-left':-width/2});
		$('.pop_body_bg').css({'height':height_w,'width':$(document.body).width()}).show();
		$('.PopBox').show().css({'top':_scrolltop});
	};
	function guanbi(){ 
		$('.pop_body_bg,.PopBox').hide();
	};
});

$('.cxbtnbd').click(function(){ 
	$('.cxjgbdBox').show();
	});

$(function(){ 
	$('.btn3').click(function(){ 
		tanchu();
	});
	$('.closebtn').click(function(){ 
		guanbi();
		$('.cxjgbdBox').hide();
	});	
	function tanchu(){ 
		var _scrolltop = $(document).scrollTop()+200;
		var height_w =$(document).height();
		var width = $('.PopBox1').width();
		$('.PopBox1').css({'margin-left':-width/2});
		$('.pop_body_bg').css({'height':height_w,'width':$(document.body).width()}).show();
		$('.PopBox1').show().css({'top':_scrolltop});
	};
	function guanbi(){ 
		$('.pop_body_bg,.PopBox1').hide();
	};
});
$(function(){ 
	$('.ycyouxiaoqiBtn').click(function(){ 
		$('.pop_body_bg,.PopBox3').hide();
		tanchu();
	});
	$('.closebtn').click(function(){ 
		guanbi();
	});	
	function tanchu(){ 
		var _scrolltop = $(document).scrollTop()+200;
		var height_w =$(document).height();
		var width = $('.PopBox2').width();
		$('.PopBox2').css({'margin-left':-width/2});
		$('.pop_body_bg').css({'height':height_w,'width':$(document.body).width()}).show();
		$('.PopBox2').show().css({'top':_scrolltop});
	};
	function guanbi(){ 
		$('.pop_body_bg,.PopBox2').hide();
	};
});
$(function(){ 
	$('.btn2').click(function(){ 
		tanchu();
	});
	$('.closebtn').click(function(){ 
		guanbi();
	});	
	function tanchu(){ 
		var _scrolltop = $(document).scrollTop()+200;
		var height_w =$(document).height();
		var width = $('.PopBox3').width();
		$('.PopBox3').css({'margin-left':-width/2});
		$('.pop_body_bg').css({'height':height_w,'width':$(document.body).width()}).show();
		$('.PopBox3').show().css({'top':_scrolltop});
	};
	function guanbi(){ 
		$('.pop_body_bg,.PopBox3').hide();
	};
});





