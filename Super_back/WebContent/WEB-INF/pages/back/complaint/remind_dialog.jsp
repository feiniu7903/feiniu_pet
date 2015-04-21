<%--
  Created by IntelliJ IDEA.
  User: troy-kou
  Date: 13-11-4
  Time: 上午11:52
  Email:kouhongyu@163.com
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%

%>
<html>
<head>
    <title></title>
    <style type="text/css">
        .ar {
            text-align: right;
            padding-right: 5px;
        }

        .al {
            text-align: left;
            padding-left: 5px;
        }
    </style>
    <script>
        $(function () {
            $("#saveButton").click(function () {

                var ncComplaintRemind_remindTime = $("#ncComplaintRemind_remindTime").val();

                if($.trim(ncComplaintRemind_remindTime)==""){
                    alert("“提醒时间”不能为空");
                    return;
                }

                var ncComplaintRemind_processInfo = $("#ncComplaintRemind_processInfo").val();

                if($.trim(ncComplaintRemind_processInfo)==""){
                    alert("“处理信息”不能为空");
                    return;
                }

                $.post(
                        "/super_back/order/complaint/saveComplaintRemind.do",
                        $("#remind_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                alert("保存成功");
                                $("#remind_div").dialog("close");
                                refreshComplaintRemindList();
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );
            });
        });
    </script>
</head>
<body>
<div>
    <form id="remind_from" style="text-align: center;">
        <s:hidden name="ncComplaintRemind.remindId"/>
        <s:hidden name="ncComplaintRemind.complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> *提醒时间：</td>
                <td class="nc_tr_body al">
                    <input type="text"
                           name="ncComplaintRemind.remindTime"
                           id="ncComplaintRemind_remindTime"
                           value="<s:date name="ncComplaintRemind.remindTime" format="yyyy-MM-dd HH:mm:ss"/>"
                           onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                           class="input_text01 Wdate"
                           style="width: 180px;"/>
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar"> *处理信息：</td>
                <td class="nc_tr_body al"><s:textarea name="ncComplaintRemind.processInfo" id="ncComplaintRemind_processInfo" cssStyle="width: 350px;height: 60px;"/></td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存" style="align: center;"/>
    </form>
</div>
</body>
</html>