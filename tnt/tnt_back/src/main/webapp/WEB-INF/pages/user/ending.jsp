<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
		<sf:form method="POST" modelAttribute="tntUser" action="/user/ending"
			id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">&nbsp;&nbsp;分销商名称：</td>
						<td class="w14"><sf:input path="realName" /></td>
						<td class="s_label">合同开始时间：</td>
						<td><sf:input path="queryStartContractStartDate"
								onFocus="WdatePicker({readOnly:false})" />--<sf:input
								path="queryEndContractStartDate"
								onFocus="WdatePicker({readOnly:false})" /></td>
						<td class="s_label operate mt20" align="right"><a
							class="btn btn_cc1" id="search_button"
							href="javascript:search();">查询</a></td>
					</tr>
					<tr>
						<td class="s_label">分销商用户名：</td>
						<td class="w14"><sf:input path="userName" /></td>
						<td class="s_label">合同到期时间：</td>
						<td><sf:input path="queryStartContractEndDate"
								onFocus="WdatePicker({readOnly:false})" />--<sf:input
								path="queryEndContractEndDate"
								onFocus="WdatePicker({readOnly:false})" /></td>
					</tr>
				</tbody>
			</table>

			<div class="ui_title f14">
				<ul class="ui_tab">
					<li><a href="<c:url value="/user/doing"/>">合作中的分销商</a></li>
					<li><a href="<c:url value="/user/waiting"/>">等待审核分销商</a></li>
					<li class="active"><a href="<c:url value="/user/ending"/>">已终止合作的分销商</a></li>
				</ul>
			</div>
		</sf:form>
	</div>

	<div class="iframe_content mt20">
		<c:if test="${tntUserList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>分销商用户名</th>
							<th>分销商类型</th>
							<th>支付方式</th>
							<th>合同开始时间</th>
							<th>合同到期时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="tntUser" items="${tntUserList}">
							<tr>
								<td>${tntUser.realName}</td>
								<td><c:if test="${tntUser.isCompany}">
										${tntUser.detail.companyName}
									</c:if> <c:if test="${!tntUser.isCompany}">
										个人
									</c:if></td>
								<td>${tntUser.userName}</td>
								<td><c:if test="${tntUser.isCompany==true }">
										<lv:mapValueShow key="${tntUser.detail.companyTypeId}"
											map="${companyTypeMap }" />
									</c:if> <c:if test="${tntUser.isCompany==false }">
										个人
									</c:if></td>
								<td><lv:mapValueShow key="${tntUser.detail.payType }"
										map="${payTypeMap }" /></td>
								<td><lv:dateOutput
										date="${tntUser.detail.contractStartDate }" /></td>
								<td><lv:dateOutput
										date="${tntUser.detail.contractEndDate }" /></td>
								<td class="oper"><a href="#" class="baseInfo"
									id="${tntUser.userId}">基本信息</a> <a
									href="javascript:showMaterial('${tntUser.userId }');">基本资料</a>
									<a
									href="javascript:showContractBox('<c:url value="/user/contract/list" />','${tntUser.userId }')">合同管理</a>
									<a
									href="javascript:showEndReason('${tntUser.detail.failReason }');">终止合作原因</a>
									<a href="javascript:repeatDoing('${tntUser.userId }');">重新合作</a></td>
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
		<c:if test="${tntUserList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关分销商，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<jsp:include page="/WEB-INF/pages/user/common_list.jsp"></jsp:include>
	<script type="text/javascript">
		//显示终止合作原因
		var showEndReason = function(reason) {
			$.dialog({
				width : 400,
				title : "终止合作原因",
				content : reason
			});
		};

		//重新合作
		var repeatDoing = function(userId) {
			$.dialog({
				width : 400,
				title : "重新合作",
				ok : function() {
					var url = "ending/repeatDo/" + userId;
					$.getJSON(url, function(data) {
						var json = eval(data);
						if (json == true) {
							search();
						}
					});
				},
				okValue : "确定",
				content : "您确定要和该分销商重新合作吗？",
				cancel : true,
				cancelValue : "取消"
			});
		};
	</script>
</body>
</html>