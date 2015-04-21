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
                $.post(
                        "/super_back/order/complaint/transferComplaint.do",
                        $("#transfer_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                alert("转移客服成功");
                                $("#transfer_div").dialog("close");
                                refreshComplaintTrackingList();
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
    <form id="transfer_from" style="text-align: center;">
        <s:hidden name="ncComplaintTracking.complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> *投诉客服编号：</td>
                <td class="nc_tr_body al"><input type="text" name="permUser.userName"/></td>
            </tr>
            <tr>
                <td class="nc_tr_head ar"> *处理信息：</td>
                <td class="nc_tr_body al"><textarea name="ncComplaintTracking.details" style="width: 300px;height: 60px;"></textarea></td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存" style="align: center;"/>
    </form>
</div>
</body>
</html>