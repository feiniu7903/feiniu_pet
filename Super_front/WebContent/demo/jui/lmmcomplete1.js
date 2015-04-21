(function($){
    $.widget( "ui.lmmcomplete", $.ui.autocomplete, {
				options: {
					appendTo: "body",
					input:"#search111",
					otherMenu:"#address_hot",
					page:1,
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
						if((request.term||request.term.length>0)&&(request.term!='中文/拼音')){
								$(om).css({top:'0',left:'0'}).hide();
							}else{
								$(om).position( $.extend({
									of: this.options.input
								}, this.options.position ));
								setTimeout(function() {
									$(om).show(300);
								}, 150 );
							}
						var self = this;
						$.ajax({
							url:"http://www.lvmama.com/search/placeSearch!searchCity.do",
							dataType: "jsonp",
							data: {
								keyword:request.term,
								page:this.options.page
							},
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
								$(this).removeClass( "ui-autocomplete-loading" );
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
				if(items&&items.length){
					ul.append( "<li class='ui-autocomplete-category ul-title'>"+val+"&nbsp;,(可以输入酒店名称或全拼)</li>" );
				}else{
					if(val&&val.length>0){
						ul.append( "<li class='ui-autocomplete-category ul-title'>对不起，没有找到:&nbsp;"+val+"</li>" );
					}
				}
				$.each( items, function( index, item ) {
					self._renderItem( ul, item );
				});
				if(items&&items.length){
					var $pageObj = $.data(document.body,'pageObj');
					var now = $pageObj.page;
					var len = $pageObj.total;
					var $paging = $( "<div class='ui-paging'></div>");
					for(var i=1;i<Math.ceil(len/10)+1;i++){
						if(now==i){
							$paging.append( $( "<a href='###' style='background:#999;' page='"+i+"'>"+i+"</a>" ));
						}else{
							$paging.append( $( "<a href='###'  page='"+i+"'>"+i+"</a>" ));
						}
					}
					$paging.appendTo(ul);
				}
			},
			_renderItem: function( ul, item ) {
				return $( "<li></li>" )
				.data( "item.autocomplete", item )
				.append("<a>"+item.value+"<span class='py_right'>"+item.label + "</span></a>" )
				.appendTo( ul );
			}
		});
})(jQuery);