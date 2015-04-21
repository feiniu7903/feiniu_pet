(function($) {
	var ui = UI;
	ui.extend.city = function(opt) {
		var option = {
			province : undefined,
			provinceRequest : false,
			city : undefined,
			cityRequest : false
		}
		$.extend(option, opt);
		var elt = $(this);
		var resource = [ ui.site + "plugin/city/json-array-of-province.js",
				ui.site + "plugin/city/json-array-of-city.js",
				ui.site + "plugin/city/json-array-of-district.js" ];
		if (!opt.area) {
			resource[1] = ui.site + "plugin/city/json-array-of-city-new.js";
		}
		ui.getJSON("provinceList", resource[0], function() {
			var item = $.provinceList;
			var arr = opt.provinceRequest?[]:[ "<option value=''>--请选择--</option>" ];
			$.each(item, function(i, n) {
				arr.push('<option value="' + n.code + '">' + n.name
						+ '</option>');
			});
			elt.find(opt.province).html(arr.join(""));
			window.provinceLoaded();
		});
		if (elt.find(opt.province).length > 0) {
			elt.find(opt.province).change(
					function() {
						var _this = this;
						ui.getJSON("cityList", resource[1], function() {
							var item = $.cityList;
							var key = _this.value;
							item = item[key];
							if (!item) {
								return;
							}
							var arr =opt.cityRequest?[]: [ "<option value=''>--请选择--</option>" ];
							for ( var i = 0; i < item.length; i++) {
								var n = item[i];
								arr.push('<option value="' + n.code + '">'
										+ n.name + '</option>');
							}
							elt.find(opt.city).html(arr.join(""));
						});
						window.cityLoaded();
					});
			$("#captialId").change();
		}
		return;
	}
})(jQuery);