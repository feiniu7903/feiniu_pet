var curProductId;
function showControlDialog(productId) {
	$('#confirm_div').showWindow({
		title : '提示',
		width : 300,
		height : 200,
		data : {
			"control.productId" : productId
		}
	});
	curProductId = productId;
}

function showConfirmControlDialog(batch) {
	if (batch) {
		$('#control_batch_div').showWindow({
			title : '批量新增',
			width : 570,
			height : 240,
			data : {
				"control.productId" : curProductId
			}
		});
	} else {
		$('#control_div').showWindow({
			title : '预控新增',
			width : 570,
			height : 440,
			data : {
				"control.productId" : curProductId
			}
		});
	}
	$("#confirm_div").dialog("close");
}

function showCopyControlDialog(metaProductControlId) {
	$('#control_div').attr('url', $('#control_div').attr('url').replace('modifyControl', 'copyControl'));
	$('#control_div').showWindow({
		title : '预控复制',
		width : 570,
		height : 440,
		data : {
			"control.metaProductControlId" : metaProductControlId
		}
	});
}
function showModifyControlDialog(metaProductControlId) {
	$('#control_div').attr('url', $('#control_div').attr('url').replace('copyControl', 'modifyControl'));
	$('#control_div').showWindow({
		title : '预控修改',
		width : 570,
		height : 440,
		data : {
			"control.metaProductControlId" : metaProductControlId
		}
	});
}

function closeControlDialog() {
	$("#control_div").dialog("close");
}

function closeBatchControlDialog() {
	$("#control_batch_div").dialog("close");
}

function saveControl() {
	var $form = $('#controlForm');
	if (!isFormValid($form)) {
		return;
	}
	$.ajax({
		url:"/super_back/meta/saveControl.do",
		type:"POST",
		data:$form.serialize(),
		success:function(dt){
			var data = dt;
			if(data.success){
				if (data.opType == 'NEW') {
					alert("添加成功");
				} else {
					alert("修改成功");
				}
				closeControlDialog();
				if ($('#query_form').length > 0) {
					$('#query_form').submit();
				}
			}else{
				alert(data.msg);
			}
		}
	});
}
//TODO
function saveBatchControl() {
	var $form = $('#controlBatchForm');
	if (!isBatchFormValid($form)) {
		return;
	}
	$.ajax({
		url:"/super_back/meta/saveBatchControl.do",
		type:"POST",
		data:$form.serialize(),
		success:function(dt){
			var data = dt;
			if(data.success){
				alert("添加成功");
				closeBatchControlDialog();
			}else{
				alert(data.msg);
			}
		}
	});
}

function isFormValid($form) {
	var controlType = $form.find('input[name=control.controlType]').val();
	if (controlType == 'BRANCH_LEVEL') {
		var productBranchId = $form.find('#productBranchId').val();
		if (!productBranchId) {
			alert("类别不能为空");
			return false;
		}
	}
	var controlQuantity = $form.find('input[name=control.controlQuantity]').val();
	if (!controlQuantity) {
		alert("预控数量不能为空");
		return false;
	} else {
		if (controlQuantity == 0) {
			alert("预控数量不能为零");
			return false;
		}
	}
	var saleQuantity = $form.find('input[name=control.saleQuantity]').val();
	if (parseInt(controlQuantity) < parseInt(saleQuantity)) {
		alert("预控数量不能低于已销量");
		return false;
	}
	
	var saleStartDate = $form.find('input[name=control.saleStartDate]').val();
	if (!saleStartDate) {
		alert("销售起始日不能为空");
		return false;
	}
	var saleEndDate = $form.find('input[name=control.saleEndDate]').val();
	if (!saleEndDate) {
		alert("销售截止日不能为空");
		return false;
	}
	var startDate = $form.find('input[name=control.startDate]').val();
	if (!startDate) {
		alert("使用有效起始日不能为空");
		return false;
	}
	var endDate = $form.find('input[name=control.endDate]').val();
	if (!endDate) {
		alert("使用有效截止日不能为空");
		return false;
	}
	if (compareDateObj(saleStartDate, saleEndDate) == 1) {
		alert("销售起始日不能大于销售截止日");
		return false;
	}
	if (compareDateObj(startDate, endDate) == 1) {
		alert("使用有效起始日不能大于使用有效截止日");
		return false;
	}
	if (compareDateObj(startDate, saleStartDate) == -1) {
		alert("使用有效起始日不能小于销售起始日");
		return false;
	}
	if (compareDateObj(endDate, saleEndDate) == -1) {
		alert("使用有效截止日不能小于销售截止日");
		return false;
	}
	return true;
}

