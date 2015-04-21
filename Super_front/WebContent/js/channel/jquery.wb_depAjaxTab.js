;(function($){
	$.fn.wb_depAjaxTab = function(options){
		var opts = $.extend({}, $.fn.wb_depAjaxTab.defaults, options)

		$this = $(this);
		
		$tabs		= $this.find(opts._tabs);
		$ajaxConPan	= $this.find(opts._ajaxConPan);			//定义ajax返回请求容器对象

		$morePanel	= $this.find(opts._morePanel);
		
		$morePanel.find(".special4").click(function(event) {
			$morePanel.find("ul").show();
			event.stopPropagation();
		});
		$('body').click(function(){
			$morePanel.find("ul").hide();		 
		});

		$morePanel.find("a").each(function(){					//更多目的地面板按钮点击事件
			$(this).click(function(){
				var _tabId	= $(this).attr("data-tabId");
				var _nameString = $(this).text();				//获取当前目的地名称
				
				var $currentTab = $tabs.filter("[data-tabId="+_tabId+"]");
				
				$currentTab.show().siblings().hide();
				$currentTab.find("li").eq(0).addClass(opts._currentTab).siblings().removeClass(opts._currentTab).click();
				
				$morePanel.find("span").replaceWith("<span>"+ _nameString +"</span>");	//替换目的地显示名称
				
				
				var _json = $currentTab.find("a").eq(0).attr("data-params");
				ajaxInsert(_json);
			});
		});
		
		$tabs.find("a").each(function(){						//标签点击事件
			$(this).click(function() {
				$(this).parents(opts._tabs).find("li").removeClass(opts._currentTab);		//标签样式更改
				$(this).parent("li").addClass(opts._currentTab);
				
				//alert($(this).text());
				
				var _json = $(this).attr("data-params");
				
				ajaxInsert(_json);
			});
		});
		
		function ajaxInsert (_params) {				// ajax 请求并重写 _inPane 内的数据  _params为json格式的字符串
			var	 _url = opts.url;
			
			var $json = eval("(" + _params + ")");
			//var $json = JSON.parse(_params);
			
			$.post(_url, $json,
				function(data){
					$ajaxConPan.empty();
					$ajaxConPan.append(data);			
			});
		};
		
	};
	
		//wb_tab Defaults Define
	$.fn.wb_depAjaxTab.defaults = {
		
		_tabs			: ".tabs",				//InTabs Container Class Name Define
		_ajaxConPan		: ".ajaxConPane",		//ajax返回数据容器
		_currentTab		: "current",
		
		
		_morePanel	: ".moreCity",			//更多选项卡面板
		
		
		//ajax Params
		
		url			: "http://localhost/wdb/ajax/destroute/"
		
	};
})(jQuery);
