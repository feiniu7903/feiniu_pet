<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/affix_upload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/lvmama_common.js"></script>

    <script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>

    <script src="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>


    <script type="text/javascript" src="<%=request.getContextPath()%>/js/complaint/complaint_edit.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/cc.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/complaint/complaint_edit.css"/>
    <script type="text/javascript">


        function ok() {
            var userNames = [];
            var selectUserType = $("#selectUserType").val();
            var i = 0;
            $("input[name='userNames']:checked").each(function () {
                if (true == $(this).attr("checked")) {
                    userNames[i++] = $(this).attr('value');
                }
            });

            if (userNames.length == 0) {
                alert("请选择用户");
            } else {
                if(selectUserType == "SELECT_STAFF"){
                parent.addDutyDetails("员工", userNames,"<s:property value="complaintDutyType"/>");
                }
                if(selectUserType == "TRANSFER"){
                parent.transferComplaint(userNames);
                }


                parent.closeStaffDialog();
            }

        }
    </script>
</head>
<body>
<ul class="gl_top">
    <form action="<%=request.getContextPath()%>/order/complaint/searchStaff.do" method="post">
        <s:hidden name="selectUserType"/>
        <s:hidden name="complaintDutyType"/>
        用户名：<input type="text" name="complaintName" value="<s:property value="complaintName" />" style="width: 80px;">
        姓名：<input type="text" name="realName" value="<s:property value="realName" />" style="width: 80px;">
        <input type="submit" value="查询">
    </form>
</ul>
<div class="tab_top"></div>
<table class="nc_table" cellspacing="1" cellpadding="1">
    <tr class="nc_tr_head">
        <th></th>
        <th>用户名</th>
        <th>真实姓名</th>
        <th>部门</th>
        <%--<th>是否有效</th>--%>
        <th>职务</th>
    </tr>
    <s:iterator value="permUserPage.items" var="item">
        <tr class="nc_tr_body">
            <td>
                <input type="checkbox" name="userNames" value="<s:property value="userName"/>">
            </td>
            <td><s:property value="userName"/></td>
            <td><s:property value="realName"/></td>
            <td><s:property value="departmentName"/></td>
            <%--<td><s:property value="zhValid"/></td>--%>
            <td><s:property value="position"/></td>

        </tr>
    </s:iterator>
    <%--<tr>
        <td colspan="1">
            总条数：<s:property value="permUserPage.totalResultSize"/>
        </td>
        <td colspan="7" align="right">
            <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permUserPage)"/>
        </td>
    </tr>--%>
</table>
<input type="button" value=" 确 定 " onclick="ok()" style="margin-left: 200px;margin-top: 10px;">
</body>
</html>