(function($) {

	/**
	 * 加载网址数据
	 * 
	 * */
	$.fn.showWindow=function(setting){
		var opt={
			width:800,
			modal:true
		};		
		$(this).hide();
		if(this.attr("dialog-content")=='undefined'){
			this.attr("dialog-content","true");
		}
		$.extend(opt,setting);
		if(typeof(opt.url)=="undefined"){
			var url=$(this).attr("url");
			$.extend(opt,{"url":url});
		}
		if(typeof(opt.data)=="undefined"){
			$.extend(opt,{"data":{}});
		}
		return this.each(function(){
			$(this).load(opt.url,opt.data,function(content){		
				if(typeof(opt.title)=="undefined"){
					var re = new RegExp("<title>(.*)</title>","ig");
					var arr = re.exec(content);
					if(arr!=null&&arr.length>0){
						$.extend(opt,{"title":arr[1]});
					}
				}
				var height=opt.minHeight;
				if(typeof(height)=="undefined"){
					$.extend(opt,{"minHeight":$(this).height()+10});
				}
				$(this).dialog(opt);
				//ajax成功后回调
				if (opt.callBack != null&& typeof opt.callBack == 'function') {
					opt.callBack();
				}
			});
		});
	},
	
	$.fn.resetWindow=function(settings){
		var opt={};
		$.extend(opt,settings);
		if(typeof(opt.url)=="undefined"){
			var url=$(this).attr("url");
			$.extend(opt,{"url":url});
		}
		if(typeof(opt.data)=="undefined"){
			$.extend(opt,{"data":{}});
		}		
		return this.each(function(){
			$(this).load(opt.url,opt.data);
		});
	},
	
	$.fn.validateAndSubmit=function(func,settings){
		var opt={onSubmit:function($form){return true;}};
		if(typeof(settings)!="undefined"){
			$.extend(opt,settings);			
		}
		//alert($(this).html());
		$.extend(opt,{submitHandler:function(form){
			var $form=$(form);
			if(typeof(opt.onSubmit)!="undefine"){
				if(false==opt.onSubmit.call(this,$form)){
					return false;//如果这个地方出现处理失败就退回
				}
			}
			$.post($form.attr("action"),
					$form.serialize(),
					function(dt){
						func.call(this,$form,dt);
					}
			);
		}});
		return $(this).validate(opt);
	};
})(jQuery);