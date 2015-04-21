// JavaScript Document
$(document).ready(function(){
	//文档就绪自动渲染日历层
	pandora.calendar({
		target:"#divContainer",//日历容器. 使用jquery选择器语法
		selectDateCallback:selectDateCallBack,
        cancelDateCallback:cancelDateCallBack,
		autoRender:true,
        allowSelected:true,
		template: {
            warp: '<div class="ui-calendar ui-calendar-mini" style="display:block"></div>',
            calControl: '<span class="month-prev" {{stylePrev}} title="上一月">‹</span><span class="month-next" {{styleNext}} title="下一月">›</span>',
            calWarp: '<div class="calwarp clearfix">{{content}}</div>',
            calMonth: '<div class="calmonth">{{content}}</div>',
            calTitle: '<div class="caltitle"><span class="mtitle">{{month}}</span></div>',
            calBody: '<div class="calbox">' +
                        '<i class="monthbg">{{month}}</i>' +
                        '<table cellspacing="0" cellpadding="0" border="0" class="caltable">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th class="sun">日</th>' +
                                    '<th class="mon">一</th>' +
                                    '<th class="tue">二</th>' +
                                    '<th class="wed">三</th>' +
                                    '<th class="thu">四</th>' +
                                    '<th class="fri">五</th>' +
                                    '<th class="sat">六</th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody>' +
                                '{{date}}' +
                            '</tbody>' +
                        '</table>' +
                    '</div>',
            weekWarp: '<tr>{{week}}</tr>',
            day: '<td {{week}} {{dateMap}} >' +
                    '<div {{className}}>{{day}}</div>' +
                 '</td>'
        }
 	}); 
	
	$("#btnDel").bind("click",function(){removeDate();});
})

function removeDate(){
	var target=$("#selDate option:selected");
    var value=target.val();
	if(!target.val())
		return;
	var next=target.next().val()?target.next():target.prev();//判断下一个被选中的目标
	$("#selDate option:selected").remove();
	next.attr("selected","selected");
    $("td.calSelected[date-map='"+value+"']").removeClass("calSelected");
}

function selectDateCallBack(data){
	var date=data.selectedDate;
	if(!date)
		return;
	if($("#selDate option[value='"+date+"']").length!=0)//once allowed
		return;
	$("#selDate").append("<option value='"+date+"'>"+date+"</option>");
}

function cancelDateCallBack(data){
    var date=data.selectedDate;
    if(!date)
        return;
    $("#selDate option[value='"+date+"']").remove();
}
