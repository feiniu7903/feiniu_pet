$(document).ready(function() {
            $('.hot_a').one('click',function(event){
                $(this).attr('href',encodeURI($(this).attr('href')));
            });
			//$(".focusImg_p").wb_focusImg();//focusIme Slide Effect
			$('#searchKey').recommend();
			$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/ticketSearch!getAutoCompletePlace.do"});

			$(".row2").wb_tab();
			$(".commentTabCon").wb_tab({
				_tabs		: ".inTabs",		//Tabs Container Class Name Define
				_panes		: ".commentTabPane",		//Panes Container Class Name Define
				_currentTab	: "inCurrent"
			});
			$(".scrollLoading").wb_scrollLoading();
			
			//搜索按钮的点击功能
			$('#searchButton').click(function() {
				var $searchKey = $('#searchKey');
				var searchKeyVal = $.trim($('#searchKey').val());
				if(searchKeyVal==''||searchKeyVal==null||searchKeyVal=='中文/拼音'){
					$searchKey.attr('style','border:1px solid red;');
					$searchKey.click();
					return false;
				}
				var _searchKeyVal = $.trim($('#searchKey').val()).replaceToLower();
				$searchKey.val(_searchKeyVal);
				if(_searchKeyVal==''||_searchKeyVal==null||_searchKeyVal=='中文拼音'){
					$searchKey.attr('style','border:1px solid red;');
					$searchKey.click();
					return false;
				} 
				window.open('http://www.lvmama.com/search/ticket-kw-'+_searchKeyVal+'.html');
				return false;
				//$('#searchForm').submit();
			});
			//输入框值为空时,不能用ENTER提交
			$('#searchKey').bind('keydown',function(event){
				$('#searchKey').attr('style','');
				var _searchKeyVal = $.trim($('#searchKey').val()).replaceToLower();
					if(event.keyCode==$.ui.keyCode.ENTER){
						var value = $.trim($(this).val()).replaceToLower();
						$(this).val(value);
						if(value.length>0){
							//$('#fromDest').val($('#fromDestSelect').find('option:selected').text());
							//$('#searchForm').submit();
							window.open('http://www.lvmama.com/search/ticket-kw-'+_searchKeyVal+'.html');
							return false;
						}else{
							$('#searchKey').attr('style','border:1px solid red;');
							return false;
						}
					}
			});
			$('#a-select-more').on('click',function(event){
				var _$row1 = $('.row1');
				var _sel = _$row1.find('.current').find('a').text();  
				var _inSel = _$row1.find('.inTabs:visible').find('.inCurrent').find('a').text();
				var _keyword = (_inSel==''||_inSel=='全部')?_sel:_inSel;
				window.open('http://www.lvmama.com/search/ticket-kw-'+_keyword+'.html');
			});
			
			$(".paneTab").each(function(index) {
				$('.paneTab').css('background', '#fff');
				$(this).click(function(){
					if ($(this).hasClass('paneTabShow')) {
						$(this).removeClass('paneTabShow');
					} else{
						$('.paneTab').removeClass('paneTabShow');
						$(this).addClass('paneTabShow');
					};
				});
			});
			$('.paneTab').mouseover(function() {
				if($(this).hasClass('paneTabShow')) {
					$(this).css('background', '#fff');
				} else {
					$(this).css('background', '#f6f6f6');
				}
			})
			$('.paneTab').mouseout(function() {
				$('.paneTab').css('background', 'none');
			});
			
			
			$(".row1").wb_ajaxTab({
				url: "http://www.lvmama.com/ticket/homePage/getAjaxProductList.do"
			});
		});