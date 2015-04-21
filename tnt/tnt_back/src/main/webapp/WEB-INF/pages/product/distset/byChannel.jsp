<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道类型设置</title>
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
			<p class="pl15">1）如果需要根据具体商品、所有分销商需要单独设置分销价策略，可以在该处设置。</p>
			<p class="pl15">
				2）如果需要根据具体商品、具体分销商需要单独设置分销价策略，可以在渠道分销商设置页面选择分销商单独设置。</p>
		</div>
		<div class="p_box box_info">
			<table class="p_table table_center">
				<thead>
					<tr>
						<th>分销渠道类型</th>
						<th>是否分销</th>
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
							<td><lv:mapValueShow map="${channelMap }"
									key="${tntProdPolicy.targetId }" /></td>
							<td><c:if test="${tntProdPolicy.canDist }">是</c:if> <c:if
									test="${!tntProdPolicy.canDist }">否</c:if></td>
							<td>${tntProdPolicy.priceRule }</td>
							<td>公共库存</td>
							<td>无</td>
							<td><c:if test="${tntProdPolicy.rebate }">是</c:if> <c:if
									test="${!tntProdPolicy.rebate }">否</c:if></td>
							<td class="oper"><a
								href="javascript:toSet('${tntProdPolicy.branchId }','${tntProdPolicy.targetId }')"
								id="modifyButton">修改</a>&#160;&#160;<a
								param="{'objectType':'TNT_POLICY','objectId':'${tntProdPolicy.tntPolicyId}','logType':'changeProdChannelPolicy'}"
								class="showLogDialog" href="#log">查看日志</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
	<script src='<c:url value="/js/log.js"/>'></script>
	<script type="text/javascript">
		var toSet = function(branchId, channelId) {
			var url = "policy/" + branchId + "?channelId=" + channelId;
			new xDialog(
					url,
					null,
					{
						width : 500,
						title : "渠道类型设置"
					});
		};
	</script>
</body>
</html>
