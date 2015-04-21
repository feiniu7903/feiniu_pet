<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="finalApproveForm" modelAttribute="tntUserDetail"
	method="post" action="/user/waiting/final" target="_top">
	<sf:hidden path="userId" />
	<c:if test="${!materialed }">
		<div>该分销商资料还未审核通过！</div>
	</c:if>
	<c:if test="${!limitsed }">
		<div>该分销商保证金还未缴纳齐全！</div>
	</c:if>
	<br />
	<div>
		<span><sf:radiobutton path="finalStatus" value="1"
				checked="checked" onclick="$('#failReasonBox').hide()" /> 审核通过</span> <span><sf:radiobutton
				path="finalStatus" value="0" onclick="$('#failReasonBox').show()" />
			拒绝合作</span>
	</div>
	<div id="failReasonBox" style="display: none">
		拒绝合作原因： <br />
		<sf:textarea path="failReason" rows="5" cssStyle="width:250px;" />
	</div>
</sf:form>
<input class="pbtn pbtn-small btn-ok" id="finalApproveButton"
	style="float: right; margin-top: 20px;" type="button" value="保存" />
<script type="text/javascript">
	$().ready(function() {
		$("#finalApproveForm").validate(user);
	});

	$("#finalApproveButton").bind("click", function() {
		var form = $("#finalApproveForm");
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

