<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>

<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<body>
	<div class="iframe_search">
		<div class="ui_title f14">
			<ul class="ui_tab">
				<li ><a href="<c:url value="/user/channel/channelPolicy"/>">渠道通用策略</a></li>
				<li class="active"><a href="<c:url value="/user/distributor/list"/>">针对分销商策略</a></li>
			</ul>
		</div>
		<sf:form id="policySearchForm" action="/user/distributor/list"
			modelAttribute="tntProdPolicy">
			<table class="s_table">
				<tbody>
					<tr>
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

	<div class="iframe_content mt20">
		<c:if test="${tntProdPolicyList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>分销商类型</th>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>分销价策略</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntProdPolicy" items="${tntProdPolicyList}">
							<tr>
								<td><lv:mapValueShow map="${companyTypeMap }"
									key="${tntProdPolicy.companyTypeId }" /></td>
								<td>${tntProdPolicy.realName }</td>
								<td>${tntProdPolicy.companyName }</td>
								<td>${tntProdPolicy.priceRule }</td>
								<td class="oper">
									<a href="javascript:distPolicy('${tntProdPolicy.userId}','${tntProdPolicy.tntPolicyId}','${tntProdPolicy.discountY}')"  class="baseInfo">修改</a>
									<a param="{'objectType':'TNT_POLICY','objectId':'${tntProdPolicy.userId}','logType':'changeDistributor'}" class="showLogDialog" href="#log">日志</a>
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
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntProdPolicyList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关分销商，重新输入相关条件查询！
			</div>
		</c:if>
	</div>
	<!-- 分销策略设置 -->
	<div style="display: none" id="policyBox">
		<sf:form id="policyForm" target="_top" action="/prodPolicy/saveDistPolicy" method="post">
			<input type = "hidden" name="tntPolicyId" id="policyId" />
			<input type = "hidden" name="userId" id="userId" />
			<table class="p_table form-inline">
				<tbody>
					<tr>
						<td colspan="2">目前通用分销价规则为：分销价=销售价-（销售价 - 结算价）*&nbsp; <span id ="oldDiscount"></span> %</td>
					</tr>
					<tr>
						<td colspan="2">新的通用分销价规则为：分销价=销售价-（销售价 - 结算价）*
						<input name="discountY" id = "newDiscount" style="width:25px;"/>
						 %</td>
					</tr>
					<tr>
						<td class="p_label"><span class="notnull">*</span>修改原因：</td>
						<td><textarea name="reason" style="width:250px;"  rows="5" /></textarea></td>
					</tr>
				</tbody>
			</table>
		</sf:form>
		<div>
			<input class="pbtn pbtn-small btn-ok" id="policyButton"
				style="float: right; margin-top: 20px;" type="button" value="保存" />
		</div>
		<script type="text/javascript">
			$().ready(function() {
				$("#policyForm").validate(prodPolicy);
			});
			$("#policyButton").bind("click", function() {
				var form = $("#policyForm");
				if (!form.validate().form()) {
					return;
				}
				form.ajaxSubmit({
					success : function(data) {
						window.location.href="/tnt_back/user/distributor/list";
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						alert("系统处理异常，请确保您提交的数据的正确！");
					}
				});
			});
		</script>
	</div>
	
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
<script type="text/javascript">
var searchForm = function() {
	$("#policySearchForm").submit();
};
var distPolicy = function(userId,policyId,discount) {
	$.dialog({
		width : 550,
		title : "通用分销价设置",
		content : $("#policyBox").html()
	});
	$("#userId").val(userId);
	$("#policyId").val(policyId);
	$("#oldDiscount").text(discount);
	$("#newDiscount").val(discount);
};
</script>
</body>
</html>