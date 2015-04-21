if(!basePath) {
	var basePath = "/ebooking";
}
if(!nowDate) {
	var n = new Date();
	var nowDate = n.getFullYear()+"-" + n.getMonth() + "-" + n.getDate();
}
$(function() {
	$("#changeRouteStockStatusForm").validate({
		rules : {
			"ebkDayStockDetail.specDate" : {
				required : true
			}
		},
		messages : {
			"ebkDayStockDetail.specDate" : {
				required : "请输入出发日期."
			}
		}
	});

	$(".search_ul_b_3").ui("calendar", {
		input : "#Calendar71",
		parm : {
			dateFmt : "yyyy-MM-dd"
		}
	});

	//时间的复制
	$("#Calendar71").change(function() {
		$("#beginDate").val($("#Calendar71").val());
	});
});

function checkAndSubmit(form) {
	if (!controlSumit()) {
		return false;
	}
	var options = {
		url : basePath+"/ebooking/routeStock/submitRouteStockTimePriceStatus.do",
		type : 'POST',
		success : function(data) {
			if (data == "success") {
				alert("操作成功!");
				window.location.reload(true);
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

var bClicked = false;
function controlSumit() {
	if (bClicked) {
		return false;
	}
	bClicked = true;
	return true;
}