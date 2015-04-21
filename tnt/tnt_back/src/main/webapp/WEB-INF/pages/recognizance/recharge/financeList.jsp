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
		<sf:form method="POST" modelAttribute="tntRecognizanceChange"
			action="/recognizance/finance/recharge/list" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<sf:hidden path="dealed" id="dealed" />
			<div class="ui_title f14">
				<ul class="ui_tab">
					<li id="unDealLi"
						class='${tntRecognizanceChange.dealed?"":"active"}'><a
						href="javascript:unDeal()">未处理</a></li>
					<li id="dealedLi"
						class='${tntRecognizanceChange.dealed?"active":""}'><a
						href="javascript:dealed()">已处理</a></li>
				</ul>
			</div>
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
										test="${tntRecognizanceChange.canApprove }">
										<a
											href="javascript:toAgree('${tntRecognizanceChange.changeId }','${tntRecognizanceChange.typeName }');">确认</a>
										<a
											href="javascript:toReject('${tntRecognizanceChange.changeId }','${tntRecognizanceChange.typeName }');">打回</a>
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
	<div style="display: none" id="agreeBox">
		<sf:form id="agreeForm" method="post"
			modelAttribute="tntRecognizanceChange" target="_top"
			action="/recognizance/finance/approve">
			<input type="hidden" name="_method" value="put" />
			<sf:hidden path="changeId" id="agree_changeId" />
			<sf:hidden path="approveStatus" value="AGREE" />
		</sf:form>
		你确定要确认吗？
		<div>
			<input class="pbtn pbtn-small btn-ok" onclick="agree();"
				style="float: right; margin-top: 20px;" type="button" value="确认" />
		</div>
	</div>
	<div style="display: none" id="rejectBox">
		<sf:form id="rejectForm" method="post"
			modelAttribute="tntRecognizanceChange" target="_top"
			action="/recognizance/finance/approve">
			<input type="hidden" name="_method" value="put" />
			<sf:hidden path="changeId" id="reject_changeId" />
			<sf:hidden path="approveStatus" value="REJECT" />
			<div class="iframe_content pd0">
				<div>打回原因：</div>
				<div>
					<sf:textarea path="approveReason" rows="5" cssStyle="width:250px;" />
				</div>
			</div>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" onclick="reject();"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$("#rejectForm").validate({
				rules : {
					"approveReason" : {
						required : true,
						maxCNLen : 500
					}
				}
			});
		</script>
	</div>
	<script src='<c:url value="/js/log.js"/>'></script>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		var search = function() {
			$("#page").val(1);
			$('#searchForm').submit();
		};
		var dealed = function() {
			$("#unDealLi").removeClass("active");
			$("#dealedLi").addClass("active");
			$("#dealed").val(true);
			search();
		};
		var unDeal = function() {
			$("#unDealLi").addClass("active");
			$("#dealedLi").removeClass("active");
			$("#dealed").val(false);
			search();
		};

		var toAgree = function(changeId, typeName) {
			$.dialog({
				width : 400,
				title : typeName + "单确认",
				content : $("#agreeBox").html()
			});
			$("#agree_changeId").val(changeId);
		};

		var toReject = function(changeId, typeName) {
			$.dialog({
				width : 400,
				title : typeName + "单打回",
				content : $("#rejectBox").html()
			});
			$("#reject_changeId").val(changeId);
		};

		var lock = false;
		var agree = function() {
			var form = $("#agreeForm");
			if (!lock) {
				form.ajaxSubmit({
					beforeSubmit : function() {
						lock = true;
					},
					success : function(data) {
						if (data.success) {
							$("#searchForm").submit();
						} else {
							alert(data.errorText);
							lock = false;
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常！");
						lock = false;
					}
				});
			}
		};

		var reject = function() {
			var form = $("#rejectForm");
			if (!form.validate().form()) {
				return;
			}
			if (!lock) {
				form.ajaxSubmit({
					beforeSubmit : function() {
						lock = true;
					},
					success : function(data) {
						if (data.success) {
							$("#searchForm").submit();
						} else {
							alert(data.errorText);
							lock = false;
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常！");
						lock = false;
					}
				});
			}
		};
	</script>
</body>
</html>