// JavaScript Document
function autoComp(init_user){
	var init={
		$input:$(".autoCInput"),//
		$out:$(".iflt_autoC_out"),
		listOut:"iflt_autoCListBox",
		inputClass:"autoCInput",
		listClass:"iflt_autoCList",
		crtClass:"iflt_autoCrt",
		pageWrap:"auto_pageWrap",
		str: "中文/英文",
		top: 0,
	    fixed:false
	}
	this.init=$.extend(init,init_user);	
}
autoComp.prototype={
    start: function () {
        var _this = this, _init = _this.init;
        var listHtml = "<div class='" + _init.listOut + "'  style='width:320px' ><ul class='" + _init.listClass + "'></ul><p class='" + _init.pageWrap + "'></p></div>"; //<p class='"+_init.pageWrap+"'></p>
		_init.$input.after(listHtml);
		this.formEvt();
		this.judgeInput();
		this.autoAjax();
		this.bindClick();
		this.keyEvent();
		this.inputBlur();
	},
	formEvt:function(){
		$("form").keydown(function(e){
			if(/^13$/.test(e.keyCode)){return false;}
		})
	},
	judgeInput:function(){
		var _this=this, _init=_this.init;
		_init.$input.click(function () {
			var val=$(this).val();
			if(val==_init.str){
				$(this).val("");
			}//
		})
	},
	autoAjax:function(){
		var _this=this, _init=_this.init;
		var $list=$("."+_init.listClass);
		_init.$input.keyup(function(e){
			if(/^13|37|38|39|40$/.test(e.keyCode)){//回车｜左｜上｜右｜下
				return;	
			}
			var $this=$(this);
			var keyword = $this.val();
			var css = '';
			if (keyword != "") {
			    $("#js-hot-city").hide();
			    if (_init.fixed) {
			        var offset = $this.offset(),
                        left = offset.left,
                        top = offset.top + _init.top;
			        css = 'width:320px; top:' + top + 'px;left:' + left + 'px';
			        $this.siblings('div[class=iflt_autoCListBox]').attr('style', css);
			    }
			    //var url="http://www.lvmama.com/search/oneSearch!getAutoCompletePlace.do?callback=?&keyword="+keyword+"&fromDestId=79&fromChannel=&newChannel=";
			    var url = "http://www.lvmama.com/product/seachTrainPlace.do?callback=?";
			    //var url="http://v2.fare2go.com/citys.php?q="+keyword+"&limit=10&timestamp="+new Date().getTime(); 
				$.post(url,{"search":keyword},function(data){
					if(data){
						var item = data;
						if(!item||item.length<=0){
							$(this).next().hide();
						}else{
							var arr=[];
							$.each(item,function(i,n){
								arr.push("<li><a href='javascript:void(0)' key='"+n.pinyin+"' >"+n.name+"</a></li>");
							})//each
							$("#js-address-hot").css({ left: 0, top: 0, display: "none" });
							$this.next().show().find("."+_init.listClass).html(arr.join("")).find("li").eq(0).addClass(_init.crtClass);
							    var $pageList=$this.next().find("li");
								var lv_page1=new lv_page({
									pSize:10,
									$list:$pageList,
									$pageWrap:$("."+_init.pageWrap),
									pageCrt:"auto_pageCrt",
									dayBox: "auto_page_dayBox",
									pagePrev: "auto_pagePrev",
									pageNext: "auto_pageNext",
									pageInput: "auto_pageInput",
									pageSure: "auto_pageSure"
								});
								lv_page1.start();
						}//item
					}else{
						$this.next().hide();
					}//dada	
				},"json")//json
			}//keyword
		})//keyup
	},//autoAjax
	bindClick:function(){
		var _this=this, _init=_this.init;
		var $list=$("."+_init.listClass);
		$list.delegate("li","click",function(){
			var cityID = $(this).parents("."+_init.listOut).prev().attr('cityID');
			$(cityID).val($(this).find('a').attr('key'));
			$(this).parents("."+_init.listOut).prev().val($(this).text()).end().hide();
		})//
	},
	keyEvent:function(){
		var _this=this, _init=_this.init;
		_init.$input.keydown(function(e){
			var $outlist=$(this).next();
			var $crtli=$outlist.find("."+_init.listClass+">li."+_init.crtClass);
			if(/^13|38|40$/.test(e.keyCode)){//回车/上/下
				switch(e.keyCode){
					case 13:
						$outlist.prev().val($crtli.text()).end().hide();
						var hiddenInput = $($outlist.prev().attr('cityid'));
						hiddenInput.val($crtli.find('a').attr('key'));
						break;
					case 38:
						$crtli.removeClass(_init.crtClass).prev().addClass(_init.crtClass);
						break;
					case 40:
					    $crtli.removeClass(_init.crtClass).next().addClass(_init.crtClass);
						break;		
				}//switch
			}
		});
	},
	inputBlur:function(){
		var _this=this, _init=_this.init;
		var $list=$("."+_init.listClass);
		$(_init.$out).click(function (e) {
		    var $target = $(e.target);
		    if (!$target.hasClass(_init.inputClass) && !$target.hasClass(_init.listClass) && !$target.parents().hasClass(_init.listOut)) {
			   $list.parent().hide();
			}//
		});
	}
}//autoComp