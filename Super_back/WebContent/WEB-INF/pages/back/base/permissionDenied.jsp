<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/backstage/mis.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="100%" border="0">
  <tr> 
    <td align="center">对不起，您<font color="#FF0000">没有权限</font>访问此地址 或者 您已经<font color="#FF0000">长时间没有操作</font>！</td>
  </tr>
  <tr> 
    <td align="center">请选择<strong><font color="#0000FF">返回</font></strong>按钮返回上一地址或者选择<font color="#0000FF"><strong>重新登录</strong></font>按钮重新登录！</td>
  </tr>
  <tr> 
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"> 
		<input name="button" type="button" class="button" value="返回" onclick="javascript:window.history.back();"/>
      	<input name="button" type="button" class="button" value="重新登录" onclick='javascript:window.parent.parent.location="<%=basePath%>login.do"'/>
	</td>
  </tr>
</table>
</body>
</html>
