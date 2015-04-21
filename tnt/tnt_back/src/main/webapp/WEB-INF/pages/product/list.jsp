<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
<link href="http://super.lvmama.com/super_back/style/houtai.css"
	rel="stylesheet" type="text/css" />
<script src='<c:url value="/js/timeprice/time.js"/>'></script>
<script type="text/javascript">
	var setDialog = null;
</script>
</head>
<body>
	<div class="iframe_search">
		<sf:form method="POST" modelAttribute="tntProduct"
			action="/product/list" id="searchForm">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">产品名称：</td>
						<td class="w14"><sf:input path="productName" /></td>

						<td class="s_label">产品ID：</td>
						<td class="w14"><sf:input path="productId" /></td>

						<td class="s_label">类别ID：</td>
						<td class="w14"><sf:input path="branchId" /></td>


					</tr>
					<tr>
						<%-- <td class="s_label">商品类型：</td>
						<td class="w14"><sf:select path="isAperiodic">
								<option value="" label="--所有-" />
								<sf:options items="${prodAperiodicMap }" />
							</sf:select></td> --%>
						<%-- <td class="s_label">是否分销：</td>
						<td class="w14"><sf:select path="valid">
								<sf:option value="" label="--所有--" />
								<sf:options items="${isDistMap }" />
							</sf:select></td> --%>
						<td class="s_label">期票：</td>
						<td class="w14"><sf:checkbox path="isAperiodic" value="true" /></td>

						<td class="s_label">支付方式：</td>
						<td class="w14"><sf:select path="payToLvmama">
								<sf:option value="" label="--所有--" />
								<sf:options items="${payTypeMap }" />
							</sf:select></td>

						<td class="s_label">分销渠道类型：</td>
						<td class="w14"><sf:select path="channelId">
								<sf:option value="" label="--所有--" />
								<sf:options items="${channelMap }" />
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
		<c:if test="${tntProductList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>类别ID</th>
							<th>类别名称</th>
							<th>类别状态</th>
							<th>产品ID</th>
							<th>产品名称</th>
							<th>产品状态</th>
							<th>景区ID</th>
							<th>景区名称</th>
							<th>时间价格表</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntProduct" items="${tntProductList}">
							<tr>
								<td>${tntProduct.branchId}</td>
								<td>${tntProduct.prod.branchName}</td>
								<td><lv:mapValueShow key="${tntProduct.prod.branchStatus}"
										map="${validMap }" /></td>
								<td>${tntProduct.productId}</td>
								<td>${tntProduct.productName}</td>
								<td><lv:mapValueShow key="${tntProduct.prod.productStatus}"
										map="${validMap }" /></td>
								<td>${tntProduct.prod.placeId}</td>
								<td>${tntProduct.prod.placeName}</td>
								<td class="oper"><a href="#timePrice" tt="PROD_PRODUCT"
									class="showTimePrice"
									param="{'prodBranchId':${tntProduct.branchId },'editable':false}">查看时间价格</a>
								</td>
								<td class="oper"><a href="distset/${tntProduct.branchId }"
									target="${tntProduct.branchId }">分销设置</a></td>
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
		<c:if test="${tntProductList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		$("#searchForm").validate(tntProduct);
		var search = function() {
			$("#page").val(1);
			var form = $("#searchForm");
			if (!form.validate().form()) {
				return;
			}
			form.submit();
		};
	</script>
</body>
</html>