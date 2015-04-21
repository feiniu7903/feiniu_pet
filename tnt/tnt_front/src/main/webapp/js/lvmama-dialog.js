(function (d) { 
d["skin"] = "dialog-blue"; 
d["mask"] = true; 
d["okClassName"] = "btn-ok"; 
d["cancelClassName"] = "btn-cancel"; 
}(pandora.dialog.defaults)); 

function dialog(url, title, width, height, okfunc, okValue) {
	if(!width){
		width="400px";
	}
	if(!height){
		height="400px";
	}
	var path;
	if(url.length>0){
		var index = url.indexOf("?");
		if(index<=0){
			path = url+"?newDate"+new Date();
		}else{
			path = url+"&newDate"+new Date();
		}
	}
	$.ajax({
		url : path,
		type : "get",
		success : function(result) {
			if (result.length > 0) {
				pandora.dialog({
					wrapClass: "dialog-big",
					width: width,
					height: height,
					title : title,
					content : result,
					ok:okfunc,
					okValue : okValue
				});
			}
		}
	});
}

/**
 * 扩展的Dialog
 * 
 * @param url 要提交的URL
 * @param data 要提交的数据， 格式{a:a,b:b}
 * @param params dialog 的参数
 * @param params iframe 是否使用Iframe的方式载入页面
 * @author mayonghua
 * @date 2013-10-15
 */
function xDialog(url,data,params){
	this.params = params ;
	this.url = url;
	this.data = data;
	this.init();
}

xDialog.prototype = {
     init : function(){
    	$.extend(this.params,{content:this.getContent(),drag:true,mask:true});
    	this.dialog =  pandora.dialog(this.params);
     },
	 getContent : function(urlparam){
		 var entity = this;
		 urlparam = urlparam?urlparam:entity.url;
		 //如果是IFRAME方式，则直接返回URL
		 if(entity.params.iframe)
			 return entity.url;
		 var content = "";
		 //加入时间戳解决IE不刷新的问题
		 $.extend(this.data,{timeStamp:this.getTimeStamp()});
		 $.ajax({
				url : urlparam,
				type : "post",
				data : entity.data,
				async : false,
				success : function(result) {
					if (result.length > 0) {
						content = result;
					}
				}
			});
		return content;
	 },
	 reload : function(){
		 this.dialog.content(this.getContent());
	 },
	 
	 reloadUrl : function(urlparam){
		 this.dialog.content(this.getContent(urlparam));
	 },
	 
	 close : function(){
		 this.dialog.close();
	 },
	 getTimeStamp : function(){
		 return Math.random(10);
	 }
}


/**
 * 搜索下拉提示框
 * 
 * @params url 后台请求的URL
 * @params renderTo 下拉结果显示的页面元素
 * @params ele 监听和设置结果的页面元素
 * 
 * @author mayonghua
 * @date 2013-10-16
 */
function xSuggester(params){
	this.url = params.url;
	this.renderTo = typeof params.renderTo === "string" ? $("#"+params.renderTo) : params.renderTo;
	this.ele = typeof params.ele === "string" ? $("#"+params.ele) : params.ele;
	this.suggestValue =typeof params.suggestValue === "string" ? $("#"+params.suggestValue) : params.suggestValue;
	this._init();
}

xSuggester.prototype = {
	_init : function(){
		var entity = this;
		var _bodyDiv = $("<ul>");
		entity._bodyDiv = _bodyDiv;
		entity._bodyDiv.css({"border":"1px solid #D8DCE5",width:entity.ele.width()}).appendTo(this.renderTo).hide();
		entity.ele.bind("input propertychange",function(){
			entity.suggestValue.val("");
			entity.ele.attr("data","");
			var text = $(this).val();
			//if(text == null || text == "")
				//return;
			$.ajax({
				url : entity.url,
				data : {"dictDefName":text},
				type : "GET",
				success : function(eleArray){
					entity._showSuggest(eleArray);
				}
			});
		});
	},
	_showSuggest : function(eleArray){
		if(eleArray.length>0){
			var entity = this;
			entity._bodyDiv.empty().show();
				for(var i=0;i<eleArray.length;i++){
					var dictDef = eleArray[i];
					$("<li>").attr("data",dictDef.dictDefId).text(dictDef.dictDefName).appendTo(entity._bodyDiv).bind("click",function(){
						entity.ele.val($(this).text()).attr("data",$(this).attr("data"));
						entity.suggestValue.val($(this).attr("data"));
						entity._bodyDiv.empty().hide();
					}).bind("mouseover",function(){
						$(this).css("background-color","#c0c0c0");
					}).bind("mouseout",function(){
						$(this).css("background-color","#FFFFFF");
					});
				}
				$("body").click(function(){
					if(entity._bodyDiv.is(":visible")){
						entity._bodyDiv.empty().hide();
					}
				});
		}
	}
}