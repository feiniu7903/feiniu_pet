<%@ page session="false" %>
<%
  String serviceId = (String) request.getAttribute("serviceId");
%>
<html>
<head>
<title>驴妈妈会员中心</title>
 <script>
  window.location.href="<%= serviceId %>";
 </script>
</head>

<body>
 <noscript>
  <p>
   点击 <a href="<%= serviceId %>">这里</a>
   返回您刚刚请求的页面
  </p>
 </noscript>
</body>

</html>
