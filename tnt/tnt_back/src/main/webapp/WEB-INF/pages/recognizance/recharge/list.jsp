<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<body>
	<div class="iframe_search">
		<div class="ui_title f14">
			<ul class="ui_tab">
				<li><a href="<c:url value="/recognizance/list"/>">保证金账户信息</a></li>
				<li class="active"><a
					href="<c:url value="/recognizance/recharge/list"/>">充值单信息</a></li>
				<li><a href="<c:url value="/recognizance/debit/list"/>">扣款单信息</a></li>
			</ul>
		</div>
		<sf:form method="POST" modelAttribute="tntRecognizanceChange"
			action="/recognizance/recharge/list" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><sf:input path="realName" /></td>
						<td class="s_label">分销商账号：</td>
						<td class="w14"><sf:input path="userName" /></td>
						<td class="s_label">充值单状态：</td>
						<td class="w14"><sf:select path="approveStatus">
								<sf:option value="" label="--所有--" />
								<sf:options items="${approveStatusMap}" />
							</sf:select></td>
						<td class="s_label operate mt20" align="right"><a
							class="btn btn_cc1" id="search_button"
							href="javascript:search();">查询</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>

	<div class="iframe_content mt20">
		<c:if test="${tntRecognizanceChangeList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>充值单号</th>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>金额</th>
							<th>日期</th>
							<th>转账单编号</th>
							<th>转账单日期</th>
							<th>分销商账户名</th>
							<th>银行账户名</th>
							<th>分销商银行账号</th>
							<th>转账银行</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntRecognizanceChange"
							items="${tntRecognizanceChangeList}">
							<tr>
								<td>${tntRecognizanceChange.changeId}</td>
								<td>${tntRecognizanceChange.realName}</td>
								<td>${tntRecognizanceChange.companyName}</td>
								<td>${tntRecognizanceChange.amountY}</td>
								<td><lv:dateOutput
										date="${tntRecognizanceChange.createTime }"
										format="yyyy-MM-dd" /></td>
								<td>${tntRecognizanceChange.billNo }</td>
								<td><lv:dateOutput
										date="${tntRecognizanceChange.billTime }" format="yyyy-MM-dd" /></td>
								<td>${tntRecognizanceChange.userName}</td>
								<td>${tntRecognizanceChange.bankAccountName}</td>
								<td>${tntRecognizanceChange.bankAccount}</td>
								<td>${tntRecognizanceChange.bankName}</td>
								<td><lv:mapValueShow
										key="${tntRecognizanceChange.approveStatus }"
										map="${approveStatusMap }"></lv:mapValueShow></td>
								<td class="oper"><c:if
										test="${tntRecognizanceChange.canEdit }">
										<a
											href="javascript:toCancel('<c:url value="/recognizance/cancel/"/>','${tntRecognizanceChange.changeId }');">废除</a>
										<a
											href="javascript:toEdit('<c:url value="/recognizance/edit/"/>','${tntRecognizanceChange.changeId }');">修改</a>
									</c:if> <c:if test="${tntRecognizanceChange.canConfirm }">
										<a
											href="javascript:toConfirm('<c:url value="/recognizance/confirm/"/>','${tntRecognizanceChange.changeId }');">提交确认</a>
									</c:if> <a
									param="{'objectType':'TNT_RECOGNIZANCE_CHANGE','objectId':'${tntRecognizanceChange.changeId}','logType':''}"
									class="showLogDialog" href="#log">日志</a></td>
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
		<c:if test="${tntRecognizanceChangeList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关分销商，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<script src='<c:url value="/js/log.js"/>'></script>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		var search = function() {
			$("#page").val(1);
			$('#searchForm').submit();
		};

		var toEdit = function(u, changeId) {
			var url = u + changeId;
			new xDialog(url, null, {
				title : "分销商保证金账户充值修改",
				width : 550
			});
		};

		var toConfirm = function(u, changeId) {
			var url = u + changeId;
			$.dialog({
				width : 400,
				title : "充值提交确认",
				ok : function() {
					$.getJSON(url, function(data) {
						if (data.success) {
							$('#searchForm').submit();
						} else {
							alert(data.errorText);
						}
					});
				},
				okValue : "确定",
				content : "您是否确定提交确认？"
			});
		};

		var toCancel = function(u, changeId) {
			var url = u + changeId;
			$.dialog({
				width : 400,
				title : "充值单废除",
				ok : function() {
					$.getJSON(url, function(data) {
						if (data.success) {
							$('#searchForm').submit();
						} else {
							alert(data.errorText);
						}
					});
				},
				okValue : "确定",
				content : "您是否确定删除充值单？"
			});
		};
	</script>
</body>
</html>