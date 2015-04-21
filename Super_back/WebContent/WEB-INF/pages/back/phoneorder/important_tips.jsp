<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
.pormpt_top {
	background: #EAF7FF;
	padding: 10px;
	line-height: 30px;
}

.pormpt_top dt {
	font-size: 14px;
	color: #3972BE;
	font-weight: 700;
}

.pormpt_top dd {
	background: #fff;
}

.pormpt_top dd a {
	color: #454545;
	margin: 0 10px;
}

.pormpt_ {
	color: #454545;
	line-height: 28px;
}

.pormpt_ h3 {
	background: url(../../images/bg.gif) no-repeat 10px -102px; *
	background-position: 10px -104px;
	height: 28px;
	line-height: 28px;
	border-bottom: 1px solid #959595;
	padding: 0 20px;
	font-weight: 700;
}

.pormpt_ ul {
	margin: 0 10px 20px 10px;
}

.pormpt_ h5 {
	color: #E60000;
	padding: 0 10px;
}

.pormpt_ h4 {
	font-weight: 700;
	padding: 0 10px;
}
</style>
		<script type="text/javascript">
function showFrontProductInfo() {
	window.open(
			'http://www.lvmama.com/product/preview.do?id=' + ${pageId},
			'newwindow', '')
}
</script>
	</head>
	<body>

		<div style="font-size: 12px;">
			<div class="pormpt_top">
				<dl>
					<dt>
						<s:property value="viewPage.product.productName" />&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="showFrontProductInfo();">查看前台页面</a>
					</dt>
					<dd>
						<s:if test="viewPage.product.productName">
						重要提示没有添加!
						</s:if>
					</dd>
					<dd>
						<a href="#pormpt01">产品经理推荐</a>
						<a href="#pormpt15">内部提示</a>
						<a href="#pormpt07">公告</a>
						<a href="#pormpt02">费用包含</a>
						<a href="#pormpt03">费用不包含</a>
						<a href="#pormpt04">推荐项目</a>
						<a href="#pormpt05">购物说明</a>
						<a href="#pormpt06">退款说明</a>
						<a href="#pormpt08">预订须知</a>
						<a href="#pormpt09">行前须知</a>
						<a href="#pormpt10">旅游服务保障</a>
						<a href="#pormpt11">行程说明</a>
						<a href="#pormpt12">游玩提示</a>
						<a href="#pormpt13">交通信息</a>
						<a href="#pormpt14">签证/签注</a>
					</dd>
				</dl>
			</div>
			<!--pormpt_top end-->
			<div class="pormpt_" id="pormpt01">
				<h3>
					产品经理推荐
				</h3>
				<ul>
					<s:if test="viewPage.contents.MANAGERRECOMMEND.content==null">
				没有添加产品经理推荐
				</s:if>
					<s:property value="viewPage.contents.MANAGERRECOMMEND.content" />
				</ul>
			</div>
			<div class="pormpt_" id="pormpt15">
				<h3>
					内部提示
				</h3>
				<ul>
					<s:if test="viewPage.contents.INTERIOR==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.INTERIOR.content"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt07">
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
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt02">
				<h3>
					费用包含
				</h3>
				<ul>
					<s:if test="viewPage.contents.COSTCONTAIN.content==null">无</s:if>
					<s:else>
						<s:property value="viewPage.contents.COSTCONTAIN.content" />
					</s:else>
				</ul>

			</div>
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt03">
				<h3>
					费用不包含
				</h3>
				<ul>
					<s:if test="viewPage.contents.NOCOSTCONTAIN.content">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.NOCOSTCONTAIN.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt_" id="pormpt04">
				<h3>
					推荐项目
				</h3>
				<ul>
					<s:if test="viewPage.contents.RECOMMENDPROJECT.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.RECOMMENDPROJECT.content" />
					</s:else>
				</ul>

			</div>

			<div class="pormpt_" id="pormpt05">
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
			<div class="pormpt_" id="pormpt06">
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
			<div class="pormpt_" id="pormpt08">
				<h3>
					预订须知（原预订须知）
				</h3>
				<ul>
					<s:if test="viewPage.contents.ORDERTOKNOWN.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.ORDERTOKNOWN.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt_" id="pormpt09">
				<h3>
					行前须知（原产品特别提示）
				</h3>
				<ul>
					<s:if test="viewPage.contents.ACITONTOKNOW.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.ACITONTOKNOW.content" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt_" id="pormpt10">
				<h3>
					旅游服务保障
				</h3>
				<ul>
					<s:if test="viewPage.contents.SERVICEGUARANTEE.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.SERVICEGUARANTEE.content" />
					</s:else>
				</ul>

			</div>
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt11">
				<h3>
					行程说明
				</h3>
				<s:if test="viewMultiJourneylist != null">
					<h3>${journeyName }</h3>
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
				</s:if>
				<s:else>
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
				</s:else>
			</div>
			<div class="pormpt_" id="pormpt12">
				<h3>
					游玩提示
				</h3>
				<ul>
					<s:if test="viewPage.contents.PLAYPOINTOUT.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.PLAYPOINTOUT.content"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt13">
				<h3>
					交通信息
				</h3>
				<ul>
					<s:if test="viewPage.contents.TRAFFICINFO.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.TRAFFICINFO.content"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt14">
				<h3>
					签证/签注
				</h3>
				<ul>
					<s:if test="viewPage.contents.VISA.content==null">
				无</s:if>
					<s:else>
						<s:property value="viewPage.contents.VISA.content"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt14">
				<h3>
					产品特色
				</h3>
				<ul>
					<s:if test="viewPage.contents.FEATURES.content==null">
				无</s:if>
					<s:else>
						${viewPage.contents.FEATURES.content}
					</s:else>
				</ul>
			</div>
			<!--pormpt_ end-->
		</div>
		<!--main end-->

	</body>
</html>