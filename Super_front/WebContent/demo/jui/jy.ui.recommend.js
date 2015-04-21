;(function($){  
    $.fn.extend({  
    recommend : function(options){  
        var defaults = {  
            input:"#search",  
			ulselect:".lmmcomplete",
            appendTo: "#address_hot",  
            list:".address_hotlist", 
			paging:".ui-paging", 
            position: {  
                my: "left top",  
                at: "left bottom",  
                collision: "none"  
            }          
        };  
        var options = $.extend(defaults, options);  
		var it=this.length?this:options.input;
        this.each(function(){
				$(it).bind('click',function(event){
							$(options.appendTo).position( $.extend({
								of: it
							}, options.position ));
						var val = $(this).val();
						if(val==''||val=='中文/拼音'){
							$(this).val('');
							setTimeout(function() {
								$(options.appendTo).show(300);
							}, 150 );
						}
						//event.stopPropagation();
				});
				//层内的link点击事件，注意让事件停止冒泡 
				$(options.appendTo).find('.address_hot_abb li').bind('click',function(event){
						$('li', options.appendTo).removeClass('hot_selected');
						$(this).addClass('hot_selected');
						$('.address_hot_adress').css({top:'0',left:'0'}).hide();
						$('.address_hot_adress[type="'+$.trim($(this).text())+'"]').show();
						event.stopPropagation();
					});
				$(options.appendTo).find('.address_hot_adress li a').bind('mouseover',function(event){
							$(this).attr('style','color:#C06;text-decoration:underline;');
							event.stopPropagation();
					}).bind('mouseout',function(event){
							$(this).attr('style','');
							event.stopPropagation();
					}).bind('click',function(event){
							$(it).val($(this).text());
							$(options.appendTo).css({top:'0',left:'0'}).hide();
							event.stopPropagation();
					});
				 
				  
				//点击层外，隐藏这个层。由于层内的事件停止了冒泡，所以不会触发这个事件  
				$(document).click(function(event){  
					$(options.appendTo).css({top:'0',left:'0'});
					$(options.appendTo).position( $.extend({
								of: it
							}, options.position ));                   
					$(options.appendTo).hide();  	
					event.stopPropagation();	  
				});  
  				$(options.paging).find('a').live('click',function(event){
			                var val =  $(this).text()-1;
							val = val==''?0:val;
							var pageNum = 10;
							$(this).parent().find('a').attr('style','');
							$(this).attr('style','background:#999;');
							$(it).attr('page',val);
							$(options.ulselect).find('li').each(function(index, element) {
								$(element).attr('style','display:none;');
								if((index>=val*pageNum)&&(index<(val+1)*pageNum)){
											$(element).attr('style','');
								}
                            });
							event.preventDefault();
					});
					
        });  
    }  
});  
})(jQuery); 