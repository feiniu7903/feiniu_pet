<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<s:property value="contentFirstFix" escape="false"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>themes/mis.css">
    <script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/base/form.js"></script>
	<script type="text/javascript">	   
	var path='<%=basePath%>';	
	function save(){
	var m = $('#viewContentForm').getForm({   
	            prefix:''  
	        }); 
	        	$.ajax({type:"POST", 
						        url:"<%=basePath%>ordEcontract/saveEcontract.do", 
						        data:m,
						        success:function (data) {
									var d = eval(data);
									if(d){
									alert("修改成功！");
									}else {
									alert("修改发生错误！");
									}
						       }
						});
	}
	function content_reset(){
			document.forms[0].action="<%=basePath%>ordEcontract/detailEcontract.do";
			document.forms[0].method = "post";
			document.forms[0].submit();
	}
	function preview(){
			document.forms[0].action="<%=basePath%>ord/downPreviewOrderContract.do";
			document.forms[0].method = "post";
			document.forms[0].submit();
	}
	</script>
	<title>修改合同</title>
  </head>
  <body>
  <form id="viewContentForm" name="contentForm" method="post" action="">
  	<div style="overflow: auto; width: 700px;border:1px solid #D6D6D6;margin:auto;">
  	  <s:hidden id="orderId" name="orderId"></s:hidden>
  	  <s:hidden id="template" name="template"/>
  	  <s:iterator value="contentData" id="data">
  	  	<input type="hidden" name="contentData['<s:property value="key"/>']" value="<s:property value="value"/>"/>
  	  </s:iterator>
  	  <s:iterator value="complementMap" id="data">
  	  	<input type="hidden" name="complementData['<s:property value="key"/>']" value="<s:property value="value"/>"/>
  	  </s:iterator>
  	  <s:property value="content" escape="false"/>
  	</div>
  	<div style="text-align: center; width: 700px;margin:auto;padding-top:10px;">
  		<input value="预　 览" name="btnPreviewViewContent" type="button" onclick="preview()"> &nbsp;&nbsp;
  		<input value="提　交" name="btnSaveViewContent" type="button" onclick="save()"> &nbsp;&nbsp;
		<input value="刷　新" name="btnResetViewContent" type="button" onclick="content_reset()" >
	</div>
  	</form>
  </body>
</html>
