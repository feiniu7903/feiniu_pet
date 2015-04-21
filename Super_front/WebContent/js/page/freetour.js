$(document).ready(function(){
		
			$('.hot_a').one('click',function(event){
				$(this).attr('href',encodeURI($(this).attr('href')));
			});
			$(".row1").wb_ajaxTab({
				url			: "http://www.lvmama.com/freetour/homePage/getAjaxProductList.do"
			});
			$('#searchKey').recommend_regional();
			$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/freetourSearch!getAutoCompletePlace.do"});

		$('.hot_a').one('click',function(event){
			$(this).attr('href',encodeURI($(this).attr('href')));
		});
	
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
				window.open('http://www.lvmama.com/search/freetour-to-'+_searchKeyVal+'.html');
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
							window.open('http://www.lvmama.com/search/freetour-to-'+_searchKeyVal+'.html');
							return false;
						}else{
							$('#searchKey').attr('style','border:1px solid red;');
							return false;
						}
					}
			});
			$('#moreFree').live('click',function(){
				//alert($('.current a').text());
				//window.location.href="http://www.lvmama.com/search/freetourSearch!freetourSearch.do?routeType=freeness&toDest="+$('.current a').text();
				window.open("http://www.lvmama.com/search/freetour-to-"+$('.tabs:visible>.current a').text()+".html");
			});
			
			//$(".focusImg_p").wb_focusImg();//focusIme Slide Effect
	
			$('.tabs > li:last-child a,.inTabs > li:last-child a,.pane dl:last-child,.moreCity dd:last-child').css('border', 'none');
			$(".scrollLoading").wb_scrollLoading();
			$('.tooltips').wb_tooltip();	//鼠标提示层
		});