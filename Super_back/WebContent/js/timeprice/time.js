/**
 * 时间价格表.
 * 同个网页当中一次只支持显示一个时间价格表.
 * 操作的方式
 * 例:<a href='#timeprice' tt='META_PRODUCT' class='showTimePrice' param='{'prodBranch'}'>查看</a>
 */
$(function() {
	//参数
	var current_time_price_param;
	var current_time_price_type;//当前的操作的产品对应的类型,META_PRODUCT,PROD_PRODUCT
	var $time_price_dlg = null;
	/**
	 * 取时间价格表的首地址.
	 * @return {TypeName} 
	 */
	function get_time_price_base_url() {
		if (current_time_price_type == 'META_PRODUCT_AUDITING_SHOW') {
			return "/super_back/meta/toMetaTimePriceAuditingShow.do";
		} else if (current_time_price_type == 'META_PRODUCT') {
			return "/super_back/meta/toMetaTimePrice.do";
		} else {
			return "/super_back/prod/toProdTimePrice.do";
		}
	}

	/**
	 * 
	 * @return {TypeName} 
	 */
	function get_time_price_load_time_url() {
		if (current_time_price_type == 'META_PRODUCT' || current_time_price_type == 'META_PRODUCT_AUDITING_SHOW') {
			return "/super_back/meta/metaTime.do";
		} else {
			return "/super_back/prod/prodTime.do";
		}
	}

	/**
	 * 取保存地址
	 */
	function get_time_price_save_url() {
		if (current_time_price_type == 'META_PRODUCT') {
			return "/super_back/meta/saveMetaTimePrice.do";
		} else {
			return "/super_back/prod/saveTimePrice.do";
		}
	}
	
	$("input.calculateHours").live("keyup", function() {
		var value = $.trim($(this).val());
		var name = $(this).attr("name");
		var reg = /^(\d+)$/;
		if(value != "") {
			if (reg.test(value)) {
				var valueInt = parseInt(value);
				if(name == "dayInput") {
					if(valueInt < 0) {
						alert("只能输入大于或等于0的正整数");
						$(this).val("");
						return false;
					}
				}else if(name == "hourInput") {
					if(valueInt < 0 || valueInt > 23) {
						alert("只能输入0到24之间的正整数");
						$(this).val("");
						return false;
					}
				} else {
					if(valueInt < 0 || valueInt > 60) {
						alert("只能输入0到60之间的正整数");
						$(this).val("");
						return false;
					}
				}
			} else {
				alert("只能输入正整数");
				$(this).val("");
				return false;
			}
		}
		var $td = $(this).parent("td");
		var day = $.trim($td.find("input[name=dayInput]").val());
		var hour = $.trim($td.find("input[name=hourInput]").val());
		var min = $.trim($td.find("input[name=minInput]").val());
		if(hour == "" && day == "" && min == "") {
			$td.find("span.showTimeSpan").text("");
			$td.find("input[type=hidden][name^='timePriceBean.']").val("");
		} else {
			var res = 0;
			if(day == "") {
				day = 0;
			}
			day = parseInt(day);
			if(hour == "") {
				hour = 0;
			}
			hour = parseInt(hour);
			if(min == "") {
				min = 0;
			}
			min = parseFloat(min);
			min = min / 60;
			if(day == 0) {
				res = (-1 * (hour + min)) + "";
			} else {
				res = ((day - 1) * 24 + (24 - (hour + min))) + "";
			}
			if(res.indexOf(".") > 0) {
				res = res.substring(0, res.indexOf(".") + 2);
			}
			$td.find("span.showTimeSpan").text(res + "小时");
			$td.find("input[type=hidden][name^='timePriceBean.']").val(res);
		}
	});

	$(".showTimePrice")
			.live(
					"click",
					function() {
						var param = $(this).attr("param");
						current_time_price_type = $(this).attr("tt");

						if (current_time_price_type != 'META_PRODUCT'
                            && current_time_price_type != 'META_PRODUCT_AUDITING_SHOW'
                            && current_time_price_type != 'PROD_PRODUCT') {

							alert("您访问的时间价格表类型错误.");
							return false;
						}
						current_time_price_param = eval("(" + param + ")");

						if ($time_price_dlg == null) {
							$time_price_dlg = $("<div style='display:none' class='time_price_dlg_div'>");
							$time_price_dlg.appendTo($("body"));
						}

						$time_price_dlg.load(get_time_price_base_url(),
								current_time_price_param, function() {
									$time_price_dlg.dialog( {
										title : "时间价格表",
										width : 1000,
										modal : true
									});
								});
					})

	$(".time_price_dlg_div em.timePage").live(
			"click",
			function() {
				var mt = $(this).attr("tt");
				var current = $(this).attr("current");
				current_time_price_param["monthType"] = mt;
				current_time_price_param["currPageDate"] = current;
				$(".time_price_dlg_div div.timeDiv").load(
						get_time_price_load_time_url(),
						current_time_price_param);
			})

	$(".time_price_dlg_div td.dateTd").live(
			"click",
			function() {
				$(".time_price_dlg_div").find("input.date").val(
						$(this).attr("date"));
				var cancelTime = $(".time_price_dlg_div").find(
						"input.cancelTime").val();
				if (cancelTime != undefined) {
					$(".time_price_dlg_div").find("input.cancelTime").val(
							$(this).attr("cancelTime"));
				}
				var aheadTime = $(".time_price_dlg_div")
						.find("input.aheadTime").val();
				if (aheadTime != undefined) {
					$(".time_price_dlg_div").find("input.aheadTime").val(
							$(this).attr("aheadTime"));
				}
				/**
				var priceF = $(".time_price_dlg_div").find("input[name=timePriceBean.priceF]").val();
				if(priceF!=undefined){
					$(".time_price_dlg_div").find("input[name=timePriceBean.priceF]").val($(this).attr("priceF"));
				}*/
			});

	//星期是否可选
	$(".time_price_dlg_div input[type=checkbox].weekOpen").live(
			"change",
			function() {
				$(".time_price_dlg_div input[type=checkbox].week").attr(
						"disabled", !$(this).attr("checked"));
			});

	$(".time_price_dlg_div input[type=checkbox][name=timePriceBean.close]")
			.live(
					"change",
					function() {
						$(".time_price_dlg_div input[type=checkbox].weekOpen")
								.attr("disabled", $(this).attr("checked"));
						changePriceDisabled();
					});

	/**
	 * 根据是否选中禁售与设置为0来是否可以修改价.
	 */
	function changePriceDisabled() {
		var ck = $(
				".time_price_dlg_div input[type=checkbox][name=timePriceBean.close]")
				.attr("checked")
				|| $(".time_price_dlg_div input[name=skipSetPrice]").attr(
						"checked");
		if (current_time_price_type == 'META_PRODUCT') {
			$(".time_price_dlg_div input[name=timePriceBean.settlementPriceF]")
					.attr("disabled", ck);
		} else {
			$(".time_price_dlg_div input[name=timePriceBean.priceF]").attr(
					"disabled", ck);
		}
	}

	function changeAdvancedOpt(val) {
		if (val == 'op3') {
			$(".time_price_dlg_div .proLabel tr").show();
		} else if (val == 'op1') {
			$(".time_price_dlg_div .proLabel tr").hide();
			$(".time_price_dlg_div .proLabel tr.updatePrice").show();
		} else if(val == 'op4') {
			$(".time_price_dlg_div .proLabel tr").hide();
			$(".time_price_dlg_div .proLabel tr.updateZeroStock").show();
		} else if(val == 'op5') {
			$(".time_price_dlg_div .proLabel tr").hide();
			$(".time_price_dlg_div .proLabel tr.updateHour").show();
		} else {
			$(".time_price_dlg_div .proLabel tr").hide();
			$(".time_price_dlg_div .proLabel tr.updateStock").show();
		}
		if (val != 'op2') {
			$(".time_price_dlg_div").find("input[name=timePriceBean.marketPriceF]").val('');
		}
	}

	$(".time_price_dlg_div input[name=advancedOpt]").live("change", function() {
		var val = $(this).val();
		changeAdvancedOpt(val);
	});

	$(".time_price_dlg_div input[name=timePriceBean.priceType]").live("change",function(){
		var val=$(this).val();
		var $dest=$("#price_type_desc_div span."+val);
		$("#price_type_desc_div span").not($dest).hide();
		$dest.show();
		var $inputSpan=$("dd.sellPrice span."+val);
		$("dd.sellPrice span").not($inputSpan).hide();
		$inputSpan.show();		
	});

	$(".time_price_dlg_div input[name=skipSetPrice]").live("change",
			function() {
				changePriceDisabled();
			});
	var $current_loading_div = null;
	$(".time_price_dlg_div em.saveTimePrice").live(
			"click",
			function() {
				var $form = $(this).parents("form");
				if (!validation_time_price($form)) {
					return false;
				}
				set_nullval_time_price($form);
				if ($current_loading_div == null) {
					$current_loading_div = $("<div/>");
					$current_loading_div.appendTo($(".time_price_dlg_div"));
				}
				$current_loading_div.showLoading();
				$.ajax( {
					type : "POST",
					url : get_time_price_save_url(),
					async : false,
					data : $form.serialize(),
					timeout : 3000,
					success : function(dt) {
						$current_loading_div.hideLoading();
						var data = eval("(" + dt + ")");
						if (data.success || data.code == -1000) {
							alert("操作成功");
							if (data.code == -1000) {
								alert("以下的日期是无法添加销售价\n" + data.msg);
							}
							$(".time_price_dlg_div div.timeDiv").load(
									get_time_price_load_time_url(),
									current_time_price_param);
						} else {
							alert(data.msg);
						}
					}
				});
			});

	/**
	 * 判断是否大于0.
	 * @param {Object} v
	 * @return {TypeName} 
	 */
	function isGZero(v) {
		if ($.trim(v) == '') {
			return false;
		}
		var val = parseInt(v);
		if (isNaN(val) || val < 1) {
			return false;
		}

		return true;
	}
	/**
	 * 大于等于0
	 * @param {Object} v
	 * @return {TypeName} 
	 */
	function isGEZero(v) {
		if ($.trim(v) == '') {
			return false;
		}
		var val = parseInt(v);
		if (isNaN(val) || val < 0) {
			return false;
		}

		return true;
	}
	
	/**
	 * 设置默认值为空的值
	 * @param {Object} $form
	 */
	function set_nullval_time_price($form){
		var close = $form.find("input[name=timePriceBean.close]").attr(
					"checked");
		if(close){//如果是禁初始化里面的
			if (current_time_price_type == 'META_PRODUCT') {
				var val=$form.find("input[name=timePriceBean.dayStock]").val();
				if($.trim(val)===''){
					$form.find("input[name=timePriceBean.dayStock]").val(0);
				}
				val=$form.find("input[name=timePriceBean.marketPriceF]").val();
				if($.trim(val)===''){
					$form.find("input[name=timePriceBean.marketPriceF]").val(0);
				}
				
				
				val=$form.find("input[name=timePriceBean.settlementPriceF]").val();
				if($.trim(val)===''){
					$form.find("input[name=timePriceBean.settlementPriceF]").val(0);
				}				
			}else{
				var val=$form.find("input[name=timePriceBean.priceF]").val();
				if($.trim(val)===''){
					$form.find("input[name=timePriceBean.priceF]").val(0);
				}
			}
		}
	}

	/**
	 * 检查表单.根据不同的类型检查各自的表单.
	 */
	function validation_time_price($form) {
		try {
			//检查相同点.
			var begin = $form.find("input[name=timePriceBean.beginDate]").val();
			var end = $form.find("input[name=timePriceBean.endDate]").val();
			if ($.trim(begin) == '' || $.trim(end) == '') {
				throw "时间范围不能为空";
			}

			var close = $form.find("input[name=timePriceBean.close]").attr(
					"checked");
			if (!close) {
				if (current_time_price_type == 'META_PRODUCT') {
					var isAperiodic = $("#isAperiodic").val();
					var advancedOpt = $form.find(
							"input[name=advancedOpt]:checked").val();
					if (advancedOpt == 'op1' || advancedOpt == 'op4' || advancedOpt == 'op5') {//修改价格或修改清库存小时数或最晚取消小时数
						//给数量一个默认值，避免action出错.
						$form.find("input[name=timePriceBean.dayStock]").val(0);
					} else {//如果选中修改全部选项或者只修改库存
						/*var metaProductProductType=$("#metaProductProductType").val();
						
						if(metaProductProductType=='HOTEL'){
							var breakfastCount = $form.find(
							"select[name=timePriceBean.breakfastCount]").val();
							if ($.trim(breakfastCount) == '') {
								throw "早餐份数不能不选";
							}
						}
						*/
						//若不为不定期产品才需要做的校验
						if(isAperiodic != "true") {
							var dayStock = $form.find(
									"input[name=timePriceBean.dayStock]").val();
							if ($.trim(dayStock) == '') {
								throw "日库存不可以为空";
							}
							if ($.trim(dayStock) <-1) {
								throw "日库存不可以小于-1";
							}
							if (dayStock.indexOf(".") != -1) {
								throw "日库存不能包含有小数点";
							}
							var tmp = parseInt(dayStock);
							if (isNaN(tmp)) {
								throw "日库存必须是数值";
							}
							var reg = /^(\-?)(\d+)$/;
							if (reg.test(dayStock)) {
							} else {
								throw " 日库存必须为整数(数字中间或前后不能有空格)";
							}
						}
					}
					//修改价格或者修改全部属性
					if (advancedOpt == 'op1' || advancedOpt == 'op3') {
						var marketPriceF = $form.find(
								"input[name=timePriceBean.marketPriceF]").val();
						var metaProductProductType=$("#metaProductProductType").val();
						if (!isGEZero(marketPriceF)) {
							throw "门市价不能为空，不能为负值!";
						}
						var reg = /^(\d+)$/;
						if (reg.test(marketPriceF)) {
						} else {
							throw " 门市价必须为正整数(数字中间或前后不能有空格)";
						}
						
						if(metaProductProductType=='HOTEL'){
							var suggestPrice = $form.find(
							"input[name=timePriceBean.suggestPriceF]").val();
							if(suggestPrice!=''){
								if (!isGEZero(suggestPrice)) {
									throw "建议售价不能为负值";
								}
								var reg = /^(\d+)$/;
								if ((!reg.test(suggestPrice))) {
									throw " 建议售价必须为正整数(数字中间或前后不能有空格)";
								}
							}
						}

						var settlementPrice = $form.find(
								"input[name=timePriceBean.settlementPriceF]")
								.val();
						var skipSetPrice = $form.find(
								"input[name=skipSetPrice]").attr("checked");
						if (!skipSetPrice) {
							reg = /^(\d+)(\.\d{1,2})?$/;
							var hasVisa=false;
							if(metaProductProductType=='OTHER'&& $("#metaProductSubProductType").val()=='VISA'){
								reg = /^-?(\d+)(\.\d{1,2})?$/;
								hasVisa = true;
							}
							if (reg.test(settlementPrice)) {
								if (!hasVisa && !isGZero(settlementPrice)) {
									throw "结算价必须大于0";
								}
							} else {
								throw " 结算价必须为正整数(数字中间或前后不能有空格)";
							}
						}
					}
					
					//若不为不定期产品才需要做的校验
					if(isAperiodic != "true") {
						//修改清库存小时数或者修改全部属性
						if (advancedOpt == 'op3' || advancedOpt == 'op4') {
							//自动清库存小时数，可为正、负或0
							var zeroStockHour = $form.find(
									"input[name=timePriceBean.zeroStockHour]").val();
			
							if (zeroStockHour != null && $.trim(zeroStockHour) != '') {
								var reg = /^(\-?)(\d+)$/;
								if (reg.test(zeroStockHour)) {
									var zeroStockHourTemp = zeroStockHour.toString();
									if(zeroStockHourTemp.substr(0,1) == '-'){
										if(Math.abs(zeroStockHour) >= 24){
											throw " 自动更改资源需确认小时数数必须为大于-24整数";
										}
									} else {
										if(parseInt(zeroStockHour) >= 24*60){
											throw " 自动更改资源需确认小时数必须为小于24*60整数";
										}
									}
								} else {
									throw " 自动清库存小时数必须为整数";
								}
							}
						}
					}
					
					if (advancedOpt == 'op2' || advancedOpt == 'op4' || advancedOpt == 'op5') {//选中只修改库存或者清除库存小时数或最晚取消小时数
						//此时门市价处于隐藏,需要给他一个默认值，避免提交时在TimePrice中set方法出错(因为marketPriceF定义为Long型).
						$form.find("input[name=timePriceBean.marketPriceF]").val(0);
					}
					//若不为不定期产品才需要做的校验
					if(isAperiodic != "true") {
						if (advancedOpt == 'op3' || advancedOpt == 'op5') {//选中修改最晚取消小时数或者全部属性
							var aheadHour = $.trim($form.find("input[name=timePriceBean.aheadHourFloat]").val());
							if(aheadHour == "") {
								throw "提前预订小时数必填";
							}
							var cancelStrategy = $form.find("input[name=timePriceBean.cancelStrategy]:checked");
							if(cancelStrategy.length < 1) {
								throw "退改策略必选！";
							}
							if(cancelStrategy.val() == 'ABLE') {
								var cancelHour = $.trim($form.find("input[name=timePriceBean.cancelHourFloat]").val());
								if(cancelHour == "") {
									throw "最晚取消小时数必填";
								}
							}
							var alertMsg = "";
							$form.find("input[name=minInput]").each(function() {
								var val = $.trim($(this).val());
								if(val%10 != 0) {
									$(this).val("");
									$(this).keyup();
									alertMsg = "若分钟不为空,必须为10的倍数!";
								}
							});
							if(alertMsg != "") {
								alert(alertMsg);
								return false;
							}
						}
					}
					//若不为不定期产品才需要做的校验
					if(isAperiodic != "true") {
						if(advancedOpt == 'op3') {
							if(metaProductProductType == 'TICKET') {
								var earliestUseTime = $.trim($form.find("input[name=timePriceBean.earliestUseTime]").val());
								var latestUseTime = $.trim($form.find("input[name=timePriceBean.latestUseTime]").val());
								var reg = /^(([0-1][0-9])|(2[0-3])):[0-5]0$/;
								if(earliestUseTime == "") {
									throw "最早换票/使用时间不能为空";
								} else {
									if (!reg.test(earliestUseTime)) {
										throw "最早换票/使用时间格式错误";
									}
								}
								if(latestUseTime == "") {
									throw "最晚换票/使用时间不能为空";
								} else {
									if (!reg.test(latestUseTime)) {
										throw "最晚换票/使用时间格式错误";
									}
								}
							}
						}
					}
				} else {
					var selfPack = $form.find("input[name=selfPack]").val() == 'true';
					if (!selfPack) {
						//多行程判断
						var multiJourneySelect = $form.find("select[name=timePriceBean.multiJourneyId]");
						if(typeof(multiJourneySelect) != 'undefined') {
							if(multiJourneySelect.val() == "0") {
								alert("请选择行程!");
								return false;
							}
						}
						var priceType=$form.find(
								"input[name=timePriceBean.priceType]:checked").val();
						var reg = /^(\d+)$/;
						if(priceType=='FIXED_PRICE'){
							var priceF = $form.find(
									"input[name=timePriceBean.priceF]").val();
							var skipSetPrice = $form.find(
									"input[name=skipSetPrice]").attr("checked");
							var visaSelfSign = $form.find("input[name=visaSelfSign]").val();
							if (!skipSetPrice) {
								if(visaSelfSign!="true"){
									if (!isGZero(priceF)) {
										throw "驴妈妈价必须大于0";
									}									
									if (reg.test(priceF)) {
									} else {
										throw "驴妈妈价必须为正整数(数字中间或前后不能有空格)";
									}
								}else{
									if (isGZero(priceF)) {
										throw "签证销售产品自备签时驴妈妈价不可以为正！";
									}
								}
							}
						}else if(priceType=='RATE_PRICE'){
							var ratePrice=$form.find(
									"input[name=timePriceBean.ratePrice]").val();
							if(!reg.test(ratePrice)){
								throw "比例加价不可以为空";
							}
							if(!isGZero(ratePrice)){
								throw "比例加价必须大于0";
							}
						}else if(priceType=='FIXED_ADD_PRICE'){
							var fixedAddPrice=$form.find(
									"input[name=timePriceBean.fixedAddPriceF]").val();
							if(!reg.test(fixedAddPrice)){
								throw "固定加价不可以为空";
							}
							if(!isGEZero(fixedAddPrice)){
								throw "固定加价必须大于等于0";
							}
						}else{
							throw "没有选中价格类型";
						}
					}
				}
			}
			return true;
		} catch (ex) {
			alert(ex);
			return false;
		}
	}
})