$(function() {
	$.ajaxSetup ({
		cache: false 
	});
	$("input[type='text']").keypress( function(e) {
        var key = window.event ? e.keyCode : e.which;
        if(key.toString() == "13"){
                    return false;

        }
});
	var _Utils = {};
	_Utils.setComboxDataSource = function(url, id){
		$.get(
				url,
				function(data){
					for(var i in data){
						$('#' + id).append('<option value="' + data[i].value + '">' + data[i].label + '</option>');
					}
				}
		);
	};
	
	_Utils.showLog = function(type, id){
		var url = "/finance/log/search/"+type+"/"+id+".htm";
		$.get(url, function(data){
			art.dialog({
				title: "操作日志",
			    id: "log-"+id +"-dialog",
			    content: data,
			    lock:true
			});
		});
	};
	
	_Utils.lockPage = function(){
		_Utils.lockPageWin = art.dialog({
			lock:true,
			esc:false,
			title:'请稍等'
		});
		$(".d-close").hide();
	};
	
	_Utils.unlockPage = function(){
		if(_Utils.lockPageWin){
			_Utils.lockPageWin.close();
		}
	};
	
	window.Utils = _Utils;
	
	$.validator.addMethod("amountfloat", function(value, element) {
			var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
		    return reg.test(value) && parseFloat(value) > 0;   
		  }, "请输入一个正数，最多只能有两位小数");   
});
(function( $ ) {
		
	$.fn.grid = function( option ,pageBarOption ){
		var _form = $(option.former);
		var _url;
		if(_form.length <= 0 ){
			_url = option.url;
		}else{
			_url = _form.attr("action");
		}
		//不允许改变的配置
		var finalConfig = {
				prmNames:{page:"currpage",rows:"pagesize",sort: "orderby",order: "order"},
				jsonReader : {
			        root:"invdata", 
			        page: "currpage", 
			        total: "totalpages", 
			        records: "totalrecords", 
			        repeatitems: false, 
			        id: "0" 
			    },  
			    datatype : "json",
			    url : _url,
			    ajaxGridOptions:{contentType:"application/x-www-form-urlencoded; charset=UTF-8"},
			    serializeGridData: function(postData){
			    	var queryParams =_form.serializeArray();
			    	$.each(postData,function(i,n){
			    		queryParams.push({name:i,value:n});
			    	});
			    	return queryParams;
			    }
		};
		//可以改变的配置
		var config = {
				autowidth: true,
				rowNum : 50,
				rowList : [15, 50 ,100, 300 , 500 ],
				height:"100%",
				viewrecords: true,
			    mtype : "POST"
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
		
		
	},
	$.msg = function(title,time){
		time = typeof time =="undefined"? null:time;
		var _icon = null;
		if(title.indexOf("成功")!=-1){
			_icon='succeed';
		}
		art.dialog({
			fixed: true,
			title: '提示信息',
		    content: title,
	    	lock:true,
	    	time:time,
	    	icon:_icon
		});
	},
	$.fn.dialog = function(config) {
		$(this).click(function() {
			$.get(config.url,function(data){
				art.dialog({
					title: config.title,
				    id: config.id,
				    content: data,
				    lock:true
				});
			});
			}
		);
	};
	$.fn.combox = function(config) {
		var options = {source : "",minLength : 2};
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
	
}(jQuery));