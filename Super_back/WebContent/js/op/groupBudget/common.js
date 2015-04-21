//工具类
var Utils = {};

/**
 * 设置<select>的数据源
 * url : 数据源地址
 * id : select 元素id
 */
Utils.setComboxDataSource = function(url, id, startWithEmpty, selectedValue){
	var data = {};
	$.post(
			url,
			data,
			function(data){
				$('#' + id).empty();
				if(startWithEmpty){
					$('#' + id).append('<option value="">请选择</option>');
				}
				for(var i in data){
					$('#' + id).append('<option value="' + data[i].value + '">' + data[i].label + '</option>');
				}
				if(selectedValue != undefined){
					$('#' + id).val(selectedValue);
				}
			},
			"json"
	);
};
/**
 * 获取select选中项的Label
 * @param {} id
 * @param {} selectedValue
 * @return {String}
 */
Utils.getSelectedLabel = function(id, selectedValue){
	var ops = $("#" + id + "").children();
	if(ops.length == 0){
		return "";
	}
	for (var i = 0; i < ops.length; i++) {
		if (ops[i].value == selectedValue) {
			return ops[i].text;
		}
	}
	return "";
}

/**
 * 初始化jqGrid
 */
$.fn.grid = function( option ,pageBarOption ){
	var _form = $(option.former);
	var _url;
	if(_form.length <= 0 ){
		_url = option.url;
	}else{
		var queryString = _form.formSerialize();
		_url = _form.attr("action")+"?"+queryString;
	}
	var finalConfig = {
			prmNames:{page:"page.currpage",rows:"page.pagesize",sort: "page.orderby",order: "page.order"},
			jsonReader : {
		        root:"records", 
		        page: "currpage", 
		        total: "page.totalpages", 
		        records: "page.records", 
		        repeatitems: false, 
		        id: "0" 
		    },  
		    datatype : "json",
		    url : _url
	};
	var config = {
			autowidth: true,
			rowNum : 50,
			rowList : [15, 50 ,100, 300 , 500 ],
			height:"100%",
			viewrecords: true
	};
	var pageBarConfig = {
			edit : false,
			add : false,
			del : false,
			search: false,
			refreshstate:"current"
	};
	$.extend( config , option );
	$.extend( config , finalConfig );
	if(typeof pageBarOption == "undefined"){
		pageBarOption = {};
	}
	$.extend( pageBarConfig , pageBarOption );
	var gridId = $(this).attr("id");
	if($(this).length>0){
		$(this).GridUnload();
	}
	var complete = config.gridComplete;
	var parent_div = $("#"+gridId).parent();
	var complete_fix_width = function(){
		$("#"+gridId).setGridWidth(parent_div.width());
		if(typeof complete != "undefined"){
			complete.apply();
		}
	};
	$.extend( config , { gridComplete : complete_fix_width });
	$("#"+gridId).jqGrid(config).navGrid();
	$("#"+gridId).jqGrid('navGrid', config.pager, pageBarConfig);
};

/**
 * 初始化autocomplete
 */
$.fn.combox = function(config) {
	var options = {source : "",minLength : 1};
	if(typeof config == "string"){
		options.source = config;
	}else{
		$.extend(options,config);
	};
	$(this).autocomplete(options).data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a>" + item.label +  "</a>" )
		.appendTo( ul );
	};
};

/**
 * 数组转json
 * @param o
 * @returns
 */
Utils.arrayToJson = function (o) { 
	var r = []; 
	if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\""; 
	if (typeof o == "object") { 
	if (!o.sort) { 
	for (var i in o) 
	r.push(i + ":" + Utils.arrayToJson(o[i])); 
	if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) { 
	r.push("toString:" + o.toString.toString()); 
	} 
	r = "{" + r.join() + "}"; 
	} else { 
	for (var i = 0; i < o.length; i++) { 
	r.push(Utils.arrayToJson(o[i])); 
	} 
	r = "[" + r.join() + "]"; 
	} 
	return r; 
	} 
	return o.toString(); 
}
