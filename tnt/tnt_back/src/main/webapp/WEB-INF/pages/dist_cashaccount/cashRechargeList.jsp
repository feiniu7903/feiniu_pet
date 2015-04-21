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
	<div class="iframe_search">
		<div class="ui_title f14">
				<ul class="ui_tab">
					<li><a href="<c:url value="/cashaccount/search.do"/>">预存款账户信息</a></li>
					<li class="active"><a href="<c:url value="/cashaccount/dist/queryCashRechargeList.do"/>">充值单信息</a></li>
					<li><a href="<c:url value="/cashaccount/dist/queryFreezeList.do"/>">冻结单信息</a></li>
				</ul>
		</div>
		<sf:form method="POST" name="tntCashAccount" modelAttribute="tntCashAccount"
			action="/cashaccount/dist/queryCashRechargeList.do" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><input name="realName" value="${tntCashAccount.realName }"/></td>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><input name="userName" value="${tntCashAccount.userName }" /></td>
						<td class="s_label">充值状态：</td>
						<td class="w14">
							<sf:select path="valid">
								<sf:option value="">全部</sf:option>
								<sf:options items="${rechargeStats }"  itemLabel="desc"></sf:options>
							</sf:select></td>
						<td class = "s_label operate mt20" align="right" ><a class="btn btn_cc1" id="search_button" href="javascript:search();">查询</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
	<c:if test="${tntCashRechargeList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>充值单号</th>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>日期</th>
							<th>充值金额</th>
							<th>转账单编号</th>
							<th>转账单日期</th>
							<th>分销商账户名</th>
							<th>分销商银行账号</th>
							<th>分销商银行账户名</th>
							<th>转账银行</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashAccount" items="${tntCashRechargeList}">
							<tr>
								<td>${tntCashAccount.cashRechargeId}</td>
								<td>${tntCashAccount.tntUser.realName}</td>
								<td>${tntCashAccount.tntUser.detail.companyNameOrPerson}</td>
								<td>${tntCashAccount.cnCreateTime}</td>
								<td>${tntCashAccount.amountToYuan}</td>
								<td>${tntCashAccount.billNo}</td>
								<td>${tntCashAccount.billTime}</td>
								<td>${tntCashAccount.tntUser.userName}</td>
								<td>${tntCashAccount.bankAccount}</td>
								<td>${tntCashAccount.bankAccountName}</td>
								<td>${tntCashAccount.bankName}</td>
								<td>${tntCashAccount.cnStatus}</td>
								<td>
								<c:if test="${tntCashAccount.status=='UNPASS_AUDIT'}">
									<a href="javascript:recharge('${tntCashAccount.cashRechargeId }','${tntCashAccount.tntUser.realName }',${tntCashAccount.amountToYuan},'${tntCashAccount.billNo }','${tntCashAccount.billTime }','${tntCashAccount.bankAccount }','${tntCashAccount.bankName }','${tntCashAccount.bankAccountName }','${tntCashAccount.reason }')">修改申请</a>
								</c:if>
								<a param="{'objectType':'TNT_ACCOUNT','objectId':'${tntCashAccount.cashRechargeId}','logType':'ACCOUNT_RECHARGE'}" class="showLogDialog" href="javascript:void(0)">日志</a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging rechargeClass_pags">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
						<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
						<script type="text/javascript">
							postSubmitPages("searchForm");
						</script>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntCashRechargeList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
</div>

<!-- 分销商现金账户充值-->
	<div style="display: none" id="rechargeBox">
		<sf:form id="addMoneyForm" modelAttribute="tntCashRecharge"
			target="_top" action="/cashaccount/updateRecharge.do" method="post">
			<sf:hidden path="cashRechargeId" id="cashRechargeId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><input type="text" readonly="readonly"
							id="rechargeForm_userName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>充值额度：</td>
						<td><sf:input path="amountY" id="amountY"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应转账单编号：</td>
						<td><sf:input path="billNo" id="billNo"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>对应转账单日期：</td>
						<td><sf:input path="billTime" 
								onFocus="WdatePicker({readOnly:true})" id="billTime"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行账号：</td>
						<td><sf:input path="bankAccount" id="bankAccount"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商银行账户名：</td>
						<td><sf:input path="bankAccountName" id="bankAccountName"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>银行名称：</td>
						<td><sf:input path="bankName" id="bankName"/></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>充值原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;"
								rows="5" id="reason"/></td>
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
<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
<script type="text/javascript">
var search = function() {
	var form = $("#searchForm");
	$("#page").val(1);
	form.submit();
};
var recharge = function(cashRechargeId, userName,amountY,billNo,billTime,bankAccount,bankName,bankAccountName,reason) {
	$.dialog({
		width : 550,
		title : "分销商现金账户充值修改",
		content : $("#rechargeBox").html()
	});
	document.getElementById("cashRechargeId").value = cashRechargeId;
	document.getElementById("rechargeForm_userName").value = userName;
	document.getElementById("amountY").value = amountY;
	document.getElementById("billNo").value = billNo;
	document.getElementById("billTime").value = billTime;
	document.getElementById("bankAccount").value = bankAccount;
	document.getElementById("bankName").value = bankName;
	document.getElementById("bankAccountName").value = bankAccountName;
	document.getElementById("reason").value = reason;
};
</script>
</html>
