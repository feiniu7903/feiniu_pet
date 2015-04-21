<%--
  Created by IntelliJ IDEA.
  User: troy-kou
  Date: 13-12-17
  Time: 上午11:47
  Email:kouhongyu@163.com
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title></title>
    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <script>
        var basePath = "<%=basePath%>";
    </script>
    <link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
    <link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>

    <link rel="stylesheet" type="text/css" href="<%=basePath %>themes/base/mark/mark_activity.css">

    <script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>

    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <%@ include file="/WEB-INF/pages/back/base/timepicker.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>


</head>
<body>
<form id="com_task_form">
    <table class="mark_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <thead>
        <tr>
            <th colspan="2">日志信息:</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>日志ID:</th>
            <td><s:property value="comTaskLog.logId"/></td>
        </tr>
        <tr>
            <th>任务名称:</th>
            <td><s:property value="comTaskLog.taskName"/></td>
        </tr>
        <tr>
            <th>返回状态:</th>
            <td>
                <s:if test="comTaskLog.resultStatus==1">完成</s:if>
                <s:if test="comTaskLog.resultStatus==2">部分完成</s:if>
                <s:if test="comTaskLog.resultStatus==3">未完成</s:if>
            </td>
        </tr>
        <tr>
            <th>任务开始时间:</th>
            <td><s:date name="comTaskLog.startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>任务结束时间:</th>
            <td><s:date name="comTaskLog.endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>返回信息:</th>
            <td><s:property value="comTaskLog.resultInfo"/></td>
        </tr>
        <tr>
            <th>异常信息:</th>
            <td><s:property value="comTaskLog.exceptionInfo"/></td>

        </tr>
        </tbody>
    </table>
</form>
</body>
</html>