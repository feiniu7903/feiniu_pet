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
    <script src="<%=basePath%>js/base/com_task.js"></script>


</head>
<body>
<form id="com_task_form">
    <table class="mark_table" cellspacing="1" cellpadding="1" style="width: 800px;">
        <thead>
        <tr>
            <th colspan="3">任务信息:</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>任务名称:</th>
            <td colspan="2">
                <s:textfield name="comTask.taskName" id="comTask_taskName" cssStyle="width:450px;"/>
                <s:hidden name="comTask.taskId"/>
            </td>
        </tr>
        <tr>
            <th>
                webService地址:
            </th>
            <td colspan="2">
                <s:textfield name="comTask.webServiceUrl" id="comTask_webServiceUrl" cssStyle="width:450px;"/>
            </td>
        </tr>
        <tr>
            <th>运行状态:</th>
            <td>
                <s:select list="@com.lvmama.comm.vo.Constant$COM_TASK_STATUS@values()"
                          listKey="code" listValue="cnName"
                          name="comTask.status" value="comTask.status"
                          id="comTask_status"/>
                </td>
            <td style="color: #008000;">
                    <div>等待:立即计算出下一次的运行时间</div>
                    <div>运行:如果从非运行状态改为运行状态，会将下次执行时间改为当前时间，状态改为“等待”，下次轮询时运行</div>
                    <div>结束:等待守护任务计算下一次的运行时间，并修改状态</div>
            </td>
        </tr>
        <tr>
            <th>是否启用:</th>
            <td colspan="2">
                <s:select list="@com.lvmama.comm.vo.Constant$COM_TASK_AVAILABLE@values()"
                          listKey="code" listValue="cnName"
                          name="comTask.available" value="comTask.available"
                          id="comTask_available"/>
            </td>
        </tr>
        <tr>
            <th>执行周期:</th>
            <td colspan="2">
                <s:select list="@com.lvmama.comm.vo.Constant$COM_TASK_CYCLE@values()"
                          listKey="code" listValue="cnName"
                          name="comTask.cycle" value="comTask.cycle"
                          id="comTask_cycle"/>

            </td>
        </tr>
        <tr>
            <th>周期尺度:</th>
            <td colspan="2">
                <span id="week" style="display: none;">
                <s:checkboxlist list="@com.lvmama.comm.vo.Constant$WEEK@values()"
                                listKey="code" listValue="cnName"
                                name="weeks" value="weeks"/>
                </span>
                <span id="dimension" style="display: none;">
                    <s:textfield name="comTask.cycleDimension" id="comTask_cycleDimension" cssStyle="text-align: right;width: 50px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <th>计划时间:</th>
            <td colspan="2">
                <input type="text" class="Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       name="comTask.planTime" id="comTask_planTime"
                       value="<s:date name="comTask.planTime" format="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <th>下次运行时间:</th>
            <td colspan="2">
                <input type="text" name="comTask.nextRunTime" value="<s:date name="comTask.nextRunTime" format="yyyy-MM-dd HH:mm:ss"/>" readonly/>
            </td>
        </tr>
        <tr>
            <th>参数:</th>
            <td colspan="2">
                <s:textarea name="comTask.parameter" id="comTask_parameter" cssStyle="width: 600px;height: 50px;"/>
            </td>
        </tr>
        <tr>
            <th>任务描述:</th>
            <td colspan="2">
                <s:textarea name="comTask.description" id="comTask_description" cssStyle="width: 600px;height: 100px;"/>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3">
                <input type="button" value=" 保 存 " id="saveComTaskButton" style="width:90px;height: 25px;"/>
                <input type="button" value=" 取 消 " id="cancelComTaskButton" style="width:90px;height: 25px;"/>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
<table class="mark_table" cellspacing="1" cellpadding="1" style="width: 600px;">
    <tr>
        <td style="padding: 5px;color: #008000;">
            <p>说明：</p>
            <br>

            <p>“周期尺度”是作用于“执行周期”的 ，比如 周期尺度＝2；执行周期＝分钟；那么就是每2分钟执行一次。周期尺度＝3；执行周期＝小时；那么就是每3小时执行一次。具体每3个小时的哪一分哪一秒执行取决于“计划时间”里的值。
            </p>
            <br>

            <p> 举例说明：</p>
            <br>

            <p>每2分钟的10秒执行，执行周期＝分钟；周期尺度＝2；计划时间＝****-**-** **:**:10； (*号代表任意值) </p>

            <p>每小时的5分钟执行，执行周期＝小时；周期尺度＝1；计划时间＝****-**-** **:05:00； </p>

            <p>每2小时的5分钟执行，执行周期＝小时；周期尺度＝2；计划时间＝****-**-** **:05:00； </p>

            <p>每天的18时5分钟执行，执行周期＝天；周期尺度＝1；计划时间＝****-**-** 18:05:00； </p>

            <p>每2天的18时5分钟执行，执行周期＝天；周期尺度＝2；计划时间＝****-**-** 18:05:00； </p>

            <p>月和年同上，既：执行时间在“计划时间” 的基础上，取“执行周期”时间尺度的下一级。 </p>

            <p>执行周期为“周”的，按具体选择的周几来执行，执行的时间周“天”的设置一样。 </p>
        </td>
    </tr>
</table>
</body>
</html>