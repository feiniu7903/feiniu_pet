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
                var supplierNames = [];
                var i = 0;
                $("input[name='supplierName']:checked").each(function () {
                    if (true == $(this).attr("checked")) {
                        supplierNames[i++] = $(this).attr('value');
                    }
                });
                addDutyDetails("供应商", supplierNames,"<s:property value="complaintDutyType"/>");
                $("#select_order_supplier_div").dialog("close");
            });
        });
    </script>
</head>
<body>
<div>
    <form id="select_department_from" style="text-align: center;">
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head"></td>
                <td class="nc_tr_head"> 供应商ID</td>
                <td class="nc_tr_head"> 供应商名称</td>
                <td class="nc_tr_head"> 产品ID</td>
                <td class="nc_tr_head"> 产品类型</td>
                <td class="nc_tr_head"> 产品名称</td>

            </tr>
            <s:iterator value="ordOrderItemMetaList">
                <tr>
                    <td class="nc_tr_body">
                        <input type="checkbox" name="supplierName" value="<s:property value="supplierName"/>">
                    </td>
                    <td class="nc_tr_body">
                        <s:property value="supplierId"/>
                    </td>
                    <td class="nc_tr_body">
                        <s:property value="supplierName"/>
                    </td>
                    <td class="nc_tr_body">
                        <s:property value="orderItemMetaId"/>
                    </td>
                    <td class="nc_tr_body">
                        <s:property value="productType"/>
                    </td>
                    <td class="nc_tr_body"><s:property value="productName"/> </td>
                </tr>
            </s:iterator>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value=" 确定 " style="align: center;"/>
    </form>
</div>
</body>
</html>