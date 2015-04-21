<%@page import="com.alibaba.fastjson.serializer.SerializerFeature"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.lvmama.comm.utils.StringUtil"%>
<%@page import="com.lvmama.comm.spring.SpringBeanProxy"%>
<%@page import="com.lvmama.operate.service.EdmSubscribeBatchJobService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div><a href="${pageContext.request.contextPath }/edm/ts/ts.jsp">返回</a></div>
	<pre>
	<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	String sql = request.getParameter("sql");
	if(StringUtil.isNotEmptyString(sql)){
		EdmSubscribeBatchJobService service = (EdmSubscribeBatchJobService)SpringBeanProxy.getBean("edmSubscribeBatchJobService");
		String result = JSON.toJSONString(service.searchBySql(sql),SerializerFeature.PrettyFormat,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
		out.print(result);
	}else{
		out.print("not result");
	}
	%>
	</pre>
</body>
</html>