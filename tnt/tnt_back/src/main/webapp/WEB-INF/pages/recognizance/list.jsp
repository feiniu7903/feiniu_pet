<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<script type="text/javascript">
	var recognizanceDialog = null;
</script>
</head>
<body>
	<div class="iframe_search">
		<div class="ui_title f14">
			<ul class="ui_tab">
				<li class="active"><a
					href="<c:url value="/recognizance/list"/>">保证金账户信息</a></li>
				<li><a href="<c:url value="/recognizance/recharge/list"/>">充值单信息</a></li>
				<li><a href="<c:url value="/recognizance/debit/list"/>">扣款单信息</a></li>
			</ul>
		</div>
		<sf:form method="POST" modelAttribute="tntRecognizance"
			action="/recognizance/list" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><sf:input path="realName" /></td>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><sf:input path="userName" /></td>
						<td class="s_label operate mt20"><a class="btn btn_cc1"
							id="search_button" href="javascript:search();">查询</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>

	<div class="iframe_search">
		<table class="s_table">
			<tbody>
				<tr>
					<td><ul>
							<li>保证金目的：保证金是为了维护消费者的正当权益、和驴妈妈品牌形象而向分销商预收的一定金额。</li>
							<li>保证金使用：分销商使用欺骗行为损害了消费者的正当权益而引起纠纷、投诉、罚款或赔偿时使用；在分销商违背了双方的合作合同或其他
								对驴妈妈品牌形象造成影响时使用。</li>
							<li>保证金退还：分销商在正常情况下和驴妈妈解除合作后，保证金按额退还分销商。</li>
							<li>注：分销商保证金账户金额没有按额交齐的情况下，不可以进行分销合作。</li>
						</ul></td>
					<td><ul>
							<li>保证金缴纳账号：${tntAccount.bankAccount }</li>
							<li>保证金缴纳银行：${tntAccount.bankName }</li>
							<li>账户开户人姓名：${tntAccount.accountName }</li>
							<li>财务联系电话：&#160;&#160;${tntAccount.telephone }</li>
						</ul></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="iframe_content mt20">
		<c:if test="${tntRecognizanceList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>分销商用户名</th>
							<th>保证金额度</th>
							<th>目前账户金额</th>
							<th>需补交</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntRecognizance" items="${tntRecognizanceList}">
							<tr>
								<td>${tntRecognizance.realName}</td>
								<td>${tntRecognizance.companyName}</td>
								<td>${tntRecognizance.userName}</td>
								<td>${tntRecognizance.limitsY}</td>
								<td>${tntRecognizance.balanceY}</td>
								<td>${tntRecognizance.needPay}</td>
								<td class="oper"><a
									href="javascript:showDetail('<c:url value="/recognizance/details/" />','${tntRecognizance.recognizanceId }')">明细查询</a>
									<a
									href="javascript:recharge('${tntRecognizance.userId }','${tntRecognizance.recognizanceId }','${tntRecognizance.userName }')">充值</a>
									<a
									href="javascript:debit('${tntRecognizance.userId }','${tntRecognizance.recognizanceId }')">扣款</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						${page.pagination}
					</div>
					<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
					<script type="text/javascript">
						postSubmitPages("searchForm");
					</script>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntRecognizanceList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目，重新输入相关条件查询！
			</div>
		</c:if>
	</div>

	<!-- 分销商保证金账户扣款-->
	<div style="display: none" id="debitBox">
		<sf:form id="debitForm" modelAttribute="tntRecognizanceChange"
			target="_top" action="/recognizance" method="post">
			<input type="hidden" name="_method" value="delete" />
			<sf:hidden path="userId" id="debitForm_userId" />
			<sf:hidden path="recognizanceId" id="debitForm_recognizanceId" />
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>扣款额度：</td>
						<td><sf:input path="amountY" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>扣款原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;"
								rows="5" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="debitButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#debitForm").validate(recognizance);
			});
			$("#debitButton").bind("click", function() {
				var form = $("#debitForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						if (data.success) {
							$("#searchForm").submit();
						} else {
							alert(data.errorText);
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>


	<!-- 分销商保证金账户充值-->
	<div style="display: none" id="rechargeBox">
		<sf:form id="rechargeForm" modelAttribute="tntRecognizanceChange"
			target="_top" action="/recognizance" method="post">
			<input type="hidden" name="_method" value="put" />
			<sf:hidden path="userId" id="rechargeForm_userId" />
			<sf:hidden path="recognizanceId" id="rechargeForm_recognizanceId" />
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><input type="text" readonly="readonly"
							id="rechargeForm_userName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>充值金额：</td>
						<td><sf:input path="amountY" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>转账单编号：</td>
						<td><sf:input path="billNo" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>转账单日期：</td>
						<td><sf:input path="stringBillTime"
								onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账户名：</td>
						<td><sf:input path="bankAccountName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行名称：</td>
						<td><sf:input path="bankName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行账号：</td>
						<td><sf:input path="bankAccount" /></td>
					</tr>
					<%-- <tr>
						<td class="p_label"><span class="notnull">*</span>充值原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;"
								rows="5" /></td>
					</tr> --%>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="rechargeButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#rechargeForm").validate(recognizance);
			});
			$("#rechargeButton").bind("click", function() {
				var form = $("#rechargeForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						if (data.success) {
							$("#searchForm").submit();
						} else {
							alert(data.errorText);
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>

	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		var search = function() {
			$("#page").val(1);
			var form = $("#searchForm");
			if (!form.validate().form()) {
				return;
			}
			form.submit();
		};
		var showDetail = function(u, id) {
			var url = u + id;
			recognizanceDialog = new xDialog(url, null, {
				width : 1200,
				title : "分销商保证金账户明细"
			});
		};

		var debit = function(userId, id) {
			$.dialog({
				width : 550,
				title : "分销商保证金扣款",
				content : $("#debitBox").html()
			});
			document.getElementById("debitForm_userId").value = userId;
			document.getElementById("debitForm_recognizanceId").value = id;
		};

		var recharge = function(userId, id, userName) {
			$.dialog({
				width : 550,
				title : "分销商保证金充值",
				content : $("#rechargeBox").html()
			});
			document.getElementById("rechargeForm_userId").value = userId;
			document.getElementById("rechargeForm_recognizanceId").value = id;
			document.getElementById("rechargeForm_userName").value = userName;
		};
	</script>
</body>
</html>