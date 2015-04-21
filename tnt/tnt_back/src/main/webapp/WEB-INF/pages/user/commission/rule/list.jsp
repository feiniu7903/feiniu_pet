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
		<sf:form id="searchForm" method="POST"
			modelAttribute="tntCommissionRule"
			action="/user/commission/rule/list">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">产品类型：</td>
						<td class="w18"><sf:select path="productType"
								onchange="changeProductType(this,'subProductType')">
								<option value="" label="--请选择--" />
								<sf:options items="${productTypeMap}" />
							</sf:select></td>
						<td class="s_label">子类型：</td>
						<td class="w18"><sf:select path="subProductType">
								<option value="" label="--请选择--" />
								<sf:options items="${subProductTypeMap }" />
							</sf:select></td>
						<td class="s_label">支付方式：</td>
						<td class="w18"><sf:select path="payOnline">
								<option value="" label="--请选择--" />
								<sf:options items="${payOnlineMap }" />
							</sf:select></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							id="search_button" href="javascript:search();">查询</a></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							onclick="toAdd()" id="new_button">添加</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
		<c:if test="${tntCommissionRuleList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>产品类型</th>
							<th>子类型</th>
							<th>支付方式</th>
							<th>月销售额（元）</th>
							<th>分销返佣点</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCommissionRule"
							items="${tntCommissionRuleList}">
							<tr>
								<td><lv:mapValueShow key="${tntCommissionRule.productType}"
										map="${productTypeMap }" /></td>
								<td><lv:mapValueShow
										key="${tntCommissionRule.subProductType}"
										map="${tdSubProductTypeMap }" /></td>
								<td><lv:mapValueShow key="${tntCommissionRule.payOnline}"
										map="${payOnlineMap }" /></td>
								<td>${tntCommissionRule.rule}</td>
								<td>${tntCommissionRule.discountRate}%</td>
								<td class="oper"><a
									href="javascript:toModify('${tntCommissionRule.commissionRuleId }')"
									class="baseInfo">修改</a> <a
									href="javascript:toDelete('${tntCommissionRule.commissionRuleId }')">删除</a></td>
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
		<c:if test="${tntCommissionRuleList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目，重新输入相关条件查询！
			</div>
		</c:if>
	</div>

	<!-- 添加分销商返佣规则-->
	<div style="display: none" id="addTypeBox">
		<sf:form id="addTypeForm" modelAttribute="tntCommissionRule"
			target="_top" action="/user/commission/rule">
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>产品类型：</td>
						<td><sf:select path="productType"
								onchange="changeProductType(this,'addTypeBox_subProductType')">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${productTypeMap}" />
							</sf:select></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>子类型：</td>
						<td><sf:select path="subProductType"
								id="addTypeBox_subProductType">
								<option value="" label="--请选择--" />
								<sf:options items="${subProductTypeMap }" />
							</sf:select></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>支付方式：</td>
						<td><sf:select path="payOnline">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${payOnlineMap}" />
							</sf:select></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>月销售额：</td>
						<td><sf:input path="minSales" size="5" />-<sf:input
								path="maxSales" size="5" /></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销返佣点：</td>
						<td><sf:input path="discountRate" /></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input class="pbtn pbtn-small btn-ok" id="typeSaveButton"
					style="float: right; margin-top: 20px;" type="button" value="保存" />
			</div>
		</sf:form>
		<script type="text/javascript">
			$().ready(function() {
				$("#addTypeForm").validate(commissionRule);
			});

			$("#typeSaveButton").bind("click", function() {
				var form = $("#addTypeForm");
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

	<!-- 删除分销商类型-->
	<div style="display: none" id="deleteBox">
		<sf:form id="deleteTypeForm" method="post"
			action="/user/commission/rule" target="_top">
			<input type="hidden" name="_method" value="delete" />
			<input type="hidden" name="commissionRuleId"
				id="deleteBox_companyTypeId" />
			<div class="iframe_content pd0">
				<div>确认要删除该分销商返佣规则！</div>
			</div>
			<div>
				<input type="submit" value="确定" />
			</div>
		</sf:form>
	</div>


	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script type="text/javascript">
		var search = function() {
			$("#page").val(1);
			$('#searchForm').submit();
		};

		//添加分销商类型
		var toAdd = function() {
			$.dialog({
				width : 550,
				title : "新增返佣规则",
				content : $("#addTypeBox").html()
			});
		};

		//显示修改类型弹窗
		var toModify = function(typeId) {
			var url = "edit/" + typeId;
			new xDialog(url, null, {
				title : "修改返佣规则",
				width : 550
			});

		};

		//显示删除类型弹窗
		var toDelete = function(typeId) {
			var url = "toDelete/" + typeId;
			$
					.getJSON(
							url,
							function(data) {
								var flag = eval(data);
								if (flag) {
									$
											.dialog({
												width : 400,
												height : 150,
												title : "删除分销商返佣规则",
												content : "确认要删除该分销商返佣规则！",
												okValue : "确定",
												ok : function() {
													document
															.getElementById("deleteBox_companyTypeId").value = typeId;

													$("#deleteTypeForm")
															.ajaxSubmit(
																	{
																		success : function(
																				data) {
																			$(
																					"#searchForm")
																					.submit();
																		},
																		error : function(
																				XmlHttpRequest,
																				textStatus,
																				errorThrown) {
																			alert("系统处理异常！");
																		}
																	});
												}
											});
								} else {
									$
											.dialog({
												width : 400,
												height : 150,
												title : "删除分销商返佣规则",
												ok : true,
												okValue : "确定",
												content : "该返佣规则有分销商不可以删除，请先把分销商移到其他返佣规则再删除！"

											});
								}
							});

		};
	</script>
</body>
</html>
