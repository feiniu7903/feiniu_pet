;(function($){
	$.fn.wb_ajaxTab = function(options){
		var opts = $.extend({}, $.fn.wb_ajaxTab.defaults, options)

		$this = $(this);
		
		var	$tabs	= $this.find(opts._tabs).find('li'),			//父级tab标签
			$pane	= $this.find(opts._panes),						//父级容器
			$inTabs	= $this.find(opts._inTabs).find('a'),			//子集tab标签
			$inPane	= $this.find(opts._inPane);						//产品列表容器
			
		var	$moreTab = $this.find(opts._moreTabs),					//更多的隐藏父级tab标签
			$morePanel 	= $this.find(opts._morePanel);				//更多城市调用面板
		
		
		$morePanel.hover(function(){								//更多城市调用面板 悬停效果
				$(this).addClass('moreCityHover');
			},
			function function_name (argument) {
				$(this).removeClass('moreCityHover');
		});
		var $morePanelBtn = $morePanel.find("a");
		$morePanelBtn.click(function(){
			var _cityName = $(this).text();
			
			var _rightTabIndex;					//存储正确父级选项卡的index值
			
			$tabs.each(function(index){						//筛选与点击按钮匹配的父级选项卡
				var _tabCityName = $(this).find("a").text();
				if ( _tabCityName === _cityName) {
					_rightTabIndex = $(this).index();
				};
			});
			
			$moreTab.hide();			//所有更多选项卡隐藏
			$tabs.removeClass(opts._currentTab);			//父级标签样式变化
			$tabs.eq(_rightTabIndex).addClass(opts._currentTab);
			$tabs.eq(_rightTabIndex).show();
			$pane.hide();									//父级容器切换
			$pane.eq(_rightTabIndex).show();
			
			var _json = $(this).attr("data-params");		//产品列表容器ajax重写
			ajaxInsert(_json);
		});
		
		
		//$tabs.eq(0).addClass(opts._currentTab);
		$pane.hide();
		$pane.eq(0).show();
		
		
		$tabs.each(function(index){							//_tabs 父级标签点击事件
			$(this).find("a").click(function(){
				$tabs.removeClass(opts._currentTab);			//父级标签样式变化
				$tabs.eq(index).addClass(opts._currentTab);
				$pane.hide();									//父级容器切换
				$pane.eq(index).show();
				
				var _json = $(this).attr("data-params");		//产品列表容器ajax重写
				ajaxInsert(_json);
			});
		});
		
		
		$inTabs.each(function(){						//_inTabs 子级标签点击事件
			$(this).click(function() {
				$(this).parents(opts._inTabs).find("li").removeClass(opts._inCurrentTab);		//子级标签样式更改
				$(this).parent("li").addClass(opts._inCurrentTab);
				
				var _json = $(this).attr("data-params");
				ajaxInsert(_json);
			});
		});
		
		function ajaxInsert (_params) {				// ajax 请求并重写 _inPane 内的数据  _params为json格式的字符串
			var	 _url = opts.url;
			var $json = eval("(" + _params + ")");
			$.post(_url, $json,
				function(data){
					$inPane.empty();
					$inPane.append(data);			
			});
		};
		
	};
	
		//wb_tab Defaults Define
	$.fn.wb_ajaxTab.defaults = {
		_tabs			: ".tabs",				//Tabs Container Class Name Define
		_panes			: ".panes > ul",		//Panes Container Class Name Define
		_inTabs			: ".inTabs",			//InTabs Container Class Name Define
		_inPane			: ".inPane",
		_currentTab		: "current",
		_inCurrentTab	: "inCurrent",
		
		_moreTabs	: ".moreCityText",		//更多选项卡class
		_morePanel	: ".moreCity",			//更多选项卡面板
		
		
		//ajax Params
		
		url			: "http://localhost/wdb/ajax"
		
	};
})(jQuery);
