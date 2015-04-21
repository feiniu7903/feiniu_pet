<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单优惠券查询</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		
		<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
		<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
		<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
	</head>
	<body>
		<div id="newMarkCouponDiv" style="display: none"></div>

		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">订单优惠券查询</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>mark/coupon/orderCouponSearch.do" method="post">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">订单号：</td>
							<td>
								<input id="orderId"  name="orderId" value="${orderId}" class="newtext1"/>
							</td>	
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button></p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>使用优惠明细：</th>
					</tr>
					<s:iterator value="markCouponUsageDescs" var="desc">
						<tr>
							<td>${desc }</td>
						</tr>	
					</s:iterator>
				</table>			
			</div>
		</div>
	</body>	
</html>


