var lv_page = function (opt) {
    var init = {
        pCrt: 1,
        pSize: 4,
        pShowNum: 6,
        $list: $(".lv_page_proList>li"),
        $pageWrap: $(".lv_pageWrap"),
        pageCrt: "lv_pageCrt",
        dayBox: "lv_page_dayBox",
        pagePrev: "lv_pagePrev",
        pageNext: "lv_pageNext",
        pagePrevActive: "lv_pagePrevActive",
        pageNextActive: "lv_pageNextActive",
        pageNext: "lv_pageNext",
        pageInput: "lv_pageInput",
        pageSure: "lv_pageSure",
        pageType:1
    }
    this.init = $.extend(init, opt);
}//init
lv_page.prototype = {
    start: function () {
        var _this = this, _init = _this.init;
        var totalItem = _init.$list.length;
        var totalPage = 1;
        if (totalItem > _init.pSize * 10) {
            _init.pSize = parseInt(totalItem / 10);
        }
        totalPage = totalItem % parseInt(_init.pSize) ? parseInt(totalItem / _init.pSize) + 1 : parseInt(totalItem / _init.pSize);
        _this.showItem(totalItem, totalPage);
        _this.showPage(totalItem, totalPage);
    },
    repairIndex: function (totalItem, totalPage, page) {
        var _this = this,
            _init = _this.init,
            len = totalPage > page ? _init.$list.eq(page * _init.pSize - 1).index() : _init.$list.eq(totalItem - 1).index();
        return len;
    },
    showItem: function (totalItem, totalPage) {
        var _this = this, _init = _this.init;
        var crtPage = _init.pCrt;
        var startIndex = crtPage === 1 ? _init.$list.eq(0).index() : _this.repairIndex(totalItem, totalPage, crtPage - 1) + 1;
        var endIndex = _this.repairIndex(totalItem, totalPage, crtPage);
        _init.$list.each(function () {
            $(this).index() >= startIndex && $(this).index() <= endIndex ? $(this).show() : $(this).hide();
        });
    },
    showPage: function (totalItem, totalPage) {
        var _this = this, _init = _this.init;
        var NumStr = "";
        for (var i = 1; i <= totalPage; i++) {
            NumStr += "<span>" + i + "</span>";
        }
        ;
        if (NumStr === '') {
            NumStr += "<span>" + 1 + "</span>"; 
        }
        if(_init.pageType == 1 || _init.pageType == null){
			var activeNext = totalPage > 1 ? _init.pageNextActive : '';
            var HtmlStr = '<span class="' + _init.pageNext + ' ' +  activeNext +'"><-</span><p class="' + _init.dayBox + '">' + NumStr +
                '</p><span class="' + _init.pagePrev + '">-></span>';
        }else if(_init.pageType == 2 ){
            var HtmlStr = '<span class="' + _init.pagePrev + '"></span><p class="' + _init.dayBox + '">' + NumStr +
                '</p><span class="' + _init.pageNext + '"></span>';
        }
        _init.$pageWrap.html(HtmlStr);

        $("." + _init.dayBox + "").each(function (i) {
            $("." + _init.dayBox + "").eq(i).find(">span").eq(_init.pCrt - 1).addClass(_init.pageCrt);
        })
        _this.pageClick(totalItem, totalPage);
    },
    pageClick: function (totalItem, totalPage) {
        var _this = this, _init = _this.init;

        $("." + _init.pagePrev).unbind('click');
        $("." + _init.pageNext).unbind('click');
        $("." + _init.dayBox + ">span").unbind('click');

        $("." + _init.pagePrev).click(function () {
			if(totalPage == 1){
				return false;
			}
            _init.pCrt - 1 < 1 ? 1 == 1 : _init.pCrt = _init.pCrt - 1;
            if(_init.pCrt == 1){
                $("." + _init.pagePrev).removeClass(_init.pagePrevActive);
                $("." + _init.pageNext).addClass(_init.pageNextActive);
            }else{
                $("." + _init.pagePrev).addClass(_init.pagePrevActive);
                $("." + _init.pageNext).addClass(_init.pageNextActive);
            }
            _this.showItem(totalItem, totalPage);
            $(this).parent().find("." + _init.dayBox + ">span").eq(_init.pCrt - 1).addClass(_init.pageCrt).siblings().removeClass(_init.pageCrt);
        })//prev
        $("." + _init.pageNext).click(function () {
			if(totalPage == 1){
				return false;
			}
            _init.pCrt + 1 > totalPage ? 1 == 1 : _init.pCrt = _init.pCrt + 1;
            if(_init.pCrt == totalPage){
                $("." + _init.pageNext).removeClass(_init.pageNextActive);
                $("." + _init.pagePrev).addClass(_init.pagePrevActive);
            }else{
                $("." + _init.pageNext).addClass(_init.pageNextActive);
                $("." + _init.pagePrev).addClass(_init.pagePrevActive);
            }
            _this.showItem(totalItem, totalPage);
            $(this).parent().find("." + _init.dayBox + ">span").eq(_init.pCrt - 1).addClass(_init.pageCrt).siblings().removeClass(_init.pageCrt);
        })//next
        $("." + _init.dayBox + ">span").click(function () {
            $(this).addClass(_init.pageCrt).siblings().removeClass(_init.pageCrt);
            _init.pCrt = parseInt($(this).text());
            if(_init.pCrt == 1){
                $("." + _init.pagePrev).removeClass(_init.pagePrevActive);
                $("." + _init.pageNext).addClass(_init.pageNextActive);
            }else if(_init.pCrt == totalPage){
                $("." + _init.pagePrev).addClass(_init.pagePrevActive);
                $("." + _init.pageNext).removeClass(_init.pageNextActive);
            }
            _this.showItem(totalItem, totalPage);
        })//day
        $("." + _init.pageSure).live("click", function () {
            var num = parseInt($("." + _init.pageInput).val());
            if (!isNaN(num) && num > 0 && num <= totalPage) {
                _init.pCrt = parseInt($(this).parent().find("." + _init.pageInput).val());
                _this.showItem(totalItem, totalPage);
                $(this).parent().find("." + _init.dayBox + ">span").eq(_init.pCrt - 1).addClass(_init.pageCrt).siblings().removeClass(_init.pageCrt);
            } else {
                alert("您确认输入的页数是存在的");
            }
        })//Sure
    }
}//prototype