<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="main index">
		<div class="login_company">
			<div class="login_company_main">
				<dl>
					<dt>注册驴妈妈企业分销商，您便可以：</dt>
					<dd>
						<span>1</span>获取覆盖全球的旅游产品，扩大业务覆盖率。
					</dd>
					<dd>
						<span>2</span>更好的满足客服个性化需求，提升品牌影响力。
					</dd>
					<dd>
						<span>3</span>低价帮客服订购旅游产品，赚取差价。
					</dd>
					<dd>
						<span>4</span>扩大销售额，赚取佣金。
					</dd>
				</dl>
				<a href="/reg/reg" title="注册"></a>
			</div>
		</div>
		<div class="login_person">
			<div class="login_person_main">
				<dl>
					<dt>注册驴妈妈个人分销商，您便可以：</dt>
					<dd>
						<span>1</span>帮亲朋好友低价购买旅游产品，快乐旅行。
					</dd>
					<dd>
						<span>2</span>低价购买旅游产品，成为旅行达人。
					</dd>
					<dd>
						<span>3</span>低价帮客服订购旅游产品，赚取差价。
					</dd>
					<dd>
						<span>4</span>扩大销售额，赚取佣金。
					</dd>
				</dl>

				<a href="/reg/regPerson" title="注册"></a>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>