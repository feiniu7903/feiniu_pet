<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——费用说明</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prod/sensitive_word.js"></script>
<style type="text/css">
.row2 .newTableFeeDetail td textarea {
	height: 120px;
	width: 650px;
}

.newTableFeeDetail {
	margin: 0 auto;
	background: #fff;
	font-size: 12px;
	color: #555;
	width: 98%;
}

.newTableFeeDetail td {
	font-size: 12px;
	padding: 3px 3px;
	width: auto;
	height: auto;
}

.newTableFeeDetail td span {
	margin: 0 3px;
	font-size: 12px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#saveFeeDetailBtn").click(function() {
			var $form = $(this).parents("form");
			var ccContent = $.trim($form.find("textarea[name='ccViewContent.content']").val());
			if(ccContent == "") {
				alert("费用包含不能为空!");
				return false;
			}
			
			var nccContent = $.trim($form.find("textarea[name='nccViewContent.content']").val());
			if(nccContent == "") {
				alert("费用不包含不能为空!");
				return false;
			}
			var sensitiveValidator=new SensitiveWordValidator($form, true);
			if(!sensitiveValidator.validate()){
				return;
			}
			var productId = $form.find("input[name='productId']").val();
			$.ajax( {
				type : "POST",
				url : "/super_back/view/saveOrEditFeeDetail.do",
				async : false,
				data : $form.serialize(),
				timeout : 3000,
				success : function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("操作成功");
						window.location.href = "/super_back/view/queryMultiJourneyList.do?productId=" + productId;
					} else {
						alert(data.msg);
					}
				}
			});
		});
	});
</script>
</head>

<body>
	<div class="row2">
		<form class="mySensitiveForm">
			<s:hidden name="multiJourneyId" />
			<s:hidden name="productId" />
			<s:hidden name="ccViewContent.contentId" />
			<s:hidden name="nccViewContent.contentId" />
			<table class="newTableFeeDetail" width="100%" border="0"
				cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2"><b>费用说明</b><span class="require">[*]</span></td>
				</tr>
				<tr>
					<td colspan="2" width="100%">费用包含</td>
				</tr>
				<tr>
					<td width="665px"><s:textarea id="cost" name="ccViewContent.content"
							cols="text2" rows="text2" cssClass="text2 sensitiveVad"></s:textarea></td>
					<td width="40%" rowspan="2" valign="middle"><font color="red">自由行产品</font>
						<br /> &nbsp;&nbsp;1. 门票：<br /> &nbsp;&nbsp;2.
						住宿：房间所包含的宽带、早餐等请在此注明<br /> <font color="red">长短线产品</font> <br />
						&nbsp;&nbsp;1. 交通：<br /> &nbsp;&nbsp;2. 住宿：房间所包含的宽带、早餐等请在此注明<br />
						&nbsp;&nbsp;3. 景点门票：（如无请注明：无） <br />&nbsp;&nbsp;4. 用餐：（如无请注明：无）<br />
						&nbsp;&nbsp;5. 导服：（如无请注明：无） <br /> &nbsp;&nbsp;6. 赠送：（如无请删除此项）</td>
				</tr>
				<tr>
					<td colspan="2">费用不包含</td>
				</tr>
				<tr>
					<td width="665px"><s:textarea id="cost" name="nccViewContent.content"
							cols="text2" rows="text2" cssClass="text2 sensitiveVad"></s:textarea></td>
					<td width="40%" rowspan="2" valign="middle">1.
						多出发地的产品需注明：该产品为上海出发，不包含苏州到上海的交通费用。</td>
				</tr>
			</table>
			<input type="button" value="保存" class="button" id="saveFeeDetailBtn" />
		</form>
	</div>
</body>
</html>

