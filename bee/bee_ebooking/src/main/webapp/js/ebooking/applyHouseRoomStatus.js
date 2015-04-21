if(!basePath) {
	var basePath = "/ebooking";
}
if(!nowDate) {
	var n = new Date();
	var nowDate = n.getFullYear()+"-" + n.getMonth() + "-" + n.getDate();
}
$(function() {
	$("#changeHouseRootStatusForm").validate({
		rules : {
			"metaProductBranchId" : {
				required : true
			},
			"ebkHouseStatus.beginDate" : {
				required : true
			},
			"ebkHouseStatus.endDate" : {
				required : true
			}
		},
		messages : {
			"metaProductBranchId" : {
				required : "请选择房型."
			},
			"ebkHouseStatus.beginDate" : {
				required : "请输入开始日期."
			},
			"ebkHouseStatus.endDate" : {
				required : "请输入结束日期."
			}
		}
	});

	$(".search_ul_b_3").ui("calendar", {
		input : "#Calendar71",
		parm : {
			dateFmt : "yyyy-MM-dd",
			maxDate : "#F{$dp.$D('Calendar81')}",
			minDate : "#F{$dp.$D('Calendar81',{d:-60})||'"+nowDate+"'}"
		}
	});
	$(".search_ul_b_3").ui("calendar", {
		input : "#Calendar81",
		parm : {
			dateFmt : 'yyyy-MM-dd',
			maxDate : "#F{$dp.$D('Calendar71',{d:60})}",
			minDate : "#F{$dp.$D('Calendar71')}"
		}
	});
	//时间的复制
	$("#Calendar71, #Calendar81").change(function() {
		$("#beginDate").val($("#Calendar71").val());
		$("#endDate").val($("#Calendar81").val());
	});
	// 产品列表的选择
	$("#chamber_all").click(function() {
		if ($(this).attr("checked")) {
			$("#changeHouseRootStatusForm input[name='metaProductBranchId']").attr("checked", true);
		} else {
			$("#changeHouseRootStatusForm input[name='metaProductBranchId']").removeAttr("checked");
		}
	});
	$("#changeHouseRootStatusForm input[name='metaProductBranchId']").click(function() {
		var isAllCheck = true;
		$("#changeHouseRootStatusForm input[name='metaProductBranchId']").each(function() {
			if (!$(this).attr("checked")) {
				isAllCheck = false;
				return false;
			}
		});
		if (isAllCheck) {
			$("#chamber_all").attr("checked", true);
		} else {
			$("#chamber_all").removeAttr("checked");
		}
	});
	$("#week_all").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='ebkHouseStatus.applyWeek']").attr("checked", true);
			$("input[name='ebkHouseStatus.applyWeek']").attr("disabled", true);
		} else {
			$("input[name='ebkHouseStatus.applyWeek']").attr("checked", false);
			$("input[name='ebkHouseStatus.applyWeek']").attr("disabled", false);
		}
	});
	$("#changeHouseRootStatusForm input[name='ebkHouseStatus.applyWeek']").each(function() {
		$(this).click(function() {
			if ($(this).attr("checked") == false) {
				$("#week_all").attr("checked", false);
			}
		});
	});

	$("#baoliuAdd").click(function() {
		$("#baoliuQuantityId").val("输入增加的数量");
	});
	$("#baoliuReduce").click(function() {
		$("#baoliuQuantityId").val("输入减少的数量");
	});

	$("#manfangTrueId").click(function() {
		$("#baoliuAdd, #baoliuReduce").attr("disabled", true);
		$("#chaomaiTrueId, #chaomaiFalseId").attr("disabled", true);
		$("#baoliuQuantityId").attr("disabled", true);

	});
	$("#manfangFalseId").click(function() {
		$("#baoliuAdd, #baoliuReduce").attr("disabled", false);
		$("#chaomaiTrueId, #chaomaiFalseId").attr("disabled", false);
		$("#baoliuQuantityId").attr("disabled", false);
	});

	$("#baoliuQuantityId").change(function() {
		var v = $(this).val();
		var result = 0;
		if (!(v == '输入增加的数量' || v == '输入减少的数量')) {
			var val = parseInt(v);
			if (!isNaN(val)) {
				result = val;
			}
		}
		$("#changeHouseRootStatusForm input[name='ebkHouseStatus.baoliuQuantity']").val(result);
	});
});
function checkAndSubmit(form) {
	if($(form).attr("id")=="changeHouseRootStatusForm") {
		if(!$("#changeHouseRootStatusForm").valid()) {
			return false;
		}
	}
	if (!controlSumit()) {
		return false;
	}
	var options = {
		url : basePath+"/ebooking/housestatus/submitChangeHouseRootStatus.do",
		type : 'POST',
		success : function(data) {
			if (data == "success") {
				alert("操作成功!");
				reSerachHousePriceTable();
			} else {
				alert("操作失败：" + data);
			}
			bClicked = false;
		},
		error : function() {
			alert("出现错误");
			bClicked = false;
		}
	};
	$(form).ajaxSubmit(options);
}
function reSerachHousePriceTable() {
	if(!$("#changeHouseRootStatusForm").valid()) {
		return false;
	}
	var beginDate = $('#changeHouseRootStatusForm input[name="ebkHouseStatus.beginDate"]').val();
	var data = "ebkHouseStatus.beginDate=" + beginDate;
	var endDate = $('#changeHouseRootStatusForm input[name="ebkHouseStatus.endDate"]').val();
	data += "&ebkHouseStatus.endDate=" + endDate;
	
	$("#changeHouseRootStatusForm input[name='metaProductBranchId']").each(function() {
		if($(this).attr("checked")) {
			data += "&metaProductBranchId=" + $(this).val();
		}
	});
	var optionsTable = {
		url : basePath+"/ebooking/housestatus/houseRoomTimePriceTable.do",
		type : 'POST',
		data : data,
		dataType : "html",
		success : function(html, textStatus) {
			if (textStatus == "success") {
				$("#timePriceDiv").html(html);
			}
		},
		error : function() {
			alert("error");
		}
	};
	$.ajax(optionsTable);
}

// /
var bClicked = false;
function controlSumit() {
	if (bClicked) {
		return false;
	}
	bClicked = true;
	return true;
}