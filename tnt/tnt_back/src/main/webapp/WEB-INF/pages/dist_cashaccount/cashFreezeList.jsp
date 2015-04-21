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
<body>
	<div class="iframe_search">
		<div class="ui_title f14">
				<ul class="ui_tab">
					<li ><a href="<c:url value="/cashaccount/search.do"/>">预存款账户信息</a></li>
					<li><a href="<c:url value="/cashaccount/dist/queryCashRechargeList.do"/>">充值单信息</a></li>
					<li class="active"><a href="<c:url value="/cashaccount/dist/queryFreezeList.do"/>">冻结单信息</a></li>
				</ul>
		</div>
		<sf:form method="POST" name="tntCashAccount" modelAttribute="tntCashAccount"
			action="/cashaccount/dist/queryFreezeList.do" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商名称：</td>
						<td class="w14"><input name="realName" value="${tntCashAccount.realName }"/></td>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><input name="userName" value="${tntCashAccount.userName }" /></td>
						<td class="s_label">冻结单状态：</td>
						<td class="w14">
							<sf:select path="valid">
								<sf:option value="">全部</sf:option>
								<sf:options items="${freezeStats }"  itemLabel="desc"></sf:options>
							</sf:select>
						</td>
						<td class = "s_label operate mt20" align="right" ><a class="btn btn_cc1" id="search_button" href="javascript:search();">查询</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
	<c:if test="${tntCashFreezeQueueList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>冻结单号</th>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>冻结金额</th>
							<th>冻结日期</th>
							<th>解冻确认日期</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashFreezeQueue" items="${tntCashFreezeQueueList}">
							<tr>
								<td>${tntCashFreezeQueue.freezeQueueId}</td>
								<td>${tntCashFreezeQueue.tntUser.realName}</td>
								<td>${tntCashFreezeQueue.tntUser.detail.companyNameOrPerson}</td>
								<td>${tntCashFreezeQueue.freezeAmountY}</td>
								<td>${tntCashFreezeQueue.cnCreateTime}</td>
								<td>${tntCashFreezeQueue.cnReleaseTime}</td>
								<td>${tntCashFreezeQueue.cnStatus}</td>
								<td>
									<c:if test='${tntCashFreezeQueue.status=="FREEZE"}'>
										<a href="javascript:void(0)" onclick="release('${tntCashFreezeQueue.freezeQueueId}','${tntCashFreezeQueue.tntUser.realName}','${tntCashFreezeQueue.freezeAmountY}');">申请解冻</a>
									</c:if>
									<a param="{'objectType':'TNT_ACCOUNT','objectId':'${tntCashFreezeQueue.freezeQueueId}','logType':'ACCOUNT_FREEZE'}" class="showLogDialog" href="javascript:void(0)">日志</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging freezeClass_pags">
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
		<c:if test="${tntCashFreezeQueueList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
	</div>
		<!-- 分销商现金账户冻结-->
	<div style="display: none" id="freezeBox">
		<sf:form id="addFreezeForm" modelAttribute="tntCashFreezeQueue"
			target="_top" action="/cashaccount/updateFreeze.do" method="post">
			<sf:hidden path="freezeQueueId" id="form_freezeQueueId"/>
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商账户名：</td>
						<td><input type="text" readonly="readonly" id="rechargeForm_userName" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>解冻额度：</td>
						<td><input type="text" readonly="readonly" id="freezeAccount" />元</td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>解冻原因：</td>
						<td><sf:textarea path="reason" cssStyle="width:250px;" rows="5" /></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="addFreezeButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#addFreezeForm").validate(addFre);
			});
			$("#addFreezeButton").bind("click", function() {
				var addFreezeForm = $("#addFreezeForm");
				if (!addFreezeForm.validate().form()) {
					return;
				}
				addFreezeForm.ajaxSubmit({
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
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
<script type="text/javascript">
var search = function() {
	var form = $("#searchForm");
	$("#page").val(1);
	form.submit();
};
var release = function(freezeQueueId,realName,count) {
	$.dialog({
		autoOpen:false,
		width : 650,
		title : "分销商预存款账户资金解冻",
		content : $("#freezeBox").html()
	});
	document.getElementById("form_freezeQueueId").value = freezeQueueId;
	document.getElementById("rechargeForm_userName").value = realName;
	document.getElementById("freezeAccount").value=count;
};
</script>
</html>
