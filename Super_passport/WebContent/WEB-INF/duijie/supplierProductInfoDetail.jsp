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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<title>产品详情页面</title>
		<link rel="stylesheet" type="text/css" href="/super_back/style/ui-components.css" >
		<link rel="stylesheet" type="text/css" href="/super_back/style/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="/super_back/style/panel-content.css"></link>
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
	color: #454560;
	margin: 0 10px;
}

.pormpt_ {
	color: #454545;
	line-height: 28px;
}

.pormpt_ h3 {
	background: url(../../img/bg.gif) no-repeat 10px -102px; *
	background-position: 10px -104px;
	height: 28px;
	line-height: 28px;
	border-bottom: 1px solid #959595;
	padding: 0 20px;
	font-weight: 700;
}

.pormpt_ ul {
	margin: 0px 10px 20px 10px;
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
	var id;
	<s:if test="lvmamaProdId">
	id = ${lvmamaProdId};
	</s:if>
	if(id != null){
	parent.window.open(
			'http://www.lvmama.com/product/preview.do?id=' +id,
			'newwindow', '');
	}else{
		alert('还没入库的产品没有驴妈妈的展示页面');
	}
}
</script>
	</head>
	<body>

		<div style="font-size: 12px;">
			<div class="pormpt_top">
				<dl>
					<dt>
						${productName }&nbsp;&nbsp;&nbsp;
					</dt>
					<dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showFrontProductInfo();">查看前台页面</a>
					</dt>
					<dd>
						<s:if test="#productName">
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
					</dd>
					<dd>
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
					<s:if test="contentmap['MANAGERRECOMMEND']==null">
				没有添加产品经理推荐
				</s:if>
				<s:else>
					<s:property value="contentmap['MANAGERRECOMMEND']" />
				</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt15">
				<h3>
					内部提示
				</h3>
				<ul>
					<s:if test="contentmap['INTERIOR']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['INTERIOR']"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt07">
				<h3>
					公告
				</h3>
				<ul>
					<s:if test="contentmap['ANNOUNCEMENT']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['ANNOUNCEMENT']" />
					</s:else>
				</ul>

			</div>
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt02">
				<h3>
					费用包含
				</h3>
				<ul>
					<s:if test="contentmap['COSTCONTAIN']==null">无</s:if>
					<s:else>
						<s:property value="contentmap['COSTCONTAIN']" />
					</s:else>
				</ul>

			</div>
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt03">
				<h3>
					费用不包含
				</h3>
				<ul>
					<s:if test="contentmap['NOCOSTCONTAIN']">
				无</s:if>
					<s:else>
						<s:property value="contentmap['NOCOSTCONTAIN']" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt_" id="pormpt04">
				<h3>
					推荐项目
				</h3>
				<ul>
					<s:if test="contentmap['RECOMMENDPROJECT']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['RECOMMENDPROJECT']" />
					</s:else>
				</ul>

			</div>

			<div class="pormpt_" id="pormpt05">
				<h3>
					购物说明
				</h3>
				<p>
					<s:if test="contentmap['SHOPPINGEXPLAIN']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['SHOPPINGEXPLAIN']" />
					</s:else>
				</p>

			</div>
			<div class="pormpt_" id="pormpt06">
				<h3>
					退款说明
				</h3>
				<p>
					<s:if test="contentmap['REFUNDSEXPLANATION']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['REFUNDSEXPLANATION']" />
					</s:else>
				</p>

			</div>
			<div class="pormpt_" id="pormpt08">
				<h3>
					预订须知（原预订须知）
				</h3>
				<ul>
					<s:if test="contentmap['ORDERTOKNOWN']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['ORDERTOKNOWN']" />
					</s:else>
				</ul>

			</div>
			<div class="pormpt_" id="pormpt09">
				<h3>
					行前须知（原产品特别提示）
				</h3>
				<p>
					<s:if test="contentmap['ACITONTOKNOW']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['ACITONTOKNOW']" />
					</s:else>
				</p>

			</div>
			<div class="pormpt_" id="pormpt10">
				<h3>
					旅游服务保障
				</h3>
				<ul>
					<s:if test="contentmap['SERVICEGUARANTEE']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['SERVICEGUARANTEE']" />
					</s:else>
				</ul>

			</div>
			<!--pormpt_ end-->
			<div class="pormpt_" id="pormpt11">
				<h3>
					行程说明
				</h3>
				<s:iterator value="journeyList">
					<h4>
						第
						<s:property value="seq" />
						天：
						<s:property value="title" />
					</h4>
					<ul>
						<li>
						<p>
							<s:property value="content" />
						</p>
						</li>
						<li>
							用餐：
							<s:property value="dinner" />
						</li>
						<li>
							住宿：
							<s:property value="hotel" />
						</li>

					</ul>
				</s:iterator>
			</div>
			<div class="pormpt_" id="pormpt12">
				<h3>
					游玩提示
				</h3>
				<ul>
					<s:if test="contentmap['PLAYPOINTOUT']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['PLAYPOINTOUT']"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt13">
				<h3>
					交通信息
				</h3>
				<ul>
					<s:if test="contentmap['TRAFFICINFO']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['TRAFFICINFO']"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt14">
				<h3>
					签证/签注
				</h3>
				<ul>
					<s:if test="contentmap['VISA']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['VISA']" escape="false"/>
					</s:else>
				</ul>
			</div>
			<div class="pormpt_" id="pormpt14">
				<h3>
					产品特色
				</h3>
				<ul>
					<s:if test="contentmap['FEATURES']==null">
				无</s:if>
					<s:else>
						<s:property value="contentmap['FEATURES']" escape="false"/>
					</s:else>
				</ul>
			</div>
			<!--pormpt_ end-->
		</div>
		<!--main end-->

	</body>
</html>