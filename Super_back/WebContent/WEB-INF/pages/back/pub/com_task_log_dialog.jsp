<%--
  Created by IntelliJ IDEA.
  User: troy-kou
  Date: 14-1-9
  Time: 下午3:16
  Email:kouhongyu@163.com
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
</head>

<body>
<iframe src="<%=request.getContextPath()%>/pub/toComTaskLogList.do?taskId=<s:property value="taskId"/>" width="840px" height="500px" frameborder="0"></iframe>
</body>
</html>

