// JavaScript Document
 function Lvmm_pop(opt){
	var init={
		popW:600,
        popTop:0,
		popWarpClass:"common_pop", // pop box
		popStyleClass:"red_common_pop", // yellow_common_pop
		popClose:"common_pop_close", // close
		pop_tit:"提示",
		pop_str: "内容",
		pop_btn:true, // false
		popBtnSure:"common_btn red_common_btn", // sure yellow_common_btn
		popBtnReset:"common_btn grey_common_btn", // cancel
		btnevt:"", // surebtn - function
		popCover:"popCover", // layerover
		popBtnHtml:""
	 }
	 this.init=$.extend(init, opt);
 }//init
 Lvmm_pop.prototype={
	common_showPop:function(){
		var _this=this, _init=_this.init;
		var popCoverHtml="<div class='"+_init.popCover+"'></div>";
		var _popBtnHtml=_init.popBtnHtml;
		_init.pop_btn?1==1:_popBtnHtml="";	
		var crtID=new Date().getTime();	   
		var popHtml="<div id='common_pop_ID"+crtID+"' class='"+_init.popWarpClass+" "+_init.popStyleClass+"'>\
	  		   <span class='"+_init.popClose+" common_opt_closeBtn'></span>\
	  		   <strong class='common_pop_tit'>"+_init.pop_tit+"</strong>\
	  		   <div class='common_pop_cont'>\
	  			   <div class='common_pop_txt '>"+_init.pop_str+"</div>"+_popBtnHtml+"\
	  		   </div>\
	  		</div>";
		_init.popCover!=""?popHtml=popCoverHtml+popHtml:1==1;
		$("body").append(popHtml);	
		_this.common_pop_posCenter(crtID,_init.popCover);
		return crtID;
	}, //生成
	common_pop_posCenter:function(crtID,popCover){
		 var _this=this, _init=_this.init;
		 var $pop = $("#common_pop_ID"+crtID);
		 var h=$(window).scrollTop()+($(window).height()-$pop.height())/2 + _init.popTop;
		 $(window).height()>$pop.height()?h=h:h=$(window).scrollTop();
		 if(h==0){
			 h+=150;
		 }
		 $pop.css({width : parseInt(_init.popW)+'px'}).css({
			display:"block",
			left : "50%",
			marginLeft: -$pop.outerWidth(false)/2,
			top : h+'px'
		 });
		 
		 if(_init.popCover!=""){
		   var dh=document.body.scrollHeight;
		   var wh=window.screen.availHeight;
		   var yScroll;
		   dh>wh?yScroll =dh:yScroll = wh; 
		   (yScroll>h+$pop.height())?yScroll=yScroll:yScroll=h+$pop.height();
		   $pop.prev().css({"display":"block","height":yScroll});
		 }
		 this.common_pop_close($pop);
   },//定位
   common_pop_close:function($pop){
	     var _this=this, _init=_this.init;
		 $pop.find(".common_opt_closeBtn").on("click",function(){
			 $pop.hide();
			 if(_init.popCover!=""){
	  			$pop.prev().hide();
	  		 } 
		 })
		 $pop.find(".common_opt_sureBtn").on("click",function(){
			 if(_init.btnevt!=""){
				try{
				   function a(b){
					   b();
				   } 
				   a(_init.btnevt);
				}catch(e){
				}
			}
		 })
   }//关闭
 }