function destInfo(id,destUlId,textId,targetType){
          var $fromPlace = $("#"+id);
          var $fromPlace_keyword = $("#"+destUlId);
          $fromPlace_keyword.show();
          if($("#"+destUlId).find("li").length<1){
        	  $("#"+destUlId).css({"position":"absolute","z-index":"10","left":""});
	          $fromPlace.mouseout(function(){$fromPlace_keyword.hide();})
	          $fromPlace_keyword.append(getText(textId,destUlId,targetType));
          }
   }
function getText(textId,displayId,type){
	var dest = new Array()
	if("fromDest"==type){
		dest = new Array("上海","杭州","广州","青岛","厦门","千岛湖","成都","广州","南京","南昌");
	}else if("toDest"==type){
		dest = new Array("上海","杭州","普陀山","乌镇","安吉","桐庐","千岛湖","宁波","嘉兴","苏州","常州","扬州","无锡","周庄","南京","常熟","北京","重庆","西安","大连","海口","厦门","桂林","青岛","哈尔滨","香港","澳门","法国","日本","普吉岛","瑞士","英国","德国","泰国","塞班岛","韩国","意大利")
	}else if("Dest"==type){
		dest = new Array("杭州","千岛湖","临安","桐庐","象山","金华","连云港","都江堰","康定","丽水","峨眉山","成都","韶关","三亚");
	}
	
	var str = "";
	for (i=0;i<dest.length;i++)
	{
		str +="<li><a href=\"javascript:getAddress('"+textId+"','"+displayId+"','"+dest[i]+"')\">"+dest[i]+"</a></li>"
	}
    return str;
}
function getAddress(textId,displayId,destName){
	$("#"+textId).val(destName);
	$("#"+displayId).hide();
}