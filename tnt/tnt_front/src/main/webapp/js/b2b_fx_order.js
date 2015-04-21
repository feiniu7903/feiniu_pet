	
//选项卡		

$('.nav li').click(function(){
	var _num = $(this).index();
	$(this).addClass('active').siblings().removeClass('active');
	$(this).parents('.order').find('.content').eq(_num).show().siblings('.content').hide();
})

//日历
$(function () {
	// 级联
    function maxDay() {
        var d1 = $(".J_calendar").eq(0).val().split('-'),
            d2 = $(".J_calendar").eq(1).val().split('-'),
            day = 0;

        d1 = new Date(parseInt(d1[0], 10), parseInt(d1[1], 10) - 1, parseInt(d1[2], 10));
        d2 = new Date(parseInt(d2[0], 10), parseInt(d2[1], 10) - 1, parseInt(d2[2], 10));
        day = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);

        return (day > 20);
    }
 
    function selectDateCallback() {
        var d1 = $(this.options.trigger).eq(0).val().split('-'),
        d2 = $(this.options.trigger).eq(1).val().split('-'),
        day = 0,
        nd = null,
        offset = null,
        week = "",
        d3 = $(this.options.trigger).eq(0).val().split('-'),
        d4 = $(this.options.trigger).eq(1).val().split('-'),
        d = {};

        d1 = new Date(parseInt(d1[0], 10), parseInt(d1[1], 10) - 1, parseInt(d1[2], 10));
        d2 = new Date(parseInt(d2[0], 10), parseInt(d2[1], 10) - 1, parseInt(d2[2], 10));
        day = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);
        offset = $(this.options.trigger).eq(1).offset();

        // hack 
        if (this.$trigger.attr("data-check") === "checkIn") {

            if (day <= 0) {
                nd = this.addDays(d1, 1);
                week = this.setToday({
                    year: parseInt(nd.getFullYear(), 10),
                    month: parseInt(nd.getMonth() + 1, 10),
                    day: parseInt(nd.getDate(), 10)
                });
                // 填充文案
                if (typeof week === "string") {
                    $(this.options.trigger).eq(1).siblings("span.date-info").find("i.weekday").html(week);
                } else {
                    $(this.options.trigger).eq(1).siblings("span.date-info").find("i.weekday").html(this.options.weeks[this._weekIndex + 1 === 7 ? 0 : this._weekIndex + 1]);
                }
                $(this.options.trigger).eq(1).val(nd.getFullYear() + "-" + this.mend(nd.getMonth() + 1) + "-" + this.mend(nd.getDate()));
            }
            d = {
                year: parseInt(d3[0], 10),
                month: parseInt(d3[1], 10),
                day: parseInt(d3[2], 10)
            };
            week = this.setToday(d);

            // 填充文案
            if (typeof week === "string") {
                this.$trigger.siblings("span.date-info").find("i.weekday").html(week);
            } else {
                this.$trigger.siblings("span.date-info").find("i.weekday").html(this.options.weeks[this._weekIndex]);
            }

            $(this.options.trigger).eq(1).click();
            return false;
        } else {

            d = {
                year: parseInt(d4[0], 10),
                month: parseInt(d4[1], 10),
                day: parseInt(d4[2], 10)
            };
            week = this.setToday(d);

            // 填充文案
            if (typeof week === "string") {
                this.$trigger.siblings("span.date-info").find("i.weekday").html(week);
            } else {
                this.$trigger.siblings("span.date-info").find("i.weekday").html(this.options.weeks[this._weekIndex]);
            }
        }
    }
    var calendar = pandora.calendar({
        trigger: ".J_calendar",
        triggerClass: "J_calendar",
        selectDateCallback: selectDateCallback,
        cascade: {
            days: 1, // 天数叠加一天 
            trigger: ".J_calendar",
            isTodayClick: false
        },
        template: {
            warp: '<div class="ui-calendar ui-calendar-mini"></div>',
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
})