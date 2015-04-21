(function ($) {
    $.calendar = {
        init: function (settings) {
            var now = new Date(),
                year = now.getFullYear(),
                month = now.getMonth(),
                that = this;
            this.settings = settings;
            $(settings.inputWrap).click(function () {
                that.$input = $(settings.input);
                if ($('#js-calendar').length > 0) {
                    var offset = that.offset();
                    $('#js-calendar').css({ "top": offset.top, "left": offset.left }).show();
                    return;
                }
                that.draw(month, year);
                that.selectMonth();
                that.selectDate();
            });
            this.inputBlur(settings);
        },
        draw: function (month, year, $obj) {
            var nextMonth = month + 1 > 11 ? 0 : month + 1,
                nextYear = month + 1 > 11 ? year + 1 : year,
                prevTitle = year + '年' + (month + 1) + '月',
                nextTitle = nextYear + '年' + (nextMonth + 1) + '月',
                prevCalendar = this.drawMonth(this.getDayIndex(month, year), month, year),
                nextCalendar = this.drawMonth(this.getDayIndex(nextMonth, nextYear), nextMonth, nextYear);
            this.month = [month, nextMonth];
            this.year = [year, nextYear];
            if ($obj !== undefined) {
                $obj.find('span').eq(0).text(prevTitle);
                $obj.find('span').eq(1).text(nextTitle);
                $obj.find('dd').eq(0).html(prevCalendar);
                $obj.find('dd').eq(1).html(nextCalendar);
            } else {
                var offset = this.offset(),
                    wrap = $('<div id="js-calendar" class="calendar-wrap" style="top:' + offset.top + 'px;left:' + offset.left + 'px"></div>').prepend(this.template().replace('{monthPrev}', prevTitle).replace('{PrevMonthContent}', prevCalendar).replace('{monthNext}', nextTitle).replace('{NextMonthContent}', nextCalendar));
                $('body').prepend(wrap);
            }
        },
        drawMonth: function (firstIndex, month, year) {
            var days = this.getDatesByMonth(month, year);
            return this.dateLayout(firstIndex, days, month, year);
        },
        dateLayout: function (firstIndex, days, month, year) {
            var html = '',
                today = this.getToday(),
                settings = this.settings,
                fatalism = settings.fatalism,
                day = this.isDay();
            for (var i = 0; i < 42; i++) {
                if (i < firstIndex || i >= days + firstIndex) {
                    html += '<a class="day-no" href="javascript:;"></a>';
                } else {
                    if (year === today.year && today.month === month && i < (today.date + 2 + firstIndex)) {
                        if (i === (today.date + firstIndex - 1)) {
                            html += '<a class="day-over" style="background:#d17; color:fff" week=' + i % 7 + '  name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + '  href="javascript:;">' + (i - firstIndex + 1) + '</a>';
                            continue;
                        }
                        html += '<a class="day-over" href="javascript:;">' + (i - firstIndex + 1) + '</a>';
                    } else {
                        if (fatalism === 0) {
                            html += '<a href="javascript:;" week=' + i % 7 + ' name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                            continue;
                        }
                        if (year === today.year && today.month === month) {
                            if (today.date + fatalism + firstIndex - 1 > i) {
                                html += '<a href="javascript:;" week=' + i % 7 + '  name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                            } else {
                                html += '<a href="javascript:;" class="day-over" week=' + i % 7 + ' name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                            }
                        } else {
                            if (day < 0 && month === (today.month + 1)%12) {
                                if (firstIndex > i + day + 1) {
                                    html += '<a href="javascript:;" week=' + i % 7 + ' name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                                } else {
                                    html += '<a href="javascript:;" class="day-over" week=' + i % 7 + ' name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                                }
                            } else {
                                html += '<a href="javascript:;" class="day-over" week=' + i % 7 + ' name=' + year + '-' + this.mend(month + 1) + '-' + this.mend(i - firstIndex + 1) + ' >' + (i - firstIndex + 1) + '</a>';
                            }
                        }
                    }
                }
            }
            return html;
        },
        mend: function (value) {
            return value.toString().length === 1 ? "0" + value : value;
        },
        getDayIndex: function (month, year) {
            var ytd = year + '/' + (parseInt(month) + 1) + '/' + 1,
                week = new Date(ytd).getDay();
            return week;
        },
        getToday: function () {
            var d = new Date();
            return { year: d.getFullYear(), month: d.getMonth(), date: d.getDate() };
        },
        getDatesByMonth: function (month, year) {
            var monthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0)) ? monthDays[1] = 29 : null;
            return monthDays[month];
        },
        selectDate: function () {
            //后续改进
            var that = this;
            $('.calendar-day').find('a[class!=day-no][class!=day-over]').live('click', function () {
                that.$input.val($(this).attr('name'));
                if (that.settings.showWeek) {
                    $('.text-info').html(that.settings.weeks[$(this).attr('week')]);
                }
                $('.calendar-wrap').hide();
            });
        },
        selectMonth: function () {
            var that = this;
            $('.month-prev, .month-next').click(function () {
                var month = that.month,
                    year = that.year;
                if ($(this).attr('class') === 'month-next') {
                    that.isMonth('next') && $(this).hide();
                    $.calendar.draw(month[1], year[1], $('.calendar-wrap'));
                    $('.month-prev').show();
                } else {
                    that.isMonth() && $(this).hide();
                    $.calendar.draw((month[0] - 1 < 0 ? 11 : month[0] - 1), (month[0] - 1 < 0 ? year[0] - 1 : year[0]), $('.calendar-wrap'));
                    $('.month-next').show();
                }
            });
        },
        isMonth: function (turn) {
            var year = this.year,
                month = this.month,
                today = this.getToday(),
                mos = this.settings.mos,
                bool = true;
            if (turn === 'next') {
                bool = mos !== 0 ? ((year[1] - today.year) * 11 + month[1] - today.month) >= this.settings.mos ? true : false : false;
            } else {
                bool = year[0] === today.year ? month[0] - 1 >= 0 ? month[0] - 1 === today.month ? true : false : true : false;
            }
            return bool;
        },
        isDay: function () {
            var fatalism = this.settings.fatalism,
                today = this.getToday(),
                days = this.getDatesByMonth(today.month, today.year);
            return days - today.date - fatalism;
        },
        inputBlur: function (settings) {
            $(document).click(function (e) {
                var target = $(e.target);
                if (!(target.hasClass(settings.inputWrapClass) || target.parents().hasClass(settings.inputWrapClass)) && !target.parents().hasClass(settings.pageWrap)) {
                    $('.calendar-wrap').hide();
                }
            });
        },
        offset: function () {
            //后续要改进
            var $input = $(this.settings.inputWrap),
                offset = $input.offset(),
                left = offset.left,
                top = offset.top + $input.height() + 2;
            return { left: left, top: top };
        },
        template: function () {
            var html = '';
            html = '    <div id="calendar0" class="calendar-month">' +
                   '        <div  class="calendar-title">' +
                   '            <a class="month-prev" href="javascript:;" style="display:none"></a><span>{monthPrev}</span>' +
                   '        </div>' +
                   '        <dl class="calendar-day">' +
                   '            <dt class="weekend">日</dt>' +
                   '            <dt>一</dt>' +
                   '            <dt>二</dt>' +
                   '            <dt>三</dt>' +
                   '            <dt>四</dt>' +
                   '            <dt>五</dt>' +
                   '            <dt class="weekend">六</dt>' +
                   '            <dd>' +
                   '                {PrevMonthContent}' +
                   '            </dd>' +
                   '        </dl>' +
                   '    </div>' +
                   '    <div id="calendar1" class="calendar-month">' +
                   '        <div class="calendar-title">' +
                   '            <a class="month-next" href="javascript:;"></a><span>{monthNext}</span>' +
                   '        </div>' +
                   '        <dl class="calendar-day">' +
                   '            <dt class="weekend">日</dt>' +
                   '            <dt>一</dt>' +
                   '            <dt>二</dt>' +
                   '            <dt>三</dt>' +
                   '            <dt>四</dt>' +
                   '            <dt>五</dt>' +
                   '            <dt class="weekend">六</dt>' +
                   '            <dd>' +
                   '                {NextMonthContent}' +
                   '            </dd>' +
                   '        </dl>' +
                   '</div>';
            return html;
        }
    }
    $.fn.calendar = function (options) {
        var settings = {
            inputWrap:'.calendar',
            input: '.calendar',
            inputWrapClass: 'calendar',
            pageWrap: 'calendar-wrap',
            weeks: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            showWeek: false,
            hour: 0,
            fatalism: 0,
            mos: 6
        };
        $.extend(true, settings, options || {});
        $.calendar.init(settings);
    }
})(jQuery);