<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试页面</title>
</head>
<body>
<%= new java.util.Date() %>
<br/>
<%
    if(request.getParameter("MemcachedKey") != null){
        String key = request.getParameter("MemcachedKey");
        com.lvmama.comm.utils.MemcachedUtil memcachedUtil = com.lvmama.comm.utils.MemcachedUtil.getInstance();
        out.println("MemcachedUtil:" + memcachedUtil.get(key)+"<br>");

        out.println("Session:" + memcachedUtil.get(key, true)+"<br>");

        com.lvmama.comm.utils.MemcachedSeckillUtil memcachedSeckillUtil = com.lvmama.comm.utils.MemcachedSeckillUtil.getInstance();
        out.println("MemcachedSeckillUtil:" + memcachedSeckillUtil.get(key)+"<br>");

        com.lvmama.comm.utils.MemcachedCalendarUtil memcachedCalendarUtil = com.lvmama.comm.utils.MemcachedCalendarUtil.getInstance();
        out.println("MemcachedCalendarUtil:" + memcachedCalendarUtil.get(key)+"<br>");
    }
%>
</body>
</html>