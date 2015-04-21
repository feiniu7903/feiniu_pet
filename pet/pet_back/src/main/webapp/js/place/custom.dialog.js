(function($) {
	var default_setting = {
		modal : true
	};

	$.ajaxDialog = function() {
		if (arguments.length == 1 && typeof arguments[0] == "object") {
			init_jquery_ui_dialog(arguments[0]);
		} else {
			var config = {
				url : arguments[0], // get方法获取页面内容的url地址
				title : arguments[1], // dialog的title
				id : arguments[2], // dialog的id
				getCallback : arguments[3]
			// get方法执行完成之后的回调方法
			}
			init_jquery_ui_dialog(config);
		}
	}
	/**
	 * config必须要赋值的配置为: 
	 * url:ajax请求的地址 
	 * jQuery dialog的config配置可以自定义设置
	 */
	var init_jquery_ui_dialog = function(config) {
		$.get(config.url, function(data) {
			if (typeof config.id == "undefined") {
				config.id = "undefined_id_dialog";
			}
			var _custom_dialog = $("#" + config.id);
			if (_custom_dialog.length > 0) {
				_custom_dialog.html(data);
			} else {
				_custom_dialog = $("<div id=\"" + config.id
						+ "\" style='display:none'/></div>");
				_custom_dialog.appendTo($("body"));
			}
			_custom_dialog.html(data);
			
			var  newOpen,newBeforeClose;
			var newConfig ={};
			$.extend(newConfig , config, {
				modal : true,
				width : _custom_dialog.outerWidth()+26,
				close : function(event, ui) {
					_custom_dialog.dialog("destroy");
					_custom_dialog.remove();
				}
			});
			if(typeof config.kindEditorId !="undefined"){//如果config包含了KindEditor的id的话 则特殊处理 beforeClose 和 open方法
				newConfig.open = function(event,ui){
					if(typeof config.open !="undefined" ){
						config.open.call(this, event, ui);
					}
					var kindEditorType;
					if(typeof config.kindEditorType != "undefined"){
						kindEditorType = ['plainpaste'];
					}else{
						kindEditorType =['undo', 'redo', '|','plainpaste' ,'|', 'justifyleft', 'justifycenter', 'justifyright',
							    	        'justifyfull', '|', 'indent', 'outdent', 'insertorderedlist', 'insertunorderedlist', 'formatblock' ,
							    	        'forecolor','bold','link', 'unlink',, 'image' , 'table','fullscreen'];
					}
					// 打开Dialog后创建编辑器
					KindEditor.create('#'+config.kindEditorId,{
				    	resizeType : 1,
				    	width:'760px',
				    	height:'500px',
				    	filterMode : true,
				    	items:kindEditorType,
				    	uploadJson:'/pet_back/upload/uploadImg.do',
				    	afterBlur:function(){this.sync(config.kindEditorId);}
				    });
				}
				
				newConfig.beforeClose = function(event, ui) {
					if(typeof config.beforeClose !="undefined" ){
						var res = config.beforeClose.call(this, event, ui);
						if(typeof res !="undefined" && false == res ){
							return res;
						}
					}
					// 关闭Dialog前移除编辑器
					KindEditor.remove('#'+config.kindEditorId);
					if(typeof res !="undefined" ){
						return res;
					}
			}
			}
			_custom_dialog.dialog(newConfig);

			if (typeof config.getCallback != "undefined") {
				config.getCallback.call(this);
			}
		});
	}
}(jQuery));