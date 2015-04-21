<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body >
       <div  id="cashMoneyDrawBox">
		<sf:form id="addCashMoneyDrawForm" modelAttribute="tntCashMoneyDraw"
			target="_top" action="/cashaccount/updateDraw.do" method="post">
			<sf:hidden path="moneyDrawId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>提现额度：</td>
						<td><sf:input path="drawAmountY" disabled="true"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应账单编号：</td>
						<td><sf:input path="billNo" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应账单日期：</td>
						<td><sf:input path="billTime" 
								onFocus="WdatePicker({readOnly:true})" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><sf:input path="bankAccountName" disabled="true"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行账号：</td>
						<td><sf:input path="bankAccount" disabled="true"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行银行：</td>
						<td><sf:input path="kaiHuHang" disabled="true"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>冻结原因：</td>
						<td><sf:textarea path="memo" cssStyle="width:250px;"
								rows="5" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addMoneyDrawButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
		<script type="text/javascript">
			$().ready(function() {
				//$("#addCashMoneyDrawForm").validate(addMoney);
			});
			$("#addMoneyDrawButton").bind("click", function() {
				var form = $("#addCashMoneyDrawForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						alert("提现成功");
						parent.location.href="/tnt_back/cashaccount/showCashMoneyDraw.do";
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
