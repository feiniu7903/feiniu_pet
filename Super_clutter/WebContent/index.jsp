<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>大家好！我是驴妈妈的邱国宾^_^</title>
    <meta charset="utf-8">
  </head>
  <body>
    <%
    	String client_id = com.lvmama.clutter.utils.DistributionParseUtil.getPropertiesByKey("tuangou.baidu.apiKey");
    	String redirect_uri = com.lvmama.clutter.utils.DistributionParseUtil.getPropertiesByKey("tuangou.baidu.authorizationCode.callbackUrl");
    %>
    <a href="https://openapi.baidu.com/oauth/2.0/authorize?client_id=<%=client_id%>&response_type=code&redirect_uri=<%=redirect_uri%>">getAccessTokenByAuthorizationCode</a>
    <a href="http://guobin.lvmama.com/fenxiao/baidu/tuangou/removeAuthorizationCodeAccessToken.do">removeAuthrizationCodeAccessToken</a>
    <a href="http://guobin.lvmama.com/fenxiao/baidu/tuangou/removeClientCredentialsAccessToken.do">removeClientCredentialsAccessToken</a>
    <form action="/clutter/router/rest.do" method="post" >
    	<input name="method" type="text" >
    	<input name="date" type="text" >
    	<input name="departure" type="text"  >
    	<input name="arrival" type="text" >
    	<input name="line" type="text" >
    	<input id="trainQuery" type="button" value="提交">
    </form>
    <script type="text/javascript" src="jquery.mobile/jquery-1.8.2.js"></script>
    <script type="text/javascript">
    $(function(){
    	$("#trainQuery").click(function(){
    		var data = {
   	    		"method":$("input[name='method']").val(),
   	    		"date":$("input[name='date']").val(),
   	    		/* "departure":encodeURI(encodeURI($("input[name='departure']").val())),
   	    		"arrival":encodeURI(encodeURI($("input[name='arrival']").val())), */
   	    		/* "departure":encodeURI($("input[name='departure']").val()),
   	    		"arrival":encodeURI($("input[name='arrival']").val()), */
   	    		"departure":$("input[name='departure']").val(),
   	    		"arrival":$("input[name='arrival']").val(),
   	    		"line":$("input[name='line']").val()
   	    	};
    		console.info(data);
   	    	$.ajax({
   	   		   type: "POST",
   	   		   url: "/clutter/router/rest.do",
   	   		   data: data,
   	   		   success: function(msg){
   	   		     alert( "Data Saved: " + msg );
   	   		   }
   	   		});
    	});
    });
    </script>
  </body>
</html>
