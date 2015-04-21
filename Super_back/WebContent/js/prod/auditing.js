function auditingDialog(productId, auditAble) {
	$('#auditing_div').showWindow({
		title : '产品审核',
		width : 1000,
		height : 710,
		data : {
			"productId" : productId,
			"auditAble" : auditAble
		}
	});
}

function auditing(productId, auditingPass, hasSensitiveWord) {
	var msg = "确定提交吗？";
	if(hasSensitiveWord != null && hasSensitiveWord == "Y") {
		msg = "输入的内容包含有广告敏感词，请按敏感词提示返回修改！是否继续提交审核？";
		doAuditing(productId, auditingPass, msg);
	} else if(hasSensitiveWord != null && (hasSensitiveWord == "null" || hasSensitiveWord == "")) {
		// 校验敏感词
		$.ajax({
			url : "/super_back/prod/validateProductSensitiveWords.do",
			async : false,
			data : {
				'productId' : productId
			},
			type : "POST",
			success : function(data) {
				var dt = eval("(" + data + ")");
				if (dt.success) {
					if (dt.hasSensitiveWords) {
						msg = "输入的内容包含有广告敏感词，请按敏感词提示返回修改！是否继续提交审核？";
					}
					doAuditing(productId, auditingPass, msg);
				} else {
					alert(dt.msg);
				}
			}
		});
	} else {
		doAuditing(productId, auditingPass, msg);
	}
}

function doAuditing(productId, auditingPass, msg) {
	if (confirm(msg)) {
		$.post("/super_back/prod/auditing.do", {
			"productId" : productId,
			"auditingPass" : auditingPass
		}, function(data) {
			if (data.jsonMap.status == "success") {
				alert("操作完成");
				window.location.reload(window.location.href);
			} else {
				alert(data.jsonMap.status);
			}
		});
	}
}

function closeAuditing() {
	$("#auditing_div").dialog("close");
}
