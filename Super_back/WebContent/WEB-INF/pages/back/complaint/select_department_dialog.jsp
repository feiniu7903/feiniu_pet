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
                var departmentNames = [];
                var i = 0;
                $("input[name='department']:checked").each(function () {
                    if (true == $(this).attr("checked")) {
                        departmentNames[i++] = $(this).attr('value');
                    }
                });
                addDutyDetails("部门", departmentNames,"<s:property value="complaintDutyType"/>");
                $("#select_department_div").dialog("close");
            });
        });
    </script>
</head>
<body>
<div>
    <form id="select_department_from" style="text-align: center;">
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head">部门</td>
            </tr>
            <s:iterator value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_RESPONSIBLE_DEPARTMENTS@values()">
                <tr>
                    <td class="nc_tr_body al">
                        <input type="checkbox" name="department" value="<s:property value="cnName"/>"><s:property value="cnName"/>
                    </td>
                </tr>
            </s:iterator>


        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value=" 确定 " style="align: center;"/>
    </form>
</div>
</body>
</html>