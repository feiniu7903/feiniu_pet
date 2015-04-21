<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchForm").validate(user);
		var infos = $(".baseInfo");
		infos.each(function(i) {
			var e = infos[i];
			$(e).click(function() {
				var userId = e.id;
				var url = "info/" + userId;
				new xDialog(url, null, {
					title : "分销商基本信息审核",
					width : 550
				});
			});
		});
	});

	var search = function() {
		var p = $("#page");
		if (p) {
			p.val(1);
		}
		var form = $("#searchForm");
		if (!form.validate().form()) {
			return;
		}
		form.submit();
	};

	var materialDialog = null;
	var showMaterial = function(userId) {
		var url = "material/" + userId;
		materialDialog = new xDialog(url, null, {
			title : "分销商资料审核",
			width : 550
		});
	};

	//合同管理弹窗
	var contractBox = null;
	var showContractBox = function(url, userId) {
		var content = url + "/" + userId;
		contractBox = new xDialog(content, null, {
			width : 600,
			title : "分销商合同管理页面"
		});
	};
</script>