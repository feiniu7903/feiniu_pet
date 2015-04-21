function hotel_calendar_mend_login(obj,callback){
	var clickObj;
	var date_array=obj.children().find("td[date]");
	for(var i=1;i<date_array.length;i++){
		var this_date_div=$(date_array.eq(i));
		var this_date=this_date_div.attr("date");
		this_date=new Date(this_date.split("-").join("/"));
		var current_date=new Date();
		var stock=this_date_div.attr("stock");
		if((this_date>current_date)&&(parseInt(stock)>=-1&&parseInt(stock)!=0)){
			var prev=$(date_array.eq(i-1));
			var prev_date=prev.attr("date");
			prev_date=new Date(prev_date.split("-").join("/"));
			var id=prev.attr("id");
			if(prev_date>current_date&&(id==null||id=='')){
				prev.unbind('click');
				prev.mouseover(function(){
					$(this).addClass("yellow_bg");
			 	});
				prev.mouseout(function(){
					$(this).removeClass("yellow_bg");
			 	});
				prev.css("cursor","pointer");
				prev.bind('click',function(){
					date_array.removeClass("yellow_bg").bind('mouseout',function(){$(this).removeClass("yellow_bg");});
				    var date=$(this).attr("date");
				    var price = $(this).attr("price");
				    var stock = $(this).attr("stock");
				    callback(date,stock);	
				    $(this).addClass("yellow_bg").unbind('mouseout');
				});
			}
		}
	}
}

function date_between_trigger(obj,begin_date,end_date,change_id){
	//1.取得入住时间
	var visitTime_current=begin_date;
	//2.取得离店时间
	var leaveTime_current=end_date;
	var count=0;
	var empty_count=0;
	var date_array;
	if(visitTime_current!=null){
		var begin=obj.children().find("td[date]").index($("#"+obj.attr("id")+" td[date="+visitTime_current+"]"));
		var end=obj.children().find("td[date]").index($("#"+obj.attr("id")+" td[date="+leaveTime_current+"]"));
		if(begin>end){
			alert("入住时间不能晚于离店时间");
			$(change_id).val("");
			return false;
		}
		var date_array=obj.children().find("td[date]");
		for(var i=begin;i<end;i++){
		  var cur_date=date_array.eq(i);
		  if(cur_date.attr('date')==null){
				empty_count=empty_count+1;
			}else{
			   var stock = cur_date.attr("stock");
			   if(i==end&&(end-begin)<=1&&parseInt(stock)==0){
			   	continue;
			   }
				if(parseInt(stock)<-1||parseInt(stock)==0){
				    count=count+1; 
				}
			}
		}
	    if(empty_count>0){
	    	alert('当前时间范围有房间已售完或不可售，请重新选择其他日期');
	    	$(change_id).val("");
	    	return false;
	    }else if(count>0){
	    	alert('当前时间范围有房间已售完或不可售，请重新选择其他日期');
	    	$(change_id).val("");
	    	return false;
	    };
	}
	return true;
};
function hotel_calendar_mend_logout(current_date,obj){
	//得到入住日期
	var login=$("#visitTime").val();
	if(!(date_between_trigger(obj,login,current_date,"#leaveTime"))){
		return false;
	};
	return true;
}
function hotel_calendar_mend_l(current_date,obj){
	//得到入住日期
	var login=$("#leaveTime").val();
	if(!(date_between_trigger(obj,login,current_date,"visitTime"))){
		return false;
	};
	return true;
}