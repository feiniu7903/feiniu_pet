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
                        "/super_back/order/complaint/updateRelatedOrder.do",
                        $("#update_related_order_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                alert("关联订单修改成功");
                                resetRelatedOrderTable(data.jsonMap.relatedOrders);
                                $("#edit_related_order_div").dialog("close");
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
    <form id="update_related_order_from" style="text-align: center;">
        <s:hidden name="ncComplaint.complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> 关联订单：</td>
                <td class="nc_tr_body al">
                    <input type="text"
                           name="ncComplaint.relatedOrder"
                           id="ncComplaint_relatedOrder"
                           value="<s:property value="ncComplaint.relatedOrder"/>">    当输入多个订单时，用英文“,”分隔
                </td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存" style="align: center;"/>
    </form>
</div>
</body>
</html>