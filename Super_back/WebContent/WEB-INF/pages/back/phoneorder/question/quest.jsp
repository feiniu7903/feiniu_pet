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
	var isClose = ${isClose};
	function subProdQuestion(){
		if($("#type").val()==""){
			alert("请选择问题类型");
			$("#type").focus();
			return false;
		}
		if($.trim($("#questionContent").val())==""){
			alert("请输入问题反馈内容");
			$("#questionContent").focus();
			return false;
		}
		 var m = $("#questionForm").getForm({   
            prefix:''  
        }); 
        $.ajax({
  type: "POST",
  dataType: "html",
  url: "<%=basePath%>count/saveCountInfo.do",
  async:false,
  data:m,
  timeout:3000,
  error:function(a,b,c){
	if(b=="timeout"){
	  alert("请求超时");
  	} else if(b=="error"){
	  alert("请求出错");
  	}
  },
  success: function(data){
	var d = eval(data);
 	if(d==true){
 		alert("提交成功!");
 		if(isClose) {
 			$("select").each(function(){$(this).show();});
			$("#infoQuestion").hide();
 		}
 
 	}else if (d==false){
 		alert("提交出错");
 	}
  }
  }); 
		
	}
	   
	</script>
  </head>
  
  <body>
  <s:form id="questionForm" theme="simple">
   <s:select list="typeList" name="count.typeId" id="type" listKey="typeId" listValue="typeName" headerKey="" headerValue="请选择"></s:select> 
   	<input type="hidden" name="count.userName" value="${userName}"/>
  	 <input type="hidden" name="count.productId" value="${id}" />
     <input name="count.content" type="text" id="questionContent" size="50" style="margin-left:50px;" /> 
     <input type="button" name="btnSubProdQuestion" onclick="subProdQuestion();" value="保存" />
     <s:if test="isClose == 'true'">
		<p style="text-align: center;"><input type="button" name="btnInfoQuestionClose" value="关闭" onclick="$('#infoQuestion').hide();" /></p>     	
     </s:if>
     </s:form>
  </body>
</html>
