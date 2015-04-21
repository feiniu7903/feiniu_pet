jQuery.md=function(opt){
 var modalObj=opt.modal;
 var timeout=opt.time;
 var url=opt.url;
 var zIndex=99;
 var thW,thH;

 bheight = function(){
  if ($.browser.msie && $.browser.version < 7) {
    var scrollHeight = Math.max(
	  document.documentElement.scrollHeight,
	  document.body.scrollHeight
    ); 
    var offsetHeight = Math.max(
	  document.documentElement.offsetHeight,
	  document.body.offsetHeight
    );

    if (scrollHeight < offsetHeight) {
	  return $(window).height();
    } else {
	  return scrollHeight;
    }
  } else {
    return $(document).height();
  }
}
 bwidth = function() {
   if ($.browser.msie && $.browser.version < 7) {
     var scrollWidth = Math.max(
	 document.documentElement.scrollWidth,
	 document.body.scrollWidth
   );
   var offsetWidth = Math.max(
	 document.documentElement.offsetWidth,
	 document.body.offsetWidth
   );

   if (scrollWidth < offsetWidth) {
	 return $(window).width();
   } else {
	return scrollWidth;
   }
  } else {
    return $(document).width();
  }
}
 $(modalObj).css({display:"block","z-index":zIndex + 1});
  if(url!=undefined){
	$(modalObj).text("正在加载...");
	thW=150;
	thH=30;
	$(modalObj).css({top : w()[0] , left : w()[1] });
	$(modalObj).load(url,function(){
	    thW=$(modalObj).width();
	    thH=$(modalObj).height();
		g();
     }); 
 }
 else{
	thW=$(modalObj).width();
	thH=$(modalObj).height();
	g();
 }
 function g(){	
    $(modalObj).css({top : w()[0] , left : w()[1] });
	$(window).resize(bg); 
	$(window).scroll(bg);
	if(timeout!=undefined) hideGrey();
 }
 
 function w(){
	var st=document.documentElement.scrollTop;//滚动条距顶部的距离
	var sl=document.documentElement.scrollLeft;//滚动条距左边的距离
	var ch=document.documentElement.clientHeight;//屏幕的高度
	var cw=document.documentElement.clientWidth;//屏幕的宽度
	var objT=Number(st)+(Number(ch)-Number(thH))/2+150;
	var objL=Number(sl)+(Number(cw)-Number(thW))/2+150;
	return [objT,objL];
 }
 function bg(){
 	
	 $(modalObj).css({top : w()[0] , left : w()[1] });
 }
 function closeGrey(){
	$(modalObj).css("display", "none");
 }
 function hideGrey(){
	$(modalObj).fadeOut(timeout,function(){
	});
 }
}
