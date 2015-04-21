var _calendarIsDownloaded  = false;
var genCalenarHtml =""; 
function isCalendarIsDownloaded(){
	return _calendarIsDownloaded;
}
function  formatDate(dateStr,format){
	if("yyyy-mm-dd"==format){
		return  dateStr.substring(0,10);
	}
	if("dd"==format){
		return  dateStr.substring(8,10);
	}
	return dateStr;
}
function showOrHide(event,hide,show){
	if (event.stopPropagation) event.stopPropagation();
  		else event.cancelBubble = true;
		if($("#"+show).attr("id")!=undefined){
		$("#"+hide).hide();
		$("#"+show).fadeIn();
	}

	
}
/**
*
*生成产品价格日历
*/
function genJsPrdCalenar(data){
	
	var prodCalendarMap = data.prodCalendarMap;
	genCalenarHtml= "<DIV style='POSITION: absolute; DISPLAY: none; TOP: 10px; LEFT: 200px' id=quick_calendar class=calendar-ui>";
	if(prodCalendarMap.flag='Y'){
		
		var cmList = prodCalendarMap.cmList;
		var  cmi = null;
		for(var i=0;i<cmList.length;i++){
			cmi=cmList[i];
			genCalenarHtml=genCalenarHtml+"<TABLE id='q_calendar_table_"+cmi.year+"_"+cmi.month+"' border=0  cellSpacing=0 cellPadding=0 "
			
			if(i!=0){
				genCalenarHtml=genCalenarHtml+	" style='display:none;'";
			}
			genCalenarHtml=genCalenarHtml+">";

			
			//生成日历头	
			genCalenarHtml=genCalenarHtml+"<thead>";
			genCalenarHtml=genCalenarHtml+"		<tr>";
			genCalenarHtml=genCalenarHtml+"		 	<th colSpan=7>";
		    genCalenarHtml=genCalenarHtml+"		 	<img src='http://pic.lvmama.com/img/super_v2/cal_left_btn.gif' class='cal-left-btn' onclick=\"showOrHide(event,'q_calendar_table_"+cmi.year+"_"+cmi.month+"','q_calendar_table_"+(cmi.month-1==0?cmi.year-1:cmi.year)+"_"+(cmi.month-1==0?12:cmi.month-1)+"')\" />";
		    genCalenarHtml=genCalenarHtml+" 		<strong id='quick_calendar_disp_year"+100+"'>"+cmi.year+"</strong>年";
		    genCalenarHtml=genCalenarHtml+"         <strong id='quick_calendar_disp_month"+100+"'>"+cmi.month+"</strong>月";
		    genCalenarHtml=genCalenarHtml+"   		<img src='http://pic.lvmama.com/img/super_v2/cal_right_btn.gif' class='cal-right-btn' onclick=\"showOrHide(event,'q_calendar_table_"+cmi.year+"_"+cmi.month+"','q_calendar_table_"+(cmi.month==12?cmi.year+1:cmi.year)+"_"+(cmi.month==12?1:cmi.month+1)+"')\" />";
		    genCalenarHtml= genCalenarHtml+" 		</th>";
		    genCalenarHtml= genCalenarHtml+" 	</tr>";  
	 	    genCalenarHtml= genCalenarHtml+"</thead>";  

			genCalenarHtml= genCalenarHtml+" <tbody>\n";  
		   	genCalenarHtml= genCalenarHtml+" 	<tr>\n";  
		   	genCalenarHtml= genCalenarHtml+" 		<th>日</th>\n";  
			genCalenarHtml= genCalenarHtml+" 		<th>一</th>\n";  
			genCalenarHtml= genCalenarHtml+" 		<th>二</th>\n";  
		    genCalenarHtml= genCalenarHtml+" 		<th>三</th>\n";  
		  	genCalenarHtml= genCalenarHtml+" 		<th>四</th>\n";  
		    genCalenarHtml= genCalenarHtml+" 		<th>五</th>\n";  
		    genCalenarHtml= genCalenarHtml+" 		<th>六</th>\n";  
			genCalenarHtml= genCalenarHtml+" 	</tr>\n";
		   	genCalenarHtml= genCalenarHtml+" </tbody>\n";
		   
		   
		   //生成具体的日历日期
		   
		   genCalenarHtml= genCalenarHtml+" <tbody>\n";
		   var cal2 = null;
		   for (var j=0;j<cmi.calendar.length;j++){
		   			 genCalenarHtml= genCalenarHtml+" 		 <tr>\n";
		   			 ca1 = cmi.calendar[j];
		   			 for(var k=0;k<ca1.length;k++){
		   			 	genCalenarHtml= genCalenarHtml+"  <td style='"+(ca1[k].dayStock!=-2?"color: blue; cursor: pointer":"");
						genCalenarHtml= genCalenarHtml+"		 id='hasTimePricedate'"; 
						genCalenarHtml= genCalenarHtml+"		 stock='"+ca1[k].dayStock+"'" ;
						genCalenarHtml= genCalenarHtml+"		 date='"+formatDate(ca1[k].specDate,'yyyy-mm-dd')+"'"; 
						genCalenarHtml= genCalenarHtml+"		 price='"+(ca1[k].priceF=='null'?'0': ca1[k].priceF)+"'  ";
						if(ca1[k].dayStock!=-2){
							genCalenarHtml= genCalenarHtml+ " onclick=\"onDate_trigger(this,event)\""; 
						}
		   			 	genCalenarHtml= genCalenarHtml+">";
						genCalenarHtml= genCalenarHtml+formatDate(ca1[k].specDate,'dd');
		   			 	genCalenarHtml= genCalenarHtml+"  </td>"
		   			 
		   			 }
		   			 genCalenarHtml= genCalenarHtml+" 		 </tr>\n";
		   
		   }	
		   genCalenarHtml= genCalenarHtml+" </tbody>\n";
		}
		genCalenarHtml= genCalenarHtml+"</table>";
		genCalenarHtml= genCalenarHtml+"</DIV>";
		$("body").append(genCalenarHtml); 
		genCalenarHtml=null;
		
		_calendarIsDownloaded =true;
	}else{
		alert("获取产品产品时间价格表失败!");
		_calendarIsDownloaded = false;
	}
} 

