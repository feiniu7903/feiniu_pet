<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@taglib prefix="sf"
	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道分销商设置</title>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<body>
	<div class="iframe_content mt10 clearfix">
		<div class="tiptext tip-warning cc5">
			<span class="tip-icon tip-icon-warning"></span>友情提示：
			<p class="pl15">
				1）默认所有可分销商品（产品状态：有效、产品在上下线时间区间、产品上下线状态：上线、产品销售渠道：驴妈妈前台、产品类别上下线状态：上线）均可在B2B平台销售；
			</p>
			<p class="pl15">2）商品的默认分销价规则按渠道类型设置分销价规则；</p>
			<span style="color: red">注：</span>
			<p class="pl15">1）如果需要根据具体商品、具体分销商需要单独设置分销价策略，可以在该处设置。</p>
			<p class="pl15">2）如果需要根据具体商品、所有分销商需要单独设置分销价策略，可以在渠道类型设置页面进行设置。</p>
		</div>
		<div class="iframe_search">
			<sf:form id="policySearchForm" action="/product/distset/user"
				modelAttribute="tntProdPolicy">
				<input type="hidden" name="page" id="page"
					value="${page.currentPage }" />
				<sf:hidden path="branchId" />
				<sf:hidden path="productId" />
				<table class="s_table">
					<tbody>
						<tr>
							<td class="s_label">分销商渠道类型：</td>
							<td class="w14"><sf:select path="channelId"
									onchange="changeChannel(this)">
									<sf:option value="" label="--请选择--" />
									<sf:options items="${channelMap}" />
								</sf:select></td>
							<td class="s_label">分销商类型：</td>
							<td class="w14"><sf:select path="user.detail.companyTypeId"
									id="companyTypeId">
									<sf:option value="" label="--请选择--" />
									<sf:options items="${typeMap}" />
								</sf:select></td>
							<td class="s_label">分销商名称：</td>
							<td class="w14"><sf:input path="user.realName" /></td>
							<td class="s_label"></td>
							<td class="w14"><div class="operate">
									<a class="btn btn_cc1" href="javascript:searchForm()">查询</a>
								</div></td>
						</tr>
					</tbody>
				</table>
			</sf:form>
		</div>
		<div class="p_box box_info">
			<table class="p_table table_center">
				<thead>
					<tr>
						<th>分销商名称</th>
						<th>公司名称</th>
						<th>分销渠道类型</th>
						<th>分销商类型</th>
						<th>分销价格</th>
						<th>分销库存</th>
						<th>点评返现</th>
						<th>销售返佣</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tntProdPolicyList }" var="tntProdPolicy">
						<tr>
							<td>${tntProdPolicy.realName }</td>
							<td>${tntProdPolicy.companyName }</td>
							<td><lv:mapValueShow map="${channelMap }"
									key="${tntProdPolicy.channelId }" /></td>
							<td><lv:mapValueShow map="${companyTypeMap }"
									key="${tntProdPolicy.companyTypeId }" /></td>
							<td>${tntProdPolicy.priceRule }</td>
							<td>公共库存</td>
							<td>无</td>
							<td><c:if test="${tntProdPolicy.rebate }">是</c:if> <c:if
									test="${!tntProdPolicy.rebate }">否</c:if></td>
							<td class="oper"><a
								href="javascript:toSet('${tntProdProduct.branchId }','${tntProdPolicy.channelId }','${tntProdPolicy.userId }')"
								id="modifyButton">修改</a>&#160;&#160;<a
								param="{'objectType':'TNT_POLICY','objectId':'${tntProdPolicy.tntPolicyId}','logType':'changeProdDistributor'}"
								class="showLogDialog" href="#log">查看日志</a></td>
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
					postSubmitPages("policySearchForm");
				</script>
			</c:if>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script src='<c:url value="/js/log.js"/>'></script>
<script type="text/javascript">
	var searchForm = function() {
		$("#page").val(1);
		$("#policySearchForm").submit();
	};
	var toSet = function(branchId, channelId, userId) {
		var url = "user/policy/" + branchId + "?channelId=" + channelId
				+ "&userId=" + userId;
		new xDialog(
				url,
				null,
				{
					width : 500,
					/* title : "产品名称：${tntProdProduct.productName}&#160;&#160;&#160;类别名称：${tntProdProduct.branchName}" */
					title : "渠道分销商设置"
				});
	};
</script>
</html>
