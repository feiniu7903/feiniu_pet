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
    <title></title>
    <link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css">
    <link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css">
    <script src="../js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <%@ include file="/WEB-INF/pages/back/base/timepicker.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
     <script type="text/javascript">
         function showComTaskLogInfo(logId){
             parent.showComTaskLogInfo(logId);

         }
     </script>
</head>

<body>
<div class="main main02" style="width: 820px;">
    <div class="row1">

    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;width: 800px;">
            <tr class="newTableTit">
                <td>日志ID</td>
                <td>任务名称</td>
                <td>返回状态</td>
                <td>任务开始时间</td>
                <td>任务结束时间</td>
                <td></td>
            </tr>

            <s:iterator value="comTaskLogList">
                <tr>

                    <td><s:property value="logId"/></td>
                    <td style="text-align: left;padding-left: 10px;"><s:property value="taskName"/></td>
                    <td>
                        <s:if test="resultStatus==1">
                        完成
                        </s:if>
                        <s:if test="resultStatus==2">
                        部分完成
                        </s:if>
                        <s:if test="resultStatus==3">
                        未完成
                        </s:if>
                    </td>
                    <td>
                        <s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" onclick="showComTaskLogInfo(<s:property value="logId"/>)">查看</a>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <table width="700" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>

</div>

</body>
</html>