var _productId = null;
var _canSubmit = false;
var _currentPrice = "";
//是否产品选择的时间是合法的
function isProductSaleDateValid() {

	if(!_canSubmit) {
		alert("当前日期没有库存，请选择其他日期");
		return false;
	}
	return true;
}
function getDays(paramId) {
	if(document.getElementById(_currentDateInput) != null) {
		var visitTimeValue = $("#"+_currentDateInput).val();
		if(visitTimeValue !="" ) {
		    _canSubmit = true;
	    }else{
	    	_canSubmit = false;
	    }
	    return _canSubmit;
	}
	return false;
}
var DATE_STOCK_TRIGGER_SIGN=true;
function onDate_trigger(tdObj,evt){
					cancelEvent(evt);
 					 var date=tdObj.getAttribute("date");
 					 var stock = tdObj.getAttribute("stock");
 					 var sign=true;
 					 if(DATE_STOCK_TRIGGER_SIGN){
 					 	sign=(parseInt(stock)>=-1);
 					 }else{
 					 	sign=(parseInt(stock)>=-1&& parseInt(stock)!=0);
 					 }
 					  if(sign){
 					  
 				 
		                    $("#"+_currentDateInput).val(date);
		                    $("#quick_calendar").hide();
	                     
		                    $.getJSON("http://www.lvmama.com/happy/product/price.do?callback=?", {productId:_productId,choseDate:date},function (data) {
									var json=data.jsonMap;
									//error 
									if(json.flag=='N'){
										alert(json.error);
			 							return;
									}
									if(json.price.length>0){
										if(_dateTriggerCallback!="" && typeof (_dateTriggerCallback) !='undefined'){
										    eval(_dateTriggerCallback+"(json.price[0].price,'"+date+"')");
									 	}
									}
								});
                    		_canSubmit = true;
                    } else {
	                     alert("当前日期没有库存，请选择其他日期");
	                     _canSubmit = false;
                    }


}
function cancelEvent(evt){
	var e=(evt)?evt:window.event; 
	 if (window.event) 
	 { 
	  	e.cancelBubble=true; 
	 } 
	 else 
	 { 
	  	e.stopPropagation(); 
	 } 
}
var _dateTriggerCallback = null;
var _prdUnitPrice=null;
function visitDateOnclick(obj,prdUnitPrice,prdId,evt,visitDateCallback){
	_currentDateInput = obj.getAttribute("id");
	cancelEvent(evt);
	_productId = prdId;
	_prdUnitPrice= prdUnitPrice;
	
	if(!_calendarIsDownloaded){
		bindDocEvent();
		showProgress(true);
		ajaxQueryProductCalendar(prdId)
	}else{
		showCalendar();
	}
	_dateTriggerCallback = visitDateCallback;
	
	
}
function showCalendar(){
	var leftNum = $("#"+_currentDateInput).offset().left;
	var topNum = $("#"+_currentDateInput).offset().top;
	$('#quick_calendar').fadeIn().css({"left":leftNum-73,"top":topNum+22});	
}


/**
*
*查询产品时间价格表
*
*/
function ajaxQueryProductCalendar(prdId){
	
	$.getJSON("http://www.lvmama.com/buy/ajaxQueryProductCalendar.do?callback=?", { productId: prdId},
           function(data) {
               genJsPrdCalenar(data);
				showProgress(false);
				showCalendar();
				
      });

}
//点击其他地方关闭日历

function bindDocEvent(){ 
	$(document).bind("click",function(event){
		if(event.currentTarget === this ){
			$('#quick_calendar').hide();
		} else {
		}
		});
}
//显示进度
function showProgress(showFlag){
		
		if(showFlag){
			var leftNum = $("#"+_currentDateInput).offset().left;
			var topNum = $("#"+_currentDateInput).offset().top;
			var dispDiv = "<div id='_prdTimeloading' style='position:absolute;display:none;left:"+leftNum+"px;top:"+topNum+ "px;z-index:999'><img src='http://pic.lvmama.com/img/loading.gif' alt='正在处理...'></div>"
			$("body").append(dispDiv);
			$("#_prdTimeloading").css({"display":"block"});
		}else{
			$("#_prdTimeloading").css("display", "none");
			$("#_prdTimeloading").remove(); 
		}
}

