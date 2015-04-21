(function ($) {
var map = new Map();
var options = null;
$.getPriceMap = function(){
return map;
};
$.getDayOfPrice = function(){
if(options.clickTdObj==null){
return null;
}else {
return options.clickTdObj.attr("price");
}
}
$.getDateOfSelected = function(){
if(options.clickTdObj==null){
return null;
}else {
return options.clickTdObj.attr("id");
}
}

	$.calendar = function (input, options) {
		var spanLeft = options.targetDiv.find("#turn_left");
		var spanRight = options.targetDiv.find("#turn_right_c");
		spanLeft.click(left);
		spanRight.click(right);
		function left(){
		var date = new Date();
		var dateStr = date.getFullYear()+""+date.getMonth();
		var month = $(this).attr("value");
		var dateSelect = $.calendarSetDate(options.date.getFullYear(),month-1,1);
		var selectDatesStr = options.date.getFullYear()+""+options.date.getMonth();//获得选择的年月
		if(dateStr!=selectDatesStr){//只允许显示当前月之前的月
		options.date = dateSelect;
		if((date.getFullYear()+""+(parseInt(date.getMonth())+1))==selectDatesStr){//设置当前年当前月之前left按钮的样式
			options.turn_left_style = "turn_left";
		}
		new $.createHeader(options);
		new $.calendar(this,options);
		} else {
		
		}
		}
		
		function loadCalendar(){
		new $.createHeader(options);
		new $.calendar(this,options);
		}
		function right(){
		var month = $(this).attr("value");
		var date = $.calendarSetDate(options.date.getFullYear(),parseInt(month)+parseInt(1),1);
		options.date = date;
		options.turn_left_style = "turn_left_c";
		new $.createHeader(options);
		new $.calendar(this,options);
		}
		
};
$.createHeader = function(options){
var tdiv = options.targetDiv;
var  title =  options.date.getFullYear()+"年"+(options.date.getMonth()+1)+"月价格日历<font>(点击日期预订)</font>";
var style = options.turn_left_style;
var header = "<table id=\"calendar_table\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th colspan=\"7\"><span id=\"turn_left\"  value=\""+(options.date.getMonth())+"\" class=\""+style+"\">"+(options.date.getMonth()==0?"12":options.date.getMonth())+"</span><span class=\"turn_right_c\" id=\"turn_right_c\" value=\""+(options.date.getMonth())+"\">"+((options.date.getMonth()+2)==13?1:(options.date.getMonth()+2))+"月</span>"+title+"</th></tr></thead><tbody>"
            +"<tr>"+
				"<th>星期日</td>"
                +"<th>星期一</td>"
               +"<th>星期二</td>"
                +"<th>星期三</td>"
               +"<th>星期四</td>"
                +"<th>星期五</td>"
               +"<th>星期六</td>"
             +"</tr>"
              +"</tbody>";
            
$.putCalendarNode(tdiv,header+$.createContent(options));//创建calendar框架元素
$.excuteCalendarCellElementCreateCallBack(options);//对表格元素渲染数据的方法
};
$.createContent= function(options){//制造格子
var days = $.getDaysOfmonth(options);
var weekDayFirstDayOfMonth = $.getWeekOfMonthFirstDay(options.date,options.date.getFullYear(),options.date.getMonth());
var m = options.date.getFullYear()+"/"+((parseInt(options.date.getMonth())+1)<10?0+""+(parseInt(options.date.getMonth())+1):(parseInt(options.date.getMonth())+1))+"/";
var day = 0;
var date = 0;
var str = "<tbody>";
for(var i=0;i<6;i++){
str +="<tr>";
 for (var j=0;j<7;j++) {
 		day ++;
 	if(day>weekDayFirstDayOfMonth){
 		if(day<=days+weekDayFirstDayOfMonth){
 		date++;
 		 //str +="<td id=\""+m+date+"\"  onClick=\"test(this)\">"+date+"</td>";
 		 var s =  $.createCell(options,m+(date<10?(0+""+date):date),date);
 		 str += s;
 		 if(day%7==0){
 		  str +="</tr>";
 		 }
 		} else{
 		str +="<td></td>";
 		}
 	} else {
 		str +="<td></td>";
 	}
 	
 }
}

str +=" </tbody>";
str +=" </table>";
return str;
};
$.putCalendarNode = function(obj,str){obj.html(str);};
$.fn.calendar = function (source,targetId,cellClickCallBack) {
		if (!source) {
			return;
		}
		options = options || {};
		var date = new Date();
		options.date = date;
		options.delay = options.delay || 100;
		options.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		options.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		options.targetDiv = $("#"+targetId);
		options.turn_left_style = "turn_left";
		options.url=source;
		options.id = $(this).val();
		options.timeData = null;
		options.timeout = false;
		options.selectTime = null;
		options.clickTdObj = null;
		options.currentDate = date.getFullYear()+"/"+((parseInt(date.getMonth())+1)<10?0+""+(parseInt(date.getMonth())+1):(parseInt(date.getMonth())+1))+"/"+(date.getDate()<10?0+""+date.getDate():date.getDate());
		options.callBack = cellClickCallBack;
		new $.createHeader(options);
		new $.calendar(this,options);
	};
	$.fn.gettimePrice = function(){
	return map.get(options.selectTime);
	};
$.getSetYearMonth = function(date,s_year,s_month){
date.setFullYear(s_year);
date.setMonth(s_month);
return date;
};
$.getWeekOfMonthFirstDay = function(date,s_year,s_month){
date.setFullYear(s_year);
date.setMonth(s_month);
date.setDate(1);
return date.getDay();
};
$.getRemoteData = function(options){
$.getJSON(options.url, {id:options.id}, function (json) {
options = $.putData(options,json);
});
};
$.putData= function(options,json){
 options.timeData = json;
}
$.calendarSetDate = function(s_year,s_month,s_day){
var d = new Date();
d.setFullYear(s_year);
d.setMonth(s_month);
d.setDate(s_day);
return d;
};
$.createCell = function(options,date,day){

var dateStr = date.split("/");
var monthArray = dateStr[1].split("");
var monthSmple = 0;
if(monthArray[0]==0){
monthSmple = monthArray[1];
} else {
monthSmple = dateStr[1];
}
var dayArray = dateStr[2].split("");
var daysimple = 0;
if(dayArray[0]==0){
daysimple=dayArray[1];
} else {
daysimple=dateStr[2];
}
var yinliMonthDay = GetLunarDay(dateStr[0],monthSmple,daysimple);
var jieriName = $.getFestivalByDate($.getMonthDay(date));

var yinlijiriName = $.getLunarFestivarl(yinliMonthDay,dateStr[0]);
var festivalDay = jieriName==null?"":"<span>"+jieriName+"</span>&nbsp;";

if(options.currentDate==date){
return "<td  name=\"calendar_tddate\" id=\""+date+"\">"+(jieriName==null?"<span>今天</span>&nbsp;":festivalDay)+"<i>"+day+"</i></td>";
} else {
return "<td  name=\"calendar_tddate\" id=\""+date+"\">"+(jieriName==null?(yinlijiriName==null?"":"<span>"+yinlijiriName+"</span>&nbsp;"):festivalDay)+"<i>"+day+"</i></td>";
}

};
//渲染日期的方法
$.excuteCalendarCellElementCreateCallBack = function(options){
 if(map.size()==0&&options.id!=null&&options.id!=""){
  //数据map为空 执行远程数据查询。
  $.getJSON(options.url, {id:options.id}, function (json) {
		for (var i=0;i<json.t.length;i++) {
			if(json.t[i]!=null&&json.t[i]!=""){
			map.put(json.t[i].date,json.t[i]);
			}
		}
		

		//alert($("#pro_calendar").find("td[name='calendar_tddate']").length);
			options.targetDiv.find("td[name='calendar_tddate']").each(function(){
			var element = $(this);
 			var elementDate = $(this).attr("id");
 			element.addClass("ye");
 			if(map.get(elementDate)!=null){//渲染单个日期
 			var textDay = element.find("i").text()
 			element.find("i").html("<font>"+textDay+"</font>");//渲染并替换
 			}
 			if(map.get(elementDate)!=null){
 			if(map.get(elementDate).stock!=null&&map.get(elementDate).stock!="null"){
 			if(parseInt(map.get(elementDate).stock)>=10){
 				element.append("<br /><strong>充足</strong>");
 			}
 			}
 			}
 			if(map.get(elementDate)!=null){
 			element.mouseover(function(){
 			element.addClass("yellow_bg");
 			});
 			element.mouseout(function(){
 		
 			element.removeClass("yellow_bg");
 			});
 			
 			element.click(function(){
 			if(options.clickTdObj!=null){
 			options.clickTdObj.removeClass("yellow_bg");
 			}
 			element.unbind("mouseover");
 			element.unbind("mouseout");
 			element.addClass("yellow_bg");
 			options.clickTdObj = element;
 			//if(options.excuteCellClick){
 			//$("#pro_calendar2").html("");
 			// $("#pro_window").fadeIn("slow");
 			//}
 			var callback = options.callBack;
 			callback();
 			});
 		
 			}
 			element.append("<br/><em>"+(map.get(elementDate)!=null?"￥"+map.get(elementDate).price:"")+"</em>");//渲染价格
 			element.attr("price",(map.get(elementDate)!=null?map.get(elementDate).price:""));//设置cell价格属性
			});
		});
 } else {
   	options.targetDiv.find("td[name='calendar_tddate']").each(function(){
			var element = $(this);
 			var elementDate = $(this).attr("id");
 			element.addClass("ye");
 			if(map.get(elementDate)!=null){//渲染单个日期
 			var textDay = element.find("i").text()
 			element.find("i").html("<font>"+textDay+"</font>");//渲染并替换
 			}
 			if(map.get(elementDate)!=null){
 			if(map.get(elementDate).stock!=null&&map.get(elementDate).stock!="null"){
 			if(parseInt(map.get(elementDate).stock)>=10){
 				element.append("<br /><strong>充足</strong>");
 			}
 			}
 			}
 		if(map.get(elementDate)!=null){
 			element.mouseover(function(){
 			element.addClass("yellow_bg");
 			});
 			element.mouseout(function(){
 			element.removeClass("yellow_bg");
 			});
 			
 			element.click(function(){
 			if(options.clickTdObj!=null){
 			options.clickTdObj.removeClass("yellow_bg");
 			}
 			element.unbind("mouseover");
 			element.unbind("mouseout");
 			element.addClass("yellow_bg");
 			options.clickTdObj = element;
 			var callback = options.callBack;
 			callback();
 			});
 		
 			}
 			element.append("<br/><em>"+(map.get(elementDate)!=null?"￥"+map.get(elementDate).price:"")+"</em>");
 			element.attr("price",(map.get(elementDate)!=null?map.get(elementDate).price:""));//设置cell价格属性
			});
 }

};
$.getFestivalByDate = function (key) {//传入月份和天得到阳历节日
var map = new Map();
map.put("0101","元旦");
map.put("0308","妇女节");
map.put("0312","植树节");
map.put("0405","清明节");
map.put("0501","劳动节");
map.put("0504","青年节");
map.put("0601","儿童节");
map.put("0910","教师节");
map.put("1001","国庆节");
map.put("1225","圣诞节");
return map.get(key);

};
$.getLunarFestivarl = function(key,year){
var map = new Map();
map.put("正月初一","春节");
map.put("十二月廿九","除夕");
map.put("十二月三十","除夕");
map.put("五月初五","端午节");
map.put("正月十五","元宵节");
map.put("七月初七","七夕");
map.put("八月十五","中秋节");
map.put("九月初九","重阳节");
if(key=="十二月廿九"){
var tdays = monthDays(year-1,12);
if(tdays==30){
return null;
} else {
return map.get(key);
}
}
return map.get(key);
};
$.getDaysOfmonth = function(options) {
        if ((options.date.getFullYear() % 4) == 0) {
                if ((options.date.getFullYear() % 100) == 0 && (options.date.getFullYear() % 400) != 0)
                        return options.DOMonth[options.date.getMonth()];
                return options.lDOMonth[options.date.getMonth()];
        } else
                return options.DOMonth[options.date.getMonth()];
};
$.getMonthDay = function(datestr){
var date = datestr.split("/");
return date[1]+date[2];
};
})(jQuery);
