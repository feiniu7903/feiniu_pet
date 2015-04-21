<%--
  Created by IntelliJ IDEA.
  User: troy-kou
  Date: 13-12-11
  Time: 下午4:42
  Email:kouhongyu@163.com
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>所有门票产品</title>
    <link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css">
    <link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css">
    <script src="../js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <%@ include file="/WEB-INF/pages/back/base/timepicker.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#addTaskButton").click(function () {
                window.location.href = "<%=basePath%>pub/editComTask.do";
            });

            $("#checkboxAll").click(function () {
                $(".taskIdClass").attr("checked", this.checked);
            });

            $("#delTaskButton").click(function () {
                var taskIds = "";

                $("input[class='taskIdClass']:checkbox:checked").each(function () {
                    taskIds += this.value + ",";
                });

                if (taskIds.length == 0) {
                    alert("请选择任务");
                    return;
                }
                taskIds.substr(0, taskIds.length - 1);
                del(taskIds);
            });

        });

        function delComTask(taskId) {
            del(taskId);
        }
        function del(taskId) {
            if (confirm("确定删除任务吗？")) {
                $.post(
                        "<%=basePath%>pub/delComTask.do",
                        {
                            "taskIds": taskId
                        },
                        function (data) {

                            if (data.jsonMap.status == "success") {
                                alert("删除任务成功");
                                location.reload(window.location.href);
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );
            }
        }

        function editComTask(taskId) {
            window.location.href = "<%=basePath%>pub/editComTask.do?comTask.taskId=" + taskId;
        }

        function showComTaskLogList(taskId) {

            $('#com_task_log_div').showWindow({
                        title: '日志列表',
                        width: 860,
                        height: 500,
                        data: {
                            "taskId": taskId
                        }
                    }
            );
        }
        function showComTaskLogInfo(logId) {
            $('#com_task_log_info_div').showWindow({
                        title: '日志明细',
                        width: 800,
                        height: 500,
                        data: {
                            "comTaskLog.logId": logId
                        }
                    }
            );
        }
    </script>
</head>

<body>
<div class="main main02">
    <div class="row1">
        <form id="query_form" action="<%=basePath%>pub/toComTaskList.do" method="post">
            <table border="0" cellspacing="0" cellpadding="0" class="newInput" width="100%">
                <tr>
                    <td><em>任务名：</em></td>
                    <td><s:textfield cssClass="newtext1" name="taskName"/></td>
                    <td><em>状态：</em></td>
                    <td>
                        <s:select list="@com.lvmama.comm.vo.Constant$COM_TASK_STATUS@values()"
                                  listKey="code" listValue="cnName"
                                  headerKey="" headerValue="选择"
                                  name="status" value="status"/>
                    </td>
                    <td><em>是否启用：</em></td>
                    <td>
                        <s:select list="@com.lvmama.comm.vo.Constant$COM_TASK_AVAILABLE@values()"
                                  listKey="code" listValue="cnName"
                                  headerKey="" headerValue="选择"
                                  name="available" value="available"/>
                    </td>
                    <td>
                        <input type="submit" class="button" value=" 查 询 "/>
                    </td>
                    <td>
                        <input type="button" class="button" id="addTaskButton" value=" 新增任务 "/>
                    </td>
                    <td>
                        <input type="button" class="button" id="delTaskButton" value=" 删 除 "/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;">
            <tr class="newTableTit">
                <td><input type="checkbox" id="checkboxAll"></td>
                <td>任务ID</td>
                <td>任务名称</td>
                <td>运行状态</td>
                <td>是否启用</td>
                <td>下次运行时间</td>
                <td>上次运行结果</td>
                <td>上次运行时间</td>
                <td>操作</td>
            </tr>

            <s:iterator value="comTaskMapList">
                <tr>
                    <td>
                        <input type="checkbox" class="taskIdClass" value="<s:property value="comTask.taskId"/>">
                    </td>
                    <td><s:property value="comTask.taskId"/></td>
                    <td style="text-align: left;padding-left: 10px;"><s:property value="comTask.taskName"/></td>
                    <td>
                        <s:if test="comTask.status=='WAIT'">
                        <span style="color: darkgoldenrod;font-weight: bold;">
                        <s:property value="@com.lvmama.comm.vo.Constant$COM_TASK_STATUS@getCnName(comTask.status)"/>
                        </span>
                        </s:if>
                        <s:if test="comTask.status=='RUN'">
                        <span style="color:darkgreen;font-weight: bold;">
                        <s:property value="@com.lvmama.comm.vo.Constant$COM_TASK_STATUS@getCnName(comTask.status)"/>
                            <img src="<%=basePath%>img/base/ajaxload.gif" alt="">
                        </span>
                        </s:if>
                        <s:if test="comTask.status=='END'">
                        <span style="color:darkblue;font-weight: bold;">
                        <s:property value="@com.lvmama.comm.vo.Constant$COM_TASK_STATUS@getCnName(comTask.status)"/>
                        </span>
                        </s:if>
                    </td>
                    <td>
                        <s:if test="comTask.available=='ENABLE'">
                        <span style="color:darkgreen;font-weight: bold;">
                        </s:if>
                        <s:else>
                        <span style="color:dimgray;">
                        </s:else>
                            <s:property value="@com.lvmama.comm.vo.Constant$COM_TASK_AVAILABLE@getCnName(comTask.available)"/>
                        </span>
                    </td>
                    <td>
                        <s:date name="comTask.nextRunTime" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <s:property value="lastStatus"/>
                    </td>
                    <td>
                        <s:property value="lastTime"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" onclick="editComTask(<s:property value="comTask.taskId"/>)">修改</a>
                        <a href="javascript:void(0);" onclick="showComTaskLogList(<s:property value="comTask.taskId"/>)">日志</a>
                        <a href="javascript:void(0);" onclick="delComTask(<s:property value="comTask.taskId"/>)">删除</a>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <table width="90%" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>

</div>
<div id="com_task_log_div" url="${basePath}pub/showComTaskLogDialog.do"></div>
<div id="com_task_log_info_div" url="${basePath}pub/showComTaskLogInfoDialog.do"></div>
</body>
</html>


