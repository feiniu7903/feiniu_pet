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
		<sf:form id="searchForm" method="POST" modelAttribute="tntCompanyType"
			action="/user/type/list">
			<input type="hidden" name="page" id="page"
				value="${page.currentPage }" />
			<table class="s_table">
				<tbody>
					<tr>
						<td class="s_label">分销商渠道类型：</td>
						<td class="w18"><sf:select path="channelId"
								onchange="changeChannel(this)">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${channelMap}" />
							</sf:select></td>
						<td class="s_label">分销商类型：</td>
						<td class="w18"><sf:select path="companyTypeId"
								id="companyTypeId">
								<option value="" label="--请选择--" />
								<sf:options items="${typeMap}" />
							</sf:select></td>

						<td class=" operate mt10"><a class="btn btn_cc1"
							id="search_button" href="javascript:search()">查询</a></td>
						<td class=" operate mt10"><a class="btn btn_cc1"
							onclick="toAddChannel()" id="new_button">新增渠道类型</a></td>

						<td class=" operate mt10"><a class="btn btn_cc1"
							onclick="toAdd()" id="new_button">新增分销商类型</a></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
	<div class="iframe_content mt20">
		<c:if test="${tntCompanyTypeList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商渠道类型</th>
							<th>分销商类型</th>
							<th>分销商数量</th>
							<th>分销商</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCompanyType" items="${tntCompanyTypeList}">
							<tr>
								<td><lv:mapValueShow key="${tntCompanyType.channelId}"
										map="${channelMap }" /></td>
								<td>${tntCompanyType.companyTypeName}</td>
								<td>${tntCompanyType.total}</td>
								<td><a
									href="javascript:showUsers('${tntCompanyType.companyTypeId }','${tntCompanyType.companyTypeName }');"
									class="baseInfo">查看分销商</a></td>
								<td class="oper"><a
									href="javascript:toModify('${tntCompanyType.companyTypeId }')"
									class="baseInfo">修改</a> <a
									href="javascript:toDelete('${tntCompanyType.companyTypeId }')">删除</a></td>
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
		<c:if test="${tntCompanyTypeList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关产品，重新输入相关条件查询！
			</div>
		</c:if>
	</div>

	<!-- 添加分销商渠道 -->
	<div style="display: none" id="addChannelBox">
		<sf:form id="addChannelForm" target="_top" action="/user/channel"
			modelAttribute="tntChannel" method="post">
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销渠道类型名：</td>
						<td><sf:input path="channelName" /></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input class="pbtn pbtn-small btn-ok" id="channelSaveButton"
					style="float: right; margin-top: 20px;" type="button" value="保存" />
			</div>
		</sf:form>
		<script type="text/javascript">
			$().ready(function() {
				$("#addChannelForm").validate(companyChannel);
			});
			$("#channelSaveButton").bind("click", function() {
				var form = $("#addChannelForm");
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

	<!-- 添加分销商类型-->
	<div style="display: none" id="addTypeBox">
		<sf:form id="addTypeForm" modelAttribute="tntCompanyType"
			target="_top" action="/user/type">
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销渠道类型：</td>
						<td><sf:select path="channelId">
								<sf:option value="" label="--请选择--" />
								<sf:options items="${channelMap}" />
							</sf:select></td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>分销商类型名：</td>
						<td><sf:input path="companyTypeName" /></td>
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
				$("#addTypeForm").validate(companyType);
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
		<sf:form id="deleteTypeForm" method="post" action="/user/type"
			target="_top">
			<input type="hidden" name="_method" value="delete" />
			<input type="hidden" name="companyTypeId"
				id="deleteBox_companyTypeId" />
			<div class="iframe_content pd0">
				<div>确认要删除该分销商类型！</div>
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
				title : "新增分销商类型",
				content : $("#addTypeBox").html()
			});
		};

		//添加销售渠道
		var toAddChannel = function() {
			$.dialog({
				width : 550,
				title : "添加分销渠道类型",
				content : $("#addChannelBox").html()
			});
		};

		//显示修改类型弹窗
		var toModify = function(typeId) {
			var url = "edit/" + typeId;
			new xDialog(url, null, {
				title : "修改分销商类型",
				width : 550
			});
		};

		//查看分销商
		var showUsers = function(typeId, typeName) {
			var url = "users/" + typeId;
			new xDialog(url, null, {
				title : "查看分销商-" + typeName,
				width : 600
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
												title : "删除分销商类型",
												content : "确认要删除该分销商类型！",
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
												title : "删除分销商类型",
												ok : true,
												okValue : "确定",
												content : "该类型有分销商不可以删除，请先把分销商移到其他类型后再删除！"

											});
								}
							});

		};
	</script>
</body>
</html>