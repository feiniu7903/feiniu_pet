<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
<script type="text/javascript">
function selectReceiver(obj){
var rid = $(obj).val();
$("#selectReceiver").reload({receiverId:rid,userId:'${userId}'});
}
$(document).ready(function(){
$("#selectReceiver").reload({receiverId:'${receiverId}'});
});
</script>
  </head>
  
  <body>
 	       <p class="selectname" style="text-align: left;">已有取票人选择：
 	        <s:select id="usrReceiver" value="receiverId" headerKey="" headerValue="请选择" list="receiversList" listKey="receiverId" listValue="receiverName" onchange="selectReceiver(this);"></s:select>
    <s:hidden name="userId" id="userId"></s:hidden>&nbsp;&nbsp;
    <a href="javascript:void(0)" id="addTourise" onclick="personByAdd();">新增</a>
		   </p>
		  <%-- <div id="selectReceiver" href="<%=basePath%>usrReceivers/loadReciever.do"> 
        
  </div> --%>
  
  </body>
</html>
