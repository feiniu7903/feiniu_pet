<!-- 
var bd_cpro_rtid="nHDvns";
//-->
	$(function(){
		var $fromDestSelect = $("#fromDestSelect");
		$fromDestSelect.empty();
		$.ajax({
			url:'http://www.lvmama.com/destroute/homePage/getAjaxFromPlaceList.do',
			type:'GET',
			dataType: 'json',
			success: function(fromPlaces) {
				$.each(fromPlaces, function(i,fromPlace){
					var $opt = $("<option></option>");
					if ($("#fromPlaceId").val()==fromPlace.fromPlaceId) {
						$opt.attr("selected", true);
					}
					$opt.val(fromPlace.fromPlaceId).text(fromPlace.fromPlaceName).attr("code",fromPlace.fromPlaceCode).attr("bid",fromPlace.blockId).appendTo($fromDestSelect);
				});
				$(".row2").find('a[code="'+$fromDestSelect.find('option:selected').attr('code')+'"]').click();
			},
			error: function() {
			}
		});
		
		$('.hot_a').one('click',function(event){
			$(this).attr('href',encodeURI($(this).attr('href')));
		});
		//$(".focusImg_p").wb_focusImg();								//焦点图
		
		
		$('#searchKey').recommend_regional();
		$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/destrouteSearch!getAutoCompletePlace.do"});
		
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
				window.open('http://www.lvmama.com/search/destroute-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
				return false;
				//$('#fromDest').val($('#fromDestSelect').find('option:selected').text());
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
							$('#fromDest').val($('#fromDestSelect').find('option:selected').text());
							window.open('http://www.lvmama.com/search/destroute-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
							//$('#searchForm').submit();
							return false;
						}else{
							$('#searchKey').attr('style','border:1px solid red;');
							return false;
						}
					}
			});
			$('#fromDestSelect').change(function(){
				var val = $(this).val();
				var $selectForm = $('#selectForm');
				$('#fromDest').val($(this).find('option:selected').text());
				$('#fromPlaceCode').val($(this).find('option:selected').attr('code'));
				$('#fromPlaceId').val(val);
				$selectForm.attr('target','');//本页面跳转
				$selectForm.attr({method:'post',action:'http://www.lvmama.com/destroute/'});
				$selectForm.submit();
			});
			
		$('#moreDestroute').live('click',function(){
				var from;
				var to="";
				from=$('.tabs:visible').attr('from-to');
				if($('.tabs:visible').attr('from-to')=="北京"){
					if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
						to="浙江";//华东默认到浙江
					}
					if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
						to="DONGBEI";//东北默认到哈尔滨
					}
				}
				if ($('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')==-1) {
						to=$('.tabs:visible>.current>a').text();
					}
				window.open(encodeURI("http://www.lvmama.com/search/destroute-from-"+from+"-to-"+to+"-route.html"));
			});
		$('#teamDestroute').live('click',function(){
				var from;
				var to;
					from=$('.tabs:visible').attr('from-to');
					if($('.tabs:visible').attr('from-to')=="北京"){
						if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
							to="浙江";//华东默认到浙江
						}
						if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
							to="DONGBEI";//东北默认到哈尔滨
						}
					}
					if ($('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')==-1) {
							to=$('.tabs:visible>.current>a').text();
					}
				window.open(encodeURI("http://www.lvmama.com/search/destroute-from-"+from+"-to-"+to+"-route.html"));
			});	
		$('#freeDestroute').live('click',function(){
				var from;
				var to;
					from=$('.tabs:visible').attr('from-to');
					if($('.tabs:visible').attr('from-to')=="北京"){
						if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
							to="浙江";//华东默认到浙江
						}
						if ($('.tabs:visible>.current>a').attr('data-params').indexOf('HUADONG')==-1) {
							to="DONGBEI";//东北默认到哈尔滨
						}
					}
					if ($('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')==-1) {
							to=$('.tabs:visible>.current>a').text();
					}
				window.open(encodeURI("http://www.lvmama.com/search/destroute-from-"+from+"-to-"+to+"-route.html"));
			});		
		
		$(".row2").wb_depAjaxTab({									//选择出发地 切换选项卡ajax
			url: "http://www.lvmama.com/destroute/homePage/getAjaxProductList.do"
		});
		$(".row2").find('a[code="'+$('#fromDestSelect').find('option:selected').attr('code')+'"]').click();
		
		$(".scrollLoading").wb_scrollLoading();						//图片延迟加载
						
		$('.tabs > li:last-child a,.inTabs > li:last-child a,.pane dl:last-child,.moreCity dd:last-child').css('border','none');

	});