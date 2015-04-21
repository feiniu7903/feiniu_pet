$(document).ready(function(){
	var $fromDestSelect = $("#fromDestSelect");
	$fromDestSelect.empty();
	$.ajax({
		url:'http://www.lvmama.com/abroad/homePage/getAjaxFromPlaceList.do',
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
			
			$(".hScrollable_p").lx_hScrollable();						//图片横向滚动
					
			$('.tabs > li:last-child a,.inTabs > li:last-child a,.pane dl:last-child,.moreCity dd:last-child').css('border','none');
			
			$(".scrollLoading").wb_scrollLoading();						//图片延迟加载
			
			
			$('#searchKey').recommend_regional();
			$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/abroadSearch!getAutoCompletePlace.do"});
				

		$('.tabs > li:last-child a,.inTabs > li:last-child a,.pane dl:last-child,.moreCity dd:last-child').css('border','none');
		
		$(".scrollLoading").wb_scrollLoading();						//图片延迟加载
		
		
		$('#searchKey').recommend_regional();
		$('#searchKey').lmmcomplete({url:"http://www.lvmama.com/search/abroadSearch!getAutoCompletePlace.do"});

				
			$(".row2").wb_depAjaxTab({									//选择出发地 切换选项卡ajax
				url : "http://www.lvmama.com/abroad/homePage/getAjaxProductList.do"

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
				window.open('http://www.lvmama.com/search/abroad-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
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
							window.open('http://www.lvmama.com/search/abroad-from-'+$('#fromDestSelect').find('option:selected').text()+'-to-'+_searchKeyVal+'-route.html');
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
				$selectForm.attr({method:'post',action:'http://www.lvmama.com/abroad/'});
				$selectForm.submit();
			});

			$(".row2").find('a[code="'+$('#fromDestSelect').find('option:selected').attr('code')+'"]').click();

			$('#teamAbroad').live('click',function(){
				var from,to,$link=$('.tabs:visible>.current>a'),$params=$link.attr('data-params');
					if($params.indexOf('ISLAND')!=-1){
						window.open("http://www.lvmama.com/search/abroad-subject-%E6%B5%B7%E6%BB%A8%E5%B2%9B%E5%B1%BF-route.html");
					}else{
						from=$('.tabs:visible').attr('from-to');
						to=($params.indexOf('SHIP')!=-1?"-subject-"+$link.text()+"-route-1.html":$link.text()+"-route.html");//为其它时,无目的地
						window.open("http://www.lvmama.com/search/abroad-from-"+from+"-to-"+to);
					}

			});	
			$('#freeAbroad').live('click',function(){                                           
				var from,to,$link=$('.tabs:visible>.current>a'),$params=$link.attr('data-params');
					if($params.indexOf('ISLAND')!=-1){
						window.open("http://www.lvmama.com/search/abroad-subject-%E6%B5%B7%E6%BB%A8%E5%B2%9B%E5%B1%BF-route.html");
					}else{
						from=$('.tabs:visible').attr('from-to');
						to=($params.indexOf('SHIP')!=-1?"-subject-"+$link.text()+"-route-1.html":$link.text()+"-route.html");//为其它时,无目的地
						window.open("http://www.lvmama.com/search/abroad-from-"+from+"-to-"+to);
					}

			});	
			
			
		});