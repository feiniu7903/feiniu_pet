<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    	<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/mis.css">
     	 <script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js"></script>
     <script type="text/javascript" charset="utf-8" src="<%=basePath %>/kindeditor-3.5.1/kindeditor.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/base/form.js"></script>
	<script type="text/javascript">
	   var path='<%=basePath%>';
	</script>
	<script type="text/javascript">
	function save(){
	
	var cnn = KE.util.getData('contentA');
	$("#fautureContent").val(cnn);
	var m = $('#viewContentForm').getForm({   
	            prefix:''  
	        }); 
	        	$.ajax({type:"POST", 
						        url:path+"/view/saveViewContent.do", 
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
	</script>
	
  </head>
  
  <body style="padding: 0 0 0 0; margin:0 0 0 0; font-size: 12px">
  <form id="viewContentForm" action="">
  产品经理推荐：<br/>
  <table width="100%" class="datatable">
  <tr>
  <td width="10%">
  内容：
  <td>
   <td>
   <s:hidden name="pageId"></s:hidden>
  <s:textarea rows="4" cols="80" name="recommendContent" ></s:textarea>
  <td>
  </tr>
  
  </table>
  
  
  产品特色
  <br/>
    <table width="100%" class="datatable">
  <tr>
  <td width="10%">
  内容：
  <td>
   <td>
<s:hidden id="fautureContent" name="fautureContent"></s:hidden>
													   		 	<textarea id="contentA" name="contentA"
													   													style="width: 780px; height: 400px; visibility: hidden;">
													   													${fautureContent}
													   													</textarea>
													   													<script type="text/javascript">
																								   		 function showKindEditor(id){
															
																								   		    KE.show({
																								   		        id : 'content'+id,
																								   		        cssPath : '/FCKEditor/skins/'
																								   		    });
																								   		 }
																								   		 </script>
																								   		 <script type="text/javascript">showKindEditor('A')</script>
																								   		 <input value="提交" name="btnSaveViewContent" type="button" onclick="save()"></input>

  <td>
  </tr>
  
  </table>
  </form>

    </body>
</html>
