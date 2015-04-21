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

    <link rel="stylesheet" href="../js/op/groupBudget/component/jquery/jquery-ui-1.8.18.custom.css">
    <script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            $("#addActivityButton").click(function () {
                window.location.href = "<%=basePath%>mark_activity/editMarkActivity.do";
            });

            $("#checkboxAll").click(function () {
                $(".actIdClass").attr("checked", this.checked);
            });

            $("#delActivityButton").click(function () {
                var actIds = "";

                $("input[class='actIdClass']:checkbox:checked").each(function () {
                    actIds += this.value + ",";
                });

                if (actIds.length == 0) {
                    alert("请选择活动");
                    return;
                }
                actIds.substr(0, actIds.length - 1);
                del(actIds);
            });

            $("#wait_div").dialog({
                autoOpen: false,
                modal: true,
                show: {
                    effect: "blind",
                    duration: 100
                },
                hide: {
                    effect: "explode",
                    duration: 100
                },
                buttons: {
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });

            $("#message_div").dialog({
                autoOpen: false,
                modal: true,
                show: {
                    effect: "blind",
                    duration: 100
                },
                hide: {
                    effect: "explode",
                    duration: 100
                },
                buttons: {
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });

        });

        function delMarkActivity(actId) {
            del(actId);
        }
        function del(actId) {
            if (confirm("确定删除活动吗？")) {
                $.post(
                        "<%=basePath%>mark_activity/delMarkActivity.do",
                        {
                            "actIds": actId
                        },
                        function (data) {

                            if (data.jsonMap.status == "success") {
                                alert("删除活动成功");
                                location.reload(window.location.href);
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );
            }
        }

        function editMarkActivity(actId) {
            window.location.href = "<%=basePath%>mark_activity/editMarkActivity.do?markActivity.actId=" + actId;
        }

        function send(actId, sendOffTimes) {

            $("#wait_div").dialog("open");

            $.ajax({
                url: "<%=basePath%>mark_activity/send.do",
                type: "post",
                timeout: (50 * 1000),
                data: {
                    "markActivity.actId": actId
                },
                success: function (data) {
                    if (data.jsonMap.status == "success") {
                        message("wait_div", "发送成功,共发送" + data.jsonMap.sendAmount + "个邮件");
                    } else {
                        message("wait_div", data.jsonMap.status);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == "timeout") {
                        newSendOffTimes = sendOffTimes;
                        setInterval("waitReturn(" + actId + "," + sendOffTimes + ")", (10 * 1000));
                    }
                }
            });
        }

        function message(target, info) {
            $("#" + target).dialog("close");
            $("#message_div").html(info);
            $("#message_div").dialog("open");
        }

        function waitReturn(actId, sendOffTimes) {
            if (sendOffTimes == newSendOffTimes) {
                $.post(
                        " <%=basePath%>mark_activity/getMarkActivityInfo.do",
                        {
                            "markActivity.actId": actId
                        },
                        function (data) {
                            if (data.jsonMap.status == "success") {

                                newSendOffTimes = data.jsonMap.markActivity.markActivityItemEmail.sendOffTimes;

                                if (newSendOffTimes > sendOffTimes) {
                                    message("wait_div", "发送成功,共发送" + data.jsonMap.sendAmount + "个邮件");
                                }
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );
            }
        }
        var newSendOffTimes = 0;
    </script>
</head>

<body>
<div class="main main02">
    <div class="row1">
        <form id="query_form" action="<%=basePath%>mark_activity/toMarkActivityList.do" method="post">
            <table border="0" cellspacing="0" cellpadding="0" class="newInput" width="100%">
                <tr>
                    <td><em>关键字：</em></td>
                    <td><s:textfield cssClass="newtext1" name="keyword"/></td>
                    <td><em>状态：</em></td>
                    <td>
                        <s:select list="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_STATUS@values()"
                                  listKey="code" listValue="cnName"
                                  headerKey="" headerValue="活动状态"
                                  name="status" value="status"/>
                    </td>
                    <td>
                        <input type="submit" class="button" value=" 查 询 "/>
                    </td>
                    <td>
                        <input type="button" class="button" id="addActivityButton" value=" 新增活动 "/>
                    </td>
                    <td>
                        <input type="button" class="button" id="delActivityButton" value=" 删 除 "/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;">
            <tr class="newTableTit">
                <td><input type="checkbox" id="checkboxAll"></td>
                <td>活动ID</td>
                <td>活动名称</td>
                <td>发送渠道</td>
                <td>负责人</td>
                <td>状态</td>
                <td>最后发送时间</td>
                <td>操作</td>
            </tr>

            <s:iterator value="markActivityList">
                <tr>
                    <td>
                        <input type="checkbox" class="actIdClass" value="<s:property value="actId"/>">
                    </td>
                    <td><s:property value="actId"/></td>
                    <td style="text-align: left;padding-left: 10px;"><s:property value="activityName"/></td>
                    <td>
                        <s:property value="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_CHANNEL@getCnName(markActivityItemEmail.channel)"/>
                    </td>
                    <td><s:property value="personCharge"/></td>
                    <td>
                        <s:property value="@com.lvmama.comm.vo.Constant$MARK_ACTIVITY_STATUS@getCnName(status)"/>
                    </td>
                    <td>
                        <s:date name="markActivityItemEmail.lastSendTime" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" onclick="editMarkActivity(<s:property value="actId"/>)">修改</a>
                        <a href="javascript:void(0);" onclick="delMarkActivity(<s:property value="actId"/>)">删除</a>
                        <a href="javascript:void(0);" onclick="send(<s:property value="actId"/>,<s:property value="markActivityItemEmail.sendOffTimes"/>)">发送</a>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <table width="90%" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>
</div>
<div id="wait_div" style="text-align: center;padding-top: 10px;">
    <p>正在发送，请耐心等待 <img src="<%=basePath%>img/base/ajaxload.gif"></p>
</div>
<div id="message_div" title="Message" style="text-align: center;padding-top: 10px;"></div>
</body>
</html>


