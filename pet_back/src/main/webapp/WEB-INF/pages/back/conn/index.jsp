<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>去来电弹屏</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath %>/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/lvmama_dialog.js"></script>
<s:include value="/WEB-INF/pages/pub/suggest.jsp"/>
<script type="text/javascript" src="<%=basePath %>/js/conn/record.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/city.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/jquery-ui-1.8.5.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/callCenter/call.css" />
 <link href="<%=basePath %>/css/cc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript">
var ctx = "<%=basePath%>";
	var confirmExit;
	(function($) {
		confirmExit = function() {
			if ($('#memo').val() != "") {
				return "该页面还有信息没有保存，确定要离开吗？";
			}
		};
		window.onbeforeunload = confirmExit;
		
		//$("#historyDiv").lvmamaDialog();
	})(jQuery);
</script>
</head>
<body>
<input type="hidden" name="ipccUrl" value="<s:property value='ipccUrl'/>" id="ipccUrl"/>
<TABLE style="FONT-SIZE: 12px; width: 100%;" border=0 cellSpacing=1 cellPadding=4
	bgColor=#b8c9d6>
	<TBODY>
		<TR align=middle>
			<TD width="25%" align="left" valign="top" bgcolor="#ffffff">
				<%@ include file="/WEB-INF/pages/back/conn/leftUp.jsp" %>
			<BR>
				<%@ include file="/WEB-INF/pages/back/conn/leftDown.jsp"%>
			</TD>
			<TD width="75%" align="center" valign="top" bgcolor="#ffffff">
				<%@ include file="/WEB-INF/pages/back/conn/right.jsp"%>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<!--弹出层灰色背景-->
<div id="bg" class="bg" style="display: none;">
	<iframe
		style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
	</iframe>
</div>
<div class="orderpop" id="historyDiv" style="display: none;"
	href="/super_back/ord/showHistoryOrderDetail.do">
</div>
<script type="text/javascript">
	var message='<s:property value="message" escape="false"/>';
	if(message!=""){
		alert(message);
	}
	$("#IDBtnRegistUser").attr("disabled",false);
</script>
</body>
</html>
