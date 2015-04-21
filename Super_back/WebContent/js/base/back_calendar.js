
     (function($) {
     var date = null;
     $.fn.initCalendar=function(options){
     options = options || {};
     options.month = options.month || 2;
     options.index = options.index || 1;
     options.callback = options.callback || null;

		var c = $(this);
		c.find("#month").eq(options.index).hide();
		c.find("#turn_left").bind("click",function(){
			if(c.find("#"+$(this).attr("hide")).attr("id")!=undefined&&c.find("#"+$(this).attr("show")).attr("id")!=undefined){
				c.find("#"+$(this).attr("hide")).hide();
				c.find("#"+$(this).attr("show")).show();
			}
			
		});
		
		c.find("#turn_right_c").bind("click",function(){
			if(c.find("#"+$(this).attr("hide")).attr("id")!=undefined&&c.find("#"+$(this).attr("show")).attr("id")!=undefined){
				c.find("#"+$(this).attr("hide")).hide();
				c.find("#"+$(this).attr("show")).show();
			}
		});
		var clickObj;
		c.find("#hasTimePrice").each(function(){
			
			$(this).mouseover(function(){
 			$(this).addClass("yellow_bg");
 			});
 			
 			$(this).mouseout(function(){
 			$(this).removeClass("yellow_bg");
 			});
 			
 			
			
			$(this).css("cursor","pointer");
			$(this).bind('click',function(){
				$(this).unbind("mouseout");
 			$(this).unbind("mouseover");
 			if (clickObj!=null) {
 			 clickObj.removeClass("yellow_bg");
 			 clickObj.mouseover(function(){
 			$(this).addClass("yellow_bg");
 			});
 			clickObj.mouseout(function(){
 			$(this).removeClass("yellow_bg");
 			});
 			
 			}
				clickObj=$(this);
				var date = "";
				var flag = true;
 				flag = checkDate($("input[name='id']").val(),$(this).attr("date"));
				if(flag){
					date=$(this).attr("date");
				}
			var price = $(this).attr("price");
			var stock = $(this).attr("stock");
			
			options.callback(date,stock);

			
			});
		});

     }
     
     $.fn.getSelectedDate=function(){
		return date;
		}
     
     })(jQuery); 
     
       function checkDate(product_id,date){
    		var limittime = "";
    		var flag = true;
    		$.ajax( {
    			type : "POST",
    			dataType : "json",
    			url : "/super_back/productDetail/checkDate.do",
    			async : false,
    			data : {id:product_id,choseDate:date},
    			timeout : 3000,
    			error : function(a, b, c) {
    				if (b == "timeout") {
    					alert("请求超时");
    				} else if (b == "error") {
    					alert("请求出错");
    				}
    			},
    			success : function(data) {
    			if(data.jsonMsg.key!=null){
    				alert("该商品此游玩日期的销售从"+data.jsonMsg.value+"开始");
    				flag = false;
    			}
    			}
    		});
    		return flag;
    	}
          function checkStock(product_id,quantity,date,endDate){
     		var limittime = "";
    		var flag = true;
    		$.ajax( {
    			type : "POST",
    			dataType : "json",
    			url : "/super_back/productDetail/checkStock.do",
    			async : false,
    			data : {id:product_id,quantity:quantity,choseDate:date,endDate:endDate},
    			timeout : 3000,
    			error : function(a, b, c) {
    				if (b == "timeout") {
    					alert("请求超时");
    				} else if (b == "error") {
    					alert("请求出错");
    				}
    			},
    			success : function(data) {
    			if(data.jsonMsg.key!=null){
    				alert(data.jsonMsg.value);
    				flag = false;
    			}
    			}
    		});
    		return flag;
    	}
 