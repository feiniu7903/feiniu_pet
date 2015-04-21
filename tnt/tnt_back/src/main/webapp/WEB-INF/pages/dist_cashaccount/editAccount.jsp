<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body>
<!-- 分销商现金账户充值-->
	<div style="display: none" id="rechargeBox">
		<sf:form id="addMoneyForm" modelAttribute="tntAccount"
			target="_top" action="/cashaccount/saveAccount.do" method="post">
			<sf:hidden path="accountId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>开户银行：</td>
						<td><sf:input path="bankName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账号：</td>
						<td><sf:input path="bankAccount" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账户名：</td>
						<td><sf:input path="accountName" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addMoneyButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#addMoneyForm").validate(addMoney);
			});
			$("#addMoneyButton").bind("click", function() {
				var form = $("#addMoneyForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						$("#searchForm").submit();
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>
</body>
</html>