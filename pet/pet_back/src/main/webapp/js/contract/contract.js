$(function() {
	$("#search_supplierName").jsonSuggest({
		url : basePath + "/sup/searchSupplierJSON.do",
		maxResults : 10,
		width : 300,
		emptyKeyup : false,
		minCharacters : 1,
		onSelect : function(item) {
			$("#search_supplierId").val(item.id);
		}
	}).change(function() {
		if ($.trim($(this).val()) == "") {
			$("#search_supplierId").val("");
		}
	});

	var $add_contract_div;
	$("#add_contract_btn").click("click", function() {
		$add_contract_div = $("#add_contract_div").showWindow({
			width : 1000,
			title : '新增合同'
		});
	});

	var $relate_supplier_div;
	$("input.relate_supplier").live("click", function() {
		$relate_supplier_div = $("#relate_supplier_div").showWindow({
			width : 1000,
			title : '关联已有供应商'
		});
	});

	$("#relate_supplier_btn")
			.live(
					"click",
					function() {
						var $form = $(this).parents("form");
						var provinceId = $form.find("select[name=provinceId]")
								.val();
						var cityId = $form.find("select[name=cityId]").val();
						if (provinceId != null && provinceId != "") {
							if (cityId == null || cityId == "") {
								alert("请选择城市！");
								return false;
							}
						}
						$
								.post(
										$form.attr("action"),
										$form.serialize(),
										function(dt) {
											var data = eval("(" + dt + ")");
											if (data.success) {
												var list = data.supplierList;
												if (list != undefined) {
													$table = $("#relate_show_table");
													$table
															.html("<tr><th>&nbsp;</th><th>编号</th><th>供应商名称</th><th>录入时间</th><th>供应商地区</th></tr>");
													for (i in list) {
														var ss = list[i];
														var $tr = $("<tr/>");
														var content = "<td><input type='radio' name='relate_supplier_id' value='"
																+ ss.supplierId
																+ "'></td><td>"
																+ ss.supplierId
																+ "</td><td>"
																+ ss.supplierName
																+ "</td><td>"
																+ ss.createTime
																+ "</td><td>"
																+ ss.cityName
																+ "</td>";
														$tr.html(content);
														$table.append($tr);
													}
												}
											} else {
												alert(data.msg);
											}
										});
					});

	$("#relate_supplier_confirm")
			.live(
					"click",
					function() {
						var supplierId = $(
								"input[name='relate_supplier_id']:checked")
								.val();
						if (supplierId == undefined) {
							alert("请选择供应商！");
							return false;
						}
						$relate_supplier_div.dialog("close");
						$
								.post(
										basePath
												+ "/sup/relateSupplierDetail.do",
										{
											"supplierId" : supplierId
										},
										function(dt) {
											var data = eval("(" + dt + ")");
											if (data.success) {
												var supplier = data.supplier;
												var $form = $("#addContractForm");
												$form
														.find(
																"input[name=supplier.supplierId]")
														.val(
																supplier.supplierId);
												$form
														.find(
																"input[name=supplier.supplierName]")
														.val(
																supplier.supplierName);
												$form
														.find(
																"select[name=supplier.supplierType]")
														.find(
																"option[value="
																		+ supplier.supplierType
																		+ "]")
														.attr("selected", true);
												$form
												.find(
														"select[name=supplier.companyId]")
												.find(
														"option[value="
																+ supplier.companyId
																+ "]")
												.attr("selected", true);
												$("#supplier_cityName").text(
														supplier.cityName);
												$form
														.find(
																"input[name=supplier.address]")
														.val(supplier.address);
												$form
														.find(
																"input[name=supplier.telephone]")
														.val(supplier.telephone);
												$form
														.find(
																"input[name=supplier.fax]")
														.val(supplier.fax);
												$form
														.find(
																"input[name=supplier.postcode]")
														.val(supplier.postcode);
												$form
														.find(
																"input[name=supplier.bosshead]")
														.val(supplier.bosshead);
												$form
														.find(
																"input[name=supplier.legalPerson]")
														.val(
																supplier.legalPerson);
												$form
														.find(
																"input[name=supplier.travelLicense]")
														.val(
																supplier.travelLicense);
												$form
														.find(
																"input[name=supplier.webSite]")
														.val(supplier.webSite);
												$form
														.find(
																"input[name=supplier.advancedpositsAlertYuan]")
														.val(
																supplier.advancedpositsAlertYuan);
												$form
														.find(
																"input[name=supplier.foregiftsAlert]")
														.val(
																supplier.foregiftsAlert);

												$("#supplier_id")
														.text(
																"编号："
																		+ supplier.supplierId);

												$form
														.find(
																"input[name^='supplier.'],select[name^='supplier.']")
														.attr("disabled", true);
												$("#supplier_suggest_id").attr(
														"disabled", true);
												$("#supplier_suggest_id")
														.val(
																supplier.supSupplierName);
												$form
														.find(
																"input[name='supplier.supplierId']")
														.removeAttr("disabled");
												$(
														"#supplier_advancedepositsBalance")
														.text(
																supplier.advancedepositsBalanceYuan);
												$("#supplier_foregiftsBalance")
														.text(
																supplier.foregiftsBalanceYuan);
												$("#supplier_guaranteeLimit")
														.text(
																supplier.guaranteeLimitYuan);

												var $targetTab = $("#targetTable");
												$targetTab
														.html("<tr><th>编号</th><th>名称</th></tr>");
												var targets = data.targets;
												if (targets != undefined) {
													for (i in targets) {
														var target = targets[i];
														var $tr = $("<tr/>");
														$tr
																.html("<td>"
																		+ target.targetId
																		+ "</td><td>"
																		+ target.targetName
																		+ "</td>");
														$targetTab.append($tr);
													}
													$("#targetsDiv").show();
													$("#newTargetDiv").hide();
												}
											} else {
												alert(data.msg);
											}
										});
					});

	function contract_validate(m) {
		var no_1 = $.trim($("#contractNo_1").val());
		var no_2 = $.trim($("#contractNo_2").val());
		var no_3 = $.trim($("#contractNo_3").val());
		var no_4 = $.trim($("#contractNo_4").val());
		if (no_1 == "" || no_2 == "" || no_3 == "" || no_4 == "") {
			alert("合同编号需填完整！");
			return false;
		} else {
			$("#contract_no").val(no_1 + "-" + no_2 + "-" + no_3 + "-" + no_4);
		}
		if ($.trim(m["contract.contractType"]) == "") {
			alert("合同类型必选！");
			return false;
		}
		if ($.trim(m["contract.beginDate"]) == "") {
			alert("合同有效期开始日期必选！");
			return false;
		}
		if ($.trim(m["contract.endDate"]) == "") {
			alert("合同有效期结束日期必选！");
			return false;
		}
		if($.trim(m["contract.endDate"]) <= $.trim(m["contract.beginDate"])) {
			alert("合同有效期结束日期必须大于开始日期！");
			return false;
		}
		if ($.trim(m["contract.signDate"]) == "") {
			alert("合同签署日期必选！");
			return false;
		}
		if($.trim(m["contract.signDate"]) > $.trim(m["contract.beginDate"])) {
			alert("合同签署日期必须小于或等于合同有效期开始日期！");
			return false;
		}
		if($.trim(m["contract.managerId"]) == "" || $.trim(m["managerName"]) == "") {
			alert("请正确填写采购经理！");
			return false;
		}
		if ($.trim(m["contract.partyA"]) == "") {
			alert("合同会计主体必选！");
			return false;
		}
		return true;
	}

	// 供应商校验
	function supplier_validate(m) {
		// 供应商校验
		if ($.trim(m["supplier.supplierName"]) == "") {
			alert("供应商名称不能为空！");
			return false;
		}
		if ($.trim(m["supplier.supplierType"]) == "") {
			alert("供应商类型必选！");
			return false;
		}
		if (m["supplier.cityId"] == "" || m["supplier.cityId"] == null
				|| m["supplier.cityId"] == undefined) {
			alert("所在省市必选！");
			return false;
		}
		if ($.trim(m["supplier.address"]) == "") {
			alert("地址不能为空！");
			return false;
		}
		if ($.trim(m["supplier.telephone"]) == "") {
			alert("供应商电话不能为空！");
			return false;
		}
		if ($.trim(m["supplier.fax"]) == "") {
			alert("传真不能为空！");
			return false;
		}
		if ($.trim(m["supplier.postcode"]) == "") {
			alert("邮编不能为空！");
			return false;
		} else {
			if (isNaN($.trim(m["supplier.postcode"]))) {
				alert("邮编格式错误！");
				return false;
			}
		}
		if ($.trim(m["supplier.bosshead"]) == "") {
			alert("我方负责人不能为空！");
			return false;
		}
		return true;
	}

	// 结算对象校验
	function settlementTarget_validate(m) {
		if ($("input[name='settlementTarget.settlementPeriod']:checked").val() == "PERORDER") {
			if ($.trim(m["settlementTarget.advancedDays"]) == "") {
				alert("提前结算天数不能为空！");
				return false;
			}
		}

		var bankAccountName = $.trim(m["settlementTarget.bankAccountName"]);
		var bankName = $.trim(m["settlementTarget.bankName"]);
		var bankAccount = $.trim(m["settlementTarget.bankAccount"]);
		var alipayAccount = $.trim(m["settlementTarget.alipayAccount"]);
		var alipyName = $.trim(m["settlementTarget.alipayName"]);
		if (bankAccountName != "" || bankName != "" || bankAccount != "") {
			if (bankAccountName != "" && bankName != "" && bankAccount != "") {
			} else {
				alert("请填写完整开户信息！");
				return false;
			}
		} else {
			if (alipayAccount != "" || alipyName != "") {
				if (alipayAccount != "" && alipyName != "") {
				} else {
					alert("请填写完整支付宝信息！");
					return false;
				}
			} else {
				alert("请填写开户信息或者支付宝信息！");
				return false;
			}
		}
		if(bankAccount != "") {
			if (isNaN(bankAccount)) {
				alert("开户账号格式错误！");
				return false;
			}
		}
		return true;
	}

	$("#add_contract_submit").live("click", function() {
		var $form = $(this).parents("form");
		var m = $form.getForm();
		var supplierId = m["supplier.supplierId"];
		// 新增供应商,需要对供应商信息和结算对象信息录入做校验
		if (supplierId == "") {
			// 供应商校验
			if (!supplier_validate(m)) {
				return false;
			}
			// 结算对象校验
			if (!settlementTarget_validate(m)) {
				return false;
			}
		}

		if (!contract_validate(m)) {
			return false;
		}
		$(this).attr("disabled", true);
		$.ajax({
			type : 'POST',
			url : $form.attr("action"),
			data : $form.serialize(),
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("新增成功！");
					$add_contract_div.dialog("close");
				} else {
					alert(data.msg);
				}
			}
		});
	});

	var $contract_detail_div;
	$("a.contract_detail_show").live("click", function() {
		var contractId = $(this).attr("contractId");
		if (contractId != undefined) {
			$contract_detail_div = $("#contract_detail_div").showWindow({
				width : 1000,
				title : '合同基本信息',
				data : {
					"contractId" : contractId
				}
			});
		}
	});

	$("#edit_contract_btn").live("click", function() {
		var $form = $(this).parents("form");
		var m = $form.getForm();

		if (!contract_validate(m)) {
			return false;
		}

		$.ajax({
			type : 'POST',
			url : $form.attr("action"),
			data : $form.serialize(),
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("修改成功！");
					$contract_detail_div.dialog("close");
				} else {
					alert(data.msg);
				}
			}
		});
	});

	$("input[name='settlementTarget.settlementPeriod']").live(
			"change",
			function() {
				var val = $(this).val();
				if (val == "PERORDER") {
					$("input[name='settlementTarget.advancedDays']").attr(
							"readonly", false);
				} else {
					$("input[name='settlementTarget.advancedDays']").attr(
							"readonly", true);
				}
			});

	var $edit_contact_div;
	$("#bindContactBtn").live("click", function() {
		$edit_contact_div = $("#edit_contact_div").showWindow({
			width : 500,
			title : '新增联系人'
		});
	});

	$("#saveContactBtn")
			.live(
					"click",
					function() {
						var $form = $(this).parents("form");
						var m = $form.getForm();
						if ($.trim(m["contact.name"]) == "") {
							alert("姓名不能为空！");
							return false;
						}
						if ($.trim(m["contact.telephone"]) == "") {
							alert("电话号码不能为空！");
							return false;
						}
						if ($.trim(m["contact.memo"]) == "") {
							alert("说明不能为空！");
							return false;
						}
						$
								.ajax({
									type : 'POST',
									url : $form.attr("action"),
									data : $form.serialize(),
									success : function(dt) {
										var data = eval("(" + dt + ")");
										if (data.success) {
											alert("新增成功！");
											var $contacts_tab = $("#contacts_tab");
											var id = data.id;
											var name = data.name;
											var telephone = data.telephone;
											var memo = data.memo;
											var $tr = $("<tr/>");
											$tr.attr("id", "contact_tr_" + id);
											$tr
													.html("<td>"
															+ name
															+ "</td><td>"
															+ telephone
															+ "</td><td>"+memo+"</td><td><a href='javascript:void(0);' class='delete_contact' data='"
															+ id
															+ "'>删除</a></td>");
											$contacts_tab.append($tr);
											$edit_contact_div.dialog("close");
										} else {
											alert(data.msg);
										}
									}
								});
					});

	$("a.delete_contact").live("click", function() {
		var id = $(this).attr("data");
		$.ajax({
			type : 'POST',
			url : basePath + "/contract/doRemoveContact.do",
			data : {
				"contactId" : id
			},
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("删除成功！");
					$("#contact_tr_" + id).hide();
				} else {
					alert(data.msg);
				}
			}
		});
	});
	
	$("a.resubmitVerify").live("click", function() {
		var id = $(this).attr("data");
		$.ajax({
			type : 'POST',
			url : basePath + "/contract/resubmitVerify.do",
			data : {
				"contractId" : id
			},
			success : function(dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					alert("提交成功！");
					window.location.reload();
				} else {
					alert(data.msg);
				}
			}
		});
	});
});