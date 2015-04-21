(function($) {
	var provinceSelect = null;
	var citySelect = null;
	var cityId = null;
	var provinceId = null;
	var divHtml = null;
	$.fn.loadRoot = function() {
		var $this = $(this);
		divHtml = $(this);
		provinceSelect = $("<select id=\"" + $this.attr("id")
				+ "province\" name=\"destRoot\"></select>");
		citySelect = $("<select id=\"" + $this.attr("id")
				+ "city\" name=\"destId\"></select>");
		cityId = $this.attr("city");
		if(cityId!="undefined"){
		if(cityId!=null&&""!=cityId){
				provinceId = $.fn.loadRoot.loadComPlace(cityId);
		}
		}
		$.ajax( {
			type : "POST",
			dataType : "json",
			url : $.fn.loadRoot.getHost()+"/super_back/common/loadRootDest.do",
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
					$.fn.loadRoot.loadDest(ps.parent(), ps.val());
				});
				//alert(data.provinceList.length)
				var defaultOption = $("<option value=\"\">请选择</option>");
				provinceSelect.append(defaultOption);
				for ( var i = 0; i < data.comPlaceList.length; i++) {
					var pro = data.comPlaceList[i];
					var option = $("<option value=\"" + pro.placeId + "\">"
							+ pro.name + "</option>");
					provinceSelect.append(option);

				}
				$this.append(provinceSelect);

			}

		});
		$.fn.loadRoot.initCommPlace();
	}
	
	$.fn.loadRoot.loadComPlace = function(placeId){
		var id = null;
		$.ajax({
			type : "POST",
			dataType : "json",
			url : $.fn.loadRoot.getHost()+"/super_back/common/loadComPlace.do",
			data : {
				id : placeId
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
				id = data.comPlace.destId;
				
			}

		});
		return id;
	}
	
	$.fn.loadRoot.loadDest = function(div, id) {
		$.ajax( {
			type : "POST",
			dataType : "json",
			url : $.fn.loadRoot.getHost()+"/super_back/common/loadDestByPlaceId.do",
			data : {
				id : id
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
				var defaultOption = $("<option value=\"\">请选择</option>");
				citySelect.append(defaultOption);
				for ( var i = 0; i < data.comPlaceList.length; i++) {
					var city = data.comPlaceList[i];
					var option = $("<option value=\"" + city.placeId + "\">"
							+ city.name + "</option>");
					citySelect.append(option);

				}
				div.append(citySelect);

			}

		});
	}

	$.fn.loadRoot.initCommPlace = function() {
		if (provinceId != null && "" != provinceId) {
			provinceSelect.children().each(function() {

				if ($(this).val() == provinceId) {
					$(this).attr("selected", "true");
				}
			});
		}

		if (cityId != null && "" != cityId) {
			if (divHtml != null) {
				$.fn.loadRoot.loadDest(divHtml, provinceId);
			}

			citySelect.children().each(function() {
				if ($(this).val() == cityId) {
					$(this).attr("selected", "true");
				}
			});

		}
	}
	
	$.fn.loadRoot.getHost = function(url) { 
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