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

                var newOrderId =$("#ncComplaint_new_orderId").val();

/*
                if($.trim(newOrderId)==""){
                    alert("订单号不能为空");
                    return;
                }
*/
               if($.trim(newOrderId)!="" && isNaN($.trim(newOrderId))){
                    alert("订单号必须是数字");
                   return;
                }

                $.post(
                        "/super_back/order/complaint/updateOrderId.do",
                        $("#update_order_id_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                alert("订单号修改成功");
                                window.location.reload(window.location.href);
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
    <form id="update_order_id_from" style="text-align: center;">
        <s:hidden name="ncComplaint.complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> 订单号：</td>
                <td class="nc_tr_body al"><input type="text" name="ncComplaint.orderId" id="ncComplaint_new_orderId" value="<s:property value="ncComplaint.orderId"/>"></td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存" style="align: center;"/>
    </form>
</div>
</body>
</html>