<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="<%=request.getContextPath()%>/style/houtai.css"
			rel="stylesheet" type="text/css" />
		<s:if test="needJquery != null && needJquery">
			<script type="text/javascript"
				src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js">
			</script>
			<script type="text/javascript"
				src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js">
			</script>
			<link
				href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css"
				rel="stylesheet" type="text/css" />
		</s:if>
	</head>
	<body>
		<s:if test="pageId != null && pageId > 0">
			<div class="mainTop" style="font-size: 11px;">
				<div id="productDetailTab">
					<ul>
						<li>
							<a
								href="${basePath}/phoneOrder/showImportantTips.do?pageId=${pageId}">产品描述及行程</a>
						</li>
						<li>
							<a
								href="${basePath}/phoneOrder/showImportantTipsProdDetail.do?pageId=${pageId}&prodBranchId=${prodBranchId}">产品详细信息</a>
						</li>
					</ul>
				</div>
			</div>
		</s:if>
		<s:else>
			该产品不存在！
		</s:else>
		<!--main end-->

		<script type="text/javascript">
//加载门票、酒店、线路tabs
$(function() {
	var index = 0;
	$('#productDetailTab').tabs( {
		selected : index
	});
});

//--------------******------------------
function showFrontProductInfo() {
window.open('http://www.lvmama.com/product/preview.do?id=' + ${pageId},'newwindow', '')
}
</script>
	</body>
</html>
