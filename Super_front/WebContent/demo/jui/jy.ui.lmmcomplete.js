(function($){
    $.widget( "ui.lmmcomplete", $.ui.autocomplete, {
				options: {
					appendTo: "body",
					input:"#search", 
					otherMenu:"#address_hot",
					pageNum:10,
					autoFocus: false,
					delay: 300,
					minLength: 0,
					position: {
						my: "left top",
						at: "left bottom",
						collision: "none"
					},
					source: function( request, response ) {
						var om = this.options.otherMenu;
						var it = this.length?this:this.options.input;
						if((request.term||request.term.length>0)&&(request.term!='中文/拼音')){
								$(om).css({top:'0',left:'0'}).hide();
							}else{
								$(it).blur().focus();
								$(om).css({top:'0',left:'0'}).position( $.extend({
									of: it
								}, this.options.position ));
								setTimeout(function() {
									$(om).show(300);
								}, 150 );
							}
						var self = this;
						$.ajax({
							url:"http://127.0.0.1/search/ticketSearch!addPlace.do",
							dataType: "jsonp",
							data: {
								keyword:request.term,
								page:1
							},
/*							url:"http://www.lvmama.com/search/placeSearch!searchCity.do",
							dataType: "jsonp",
							data: {
								keyword:request.term,
								page:this.options.page
							},*/
							success: function( data ) {
								var p = data.page,t=data.totalResultSize;
								$.data(document.body,'pageObj',{page:p,total:t});
								if(data.totalResultSize>0){
									response($.map( data.placeListJson, function( item ) {
										//alert(item.name+','+item.pinYin);
										return {
											label: item.pinYin,
											value: item.name
										}
									}));
								}else{
									response([]);	
									
									//this._renderMenu();
								}
							},
							error:function(x,e,c){
							}
						});
					}
				},
				_response: function( content ) {
					if ( !this.options.disabled && content && content.length ) {
						content = this._normalize( content );
						this._suggest( content );
						this._trigger( "open" );
					} else {
						content = this._normalize( content );
						this._suggest( content );
						this._trigger( "open" );
						//this.close();
					}
					this.pending--;
					if ( !this.pending ) {
						this.element.removeClass( "ui-autocomplete-loading" );
					}
				},
			_renderMenu: function( ul, items ) {
				var self = this;
				var val = this.element.val();
				var pageNum = this.options.pageNum;
				if(items&&items.length){
					ul.append( "<div class='ui-autocomplete-category ul-title'>"+val+"&nbsp;,(可以输入酒店名称或全拼)</div>" );
				}else{
					if(val&&val.length>0){
						ul.append( "<div class='ui-autocomplete-category ul-title'>对不起，没有找到:&nbsp;"+val+"</div>" );
					}
				}
				$.each( items, function( index, item ) {				
					if(index<pageNum){
						self._renderItem( ul, item );
					}else{
						self._renderItemHide( ul, item );
					}
				});
				
				if(items&&items.length){
					var $pageObj = $.data(document.body,'pageObj');
					var now = $pageObj.page||1;
					var len = $pageObj.total;
					var $paging = $( "<div class='ui-paging'></div>");
					for(var i=1;i-1<Math.ceil(len/pageNum);i++){
						if(now==i){
							$paging.append( $( "<a href='javascript:void(0);' style='background:#999;' page='"+i+"'>"+i+"</a>" ));
						}else{
							$paging.append( $( "<a href='javascript:void(0);'  page='"+i+"'>"+i+"</a>" ));
						}
						/*if(i>1){break;}*/
					}
					$paging.appendTo(ul);
				}
			},
			_renderItem: function( ul, item ) {
				return $( "<li></li>" )
				.data( "item.autocomplete", item )
				.append("<a><span class='py_right'>"+item.label + "</span>"+item.value+"</a>" )
				.appendTo( ul );
			}
			,_renderItemHide: function( ul, item ) {
				return $( "<li style='display:none;'></li>" )
				.data( "item.autocomplete", item )
				.append("<a><span class='py_right'>"+item.label + "</span>"+item.value+"</a>" )
				.appendTo( ul );
			}
		});
})(jQuery);