/*
 * jquery.wb_focusImg.js
 * by Abram Wang  http://www.abramstudio.com
 * 2011-11-11 v0.1
 */
;(function($){
	$.fn.wb_focusImg = function(options){

		var _opts = $.extend({}, $.fn.wb_focusImg.defaults, options);
		var num = 1,
			lastNum	= 0;
				
		$this = $(this);
		
		$this.prepend("<ul class="+_opts._tabsBg+"><li></li><li></li><li></li><li></li><li></li><li></li></ul>");	//Add Opacity Background Layer
		
		var	$tab	= $this.find(_opts._tabs).find("li"),
			$tabBg	= $this.find("."+_opts._tabsBg).find("li"),	//Find Opacity Background Layer
			$pane	= $this.find(_opts._panes);
			$parent	= $(_opts._parent);
		
		var indexNum = $this.find(_opts._panes+":last-child").index();
		
		//console.error($this.parent().is('div'));
		$this.parent().appendTo($parent);
		$tab.eq(0).addClass(_opts._currentTab);
		$tabBg.eq(0).addClass(_opts._currentTab);
		$pane.hide();
		$pane.eq(0).show();
		
		/*click slide function*/
		$tab.each(function(index){
			$(this).click(function(){
				if($(this).attr("class") !== _opts._currentTab){ //where this tab isn't current, run the function
					//img button operate
					$tab.eq(index).addClass(_opts._currentTab).siblings().removeClass(_opts._currentTab);
					$tabBg.eq(index).addClass(_opts._currentTab).siblings().removeClass(_opts._currentTab);
					
					$pane.eq(lastNum).css("z-index", "1").show().siblings().hide().css("z-index", "0");
					$pane.eq(index).css("z-index", "2").fadeIn(1000);
					lastNum = index;
					//fadeIn anima over
					
					if( index == indexNum){	//make the auto slide function operate the same slide
						num = 0;
					} else {
						num = index + 1;
					}					
				};
				
			});
		});
		/*auto slide function*/
		
		function imgScroll(){
			$pane.eq(lastNum).css("z-index", "1").show().siblings().hide().css("z-index", "0");
			$pane.eq(num).css("z-index", "2").fadeIn(1000);
			
			//img button operate
			$tab.eq(num).addClass(_opts._currentTab).siblings().removeClass(_opts._currentTab);
			//img button background operate
			$tabBg.eq(num).addClass(_opts._currentTab).siblings().removeClass(_opts._currentTab);
			
			
			lastNum = num;
			//fadeIn anima over
			
			num += 1;
			if(num>indexNum) num=0;
		};
		var anima = setInterval(imgScroll,5000);				//When mouse over this lay, stop anima
		$this.hover(
			function () {
				clearInterval(anima);
			},
			function () {
				anima = setInterval(imgScroll,5000);
			}
		);
		//auto slide over
	};
	
	//wb_tab Defaults Define
	$.fn.wb_focusImg.defaults = {
		_tabs		: ".imgButtons_p",		//Tabs Container Class Name Define
		_tabsBg		: "imgButtonsBg_p",		//Opacity Background Class Name Define
		_panes		: ".imgContainers_p > .imgContent_p",		//Panes Container Class Name Define
		_parent		: ".imgFocus",		//Panes Container Class Name Define
		_currentTab	: "current"

	};
	
})(jQuery);