<%@ page language="java" import="java.util.*,com.lvmama.comm.vo.Constant" pageEncoding="UTF-8"%>
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
<title>点评活动</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">
<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>

<script type="text/javascript">
        $(function(){

        });
        
        function openEditActivityWindow(activityId)
        {
        	$("#editActivityDiv").load("<%=basePath%>/commentManager/openEditActivity.do?activityId=" + activityId,function() {
        		$(this).dialog({
        			modal:true,
        			title:"点评活动编辑",
        			width:950,
        			height:650
            	});
        	});
        }
    </script>
</head>
<body>
<div id="editActivityDiv" style="display: none"></div>
	<div class="main main02">
		<div class="row1">
		<h3 class="newTit">点评活动</h3>
			<table border="1" cellspacing="0" cellpadding="0" class="gl_table" width="100%">
				<tr>
					<th>活动主题</th>
					<th>活动链接</th>
					<th>图片</th>
					<th>操作</th>
				</tr>
				<s:iterator value="cmtActivityList" var="cmtActivity" status="c">
					<tr>
						<td>${cmtActivity.activitySubject}</td>
						<td>${cmtActivity.url}</td>
						<td><img src="<%=Constant.getInstance().getPrefixPic()%>${cmtActivity.pic}" width="100px" height="100px"/></td>
                        <td>
                        <input type="button" class="button" value="查看" onClick="openEditActivityWindow(${cmtActivity.activityId})"/><br/>
                        </td>
					</tr>
				</s:iterator>		
			</table>
		</div>
	</div>
</body>
</html>


