$(function(){
	$(".priceform,.price-range").find('"input[type="text"]').keypress(function(e){
		var curKey = e.which; 
		if(curKey == 13){ 
			$(".priceform,.price-range").find('"input[type="button"]').click();
		} 
	});
	$(".priceform,.price-range").find('"input[type="button"]').click(function(){
		var form =$(".priceform,.price-range").find('form');
		var inputs = form.find('"input[type="text"]');
		var _action= form.attr("action");
		var startPrice = $(inputs[0]).val();
		var endPrice = $(inputs[1]).val();
	
		var has_startPrice = false;
		var has_endPrice = false;
		if(startPrice!="最低价" && startPrice!=""){
			has_startPrice = true;
		}
		if(endPrice!="最高价" && endPrice!=""){
			has_endPrice = true;
		}
		if(has_startPrice && !/^\d+$/.test(startPrice)) {
			$(inputs[0]).focus();
			return;
		}else if(has_endPrice && !/^\d+$/.test(endPrice)) {
			$(inputs[1]).focus();
			return;
		}else{
			if((has_startPrice || has_endPrice) && ($(inputs[0]).attr("val")!=startPrice || $(inputs[1]).attr("val")!=endPrice)){
				if(has_startPrice){
					_action= _action.replace("K1","K"+startPrice);
					submitFlag = true;
				}else{
					_action= _action.replace("K1","");
				}
				
				if(has_endPrice){
					_action= _action.replace("O1","O"+endPrice);
					submitFlag = true;
				}else{
					_action= _action.replace("O1","");
				}
				form.attr("action",_action);
				form.submit();
			}
		}
	});
	
	
	$("div.result-search").find('"input[type="text"]').keypress(function(e){
		var curKey = e.which; 
		if(curKey == 13){ 
			$("div.result-search").find('"input[type="button"]').click();
		} 
	});
	$("div.result-search").find('"input[type="button"]').click(function(){
		console.log(11);
		var _input = $("div.result-search").find('"input[type="text"]');
		if("在结果中搜索" != _input.val()){
			var _input_val = _input.val();
			if(_input.attr("val")!=_input_val){
				if(_input_val.indexOf("W") == 0 || /^\d+$/.test(_input_val)){ 
					_input_val = "W"+_input_val; 
				}
				_input_val = _input_val.replace(new RegExp("A", 'g'),"/A")
										.replace(new RegExp("B", 'g'),"/B")
										.replace(new RegExp("C", 'g'),"/C")
										.replace(new RegExp("D", 'g'),"/D")
										.replace(new RegExp("E", 'g'),"/E")
										.replace(new RegExp("F", 'g'),"/F")
										.replace(new RegExp("G", 'g'),"/G")
										.replace(new RegExp("H", 'g'),"/H")
										.replace(new RegExp("I", 'g'),"/I")
										.replace(new RegExp("J", 'g'),"/J")
										.replace(new RegExp("K", 'g'),"/K")
										.replace(new RegExp("L", 'g'),"/L")
										.replace(new RegExp("M", 'g'),"/M")
										.replace(new RegExp("N", 'g'),"/N")
										.replace(new RegExp("O", 'g'),"/O")
										.replace(new RegExp("P", 'g'),"/P")
										.replace(new RegExp("Q", 'g'),"/Q")
										.replace(new RegExp("R", 'g'),"/R")
										.replace(new RegExp("S", 'g'),"/S")
										.replace(new RegExp("T", 'g'),"/T")
										.replace(new RegExp("U", 'g'),"/U")
										.replace(new RegExp("V", 'g'),"/V")
										.replace(new RegExp("W", 'g'),"/W")
										.replace(new RegExp("X", 'g'),"/X")
										.replace(new RegExp("Y", 'g'),"/Y")
										.replace(new RegExp("Z", 'g'),"/Z");
				var _action= $(this).attr("url").replace("Q1","Q"+encodeURIComponent(_input_val));
				var form =$("<form class=\"form-result-search\" action=\""+_action+"\" ></form>");
				form.attr("action",_action);
				form.insertAfter($(this));
				form.submit();
			}
		}
	});
});