function isBatchFormValid($form) {
	var controlType = $form.find('input[name=control.controlType]').val();
	if (controlType == 'BRANCH_LEVEL') {
		var productBranchId = $form.find('#_productBranchId').val();
		if (!productBranchId) {
			alert("类别不能为空");
			return false;
		}
	}

	var saleStartDate = $form.find('input[name=control.saleStartDate]').val();
	if (!saleStartDate) {
		alert("销售起始日不能为空");
		return false;
	}

	var startDate = $form.find('input[name=control.startDate]').val();
	if (!startDate) {
		alert("使用有效起始日不能为空");
		return false;
	}
	var endDate = $form.find('input[name=control.endDate]').val();
	if (!endDate) {
		alert("使用有效截止日不能为空");
		return false;
	}
	if (compareDateObj(startDate, endDate) == 1) {
		alert("使用有效起始日不能大于使用有效截止日");
		return false;
	}
	if (compareDateObj(startDate, saleStartDate) == -1) {
		alert("使用有效起始日不能小于销售起始日");
		return false;
	}
	return true;
}

function deleteControls(productId) {
	if (confirm('确定要删除产品预控信息吗?')) {
		$.ajax({
			url:"/super_back/meta/deleteControlByProduct.do",
			type:"POST",
			data:{'control.productId': productId},
			success:function(dt){
				var data = dt;
				if(data.success){
					alert("删除预控信息成功");
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

function deleteControl(controlId) {
	if (confirm('确定要删除产品预控信息吗?')) {
		$.ajax({
			url:"/super_back/meta/deleteControl.do",
			type:"POST",
			data:{'control.metaProductControlId': controlId},
			success:function(dt){
				var data = dt;
				if(data.success){
					alert("删除成功");
					if ($('#query_form').length > 0) {
						$('#query_form').submit();
					}
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

function doTextKeyUp(text) {
	var v = text.value.replace(/[^\d]/g, '');
	text.value = v;
}

function compareDateObj(stra, strb) {
	var oa = toDateObj(stra);
	if (oa == null) {
		return -1;
	}
	var ob = toDateObj(strb);
	if (ob == null) {
		return 1;
	}
	if (oa.year < ob.year) {
		return -1;
	} else if (oa.year > ob.year) {
		return 1;
	} else {
		if (oa.month < ob.month) {
			return -1;
		} else if (oa.month > ob.month) {
			return 1;
		} else {
			if (oa.day < ob.day) {
				return -1;
			} else if (oa.day > ob.day) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

function calculateDefaultTime() {
	var $form = $('#controlForm');
	var startStr = $form.find('input[name=control.saleStartDate]').val();
	var endStr = $form.find('input[name=control.saleEndDate]').val();
	$form.find('input[name=control.startDate]').val(startStr);
	$form.find('input[name=control.endDate]').val(endStr);
	if (startStr == '' || endStr == '') {
		return;
	}
	
	var startObj = toDateObj(startStr);
	var endObj = toDateObj(endStr);
	var startDate = new Date();
	startDate.setFullYear(startObj.year, startObj.month - 1, startObj.day);
	var endDate = new Date();
	endDate.setFullYear(endObj.year, endObj.month - 1, endObj.day + 1);
	var subDay = Math.ceil((endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24);
	var firstTime = getWarningDate(startDate, subDay, 1/3);
	if (compareDateObj(firstTime, endStr) == 1) {
		$form.find('input[name=control.firstTime]').val(endStr);
	} else {
		$form.find('input[name=control.firstTime]').val(firstTime);
	}
	var secondTime = getWarningDate(startDate, subDay, 1/2);
	if (compareDateObj(secondTime, endStr) == 1) {
		$form.find('input[name=control.secondTime]').val(endStr);
	} else {
		$form.find('input[name=control.secondTime]').val(secondTime);
	}
	var thirdTime = getWarningDate(startDate, subDay, 2/3);
	if (compareDateObj(thirdTime, endStr) == 1) {
		$form.find('input[name=control.thirdTime]').val(endStr);
	} else {
		$form.find('input[name=control.thirdTime]').val(thirdTime);
	}
}

function getWarningDate(startDate, subDay, scale) {
	var tempDate = new Date();
	tempDate.setFullYear(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
	tempDate.setDate(tempDate.getDate() + Math.ceil(subDay * scale));
	return (tempDate.getFullYear()) + '-' + (fillStr(tempDate.getMonth() + 1 + '')) + '-' + fillStr(tempDate.getDate() - 1 + '');
}

function fillStr(month) {
	if (month.length == 1) {
		return '0' + month;
	}
	return month;
}

function toDateObj(str) {
	if (str == null) {
		return null;
	}
	var obj = {};
	var arr = str.split('-');
	obj.year = parseInt(arr[0]);
	obj.month = parseInt(arr[1]);
	obj.day = parseInt(arr[2]);
	return obj;
}