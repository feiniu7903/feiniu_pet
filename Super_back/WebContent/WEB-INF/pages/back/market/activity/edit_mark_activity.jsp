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
    <script src="<%=basePath%>js/market/mark_activity.js"></script>


</head>
<body>
<form id="mark_activity_form">
    <table class="mark_table" cellspacing="1" cellpadding="1" style="width: 800px;">
        <thead>
        <tr>
            <th colspan="2">活动信息:</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>活动名称:</th>
            <td>
                <s:textfield name="markActivity.activityName" id="markActivity_activityName" cssStyle="width:450px;"/>
                <s:hidden name="markActivity.actId"/>
            </td>
        </tr>
        <tr>
            <th>活动状态:</th>
            <td>
                <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_STATUS@values()"
                          listKey="code" listValue="cnName"
                          headerKey="" headerValue="选择"
                          name="markActivity.status" value="markActivity.status"
                          id="markActivity_status"/>
            </td>
        </tr>
        <tr>
            <th>负责人:</th>
            <td>
                <s:textfield name="markActivity.personCharge" id="markActivity_personCharge" cssStyle="width:100px;"/>
            </td>
        </tr>
        <tr>
            <th>选择营销渠道:</th>
            <td>
                <input type="hidden" name="markActivity.markActivityItemEmail.channel" id="markActivity_markActivityItemEmail_channel" value="EMAIL">EDM
                <s:hidden name="markActivity.markActivityItemEmail.actItemId"/>
            </td>
        </tr>
        </tbody>
        <thead>
        <tr>
            <th colspan="2">EMD发送内容设置:</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>发送方式:</th>
            <td>
                <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_SEND_WAY@values()"
                          listKey="code" listValue="cnName"
                          headerKey="" headerValue="选择"
                          name="markActivity.markActivityItemEmail.sendWay" value="markActivity.markActivityItemEmail.sendWay"
                          id="markActivity_markActivityItemEmail_sendWay"/>
            </td>
        </tr>
        <tr>
            <th>发送时间:</th>
            <td>
                <input type="text" class="Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       name="markActivity.markActivityItemEmail.sendTime" id="markActivity_markActivityItemEmail_sendTime"
                       value="<s:date name="markActivity.markActivityItemEmail.sendTime" format="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr id="cycle" style="display: none;">
            <th>循环设定:</th>
            <td>
                <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_CYCLE@values()"
                          listKey="code" listValue="cnName"
                          name="markActivity.markActivityItemEmail.cycle" value="markActivity.markActivityItemEmail.cycle"
                          id="markActivity_markActivityItemEmail_cycle"/>
                <span id="week" style="display: none;">
                <s:checkboxlist list="@com.lvmama.comm.vo.Constant$WEEK@values()"
                                listKey="code" listValue="cnName"
                                name="weeks" value="weeks"/>
                </span>

            </td>
        </tr>
        <tr>
            <th>选择数据模型:</th>
            <td>
                <s:select list="dataModelMap"
                          listKey="key" listValue="value"
                          headerKey="" headerValue="选择"
                          name="markActivity.markActivityItemEmail.dataModel" value="markActivity.markActivityItemEmail.dataModel"
                          id="markActivity_markActivityItemEmail_dataModel"/>

                <span id="dataModelSpan"></span>
            </td>
        </tr>
        <tr>
            <th>邮件内容URL:</th>
            <td>
                <s:textarea name="markActivity.markActivityItemEmail.content" id="markActivity_markActivityItemEmail_content" cssStyle="width: 600px;height: 50px;"/>
            </td>
        </tr>
        <tr>
            <th>排除发送次数:</th>
            <td>
                <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_EXCLUDE_SCOPE@values()"
                          listKey="code" listValue="cnName"
                          name="markActivity.markActivityItemEmail.excludeScope" value="markActivity.markActivityItemEmail.excludeScope"/>

                <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_EXCLUDE_SYMBOL@values()"
                          listKey="code" listValue="cnName"
                          name="markActivity.markActivityItemEmail.excludeSymbol" value="markActivity.markActivityItemEmail.excludeSymbol"/>

                <s:textfield name="markActivity.markActivityItemEmail.excludeTimes" id="markActivity_markActivityItemEmail_excludeTimes" cssStyle="width: 60px;text-align: right;"/> 次
            </td>
        </tr>
        <tr>
            <th>最后发送时间:</th>
            <td>
                <s:date name="markActivity.markActivityItemEmail.lastSendTime" format="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <input type="button" value=" 保 存 " id="saveMarkActivityButton" style="width:90px;height: 25px;"/>
                <input type="button" value=" 取 消 " id="cancelMarkActivityButton" style="width:90px;height: 25px;"/>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
<script>
    init();
</script>
</body>
</html>