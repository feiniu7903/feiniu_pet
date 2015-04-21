<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <link href="<%=basePath%>/themes/cc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/back_calendar.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("#pcalendar").loadUrlHtml();
});
$(document).ready(function(){
    $("#backCalendar2").initCalendar({callback:myCall});
    });
var myCall = function(d,stock){
	 var flag = checkStock($("input[name='id']").val(),$("#prod_quantity").val(),d,"");
	if(flag){
		  $("#visitTime").val(d);
	} else {
		  $("#visitTime").val("");
	}
   window.opener.document.getElementById('<%=request.getParameter("inputid") %>').value=d;
   window.close();
}


</script>
</head>
<body>
<input type="hidden" value="<%=request.getParameter("id") %>" name="id" />
<DIV  ID="backCalendar2">
<div class="detailtxtr" id="pcalendar"
	href="<%=basePath%>/common/calendar.do"
	param="{id:<%=request.getParameter("id") %>}"></div>
</DIV>

</body>
</html>