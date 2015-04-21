(function($) {
	var provinceSelect = null;
	var citySelect = null;
	var cityId = null;
	var provinceId = null;
	var divHtml = null;
	$.fn.loadProvinceCity = function() {

		var $this = $(this);
		divHtml = $(this);
		provinceSelect = $("<select id=\"" + $this.attr("id")
				+ "province\" name=\"province\"></select>");
		citySelect = $("<select id=\"" + $this.attr("id")
				+ "city\" name=\"city\"></select>");
		cityId = $this.attr("city");
		
		if(cityId!="undefined"){
		if(cityId!=null&&""!=cityId){
				provinceId = $.fn.loadProvinceCity.getProvinceId(cityId);
		}
		}
		$.ajax( {
			type : "POST",
			dataType : "json",
			url : $.fn.loadProvinceCity.getHost()+"/super_back/common/loadProvince.do",
			async : false,
			beforeSend : function() {
				$this.html("<img src=\"http://pic.lvmama.com/img/loading.gif\"/>loading...");
			},
			timeout : 3000,
			error : function(a, b, c) {
				if (b == "timeout") {
					alert("地址请求超时");
				} else if (b == "error") {
					alert("无法请求到服务器地址");
				}
				$this.html("");
			},
			success : function(data) {
				$this.html("");
				provinceSelect.change(function() {
					var ps = $(this)
					$.fn.loadProvinceCity.loadCity(ps.parent(), ps.val());
				});
				//alert(data.provinceList.length)
				for ( var i = 0; i < data.provinceList.length; i++) {
					var pro = data.provinceList[i];
					var option = $("<option value=\"" + pro.provinceId + "\">"
							+ pro.provinceName + "</option>");
					provinceSelect.append(option);

				}
				$this.append(provinceSelect);

			}

		});
		$.fn.loadProvinceCity.initCityProvince();
	}
	
	$.fn.loadProvinceCity.getProvinceId = function(cityId){
		var id = null;
		$.ajax({
			type : "POST",
			dataType : "json",
			url : $.fn.loadProvinceCity.getHost()+"/super_back/common/loadCity.do",
			data : {
				cityId : cityId
			},
			async : false,
			beforeSend : function() {
			},
			timeout : 3000,
			error : function(a, b, c) {
				if (b == "timeout") {
					alert("地址请求超时");
				} else if (b == "error") {
					alert("无法请求到服务器地址");
				}
			},
			success : function(data) {
				id = data.comCity.provinceId;
				
			}

		});
		return id;
	}
	
	$.fn.loadProvinceCity.loadCity = function(div, provinceId) {
		$.ajax( {
			type : "POST",
			dataType : "json",
			url : $.fn.loadProvinceCity.getHost()+"/super_back/common/loadCities.do",
			data : {
				provinceId : provinceId
			},
			async : false,
			beforeSend : function() {
			},
			timeout : 3000,
			error : function(a, b, c) {
				if (b == "timeout") {
					alert("地址请求超时");
				} else if (b == "error") {
					alert("无法请求到服务器地址");
				}
			},
			success : function(data) {
				if (citySelect.html() != "") {
					citySelect.html("");
				}
				for ( var i = 0; i < data.cityList.length; i++) {
					var city = data.cityList[i];
					var option = $("<option value=\"" + city.cityId + "\">"
							+ city.cityName + "</option>");
					citySelect.append(option);

				}
				div.append(citySelect);

			}

		});
	}

	$.fn.loadProvinceCity.initCityProvince = function() {
		if (provinceId != null && "" != provinceId) {
			provinceSelect.children().each(function() {

				if ($(this).val() == provinceId) {
					$(this).attr("selected", "true");
				}
			});
		}

		if (cityId != null && "" != cityId) {
			if (divHtml != null) {
				$.fn.loadProvinceCity.loadCity(divHtml, provinceId);
			}

			citySelect.children().each(function() {
				if ($(this).val() == cityId) {
					$(this).attr("selected", "true");
				}
			});

		}
	}
	
	$.fn.loadProvinceCity.getHost = function(url) { 
         var host = null;
         if(typeof url == "undefined"
                         || null == url)
                 url = window.location.href;
         var regex = /(.*\:\/\/[^\/]*).*/;
         var match = url.match(regex);
         if(typeof match != "undefined"
                         && null != match)
                 host = match[1];
         return host;
	}


})(jQuery);