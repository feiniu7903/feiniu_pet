$(function() {
	$(".showPlaceInfo")
			.mouseover(
					function() {
						var $this = $(this);
						var offset = $(this).offset();
						var left = offset.left;
						var top = offset.top + 20;
						var productId = $(this).attr('productId');
						if (typeof ($placeInfoDiv) == 'undefined') {
							$placeInfoDiv = $('<div style="position: absolute; border: 1px solid #000; background: #fff; padding: 10px; margin: 0 2px; z-index: 99999;"></div>');
							$placeInfoDiv.appendTo($('body'));
						}
						var data = $this.data("placeInfo");
						if (data != undefined) {
							$placeInfoDiv.html(data);
							$placeInfoDiv.css('left', left + "px");
							$placeInfoDiv.css('top', top + "px");
							$placeInfoDiv.show();
						} else {
							$.ajax( {
								type : "POST",
								dataType : "json",
								url : "/super_back/phoneOrder/showPlaceInfo.do",
								async : false,
								data : {
									productId : productId
								},
								timeout : 3000,
								success : function(d) {
									if (d.success) {
										var cons = "";
										if (d.msg != null) {
											cons = d.msg;
										} else {
											if (d.hotelStar != null) {
												cons += d.hotelStar
														+ "星级<br />";
											}
											if (d.address != null) {
												cons += "地址：" + d.address
														+ "<br />";
											}
											if (d.phone != null) {
												cons += "电话：" + d.phone;
											}
											if(cons == '') {
												cons = "无目的地信息";
											}
										}
										$this.data("placeInfo", cons);
										$placeInfoDiv.html(cons);
										$placeInfoDiv.css('left', left + "px");
										$placeInfoDiv.css('top', top + "px");
										$placeInfoDiv.show();
									}
								}
							});
						}
					}).mouseout(function() {
				if (typeof ($placeInfoDiv) != 'undefined') {
					$placeInfoDiv.hide();
				}
			})
});