 (function($) {
	 
	 $.fn.lvmamaDialog = function(options){
		 var bg=null;
	 var dialog=null;
	 	var myDiv = $(this);
		var options = options || {};
		
		options.zindex=options.zindex||999;
		
		if(options.modal==false){
			myDiv.attr("modal","true")
			 myDiv.css("width",options.width+"px"||800);
		 myDiv.css("height",options.height+"px"||800);
		  myDiv.css("left",(($(document).width())/2-(parseInt(options.width)/2))+"px")
		 if((($(document).height())/2-(parseInt(options.width)/2))<0){
			  myDiv.css("top","10px");
		 } else {
			  myDiv.css("top",(($(document).height())/2-(parseInt(options.height)/2))+"px");
		 }
		} else {
	
		 dialog =$("<div id=\""+myDiv.attr("id")+"_lvmama_dialog\" class=\"dragDiv2\"></div>");
		 dialog.css("width",options.width+"px"||800);
		 dialog.css("height",options.height+"px"||800);
		 dialog.css("z-index",options.zindex+1);
		 dialog.css("display","");
		 dialog.css("margin-top","0px");
		 dialog.css("left",(($(document).width())/2-(parseInt(options.width)/2))+"px")
		 if((($(document).height())/2-(parseInt(options.height)/2))<0){
			  dialog.css("top","10px");
		 } else {
			  dialog.css("top",(($(document).height())/2-(parseInt(options.height)/2))+"px");
		 }
        
		 dialog.hide();
		 bg=$("<div class=\"fullbg\" id=\""+myDiv.attr("id")+"_bg\"></div>");
		 bg.css("z-index",options.zindex);
		   var bH = $(document).height();
           var bW = $(document).width();
           bg.css({ width: bW, height: bH});

		 var closeButton = $("<button type=\"button\" class=\"right-button02\" style=\"float:right;cursor:pointer;margin:0px;\">关闭</button><br/>")
		 var content = $("<div id=content style=\"position:absolute;margin-top:20px;\"></div>")
		 
		 dialog.append(closeButton);
         closeButton.bind("click",function(){
        	   bg.hide();
		 	   dialog.hide();
		 	   options.close(myDiv);
		 	    $("select").each(function(){$(this).show();});
           });
         //bg.append(iframe);
         //var iframe = $("<IFRAME id=\"frame\" width=\"100%\" height=\"100%\" style=\"position:absolute;z-index:"+(options.zindex+2)+";\" mce_style=\"position:absolute;z-index:1; background-color:Blue; display:none;\" frameborder=\"0\"></IFRAME> ")
         //dialog.append(iframe);
		 dialog.append(content);
		 //$("body").append(iframe);
		$("body").append(bg);
		 $("body").append(dialog);
		 var pagestyle = function() {
           var bH = $(document).height();
           var bW = $(document).width();
           bg.css({ width: bW, height: bH});
           dialog.css("left",(($(document).width())/2-(parseInt(options.width)/2))+"px")
		 if((($(document).height())/2-(parseInt(options.height)/2))<0){
			  dialog.css("top","10px");
		 } else {
			  dialog.css("top",(($(document).height())/2-(parseInt(options.height)/2))+"px");
		 }
        
        }
		 
        //注册窗体改变大小事件  
        $(window).resize(function(){
        	pagestyle();
        	
        });
        
        
        var pagestyle = function() {
           var bH = $(document).height();
           var bW = $(document).width();
          bg.css({ width: bW, height: bH});
        }
		 }
	 }
	 
	 $.fn.openDialog = function(){
		 var h = $(document).scrollTop();
		 //alert($(document).offset().top);
		// $("select").each(function(){$(this).hide();});
		 var o =$(this);
		 if(o.attr("modal")=="true"){
		  	o.css("top",parseInt(h)+"px");
		  	$("select").each(function(){$(this).hide();});
			 o.find("select").each(function(){$(this).show();});
			 o.show();
		 } else {
		 var d= $("#"+o.attr("id")+"_lvmama_dialog")
		 
		 if(h!=0){
		 
		 d.css("top",parseInt(h)+"px");
		 }

		 d.find("#content").html(o);
		 $("select").each(function(){$(this).hide();});
		 d.find("select").each(function(){$(this).show();});
		 //d.append(o);
		 
		 
		 d.show();
		 var bH = $(document).height();
         var bW = $(document).width();
     
		 var b= $("#"+o.attr("id")+"_bg")
		 b.css({ width: bW, height: bH+200});
		 b.show();

		 
		 }
		
	 }
	 $.fn.closeDialog = function(){
		 $("select").each(function(){$(this).show();});
		  var o =$(this);
		  var d= $("#"+o.attr("id")+"_lvmama_dialog")
		   var b= $("#"+o.attr("id")+"_bg")
		   d.hide();
		   b.hide();
	 }
	 

 
 })(jQuery); 
 
 
 function openWin(url,width,height){
	window.open(url,"_blank", "height="+height+", width="+width+", top=0, left=255, toolbar=no,menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
 }
 
 function closeDetailDiv(div){
	document.getElementById(div).style.display = "none";
	if(document.getElementById("bg"))
		document.getElementById("bg").style.display = "none";
	$("#"+div).closeDialog();
}
 