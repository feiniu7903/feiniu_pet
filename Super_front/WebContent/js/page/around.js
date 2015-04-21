<!-- 
var bd_cpro_rtid="nHDvns";
//-->
	$(function(){
		var $fromDestSelect = $("#fromDestSelect");
		$fromDestSelect.empty();
		$.ajax({
			url:'http://www.lvmama.com/around/homePage/getAjaxFromPlaceList.do',
			type:'GET',
			dataType: 'json',
			success: function(fromPlaces) {
				$.each(fromPlaces, function(i,fromPlace){
					var $opt = $("<option></option>");
					if ($("#fromPlaceId").val()==fromPlace.fromPlaceId) {
						$opt.attr("selected", true);
					}
					$opt.val(fromPlace.fromPlaceId).text(fromPlace.fromPlaceName).attr("code",fromPlace.fromPlaceCode).appendTo($fromDestSelect);
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

		$(".row2").wb_depAjaxTab({									//选择出发地 切换选项卡ajax
			url: "http://www.lvmama.com/around/homePage/getAjaxProductList.do"
		});
		
		$('#searchKey').recommend_regional();

		$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/aroundSearch!getAutoCompletePlace.do"});
		
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
				window.open('http://www.lvmama.com/search/around-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
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
							//$('#searchForm').submit();
							window.open('http://www.lvmama.com/search/around-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
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
				$selectForm.attr({method:'post',action:'http://www.lvmama.com/around/'});
				$selectForm.submit();
			});	
			
		$('#moreAround').live('click',function(){
		
				//window.location.href="http://www.lvmama.com/search/aroundSearch!aroundSearch.do?routeType=route&toDest="+$('#fromDestSelect').find('option:selected').text();
				var from;
				var to;
					from=$('.tabs:visible').attr('from-to');
					to=$('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')!=-1?"":$('.tabs:visible>.current>a').text();//为其它时,无目的地
				window.open(encodeURI("http://www.lvmama.com/search/around-from-"+from+"-to-"+to+"-route.html"));
			});	
			
		$('#travelAround').live('click',function(){
		
				//window.location.href="http://www.lvmama.com/search/aroundSearch!aroundSearch.do?routeType=route&toDest="+$('#fromDestSelect').find('option:selected').text();
				var from;
				var to;
					from=$('.tabs:visible').attr('from-to');
					to=$('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')!=-1?"":$('.tabs:visible>.current>a').text();//为其它时,无目的地
				window.open(encodeURI("http://www.lvmama.com/search/around-from-"+from+"-to-"+to+"-route.html"));
			});		
			
		$('#jointAround').live('click',function(){
		
				//window.location.href="http://www.lvmama.com/search/aroundSearch!aroundSearch.do?routeType=route&toDest="+$('#fromDestSelect').find('option:selected').text();
				var from;
				var to;
					from=$('.tabs:visible').attr('from-to');
					to=$('.tabs:visible>.current>a').attr('data-params').indexOf('OTHER')!=-1?"":$('.tabs:visible>.current>a').text();//为其它时,无目的地
				window.open(encodeURI("http://www.lvmama.com/search/around-from-"+from+"-to-"+to+"-route.html"));
			});		
			
		
		$(".scrollLoading").wb_scrollLoading();						//图片延迟加载
						
		$('.tabs > li:last-child a,.inTabs > li:last-child a,.pane dl:last-child,.moreCity dd:last-child').css('border','none');

		$('.row1 ul li').mouseover(function(){
			$(this).css('background','#f6f6f6');
		});
		$('.row1 ul li').mouseout(function(){
			$(this).css('background','none');
		});
	});