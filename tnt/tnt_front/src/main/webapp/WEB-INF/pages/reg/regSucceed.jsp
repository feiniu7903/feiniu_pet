<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>激活成功-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css" rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css"> 
</head>

<body>
<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>

<div class="flowstep_wrap">
		<div style="display: none" id="filterPage"></div>
	<div class="flowstep step04"></div>
    <div class="tipbox tip-success tip-nowrap">
    		<c:if test="${result=='true'}">
	            <span class="tip-icon-big tip-icon-big-success"></span>
	            <div class="tip-content">
	                <h3 class="tip-title">恭喜您，账号已成功激活，请您补全资料。</h3>
	                <p class="tip-explain">您可以：<a class="btn cbtn-blue btn-small" href="/login/index">立即登录</a></p>
	            </div>
            </c:if>
            <c:if test="${result!='true'}">
	            <div class="tip-content">
	                <h3 class="tip-title">对不起，验证无效或验证码失效。</h3>
	            </div>
            </c:if>
	</div>
</div>


<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>

</body>
</html>