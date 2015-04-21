;(function($){
	$.fn.wb_tooltip = function(options){
		var opts = $.extend({}, $.fn.wb_tooltip.defaults, options);
		
		$root	= $(this);
		
		$root.each(function(index) {
			$twig = $(this);
			
			string		= $twig.attr("title");				//获取title内容
			$twig.attr("title","");							//清空原本title值，避免默认title样式出现
			
			offset 		= $twig.offset();					//获取提示图标位置
			width		= $twig.outerWidth();				//获取提示图标宽高
			_left		= offset.left + width + 7;			//设置tips容器位置
			_top		= offset.top - 8;
			domNum		= document.getElementsByTagName('*').length;		//获取dom对象数，设置tips容器z-index值，以免被遮挡
			
			$('body').append('<div class="tipsCon" style="position: absolute; display: none; top:'+ _top +'px; left:'+ _left +'px; z-index: ' + domNum + ';"></div>');
			$('.tipsCon').eq(index).html(string);								//插入tip内容
			$('.tipsCon').append('<span class="tipsCorner"></span>');			//tip容器三角箭头
			
			$twig.hover(function() {
				$('.tipsCon').eq(index).show();
			}, function() {
				$('.tipsCon').eq(index).hide();
			});
			
			$('.tipsCon').eq(index).hover(function() {
				$(this).show();
			}, function() {
				$(this).hide();
			});
		});
		
	}
	$.fn.wb_tooltip.defaults = {
		
	};
})(jQuery);
