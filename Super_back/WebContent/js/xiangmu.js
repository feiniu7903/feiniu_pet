
var init = function(){
	var xiangmustyle = document.getElementById("xiangmu");
	alert(xiangmustyle);
	xiangmustyle.style.display = "";
	}




//ȡgetPageSize
var getPageSize = function () {
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight
	arrayPageSize = new Array(w,h) 
	return arrayPageSize;
}

//ȡpageScrollTop
var getPageScroll = function (){
	var yScrolltop;
	var xScrollleft;
	if (self.pageYOffset || self.pageXOffset) {
		yScrolltop = self.pageYOffset;
		xScrollleft = self.pageXOffset;
	} else if (document.documentElement && document.documentElement.scrollTop || document.documentElement.scrollLeft ){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
		xScrollleft = document.documentElement.scrollLeft;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
		xScrollleft = document.body.scrollLeft;
	}
	arrayPageScroll = new Array(xScrollleft,yScrolltop) 
	return arrayPageScroll;
}

//leftframe menu
function  showSubMenu(n){
	var thismenu = document.getElementById("submenu"+n).style.display;
	if(thismenu=="none"){document.getElementById("submenu"+n).style.display="block"}else{document.getElementById("submenu"+n).style.display="none"};
	
	}
                    
jQuery(function($){		
//	   var $passed = $("#passed");
//	   var $editPassed = $("#editPassed");
//	   var $nullify = $("#nullify");
	   
	   var $passed = $("#tg_order").find("input[name='passed']");
	   var $passed2 = $("#tg_order").find("input[name='passed2']");
	   var $passed3 = $("#tg_order").find("input[name='passed3']");	
	   var $passed4 = $("#tg_order").find("a[name='passed4']");	
	   var $passed5 = $("#tg_order").find("input[name='passed5']");		
	   var $passed6 = $("#tg_order").find("a[name='passed6']");		
	   var $passed7 = $("#tg_order").find("input[name='passed7']");	
	   var $passed8 = $("#tg_order").find("a[name='passed8']");	
	   var $passed9 = $("#tg_order").find("input[name='passed9']");	
	   var $passed10 = $("#tg_order").find("input[name='passed10']");
	   var $passed11 = $("#tg_order").find("input[name='passed11']");	
	   var $passed12= $("#tg_order").find("a[name='passed12']");	
	   var $passed13 = $("#tg_order").find("input[name='passed13']");	   
	   var $editPassed = $("#tg_order").find("input[name='editPassed']");
	   var $nullify = $("#tg_order").find("input[name='nullify']");
	   var $nullify2 = $("#tg_order").find("input[name='nullify2']");
	   var $nullify3 = $("#tg_order").find("input[name='nullify3']");	
	   var $nullify4 = $("#tg_order").find("input[name='nullify4']");
	   var $nullify5 = $("#tg_order").find("input[name='nullify5']");
	   var $nullify7 = $("#tg_order").find("input[name='nullify7']");
	   var $nullify9 = $("#tg_order").find("input[name='nullify9']");	   
	   
	   var $passedWindow = $("#passedWindow");
	   var $passedWindow2 = $("#passedWindow2");
	   var $passedWindow3 = $("#passedWindow3");
	   var $passedWindow4 = $("#passedWindow4");
	   var $passedWindow5 = $("#passedWindow5");
	   var $passedWindow6 = $("#passedWindow6");
	   var $passedWindow7 = $("#passedWindow7");
	   var $passedWindow8 = $("#passedWindow8");
	   var $passedWindow9 = $("#passedWindow9");
	   var $passedWindow10 = $("#passedWindow10");
	   var $passedWindow11 = $("#passedWindow11");
	   var $passedWindow12 = $("#passedWindow12");
	   var $passedWindow13 = $("#passedWindow13");	   
	   var $editWindow = $("#editWindow");
	   var $nullifyWindow = $("#nullifyWindow");
	   var $nullifyWindow2 = $("#nullifyWindow2");
	   var $nullifyWindow3 = $("#nullifyWindow3");
	   var $nullifyWindow4 = $("#nullifyWindow4");	
	   var $nullifyWindow5 = $("#nullifyWindow5");
	   var $nullifyWindow7 = $("#nullifyWindow7");
	   var $nullifyWindow8 = $("#nullifyWindow8");
	   var $nullifyWindow9 = $("#nullifyWindow9");	   
	   var $popDiv = $("#popDiv");		
	   var $bg = $("#bg");		   
	   
	   
	   var $windows = $(window);
	   
	   $passed.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow.fadeIn("fast");
							  
							  
							  var ctop = ($windows.height() - $passedWindow.height())/2;
							  var cleft = ($windows.width() - $passedWindow.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow input[name='closebtn']").click(function(){$passedWindow.fadeOut("fast")});

	   $passed2.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow3.fadeIn("fast");							  
							  
							  
							  var ctop = ($windows.height() - $passedWindow3.height())/2;
							  var cleft = ($windows.width() - $passedWindow3.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow3.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow3 input[name='closebtn']").click(function(){$passedWindow3.fadeOut("fast")});
	   
	   $passed3.click(function(){
							  $editWindow.fadeIn("fast");
							  $nullifyWindow.fadeIn("fast");
							  $passedWindow2.fadeIn("fast");
							  							  
							  var ctop = ($windows.height() - $passedWindow2.height())/2;
							  var cleft = ($windows.width() - $passedWindow2.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow2.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow2 input[name='closebtn']").click(function(){$passedWindow2.fadeOut("fast")});	
	   
	   $passed4.click(function(){
							  $editWindow.fadeIn("fast");
							  $nullifyWindow.fadeIn("fast");
							  $passedWindow4.fadeIn("fast");
							  							  
							  var ctop = ($windows.height() - $passedWindow4.height())/2;
							  var cleft = ($windows.width() - $passedWindow4.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow4.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow4 input[name='closebtn']").click(function(){$passedWindow4.fadeOut("fast")});	
	   
	   $passed5.click(function(){
							  $editWindow.fadeIn("fast");
							  $nullifyWindow.fadeIn("fast");
							  $passedWindow5.fadeIn("fast");
							  							  
							  var ctop = ($windows.height() - $passedWindow5.height())/2;
							  var cleft = ($windows.width() - $passedWindow5.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow5.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow5 input[name='closebtn']").click(function(){$passedWindow5.fadeOut("fast")});	

	   $passed6.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow6.fadeIn("fast");
							  							  
							  var ctop = ($windows.height() - $passedWindow6.height())/2;
							  var cleft = ($windows.width() - $passedWindow6.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow6.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow6 input[name='closebtn']").click(function(){$passedWindow6.fadeOut("fast")});	

	   $passed7.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow7.fadeIn("fast");
							  							  
							  var ctop = ($windows.height() - $passedWindow7.height())/2;
							  var cleft = ($windows.width() - $passedWindow7.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow7.css({"top":ctop + "px","left":cleft + "px"})
							  });
	   $("#passedWindow7 input[name='closebtn']").click(function(){$passedWindow7.fadeOut("fast")});	
	   
	   $editPassed.click(function(){
							  $passedWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");							  
							  $editWindow.fadeIn("fast")
								  
							  var ctop = ($windows.height() - $editWindow.height())/2;
							  var cleft = ($windows.width() - $editWindow.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $editWindow.css({"top":ctop + "px","left":cleft + "px"})
								  });
	   $("#editWindow input[id='closebtn']").click(function(){$editWindow.fadeOut("fast")});
	   $("#editWindow input[name='closebtn']").click(function(){
															  $editWindow.fadeOut("fast");
															  $bg.css("display","none");
															  });
	   
	   $nullify.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
							   $nullifyWindow.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow input[id='closebtn']").click(function(){$nullifyWindow.fadeOut("fast")});
	   
	   $nullify2.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeIn("fast");
							   $nullifyWindow2.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow2.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow2.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow2.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow2 input[name='closebtn']").click(function(){$nullifyWindow2.fadeOut("fast")});
	   
	   $nullify3.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
							  $nullifyWindow2.fadeIn("fast");
                              $nullifyWindow3.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow3.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow3.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow3.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow3 input[name='closebtn']").click(function(){$nullifyWindow3.fadeOut("fast")});	

	   $nullify4.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");							  
                              $nullifyWindow3.fadeOut("fast");					  
                              $nullifyWindow4.fadeIn("fast");							  
							  $nullifyWindow2.fadeIn("fast");	
							  
							  var ctop = ($windows.height() - $nullifyWindow4.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow4.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow4.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow4 input[name='closebtn']").click(function(){$nullifyWindow4.fadeOut("fast")});
	   
	   $nullify5.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $nullifyWindow3.fadeIn("fast");							  
                              $nullifyWindow4.fadeIn("fast");							  
                              $nullifyWindow5.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow5.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow5.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow5.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow5 input[name='closebtn']").click(function(){$nullifyWindow5.fadeOut("fast")});
	   $("#nullifyWindow5 input[name='closebtn']").click(function(){
															  $editWindow.fadeOut("fast");
															  $bg.css("display","none");
															  });	   
	   
	   $nullify7.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $nullifyWindow7.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow7.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow7.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow7.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow7 input[name='closebtn']").click(function(){$nullifyWindow7.fadeOut("fast")});	

	   $nullify9.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $nullifyWindow9.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $nullifyWindow9.height())/2;
							  var cleft = ($windows.width() - $nullifyWindow9.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $nullifyWindow9.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#nullifyWindow9 input[name='closebtn']").click(function(){$nullifyWindow9.fadeOut("fast")});	
	   
	   $passed8.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $passedWindow8.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $passedWindow8.height())/2;
							  var cleft = ($windows.width() - $passedWindow8.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow8.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow8 input[name='closebtn']").click(function(){$passedWindow8.fadeOut("fast")});	
	   
	   $passed9.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $passedWindow9.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $passedWindow9.height())/2;
							  var cleft = ($windows.width() - $passedWindow9.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow9.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow9 input[name='closebtn']").click(function(){$passedWindow9.fadeOut("fast")});	
	   
	   $passed10.click(function(){
							  $passedWindow.fadeOut("fast");
							  $editWindow.fadeOut("fast");
                              $passedWindow10.fadeIn("fast")
							   
							  var ctop = ($windows.height() - $passedWindow10.height())/2;
							  var cleft = ($windows.width() - $passedWindow10.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow10.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow10 input[name='closebtn']").click(function(){$passedWindow10.fadeOut("fast")});	
	   
	   
	   $passed11.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow11.fadeIn("fast");
							   
							  var ctop = ($windows.height() - $passedWindow11.height())/2;
							  var cleft = ($windows.width() - $passedWindow11.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow11.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow11 input[name='closebtn']").click(function(){$passedWindow11.fadeOut("fast")});	
	   
	   
	   $passed12.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow12.fadeIn("fast");
							   
							  var ctop = ($windows.height() - $passedWindow12.height())/2;
							  var cleft = ($windows.width() - $passedWindow12.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow12.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow12 input[name='closebtn']").click(function(){$passedWindow12.fadeOut("fast")});	
	   
	   $passed13.click(function(){
							  $editWindow.fadeOut("fast");
							  $nullifyWindow.fadeOut("fast");
							  $passedWindow13.fadeIn("fast");
							   
							  var ctop = ($windows.height() - $passedWindow13.height())/2;
							  var cleft = ($windows.width() - $passedWindow13.width())/2;
							  if(ctop<=0){ctop = 0 + $windows.scrollTop()}else{ctop=parseInt(ctop + $windows.scrollTop())};
							  if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
							 $passedWindow13.css({"top":ctop + "px","left":cleft + "px"})
							   });
	   $("#passedWindow13 input[name='closebtn']").click(function(){$passedWindow13.fadeOut("fast")});		   
	   
})