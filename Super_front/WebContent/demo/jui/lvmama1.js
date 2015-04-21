(function($){
    $.fn.recommend = function(options){
        var defaults = {
            input:"#search111",
			appendTo: "#address_hot",
			list:".address_hotlist",
			placed_in:"div .demo",
			position: {
				my: "left top",
				at: "left bottom",
				collision: "none"
			}        
        };
        var options = $.extend(defaults, options);
        this.each(function(){	
			var address_hot = $('<div class="address_hot" id="address_hot">'+
			'<div class="address_hotcity"><strong>热门城市</strong> （可直接输入城市或城市拼音）</div>'+
			'<div class="address_hotlist">'+
				'<ol class="address_hot_abb" style=""><li><span class="hot_selected">推荐</span></li><li><span>A-F</span></li><li><span>G-J</span></li><li><span>K-N</span></li><li><span>P-W</span></li><li><span>X-Z</span></li></ol>'+
				'<ul class="address_hot_adress layoutfix" type="推荐">'+
				'<li><a href="###" data="BJS|北京">九寨沟黄龙</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'<li><a href="###" data="BJS|北京">北京</a></li>'+
				'</ul>'+
				'<ul class="address_hot_adress layoutfix" style="display:none" type="A-F"></ul>'+
				'<ul class="address_hot_adress layoutfix" style="display:none" type="G-J"></ul>'+
				'<ul class="address_hot_adress layoutfix" style="display:none" type="K-N"></ul>'+
				'<ul class="address_hot_adress layoutfix" style="display:none" type="P-W"></ul>'+
				'<ul class="address_hot_adress layoutfix" style="display:none" type="X-Z"></ul>'+
			'</div>'+
			'</div>'+
			'</div>');//.prependTo($("body")).zIndex(9999);//.hide();
			//alert($(options.placed_in || "body")[0].innerHTML);
			//alert($("body").html());
			//alert($("body").html());
			//$(options.appendTo).css({ top: '0', left: '0',position:'relative' });
			//alert($(options.input).position().top+'<>'+$(options.input).position().left);
			//for(var kk in options){alert(kk)}
			$('body').append(address_hot);
			//alert($("#cont").html());
/*			$(options.appendTo).position( $.extend({
					of: $(options.input)
				}, options.position ))*/
/*			$(options.list).find('span').live('click',function(){
				var val = $(this).text();
				var $list = $(options.list);
				$(this).parents('ol').find('span').removeClass('hot_selected');
				$(this).addClass('hot_selected');
				$list.find('ul').hide();
				$list.find('ul[type="'+val+'"]').show();
					
			});
			$(options.list).find('a').live('click',function(){
				$(options.input).val($(this).text());
				$(options.appendTo)/*.css({ top: 0, left: 0 }).hide();
			});*/
        });
    };
})(jQuery);