<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=basePath%>style/detail_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ord/detail_js.js"></script>
		<title>后台_下单详情</title>
	</head>
	<body>

		<div class="main main07">
			<div class="pormptTop">
				<dl>
					<dt>
						<s:property value="viewPage.product.productName" />
					</dt>
					<dd>
						<s:if test="viewPage.product.productName">
						重要提示没有添加!
						</s:if>
					</dd>
					<dd>
						<a href="#pormpt01">内部提示</a>
						<a href="#pormpt02">费用说明</a>
						<a href="#pormpt03">费用包含</a>
						<a href="#pormpt04">推荐项目</a>
						<a href="#pormpt05">退款说明</a>
						<a href="#pormpt06">购物说明</a>
						<a href="#pormpt07">公告</a>
						<a href="#pormpt08">产品特别提示</a>
						<a href="#pormpt09">旅游线路服务保障</a>
						<a href="#pormpt10">预订须知</a>
						<a href="#pormpt11">行程说明</a>
					</dd>
				</dl>
			</div>
			<!--pormptTop end-->
			<div class="pormpt" id="pormpt01">
				<h3>
					内部提示
				</h3>
				<ul>
					<s:if test="viewPage.contents.RECOMMENDPROJECT.content==null">
				重要信息没有添加！
				</s:if>
					<s:property value="viewPage.contents.RECOMMENDPROJECT.content" />
				</ul>
			</div>
			<!--pormpt end-->
			<div class="pormpt" id="pormpt02">
				<h3>
					费用说明
				</h3>
				<ul>
					<s:if test="viewPage.product.description==null">
				无
				</s:if>
					<s:else>
						<s:property value="viewPage.product.description" />
					</s:else>
				</ul>

			</div>
			<!--pormpt end-->
			<div class="pormpt" id="pormpt03">
				<h3>
					费用包含
				</h3>
				<ul>
					<s:if test="viewPage.contents.COSTCONTAIN.content">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.COSTCONTAIN.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt04">
				<h3>
					推荐项目
				</h3>
				<ul>
					<s:if test="viewPage.contents.RECOMMENDPROJECT.content">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.RECOMMENDPROJECT.content" />
					</s:else>
				</ul>

			</div>

			<div class="pormpt" id="pormpt05">
				<h3>
					退款说明
				</h3>
				<ul>
					<s:if test="viewPage.contents.REFUNDSEXPLANATION.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.REFUNDSEXPLANATION.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt06">
				<h3>
					购物说明
				</h3>
				<ul>
					<s:if test="viewPage.contents.SHOPPINGEXPLAIN.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.SHOPPINGEXPLAIN.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt07">
				<h3>
					公告
				</h3>
				<ul>
					<s:if test="viewPage.contents.ANNOUNCEMENT.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.ANNOUNCEMENT.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt08">
				<h3>
					产品特别提示
				</h3>
				<ul>
					<s:if test="viewPage.contents.IMPORTMENTCLEW.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.IMPORTMENTCLEW.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt09">
				<h3>
					旅游线路服务保障
				</h3>
				<ul>
					<s:if test="viewPage.contents.SERVICEGUARANTEE.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.SERVICEGUARANTEE.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt" id="pormpt10">
				<h3>
					预订须知
				</h3>
				<ul>
					<s:if test="viewPage.contents.ORDERTOKNOWN.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.ORDERTOKNOWN.content" />
					</s:else>
				</ul>

			</div>
			<!--pormpt end-->
			<div class="pormpt" id="pormpt11">
				<h3>

					行程说明
				</h3>
				<s:iterator value="viewJourneylist">
					<h4>
						第
						<s:property value="seq" />
						天：
						<s:property value="title" />
					</h4>
					<ul>
						<li>
							<s:property value="content" />
						</li>
						<li>
							用餐：
							<s:property value="dinner" />
						</li>
						<li>
							住宿
							<s:property value="hotel" />
						</li>

					</ul>
				</s:iterator>
			</div>
			<!--pormpt end-->
		</div>
		<!--main end-->

	</body>
</html>