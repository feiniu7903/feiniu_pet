/**
 * Created with JetBrains WebStorm.
 * User: chenyanling
 * Date: 13-10-16
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 * 调用方式：    $('.tab').sortModel();
 */

$.fn.sortModel = function (options) {
    "use strict";
    var defaults = {
        sortWrap: '.sort-wrap', 
        sortTag: '.sort-tag',   
        sortEle: '.sort-ele',  
        sortVal: '.sort-val',  
        dataType: 'data-type',
        sortUp: 'sort-up',
        sortDown:'sort-down'
    };
    var _options = $.extend({}, defaults, options);

    //初始化
    this.each(function () {
        var self = this;
        $(self).find(_options.sortTag).on("click", function () {
            var index = $(self).find(_options.sortTag).index($(this));
            var _bool = $(this).hasClass(_options.sortUp);
            $(self).find(_options.sortTag).removeClass(_options.sortUp).removeClass(_options.sortDown);
            if(_bool){
                $(this).addClass(_options.sortDown);
            }else{
                $(this).addClass(_options.sortUp);
            }
            var dataType = $(this).attr(_options.dataType);
            _init.sortTab(index, $(self), dataType, _bool);
        });
    });
    var _init = {

        //根据类型获得元素的值，可根据情况进行添加新的内容
        getValue: function (value, valueType) {
            switch (valueType) {
                case "int":
                    return parseInt(value);
                    break;
                case "float":
                    return parseFloat(value);
                    break;
                case "string":
                    return value.toString();
                    break;
                case "date":
                    var arr = value.split('-');
                    var time = new Date(arr[0], arr[1], arr[2]);
                    return time.getTime();
                    break;
                case "startTime":
                    var arrT = value.split(':');
                    var times = arrT[0] * 60 + arrT[1];
                    times = parseInt(times);
                    return times;
                    break;
                case "timing":
                    var hoursIndex = value.indexOf('时');
                    var hours = Number(value.substring(0, hoursIndex));
                    var minues = Number(value.substring(hoursIndex + 1, value.length - 1)) || 0;
                    var wholeTime = hours * 60 + minues;
                    wholeTime = parseInt(wholeTime);
                    return wholeTime;
                    break;
                default:
                    return value.toString();
            }
        },

        //sort的方法
        sortValue: function (index, valueType, _bool) {
            return function (a, b) {
                var value1 = _init.getValue(a.find(_options.sortVal).eq(index).html(), valueType);
                var value2 = _init.getValue(b.find(_options.sortVal).eq(index).html(), valueType);
                if (_bool) {
                    if (value1 < value2) {
                        return 1;
                    }
                    else if (value1 > value2) {
                        return -1;
                    }
                    else {
                        return 0;
                    }
                } else {
                    if (value1 < value2) {
                        return -1;
                    }
                    else if (value1 > value2) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            };
        },

        //利用sort进行排序
        sortTab: function (index, tab, valueType, _bool) {
            var eleArr = [],
                sortElement = tab.find(_options.sortEle),
                trLen = sortElement.length;
            for (var i = 0; i < trLen; i++) {
                eleArr[i] = sortElement.eq(i);
            }
            eleArr.sort(_init.sortValue(index, valueType, _bool));
            var fragment = document.createDocumentFragment();
            for (var j = 0; j < trLen; j++) {
                fragment.appendChild(eleArr[j].get(0));
            }
            tab.find(_options.sortEle).empty();
            tab.append(fragment);
        }
    };

};
